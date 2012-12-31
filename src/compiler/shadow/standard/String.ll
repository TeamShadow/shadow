; shadow.standard@String

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

%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CIllegalArgumentException"* (%"_Pshadow_Pstandard_CIllegalArgumentException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CIllegalArgumentException" = type { %"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cushort"* (%"_Pshadow_Pstandard_Cushort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %int (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"*, %ushort }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Ccode"* (%"_Pshadow_Pstandard_Ccode"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"*, %code }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cdouble"* (%"_Pshadow_Pstandard_Cdouble"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cdouble"*)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %int (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"*, %double }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Clong"* (%"_Pshadow_Pstandard_Clong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*)*, %ulong (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*, %ulong)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"*, %long }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cfloat"* (%"_Pshadow_Pstandard_Cfloat"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %int (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"*, %float }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString_IStringIterator"* (%"_Pshadow_Pstandard_CString_IStringIterator"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)*, %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)* }
%"_Pshadow_Pstandard_CString_IStringIterator" = type { %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }
%"_Pshadow_Pstandard_CMutableString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)* }
%"_Pshadow_Pstandard_CMutableString" = type { %"_Pshadow_Pstandard_CMutableString_Mclass"* }

@"_Pshadow_Pstandard_CString_Mclass" = global %"_Pshadow_Pstandard_CString_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CString"* getelementptr (%"_Pshadow_Pstandard_CString"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CString"** getelementptr (%"_Pshadow_Pstandard_CString"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoString", { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mchars", %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString", %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString", %byte (%"_Pshadow_Pstandard_CString"*, %int)* @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Miterator", %int (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mlength", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoLowerCase", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoUpperCase" }
@"_Pshadow_Pstandard_CException_Mclass" = external global %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_CIllegalArgumentException_Mclass" = external global %"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external global %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external global %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external global %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external global %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external global %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = external global %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external global %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external global %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external global %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external global %"_Pshadow_Pstandard_CObject_Mclass"
@"_Pshadow_Pstandard_CMutableString_Mclass" = external global %"_Pshadow_Pstandard_CMutableString_Mclass"

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoUpperCase"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %iterator = alloca %"_Pshadow_Pstandard_CString_IStringIterator"*
    %string = alloca %"_Pshadow_Pstandard_CMutableString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* @"_Pshadow_Pstandard_CString_IStringIterator_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Pstandard_CString_IStringIterator"*
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = call %"_Pshadow_Pstandard_CString_IStringIterator"* @"_Pshadow_Pstandard_CString_IStringIterator_Mcreate"(%"_Pshadow_Pstandard_CString_IStringIterator"* %3, %"_Pshadow_Pstandard_CString"* %4)
    store %"_Pshadow_Pstandard_CString_IStringIterator"* %3, %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %6 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CMutableString_Mclass"* @"_Pshadow_Pstandard_CMutableString_Mclass", i32 0, i32 0))
    %7 = bitcast %"_Pshadow_Pstandard_CObject"* %6 to %"_Pshadow_Pstandard_CMutableString"*
    %8 = call %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate"(%"_Pshadow_Pstandard_CMutableString"* %7)
    store %"_Pshadow_Pstandard_CMutableString"* %7, %"_Pshadow_Pstandard_CMutableString"** %string
    br label %_label1
_label0:
    %9 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %10 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %11, i32 0, i32 9
    %13 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %12
    %14 = call %code %13(%"_Pshadow_Pstandard_CString_IStringIterator"* %9)
    %15 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %16 = bitcast %"_Pshadow_Pstandard_CObject"*%15 to %"_Pshadow_Pstandard_Ccode"*
    %17 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%16, i32 0, i32 1
    store %code %14, %code* %17
    %18 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %16)
    %19 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %20 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %21 = bitcast %"_Pshadow_Pstandard_CObject"*%20 to %"_Pshadow_Pstandard_Ccode"*
    %22 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%21, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %22
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %21, i32 0, i32 1
    store %code %18, %code* %23
    %24 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %19, i32 0, i32 0
    %25 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %24
    %26 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %25, i32 0, i32 8
    %27 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %26
    %28 = call %"_Pshadow_Pstandard_CMutableString"* %27(%"_Pshadow_Pstandard_CMutableString"* %19, %"_Pshadow_Pstandard_CObject"* %20)
    br label %_label1
_label1:
    %29 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %30 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %29, i32 0, i32 0
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %30
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %31, i32 0, i32 8
    %33 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %32
    %34 = call %boolean %33(%"_Pshadow_Pstandard_CString_IStringIterator"* %29)
    br %boolean %34, label %_label0, label %_label2
_label2:
    %35 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %36 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %35, i32 0, i32 0
    %37 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %36
    %38 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %37, i32 0, i32 7
    %39 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %38
    %40 = call %"_Pshadow_Pstandard_CString"* %39(%"_Pshadow_Pstandard_CMutableString"* %35)
    ret %"_Pshadow_Pstandard_CString"* %40
}

define %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CString_Miterator"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* @"_Pshadow_Pstandard_CString_IStringIterator_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Pstandard_CString_IStringIterator"*
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = call %"_Pshadow_Pstandard_CString_IStringIterator"* @"_Pshadow_Pstandard_CString_IStringIterator_Mcreate"(%"_Pshadow_Pstandard_CString_IStringIterator"* %3, %"_Pshadow_Pstandard_CString"* %4)
    %6 = bitcast %"_Pshadow_Pstandard_CString_IStringIterator"* %3 to %"_Pshadow_Pstandard_CObject"*
    ret %"_Pshadow_Pstandard_CObject"* %6
}

define %boolean @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = load %"_Pshadow_Pstandard_CString"** %other
    %5 = getelementptr %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pstandard_CString_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %6, i32 0, i32 17
    %8 = load %int (%"_Pshadow_Pstandard_CString"*)** %7
    %9 = call %int %8(%"_Pshadow_Pstandard_CString"* %3)
    %10 = getelementptr %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CString_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %11, i32 0, i32 17
    %13 = load %int (%"_Pshadow_Pstandard_CString"*)** %12
    %14 = call %int %13(%"_Pshadow_Pstandard_CString"* %4)
    %15 = icmp ne %int %9, %14
    store %boolean %15, %boolean* %_temp
    br %boolean %15, label %_label4, label %_label3
_label4:
    %16 = load %"_Pshadow_Pstandard_CString"** %this
    %17 = load %"_Pshadow_Pstandard_CString"** %other
    %18 = call %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %16, %"_Pshadow_Pstandard_CString"* %17)
    %19 = icmp eq %int %18, 0
    store %boolean %19, %boolean* %_temp
    br label %_label3
