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
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
%"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString_IStringIterator"* (%"_Pshadow_Pstandard_CString_IStringIterator"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)*, %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)* }
%"_Pshadow_Pstandard_CString_IStringIterator" = type { %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"*, %"_Pshadow_Pstandard_CString"*, %int }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject"* }
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
@"_Pshadow_Pstandard_CString_IStringIterator_Mclass" = external constant %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external constant %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
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
    %52 = getelementptr %"_Pshadow_Pstandard_CException"* %51, i32 0, i32 0
    %53 = load %"_Pshadow_Pstandard_CException_Mclass"** %52
    %54 = getelementptr %"_Pshadow_Pstandard_CException_Mclass"* %53, i32 0, i32 9
    %55 = load void (%"_Pshadow_Pstandard_CException"*)** %54
    %56 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %49 to %"_Pshadow_Pstandard_CException"*
    call void %55(%"_Pshadow_Pstandard_CException"* %56)
    br label %_label24
_label23:
    %57 = load { %code*, [1 x %int] }* %data
    %58 = load %int* %i
    %59 = extractvalue { %code*, [1 x %int] } %57, 0
    %60 = getelementptr inbounds %code* %59, %int %58
    %61 = shl %int 1, 7
    %62 = load %code* %60
    %63 = bitcast %code %62 to %int
    %64 = icmp sge %int %63, %61
    br %boolean %64, label %_label25, label %_label26
_label25:
    %65 = load %"_Pshadow_Pstandard_CString"** %this
    %66 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %65, i32 0, i32 2
    store %boolean false, %boolean* %66
    %67 = load %int* %size
    %68 = add %int %67, 1
    store %int %68, %int* %size
    store %int 11, %int* %shift
    br label %_label29
_label28:
    %69 = load %int* %size
    %70 = add %int %69, 1
    store %int %70, %int* %size
    %71 = load %int* %shift
    %72 = add %int %71, 5
    store %int %72, %int* %shift
    br label %_label29
_label29:
    %73 = load { %code*, [1 x %int] }* %data
    %74 = load %int* %i
    %75 = extractvalue { %code*, [1 x %int] } %73, 0
    %76 = getelementptr inbounds %code* %75, %int %74
    %77 = load %int* %shift
    %78 = shl %int 1, %77
    %79 = load %code* %76
    %80 = bitcast %code %79 to %int
    %81 = icmp sge %int %80, %78
    br %boolean %81, label %_label28, label %_label30
_label30:
    br label %_label27
_label26:
    br label %_label27
_label27:
    br label %_label24
_label24:
    %82 = load %int* %i
    %83 = add %int %82, 1
    store %int %83, %int* %i
    br label %_label20
_label20:
    %84 = load { %code*, [1 x %int] }* %data
    %85 = load %int* %i
    %86 = extractvalue { %code*, [1 x %int] } %84, 1
    %87 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %88 = bitcast %"_Pshadow_Pstandard_CObject"* %87 to [1 x %int]*
    store [1 x %int] %86, [1 x %int]* %88
    %89 = getelementptr inbounds [1 x %int]* %88, i32 0, i32 0
    %90 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %89, 0
    %91 = extractvalue { %code*, [1 x %int] } %84, 0
    %92 = bitcast %code* %91 to %"_Pshadow_Pstandard_CObject"*
    %93 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %94 = bitcast %"_Pshadow_Pstandard_CObject"* %93 to %"_Pshadow_Pstandard_CArray"*
    %95 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %94, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %90, %"_Pshadow_Pstandard_CObject"* %92)
    %96 = getelementptr %"_Pshadow_Pstandard_CArray"* %95, i32 0, i32 0
    %97 = load %"_Pshadow_Pstandard_CArray_Mclass"** %96
    %98 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %97, i32 0, i32 13
    %99 = load %int (%"_Pshadow_Pstandard_CArray"*)** %98
    %100 = extractvalue { %code*, [1 x %int] } %84, 1
    %101 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %102 = bitcast %"_Pshadow_Pstandard_CObject"* %101 to [1 x %int]*
    store [1 x %int] %100, [1 x %int]* %102
    %103 = getelementptr inbounds [1 x %int]* %102, i32 0, i32 0
    %104 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %103, 0
    %105 = extractvalue { %code*, [1 x %int] } %84, 0
    %106 = bitcast %code* %105 to %"_Pshadow_Pstandard_CObject"*
    %107 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %108 = bitcast %"_Pshadow_Pstandard_CObject"* %107 to %"_Pshadow_Pstandard_CArray"*
    %109 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %108, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %104, %"_Pshadow_Pstandard_CObject"* %106)
    %110 = call %int %99(%"_Pshadow_Pstandard_CArray"* %109)
    %111 = icmp slt %int %85, %110
    br %boolean %111, label %_label19, label %_label21
