; SignalerTest.native.ll
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

; Signaler
%shadow.natives..Signaler = type opaque

; SignalerTest
%shadow.test..SignalerTest = type opaque

;---------
; Globals
;---------
@STATIC_signaler = private global %shadow.natives..Signaler* null

;---------------------
; Method Declarations
;---------------------
declare %shadow.natives..Signaler* @shadow.test..SignalerTest_McreateSignalerNative(%shadow.test..SignalerTest*)

;---------------------------
; Shadow Method Definitions
;---------------------------

define void @shadow.test..SignalerTest_MinitSignaler(%shadow.test..SignalerTest*) {
entry:
	%signaler = call %shadow.natives..Signaler* @shadow.test..SignalerTest_McreateSignalerNative(%shadow.test..SignalerTest* %0)
	store %shadow.natives..Signaler* %signaler, %shadow.natives..Signaler** @STATIC_signaler
	
	ret void
}

define %shadow.natives..Signaler* @shadow.test..SignalerTest_MstaticSignaler(%shadow.test..SignalerTest*) {
entry:
	%signaler = load %shadow.natives..Signaler*, %shadow.natives..Signaler** @STATIC_signaler
	ret %shadow.natives..Signaler* %signaler
}