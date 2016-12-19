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
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type { %uint, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %uint, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %uint, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, %int, %int, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] } }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %uint, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%uint, %byte}*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %uint, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %uint, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods

%shadow.standard..ClassSet_methods = type opaque
;%shadow.standard..ClassSet = type { %uint, %shadow.standard..Class*, %shadow.standard..ClassSet_methods* , { { %uint, %shadow.standard..ClassSet.Node* }*, [1 x %int] }, %float, %int, %int, %int }
%shadow.standard..ClassSet = type opaque

@shadow.standard..Array_methods = external constant %shadow.standard..MethodTable
@shadow.standard..Array_class = external constant %shadow.standard..Class
@int_A1_class = external constant %shadow.standard..Class
@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..int_methods = external constant %shadow.standard..MethodTable

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %uint %elements)

define %shadow.standard..Class* @shadow.standard..ClassSet_MgetIntArrayClass(%shadow.standard..ClassSet*) alwaysinline {
	ret %shadow.standard..Class* @int_A1_class
}

define %shadow.standard..MethodTable* @shadow.standard..ClassSet_MgetIntMethodTable(%shadow.standard..ClassSet*) alwaysinline {
	ret %shadow.standard..MethodTable* @shadow.standard..int_methods
}

define {{%uint, %shadow.standard..MethodTable*}*, [1 x %int]} @shadow.standard..ClassSet_MgetEmptyMethodTableArray(%shadow.standard..ClassSet*) alwaysinline {
	ret {{%uint, %shadow.standard..MethodTable*}*, [1 x %int]} zeroinitializer
}


define {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*) {
	%memory = call {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Object_class, %uint 1)
	%memoryAsTables = bitcast {i32, %shadow.standard..Object*}* %memory to {i32, %shadow.standard..MethodTable*}*
	%array = getelementptr {i32, %shadow.standard..MethodTable*}, {i32, %shadow.standard..MethodTable*}* %memoryAsTables, i32 0, i32 1
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array
	%array1 = insertvalue {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} zeroinitializer, {i32, %shadow.standard..MethodTable*}* %memoryAsTables, 0
	%array2 = insertvalue {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} %array1, %int 1, 1, 0
	ret  {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} %array2
}


define {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*, %shadow.standard..MethodTable* ) {
	%memory = call {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Object_class, %uint 2)
	%memoryAsTables = bitcast {i32, %shadow.standard..Object*}* %memory to {i32, %shadow.standard..MethodTable*}*
	%array = getelementptr {i32, %shadow.standard..MethodTable*}, {i32, %shadow.standard..MethodTable*}* %memoryAsTables, i32 0, i32 1
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array
	%spot2 = getelementptr %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %array, %int 1
	store %shadow.standard..MethodTable* %2, %shadow.standard..MethodTable** %spot2	
	%array1 = insertvalue {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} zeroinitializer, {i32, %shadow.standard..MethodTable*}* %memoryAsTables, 0
	%array2 = insertvalue {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} %array1, %int 2, 1, 0
	ret  {{i32, %shadow.standard..MethodTable*}*, [1 x %int]} %array2
}


define {{i32, %shadow.standard..Class*}*, [1 x %int]} @shadow.standard..ClassSet_MgetEmptyClassArray(%shadow.standard..ClassSet*) alwaysinline {
	ret {{i32, %shadow.standard..Class*}*, [1 x %int]} zeroinitializer
}

; 1 class version
define {{i32, %shadow.standard..Class*}*, [1 x %int]} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*) {
	%memory = call {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %uint 1)
	%asClasses = bitcast {i32, %shadow.standard..Object*}* %memory to {i32, %shadow.standard..Class*}*	
	%array = getelementptr {i32, %shadow.standard..Class*}, {i32, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%array1 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} zeroinitializer, {i32, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} %array1, %int 1, 1, 0
	ret  {{i32, %shadow.standard..Class*}*, [1 x %int]} %array2
}



; 2 class version
define {{i32, %shadow.standard..Class*}*, [1 x %int]} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*) {
	%memory = call {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %uint 2)
	%asClasses = bitcast {i32, %shadow.standard..Object*}* %memory to {i32, %shadow.standard..Class*}*	
	%array = getelementptr {i32, %shadow.standard..Class*}, {i32, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%spot2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %spot2
	%array1 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} zeroinitializer, {i32, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} %array1, %int 2, 1, 0
	ret  {{i32, %shadow.standard..Class*}*, [1 x %int]} %array2
}

; 3 class version
define {{i32, %shadow.standard..Class*}*, [1 x %int]} @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*, %shadow.standard..Class* ) {
	%memory = call {i32, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %uint 3)
	%asClasses = bitcast {i32, %shadow.standard..Object*}* %memory to {i32, %shadow.standard..Class*}*	
	%array = getelementptr {i32, %shadow.standard..Class*}, {i32, %shadow.standard..Class*}* %asClasses, i32 0, i32 1
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array
	%spot2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %spot2
	%spot3 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array, %int 2
	store %shadow.standard..Class* %3, %shadow.standard..Class** %spot3
	%array1 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} zeroinitializer, {i32, %shadow.standard..Class*}* %asClasses, 0
	%array2 = insertvalue {{i32, %shadow.standard..Class*}*, [1 x %int]} %array1, %int 3, 1, 0
	ret  {{i32, %shadow.standard..Class*}*, [1 x %int]} %array2
}