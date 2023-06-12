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

; Object
%shadow.standard..Object = type opaque

; Thread
%shadow.standard..Thread = type opaque

; CurrentThread
%shadow.standard..CurrentThread = type opaque

declare void @__incrementRef(%shadow.standard..Object*) nounwind

;---------
; Globals
;---------
@shadow.standard..Thread_TLS_currentThread = external thread_local global %shadow.standard..Thread*

;---------------------------
; Shadow Method Definitions
;---------------------------
; get currentThread() => (Thread);
define %shadow.standard..Thread* @shadow.standard..CurrentThread..currentThread(%shadow.standard..CurrentThread*) {
entry:
	%currentThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	; The following increment is necessary because the current thread will otherwise be decremented wherever it's used and deallocated
	%threadAsObj = bitcast %shadow.standard..Thread* %currentThread to %shadow.standard..Object*
    call void @__incrementRef(%shadow.standard..Object* %threadAsObj) nounwind
	ret %shadow.standard..Thread* %currentThread
}
