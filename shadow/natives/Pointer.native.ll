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
%shadow.natives..Pointer = type opaque

; Pointer.LongPointer
%shadow.natives..Pointer.LongPointer = type opaque

;---------------------
; Method Declarations
;---------------------
; void* calloc(int num, int size);
declare noalias %void* @calloc(%int, %int) nounwind
; void free(void* ptr);
declare void @free(%void*) nounwind
; getAddressNative() => (long);
declare %long @shadow.natives..Pointer_MgetAddressNative(%shadow.natives..Pointer*)
; LongPointer.invalidateNative() => ();
declare void @shadow.natives..Pointer.LongPointer_MinvalidateNative(%shadow.natives..Pointer.LongPointer*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; allocMemory(int size) => (long address);
define %long @shadow.natives..Pointer_MallocMemory_int(%shadow.natives..Pointer*, %int) {
entry:
	%pointer = call noalias %void* @calloc(%int 1, %int %1) nounwind
	%address =  ptrtoint %void* %pointer to %long
	ret %long %address
}

; freeMemory() => ();
define void @shadow.natives..Pointer_MfreeMemory(%shadow.natives..Pointer*) {
entry:
	%pointer = call %void* @__extractPointer(%shadow.natives..Pointer* %0)
	call void @free(%void* %pointer)
	
	ret void
}

define void @shadow.natives..Pointer_MinvalidateHandle_shadow.natives..Pointer.LongPointer(%shadow.natives..Pointer*, %shadow.natives..Pointer.LongPointer*) {
entry:
	call void @shadow.natives..Pointer.LongPointer_MinvalidateNative(%shadow.natives..Pointer.LongPointer* %1)
	ret void
}

;---------------------------
; Custom Method Definitions
;---------------------------
define %void* @__extractPointer(%shadow.natives..Pointer*) {
entry:
	%address = call %long @shadow.natives..Pointer_MgetAddressNative(%shadow.natives..Pointer* %0)
	%pointer = inttoptr %long %address to %void*
	ret %void* %pointer
}