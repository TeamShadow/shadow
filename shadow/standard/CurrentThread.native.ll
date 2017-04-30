; CurrentThread.native.ll
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

; Thread
%shadow.standard..Thread = type opaque

; CurrentThread
%shadow.standard..CurrentThread = type opaque

;---------
; Globals
;---------
@shadow.standard..Thread_TLS_currentThread = external global %shadow.standard..Thread*

;---------------------------
; Shadow Method Definitions
;---------------------------
; get currentThread() => (Thread);
define %shadow.standard..Thread* @shadow.standard..CurrentThread_McurrentThread(%shadow.standard..CurrentThread*) {
entry:
	%currentThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	ret %shadow.standard..Thread* %currentThread
}