; SignalToken.native.ll
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

; Signaler
%shadow.natives..Signaler = type opaque

; SignalToken
%shadow.natives..SignalToken = type opaque

;---------------------
; Method Declarations
;---------------------
declare void @shadow.natives..Signaler_MsignalNative_shadow.natives..SignalToken(%shadow.natives..Signaler*, %shadow.natives..SignalToken*)

;---------------------------
; Shadow Method Definitions
;---------------------------
define void @shadow.natives..SignalToken_Msignal_shadow.natives..Signaler(%shadow.natives..SignalToken*, %shadow.natives..Signaler*) {
entry:
	call void @shadow.natives..Signaler_MsignalNative_shadow.natives..SignalToken(%shadow.natives..Signaler* %1, %shadow.natives..SignalToken* %0)
	ret void
}