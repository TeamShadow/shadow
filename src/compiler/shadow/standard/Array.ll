; shadow.standard@Array<T>

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

%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
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
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %ubyte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %ubyte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }
%"_Pshadow_Pstandard_CMutableString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)* }
%"_Pshadow_Pstandard_CMutableString" = type { %"_Pshadow_Pstandard_CMutableString_Mclass"* }

@"_Pshadow_Pstandard_CArray_Mclass" = global %"_Pshadow_Pstandard_CArray_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CArray"** getelementptr (%"_Pshadow_Pstandard_CArray"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mcopy", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_MtoString", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mdims", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_MgetBaseClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1", void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mlength", { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mlengths", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint" }
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external global %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external global %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external global %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external global %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external global %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external global %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external global %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external global %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external global %"_Pshadow_Pstandard_CObject_Mclass"
@"_Pshadow_Pstandard_CMutableString_Mclass" = external global %"_Pshadow_Pstandard_CMutableString_Mclass"

define %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Pstandard_CClass"** %3
    ret %"_Pshadow_Pstandard_CClass"* %4
}

declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })

declare void @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT"(%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)

define %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 1
    %4 = load { %int*, [1 x %int] }* %3
    %5 = extractvalue { %int*, [1 x %int] } %4, 1
    %6 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %7 = bitcast %"_Pshadow_Pstandard_CObject"* %6 to [1 x %int]*
    store [1 x %int] %5, [1 x %int]* %7
    %8 = getelementptr inbounds [1 x %int]* %7, i32 0, i32 0
    %9 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %8, 0
    %10 = extractvalue { %int*, [1 x %int] } %4, 0
    %11 = bitcast %int* %10 to %"_Pshadow_Pstandard_CObject"*
    %12 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %"_Pshadow_Pstandard_CArray"*
    %14 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %13, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %9, %"_Pshadow_Pstandard_CObject"* %11)
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 14
    %18 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = call { %int*, [1 x %int] } %18(%"_Pshadow_Pstandard_CArray"* %14)
    %20 = extractvalue { %int*, [1 x %int] } %19, 0
    %21 = getelementptr inbounds %int* %20, %int 0
    %22 = load %int* %21
    ret %int %22
}

define %int @"_Pshadow_Pstandard_CArray_Mlength"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %length = alloca %int
    %i = alloca %int
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 1
    %4 = load { %int*, [1 x %int] }* %3
    %5 = extractvalue { %int*, [1 x %int] } %4, 0
    %6 = getelementptr inbounds %int* %5, %int 0
    %7 = load %int* %6
    store %int %7, %int* %length
    store %int 1, %int* %i
    br label %_label1
_label0:
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %8, i32 0, i32 1
    %10 = load { %int*, [1 x %int] }* %9
    %11 = load %int* %i
    %12 = extractvalue { %int*, [1 x %int] } %10, 0
    %13 = getelementptr inbounds %int* %12, %int %11
    %14 = load %int* %length
    %15 = load %int* %13
    %16 = mul %int %14, %15
    store %int %16, %int* %length
    %17 = load %int* %i
    %18 = add %int %17, 1
    store %int %18, %int* %i
    br label %_label1
