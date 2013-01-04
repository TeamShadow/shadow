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
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
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
%"_Pshadow_Pstandard_CMutableString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %int)*, %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*)*, %int (%"_Pshadow_Pstandard_CMutableString"*)* }
%"_Pshadow_Pstandard_CMutableString" = type { %"_Pshadow_Pstandard_CMutableString_Mclass"* }

@"_Pshadow_Pstandard_CString_Mclass" = constant %"_Pshadow_Pstandard_CString_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CString"* getelementptr (%"_Pshadow_Pstandard_CString"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CString"** getelementptr (%"_Pshadow_Pstandard_CString"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoString", { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mchars", %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString", %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString", %byte (%"_Pshadow_Pstandard_CString"*, %int)* @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint", %boolean (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MisEmpty", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Miterator", %int (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Msize", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoLowerCase", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoUpperCase" }
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
    %3 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %2)
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
    %5 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %3)
    %6 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %4)
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
    %28 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %26)
    %29 = icmp slt %int %27, %28
    store %boolean %29, %boolean* %_temp
    br %boolean %29, label %_label15, label %_label14
_label15:
    %30 = load %"_Pshadow_Pstandard_CString"** %other
    %31 = load %int* %i
    %32 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %30)
    %33 = icmp slt %int %31, %32
    store %boolean %33, %boolean* %_temp
    br label %_label14
_label14:
    %34 = load %boolean* %_temp
    br %boolean %34, label %_label8, label %_label10
_label10:
    %35 = load %"_Pshadow_Pstandard_CString"** %this
    %36 = load %"_Pshadow_Pstandard_CString"** %other
    %37 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %35)
    %38 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %36)
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
    %16 = extractvalue { %byte*, [1 x %int] } %15, 1
    %17 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %18 = bitcast %"_Pshadow_Pstandard_CObject"* %17 to [1 x %int]*
    store [1 x %int] %16, [1 x %int]* %18
    %19 = getelementptr inbounds [1 x %int]* %18, i32 0, i32 0
    %20 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %19, 0
    %21 = extractvalue { %byte*, [1 x %int] } %15, 0
    %22 = bitcast %byte* %21 to %"_Pshadow_Pstandard_CObject"*
    %23 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %24 = bitcast %"_Pshadow_Pstandard_CObject"* %23 to %"_Pshadow_Pstandard_CArray"*
    %25 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %24, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %20, %"_Pshadow_Pstandard_CObject"* %22)
    %26 = getelementptr %"_Pshadow_Pstandard_CArray"* %25, i32 0, i32 0
    %27 = load %"_Pshadow_Pstandard_CArray_Mclass"** %26
    %28 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %27, i32 0, i32 13
    %29 = load %int (%"_Pshadow_Pstandard_CArray"*)** %28
    %30 = extractvalue { %byte*, [1 x %int] } %15, 1
    %31 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to [1 x %int]*
    store [1 x %int] %30, [1 x %int]* %32
    %33 = getelementptr inbounds [1 x %int]* %32, i32 0, i32 0
    %34 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %33, 0
    %35 = extractvalue { %byte*, [1 x %int] } %15, 0
    %36 = bitcast %byte* %35 to %"_Pshadow_Pstandard_CObject"*
    %37 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to %"_Pshadow_Pstandard_CArray"*
    %39 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %38, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %34, %"_Pshadow_Pstandard_CObject"* %36)
    %40 = call %int %29(%"_Pshadow_Pstandard_CArray"* %39)
    %41 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %40)
    %42 = bitcast %"_Pshadow_Pstandard_CObject"* %41 to %byte*
    %43 = insertvalue { %byte*, [1 x %int] } undef, %byte* %42, 0
    %44 = insertvalue { %byte*, [1 x %int] } %43, %int %40, 1, 0
    %45 = load %"_Pshadow_Pstandard_CString"** %this
    %46 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %45, i32 0, i32 1
    store { %byte*, [1 x %int] } %44, { %byte*, [1 x %int] }* %46
    store %int 0, %int* %i
    br label %_label17
_label16:
    %47 = load { %byte*, [1 x %int] }* %data
    %48 = load %int* %i
    %49 = extractvalue { %byte*, [1 x %int] } %47, 0
    %50 = getelementptr inbounds %byte* %49, %int %48
    %51 = load %"_Pshadow_Pstandard_CString"** %this
    %52 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %51, i32 0, i32 1
    %53 = load { %byte*, [1 x %int] }* %52
    %54 = load %int* %i
    %55 = extractvalue { %byte*, [1 x %int] } %53, 0
    %56 = getelementptr inbounds %byte* %55, %int %54
    %57 = load %byte* %50
    store %byte %57, %byte* %56
    %58 = load { %byte*, [1 x %int] }* %data
    %59 = load %int* %i
    %60 = extractvalue { %byte*, [1 x %int] } %58, 0
    %61 = getelementptr inbounds %byte* %60, %int %59
    %62 = load %byte* %ascii
    %63 = load %byte* %61
    %64 = or %byte %62, %63
    store %byte %64, %byte* %ascii
    %65 = load %int* %i
    %66 = add %int %65, 1
    store %int %66, %int* %i
    br label %_label17