_label21:
    %112 = load %int* %size
    %113 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %112)
    %114 = bitcast %"_Pshadow_Pstandard_CObject"* %113 to %byte*
    %115 = insertvalue { %byte*, [1 x %int] } undef, %byte* %114, 0
    %116 = insertvalue { %byte*, [1 x %int] } %115, %int %112, 1, 0
    %117 = load %"_Pshadow_Pstandard_CString"** %this
    %118 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %117, i32 0, i32 1
    store { %byte*, [1 x %int] } %116, { %byte*, [1 x %int] }* %118
    store %int 0, %int* %i1
    store %int 0, %int* %j
    br label %_label32
_label31:
    %119 = load { %code*, [1 x %int] }* %data
    %120 = load %int* %i1
    %121 = extractvalue { %code*, [1 x %int] } %119, 0
    %122 = getelementptr inbounds %code* %121, %int %120
    %123 = shl %int 1, 7
    %124 = load %code* %122
    %125 = bitcast %code %124 to %int
    %126 = icmp slt %int %125, %123
    br %boolean %126, label %_label34, label %_label35
_label34:
    %127 = load { %code*, [1 x %int] }* %data
    %128 = load %int* %i1
    %129 = extractvalue { %code*, [1 x %int] } %127, 0
    %130 = getelementptr inbounds %code* %129, %int %128
    %131 = load %code* %130
    %132 = trunc %code %131 to %byte
    %133 = load %"_Pshadow_Pstandard_CString"** %this
    %134 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %133, i32 0, i32 1
    %135 = load %int* %j
    %136 = add %int %135, 0
    %137 = load { %byte*, [1 x %int] }* %134
    %138 = extractvalue { %byte*, [1 x %int] } %137, 0
    %139 = getelementptr inbounds %byte* %138, %int %136
    store %byte %132, %byte* %139
    %140 = load %int* %j
    %141 = add %int %140, 1
    store %int %141, %int* %j
    br label %_label36
_label35:
    %142 = load { %code*, [1 x %int] }* %data
    %143 = load %int* %i1
    %144 = extractvalue { %code*, [1 x %int] } %142, 0
    %145 = getelementptr inbounds %code* %144, %int %143
    %146 = shl %int 1, 11
    %147 = load %code* %145
    %148 = bitcast %code %147 to %int
    %149 = icmp slt %int %148, %146
    br %boolean %149, label %_label37, label %_label38
_label37:
    %150 = load { %code*, [1 x %int] }* %data
    %151 = load %int* %i1
    %152 = extractvalue { %code*, [1 x %int] } %150, 0
    %153 = getelementptr inbounds %code* %152, %int %151
    %154 = load %code* %153
    %155 = bitcast %code %154 to %int
    %156 = ashr %int %155, 6
    %157 = and %int %156, 31
    %158 = or %int %157, 192
    %159 = trunc %int %158 to %byte
    %160 = load %"_Pshadow_Pstandard_CString"** %this
    %161 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %160, i32 0, i32 1
    %162 = load %int* %j
    %163 = add %int %162, 0
    %164 = load { %byte*, [1 x %int] }* %161
    %165 = extractvalue { %byte*, [1 x %int] } %164, 0
    %166 = getelementptr inbounds %byte* %165, %int %163
    store %byte %159, %byte* %166
    %167 = load { %code*, [1 x %int] }* %data
    %168 = load %int* %i1
    %169 = extractvalue { %code*, [1 x %int] } %167, 0
    %170 = getelementptr inbounds %code* %169, %int %168
    %171 = load %code* %170
    %172 = bitcast %code %171 to %int
    %173 = ashr %int %172, 0
    %174 = and %int %173, 63
    %175 = or %int %174, 128
    %176 = trunc %int %175 to %byte
    %177 = load %"_Pshadow_Pstandard_CString"** %this
    %178 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %177, i32 0, i32 1
    %179 = load %int* %j
    %180 = add %int %179, 1
    %181 = load { %byte*, [1 x %int] }* %178
    %182 = extractvalue { %byte*, [1 x %int] } %181, 0
    %183 = getelementptr inbounds %byte* %182, %int %180
    store %byte %176, %byte* %183
    %184 = load %int* %j
    %185 = add %int %184, 2
    store %int %185, %int* %j
    br label %_label39
