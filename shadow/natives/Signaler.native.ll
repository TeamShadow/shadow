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
%shadow.standard..Object = type opaque

; Pointer
%shadow.natives..Pointer = type opaque

; Signaler
%shadow.natives..Signaler = type opaque

; pthread_mutex_t
%struct.pthread_mutex_t = type opaque

; pthread_cond_t
%struct.pthread_cond_t = type i8*

; pthread_condattr_t
%struct.pthread_condattr_t = type i32

;---------------------
; Method Declarations
;---------------------
declare %int @pthread_cond_init(%struct.pthread_cond_t*, %struct.pthread_condattr_t*)
declare %int @pthread_cond_destroy(%struct.pthread_cond_t*)

declare %int @pthread_cond_wait(%struct.pthread_cond_t*, %struct.pthread_mutex_t*)
declare %int @pthread_cond_signal(%struct.pthread_cond_t*)
declare %int @pthread_cond_broadcast(%struct.pthread_cond_t*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; initSignaler(Pointer cond) => (int);
define %int @shadow.natives..Signaler_MinitSignaler_shadow.natives..Pointer(%shadow.natives..Signaler*, %shadow.natives..Pointer*) {
entry:
	%cond = bitcast %shadow.natives..Pointer* %1 to %struct.pthread_cond_t*
	%call = call %int @pthread_cond_init(%struct.pthread_cond_t* %cond, %struct.pthread_condattr_t* null)
	
	ret %int %call
}

; destroySignaler(Pointer cond) => (int);
define %int @shadow.natives..Signaler_MdestroySignaler_shadow.natives..Pointer(%shadow.natives..Signaler*, %shadow.natives..Pointer*) {
entry:
	%cond = bitcast %shadow.natives..Pointer* %1 to %struct.pthread_cond_t*
	%call = call %int @pthread_cond_destroy(%struct.pthread_cond_t* %cond)
	
	ret %int %call
}

; wait(Pointer cond, Pointer mutex) => (int);
define %int @shadow.natives..Signaler_Mwait_shadow.natives..Pointer_shadow.natives..Pointer(%shadow.natives..Signaler*, %shadow.natives..Pointer*, %shadow.natives..Pointer*) {
entry:
	%cond = bitcast %shadow.natives..Pointer* %1 to %struct.pthread_cond_t*
	%mutex = bitcast %shadow.natives..Pointer* %2 to %struct.pthread_mutex_t*
	
	%call = call %int @pthread_cond_wait(%struct.pthread_cond_t* %cond, %struct.pthread_mutex_t* %mutex)
	ret %int %call
}

; signal(Pointer cond) => (int);
define %int @shadow.natives..Signaler_Msignal_shadow.natives..Pointer(%shadow.natives..Signaler*, %shadow.natives..Pointer*) {
entry:
	%cond = bitcast %shadow.natives..Pointer* %1 to %struct.pthread_cond_t*
	%call = call %int @pthread_cond_signal(%struct.pthread_cond_t* %cond)
	
	ret %int %call
}

; broadcast(Pointer cond) => (int);
define %int @shadow.natives..Signaler_Mbroadcast_shadow.natives..Pointer(%shadow.natives..Signaler*, %shadow.natives..Pointer*) {
entry:
	%cond = bitcast %shadow.natives..Pointer* %1 to %struct.pthread_cond_t*
	%call = call %int @pthread_cond_broadcast(%struct.pthread_cond_t* %cond)
	
	ret %int %call
}

; get handleSize() => (int);
define %int @shadow.natives..Signaler_MhandleSize(%shadow.natives..Signaler*) {
entry:
	%sizeOf = ptrtoint %struct.pthread_cond_t* getelementptr (%struct.pthread_cond_t, %struct.pthread_cond_t* null, i32 1) to i32
	ret %int %sizeOf
}