; Shared.ll
; 
; A set of functions which are of help to native C code. This facilitates
; writing C code which communicates with Shadow and LLVM.
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

; Pointer
%shadow.natives..Pointer = type opaque

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%ulong, %byte}*, %shadow.standard..Class*,%ulong}, %boolean }

;---------------------------
; Custom Method Definitions
;---------------------------
; String.c
define {{%ulong, %byte}*, %shadow.standard..Class*, %ulong }* @_shadowString_GetDataArray(%shadow.standard..String*) {
entry:
	%array.addr = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 3	
	ret {{%ulong, %byte}*, %shadow.standard..Class*, %ulong }* %array.addr
}