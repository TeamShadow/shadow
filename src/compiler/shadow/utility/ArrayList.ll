; shadow.utility@ArrayList<V>

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
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

%"_Pshadow_Putility_CArrayList_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CArrayList"* (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CArrayList"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)*, void (%"_Pshadow_Putility_CArrayList"*, %int, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CArrayList"*)* }
%"_Pshadow_Putility_CArrayList" = type { %"_Pshadow_Putility_CArrayList_Mclass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %int, %int }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cushort"* (%"_Pshadow_Pstandard_Cushort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %int (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"*, %ushort }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Ccode"* (%"_Pshadow_Pstandard_Ccode"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"*, %code }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cdouble"* (%"_Pshadow_Pstandard_Cdouble"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cdouble"*)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %int (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"*, %double }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Clong"* (%"_Pshadow_Pstandard_Clong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*)*, %ulong (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*, %ulong)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"*, %long }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cfloat"* (%"_Pshadow_Pstandard_Cfloat"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %int (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"*, %float }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CArrayList_IArrayListIterator"* (%"_Pshadow_Putility_CArrayList_IArrayListIterator"*, %"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CArrayList_IArrayListIterator"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList_IArrayListIterator"*)* }
%"_Pshadow_Putility_CArrayList_IArrayListIterator" = type { %"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Putility_CArrayList"*, %int, %int }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %int)* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException" = type { %"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_P_CUnexpectedNullException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_P_CUnexpectedNullException"* (%"_P_CUnexpectedNullException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_P_CUnexpectedNullException" = type { %"_P_CUnexpectedNullException_Mclass"* }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }

@"_Pshadow_Putility_CArrayList_Mclass" = constant %"_Pshadow_Putility_CArrayList_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Putility_CArrayList"* getelementptr (%"_Pshadow_Putility_CArrayList"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Putility_CArrayList"** getelementptr (%"_Pshadow_Putility_CArrayList"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Putility_CArrayList"* (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)* @"_Pshadow_Putility_CArrayList_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CArrayList_Madd_CV", void (%"_Pshadow_Putility_CArrayList"*)* @"_Pshadow_Putility_CArrayList_Mclear", %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CArrayList_Mcontains_CV", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)* @"_Pshadow_Putility_CArrayList_Mdelete_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)* @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint", void (%"_Pshadow_Putility_CArrayList"*, %int, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint_CV", %int (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CArrayList_MindexOf_CV", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*)* @"_Pshadow_Putility_CArrayList_Miterator", %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Putility_CArrayList_Mremove_CV", %int (%"_Pshadow_Putility_CArrayList"*)* @"_Pshadow_Putility_CArrayList_Msize" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass" = external constant %"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external constant %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external constant %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external constant %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external constant %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass" = external constant %"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_P_CUnexpectedNullException_Mclass" = external constant %"_P_CUnexpectedNullException_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external constant %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external constant %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external constant %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external constant %"_Pshadow_Pstandard_CObject_Mclass"

define void @"_Pshadow_Putility_CArrayList_Mclear"(%"_Pshadow_Putility_CArrayList"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    %2 = load %"_Pshadow_Putility_CArrayList"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %2, i32 0, i32 4
    store %int 0, %int* %3
    %4 = load %"_Pshadow_Putility_CArrayList"** %this
    %5 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %4, i32 0, i32 3
    %6 = load %int* %5
    %7 = add %int %6, 1
    store %int %7, %int* %5
    ret void
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Miterator"(%"_Pshadow_Putility_CArrayList"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass"* @"_Pshadow_Putility_CArrayList_IArrayListIterator_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Putility_CArrayList_IArrayListIterator"*
    %4 = load %"_Pshadow_Putility_CArrayList"** %this
    %5 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %4, i32 0, i32 2
    %6 = load %"_Pshadow_Pstandard_CClass"** %5
    %7 = load %"_Pshadow_Putility_CArrayList"** %this
    %8 = call %"_Pshadow_Putility_CArrayList_IArrayListIterator"* @"_Pshadow_Putility_CArrayList_IArrayListIterator_Mcreate"(%"_Pshadow_Putility_CArrayList_IArrayListIterator"* %3, %"_Pshadow_Putility_CArrayList"* %7, %"_Pshadow_Pstandard_CClass"* %6)
    %9 = bitcast %"_Pshadow_Putility_CArrayList_IArrayListIterator"* %3 to %"_Pshadow_Pstandard_CObject"*
    ret %"_Pshadow_Pstandard_CObject"* %9
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CArrayList"*, %int) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %index = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %int %1, %int* %index
    %3 = load %int* %index
    %4 = icmp slt %int %3, 0
    store %boolean %4, %boolean* %_temp
    br %boolean %4, label %_label3, label %_label4
_label4:
    %5 = load %"_Pshadow_Putility_CArrayList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %5, i32 0, i32 4
    %7 = load %int* %index
    %8 = load %int* %6
    %9 = icmp sge %int %7, %8
    store %boolean %9, %boolean* %_temp
    br label %_label3
_label3:
    %10 = load %boolean* %_temp
    br %boolean %10, label %_label0, label %_label1
_label0:
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass", i32 0, i32 0))
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %"_Pshadow_Pstandard_CIndexOutOfBoundsException"*
    %13 = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12)
    %14 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12 to %"_Pshadow_Pstandard_CException"*
    %15 = getelementptr %"_Pshadow_Pstandard_CException"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CException_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %16, i32 0, i32 9
    %18 = load void (%"_Pshadow_Pstandard_CException"*)** %17
    %19 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12 to %"_Pshadow_Pstandard_CException"*
    call void %18(%"_Pshadow_Pstandard_CException"* %19)
    br label %_label2
_label1:
    br label %_label2
_label2:
    %20 = load %"_Pshadow_Putility_CArrayList"** %this
    %21 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %20, i32 0, i32 1
    %22 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %21
    %23 = load %int* %index
    %24 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %22, 0
    %25 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %24, %int %23
    %26 = load %"_Pshadow_Pstandard_CObject"** %25
    %27 = icmp eq %"_Pshadow_Pstandard_CObject"* %26, null
    br %boolean %27, label %_label6, label %_label7
_label7:
    %28 = load %"_Pshadow_Pstandard_CObject"** %25
    ret %"_Pshadow_Pstandard_CObject"* %28
    br label %_label5
_label6:
    %30 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_P_CUnexpectedNullException_Mclass"* @"_P_CUnexpectedNullException_Mclass", i32 0, i32 0))
    %31 = bitcast %"_Pshadow_Pstandard_CObject"* %30 to %"_P_CUnexpectedNullException"*
    %32 = call %"_P_CUnexpectedNullException"* @"_P_CUnexpectedNullException_Mcreate"(%"_P_CUnexpectedNullException"* %31)
    %33 = bitcast %"_P_CUnexpectedNullException"* %31 to %"_Pshadow_Pstandard_CException"*
    %34 = getelementptr %"_Pshadow_Pstandard_CException"* %33, i32 0, i32 0
    %35 = load %"_Pshadow_Pstandard_CException_Mclass"** %34
    %36 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %35, i32 0, i32 9
    %37 = load void (%"_Pshadow_Pstandard_CException"*)** %36
    %38 = bitcast %"_P_CUnexpectedNullException"* %31 to %"_Pshadow_Pstandard_CException"*
    call void %37(%"_Pshadow_Pstandard_CException"* %38)
    br label %_label5
_label5:
    ret %"_Pshadow_Pstandard_CObject"* null
}

