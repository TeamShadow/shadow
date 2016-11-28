; C_Helpers.ll
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
%shadow.natives..ShadowPointer = type opaque

; String
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }

;---------------------------
; Custom Method Definitions
;---------------------------
; ShadowString.c
define { %byte*, [1 x %int] }* @GetShadowArrayFromShadowString(%shadow.standard..String*) {
entry:
	%array.addr = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %0, i32 0, i32 2
	ret { %byte*, [1 x %int] }* %array.addr
}

; ShadowArray.c
%ShadowArray = type { %void*, [1 x %int] }
define void @ExtractDataFromShadowArray(%ShadowArray*, %int*, %void**) {
entry:
	%arrayData = load %ShadowArray, %ShadowArray* %0
	
	%size = extractvalue %ShadowArray %arrayData, 1, 0
	store %int %size, %int* %1
	
	%array = extractvalue %ShadowArray %arrayData, 0
	store %void* %array, %void** %2
	
	ret void
}

; ShadowPointer.h
declare %shadow.natives..ShadowPointer* @shadow.natives..ShadowPointer_McreateNative_long_boolean(%shadow.natives..ShadowPointer*, %long, %boolean)
define %shadow.natives..ShadowPointer* @CreateShadowPointer(%void*, %boolean) {
entry:
	%address = ptrtoint %void* %0 to %long
	%call = call %shadow.natives..ShadowPointer* @shadow.natives..ShadowPointer_McreateNative_long_boolean(%shadow.natives..ShadowPointer* null, %long %address, %boolean %1)
	ret %shadow.natives..ShadowPointer* %call
}

; ShadowPointer.h
declare %long @shadow.natives..ShadowPointer_MgetAddressNative(%shadow.natives..ShadowPointer*)
define %void* @ExtractRawPointer(%shadow.natives..ShadowPointer*) {
entry:
	%address = call %long @shadow.natives..ShadowPointer_MgetAddressNative(%shadow.natives..ShadowPointer* %0)
	%pointer = inttoptr %long %address to %void*
	ret %void* %pointer
}