; shadow.standard@Array native methods

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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type { %shadow.standard..GenericClass* (%shadow.standard..GenericClass*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..GenericClass*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..GenericClass*)* }
%shadow.standard..GenericClass = type { %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int, { %shadow.standard..Object**, [1 x %int] } }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..ClassSet_methods = type { %shadow.standard..ClassSet* (%shadow.standard..ClassSet*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..ClassSet*, %shadow.standard..String*, %shadow.standard..Class*, %int)*, %shadow.standard..GenericClass* (%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Class**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] })*, void (%shadow.standard..ClassSet*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..ClassSet*, %shadow.standard..String*, %shadow.standard..Class*, %int)*, %shadow.standard..GenericClass* (%shadow.standard..ClassSet*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] })*, %shadow.standard..GenericClass* (%shadow.standard..ClassSet*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %boolean)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, void (%shadow.standard..ClassSet*, %shadow.standard..Class*, %boolean)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..ClassSet*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, void (%shadow.standard..ClassSet*, %int)*, %int (%shadow.standard..ClassSet*)* }
%shadow.standard..ClassSet = type { %shadow.standard..Class*, %shadow.standard..ClassSet_methods* , { %shadow.standard..ClassSet.Node**, [1 x %int] }, %float, %int, %int, %int }
%shadow.standard..ClassSet.Node_methods = type { %shadow.standard..ClassSet.Node* (%shadow.standard..ClassSet.Node*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %int (%shadow.standard..ClassSet.Node*)*, %shadow.standard..ClassSet.Node* (%shadow.standard..ClassSet.Node*)*, void (%shadow.standard..ClassSet.Node*, %shadow.standard..ClassSet.Node*)*, %shadow.standard..Class* (%shadow.standard..ClassSet.Node*)* }
%shadow.standard..ClassSet.Node = type { %shadow.standard..Class*, %shadow.standard..ClassSet.Node_methods* , %shadow.standard..ClassSet*, %shadow.standard..ClassSet.Node*, %shadow.standard..Class*, %int }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*)*, %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..IndexOutOfBoundsException_methods = type { %shadow.standard..IndexOutOfBoundsException* (%shadow.standard..IndexOutOfBoundsException*)*, %shadow.standard..IndexOutOfBoundsException* (%shadow.standard..IndexOutOfBoundsException*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..IndexOutOfBoundsException = type { %shadow.standard..Class*, %shadow.standard..IndexOutOfBoundsException_methods* , %shadow.standard..String* }

@shadow.standard..IndexOutOfBoundsException_class = external constant %shadow.standard..Class
@shadow.standard..IndexOutOfBoundsException_methods = external constant %shadow.standard..IndexOutOfBoundsException_methods

%shadow.standard..Array_methods = type { %shadow.standard..Array* (%shadow.standard..Array*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Array*)*, %int (%shadow.standard..Array*)*, %shadow.standard..Class* (%shadow.standard..Array*)*, %shadow.standard..Object* (%shadow.standard..Array*, { %int*, [1 x %int] })*, void (%shadow.standard..Array*, { %int*, [1 x %int] }, %shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Array*, %int)*, void (%shadow.standard..Array*, %int, %shadow.standard..Object*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..Array*)*, { %int*, [1 x %int] } (%shadow.standard..Array*)*, %int (%shadow.standard..Array*)*, %shadow.standard..Array* (%shadow.standard..Array*, %int, %int)* }
%shadow.standard..Array = type { %shadow.standard..Class*, %shadow.standard..Array_methods* , %shadow.standard..Object*, { %int*, [1 x %int] } }
%shadow.standard..ArrayNullable_methods = type { %shadow.standard..ArrayNullable* (%shadow.standard..ArrayNullable*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..ArrayNullable*)*, %int (%shadow.standard..ArrayNullable*)*, %shadow.standard..Class* (%shadow.standard..ArrayNullable*)*, %shadow.standard..Object* (%shadow.standard..ArrayNullable*, { %int*, [1 x %int] })*, void (%shadow.standard..ArrayNullable*, { %int*, [1 x %int] }, %shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..ArrayNullable*, %int)*, void (%shadow.standard..ArrayNullable*, %int, %shadow.standard..Object*)*, { %shadow.standard..IteratorNullable_methods*, %shadow.standard..Object* } (%shadow.standard..ArrayNullable*)*, { %int*, [1 x %int] } (%shadow.standard..ArrayNullable*)*, %int (%shadow.standard..ArrayNullable*)*, %shadow.standard..Array* (%shadow.standard..ArrayNullable*, %int, %int)* }
%shadow.standard..ArrayNullable = type { %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %shadow.standard..Object*, { %int*, [1 x %int] } }
%shadow.standard..IteratorNullable_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..int_class = external constant %shadow.standard..Class
@shadow.standard..Array_class = external constant %shadow.standard..Class
@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods
@shadow.standard..ArrayNullable_class = external constant %shadow.standard..Class
@shadow.standard..ArrayNullable_methods = external constant %shadow.standard..ArrayNullable_methods

declare %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* returned, { %int*, [1 x %int] }, %shadow.standard..Object*)
declare %int @shadow.standard..Array_Msize(%shadow.standard..Array*)

declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class*, %shadow.standard..Object_methods*)
declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class*, %int)
declare %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object*)
declare %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)
declare %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Object_A1_boolean(%shadow.standard..ClassSet*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %boolean)
declare %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Object_A1_int_int(%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)

