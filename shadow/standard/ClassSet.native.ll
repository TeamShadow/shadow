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
declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %byte (%"_Pshadow_Pstandard_CString"*)*, %double (%"_Pshadow_Pstandard_CString"*)*, %float (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %long (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %short (%"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %ulong (%"_Pshadow_Pstandard_CString"*)*, %ushort (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CAddressMap_methods" = type opaque
%"_Pshadow_Pstandard_CAddressMap" = type opaque

%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }

%"_Pshadow_Pstandard_CClassSet_methods" = type opaque
%"_Pshadow_Pstandard_CClassSet" = type opaque

@"_Pshadow_Pstandard_CObject_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"

@"_Pshadow_Pstandard_CArray_methods" = external constant %"_Pshadow_Pstandard_CObject"
@"_Pshadow_Pstandard_CArray_class" = external constant %"_Pshadow_Pstandard_CClass"

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClassSet_MgetArrayMethods"(%"_Pshadow_Pstandard_CClassSet"*) alwaysinline {
	ret %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_methods"
}

define %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClassSet_MgetIntArrayClass"(%"_Pshadow_Pstandard_CClassSet"*) alwaysinline {
	ret %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_class"
}

define {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} @"_Pshadow_Pstandard_CClassSet_MgetEmptyObjectArray"(%"_Pshadow_Pstandard_CClassSet"*) alwaysinline {
	ret {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} zeroinitializer
}

define {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} @"_Pshadow_Pstandard_CClassSet_MmakeObjectArray_Pshadow_Pstandard_CObject_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"* ) {
	%memory = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CObject_class", i32 2)
	%pointer = bitcast %"_Pshadow_Pstandard_CObject"* %memory to %"_Pshadow_Pstandard_CObject"**
	store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %pointer
	%spot2 = getelementptr %"_Pshadow_Pstandard_CObject"** %pointer, %int 1
	store %"_Pshadow_Pstandard_CObject"* %2, %"_Pshadow_Pstandard_CObject"** %spot2
	%array1 = insertvalue {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} zeroinitializer, %"_Pshadow_Pstandard_CObject"** %pointer, 0
	%array2 = insertvalue {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} %array1, %int 2, 1, 0
	ret  {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} %array2
}

define {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} @"_Pshadow_Pstandard_CClassSet_MmakeObjectArray_Pshadow_Pstandard_CObject_Pshadow_Pstandard_CObject_Pshadow_Pstandard_CObject_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"* ) {
	%memory = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CObject_class", i32 4)
	%pointer = bitcast %"_Pshadow_Pstandard_CObject"* %memory to %"_Pshadow_Pstandard_CObject"**
	store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %pointer
	%spot2 = getelementptr %"_Pshadow_Pstandard_CObject"** %pointer, %int 1
	store %"_Pshadow_Pstandard_CObject"* %2, %"_Pshadow_Pstandard_CObject"** %spot2
	%spot3 = getelementptr %"_Pshadow_Pstandard_CObject"** %pointer, %int 2
	store %"_Pshadow_Pstandard_CObject"* %3, %"_Pshadow_Pstandard_CObject"** %spot3
	%spot4 = getelementptr %"_Pshadow_Pstandard_CObject"** %pointer, %int 3
	store %"_Pshadow_Pstandard_CObject"* %4, %"_Pshadow_Pstandard_CObject"** %spot4
	%array1 = insertvalue {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} zeroinitializer, %"_Pshadow_Pstandard_CObject"** %pointer, 0
	%array2 = insertvalue {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} %array1, %int 2, 1, 0
	ret  {%"_Pshadow_Pstandard_CObject"**, [1 x %int]} %array2
}


define {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} @"_Pshadow_Pstandard_CClassSet_MgetEmptyClassArray"(%"_Pshadow_Pstandard_CClassSet"*) alwaysinline {
	ret {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} zeroinitializer
}

define {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} @"_Pshadow_Pstandard_CClassSet_MmakeClassArray_Pshadow_Pstandard_CClass_Pshadow_Pstandard_CClass_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"* ) {
	%memory = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CClass_class", i32 3)
	%pointer = bitcast %"_Pshadow_Pstandard_CObject"* %memory to %"_Pshadow_Pstandard_CClass"**
	store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %pointer
	%spot2 = getelementptr %"_Pshadow_Pstandard_CClass"** %pointer, %int 1
	store %"_Pshadow_Pstandard_CClass"* %2, %"_Pshadow_Pstandard_CClass"** %spot2
	%spot3 = getelementptr %"_Pshadow_Pstandard_CClass"** %pointer, %int 2
	store %"_Pshadow_Pstandard_CClass"* %3, %"_Pshadow_Pstandard_CClass"** %spot3
	%array1 = insertvalue {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} zeroinitializer, %"_Pshadow_Pstandard_CClass"** %pointer, 0
	%array2 = insertvalue {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} %array1, %int 2, 1, 0
	ret  {%"_Pshadow_Pstandard_CClass"**, [1 x %int]} %array2
}