_label38:
    %186 = load { %code*, [1 x %int] }* %data
    %187 = load %int* %i1
    %188 = extractvalue { %code*, [1 x %int] } %186, 0
    %189 = getelementptr inbounds %code* %188, %int %187
    %190 = shl %int 1, 16
    %191 = load %code* %189
    %192 = bitcast %code %191 to %int
    %193 = icmp slt %int %192, %190
    br %boolean %193, label %_label40, label %_label41
_label40:
    %194 = load { %code*, [1 x %int] }* %data
    %195 = load %int* %i1
    %196 = extractvalue { %code*, [1 x %int] } %194, 0
    %197 = getelementptr inbounds %code* %196, %int %195
    %198 = load %code* %197
    %199 = bitcast %code %198 to %int
    %200 = ashr %int %199, 12
    %201 = and %int %200, 15
    %202 = or %int %201, 224
    %203 = trunc %int %202 to %byte
    %204 = load %"_Pshadow_Pstandard_CString"** %this
    %205 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %204, i32 0, i32 1
    %206 = load %int* %j
    %207 = add %int %206, 0
    %208 = load { %byte*, [1 x %int] }* %205
    %209 = extractvalue { %byte*, [1 x %int] } %208, 0
    %210 = getelementptr inbounds %byte* %209, %int %207
    store %byte %203, %byte* %210
    %211 = load { %code*, [1 x %int] }* %data
    %212 = load %int* %i1
    %213 = extractvalue { %code*, [1 x %int] } %211, 0
    %214 = getelementptr inbounds %code* %213, %int %212
    %215 = load %code* %214
    %216 = bitcast %code %215 to %int
    %217 = ashr %int %216, 6
    %218 = and %int %217, 63
    %219 = or %int %218, 128
    %220 = trunc %int %219 to %byte
    %221 = load %"_Pshadow_Pstandard_CString"** %this
    %222 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %221, i32 0, i32 1
    %223 = load %int* %j
    %224 = add %int %223, 1
    %225 = load { %byte*, [1 x %int] }* %222
    %226 = extractvalue { %byte*, [1 x %int] } %225, 0
    %227 = getelementptr inbounds %byte* %226, %int %224
    store %byte %220, %byte* %227
    %228 = load { %code*, [1 x %int] }* %data
    %229 = load %int* %i1
    %230 = extractvalue { %code*, [1 x %int] } %228, 0
    %231 = getelementptr inbounds %code* %230, %int %229
    %232 = load %code* %231
    %233 = bitcast %code %232 to %int
    %234 = ashr %int %233, 0
    %235 = and %int %234, 63
    %236 = or %int %235, 128
    %237 = trunc %int %236 to %byte
    %238 = load %"_Pshadow_Pstandard_CString"** %this
    %239 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %238, i32 0, i32 1
    %240 = load %int* %j
    %241 = add %int %240, 2
    %242 = load { %byte*, [1 x %int] }* %239
    %243 = extractvalue { %byte*, [1 x %int] } %242, 0
    %244 = getelementptr inbounds %byte* %243, %int %241
    store %byte %237, %byte* %244
    %245 = load %int* %j
    %246 = add %int %245, 3
    store %int %246, %int* %j
    br label %_label42
_label41:
    %247 = load { %code*, [1 x %int] }* %data
    %248 = load %int* %i1
    %249 = extractvalue { %code*, [1 x %int] } %247, 0
    %250 = getelementptr inbounds %code* %249, %int %248
    %251 = shl %int 1, 21
    %252 = load %code* %250
    %253 = bitcast %code %252 to %int
    %254 = icmp slt %int %253, %251
    br %boolean %254, label %_label43, label %_label44
