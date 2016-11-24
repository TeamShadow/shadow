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

; String
%shadow.standard..String = type opaque

; Console
%shadow.io..Console = type opaque

;---------------------
; Method Declarations
;---------------------
declare void @__ShadowConsole_Initialize()
declare void @__ShadowConsole_ReadByte(%byte*, %boolean*)

declare void @__ShadowConsole_Print(%shadow.standard..String*)
declare void @__ShadowConsole_PrintLine()

declare void @__ShadowConsole_PrintError(%shadow.standard..String*)
declare void @__ShadowConsole_PrintErrorLine()

;---------------------------
; Shadow Method Definitions
;---------------------------
; init() => ();
define void @shadow.io..Console_Minit(%shadow.io..Console*) {
entry:
	call void @__ShadowConsole_Initialize()
	ret void
}

; readByte() => (byte value, boolean eof);
%ReadByteReturn = type { %byte, %boolean }
define %ReadByteReturn @shadow.io..Console_MreadByte(%shadow.io..Console*) {
entry:
	%ret.addr = alloca %ReadByteReturn
	
	; get address pointers
	%value.addr = getelementptr %ReadByteReturn, %ReadByteReturn* %ret.addr, i32 0, i32 0
	%eof.addr = getelementptr %ReadByteReturn, %ReadByteReturn* %ret.addr, i32 0, i32 1
	
	; call the native method
	call void @__ShadowConsole_ReadByte(%byte* %value.addr, %boolean* %eof.addr)
	
	; return the actual values
	%ret = load %ReadByteReturn, %ReadByteReturn* %ret.addr
	ret %ReadByteReturn %ret
}

; print(String) => (Console);
define %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*) {
entry:
	call void @__ShadowConsole_Print(%shadow.standard..String* %1)
	ret %shadow.io..Console* %0
}

; printLine() => (Console);
define %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) {
entry:
	call void @__ShadowConsole_PrintLine()
	ret %shadow.io..Console* %0
}

; printError(String) => (Console);
define %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*) {
entry:
	call void @__ShadowConsole_PrintError(%shadow.standard..String* %1)
	ret %shadow.io..Console* %0
}

; printErrorLine() => (Console);
define %shadow.io..Console* @shadow.io..Console_MprintErrorLine(%shadow.io..Console*) {
entry:
	call void @__ShadowConsole_PrintErrorLine()
	ret %shadow.io..Console* %0
}