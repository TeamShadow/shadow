; Time.native.ll
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

; Time
%shadow.standard..Time = type opaque

; timeval
%struct.timeval = type { %int, %int }

;---------------------
; Method Declarations
;---------------------
declare %int @gettimeofday(%struct.timeval*, i8*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; getAbsoluteTime() => (int sec, int usec);
define { %int, %int } @shadow.standard..Time_MgetAbsoluteTime(%shadow.standard..Time*) {
entry:
	%now = alloca %struct.timeval
	%call = call %int @gettimeofday(%struct.timeval* %now, i8* null)
	
	%now.tv_sec.addr = getelementptr inbounds %struct.timeval, %struct.timeval* %now, i32 0, i32 0
	%now.tv_sec = load %int, %int* %now.tv_sec.addr
	
	%now.tv_usec.addr = getelementptr inbounds %struct.timeval, %struct.timeval* %now, i32 0, i32 1
	%now.tv_usec = load %int, %int* %now.tv_usec.addr
	
    %retVal.1 = insertvalue { %int, %int } undef, %int %now.tv_sec, 0
    %retVal = insertvalue { %int, %int } %retVal.1, %int %now.tv_usec, 1
	
    ret { %int, %int } %retVal
}