_label1:
    %19 = load %"_Pshadow_Pstandard_CArray"** %this
    %20 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %19, i32 0, i32 1
    %21 = load { %int*, [1 x %int] }* %20
    %22 = extractvalue { %int*, [1 x %int] } %21, 1
    %23 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %24 = bitcast %"_Pshadow_Pstandard_CObject"* %23 to [1 x %int]*
    store [1 x %int] %22, [1 x %int]* %24
    %25 = getelementptr inbounds [1 x %int]* %24, i32 0, i32 0
    %26 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %25, 0
    %27 = extractvalue { %int*, [1 x %int] } %21, 0
    %28 = bitcast %int* %27 to %"_Pshadow_Pstandard_CObject"*
    %29 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %30 = bitcast %"_Pshadow_Pstandard_CObject"* %29 to %"_Pshadow_Pstandard_CArray"*
    %31 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %30, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %26, %"_Pshadow_Pstandard_CObject"* %28)
    %32 = getelementptr %"_Pshadow_Pstandard_CArray"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CArray_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %33, i32 0, i32 14
    %35 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %34
    %36 = call { %int*, [1 x %int] } %35(%"_Pshadow_Pstandard_CArray"* %31)
    %37 = extractvalue { %int*, [1 x %int] } %36, 0
    %38 = getelementptr inbounds %int* %37, %int 0
    %39 = load %int* %i
    %40 = load %int* %38
    %41 = icmp slt %int %39, %40
    br %boolean %41, label %_label0, label %_label2
_label2:
    %42 = load %int* %length
    ret %int %42
}

define { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 1
    %4 = load { %int*, [1 x %int] }* %3
    ret { %int*, [1 x %int] } %4
}

declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, %int)

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CArray_MtoString"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %string = alloca %"_Pshadow_Pstandard_CMutableString"*
    %indicies = alloca { %int*, [1 x %int] }
    %first = alloca %boolean
    %i = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 0
    %4 = load %"_Pshadow_Pstandard_CArray_Mclass"** %3
    %5 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %4, i32 0, i32 13
    %6 = load %int (%"_Pshadow_Pstandard_CArray"*)** %5
    %7 = call %int %6(%"_Pshadow_Pstandard_CArray"* %2)
    %8 = icmp eq %int %7, 0
    br %boolean %8, label %_label3, label %_label4
_label3:
    ret %"_Pshadow_Pstandard_CString"* @_string1
    br label %_label5
_label4:
    br label %_label5
_label5:
    %10 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CMutableString_Mclass"* @"_Pshadow_Pstandard_CMutableString_Mclass", i32 0, i32 0))
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %"_Pshadow_Pstandard_CMutableString"*
    %12 = bitcast %"_Pshadow_Pstandard_CString"* @_string2 to %"_Pshadow_Pstandard_CObject"*
    %13 = call %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"* %11, %"_Pshadow_Pstandard_CObject"* %12)
    store %"_Pshadow_Pstandard_CMutableString"* %11, %"_Pshadow_Pstandard_CMutableString"** %string
    %14 = load %"_Pshadow_Pstandard_CArray"** %this
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 9
    %18 = load %int (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = call %int %18(%"_Pshadow_Pstandard_CArray"* %14)
    %20 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int %19)
    %21 = bitcast %"_Pshadow_Pstandard_CObject"* %20 to %int*
    %22 = insertvalue { %int*, [1 x %int] } undef, %int* %21, 0
    %23 = insertvalue { %int*, [1 x %int] } %22, %int %19, 1, 0
    store { %int*, [1 x %int] } %23, { %int*, [1 x %int] }* %indicies
    store %boolean true, %boolean* %first
    br label %_label7
_label6:
    %24 = load %boolean* %first
    br %boolean %24, label %_label9, label %_label10
_label9:
    store %boolean false, %boolean* %first
    br label %_label11
_label10:
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %27 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %25, i32 0, i32 0
    %28 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %27
    %29 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %28, i32 0, i32 8
    %30 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %29
    %31 = call %"_Pshadow_Pstandard_CMutableString"* %30(%"_Pshadow_Pstandard_CMutableString"* %25, %"_Pshadow_Pstandard_CObject"* %26)
    br label %_label11
