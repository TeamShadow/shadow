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
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type opaque

; Handle
%shadow.standard..Handle_methods = type opaque
%shadow.standard..Handle = type opaque

;---------------------
; Method Declarations
;---------------------
; void* calloc(int num, int size);
declare noalias %void* @calloc(%int, %int) nounwind
; void free(void* ptr);
declare void @free(%void*) nounwind

;---------------------------
; Shadow Method Definitions
;---------------------------
; createHandle(int size) => (long handle);
define %long @shadow.standard..Handle_McreateHandle_int(%shadow.standard..Handle*, %int) {
entry:
	%handle.addr = call noalias %void* @calloc(%int 1, %int %1) nounwind
	%handle =  ptrtoint %void* %handle.addr to %long
	ret %long %handle
}

; freeHandle(immutable Object handle) => ();
define void @shadow.standard..Handle_MfreeHandle_shadow.standard..Object(%shadow.standard..Handle*, %shadow.standard..Object*) {
entry:
	%handle.addr = bitcast %shadow.standard..Object* %1 to i8*
	call void @free(i8* %handle.addr)
	ret void
}

; getPtrFromLong(long handle) => (immutable Object handle);
define %shadow.standard..Object* @shadow.standard..Handle_MgetPtrFromLong_long(%shadow.standard..Handle*, %long) {
entry:
	%handle.obj = inttoptr %long %1 to %shadow.standard..Object*
	ret %shadow.standard..Object* %handle.obj
}