_label3:
    %20 = load %boolean* %_temp
    ret %boolean %20
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoLowerCase"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %iterator = alloca %"_Pshadow_Pstandard_CString_IStringIterator"*
    %string = alloca %"_Pshadow_Pstandard_CMutableString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* @"_Pshadow_Pstandard_CString_IStringIterator_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Pstandard_CString_IStringIterator"*
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = call %"_Pshadow_Pstandard_CString_IStringIterator"* @"_Pshadow_Pstandard_CString_IStringIterator_Mcreate"(%"_Pshadow_Pstandard_CString_IStringIterator"* %3, %"_Pshadow_Pstandard_CString"* %4)
    store %"_Pshadow_Pstandard_CString_IStringIterator"* %3, %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %6 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CMutableString_Mclass"* @"_Pshadow_Pstandard_CMutableString_Mclass", i32 0, i32 0))
    %7 = bitcast %"_Pshadow_Pstandard_CObject"* %6 to %"_Pshadow_Pstandard_CMutableString"*
    %8 = call %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate"(%"_Pshadow_Pstandard_CMutableString"* %7)
    store %"_Pshadow_Pstandard_CMutableString"* %7, %"_Pshadow_Pstandard_CMutableString"** %string
    br label %_label6
_label5:
    %9 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %10 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %11, i32 0, i32 9
    %13 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %12
    %14 = call %code %13(%"_Pshadow_Pstandard_CString_IStringIterator"* %9)
    %15 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %16 = bitcast %"_Pshadow_Pstandard_CObject"*%15 to %"_Pshadow_Pstandard_Ccode"*
    %17 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%16, i32 0, i32 1
    store %code %14, %code* %17
    %18 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %16)
    %19 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %20 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %21 = bitcast %"_Pshadow_Pstandard_CObject"*%20 to %"_Pshadow_Pstandard_Ccode"*
    %22 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%21, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %22
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %21, i32 0, i32 1
    store %code %18, %code* %23
    %24 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %19, i32 0, i32 0
    %25 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %24
    %26 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %25, i32 0, i32 8
    %27 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %26
    %28 = call %"_Pshadow_Pstandard_CMutableString"* %27(%"_Pshadow_Pstandard_CMutableString"* %19, %"_Pshadow_Pstandard_CObject"* %20)
    br label %_label6
_label6:
    %29 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %30 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %29, i32 0, i32 0
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %30
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %31, i32 0, i32 8
    %33 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %32
    %34 = call %boolean %33(%"_Pshadow_Pstandard_CString_IStringIterator"* %29)
    br %boolean %34, label %_label5, label %_label7
_label7:
    %35 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %36 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %35, i32 0, i32 0
    %37 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %36
    %38 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %37, i32 0, i32 7
    %39 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %38
    %40 = call %"_Pshadow_Pstandard_CString"* %39(%"_Pshadow_Pstandard_CMutableString"* %35)
    ret %"_Pshadow_Pstandard_CString"* %40
}

define %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %i = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    store %int 0, %int* %i
    br label %_label9
_label8:
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = load %int* %i
    %5 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %3, %int %4)
    %6 = load %"_Pshadow_Pstandard_CString"** %other
    %7 = load %int* %i
    %8 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %6, %int %7)
    %9 = icmp ne %byte %5, %8
    br %boolean %9, label %_label11, label %_label12
_label11:
    %10 = load %"_Pshadow_Pstandard_CString"** %this
    %11 = load %int* %i
    %12 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %10, %int %11)
    %13 = load %"_Pshadow_Pstandard_CString"** %other
    %14 = load %int* %i
    %15 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %13, %int %14)
    %16 = sub %byte %12, %15
    %17 = sext %byte %16 to %int
    ret %int %17
    br label %_label13
_label12:
    br label %_label13
_label13:
    %19 = load %int* %i
    %20 = add %int %19, 1
    store %int %20, %int* %i
    br label %_label9
_label9:
    %21 = load %"_Pshadow_Pstandard_CString"** %this
    %22 = load %int* %i
    %23 = getelementptr %"_Pshadow_Pstandard_CString"* %21, i32 0, i32 0
    %24 = load %"_Pshadow_Pstandard_CString_Mclass"** %23
    %25 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %24, i32 0, i32 17
    %26 = load %int (%"_Pshadow_Pstandard_CString"*)** %25
    %27 = call %int %26(%"_Pshadow_Pstandard_CString"* %21)
    %28 = icmp slt %int %22, %27
    store %boolean %28, %boolean* %_temp
    br %boolean %28, label %_label15, label %_label14
_label15:
    %29 = load %"_Pshadow_Pstandard_CString"** %other
    %30 = load %int* %i
    %31 = getelementptr %"_Pshadow_Pstandard_CString"* %29, i32 0, i32 0
    %32 = load %"_Pshadow_Pstandard_CString_Mclass"** %31
    %33 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %32, i32 0, i32 17
    %34 = load %int (%"_Pshadow_Pstandard_CString"*)** %33
    %35 = call %int %34(%"_Pshadow_Pstandard_CString"* %29)
    %36 = icmp slt %int %30, %35
    store %boolean %36, %boolean* %_temp
    br label %_label14
_label14:
    %37 = load %boolean* %_temp
    br %boolean %37, label %_label8, label %_label10
_label10:
    %38 = load %"_Pshadow_Pstandard_CString"** %this
    %39 = load %"_Pshadow_Pstandard_CString"** %other
    %40 = getelementptr %"_Pshadow_Pstandard_CString"* %38, i32 0, i32 0
    %41 = load %"_Pshadow_Pstandard_CString_Mclass"** %40
    %42 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %41, i32 0, i32 17
    %43 = load %int (%"_Pshadow_Pstandard_CString"*)** %42
    %44 = call %int %43(%"_Pshadow_Pstandard_CString"* %38)
    %45 = getelementptr %"_Pshadow_Pstandard_CString"* %39, i32 0, i32 0
    %46 = load %"_Pshadow_Pstandard_CString_Mclass"** %45
    %47 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %46, i32 0, i32 17
    %48 = load %int (%"_Pshadow_Pstandard_CString"*)** %47
    %49 = call %int %48(%"_Pshadow_Pstandard_CString"* %39)
    %50 = sub %int %44, %49
    ret %int %50
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int, %int) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %start = alloca %int
    %end = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %int %1, %int* %start
    store %int %2, %int* %end
    %4 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0))
    %5 = bitcast %"_Pshadow_Pstandard_CObject"* %4 to %"_Pshadow_Pstandard_CString"*
    %6 = load %"_Pshadow_Pstandard_CString"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %6, i32 0, i32 1
    %8 = load { %byte*, [1 x %int] }* %7
    %9 = extractvalue { %byte*, [1 x %int] } %8, 1
    %10 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to [1 x %int]*
    store [1 x %int] %9, [1 x %int]* %11
    %12 = getelementptr inbounds [1 x %int]* %11, i32 0, i32 0
    %13 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %12, 0
    %14 = extractvalue { %byte*, [1 x %int] } %8, 0
    %15 = bitcast %byte* %14 to %"_Pshadow_Pstandard_CObject"*
    %16 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %17 = bitcast %"_Pshadow_Pstandard_CObject"* %16 to %"_Pshadow_Pstandard_CArray"*
    %18 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %17, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %13, %"_Pshadow_Pstandard_CObject"* %15)
    %19 = load %int* %start
    %20 = load %int* %end
    %21 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"* %18, %int %19, %int %20)
    %22 = bitcast %"_Pshadow_Pstandard_CArray"* %21 to %"_Pshadow_Pstandard_CObject"*
    %23 = bitcast %"_Pshadow_Pstandard_CObject"* %22 to %"_Pshadow_Pstandard_CArray"*
    %24 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %23, i32 0, i32 3
    %25 = load %"_Pshadow_Pstandard_CObject"** %24
    %26 = bitcast %"_Pshadow_Pstandard_CObject"* %25 to %byte*
    %27 = insertvalue { %byte*, [1 x %int] } undef, %byte* %26, 0
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %23, i32 0, i32 1, i32 0
    %29 = load %int** %28
    %30 = bitcast %int* %29 to [1 x %int]*
    %31 = load [1 x %int]* %30
    %32 = insertvalue { %byte*, [1 x %int] } %27, [1 x %int] %31, 1
    %33 = load %"_Pshadow_Pstandard_CString"** %this
    %34 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %33, i32 0, i32 2
    %35 = load %boolean* %34
    %36 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"* %5, { %byte*, [1 x %int] } %32, %boolean %35)
    ret %"_Pshadow_Pstandard_CString"* %5
}