_label11:
    %32 = load %"_Pshadow_Pstandard_CArray"** %this
    %33 = load { %int*, [1 x %int] }* %indicies
    %34 = getelementptr %"_Pshadow_Pstandard_CArray"* %32, i32 0, i32 0
    %35 = load %"_Pshadow_Pstandard_CArray_Mclass"** %34
    %36 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %35, i32 0, i32 11
    %37 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })** %36
    %38 = call %"_Pshadow_Pstandard_CObject"* %37(%"_Pshadow_Pstandard_CArray"* %32, { %int*, [1 x %int] } %33)
    %39 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %40 = bitcast %"_Pshadow_Pstandard_CObject"* %38 to %"_Pshadow_Pstandard_CObject"*
    %41 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %39, i32 0, i32 0
    %42 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %41
    %43 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %42, i32 0, i32 8
    %44 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %43
    %45 = call %"_Pshadow_Pstandard_CMutableString"* %44(%"_Pshadow_Pstandard_CMutableString"* %39, %"_Pshadow_Pstandard_CObject"* %40)
    %46 = load { %int*, [1 x %int] }* %indicies
    %47 = extractvalue { %int*, [1 x %int] } %46, 1, 0
    %48 = sub %int %47, 1
    store %int %48, %int* %i
    %49 = load { %int*, [1 x %int] }* %indicies
    %50 = load %int* %i
    %51 = extractvalue { %int*, [1 x %int] } %49, 0
    %52 = getelementptr inbounds %int* %51, %int %50
    %53 = load %int* %52
    %54 = add %int %53, 1
    store %int %54, %int* %52
    br label %_label13
_label12:
    %55 = load { %int*, [1 x %int] }* %indicies
    %56 = load %int* %i
    %57 = extractvalue { %int*, [1 x %int] } %55, 0
    %58 = getelementptr inbounds %int* %57, %int %56
    store %int 0, %int* %58
    %59 = load %int* %i
    %60 = sub %int %59, 1
    store %int %60, %int* %i
    %61 = load { %int*, [1 x %int] }* %indicies
    %62 = load %int* %i
    %63 = extractvalue { %int*, [1 x %int] } %61, 0
    %64 = getelementptr inbounds %int* %63, %int %62
    %65 = load %int* %64
    %66 = add %int %65, 1
    store %int %66, %int* %64
    br label %_label13
_label13:
    %67 = load %int* %i
    %68 = icmp sgt %int %67, 0
    store %boolean %68, %boolean* %_temp
    br %boolean %68, label %_label16, label %_label15
_label16:
    %69 = load { %int*, [1 x %int] }* %indicies
    %70 = load %int* %i
    %71 = extractvalue { %int*, [1 x %int] } %69, 0
    %72 = getelementptr inbounds %int* %71, %int %70
    %73 = load %"_Pshadow_Pstandard_CArray"** %this
    %74 = getelementptr %"_Pshadow_Pstandard_CArray"* %73, i32 0, i32 0
    %75 = load %"_Pshadow_Pstandard_CArray_Mclass"** %74
    %76 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %75, i32 0, i32 14
    %77 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %76
    %78 = call { %int*, [1 x %int] } %77(%"_Pshadow_Pstandard_CArray"* %73)
    %79 = load %int* %i
    %80 = extractvalue { %int*, [1 x %int] } %78, 0
    %81 = getelementptr inbounds %int* %80, %int %79
    %82 = load %int* %72
    %83 = load %int* %81
    %84 = icmp eq %int %82, %83
    store %boolean %84, %boolean* %_temp
    br label %_label15
_label15:
    %85 = load %boolean* %_temp
    br %boolean %85, label %_label12, label %_label14
_label14:
    br label %_label7
_label7:
    %86 = load { %int*, [1 x %int] }* %indicies
    %87 = extractvalue { %int*, [1 x %int] } %86, 0
    %88 = getelementptr inbounds %int* %87, %int 0
    %89 = load %"_Pshadow_Pstandard_CArray"** %this
    %90 = getelementptr %"_Pshadow_Pstandard_CArray"* %89, i32 0, i32 0
    %91 = load %"_Pshadow_Pstandard_CArray_Mclass"** %90
    %92 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %91, i32 0, i32 14
    %93 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %92
    %94 = call { %int*, [1 x %int] } %93(%"_Pshadow_Pstandard_CArray"* %89)
    %95 = extractvalue { %int*, [1 x %int] } %94, 0
    %96 = getelementptr inbounds %int* %95, %int 0
    %97 = load %int* %88
    %98 = load %int* %96
    %99 = icmp ne %int %97, %98
    br %boolean %99, label %_label6, label %_label8