_label17:
    %67 = load { %byte*, [1 x %int] }* %data
    %68 = load %int* %i
    %69 = extractvalue { %byte*, [1 x %int] } %67, 1
    %70 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %71 = bitcast %"_Pshadow_Pstandard_CObject"* %70 to [1 x %int]*
    store [1 x %int] %69, [1 x %int]* %71
    %72 = getelementptr inbounds [1 x %int]* %71, i32 0, i32 0
    %73 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %72, 0
    %74 = extractvalue { %byte*, [1 x %int] } %67, 0
    %75 = bitcast %byte* %74 to %"_Pshadow_Pstandard_CObject"*
    %76 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %77 = bitcast %"_Pshadow_Pstandard_CObject"* %76 to %"_Pshadow_Pstandard_CArray"*
    %78 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %77, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %73, %"_Pshadow_Pstandard_CObject"* %75)
    %79 = getelementptr %"_Pshadow_Pstandard_CArray"* %78, i32 0, i32 0
    %80 = load %"_Pshadow_Pstandard_CArray_Mclass"** %79
    %81 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %80, i32 0, i32 13
    %82 = load %int (%"_Pshadow_Pstandard_CArray"*)** %81
    %83 = extractvalue { %byte*, [1 x %int] } %67, 1
    %84 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %85 = bitcast %"_Pshadow_Pstandard_CObject"* %84 to [1 x %int]*
    store [1 x %int] %83, [1 x %int]* %85
    %86 = getelementptr inbounds [1 x %int]* %85, i32 0, i32 0
    %87 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %86, 0
    %88 = extractvalue { %byte*, [1 x %int] } %67, 0
    %89 = bitcast %byte* %88 to %"_Pshadow_Pstandard_CObject"*
    %90 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %91 = bitcast %"_Pshadow_Pstandard_CObject"* %90 to %"_Pshadow_Pstandard_CArray"*
    %92 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %91, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %87, %"_Pshadow_Pstandard_CObject"* %89)
    %93 = call %int %82(%"_Pshadow_Pstandard_CArray"* %92)
    %94 = icmp slt %int %68, %93
    br %boolean %94, label %_label16, label %_label18
_label18:
    %95 = load %byte* %ascii
    %96 = sext %byte %95 to %int
    %97 = icmp slt %int %96, 0
    %98 = load %"_Pshadow_Pstandard_CString"** %this
    %99 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %98, i32 0, i32 2
    store %boolean %97, %boolean* %99
    ret %"_Pshadow_Pstandard_CString"* %0
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1"(%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %data = alloca { %code*, [1 x %int] }
    %size = alloca %int
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
    %16 = extractvalue { %code*, [1 x %int] } %15, 1
    %17 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %18 = bitcast %"_Pshadow_Pstandard_CObject"* %17 to [1 x %int]*
    store [1 x %int] %16, [1 x %int]* %18
    %19 = getelementptr inbounds [1 x %int]* %18, i32 0, i32 0
    %20 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %19, 0
    %21 = extractvalue { %code*, [1 x %int] } %15, 0
    %22 = bitcast %code* %21 to %"_Pshadow_Pstandard_CObject"*
    %23 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %24 = bitcast %"_Pshadow_Pstandard_CObject"* %23 to %"_Pshadow_Pstandard_CArray"*
    %25 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %24, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %20, %"_Pshadow_Pstandard_CObject"* %22)
    %26 = getelementptr %"_Pshadow_Pstandard_CArray"* %25, i32 0, i32 0
    %27 = load %"_Pshadow_Pstandard_CArray_Mclass"** %26
    %28 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %27, i32 0, i32 13
    %29 = load %int (%"_Pshadow_Pstandard_CArray"*)** %28
    %30 = extractvalue { %code*, [1 x %int] } %15, 1
    %31 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %32 = bitcast %"_Pshadow_Pstandard_CObject"* %31 to [1 x %int]*
    store [1 x %int] %30, [1 x %int]* %32
    %33 = getelementptr inbounds [1 x %int]* %32, i32 0, i32 0
    %34 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %33, 0
    %35 = extractvalue { %code*, [1 x %int] } %15, 0
    %36 = bitcast %code* %35 to %"_Pshadow_Pstandard_CObject"*
    %37 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to %"_Pshadow_Pstandard_CArray"*
    %39 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %38, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %34, %"_Pshadow_Pstandard_CObject"* %36)
    %40 = call %int %29(%"_Pshadow_Pstandard_CArray"* %39)
    store %int %40, %int* %size
    store %int 0, %int* %i
    br label %_label20
_label19:
    %41 = load { %code*, [1 x %int] }* %data
    %42 = load %int* %i
    %43 = extractvalue { %code*, [1 x %int] } %41, 0
    %44 = getelementptr inbounds %code* %43, %int %42
    %45 = load %code* %44
    %46 = bitcast %code %45 to %int
    %47 = icmp slt %int %46, 0
    br %boolean %47, label %_label22, label %_label23
