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

declare i32 @__shadow_personality_v0(...)
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CIllegalArgumentException"* (%"_Pshadow_Pstandard_CIllegalArgumentException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CIllegalArgumentException" = type { %"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cint"* (%"_Pshadow_Pstandard_Cint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*)*, %uint (%"_Pshadow_Pstandard_Cint"*)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %int (%"_Pshadow_Pstandard_Cint"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cint"*, %uint)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"*, %int }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cushort"* (%"_Pshadow_Pstandard_Cushort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %int (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)*, %ushort (%"_Pshadow_Pstandard_Cushort"*, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"*, %ushort }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Ccode"* (%"_Pshadow_Pstandard_Ccode"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)*, %code (%"_Pshadow_Pstandard_Ccode"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"*, %code }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cdouble"* (%"_Pshadow_Pstandard_Cdouble"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cdouble"*)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %int (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)*, %double (%"_Pshadow_Pstandard_Cdouble"*, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"*, %double }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Clong"* (%"_Pshadow_Pstandard_Clong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*)*, %ulong (%"_Pshadow_Pstandard_Clong"*)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %int (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %long (%"_Pshadow_Pstandard_Clong"*, %long)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Clong"*, %ulong)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"*, %long }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cfloat"* (%"_Pshadow_Pstandard_Cfloat"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %int (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)*, %float (%"_Pshadow_Pstandard_Cfloat"*, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"*, %float }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cboolean"* (%"_Pshadow_Pstandard_Cboolean"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cboolean"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"*, %boolean }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
%"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString_IStringIterator"* (%"_Pshadow_Pstandard_CString_IStringIterator"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)*, %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)* }
%"_Pshadow_Pstandard_CString_IStringIterator" = type { %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"*, %"_Pshadow_Pstandard_CString"*, %int }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Culong"* (%"_Pshadow_Pstandard_Culong"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %int (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %ulong (%"_Pshadow_Pstandard_Culong"*, %ulong)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Culong"*, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"*, %ulong }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cubyte"* (%"_Pshadow_Pstandard_Cubyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %int (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %ubyte (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cubyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"*, %ubyte }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cuint"* (%"_Pshadow_Pstandard_Cuint"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %int (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %uint (%"_Pshadow_Pstandard_Cuint"*, %uint)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cuint"*, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"*, %uint }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }
%"_Pshadow_Pstandard_CMutableString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)* }
%"_Pshadow_Pstandard_CMutableString" = type { %"_Pshadow_Pstandard_CMutableString_Mclass"* }

@"_Pshadow_Pstandard_CString_Mclass" = constant %"_Pshadow_Pstandard_CString_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CString"* getelementptr (%"_Pshadow_Pstandard_CString"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CString"** getelementptr (%"_Pshadow_Pstandard_CString"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoString", { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mchars", %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString", %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString", %byte (%"_Pshadow_Pstandard_CString"*, %int)* @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint", %boolean (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MisEmpty", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Miterator", %int (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mlength", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoLowerCase", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoUpperCase" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_CIllegalArgumentException_Mclass" = external constant %"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external constant %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external constant %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external constant %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = external constant %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external constant %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external constant %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external constant %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external constant %"_Pshadow_Pstandard_CObject_Mclass"
@"_Pshadow_Pstandard_CMutableString_Mclass" = external constant %"_Pshadow_Pstandard_CMutableString_Mclass"

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
    %9 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %10 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %11, i32 0, i32 7
    %13 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %12
    %14 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %15 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %16, i32 0, i32 8
    %18 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %17
    %19 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %20 = call %code %18(%"_Pshadow_Pstandard_CString_IStringIterator"* %19)
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"*%21 to %"_Pshadow_Pstandard_Ccode"*
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%22, i32 0, i32 1
    store %code %20, %code* %23
    %24 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %22)
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_Ccode"*
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %28
    %29 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 1
    store %code %24, %code* %29
    %30 = call %"_Pshadow_Pstandard_CMutableString"* %13(%"_Pshadow_Pstandard_CMutableString"* %25, %"_Pshadow_Pstandard_CObject"* %26)
    br label %_label1
_label1:
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %33, i32 0, i32 7
    %35 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %34
    %36 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %37 = call %boolean %35(%"_Pshadow_Pstandard_CString_IStringIterator"* %36)
    br %boolean %37, label %_label0, label %_label2
_label2:
    %38 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %39 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %40, i32 0, i32 6
    %42 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %41
    %43 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %44 = call %"_Pshadow_Pstandard_CString"* %42(%"_Pshadow_Pstandard_CMutableString"* %43)
    ret %"_Pshadow_Pstandard_CString"* %44
}

define %boolean @"_Pshadow_Pstandard_CString_MisEmpty"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %2)
    %4 = icmp eq %int %3, 0
    ret %boolean %4
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
    %5 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %3)
    %6 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %4)
    %7 = icmp eq %int %5, %6
    %8 = xor %boolean %7, true
    store %boolean %8, %boolean* %_temp
    br %boolean %8, label %_label4, label %_label3
_label4:
    %9 = load %"_Pshadow_Pstandard_CString"** %this
    %10 = load %"_Pshadow_Pstandard_CString"** %this
    %11 = load %"_Pshadow_Pstandard_CString"** %other
    %12 = call %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %10, %"_Pshadow_Pstandard_CString"* %11)
    %13 = icmp eq %int %12, 0
    store %boolean %13, %boolean* %_temp
    br label %_label3
_label3:
    %14 = load %boolean* %_temp
    ret %boolean %14
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
    %9 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %10 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %11, i32 0, i32 7
    %13 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %12
    %14 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %15 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %16, i32 0, i32 8
    %18 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %17
    %19 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %20 = call %code %18(%"_Pshadow_Pstandard_CString_IStringIterator"* %19)
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"*%21 to %"_Pshadow_Pstandard_Ccode"*
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%22, i32 0, i32 1
    store %code %20, %code* %23
    %24 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %22)
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_Ccode"*
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %28
    %29 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 1
    store %code %24, %code* %29
    %30 = call %"_Pshadow_Pstandard_CMutableString"* %13(%"_Pshadow_Pstandard_CMutableString"* %25, %"_Pshadow_Pstandard_CObject"* %26)
    br label %_label6
_label6:
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %33, i32 0, i32 7
    %35 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %34
    %36 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %37 = call %boolean %35(%"_Pshadow_Pstandard_CString_IStringIterator"* %36)
    br %boolean %37, label %_label5, label %_label7
_label7:
    %38 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %39 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %40, i32 0, i32 6
    %42 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)** %41
    %43 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %44 = call %"_Pshadow_Pstandard_CString"* %42(%"_Pshadow_Pstandard_CMutableString"* %43)
    ret %"_Pshadow_Pstandard_CString"* %44
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
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = load %int* %i
    %6 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %4, %int %5)
    %7 = load %"_Pshadow_Pstandard_CString"** %other
    %8 = load %"_Pshadow_Pstandard_CString"** %other
    %9 = load %int* %i
    %10 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %8, %int %9)
    %11 = icmp eq %byte %6, %10
    %12 = xor %boolean %11, true
    br %boolean %12, label %_label11, label %_label12
