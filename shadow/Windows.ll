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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type { %shadow.standard..GenericClass* (%shadow.standard..GenericClass*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..GenericClass*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..GenericClass*)* }
%shadow.standard..GenericClass = type { %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int, { %shadow.standard..Object**, [1 x %int] } }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.io..IOException_methods = type { %shadow.io..IOException* (%shadow.io..IOException*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.io..IOException = type { %shadow.standard..Class*, %shadow.io..IOException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.io..IOException_class = external constant %shadow.standard..Class
@shadow.io..IOException_methods = external constant %shadow.io..IOException_methods

%shadow.io..File_methods = type { %shadow.io..File* (%shadow.io..File*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..File*)*, void (%shadow.io..File*)*, void (%shadow.io..File*)*, %boolean (%shadow.io..File*)*, void (%shadow.io..File*, %boolean)*, %shadow.io..Path* (%shadow.io..File*)*, %long (%shadow.io..File*)*, void (%shadow.io..File*, %long)*, %int (%shadow.io..File*, { %byte*, [1 x %int] })*, %long (%shadow.io..File*)*, void (%shadow.io..File*, %long)*, %int (%shadow.io..File*, { %byte*, [1 x %int] })* }
%shadow.io..File = type { %shadow.standard..Class*, %shadow.io..File_methods* , %long, %long, %shadow.io..Path* }
%shadow.io..Path_methods = type { %shadow.io..Path* (%shadow.io..Path*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..Path*)*, %code (%shadow.io..Path*)* }
%shadow.io..Path = type { %shadow.standard..Class*, %shadow.io..Path_methods* , { %shadow.standard..String**, [1 x %int] } }
%shadow.standard..System = type opaque
%shadow.io..Console = type opaque

declare %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A1(%shadow.standard..Object*, { i8*, [1 x i32] })
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class*, %shadow.standard..Object_methods*)


declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
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
	%2 = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.io..IOException_class, %shadow.standard..Object_methods* bitcast(%shadow.io..IOException_methods* @shadow.io..IOException_methods to %shadow.standard..Object_methods*))
	%3 = call %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object* %2)
	%4 = bitcast %shadow.io..IOException* %3 to %shadow.standard..Object*
	call void @__shadow_throw(%shadow.standard..Object* %4) noreturn
    unreachable	
}

define i64 @shadow.standard..System_MnanoTime(%shadow.standard..System*) {
	%2 = alloca i64
	%3 = alloca i64
	%4 = call x86_stdcallcc i32 @QueryPerformanceFrequency(i64* %2)
	%5 = icmp ne i32 %4, 0
	br i1 %5, label %6, label %14
	%7 = call x86_stdcallcc i32 @QueryPerformanceCounter(i64* %3)
	%8 = icmp ne i32 %7, 0
	br i1 %8, label %9, label %14
	%10 = load i64, i64* %2
	%11 = load i64, i64* %3
	%12 = mul nuw i64 %11, 1000000000
	%13 = udiv i64 %12, %10
	ret i64 %13
	ret i64 0
}

define void @shadow.io..Console_Minit(%shadow.io..Console*) {
	%2 = call x86_stdcallcc i32 @SetConsoleCP(i32 65001)
	%3 = call x86_stdcallcc i32 @SetConsoleOutputCP(i32 65001)
	ret void
}
define { i8, i1 } @shadow.io..Console_MreadByte(%shadow.io..Console*) {
	%2 = alloca i32
	%3 = alloca i8
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -10)
	%5 = call x86_stdcallcc i32 @ReadFile(i8* %4, i8* %3, i32 1, i32* %2, i8* null)
	%6 = load i32, i32* %2
	%7 = icmp ne i32 %6, 1
	%8 = load i8, i8* %3
	%9 = select i1 %7, i8 0, i8 %8
	%10 = insertvalue { i8, i1 } undef, i8 %9, 0
	%11 = insertvalue { i8, i1 } %10, i1 %7, 1
	ret { i8, i1 } %11
}

