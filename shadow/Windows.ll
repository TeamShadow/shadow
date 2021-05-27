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
%shadow.standard..Object._methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object._methods*  }
%shadow.standard..Class._methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass._methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator._methods = type opaque
%shadow.standard..String._methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String._methods* , %shadow.standard..Array*, %boolean }
%shadow.standard..AddressMap._methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable._methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array._methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array._methods* , %long }
%shadow.standard..ArrayNullable._methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable._methods* , %long }

%shadow.standard..Exception._methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception._methods* , %shadow.standard..String* }
%shadow.io..IOException._methods = type opaque
%shadow.io..IOException = type { %ulong, %shadow.standard..Class*, %shadow.io..IOException._methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException._methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException._methods* , %shadow.standard..String* }

@shadow.standard..Class._methods = external constant %shadow.standard..Class._methods
@shadow.standard..Class.class = external constant %shadow.standard..Class
@shadow.standard..String._methods = external constant %shadow.standard..String._methods
@shadow.standard..String.class = external constant %shadow.standard..Class
@ubyte._A.class = external constant %shadow.standard..Class
@shadow.standard..Exception._methods = external constant %shadow.standard..Exception._methods
@shadow.standard..Exception.class = external constant %shadow.standard..Class
@shadow.io..IOException.class = external constant %shadow.standard..Class
@shadow.io..IOException._methods = external constant %shadow.io..IOException._methods

%shadow.io..File._methods = type opaque
%shadow.io..File = type { %ulong, %shadow.standard..Class*, %shadow.io..File._methods* , %long, %shadow.io..Path* }
%shadow.io..Path._methods = type { %shadow.io..Path* (%shadow.io..Path*, %shadow.standard..AddressMap*)*, void (%shadow.io..Path*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..Path*)*, %code (%shadow.io..Path*)* }
%shadow.io..Path = type { %ulong, %shadow.standard..Class*, %shadow.io..Path._methods* , {{%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } }
%shadow.standard..System = type opaque

declare %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object*, %shadow.standard..Array*)
declare %shadow.io..IOException* @shadow.io..IOException..create_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException..create(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object._methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)
declare %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) alwaysinline nounwind

;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console..print_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console..printLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console..printLine(%shadow.io..Console*)
;declare %shadow.io..Console* @shadow.io..Console..debugPrint.int(%shadow.io..Console*, %int)

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
declare x86_stdcallcc i32 @FormatMessageA(i32, i8*, i32, i32, i8**, i32, i8*)

declare i8* @strncpy(i8*, i8* nocapture, %size_t) nounwind

; one day update this method to use the @FormatMessage() function
define private void @throwIOException() noreturn {
	%buffer = alloca i8, i32 1024
	%pointer = alloca i8*
	store i8* %buffer, i8** %pointer
	%error = tail call x86_stdcallcc i32 @GetLastError()
	; FORMAT..ESSAGE_FROM_SYSTEM = 0x1000
	; FORMAT..ESSAGE_IGNORE_INSERTS = 0x0200
	; combined is 0x1200 = 4608
	%characters = tail call x86_stdcallcc i32 @FormatMessageA( i32 4608, i8* null, i32 %error, i32 0, i8** %pointer, i32 1024, i8* null)
	%length = inttoptr i32 %characters to %size_t
	%lengthLong = ptrtoint %size_t %length to %ulong	
	%array = call noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @ubyte._A.class, %ulong %lengthLong, %boolean false) nounwind
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%dataAsChars = bitcast %shadow.standard..Array* %data to i8*
	call i8* @strncpy(i8* %dataAsChars, i8* nocapture %buffer, %size_t %length) nounwind
	%stringAsObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String.class, %shadow.standard..Object._methods* bitcast(%shadow.standard..String._methods* @shadow.standard..String._methods to %shadow.standard..Object._methods*))
	%string = call %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object* %stringAsObj, %shadow.standard..Array* %array)	
	%exceptionAsObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..IOException.class, %shadow.standard..Object._methods* bitcast(%shadow.io..IOException._methods* @shadow.io..IOException._methods to %shadow.standard..Object._methods*))
	%exception = call %shadow.io..IOException* @shadow.io..IOException..create_shadow.standard..String(%shadow.standard..Object* %exceptionAsObj, %shadow.standard..String* %string)
	call void @__shadow_throw(%shadow.standard..Object* %exceptionAsObj) noreturn
    unreachable	
}

define i32 @shadow.io..Path..separator(%shadow.io..Path*) alwaysinline {
	ret i32 92
}

define private i1 @handleIsLegal(i8* %handle) alwaysinline { 
	%pointer = getelementptr i8*, i8** null, i32 1
	%size = ptrtoint i8** %pointer to i32
	%is8Bytes = icmp eq i32 %size, 8
	br i1 %is8Bytes, label %_bits64, label %_bits32
_bits64:
	%asLong = ptrtoint i8* %handle to i64
	%valid64 = icmp sge i64 %asLong, 0
	ret i1 %valid64
_bits32:
	%asInt = ptrtoint i8* %handle to i32
	%valid32 = icmp sge i32 %asInt, 0
	ret i1 %valid32
}

declare void @shadow.io..File..close(%shadow.io..File*)