_label43:
    %255 = load { %code*, [1 x %int] }* %data
    %256 = load %int* %i1
    %257 = extractvalue { %code*, [1 x %int] } %255, 0
    %258 = getelementptr inbounds %code* %257, %int %256
    %259 = load %code* %258
    %260 = bitcast %code %259 to %int
    %261 = ashr %int %260, 18
    %262 = and %int %261, 7
    %263 = or %int %262, 240
    %264 = trunc %int %263 to %byte
    %265 = load %"_Pshadow_Pstandard_CString"** %this
    %266 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %265, i32 0, i32 1
    %267 = load %int* %j
    %268 = add %int %267, 0
    %269 = load { %byte*, [1 x %int] }* %266
    %270 = extractvalue { %byte*, [1 x %int] } %269, 0
    %271 = getelementptr inbounds %byte* %270, %int %268
    store %byte %264, %byte* %271
    %272 = load { %code*, [1 x %int] }* %data
    %273 = load %int* %i1
    %274 = extractvalue { %code*, [1 x %int] } %272, 0
    %275 = getelementptr inbounds %code* %274, %int %273
    %276 = load %code* %275
    %277 = bitcast %code %276 to %int
    %278 = ashr %int %277, 12
    %279 = and %int %278, 63
    %280 = or %int %279, 128
    %281 = trunc %int %280 to %byte
    %282 = load %"_Pshadow_Pstandard_CString"** %this
    %283 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %282, i32 0, i32 1
    %284 = load %int* %j
    %285 = add %int %284, 1
    %286 = load { %byte*, [1 x %int] }* %283
    %287 = extractvalue { %byte*, [1 x %int] } %286, 0
    %288 = getelementptr inbounds %byte* %287, %int %285
    store %byte %281, %byte* %288
    %289 = load { %code*, [1 x %int] }* %data
    %290 = load %int* %i1
    %291 = extractvalue { %code*, [1 x %int] } %289, 0
    %292 = getelementptr inbounds %code* %291, %int %290
    %293 = load %code* %292
    %294 = bitcast %code %293 to %int
    %295 = ashr %int %294, 6
    %296 = and %int %295, 63
    %297 = or %int %296, 128
    %298 = trunc %int %297 to %byte
    %299 = load %"_Pshadow_Pstandard_CString"** %this
    %300 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %299, i32 0, i32 1
    %301 = load %int* %j
    %302 = add %int %301, 2
    %303 = load { %byte*, [1 x %int] }* %300
    %304 = extractvalue { %byte*, [1 x %int] } %303, 0
    %305 = getelementptr inbounds %byte* %304, %int %302
    store %byte %298, %byte* %305
    %306 = load { %code*, [1 x %int] }* %data
    %307 = load %int* %i1
    %308 = extractvalue { %code*, [1 x %int] } %306, 0
    %309 = getelementptr inbounds %code* %308, %int %307
    %310 = load %code* %309
    %311 = bitcast %code %310 to %int
    %312 = ashr %int %311, 0
    %313 = and %int %312, 63
    %314 = or %int %313, 128
    %315 = trunc %int %314 to %byte
    %316 = load %"_Pshadow_Pstandard_CString"** %this
    %317 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %316, i32 0, i32 1
    %318 = load %int* %j
    %319 = add %int %318, 3
    %320 = load { %byte*, [1 x %int] }* %317
    %321 = extractvalue { %byte*, [1 x %int] } %320, 0
    %322 = getelementptr inbounds %byte* %321, %int %319
    store %byte %315, %byte* %322
    %323 = load %int* %j
    %324 = add %int %323, 4
    store %int %324, %int* %j
    br label %_label45
_label44:
    %325 = load { %code*, [1 x %int] }* %data
    %326 = load %int* %i1
    %327 = extractvalue { %code*, [1 x %int] } %325, 0
    %328 = getelementptr inbounds %code* %327, %int %326
    %329 = shl %int 1, 26
    %330 = load %code* %328
    %331 = bitcast %code %330 to %int
    %332 = icmp slt %int %331, %329
    br %boolean %332, label %_label46, label %_label47