; does this work for non-ASCII strings?
define %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%5 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %1, i32 0, i32 2, i32 0
	%6 = load i8*, i8** %5
	%7 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %1, i32 0, i32 2, i32 1, i32 0
	%8 = load i32, i32* %7
	%9 = call x86_stdcallcc i32 @WriteFile(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	;%10 = call x86_stdcallcc i32 @FlushFileBuffers(i8* %4)
	ret %shadow.io..Console* %0
}
define %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) {
	%2 = alloca i32
	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -11)
	%4 = call x86_stdcallcc i32 @WriteFile(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8], [2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
	ret %shadow.io..Console* %0
}
define %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*) {
	%3 = alloca i32
	%4 = call x86_stdcallcc i8* @GetStdHandle(i32 -12)
	%5 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %1, i32 0, i32 2, i32 0
	%6 = load i8*, i8** %5
	%7 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %1, i32 0, i32 2, i32 1, i32 0
	%8 = load i32, i32* %7
	%9 = call x86_stdcallcc i32 @WriteFile(i8* %4, i8* %6, i32 %8, i32* %3, i8* null)
	;%10 = call x86_stdcallcc i32 @FlushFileBuffers(i8* %4)
	ret %shadow.io..Console* %0
}
define %shadow.io..Console* @shadow.io..Console_MprintErrorLine(%shadow.io..Console*) {
	%2 = alloca i32
	%3 = call x86_stdcallcc i8* @GetStdHandle(i32 -12)
	%4 = call x86_stdcallcc i32 @WriteFile(i8* %3, i8* nocapture getelementptr inbounds ([2 x i8], [2 x i8]* @newline, i32 0, i32 0), i32 2, i32* %2, i8* null)
	ret %shadow.io..Console* %0
}

define i32 @shadow.io..Path_Mseparator(%shadow.io..Path*) {
	ret i32 92
}

define private i8* @filepath(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 4
	%3 = load %shadow.io..Path*, %shadow.io..Path** %2
	%4 = getelementptr %shadow.io..Path, %shadow.io..Path* %3, i32 0, i32 1
	%5 = load %shadow.io..Path_methods*, %shadow.io..Path_methods** %4
	%6 = getelementptr %shadow.io..Path_methods, %shadow.io..Path_methods* %5, i32 0, i32 2
	%7 = load %shadow.standard..String* (%shadow.io..Path*)*, %shadow.standard..String* (%shadow.io..Path*)** %6
	%8 = tail call %shadow.standard..String* %7(%shadow.io..Path* %3)
	%9 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %8, i32 0, i32 2, i32 0
	%10 = load i8*, i8** %9
	%11 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %8, i32 0, i32 2, i32 1, i32 0
	%12 = load i32, i32* %11
	%13 = add nuw i32 %12, 1
	%14 = tail call noalias i8* @malloc(i32 %13)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %14, i8* %10, i32 %12, i32 1, i1 0)
	%15 = getelementptr inbounds i8, i8* %14, i32 %12
	store i8 0, i8* %15
	ret i8* %14
}