define %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %2, i32 0, i32 1
    %4 = load { %byte*, [1 x %int] }* %3
    %5 = extractvalue { %byte*, [1 x %int] } %4, 1, 0
    ret %int %5
}

define %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %index = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %int %1, %int* %index
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 1
    %5 = load { %byte*, [1 x %int] }* %4
    %6 = load %int* %index
    %7 = extractvalue { %byte*, [1 x %int] } %5, 0
    %8 = getelementptr inbounds %byte* %7, %int %6
    %9 = load %byte* %8
    ret %byte %9
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    ret %"_Pshadow_Pstandard_CString"* %2
}

define { %byte*, [1 x %int] } @"_Pshadow_Pstandard_CString_Mchars"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %2, i32 0, i32 1
    %4 = load { %byte*, [1 x %int] }* %3
    %5 = extractvalue { %byte*, [1 x %int] } %4, 1
    %6 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %7 = bitcast %"_Pshadow_Pstandard_CObject"* %6 to [1 x %int]*
    store [1 x %int] %5, [1 x %int]* %7
    %8 = getelementptr inbounds [1 x %int]* %7, i32 0, i32 0
    %9 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %8, 0
    %10 = extractvalue { %byte*, [1 x %int] } %4, 0
    %11 = bitcast %byte* %10 to %"_Pshadow_Pstandard_CObject"*
    %12 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %"_Pshadow_Pstandard_CArray"*
    %14 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %13, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %9, %"_Pshadow_Pstandard_CObject"* %11)
    %15 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"* %14)
    %16 = bitcast %"_Pshadow_Pstandard_CArray"* %15 to %"_Pshadow_Pstandard_CObject"*
    %17 = bitcast %"_Pshadow_Pstandard_CObject"* %16 to %"_Pshadow_Pstandard_CArray"*
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 3
    %19 = load %"_Pshadow_Pstandard_CObject"** %18
    %20 = bitcast %"_Pshadow_Pstandard_CObject"* %19 to %byte*
    %21 = insertvalue { %byte*, [1 x %int] } undef, %byte* %20, 0
    %22 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 1, i32 0
    %23 = load %int** %22
    %24 = bitcast %int* %23 to [1 x %int]*
    %25 = load [1 x %int]* %24
    %26 = insertvalue { %byte*, [1 x %int] } %21, [1 x %int] %25, 1
    ret { %byte*, [1 x %int] } %26
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %2 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %2
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 2
    store %boolean true, %boolean* %4
    %5 = load %"_Pshadow_Pstandard_CString"** %this
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %5, i32 0, i32 1
    %7 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %8 = bitcast %"_Pshadow_Pstandard_CObject"* %7 to %byte*
    %9 = insertvalue { %byte*, [1 x %int] } undef, %byte* %8, 0
    %10 = insertvalue { %byte*, [1 x %int] } %9, %int 0, 1, 0
    store { %byte*, [1 x %int] } %10, { %byte*, [1 x %int] }* %6
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %byte*, [1 x %int] }
    %ascii = alloca %byte
    %i = alloca %int
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %3
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %byte*, [1 x %int] } %1, { %byte*, [1 x %int] }* %data
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 2
    store %boolean true, %boolean* %5
    %6 = load %"_Pshadow_Pstandard_CString"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %6, i32 0, i32 1
    %8 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %byte*
    %10 = insertvalue { %byte*, [1 x %int] } undef, %byte* %9, 0
    %11 = insertvalue { %byte*, [1 x %int] } %10, %int 0, 1, 0
    store { %byte*, [1 x %int] } %11, { %byte*, [1 x %int] }* %7
    store %byte 0, %byte* %ascii
    %12 = load { %byte*, [1 x %int] }* %data
    %13 = extractvalue { %byte*, [1 x %int] } %12, 1, 0
    %14 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %13)
    %15 = bitcast %"_Pshadow_Pstandard_CObject"* %14 to %byte*
    %16 = insertvalue { %byte*, [1 x %int] } undef, %byte* %15, 0
    %17 = insertvalue { %byte*, [1 x %int] } %16, %int %13, 1, 0
    %18 = load %"_Pshadow_Pstandard_CString"** %this
    %19 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %18, i32 0, i32 1
    store { %byte*, [1 x %int] } %17, { %byte*, [1 x %int] }* %19
    store %int 0, %int* %i
    br label %_label17
_label16:
    %20 = load { %byte*, [1 x %int] }* %data
    %21 = load %int* %i
    %22 = extractvalue { %byte*, [1 x %int] } %20, 0
    %23 = getelementptr inbounds %byte* %22, %int %21
    %24 = load %"_Pshadow_Pstandard_CString"** %this
    %25 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %24, i32 0, i32 1
    %26 = load { %byte*, [1 x %int] }* %25
    %27 = load %int* %i
    %28 = extractvalue { %byte*, [1 x %int] } %26, 0
    %29 = getelementptr inbounds %byte* %28, %int %27
    %30 = load %byte* %23
    store %byte %30, %byte* %29
    %31 = load { %byte*, [1 x %int] }* %data
    %32 = load %int* %i
    %33 = extractvalue { %byte*, [1 x %int] } %31, 0
    %34 = getelementptr inbounds %byte* %33, %int %32
    %35 = load %byte* %ascii
    %36 = load %byte* %34
    %37 = or %byte %35, %36
    store %byte %37, %byte* %ascii
    %38 = load %int* %i
    %39 = add %int %38, 1
    store %int %39, %int* %i
    br label %_label17
_label17:
    %40 = load { %byte*, [1 x %int] }* %data
    %41 = extractvalue { %byte*, [1 x %int] } %40, 1, 0
    %42 = load %int* %i
    %43 = icmp slt %int %42, %41
    br %boolean %43, label %_label16, label %_label18