define void @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint_CV"(%"_Pshadow_Putility_CArrayList"*, %int, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %index = alloca %int
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %_temp = alloca %boolean
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %int %1, %int* %index
    store %"_Pshadow_Pstandard_CObject"* %2, %"_Pshadow_Pstandard_CObject"** %value
    %4 = load %int* %index
    %5 = icmp slt %int %4, 0
    store %boolean %5, %boolean* %_temp
    br %boolean %5, label %_label11, label %_label12
_label12:
    %6 = load %"_Pshadow_Putility_CArrayList"** %this
    %7 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %6, i32 0, i32 4
    %8 = load %int* %index
    %9 = load %int* %7
    %10 = icmp sgt %int %8, %9
    store %boolean %10, %boolean* %_temp
    br label %_label11
_label11:
    %11 = load %boolean* %_temp
    br %boolean %11, label %_label8, label %_label9
_label8:
    %12 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass", i32 0, i32 0))
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %"_Pshadow_Pstandard_CIndexOutOfBoundsException"*
    %14 = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %13)
    %15 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %13 to %"_Pshadow_Pstandard_CException"*
    %16 = getelementptr %"_Pshadow_Pstandard_CException"* %15, i32 0, i32 0
    %17 = load %"_Pshadow_Pstandard_CException_Mclass"** %16
    %18 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %17, i32 0, i32 9
    %19 = load void (%"_Pshadow_Pstandard_CException"*)** %18
    %20 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %13 to %"_Pshadow_Pstandard_CException"*
    call void %19(%"_Pshadow_Pstandard_CException"* %20)
    br label %_label10
_label9:
    br label %_label10
_label10:
    %21 = load %"_Pshadow_Putility_CArrayList"** %this
    %22 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %21, i32 0, i32 4
    %23 = load %int* %index
    %24 = load %int* %22
    %25 = icmp eq %int %23, %24
    br %boolean %25, label %_label13, label %_label14
_label13:
    %26 = load %"_Pshadow_Putility_CArrayList"** %this
    %27 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %26, i32 0, i32 4
    %28 = load %"_Pshadow_Putility_CArrayList"** %this
    %29 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %28, i32 0, i32 1
    %30 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %29
    %31 = load %int* %27
    %32 = load %"_Pshadow_Putility_CArrayList"** %this
    %33 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %32, i32 0, i32 2
    %34 = load %"_Pshadow_Pstandard_CClass"** %33
    %35 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %30, 1
    %36 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %37 = bitcast %"_Pshadow_Pstandard_CObject"* %36 to [1 x %int]*
    store [1 x %int] %35, [1 x %int]* %37
    %38 = getelementptr inbounds [1 x %int]* %37, i32 0, i32 0
    %39 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %38, 0
    %40 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %30, 0
    %41 = bitcast %"_Pshadow_Pstandard_CObject"** %40 to %"_Pshadow_Pstandard_CObject"*
    %42 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %43 = bitcast %"_Pshadow_Pstandard_CObject"* %42 to %"_Pshadow_Pstandard_CArray"*
    %44 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %43, %"_Pshadow_Pstandard_CClass"* %34, { %int*, [1 x %int] } %39, %"_Pshadow_Pstandard_CObject"* %41)
    %45 = getelementptr %"_Pshadow_Pstandard_CArray"* %44, i32 0, i32 0
    %46 = load %"_Pshadow_Pstandard_CArray_Mclass"** %45
    %47 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %46, i32 0, i32 13
    %48 = load %int (%"_Pshadow_Pstandard_CArray"*)** %47
    %49 = load %"_Pshadow_Putility_CArrayList"** %this
    %50 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %49, i32 0, i32 2
    %51 = load %"_Pshadow_Pstandard_CClass"** %50
    %52 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %30, 1
    %53 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %54 = bitcast %"_Pshadow_Pstandard_CObject"* %53 to [1 x %int]*
    store [1 x %int] %52, [1 x %int]* %54
    %55 = getelementptr inbounds [1 x %int]* %54, i32 0, i32 0
    %56 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %55, 0
    %57 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %30, 0
    %58 = bitcast %"_Pshadow_Pstandard_CObject"** %57 to %"_Pshadow_Pstandard_CObject"*
    %59 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %60 = bitcast %"_Pshadow_Pstandard_CObject"* %59 to %"_Pshadow_Pstandard_CArray"*
    %61 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %60, %"_Pshadow_Pstandard_CClass"* %51, { %int*, [1 x %int] } %56, %"_Pshadow_Pstandard_CObject"* %58)
    %62 = call %int %48(%"_Pshadow_Pstandard_CArray"* %61)
    %63 = icmp sge %int %31, %62
    br %boolean %63, label %_label16, label %_label17
