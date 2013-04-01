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

declare i32 @__shadow_personality_v0(...)
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cushort"* (%"_Pshadow_Pstandard_Cushort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %int (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"*, %ushort }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Ccode"* (%"_Pshadow_Pstandard_Ccode"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"*, %code }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cdouble"* (%"_Pshadow_Pstandard_Cdouble"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %int (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"*, %double }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Clong"* (%"_Pshadow_Pstandard_Clong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*)*, %ulong (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*, %ulong)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"*, %long }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cfloat"* (%"_Pshadow_Pstandard_Cfloat"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %int (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"*, %float }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }
%"_Pshadow_Pstandard_CMutableString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %byte (%"_Pshadow_Pstandard_CMutableString"*, %int)*, void (%"_Pshadow_Pstandard_CMutableString"*, %int, %byte)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)* }
%"_Pshadow_Pstandard_CMutableString" = type { %"_Pshadow_Pstandard_CMutableString_Mclass"* }

@"_Pshadow_Pstandard_CArray_Mclass" = constant %"_Pshadow_Pstandard_CArray_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CArray"** getelementptr (%"_Pshadow_Pstandard_CArray"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mcopy", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_MtoString", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mdims", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_MgetBaseClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1", void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT", { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mlengths", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Msize", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external constant %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external constant %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external constant %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external constant %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external constant %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external constant %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external constant %"_Pshadow_Pstandard_CObject_Mclass"
@"_Pshadow_Pstandard_CMutableString_Mclass" = external constant %"_Pshadow_Pstandard_CMutableString_Mclass"

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
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 12
    %18 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = extractvalue { %int*, [1 x %int] } %4, 1
    %20 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %21 = bitcast %"_Pshadow_Pstandard_CObject"* %20 to [1 x %int]*
    store [1 x %int] %19, [1 x %int]* %21
    %22 = getelementptr inbounds [1 x %int]* %21, i32 0, i32 0
    %23 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %22, 0
    %24 = extractvalue { %int*, [1 x %int] } %4, 0
    %25 = bitcast %int* %24 to %"_Pshadow_Pstandard_CObject"*
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_CArray"*
    %28 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %27, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %23, %"_Pshadow_Pstandard_CObject"* %25)
    %29 = call { %int*, [1 x %int] } %18(%"_Pshadow_Pstandard_CArray"* %28)
    %30 = extractvalue { %int*, [1 x %int] } %29, 0
    %31 = getelementptr inbounds %int* %30, %int 0
    %32 = load %int* %31
    ret %int %32
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
    %indices = alloca { %int*, [1 x %int] }
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
    br %boolean %8, label %_label0, label %_label1
_label0:
    ret %"_Pshadow_Pstandard_CString"* @_string1
    br label %_label2
_label1:
    br label %_label2
_label2:
    %10 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CMutableString_Mclass"* @"_Pshadow_Pstandard_CMutableString_Mclass", i32 0, i32 0))
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %"_Pshadow_Pstandard_CMutableString"*
    %12 = bitcast %"_Pshadow_Pstandard_CString"* @_string2 to %"_Pshadow_Pstandard_CObject"*
    %13 = call %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"* %11, %"_Pshadow_Pstandard_CObject"* %12)
    store %"_Pshadow_Pstandard_CMutableString"* %11, %"_Pshadow_Pstandard_CMutableString"** %string
    %14 = load %"_Pshadow_Pstandard_CArray"** %this
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 8
    %18 = load %int (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = call %int %18(%"_Pshadow_Pstandard_CArray"* %14)
    %20 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int %19)
    %21 = bitcast %"_Pshadow_Pstandard_CObject"* %20 to %int*
    %22 = insertvalue { %int*, [1 x %int] } undef, %int* %21, 0
    %23 = insertvalue { %int*, [1 x %int] } %22, %int %19, 1, 0
    store { %int*, [1 x %int] } %23, { %int*, [1 x %int] }* %indices
    store %boolean true, %boolean* %first
    br label %_label4
_label3:
    %24 = load %boolean* %first
    br %boolean %24, label %_label6, label %_label7
_label6:
    store %boolean false, %boolean* %first
    br label %_label8
_label7:
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %25, i32 0, i32 0
    %27 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %26
    %28 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %27, i32 0, i32 7
    %29 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %28
    %30 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %31 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %32 = call %"_Pshadow_Pstandard_CMutableString"* %29(%"_Pshadow_Pstandard_CMutableString"* %30, %"_Pshadow_Pstandard_CObject"* %31)
    br label %_label8
_label8:
    %33 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %34 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %33, i32 0, i32 0
    %35 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %34
    %36 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %35, i32 0, i32 7
    %37 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %36
    %38 = load %"_Pshadow_Pstandard_CArray"** %this
    %39 = getelementptr %"_Pshadow_Pstandard_CArray"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CArray_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %40, i32 0, i32 10
    %42 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })** %41
    %43 = load %"_Pshadow_Pstandard_CArray"** %this
    %44 = load { %int*, [1 x %int] }* %indices
    %45 = call %"_Pshadow_Pstandard_CObject"* %42(%"_Pshadow_Pstandard_CArray"* %43, { %int*, [1 x %int] } %44)
    %46 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %47 = bitcast %"_Pshadow_Pstandard_CObject"* %45 to %"_Pshadow_Pstandard_CObject"*
    %48 = call %"_Pshadow_Pstandard_CMutableString"* %37(%"_Pshadow_Pstandard_CMutableString"* %46, %"_Pshadow_Pstandard_CObject"* %47)
    %49 = load { %int*, [1 x %int] }* %indices
    %50 = extractvalue { %int*, [1 x %int] } %49, 1
    %51 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %52 = bitcast %"_Pshadow_Pstandard_CObject"* %51 to [1 x %int]*
    store [1 x %int] %50, [1 x %int]* %52
    %53 = getelementptr inbounds [1 x %int]* %52, i32 0, i32 0
    %54 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %53, 0
    %55 = extractvalue { %int*, [1 x %int] } %49, 0
    %56 = bitcast %int* %55 to %"_Pshadow_Pstandard_CObject"*
    %57 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %58 = bitcast %"_Pshadow_Pstandard_CObject"* %57 to %"_Pshadow_Pstandard_CArray"*
    %59 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %58, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %54, %"_Pshadow_Pstandard_CObject"* %56)
    %60 = getelementptr %"_Pshadow_Pstandard_CArray"* %59, i32 0, i32 0
    %61 = load %"_Pshadow_Pstandard_CArray_Mclass"** %60
    %62 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %61, i32 0, i32 13
    %63 = load %int (%"_Pshadow_Pstandard_CArray"*)** %62
    %64 = extractvalue { %int*, [1 x %int] } %49, 1
    %65 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %66 = bitcast %"_Pshadow_Pstandard_CObject"* %65 to [1 x %int]*
    store [1 x %int] %64, [1 x %int]* %66
    %67 = getelementptr inbounds [1 x %int]* %66, i32 0, i32 0
    %68 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %67, 0
    %69 = extractvalue { %int*, [1 x %int] } %49, 0
    %70 = bitcast %int* %69 to %"_Pshadow_Pstandard_CObject"*
    %71 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %72 = bitcast %"_Pshadow_Pstandard_CObject"* %71 to %"_Pshadow_Pstandard_CArray"*
    %73 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %72, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %68, %"_Pshadow_Pstandard_CObject"* %70)
    %74 = call %int %63(%"_Pshadow_Pstandard_CArray"* %73)
    %75 = sub %int %74, 1
    store %int %75, %int* %i
    %76 = load { %int*, [1 x %int] }* %indices
    %77 = load %int* %i
    %78 = extractvalue { %int*, [1 x %int] } %76, 0
    %79 = getelementptr inbounds %int* %78, %int %77
    %80 = load %int* %79
    %81 = add %int %80, 1
    store %int %81, %int* %79
    br label %_label10
