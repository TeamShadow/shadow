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

; Class
%shadow.standard..Class = type opaque

; Pointer
%shadow.natives..Pointer = type opaque

; Array
%shadow.standard..Array._methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array._methods* , %long }


; String
%shadow.standard..String._methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String._methods* , %shadow.standard..Array*, %boolean }

;---------------------------
; Custom Method Definitions
;---------------------------
; String.c
define %shadow.standard..Array* @_shadowString_GetDataArray(%shadow.standard..String*) {
entry:
	%array.addr = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 3
	%array = load %shadow.standard..Array*, %shadow.standard..Array** %array.addr
	ret %shadow.standard..Array* %array
}