_label46:
    %333 = load { %code*, [1 x %int] }* %data
    %334 = load %int* %i1
    %335 = extractvalue { %code*, [1 x %int] } %333, 0
    %336 = getelementptr inbounds %code* %335, %int %334
    %337 = load %code* %336
    %338 = bitcast %code %337 to %int
    %339 = ashr %int %338, 24
    %340 = and %int %339, 3
    %341 = or %int %340, 248
    %342 = trunc %int %341 to %byte
    %343 = load %"_Pshadow_Pstandard_CString"** %this
    %344 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %343, i32 0, i32 1
    %345 = load %int* %j
    %346 = add %int %345, 0
    %347 = load { %byte*, [1 x %int] }* %344
    %348 = extractvalue { %byte*, [1 x %int] } %347, 0
    %349 = getelementptr inbounds %byte* %348, %int %346
    store %byte %342, %byte* %349
    %350 = load { %code*, [1 x %int] }* %data
    %351 = load %int* %i1
    %352 = extractvalue { %code*, [1 x %int] } %350, 0
    %353 = getelementptr inbounds %code* %352, %int %351
    %354 = load %code* %353
    %355 = bitcast %code %354 to %int
    %356 = ashr %int %355, 18
    %357 = and %int %356, 63
    %358 = or %int %357, 128
    %359 = trunc %int %358 to %byte
    %360 = load %"_Pshadow_Pstandard_CString"** %this
    %361 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %360, i32 0, i32 1
    %362 = load %int* %j
    %363 = add %int %362, 1
    %364 = load { %byte*, [1 x %int] }* %361
    %365 = extractvalue { %byte*, [1 x %int] } %364, 0
    %366 = getelementptr inbounds %byte* %365, %int %363
    store %byte %359, %byte* %366
    %367 = load { %code*, [1 x %int] }* %data
    %368 = load %int* %i1
    %369 = extractvalue { %code*, [1 x %int] } %367, 0
    %370 = getelementptr inbounds %code* %369, %int %368
    %371 = load %code* %370
    %372 = bitcast %code %371 to %int
    %373 = ashr %int %372, 12
    %374 = and %int %373, 63
    %375 = or %int %374, 128
    %376 = trunc %int %375 to %byte
    %377 = load %"_Pshadow_Pstandard_CString"** %this
    %378 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %377, i32 0, i32 1
    %379 = load %int* %j
    %380 = add %int %379, 2
    %381 = load { %byte*, [1 x %int] }* %378
    %382 = extractvalue { %byte*, [1 x %int] } %381, 0
    %383 = getelementptr inbounds %byte* %382, %int %380
    store %byte %376, %byte* %383
    %384 = load { %code*, [1 x %int] }* %data
    %385 = load %int* %i1
    %386 = extractvalue { %code*, [1 x %int] } %384, 0
    %387 = getelementptr inbounds %code* %386, %int %385
    %388 = load %code* %387
    %389 = bitcast %code %388 to %int
    %390 = ashr %int %389, 6
    %391 = and %int %390, 63
    %392 = or %int %391, 128
    %393 = trunc %int %392 to %byte
    %394 = load %"_Pshadow_Pstandard_CString"** %this
    %395 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %394, i32 0, i32 1
    %396 = load %int* %j
    %397 = add %int %396, 3
    %398 = load { %byte*, [1 x %int] }* %395
    %399 = extractvalue { %byte*, [1 x %int] } %398, 0
    %400 = getelementptr inbounds %byte* %399, %int %397
    store %byte %393, %byte* %400
    %401 = load { %code*, [1 x %int] }* %data
    %402 = load %int* %i1
    %403 = extractvalue { %code*, [1 x %int] } %401, 0
    %404 = getelementptr inbounds %code* %403, %int %402
    %405 = load %code* %404
    %406 = bitcast %code %405 to %int
    %407 = ashr %int %406, 0
    %408 = and %int %407, 63
    %409 = or %int %408, 128
    %410 = trunc %int %409 to %byte
    %411 = load %"_Pshadow_Pstandard_CString"** %this
    %412 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %411, i32 0, i32 1
    %413 = load %int* %j
    %414 = add %int %413, 4
    %415 = load { %byte*, [1 x %int] }* %412
    %416 = extractvalue { %byte*, [1 x %int] } %415, 0
    %417 = getelementptr inbounds %byte* %416, %int %414
    store %byte %410, %byte* %417
    %418 = load %int* %j
    %419 = add %int %418, 5
    store %int %419, %int* %j
    br label %_label48
