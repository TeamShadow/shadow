; shadow.standard@Class native methods

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

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*)*, %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }

%shadow.standard..ClassSet_methods = type opaque
%shadow.standard..ClassSet = type opaque

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

@shadow.standard..Array_methods = external constant %shadow.standard..Object
@shadow.standard..Array_class = external constant %shadow.standard..Class
@int_A1_class = external constant %shadow.standard..Class

declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class*, %int)

define %shadow.standard..Class* @shadow.standard..ClassSet_MgetIntArrayClass(%shadow.standard..ClassSet*) alwaysinline {
	ret %shadow.standard..Class* @int_A1_class
}

define {%shadow.standard..Object**, [1 x %int]} @shadow.standard..ClassSet_MgetEmptyObjectArray(%shadow.standard..ClassSet*) alwaysinline {
	ret {%shadow.standard..Object**, [1 x %int]} zeroinitializer
}

define {%shadow.standard..Object**, [1 x %int]} @shadow.standard..ClassSet_MmakeObjectArray_shadow.standard..Object_shadow.standard..Object_shadow.standard..Object_shadow.standard..Object(%shadow.standard..ClassSet*, %shadow.standard..Object*, %shadow.standard..Object*, %shadow.standard..Object*, %shadow.standard..Object* ) {
	%memory = call %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..Object_class, i32 4)
	%pointer = bitcast %shadow.standard..Object* %memory to %shadow.standard..Object**
	store %shadow.standard..Object* %1, %shadow.standard..Object** %pointer
	%spot2 = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %pointer, %int 1
	store %shadow.standard..Object* %2, %shadow.standard..Object** %spot2
	%spot3 = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %pointer, %int 2
	store %shadow.standard..Object* %3, %shadow.standard..Object** %spot3
	%spot4 = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %pointer, %int 3
	store %shadow.standard..Object* %4, %shadow.standard..Object** %spot4
	%array1 = insertvalue {%shadow.standard..Object**, [1 x %int]} zeroinitializer, %shadow.standard..Object** %pointer, 0
	%array2 = insertvalue {%shadow.standard..Object**, [1 x %int]} %array1, %int 4, 1, 0
	ret  {%shadow.standard..Object**, [1 x %int]} %array2
}


define {%shadow.standard..Class**, [1 x %int]} @shadow.standard..ClassSet_MgetEmptyClassArray(%shadow.standard..ClassSet*) alwaysinline {
	ret {%shadow.standard..Class**, [1 x %int]} zeroinitializer
}

define {%shadow.standard..Class**, [1 x %int]} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*, %shadow.standard..Class* ) {
	%memory = call %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..Class_class, i32 3)
	%pointer = bitcast %shadow.standard..Object* %memory to %shadow.standard..Class**
	store %shadow.standard..Class* %1, %shadow.standard..Class** %pointer
	%spot2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %pointer, %int 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %spot2
	%spot3 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %pointer, %int 2
	store %shadow.standard..Class* %3, %shadow.standard..Class** %spot3
	%array1 = insertvalue {%shadow.standard..Class**, [1 x %int]} zeroinitializer, %shadow.standard..Class** %pointer, 0
	%array2 = insertvalue {%shadow.standard..Class**, [1 x %int]} %array1, %int 3, 1, 0
	ret  {%shadow.standard..Class**, [1 x %int]} %array2
}