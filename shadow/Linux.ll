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
%shadow.io..Path = type { %ulong, %shadow.standard..Class*, %shadow.io..Path._methods* , {{%ulong, %shadow.standard..String*}*, %shadow.standard..Class*,%ulong} }

%shadow.standard..System = type opaque
%shadow.io..Console = type opaque

declare %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object*, %shadow.standard..Array*)
declare %shadow.io..IOException* @shadow.io..IOException..create_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException..create(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object._methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)
declare %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) alwaysinline nounwind

;declare %shadow.io..Console* @shadow.io..Console..debugPrint.int(%shadow.io..Console*, %int)
;declare %shadow.io..Console* @shadow.io..Console..printLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i32, i1)

; Many of these functions use i8* as %size_t as a hack to avoid having separate 32- and 64-bit code
; pointer size matches architecture size and also size_t
declare noalias i8* @malloc(%size_t nocapture) nounwind
declare void @free(i8*) nounwind

declare i32* @__errno_location() nounwind readnone
declare i8* @strerror_r(i32, i8*, i32) nounwind
declare %size_t @strlen(i8*) nounwind readonly
declare i8* @strncpy(i8*, i8* nocapture, %size_t) nounwind
declare i32 @access(i8* nocapture, i32)
declare i64 @lseek64(i32, i64, i32)
declare i32 @truncate64(i8* nocapture, i64)
declare i32 @ftruncate64(i32, i64)
declare i32 @open(i8* nocapture, i32, ...)
declare %size_t @read(i32, i8* nocapture, %size_t)
declare %size_t @write(i32, i8* nocapture, %size_t)
declare i32 @unlink(i8*)
declare i32 @close(i32)


define private void @throwIOException() noreturn {
	%buffer = alloca i8, i32 256
	%errorRef = tail call i32* @__errno_location() nounwind readnone
	%error = load i32, i32* %errorRef
	%message = call i8* @strerror_r(i32 %error, i8* %buffer, i32 256) nounwind
	%length = tail call %size_t @strlen(i8* %message)
	%lengthLong = ptrtoint %size_t %length to %ulong
	%array = call noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @ubyte._A.class, %ulong %lengthLong, %boolean false) nounwind
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%dataAsChars = bitcast %shadow.standard..Array* %data to i8*
	call i8* @strncpy(i8* %dataAsChars, i8* nocapture %message, %size_t %length) nounwind
	%stringAsObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String.class, %shadow.standard..Object._methods* bitcast(%shadow.standard..String._methods* @shadow.standard..String._methods to %shadow.standard..Object._methods*))
	%string = call %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object* %stringAsObj, %shadow.standard..Array* %array)
	%exceptionAsObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..IOException.class, %shadow.standard..Object._methods* bitcast(%shadow.io..IOException._methods* @shadow.io..IOException._methods to %shadow.standard..Object._methods*))
	%exception = call %shadow.io..IOException* @shadow.io..IOException..create_shadow.standard..String(%shadow.standard..Object* %exceptionAsObj, %shadow.standard..String* %string)
	call void @__shadow_throw(%shadow.standard..Object* %exceptionAsObj) noreturn
    unreachable
}

define i32 @shadow.io..Path..separator(%shadow.io..Path*) alwaysinline {
	ret i32 47
}

declare void @shadow.io..File..close(%shadow.io..File*)

define private i8* @filepath(%shadow.io..File* %file) {
	%pathRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 4
	%path = load %shadow.io..Path*, %shadow.io..Path** %pathRef
	%methodsRef = getelementptr %shadow.io..Path, %shadow.io..Path* %path, i32 0, i32 2
	%methods = load %shadow.io..Path._methods*, %shadow.io..Path._methods** %methodsRef
	%toStringRef = getelementptr %shadow.io..Path._methods, %shadow.io..Path._methods* %methods, i32 0, i32 3
	%toString = load %shadow.standard..String* (%shadow.io..Path*)*, %shadow.standard..String* (%shadow.io..Path*)** %toStringRef
	%string = call %shadow.standard..String* %toString(%shadow.io..Path* %path)
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

define void @shadow.io..File..exists.boolean(%shadow.io..File* %file, i1 %createOrDelete) {
	call void @shadow.io..File..close(%shadow.io..File* %file)
	%path = call i8* @filepath(%shadow.io..File* %file)
	br i1 %createOrDelete, label %_create, label %_delete
_create:
  ; O_CREAT | O_RDWR = 66, then permissions 664 is 436 in decimal
	%descriptor = call i32 (i8*, i32, ...) @open(i8* %path, i32 66, i32 436)
	call void @free(i8* %path)
	%validCreate = icmp sge i32 %descriptor, 0
	br i1 %validCreate, label %_createSuccess, label %_error
_createSuccess:
	%longDescriptor = zext i32 %descriptor to i64
	%handleRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	store i64 %longDescriptor, i64* %handleRef
	ret void
_delete:
	%unlinkCode = call i32 @unlink(i8* %path)
	call void @free(i8* %path)
	%validDelete = icmp sge i32 %unlinkCode, 0
	br i1 %validDelete, label %_deleteSuccess, label %_error
_deleteSuccess:
	ret void
_error:
	call void @throwIOException() noreturn
	unreachable
}
define i64 @shadow.io..File..position(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%3 = load i64, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = icmp sge i64 %5, 0
	br i1 %6, label %7, label %8
	ret i64 %5
	%9 = icmp slt i64 %3, 0
	br i1 %9, label %10, label %11
	ret i64 0
	call void @throwIOException() noreturn
	unreachable
}
define void @shadow.io..File..position.long(%shadow.io..File*, i64) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i64 @lseek64(i32 %5, i64 %1, i32 0)
	ret void
}
define i64 @shadow.io..File..size(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%3 = load i64, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek64(i32 %4, i64 0, i32 1)
	%6 = tail call i64 @lseek64(i32 %4, i64 0, i32 2)
	%7 = tail call i64 @lseek64(i32 %4, i64 %5, i32 0)
	ret i64 %6
}
define void @shadow.io..File..size.long(%shadow.io..File*, i64) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i32 @ftruncate64(i32 %5, i64 %1)
	%7 = icmp sge i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	%10 = icmp slt i32 %5, 0
	br i1 %10, label %11, label %15
	%12 = tail call i8* @filepath(%shadow.io..File* %0)
	%13 = tail call i32 @truncate64(i8* %12, i64 %1)
	tail call void @free(i8* %12)
	%14 = icmp sge i32 %13, 0
	br i1 %14, label %8, label %15
	tail call void @throwIOException() noreturn
	unreachable
}


