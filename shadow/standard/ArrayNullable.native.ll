; shadow.standard@ArrayNullable native methods

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
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type { %shadow.standard..GenericClass* (%shadow.standard..GenericClass*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..GenericClass*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..GenericClass*)* }
%shadow.standard..GenericClass = type { %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int, { %shadow.standard..Object**, [1 x %int] } }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

%shadow.standard..ArrayNullable_methods = type { %shadow.standard..ArrayNullable* (%shadow.standard..ArrayNullable*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..ArrayNullable*)*, %int (%shadow.standard..ArrayNullable*)*, %shadow.standard..Class* (%shadow.standard..ArrayNullable*)*, %shadow.standard..Object* (%shadow.standard..ArrayNullable*, { %int*, [1 x %int] })*, void (%shadow.standard..ArrayNullable*, { %int*, [1 x %int] }, %shadow.standard..Object*)*, { %int*, [1 x %int] } (%shadow.standard..ArrayNullable*)*, %int (%shadow.standard..ArrayNullable*)* }
%shadow.standard..ArrayNullable = type { %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %shadow.standard..Object*, { %int*, [1 x %int] } }
%shadow.standard..IteratorNullable_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..Array_methods = type { %shadow.standard..Array* (%shadow.standard..Array*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Array*)*, %int (%shadow.standard..Array*)*, %shadow.standard..Class* (%shadow.standard..Array*)*, %shadow.standard..Object* (%shadow.standard..Array*, { %int*, [1 x %int] })*, void (%shadow.standard..Array*, { %int*, [1 x %int] }, %shadow.standard..Object*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..Array*)*, { %int*, [1 x %int] } (%shadow.standard..Array*)*, %int (%shadow.standard..Array*)*, %shadow.standard..Array* (%shadow.standard..Array*, %int, %int)* }
%shadow.standard..Array = type { %shadow.standard..Class*, %shadow.standard..Array_methods* , %shadow.standard..Object*, { %int*, [1 x %int] } }

;aliases are in Array.native.ll

declare i32 @__computeIndex(%shadow.standard..Array*, { i32*, [1 x i32] })	
define %shadow.standard..Object* @shadow.standard..ArrayNullable_Mindex_int_A1(%shadow.standard..Array*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @__computeIndex(%shadow.standard..Array* %0, { i32*, [1 x i32] } %1)	
	%4 = call %shadow.standard..Object* @shadow.standard..ArrayNullable_Mindex_int(%shadow.standard..Array* %0, i32 %3)
	ret %shadow.standard..Object* %4
}

declare %shadow.standard..Object* @__arrayLoad(%shadow.standard..Object**, i32, %shadow.standard..Class*, %shadow.standard..Object*, %boolean)
define %shadow.standard..Object* @shadow.standard..ArrayNullable_Mindex_int(%shadow.standard..Array*, i32 ) {
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
	
	%result = call %shadow.standard..Object* @__arrayLoad(%shadow.standard..Object** %array, i32 %1, %shadow.standard..Class* %baseClass, %shadow.standard..Object* %methods, %boolean 1)
	ret %shadow.standard..Object* %result
}