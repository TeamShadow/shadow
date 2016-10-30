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

; Thread
%shadow.standard..Thread_methods = type opaque
%shadow.standard..Thread = type opaque

; struct timespec { time_t tv_sec; long tv_nsec; };
%struct.timespec = type { %int, %int }

; int nanosleep(const struct timespec *req, struct timespec *rem);
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)

@shadow.standard..ThreadWorker_currentThread = external global %shadow.standard..ThreadWorker*
@shadow.standard..ThreadWorker_mainThread = external global %shadow.standard..ThreadWorker*

; sleepNanos(int sec, int nsec) => ();
define void @shadow.standard..Thread_MsleepNanos_int_int(%shadow.standard..Thread* %this, %int %sec, %int %nsec) {
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

; get current() => (ThreadWorker);
define %shadow.standard..ThreadWorker* @shadow.standard..Thread_Mcurrent(%shadow.standard..Thread*) {
entry:
	%currentThread = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread
	ret %shadow.standard..ThreadWorker* %currentThread
}

; get main() => (ThreadWorker);
define %shadow.standard..ThreadWorker* @shadow.standard..Thread_Mmain(%shadow.standard..Thread*) {
entry:
	%mainThread = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_mainThread
	ret %shadow.standard..ThreadWorker* %mainThread
}
