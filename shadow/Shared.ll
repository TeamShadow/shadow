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

; ShadowPointer
%shadow.natives..Pointer = type opaque

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%ulong, %byte}*, %shadow.standard..Class*, %ulong }, %boolean }

;---------------------------
; Custom Method Definitions
;---------------------------
; ShadowString.c
define void @_shadow_UnpackString(%shadow.standard..String*, {{ %ulong, %byte }*, %shadow.standard..Class*, %ulong }**, %boolean*) {
entry:
	%array.addr = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 3
	store {{ %ulong, %byte }*, %shadow.standard..Class*, %ulong }* %array.addr, {{ %ulong, %byte }*, %shadow.standard..Class*, %ulong }** %1
	
	%ascii.addr = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 4
	%ascii = load %boolean, %boolean* %ascii.addr
	store %boolean %ascii, %boolean* %2
	
	ret void
}

; ShadowArray.c
%ShadowArray = type {{ %ulong, %void }*, %shadow.standard..Class*, %ulong }
define void @_shadow_UnpackArray(%ShadowArray*, %ulong*, %void**) {
entry:
	%arrayData = load %ShadowArray, %ShadowArray* %0
	
	%size = extractvalue %ShadowArray %arrayData, 2
	store %ulong %size, %ulong* %1

	%array.ptr = extractvalue %ShadowArray %arrayData, 0
	%array = getelementptr inbounds { %ulong, %void }, { %ulong, %void }* %array.ptr, i32 0, i32 1
	store %void* %array, %void** %2
	
	ret void
}

; ShadowPointer.h
declare %shadow.natives..Pointer* @shadow.natives..Pointer_McreateNative_long_boolean(%shadow.natives..Pointer*, %long, %boolean)
define %shadow.natives..Pointer* @_shadow_CreatePointer(%void*, %boolean) {
entry:
	%address = ptrtoint %void* %0 to %long
	%call = call %shadow.natives..Pointer* @shadow.natives..Pointer_McreateNative_long_boolean(%shadow.natives..Pointer* null, %long %address, %boolean %1)
	ret %shadow.natives..Pointer* %call
}

; ShadowPointer.h
declare %long @shadow.natives..Pointer_MgetAddressNative(%shadow.natives..Pointer*)
define %void* @_shadow_ExtractPointer(%shadow.natives..Pointer*) {
entry:
	%address = call %long @shadow.natives..Pointer_MgetAddressNative(%shadow.natives..Pointer* %0)
	%pointer = inttoptr %long %address to %void*
	ret %void* %pointer
}