_label47:
    %420 = load { %code*, [1 x %int] }* %data
    %421 = load %int* %i1
    %422 = extractvalue { %code*, [1 x %int] } %420, 0
    %423 = getelementptr inbounds %code* %422, %int %421
    %424 = shl %int 1, 31
    %425 = load %code* %423
    %426 = bitcast %code %425 to %int
    %427 = icmp slt %int %426, %424
    br %boolean %427, label %_label49, label %_label50
_label49:
    %428 = load { %code*, [1 x %int] }* %data
    %429 = load %int* %i1
    %430 = extractvalue { %code*, [1 x %int] } %428, 0
    %431 = getelementptr inbounds %code* %430, %int %429
    %432 = load %code* %431
    %433 = bitcast %code %432 to %int
    %434 = ashr %int %433, 30
    %435 = and %int %434, 1
    %436 = or %int %435, 252
    %437 = trunc %int %436 to %byte
    %438 = load %"_Pshadow_Pstandard_CString"** %this
    %439 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %438, i32 0, i32 1
    %440 = load %int* %j
    %441 = add %int %440, 0
    %442 = load { %byte*, [1 x %int] }* %439
    %443 = extractvalue { %byte*, [1 x %int] } %442, 0
    %444 = getelementptr inbounds %byte* %443, %int %441
    store %byte %437, %byte* %444
    %445 = load { %code*, [1 x %int] }* %data
    %446 = load %int* %i1
    %447 = extractvalue { %code*, [1 x %int] } %445, 0
    %448 = getelementptr inbounds %code* %447, %int %446
    %449 = load %code* %448
    %450 = bitcast %code %449 to %int
    %451 = ashr %int %450, 24
    %452 = and %int %451, 63
    %453 = or %int %452, 128
    %454 = trunc %int %453 to %byte
    %455 = load %"_Pshadow_Pstandard_CString"** %this
    %456 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %455, i32 0, i32 1
    %457 = load %int* %j
    %458 = add %int %457, 1
    %459 = load { %byte*, [1 x %int] }* %456
    %460 = extractvalue { %byte*, [1 x %int] } %459, 0
    %461 = getelementptr inbounds %byte* %460, %int %458
    store %byte %454, %byte* %461
    %462 = load { %code*, [1 x %int] }* %data
    %463 = load %int* %i1
    %464 = extractvalue { %code*, [1 x %int] } %462, 0
    %465 = getelementptr inbounds %code* %464, %int %463
    %466 = load %code* %465
    %467 = bitcast %code %466 to %int
    %468 = ashr %int %467, 18
    %469 = and %int %468, 63
    %470 = or %int %469, 128
    %471 = trunc %int %470 to %byte
    %472 = load %"_Pshadow_Pstandard_CString"** %this
    %473 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %472, i32 0, i32 1
    %474 = load %int* %j
    %475 = add %int %474, 2
    %476 = load { %byte*, [1 x %int] }* %473
    %477 = extractvalue { %byte*, [1 x %int] } %476, 0
    %478 = getelementptr inbounds %byte* %477, %int %475
    store %byte %471, %byte* %478
    %479 = load { %code*, [1 x %int] }* %data
    %480 = load %int* %i1
    %481 = extractvalue { %code*, [1 x %int] } %479, 0
    %482 = getelementptr inbounds %code* %481, %int %480
    %483 = load %code* %482
    %484 = bitcast %code %483 to %int
    %485 = ashr %int %484, 12
    %486 = and %int %485, 63
    %487 = or %int %486, 128
    %488 = trunc %int %487 to %byte
    %489 = load %"_Pshadow_Pstandard_CString"** %this
    %490 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %489, i32 0, i32 1
    %491 = load %int* %j
    %492 = add %int %491, 3
    %493 = load { %byte*, [1 x %int] }* %490
    %494 = extractvalue { %byte*, [1 x %int] } %493, 0
    %495 = getelementptr inbounds %byte* %494, %int %492
    store %byte %488, %byte* %495
    %496 = load { %code*, [1 x %int] }* %data
    %497 = load %int* %i1
    %498 = extractvalue { %code*, [1 x %int] } %496, 0
    %499 = getelementptr inbounds %code* %498, %int %497
    %500 = load %code* %499
    %501 = bitcast %code %500 to %int
    %502 = ashr %int %501, 6
    %503 = and %int %502, 63
    %504 = or %int %503, 128
    %505 = trunc %int %504 to %byte
    %506 = load %"_Pshadow_Pstandard_CString"** %this
    %507 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %506, i32 0, i32 1
    %508 = load %int* %j
    %509 = add %int %508, 4
    %510 = load { %byte*, [1 x %int] }* %507
    %511 = extractvalue { %byte*, [1 x %int] } %510, 0
    %512 = getelementptr inbounds %byte* %511, %int %509
    store %byte %505, %byte* %512
    %513 = load { %code*, [1 x %int] }* %data
    %514 = load %int* %i1
    %515 = extractvalue { %code*, [1 x %int] } %513, 0
    %516 = getelementptr inbounds %code* %515, %int %514
    %517 = load %code* %516
    %518 = bitcast %code %517 to %int
    %519 = ashr %int %518, 0
    %520 = and %int %519, 63
    %521 = or %int %520, 128
    %522 = trunc %int %521 to %byte
    %523 = load %"_Pshadow_Pstandard_CString"** %this
    %524 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %523, i32 0, i32 1
    %525 = load %int* %j
    %526 = add %int %525, 5
    %527 = load { %byte*, [1 x %int] }* %524
    %528 = extractvalue { %byte*, [1 x %int] } %527, 0
    %529 = getelementptr inbounds %byte* %528, %int %526
    store %byte %522, %byte* %529
    %530 = load %int* %j
    %531 = add %int %530, 6
    store %int %531, %int* %j
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
    %532 = load %int* %i1
    %533 = add %int %532, 1
    store %int %533, %int* %i1
    br label %_label32
