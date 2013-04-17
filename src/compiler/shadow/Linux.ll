; Linux specific native methods

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

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

declare i32 @open(i8* nocapture, i32)
declare i32 @read(i32, i8* nocapture, i32)
declare i32 @write(i32, i8* nocapture, i32)
declare i64 @lseek64(i32, i64, i32)
declare i32 @ftruncate64(i32, i64)
declare i32 @close(i32)

%struct.timespec = type { i1*, i1* }
declare i32 @clock_gettime(i32, %struct.timespec*) nounwind

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
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
	%4 = load i8** %3
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
	%6 = load i32* %5
	%7 = call i32 @write(i32 1, i8* nocapture %4, i32 %6)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintLine(%_Pshadow_Pio_CConsole*) {
	%2 = call i32 @write(i32 1, i8* nocapture getelementptr inbounds ([1 x i8]* @newline, i32 0, i32 0), i32 1)
	ret %_Pshadow_Pio_CConsole* %0
}
define %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*) {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 0
	%4 = load i8** %3
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CString* %1, i32 0, i32 1, i32 1, i32 0
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
	ret i8* %15
}

define void @_Pshadow_Pio_CFile_Mopen(%_Pshadow_Pio_CFile*) {
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
	%17 = call i32 @open(i8* nocapture %15, i32 3)
	%18 = add i32 %17, 1
	%19 = inttoptr i32 %18 to %_Pshadow_Pstandard_CObject*
	%20 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject* %19, %_Pshadow_Pstandard_CObject** %20
	tail call void @free(i8* %15)
	ret void
}
define i32 @_Pshadow_Pio_CFile_Mread_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%4 = load %_Pshadow_Pstandard_CObject** %3
	%5 = ptrtoint %_Pshadow_Pstandard_CObject* %4 to i32
	%6 = sub i32 %5, 1
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	%9 = tail call i32 @read(i32 %6, i8* %7, i32 %8)
	ret i32 %9
}
define i32 @_Pshadow_Pio_CFile_Mwrite_Pshadow_Pstandard_Cbyte_A1(%_Pshadow_Pio_CFile*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%4 = load %_Pshadow_Pstandard_CObject** %3
	%5 = ptrtoint %_Pshadow_Pstandard_CObject* %4 to i32
	%6 = sub i32 %5, 1
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	%9 = tail call i32 @write(i32 %6, i8* %7, i32 %8)
	ret i32 %9
}
define i64 @_Pshadow_Pio_CFile_Mposition(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%3 = load %_Pshadow_Pstandard_CObject** %2
	%4 = ptrtoint %_Pshadow_Pstandard_CObject* %3 to i32
	%5 = sub i32 %4, 1
	%6 = tail call i64 @lseek64(i32 %5, i64 0, i32 1)
	ret i64 %6
}
define void @_Pshadow_Pio_CFile_Mposition_Pshadow_Pstandard_Culong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%4 = load %_Pshadow_Pstandard_CObject** %3
	%5 = ptrtoint %_Pshadow_Pstandard_CObject* %4 to i32
	%6 = sub i32 %5, 1
	%7 = tail call i64 @lseek64(i32 %6, i64 %1, i32 0)
	ret void
}
define i64 @_Pshadow_Pio_CFile_Msize(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%3 = load %_Pshadow_Pstandard_CObject** %2
	%4 = ptrtoint %_Pshadow_Pstandard_CObject* %3 to i32
	%5 = sub i32 %4, 1
	%6 = tail call i64 @lseek64(i32 %5, i64 0, i32 1)
	%7 = tail call i64 @lseek64(i32 %5, i64 0, i32 2)
	%8 = tail call i64 @lseek64(i32 %5, i64 %6, i32 0)
	ret i64 %7
}
define void @_Pshadow_Pio_CFile_Msize_Pshadow_Pstandard_Culong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%4 = load %_Pshadow_Pstandard_CObject** %3
	%5 = ptrtoint %_Pshadow_Pstandard_CObject* %4 to i32
	%6 = sub i32 %5, 1
	%7 = tail call i32 @ftruncate64(i32 %6, i64 %1)
	ret void
}
define void @_Pshadow_Pio_CFile_Mclose(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%3 = load %_Pshadow_Pstandard_CObject** %2
	%4 = icmp ne %_Pshadow_Pstandard_CObject* %3, null
	br i1 %4, label %5, label %8
	%6 = ptrtoint %_Pshadow_Pstandard_CObject* %3 to i32
	%7 = tail call i32 @close(i32 %6)
	br label %8
	ret void
}