_label11:
    %13 = load %"_Pshadow_Pstandard_CString"** %this
    %14 = load %"_Pshadow_Pstandard_CString"** %this
    %15 = load %int* %i
    %16 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %14, %int %15)
    %17 = load %"_Pshadow_Pstandard_CString"** %other
    %18 = load %"_Pshadow_Pstandard_CString"** %other
    %19 = load %int* %i
    %20 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %18, %int %19)
    %21 = sub %byte %16, %20
    %22 = sext %byte %21 to %int
    ret %int %22
    br label %_label13
_label12:
    br label %_label13
_label13:
    %24 = load %int* %i
    %25 = add %int %24, 1
    store %int %25, %int* %i
    br label %_label9
_label9:
    %26 = load %"_Pshadow_Pstandard_CString"** %this
    %27 = load %int* %i
    %28 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %26)
    %29 = icmp slt %int %27, %28
    store %boolean %29, %boolean* %_temp
    br %boolean %29, label %_label15, label %_label14
_label15:
    %30 = load %"_Pshadow_Pstandard_CString"** %other
    %31 = load %int* %i
    %32 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %30)
    %33 = icmp slt %int %31, %32
    store %boolean %33, %boolean* %_temp
    br label %_label14
_label14:
    %34 = load %boolean* %_temp
    br %boolean %34, label %_label8, label %_label10
_label10:
    %35 = load %"_Pshadow_Pstandard_CString"** %this
    %36 = load %"_Pshadow_Pstandard_CString"** %other
    %37 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %35)
    %38 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %36)
    %39 = sub %int %37, %38
    ret %int %39
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
    %19 = getelementptr %"_Pshadow_Pstandard_CArray"* %18, i32 0, i32 0
    %20 = load %"_Pshadow_Pstandard_CArray_Mclass"** %19
    %21 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %20, i32 0, i32 14
    %22 = load %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)** %21
    %23 = load { %byte*, [1 x %int] }* %7
    %24 = extractvalue { %byte*, [1 x %int] } %23, 1
    %25 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %26 = bitcast %"_Pshadow_Pstandard_CObject"* %25 to [1 x %int]*
    store [1 x %int] %24, [1 x %int]* %26
    %27 = getelementptr inbounds [1 x %int]* %26, i32 0, i32 0
    %28 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %27, 0
    %29 = extractvalue { %byte*, [1 x %int] } %23, 0
    %30 = bitcast %byte* %29 to %"_Pshadow_Pstandard_CObject"*
    %31 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to %"_Pshadow_Pstandard_CArray"*
    %33 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %32, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %28, %"_Pshadow_Pstandard_CObject"* %30)
    %34 = load %int* %start
    %35 = load %int* %end
    %36 = call %"_Pshadow_Pstandard_CArray"* %22(%"_Pshadow_Pstandard_CArray"* %33, %int %34, %int %35)
    %37 = bitcast %"_Pshadow_Pstandard_CArray"* %36 to %"_Pshadow_Pstandard_CObject"*
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to %"_Pshadow_Pstandard_CArray"*
    %39 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %38, i32 0, i32 3
    %40 = load %"_Pshadow_Pstandard_CObject"** %39
    %41 = bitcast %"_Pshadow_Pstandard_CObject"* %40 to %byte*
    %42 = insertvalue { %byte*, [1 x %int] } undef, %byte* %41, 0
    %43 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %38, i32 0, i32 1, i32 0
    %44 = load %int** %43
    %45 = bitcast %int* %44 to [1 x %int]*
    %46 = load [1 x %int]* %45
    %47 = insertvalue { %byte*, [1 x %int] } %42, [1 x %int] %46, 1
    %48 = load %"_Pshadow_Pstandard_CString"** %this
    %49 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %48, i32 0, i32 2
    %50 = load %boolean* %49
    %51 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"* %5, { %byte*, [1 x %int] } %47, %boolean %50)
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
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 1
    %18 = load %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = load { %byte*, [1 x %int] }* %3
    %20 = extractvalue { %byte*, [1 x %int] } %19, 1
    %21 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %22 = bitcast %"_Pshadow_Pstandard_CObject"* %21 to [1 x %int]*
    store [1 x %int] %20, [1 x %int]* %22
    %23 = getelementptr inbounds [1 x %int]* %22, i32 0, i32 0
    %24 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %23, 0
    %25 = extractvalue { %byte*, [1 x %int] } %19, 0
    %26 = bitcast %byte* %25 to %"_Pshadow_Pstandard_CObject"*
    %27 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %28 = bitcast %"_Pshadow_Pstandard_CObject"* %27 to %"_Pshadow_Pstandard_CArray"*
    %29 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %28, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %24, %"_Pshadow_Pstandard_CObject"* %26)
    %30 = call %"_Pshadow_Pstandard_CArray"* %18(%"_Pshadow_Pstandard_CArray"* %29)
    %31 = bitcast %"_Pshadow_Pstandard_CArray"* %30 to %"_Pshadow_Pstandard_CObject"*
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to %"_Pshadow_Pstandard_CArray"*
    %33 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %32, i32 0, i32 3
    %34 = load %"_Pshadow_Pstandard_CObject"** %33
    %35 = bitcast %"_Pshadow_Pstandard_CObject"* %34 to %byte*
    %36 = insertvalue { %byte*, [1 x %int] } undef, %byte* %35, 0
    %37 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %32, i32 0, i32 1, i32 0
    %38 = load %int** %37
    %39 = bitcast %int* %38 to [1 x %int]*
    %40 = load [1 x %int]* %39
    %41 = insertvalue { %byte*, [1 x %int] } %36, [1 x %int] %40, 1
    ret { %byte*, [1 x %int] } %41
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = bitcast %"_Pshadow_Pstandard_CString"* %2 to %"_Pshadow_Pstandard_CObject"*
    %4 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %3)
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %5
    %6 = load %"_Pshadow_Pstandard_CString"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %6, i32 0, i32 2
    store %boolean true, %boolean* %7
    %8 = load %"_Pshadow_Pstandard_CString"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %8, i32 0, i32 1
    %10 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %byte*
    %12 = insertvalue { %byte*, [1 x %int] } undef, %byte* %11, 0
    %13 = insertvalue { %byte*, [1 x %int] } %12, %int 0, 1, 0
    store { %byte*, [1 x %int] } %13, { %byte*, [1 x %int] }* %9
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %byte*, [1 x %int] }
    %ascii = alloca %byte
    %i = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %byte*, [1 x %int] } %1, { %byte*, [1 x %int] }* %data
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = bitcast %"_Pshadow_Pstandard_CString"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %6
    %7 = load %"_Pshadow_Pstandard_CString"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %7, i32 0, i32 2
    store %boolean true, %boolean* %8
    %9 = load %"_Pshadow_Pstandard_CString"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %9, i32 0, i32 1
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %byte*
    %13 = insertvalue { %byte*, [1 x %int] } undef, %byte* %12, 0
    %14 = insertvalue { %byte*, [1 x %int] } %13, %int 0, 1, 0
    store { %byte*, [1 x %int] } %14, { %byte*, [1 x %int] }* %10
    store %byte 0, %byte* %ascii
    %15 = load { %byte*, [1 x %int] }* %data
    %16 = extractvalue { %byte*, [1 x %int] } %15, 1, 0
    %17 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %16)
    %18 = bitcast %"_Pshadow_Pstandard_CObject"* %17 to %byte*
    %19 = insertvalue { %byte*, [1 x %int] } undef, %byte* %18, 0
    %20 = insertvalue { %byte*, [1 x %int] } %19, %int %16, 1, 0
    %21 = load %"_Pshadow_Pstandard_CString"** %this
    %22 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %21, i32 0, i32 1
    store { %byte*, [1 x %int] } %20, { %byte*, [1 x %int] }* %22
    store %int 0, %int* %i
    br label %_label17
