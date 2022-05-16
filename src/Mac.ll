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
;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console..print_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console..printLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console..printLine(%shadow.io..Console*)
;declare %shadow.io..Console* @shadow.io..Console..debugPrint.int(%shadow.io..Console*, %int)

declare %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object*, %shadow.standard..Array*)
declare %shadow.io..IOException* @shadow.io..IOException..create_shadow.standard..String(%shadow.standard..Object*, %shadow.standard..String*)
declare %shadow.io..IOException* @shadow.io..IOException..create(%shadow.standard..Object*)
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object._methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)
declare %long @shadow.standard..Array..sizeLong(%shadow.standard..Array* %array) alwaysinline nounwind

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i32, i1)

; Many of these functions use i8* as %size_t as a hack to avoid having separate 32- and 64-bit code
; pointer size matches architecture size and also size_t
declare noalias i8* @malloc(%size_t nocapture) nounwind
declare void @free(i8*) nounwind

declare i32* @__error() nounwind readnone
declare i32 @strerror_r(i32, i8*, i32) nounwind
declare %size_t @strlen(i8*) nounwind readonly
declare i8* @strncpy(i8*, i8* nocapture, %size_t) nounwind
declare i32 @access(i8* nocapture, i32)
declare i64 @lseek(i32, i64, i32)
declare i32 @truncate(i8* nocapture, i64)
declare i32 @ftruncate(i32, i64)
declare i32 @open(i8* nocapture, i32, ...)
declare %size_t @read(i32, i8* nocapture, %size_t)
declare %size_t @write(i32, i8* nocapture, %size_t)
declare i32 @unlink(i8*)
declare i32 @close(i32)


define i32 @shadow.io..Path..separator(%shadow.io..Path*) alwaysinline {
	ret i32 47
}
