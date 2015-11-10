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
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CGenericClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %byte (%"_Pshadow_Pstandard_CString"*)*, %double (%"_Pshadow_Pstandard_CString"*)*, %float (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %long (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %short (%"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %ulong (%"_Pshadow_Pstandard_CString"*)*, %ushort (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CAddressMap_methods" = type opaque
%"_Pshadow_Pstandard_CAddressMap" = type opaque
%"_Pshadow_Pstandard_CClassSet_methods" = type { %"_Pshadow_Pstandard_CClassSet"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int)*, %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] })*, void (%"_Pshadow_Pstandard_CClassSet"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int)*, %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] })*, %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %boolean)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, void (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*, %boolean)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CClassSet"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, void (%"_Pshadow_Pstandard_CClassSet"*, %int)*, %int (%"_Pshadow_Pstandard_CClassSet"*)* }
%"_Pshadow_Pstandard_CClassSet" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClassSet_methods"* , { %"_Pshadow_Pstandard_CClassSet_INode"**, [1 x %int] }, %float, %int, %int, %int }
%"_Pshadow_Pstandard_CClassSet_INode_methods" = type { %"_Pshadow_Pstandard_CClassSet_INode"* (%"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CClassSet_INode"*)*, %"_Pshadow_Pstandard_CClassSet_INode"* (%"_Pshadow_Pstandard_CClassSet_INode"*)*, void (%"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CClassSet_INode"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet_INode"*)* }
%"_Pshadow_Pstandard_CClassSet_INode" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClassSet_INode_methods"* , %"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CClass"*, %int }

@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"

%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = type { %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* , %"_Pshadow_Pstandard_CString"* }

@"_Pshadow_Pstandard_CIndexOutOfBoundsException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = external constant %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"

%"_Pshadow_Pstandard_CArray_methods" = type { %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, %int)*, void (%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CObject"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
%"_Pshadow_Pstandard_CArrayNullable_methods" = type { %"_Pshadow_Pstandard_CArrayNullable"* (%"_Pshadow_Pstandard_CArrayNullable"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArrayNullable"*)*, %int (%"_Pshadow_Pstandard_CArrayNullable"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArrayNullable"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArrayNullable"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArrayNullable"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArrayNullable"*, %int)*, void (%"_Pshadow_Pstandard_CArrayNullable"*, %int, %"_Pshadow_Pstandard_CObject"*)*, { %"_Pshadow_Pstandard_CIteratorNullable_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CArrayNullable"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArrayNullable"*)*, %int (%"_Pshadow_Pstandard_CArrayNullable"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArrayNullable"*, %int, %int)* }
%"_Pshadow_Pstandard_CArrayNullable" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArrayNullable_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
%"_Pshadow_Pstandard_CIteratorNullable_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }

@"_Pshadow_Pstandard_CObject_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_Cint_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CArray_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CArray_methods" = external constant %"_Pshadow_Pstandard_CArray_methods"
@"_Pshadow_Pstandard_CArrayNullable_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CArrayNullable_methods" = external constant %"_Pshadow_Pstandard_CArrayNullable_methods"

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"*)

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CGenericClass"* @"_Pshadow_Pstandard_CClassSet_MgetGenericArray_Pshadow_Pstandard_CString_Pshadow_Pstandard_CObject_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %boolean)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MmakeName_Pshadow_Pstandard_CString_Pshadow_Pstandard_CObject_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)

@_genericSet = external global %"_Pshadow_Pstandard_CClassSet"*

; aliases used by ArrayNullable
@"_Pshadow_Pstandard_CArrayNullable_Mcreate_Pshadow_Pstandard_Cint_A1" = alias %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] })* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"
@"_Pshadow_Pstandard_CArrayNullable_Mdimensions" = alias %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mdimensions"
@"_Pshadow_Pstandard_CArrayNullable_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint" = alias %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"
@"_Pshadow_Pstandard_CArrayNullable_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject" = alias void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"
@"_Pshadow_Pstandard_CArrayNullable_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject" = alias void (%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject"
@"_Pshadow_Pstandard_CArrayNullable_MgetBaseClass" = alias %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_MgetBaseClass"

; debug
%"_Pshadow_Pio_CConsole_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, { %byte, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CConsole_methods"* , %boolean }
@"_Pshadow_Pio_CConsole_instance" = external global %"_Pshadow_Pio_CConsole"*
declare void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)