_label22:
    %48 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mclass", i32 0, i32 0))
    %49 = bitcast %"_Pshadow_Pstandard_CObject"* %48 to %"_Pshadow_Pstandard_CIllegalArgumentException"*
    %50 = call %"_Pshadow_Pstandard_CIllegalArgumentException"* @"_Pshadow_Pstandard_CIllegalArgumentException_Mcreate"(%"_Pshadow_Pstandard_CIllegalArgumentException"* %49)
    %51 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %49 to %"_Pshadow_Pstandard_CException"*
    %52 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %49 to %"_Pshadow_Pstandard_CException"*
    call void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"* %52)
    br label %_label24
_label23:
    %53 = load { %code*, [1 x %int] }* %data
    %54 = load %int* %i
    %55 = extractvalue { %code*, [1 x %int] } %53, 0
    %56 = getelementptr inbounds %code* %55, %int %54
    %57 = shl %int 1, 7
    %58 = load %code* %56
    %59 = bitcast %code %58 to %int
    %60 = icmp sge %int %59, %57
    br %boolean %60, label %_label25, label %_label26
_label25:
    %61 = load %"_Pshadow_Pstandard_CString"** %this
    %62 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %61, i32 0, i32 2
    store %boolean false, %boolean* %62
    %63 = load %int* %size
    %64 = add %int %63, 1
    store %int %64, %int* %size
    store %int 11, %int* %shift
    br label %_label29
_label28:
    %65 = load %int* %size
    %66 = add %int %65, 1
    store %int %66, %int* %size
    %67 = load %int* %shift
    %68 = add %int %67, 5
    store %int %68, %int* %shift
    br label %_label29
_label29:
    %69 = load { %code*, [1 x %int] }* %data
    %70 = load %int* %i
    %71 = extractvalue { %code*, [1 x %int] } %69, 0
    %72 = getelementptr inbounds %code* %71, %int %70
    %73 = load %int* %shift
    %74 = shl %int 1, %73
    %75 = load %code* %72
    %76 = bitcast %code %75 to %int
    %77 = icmp sge %int %76, %74
    br %boolean %77, label %_label28, label %_label30
_label30:
    br label %_label27
_label26:
    br label %_label27
_label27:
    br label %_label24
_label24:
    %78 = load %int* %i
    %79 = add %int %78, 1
    store %int %79, %int* %i
    br label %_label20
_label20:
    %80 = load { %code*, [1 x %int] }* %data
    %81 = load %int* %i
    %82 = extractvalue { %code*, [1 x %int] } %80, 1
    %83 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %84 = bitcast %"_Pshadow_Pstandard_CObject"* %83 to [1 x %int]*
    store [1 x %int] %82, [1 x %int]* %84
    %85 = getelementptr inbounds [1 x %int]* %84, i32 0, i32 0
    %86 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %85, 0
    %87 = extractvalue { %code*, [1 x %int] } %80, 0
    %88 = bitcast %code* %87 to %"_Pshadow_Pstandard_CObject"*
    %89 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %90 = bitcast %"_Pshadow_Pstandard_CObject"* %89 to %"_Pshadow_Pstandard_CArray"*
    %91 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %90, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %86, %"_Pshadow_Pstandard_CObject"* %88)
    %92 = getelementptr %"_Pshadow_Pstandard_CArray"* %91, i32 0, i32 0
    %93 = load %"_Pshadow_Pstandard_CArray_Mclass"** %92
    %94 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %93, i32 0, i32 13
    %95 = load %int (%"_Pshadow_Pstandard_CArray"*)** %94
    %96 = extractvalue { %code*, [1 x %int] } %80, 1
    %97 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %98 = bitcast %"_Pshadow_Pstandard_CObject"* %97 to [1 x %int]*
    store [1 x %int] %96, [1 x %int]* %98
    %99 = getelementptr inbounds [1 x %int]* %98, i32 0, i32 0
    %100 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %99, 0
    %101 = extractvalue { %code*, [1 x %int] } %80, 0
    %102 = bitcast %code* %101 to %"_Pshadow_Pstandard_CObject"*
    %103 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %104 = bitcast %"_Pshadow_Pstandard_CObject"* %103 to %"_Pshadow_Pstandard_CArray"*
    %105 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %104, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %100, %"_Pshadow_Pstandard_CObject"* %102)
    %106 = call %int %95(%"_Pshadow_Pstandard_CArray"* %105)
    %107 = icmp slt %int %81, %106
    br %boolean %107, label %_label19, label %_label21
_label21:
    %108 = load %int* %size
    %109 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %108)
    %110 = bitcast %"_Pshadow_Pstandard_CObject"* %109 to %byte*
    %111 = insertvalue { %byte*, [1 x %int] } undef, %byte* %110, 0
    %112 = insertvalue { %byte*, [1 x %int] } %111, %int %108, 1, 0
    %113 = load %"_Pshadow_Pstandard_CString"** %this
    %114 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %113, i32 0, i32 1
    store { %byte*, [1 x %int] } %112, { %byte*, [1 x %int] }* %114
    store %int 0, %int* %i1
    store %int 0, %int* %j
    br label %_label32
