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
declare void @__ShadowConsole_ReadByte(%byte*, %boolean*)
declare void @__ShadowConsole_Print(%shadow.standard..String*)
declare void @__ShadowConsole_PrintLine()

@debugPrint = private global %boolean 0

define void @shadow.io..Console_MdebugPrint_shadow.standard..String(%shadow.io..Console* %console, %shadow.standard..String* %string) {
entry:
	%debug = load %boolean, %boolean* @debugPrint
	br i1 %debug, label %_print, label %_exit
_print:
	call void @__ShadowConsole_Print(%shadow.standard..String* %string)
	call void @__ShadowConsole_PrintLine()
	ret void
_exit:
	ret void
}

define void  @shadow.io..Console_MsetDebug_boolean(%shadow.io..Console* %console, %boolean %debug) {
	store %boolean %debug, %boolean* @debugPrint
	ret void	
}

;---------------------------
; Shadow Method Definitions
;---------------------------
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