_label18:
    %44 = load %byte* %ascii
    %45 = sext %byte %44 to %int
    %46 = icmp slt %int %45, 0
    %47 = load %"_Pshadow_Pstandard_CString"** %this
    %48 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %47, i32 0, i32 2
    store %boolean %46, %boolean* %48
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1"(%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %code*, [1 x %int] }
    %length = alloca %int
    %i = alloca %int
    %shift = alloca %int
    %i1 = alloca %int
    %j = alloca %int
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %3
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %code*, [1 x %int] } %1, { %code*, [1 x %int] }* %data
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 2
    store %boolean true, %boolean* %5
    %6 = load %"_Pshadow_Pstandard_CString"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %6, i32 0, i32 1
    %8 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %byte*
    %10 = insertvalue { %byte*, [1 x %int] } undef, %byte* %9, 0
    %11 = insertvalue { %byte*, [1 x %int] } %10, %int 0, 1, 0
    store { %byte*, [1 x %int] } %11, { %byte*, [1 x %int] }* %7
    %12 = load { %code*, [1 x %int] }* %data
    %13 = extractvalue { %code*, [1 x %int] } %12, 1, 0
    store %int %13, %int* %length
    store %int 0, %int* %i
    br label %_label20
_label19:
    %14 = load { %code*, [1 x %int] }* %data
    %15 = load %int* %i
    %16 = extractvalue { %code*, [1 x %int] } %14, 0
    %17 = getelementptr inbounds %code* %16, %int %15
    %18 = load %code* %17
    %19 = bitcast %code %18 to %int
    %20 = icmp slt %int %19, 0
    br %boolean %20, label %_label22, label %_label23
_label22:
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"* %21 to %"_Pshadow_Pstandard_CIllegalArgumentException"*
    %23 = call %"_Pshadow_Pstandard_CIllegalArgumentException"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mcreate"(%"_Pshadow_Pstandard_CIllegalArgumentException"* %22)
    br label %_label24
_label23:
    %24 = load { %code*, [1 x %int] }* %data
    %25 = load %int* %i
    %26 = extractvalue { %code*, [1 x %int] } %24, 0
    %27 = getelementptr inbounds %code* %26, %int %25
    %28 = shl %int 1, 7
    %29 = load %code* %27
    %30 = bitcast %code %29 to %int
    %31 = icmp sge %int %30, %28
    br %boolean %31, label %_label25, label %_label26
_label25:
    %32 = load %"_Pshadow_Pstandard_CString"** %this
    %33 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %32, i32 0, i32 2
    store %boolean false, %boolean* %33
    %34 = load %int* %length
    %35 = add %int %34, 1
    store %int %35, %int* %length
    store %int 11, %int* %shift
    br label %_label29
_label28:
    %36 = load %int* %length
    %37 = add %int %36, 1
    store %int %37, %int* %length
    %38 = load %int* %shift
    %39 = add %int %38, 5
    store %int %39, %int* %shift
    br label %_label29
_label29:
    %40 = load { %code*, [1 x %int] }* %data
    %41 = load %int* %i
    %42 = extractvalue { %code*, [1 x %int] } %40, 0
    %43 = getelementptr inbounds %code* %42, %int %41
    %44 = load %int* %shift
    %45 = shl %int 1, %44
    %46 = load %code* %43
    %47 = bitcast %code %46 to %int
    %48 = icmp sge %int %47, %45
    br %boolean %48, label %_label28, label %_label30
_label30:
    br label %_label27
_label26:
    br label %_label27
_label27:
    br label %_label24
_label24:
    %49 = load %int* %i
    %50 = add %int %49, 1
    store %int %50, %int* %i
    br label %_label20
_label20:
    %51 = load { %code*, [1 x %int] }* %data
    %52 = extractvalue { %code*, [1 x %int] } %51, 1, 0
    %53 = load %int* %i
    %54 = icmp slt %int %53, %52
    br %boolean %54, label %_label19, label %_label21
_label21:
    %55 = load %int* %length
    %56 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %55)
    %57 = bitcast %"_Pshadow_Pstandard_CObject"* %56 to %byte*
    %58 = insertvalue { %byte*, [1 x %int] } undef, %byte* %57, 0
    %59 = insertvalue { %byte*, [1 x %int] } %58, %int %55, 1, 0
    %60 = load %"_Pshadow_Pstandard_CString"** %this
    %61 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %60, i32 0, i32 1
    store { %byte*, [1 x %int] } %59, { %byte*, [1 x %int] }* %61
    store %int 0, %int* %i1
    store %int 0, %int* %j
    br label %_label32
_label31:
    %62 = load { %code*, [1 x %int] }* %data
    %63 = load %int* %i1
    %64 = extractvalue { %code*, [1 x %int] } %62, 0
    %65 = getelementptr inbounds %code* %64, %int %63
    %66 = shl %int 1, 7
    %67 = load %code* %65
    %68 = bitcast %code %67 to %int
    %69 = icmp slt %int %68, %66
    br %boolean %69, label %_label34, label %_label35
_label34:
    %70 = load { %code*, [1 x %int] }* %data
    %71 = load %int* %i1
    %72 = extractvalue { %code*, [1 x %int] } %70, 0
    %73 = getelementptr inbounds %code* %72, %int %71
    %74 = load %code* %73
    %75 = trunc %code %74 to %byte
    %76 = load %"_Pshadow_Pstandard_CString"** %this
    %77 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %76, i32 0, i32 1
    %78 = load %int* %j
    %79 = add %int %78, 0
    %80 = load { %byte*, [1 x %int] }* %77
    %81 = extractvalue { %byte*, [1 x %int] } %80, 0
    %82 = getelementptr inbounds %byte* %81, %int %79
    store %byte %75, %byte* %82
    %83 = load %int* %j
    %84 = add %int %83, 1
    store %int %84, %int* %j
    br label %_label36
_label35:
    %85 = load { %code*, [1 x %int] }* %data
    %86 = load %int* %i1
    %87 = extractvalue { %code*, [1 x %int] } %85, 0
    %88 = getelementptr inbounds %code* %87, %int %86
    %89 = shl %int 1, 11
    %90 = load %code* %88
    %91 = bitcast %code %90 to %int
    %92 = icmp slt %int %91, %89
    br %boolean %92, label %_label37, label %_label38
_label37:
    %93 = load { %code*, [1 x %int] }* %data
    %94 = load %int* %i1
    %95 = extractvalue { %code*, [1 x %int] } %93, 0
    %96 = getelementptr inbounds %code* %95, %int %94
    %97 = load %code* %96
    %98 = bitcast %code %97 to %int
    %99 = ashr %int %98, 6
    %100 = and %int %99, 31
    %101 = or %int %100, 192
    %102 = trunc %int %101 to %byte
    %103 = load %"_Pshadow_Pstandard_CString"** %this
    %104 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %103, i32 0, i32 1
    %105 = load %int* %j
    %106 = add %int %105, 0
    %107 = load { %byte*, [1 x %int] }* %104
    %108 = extractvalue { %byte*, [1 x %int] } %107, 0
    %109 = getelementptr inbounds %byte* %108, %int %106
    store %byte %102, %byte* %109
    %110 = load { %code*, [1 x %int] }* %data
    %111 = load %int* %i1
    %112 = extractvalue { %code*, [1 x %int] } %110, 0
    %113 = getelementptr inbounds %code* %112, %int %111
    %114 = load %code* %113
    %115 = bitcast %code %114 to %int
    %116 = ashr %int %115, 0
    %117 = and %int %116, 63
    %118 = or %int %117, 128
    %119 = trunc %int %118 to %byte
    %120 = load %"_Pshadow_Pstandard_CString"** %this
    %121 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %120, i32 0, i32 1
    %122 = load %int* %j
    %123 = add %int %122, 1
    %124 = load { %byte*, [1 x %int] }* %121
    %125 = extractvalue { %byte*, [1 x %int] } %124, 0
    %126 = getelementptr inbounds %byte* %125, %int %123
    store %byte %119, %byte* %126
    %127 = load %int* %j
    %128 = add %int %127, 2
    store %int %128, %int* %j
    br label %_label39