_label9:
    %82 = load { %int*, [1 x %int] }* %indices
    %83 = load %int* %i
    %84 = extractvalue { %int*, [1 x %int] } %82, 0
    %85 = getelementptr inbounds %int* %84, %int %83
    store %int 0, %int* %85
    %86 = load %int* %i
    %87 = sub %int %86, 1
    store %int %87, %int* %i
    %88 = load { %int*, [1 x %int] }* %indices
    %89 = load %int* %i
    %90 = extractvalue { %int*, [1 x %int] } %88, 0
    %91 = getelementptr inbounds %int* %90, %int %89
    %92 = load %int* %91
    %93 = add %int %92, 1
    store %int %93, %int* %91
    br label %_label10
_label10:
    %94 = load %int* %i
    %95 = icmp sgt %int %94, 0
    store %boolean %95, %boolean* %_temp
    br %boolean %95, label %_label13, label %_label12
_label13:
    %96 = load { %int*, [1 x %int] }* %indices
    %97 = load %int* %i
    %98 = extractvalue { %int*, [1 x %int] } %96, 0
    %99 = getelementptr inbounds %int* %98, %int %97
    %100 = load %"_Pshadow_Pstandard_CArray"** %this
    %101 = getelementptr %"_Pshadow_Pstandard_CArray"* %100, i32 0, i32 0
    %102 = load %"_Pshadow_Pstandard_CArray_Mclass"** %101
    %103 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %102, i32 0, i32 12
    %104 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %103
    %105 = call { %int*, [1 x %int] } %104(%"_Pshadow_Pstandard_CArray"* %100)
    %106 = load %int* %i
    %107 = extractvalue { %int*, [1 x %int] } %105, 0
    %108 = getelementptr inbounds %int* %107, %int %106
    %109 = load %int* %99
    %110 = load %int* %108
    %111 = icmp eq %int %109, %110
    store %boolean %111, %boolean* %_temp
    br label %_label12