define private void @debugPrint(%int) {
	%2 = load %"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"
	call void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"* %2, %int %0)
	ret void
}

declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn

define i32 @__computeIndex(%"_Pshadow_Pstandard_CArray"*, { i32*, [1 x i32] }) alwaysinline {	
	%3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 3	
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
	%ex.obj = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CIndexOutOfBoundsException_class, %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" to %"_Pshadow_Pstandard_CObject_methods"*))
	%ex.ex = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"* %ex.obj)
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %ex.obj) noreturn	
	unreachable
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %lengths = alloca { %int*, [1 x %int] }
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CArray"** %this
    store { %int*, [1 x %int] } %1, { %int*, [1 x %int] }* %lengths
    %4 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this
    %5 = bitcast %"_Pshadow_Pstandard_CArray"* %4 to %"_Pshadow_Pstandard_CObject"*
    %6 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %5)
    %7 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this		
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %7, i32 0, i32 2
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %8
    %9 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 3
    %11 = insertvalue { %int*, [1 x %int] } zeroinitializer, %int* null, 0
    store { %int*, [1 x %int] } %11, { %int*, [1 x %int] }* %10
    %12 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %12, i32 0, i32 3
    %14 = load { %int*, [1 x %int] }, { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %14, { %int*, [1 x %int] }* %13
    %15 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this
    %16 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %15, i32 0, i32 2
    %17 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this    
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 0
    %19 = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %18
    %20 = bitcast %"_Pshadow_Pstandard_CClass"* %19 to %"_Pshadow_Pstandard_CGenericClass"*
	%21 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass", %"_Pshadow_Pstandard_CGenericClass"* %20, i32 0, i32 8	
	%22 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %21
	%23 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %22, 0
	%24 = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %23
	%25 = bitcast %"_Pshadow_Pstandard_CObject"* %24 to %"_Pshadow_Pstandard_CClass"*	    
    %26 = call %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"* %17)
	%27 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %25, i32 %26)		
    store %"_Pshadow_Pstandard_CObject"* %27, %"_Pshadow_Pstandard_CObject"** %16
    %28 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CArray"*
    ret %"_Pshadow_Pstandard_CArray"* %28
}

define %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 0
    %4 = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %3
    %5 = bitcast %"_Pshadow_Pstandard_CClass"* %4 to %"_Pshadow_Pstandard_CGenericClass"*
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass", %"_Pshadow_Pstandard_CGenericClass"* %5, i32 0, i32 8	
	%7 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %6	
    %8 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %7, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%9 = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %8
	%10 = bitcast %"_Pshadow_Pstandard_CObject"* %9 to %"_Pshadow_Pstandard_CClass"*
    ret %"_Pshadow_Pstandard_CClass"* %10
}


define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32 ) {
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
	
	%result = call %_Pshadow_Pstandard_CObject* @__arrayLoad(%"_Pshadow_Pstandard_CObject"** %array, i32 %1, %"_Pshadow_Pstandard_CClass"* %baseClass, %"_Pshadow_Pstandard_CObject"* %methods, %boolean 0)
	ret %_Pshadow_Pstandard_CObject* %result
}

; Should arrayLoad take an array of type parameters corresponding to class and method table?