define %long @shadow.io..File..read.ubyte._A(%shadow.io..File* %file, %shadow.standard..Array* %array) {
_entry:
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%descriptor = trunc i64 %descriptorAsLong to i32
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) nounwind
	%length = inttoptr %ulong %longLength to %size_t
	br label %_read
_read:
	%realDescriptor = phi i32 [ %descriptor, %_entry ], [ %newDescriptor, %_open ]
	%resultHack = tail call %size_t @read(i32 %realDescriptor, i8* %buffer, %size_t %length)
	%result = ptrtoint %size_t %resultHack to %long
	%checkError = icmp sge %long %result, 0
	br i1 %checkError, label %_success, label %_error
_success:
	ret %long %result
_error:
	%errorRef = tail call i32* @__errno_location()
	%error = load i32, i32* %errorRef
	; if the error is EBADF (9), then the file wasn't opened for reading
	%checkIfBad = icmp eq i32 %error, 9
	br i1 %checkIfBad, label %_checkIfOpen, label %_throw
_checkIfOpen:
	; if already open, must be for writing, close and then open again for reading and writing
	%open = icmp sge i32 %realDescriptor, 0
	br i1 %open, label %_close, label %_open
_close:
	tail call void @shadow.io..File..close(%shadow.io..File* %file)
	br label %_open
_open:
	; 0 = O_RDONLY, 2 = O_RDWR
	%mode = phi i32 [ 0, %_checkIfOpen ], [ 2, %_close ]
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	%newDescriptor = tail call i32 (i8*, i32, ...) @open(i8* %path, i32 %mode)
	tail call void @free(i8* %path)
	%newDescriptorAsLong = sext i32 %newDescriptor to i64
	store i64 %newDescriptorAsLong, i64* %descriptorRef
	%checkValid = icmp sge i64 %newDescriptorAsLong, 0
	br i1 %checkValid, label %_read, label %_throw
_throw:
	tail call void @throwIOException() noreturn
	unreachable
}


define %long @shadow.io..File..write.ubyte._A(%shadow.io..File* %file, %shadow.standard..Array* %array ) {
_entry:
	%descriptorRef = getelementptr inbounds %shadow.io..File, %shadow.io..File* %file, i32 0, i32 3
	%descriptorAsLong = load i64, i64* %descriptorRef
	%descriptor = trunc i64 %descriptorAsLong to i32
	%data = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%buffer = bitcast %shadow.standard..Array* %data to i8*
	%longLength = call %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) nounwind
	%length = inttoptr %ulong %longLength to %size_t
	br label %_write
_write:
	%realDescriptor = phi i32 [ %descriptor, %_entry ], [ %newDescriptor, %_updateDescriptor ]
	%resultHack = tail call %size_t @write(i32 %realDescriptor, i8* %buffer, %size_t %length)
	%result = ptrtoint %size_t %resultHack to %long
	%checkError = icmp sge %long %result, 0
	br i1 %checkError, label %_success, label %_error
_success:
	ret %long %result
_error:
	%errorRef = tail call i32* @__errno_location()
	%error = load i32, i32* %errorRef
	; if the error is EBADF (9), then the file wasn't opened for writing
	%checkIfBad = icmp eq i32 %error, 9
	br i1 %checkIfBad, label %_checkIfOpen, label %_throw
_checkIfOpen:
	; if already open, must be for writing, close and then open again for reading and writing
	%open = icmp sge i32 %realDescriptor, 0
	%path = tail call i8* @filepath(%shadow.io..File* %file)
	br i1 %open, label %_reopen, label %_open
_reopen:
	tail call void @shadow.io..File..close(%shadow.io..File* %file)
	; 2 = O_RDWR
	%newDescriptor1 = tail call i32 (i8*, i32, ...) @open(i8* %path, i32 2)
	br label %_updateDescriptor
_open:
	; 1 = O_WRONLY, must combine with 64 to create, then permissions 664 is 436 in decimal
	%newDescriptor2 = tail call i32 (i8*, i32, ...) @open(i8* %path, i32 65, i32 436)
	br label %_updateDescriptor
_updateDescriptor:
	%newDescriptor = phi i32 [ %newDescriptor1, %_reopen ], [ %newDescriptor2, %_open]
	tail call void @free(i8* %path)
	%newDescriptorAsLong = sext i32 %newDescriptor to i64
	store i64 %newDescriptorAsLong, i64* %descriptorRef
	%checkValid = icmp sge i64 %newDescriptorAsLong, 0
	br i1 %checkValid, label %_write, label %_throw
_throw:
	tail call void @throwIOException() noreturn
	unreachable
}