_label8:
    %100 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %101 = bitcast %"_Pshadow_Pstandard_CString"* @_string4 to %"_Pshadow_Pstandard_CObject"*
    %102 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %100, i32 0, i32 0
    %103 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %102
    %104 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %103, i32 0, i32 8
    %105 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %104
    %106 = call %"_Pshadow_Pstandard_CMutableString"* %105(%"_Pshadow_Pstandard_CMutableString"* %100, %"_Pshadow_Pstandard_CObject"* %101)
    %107 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %106, i32 0, i32 0
    %108 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %107
    %109 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %108, i32 0, i32 7
    %110 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %109
    %111 = call %"_Pshadow_Pstandard_CString"* %110(%"_Pshadow_Pstandard_CMutableString"* %106)
    ret %"_Pshadow_Pstandard_CString"* %111
}

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %lengths = alloca { %int*, [1 x %int] }
    %data = alloca %"_Pshadow_Pstandard_CObject"*
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %5
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store { %int*, [1 x %int] } %2, { %int*, [1 x %int] }* %lengths
    store %"_Pshadow_Pstandard_CObject"* %3, %"_Pshadow_Pstandard_CObject"** %data
    %6 = load %"_Pshadow_Pstandard_CArray"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %6, i32 0, i32 2
    %8 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %8, %"_Pshadow_Pstandard_CClass"** %7
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %10
    %11 = load %"_Pshadow_Pstandard_CArray"** %this
    %12 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %11, i32 0, i32 1
    %13 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 0)
    %14 = bitcast %"_Pshadow_Pstandard_CObject"* %13 to %int*
    %15 = insertvalue { %int*, [1 x %int] } undef, %int* %14, 0
    %16 = insertvalue { %int*, [1 x %int] } %15, %int 0, 1, 0
    store { %int*, [1 x %int] } %16, { %int*, [1 x %int] }* %12
    %17 = load %"_Pshadow_Pstandard_CArray"** %this
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 1
    %19 = load { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %19, { %int*, [1 x %int] }* %18
    %20 = load %"_Pshadow_Pstandard_CArray"** %this
    %21 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %20, i32 0, i32 3
    %22 = load %"_Pshadow_Pstandard_CObject"** %data
    store %"_Pshadow_Pstandard_CObject"* %22, %"_Pshadow_Pstandard_CObject"** %21
    ret %"_Pshadow_Pstandard_CArray"* %0
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %lengths = alloca { %int*, [1 x %int] }
    %4 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %4
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store { %int*, [1 x %int] } %2, { %int*, [1 x %int] }* %lengths
    %5 = load %"_Pshadow_Pstandard_CArray"** %this
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %5, i32 0, i32 2
    %7 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %7, %"_Pshadow_Pstandard_CClass"** %6
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %8, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %9
    %10 = load %"_Pshadow_Pstandard_CArray"** %this
    %11 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %10, i32 0, i32 1
    %12 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 0)
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %int*
    %14 = insertvalue { %int*, [1 x %int] } undef, %int* %13, 0
    %15 = insertvalue { %int*, [1 x %int] } %14, %int 0, 1, 0
    store { %int*, [1 x %int] } %15, { %int*, [1 x %int] }* %11
    %16 = load { %int*, [1 x %int] }* %lengths
    %17 = extractvalue { %int*, [1 x %int] } %16, 1
    %18 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %19 = bitcast %"_Pshadow_Pstandard_CObject"* %18 to [1 x %int]*
    store [1 x %int] %17, [1 x %int]* %19
    %20 = getelementptr inbounds [1 x %int]* %19, i32 0, i32 0
    %21 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %20, 0
    %22 = extractvalue { %int*, [1 x %int] } %16, 0
    %23 = bitcast %int* %22 to %"_Pshadow_Pstandard_CObject"*
    %24 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %25 = bitcast %"_Pshadow_Pstandard_CObject"* %24 to %"_Pshadow_Pstandard_CArray"*
    %26 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %25, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %21, %"_Pshadow_Pstandard_CObject"* %23)
    %27 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"* %26)
    %28 = bitcast %"_Pshadow_Pstandard_CArray"* %27 to %"_Pshadow_Pstandard_CObject"*
    %29 = bitcast %"_Pshadow_Pstandard_CObject"* %28 to %"_Pshadow_Pstandard_CArray"*
    %30 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %29, i32 0, i32 3
    %31 = load %"_Pshadow_Pstandard_CObject"** %30
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to %int*
    %33 = insertvalue { %int*, [1 x %int] } undef, %int* %32, 0
    %34 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %29, i32 0, i32 1, i32 0
    %35 = load %int** %34
    %36 = bitcast %int* %35 to [1 x %int]*
    %37 = load [1 x %int]* %36
    %38 = insertvalue { %int*, [1 x %int] } %33, [1 x %int] %37, 1
    %39 = load %"_Pshadow_Pstandard_CArray"** %this
    %40 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %39, i32 0, i32 1
    store { %int*, [1 x %int] } %38, { %int*, [1 x %int] }* %40
    %41 = load %"_Pshadow_Pstandard_CArray"** %this
    %42 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %41, i32 0, i32 2
    %43 = load %"_Pshadow_Pstandard_CArray"** %this
    %44 = load %"_Pshadow_Pstandard_CArray"** %this
    %45 = load %"_Pshadow_Pstandard_CClass"** %42
    %46 = getelementptr %"_Pshadow_Pstandard_CArray"* %43, i32 0, i32 0
    %47 = load %"_Pshadow_Pstandard_CArray_Mclass"** %46
    %48 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %47, i32 0, i32 13
    %49 = load %int (%"_Pshadow_Pstandard_CArray"*)** %48
    %50 = call %int %49(%"_Pshadow_Pstandard_CArray"* %43)
    %51 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"* %44, %"_Pshadow_Pstandard_CClass"* %45, %int %50)
    %52 = load %"_Pshadow_Pstandard_CArray"** %this
    %53 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %52, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* %51, %"_Pshadow_Pstandard_CObject"** %53
    ret %"_Pshadow_Pstandard_CArray"* %0
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %copy = alloca %"_Pshadow_Pstandard_CArray"*
    %indicies = alloca { %int*, [1 x %int] }
    %i = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Pstandard_CArray"*
    %4 = load %"_Pshadow_Pstandard_CArray"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %4, i32 0, i32 2
    %6 = load %"_Pshadow_Pstandard_CArray"** %this
    %7 = load %"_Pshadow_Pstandard_CArray"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %7, i32 0, i32 2
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = load %"_Pshadow_Pstandard_CArray"** %this
    %11 = load %"_Pshadow_Pstandard_CClass"** %8
    %12 = getelementptr %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 0
    %13 = load %"_Pshadow_Pstandard_CArray_Mclass"** %12
    %14 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %13, i32 0, i32 13
    %15 = load %int (%"_Pshadow_Pstandard_CArray"*)** %14
    %16 = call %int %15(%"_Pshadow_Pstandard_CArray"* %9)
    %17 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"* %10, %"_Pshadow_Pstandard_CClass"* %11, %int %16)
    %18 = load %"_Pshadow_Pstandard_CClass"** %5
    %19 = getelementptr %"_Pshadow_Pstandard_CArray"* %6, i32 0, i32 0
    %20 = load %"_Pshadow_Pstandard_CArray_Mclass"** %19
    %21 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %20, i32 0, i32 14
    %22 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %21
    %23 = call { %int*, [1 x %int] } %22(%"_Pshadow_Pstandard_CArray"* %6)
    %24 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CClass"* %18, { %int*, [1 x %int] } %23, %"_Pshadow_Pstandard_CObject"* %17)
    store %"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CArray"** %copy
    %25 = load %"_Pshadow_Pstandard_CArray"** %this
    %26 = getelementptr %"_Pshadow_Pstandard_CArray"* %25, i32 0, i32 0
    %27 = load %"_Pshadow_Pstandard_CArray_Mclass"** %26
    %28 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %27, i32 0, i32 9
    %29 = load %int (%"_Pshadow_Pstandard_CArray"*)** %28
    %30 = call %int %29(%"_Pshadow_Pstandard_CArray"* %25)
    %31 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int %30)
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to %int*
    %33 = insertvalue { %int*, [1 x %int] } undef, %int* %32, 0
    %34 = insertvalue { %int*, [1 x %int] } %33, %int %30, 1, 0
    store { %int*, [1 x %int] } %34, { %int*, [1 x %int] }* %indicies
    br label %_label18
