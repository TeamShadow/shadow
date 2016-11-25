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
; setOwnerNative(Thread owner) => ()
declare void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner*, %shadow.standard..Thread*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; setOwner(immutable Owner owner, nullable Thread t) => ();
define void @shadow.natives..Mutex_MsetOwner_shadow.natives..Mutex.Owner_shadow.standard..Thread(%shadow.natives..Mutex*, %shadow.natives..Mutex.Owner*, %shadow.standard..Thread*) {
entry:
	call void @shadow.natives..Mutex.Owner_MsetOwnerNative_shadow.standard..Thread(%shadow.natives..Mutex.Owner* %1, %shadow.standard..Thread* %2)
	ret void
}