_label12:
    %112 = load %boolean* %_temp
    br %boolean %112, label %_label9, label %_label11
_label11:
    br label %_label4
_label4:
    %113 = load { %int*, [1 x %int] }* %indices
    %114 = extractvalue { %int*, [1 x %int] } %113, 0
    %115 = getelementptr inbounds %int* %114, %int 0
    %116 = load %"_Pshadow_Pstandard_CArray"** %this
    %117 = getelementptr %"_Pshadow_Pstandard_CArray"* %116, i32 0, i32 0
    %118 = load %"_Pshadow_Pstandard_CArray_Mclass"** %117
    %119 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %118, i32 0, i32 12
    %120 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %119
    %121 = call { %int*, [1 x %int] } %120(%"_Pshadow_Pstandard_CArray"* %116)
    %122 = extractvalue { %int*, [1 x %int] } %121, 0
    %123 = getelementptr inbounds %int* %122, %int 0
    %124 = load %int* %115
    %125 = load %int* %123
    %126 = icmp eq %int %124, %125
    %127 = xor %boolean %126, true
    br %boolean %127, label %_label3, label %_label5
_label5:
    %128 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %129 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %128, i32 0, i32 0
    %130 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %129
    %131 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %130, i32 0, i32 7
    %132 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %131
    %133 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %134 = bitcast %"_Pshadow_Pstandard_CString"* @_string4 to %"_Pshadow_Pstandard_CObject"*
    %135 = call %"_Pshadow_Pstandard_CMutableString"* %132(%"_Pshadow_Pstandard_CMutableString"* %133, %"_Pshadow_Pstandard_CObject"* %134)
    %136 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %135, i32 0, i32 0
    %137 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %136
    %138 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %137, i32 0, i32 6
    %139 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %138
    %140 = call %"_Pshadow_Pstandard_CString"* %139(%"_Pshadow_Pstandard_CMutableString"* %135)
    ret %"_Pshadow_Pstandard_CString"* %140
}

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %lengths = alloca { %int*, [1 x %int] }
    %data = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store { %int*, [1 x %int] } %2, { %int*, [1 x %int] }* %lengths
    store %"_Pshadow_Pstandard_CObject"* %3, %"_Pshadow_Pstandard_CObject"** %data
    %5 = load %"_Pshadow_Pstandard_CArray"** %this
    %6 = bitcast %"_Pshadow_Pstandard_CArray"* %5 to %"_Pshadow_Pstandard_CObject"*
    %7 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %6)
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %8
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 2
    %11 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %11, %"_Pshadow_Pstandard_CClass"** %10
    %12 = load %"_Pshadow_Pstandard_CArray"** %this
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %12, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %13
    %14 = load %"_Pshadow_Pstandard_CArray"** %this
    %15 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 1
    %16 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 0)
    %17 = bitcast %"_Pshadow_Pstandard_CObject"* %16 to %int*
    %18 = insertvalue { %int*, [1 x %int] } undef, %int* %17, 0
    %19 = insertvalue { %int*, [1 x %int] } %18, %int 0, 1, 0
    store { %int*, [1 x %int] } %19, { %int*, [1 x %int] }* %15
    %20 = load %"_Pshadow_Pstandard_CArray"** %this
    %21 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %20, i32 0, i32 1
    %22 = load { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %22, { %int*, [1 x %int] }* %21
    %23 = load %"_Pshadow_Pstandard_CArray"** %this
    %24 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %23, i32 0, i32 3
    %25 = load %"_Pshadow_Pstandard_CObject"** %data
    store %"_Pshadow_Pstandard_CObject"* %25, %"_Pshadow_Pstandard_CObject"** %24
    ret %"_Pshadow_Pstandard_CArray"* %0
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %lengths = alloca { %int*, [1 x %int] }
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store { %int*, [1 x %int] } %2, { %int*, [1 x %int] }* %lengths
    %4 = load %"_Pshadow_Pstandard_CArray"** %this
    %5 = bitcast %"_Pshadow_Pstandard_CArray"* %4 to %"_Pshadow_Pstandard_CObject"*
    %6 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %5)
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %7
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %8, i32 0, i32 2
    %10 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %10, %"_Pshadow_Pstandard_CClass"** %9
    %11 = load %"_Pshadow_Pstandard_CArray"** %this
    %12 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %11, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %12
    %13 = load %"_Pshadow_Pstandard_CArray"** %this
    %14 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %13, i32 0, i32 1
    %15 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 0)
    %16 = bitcast %"_Pshadow_Pstandard_CObject"* %15 to %int*
    %17 = insertvalue { %int*, [1 x %int] } undef, %int* %16, 0
    %18 = insertvalue { %int*, [1 x %int] } %17, %int 0, 1, 0
    store { %int*, [1 x %int] } %18, { %int*, [1 x %int] }* %14
    %19 = load { %int*, [1 x %int] }* %lengths
    %20 = extractvalue { %int*, [1 x %int] } %19, 1
    %21 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %22 = bitcast %"_Pshadow_Pstandard_CObject"* %21 to [1 x %int]*
    store [1 x %int] %20, [1 x %int]* %22
    %23 = getelementptr inbounds [1 x %int]* %22, i32 0, i32 0
    %24 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %23, 0
    %25 = extractvalue { %int*, [1 x %int] } %19, 0
    %26 = bitcast %int* %25 to %"_Pshadow_Pstandard_CObject"*
    %27 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %28 = bitcast %"_Pshadow_Pstandard_CObject"* %27 to %"_Pshadow_Pstandard_CArray"*
    %29 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %28, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %24, %"_Pshadow_Pstandard_CObject"* %26)
    %30 = getelementptr %"_Pshadow_Pstandard_CArray"* %29, i32 0, i32 0
    %31 = load %"_Pshadow_Pstandard_CArray_Mclass"** %30
    %32 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %31, i32 0, i32 1
    %33 = load %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)** %32
    %34 = load { %int*, [1 x %int] }* %lengths
    %35 = extractvalue { %int*, [1 x %int] } %34, 1
    %36 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %37 = bitcast %"_Pshadow_Pstandard_CObject"* %36 to [1 x %int]*
    store [1 x %int] %35, [1 x %int]* %37
    %38 = getelementptr inbounds [1 x %int]* %37, i32 0, i32 0
    %39 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %38, 0
    %40 = extractvalue { %int*, [1 x %int] } %34, 0
    %41 = bitcast %int* %40 to %"_Pshadow_Pstandard_CObject"*
    %42 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %43 = bitcast %"_Pshadow_Pstandard_CObject"* %42 to %"_Pshadow_Pstandard_CArray"*
    %44 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %43, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %39, %"_Pshadow_Pstandard_CObject"* %41)
    %45 = call %"_Pshadow_Pstandard_CArray"* %33(%"_Pshadow_Pstandard_CArray"* %44)
    %46 = bitcast %"_Pshadow_Pstandard_CArray"* %45 to %"_Pshadow_Pstandard_CObject"*
    %47 = bitcast %"_Pshadow_Pstandard_CObject"* %46 to %"_Pshadow_Pstandard_CArray"*
    %48 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %47, i32 0, i32 3
    %49 = load %"_Pshadow_Pstandard_CObject"** %48
    %50 = bitcast %"_Pshadow_Pstandard_CObject"* %49 to %int*
    %51 = insertvalue { %int*, [1 x %int] } undef, %int* %50, 0
    %52 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %47, i32 0, i32 1, i32 0
    %53 = load %int** %52
    %54 = bitcast %int* %53 to [1 x %int]*
    %55 = load [1 x %int]* %54
    %56 = insertvalue { %int*, [1 x %int] } %51, [1 x %int] %55, 1
    %57 = load %"_Pshadow_Pstandard_CArray"** %this
    %58 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %57, i32 0, i32 1
    store { %int*, [1 x %int] } %56, { %int*, [1 x %int] }* %58
    %59 = load %"_Pshadow_Pstandard_CArray"** %this
    %60 = load %"_Pshadow_Pstandard_CClass"** %T
    %61 = load %"_Pshadow_Pstandard_CArray"** %this
    %62 = load %"_Pshadow_Pstandard_CArray"** %this
    %63 = getelementptr %"_Pshadow_Pstandard_CArray"* %61, i32 0, i32 0
    %64 = load %"_Pshadow_Pstandard_CArray_Mclass"** %63
    %65 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %64, i32 0, i32 13
    %66 = load %int (%"_Pshadow_Pstandard_CArray"*)** %65
    %67 = call %int %66(%"_Pshadow_Pstandard_CArray"* %61)
    %68 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"* %62, %"_Pshadow_Pstandard_CClass"* %60, %int %67)
    %69 = load %"_Pshadow_Pstandard_CArray"** %this
    %70 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %69, i32 0, i32 3
    store %"_Pshadow_Pstandard_CObject"* %68, %"_Pshadow_Pstandard_CObject"** %70
    ret %"_Pshadow_Pstandard_CArray"* %0
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcopy"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %copy = alloca %"_Pshadow_Pstandard_CArray"*
    %indices = alloca { %int*, [1 x %int] }
    %i = alloca %int
    %_temp = alloca %boolean
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %2 to %"_Pshadow_Pstandard_CArray"*
    %4 = load %"_Pshadow_Pstandard_CArray"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %4, i32 0, i32 2
    %6 = load %"_Pshadow_Pstandard_CClass"** %5
    %7 = load %"_Pshadow_Pstandard_CArray"** %this
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 2
    %11 = load %"_Pshadow_Pstandard_CClass"** %10
    %12 = load %"_Pshadow_Pstandard_CArray"** %this
    %13 = load %"_Pshadow_Pstandard_CArray"** %this
    %14 = getelementptr %"_Pshadow_Pstandard_CArray"* %12, i32 0, i32 0
    %15 = load %"_Pshadow_Pstandard_CArray_Mclass"** %14
    %16 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %15, i32 0, i32 13
    %17 = load %int (%"_Pshadow_Pstandard_CArray"*)** %16
    %18 = call %int %17(%"_Pshadow_Pstandard_CArray"* %12)
    %19 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"* %13, %"_Pshadow_Pstandard_CClass"* %11, %int %18)
    %20 = getelementptr %"_Pshadow_Pstandard_CArray"* %7, i32 0, i32 0
    %21 = load %"_Pshadow_Pstandard_CArray_Mclass"** %20
    %22 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %21, i32 0, i32 12
    %23 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %22
    %24 = call { %int*, [1 x %int] } %23(%"_Pshadow_Pstandard_CArray"* %7)
    %25 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CClass"* %6, { %int*, [1 x %int] } %24, %"_Pshadow_Pstandard_CObject"* %19)
    store %"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CArray"** %copy
    %26 = load %"_Pshadow_Pstandard_CArray"** %this
    %27 = getelementptr %"_Pshadow_Pstandard_CArray"* %26, i32 0, i32 0
    %28 = load %"_Pshadow_Pstandard_CArray_Mclass"** %27
    %29 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %28, i32 0, i32 8
    %30 = load %int (%"_Pshadow_Pstandard_CArray"*)** %29
    %31 = call %int %30(%"_Pshadow_Pstandard_CArray"* %26)
    %32 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int %31)
    %33 = bitcast %"_Pshadow_Pstandard_CObject"* %32 to %int*
    %34 = insertvalue { %int*, [1 x %int] } undef, %int* %33, 0
    %35 = insertvalue { %int*, [1 x %int] } %34, %int %31, 1, 0
    store { %int*, [1 x %int] } %35, { %int*, [1 x %int] }* %indices
    br label %_label15
