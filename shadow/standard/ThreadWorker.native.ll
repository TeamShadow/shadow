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
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }

; Class
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }

; Iterator
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }

; AddressMap
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

; Exception
%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }

; System
%shadow.standard..System_methods = type opaque
%shadow.standard..System = type { %shadow.standard..Class*, %shadow.standard..System_methods*  }

; CanRun
%shadow.standard..CanRun_methods = type { void (%shadow.standard..Object*, %shadow.standard..ThreadWorker*)* }

; ThreadWorker
%shadow.standard..ThreadWorker_methods = type opaque
%shadow.standard..ThreadWorker = type { %shadow.standard..Class*, %shadow.standard..ThreadWorker_methods* , { %shadow.standard..CanRun_methods*, %shadow.standard..Object* }, %shadow.standard..ThreadWorker*, %uint, %int }

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

; pthread_t pthread_self();
declare %struct.pthread_t @pthread_self()
;===================================================================================================

; the runner which is executed from the newly spawned thread
declare void @shadow.standard..ThreadWorker_Mrunner(%shadow.standard..ThreadWorker*)

declare void @LockInit(%void** %lock)
declare void @LockEnter(%void** %lock)
declare void @LockExit(%void** %lock)
declare void @LockDestroy(%void** %lock)

; 0: class (Class)
; 1: _methods
; 2: runnable (shadow:standard@CanRun)
; 3: parent (shadow:standard@Thread)
; 4: handle (uint)
; 5: id (int)

; used to store the current instance of the thread; System->currentThread. Each System singleton
; refers to its own thread.
@shadow.standard..ThreadWorker_currentThread = thread_local global %shadow.standard..ThreadWorker* null

@nextThreadId = global %int 0

define %int @shadow.standard..ThreadWorker_MgetNextId(%shadow.standard..ThreadWorker*) {
entry:
	%currentId = atomicrmw add i32* @nextThreadId, i32 1 acquire
	ret %int %currentId
}

define %void* @thread_func(%void* %currentThread) {
entry:
	%currentThread.addr = bitcast %void* %currentThread to %shadow.standard..ThreadWorker*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..ThreadWorker* %currentThread.addr, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..ThreadWorker_Mrunner(%shadow.standard..ThreadWorker* %currentThread.addr)

	ret %void* null
}

; createThread() => (int);
define %int @shadow.standard..ThreadWorker_McreateThread(%shadow.standard..ThreadWorker*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; load handle
	%handle.addr = getelementptr inbounds %shadow.standard..ThreadWorker, %shadow.standard..ThreadWorker* %this, i32 0, i32 4

	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..ThreadWorker* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handle.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_func, %void* %this.void)

	ret %int %call
}

; getCurrentThreadHandle() => (uint);
define %struct.pthread_t @shadow.standard..ThreadWorker_MgetCurrentThreadHandle(%shadow.standard..ThreadWorker*) {
entry:
	%call = call %struct.pthread_t @pthread_self()
	ret %struct.pthread_t %call
}

; joinThread() => (int);
define %int @shadow.standard..ThreadWorker_MjoinThread(%shadow.standard..ThreadWorker*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..ThreadWorker*
	store %shadow.standard..ThreadWorker* %0, %shadow.standard..ThreadWorker** %this.addr
	%this = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** %this.addr

	; load handle
	%handle.addr = getelementptr inbounds %shadow.standard..ThreadWorker, %shadow.standard..ThreadWorker* %this, i32 0, i32 4
	%handle = load %struct.pthread_t, %struct.pthread_t* %handle.addr

	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handle, %void** null)

	ret %int %call
}