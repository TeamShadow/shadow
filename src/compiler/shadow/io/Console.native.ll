; shadow.io@Console native methods

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

%_Pshadow_Pstandard_CObject = type opaque
%_Pshadow_Pstandard_CClass = type opaque
%_Pshadow_Pstandard_CString_Imethods = type opaque
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Imethods*, { %ubyte*, [1 x %int] } }
%_Pshadow_Pio_CConsole = type opaque

%FILE = type { i8*, i32, i8*, i32, i32, i32, i32, i8* }
@stdin = extern_weak global %FILE*
@stdout = extern_weak global %FILE*
@stderr = extern_weak global %FILE*
@_imp___iob = extern_weak global [0 x %FILE]*

define private %FILE* @in() {
	%1 = icmp ne %FILE** @stdin, null
	br i1 %1, label %2, label %4
	%3 = load %FILE** @stdin
	ret %FILE* %3
	%5 = load [0 x %FILE]** @_imp___iob
	%6 = getelementptr [0 x %FILE]* %5, i32 0, i32 0
	ret %FILE* %6
}

define private %FILE* @out() {
	%1 = icmp ne %FILE** @stdout, null
	br i1 %1, label %2, label %4
	%3 = load %FILE** @stdout
	ret %FILE* %3
	%5 = load [0 x %FILE]** @_imp___iob
	%6 = getelementptr [0 x %FILE]* %5, i32 0, i32 1
	ret %FILE* %6
}

define private %FILE* @err() {
	%1 = icmp ne %FILE** @stderr, null
	br i1 %1, label %2, label %4
	%3 = load %FILE** @stderr
	ret %FILE* %3
	%5 = load [0 x %FILE]** @_imp___iob
	%6 = getelementptr [0 x %FILE]* %5, i32 0, i32 2
	ret %FILE* %6
}

declare i32 @fwrite(%ubyte* nocapture, i32, i32, %FILE* nocapture) nounwind

define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = getelementptr %"_Pshadow_Pstandard_CString"* %1, i32 0, i32 1
	%4 = load { %ubyte*, [1 x %int] }* %3
	%5 = extractvalue { %ubyte*, [1 x %int] } %4, 0
	%6 = extractvalue { %ubyte*, [1 x %int] } %4, 1, 0
	%7 = call %FILE* @out()
	%8 = call i32 @fwrite(%ubyte* %5, i32 1, i32 %6, %FILE* %7)
	ret %_Pshadow_Pio_CConsole* %0
}
