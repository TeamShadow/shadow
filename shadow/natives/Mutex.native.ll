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

; Thread
%shadow.standard..Thread = type opaque

; Pointer
%shadow.natives..ShadowPointer = type opaque

; Mutex
%shadow.natives..Mutex = type opaque

; Mutex:Owner
%shadow.natives..Mutex.Owner = type opaque

;---------------------
; Method Declarations
;---------------------
; @ShadowMutex.c
declare %shadow.natives..ShadowPointer* @__ShadowMutex_Initialize()
declare %boolean @__ShadowMutex_Destroy(%shadow.natives..ShadowPointer*)
declare %boolean @__ShadowMutex_Lock(%shadow.natives..ShadowPointer*)
declare %boolean @__ShadowMutex_Unlock(%shadow.natives..ShadowPointer*)

; setOwnerNative(Thread owner) => ()
declare void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner*, %shadow.standard..Thread*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; initMutex() => (ShadowPointer);
define %shadow.natives..ShadowPointer* @shadow.natives..Mutex_MinitMutex(%shadow.natives..Mutex*) {
entry:
	%call = call %shadow.natives..ShadowPointer* @__ShadowMutex_Initialize()
	ret %shadow.natives..ShadowPointer* %call
}

; destroyMutex(ShadowPointer ptr) => (boolean);
define %boolean @shadow.natives..Mutex_MdestroyMutex_shadow.natives..ShadowPointer(%shadow.natives..Mutex*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowMutex_Destroy(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; lockMutex(ShadowPointer ptr) => (boolean);
define %boolean @shadow.natives..Mutex_MlockMutex_shadow.natives..ShadowPointer(%shadow.natives..Mutex*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowMutex_Lock(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; unlockMutex(ShadowPointer ptr) => (boolean);
define %boolean @shadow.natives..Mutex_MunlockMutex_shadow.natives..ShadowPointer(%shadow.natives..Mutex*, %shadow.natives..ShadowPointer*) {
entry:
	%call = call %boolean @__ShadowMutex_Unlock(%shadow.natives..ShadowPointer* %1)
	ret %boolean %call
}

; setOwner(immutable Owner owner, nullable Thread t) => ();
define void @shadow.natives..Mutex_MsetOwner_shadow.natives..Mutex.Owner_shadow.standard..Thread(%shadow.natives..Mutex*, %shadow.natives..Mutex.Owner*, %shadow.standard..Thread*) {
entry:
	call void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner* %1, %shadow.standard..Thread* %2)
	ret void
}