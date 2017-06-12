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
%size_t = type i8*

; standard definitions
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , %shadow.standard..Array*, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %long }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %long }

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
@byte_A_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.io..IOException_class = external constant %shadow.standard..Class
@shadow.io..IOException_methods = external constant %shadow.io..IOException_methods

%shadow.io..File_methods = type opaque
%shadow.io..File = type { %ulong, %shadow.standard..Class*, %shadow.io..File_methods* , %long, %shadow.io..Path* }
%shadow.io..Path_methods = type { %shadow.io..Path* (%shadow.io..Path*, %shadow.standard..AddressMap*)*, void (%shadow.io..Path*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..Path*)*, %code (%shadow.io..Path*)* }
%shadow.io..Path = type { %ulong, %shadow.standard..Class*, %shadow.io..Path_methods* , {{%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } }
%shadow.standard..System = type opaque
%shadow.io..Console = type opaque

declare %shadow.io..IOException* @shadow.io..IOException_Mcreate_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array) alwaysinline nounwind


declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i32, i1)

declare noalias i8* @malloc(%size_t nocapture) nounwind
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

define i32 @shadow.io..Path_Mseparator(%shadow.io..Path*) alwaysinline {
	ret i32 92
}

declare void @shadow.io..File_Mclose(%shadow.io..File*)

define private i8* @filepath(%shadow.io..File* %file) {
	%pathRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 4
	%path = load %shadow.io..Path*, %shadow.io..Path** %pathRef
	%methodsRef = getelementptr %shadow.io..Path, %shadow.io..Path* %path, i32 0, i32 2
	%methods = load %shadow.io..Path_methods*, %shadow.io..Path_methods** %methodsRef
	%toStringRef = getelementptr %shadow.io..Path_methods, %shadow.io..Path_methods* %methods, i32 0, i32 3
	%toString = load %shadow.standard..String* (%shadow.io..Path*)*, %shadow.standard..String* (%shadow.io..Path*)** %toStringRef
	%string = tail call %shadow.standard..String* %toString(%shadow.io..Path* %path)
	%arrayRef = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %string, i32 0, i32 3
	%array = load %shadow.standard..Array*, %shadow.standard..Array** %arrayRef
	%data = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1		
	%bytes = bitcast %shadow.standard..Array* %data to i8*
	%size = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array) nounwind
	%sizeWithNull = add nuw i64 %size, 1
	%sizeHack = inttoptr i64 %size to %size_t	
	%cstring = tail call noalias i8* @malloc(%size_t %sizeHack)
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %cstring, i8* %bytes, i64 %size, i32 1, i1 0)
	%nullRef = getelementptr inbounds i8, i8* %cstring, i64 %size
	store i8 0, i8* %nullRef
	ret i8* %cstring
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
define %long @shadow.io..File_Mread_byte_A(%shadow.io..File* %file, %shadow.standard..Array* %array) {
_entry:
	%bytesReadRef = alloca i32
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%handle = inttoptr i64 %descriptorAsLong to i8*
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array) nounwind
	%length = trunc %long %longLength to %int
	br label %_read
_read:
	%realHandle = phi i8* [ %handle, %_entry ], [ %newHandle, %_open ]
	%result = call x86_stdcallcc i32 @ReadFile(i8* %realHandle, i8* %buffer, i32 %length, i32* %bytesReadRef, i8* null)
	%checkError = icmp ne i32 %result, 0
	br i1 %checkError, label %_success, label %_error
_success:
	%bytesRead = load i32, i32* %bytesReadRef
	%readAsLong = sext i32 %bytesRead to %long
	ret %long %readAsLong
_error:
	%error = tail call i32 @GetLastError()	
	; ERROR_ACCESS_DENIED = 5, ERROR_INVALID_HANDLE = 6	
	%denied = icmp eq i32 %error, 5
	%invalid = icmp eq i32 %error, 6
	%deniedOrInvalid = or i1 %denied, %invalid
	br i1 %deniedOrInvalid, label %_checkIfOpen, label %_throw
_checkIfOpen:
	%open = icmp sge i64 %descriptorAsLong, 0
	br i1 %open, label %_close, label %_open
_close:
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %file)
	br label %_open
_open:
	; GENERIC_READ = 0x80000000, GENERIC_READ | GENERIC_WRITE = 0xC0000000
	%access = phi i32 [ shl (i32 1, i32 31), %_checkIfOpen ], [ shl (i32 3, i32 30), %_close ]
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	%newHandle = tail call x86_stdcallcc i8* @CreateFileA(i8* %path, i32 %access, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %path)
	%handleAsLong = ptrtoint i8* %newHandle to i64
	store i64 %handleAsLong, i64* %descriptorRef
	%checkValid = icmp sge i64 %handleAsLong, 0
	br i1 %checkValid, label %_read, label %_throw
_throw:
	tail call void @throwIOException() noreturn
	unreachable
}

define %long @shadow.io..File_Mwrite_byte_A(%shadow.io..File* %file, %shadow.standard..Array* %array) {
_entry:
	%bytesWrittenRef = alloca i32
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%handle = inttoptr i64 %descriptorAsLong to i8*
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array) nounwind
	%length = trunc %long %longLength to %int
	br label %_write	
_write:
	%realHandle = phi i8* [ %handle, %_entry ], [ %newHandle, %_open ]
	%result = call x86_stdcallcc i32 @WriteFile(i8* %realHandle, i8* %buffer, i32 %length, i32* %bytesWrittenRef, i8* null)
	%checkError = icmp ne i32 %result, 0
	br i1 %checkError, label %_success, label %_error
_success:
	%bytesWritten = load i32, i32* %bytesWrittenRef
	%writtenAsLong = sext i32 %bytesWritten to %long
	ret %long %writtenAsLong	
_error:
	%error = tail call i32 @GetLastError()	
	; ERROR_ACCESS_DENIED = 5, ERROR_INVALID_HANDLE = 6	
	%denied = icmp eq i32 %error, 5
	%invalid = icmp eq i32 %error, 6
	%deniedOrInvalid = or i1 %denied, %invalid
	br i1 %deniedOrInvalid, label %_checkIfOpen, label %_throw
_checkIfOpen:
	%open = icmp sge i64 %descriptorAsLong, 0
	br i1 %open, label %_close, label %_open
_close:
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %file)
	br label %_open
_open:
	; GENERIC_WRITE = 0x40000000, GENERIC_READ | GENERIC_WRITE = 0xC0000000
	%access = phi i32 [ shl (i32 1, i32 30), %_checkIfOpen ], [ shl (i32 3, i32 30), %_close ]
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	%newHandle = tail call x86_stdcallcc i8* @CreateFileA(i8* %path, i32 %access, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %path)
	%handleAsLong = ptrtoint i8* %newHandle to i64
	store i64 %handleAsLong, i64* %descriptorRef
	%checkValid = icmp sge i64 %handleAsLong, 0
	br i1 %checkValid, label %_write, label %_throw
_throw:
	tail call void @throwIOException() noreturn
	unreachable
}