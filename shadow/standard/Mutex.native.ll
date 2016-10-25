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

; 0: class (Class)
; 1: _methods
; 2: mutex (shadow:standard@Object)
%shadow.standard..Mutex_methods = type opaque
%shadow.standard..Mutex = type { %shadow.standard..Class*, %shadow.standard..Mutex_methods* , %shadow.standard..Object* }

%struct.pthread_mutex_t = type i8*
declare i32 @pthread_mutex_init(%struct.pthread_mutex_t*, i32*)
declare i32 @pthread_mutex_lock(%struct.pthread_mutex_t*)
declare i32 @pthread_mutex_unlock(%struct.pthread_mutex_t*)
declare i32 @pthread_mutex_destroy(%struct.pthread_mutex_t*)
declare noalias i8* @calloc(i32, i32) nounwind
declare void @free(i8*) nounwind

define %shadow.standard..Object* @shadow.standard..Mutex_Minit(%shadow.standard..Mutex*) {
entry:
	; allocate space for the new mutex
	%sizeOfMutex = ptrtoint %struct.pthread_mutex_t* getelementptr (%struct.pthread_mutex_t, %struct.pthread_mutex_t* null, i32 1) to i32
	%mutex.addr.calloc = call noalias i8* @calloc(i32 1, i32 %sizeOfMutex) nounwind

	; initialize the mutex using pthread_mutex_init()
	%mutex.addr = bitcast i8* %mutex.addr.calloc to %struct.pthread_mutex_t*
	%call = call i32 @pthread_mutex_init(%struct.pthread_mutex_t* %mutex.addr, i32* null)
	
	; we cast the allocated memory to an Object* so we can return it to Shadow
	%mutex.addr.obj = bitcast i8* %mutex.addr.calloc to %shadow.standard..Object*
	ret %shadow.standard..Object* %mutex.addr.obj
}

define %int @shadow.standard..Mutex_MdestroyNative_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%mutex.addr = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call i32 @pthread_mutex_destroy(%struct.pthread_mutex_t* %mutex.addr)
	
	; need an i8* to free
	%mutex.addr.f = bitcast %shadow.standard..Object* %1 to i8*
	call void @free(i8* %mutex.addr.f)

	ret %int %call
}

define %int @shadow.standard..Mutex_Munlock_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%mutex = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call i32 @pthread_mutex_unlock(%struct.pthread_mutex_t* %mutex)
	ret %int %call
}

define %int @shadow.standard..Mutex_Mlock_shadow.standard..Object(%shadow.standard..Mutex*, %shadow.standard..Object*) {
entry:
	%mutex = bitcast %shadow.standard..Object* %1 to %struct.pthread_mutex_t*
	%call = call i32 @pthread_mutex_lock(%struct.pthread_mutex_t* %mutex)
	ret %int %call
}