@_genericSet = external global %shadow.standard..ClassSet*

; aliases used by ArrayNullable
@shadow.standard..ArrayNullable_Mcreate_int_A1 = alias %shadow.standard..Array* (%shadow.standard..Object*, { %int*, [1 x %int] })* @shadow.standard..Array_Mcreate_int_A1
@shadow.standard..ArrayNullable_Mdimensions = alias %int (%shadow.standard..Array*)* @shadow.standard..Array_Mdimensions
@shadow.standard..ArrayNullable_Msubarray_int_int = alias %shadow.standard..Array* (%shadow.standard..Array*, %int, %int)* @shadow.standard..Array_Msubarray_int_int
@shadow.standard..ArrayNullable_Mindex_int_A1_T = alias void (%shadow.standard..Array*, { %int*, [1 x %int] }, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_int_A1_T
@shadow.standard..ArrayNullable_Mindex_int_T = alias void (%shadow.standard..Array*, %int, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_int_T
@shadow.standard..ArrayNullable_MgetBaseClass = alias %shadow.standard..Class* (%shadow.standard..Array*)* @shadow.standard..Array_MgetBaseClass

declare void @__shadow_throw(%shadow.standard..Object*) noreturn

define i32 @__computeIndex(%shadow.standard..Array*, { i32*, [1 x i32] }) alwaysinline {	
	%3 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 3	
	%4 = load { i32*, [1 x i32] }, { i32*, [1 x i32] }* %3
	%5 = extractvalue { i32*, [1 x i32] } %1, 0
	%6 = extractvalue { i32*, [1 x i32] } %1, 1, 0
	%7 = extractvalue { i32*, [1 x i32] } %4, 0
	%8 = extractvalue { i32*, [1 x i32] } %4, 1, 0
	%9 = icmp eq i32 %6, %8
	br i1 %9, label %10, label %throw
	%11 = load i32, i32* %5
	%12 = load i32, i32* %7
	%13 = icmp ult i32 %11, %12
	br i1 %13, label %14, label %throw
	%15 = sub i32 %6, 1
	%16 = icmp ne i32 %15, 0
	br i1 %16, label %17, label %32
	%18 = phi i32* [ %5, %14 ], [ %22, %27 ]
	%19 = phi i32* [ %7, %14 ], [ %23, %27 ]
	%20 = phi i32 [ %11, %14 ], [ %29, %27 ]
	%21 = phi i32 [ %15, %14 ], [ %30, %27 ]
	%22 = getelementptr inbounds i32, i32* %18, i32 1
	%23 = getelementptr inbounds i32, i32* %19, i32 1
	%24 = load i32, i32* %22
	%25 = load i32, i32* %23
	%26 = icmp ult i32 %24, %25
	br i1 %26, label %27, label %throw
	%28 = mul i32 %20, %25
	%29 = add i32 %28, %24
	%30 = sub i32 %21, 1
	%31 = icmp ne i32 %30, 0
	br i1 %31, label %17, label %32
	%33 = phi i32 [ %11, %14 ], [ %29, %27 ]
	ret i32 %33
throw:
	%ex.obj = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn	
	unreachable
}

define %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1(%shadow.standard..Object* returned, { %int*, [1 x %int] }) {
    %this = alloca %shadow.standard..Array*
    %lengths = alloca { %int*, [1 x %int] }
    %3 = bitcast %shadow.standard..Object* %0 to %shadow.standard..Array*
    store %shadow.standard..Array* %3, %shadow.standard..Array** %this
    store { %int*, [1 x %int] } %1, { %int*, [1 x %int] }* %lengths
    %4 = load %shadow.standard..Array*, %shadow.standard..Array** %this
    %5 = bitcast %shadow.standard..Array* %4 to %shadow.standard..Object*
    %6 = call %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object* %5)
    %7 = load %shadow.standard..Array*, %shadow.standard..Array** %this		
    %8 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %7, i32 0, i32 2
    store %shadow.standard..Object* null, %shadow.standard..Object** %8
    %9 = load %shadow.standard..Array*, %shadow.standard..Array** %this
    %10 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %9, i32 0, i32 3
    %11 = insertvalue { %int*, [1 x %int] } zeroinitializer, %int* null, 0
    store { %int*, [1 x %int] } %11, { %int*, [1 x %int] }* %10
    %12 = load %shadow.standard..Array*, %shadow.standard..Array** %this
    %13 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %12, i32 0, i32 3
    %14 = load { %int*, [1 x %int] }, { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %14, { %int*, [1 x %int] }* %13
    %15 = load %shadow.standard..Array*, %shadow.standard..Array** %this
    %16 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %15, i32 0, i32 2
    %17 = load %shadow.standard..Array*, %shadow.standard..Array** %this    
    %18 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %17, i32 0, i32 0
    %19 = load %shadow.standard..Class*, %shadow.standard..Class** %18
    %20 = bitcast %shadow.standard..Class* %19 to %shadow.standard..GenericClass*
	%21 = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %20, i32 0, i32 8	
	%22 = load { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] }* %21
	%23 = extractvalue { %shadow.standard..Object**, [1 x %int] } %22, 0
	%24 = load %shadow.standard..Object*, %shadow.standard..Object** %23
	%25 = bitcast %shadow.standard..Object* %24 to %shadow.standard..Class*	    
    %26 = call %int @shadow.standard..Array_Msize(%shadow.standard..Array* %17)
	%27 = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* %25, i32 %26)		
    store %shadow.standard..Object* %27, %shadow.standard..Object** %16
    %28 = bitcast %shadow.standard..Object* %0 to %shadow.standard..Array*
    ret %shadow.standard..Array* %28
}

