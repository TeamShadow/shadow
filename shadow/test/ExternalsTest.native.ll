; ExternalsTest.native.ll
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
%shadow.natives..ShadowPointer = type opaque

; ExternalsTest
%shadow.test..ExternalsTest = type opaque

;---------------------
; Method Declarations
;---------------------
declare %shadow.natives..ShadowPointer* @__Test_Allocate()

;---------------------------
; Shadow Method Definitions
;---------------------------
define %shadow.natives..ShadowPointer* @shadow.test..ExternalsTest_MTest__Allocate(%shadow.test..ExternalsTest*) {
entry:
	%call = call %shadow.natives..ShadowPointer* @__Test_Allocate()
	ret %shadow.natives..ShadowPointer* %call
}