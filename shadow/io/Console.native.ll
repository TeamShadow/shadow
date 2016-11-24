; Console.native.ll
; 
; Author:
;   Barry Wittman
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

; Console
%shadow.io..Console = type opaque

;---------------------
; Method Declarations
;---------------------
declare void @__ShadowConsole_Initialize()

;---------------------------
; Shadow Method Definitions
;---------------------------
define void @shadow.io..Console_Minit(%shadow.io..Console*) {
entry:
	call void @__ShadowConsole_Initialize()
	ret void
}