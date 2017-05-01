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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, void (%shadow.standard..Object*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong} }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods

%shadow.standard..ClassSet_methods = type opaque
%shadow.standard..ClassSet = type opaque

@shadow.standard..Array_methods = external constant %shadow.standard..MethodTable
@shadow.standard..Array_class = external constant %shadow.standard..Class
@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..long_methods = external constant %shadow.standard..MethodTable

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %ulong %elements)

define %shadow.standard..MethodTable* @shadow.standard..ClassSet_MgetLongMethodTable(%shadow.standard..ClassSet*) alwaysinline {
	ret %shadow.standard..MethodTable* @shadow.standard..long_methods
}

define {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MgetEmptyMethodTableArray(%shadow.standard..ClassSet*) alwaysinline {
	%array = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} zeroinitializer, %shadow.standard..Class* @shadow.standard..MethodTable_class, 1
	ret {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array
}

define {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*) {
	%memory = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Object_class, %ulong 1)
	%memoryAsTables = bitcast {%ulong, %shadow.standard..Object*}* %memory to {%ulong, %shadow.standard..MethodTable*}*
	%array = getelementptr {%ulong, %shadow.standard..MethodTable*}, {%ulong, %shadow.standard..MethodTable*}* %memoryAsTables, i32 0, i32 1
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array
	%array1 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..MethodTable*}* %memoryAsTables, 0
	%array2 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array1, %shadow.standard..Class* @shadow.standard..MethodTable_class, 1
	%array3 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array2, %ulong 2, 2
	ret  {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array2
}


define {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*, %shadow.standard..MethodTable* ) {
	%memory = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Object_class, %ulong 2)
	%memoryAsTables = bitcast {%ulong, %shadow.standard..Object*}* %memory to {%ulong, %shadow.standard..MethodTable*}*
	%array = getelementptr {%ulong, %shadow.standard..MethodTable*}, {%ulong, %shadow.standard..MethodTable*}* %memoryAsTables, i32 0, i32 1
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array
	%spot2 = getelementptr %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %array, %int 1
	store %shadow.standard..MethodTable* %2, %shadow.standard..MethodTable** %spot2	
	%array1 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..MethodTable*}* %memoryAsTables, 0
	%array2 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array1, %shadow.standard..Class* @shadow.standard..MethodTable_class, 1
	%array3 = insertvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array2, %ulong 2, 2
	ret  {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %array2
}


define {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MgetEmptyClassArray(%shadow.standard..ClassSet*) alwaysinline {
	%array = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} zeroinitializer, %shadow.standard..Class* @shadow.standard..Class_class, 1
	ret {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array
}

; 1 class version
define {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*) {
	%memory = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %ulong 1)
	%asClasses = bitcast {%ulong, %shadow.standard..Object*}* %memory to {%ulong, %shadow.standard..Class*}*	
	%array = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%array1 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array1, %shadow.standard..Class* @shadow.standard..Class_class, 1
	%array3 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array2, %ulong 1, 2
	ret  {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array3
}

; 2 class version
define {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*) {
	%memory = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %ulong 2)
	%asClasses = bitcast {%ulong, %shadow.standard..Object*}* %memory to {%ulong, %shadow.standard..Class*}*	
	%array = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%spot2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %spot2
	%array1 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array1, %shadow.standard..Class* @shadow.standard..Class_class, 1
	%array3 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array2, %ulong 2, 2
	ret  {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array3
}

; 3 class version
define {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*, %shadow.standard..Class* ) {
	%memory = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %ulong 3)
	%asClasses = bitcast {%ulong, %shadow.standard..Object*}* %memory to {%ulong, %shadow.standard..Class*}*	
	%array = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%spot2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %spot2
	%spot3 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 2
	store %shadow.standard..Class* %3, %shadow.standard..Class** %spot3
	%array1 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array1, %shadow.standard..Class* @shadow.standard..Class_class, 1
	%array3 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array2, %ulong 3, 2
	ret  {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %array3
}