define %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array*) {
    %this = alloca %shadow.standard..Array*
    store %shadow.standard..Array* %0, %shadow.standard..Array** %this
    %2 = load %shadow.standard..Array*, %shadow.standard..Array** %this
    %3 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %2, i32 0, i32 0
    %4 = load %shadow.standard..Class*, %shadow.standard..Class** %3
    %5 = bitcast %shadow.standard..Class* %4 to %shadow.standard..GenericClass*
    %6 = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %5, i32 0, i32 8	
	%7 = load { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] }* %6	
    %8 = extractvalue { %shadow.standard..Object**, [1 x %int] } %7, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%9 = load %shadow.standard..Object*, %shadow.standard..Object** %8
	%10 = bitcast %shadow.standard..Object* %9 to %shadow.standard..Class*
    ret %shadow.standard..Class* %10
}

define %shadow.standard..Object* @shadow.standard..Array_Mindex_int(%shadow.standard..Array*, i32 ) {
	; get array data
	%arrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 2
	%arrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %arrayRef	
	%array = bitcast %shadow.standard..Object* %arrayAsObj to %shadow.standard..Object**

	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 0
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%arrayClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*	
	
	; get base class
	%typeParametersArrayRef = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %shadow.standard..Object**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %typeParameters
	%baseClass = bitcast %shadow.standard..Object* %baseClassAsObj to %shadow.standard..Class*
	
	; get base class method table (might be base of base of base... methods if base class is an array class)
	%methodsRef = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %typeParameters, i32 1
	%methods = load %shadow.standard..Object*, %shadow.standard..Object** %methodsRef
	
	%result = call %shadow.standard..Object* @__arrayLoad(%shadow.standard..Object** %array, i32 %1, %shadow.standard..Class* %baseClass, %shadow.standard..Object* %methods, %boolean 0)
	ret %shadow.standard..Object* %result
}

; Should arrayLoad take an array of type parameters corresponding to class and method table?

