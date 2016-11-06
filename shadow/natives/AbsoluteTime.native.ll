; AbsoluteTime.native.ll
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

; AbsoluteTime
%shadow.natives..AbsoluteTime = type opaque

; timespec
%struct.timespec = type { %int, %int }

;---------------------------
; Shadow Method Definitions
;---------------------------

; setAbsoluteTime(Pointer time, int sec, int nsec) => ();
define void @shadow.natives..AbsoluteTime_MsetAbsoluteTime_shadow.natives..Pointer_int_int(%shadow.natives..AbsoluteTime*, %shadow.natives..Pointer* %waitTime, %int %sec, %int %nsec) {
entry:
	%waitTime.addr = bitcast %shadow.natives..Pointer* %waitTime to %struct.timespec*
	
	%waitTime.tv_sec.addr = getelementptr inbounds %struct.timespec, %struct.timespec* %waitTime.addr, i32 0, i32 0
	store %int %sec, %int* %waitTime.tv_sec.addr
	
	%waitTime.tv_nsec.addr = getelementptr inbounds %struct.timespec, %struct.timespec* %waitTime.addr, i32 0, i32 1
	store %int %nsec, %int* %waitTime.tv_nsec.addr
	
	ret void
}

; get handleSize() => (int);
define %int @shadow.natives..AbsoluteTime_MhandleSize(%shadow.natives..AbsoluteTime*) {
entry:
	%sizeOf = ptrtoint %struct.timespec* getelementptr (%struct.timespec, %struct.timespec* null, i32 1) to i32
	ret %int %sizeOf
}