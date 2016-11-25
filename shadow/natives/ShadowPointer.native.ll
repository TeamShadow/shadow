; Pointer.native.ll
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

; Pointer:LongPointer
%shadow.natives..ShadowPointer.LongPointer = type opaque

;---------------------
; Method Declarations
;---------------------
; void* calloc(int num, int size);
declare noalias %void* @calloc(%int, %int) nounwind
; void free(void* ptr);
declare void @free(%void*) nounwind
; LongPointer:invalidateNative() => ();
declare void @shadow.natives..ShadowPointer.LongPointer_MinvalidateNative(%shadow.natives..ShadowPointer.LongPointer*)

; ExtractRawPointer()
declare %void* @ExtractRawPointer(%shadow.natives..ShadowPointer*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; allocMemory(int size) => (long address);
define %long @shadow.natives..ShadowPointer_MallocMemory_int(%shadow.natives..ShadowPointer*, %int) {
entry:
	%pointer = call noalias %void* @calloc(%int 1, %int %1) nounwind
	%address =  ptrtoint %void* %pointer to %long
	ret %long %address
}

; freeMemory() => ();
define void @shadow.natives..ShadowPointer_MfreeMemory(%shadow.natives..ShadowPointer*) {
entry:
	%pointer = call %void* @ExtractRawPointer(%shadow.natives..ShadowPointer* %0)
	call void @free(%void* %pointer)
	ret void
}

; invalidateHandle(ShadowPointer:LongPointer) => ();
define void @shadow.natives..ShadowPointer_MinvalidateHandle_shadow.natives..ShadowPointer.LongPointer(%shadow.natives..ShadowPointer*, %shadow.natives..ShadowPointer.LongPointer*) {
entry:
	call void @shadow.natives..ShadowPointer.LongPointer_MinvalidateNative(%shadow.natives..ShadowPointer.LongPointer* %1)
	ret void
}