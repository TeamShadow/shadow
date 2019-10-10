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

; Thread
%shadow.standard..Thread = type opaque

;---------
; Globals
;---------
; used to store the current instance of the thread; CurrentThread->instance.
@shadow.standard..Thread_TLS_currentThread = thread_local global %shadow.standard..Thread* null
@shadow.standard..Thread_STATIC_mainThread = global %shadow.standard..Thread* null
@STATIC_nextThreadId = private global %int 0

;---------------------
; Method Declarations
;---------------------
; threadStart() => ();
declare void @shadow.standard..Thread_MthreadStart(%shadow.standard..Thread*)
; createMainThread() => (Thread);
declare %shadow.standard..Thread* @shadow.standard..Thread_McreateMainThread(%shadow.standard..Thread*)

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

;---------------------------
; Custom Method Definitions
;---------------------------
; the function ran from the newly spawned thread
define %void* @_shadowThread_ThreadStart(%shadow.standard..Thread* %currentThread) {
entry:
	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..Thread* %currentThread, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..Thread_MthreadStart(%shadow.standard..Thread* %currentThread)

	ret %void* null
}

; initializes the main thread and sets the currentThread and mainThread to that instance
define %shadow.standard..Thread* @shadow.standard..Thread_MinitMainThread() {
entry:
	; we initialize the dummy Thread for the main thread
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread_McreateMainThread(%shadow.standard..Thread* null)
	
	; each thread needs to be able to get a reference to its own Thread, so we set its instance to the currentThread TLS.
	store %shadow.standard..Thread* %mainThread, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	
	; each thread should also be able to reference the main thread from anywhere, as it is the root of all threads.
	store %shadow.standard..Thread* %mainThread, %shadow.standard..Thread** @shadow.standard..Thread_STATIC_mainThread
	
	ret %shadow.standard..Thread* %mainThread
}