_label16:
    %23 = load { %byte*, [1 x %int] }* %data
    %24 = load %int* %i
    %25 = extractvalue { %byte*, [1 x %int] } %23, 0
    %26 = getelementptr inbounds %byte* %25, %int %24
    %27 = load %"_Pshadow_Pstandard_CString"** %this
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %27, i32 0, i32 1
    %29 = load { %byte*, [1 x %int] }* %28
    %30 = load %int* %i
    %31 = extractvalue { %byte*, [1 x %int] } %29, 0
    %32 = getelementptr inbounds %byte* %31, %int %30
    %33 = load %byte* %26
    store %byte %33, %byte* %32
    %34 = load { %byte*, [1 x %int] }* %data
    %35 = load %int* %i
    %36 = extractvalue { %byte*, [1 x %int] } %34, 0
    %37 = getelementptr inbounds %byte* %36, %int %35
    %38 = load %byte* %ascii
    %39 = load %byte* %37
    %40 = or %byte %38, %39
    store %byte %40, %byte* %ascii
    %41 = load %int* %i
    %42 = add %int %41, 1
    store %int %42, %int* %i
    br label %_label17
_label17:
    %43 = load { %byte*, [1 x %int] }* %data
    %44 = extractvalue { %byte*, [1 x %int] } %43, 1, 0
    %45 = load %int* %i
    %46 = icmp slt %int %45, %44
    br %boolean %46, label %_label16, label %_label18
_label18:
    %47 = load %byte* %ascii
    %48 = sext %byte %47 to %int
    %49 = icmp slt %int %48, 0
    %50 = load %"_Pshadow_Pstandard_CString"** %this
    %51 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %50, i32 0, i32 2
    store %boolean %49, %boolean* %51
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
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %code*, [1 x %int] } %1, { %code*, [1 x %int] }* %data
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = bitcast %"_Pshadow_Pstandard_CString"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %6
    %7 = load %"_Pshadow_Pstandard_CString"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %7, i32 0, i32 2
    store %boolean true, %boolean* %8
    %9 = load %"_Pshadow_Pstandard_CString"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %9, i32 0, i32 1
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %byte*
    %13 = insertvalue { %byte*, [1 x %int] } undef, %byte* %12, 0
    %14 = insertvalue { %byte*, [1 x %int] } %13, %int 0, 1, 0
    store { %byte*, [1 x %int] } %14, { %byte*, [1 x %int] }* %10
    %15 = load { %code*, [1 x %int] }* %data
    %16 = extractvalue { %code*, [1 x %int] } %15, 1, 0
    store %int %16, %int* %length
    store %int 0, %int* %i
    br label %_label20
_label19:
    %17 = load { %code*, [1 x %int] }* %data
    %18 = load %int* %i
    %19 = extractvalue { %code*, [1 x %int] } %17, 0
    %20 = getelementptr inbounds %code* %19, %int %18
    %21 = load %code* %20
    %22 = bitcast %code %21 to %int
    %23 = icmp slt %int %22, 0
    br %boolean %23, label %_label22, label %_label23
_label22:
    %24 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mclass", i32 0, i32 0))
    %25 = bitcast %"_Pshadow_Pstandard_CObject"* %24 to %"_Pshadow_Pstandard_CIllegalArgumentException"*
    %26 = call %"_Pshadow_Pstandard_CIllegalArgumentException"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mcreate"(%"_Pshadow_Pstandard_CIllegalArgumentException"* %25)
    %27 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %25 to %"_Pshadow_Pstandard_CException"*
    %28 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %25 to %"_Pshadow_Pstandard_CException"*
    call void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"* %28)
    br label %_label24
