; Mac specific native methods

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
@shadow.standard..byte_class = external constant %shadow.standard..Class

%shadow.io..File_methods = type opaque
%shadow.io..File = type { %ulong, %shadow.standard..Class*, %shadow.io..File_methods* , %long, %shadow.io..Path* }
%shadow.io..Path_methods = type { %shadow.io..Path* (%shadow.io..Path*, %shadow.standard..AddressMap*)*, void (%shadow.io..Path*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.io..Path*)*, %code (%shadow.io..Path*)* }
%shadow.io..Path = type { %ulong, %shadow.standard..Class*, %shadow.io..Path_methods* , {{%ulong, %shadow.standard..String*}*, [1 x %int] } }
%shadow.standard..System = type opaque
%shadow.io..Console = type opaque

declare %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A1(%shadow.standard..Object*, {{%ulong, %byte}*, [1 x i32]})
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException_Mcreate(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %uint %elements)

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)

declare noalias i8* @malloc(i32) nounwind
declare void @free(i8*) nounwind

declare i32* @__error() nounwind readnone
declare i8* @strerror_r(i32, i8*, i32) nounwind
declare i32 @strlen(i8*) nounwind readonly
declare i8* @strcpy(i8*, i8*) nounwind
declare i32 @access(i8* nocapture, i32)
declare i64 @lseek(i32, i64, i32)
declare i32 @truncate(i8* nocapture, i64)
declare i32 @ftruncate(i32, i64)
declare i32 @open(i8* nocapture, i32, ...)
declare i32 @read(i32, i8* nocapture, i32)
declare i32 @write(i32, i8* nocapture, i32)
declare i32 @unlink(i8*)
declare i32 @close(i32)

define private void @throwIOException() noreturn {
	%1 = alloca i8, i32 256
	%2 = tail call i32* @__error() nounwind readnone
	%3 = load i32, i32* %2
	%4 = call i8* @strerror_r(i32 %3, i8* %1, i32 256) nounwind
	%5 = tail call i32 @strlen(i8* %4)
	%6 = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..byte_class, %uint %5) nounwind
	%7 = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %6, i32 0, i32 1
	%8 = bitcast %shadow.standard..Object** %7 to i8*
	%9 = call i8* @strcpy(i8* %8, i8* %1) nounwind
	%10 = bitcast {%ulong, %shadow.standard..Object*}* %6 to {%ulong, %byte}*
	%11 = insertvalue {{%ulong, %byte}*, [1 x i32]} undef, {%ulong, %byte}* %10, 0
	%12 = insertvalue {{%ulong, %byte}*, [1 x i32]} %11, i32 %5, 1, 0
	%13 = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..String_methods* @shadow.standard..String_methods to %shadow.standard..Object_methods*))
	%14 = call %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A1(%shadow.standard..Object* %13, {{%ulong, %byte}*, [1 x i32]} %12)	
	%15 = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..IOException_class, %shadow.standard..Object_methods* bitcast(%shadow.io..IOException_methods* @shadow.io..IOException_methods to %shadow.standard..Object_methods*))
	%16 = call %shadow.io..IOException* @shadow.io..IOException_Mcreate_shadow.standard..String(%shadow.standard..Object* %15, %shadow.standard..String* %14)
	call void @__shadow_throw(%shadow.standard..Object* %15) noreturn
    unreachable
}

define i32 @shadow.io..Path_Mseparator(%shadow.io..Path*) {
	ret i32 47
}

declare void @shadow.io..File_Mclose(%shadow.io..File*)
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

define void @shadow.io..File_Mexists_boolean(%shadow.io..File*, i1) {
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	%3 = tail call i8* @filepath(%shadow.io..File* %0)
	br i1 %1, label %4, label %10
	%5 = tail call i32 (i8*, i32, ...) @open(i8* %3, i32 193, i32 420)
	tail call void @free(i8* %3)
	%6 = icmp sge i32 %5, 0
	br i1 %6, label %7, label %14
	%8 = sext i32 %5 to i64
	%9 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
define i64 @shadow.io..File_Mposition(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%3 = load i64, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek(i32 %4, i64 0, i32 1)
	%6 = icmp sge i64 %5, 0
	br i1 %6, label %7, label %8
	ret i64 %5
	%9 = icmp slt i64 %3, 0
	br i1 %9, label %10, label %11
	ret i64 0
	tail call void @throwIOException() noreturn
	unreachable
}
define void @shadow.io..File_Mposition_long(%shadow.io..File*, i64) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i64 @lseek(i32 %5, i64 %1, i32 0)
	ret void
}
define i64 @shadow.io..File_Msize(%shadow.io..File*) {
	%2 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%3 = load i64, i64* %2
	%4 = trunc i64 %3 to i32
	%5 = tail call i64 @lseek(i32 %4, i64 0, i32 1)
	%6 = tail call i64 @lseek(i32 %4, i64 0, i32 2)
	%7 = tail call i64 @lseek(i32 %4, i64 %5, i32 0)
	ret i64 %6
}
define void @shadow.io..File_Msize_long(%shadow.io..File*, i64) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
	%4 = load i64, i64* %3
	%5 = trunc i64 %4 to i32
	%6 = tail call i32 @ftruncate(i32 %5, i64 %1)
	%7 = icmp sge i32 %6, 0
	br i1 %7, label %8, label %9
	ret void
	%10 = icmp slt i32 %5, 0
	br i1 %10, label %11, label %15
	%12 = tail call i8* @filepath(%shadow.io..File* %0)
	%13 = tail call i32 @truncate(i8* %12, i64 %1)
	tail call void @free(i8* %12)
	%14 = icmp sge i32 %13, 0
	br i1 %14, label %8, label %15
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @shadow.io..File_Mread_byte_A1(%shadow.io..File*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%14 = tail call i32* @__error()
	%15 = load i32, i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	br label %20
	%21 = phi i32 [ 0, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%shadow.io..File* %0)
	%23 = tail call i32 (i8*, i32, ...) @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}
define i32 @shadow.io..File_Mwrite_byte_A1(%shadow.io..File*, { i8*, [1 x i32] }) {
	%3 = getelementptr inbounds %shadow.io..File, %shadow.io..File* %0, i32 0, i32 3
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
	%14 = tail call i32* @__error()
	%15 = load i32, i32* %14
	%16 = icmp eq i32 %15, 9
	br i1 %16, label %17, label %26
	%18 = icmp sge i64 %4, 0
	br i1 %18, label %19, label %20
	tail call void @shadow.io..File_Mclose(%shadow.io..File* %0)
	br label %20
	%21 = phi i32 [ 1, %17 ], [ 2, %19 ]
	%22 = tail call i8* @filepath(%shadow.io..File* %0)
	%23 = tail call i32 (i8*, i32, ...) @open(i8* %22, i32 %21)
	tail call void @free(i8* %22)
	%24 = sext i32 %23 to i64
	store i64 %24, i64* %3
	%25 = icmp sge i64 %24, 0
	br i1 %25, label %8, label %26
	tail call void @throwIOException() noreturn
	unreachable
}