_label38:
    %129 = load { %code*, [1 x %int] }* %data
    %130 = load %int* %i1
    %131 = extractvalue { %code*, [1 x %int] } %129, 0
    %132 = getelementptr inbounds %code* %131, %int %130
    %133 = shl %int 1, 16
    %134 = load %code* %132
    %135 = bitcast %code %134 to %int
    %136 = icmp slt %int %135, %133
    br %boolean %136, label %_label40, label %_label41
_label40:
    %137 = load { %code*, [1 x %int] }* %data
    %138 = load %int* %i1
    %139 = extractvalue { %code*, [1 x %int] } %137, 0
    %140 = getelementptr inbounds %code* %139, %int %138
    %141 = load %code* %140
    %142 = bitcast %code %141 to %int
    %143 = ashr %int %142, 12
    %144 = and %int %143, 15
    %145 = or %int %144, 224
    %146 = trunc %int %145 to %byte
    %147 = load %"_Pshadow_Pstandard_CString"** %this
    %148 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %147, i32 0, i32 1
    %149 = load %int* %j
    %150 = add %int %149, 0
    %151 = load { %byte*, [1 x %int] }* %148
    %152 = extractvalue { %byte*, [1 x %int] } %151, 0
    %153 = getelementptr inbounds %byte* %152, %int %150
    store %byte %146, %byte* %153
    %154 = load { %code*, [1 x %int] }* %data
    %155 = load %int* %i1
    %156 = extractvalue { %code*, [1 x %int] } %154, 0
    %157 = getelementptr inbounds %code* %156, %int %155
    %158 = load %code* %157
    %159 = bitcast %code %158 to %int
    %160 = ashr %int %159, 6
    %161 = and %int %160, 63
    %162 = or %int %161, 128
    %163 = trunc %int %162 to %byte
    %164 = load %"_Pshadow_Pstandard_CString"** %this
    %165 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %164, i32 0, i32 1
    %166 = load %int* %j
    %167 = add %int %166, 1
    %168 = load { %byte*, [1 x %int] }* %165
    %169 = extractvalue { %byte*, [1 x %int] } %168, 0
    %170 = getelementptr inbounds %byte* %169, %int %167
    store %byte %163, %byte* %170
    %171 = load { %code*, [1 x %int] }* %data
    %172 = load %int* %i1
    %173 = extractvalue { %code*, [1 x %int] } %171, 0
    %174 = getelementptr inbounds %code* %173, %int %172
    %175 = load %code* %174
    %176 = bitcast %code %175 to %int
    %177 = ashr %int %176, 0
    %178 = and %int %177, 63
    %179 = or %int %178, 128
    %180 = trunc %int %179 to %byte
    %181 = load %"_Pshadow_Pstandard_CString"** %this
    %182 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %181, i32 0, i32 1
    %183 = load %int* %j
    %184 = add %int %183, 2
    %185 = load { %byte*, [1 x %int] }* %182
    %186 = extractvalue { %byte*, [1 x %int] } %185, 0
    %187 = getelementptr inbounds %byte* %186, %int %184
    store %byte %180, %byte* %187
    %188 = load %int* %j
    %189 = add %int %188, 3
    store %int %189, %int* %j
    br label %_label42
_label41:
    %190 = load { %code*, [1 x %int] }* %data
    %191 = load %int* %i1
    %192 = extractvalue { %code*, [1 x %int] } %190, 0
    %193 = getelementptr inbounds %code* %192, %int %191
    %194 = shl %int 1, 21
    %195 = load %code* %193
    %196 = bitcast %code %195 to %int
    %197 = icmp slt %int %196, %194
    br %boolean %197, label %_label43, label %_label44
_label43:
    %198 = load { %code*, [1 x %int] }* %data
    %199 = load %int* %i1
    %200 = extractvalue { %code*, [1 x %int] } %198, 0
    %201 = getelementptr inbounds %code* %200, %int %199
    %202 = load %code* %201
    %203 = bitcast %code %202 to %int
    %204 = ashr %int %203, 18
    %205 = and %int %204, 7
    %206 = or %int %205, 240
    %207 = trunc %int %206 to %byte
    %208 = load %"_Pshadow_Pstandard_CString"** %this
    %209 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %208, i32 0, i32 1
    %210 = load %int* %j
    %211 = add %int %210, 0
    %212 = load { %byte*, [1 x %int] }* %209
    %213 = extractvalue { %byte*, [1 x %int] } %212, 0
    %214 = getelementptr inbounds %byte* %213, %int %211
    store %byte %207, %byte* %214
    %215 = load { %code*, [1 x %int] }* %data
    %216 = load %int* %i1
    %217 = extractvalue { %code*, [1 x %int] } %215, 0
    %218 = getelementptr inbounds %code* %217, %int %216
    %219 = load %code* %218
    %220 = bitcast %code %219 to %int
    %221 = ashr %int %220, 12
    %222 = and %int %221, 63
    %223 = or %int %222, 128
    %224 = trunc %int %223 to %byte
    %225 = load %"_Pshadow_Pstandard_CString"** %this
    %226 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %225, i32 0, i32 1
    %227 = load %int* %j
    %228 = add %int %227, 1
    %229 = load { %byte*, [1 x %int] }* %226
    %230 = extractvalue { %byte*, [1 x %int] } %229, 0
    %231 = getelementptr inbounds %byte* %230, %int %228
    store %byte %224, %byte* %231
    %232 = load { %code*, [1 x %int] }* %data
    %233 = load %int* %i1
    %234 = extractvalue { %code*, [1 x %int] } %232, 0
    %235 = getelementptr inbounds %code* %234, %int %233
    %236 = load %code* %235
    %237 = bitcast %code %236 to %int
    %238 = ashr %int %237, 6
    %239 = and %int %238, 63
    %240 = or %int %239, 128
    %241 = trunc %int %240 to %byte
    %242 = load %"_Pshadow_Pstandard_CString"** %this
    %243 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %242, i32 0, i32 1
    %244 = load %int* %j
    %245 = add %int %244, 2
    %246 = load { %byte*, [1 x %int] }* %243
    %247 = extractvalue { %byte*, [1 x %int] } %246, 0
    %248 = getelementptr inbounds %byte* %247, %int %245
    store %byte %241, %byte* %248
    %249 = load { %code*, [1 x %int] }* %data
    %250 = load %int* %i1
    %251 = extractvalue { %code*, [1 x %int] } %249, 0
    %252 = getelementptr inbounds %code* %251, %int %250
    %253 = load %code* %252
    %254 = bitcast %code %253 to %int
    %255 = ashr %int %254, 0
    %256 = and %int %255, 63
    %257 = or %int %256, 128
    %258 = trunc %int %257 to %byte
    %259 = load %"_Pshadow_Pstandard_CString"** %this
    %260 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %259, i32 0, i32 1
    %261 = load %int* %j
    %262 = add %int %261, 3
    %263 = load { %byte*, [1 x %int] }* %260
    %264 = extractvalue { %byte*, [1 x %int] } %263, 0
    %265 = getelementptr inbounds %byte* %264, %int %262
    store %byte %258, %byte* %265
    %266 = load %int* %j
    %267 = add %int %266, 4
    store %int %267, %int* %j
    br label %_label45
