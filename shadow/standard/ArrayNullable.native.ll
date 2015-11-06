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
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CGenericClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %byte (%"_Pshadow_Pstandard_CString"*)*, %double (%"_Pshadow_Pstandard_CString"*)*, %float (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %long (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %short (%"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %ulong (%"_Pshadow_Pstandard_CString"*)*, %ushort (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CAddressMap_methods" = type opaque
%"_Pshadow_Pstandard_CAddressMap" = type opaque

@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"

%"_Pshadow_Pstandard_CArrayNullable_methods" = type { %"_Pshadow_Pstandard_CArrayNullable"* (%"_Pshadow_Pstandard_CArrayNullable"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArrayNullable"*)*, %int (%"_Pshadow_Pstandard_CArrayNullable"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArrayNullable"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArrayNullable"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArrayNullable"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArrayNullable"*)*, %int (%"_Pshadow_Pstandard_CArrayNullable"*)* }
%"_Pshadow_Pstandard_CArrayNullable" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArrayNullable_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
%"_Pshadow_Pstandard_CIteratorNullable_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CArray_methods" = type { %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }

;aliases are in Array.native.ll

declare i32 @__computeIndex(%"_Pshadow_Pstandard_CArray"*, { i32*, [1 x i32] })	
define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArrayNullable_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @__computeIndex(%"_Pshadow_Pstandard_CArray"* %0, { i32*, [1 x i32] } %1)	
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArrayNullable_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray* %0, i32 %3)
	ret %_Pshadow_Pstandard_CObject* %4
}

declare %_Pshadow_Pstandard_CObject* @__arrayLoad(%"_Pshadow_Pstandard_CObject"**, i32, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"*, %boolean)
define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArrayNullable_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32 ) {
	; get array data
	%arrayRef = getelementptr inbounds %_Pshadow_Pstandard_CArray, %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%arrayAsObj = load %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %arrayRef	
	%array = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to %_Pshadow_Pstandard_CObject**

	; get array class
	%classRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	%class = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %classRef
	%arrayClass = bitcast %"_Pshadow_Pstandard_CClass"* %class to %"_Pshadow_Pstandard_CGenericClass"*	
	
	; get base class
	%typeParametersArrayRef = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass", %"_Pshadow_Pstandard_CGenericClass"* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %typeParameters
	%baseClass = bitcast %"_Pshadow_Pstandard_CObject"* %baseClassAsObj to %"_Pshadow_Pstandard_CClass"*
	
	; get base class method table (might be base of base of base... methods if base class is an array class)
	%methodsRef = getelementptr inbounds %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %typeParameters, i32 1
	%methods = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %methodsRef
	
	%result = call %_Pshadow_Pstandard_CObject* @__arrayLoad(%"_Pshadow_Pstandard_CObject"** %array, i32 %1, %"_Pshadow_Pstandard_CClass"* %baseClass, %"_Pshadow_Pstandard_CObject"* %methods, %boolean 1)
	ret %_Pshadow_Pstandard_CObject* %result
}