_label23:
    %29 = load { %code*, [1 x %int] }* %data
    %30 = load %int* %i
    %31 = extractvalue { %code*, [1 x %int] } %29, 0
    %32 = getelementptr inbounds %code* %31, %int %30
    %33 = shl %int 1, 7
    %34 = load %code* %32
    %35 = bitcast %code %34 to %int
    %36 = icmp sge %int %35, %33
    br %boolean %36, label %_label25, label %_label26
_label25:
    %37 = load %"_Pshadow_Pstandard_CString"** %this
    %38 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %37, i32 0, i32 2
    store %boolean false, %boolean* %38
    %39 = load %int* %length
    %40 = add %int %39, 1
    store %int %40, %int* %length
    store %int 11, %int* %shift
    br label %_label29
_label28:
    %41 = load %int* %length
    %42 = add %int %41, 1
    store %int %42, %int* %length
    %43 = load %int* %shift
    %44 = add %int %43, 5
    store %int %44, %int* %shift
    br label %_label29
_label29:
    %45 = load { %code*, [1 x %int] }* %data
    %46 = load %int* %i
    %47 = extractvalue { %code*, [1 x %int] } %45, 0
    %48 = getelementptr inbounds %code* %47, %int %46
    %49 = load %int* %shift
    %50 = shl %int 1, %49
    %51 = load %code* %48
    %52 = bitcast %code %51 to %int
    %53 = icmp sge %int %52, %50
    br %boolean %53, label %_label28, label %_label30
_label30:
    br label %_label27
_label26:
    br label %_label27
_label27:
    br label %_label24
_label24:
    %54 = load %int* %i
    %55 = add %int %54, 1
    store %int %55, %int* %i
    br label %_label20
_label20:
    %56 = load { %code*, [1 x %int] }* %data
    %57 = extractvalue { %code*, [1 x %int] } %56, 1, 0
    %58 = load %int* %i
    %59 = icmp slt %int %58, %57
    br %boolean %59, label %_label19, label %_label21
_label21:
    %60 = load %int* %length
    %61 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %60)
    %62 = bitcast %"_Pshadow_Pstandard_CObject"* %61 to %byte*
    %63 = insertvalue { %byte*, [1 x %int] } undef, %byte* %62, 0
    %64 = insertvalue { %byte*, [1 x %int] } %63, %int %60, 1, 0
    %65 = load %"_Pshadow_Pstandard_CString"** %this
    %66 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %65, i32 0, i32 1
    store { %byte*, [1 x %int] } %64, { %byte*, [1 x %int] }* %66
    store %int 0, %int* %i1
    store %int 0, %int* %j
    br label %_label32
_label31:
    %67 = load { %code*, [1 x %int] }* %data
    %68 = load %int* %i1
    %69 = extractvalue { %code*, [1 x %int] } %67, 0
    %70 = getelementptr inbounds %code* %69, %int %68
    %71 = shl %int 1, 7
    %72 = load %code* %70
    %73 = bitcast %code %72 to %int
    %74 = icmp slt %int %73, %71
    br %boolean %74, label %_label34, label %_label35
_label34:
    %75 = load { %code*, [1 x %int] }* %data
    %76 = load %int* %i1
    %77 = extractvalue { %code*, [1 x %int] } %75, 0
    %78 = getelementptr inbounds %code* %77, %int %76
    %79 = load %code* %78
    %80 = trunc %code %79 to %byte
    %81 = load %"_Pshadow_Pstandard_CString"** %this
    %82 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %81, i32 0, i32 1
    %83 = load %int* %j
    %84 = add %int %83, 0
    %85 = load { %byte*, [1 x %int] }* %82
    %86 = extractvalue { %byte*, [1 x %int] } %85, 0
    %87 = getelementptr inbounds %byte* %86, %int %84
    store %byte %80, %byte* %87
    %88 = load %int* %j
    %89 = add %int %88, 1
    store %int %89, %int* %j
    br label %_label36
_label35:
    %90 = load { %code*, [1 x %int] }* %data
    %91 = load %int* %i1
    %92 = extractvalue { %code*, [1 x %int] } %90, 0
    %93 = getelementptr inbounds %code* %92, %int %91
    %94 = shl %int 1, 11
    %95 = load %code* %93
    %96 = bitcast %code %95 to %int
    %97 = icmp slt %int %96, %94
    br %boolean %97, label %_label37, label %_label38
_label37:
    %98 = load { %code*, [1 x %int] }* %data
    %99 = load %int* %i1
    %100 = extractvalue { %code*, [1 x %int] } %98, 0
    %101 = getelementptr inbounds %code* %100, %int %99
    %102 = load %code* %101
    %103 = bitcast %code %102 to %int
    %104 = ashr %int %103, 6
    %105 = and %int %104, 31
    %106 = or %int %105, 192
    %107 = trunc %int %106 to %byte
    %108 = load %"_Pshadow_Pstandard_CString"** %this
    %109 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %108, i32 0, i32 1
    %110 = load %int* %j
    %111 = add %int %110, 0
    %112 = load { %byte*, [1 x %int] }* %109
    %113 = extractvalue { %byte*, [1 x %int] } %112, 0
    %114 = getelementptr inbounds %byte* %113, %int %111
    store %byte %107, %byte* %114
    %115 = load { %code*, [1 x %int] }* %data
    %116 = load %int* %i1
    %117 = extractvalue { %code*, [1 x %int] } %115, 0
    %118 = getelementptr inbounds %code* %117, %int %116
    %119 = load %code* %118
    %120 = bitcast %code %119 to %int
    %121 = ashr %int %120, 0
    %122 = and %int %121, 63
    %123 = or %int %122, 128
    %124 = trunc %int %123 to %byte
    %125 = load %"_Pshadow_Pstandard_CString"** %this
    %126 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %125, i32 0, i32 1
    %127 = load %int* %j
    %128 = add %int %127, 1
    %129 = load { %byte*, [1 x %int] }* %126
    %130 = extractvalue { %byte*, [1 x %int] } %129, 0
    %131 = getelementptr inbounds %byte* %130, %int %128
    store %byte %124, %byte* %131
    %132 = load %int* %j
    %133 = add %int %132, 2
    store %int %133, %int* %j
    br label %_label39