define %_Pshadow_Pstandard_CObject* @__arrayLoad(%"_Pshadow_Pstandard_CObject"**, i32, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"*, %boolean) {
	; get generic class flag
	%flagRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* %2, i32 0, i32 6
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
	%elementRef = getelementptr inbounds %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %0, i32 %1
	%element = load %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %elementRef
	ret %_Pshadow_Pstandard_CObject* %element	

_primitive:
	; deal with primitive type
	; create new wrapper object
	%methodTable = bitcast %_Pshadow_Pstandard_CObject* %3 to %"_Pshadow_Pstandard_CObject_methods"*
	%wrapper = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %2, %"_Pshadow_Pstandard_CObject_methods"* %methodTable)
	
	; copy primitive value into new object
	%data = getelementptr inbounds %_Pshadow_Pstandard_CObject, %_Pshadow_Pstandard_CObject* %wrapper, i32 1
	%dataAsBytes = bitcast %_Pshadow_Pstandard_CObject* %data to i8*
	
	%width = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %2)	
	%offset = mul i32 %1, %width		
	%arrayAsBytes = bitcast %_Pshadow_Pstandard_CObject** %0 to i8*
	%primitiveElement = getelementptr inbounds i8, i8* %arrayAsBytes, i32 %offset
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %dataAsBytes, i8* %primitiveElement, i32 %width, i32 1, i1 0)
	ret %_Pshadow_Pstandard_CObject* %wrapper

_array:	
	; create new Array wrapper
	; ugh, have to create Array<T> class or ArrayNullable<T>
	%baseClassRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* %2, i32 0, i32 3
	%baseClass = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %baseClassRef
	
	br i1 %4, label %_null, label %_notNull
_null:
	%nullNameRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CArrayNullable_class" , i32 0, i32 2
	%nullName = load %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"** %nullNameRef
	%nullMethods = bitcast %"_Pshadow_Pstandard_CArrayNullable_methods"* @"_Pshadow_Pstandard_CArrayNullable_methods" to %"_Pshadow_Pstandard_CObject_methods"*
	br label %_name	
_notNull:
	%notNullNameRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CArray_class" , i32 0, i32 2
	%notNullName = load %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"** %notNullNameRef
	%notNullMethods = bitcast %"_Pshadow_Pstandard_CArray_methods"* @"_Pshadow_Pstandard_CArray_methods" to %"_Pshadow_Pstandard_CObject_methods"*
	br label %_name
