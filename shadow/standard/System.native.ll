; System.native.ll
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

; System
%shadow.standard..System = type opaque

; Thread
%shadow.standard..Thread = type opaque

; struct timespec { time_t tv_sec; long tv_nsec; };
%struct.timespec = type { %int, %int }

;---------
; Globals
;---------
@shadow.standard..Thread_TLS_currentThread = external global %shadow.standard..Thread*
@shadow.standard..Thread_STATIC_mainThread = external global %shadow.standard..Thread*

;---------------------------
; Shadow Method Definitions
;---------------------------
; get currentThread() => (Thread);
define %shadow.standard..Thread* @shadow.standard..System_McurrentThread(%shadow.standard..System*) {
entry:
	%currentThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	ret %shadow.standard..Thread* %currentThread
}

; get mainThread() => (Thread);
define %shadow.standard..Thread* @shadow.standard..System_MmainThread(%shadow.standard..System*) {
entry:
	%mainThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_STATIC_mainThread
	ret %shadow.standard..Thread* %mainThread
}