_label16:
    %64 = load %"_Pshadow_Putility_CArrayList"** %this
    %65 = load %"_Pshadow_Putility_CArrayList"** %this
    call void @"_Pshadow_Putility_CArrayList_Mgrow"(%"_Pshadow_Putility_CArrayList"* %65)
    br label %_label18
_label17:
    br label %_label18
_label18:
    %66 = load %"_Pshadow_Putility_CArrayList"** %this
    %67 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %66, i32 0, i32 4
    %68 = load %int* %67
    %69 = add %int %68, 1
    store %int %69, %int* %67
    br label %_label15
_label14:
    br label %_label15
_label15:
    %70 = load %"_Pshadow_Putility_CArrayList"** %this
    %71 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %70, i32 0, i32 1
    %72 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %71
    %73 = load %int* %index
    %74 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %72, 0
    %75 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %74, %int %73
    %76 = load %"_Pshadow_Pstandard_CObject"** %value
    store %"_Pshadow_Pstandard_CObject"* %76, %"_Pshadow_Pstandard_CObject"** %75
    %77 = load %"_Pshadow_Putility_CArrayList"** %this
    %78 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %77, i32 0, i32 3
    %79 = load %int* %78
    %80 = add %int %79, 1
    store %int %80, %int* %78
    ret void
}

define %boolean @"_Pshadow_Putility_CArrayList_Mremove_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %index = alloca %int
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    %3 = load %"_Pshadow_Putility_CArrayList"** %this
    %4 = getelementptr %"_Pshadow_Putility_CArrayList"* %3, i32 0, i32 0
    %5 = load %"_Pshadow_Putility_CArrayList_Mclass"** %4
    %6 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %5, i32 0, i32 13
    %7 = load %int (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)** %6
    %8 = load %"_Pshadow_Putility_CArrayList"** %this
    %9 = load %"_Pshadow_Pstandard_CObject"** %value
    %10 = call %int %7(%"_Pshadow_Putility_CArrayList"* %8, %"_Pshadow_Pstandard_CObject"* %9)
    store %int %10, %int* %index
    %11 = sub %int 0, 1
    %12 = load %int* %index
    %13 = icmp eq %int %12, %11
    br %boolean %13, label %_label19, label %_label20
_label19:
    ret %boolean false
    br label %_label21
_label20:
    br label %_label21
_label21:
    %15 = load %"_Pshadow_Putility_CArrayList"** %this
    %16 = getelementptr %"_Pshadow_Putility_CArrayList"* %15, i32 0, i32 0
    %17 = load %"_Pshadow_Putility_CArrayList_Mclass"** %16
    %18 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %17, i32 0, i32 10
    %19 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)** %18
    %20 = load %"_Pshadow_Putility_CArrayList"** %this
    %21 = load %int* %index
    %22 = call %"_Pshadow_Pstandard_CObject"* %19(%"_Pshadow_Putility_CArrayList"* %20, %int %21)
    ret %boolean true
}

