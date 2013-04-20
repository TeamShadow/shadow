; Linux specific native methods

%_Pshadow_Pstandard_CObject_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, i1 (%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)* }
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CClass_Mclass = type opaque
%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass*, { %_Pshadow_Pstandard_CObject**, [1 x i32] }, { %_Pshadow_Pstandard_CClass**, [1 x i32] }, %_Pshadow_Pstandard_CString*, %_Pshadow_Pstandard_CClass*, i32, i32, i32 }
%_Pshadow_Pstandard_CString_Mclass = type opaque
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }
%_Pshadow_Pstandard_CException_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CException* (%_Pshadow_Pstandard_CException*)*, i1 (%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CException* (%_Pshadow_Pstandard_CException*, %_Pshadow_Pstandard_CString*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)*, void (%_Pshadow_Pstandard_CException*)* }
%_Pshadow_Pstandard_CException = type { %_Pshadow_Pstandard_CException_Mclass* }
@_Pshadow_Pstandard_CException_Mclass = external constant %_Pshadow_Pstandard_CException_Mclass
%_Pshadow_Pstandard_CSystem = type opaque
%_Pshadow_Pio_CPath = type opaque
%_Pshadow_Pio_CFile_Mclass = type opaque
%_Pshadow_Pio_CFile = type { %_Pshadow_Pio_CFile_Mclass*, i64, i64, %_Pshadow_Pio_CPath* }
%_Pshadow_Pio_CConsole_Mclass = type opaque
%_Pshadow_Pio_CConsole = type { %_Pshadow_Pio_CConsole_Mclass*, i1 }
@_Pshadow_Pio_CConsole_Mclass = external constant %_Pshadow_Pio_CConsole_Mclass
%_Pshadow_Pio_CIOException_Mclass = type { %_Pshadow_Pstandard_CClass }
%_Pshadow_Pio_CIOException = type { %_Pshadow_Pio_CIOException_Mclass* }
@_Pshadow_Pio_CIOException_Mclass = external constant %_Pshadow_Pio_CIOException_Mclass

declare %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate(%_Pshadow_Pio_CIOException*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

declare i32* @__errno_location() nounwind readnone
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
	%1 = tail call i32* @__errno_location() nounwind readnone
	%2 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pio_CIOException_Mclass* @_Pshadow_Pio_CIOException_Mclass, i32 0, i32 0))
	%3 = bitcast %_Pshadow_Pstandard_CObject* %2 to %_Pshadow_Pio_CIOException*
	%4 = call %_Pshadow_Pio_CIOException* @_Pshadow_Pio_CIOException_Mcreate(%_Pshadow_Pio_CIOException* %3)
	%5 = bitcast %_Pshadow_Pio_CIOException* %4 to %_Pshadow_Pstandard_CException*
	%6 = getelementptr inbounds %_Pshadow_Pstandard_CException* %5, i32 0, i32 0
	%7 = load %_Pshadow_Pstandard_CException_Mclass** %6
	%8 = getelementptr inbounds %_Pshadow_Pstandard_CException_Mclass* %7, i32 0, i32 9
	%9 = load void (%_Pshadow_Pstandard_CException*)** %8
	call void %9(%_Pshadow_Pstandard_CException* %5)
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
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 3
	%3 = load %_Pshadow_Pio_CPath** %2
	%4 = bitcast %_Pshadow_Pio_CPath* %3 to %_Pshadow_Pstandard_CObject*
	%5 = getelementptr %_Pshadow_Pstandard_CObject* %4, i32 0, i32 0
	%6 = load %_Pshadow_Pstandard_CObject_Mclass** %5
	%7 = getelementptr %_Pshadow_Pstandard_CObject_Mclass* %6, i32 0, i32 6
	%8 = load %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)** %7
	%9 = tail call %_Pshadow_Pstandard_CString* %8(%_Pshadow_Pstandard_CObject* %4)
	%10 = getelementptr inbounds %_Pshadow_Pstandard_CString* %9, i32 0, i32 1, i32 0
	%11 = load i8** %10
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CString* %9, i32 0, i32 1, i32 1, i32 0
	%13 = load i32* %12
	%14 = add nuw i32 %13, 1
	%15 = tail call noalias i8* @malloc(i32 %14)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %15, i8* %11, i32 %13, i32 1, i1 0)
	%16 = getelementptr inbounds i8* %15, i32 %13
	store i8 0, i8* %16
	ret i8* %15
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
	%9 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%4 = load i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i64 @lseek64(i32 %5, i64 %1, i32 0)
	ret void
}
define i64 @_Pshadow_Pio_CFile_Msize(%_Pshadow_Pio_CFile*) {
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
	%3 = load i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = tail call i64 @lseek64(i32 %4, i64 0, i32 2)
	%7 = tail call i64 @lseek64(i32 %4, i64 %5, i32 0)
	ret i64 %6
}
define void @_Pshadow_Pio_CFile_Msize_Pshadow_Pstandard_Clong(%_Pshadow_Pio_CFile*, i64) {
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
	%3 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
	%2 = getelementptr inbounds %_Pshadow_Pio_CFile* %0, i32 0, i32 1
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