_label38:
    %134 = load { %code*, [1 x %int] }* %data
    %135 = load %int* %i1
    %136 = extractvalue { %code*, [1 x %int] } %134, 0
    %137 = getelementptr inbounds %code* %136, %int %135
    %138 = shl %int 1, 16
    %139 = load %code* %137
    %140 = bitcast %code %139 to %int
    %141 = icmp slt %int %140, %138
    br %boolean %141, label %_label40, label %_label41
_label40:
    %142 = load { %code*, [1 x %int] }* %data
    %143 = load %int* %i1
    %144 = extractvalue { %code*, [1 x %int] } %142, 0
    %145 = getelementptr inbounds %code* %144, %int %143
    %146 = load %code* %145
    %147 = bitcast %code %146 to %int
    %148 = ashr %int %147, 12
    %149 = and %int %148, 15
    %150 = or %int %149, 224
    %151 = trunc %int %150 to %byte
    %152 = load %"_Pshadow_Pstandard_CString"** %this
    %153 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %152, i32 0, i32 1
    %154 = load %int* %j
    %155 = add %int %154, 0
    %156 = load { %byte*, [1 x %int] }* %153
    %157 = extractvalue { %byte*, [1 x %int] } %156, 0
    %158 = getelementptr inbounds %byte* %157, %int %155
    store %byte %151, %byte* %158
    %159 = load { %code*, [1 x %int] }* %data
    %160 = load %int* %i1
    %161 = extractvalue { %code*, [1 x %int] } %159, 0
    %162 = getelementptr inbounds %code* %161, %int %160
    %163 = load %code* %162
    %164 = bitcast %code %163 to %int
    %165 = ashr %int %164, 6
    %166 = and %int %165, 63
    %167 = or %int %166, 128
    %168 = trunc %int %167 to %byte
    %169 = load %"_Pshadow_Pstandard_CString"** %this
    %170 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %169, i32 0, i32 1
    %171 = load %int* %j
    %172 = add %int %171, 1
    %173 = load { %byte*, [1 x %int] }* %170
    %174 = extractvalue { %byte*, [1 x %int] } %173, 0
    %175 = getelementptr inbounds %byte* %174, %int %172
    store %byte %168, %byte* %175
    %176 = load { %code*, [1 x %int] }* %data
    %177 = load %int* %i1
    %178 = extractvalue { %code*, [1 x %int] } %176, 0
    %179 = getelementptr inbounds %code* %178, %int %177
    %180 = load %code* %179
    %181 = bitcast %code %180 to %int
    %182 = ashr %int %181, 0
    %183 = and %int %182, 63
    %184 = or %int %183, 128
    %185 = trunc %int %184 to %byte
    %186 = load %"_Pshadow_Pstandard_CString"** %this
    %187 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %186, i32 0, i32 1
    %188 = load %int* %j
    %189 = add %int %188, 2
    %190 = load { %byte*, [1 x %int] }* %187
    %191 = extractvalue { %byte*, [1 x %int] } %190, 0
    %192 = getelementptr inbounds %byte* %191, %int %189
    store %byte %185, %byte* %192
    %193 = load %int* %j
    %194 = add %int %193, 3
    store %int %194, %int* %j
    br label %_label42
_label41:
    %195 = load { %code*, [1 x %int] }* %data
    %196 = load %int* %i1
    %197 = extractvalue { %code*, [1 x %int] } %195, 0
    %198 = getelementptr inbounds %code* %197, %int %196
    %199 = shl %int 1, 21
    %200 = load %code* %198
    %201 = bitcast %code %200 to %int
    %202 = icmp slt %int %201, %199
    br %boolean %202, label %_label43, label %_label44
_label43:
    %203 = load { %code*, [1 x %int] }* %data
    %204 = load %int* %i1
    %205 = extractvalue { %code*, [1 x %int] } %203, 0
    %206 = getelementptr inbounds %code* %205, %int %204
    %207 = load %code* %206
    %208 = bitcast %code %207 to %int
    %209 = ashr %int %208, 18
    %210 = and %int %209, 7
    %211 = or %int %210, 240
    %212 = trunc %int %211 to %byte
    %213 = load %"_Pshadow_Pstandard_CString"** %this
    %214 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %213, i32 0, i32 1
    %215 = load %int* %j
    %216 = add %int %215, 0
    %217 = load { %byte*, [1 x %int] }* %214
    %218 = extractvalue { %byte*, [1 x %int] } %217, 0
    %219 = getelementptr inbounds %byte* %218, %int %216
    store %byte %212, %byte* %219
    %220 = load { %code*, [1 x %int] }* %data
    %221 = load %int* %i1
    %222 = extractvalue { %code*, [1 x %int] } %220, 0
    %223 = getelementptr inbounds %code* %222, %int %221
    %224 = load %code* %223
    %225 = bitcast %code %224 to %int
    %226 = ashr %int %225, 12
    %227 = and %int %226, 63
    %228 = or %int %227, 128
    %229 = trunc %int %228 to %byte
    %230 = load %"_Pshadow_Pstandard_CString"** %this
    %231 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %230, i32 0, i32 1
    %232 = load %int* %j
    %233 = add %int %232, 1
    %234 = load { %byte*, [1 x %int] }* %231
    %235 = extractvalue { %byte*, [1 x %int] } %234, 0
    %236 = getelementptr inbounds %byte* %235, %int %233
    store %byte %229, %byte* %236
    %237 = load { %code*, [1 x %int] }* %data
    %238 = load %int* %i1
    %239 = extractvalue { %code*, [1 x %int] } %237, 0
    %240 = getelementptr inbounds %code* %239, %int %238
    %241 = load %code* %240
    %242 = bitcast %code %241 to %int
    %243 = ashr %int %242, 6
    %244 = and %int %243, 63
    %245 = or %int %244, 128
    %246 = trunc %int %245 to %byte
    %247 = load %"_Pshadow_Pstandard_CString"** %this
    %248 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %247, i32 0, i32 1
    %249 = load %int* %j
    %250 = add %int %249, 2
    %251 = load { %byte*, [1 x %int] }* %248
    %252 = extractvalue { %byte*, [1 x %int] } %251, 0
    %253 = getelementptr inbounds %byte* %252, %int %250
    store %byte %246, %byte* %253
    %254 = load { %code*, [1 x %int] }* %data
    %255 = load %int* %i1
    %256 = extractvalue { %code*, [1 x %int] } %254, 0
    %257 = getelementptr inbounds %code* %256, %int %255
    %258 = load %code* %257
    %259 = bitcast %code %258 to %int
    %260 = ashr %int %259, 0
    %261 = and %int %260, 63
    %262 = or %int %261, 128
    %263 = trunc %int %262 to %byte
    %264 = load %"_Pshadow_Pstandard_CString"** %this
    %265 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %264, i32 0, i32 1
    %266 = load %int* %j
    %267 = add %int %266, 3
    %268 = load { %byte*, [1 x %int] }* %265
    %269 = extractvalue { %byte*, [1 x %int] } %268, 0
    %270 = getelementptr inbounds %byte* %269, %int %267
    store %byte %263, %byte* %270
    %271 = load %int* %j
    %272 = add %int %271, 4
    store %int %272, %int* %j
    br label %_label45
