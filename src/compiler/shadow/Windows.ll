; Windows specific native methods

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

; standard definitions
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }

%"_Pshadow_Pio_CIOException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pio_CIOException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CIOException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }

@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CException_methods" = external constant %"_Pshadow_Pstandard_CException_methods"
@"_Pshadow_Pstandard_CException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pio_CIOException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pio_CIOException_methods" = external constant %"_Pshadow_Pio_CIOException_methods"
%"_Pshadow_Pio_CFile_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pio_CFile"*)*, void (%"_Pshadow_Pio_CFile"*)*, void (%"_Pshadow_Pio_CFile"*)*, %boolean (%"_Pshadow_Pio_CFile"*)*, void (%"_Pshadow_Pio_CFile"*, %boolean)*, %"_Pshadow_Pio_CPath"* (%"_Pshadow_Pio_CFile"*)*, %long (%"_Pshadow_Pio_CFile"*)*, void (%"_Pshadow_Pio_CFile"*, %long)*, %int (%"_Pshadow_Pio_CFile"*, { %byte*, [1 x %int] })*, %long (%"_Pshadow_Pio_CFile"*)*, void (%"_Pshadow_Pio_CFile"*, %long)*, %int (%"_Pshadow_Pio_CFile"*, { %byte*, [1 x %int] })* }
%"_Pshadow_Pio_CFile" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CFile_methods"* , %long, %long, %"_Pshadow_Pio_CPath"* }
%"_Pshadow_Pio_CPath_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pio_CPath"*)*, %code (%"_Pshadow_Pio_CPath"*)* }
%"_Pshadow_Pio_CPath" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CPath_methods"* , { %"_Pshadow_Pstandard_CString"**, [1 x %int] } }
%_Pshadow_Pstandard_CSystem = type opaque
%_Pshadow_Pio_CConsole = type opaque

declare %_Pshadow_Pstandard_CString* @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pstandard_CObject*, { i8*, [1 x i32] })
declare %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate_Pshadow_Pstandard_CString(%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CString*)
declare %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate(%_Pshadow_Pstandard_CObject*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)


declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