_label31:
    %115 = load { %code*, [1 x %int] }* %data
    %116 = load %int* %i1
    %117 = extractvalue { %code*, [1 x %int] } %115, 0
    %118 = getelementptr inbounds %code* %117, %int %116
    %119 = shl %int 1, 7
    %120 = load %code* %118
    %121 = bitcast %code %120 to %int
    %122 = icmp slt %int %121, %119
    br %boolean %122, label %_label34, label %_label35
_label34:
    %123 = load { %code*, [1 x %int] }* %data
    %124 = load %int* %i1
    %125 = extractvalue { %code*, [1 x %int] } %123, 0
    %126 = getelementptr inbounds %code* %125, %int %124
    %127 = load %code* %126
    %128 = trunc %code %127 to %byte
    %129 = load %"_Pshadow_Pstandard_CString"** %this
    %130 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %129, i32 0, i32 1
    %131 = load %int* %j
    %132 = add %int %131, 0
    %133 = load { %byte*, [1 x %int] }* %130
    %134 = extractvalue { %byte*, [1 x %int] } %133, 0
    %135 = getelementptr inbounds %byte* %134, %int %132
    store %byte %128, %byte* %135
    %136 = load %int* %j
    %137 = add %int %136, 1
    store %int %137, %int* %j
    br label %_label36
_label35:
    %138 = load { %code*, [1 x %int] }* %data
    %139 = load %int* %i1
    %140 = extractvalue { %code*, [1 x %int] } %138, 0
    %141 = getelementptr inbounds %code* %140, %int %139
    %142 = shl %int 1, 11
    %143 = load %code* %141
    %144 = bitcast %code %143 to %int
    %145 = icmp slt %int %144, %142
    br %boolean %145, label %_label37, label %_label38
_label37:
    %146 = load { %code*, [1 x %int] }* %data
    %147 = load %int* %i1
    %148 = extractvalue { %code*, [1 x %int] } %146, 0
    %149 = getelementptr inbounds %code* %148, %int %147
    %150 = load %code* %149
    %151 = bitcast %code %150 to %int
    %152 = ashr %int %151, 6
    %153 = and %int %152, 31
    %154 = or %int %153, 192
    %155 = trunc %int %154 to %byte
    %156 = load %"_Pshadow_Pstandard_CString"** %this
    %157 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %156, i32 0, i32 1
    %158 = load %int* %j
    %159 = add %int %158, 0
    %160 = load { %byte*, [1 x %int] }* %157
    %161 = extractvalue { %byte*, [1 x %int] } %160, 0
    %162 = getelementptr inbounds %byte* %161, %int %159
    store %byte %155, %byte* %162
    %163 = load { %code*, [1 x %int] }* %data
    %164 = load %int* %i1
    %165 = extractvalue { %code*, [1 x %int] } %163, 0
    %166 = getelementptr inbounds %code* %165, %int %164
    %167 = load %code* %166
    %168 = bitcast %code %167 to %int
    %169 = ashr %int %168, 0
    %170 = and %int %169, 63
    %171 = or %int %170, 128
    %172 = trunc %int %171 to %byte
    %173 = load %"_Pshadow_Pstandard_CString"** %this
    %174 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %173, i32 0, i32 1
    %175 = load %int* %j
    %176 = add %int %175, 1
    %177 = load { %byte*, [1 x %int] }* %174
    %178 = extractvalue { %byte*, [1 x %int] } %177, 0
    %179 = getelementptr inbounds %byte* %178, %int %176
    store %byte %172, %byte* %179
    %180 = load %int* %j
    %181 = add %int %180, 2
    store %int %181, %int* %j
    br label %_label39
_label38:
    %182 = load { %code*, [1 x %int] }* %data
    %183 = load %int* %i1
    %184 = extractvalue { %code*, [1 x %int] } %182, 0
    %185 = getelementptr inbounds %code* %184, %int %183
    %186 = shl %int 1, 16
    %187 = load %code* %185
    %188 = bitcast %code %187 to %int
    %189 = icmp slt %int %188, %186
    br %boolean %189, label %_label40, label %_label41
