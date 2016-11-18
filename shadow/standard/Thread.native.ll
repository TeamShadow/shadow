; Thread.native.ll
; 
; Author:
; Claude Abounegm

;-------------
; Definitions
;-------------
; Primitives
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

; Object
%shadow.standard..Object = type opaque

; Pointer
%shadow.natives..Pointer = type opaque

; Thread
%shadow.standard..Thread = type opaque

; typedef uintptr_t pthread_t;
%struct.pthread_t = type %uint

; struct pthread_attr_t { unsigned p_state; void* stack; size_t s_size; struct sched_param param; };
%struct.pthread_attr_t = type { %int, %void*, %int, %struct.sched_param }

; struct sched_param { int sched_priority; };
%struct.sched_param = type { %int }

;---------
; Globals
;---------
; used to store the current instance of the thread; Thread->current.
@shadow.standard..Thread_TLS_currentThread = thread_local global %shadow.standard..Thread* null
@shadow.standard..Thread_STATIC_mainThread = global %shadow.standard..Thread* null
@STATIC_nextThreadId = private global %int 0

;---------------------
; Method Declarations
;---------------------
; int pthread_create(pthread_t*, pthread_attr_t*, void* (*start_routine)(void*), void*);
declare %int @pthread_create(%struct.pthread_t*, %struct.pthread_attr_t*, %void* (%void*)*, %void*)

; runnerNative() => ();
declare void @shadow.standard..Thread_MrunnerNative(%shadow.standard..Thread*)

; createMainNative() => (Thread);
declare %shadow.standard..Thread* @shadow.standard..Thread_McreateMainNative(%shadow.standard..Thread*)

declare %void* @extractPointer(%shadow.natives..Pointer*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; get main() => (Thread);
define %shadow.standard..Thread* @shadow.standard..Thread_Mmain(%shadow.standard..Thread*) {
entry:
	%mainThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_STATIC_mainThread
	ret %shadow.standard..Thread* %mainThread
}

; get staticNextId() => (int); (ThreadSafe)
define %int @shadow.standard..Thread_MstaticNextId(%shadow.standard..Thread*) {
entry:
	%currentId = atomicrmw add %int* @STATIC_nextThreadId, %int 1 seq_cst
	ret %int %currentId
}

; spawnThread(Pointer ptr) => (int);
define %int @shadow.standard..Thread_MspawnThread_shadow.natives..Pointer(%shadow.standard..Thread*, %shadow.natives..Pointer*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..Thread*
	store %shadow.standard..Thread* %0, %shadow.standard..Thread** %this.addr
	%this = load %shadow.standard..Thread*, %shadow.standard..Thread** %this.addr

	; get the handle
	%ptr.addr = call %void* @extractPointer(%shadow.natives..Pointer* %1)
	%handle.addr = bitcast %void* %ptr.addr to %struct.pthread_t*
	
	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..Thread* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handle.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_start, %void* %this.void)

	ret %int %call
}

; get handleSize() => (int);
define %int @shadow.standard..Thread_MhandleSize(%shadow.standard..Thread*) {
entry:
	%sizeOfPthread = ptrtoint %struct.pthread_t* getelementptr (%struct.pthread_t, %struct.pthread_t* null, i32 1) to i32
	
	ret %int %sizeOfPthread
}

;---------------------------
; Custom Method Definitions
;---------------------------
; the function ran from the newly spawned thread
define %void* @thread_start(%void*) {
entry:
	%currentThread.addr = bitcast %void* %0 to %shadow.standard..Thread*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..Thread* %currentThread.addr, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..Thread_MrunnerNative(%shadow.standard..Thread* %currentThread.addr)

	ret %void* null
}

; initializes the main thread and set the currentThread and mainThread to that instance
define void @shadow.standard..Thread_MinitMainThread() {
entry:
	; we initialize the dummy Thread for the main thread
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread_McreateMainNative(%shadow.standard..Thread* null)
	
	; each thread needs to be able to get a reference to its own Thread, so we set its instance to the currentThread TLS.
	store %shadow.standard..Thread* %mainThread, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	
	; each thread should also be able to reference the main thread from anywhere, as it is the root of all threads.
	store %shadow.standard..Thread* %mainThread, %shadow.standard..Thread** @shadow.standard..Thread_STATIC_mainThread
	
	ret void
}
