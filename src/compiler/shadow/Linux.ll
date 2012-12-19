; Linux specific native methods

%_Pshadow_Pstandard_CString_Mclass = type opaque
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }
%_Pshadow_Pstandard_CSystem = type opaque
%_Pshadow_Pio_CConsole = type opaque

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
declare i32 @write(i32, i8* nocapture, i32)
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