_label17:
    %35 = load %"_Pshadow_Pstandard_CArray"** %this
    %36 = load { %int*, [1 x %int] }* %indicies
    %37 = getelementptr %"_Pshadow_Pstandard_CArray"* %35, i32 0, i32 0
    %38 = load %"_Pshadow_Pstandard_CArray_Mclass"** %37
    %39 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %38, i32 0, i32 11
    %40 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })** %39
    %41 = call %"_Pshadow_Pstandard_CObject"* %40(%"_Pshadow_Pstandard_CArray"* %35, { %int*, [1 x %int] } %36)
    %42 = load %"_Pshadow_Pstandard_CArray"** %copy
    %43 = load { %int*, [1 x %int] }* %indicies
    %44 = getelementptr %"_Pshadow_Pstandard_CArray"* %42, i32 0, i32 0
    %45 = load %"_Pshadow_Pstandard_CArray_Mclass"** %44
    %46 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %45, i32 0, i32 12
    %47 = load void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)** %46
    call void %47(%"_Pshadow_Pstandard_CArray"* %42, { %int*, [1 x %int] } %43, %"_Pshadow_Pstandard_CObject"* %41)
    %48 = load { %int*, [1 x %int] }* %indicies
    %49 = extractvalue { %int*, [1 x %int] } %48, 1, 0
    %50 = sub %int %49, 1
    store %int %50, %int* %i
    %51 = load { %int*, [1 x %int] }* %indicies
    %52 = load %int* %i
    %53 = extractvalue { %int*, [1 x %int] } %51, 0
    %54 = getelementptr inbounds %int* %53, %int %52
    %55 = load %int* %54
    %56 = add %int %55, 1
    store %int %56, %int* %54
    br label %_label21