_label40:
    %190 = load { %code*, [1 x %int] }* %data
    %191 = load %int* %i1
    %192 = extractvalue { %code*, [1 x %int] } %190, 0
    %193 = getelementptr inbounds %code* %192, %int %191
    %194 = load %code* %193
    %195 = bitcast %code %194 to %int
    %196 = ashr %int %195, 12
    %197 = and %int %196, 15
    %198 = or %int %197, 224
    %199 = trunc %int %198 to %byte
    %200 = load %"_Pshadow_Pstandard_CString"** %this
    %201 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %200, i32 0, i32 1
    %202 = load %int* %j
    %203 = add %int %202, 0
    %204 = load { %byte*, [1 x %int] }* %201
    %205 = extractvalue { %byte*, [1 x %int] } %204, 0
    %206 = getelementptr inbounds %byte* %205, %int %203
    store %byte %199, %byte* %206
    %207 = load { %code*, [1 x %int] }* %data
    %208 = load %int* %i1
    %209 = extractvalue { %code*, [1 x %int] } %207, 0
    %210 = getelementptr inbounds %code* %209, %int %208
    %211 = load %code* %210
    %212 = bitcast %code %211 to %int
    %213 = ashr %int %212, 6
    %214 = and %int %213, 63
    %215 = or %int %214, 128
    %216 = trunc %int %215 to %byte
    %217 = load %"_Pshadow_Pstandard_CString"** %this
    %218 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %217, i32 0, i32 1
    %219 = load %int* %j
    %220 = add %int %219, 1
    %221 = load { %byte*, [1 x %int] }* %218
    %222 = extractvalue { %byte*, [1 x %int] } %221, 0
    %223 = getelementptr inbounds %byte* %222, %int %220
    store %byte %216, %byte* %223
    %224 = load { %code*, [1 x %int] }* %data
    %225 = load %int* %i1
    %226 = extractvalue { %code*, [1 x %int] } %224, 0
    %227 = getelementptr inbounds %code* %226, %int %225
    %228 = load %code* %227
    %229 = bitcast %code %228 to %int
    %230 = ashr %int %229, 0
    %231 = and %int %230, 63
    %232 = or %int %231, 128
    %233 = trunc %int %232 to %byte
    %234 = load %"_Pshadow_Pstandard_CString"** %this
    %235 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %234, i32 0, i32 1
    %236 = load %int* %j
    %237 = add %int %236, 2
    %238 = load { %byte*, [1 x %int] }* %235
    %239 = extractvalue { %byte*, [1 x %int] } %238, 0
    %240 = getelementptr inbounds %byte* %239, %int %237
    store %byte %233, %byte* %240
    %241 = load %int* %j
    %242 = add %int %241, 3
    store %int %242, %int* %j
    br label %_label42
_label41:
    %243 = load { %code*, [1 x %int] }* %data
    %244 = load %int* %i1
    %245 = extractvalue { %code*, [1 x %int] } %243, 0
    %246 = getelementptr inbounds %code* %245, %int %244
    %247 = shl %int 1, 21
    %248 = load %code* %246
    %249 = bitcast %code %248 to %int
    %250 = icmp slt %int %249, %247
    br %boolean %250, label %_label43, label %_label44
_label43:
    %251 = load { %code*, [1 x %int] }* %data
    %252 = load %int* %i1
    %253 = extractvalue { %code*, [1 x %int] } %251, 0
    %254 = getelementptr inbounds %code* %253, %int %252
    %255 = load %code* %254
    %256 = bitcast %code %255 to %int
    %257 = ashr %int %256, 18
    %258 = and %int %257, 7
    %259 = or %int %258, 240
    %260 = trunc %int %259 to %byte
    %261 = load %"_Pshadow_Pstandard_CString"** %this
    %262 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %261, i32 0, i32 1
    %263 = load %int* %j
    %264 = add %int %263, 0
    %265 = load { %byte*, [1 x %int] }* %262
    %266 = extractvalue { %byte*, [1 x %int] } %265, 0
    %267 = getelementptr inbounds %byte* %266, %int %264
    store %byte %260, %byte* %267
    %268 = load { %code*, [1 x %int] }* %data
    %269 = load %int* %i1
    %270 = extractvalue { %code*, [1 x %int] } %268, 0
    %271 = getelementptr inbounds %code* %270, %int %269
    %272 = load %code* %271
    %273 = bitcast %code %272 to %int
    %274 = ashr %int %273, 12
    %275 = and %int %274, 63
    %276 = or %int %275, 128
    %277 = trunc %int %276 to %byte
    %278 = load %"_Pshadow_Pstandard_CString"** %this
    %279 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %278, i32 0, i32 1
    %280 = load %int* %j
    %281 = add %int %280, 1
    %282 = load { %byte*, [1 x %int] }* %279
    %283 = extractvalue { %byte*, [1 x %int] } %282, 0
    %284 = getelementptr inbounds %byte* %283, %int %281
    store %byte %277, %byte* %284
    %285 = load { %code*, [1 x %int] }* %data
    %286 = load %int* %i1
    %287 = extractvalue { %code*, [1 x %int] } %285, 0
    %288 = getelementptr inbounds %code* %287, %int %286
    %289 = load %code* %288
    %290 = bitcast %code %289 to %int
    %291 = ashr %int %290, 6
    %292 = and %int %291, 63
    %293 = or %int %292, 128
    %294 = trunc %int %293 to %byte
    %295 = load %"_Pshadow_Pstandard_CString"** %this
    %296 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %295, i32 0, i32 1
    %297 = load %int* %j
    %298 = add %int %297, 2
    %299 = load { %byte*, [1 x %int] }* %296
    %300 = extractvalue { %byte*, [1 x %int] } %299, 0
    %301 = getelementptr inbounds %byte* %300, %int %298
    store %byte %294, %byte* %301
    %302 = load { %code*, [1 x %int] }* %data
    %303 = load %int* %i1
    %304 = extractvalue { %code*, [1 x %int] } %302, 0
    %305 = getelementptr inbounds %code* %304, %int %303
    %306 = load %code* %305
    %307 = bitcast %code %306 to %int
    %308 = ashr %int %307, 0
    %309 = and %int %308, 63
    %310 = or %int %309, 128
    %311 = trunc %int %310 to %byte
    %312 = load %"_Pshadow_Pstandard_CString"** %this
    %313 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %312, i32 0, i32 1
    %314 = load %int* %j
    %315 = add %int %314, 3
    %316 = load { %byte*, [1 x %int] }* %313
    %317 = extractvalue { %byte*, [1 x %int] } %316, 0
    %318 = getelementptr inbounds %byte* %317, %int %315
    store %byte %311, %byte* %318
    %319 = load %int* %j
    %320 = add %int %319, 4
    store %int %320, %int* %j
    br label %_label45