@newline = private unnamed_addr constant [2 x i8] c"\0D\0A"
declare x86_stdcallcc i8* @GetStdHandle(i32)
declare x86_stdcallcc i32 @GetFileType(i8*)
declare x86_stdcallcc i32 @GetConsoleMode(i8*, i32*)
declare x86_stdcallcc i32 @SetConsoleMode(i8*, i32)
declare x86_stdcallcc i32 @SetConsoleCP(i32)
declare x86_stdcallcc i32 @SetConsoleOutputCP(i32)
declare x86_stdcallcc i32 @WriteConsoleA(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteConsoleW(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @FlushFileBuffers(i8*)

declare x86_stdcallcc i32 @GetLastError()
declare x86_stdcallcc i32 @GetFileAttributesA(i8*)
declare x86_stdcallcc i32 @SetFilePointerEx(i8*, i64, i64*, i32)
declare x86_stdcallcc i32 @SetEndOfFile(i8*)
declare x86_stdcallcc i32 @GetFileSizeEx(i8*, i64*)
declare x86_stdcallcc i8* @CreateFileA(i8*, i32, i32, i8*, i32, i32, i8*)
declare x86_stdcallcc i32 @ReadFile(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @WriteFile(i8*, i8*, i32, i32*, i8*)
declare x86_stdcallcc i32 @DeleteFileA(i8*)
declare x86_stdcallcc i32 @CloseHandle(i8*)

declare x86_stdcallcc i32 @MultiByteToWideChar(i32, i32, i8*, i32, i16*, i32)

declare x86_stdcallcc i32 @QueryPerformanceFrequency(i64*)
declare x86_stdcallcc i32 @QueryPerformanceCounter(i64*)

; one day update this method to use the @FormatMessage() function
define private void @throwIOException() noreturn {
	%1 = tail call x86_stdcallcc i32 @GetLastError()
	%2 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CIOException_class)
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CIOException_class, %_Pshadow_Pstandard_CClass** %3
	%4 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pio_CIOException_methods* @_Pshadow_Pio_CIOException_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %4
	%5 = call %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate(%_Pshadow_Pstandard_CObject* %2)
	%6 = bitcast %"_Pshadow_Pio_CIOException"* %5 to %"_Pshadow_Pstandard_CObject"*
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %6) noreturn
    unreachable	
}

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

; does this work for non-ASCII strings?
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 0
	%6 = load i8** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 1, i32 0
	%8 = load i32* %7
	%9 = call x86_stdcallcc i32 @WriteFile(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	;%10 = call x86_stdcallcc i32 @FlushFileBuffers(i8* %4)
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
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 0
	%6 = load i8** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 1, i32 0
	%8 = load i32* %7
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 3
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

define private i8* @filepath(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 4
	%3 = load %_Pshadow_Pio_CPath** %2
	%4 = getelementptr %_Pshadow_Pio_CPath* %3, i32 0, i32 1
	%5 = load %_Pshadow_Pio_CPath_methods** %4
	%6 = getelementptr %_Pshadow_Pio_CPath_methods* %5, i32 0, i32 2
	%7 = load %_Pshadow_Pstandard_CString* (%_Pshadow_Pio_CPath*)** %6
	%8 = tail call %_Pshadow_Pstandard_CString* %7(%_Pshadow_Pio_CPath* %3)
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 2, i32 0
	%10 = load i8** %9
	%11 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 2, i32 1, i32 0
	%12 = load i32* %11
	%13 = add nuw i32 %12, 1
	%14 = tail call noalias i8* @malloc(i32 %13)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %14, i8* %10, i32 %12, i32 1, i1 0)
	%15 = getelementptr inbounds i8* %14, i32 %12
	store i8 0, i8* %15
	ret i8* %14
}

define i1 @_Pshadow_Pio_CFile_Mexists(%_Pshadow_Pio_CFile*) {
	%2 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%3 = tail call x86_stdcallcc i32 @GetFileAttributesA(i8* %2)
	tail call void @free(i8* %2)
	%4 = icmp sge i32 %3, 0
	ret i1 %4
}
define void @_Pshadow_Pio_CFile_Mexists_Pshadow_Pstandard_Cboolean(%_Pshadow_Pio_CFile*, i1) {
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	%3 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	br i1 %1, label %4, label %10
	%5 = tail call x86_stdcallcc i8* @CreateFileA(i8* %3, i32 shl (i32 1, i32 30), i32 7, i8* null, i32 1, i32 128, i8* null)
	tail call void @free(i8* %3)
	%6 = ptrtoint i8* %5 to i64
	%7 = icmp sge i64 %6, 0
	br i1 %7, label %8, label %14
	%9 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	store i64 %6, i64* %9
	ret void
	%11 = tail call x86_stdcallcc i32 @DeleteFileA(i8* %3)
	tail call void @free(i8* %3)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label %13, label %14
	ret void
	tail call void @throwIOException() noreturn
	unreachable
}
define i64 @_Pshadow_Pio_CFile_Mposition(%_Pshadow_Pio_CFile*) {
	%2 = alloca i64
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %5, i64 0, i64* %2, i32 1)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %10
	%9 = load i64* %2
	ret i64 %9
	tail call void @throwIOException() noreturn
	unreachable
}
define void @_Pshadow_Pio_CFile_Mposition_Pshadow_Pstandard_Clong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %5, i64 %1, i64* null, i32 0)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	tail call void @throwIOException() noreturn
	unreachable
}
define i64 @_Pshadow_Pio_CFile_Msize(%_Pshadow_Pio_CFile*) {
	%2 = alloca i64
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @GetFileSizeEx(i8* %5, i64* %2)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %10
	%9 = load i64* %2
	ret i64 %9
	tail call void @throwIOException() noreturn
	unreachable
}
define void @_Pshadow_Pio_CFile_Msize_Pshadow_Pstandard_Clong(%_Pshadow_Pio_CFile*, i64) {
	%3 = alloca i64
	%4 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%5 = load i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 0, i64* %3, i32 1)
	%8 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 %1, i64* null, i32 0)
	%9 = call x86_stdcallcc i32 @SetEndOfFile(i8* %6)
	%10 = load i64* %3
	%11 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 %10, i64* null, i32 0)
	ret void
}
define i32 @_Pshadow_Pio_CFile_Mread_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%5 = load i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %9
	%10 = phi i8* [ %6, %2 ], [ %26, %23 ]
	%11 = call x86_stdcallcc i32 @ReadFile(i8* %10, i8* %7, i32 %8, i32* %3, i8* null)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label %13, label %15
	%14 = load i32* %3
	ret i32 %14
	%16 = tail call i32 @GetLastError()
	%17 = icmp eq i32 %16, 5
	%18 = icmp eq i32 %16, 6
	%19 = or i1 %17, %18
	br i1 %19, label %20, label %29
	%21 = icmp sge i64 %5, 0
	br i1 %21, label %22, label %23
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	br label %23
	%24 = phi i32 [ shl (i32 1, i32 31), %20 ], [ shl (i32 3, i32 30), %22 ]
	%25 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%26 = tail call x86_stdcallcc i8* @CreateFileA(i8* %25, i32 %24, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %25)
	%27 = ptrtoint i8* %26 to i64
	store i64 %27, i64* %4
	%28 = icmp sge i64 %27, 0
	br i1 %28, label %9, label %29
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @_Pshadow_Pio_CFile_Mwrite_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%5 = load i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %9
	%10 = phi i8* [ %6, %2 ], [ %26, %23 ]
	%11 = call x86_stdcallcc i32 @WriteFile(i8* %10, i8* %7, i32 %8, i32* %3, i8* null)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label %13, label %15
	%14 = load i32* %3
	ret i32 %14
	%16 = tail call i32 @GetLastError()
	%17 = icmp eq i32 %16, 5
	%18 = icmp eq i32 %16, 6
	%19 = or i1 %17, %18
	br i1 %19, label %20, label %29
	%21 = icmp sge i64 %5, 0
	br i1 %21, label %22, label %23
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	br label %23
	%24 = phi i32 [ shl (i32 1, i32 30), %20 ], [ shl (i32 3, i32 30), %22 ]
	%25 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%26 = tail call x86_stdcallcc i8* @CreateFileA(i8* %25, i32 %24, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %25)
	%27 = ptrtoint i8* %26 to i64
	store i64 %27, i64* %4
	%28 = icmp sge i64 %27, 0
	br i1 %28, label %9, label %29
	tail call void @throwIOException() noreturn
	unreachable
}
define void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%3 = load i64* %2
	store i64 -1, i64* %2
	%4 = inttoptr i64 %3 to i8*
	%5 = tail call x86_stdcallcc i32 @CloseHandle(i8* %4)
	%6 = icmp ne i32 %5, 0
	br i1 %6, label %7, label %8
	ret void
	%9 = icmp slt i64 %3, 0
	br i1 %9, label %7, label %10
	tail call void @throwIOException() noreturn
	unreachable
}