_label44:
    %268 = load { %code*, [1 x %int] }* %data
    %269 = load %int* %i1
    %270 = extractvalue { %code*, [1 x %int] } %268, 0
    %271 = getelementptr inbounds %code* %270, %int %269
    %272 = shl %int 1, 26
    %273 = load %code* %271
    %274 = bitcast %code %273 to %int
    %275 = icmp slt %int %274, %272
    br %boolean %275, label %_label46, label %_label47
_label46:
    %276 = load { %code*, [1 x %int] }* %data
    %277 = load %int* %i1
    %278 = extractvalue { %code*, [1 x %int] } %276, 0
    %279 = getelementptr inbounds %code* %278, %int %277
    %280 = load %code* %279
    %281 = bitcast %code %280 to %int
    %282 = ashr %int %281, 24
    %283 = and %int %282, 3
    %284 = or %int %283, 248
    %285 = trunc %int %284 to %byte
    %286 = load %"_Pshadow_Pstandard_CString"** %this
    %287 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %286, i32 0, i32 1
    %288 = load %int* %j
    %289 = add %int %288, 0
    %290 = load { %byte*, [1 x %int] }* %287
    %291 = extractvalue { %byte*, [1 x %int] } %290, 0
    %292 = getelementptr inbounds %byte* %291, %int %289
    store %byte %285, %byte* %292
    %293 = load { %code*, [1 x %int] }* %data
    %294 = load %int* %i1
    %295 = extractvalue { %code*, [1 x %int] } %293, 0
    %296 = getelementptr inbounds %code* %295, %int %294
    %297 = load %code* %296
    %298 = bitcast %code %297 to %int
    %299 = ashr %int %298, 18
    %300 = and %int %299, 63
    %301 = or %int %300, 128
    %302 = trunc %int %301 to %byte
    %303 = load %"_Pshadow_Pstandard_CString"** %this
    %304 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %303, i32 0, i32 1
    %305 = load %int* %j
    %306 = add %int %305, 1
    %307 = load { %byte*, [1 x %int] }* %304
    %308 = extractvalue { %byte*, [1 x %int] } %307, 0
    %309 = getelementptr inbounds %byte* %308, %int %306
    store %byte %302, %byte* %309
    %310 = load { %code*, [1 x %int] }* %data
    %311 = load %int* %i1
    %312 = extractvalue { %code*, [1 x %int] } %310, 0
    %313 = getelementptr inbounds %code* %312, %int %311
    %314 = load %code* %313
    %315 = bitcast %code %314 to %int
    %316 = ashr %int %315, 12
    %317 = and %int %316, 63
    %318 = or %int %317, 128
    %319 = trunc %int %318 to %byte
    %320 = load %"_Pshadow_Pstandard_CString"** %this
    %321 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %320, i32 0, i32 1
    %322 = load %int* %j
    %323 = add %int %322, 2
    %324 = load { %byte*, [1 x %int] }* %321
    %325 = extractvalue { %byte*, [1 x %int] } %324, 0
    %326 = getelementptr inbounds %byte* %325, %int %323
    store %byte %319, %byte* %326
    %327 = load { %code*, [1 x %int] }* %data
    %328 = load %int* %i1
    %329 = extractvalue { %code*, [1 x %int] } %327, 0
    %330 = getelementptr inbounds %code* %329, %int %328
    %331 = load %code* %330
    %332 = bitcast %code %331 to %int
    %333 = ashr %int %332, 6
    %334 = and %int %333, 63
    %335 = or %int %334, 128
    %336 = trunc %int %335 to %byte
    %337 = load %"_Pshadow_Pstandard_CString"** %this
    %338 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %337, i32 0, i32 1
    %339 = load %int* %j
    %340 = add %int %339, 3
    %341 = load { %byte*, [1 x %int] }* %338
    %342 = extractvalue { %byte*, [1 x %int] } %341, 0
    %343 = getelementptr inbounds %byte* %342, %int %340
    store %byte %336, %byte* %343
    %344 = load { %code*, [1 x %int] }* %data
    %345 = load %int* %i1
    %346 = extractvalue { %code*, [1 x %int] } %344, 0
    %347 = getelementptr inbounds %code* %346, %int %345
    %348 = load %code* %347
    %349 = bitcast %code %348 to %int
    %350 = ashr %int %349, 0
    %351 = and %int %350, 63
    %352 = or %int %351, 128
    %353 = trunc %int %352 to %byte
    %354 = load %"_Pshadow_Pstandard_CString"** %this
    %355 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %354, i32 0, i32 1
    %356 = load %int* %j
    %357 = add %int %356, 4
    %358 = load { %byte*, [1 x %int] }* %355
    %359 = extractvalue { %byte*, [1 x %int] } %358, 0
    %360 = getelementptr inbounds %byte* %359, %int %357
    store %byte %353, %byte* %360
    %361 = load %int* %j
    %362 = add %int %361, 5
    store %int %362, %int* %j
    br label %_label48
_label47:
    %363 = load { %code*, [1 x %int] }* %data
    %364 = load %int* %i1
    %365 = extractvalue { %code*, [1 x %int] } %363, 0
    %366 = getelementptr inbounds %code* %365, %int %364
    %367 = shl %int 1, 31
    %368 = load %code* %366
    %369 = bitcast %code %368 to %int
    %370 = icmp slt %int %369, %367
    br %boolean %370, label %_label49, label %_label50