_label44:
    %321 = load { %code*, [1 x %int] }* %data
    %322 = load %int* %i1
    %323 = extractvalue { %code*, [1 x %int] } %321, 0
    %324 = getelementptr inbounds %code* %323, %int %322
    %325 = shl %int 1, 26
    %326 = load %code* %324
    %327 = bitcast %code %326 to %int
    %328 = icmp slt %int %327, %325
    br %boolean %328, label %_label46, label %_label47
_label46:
    %329 = load { %code*, [1 x %int] }* %data
    %330 = load %int* %i1
    %331 = extractvalue { %code*, [1 x %int] } %329, 0
    %332 = getelementptr inbounds %code* %331, %int %330
    %333 = load %code* %332
    %334 = bitcast %code %333 to %int
    %335 = ashr %int %334, 24
    %336 = and %int %335, 3
    %337 = or %int %336, 248
    %338 = trunc %int %337 to %byte
    %339 = load %"_Pshadow_Pstandard_CString"** %this
    %340 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %339, i32 0, i32 1
    %341 = load %int* %j
    %342 = add %int %341, 0
    %343 = load { %byte*, [1 x %int] }* %340
    %344 = extractvalue { %byte*, [1 x %int] } %343, 0
    %345 = getelementptr inbounds %byte* %344, %int %342
    store %byte %338, %byte* %345
    %346 = load { %code*, [1 x %int] }* %data
    %347 = load %int* %i1
    %348 = extractvalue { %code*, [1 x %int] } %346, 0
    %349 = getelementptr inbounds %code* %348, %int %347
    %350 = load %code* %349
    %351 = bitcast %code %350 to %int
    %352 = ashr %int %351, 18
    %353 = and %int %352, 63
    %354 = or %int %353, 128
    %355 = trunc %int %354 to %byte
    %356 = load %"_Pshadow_Pstandard_CString"** %this
    %357 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %356, i32 0, i32 1
    %358 = load %int* %j
    %359 = add %int %358, 1
    %360 = load { %byte*, [1 x %int] }* %357
    %361 = extractvalue { %byte*, [1 x %int] } %360, 0
    %362 = getelementptr inbounds %byte* %361, %int %359
    store %byte %355, %byte* %362
    %363 = load { %code*, [1 x %int] }* %data
    %364 = load %int* %i1
    %365 = extractvalue { %code*, [1 x %int] } %363, 0
    %366 = getelementptr inbounds %code* %365, %int %364
    %367 = load %code* %366
    %368 = bitcast %code %367 to %int
    %369 = ashr %int %368, 12
    %370 = and %int %369, 63
    %371 = or %int %370, 128
    %372 = trunc %int %371 to %byte
    %373 = load %"_Pshadow_Pstandard_CString"** %this
    %374 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %373, i32 0, i32 1
    %375 = load %int* %j
    %376 = add %int %375, 2
    %377 = load { %byte*, [1 x %int] }* %374
    %378 = extractvalue { %byte*, [1 x %int] } %377, 0
    %379 = getelementptr inbounds %byte* %378, %int %376
    store %byte %372, %byte* %379
    %380 = load { %code*, [1 x %int] }* %data
    %381 = load %int* %i1
    %382 = extractvalue { %code*, [1 x %int] } %380, 0
    %383 = getelementptr inbounds %code* %382, %int %381
    %384 = load %code* %383
    %385 = bitcast %code %384 to %int
    %386 = ashr %int %385, 6
    %387 = and %int %386, 63
    %388 = or %int %387, 128
    %389 = trunc %int %388 to %byte
    %390 = load %"_Pshadow_Pstandard_CString"** %this
    %391 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %390, i32 0, i32 1
    %392 = load %int* %j
    %393 = add %int %392, 3
    %394 = load { %byte*, [1 x %int] }* %391
    %395 = extractvalue { %byte*, [1 x %int] } %394, 0
    %396 = getelementptr inbounds %byte* %395, %int %393
    store %byte %389, %byte* %396
    %397 = load { %code*, [1 x %int] }* %data
    %398 = load %int* %i1
    %399 = extractvalue { %code*, [1 x %int] } %397, 0
    %400 = getelementptr inbounds %code* %399, %int %398
    %401 = load %code* %400
    %402 = bitcast %code %401 to %int
    %403 = ashr %int %402, 0
    %404 = and %int %403, 63
    %405 = or %int %404, 128
    %406 = trunc %int %405 to %byte
    %407 = load %"_Pshadow_Pstandard_CString"** %this
    %408 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %407, i32 0, i32 1
    %409 = load %int* %j
    %410 = add %int %409, 4
    %411 = load { %byte*, [1 x %int] }* %408
    %412 = extractvalue { %byte*, [1 x %int] } %411, 0
    %413 = getelementptr inbounds %byte* %412, %int %410
    store %byte %406, %byte* %413
    %414 = load %int* %j
    %415 = add %int %414, 5
    store %int %415, %int* %j
    br label %_label48