_label14:
    %36 = load %"_Pshadow_Pstandard_CArray"** %copy
    %37 = getelementptr %"_Pshadow_Pstandard_CArray"* %36, i32 0, i32 0
    %38 = load %"_Pshadow_Pstandard_CArray_Mclass"** %37
    %39 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %38, i32 0, i32 11
    %40 = load void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)** %39
    %41 = load %"_Pshadow_Pstandard_CArray"** %this
    %42 = getelementptr %"_Pshadow_Pstandard_CArray"* %41, i32 0, i32 0
    %43 = load %"_Pshadow_Pstandard_CArray_Mclass"** %42
    %44 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %43, i32 0, i32 10
    %45 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })** %44
    %46 = load %"_Pshadow_Pstandard_CArray"** %this
    %47 = load { %int*, [1 x %int] }* %indices
    %48 = call %"_Pshadow_Pstandard_CObject"* %45(%"_Pshadow_Pstandard_CArray"* %46, { %int*, [1 x %int] } %47)
    %49 = load %"_Pshadow_Pstandard_CArray"** %copy
    %50 = load { %int*, [1 x %int] }* %indices
    call void %40(%"_Pshadow_Pstandard_CArray"* %49, { %int*, [1 x %int] } %50, %"_Pshadow_Pstandard_CObject"* %48)
    %51 = load { %int*, [1 x %int] }* %indices
    %52 = extractvalue { %int*, [1 x %int] } %51, 1
    %53 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %54 = bitcast %"_Pshadow_Pstandard_CObject"* %53 to [1 x %int]*
    store [1 x %int] %52, [1 x %int]* %54
    %55 = getelementptr inbounds [1 x %int]* %54, i32 0, i32 0
    %56 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %55, 0
    %57 = extractvalue { %int*, [1 x %int] } %51, 0
    %58 = bitcast %int* %57 to %"_Pshadow_Pstandard_CObject"*
    %59 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %60 = bitcast %"_Pshadow_Pstandard_CObject"* %59 to %"_Pshadow_Pstandard_CArray"*
    %61 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %60, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %56, %"_Pshadow_Pstandard_CObject"* %58)
    %62 = getelementptr %"_Pshadow_Pstandard_CArray"* %61, i32 0, i32 0
    %63 = load %"_Pshadow_Pstandard_CArray_Mclass"** %62
    %64 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %63, i32 0, i32 13
    %65 = load %int (%"_Pshadow_Pstandard_CArray"*)** %64
    %66 = extractvalue { %int*, [1 x %int] } %51, 1
    %67 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %68 = bitcast %"_Pshadow_Pstandard_CObject"* %67 to [1 x %int]*
    store [1 x %int] %66, [1 x %int]* %68
    %69 = getelementptr inbounds [1 x %int]* %68, i32 0, i32 0
    %70 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %69, 0
    %71 = extractvalue { %int*, [1 x %int] } %51, 0
    %72 = bitcast %int* %71 to %"_Pshadow_Pstandard_CObject"*
    %73 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %74 = bitcast %"_Pshadow_Pstandard_CObject"* %73 to %"_Pshadow_Pstandard_CArray"*
    %75 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %74, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %70, %"_Pshadow_Pstandard_CObject"* %72)
    %76 = call %int %65(%"_Pshadow_Pstandard_CArray"* %75)
    %77 = sub %int %76, 1
    store %int %77, %int* %i
    %78 = load { %int*, [1 x %int] }* %indices
    %79 = load %int* %i
    %80 = extractvalue { %int*, [1 x %int] } %78, 0
    %81 = getelementptr inbounds %int* %80, %int %79
    %82 = load %int* %81
    %83 = add %int %82, 1
    store %int %83, %int* %81
    br label %_label18
