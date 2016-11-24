; System.native.ll
; 
; Author:
;   Barry Wittman
;   Claude Abounegm

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

; System
%shadow.standard..System = type opaque

;---------------------
; Method Declarations
;---------------------
declare %long @__ShadowSystem_GetNanoTime()
declare %long @__ShadowSystem_GetEpochNanoTime()

;---------------------------
; Shadow Method Definitions
;---------------------------
; nanoTime() => (long);
define %long @shadow.standard..System_MnanoTime(%shadow.standard..System*) {
entry:
	%call = call %long @__ShadowSystem_GetNanoTime()
	ret %long %call
}

; epochNanoTime() => (long);
define %long @shadow.standard..System_MepochNanoTime(%shadow.standard..System*) {
entry:
	%call = call %long @__ShadowSystem_GetEpochNanoTime()
	ret %long %call
}