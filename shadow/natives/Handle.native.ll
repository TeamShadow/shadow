; Handle.native.ll
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

; Handle
%shadow.natives..Handle = type opaque

;---------------------
; Method Declarations
;---------------------
; void* calloc(int num, int size);
declare noalias %void* @calloc(%int, %int) nounwind
; void free(void* ptr);
declare void @free(%void*) nounwind
declare void @shadow.natives..Handle_MfreeNative(%shadow.natives..Handle*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; createHandle(int size) => (long handle);
define %long @shadow.natives..Handle_McreateHandle_int(%shadow.natives..Handle*, %int) {
entry:
	%handle.addr = call noalias %void* @calloc(%int 1, %int %1) nounwind
	%handle =  ptrtoint %void* %handle.addr to %long
	ret %long %handle
}

; freeHandle(Pointer ptr) => ();
define void @shadow.natives..Handle_MfreeHandle_shadow.natives..Pointer(%shadow.natives..Handle*, %shadow.natives..Pointer*) {
entry:
	%handle.addr = bitcast %shadow.natives..Pointer* %1 to i8*
	call void @free(i8* %handle.addr)
	call void @shadow.natives..Handle_MfreeNative(%shadow.natives..Handle* %0)
	ret void
}

; getPtrFromLong(long handle) => (Pointer ptr);
define %shadow.natives..Pointer* @shadow.natives..Handle_MgetPtrFromLong_long(%shadow.natives..Handle*, %long) {
entry:
	%handle.obj = inttoptr %long %1 to %shadow.natives..Pointer*
	ret %shadow.natives..Pointer* %handle.obj
}