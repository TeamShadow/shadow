; Windows specific native methods

%_Pshadow_Pstandard_CObject_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, i1 (%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)* }
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CClass_Mclass = type opaque
%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass*, { %_Pshadow_Pstandard_CClass**, [1 x i32] }, %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CString*, %_Pshadow_Pstandard_CClass*, i32, i32, i32 }
%_Pshadow_Pstandard_CString_Mclass = type opaque
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }
%_Pshadow_Pstandard_CSystem = type opaque
%_Pshadow_Pio_CPath = type opaque
%_Pshadow_Pio_CFile_Mclass = type opaque
%_Pshadow_Pio_CFile = type { %_Pshadow_Pio_CFile_Mclass*, %_Pshadow_Pstandard_CObject*, %_Pshadow_Pio_CPath* }
%_Pshadow_Pio_CConsole_Mclass = type opaque
%_Pshadow_Pio_CConsole = type { %_Pshadow_Pio_CConsole_Mclass*, i1 }
@_Pshadow_Pio_CConsole_Mclass = external constant %_Pshadow_Pio_CConsole_Mclass

declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CObject_Mcreate(%_Pshadow_Pstandard_CObject*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

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
declare x86_stdcallcc i32 @GetFileType(i8*)
declare x86_stdcallcc i32 @GetConsoleMode(i8*, i32*)
declare x86_stdcallcc i32 @SetConsoleMode(i8*, i32)
declare x86_stdcallcc i32 @SetConsoleCP(i32)
declare x86_stdcallcc i32 @SetConsoleOutputCP(i32)
declare x86_stdcallcc i32 @OpenFile(i8*, i8*, i32)
declare x86_stdcallcc i32 @ReadFile(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteFile(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteConsoleA(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteConsoleW(i8*, i8*, i32, i32*, i8*)
define void @_Pshadow_Pio_CConsole_Minit(%_Pshadow_Pio_CConsole*) {
	%2 = call x86_stdcallcc i32 @SetConsoleCP(i32 65001)
	%3 = call x86_stdcallcc i32 @SetConsoleOutputCP(i32 65001)
	ret void
}
define { i8, i1 } @_Pshadow_Pio_CConsole_MreadByte(%_Pshadow_Pio_CConsole*) {
	%2 = alloca i32
	%3 = alloca i8
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -10)
	%5 = call x86_stdcallcc i32 @ReadFile(i8* %4, i8* %3, i32 1, i32* %2, i8* null)
	%6 = load i32* %2
	%7 = icmp ne i32 %6, 1
	%8 = load i8* %3
	%9 = select i1 %7, i8 0, i8 %8
	%10 = insertvalue { i8, i1 } undef, i8 %9, 0
	%11 = insertvalue { i8, i1 } %10, i1 %7, 1
	ret { i8, i1 } %11
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
	%6 = load i8** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
	%8 = load i32* %7
;	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2
;	%10 = load i1* %9
;	br i1 %10, label %11, label %13
	%9 = call x86_stdcallcc i32 @WriteFile(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
;	%14 = call x86_stdcallcc i32 @GetFileType(i8* %4)
;	%15 = icmp ne i32 %14, 2
;	br i1 %15, label %11, label %16
;	%3 = alloca i32
;	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
;	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
;	%6 = load i8** %5
;	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
;	%8 = load i32* %7
;	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2
;	%10 = load i1* %9
;	br i1 %10, label %11, label %13
;	%12 = call x86_stdcallcc i32 @WriteConsoleA(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
;	ret %_Pshadow_Pio_CConsole* %0
;	%14 = shl i32 %8, 1
;	%15 = tail call noalias i8* @malloc(i32 %14) nounwind
;	%16 = bitcast i8* %15 to i16*
;	%17 = call x86_stdcallcc i32 @MultiByteToWideChar(i32 65001, i32 0, i8* %6, i32 %8, i16* %16, i32 %8)
;	%18 = call x86_stdcallcc i32 @WriteConsoleW(i8* %4, i8* %15, i32 %17, i32* %3, i8* null)
;	call void @free(i8* %15) nounwind
;	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintLine(%_Pshadow_Pio_CConsole*) {
	%2 = alloca i32
	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%4 = call x86_stdcallcc i32 @WriteFile(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
	ret %_Pshadow_Pio_CConsole* %0
;	%2 = alloca i32
;	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
;	%4 = call x86_stdcallcc i32 @WriteConsoleA(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
;	ret %_Pshadow_Pio_CConsole* %0
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

define i32 @_Pshadow_Pio_CPath_Mseparator(%_Pshadow_Pio_CPath*) {
	ret i32 92
}

define void @_Pshadow_Pio_CFile_Mopen(%_Pshadow_Pio_CFile*) {
	%temp = alloca i8, i32 88
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%3 = load %_Pshadow_Pio_CPath** %2
	%4 = bitcast %_Pshadow_Pio_CPath* %3 to %_Pshadow_Pstandard_CObject*
	%5 = getelementptr %_Pshadow_Pstandard_CObject* %4, i32 0, i32 0
	%6 = load %_Pshadow_Pstandard_CObject_Mclass** %5
	%7 = getelementptr %_Pshadow_Pstandard_CObject_Mclass* %6, i32 0, i32 6
	%8 = load %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)** %7
	%9 = call %_Pshadow_Pstandard_CString* %8(%_Pshadow_Pstandard_CObject* %4)
	%10 = getelementptr inbounds %_Pshadow_Pstandard_CString* %9, i32 0, i32 1, i32 0
	%11 = load i8** %10
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CString* %9, i32 0, i32 1, i32 1, i32 0
	%13 = load i32* %12
	%14 = add nuw i32 %13, 1
	%15 = call noalias i8* @malloc(i32 %14)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %15, i8* %11, i32 %13, i32 1, i1 0)
	%16 = getelementptr inbounds i8* %15, i32 %13
	store i8 0, i8* %16
	%17 = call i32 @OpenFile(i8* %15, i8* %temp, i32 2)
	call void @free(i8* %15)
	%18 = inttoptr i32 %17 to %_Pshadow_Pstandard_CObject*
	%19 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject* %18, %_Pshadow_Pstandard_CObject** %19
	ret void
}
define i32 @_Pshadow_Pio_CFile_Mread_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%5 = load %_Pshadow_Pstandard_CObject** %4
	%6 = bitcast %_Pshadow_Pstandard_CObject* %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	%9 = call i32 @ReadFile(i8* %6, i8* %7, i32 %8, i32* %3, i8* null)
	%10 = load i32* %3
	ret i32 %10
}
define i32 @_Pshadow_Pio_CFile_Mwrite_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%5 = load %_Pshadow_Pstandard_CObject** %4
	%6 = bitcast %_Pshadow_Pstandard_CObject* %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	%9 = call i32 @WriteFile(i8* %6, i8* %7, i32 %8, i32* %3, i8* null)
	%10 = load i32* %3
	ret i32 %10
}
