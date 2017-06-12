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
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %long }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type opaque


%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class
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
@shadow.standard..MethodTable_A_class = external constant %shadow.standard..Class
@shadow.standard..Class_A_class = external constant %shadow.standard..Class
@shadow.standard..long_methods = external constant %shadow.standard..MethodTable

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)

define %shadow.standard..MethodTable* @shadow.standard..ClassSet_MgetLongMethodTable(%shadow.standard..ClassSet*) alwaysinline {
	ret %shadow.standard..MethodTable* @shadow.standard..long_methods
}

define %shadow.standard..Array* @shadow.standard..ClassSet_MgetEmptyMethodTableArray(%shadow.standard..ClassSet*) alwaysinline {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..MethodTable_A_class, %ulong 0, %boolean false)
	ret %shadow.standard..Array* %array
}

; 1 method table version
define %shadow.standard..Array* @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*) {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..MethodTable_A_class, %ulong 1, %boolean false)
	%memory = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%array1 = bitcast %shadow.standard..Array* %memory to %shadow.standard..MethodTable**	
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array1	
	ret  %shadow.standard..Array* %array
}

; 2 method table version
define %shadow.standard..Array* @shadow.standard..ClassSet_MmakeMethodTableArray_shadow.standard..MethodTable_shadow.standard..MethodTable(%shadow.standard..ClassSet*, %shadow.standard..MethodTable*, %shadow.standard..MethodTable* ) {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..MethodTable_A_class, %ulong 2, %boolean false)
	%memory = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%array1 = bitcast %shadow.standard..Array* %memory to %shadow.standard..MethodTable**	
	store %shadow.standard..MethodTable* %1, %shadow.standard..MethodTable** %array1
	%array2 = getelementptr %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %array1, i32 1
	store %shadow.standard..MethodTable* %2, %shadow.standard..MethodTable** %array2
	ret  %shadow.standard..Array* %array
}

define %shadow.standard..Array* @shadow.standard..ClassSet_MgetEmptyClassArray(%shadow.standard..ClassSet*) alwaysinline {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_A_class, %ulong 0, %boolean false)
	ret %shadow.standard..Array* %array
}

; 1 class version
define %shadow.standard..Array* @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*) {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_A_class, %ulong 1, %boolean false)
	%memory = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%array1 = bitcast %shadow.standard..Array* %memory to %shadow.standard..Class**	
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array1
	ret  %shadow.standard..Array* %array
}

; 2 class version
define %shadow.standard..Array* @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*) {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_A_class, %ulong 2, %boolean false)
	%memory = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%array1 = bitcast %shadow.standard..Array* %memory to %shadow.standard..Class**	
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array1
	%array2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array1, i32 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %array2	
	ret  %shadow.standard..Array* %array
}

; 3 class version
define %shadow.standard..Array* @shadow.standard..ClassSet_MmakeClassArray_shadow.standard..Class_shadow.standard..Class_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..Class*, %shadow.standard..Class* ) {
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_A_class, %ulong 3, %boolean false)
	%memory = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%array1 = bitcast %shadow.standard..Array* %memory to %shadow.standard..Class**	
	store %shadow.standard..Class* %1, %shadow.standard..Class** %array1
	%array2 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array1, i32 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %array2	
	%array3 = getelementptr %shadow.standard..Class*, %shadow.standard..Class** %array2, i32 1
	store %shadow.standard..Class* %2, %shadow.standard..Class** %array3	
	ret  %shadow.standard..Array* %array
}