define void @"_Pshadow_Putility_CArrayList_Mgrow"(%"_Pshadow_Putility_CArrayList"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %temp = alloca { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }
    %i = alloca %int
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    %2 = load %"_Pshadow_Putility_CArrayList"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %2, i32 0, i32 1
    %4 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %3
    %5 = load %"_Pshadow_Putility_CArrayList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %5, i32 0, i32 2
    %7 = load %"_Pshadow_Pstandard_CClass"** %6
    %8 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %4, 1
    %9 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %10 = bitcast %"_Pshadow_Pstandard_CObject"* %9 to [1 x %int]*
    store [1 x %int] %8, [1 x %int]* %10
    %11 = getelementptr inbounds [1 x %int]* %10, i32 0, i32 0
    %12 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %11, 0
    %13 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %4, 0
    %14 = bitcast %"_Pshadow_Pstandard_CObject"** %13 to %"_Pshadow_Pstandard_CObject"*
    %15 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %16 = bitcast %"_Pshadow_Pstandard_CObject"* %15 to %"_Pshadow_Pstandard_CArray"*
    %17 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %16, %"_Pshadow_Pstandard_CClass"* %7, { %int*, [1 x %int] } %12, %"_Pshadow_Pstandard_CObject"* %14)
    %18 = getelementptr %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 0
    %19 = load %"_Pshadow_Pstandard_CArray_Mclass"** %18
    %20 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %19, i32 0, i32 13
    %21 = load %int (%"_Pshadow_Pstandard_CArray"*)** %20
    %22 = load %"_Pshadow_Putility_CArrayList"** %this
    %23 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %22, i32 0, i32 2
    %24 = load %"_Pshadow_Pstandard_CClass"** %23
    %25 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %4, 1
    %26 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to [1 x %int]*
    store [1 x %int] %25, [1 x %int]* %27
    %28 = getelementptr inbounds [1 x %int]* %27, i32 0, i32 0
    %29 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %28, 0
    %30 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %4, 0
    %31 = bitcast %"_Pshadow_Pstandard_CObject"** %30 to %"_Pshadow_Pstandard_CObject"*
    %32 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %33 = bitcast %"_Pshadow_Pstandard_CObject"* %32 to %"_Pshadow_Pstandard_CArray"*
    %34 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %33, %"_Pshadow_Pstandard_CClass"* %24, { %int*, [1 x %int] } %29, %"_Pshadow_Pstandard_CObject"* %31)
    %35 = call %int %21(%"_Pshadow_Pstandard_CArray"* %34)
    %36 = mul %int %35, 2
    %37 = load %"_Pshadow_Putility_CArrayList"** %this
    %38 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %37, i32 0, i32 2
    %39 = load %"_Pshadow_Pstandard_CClass"** %38
    %40 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* %39, %int %36)
    %41 = bitcast %"_Pshadow_Pstandard_CObject"* %40 to %"_Pshadow_Pstandard_CObject"**
    %42 = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } undef, %"_Pshadow_Pstandard_CObject"** %41, 0
    %43 = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %42, %int %36, 1, 0
    store { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %43, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %temp
    store %int 0, %int* %i
    br label %_label23
_label22:
    %44 = load %"_Pshadow_Putility_CArrayList"** %this
    %45 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %44, i32 0, i32 1
    %46 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %45
    %47 = load %int* %i
    %48 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %46, 0
    %49 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %48, %int %47
    %50 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %temp
    %51 = load %int* %i
    %52 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %50, 0
    %53 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %52, %int %51
    %54 = load %"_Pshadow_Pstandard_CObject"** %49
    store %"_Pshadow_Pstandard_CObject"* %54, %"_Pshadow_Pstandard_CObject"** %53
    %55 = load %int* %i
    %56 = add %int %55, 1
    store %int %56, %int* %i
    br label %_label23
_label23:
    %57 = load %"_Pshadow_Putility_CArrayList"** %this
    %58 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %57, i32 0, i32 1
    %59 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %58
    %60 = load %int* %i
    %61 = load %"_Pshadow_Putility_CArrayList"** %this
    %62 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %61, i32 0, i32 2
    %63 = load %"_Pshadow_Pstandard_CClass"** %62
    %64 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %59, 1
    %65 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %66 = bitcast %"_Pshadow_Pstandard_CObject"* %65 to [1 x %int]*
    store [1 x %int] %64, [1 x %int]* %66
    %67 = getelementptr inbounds [1 x %int]* %66, i32 0, i32 0
    %68 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %67, 0
    %69 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %59, 0
    %70 = bitcast %"_Pshadow_Pstandard_CObject"** %69 to %"_Pshadow_Pstandard_CObject"*
    %71 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %72 = bitcast %"_Pshadow_Pstandard_CObject"* %71 to %"_Pshadow_Pstandard_CArray"*
    %73 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %72, %"_Pshadow_Pstandard_CClass"* %63, { %int*, [1 x %int] } %68, %"_Pshadow_Pstandard_CObject"* %70)
    %74 = getelementptr %"_Pshadow_Pstandard_CArray"* %73, i32 0, i32 0
    %75 = load %"_Pshadow_Pstandard_CArray_Mclass"** %74
    %76 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %75, i32 0, i32 13
    %77 = load %int (%"_Pshadow_Pstandard_CArray"*)** %76
    %78 = load %"_Pshadow_Putility_CArrayList"** %this
    %79 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %78, i32 0, i32 2
    %80 = load %"_Pshadow_Pstandard_CClass"** %79
    %81 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %59, 1
    %82 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %83 = bitcast %"_Pshadow_Pstandard_CObject"* %82 to [1 x %int]*
    store [1 x %int] %81, [1 x %int]* %83
    %84 = getelementptr inbounds [1 x %int]* %83, i32 0, i32 0
    %85 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %84, 0
    %86 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %59, 0
    %87 = bitcast %"_Pshadow_Pstandard_CObject"** %86 to %"_Pshadow_Pstandard_CObject"*
    %88 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %89 = bitcast %"_Pshadow_Pstandard_CObject"* %88 to %"_Pshadow_Pstandard_CArray"*
    %90 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %89, %"_Pshadow_Pstandard_CClass"* %80, { %int*, [1 x %int] } %85, %"_Pshadow_Pstandard_CObject"* %87)
    %91 = call %int %77(%"_Pshadow_Pstandard_CArray"* %90)
    %92 = icmp slt %int %60, %91
    br %boolean %92, label %_label22, label %_label24
_label24:
    %93 = load %"_Pshadow_Putility_CArrayList"** %this
    %94 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %93, i32 0, i32 1
    %95 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %temp
    store { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %95, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %94
    ret void
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Mdelete_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CArrayList"*, %int) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %index = alloca %int
    %_temp = alloca %boolean
    %temp = alloca %"_Pshadow_Pstandard_CObject"*
    %i = alloca %int
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %int %1, %int* %index
    %3 = load %int* %index
    %4 = icmp slt %int %3, 0
    store %boolean %4, %boolean* %_temp
    br %boolean %4, label %_label28, label %_label29
_label29:
    %5 = load %"_Pshadow_Putility_CArrayList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %5, i32 0, i32 4
    %7 = load %int* %index
    %8 = load %int* %6
    %9 = icmp sge %int %7, %8
    store %boolean %9, %boolean* %_temp
    br label %_label28
_label28:
    %10 = load %boolean* %_temp
    br %boolean %10, label %_label25, label %_label26
_label25:
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass", i32 0, i32 0))
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %"_Pshadow_Pstandard_CIndexOutOfBoundsException"*
    %13 = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12)
    %14 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12 to %"_Pshadow_Pstandard_CException"*
    %15 = getelementptr %"_Pshadow_Pstandard_CException"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CException_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %16, i32 0, i32 9
    %18 = load void (%"_Pshadow_Pstandard_CException"*)** %17
    %19 = bitcast %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %12 to %"_Pshadow_Pstandard_CException"*
    call void %18(%"_Pshadow_Pstandard_CException"* %19)
    br label %_label27
