; String.native.ll
; 
; Author:
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

; Class
%shadow.standard..Class = type opaque

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }

;---------------------------
; Custom Method Definitions
;---------------------------
define void @__getShadowArrayFromString(%shadow.standard..String*, { %byte*, [1 x %int] }**) {
entry:
	%array = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 2
	store { %byte*, [1 x %int] }* %array, { %byte*, [1 x %int] }** %1
	ret void
}