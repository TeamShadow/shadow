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

; ThreadWorker
%shadow.standard..ThreadWorker_methods = type opaque
%shadow.standard..ThreadWorker = type opaque

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

; the runner which is executed from the newly spawned thread
declare void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker*)
declare %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker*)
declare void @shadow.standard..ThreadWorker_MunlockMutexNative(%shadow.standard..ThreadWorker*)

; used to store the current instance of the thread; Thread->current.
@shadow.standard..ThreadWorker_currentThread = thread_local global %shadow.standard..ThreadWorker* null
@shadow.standard..ThreadWorker_mainThread = global %shadow.standard..ThreadWorker* null
@nextThreadId = private global %int 0

; get staticNextId() => (int); (ThreadSafe)
define %int @shadow.standard..ThreadWorker_MstaticNextId(%shadow.standard..ThreadWorker*) {
entry:
	%currentId = atomicrmw add %int* @nextThreadId, %int 1 seq_cst
	ret %int %currentId
}

define %void* @thread_func(%void*) {
entry:
	%currentThread.addr = bitcast %void* %0 to %shadow.standard..ThreadWorker*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..ThreadWorker* %currentThread.addr, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..ThreadWorker_MrunnerNative(%shadow.standard..ThreadWorker* %currentThread.addr)

	ret %void* null
}

; spawnThread(nullable Object handle) => (int);
define %int @shadow.standard..ThreadWorker_MspawnThread_shadow.standard..Object(%shadow.standard..ThreadWorker*, %shadow.standard..Object*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; get the handle
	%handle.addr = bitcast %shadow.standard..Object* %1 to %struct.pthread_t*
	
	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..ThreadWorker* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handle.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_func, %void* %this.void)

	ret %int %call
}

; createHandle() => (nullable Object);
define %shadow.standard..Object* @shadow.standard..ThreadWorker_McreateHandle(%shadow.standard..ThreadWorker*) {
entry:
	%sizeOfPthread = ptrtoint %struct.pthread_t* getelementptr (%struct.pthread_t, %struct.pthread_t* null, i32 1) to i32
	%handle.addr.1 = call noalias i8* @calloc(i32 1, i32 %sizeOfPthread) nounwind
	%handle.addr =  bitcast i8* %handle.addr.1 to %shadow.standard..Object*
	
	ret %shadow.standard..Object* %handle.addr
}

; freeHandle(nullable Object handle) => ();
define void @shadow.standard..ThreadWorker_MfreeHandle_shadow.standard..Object(%shadow.standard..ThreadWorker*, %shadow.standard..Object*) {
entry:
	; free the handle
	%handle.addr = bitcast %shadow.standard..Object* %1 to i8*
	call void @free(i8* %handle.addr)
	
	ret void
}

; joinThread(Object handle) => (int);
define %int @shadow.standard..ThreadWorker_MjoinThread_shadow.standard..Object(%shadow.standard..ThreadWorker*, %shadow.standard..Object*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; load handle
	%handle.addr = bitcast %shadow.standard..Object* %1 to %struct.pthread_t*
	%handle = load %struct.pthread_t, %struct.pthread_t* %handle.addr
	
	; we unlock the mutex before joining
	call void @shadow.standard..ThreadWorker_MunlockMutexNative(%shadow.standard..ThreadWorker* %this)
	
	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handle, %void** null)

	ret %int %call
}

; initializes the main thread and set the currentThread and mainThread to that instance
define void @shadow.standard..ThreadWorker_MinitMainThread() {
entry:
	; we initialize the dummy ThreadWorker for the main thread
	%mainThread = call %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker* null)
	
	; each thread needs to be able to get a reference to its own ThreadWorker, so we set its instance to the currentThread TLS.
	store %shadow.standard..ThreadWorker* %mainThread, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread
	
	; each thread should also be able to reference the main thread from anywhere, as it is the root of all threads.
	store %shadow.standard..ThreadWorker* %mainThread, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_mainThread
	
	ret void
}