_label44:
    %273 = load { %code*, [1 x %int] }* %data
    %274 = load %int* %i1
    %275 = extractvalue { %code*, [1 x %int] } %273, 0
    %276 = getelementptr inbounds %code* %275, %int %274
    %277 = shl %int 1, 26
    %278 = load %code* %276
    %279 = bitcast %code %278 to %int
    %280 = icmp slt %int %279, %277
    br %boolean %280, label %_label46, label %_label47
_label46:
    %281 = load { %code*, [1 x %int] }* %data
    %282 = load %int* %i1
    %283 = extractvalue { %code*, [1 x %int] } %281, 0
    %284 = getelementptr inbounds %code* %283, %int %282
    %285 = load %code* %284
    %286 = bitcast %code %285 to %int
    %287 = ashr %int %286, 24
    %288 = and %int %287, 3
    %289 = or %int %288, 248
    %290 = trunc %int %289 to %byte
    %291 = load %"_Pshadow_Pstandard_CString"** %this
    %292 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %291, i32 0, i32 1
    %293 = load %int* %j
    %294 = add %int %293, 0
    %295 = load { %byte*, [1 x %int] }* %292
    %296 = extractvalue { %byte*, [1 x %int] } %295, 0
    %297 = getelementptr inbounds %byte* %296, %int %294
    store %byte %290, %byte* %297
    %298 = load { %code*, [1 x %int] }* %data
    %299 = load %int* %i1
    %300 = extractvalue { %code*, [1 x %int] } %298, 0
    %301 = getelementptr inbounds %code* %300, %int %299
    %302 = load %code* %301
    %303 = bitcast %code %302 to %int
    %304 = ashr %int %303, 18
    %305 = and %int %304, 63
    %306 = or %int %305, 128
    %307 = trunc %int %306 to %byte
    %308 = load %"_Pshadow_Pstandard_CString"** %this
    %309 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %308, i32 0, i32 1
    %310 = load %int* %j
    %311 = add %int %310, 1
    %312 = load { %byte*, [1 x %int] }* %309
    %313 = extractvalue { %byte*, [1 x %int] } %312, 0
    %314 = getelementptr inbounds %byte* %313, %int %311
    store %byte %307, %byte* %314
    %315 = load { %code*, [1 x %int] }* %data
    %316 = load %int* %i1
    %317 = extractvalue { %code*, [1 x %int] } %315, 0
    %318 = getelementptr inbounds %code* %317, %int %316
    %319 = load %code* %318
    %320 = bitcast %code %319 to %int
    %321 = ashr %int %320, 12
    %322 = and %int %321, 63
    %323 = or %int %322, 128
    %324 = trunc %int %323 to %byte
    %325 = load %"_Pshadow_Pstandard_CString"** %this
    %326 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %325, i32 0, i32 1
    %327 = load %int* %j
    %328 = add %int %327, 2
    %329 = load { %byte*, [1 x %int] }* %326
    %330 = extractvalue { %byte*, [1 x %int] } %329, 0
    %331 = getelementptr inbounds %byte* %330, %int %328
    store %byte %324, %byte* %331
    %332 = load { %code*, [1 x %int] }* %data
    %333 = load %int* %i1
    %334 = extractvalue { %code*, [1 x %int] } %332, 0
    %335 = getelementptr inbounds %code* %334, %int %333
    %336 = load %code* %335
    %337 = bitcast %code %336 to %int
    %338 = ashr %int %337, 6
    %339 = and %int %338, 63
    %340 = or %int %339, 128
    %341 = trunc %int %340 to %byte
    %342 = load %"_Pshadow_Pstandard_CString"** %this
    %343 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %342, i32 0, i32 1
    %344 = load %int* %j
    %345 = add %int %344, 3
    %346 = load { %byte*, [1 x %int] }* %343
    %347 = extractvalue { %byte*, [1 x %int] } %346, 0
    %348 = getelementptr inbounds %byte* %347, %int %345
    store %byte %341, %byte* %348
    %349 = load { %code*, [1 x %int] }* %data
    %350 = load %int* %i1
    %351 = extractvalue { %code*, [1 x %int] } %349, 0
    %352 = getelementptr inbounds %code* %351, %int %350
    %353 = load %code* %352
    %354 = bitcast %code %353 to %int
    %355 = ashr %int %354, 0
    %356 = and %int %355, 63
    %357 = or %int %356, 128
    %358 = trunc %int %357 to %byte
    %359 = load %"_Pshadow_Pstandard_CString"** %this
    %360 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %359, i32 0, i32 1
    %361 = load %int* %j
    %362 = add %int %361, 4
    %363 = load { %byte*, [1 x %int] }* %360
    %364 = extractvalue { %byte*, [1 x %int] } %363, 0
    %365 = getelementptr inbounds %byte* %364, %int %362
    store %byte %358, %byte* %365
    %366 = load %int* %j
    %367 = add %int %366, 5
    store %int %367, %int* %j
    br label %_label48