define %shadow.standard..Object* @__arrayLoad(%shadow.standard..Object**, i32, %shadow.standard..Class*, %shadow.standard..Object*, %boolean) {
	; get generic class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %2, i32 0, i32 6
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	
	; if flag does not contain 2 (primitive), proceed with array check
	br i1 %notPrimitive, label %_checkArray, label %_primitive
_checkArray: 	
	; get array class flag	
	%arrayFlag = and i32 %flag, 8
	%notArray = icmp eq i32 %arrayFlag, 0
	
	; if flag does not contain 8, proceed with regular object
	br i1 %notArray, label %_object, label %_array

_object:		
	%elementRef = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %0, i32 %1
	%element = load %shadow.standard..Object*, %shadow.standard..Object** %elementRef
	ret %shadow.standard..Object* %element	

_primitive:
	; deal with primitive type
	; create new wrapper object
	%methodTable = bitcast %shadow.standard..Object* %3 to %shadow.standard..Object_methods*
	%wrapper = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* %2, %shadow.standard..Object_methods* %methodTable)
	
	; copy primitive value into new object
	%data = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %wrapper, i32 1
	%dataAsBytes = bitcast %shadow.standard..Object* %data to i8*
	
	%width = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %2)	
	%offset = mul i32 %1, %width		
	%arrayAsBytes = bitcast %shadow.standard..Object** %0 to i8*
	%primitiveElement = getelementptr inbounds i8, i8* %arrayAsBytes, i32 %offset
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %dataAsBytes, i8* %primitiveElement, i32 %width, i32 1, i1 0)
	ret %shadow.standard..Object* %wrapper

_array:	
	; create new Array wrapper
	; ugh, have to create Array<T> class or ArrayNullable<T>
	%baseClassRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %2, i32 0, i32 3
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef
	
	br i1 %4, label %_null, label %_notNull
_null:
	%nullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..ArrayNullable_class , i32 0, i32 2
	%nullName = load %shadow.standard..String*, %shadow.standard..String** %nullNameRef
	%nullMethods = bitcast %shadow.standard..ArrayNullable_methods* @shadow.standard..ArrayNullable_methods to %shadow.standard..Object_methods*
	br label %_name	
_notNull:
	%notNullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..Array_class , i32 0, i32 2
	%notNullName = load %shadow.standard..String*, %shadow.standard..String** %notNullNameRef
	%notNullMethods = bitcast %shadow.standard..Array_methods* @shadow.standard..Array_methods to %shadow.standard..Object_methods*
	br label %_name
_name:
	%arrayName = phi %shadow.standard..String* [ %nullName, %_null ], [ %notNullName, %_notNull ]
	%methods = phi %shadow.standard..Object_methods* [%nullMethods, %_null ], [ %notNullMethods, %_notNull ]
	
	%parametersArrayAsObj = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..Object_class, i32 2)
	%parametersArrayRef = bitcast %shadow.standard..Object* %parametersArrayAsObj to %shadow.standard..Object**
	%parametersClass = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %parametersArrayRef, i32 0
	%baseClassAsObj = bitcast %shadow.standard..Class* %baseClass to %shadow.standard..Object*
	store %shadow.standard..Object* %baseClassAsObj, %shadow.standard..Object** %parametersClass
	
	%parametersMethodTable = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %parametersArrayRef, i32 1
	store %shadow.standard..Object* %3, %shadow.standard..Object** %parametersMethodTable
	
	%uninitializedParameters = insertvalue { %shadow.standard..Object**, [1 x i32] } undef, %shadow.standard..Object** %parametersArrayRef, 0
	%parameters = insertvalue { %shadow.standard..Object**, [1 x i32] } %uninitializedParameters, i32 2, 1, 0
	
	%className = call %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Object_A1_int_int(%shadow.standard..Class* %2, %shadow.standard..String* %arrayName, { %shadow.standard..Object**, [1 x i32] } %parameters, %int 0, %int 2)
	%classSet = load %shadow.standard..ClassSet*, %shadow.standard..ClassSet** @_genericSet
	%arrayClass = call %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Object_A1_boolean(%shadow.standard..ClassSet* %classSet, %shadow.standard..String* %className, { %shadow.standard..Object**, [1 x i32] } %parameters, %boolean %4)
	%arrayClassAsClass = bitcast %shadow.standard..GenericClass* %arrayClass to %shadow.standard..Class*
	%arrayWrapper = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* %arrayClassAsClass, %shadow.standard..Object_methods* %methods)
	
	; get size member of array class, which is equal to dimensions
	%dimensionsRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %2, i32 0, i32 7
	%dimensions = load i32, i32* %dimensionsRef
	
	; allocate new array for dimensions
	%dimensionsArrayAsObj = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..int_class, i32 %dimensions)
	
	; get element width (which is an array)
	%arrayWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %2)		
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %shadow.standard..Object** %0 to i8*
	
	; get offset in bytes
	%arrayElement = getelementptr inbounds i8, i8* %arrayAsBytes2, i32 %arrayOffset
	
	; cast to object*** because an array is { Object**, [dimensions x int] }
	%arrayElementAsBytes = bitcast i8* %arrayElement to %shadow.standard..Object***
	%arrayDataRef = load %shadow.standard..Object**, %shadow.standard..Object*** %arrayElementAsBytes
	%arrayDataAsObj = bitcast %shadow.standard..Object** %arrayDataRef to %shadow.standard..Object*
	
	; skip past object pointer to dimensions
	%arraySizesAsObj = getelementptr inbounds %shadow.standard..Object**, %shadow.standard..Object*** %arrayElementAsBytes, i32 1
	%arraySizes = bitcast %shadow.standard..Object*** %arraySizesAsObj to i32*
	
	; copy dimensions into allocated space
	%dimensionsArray = bitcast %shadow.standard..Object* %dimensionsArrayAsObj to i32*
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %dimensionsArray, i32* %arraySizes, i32 %dimensions, i32 0, i1 0)
	
	; make lengths array
	%lengthsArray1 = insertvalue { i32*, [1 x i32] } undef, i32* %arraySizes, 0
	%lengthsArray2 = insertvalue { i32*, [1 x i32] } %lengthsArray1, i32 %dimensions, 1, 0
	
	; initialize Array<T> object
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* %arrayWrapper, { %int*, [1 x %int] } %lengthsArray2, %shadow.standard..Object* %arrayDataAsObj)
	
	; Object that just got initialized
	ret %shadow.standard..Object* %arrayWrapper
}