define i1 @shadow.io..File_Mexists(%shadow.io..File*) {
	%2 = tail call i8* @filepath(%shadow.io..File* %0)
	%3 = tail call x86_stdcallcc i32 @GetFileAttributesA(i8* %2)
	tail call void @free(i8* %2)
	%4 = icmp sge i32 %3, 0
	ret i1 %4
}
define void @shadow.io..File_Mexists_boolean(%shadow.io..File*, i1) {
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	%3 = tail call i8* @filepath(%shadow.io..File* %0)
	br i1 %1, label %4, label %10
	%5 = tail call x86_stdcallcc i8* @CreateFileA(i8* %3, i32 shl (i32 1, i32 30), i32 7, i8* null, i32 1, i32 128, i8* null)
	tail call void @free(i8* %3)
	%6 = ptrtoint i8* %5 to i64
	%7 = icmp sge i64 %6, 0
	br i1 %7, label %8, label %14
	%9 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
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
define i64 @shadow.io..File_Mposition(%shadow.io..File*) {
	%2 = alloca i64
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%4 = load i64, i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %5, i64 0, i64* %2, i32 1)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %10
	%9 = load i64, i64* %2
	ret i64 %9
	tail call void @throwIOException() noreturn
	unreachable
}
define void @shadow.io..File_Mposition_long(%shadow.io..File*, i64) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%4 = load i64, i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %5, i64 %1, i64* null, i32 0)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	tail call void @throwIOException() noreturn
	unreachable
}
define i64 @shadow.io..File_Msize(%shadow.io..File*) {
	%2 = alloca i64
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%4 = load i64, i64* %3
	%5 = inttoptr i64 %4 to i8*
	%6 = call x86_stdcallcc i32 @GetFileSizeEx(i8* %5, i64* %2)
	%7 = icmp ne i32 %6, 0
	br i1 %7, label %8, label %10
	%9 = load i64, i64* %2
	ret i64 %9
	tail call void @throwIOException() noreturn
	unreachable
}
define void @shadow.io..File_Msize_long(%shadow.io..File*, i64) {
	%3 = alloca i64
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%5 = load i64, i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 0, i64* %3, i32 1)
	%8 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 %1, i64* null, i32 0)
	%9 = call x86_stdcallcc i32 @SetEndOfFile(i8* %6)
	%10 = load i64, i64* %3
	%11 = call x86_stdcallcc i32 @SetFilePointerEx(i8* %6, i64 %10, i64* null, i32 0)
	ret void
}
define i32 @shadow.io..File_Mread_byte_A1(%shadow.io..File*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%5 = load i64, i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %9
	%10 = phi i8* [ %6, %2 ], [ %26, %23 ]
	%11 = call x86_stdcallcc i32 @ReadFile(i8* %10, i8* %7, i32 %8, i32* %3, i8* null)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label %13, label %15
	%14 = load i32, i32* %3
	ret i32 %14
	%16 = tail call i32 @GetLastError()
	%17 = icmp eq i32 %16, 5
	%18 = icmp eq i32 %16, 6
	%19 = or i1 %17, %18
	br i1 %19, label %20, label %29
	%21 = icmp sge i64 %5, 0
	br i1 %21, label %22, label %23
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	br label %23
	%24 = phi i32 [ shl (i32 1, i32 31), %20 ], [ shl (i32 3, i32 30), %22 ]
	%25 = tail call i8* @filepath(%shadow.io..File* %0)
	%26 = tail call x86_stdcallcc i8* @CreateFileA(i8* %25, i32 %24, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %25)
	%27 = ptrtoint i8* %26 to i64
	store i64 %27, i64* %4
	%28 = icmp sge i64 %27, 0
	br i1 %28, label %9, label %29
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @shadow.io..File_Mwrite_byte_A1(%shadow.io..File*, { i8*, [1 x i32] }) {
	%3 = alloca i32
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%5 = load i64, i64* %4
	%6 = inttoptr i64 %5 to i8*
	%7 = extractvalue { i8*, [1 x i32] } %1, 0
	%8 = extractvalue { i8*, [1 x i32] } %1, 1, 0
	br label %9
	%10 = phi i8* [ %6, %2 ], [ %26, %23 ]
	%11 = call x86_stdcallcc i32 @WriteFile(i8* %10, i8* %7, i32 %8, i32* %3, i8* null)
	%12 = icmp ne i32 %11, 0
	br i1 %12, label %13, label %15
	%14 = load i32, i32* %3
	ret i32 %14
	%16 = tail call i32 @GetLastError()
	%17 = icmp eq i32 %16, 5
	%18 = icmp eq i32 %16, 6
	%19 = or i1 %17, %18
	br i1 %19, label %20, label %29
	%21 = icmp sge i64 %5, 0
	br i1 %21, label %22, label %23
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	br label %23
	%24 = phi i32 [ shl (i32 1, i32 30), %20 ], [ shl (i32 3, i32 30), %22 ]
	%25 = tail call i8* @filepath(%shadow.io..File* %0)
	%26 = tail call x86_stdcallcc i8* @CreateFileA(i8* %25, i32 %24, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %25)
	%27 = ptrtoint i8* %26 to i64
	store i64 %27, i64* %4
	%28 = icmp sge i64 %27, 0
	br i1 %28, label %9, label %29
	tail call void @throwIOException() noreturn
	unreachable
}
define void @shadow.io..File_Mclose(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 2
	%3 = load i64, i64* %2
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
