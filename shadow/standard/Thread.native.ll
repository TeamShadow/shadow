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

; Thread
%shadow.standard..Thread_methods = type opaque
%shadow.standard..Thread = type { %shadow.standard..Class*, %shadow.standard..Thread_methods*  }

; struct timespec { time_t tv_sec; long tv_nsec; };
%struct.timespec = type { %int, %int }

; int nanosleep(const struct timespec *req, struct timespec *rem);
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)

; used to allocate a new thread and call the empty constructor
declare %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker*)

@shadow.standard..ThreadWorker_currentThread = external global %shadow.standard..ThreadWorker*

define void @shadow.standard..Thread_Msleep_int_int(%shadow.standard..Thread* %this, %int %sec, %int %nsec) {
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
define %shadow.standard..ThreadWorker* @shadow.standard..Thread_Mcurrent(%shadow.standard..Thread*) {
entry:
	%currentThread = load %shadow.standard..ThreadWorker*, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread
	%cmp = icmp eq %shadow.standard..ThreadWorker* %currentThread, null
	br %boolean %cmp, label %if.then, label %if.else

if.then:
	%newThread = call %shadow.standard..ThreadWorker* @shadow.standard..ThreadWorker_McreateNative(%shadow.standard..ThreadWorker* null)
	store %shadow.standard..ThreadWorker* %newThread, %shadow.standard..ThreadWorker** @shadow.standard..ThreadWorker_currentThread
	ret %shadow.standard..ThreadWorker* %newThread

if.else:
	ret %shadow.standard..ThreadWorker* %currentThread
}