define %shadow.standard..Object* @shadow.standard..Array_Mindex_int_A1(%shadow.standard..Array*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @__computeIndex(%shadow.standard..Array* %0, { i32*, [1 x i32] } %1)	
	%4 = call %shadow.standard..Object* @shadow.standard..Array_Mindex_int(%shadow.standard..Array* %0, i32 %3)
	ret %shadow.standard..Object* %4
}

define void @shadow.standard..Array_Mindex_int_T(%shadow.standard..Array*, i32, %shadow.standard..Object*) {
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 0
	
	; get array class
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%arrayClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*		
	
	; get base class
	%typeParametersArrayRef = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %shadow.standard..Object**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %typeParameters
	%baseClass = bitcast %shadow.standard..Object* %baseClassAsObj to %shadow.standard..Class*
		
	%arrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 2
	%arrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %arrayRef
	%array = bitcast %shadow.standard..Object* %arrayAsObj to %shadow.standard..Object**
	
	call void @__arrayStore(%shadow.standard..Object** %array, i32 %1, %shadow.standard..Object* %2, %shadow.standard..Class* %baseClass)
	ret void
}

define void @__arrayStore(%shadow.standard..Object**, i32, %shadow.standard..Object*, %shadow.standard..Class*) {
	
	; get generic class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %3, i32 0, i32 6	
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	%notPrimitive = icmp eq i32 %primitiveFlag, 0	
	br i1 %notPrimitive, label %_checkArray, label %_primitive
	
_checkArray:	
	; check for array class
	%arrayFlag = and i32 %flag, 8	
	%notArray = icmp eq i32 %arrayFlag, 0
	br i1 %notArray, label %_object, label %_array

_object:
	%elementRef = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %0, i32 %1
	store %shadow.standard..Object* %2, %shadow.standard..Object** %elementRef
	ret void

_primitive:
	%primitiveWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %3)
	%offset = mul i32 %1, %primitiveWidth	
	%arrayAsBytes1 = bitcast %shadow.standard..Object** %0 to i8*
	%primitiveElement = getelementptr i8, i8* %arrayAsBytes1, i32 %offset
	%primitiveWrapper = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %2, i32 1
	%primitiveWrapperAsBytes = bitcast %shadow.standard..Object* %primitiveWrapper to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %primitiveElement, i8* %primitiveWrapperAsBytes, i32 %primitiveWidth, i32 1, i1 0)
	ret void