_label17:
    %84 = load { %int*, [1 x %int] }* %indices
    %85 = load %int* %i
    %86 = extractvalue { %int*, [1 x %int] } %84, 0
    %87 = getelementptr inbounds %int* %86, %int %85
    store %int 0, %int* %87
    %88 = load %int* %i
    %89 = sub %int %88, 1
    store %int %89, %int* %i
    %90 = load { %int*, [1 x %int] }* %indices
    %91 = load %int* %i
    %92 = extractvalue { %int*, [1 x %int] } %90, 0
    %93 = getelementptr inbounds %int* %92, %int %91
    %94 = load %int* %93
    %95 = add %int %94, 1
    store %int %95, %int* %93
    br label %_label18
_label18:
    %96 = load %int* %i
    %97 = icmp sgt %int %96, 0
    store %boolean %97, %boolean* %_temp
    br %boolean %97, label %_label21, label %_label20
_label21:
    %98 = load { %int*, [1 x %int] }* %indices
    %99 = load %int* %i
    %100 = extractvalue { %int*, [1 x %int] } %98, 0
    %101 = getelementptr inbounds %int* %100, %int %99
    %102 = load %"_Pshadow_Pstandard_CArray"** %this
    %103 = getelementptr %"_Pshadow_Pstandard_CArray"* %102, i32 0, i32 0
    %104 = load %"_Pshadow_Pstandard_CArray_Mclass"** %103
    %105 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %104, i32 0, i32 12
    %106 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %105
    %107 = call { %int*, [1 x %int] } %106(%"_Pshadow_Pstandard_CArray"* %102)
    %108 = load %int* %i
    %109 = extractvalue { %int*, [1 x %int] } %107, 0
    %110 = getelementptr inbounds %int* %109, %int %108
    %111 = load %int* %101
    %112 = load %int* %110
    %113 = icmp eq %int %111, %112
    store %boolean %113, %boolean* %_temp
    br label %_label20
