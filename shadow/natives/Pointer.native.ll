; Pointer.native.ll
; 
; Author:
; 	Claude Abounegm

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

; Class
%shadow.standard..Class = type opaque

; Pointer
%shadow.natives..Pointer._methods = type opaque
%shadow.natives..Pointer = type { %ulong, %shadow.standard..Class*, %shadow.natives..Pointer._methods* , %long, %boolean }

;---------------------------
; Shadow Method Definitions
;---------------------------
define void @shadow.natives..Pointer..invalidateAddress(%shadow.natives..Pointer*)
{
entry:
	%ptr = getelementptr inbounds %shadow.natives..Pointer, %shadow.natives..Pointer* %0, i32 0, i32 3
    store %long 0, %long* %ptr
	
	ret void
}