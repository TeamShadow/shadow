%boolean = type i1
%byte = type i8
%ubyte = type i8
%short = type i16
%ushort = type i16
%int = type i32
%uint = type i32
%code = type i32
%long = type i64
%ulong = type i64
%float = type float
%double = type double
%void = type i8

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions

; Object
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type opaque

; Class
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type opaque

; Iterator
%shadow.standard..Iterator_methods = type opaque

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type opaque

; AddressMap
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

; Exception
%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type opaque

; System
%shadow.standard..System_methods = type opaque
%shadow.standard..System = type opaque

; CanRun
%shadow.standard..CanRun_methods = type opaque

;===================================================================================================
; Externals

;-------------
; Definitions
;-------------

; typedef uintptr_t pthread_t;
%struct.pthread_t = type %uint

; struct pthread_attr_t { unsigned p_state; void* stack; size_t s_size; struct sched_param param; };
%struct.pthread_attr_t = type { %int, %void*, %int, %struct.sched_param }

; struct sched_param { int sched_priority; };
%struct.sched_param = type { %int }

;---------
; Methods
;---------

; int pthread_create(pthread_t*, pthread_attr_t*, void* (*start_routine)(void*), void*);
declare %int @pthread_create(%struct.pthread_t*, %struct.pthread_attr_t*, %void* (%void*)*, %void*)

; int pthread_join(pthread_t, void**);
declare %int @pthread_join(%struct.pthread_t, %void**)

declare noalias i8* @calloc(i32, i32) nounwind
declare void @free(i8*) nounwind
;===================================================================================================

; Current definition
; 0: class (Class)
; 1: _methods
; 2: runner (shadow:standard@CanRun)
; 3: handle (shadow:standard@Object)
; 4: name (shadow:standard@String)
; 5: parent (shadow:standard@ThreadWorker)
; 6: id (int)

; ThreadWorker
%shadow.standard..ThreadWorker_methods = type opaque
%shadow.standard..ThreadWorker = type { %shadow.standard..Class*, %shadow.standard..ThreadWorker_methods* , { %shadow.standard..CanRun_methods*, %shadow.standard..Object* }, %struct.pthread_t*, %shadow.standard..String*, %shadow.standard..ThreadWorker*, %int }

; the runner which is executed from the newly spawned thread
declare void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker*)

@nextThreadId = global %int 0
; getNextId() => (int); (ThreadSafe)
define %int @shadow.standard..ThreadWorker_MgetNextId(%shadow.standard..ThreadWorker*) {
entry:
	%currentId = atomicrmw add i32* @nextThreadId, i32 1 acquire
	ret %int %currentId
}

; used to store the current instance of the thread; Thread->current.
@shadow.standard..ThreadWorker_currentThread = thread_local global %shadow.standard..ThreadWorker* null

define %void* @thread_func(%void* %currentThread) {
entry:
	%currentThread.addr = bitcast %void* %currentThread to %shadow.standard..ThreadWorker*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..ThreadWorker* %currentThread.addr, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker* %currentThread.addr)

	ret %void* null
}

; spawnThread() => (int);
define %int @shadow.standard..ThreadWorker_MspawnThread(%shadow.standard..ThreadWorker*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; allocate space for the new handle
	%sizeOfPthread = ptrtoint %struct.pthread_t* getelementptr (%struct.pthread_t, %struct.pthread_t* null, i32 1) to i32
	%handle.addr.0 = getelementptr inbounds %shadow.standard..ThreadWorker, %shadow.standard..ThreadWorker* %this, i32 0, i32 3
	%handle.addr.1 = call noalias i8* @calloc(i32 1, i32 %sizeOfPthread) nounwind
	%handle.addr = bitcast i8* %handle.addr.1 to %struct.pthread_t*
	store %struct.pthread_t* %handle.addr, %struct.pthread_t** %handle.addr.0
	
	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..ThreadWorker* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handle.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_func, %void* %this.void)

	ret %int %call
}

; destroyHandle() => ();
define void @shadow.standard..ThreadWorker_MdestroyHandle(%shadow.standard..ThreadWorker*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr
	
	; free the handle
	%handle.addr.0 = getelementptr inbounds %shadow.standard..ThreadWorker, %shadow.standard..ThreadWorker* %this, i32 0, i32 3
	%handle.addr.1 = load %struct.pthread_t*, %struct.pthread_t** %handle.addr.0
	%handle.addr = bitcast %struct.pthread_t* %handle.addr.1 to i8*
	call void @free(i8* %handle.addr)

	; set the pointer to null so we can test for it in shadow
	store %struct.pthread_t* null, %struct.pthread_t** %handle.addr.0
	
	ret void
}

; joinThread() => (int);
define %int @shadow.standard..ThreadWorker_MjoinThread(%shadow.standard..ThreadWorker*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; load handle
	%handle.addr.0 = getelementptr inbounds %shadow.standard..ThreadWorker, %shadow.standard..ThreadWorker* %this, i32 0, i32 3
	%handle.addr = load %struct.pthread_t*, %struct.pthread_t** %handle.addr.0
	%handle = load %struct.pthread_t, %struct.pthread_t* %handle.addr

	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handle, %void** null)

	ret %int %call
}