_label20:
    %57 = load { %int*, [1 x %int] }* %indicies
    %58 = load %int* %i
    %59 = extractvalue { %int*, [1 x %int] } %57, 0
    %60 = getelementptr inbounds %int* %59, %int %58
    store %int 0, %int* %60
    %61 = load %int* %i
    %62 = sub %int %61, 1
    store %int %62, %int* %i
    %63 = load { %int*, [1 x %int] }* %indicies
    %64 = load %int* %i
    %65 = extractvalue { %int*, [1 x %int] } %63, 0
    %66 = getelementptr inbounds %int* %65, %int %64
    %67 = load %int* %66
    %68 = add %int %67, 1
    store %int %68, %int* %66
    br label %_label21
_label21:
    %69 = load %int* %i
    %70 = icmp sgt %int %69, 0
    store %boolean %70, %boolean* %_temp
    br %boolean %70, label %_label24, label %_label23
_label24:
    %71 = load { %int*, [1 x %int] }* %indicies
    %72 = load %int* %i
    %73 = extractvalue { %int*, [1 x %int] } %71, 0
    %74 = getelementptr inbounds %int* %73, %int %72
    %75 = load %"_Pshadow_Pstandard_CArray"** %this
    %76 = getelementptr %"_Pshadow_Pstandard_CArray"* %75, i32 0, i32 0
    %77 = load %"_Pshadow_Pstandard_CArray_Mclass"** %76
    %78 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %77, i32 0, i32 14
    %79 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %78
    %80 = call { %int*, [1 x %int] } %79(%"_Pshadow_Pstandard_CArray"* %75)
    %81 = load %int* %i
    %82 = extractvalue { %int*, [1 x %int] } %80, 0
    %83 = getelementptr inbounds %int* %82, %int %81
    %84 = load %int* %74
    %85 = load %int* %83
    %86 = icmp eq %int %84, %85
    store %boolean %86, %boolean* %_temp
    br label %_label23