_name:
	%arrayName = phi %"_Pshadow_Pstandard_CString"* [ %nullName, %_null ], [ %notNullName, %_notNull ]
	%methods = phi %"_Pshadow_Pstandard_CObject_methods"* [%nullMethods, %_null ], [ %notNullMethods, %_notNull ]
	
	%parametersArrayAsObj = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @"_Pshadow_Pstandard_CObject_class", i32 2)
	%parametersArrayRef = bitcast %_Pshadow_Pstandard_CObject* %parametersArrayAsObj to %_Pshadow_Pstandard_CObject**
	%parametersClass = getelementptr inbounds %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %parametersArrayRef, i32 0
	%baseClassAsObj = bitcast %"_Pshadow_Pstandard_CClass"* %baseClass to %"_Pshadow_Pstandard_CObject"*
	store %"_Pshadow_Pstandard_CObject"* %baseClassAsObj, %"_Pshadow_Pstandard_CObject"** %parametersClass
	
	%parametersMethodTable = getelementptr inbounds %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %parametersArrayRef, i32 1
	store %"_Pshadow_Pstandard_CObject"* %3, %"_Pshadow_Pstandard_CObject"** %parametersMethodTable
	
	%uninitializedParameters = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x i32] } undef, %"_Pshadow_Pstandard_CObject"** %parametersArrayRef, 0
	%parameters = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x i32] } %uninitializedParameters, i32 2, 1, 0
	
	%className = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MmakeName_Pshadow_Pstandard_CString_Pshadow_Pstandard_CObject_A1_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* %2, %"_Pshadow_Pstandard_CString"* %arrayName, { %"_Pshadow_Pstandard_CObject"**, [1 x i32] } %parameters, %int 0, %int 2)
	%classSet = load %"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClassSet"** @_genericSet
	%arrayClass = call %"_Pshadow_Pstandard_CGenericClass"* @"_Pshadow_Pstandard_CClassSet_MgetGenericArray_Pshadow_Pstandard_CString_Pshadow_Pstandard_CObject_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CClassSet"* %classSet, %"_Pshadow_Pstandard_CString"* %className, { %"_Pshadow_Pstandard_CObject"**, [1 x i32] } %parameters, %boolean %4)
	%arrayClassAsClass = bitcast %"_Pshadow_Pstandard_CGenericClass"* %arrayClass to %"_Pshadow_Pstandard_CClass"*
	%arrayWrapper = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %arrayClassAsClass, %"_Pshadow_Pstandard_CObject_methods"* %methods)
	
	; get size member of array class, which is equal to dimensions
	%dimensionsRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* %2, i32 0, i32 7
	%dimensions = load i32, i32* %dimensionsRef
	
	; allocate new array for dimensions
	%dimensionsArrayAsObj = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %dimensions)
	
	; get element width (which is an array)
	%arrayWidth = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %2)		
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %_Pshadow_Pstandard_CObject** %0 to i8*
	
	; get offset in bytes
	%arrayElement = getelementptr inbounds i8, i8* %arrayAsBytes2, i32 %arrayOffset
	
	; cast to object*** because an array is { Object**, [dimensions x int] }
	%arrayElementAsBytes = bitcast i8* %arrayElement to %_Pshadow_Pstandard_CObject***
	%arrayDataRef = load %_Pshadow_Pstandard_CObject**, %_Pshadow_Pstandard_CObject*** %arrayElementAsBytes
	%arrayDataAsObj = bitcast %_Pshadow_Pstandard_CObject** %arrayDataRef to %_Pshadow_Pstandard_CObject*
	
	; skip past object pointer to dimensions
	%arraySizesAsObj = getelementptr inbounds %_Pshadow_Pstandard_CObject**, %_Pshadow_Pstandard_CObject*** %arrayElementAsBytes, i32 1
	%arraySizes = bitcast %_Pshadow_Pstandard_CObject*** %arraySizesAsObj to i32*
	
	; copy dimensions into allocated space
	%dimensionsArray = bitcast %_Pshadow_Pstandard_CObject* %dimensionsArrayAsObj to i32*
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %dimensionsArray, i32* %arraySizes, i32 %dimensions, i32 0, i1 0)
	
	; make lengths array
	%lengthsArray1 = insertvalue { i32*, [1 x i32] } undef, i32* %arraySizes, 0
	%lengthsArray2 = insertvalue { i32*, [1 x i32] } %lengthsArray1, i32 %dimensions, 1, 0
	
	; initialize Array<T> object
	%initializedArray = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* %arrayWrapper, { %int*, [1 x %int] } %lengthsArray2, %"_Pshadow_Pstandard_CObject"* %arrayDataAsObj)
	
	; Object that just got initialized
	ret %_Pshadow_Pstandard_CObject* %arrayWrapper
}


define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @__computeIndex(%"_Pshadow_Pstandard_CArray"* %0, { i32*, [1 x i32] } %1)	
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray* %0, i32 %3)
	ret %_Pshadow_Pstandard_CObject* %4
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, i32, %_Pshadow_Pstandard_CObject*) {
	%classRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	
	; get array class
	%class = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %classRef
	%arrayClass = bitcast %"_Pshadow_Pstandard_CClass"* %class to %"_Pshadow_Pstandard_CGenericClass"*		
	
	; get base class
	%typeParametersArrayRef = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass", %"_Pshadow_Pstandard_CGenericClass"* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %typeParameters
	%baseClass = bitcast %"_Pshadow_Pstandard_CObject"* %baseClassAsObj to %"_Pshadow_Pstandard_CClass"*
		
	%arrayRef = getelementptr inbounds %_Pshadow_Pstandard_CArray, %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%arrayAsObj = load %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %arrayRef
	%array = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to %_Pshadow_Pstandard_CObject**
	
	call void @__arrayStore(%_Pshadow_Pstandard_CObject** %array, i32 %1, %_Pshadow_Pstandard_CObject* %2, %"_Pshadow_Pstandard_CClass"* %baseClass)
	ret void
}

