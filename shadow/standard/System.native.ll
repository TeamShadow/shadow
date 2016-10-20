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
%shadow.standard..CanRun_methods = type { void (%shadow.standard..Object*, %shadow.standard..Thread*)* }

; Thread
%shadow.standard..Thread_methods = type opaque
%shadow.standard..Thread = type { %shadow.standard..Class*, %shadow.standard..Thread_methods* , { %shadow.standard..CanRun_methods*, %shadow.standard..Object* }, %shadow.standard..Thread*, %uint }

; struct timespec { time_t tv_sec; long tv_nsec; };
%struct.timespec = type { %int, %int }

; int nanosleep(const struct timespec *req, struct timespec *rem);
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)

; used to allocate a new thread and call the empty constructor
declare %shadow.standard..Thread* @shadow.standard..Thread_McreateNative(%shadow.standard..Thread*)

@shadow.standard..Thread_currentThread = external global %shadow.standard..Thread*

; sleep_ms(int ms) => (int);
define %int @shadow.standard..System_Msleep__ms_int(%shadow.standard..System* %this, %int %ms) {
entry:
	%ms.addr = alloca i32
	%t = alloca %struct.timespec
	store i32 %ms, i32* %ms.addr
	%0 = load i32, i32* %ms.addr
	%cmp = icmp slt i32 %0, 1000
	br %boolean %cmp, label %if.then, label %if.else

if.then:                                          ; preds = %entry
	%tv_sec = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 0
	store i32 0, i32* %tv_sec
	%1 = load i32, i32* %ms.addr
	%mul = mul nsw i32 %1, 1000000
	%tv_nsec = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 1
	store i32 %mul, i32* %tv_nsec
	br label %if.end

if.else:                                          ; preds = %entry
	%2 = load i32, i32* %ms.addr
	%div = sdiv i32 %2, 1000
	%tv_sec1 = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 0
	store i32 %div, i32* %tv_sec1
	%3 = load i32, i32* %ms.addr
	%tv_sec2 = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 0
	%4 = load i32, i32* %tv_sec2
	%mul3 = mul nsw i32 %4, 1000
	%sub = sub nsw i32 %3, %mul3
	%mul4 = mul nsw i32 %sub, 1000000
	%tv_nsec5 = getelementptr inbounds %struct.timespec, %struct.timespec* %t, i32 0, i32 1
	store i32 %mul4, i32* %tv_nsec5
	br label %if.end

if.end:                                           ; preds = %if.else, %if.then
	%call = call i32 @nanosleep(%struct.timespec* %t, %struct.timespec* null)
	ret i32 %call
}

; get currentThread() => (Thread);
define %shadow.standard..Thread* @shadow.standard..System_McurrentThread(%shadow.standard..System*) {
entry:
	%currentThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_currentThread
	%cmp = icmp eq %shadow.standard..Thread* %currentThread, null
	br %boolean %cmp, label %if.then, label %if.else

if.then:
	%newThread = call %shadow.standard..Thread* @shadow.standard..Thread_McreateNative(%shadow.standard..Thread* null)
	store %shadow.standard..Thread* %newThread, %shadow.standard..Thread** @shadow.standard..Thread_currentThread
	ret %shadow.standard..Thread* %newThread

if.else:
	ret %shadow.standard..Thread* %currentThread
}