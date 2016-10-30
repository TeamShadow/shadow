; Mutex.native.ll
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
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type opaque

; Mutex
%shadow.standard..Mutex_methods = type opaque
%shadow.standard..Mutex = type opaque

; 
%struct.pthread_mutex_t = type i8*

;---------------------
; Method Declarations
;---------------------
; int pthread_mutex_init(pthread_mutex_t* mutex, pthread_mutexattr_t* attr);
declare %int @pthread_mutex_init(%struct.pthread_mutex_t*, %int*)
; int pthread_mutex_destroy(pthread_mutex_t *mutex);
declare %int @pthread_mutex_destroy(%struct.pthread_mutex_t*)
; int pthread_mutex_lock(pthread_mutex_t* mutex);
declare %int @pthread_mutex_lock(%struct.pthread_mutex_t*)
; int pthread_mutex_lock(pthread_mutex_t* mutex);
declare %int @pthread_mutex_unlock(%struct.pthread_mutex_t*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; initMutex(immutable Object ptr) => (int);
define %int @shadow.standard..Mutex_MinitMutex_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%handle = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_init(%struct.pthread_mutex_t* %handle, %int* null)
	
	ret %int %call
}

; destroyMutex(immutable Object ptr) => (int);
define %int @shadow.standard..Mutex_MdestroyMutex_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%handle = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_destroy(%struct.pthread_mutex_t* %handle)

	ret %int %call
}

; unlock(immutable Object ptr) => (int);
define %int @shadow.standard..Mutex_Munlock_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%handle = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_unlock(%struct.pthread_mutex_t* %handle)
	ret %int %call
}

; lock(immutable Object ptr) => (int);
define %int @shadow.standard..Mutex_Mlock_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%handle = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call %int @pthread_mutex_lock(%struct.pthread_mutex_t* %handle)
	ret %int %call
}

; get handleSize() => (int);
define %int @shadow.standard..Mutex_MhandleSize(%shadow.standard..Mutex*) {
entry:
	%sizeOfMutex = ptrtoint %struct.pthread_mutex_t* getelementptr (%struct.pthread_mutex_t, %struct.pthread_mutex_t* null, i32 1) to i32
	ret %int %sizeOfMutex
}