define private i8* @filepath(%shadow.io..File* %file) {
	%pathRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 4
	%path = load %shadow.io..Path*, %shadow.io..Path** %pathRef
	%methodsRef = getelementptr %shadow.io..Path, %shadow.io..Path* %path, i32 0, i32 2
	%methods = load %shadow.io..Path._methods*, %shadow.io..Path._methods** %methodsRef
	%toStringRef = getelementptr %shadow.io..Path._methods, %shadow.io..Path._methods* %methods, i32 0, i32 3
	%toString = load %shadow.standard..String* (%shadow.io..Path*)*, %shadow.standard..String* (%shadow.io..Path*)** %toStringRef
	%string = tail call %shadow.standard..String* %toString(%shadow.io..Path* %path)
	%arrayRef = getelementptr inbounds %shadow.standard..String, %shadow.standard..String* %string, i32 0, i32 3
	%array = load %shadow.standard..Array*, %shadow.standard..Array** %arrayRef
	%data = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1		
	%bytes = bitcast %shadow.standard..Array* %data to i8*
	%size = call %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) nounwind
	%sizeWithNull = add nuw i64 %size, 1
	%sizeHack = inttoptr i64 %size to %size_t	
	%cstring = tail call noalias i8* @malloc(%size_t %sizeHack)
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %cstring, i8* %bytes, i64 %size, i32 1, i1 0)
	%nullRef = getelementptr inbounds i8, i8* %cstring, i64 %size
	store i8 0, i8* %nullRef
	ret i8* %cstring
}

define void @shadow.io..File..exists.boolean(%shadow.io..File* %file, i1 %createOrDelete ) {
	tail call void @shadow.io..File..close(%shadow.io..File* %file)
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	br i1 %createOrDelete, label %_create, label %_delete
_create:
	%handle = tail call x86_stdcallcc i8* @CreateFileA(i8* %path, i32 shl (i32 1, i32 30), i32 7, i8* null, i32 1, i32 128, i8* null)
	tail call void @free(i8* %path)	
	%checkValid = call i1 @handleIsLegal(i8* %handle)	
	br i1 %checkValid, label %_createSuccess, label %_error
_createSuccess:	
	%handleAsLong = ptrtoint i8* %handle to i64
	%handleRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	store i64 %handleAsLong, i64* %handleRef
	ret void
_delete:
	%deleteCode = tail call x86_stdcallcc i32 @DeleteFileA(i8* %path)
	tail call void @free(i8* %path)
	%validDelete = icmp ne i32 %deleteCode, 0
	br i1 %validDelete, label %_deleteSuccess, label %_error
_deleteSuccess:
	ret void
_error:
	tail call void @throwIOException() noreturn
	unreachable
}
define i64 @shadow.io..File..position(%shadow.io..File*) {
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
define void @shadow.io..File..position.long(%shadow.io..File*, i64) {
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
define i64 @shadow.io..File..size(%shadow.io..File*) {
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
define void @shadow.io..File..size.long(%shadow.io..File*, i64) {
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
define %long @shadow.io..File..read.ubyte._A(%shadow.io..File* %file, %shadow.standard..Array* %array) {
_entry:
	%bytesReadRef = alloca i32
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%handle = inttoptr i64 %descriptorAsLong to i8*
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) nounwind
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
	%descriptorCheck = ptrtoint i8* %realHandle to i64
	%open = icmp sge i64 %descriptorCheck, 0
	br i1 %open, label %_close, label %_open
_close:
	tail call void @shadow.io..File..close(%shadow.io..File* %file)
	br label %_open
_open:
	; GENERIC_READ = 0x80000000, GENERIC_READ | GENERIC_WRITE = 0xC0000000
	%access = phi i32 [ shl (i32 1, i32 31), %_checkIfOpen ], [ shl (i32 3, i32 30), %_close ]
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	%newHandle = tail call x86_stdcallcc i8* @CreateFileA(i8* %path, i32 %access, i32 7, i8* null, i32 3, i32 128, i8* null)
	tail call void @free(i8* %path)
	%handleAsLong = ptrtoint i8* %newHandle to i64
	%handleRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	store i64 %handleAsLong, i64* %handleRef
	%checkValid = call i1 @handleIsLegal(i8* %newHandle)
	br i1 %checkValid, label %_read, label %_throw
_throw:	
	tail call void @throwIOException() noreturn
	unreachable
}

define %long @shadow.io..File..write.ubyte._A(%shadow.io..File* %file, %shadow.standard..Array* %array) {
_entry:
	%bytesWrittenRef = alloca i32
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%handle = inttoptr i64 %descriptorAsLong to i8*
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) nounwind
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
	tail call void @shadow.io..File..close(%shadow.io..File* %file)
	br label %_open
_open:
	; GENERIC_WRITE = 0x40000000, GENERIC_READ | GENERIC_WRITE = 0xC0000000
	%access = phi i32 [ shl (i32 1, i32 30), %_checkIfOpen ], [ shl (i32 3, i32 30), %_close ]
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	%newHandle = tail call x86_stdcallcc i8* @CreateFileA(i8* %path, i32 %access, i32 7, i8* null, i32 3, i32 0, i8* null)
	tail call void @free(i8* %path)
	%handleAsLong = ptrtoint i8* %newHandle to i64
	store i64 %handleAsLong, i64* %descriptorRef
	%checkValid = call i1 @handleIsLegal(i8* %newHandle)
	br i1 %checkValid, label %_write, label %_throw
_throw:
	tail call void @throwIOException() noreturn
	unreachable
}