_label20:
    %114 = load %boolean* %_temp
    br %boolean %114, label %_label17, label %_label19
_label19:
    br label %_label15
_label15:
    %115 = load { %int*, [1 x %int] }* %indices
    %116 = extractvalue { %int*, [1 x %int] } %115, 0
    %117 = getelementptr inbounds %int* %116, %int 0
    %118 = load %"_Pshadow_Pstandard_CArray"** %this
    %119 = getelementptr %"_Pshadow_Pstandard_CArray"* %118, i32 0, i32 0
    %120 = load %"_Pshadow_Pstandard_CArray_Mclass"** %119
    %121 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %120, i32 0, i32 12
    %122 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %121
    %123 = call { %int*, [1 x %int] } %122(%"_Pshadow_Pstandard_CArray"* %118)
    %124 = extractvalue { %int*, [1 x %int] } %123, 0
    %125 = getelementptr inbounds %int* %124, %int 0
    %126 = load %int* %117
    %127 = load %int* %125
    %128 = icmp eq %int %126, %127
    %129 = xor %boolean %128, true
    br %boolean %129, label %_label14, label %_label16
_label16:
    %130 = load %"_Pshadow_Pstandard_CArray"** %copy
    ret %"_Pshadow_Pstandard_CArray"* %130
}

