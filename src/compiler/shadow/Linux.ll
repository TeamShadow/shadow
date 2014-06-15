; Linux specific native methods

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
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)


declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

declare i32* @__errno_location() nounwind readnone
declare i8* @strerror_r(i32, i8*, i32) nounwind
declare i32 @strlen(i8*) nounwind readonly
declare i32 @access(i8* nocapture, i32)
declare i64 @lseek64(i32, i64, i32)
declare i32 @truncate64(i8* nocapture, i64)
declare i32 @ftruncate64(i32, i64)
declare i32 @open(i8* nocapture, i32, ...)
declare i32 @read(i32, i8* nocapture, i32)
declare i32 @write(i32, i8* nocapture, i32)
declare i32 @unlink(i8*)
declare i32 @close(i32)

%struct.timespec = type { i1*, i1* }
declare i32 @clock_gettime(i32, %struct.timespec*) nounwind

define private void @throwIOException() noreturn {
	%1 = alloca i8, i32 256
	%2 = tail call i32* @__errno_location() nounwind readnone
	%3 = load i32* %2
	%4 = call i8* @strerror_r(i32 %3, i8* %1, i32 256) nounwind
	%5 = insertvalue { i8*, [1 x i32] } undef, i8* %4, 0
	%6 = tail call i32 @strlen(i8* %4)
	%7 = insertvalue { i8*, [1 x i32] } %5, i32 %6, 1, 0
	%8 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CString_class)
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %8, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CString_class, %_Pshadow_Pstandard_CClass** %9
	%10 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %8, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pstandard_CString_methods* @_Pshadow_Pstandard_CString_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %10
	%11 = call %_Pshadow_Pstandard_CString* @_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pstandard_CObject* %8, { i8*, [1 x i32] } %7)
	%12 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CIOException_class)
	%13 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %12, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CIOException_class, %_Pshadow_Pstandard_CClass** %13
	%14 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %12, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pio_CIOException_methods* @_Pshadow_Pio_CIOException_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %10
	%15 = call %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate_Pshadow_Pstandard_CString(%_Pshadow_Pstandard_CObject* %12, %_Pshadow_Pstandard_CString* %11)
	%16 = bitcast %"_Pshadow_Pio_CIOException"* %15 to %"_Pshadow_Pstandard_CObject"*
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %16) noreturn
    unreachable
}

define i64 @_Pshadow_Pstandard_CSystem_MnanoTime(%_Pshadow_Pstandard_CSystem*) {
	%2 = alloca %struct.timespec
	%3 = call i32 @clock_gettime(i32 1, %struct.timespec* %2) nounwind
	%4 = icmp eq i32 %3, 0
	br i1 %4, label %5, label %14
	%6 = getelementptr inbounds %struct.timespec* %2, i32 0, i32 0
	%7 = load i1** %6
	%8 = ptrtoint i1* %7 to i64
	%9 = mul nuw i64 %8, 1000000000
	%10 = getelementptr inbounds %struct.timespec* %2, i32 0, i32 1
	%11 = load i1** %10
	%12 = ptrtoint i1* %11 to i64
	%13 = add i64 %9, %12
	ret i64 %13
	ret i64 0
}