_label49:
    %371 = load { %code*, [1 x %int] }* %data
    %372 = load %int* %i1
    %373 = extractvalue { %code*, [1 x %int] } %371, 0
    %374 = getelementptr inbounds %code* %373, %int %372
    %375 = load %code* %374
    %376 = bitcast %code %375 to %int
    %377 = ashr %int %376, 30
    %378 = and %int %377, 1
    %379 = or %int %378, 252
    %380 = trunc %int %379 to %byte
    %381 = load %"_Pshadow_Pstandard_CString"** %this
    %382 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %381, i32 0, i32 1
    %383 = load %int* %j
    %384 = add %int %383, 0
    %385 = load { %byte*, [1 x %int] }* %382
    %386 = extractvalue { %byte*, [1 x %int] } %385, 0
    %387 = getelementptr inbounds %byte* %386, %int %384
    store %byte %380, %byte* %387
    %388 = load { %code*, [1 x %int] }* %data
    %389 = load %int* %i1
    %390 = extractvalue { %code*, [1 x %int] } %388, 0
    %391 = getelementptr inbounds %code* %390, %int %389
    %392 = load %code* %391
    %393 = bitcast %code %392 to %int
    %394 = ashr %int %393, 24
    %395 = and %int %394, 63
    %396 = or %int %395, 128
    %397 = trunc %int %396 to %byte
    %398 = load %"_Pshadow_Pstandard_CString"** %this
    %399 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %398, i32 0, i32 1
    %400 = load %int* %j
    %401 = add %int %400, 1
    %402 = load { %byte*, [1 x %int] }* %399
    %403 = extractvalue { %byte*, [1 x %int] } %402, 0
    %404 = getelementptr inbounds %byte* %403, %int %401
    store %byte %397, %byte* %404
    %405 = load { %code*, [1 x %int] }* %data
    %406 = load %int* %i1
    %407 = extractvalue { %code*, [1 x %int] } %405, 0
    %408 = getelementptr inbounds %code* %407, %int %406
    %409 = load %code* %408
    %410 = bitcast %code %409 to %int
    %411 = ashr %int %410, 18
    %412 = and %int %411, 63
    %413 = or %int %412, 128
    %414 = trunc %int %413 to %byte
    %415 = load %"_Pshadow_Pstandard_CString"** %this
    %416 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %415, i32 0, i32 1
    %417 = load %int* %j
    %418 = add %int %417, 2
    %419 = load { %byte*, [1 x %int] }* %416
    %420 = extractvalue { %byte*, [1 x %int] } %419, 0
    %421 = getelementptr inbounds %byte* %420, %int %418
    store %byte %414, %byte* %421
    %422 = load { %code*, [1 x %int] }* %data
    %423 = load %int* %i1
    %424 = extractvalue { %code*, [1 x %int] } %422, 0
    %425 = getelementptr inbounds %code* %424, %int %423
    %426 = load %code* %425
    %427 = bitcast %code %426 to %int
    %428 = ashr %int %427, 12
    %429 = and %int %428, 63
    %430 = or %int %429, 128
    %431 = trunc %int %430 to %byte
    %432 = load %"_Pshadow_Pstandard_CString"** %this
    %433 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %432, i32 0, i32 1
    %434 = load %int* %j
    %435 = add %int %434, 3
    %436 = load { %byte*, [1 x %int] }* %433
    %437 = extractvalue { %byte*, [1 x %int] } %436, 0
    %438 = getelementptr inbounds %byte* %437, %int %435
    store %byte %431, %byte* %438
    %439 = load { %code*, [1 x %int] }* %data
    %440 = load %int* %i1
    %441 = extractvalue { %code*, [1 x %int] } %439, 0
    %442 = getelementptr inbounds %code* %441, %int %440
    %443 = load %code* %442
    %444 = bitcast %code %443 to %int
    %445 = ashr %int %444, 6
    %446 = and %int %445, 63
    %447 = or %int %446, 128
    %448 = trunc %int %447 to %byte
    %449 = load %"_Pshadow_Pstandard_CString"** %this
    %450 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %449, i32 0, i32 1
    %451 = load %int* %j
    %452 = add %int %451, 4
    %453 = load { %byte*, [1 x %int] }* %450
    %454 = extractvalue { %byte*, [1 x %int] } %453, 0
    %455 = getelementptr inbounds %byte* %454, %int %452
    store %byte %448, %byte* %455
    %456 = load { %code*, [1 x %int] }* %data
    %457 = load %int* %i1
    %458 = extractvalue { %code*, [1 x %int] } %456, 0
    %459 = getelementptr inbounds %code* %458, %int %457
    %460 = load %code* %459
    %461 = bitcast %code %460 to %int
    %462 = ashr %int %461, 0
    %463 = and %int %462, 63
    %464 = or %int %463, 128
    %465 = trunc %int %464 to %byte
    %466 = load %"_Pshadow_Pstandard_CString"** %this
    %467 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %466, i32 0, i32 1
    %468 = load %int* %j
    %469 = add %int %468, 5
    %470 = load { %byte*, [1 x %int] }* %467
    %471 = extractvalue { %byte*, [1 x %int] } %470, 0
    %472 = getelementptr inbounds %byte* %471, %int %469
    store %byte %465, %byte* %472
    %473 = load %int* %j
    %474 = add %int %473, 6
    store %int %474, %int* %j
    br label %_label51
_label50:
    br label %_label51
_label51:
    br label %_label48
_label48:
    br label %_label45
_label45:
    br label %_label42
_label42:
    br label %_label39
_label39:
    br label %_label36
_label36:
    %475 = load %int* %i1
    %476 = add %int %475, 1
    store %int %476, %int* %i1
    br label %_label32
_label32:
    %477 = load { %code*, [1 x %int] }* %data
    %478 = extractvalue { %code*, [1 x %int] } %477, 1, 0
    %479 = load %int* %i1
    %480 = icmp slt %int %479, %478
    br %boolean %480, label %_label31, label %_label33
_label33:
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %3
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 2
    store %boolean true, %boolean* %5
    %6 = load %"_Pshadow_Pstandard_CString"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %6, i32 0, i32 1
    %8 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %byte*
    %10 = insertvalue { %byte*, [1 x %int] } undef, %byte* %9, 0
    %11 = insertvalue { %byte*, [1 x %int] } %10, %int 0, 1, 0
    store { %byte*, [1 x %int] } %11, { %byte*, [1 x %int] }* %7
    %12 = load %"_Pshadow_Pstandard_CString"** %other
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %12, i32 0, i32 1
    %14 = load %"_Pshadow_Pstandard_CString"** %this
    %15 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %14, i32 0, i32 1
    %16 = load { %byte*, [1 x %int] }* %13
    store { %byte*, [1 x %int] } %16, { %byte*, [1 x %int] }* %15
    %17 = load %"_Pshadow_Pstandard_CString"** %other
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %17, i32 0, i32 2
    %19 = load %"_Pshadow_Pstandard_CString"** %this
    %20 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %19, i32 0, i32 2
    %21 = load %boolean* %18
    store %boolean %21, %boolean* %20
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] }, %boolean) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %byte*, [1 x %int] }
    %ascii = alloca %boolean
    %4 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %4
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %byte*, [1 x %int] } %1, { %byte*, [1 x %int] }* %data
    store %boolean %2, %boolean* %ascii
    %5 = load %"_Pshadow_Pstandard_CString"** %this
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %5, i32 0, i32 2
    store %boolean true, %boolean* %6
    %7 = load %"_Pshadow_Pstandard_CString"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %7, i32 0, i32 1
    %9 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %10 = bitcast %"_Pshadow_Pstandard_CObject"* %9 to %byte*
    %11 = insertvalue { %byte*, [1 x %int] } undef, %byte* %10, 0
    %12 = insertvalue { %byte*, [1 x %int] } %11, %int 0, 1, 0
    store { %byte*, [1 x %int] } %12, { %byte*, [1 x %int] }* %8
    %13 = load %"_Pshadow_Pstandard_CString"** %this
    %14 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %13, i32 0, i32 1
    %15 = load { %byte*, [1 x %int] }* %data
    store { %byte*, [1 x %int] } %15, { %byte*, [1 x %int] }* %14
    %16 = load %"_Pshadow_Pstandard_CString"** %this
    %17 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %16, i32 0, i32 2
    %18 = load %boolean* %ascii
    store %boolean %18, %boolean* %17
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %byte*, [1 x %int] }
    %i = alloca %int
    %i1 = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = load %"_Pshadow_Pstandard_CString"** %other
    %5 = getelementptr %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pstandard_CString_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %6, i32 0, i32 17
    %8 = load %int (%"_Pshadow_Pstandard_CString"*)** %7
    %9 = call %int %8(%"_Pshadow_Pstandard_CString"* %3)
    %10 = getelementptr %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CString_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %11, i32 0, i32 17
    %13 = load %int (%"_Pshadow_Pstandard_CString"*)** %12
    %14 = call %int %13(%"_Pshadow_Pstandard_CString"* %4)
    %15 = add %int %9, %14
    %16 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %15)
    %17 = bitcast %"_Pshadow_Pstandard_CObject"* %16 to %byte*
    %18 = insertvalue { %byte*, [1 x %int] } undef, %byte* %17, 0
    %19 = insertvalue { %byte*, [1 x %int] } %18, %int %15, 1, 0
    store { %byte*, [1 x %int] } %19, { %byte*, [1 x %int] }* %data
    store %int 0, %int* %i
    br label %_label53
