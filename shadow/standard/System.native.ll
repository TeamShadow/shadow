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

; TimeSpan
%shadow.standard..TimeSpan = type opaque

;---------
; Globals
;---------
@shadow.standard..Thread_TLS_currentThread = external global %shadow.standard..Thread*
@shadow.standard..Thread_STATIC_mainThread = external global %shadow.standard..Thread*

;---------------------
; Method Declarations
;---------------------
declare void @shadow.standard..Thread_MsleepNative_shadow.standard..TimeSpan(%shadow.standard..Thread*, %shadow.standard..TimeSpan*)
declare void @shadow.standard..Thread_MsleepNative_int(%shadow.standard..Thread*, %int)

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

; sleep(Thread thread, int milliseconds) => ();
define void @shadow.standard..System_Msleep_shadow.standard..Thread_int(%shadow.standard..System*, %shadow.standard..Thread* %thread, %int %millis) {
entry:
	call void @shadow.standard..Thread_MsleepNative_int(%shadow.standard..Thread* %thread, %int %millis)
	ret void
}

; sleep(Thread thread, TimeSpan time) => ();
define void @shadow.standard..System_Msleep_shadow.standard..Thread_shadow.standard..TimeSpan(%shadow.standard..System*, %shadow.standard..Thread* %thread, %shadow.standard..TimeSpan* %time) {
entry:
	call void @shadow.standard..Thread_MsleepNative_shadow.standard..TimeSpan(%shadow.standard..Thread* %thread, %shadow.standard..TimeSpan* %time)
	ret void
}