_label26:
    br label %_label27
_label27:
    %20 = load %"_Pshadow_Putility_CArrayList"** %this
    %21 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %20, i32 0, i32 1
    %22 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %21
    %23 = load %int* %index
    %24 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %22, 0
    %25 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %24, %int %23
    %26 = load %"_Pshadow_Pstandard_CObject"** %25
    %27 = icmp eq %"_Pshadow_Pstandard_CObject"* %26, null
    br %boolean %27, label %_label31, label %_label32
_label32:
    %28 = load %"_Pshadow_Pstandard_CObject"** %25
    store %"_Pshadow_Pstandard_CObject"* %28, %"_Pshadow_Pstandard_CObject"** %temp
    %29 = load %int* %index
    store %int %29, %int* %i
    br label %_label34
_label33:
    %30 = load %"_Pshadow_Putility_CArrayList"** %this
    %31 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %30, i32 0, i32 1
    %32 = load %int* %i
    %33 = add %int %32, 1
    %34 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %31
    %35 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %34, 0
    %36 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %35, %int %33
    %37 = load %"_Pshadow_Putility_CArrayList"** %this
    %38 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %37, i32 0, i32 1
    %39 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %38
    %40 = load %int* %i
    %41 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %39, 0
    %42 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %41, %int %40
    %43 = load %"_Pshadow_Pstandard_CObject"** %36
    store %"_Pshadow_Pstandard_CObject"* %43, %"_Pshadow_Pstandard_CObject"** %42
    %44 = load %int* %i
    %45 = add %int %44, 1
    store %int %45, %int* %i
    br label %_label34
_label34:
    %46 = load %"_Pshadow_Putility_CArrayList"** %this
    %47 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %46, i32 0, i32 4
    %48 = load %int* %47
    %49 = sub %int %48, 1
    %50 = load %int* %i
    %51 = icmp slt %int %50, %49
    br %boolean %51, label %_label33, label %_label35
_label35:
    %52 = load %"_Pshadow_Putility_CArrayList"** %this
    %53 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %52, i32 0, i32 4
    %54 = load %int* %53
    %55 = sub %int %54, 1
    store %int %55, %int* %53
    %56 = load %"_Pshadow_Putility_CArrayList"** %this
    %57 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %56, i32 0, i32 3
    %58 = load %int* %57
    %59 = add %int %58, 1
    store %int %59, %int* %57
    %60 = load %"_Pshadow_Pstandard_CObject"** %temp
    ret %"_Pshadow_Pstandard_CObject"* %60
    br label %_label30
_label31:
    %62 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_P_CUnexpectedNullException_Mclass"* @"_P_CUnexpectedNullException_Mclass", i32 0, i32 0))
    %63 = bitcast %"_Pshadow_Pstandard_CObject"* %62 to %"_P_CUnexpectedNullException"*
    %64 = call %"_P_CUnexpectedNullException"* @"_P_CUnexpectedNullException_Mcreate"(%"_P_CUnexpectedNullException"* %63)
    %65 = bitcast %"_P_CUnexpectedNullException"* %63 to %"_Pshadow_Pstandard_CException"*
    %66 = getelementptr %"_Pshadow_Pstandard_CException"* %65, i32 0, i32 0
    %67 = load %"_Pshadow_Pstandard_CException_Mclass"** %66
    %68 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %67, i32 0, i32 9
    %69 = load void (%"_Pshadow_Pstandard_CException"*)** %68
    %70 = bitcast %"_P_CUnexpectedNullException"* %63 to %"_Pshadow_Pstandard_CException"*
    call void %69(%"_Pshadow_Pstandard_CException"* %70)
    br label %_label30
_label30:
    ret %"_Pshadow_Pstandard_CObject"* null
}

define %boolean @"_Pshadow_Putility_CArrayList_Mcontains_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    %3 = load %"_Pshadow_Putility_CArrayList"** %this
    %4 = getelementptr %"_Pshadow_Putility_CArrayList"* %3, i32 0, i32 0
    %5 = load %"_Pshadow_Putility_CArrayList_Mclass"** %4
    %6 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %5, i32 0, i32 13
    %7 = load %int (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)** %6
    %8 = load %"_Pshadow_Putility_CArrayList"** %this
    %9 = load %"_Pshadow_Pstandard_CObject"** %value
    %10 = call %int %7(%"_Pshadow_Putility_CArrayList"* %8, %"_Pshadow_Pstandard_CObject"* %9)
    %11 = sub %int 0, 1
    %12 = icmp eq %int %10, %11
    %13 = xor %boolean %12, true
    ret %boolean %13
}

define %int @"_Pshadow_Putility_CArrayList_MindexOf_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    %i = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    store %int 0, %int* %i
    br label %_label37
_label36:
    %3 = load %"_Pshadow_Putility_CArrayList"** %this
    %4 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %3, i32 0, i32 1
    %5 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %4
    %6 = load %int* %i
    %7 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %5, 0
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %7, %int %6
    %9 = load %"_Pshadow_Pstandard_CObject"** %8
    %10 = icmp eq %"_Pshadow_Pstandard_CObject"* %9, null
    br %boolean %10, label %_label42, label %_label43
_label42:
    %11 = load %"_Pshadow_Pstandard_CObject"** %value
    %12 = icmp eq %"_Pshadow_Pstandard_CObject"* %11, null
    store %boolean %12, %boolean* %_temp
    br label %_label44