_label52:
    %20 = load %"_Pshadow_Pstandard_CString"** %this
    %21 = load %int* %i
    %22 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %20, %int %21)
    %23 = load { %byte*, [1 x %int] }* %data
    %24 = load %int* %i
    %25 = extractvalue { %byte*, [1 x %int] } %23, 0
    %26 = getelementptr inbounds %byte* %25, %int %24
    store %byte %22, %byte* %26
    %27 = load %int* %i
    %28 = add %int %27, 1
    store %int %28, %int* %i
    br label %_label53
_label53:
    %29 = load %"_Pshadow_Pstandard_CString"** %this
    %30 = load %int* %i
    %31 = getelementptr %"_Pshadow_Pstandard_CString"* %29, i32 0, i32 0
    %32 = load %"_Pshadow_Pstandard_CString_Mclass"** %31
    %33 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %32, i32 0, i32 17
    %34 = load %int (%"_Pshadow_Pstandard_CString"*)** %33
    %35 = call %int %34(%"_Pshadow_Pstandard_CString"* %29)
    %36 = icmp slt %int %30, %35
    br %boolean %36, label %_label52, label %_label54
_label54:
    store %int 0, %int* %i1
    br label %_label56
_label55:
    %37 = load %"_Pshadow_Pstandard_CString"** %other
    %38 = load %int* %i1
    %39 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %37, %int %38)
    %40 = load %"_Pshadow_Pstandard_CString"** %this
    %41 = getelementptr %"_Pshadow_Pstandard_CString"* %40, i32 0, i32 0
    %42 = load %"_Pshadow_Pstandard_CString_Mclass"** %41
    %43 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %42, i32 0, i32 17
    %44 = load %int (%"_Pshadow_Pstandard_CString"*)** %43
    %45 = call %int %44(%"_Pshadow_Pstandard_CString"* %40)
    %46 = load %int* %i1
    %47 = add %int %45, %46
    %48 = load { %byte*, [1 x %int] }* %data
    %49 = extractvalue { %byte*, [1 x %int] } %48, 0
    %50 = getelementptr inbounds %byte* %49, %int %47
    store %byte %39, %byte* %50
    %51 = load %int* %i1
    %52 = add %int %51, 1
    store %int %52, %int* %i1
    br label %_label56
_label56:
    %53 = load %"_Pshadow_Pstandard_CString"** %other
    %54 = load %int* %i1
    %55 = getelementptr %"_Pshadow_Pstandard_CString"* %53, i32 0, i32 0
    %56 = load %"_Pshadow_Pstandard_CString_Mclass"** %55
    %57 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %56, i32 0, i32 17
    %58 = load %int (%"_Pshadow_Pstandard_CString"*)** %57
    %59 = call %int %58(%"_Pshadow_Pstandard_CString"* %53)
    %60 = icmp slt %int %54, %59
    br %boolean %60, label %_label55, label %_label57
_label57:
    %61 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0))
    %62 = bitcast %"_Pshadow_Pstandard_CObject"* %61 to %"_Pshadow_Pstandard_CString"*
    %63 = load %"_Pshadow_Pstandard_CString"** %this
    %64 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %63, i32 0, i32 2
    %65 = load %boolean* %64
    store %boolean %65, %boolean* %_temp
    %66 = load %boolean* %64
    br %boolean %66, label %_label59, label %_label58
_label59:
    %67 = load %"_Pshadow_Pstandard_CString"** %other
    %68 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %67, i32 0, i32 2
    %69 = load %boolean* %68
    store %boolean %69, %boolean* %_temp
    br label %_label58
_label58:
    %70 = load %boolean* %_temp
    %71 = load { %byte*, [1 x %int] }* %data
    %72 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"* %62, { %byte*, [1 x %int] } %71, %boolean %70)
    ret %"_Pshadow_Pstandard_CString"* %62
}

declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate"(%"_Pshadow_Pstandard_CException"*)

declare %"_Pshadow_Pstandard_CIllegalArgumentException"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mcreate"(%"_Pshadow_Pstandard_CIllegalArgumentException"*)

declare %int @"_Pshadow_Pstandard_Cint_Mmin_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %int @"_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
declare %uint @"_Pshadow_Pstandard_Cint_Mabs"(%"_Pshadow_Pstandard_Cint"*)
declare %int @"_Pshadow_Pstandard_Cint_Mmax_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_Cint"*, %int)
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

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisInterface"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisPrimitive"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cboolean_MtoString"(%"_Pshadow_Pstandard_Cboolean"*)
declare %"_Pshadow_Pstandard_Cboolean"* @"_Pshadow_Pstandard_Cboolean_Mcreate"(%"_Pshadow_Pstandard_Cboolean"*)

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })
declare void @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*)
declare %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*)
declare %int @"_Pshadow_Pstandard_CArray_Mlength"(%"_Pshadow_Pstandard_CArray"*)
declare { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CArray_MtoString"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"*)

declare %boolean @"_Pshadow_Pstandard_CString_IStringIterator_MhasNext"(%"_Pshadow_Pstandard_CString_IStringIterator"*)
declare %code @"_Pshadow_Pstandard_CString_IStringIterator_Mnext"(%"_Pshadow_Pstandard_CString_IStringIterator"*)
declare %"_Pshadow_Pstandard_CString_IStringIterator"* @"_Pshadow_Pstandard_CString_IStringIterator_Mcreate"(%"_Pshadow_Pstandard_CString_IStringIterator"*, %"_Pshadow_Pstandard_CString"*)

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
declare %boolean @"_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcopy"(%"_Pshadow_Pstandard_CObject"*)

declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mreverse"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare %int @"_Pshadow_Pstandard_CMutableString_Mlength"(%"_Pshadow_Pstandard_CMutableString"*)
declare %int @"_Pshadow_Pstandard_CMutableString_Mcapacity"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CMutableString_MtoString"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)

@_array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@String"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([22 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 22] }, %boolean true }