@newline = private unnamed_addr constant [1 x i8] c"\0A"
define void @_Pshadow_Pio_CConsole_Minit(%_Pshadow_Pio_CConsole*) {
	ret void
}
define { i8, i1 } @_Pshadow_Pio_CConsole_MreadByte(%_Pshadow_Pio_CConsole*) {
	%2 = alloca i8
	%3 = call i32 @read(i32 0, i8* nocapture %2, i32 1)
	%4 = icmp ne i32 %3, 1
	%5 = insertvalue { i8, i1 } undef, i1 %4, 1
	%6 = load i8* %2
	%7 = select i1 %4, i8 0, i8 %6
	%8 = insertvalue { i8, i1 } %5, i8 %7, 0
	ret { i8, i1 } %8
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 0
	%4 = load i8** %3
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 1, i32 0
	%6 = load i32* %5
	%7 = call i32 @write(i32 1, i8* nocapture %4, i32 %6)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintLine(%_Pshadow_Pio_CConsole*) {
	%2 = call i32 @write(i32 1, i8* nocapture getelementptr inbounds ([1 x i8]* @newline, i32 0, i32 0), i32 1)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 0
	%4 = load i8** %3
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 2, i32 1, i32 0
	%6 = load i32* %5
	%7 = call i32 @write(i32 2, i8* nocapture %4, i32 %6)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine(%_Pshadow_Pio_CConsole*) {
	%2 = call i32 @write(i32 2, i8* nocapture getelementptr inbounds ([1 x i8]* @newline, i32 0, i32 0), i32 1)
	ret %_Pshadow_Pio_CConsole* %0
}

define i32 @_Pshadow_Pio_CPath_Mseparator(%_Pshadow_Pio_CPath*) {
	ret i32 47
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
	%3 = tail call i32 @access(i8* %2, i32 0)
	tail call void @free(i8* %2)
	%4 = icmp sge i32 %3, 0
	ret i1 %4
}
define void @_Pshadow_Pio_CFile_Mexists_Pshadow_Pstandard_Cboolean(%_Pshadow_Pio_CFile*, i1) {
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	%3 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	br i1 %1, label %4, label %10
	%5 = tail call i32 (i8*, i32, ...)* @open(i8* %3, i32 193, i32 420)
	tail call void @free(i8* %3)
	%6 = icmp sge i32 %5, 0
	br i1 %6, label %7, label %14
	%8 = sext i32 %5 to i64
	%9 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	store i64 %8, i64* %9
	ret void
	%11 = tail call i32 @unlink(i8* %3)
	tail call void @free(i8* %3)
	%12 = icmp sge i32 %11, 0
	br i1 %12, label %13, label %14
	ret void
	tail call void @throwIOException() noreturn
	unreachable
}
define i64 @_Pshadow_Pio_CFile_Mposition(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%3 = load i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = icmp sge i64 %5, 0
	br i1 %6, label %7, label %8
	ret i64 %5
	%9 = icmp slt i64 %3, 0
	br i1 %9, label %10, label %11
	ret i64 0
	tail call void @throwIOException() noreturn
	unreachable
}
define void @_Pshadow_Pio_CFile_Mposition_Pshadow_Pstandard_Clong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i64 @lseek64(i32 %5, i64 %1, i32 0)
	ret void
}
define i64 @_Pshadow_Pio_CFile_Msize(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%3 = load i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = tail call i64 @lseek64(i32 %4, i64 0, i32 2)
	%7 = tail call i64 @lseek64(i32 %4, i64 %5, i32 0)
	ret i64 %6
}
define void @_Pshadow_Pio_CFile_Msize_Pshadow_Pstandard_Clong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i32 @ftruncate64(i32 %5, i64 %1)
	%7 = icmp sge i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	%10 = icmp slt i32 %5, 0
	br i1 %10, label %11, label %15
	%12 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%13 = tail call i32 @truncate64(i8* %12, i64 %1)
	tail call void @free(i8* %12)
	%14 = icmp sge i32 %13, 0
	br i1 %14, label %8, label %15
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @_Pshadow_Pio_CFile_Mread_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = trunc i64 %4 to i32
	%6 = extractvalue { i8*, [1 x i32] } %1, 0
	%7 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %8
	%9 = phi i32 [ %5, %2 ], [ %23, %20 ]
	%10 = tail call i32 @read(i32 %9, i8* %6, i32 %7)
	%11 = icmp sge i32 %10, 0
	br i1 %11, label %12, label %13
	ret i32 %10
	%14 = tail call i32* @__errno_location()
	%15 = load i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	br label %20
	%21 = phi i32 [ 0, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%23 = tail call i32 (i8*, i32, ...)* @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @_Pshadow_Pio_CFile_Mwrite_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%4 = load i64* %3
	%5 = trunc i64 %4 to i32
	%6 = extractvalue { i8*, [1 x i32] } %1, 0
	%7 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %8
	%9 = phi i32 [ %5, %2 ], [ %23, %20 ]
	%10 = tail call i32 @write(i32 %9, i8* %6, i32 %7)
	%11 = icmp sge i32 %10, 0
	br i1 %11, label %12, label %13
	ret i32 %10
	%14 = tail call i32* @__errno_location()
	%15 = load i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile* %0)
	br label %20
	%21 = phi i32 [ 1, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%_Pshadow_Pio_CFile* %0)
	%23 = tail call i32 (i8*, i32, ...)* @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}
define void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 2
	%3 = load i64* %2
	store i64 -1, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i32 @close(i32 %4)
	%6 = icmp sge i32 %5, 0
	br i1 %6, label %7, label %8
	ret void
	%9 = icmp slt i64 %3, 0
	br i1 %9, label %7, label %10
	tail call void @throwIOException() noreturn
	unreachable
}