_label23:
    %87 = load %boolean* %_temp
    br %boolean %87, label %_label20, label %_label22
_label22:
    br label %_label18
_label18:
    %88 = load { %int*, [1 x %int] }* %indicies
    %89 = extractvalue { %int*, [1 x %int] } %88, 0
    %90 = getelementptr inbounds %int* %89, %int 0
    %91 = load %"_Pshadow_Pstandard_CArray"** %this
    %92 = getelementptr %"_Pshadow_Pstandard_CArray"* %91, i32 0, i32 0
    %93 = load %"_Pshadow_Pstandard_CArray_Mclass"** %92
    %94 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %93, i32 0, i32 14
    %95 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %94
    %96 = call { %int*, [1 x %int] } %95(%"_Pshadow_Pstandard_CArray"* %91)
    %97 = extractvalue { %int*, [1 x %int] } %96, 0
    %98 = getelementptr inbounds %int* %97, %int 0
    %99 = load %int* %90
    %100 = load %int* %98
    %101 = icmp ne %int %99, %100
    br %boolean %101, label %_label17, label %_label19
_label19:
    %102 = load %"_Pshadow_Pstandard_CArray"** %copy
    ret %"_Pshadow_Pstandard_CArray"* %102
}

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

declare %byte @"_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %int @"_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %byte @"_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)
declare %"_Pshadow_Pstandard_Cbyte"* @"_Pshadow_Pstandard_Cbyte_Mcreate"(%"_Pshadow_Pstandard_Cbyte"*)
declare %byte @"_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_Cbyte"*, %byte)

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoUpperCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CString_Miterator"(%"_Pshadow_Pstandard_CString"*)
declare %boolean @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoLowerCase"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int, %int)
declare %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"*)
declare %ubyte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int)
declare { %ubyte*, [1 x %int] } @"_Pshadow_Pstandard_CString_Mchars"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cubyte_A1"(%"_Pshadow_Pstandard_CString"*, { %ubyte*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1"(%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisInterface"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisPrimitive"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_Cboolean_MtoString"(%"_Pshadow_Pstandard_Cboolean"*)
declare %"_Pshadow_Pstandard_Cboolean"* @"_Pshadow_Pstandard_Cboolean_Mcreate"(%"_Pshadow_Pstandard_Cboolean"*)

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

@_array0 = private unnamed_addr constant [24 x %ubyte] c"shadow.standard@Array<T>"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([24 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 24] }, %boolean true }
@_array1 = private unnamed_addr constant [2 x %ubyte] c"[]"
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([2 x %ubyte]* @_array1, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array2 = private unnamed_addr constant [2 x %ubyte] c"[ "
@_string2 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([2 x %ubyte]* @_array2, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array3 = private unnamed_addr constant [2 x %ubyte] c", "
@_string3 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([2 x %ubyte]* @_array3, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array4 = private unnamed_addr constant [2 x %ubyte] c" ]"
@_string4 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([2 x %ubyte]* @_array4, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