define void @__arrayStore(%_Pshadow_Pstandard_CObject**, i32, %_Pshadow_Pstandard_CObject*, %"_Pshadow_Pstandard_CClass"*) {
	
	; get generic class flag
	%flagRef = getelementptr inbounds %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CClass* %3, i32 0, i32 6	
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
	%elementRef = getelementptr inbounds %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %0, i32 %1
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %elementRef
	ret void

_primitive:
	%primitiveWidth = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %3)
	%offset = mul i32 %1, %primitiveWidth	
	%arrayAsBytes1 = bitcast %_Pshadow_Pstandard_CObject** %0 to i8*
	%primitiveElement = getelementptr i8, i8* %arrayAsBytes1, i32 %offset
	%primitiveWrapper = getelementptr inbounds %_Pshadow_Pstandard_CObject, %_Pshadow_Pstandard_CObject* %2, i32 1
	%primitiveWrapperAsBytes = bitcast %_Pshadow_Pstandard_CObject* %primitiveWrapper to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %primitiveElement, i8* %primitiveWrapperAsBytes, i32 %primitiveWidth, i32 1, i1 0)
	ret void

_array:	
	; get the location inside the current Array<T>
	%arrayWidth = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %3)	
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %_Pshadow_Pstandard_CObject** %0 to i8*
	%arrayElement = getelementptr i8, i8* %arrayAsBytes2, i32 %arrayOffset
		
	; get the array data from the input Array<T> (which it must be, since anything else isnt' going to have an ArrayType as a parameter)
	%input = bitcast %_Pshadow_Pstandard_CObject* %2 to %"_Pshadow_Pstandard_CArray"*
	%inputArray = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %input, i32 0, i32 2
	%inputArrayAsObj = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %inputArray
				
	; store it into the array
	%arrayElementAsObj = bitcast i8* %arrayElement to %"_Pshadow_Pstandard_CObject"**
	store %"_Pshadow_Pstandard_CObject"* %inputArrayAsObj, %"_Pshadow_Pstandard_CObject"** %arrayElementAsObj	
	
	; store length data
	%dimensionsRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %input, i32 0, i32 3, i32 1, i32 0
	%dimensions = load i32, i32* %dimensionsRef
	%lengthsRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %input, i32 0, i32 3, i32 0
	%lengths = load i32*, i32** %lengthsRef
	
	; skip past the pointer to the length data
	%lengthDataAsObj = getelementptr inbounds %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %arrayElementAsObj, i32 1
	%lengthData = bitcast %"_Pshadow_Pstandard_CObject"** %lengthDataAsObj to i32*
	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %lengthData, i32* %lengths, i32 %dimensions, i32 0, i1 0)	
	ret void
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*) alwaysinline {
	%4 = call i32 @__computeIndex(%_Pshadow_Pstandard_CArray* %0, { i32*, [1 x i32] } %1)	
	call void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray* %0, i32 %4, %_Pshadow_Pstandard_CObject* %2)
	ret void
}

