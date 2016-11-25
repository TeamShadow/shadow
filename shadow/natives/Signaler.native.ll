; Signaler.native.ll
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

; Thread
%shadow.standard..Thread = type opaque

; Pointer
%shadow.natives..ShadowPointer = type opaque

; SignalToken
%shadow.natives..SignalToken = type opaque

; Signaler
%shadow.natives..Signaler = type opaque

;---------------------
; Method Declarations
;---------------------
declare void @shadow.standard..Thread_MsetInterruptTokenNative_shadow.natives..SignalToken(%shadow.standard..Thread*, %shadow.natives..SignalToken*)

;---------------------------
; Shadow Method Definitions
;---------------------------
; setInterruptToken(nullable SignalToken interruptToken) => ();
define void @shadow.natives..Signaler_MsetInterruptToken_shadow.standard..Thread_shadow.natives..SignalToken(%shadow.natives..Signaler*, %shadow.standard..Thread*, %shadow.natives..SignalToken*) {
entry:
	call void @shadow.standard..Thread_MsetInterruptTokenNative_shadow.natives..SignalToken(%shadow.standard..Thread* %1, %shadow.natives..SignalToken* %2)
	ret void
}