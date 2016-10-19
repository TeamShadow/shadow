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

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*)*, %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }

; System
%shadow.standard..System_methods = type { %shadow.standard..System* (%shadow.standard..System*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %shadow.standard..Thread* (%shadow.standard..System*)*, %long (%shadow.standard..System*)* }
%shadow.standard..System = type { %shadow.standard..Class*, %shadow.standard..System_methods*  }

; Thread specific stuff
%void = type i8
%shadow.standard..Runnable_methods = type { %shadow.standard..Runnable* (%shadow.standard..Runnable*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, void (%shadow.standard..Runnable*)* }
%shadow.standard..Runnable = type { %shadow.standard..Class*, %shadow.standard..Runnable_methods*  }

%shadow.standard..Thread_methods = type { %shadow.standard..Thread* (%shadow.standard..Thread*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, void (%shadow.standard..Thread*)* }
%shadow.standard..Thread = type { %shadow.standard..Class*, %shadow.standard..Thread_methods* , %shadow.standard..Runnable*, %uint }

; typedef uintptr_t pthread_t;
%struct.pthread_t = type %uint
; struct pthread_attr_t { unsigned p_state; void* stack; size_t s_size; struct sched_param param; };
%struct.pthread_attr_t = type { %int, %void*, %int, %struct.sched_param }
; struct sched_param { int sched_priority; };
%struct.sched_param = type { %int }

%struct.timespec = type { %int, %int }

; int pthread_create(pthread_t*, pthread_attr_t*, void* (*start_routine)(void*), void*);
declare %int @pthread_create(%struct.pthread_t*, %struct.pthread_attr_t*, %void* (%void*)*, %void*)
; int pthread_join(pthread_t, void**);
declare %int @pthread_join(%struct.pthread_t, %void**)
; 
declare %struct.pthread_t @pthread_self()
declare %int @nanosleep(%struct.timespec*, %struct.timespec*)

; the runner which executes the actual content
declare void @shadow.standard..Thread_Mrunner(%shadow.standard..Thread*)
declare %shadow.standard..Thread* @shadow.standard..Thread_McreateNative(%shadow.standard..Thread*)

; used to store the current instance of the thread; System->currentThread. Each singleton
; refers to its own thread.
@shadow.standard..Thread_currentThread = thread_local global %shadow.standard..Thread* null

define %int @shadow.standard..System_Msleep__ms_int(%shadow.standard..System* %this, %int %miliseconds) {
entry:
  %miliseconds.addr = alloca i32
  %req = alloca %struct.timespec
  store i32 %miliseconds, i32* %miliseconds.addr
  %0 = load i32, i32* %miliseconds.addr
  %cmp = icmp sgt i32 %0, 999
  br i1 %cmp, label %if.then, label %if.else

if.then:                                          ; preds = %entry
  %1 = load i32, i32* %miliseconds.addr
  %div = sdiv i32 %1, 1000
  %tv_sec = getelementptr inbounds %struct.timespec, %struct.timespec* %req, i32 0, i32 0
  store i32 %div, i32* %tv_sec
  %2 = load i32, i32* %miliseconds.addr
  %tv_sec1 = getelementptr inbounds %struct.timespec, %struct.timespec* %req, i32 0, i32 0
  %3 = load i32, i32* %tv_sec
  %mul = mul nsw i32 %3, 1000
  %sub = sub nsw i32 %2, %mul
  %mul2 = mul nsw i32 %sub, 1000000
  %tv_nsec = getelementptr inbounds %struct.timespec, %struct.timespec* %req, i32 0, i32 1
  store i32 %mul2, i32* %tv_nsec
  br label %if.end

if.else:                                          ; preds = %entry
  %tv_sec3 = getelementptr inbounds %struct.timespec, %struct.timespec* %req, i32 0, i32 0
  store i32 0, i32* %tv_sec3
  %4 = load i32, i32* %miliseconds.addr
  %mul4 = mul nsw i32 %4, 1000000
  %tv_nsec5 = getelementptr inbounds %struct.timespec, %struct.timespec* %req, i32 0, i32 1
  store i32 %mul4, i32* %tv_nsec5
  br label %if.end

if.end:                                           ; preds = %if.else, %if.then
  %call = call i32 @nanosleep(%struct.timespec* %req, %struct.timespec* null)
  ret i32 %call
}

define %shadow.standard..Thread* @shadow.standard..System_McurrentThread(%shadow.standard..System*) {
    br label %_label0
_label0:
    %2 = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_currentThread
    %3 = icmp eq %shadow.standard..Thread* %2, null
    br %boolean %3, label %_label1, label %_label2
	
_label1:
    %4 = call %shadow.standard..Thread* @shadow.standard..Thread_McreateNative(%shadow.standard..Thread* null)
    store %shadow.standard..Thread* %4, %shadow.standard..Thread** @shadow.standard..Thread_currentThread
    ret %shadow.standard..Thread* %4
	
_label2:
	ret %shadow.standard..Thread* %2
}

define %struct.pthread_t @shadow.standard..Thread_MgetCurrentThreadId(%shadow.standard..Thread*) {
	%call = call %struct.pthread_t @pthread_self()
	ret %struct.pthread_t %call
}

define %void* @thread_func(%void*) {
	%2 = bitcast %void* %0 to %shadow.standard..Thread*

	; set the TLS of the current thread with the reference to this newly created thread
	store %shadow.standard..Thread* %2, %shadow.standard..Thread** @shadow.standard..Thread_currentThread

	call void @shadow.standard..Thread_Mrunner(%shadow.standard..Thread* %2)
	ret %void* null
}

define %int @shadow.standard..Thread_McreateThread(%shadow.standard..Thread*) {
	%this = alloca %shadow.standard..Thread*
	
	; load %this
	store %shadow.standard..Thread* %0, %shadow.standard..Thread** %this
	%thisInstance = load %shadow.standard..Thread*, %shadow.standard..Thread** %this

	; load handleId
	%handleIdPtr = getelementptr inbounds %shadow.standard..Thread, %shadow.standard..Thread* %thisInstance, i32 0, i32 3
	%2 = bitcast %shadow.standard..Thread* %thisInstance to %void*

	; create the thread using pthread_create
	%call = call %int @pthread_create(%struct.pthread_t* %handleIdPtr, %struct.pthread_attr_t* null, %void*(%void*)* @thread_func, %void* %2)

	ret %int %call
}

define %int @shadow.standard..Thread_MjoinThread(%shadow.standard..Thread*) {
	%this = alloca %shadow.standard..Thread*

	; load %this
	store %shadow.standard..Thread* %0, %shadow.standard..Thread** %this
	%thisInstance = load %shadow.standard..Thread*, %shadow.standard..Thread** %this

	; load handleId
	%handleIdPtr = getelementptr inbounds %shadow.standard..Thread, %shadow.standard..Thread* %thisInstance, i32 0, i32 3
	%handleId = load %struct.pthread_t, %struct.pthread_t* %handleIdPtr

	; join thread
	%call = call %int @pthread_join(%struct.pthread_t %handleId, %void** null)
	
	ret %int %call
}