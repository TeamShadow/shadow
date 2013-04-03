; Windows specific native methods

%_Pshadow_Pstandard_CString_Mclass = type opaque
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }
%_Pshadow_Pstandard_CSystem = type opaque
%_Pshadow_Pio_CConsole = type opaque

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

declare x86_stdcallcc i32 @MultiByteToWideChar(i32, i32, i8*, i32, i16*, i32)

declare x86_stdcallcc i32 @QueryPerformanceFrequency(i64*)
declare x86_stdcallcc i32 @QueryPerformanceCounter(i64*)
define i64 @_Pshadow_Pstandard_CSystem_MnanoTime(%_Pshadow_Pstandard_CSystem*) {
	%2 = alloca i64
	%3 = alloca i64
	%4 = call x86_stdcallcc i32 @QueryPerformanceFrequency(i64* %2)
	%5 = icmp ne i32 %4, 0
	br i1 %5, label %6, label %14
	%7 = call x86_stdcallcc i32 @QueryPerformanceCounter(i64* %3)
	%8 = icmp ne i32 %7, 0
	br i1 %8, label %9, label %14
	%10 = load i64* %2
	%11 = load i64* %3
	%12 = mul nuw i64 %11, 1000000000
	%13 = udiv i64 %12, %10
	ret i64 %13
	ret i64 0
}

@newline = private unnamed_addr constant [2 x i8] c"\0D\0A"
declare x86_stdcallcc i8* @GetStdHandle(i32)
declare x86_stdcallcc i32 @WriteConsoleA(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteConsoleW(i8*, i8*, i32, i32*, i8*)
define { i8, i1 } @_Pshadow_Pio_CConsole_MreadByte(%_Pshadow_Pio_CConsole*) {
	ret { i8, i1 } { i8 0, i1 1 }
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
	%6 = load i8** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
	%8 = load i32* %7
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2
	%10 = load i1* %9
	br i1 %10, label %11, label %13
	%12 = call x86_stdcallcc i32 @WriteConsoleA(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
	%14 = shl i32 %8, 1
	%15 = tail call noalias i8* @malloc(i32 %14) nounwind
	%16 = bitcast i8* %15 to i16*
	%17 = call x86_stdcallcc i32 @MultiByteToWideChar(i32 65001, i32 0, i8* %6, i32 %8, i16* %16, i32 %8)
	%18 = call x86_stdcallcc i32 @WriteConsoleW(i8* %4, i8* %15, i32 %17, i32* %3, i8* null)
	call void @free(i8* %15) nounwind
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintLine(%_Pshadow_Pio_CConsole*) {
	%2 = alloca i32
	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%4 = call x86_stdcallcc i32 @WriteConsoleA(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -12)
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
	%6 = load i8** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
	%8 = load i32* %7
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2
	%10 = load i1* %9
	br i1 %10, label %11, label %13
	%12 = call x86_stdcallcc i32 @WriteConsoleA(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
	%14 = shl i32 %8, 1
	%15 = tail call noalias i8* @malloc(i32 %14) nounwind
	%16 = bitcast i8* %15 to i16*
	%17 = call x86_stdcallcc i32 @MultiByteToWideChar(i32 65001, i32 0, i8* %6, i32 %8, i16* %16, i32 %8)
	%18 = call x86_stdcallcc i32 @WriteConsoleW(i8* %4, i8* %15, i32 %17, i32* %3, i8* null)
	call void @free(i8* %15) nounwind
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine(%_Pshadow_Pio_CConsole*) {
	%2 = alloca i32
	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -12)
	%4 = call x86_stdcallcc i32 @WriteConsoleA(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
}
