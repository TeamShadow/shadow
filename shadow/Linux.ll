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
%"shadow:standard@Object:_methods" = type { %"shadow:standard@Object"* (%"shadow:standard@Object"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Object"*)* }
%"shadow:standard@Object" = type { %"shadow:standard@Class"*, %"shadow:standard@Object:_methods"*  }
%"shadow:standard@Class:_methods" = type { %"shadow:standard@Class"* (%"shadow:standard@Class"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, { %"shadow:standard@Object"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %uint (%"shadow:standard@Class"*)*, %"shadow:standard@Object"* (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, { %"shadow:standard@Class"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*, %"shadow:standard@String"*, { %"shadow:standard@Object"**, [1 x %int] }, %int, %int)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %"shadow:standard@Class"* (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)* }
%"shadow:standard@Class" = type { %"shadow:standard@Class"*, %"shadow:standard@Class:_methods"* , %"shadow:standard@String"*, %"shadow:standard@Class"*, { %"shadow:standard@Object"**, [1 x %int] }, { %"shadow:standard@Class"**, [1 x %int] }, %int, %int }
%"shadow:standard@GenericClass:_methods" = type { %"shadow:standard@GenericClass"* (%"shadow:standard@GenericClass"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %uint (%"shadow:standard@Class"*)*, %"shadow:standard@Object"* (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, { %"shadow:standard@Class"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@GenericClass"*, %"shadow:standard@Class"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %"shadow:standard@Class"* (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, { %"shadow:standard@Object"**, [1 x %int] } (%"shadow:standard@GenericClass"*)* }
%"shadow:standard@GenericClass" = type { %"shadow:standard@Class"*, %"shadow:standard@GenericClass:_methods"* , %"shadow:standard@String"*, %"shadow:standard@Class"*, { %"shadow:standard@Object"**, [1 x %int] }, { %"shadow:standard@Class"**, [1 x %int] }, %int, %int, { %"shadow:standard@Object"**, [1 x %int] } }
%"shadow:standard@ArrayClass:_methods" = type { %"shadow:standard@ArrayClass"* (%"shadow:standard@ArrayClass"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %uint (%"shadow:standard@Class"*)*, %"shadow:standard@Object"* (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, { %"shadow:standard@Class"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@GenericClass"*, %"shadow:standard@Class"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %"shadow:standard@Class"* (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, { %"shadow:standard@Object"**, [1 x %int] } (%"shadow:standard@GenericClass"*)* }
%"shadow:standard@ArrayClass" = type { %"shadow:standard@Class"*, %"shadow:standard@ArrayClass:_methods"* , %"shadow:standard@String"*, %"shadow:standard@Class"*, { %"shadow:standard@Object"**, [1 x %int] }, { %"shadow:standard@Class"**, [1 x %int] }, %int, %int, { %"shadow:standard@Object"**, [1 x %int] }, %"shadow:standard@Class"* }
%"shadow:standard@Iterator:_methods" = type { %boolean (%"shadow:standard@Object"*)*, %"shadow:standard@Object"* (%"shadow:standard@Object"*)* }
%"shadow:standard@String:_methods" = type { %"shadow:standard@String"* (%"shadow:standard@String"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)*, { %byte*, [1 x %int] } (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %boolean (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %uint (%"shadow:standard@String"*)*, %byte (%"shadow:standard@String"*, %int)*, %boolean (%"shadow:standard@String"*)*, { %"shadow:standard@Iterator:_methods"*, %"shadow:standard@Object"* } (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %int)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %int, %int)*, %byte (%"shadow:standard@String"*)*, %double (%"shadow:standard@String"*)*, %float (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*)*, %long (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)*, %short (%"shadow:standard@String"*)*, %ubyte (%"shadow:standard@String"*)*, %uint (%"shadow:standard@String"*)*, %ulong (%"shadow:standard@String"*)*, %ushort (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)* }
%"shadow:standard@String" = type { %"shadow:standard@Class"*, %"shadow:standard@String:_methods"* , { %byte*, [1 x %int] }, %boolean }
%"shadow:standard@AddressMap:_methods" = type opaque
%"shadow:standard@AddressMap" = type opaque

%"shadow:standard@Exception:_methods" = type { %"shadow:standard@Exception"* (%"shadow:standard@Exception"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)* }
%"shadow:standard@Exception" = type { %"shadow:standard@Class"*, %"shadow:standard@Exception:_methods"* , %"shadow:standard@String"* }
%"shadow:io@IOException:_methods" = type { %"shadow:io@IOException"* (%"shadow:io@IOException"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)* }
%"shadow:io@IOException" = type { %"shadow:standard@Class"*, %"shadow:io@IOException:_methods"* , %"shadow:standard@String"* }

@"shadow:standard@Class:_methods" = external constant %"shadow:standard@Class:_methods"
@"shadow:standard@Class:class" = external constant %"shadow:standard@Class"
@"shadow:standard@String:_methods" = external constant %"shadow:standard@String:_methods"
@"shadow:standard@String:class" = external constant %"shadow:standard@Class"
@"shadow:standard@Exception:_methods" = external constant %"shadow:standard@Exception:_methods"
@"shadow:standard@Exception:class" = external constant %"shadow:standard@Class"
@"shadow:io@IOException:class" = external constant %"shadow:standard@Class"
@"shadow:io@IOException:_methods" = external constant %"shadow:io@IOException:_methods"

%"shadow:io@File:_methods" = type { %"shadow:io@File"* (%"shadow:io@File"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:io@File"*)*, void (%"shadow:io@File"*)*, void (%"shadow:io@File"*)*, %boolean (%"shadow:io@File"*)*, void (%"shadow:io@File"*, %boolean)*, %"shadow:io@Path"* (%"shadow:io@File"*)*, %long (%"shadow:io@File"*)*, void (%"shadow:io@File"*, %long)*, %int (%"shadow:io@File"*, { %byte*, [1 x %int] })*, %long (%"shadow:io@File"*)*, void (%"shadow:io@File"*, %long)*, %int (%"shadow:io@File"*, { %byte*, [1 x %int] })* }
%"shadow:io@File" = type { %"shadow:standard@Class"*, %"shadow:io@File:_methods"* , %long, %long, %"shadow:io@Path"* }
%"shadow:io@Path:_methods" = type { %"shadow:io@Path"* (%"shadow:io@Path"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:io@Path"*)*, %code (%"shadow:io@Path"*)* }
%"shadow:io@Path" = type { %"shadow:standard@Class"*, %"shadow:io@Path:_methods"* , { %"shadow:standard@String"**, [1 x %int] } }
%"shadow:standard@System" = type opaque
%"shadow:io@Console" = type opaque

declare %"shadow:standard@String"* @"shadow:standard@String.create(byte[])"(%"shadow:standard@Object"*, { i8*, [1 x i32] })
declare %"shadow:io@IOException"* @"shadow:io@IOException.create(shadow:standard@String)"(%"shadow:standard@Object"*, %"shadow:standard@String"*)
declare %"shadow:io@IOException"* @"shadow:io@IOException.create()"(%"shadow:standard@Object"*)
declare noalias %"shadow:standard@Object"* @"shadow:standard@Class.allocate()"(%"shadow:standard@Class"*, %"shadow:standard@Object:_methods"*)

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%"shadow:standard@Object"*) noreturn
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
	%3 = load i32, i32* %2
	%4 = call i8* @strerror_r(i32 %3, i8* %1, i32 256) nounwind
	%5 = insertvalue { i8*, [1 x i32] } undef, i8* %4, 0
	%6 = tail call i32 @strlen(i8* %4)
	%7 = insertvalue { i8*, [1 x i32] } %5, i32 %6, 1, 0
	%8 = call noalias %"shadow:standard@Object"* @"shadow:standard@Class.allocate()"(%"shadow:standard@Class"* @"shadow:standard@String:class", %"shadow:standard@Object:_methods"* bitcast(%"shadow:standard@String:_methods"* @"shadow:standard@String:_methods" to %"shadow:standard@Object:_methods"*))
	%9 = call %"shadow:standard@String"* @"shadow:standard@String.create(byte[])"(%"shadow:standard@Object"* %8, { i8*, [1 x i32] } %7)
	%10 = call noalias %"shadow:standard@Object"* @"shadow:standard@Class.allocate()"(%"shadow:standard@Class"* @"shadow:io@IOException:class", %"shadow:standard@Object:_methods"* bitcast(%"shadow:io@IOException:_methods"* @"shadow:io@IOException:_methods" to %"shadow:standard@Object:_methods"*))
	%11 = call %"shadow:io@IOException"* @"shadow:io@IOException.create(shadow:standard@String)"(%"shadow:standard@Object"* %10, %"shadow:standard@String"* %9)
	call void @__shadow_throw(%"shadow:standard@Object"* %10) noreturn
    unreachable
}

define i64 @"shadow:standard@System.nanoTime()"(%"shadow:standard@System"*) {
	%2 = alloca %struct.timespec
	%3 = call i32 @clock_gettime(i32 1, %struct.timespec* %2) nounwind
	%4 = icmp eq i32 %3, 0
	br i1 %4, label %5, label %14
	%6 = getelementptr inbounds %struct.timespec, %struct.timespec* %2, i32 0, i32 0
	%7 = load i1*, i1** %6
	%8 = ptrtoint i1* %7 to i64
	%9 = mul nuw i64 %8, 1000000000
	%10 = getelementptr inbounds %struct.timespec, %struct.timespec* %2, i32 0, i32 1
	%11 = load i1*, i1** %10
	%12 = ptrtoint i1* %11 to i64
	%13 = add i64 %9, %12
	ret i64 %13
	ret i64 0
}

@newline = private unnamed_addr constant [1 x i8] c"\0A"
define void @"shadow:io@Console.init()"(%"shadow:io@Console"*) {
	ret void
}
define { i8, i1 } @"shadow:io@Console.readByte()"(%"shadow:io@Console"*) {
	%2 = alloca i8
	%3 = call i32 @read(i32 0, i8* nocapture %2, i32 1)
	%4 = icmp ne i32 %3, 1
	%5 = insertvalue { i8, i1 } undef, i1 %4, 1
	%6 = load i8, i8* %2
	%7 = select i1 %4, i8 0, i8 %6
	%8 = insertvalue { i8, i1 } %5, i8 %7, 0
	ret { i8, i1 } %8
}
define %"shadow:io@Console"* @"shadow:io@Console.print(shadow:standard@String)"(%"shadow:io@Console"*, %"shadow:standard@String"*) {
	%3 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %1, i32 0, i32 2, i32 0
	%4 = load i8*, i8** %3
	%5 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %1, i32 0, i32 2, i32 1, i32 0
	%6 = load i32, i32* %5
	%7 = call i32 @write(i32 1, i8* nocapture %4, i32 %6)
	ret %"shadow:io@Console"* %0
}
define %"shadow:io@Console"* @"shadow:io@Console.printLine()"(%"shadow:io@Console"*) {
	%2 = call i32 @write(i32 1, i8* nocapture getelementptr inbounds ([1 x i8], [1 x i8]* @newline, i32 0, i32 0), i32 1)
	ret %"shadow:io@Console"* %0
}
define %"shadow:io@Console"* @"shadow:io@Console.printError(shadow:standard@String)"(%"shadow:io@Console"*, %"shadow:standard@String"*) {
	%3 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %1, i32 0, i32 2, i32 0
	%4 = load i8*, i8** %3
	%5 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %1, i32 0, i32 2, i32 1, i32 0
	%6 = load i32, i32* %5
	%7 = call i32 @write(i32 2, i8* nocapture %4, i32 %6)
	ret %"shadow:io@Console"* %0
}
define %"shadow:io@Console"* @"shadow:io@Console.printErrorLine()"(%"shadow:io@Console"*) {
	%2 = call i32 @write(i32 2, i8* nocapture getelementptr inbounds ([1 x i8], [1 x i8]* @newline, i32 0, i32 0), i32 1)
	ret %"shadow:io@Console"* %0
}

define i32 @"shadow:io@Path.separator()"(%"shadow:io@Path"*) {
	ret i32 47
}

define private i8* @filepath(%"shadow:io@File"*) {
	%2 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 4
	%3 = load %"shadow:io@Path"*, %"shadow:io@Path"** %2
	%4 = getelementptr %"shadow:io@Path", %"shadow:io@Path"* %3, i32 0, i32 1
	%5 = load %"shadow:io@Path:_methods"*, %"shadow:io@Path:_methods"** %4
	%6 = getelementptr %"shadow:io@Path:_methods", %"shadow:io@Path:_methods"* %5, i32 0, i32 2
	%7 = load %"shadow:standard@String"* (%"shadow:io@Path"*)*, %"shadow:standard@String"* (%"shadow:io@Path"*)** %6
	%8 = tail call %"shadow:standard@String"* %7(%"shadow:io@Path"* %3)
	%9 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %8, i32 0, i32 2, i32 0
	%10 = load i8*, i8** %9
	%11 = getelementptr inbounds %"shadow:standard@String", %"shadow:standard@String"* %8, i32 0, i32 2, i32 1, i32 0
	%12 = load i32, i32* %11
	%13 = add nuw i32 %12, 1
	%14 = tail call noalias i8* @malloc(i32 %13)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %14, i8* %10, i32 %12, i32 1, i1 0)
	%15 = getelementptr inbounds i8, i8* %14, i32 %12
	store i8 0, i8* %15
	ret i8* %14
}

define i1 @"shadow:io@File.exists()"(%"shadow:io@File"*) {
	%2 = tail call i8* @filepath(%"shadow:io@File"* %0)
	%3 = tail call i32 @access(i8* %2, i32 0)
	tail call void @free(i8* %2)
	%4 = icmp sge i32 %3, 0
	ret i1 %4
}
define void @"shadow:io@File.exists(boolean)"(%"shadow:io@File"*, i1) {
	tail call void @"shadow:io@File.close()"(%"shadow:io@File"* %0)
	%3 = tail call i8* @filepath(%"shadow:io@File"* %0)
	br i1 %1, label %4, label %10
	%5 = tail call i32 (i8*, i32, ...) @open(i8* %3, i32 193, i32 420)
	tail call void @free(i8* %3)
	%6 = icmp sge i32 %5, 0
	br i1 %6, label %7, label %14
	%8 = sext i32 %5 to i64
	%9 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
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
define i64 @"shadow:io@File.position()"(%"shadow:io@File"*) {
	%2 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%3 = load i64, i64* %2
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
define void @"shadow:io@File.position(long)"(%"shadow:io@File"*, i64) {
	%3 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i64 @lseek64(i32 %5, i64 %1, i32 0)
	ret void
}
define i64 @"shadow:io@File.size()"(%"shadow:io@File"*) {
	%2 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%3 = load i64, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = tail call i64 @lseek64(i32 %4, i64 0, i32 2)
	%7 = tail call i64 @lseek64(i32 %4, i64 %5, i32 0)
	ret i64 %6
}
define void @"shadow:io@File.size(long)"(%"shadow:io@File"*, i64) {
	%3 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i32 @ftruncate64(i32 %5, i64 %1)
	%7 = icmp sge i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	%10 = icmp slt i32 %5, 0
	br i1 %10, label %11, label %15
	%12 = tail call i8* @filepath(%"shadow:io@File"* %0)
	%13 = tail call i32 @truncate64(i8* %12, i64 %1)
	tail call void @free(i8* %12)
	%14 = icmp sge i32 %13, 0
	br i1 %14, label %8, label %15
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @"shadow:io@File.read(byte[])"(%"shadow:io@File"*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%4 = load i64, i64* %3
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
	%15 = load i32, i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @"shadow:io@File.close()"(%"shadow:io@File"* %0)
	br label %20
	%21 = phi i32 [ 0, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%"shadow:io@File"* %0)
	%23 = tail call i32 (i8*, i32, ...) @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @"shadow:io@File.write(byte[])"(%"shadow:io@File"*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%4 = load i64, i64* %3
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
	%15 = load i32, i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @"shadow:io@File.close()"(%"shadow:io@File"* %0)
	br label %20
	%21 = phi i32 [ 1, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%"shadow:io@File"* %0)
	%23 = tail call i32 (i8*, i32, ...) @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}
define void @"shadow:io@File.close()"(%"shadow:io@File"*) {
	%2 = getelementptr inbounds %"shadow:io@File", %"shadow:io@File"* %0, i32 0, i32 2
	%3 = load i64, i64* %2
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