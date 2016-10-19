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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }

; Class
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }

; String
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }

; AddressMap
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

; Exception
%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*)*, %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }

; System
%shadow.standard..System_methods = type { %shadow.standard..System* (%shadow.standard..System*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %shadow.standard..Thread* (%shadow.standard..System*)*, %long (%shadow.standard..System*)* }
%shadow.standard..System = type { %shadow.standard..Class*, %shadow.standard..System_methods*  }

; CanRun
%shadow.standard..CanRun_methods = type { void (%shadow.standard..Object*, %shadow.standard..Thread*)* }

; Thread
%shadow.standard..Thread_methods = type { %shadow.standard..Thread* (%shadow.standard..Thread*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %uint (%shadow.standard..Thread*)*, void (%shadow.standard..Thread*)* }
%shadow.standard..Thread = type { %shadow.standard..Class*, %shadow.standard..Thread_methods* , { %shadow.standard..CanRun_methods*, %shadow.standard..Object* }, %uint }

;===================================================================================================
; External Definitions
; --------------------

; typedef uintptr_t pthread_t;
%struct.pthread_t = type %uint

; struct pthread_attr_t { unsigned p_state; void* stack; size_t s_size; struct sched_param param; };
%struct.pthread_attr_t = type { %int, %void*, %int, %struct.sched_param }

; struct sched_param { int sched_priority; };
%struct.sched_param = type { %int }

; struct timespec { time_t tv_sec; long tv_nsec; };
%struct.timespec = type { %int, %int }
;===================================================================================================

;===================================================================================================
; External Methods
; ----------------

; int pthread_create(pthread_t*, pthread_attr_t*, void* (*start_routine)(void*), void*);
declare %int @pthread_create(%struct.pthread_t*, %struct.pthread_attr_t*, %void* (%void*)*, %void*)

; int pthread_join(pthread_t, void**);
declare %int @pthread_join(%struct.pthread_t, %void**)

; pthread_t pthread_self();
declare %struct.pthread_t @pthread_self()

; int nanosleep(const struct timespec *req, struct timespec *rem);
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)
;===================================================================================================

; the runner which is executed from the newly spawned thread
declare void @shadow.standard..Thread_Mrunner(%shadow.standard..Thread*)
; used to allocate a new thread and call the empty constructor
declare %shadow.standard..Thread* @shadow.standard..Thread_McreateNative(%shadow.standard..Thread*)

; used to store the current instance of the thread; System->currentThread. Each System singleton
; refers to its own thread.
@shadow.standard..Thread_currentThread = thread_local global %shadow.standard..Thread* null

; revised
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

; revised
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

; revised
define %struct.pthread_t @shadow.standard..Thread_MgetCurrentThreadId(%shadow.standard..Thread*) {
entry:
	%call = call %struct.pthread_t @pthread_self()

	ret %struct.pthread_t %call
}

; revised
define %void* @thread_func(%void* %currentThread) {
entry:
	%currentThread.addr = bitcast %void* %currentThread to %shadow.standard..Thread*

	; we need to set the reference of the current thread in this function as it is executed from the newly created thread
	; and will cause the TLS to correctly store the reference of this thread.
	store %shadow.standard..Thread* %currentThread.addr, %shadow.standard..Thread** @shadow.standard..Thread_currentThread

	; we let Shadow take care of running the actual desired operation
	call void @shadow.standard..Thread_Mrunner(%shadow.standard..Thread* %currentThread.addr)

	ret %void* null
}

; revised
define %int @shadow.standard..Thread_McreateThread(%shadow.standard..Thread*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..Thread*
	store %shadow.standard..Thread* %0, %shadow.standard..Thread** %this.addr
	%this = load %shadow.standard..Thread*, %shadow.standard..Thread** %this.addr

	; load handleId
	%handleId.addr = getelementptr inbounds %shadow.standard..Thread, %shadow.standard..Thread* %this, i32 0, i32 3
	
	; cast Thread* to void*
	%this.void = bitcast %shadow.standard..Thread* %this to %void*

	; create the thread using pthread_create()
	%call = call %int @pthread_create(%struct.pthread_t* %handleId.addr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_func, %void* %this.void)

	ret %int %call
}

; revised
define %int @shadow.standard..Thread_MjoinThread(%shadow.standard..Thread*) {
entry:
	; get the reference of the current Thread
	%this.addr = alloca %shadow.standard..Thread*
	store %shadow.standard..Thread* %0, %shadow.standard..Thread** %this.addr
	%this = load %shadow.standard..Thread*, %shadow.standard..Thread** %this.addr

	; load handleId
	%handleId.addr = getelementptr inbounds %shadow.standard..Thread, %shadow.standard..Thread* %this, i32 0, i32 3
	%handleId = load %struct.pthread_t, %struct.pthread_t* %handleId.addr

	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handleId, %void** null)

	ret %int %call
}