_label43:
    %13 = load %"_Pshadow_Pstandard_CObject"** %8
    %14 = bitcast %"_Pshadow_Pstandard_CObject"* %13 to %"_Pshadow_Pstandard_CObject"*
    %15 = getelementptr %"_Pshadow_Pstandard_CObject"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CObject_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %16, i32 0, i32 3
    %18 = load %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)** %17
    %19 = load %"_Pshadow_Pstandard_CObject"** %8
    %20 = bitcast %"_Pshadow_Pstandard_CObject"* %19 to %"_Pshadow_Pstandard_CObject"*
    %21 = load %"_Pshadow_Pstandard_CObject"** %value
    %22 = bitcast %"_Pshadow_Pstandard_CObject"* %21 to %"_Pshadow_Pstandard_CObject"*
    %23 = call %boolean %18(%"_Pshadow_Pstandard_CObject"* %20, %"_Pshadow_Pstandard_CObject"* %22)
    store %boolean %23, %boolean* %_temp
    br label %_label44
_label44:
    %24 = load %boolean* %_temp
    br %boolean %24, label %_label39, label %_label40
_label39:
    %25 = load %int* %i
    ret %int %25
    br label %_label41
_label40:
    br label %_label41
_label41:
    %27 = load %int* %i
    %28 = add %int %27, 1
    store %int %28, %int* %i
    br label %_label37
_label37:
    %29 = load %"_Pshadow_Putility_CArrayList"** %this
    %30 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %29, i32 0, i32 1
    %31 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %30
    %32 = load %int* %i
    %33 = load %"_Pshadow_Putility_CArrayList"** %this
    %34 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %33, i32 0, i32 2
    %35 = load %"_Pshadow_Pstandard_CClass"** %34
    %36 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %31, 1
    %37 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to [1 x %int]*
    store [1 x %int] %36, [1 x %int]* %38
    %39 = getelementptr inbounds [1 x %int]* %38, i32 0, i32 0
    %40 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %39, 0
    %41 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %31, 0
    %42 = bitcast %"_Pshadow_Pstandard_CObject"** %41 to %"_Pshadow_Pstandard_CObject"*
    %43 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %44 = bitcast %"_Pshadow_Pstandard_CObject"* %43 to %"_Pshadow_Pstandard_CArray"*
    %45 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %44, %"_Pshadow_Pstandard_CClass"* %35, { %int*, [1 x %int] } %40, %"_Pshadow_Pstandard_CObject"* %42)
    %46 = getelementptr %"_Pshadow_Pstandard_CArray"* %45, i32 0, i32 0
    %47 = load %"_Pshadow_Pstandard_CArray_Mclass"** %46
    %48 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %47, i32 0, i32 13
    %49 = load %int (%"_Pshadow_Pstandard_CArray"*)** %48
    %50 = load %"_Pshadow_Putility_CArrayList"** %this
    %51 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %50, i32 0, i32 2
    %52 = load %"_Pshadow_Pstandard_CClass"** %51
    %53 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %31, 1
    %54 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %55 = bitcast %"_Pshadow_Pstandard_CObject"* %54 to [1 x %int]*
    store [1 x %int] %53, [1 x %int]* %55
    %56 = getelementptr inbounds [1 x %int]* %55, i32 0, i32 0
    %57 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %56, 0
    %58 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %31, 0
    %59 = bitcast %"_Pshadow_Pstandard_CObject"** %58 to %"_Pshadow_Pstandard_CObject"*
    %60 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %61 = bitcast %"_Pshadow_Pstandard_CObject"* %60 to %"_Pshadow_Pstandard_CArray"*
    %62 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %61, %"_Pshadow_Pstandard_CClass"* %52, { %int*, [1 x %int] } %57, %"_Pshadow_Pstandard_CObject"* %59)
    %63 = call %int %49(%"_Pshadow_Pstandard_CArray"* %62)
    %64 = icmp slt %int %32, %63
    br %boolean %64, label %_label36, label %_label38
_label38:
    %65 = sub %int 0, 1
    ret %int %65
}

define %boolean @"_Pshadow_Putility_CArrayList_Madd_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %value = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %value
    %3 = load %"_Pshadow_Putility_CArrayList"** %this
    %4 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %3, i32 0, i32 4
    %5 = load %"_Pshadow_Putility_CArrayList"** %this
    %6 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %5, i32 0, i32 1
    %7 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %6
    %8 = load %int* %4
    %9 = load %"_Pshadow_Putility_CArrayList"** %this
    %10 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %9, i32 0, i32 2
    %11 = load %"_Pshadow_Pstandard_CClass"** %10
    %12 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %7, 1
    %13 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %14 = bitcast %"_Pshadow_Pstandard_CObject"* %13 to [1 x %int]*
    store [1 x %int] %12, [1 x %int]* %14
    %15 = getelementptr inbounds [1 x %int]* %14, i32 0, i32 0
    %16 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %15, 0
    %17 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %7, 0
    %18 = bitcast %"_Pshadow_Pstandard_CObject"** %17 to %"_Pshadow_Pstandard_CObject"*
    %19 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %20 = bitcast %"_Pshadow_Pstandard_CObject"* %19 to %"_Pshadow_Pstandard_CArray"*
    %21 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %20, %"_Pshadow_Pstandard_CClass"* %11, { %int*, [1 x %int] } %16, %"_Pshadow_Pstandard_CObject"* %18)
    %22 = getelementptr %"_Pshadow_Pstandard_CArray"* %21, i32 0, i32 0
    %23 = load %"_Pshadow_Pstandard_CArray_Mclass"** %22
    %24 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %23, i32 0, i32 13
    %25 = load %int (%"_Pshadow_Pstandard_CArray"*)** %24
    %26 = load %"_Pshadow_Putility_CArrayList"** %this
    %27 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %26, i32 0, i32 2
    %28 = load %"_Pshadow_Pstandard_CClass"** %27
    %29 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %7, 1
    %30 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %31 = bitcast %"_Pshadow_Pstandard_CObject"* %30 to [1 x %int]*
    store [1 x %int] %29, [1 x %int]* %31
    %32 = getelementptr inbounds [1 x %int]* %31, i32 0, i32 0
    %33 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %32, 0
    %34 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %7, 0
    %35 = bitcast %"_Pshadow_Pstandard_CObject"** %34 to %"_Pshadow_Pstandard_CObject"*
    %36 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %37 = bitcast %"_Pshadow_Pstandard_CObject"* %36 to %"_Pshadow_Pstandard_CArray"*
    %38 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %37, %"_Pshadow_Pstandard_CClass"* %28, { %int*, [1 x %int] } %33, %"_Pshadow_Pstandard_CObject"* %35)
    %39 = call %int %25(%"_Pshadow_Pstandard_CArray"* %38)
    %40 = icmp sge %int %8, %39
    br %boolean %40, label %_label45, label %_label46