_label32:
    %534 = load { %code*, [1 x %int] }* %data
    %535 = load %int* %i1
    %536 = extractvalue { %code*, [1 x %int] } %534, 1
    %537 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %538 = bitcast %"_Pshadow_Pstandard_CObject"* %537 to [1 x %int]*
    store [1 x %int] %536, [1 x %int]* %538
    %539 = getelementptr inbounds [1 x %int]* %538, i32 0, i32 0
    %540 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %539, 0
    %541 = extractvalue { %code*, [1 x %int] } %534, 0
    %542 = bitcast %code* %541 to %"_Pshadow_Pstandard_CObject"*
    %543 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %544 = bitcast %"_Pshadow_Pstandard_CObject"* %543 to %"_Pshadow_Pstandard_CArray"*
    %545 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %544, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %540, %"_Pshadow_Pstandard_CObject"* %542)
    %546 = getelementptr %"_Pshadow_Pstandard_CArray"* %545, i32 0, i32 0
    %547 = load %"_Pshadow_Pstandard_CArray_Mclass"** %546
    %548 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %547, i32 0, i32 13
    %549 = load %int (%"_Pshadow_Pstandard_CArray"*)** %548
    %550 = extractvalue { %code*, [1 x %int] } %534, 1
    %551 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %552 = bitcast %"_Pshadow_Pstandard_CObject"* %551 to [1 x %int]*
    store [1 x %int] %550, [1 x %int]* %552
    %553 = getelementptr inbounds [1 x %int]* %552, i32 0, i32 0
    %554 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %553, 0
    %555 = extractvalue { %code*, [1 x %int] } %534, 0
    %556 = bitcast %code* %555 to %"_Pshadow_Pstandard_CObject"*
    %557 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %558 = bitcast %"_Pshadow_Pstandard_CObject"* %557 to %"_Pshadow_Pstandard_CArray"*
    %559 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %558, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %554, %"_Pshadow_Pstandard_CObject"* %556)
    %560 = call %int %549(%"_Pshadow_Pstandard_CArray"* %559)
    %561 = icmp slt %int %535, %560
    br %boolean %561, label %_label31, label %_label33
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

declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"*)
declare noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisInterface"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisPrimitive"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)

declare %boolean @"_Pshadow_Pstandard_CString_IStringIterator_MhasNext"(%"_Pshadow_Pstandard_CString_IStringIterator"*)
declare %code @"_Pshadow_Pstandard_CString_IStringIterator_Mnext"(%"_Pshadow_Pstandard_CString_IStringIterator"*)
declare %"_Pshadow_Pstandard_CString_IStringIterator"* @"_Pshadow_Pstandard_CString_IStringIterator_Mcreate"(%"_Pshadow_Pstandard_CString_IStringIterator"*, %"_Pshadow_Pstandard_CString"*)

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

@_array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@String"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([22 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 22] }, %boolean true }