define %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %size = alloca %int
    %i = alloca %int
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 1
    %4 = load { %int*, [1 x %int] }* %3
    %5 = extractvalue { %int*, [1 x %int] } %4, 0
    %6 = getelementptr inbounds %int* %5, %int 0
    %7 = load %int* %6
    store %int %7, %int* %size
    store %int 1, %int* %i
    br label %_label23
_label22:
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %8, i32 0, i32 1
    %10 = load { %int*, [1 x %int] }* %9
    %11 = load %int* %i
    %12 = extractvalue { %int*, [1 x %int] } %10, 0
    %13 = getelementptr inbounds %int* %12, %int %11
    %14 = load %int* %size
    %15 = load %int* %13
    %16 = mul %int %14, %15
    store %int %16, %int* %size
    %17 = load %int* %i
    %18 = add %int %17, 1
    store %int %18, %int* %i
    br label %_label23
_label23:
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
    %34 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %33, i32 0, i32 12
    %35 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %34
    %36 = extractvalue { %int*, [1 x %int] } %21, 1
    %37 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to [1 x %int]*
    store [1 x %int] %36, [1 x %int]* %38
    %39 = getelementptr inbounds [1 x %int]* %38, i32 0, i32 0
    %40 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %39, 0
    %41 = extractvalue { %int*, [1 x %int] } %21, 0
    %42 = bitcast %int* %41 to %"_Pshadow_Pstandard_CObject"*
    %43 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %44 = bitcast %"_Pshadow_Pstandard_CObject"* %43 to %"_Pshadow_Pstandard_CArray"*
    %45 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %44, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %40, %"_Pshadow_Pstandard_CObject"* %42)
    %46 = call { %int*, [1 x %int] } %35(%"_Pshadow_Pstandard_CArray"* %45)
    %47 = extractvalue { %int*, [1 x %int] } %46, 0
    %48 = getelementptr inbounds %int* %47, %int 0
    %49 = load %int* %i
    %50 = load %int* %48
    %51 = icmp slt %int %49, %50
    br %boolean %51, label %_label22, label %_label24