define noalias %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32, i32) {
	; check sizes first
	%size = call %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"* %0)
	%test1 = icmp ule i32 %2, %size
	br i1 %test1, label %_secondLessThanSize, label %throw
_secondLessThanSize:
	%test2 = icmp ule i32 %1, %2
	br i1 %test2, label %_firstLessThanSecond, label %throw
_firstLessThanSecond:
	; get class from original array
	%classRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	%class = load %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"** %classRef		
	
	; get method table from original array
	%methodsRef = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 1		
	%methods = load %"_Pshadow_Pstandard_CArray_methods"*, %"_Pshadow_Pstandard_CArray_methods"** %methodsRef
	%objMethods = bitcast %"_Pshadow_Pstandard_CArray_methods"* %methods to %"_Pshadow_Pstandard_CObject_methods"*
	
	; allocate new array object
	%arrayObj = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* %class, %"_Pshadow_Pstandard_CObject_methods"* %objMethods)
	
	; get base class
	%arrayClass = bitcast %"_Pshadow_Pstandard_CClass"* %class to %"_Pshadow_Pstandard_CGenericClass"*	
	%typeParametersArrayRef = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass", %"_Pshadow_Pstandard_CGenericClass"* %arrayClass, i32 0, i32 8	
	%typeParametersArray = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %typeParametersArrayRef
	%typeParameters = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %typeParametersArray, 0	
	; because the class is the first element, we can skip the getelementptr 0 stuff
	%baseClassAsObj = load %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"** %typeParameters
	%baseClass = bitcast %"_Pshadow_Pstandard_CObject"* %baseClassAsObj to %"_Pshadow_Pstandard_CClass"*
		
	; create array for dimensions (only a single element, since the subarray will always be 1D)
	%dimensionsAsObj = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 1)
	%dimensions = bitcast %_Pshadow_Pstandard_CObject* %dimensionsAsObj to i32*
	
	; get difference between indexes
	%difference = sub i32 %2, %1
	
	; store difference (size) in the only position in the new array
	store i32 %difference, i32* %dimensions
	
	; put new dimension data into array
	%dimensionsArray1 = insertvalue { i32*, [1 x i32] } undef, i32* %dimensions, 0
	
	; put number of dimensions (1) into array
	%dimensionsArray2 = insertvalue { i32*, [1 x i32] } %dimensionsArray1, i32 1, 1, 0

	; get array data
	%arrayDataRef = getelementptr inbounds %_Pshadow_Pstandard_CArray, %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%arrayData = load %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject** %arrayDataRef
	
	; cast to char*
	%arrayDataAsChar = bitcast %_Pshadow_Pstandard_CObject* %arrayData to i8*
	
	
	;allocate new real array
	%newArray = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %baseClass, i32 %difference)
	%newArrayAsChar = bitcast %_Pshadow_Pstandard_CObject* %newArray to i8*
	
	; get type parameter width
	%width = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %baseClass)			
	
	; multiply starting point by width
	%offset = mul i32 %1, %width
	%arrayDataAtOffset = getelementptr i8, i8* %arrayDataAsChar, i32 %offset
	%total = mul i32 %difference, %width
	
	;copy data
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %newArrayAsChar, i8* %arrayDataAtOffset, i32 %total, i32 1, i1 0)
	
	%initializedArray = call %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CObject* %arrayObj, { i32*, [1 x i32] } %dimensionsArray2, %_Pshadow_Pstandard_CObject* %newArray)		
	ret %_Pshadow_Pstandard_CArray* %initializedArray	
throw:
	%ex.obj = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CIndexOutOfBoundsException_class, %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" to %"_Pshadow_Pstandard_CObject_methods"*))
	%ex.ex = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"* %ex.obj)
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %ex.obj) noreturn	
	unreachable
}

define %int @"_Pshadow_Pstandard_CArray_Mdimensions"(%"_Pshadow_Pstandard_CArray"*) {
    %2 = getelementptr inbounds %"_Pshadow_Pstandard_CArray", %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 3
    %3 = load { %int*, [1 x %int] } , { %int*, [1 x %int] }* %2
    %4 = extractvalue { %int*, [1 x %int] } %3, 1, 0
    ret %int %4
}