_label47:
    %368 = load { %code*, [1 x %int] }* %data
    %369 = load %int* %i1
    %370 = extractvalue { %code*, [1 x %int] } %368, 0
    %371 = getelementptr inbounds %code* %370, %int %369
    %372 = shl %int 1, 31
    %373 = load %code* %371
    %374 = bitcast %code %373 to %int
    %375 = icmp slt %int %374, %372
    br %boolean %375, label %_label49, label %_label50
_label49:
    %376 = load { %code*, [1 x %int] }* %data
    %377 = load %int* %i1
    %378 = extractvalue { %code*, [1 x %int] } %376, 0
    %379 = getelementptr inbounds %code* %378, %int %377
    %380 = load %code* %379
    %381 = bitcast %code %380 to %int
    %382 = ashr %int %381, 30
    %383 = and %int %382, 1
    %384 = or %int %383, 252
    %385 = trunc %int %384 to %byte
    %386 = load %"_Pshadow_Pstandard_CString"** %this
    %387 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %386, i32 0, i32 1
    %388 = load %int* %j
    %389 = add %int %388, 0
    %390 = load { %byte*, [1 x %int] }* %387
    %391 = extractvalue { %byte*, [1 x %int] } %390, 0
    %392 = getelementptr inbounds %byte* %391, %int %389
    store %byte %385, %byte* %392
    %393 = load { %code*, [1 x %int] }* %data
    %394 = load %int* %i1
    %395 = extractvalue { %code*, [1 x %int] } %393, 0
    %396 = getelementptr inbounds %code* %395, %int %394
    %397 = load %code* %396
    %398 = bitcast %code %397 to %int
    %399 = ashr %int %398, 24
    %400 = and %int %399, 63
    %401 = or %int %400, 128
    %402 = trunc %int %401 to %byte
    %403 = load %"_Pshadow_Pstandard_CString"** %this
    %404 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %403, i32 0, i32 1
    %405 = load %int* %j
    %406 = add %int %405, 1
    %407 = load { %byte*, [1 x %int] }* %404
    %408 = extractvalue { %byte*, [1 x %int] } %407, 0
    %409 = getelementptr inbounds %byte* %408, %int %406
    store %byte %402, %byte* %409
    %410 = load { %code*, [1 x %int] }* %data
    %411 = load %int* %i1
    %412 = extractvalue { %code*, [1 x %int] } %410, 0
    %413 = getelementptr inbounds %code* %412, %int %411
    %414 = load %code* %413
    %415 = bitcast %code %414 to %int
    %416 = ashr %int %415, 18
    %417 = and %int %416, 63
    %418 = or %int %417, 128
    %419 = trunc %int %418 to %byte
    %420 = load %"_Pshadow_Pstandard_CString"** %this
    %421 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %420, i32 0, i32 1
    %422 = load %int* %j
    %423 = add %int %422, 2
    %424 = load { %byte*, [1 x %int] }* %421
    %425 = extractvalue { %byte*, [1 x %int] } %424, 0
    %426 = getelementptr inbounds %byte* %425, %int %423
    store %byte %419, %byte* %426
    %427 = load { %code*, [1 x %int] }* %data
    %428 = load %int* %i1
    %429 = extractvalue { %code*, [1 x %int] } %427, 0
    %430 = getelementptr inbounds %code* %429, %int %428
    %431 = load %code* %430
    %432 = bitcast %code %431 to %int
    %433 = ashr %int %432, 12
    %434 = and %int %433, 63
    %435 = or %int %434, 128
    %436 = trunc %int %435 to %byte
    %437 = load %"_Pshadow_Pstandard_CString"** %this
    %438 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %437, i32 0, i32 1
    %439 = load %int* %j
    %440 = add %int %439, 3
    %441 = load { %byte*, [1 x %int] }* %438
    %442 = extractvalue { %byte*, [1 x %int] } %441, 0
    %443 = getelementptr inbounds %byte* %442, %int %440
    store %byte %436, %byte* %443
    %444 = load { %code*, [1 x %int] }* %data
    %445 = load %int* %i1
    %446 = extractvalue { %code*, [1 x %int] } %444, 0
    %447 = getelementptr inbounds %code* %446, %int %445
    %448 = load %code* %447
    %449 = bitcast %code %448 to %int
    %450 = ashr %int %449, 6
    %451 = and %int %450, 63
    %452 = or %int %451, 128
    %453 = trunc %int %452 to %byte
    %454 = load %"_Pshadow_Pstandard_CString"** %this
    %455 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %454, i32 0, i32 1
    %456 = load %int* %j
    %457 = add %int %456, 4
    %458 = load { %byte*, [1 x %int] }* %455
    %459 = extractvalue { %byte*, [1 x %int] } %458, 0
    %460 = getelementptr inbounds %byte* %459, %int %457
    store %byte %453, %byte* %460
    %461 = load { %code*, [1 x %int] }* %data
    %462 = load %int* %i1
    %463 = extractvalue { %code*, [1 x %int] } %461, 0
    %464 = getelementptr inbounds %code* %463, %int %462
    %465 = load %code* %464
    %466 = bitcast %code %465 to %int
    %467 = ashr %int %466, 0
    %468 = and %int %467, 63
    %469 = or %int %468, 128
    %470 = trunc %int %469 to %byte
    %471 = load %"_Pshadow_Pstandard_CString"** %this
    %472 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %471, i32 0, i32 1
    %473 = load %int* %j
    %474 = add %int %473, 5
    %475 = load { %byte*, [1 x %int] }* %472
    %476 = extractvalue { %byte*, [1 x %int] } %475, 0
    %477 = getelementptr inbounds %byte* %476, %int %474
    store %byte %470, %byte* %477
    %478 = load %int* %j
    %479 = add %int %478, 6
    store %int %479, %int* %j
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
    %480 = load %int* %i1
    %481 = add %int %480, 1
    store %int %481, %int* %i1
    br label %_label32
_label32:
    %482 = load { %code*, [1 x %int] }* %data
    %483 = extractvalue { %code*, [1 x %int] } %482, 1, 0
    %484 = load %int* %i1
    %485 = icmp slt %int %484, %483
    br %boolean %485, label %_label31, label %_label33
