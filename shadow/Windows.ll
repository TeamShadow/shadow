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
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %int, %int, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] } }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%ulong, %byte}*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.io..IOException_methods = type opaque
%shadow.io..IOException = type { %ulong, %shadow.standard..Class*, %shadow.io..IOException_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.io..IOException_class = external constant %shadow.standard..Class
@shadow.io..IOException_methods = external constant %shadow.io..IOException_methods

%shadow.io..File_methods = type opaque
%shadow.io..File = type { %ulong, %shadow.standard..Class*, %shadow.io..File_methods* , %long, %shadow.io..Path* }
%shadow.io..Path_methods = type { %shadow.io..Path* (%shadow.io..Path*, %shadow.standard..AddressMap*)*, void (%shadow.io..Path*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..Path*)*, %code (%shadow.io..Path*)* }
%shadow.io..Path = type { %ulong, %shadow.standard..Class*, %shadow.io..Path_methods* , {{%ulong, %shadow.standard..String*}*, [1 x %int] } }
%shadow.standard..System = type opaque
%shadow.io..Console = type opaque

declare %shadow.io..IOException* @shadow.io..IOException_Mcreate_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)


declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

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

; one day update this method to use the @FormatMessage() function
define private void @throwIOException() noreturn {
	%1 = tail call x86_stdcallcc i32 @GetLastError()
	%2 = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..IOException_class, %shadow.standard..Object_methods* bitcast(%shadow.io..IOException_methods* @shadow.io..IOException_methods to %shadow.standard..Object_methods*))
	%3 = call %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object* %2)
	%4 = bitcast %shadow.io..IOException* %3 to %shadow.standard..Object*
	call void @__shadow_throw(%shadow.standard..Object* %4) noreturn
    unreachable	
}

define i32 @shadow.io..Path_Mseparator(%shadow.io..Path*) {
	ret i32 92
}

define private i8* @filepath(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 4
	%3 = load %shadow.io..Path*, %shadow.io..Path** %2
	%4 = getelementptr %shadow.io..Path, %shadow.io..Path* %3, i32 0, i32 2
	%5 = load %shadow.io..Path_methods*, %shadow.io..Path_methods** %4
	%6 = getelementptr %shadow.io..Path_methods, %shadow.io..Path_methods* %5, i32 0, i32 3
	%7 = load %shadow.standard..String* (%shadow.io..Path*)*, %shadow.standard..String* (%shadow.io..Path*)** %6
	%8 = tail call %shadow.standard..String* %7(%shadow.io..Path* %3)
	%9 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %8, i32 0, i32 3, i32 0
	%10 = load {%ulong, i8}*, {%ulong, i8}** %9
	%11 = getelementptr inbounds {%ulong, i8}, {%ulong, i8}* %10, i32 0, i32 1	
	%12 = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %8, i32 0, i32 3, i32 1, i32 0
	%13 = load i32, i32* %12
	%14 = add nuw i32 %13, 1
	%15 = tail call noalias i8* @malloc(i32 %14)
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %15, i8* %11, i32 %13, i32 1, i1 0)
	%16 = getelementptr inbounds i8, i8* %15, i32 %13
	store i8 0, i8* %16
	ret i8* %15
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
	%9 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%4 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