_label47:
    %416 = load { %code*, [1 x %int] }* %data
    %417 = load %int* %i1
    %418 = extractvalue { %code*, [1 x %int] } %416, 0
    %419 = getelementptr inbounds %code* %418, %int %417
    %420 = shl %int 1, 31
    %421 = load %code* %419
    %422 = bitcast %code %421 to %int
    %423 = icmp slt %int %422, %420
    br %boolean %423, label %_label49, label %_label50
_label49:
    %424 = load { %code*, [1 x %int] }* %data
    %425 = load %int* %i1
    %426 = extractvalue { %code*, [1 x %int] } %424, 0
    %427 = getelementptr inbounds %code* %426, %int %425
    %428 = load %code* %427
    %429 = bitcast %code %428 to %int
    %430 = ashr %int %429, 30
    %431 = and %int %430, 1
    %432 = or %int %431, 252
    %433 = trunc %int %432 to %byte
    %434 = load %"_Pshadow_Pstandard_CString"** %this
    %435 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %434, i32 0, i32 1
    %436 = load %int* %j
    %437 = add %int %436, 0
    %438 = load { %byte*, [1 x %int] }* %435
    %439 = extractvalue { %byte*, [1 x %int] } %438, 0
    %440 = getelementptr inbounds %byte* %439, %int %437
    store %byte %433, %byte* %440
    %441 = load { %code*, [1 x %int] }* %data
    %442 = load %int* %i1
    %443 = extractvalue { %code*, [1 x %int] } %441, 0
    %444 = getelementptr inbounds %code* %443, %int %442
    %445 = load %code* %444
    %446 = bitcast %code %445 to %int
    %447 = ashr %int %446, 24
    %448 = and %int %447, 63
    %449 = or %int %448, 128
    %450 = trunc %int %449 to %byte
    %451 = load %"_Pshadow_Pstandard_CString"** %this
    %452 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %451, i32 0, i32 1
    %453 = load %int* %j
    %454 = add %int %453, 1
    %455 = load { %byte*, [1 x %int] }* %452
    %456 = extractvalue { %byte*, [1 x %int] } %455, 0
    %457 = getelementptr inbounds %byte* %456, %int %454
    store %byte %450, %byte* %457
    %458 = load { %code*, [1 x %int] }* %data
    %459 = load %int* %i1
    %460 = extractvalue { %code*, [1 x %int] } %458, 0
    %461 = getelementptr inbounds %code* %460, %int %459
    %462 = load %code* %461
    %463 = bitcast %code %462 to %int
    %464 = ashr %int %463, 18
    %465 = and %int %464, 63
    %466 = or %int %465, 128
    %467 = trunc %int %466 to %byte
    %468 = load %"_Pshadow_Pstandard_CString"** %this
    %469 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %468, i32 0, i32 1
    %470 = load %int* %j
    %471 = add %int %470, 2
    %472 = load { %byte*, [1 x %int] }* %469
    %473 = extractvalue { %byte*, [1 x %int] } %472, 0
    %474 = getelementptr inbounds %byte* %473, %int %471
    store %byte %467, %byte* %474
    %475 = load { %code*, [1 x %int] }* %data
    %476 = load %int* %i1
    %477 = extractvalue { %code*, [1 x %int] } %475, 0
    %478 = getelementptr inbounds %code* %477, %int %476
    %479 = load %code* %478
    %480 = bitcast %code %479 to %int
    %481 = ashr %int %480, 12
    %482 = and %int %481, 63
    %483 = or %int %482, 128
    %484 = trunc %int %483 to %byte
    %485 = load %"_Pshadow_Pstandard_CString"** %this
    %486 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %485, i32 0, i32 1
    %487 = load %int* %j
    %488 = add %int %487, 3
    %489 = load { %byte*, [1 x %int] }* %486
    %490 = extractvalue { %byte*, [1 x %int] } %489, 0
    %491 = getelementptr inbounds %byte* %490, %int %488
    store %byte %484, %byte* %491
    %492 = load { %code*, [1 x %int] }* %data
    %493 = load %int* %i1
    %494 = extractvalue { %code*, [1 x %int] } %492, 0
    %495 = getelementptr inbounds %code* %494, %int %493
    %496 = load %code* %495
    %497 = bitcast %code %496 to %int
    %498 = ashr %int %497, 6
    %499 = and %int %498, 63
    %500 = or %int %499, 128
    %501 = trunc %int %500 to %byte
    %502 = load %"_Pshadow_Pstandard_CString"** %this
    %503 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %502, i32 0, i32 1
    %504 = load %int* %j
    %505 = add %int %504, 4
    %506 = load { %byte*, [1 x %int] }* %503
    %507 = extractvalue { %byte*, [1 x %int] } %506, 0
    %508 = getelementptr inbounds %byte* %507, %int %505
    store %byte %501, %byte* %508
    %509 = load { %code*, [1 x %int] }* %data
    %510 = load %int* %i1
    %511 = extractvalue { %code*, [1 x %int] } %509, 0
    %512 = getelementptr inbounds %code* %511, %int %510
    %513 = load %code* %512
    %514 = bitcast %code %513 to %int
    %515 = ashr %int %514, 0
    %516 = and %int %515, 63
    %517 = or %int %516, 128
    %518 = trunc %int %517 to %byte
    %519 = load %"_Pshadow_Pstandard_CString"** %this
    %520 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %519, i32 0, i32 1
    %521 = load %int* %j
    %522 = add %int %521, 5
    %523 = load { %byte*, [1 x %int] }* %520
    %524 = extractvalue { %byte*, [1 x %int] } %523, 0
    %525 = getelementptr inbounds %byte* %524, %int %522
    store %byte %518, %byte* %525
    %526 = load %int* %j
    %527 = add %int %526, 6
    store %int %527, %int* %j
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
    %528 = load %int* %i1
    %529 = add %int %528, 1
    store %int %529, %int* %i1
    br label %_label32