_label33:
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = bitcast %"_Pshadow_Pstandard_CString"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %4)
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %6
    %7 = load %"_Pshadow_Pstandard_CString"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %7, i32 0, i32 2
    store %boolean true, %boolean* %8
    %9 = load %"_Pshadow_Pstandard_CString"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %9, i32 0, i32 1
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %byte*
    %13 = insertvalue { %byte*, [1 x %int] } undef, %byte* %12, 0
    %14 = insertvalue { %byte*, [1 x %int] } %13, %int 0, 1, 0
    store { %byte*, [1 x %int] } %14, { %byte*, [1 x %int] }* %10
    %15 = load %"_Pshadow_Pstandard_CString"** %other
    %16 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %15, i32 0, i32 1
    %17 = load %"_Pshadow_Pstandard_CString"** %this
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %17, i32 0, i32 1
    %19 = load { %byte*, [1 x %int] }* %16
    store { %byte*, [1 x %int] } %19, { %byte*, [1 x %int] }* %18
    %20 = load %"_Pshadow_Pstandard_CString"** %other
    %21 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %20, i32 0, i32 2
    %22 = load %"_Pshadow_Pstandard_CString"** %this
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %22, i32 0, i32 2
    %24 = load %boolean* %21
    store %boolean %24, %boolean* %23
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] }, %boolean) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %byte*, [1 x %int] }
    %ascii = alloca %boolean
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store { %byte*, [1 x %int] } %1, { %byte*, [1 x %int] }* %data
    store %boolean %2, %boolean* %ascii
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = bitcast %"_Pshadow_Pstandard_CString"* %4 to %"_Pshadow_Pstandard_CObject"*
    %6 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %5)
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %0, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %7
    %8 = load %"_Pshadow_Pstandard_CString"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %8, i32 0, i32 2
    store %boolean true, %boolean* %9
    %10 = load %"_Pshadow_Pstandard_CString"** %this
    %11 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %10, i32 0, i32 1
    %12 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int 0)
    %13 = bitcast %"_Pshadow_Pstandard_CObject"* %12 to %byte*
    %14 = insertvalue { %byte*, [1 x %int] } undef, %byte* %13, 0
    %15 = insertvalue { %byte*, [1 x %int] } %14, %int 0, 1, 0
    store { %byte*, [1 x %int] } %15, { %byte*, [1 x %int] }* %11
    %16 = load %"_Pshadow_Pstandard_CString"** %this
    %17 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %16, i32 0, i32 1
    %18 = load { %byte*, [1 x %int] }* %data
    store { %byte*, [1 x %int] } %18, { %byte*, [1 x %int] }* %17
    %19 = load %"_Pshadow_Pstandard_CString"** %this
    %20 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %19, i32 0, i32 2
    %21 = load %boolean* %ascii
    store %boolean %21, %boolean* %20
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
    %5 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %3)
    %6 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %4)
    %7 = add %int %5, %6
    %8 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %7)
    %9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %byte*
    %10 = insertvalue { %byte*, [1 x %int] } undef, %byte* %9, 0
    %11 = insertvalue { %byte*, [1 x %int] } %10, %int %7, 1, 0
    store { %byte*, [1 x %int] } %11, { %byte*, [1 x %int] }* %data
    store %int 0, %int* %i
    br label %_label53
_label52:
    %12 = load %"_Pshadow_Pstandard_CString"** %this
    %13 = load %"_Pshadow_Pstandard_CString"** %this
    %14 = load %int* %i
    %15 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %13, %int %14)
    %16 = load { %byte*, [1 x %int] }* %data
    %17 = load %int* %i
    %18 = extractvalue { %byte*, [1 x %int] } %16, 0
    %19 = getelementptr inbounds %byte* %18, %int %17
    store %byte %15, %byte* %19
    %20 = load %int* %i
    %21 = add %int %20, 1
    store %int %21, %int* %i
    br label %_label53
_label53:
    %22 = load %"_Pshadow_Pstandard_CString"** %this
    %23 = load %int* %i
    %24 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %22)
    %25 = icmp slt %int %23, %24
    br %boolean %25, label %_label52, label %_label54
_label54:
    store %int 0, %int* %i1
    br label %_label56
_label55:
    %26 = load %"_Pshadow_Pstandard_CString"** %other
    %27 = load %"_Pshadow_Pstandard_CString"** %other
    %28 = load %int* %i1
    %29 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %27, %int %28)
    %30 = load %"_Pshadow_Pstandard_CString"** %this
    %31 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %30)
    %32 = load %int* %i1
    %33 = add %int %31, %32
    %34 = load { %byte*, [1 x %int] }* %data
    %35 = extractvalue { %byte*, [1 x %int] } %34, 0
    %36 = getelementptr inbounds %byte* %35, %int %33
    store %byte %29, %byte* %36
    %37 = load %int* %i1
    %38 = add %int %37, 1
    store %int %38, %int* %i1
    br label %_label56
_label56:
    %39 = load %"_Pshadow_Pstandard_CString"** %other
    %40 = load %int* %i1
    %41 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %39)
    %42 = icmp slt %int %40, %41
    br %boolean %42, label %_label55, label %_label57
_label57:
    %43 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0))
    %44 = bitcast %"_Pshadow_Pstandard_CObject"* %43 to %"_Pshadow_Pstandard_CString"*
    %45 = load %"_Pshadow_Pstandard_CString"** %this
    %46 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %45, i32 0, i32 2
    %47 = load %boolean* %46
    store %boolean %47, %boolean* %_temp
    %48 = load %boolean* %46
    br %boolean %48, label %_label59, label %_label58
_label59:
    %49 = load %"_Pshadow_Pstandard_CString"** %other
    %50 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %49, i32 0, i32 2
    %51 = load %boolean* %50
    store %boolean %51, %boolean* %_temp
    br label %_label58
_label58:
    %52 = load %boolean* %_temp
    %53 = load { %byte*, [1 x %int] }* %data
    %54 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pstandard_CString"* %44, { %byte*, [1 x %int] } %53, %boolean %52)
    ret %"_Pshadow_Pstandard_CString"* %44
}

declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CException_Mmessage"(%"_Pshadow_Pstandard_CException"*)
declare void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate"(%"_Pshadow_Pstandard_CException"*)
declare %"_Pshadow_Pstandard_CException"* @"_Pshadow_Pstandard_CException_Mcreate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)

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
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([22 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 22] }, %boolean true }