_label45:
    %41 = load %"_Pshadow_Putility_CArrayList"** %this
    %42 = load %"_Pshadow_Putility_CArrayList"** %this
    call void @"_Pshadow_Putility_CArrayList_Mgrow"(%"_Pshadow_Putility_CArrayList"* %42)
    br label %_label47
_label46:
    br label %_label47
_label47:
    %43 = load %"_Pshadow_Putility_CArrayList"** %this
    %44 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %43, i32 0, i32 1
    %45 = load %"_Pshadow_Putility_CArrayList"** %this
    %46 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %45, i32 0, i32 4
    %47 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %44
    %48 = load %int* %46
    %49 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %47, 0
    %50 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %49, %int %48
    %51 = load %"_Pshadow_Pstandard_CObject"** %value
    store %"_Pshadow_Pstandard_CObject"* %51, %"_Pshadow_Pstandard_CObject"** %50
    %52 = load %"_Pshadow_Putility_CArrayList"** %this
    %53 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %52, i32 0, i32 4
    %54 = load %int* %53
    %55 = add %int %54, 1
    store %int %55, %int* %53
    %56 = load %"_Pshadow_Putility_CArrayList"** %this
    %57 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %56, i32 0, i32 3
    %58 = load %int* %57
    %59 = add %int %58, 1
    store %int %59, %int* %57
    ret %boolean true
}

define %"_Pshadow_Putility_CArrayList"* @"_Pshadow_Putility_CArrayList_Mcreate"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    %V = alloca %"_Pshadow_Pstandard_CClass"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %V
    %3 = load %"_Pshadow_Putility_CArrayList"** %this
    %4 = bitcast %"_Pshadow_Putility_CArrayList"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %0, i32 0, i32 0
    store %"_Pshadow_Putility_CArrayList_Mclass"* @"_Pshadow_Putility_CArrayList_Mclass", %"_Pshadow_Putility_CArrayList_Mclass"** %6
    %7 = load %"_Pshadow_Putility_CArrayList"** %this
    %8 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %7, i32 0, i32 2
    %9 = load %"_Pshadow_Pstandard_CClass"** %V
    store %"_Pshadow_Pstandard_CClass"* %9, %"_Pshadow_Pstandard_CClass"** %8
    %10 = load %"_Pshadow_Putility_CArrayList"** %this
    %11 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %10, i32 0, i32 3
    store %int 0, %int* %11
    %12 = load %"_Pshadow_Putility_CArrayList"** %this
    %13 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %12, i32 0, i32 1
    %14 = load %"_Pshadow_Pstandard_CClass"** %V
    %15 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* %14, %int 10)
    %16 = bitcast %"_Pshadow_Pstandard_CObject"* %15 to %"_Pshadow_Pstandard_CObject"**
    %17 = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } undef, %"_Pshadow_Pstandard_CObject"** %16, 0
    %18 = insertvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %17, %int 10, 1, 0
    store { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %18, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %13
    %19 = load %"_Pshadow_Putility_CArrayList"** %this
    %20 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %19, i32 0, i32 4
    store %int 0, %int* %20
    ret %"_Pshadow_Putility_CArrayList"* %0
}

define %int @"_Pshadow_Putility_CArrayList_Msize"(%"_Pshadow_Putility_CArrayList"*) {
    %this = alloca %"_Pshadow_Putility_CArrayList"*
    store %"_Pshadow_Putility_CArrayList"* %0, %"_Pshadow_Putility_CArrayList"** %this
    %2 = load %"_Pshadow_Putility_CArrayList"** %this
    %3 = getelementptr inbounds %"_Pshadow_Putility_CArrayList"* %2, i32 0, i32 4
    %4 = load %int* %3
    ret %int %4
}

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CException_Mmessage"(%"_Pshadow_Pstandard_CException"*)
declare void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)

declare %int @"_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %uint @"_Pshadow_Pstandard_Cint_Mabs"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mhash"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cint_MtoString"(%"_Pshadow_Pstandard_Cint"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cint_MtoString_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cint"*, %uint)
declare %int @"_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %"_Pshadow_Pstandard_Cint"* @"_Pshadow_Pstandard_Cint_Mcreate"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)

declare %ushort @"_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %int @"_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %ushort @"_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)
declare %"_Pshadow_Pstandard_Cushort"* @"_Pshadow_Pstandard_Cushort_Mcreate"(%"_Pshadow_Pstandard_Cushort"*)
declare %ushort @"_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort"(%"_Pshadow_Pstandard_Cushort"*, %ushort)

declare %code @"_Pshadow_Pstandard_Ccode_MtoUpperCase"(%"_Pshadow_Pstandard_Ccode"*)
declare %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Ccode_MtoString"(%"_Pshadow_Pstandard_Ccode"*)
declare %"_Pshadow_Pstandard_Ccode"* @"_Pshadow_Pstandard_Ccode_Mcreate"(%"_Pshadow_Pstandard_Ccode"*)