_label32:
    %530 = load { %code*, [1 x %int] }* %data
    %531 = load %int* %i1
    %532 = extractvalue { %code*, [1 x %int] } %530, 1
    %533 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %534 = bitcast %"_Pshadow_Pstandard_CObject"* %533 to [1 x %int]*
    store [1 x %int] %532, [1 x %int]* %534
    %535 = getelementptr inbounds [1 x %int]* %534, i32 0, i32 0
    %536 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %535, 0
    %537 = extractvalue { %code*, [1 x %int] } %530, 0
    %538 = bitcast %code* %537 to %"_Pshadow_Pstandard_CObject"*
    %539 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %540 = bitcast %"_Pshadow_Pstandard_CObject"* %539 to %"_Pshadow_Pstandard_CArray"*
    %541 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %540, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %536, %"_Pshadow_Pstandard_CObject"* %538)
    %542 = getelementptr %"_Pshadow_Pstandard_CArray"* %541, i32 0, i32 0
    %543 = load %"_Pshadow_Pstandard_CArray_Mclass"** %542
    %544 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %543, i32 0, i32 13
    %545 = load %int (%"_Pshadow_Pstandard_CArray"*)** %544
    %546 = extractvalue { %code*, [1 x %int] } %530, 1
    %547 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %548 = bitcast %"_Pshadow_Pstandard_CObject"* %547 to [1 x %int]*
    store [1 x %int] %546, [1 x %int]* %548
    %549 = getelementptr inbounds [1 x %int]* %548, i32 0, i32 0
    %550 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %549, 0
    %551 = extractvalue { %code*, [1 x %int] } %530, 0
    %552 = bitcast %code* %551 to %"_Pshadow_Pstandard_CObject"*
    %553 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %554 = bitcast %"_Pshadow_Pstandard_CObject"* %553 to %"_Pshadow_Pstandard_CArray"*
    %555 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %554, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %550, %"_Pshadow_Pstandard_CObject"* %552)
    %556 = call %int %545(%"_Pshadow_Pstandard_CArray"* %555)
    %557 = icmp slt %int %531, %556
    br %boolean %557, label %_label31, label %_label33
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
    %5 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %3)
    %6 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %4)
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
    %24 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %22)
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
    %31 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %30)
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
    %41 = call %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"* %39)
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

define %int @"_Pshadow_Pstandard_CString_Msize"(%"_Pshadow_Pstandard_CString"*) {
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
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 13
    %18 = load %int (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = extractvalue { %byte*, [1 x %int] } %4, 1
    %20 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %21 = bitcast %"_Pshadow_Pstandard_CObject"* %20 to [1 x %int]*
    store [1 x %int] %19, [1 x %int]* %21
    %22 = getelementptr inbounds [1 x %int]* %21, i32 0, i32 0
    %23 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %22, 0
    %24 = extractvalue { %byte*, [1 x %int] } %4, 0
    %25 = bitcast %byte* %24 to %"_Pshadow_Pstandard_CObject"*
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_CArray"*
    %28 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %27, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %23, %"_Pshadow_Pstandard_CObject"* %25)
    %29 = call %int %18(%"_Pshadow_Pstandard_CArray"* %28)
    ret %int %29
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
declare { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CArray_MtoString"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int, %int)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })
declare %int @"_Pshadow_Pstandard_CArray_Msize"(%"_Pshadow_Pstandard_CArray"*)
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
declare %int @"_Pshadow_Pstandard_CMutableString_Mcapacity"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CMutableString_MtoString"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate"(%"_Pshadow_Pstandard_CMutableString"*)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CMutableString"*, %int)
declare %"_Pshadow_Pstandard_CMutableString"* @"_Pshadow_Pstandard_CMutableString_Mcreate_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)
declare %int @"_Pshadow_Pstandard_CMutableString_Msize"(%"_Pshadow_Pstandard_CMutableString"*)

@_array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@String"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([22 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 22] }, %boolean true }
