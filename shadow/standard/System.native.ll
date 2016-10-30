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

;---------------------
; Method Declarations
;---------------------
; int nanosleep(const struct timespec *req, struct timespec *rem);
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; sleepNanos(int sec, int nsec) => ();
define void @shadow.standard..System_MsleepNanos_int_int(%shadow.standard..System* %this, %int %sec, %int %nsec) {
entry:
	%sec.addr = alloca i32, align 4
	%nsec.addr = alloca i32, align 4
	%t = alloca %struct.timespec, align 4
	store i32 %sec, i32* %sec.addr, align 4
	store i32 %nsec, i32* %nsec.addr, align 4
	%0 = load i32, i32* %sec.addr, align 4
	%tv_sec = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 0
	store i32 %0, i32* %tv_sec, align 4
	%1 = load i32, i32* %nsec.addr, align 4
	%tv_nsec = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 1
	store i32 %1, i32* %tv_nsec, align 4
	%call = call i32 @nanosleep(%struct.timespec* %t, %struct.timespec* null)

	ret void
}

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