declare %double @"_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %int @"_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %double @"_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %double @"_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cdouble_MtoString"(%"_Pshadow_Pstandard_Cdouble"*)
declare %double @"_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)
declare %"_Pshadow_Pstandard_Cdouble"* @"_Pshadow_Pstandard_Cdouble_Mcreate"(%"_Pshadow_Pstandard_Cdouble"*)
declare %double @"_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble"(%"_Pshadow_Pstandard_Cdouble"*, %double)

declare %long @"_Pshadow_Pstandard_Clong_Mmin_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %ulong @"_Pshadow_Pstandard_Clong_Mabs"(%"_Pshadow_Pstandard_Clong"*)
declare %long @"_Pshadow_Pstandard_Clong_Mmax_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %int @"_Pshadow_Pstandard_Clong_Mhash"(%"_Pshadow_Pstandard_Clong"*)
declare %int @"_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %long @"_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Clong_MtoString"(%"_Pshadow_Pstandard_Clong"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Clong_MtoString_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Clong"*, %ulong)
declare %long @"_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)
declare %"_Pshadow_Pstandard_Clong"* @"_Pshadow_Pstandard_Clong_Mcreate"(%"_Pshadow_Pstandard_Clong"*)
declare %long @"_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong"(%"_Pshadow_Pstandard_Clong"*, %long)

declare %float @"_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %int @"_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %float @"_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)
declare %"_Pshadow_Pstandard_Cfloat"* @"_Pshadow_Pstandard_Cfloat_Mcreate"(%"_Pshadow_Pstandard_Cfloat"*)
declare %float @"_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat"(%"_Pshadow_Pstandard_Cfloat"*, %float)

declare %short @"_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %int @"_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %short @"_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)
declare %"_Pshadow_Pstandard_Cshort"* @"_Pshadow_Pstandard_Cshort_Mcreate"(%"_Pshadow_Pstandard_Cshort"*)
declare %short @"_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort"(%"_Pshadow_Pstandard_Cshort"*, %short)

declare %boolean @"_Pshadow_Putility_CArrayList_IArrayListIterator_MhasNext"(%"_Pshadow_Putility_CArrayList_IArrayListIterator"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_IArrayListIterator_Mnext"(%"_Pshadow_Putility_CArrayList_IArrayListIterator"*)
declare %"_Pshadow_Putility_CArrayList_IArrayListIterator"* @"_Pshadow_Putility_CArrayList_IArrayListIterator_Mcreate"(%"_Pshadow_Putility_CArrayList_IArrayListIterator"*, %"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)

declare %byte @"_Pshadow_Pstandard_Cbyte_Mmin_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %ubyte @"_Pshadow_Pstandard_Cbyte_Mabs"(%"_Pshadow_Pstandard_Cbyte"*)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmax_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %int @"_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cbyte_MtoString"(%"_Pshadow_Pstandard_Cbyte"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cbyte_MtoString_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cbyte"*, %ubyte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %"_Pshadow_Pstandard_Cbyte"* @"_Pshadow_Pstandard_Cbyte_Mcreate"(%"_Pshadow_Pstandard_Cbyte"*)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)

declare %boolean @"_Pshadow_Pstandard_CString_MisEmpty"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoUpperCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CString_Miterator"(%"_Pshadow_Pstandard_CString"*)
declare %boolean @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoLowerCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int, %int)
declare %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int)
declare { %byte*, [1 x %int] } @"_Pshadow_Pstandard_CString_Mchars"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1"(%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"*)

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisInterface"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisPrimitive"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })
declare void @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*)
declare %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*)
declare { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CArray_MtoString"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })
declare %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"*)

declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %int)

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cboolean_MtoString"(%"_Pshadow_Pstandard_Cboolean"*)
declare %"_Pshadow_Pstandard_Cboolean"* @"_Pshadow_Pstandard_Cboolean_Mcreate"(%"_Pshadow_Pstandard_Cboolean"*)

declare %"_P_CUnexpectedNullException"* @"_P_CUnexpectedNullException_Mcreate"(%"_P_CUnexpectedNullException"*)

declare %ulong @"_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %int @"_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Culong_MtoString"(%"_Pshadow_Pstandard_Culong"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Culong_MtoString_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %ulong @"_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)
declare %"_Pshadow_Pstandard_Culong"* @"_Pshadow_Pstandard_Culong_Mcreate"(%"_Pshadow_Pstandard_Culong"*)
declare %ulong @"_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong"(%"_Pshadow_Pstandard_Culong"*, %ulong)

declare %ubyte @"_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %int @"_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cubyte_MtoString"(%"_Pshadow_Pstandard_Cubyte"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cubyte_MtoString_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)
declare %"_Pshadow_Pstandard_Cubyte"* @"_Pshadow_Pstandard_Cubyte_Mcreate"(%"_Pshadow_Pstandard_Cubyte"*)
declare %ubyte @"_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte"(%"_Pshadow_Pstandard_Cubyte"*, %ubyte)

declare %uint @"_Pshadow_Pstandard_Cuint_Mmin_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmax_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %int @"_Pshadow_Pstandard_Cuint_Mhash"(%"_Pshadow_Pstandard_Cuint"*)
declare %int @"_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cuint_MtoString"(%"_Pshadow_Pstandard_Cuint"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cuint_MtoString_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %uint @"_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)
declare %"_Pshadow_Pstandard_Cuint"* @"_Pshadow_Pstandard_Cuint_Mcreate"(%"_Pshadow_Pstandard_Cuint"*)
declare %uint @"_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint"(%"_Pshadow_Pstandard_Cuint"*, %uint)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CObject_MgetClass"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mfreeze"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CObject_MtoString"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcopy"(%"_Pshadow_Pstandard_CObject"*)

@_array0 = private unnamed_addr constant [27 x %ubyte] c"shadow.utility@ArrayList<V>"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([27 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 27] }, %boolean true }