_label24:
    %52 = load %int* %size
    ret %int %52
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

declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mappend_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)
declare %byte @"_Pshadow_Pstandard_CMutableString_Mindex_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare void @"_Pshadow_Pstandard_CMutableString_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cbyte"(%"_Pshadow_Pstandard_CMutableString"*, %int, %byte)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mreverse"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_MensureCapacity_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare %int @"_Pshadow_Pstandard_CMutableString_Mcapacity"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CMutableString_MtoString"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)
declare %int @"_Pshadow_Pstandard_CMutableString_Msize"(%"_Pshadow_Pstandard_CMutableString"*)

@_array0 = private unnamed_addr constant [24 x %ubyte] c"shadow.standard@Array<T>"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([24 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 24] }, %boolean true }
@_array1 = private unnamed_addr constant [2 x %ubyte] c"[]"
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([2 x %byte]* @_array1, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array2 = private unnamed_addr constant [2 x %ubyte] c"[ "
@_string2 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([2 x %byte]* @_array2, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array3 = private unnamed_addr constant [2 x %ubyte] c", "
@_string3 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([2 x %byte]* @_array3, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
@_array4 = private unnamed_addr constant [2 x %ubyte] c" ]"
@_string4 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([2 x %byte]* @_array4, i32 0, i32 0), [1 x %int] [%int 2] }, %boolean true }