_array:	
	; get the location inside the current Array<T>
	%arrayWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %3)	
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %shadow.standard..Object** %0 to i8*
	%arrayElement = getelementptr i8, i8* %arrayAsBytes2, i32 %arrayOffset
		
	; get the array data from the input Array<T> (which it must be, since anything else isnt' going to have an ArrayType as a parameter)
	%input = bitcast %shadow.standard..Object* %2 to %shadow.standard..Array*
	%inputArray = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 2
	%inputArrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %inputArray
				
	; store it into the array
	%arrayElementAsObj = bitcast i8* %arrayElement to %shadow.standard..Object**
	store %shadow.standard..Object* %inputArrayAsObj, %shadow.standard..Object** %arrayElementAsObj	
	
	; store length data
	%dimensionsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 3, i32 1, i32 0
	%dimensions = load i32, i32* %dimensionsRef
	%lengthsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 3, i32 0
	%lengths = load i32*, i32** %lengthsRef
	
	; skip past the pointer to the length data
	%lengthDataAsObj = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %arrayElementAsObj, i32 1
	%lengthData = bitcast %shadow.standard..Object** %lengthDataAsObj to i32*
	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %lengthData, i32* %lengths, i32 %dimensions, i32 0, i1 0)	
	ret void
}

define void @shadow.standard..Array_Mindex_int_A1_T(%shadow.standard..Array*, { i32*, [1 x i32] }, %shadow.standard..Object*) alwaysinline {
	%4 = call i32 @__computeIndex(%shadow.standard..Array* %0, { i32*, [1 x i32] } %1)	
	call void @shadow.standard..Array_Mindex_int_T(%shadow.standard..Array* %0, i32 %4, %shadow.standard..Object* %2)
	ret void
}

define noalias %shadow.standard..Array* @shadow.standard..Array_Msubarray_int_int(%shadow.standard..Array*, i32, i32) {
	; check sizes first
	%size = call %int @shadow.standard..Array_Msize(%shadow.standard..Array* %0)
	%test1 = icmp ule i32 %2, %size
	br i1 %test1, label %_secondLessThanSize, label %throw
_secondLessThanSize:
	%test2 = icmp ule i32 %1, %2
	br i1 %test2, label %_firstLessThanSecond, label %throw
_firstLessThanSecond:
	; get class from original array
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 0
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef		
	
	; get method table from original array
	%methodsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 1		
	%methods = load %shadow.standard..Array_methods*, %shadow.standard..Array_methods** %methodsRef
	%objMethods = bitcast %shadow.standard..Array_methods* %methods to %shadow.standard..Object_methods*
	
	; allocate new array object
	%arrayObj = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %objMethods)
	
	; get base class
	%arrayClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*	
	%typeParametersArrayRef = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %shadow.standard..Object**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %typeParameters
	%baseClass = bitcast %shadow.standard..Object* %baseClassAsObj to %shadow.standard..Class*
		
	; create array for dimensions (only a single element, since the subarray will always be 1D)
	%dimensionsAsObj = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..int_class, i32 1)
	%dimensions = bitcast %shadow.standard..Object* %dimensionsAsObj to i32*
	
	; get difference between indexes
	%difference = sub i32 %2, %1
	
	; store difference (size) in the only position in the new array
	store i32 %difference, i32* %dimensions
	
	; put new dimension data into array
	%dimensionsArray1 = insertvalue { i32*, [1 x i32] } undef, i32* %dimensions, 0
	
	; put number of dimensions (1) into array
	%dimensionsArray2 = insertvalue { i32*, [1 x i32] } %dimensionsArray1, i32 1, 1, 0

	; get array data
	%arrayDataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 2
	%arrayData = load %shadow.standard..Object*, %shadow.standard..Object** %arrayDataRef
	
	; cast to char*
	%arrayDataAsChar = bitcast %shadow.standard..Object* %arrayData to i8*
	
	
	;allocate new real array
	%newArray = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* %baseClass, i32 %difference)
	%newArrayAsChar = bitcast %shadow.standard..Object* %newArray to i8*
	
	; get type parameter width
	%width = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)			
	
	; multiply starting point by width
	%offset = mul i32 %1, %width
	%arrayDataAtOffset = getelementptr i8, i8* %arrayDataAsChar, i32 %offset
	%total = mul i32 %difference, %width
	
	;copy data
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %newArrayAsChar, i8* %arrayDataAtOffset, i32 %total, i32 1, i1 0)
	
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* %arrayObj, { i32*, [1 x i32] } %dimensionsArray2, %shadow.standard..Object* %newArray)		
	ret %shadow.standard..Array* %initializedArray	
throw:
	%ex.obj = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn	
	unreachable
}

define %int @shadow.standard..Array_Mdimensions(%shadow.standard..Array*) {
    %2 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 3
    %3 = load { %int*, [1 x %int] } , { %int*, [1 x %int] }* %2
    %4 = extractvalue { %int*, [1 x %int] } %3, 1, 0
    ret %int %4
}