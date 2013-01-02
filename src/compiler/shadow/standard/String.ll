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

%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CException_Mclass"* }
%"_Pshadow_Pstandard_CIllegalArgumentException_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CIllegalArgumentException"* (%"_Pshadow_Pstandard_CIllegalArgumentException"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, void (%"_Pshadow_Pstandard_CException"*)* }
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

@"_Pshadow_Pstandard_CString_Mclass" = constant %"_Pshadow_Pstandard_CString_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Pstandard_CString"* getelementptr (%"_Pshadow_Pstandard_CString"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Pstandard_CString"** getelementptr (%"_Pshadow_Pstandard_CString"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoString", { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mchars", %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Ccode_A1", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_CString", %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mequals_Pshadow_Pstandard_CString", %byte (%"_Pshadow_Pstandard_CString"*, %int)* @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint", %boolean (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MisEmpty", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Miterator", %int (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mlength", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)* @"_Pshadow_Pstandard_CString_Msubstring_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoLowerCase", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoUpperCase" }
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
    %12 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %11, i32 0, i32 8
    %13 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %12
    %14 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %15 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %16, i32 0, i32 9
    %18 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %17
    %19 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %20 = call %code %18(%"_Pshadow_Pstandard_CString_IStringIterator"* %19)
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"*%21 to %"_Pshadow_Pstandard_Ccode"*
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%22, i32 0, i32 1
    store %code %20, %code* %23
    %24 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %22)
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"*%26 to %"_Pshadow_Pstandard_Ccode"*
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%27, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %28
    %29 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 1
    store %code %24, %code* %29
    %30 = call %"_Pshadow_Pstandard_CMutableString"* %13(%"_Pshadow_Pstandard_CMutableString"* %25, %"_Pshadow_Pstandard_CObject"* %26)
    br label %_label1
_label1:
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %33, i32 0, i32 8
    %35 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %34
    %36 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %37 = call %boolean %35(%"_Pshadow_Pstandard_CString_IStringIterator"* %36)
    br %boolean %37, label %_label0, label %_label2
_label2:
    %38 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %39 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %40, i32 0, i32 7
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
    %7 = icmp ne %int %5, %6
    store %boolean %7, %boolean* %_temp
    br %boolean %7, label %_label4, label %_label3
_label4:
    %8 = load %"_Pshadow_Pstandard_CString"** %this
    %9 = load %"_Pshadow_Pstandard_CString"** %this
    %10 = load %"_Pshadow_Pstandard_CString"** %other
    %11 = call %int @"_Pshadow_Pstandard_CString_Mcompare_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %9, %"_Pshadow_Pstandard_CString"* %10)
    %12 = icmp eq %int %11, 0
    store %boolean %12, %boolean* %_temp
    br label %_label3
_label3:
    %13 = load %boolean* %_temp
    ret %boolean %13
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
    %12 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %11, i32 0, i32 8
    %13 = load %"_Pshadow_Pstandard_CMutableString"* (%"_Pshadow_Pstandard_CMutableString"*, %"_Pshadow_Pstandard_CObject"*)** %12
    %14 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %15 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %16, i32 0, i32 9
    %18 = load %code (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %17
    %19 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %20 = call %code %18(%"_Pshadow_Pstandard_CString_IStringIterator"* %19)
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"*%21 to %"_Pshadow_Pstandard_Ccode"*
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%22, i32 0, i32 1
    store %code %20, %code* %23
    %24 = call %code @"_Pshadow_Pstandard_Ccode_MtoLowerCase"(%"_Pshadow_Pstandard_Ccode"* %22)
    %25 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %26 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %27 = bitcast %"_Pshadow_Pstandard_CObject"*%26 to %"_Pshadow_Pstandard_Ccode"*
    %28 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"*%27, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %28
    %29 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %27, i32 0, i32 1
    store %code %24, %code* %29
    %30 = call %"_Pshadow_Pstandard_CMutableString"* %13(%"_Pshadow_Pstandard_CMutableString"* %25, %"_Pshadow_Pstandard_CObject"* %26)
    br label %_label6
_label6:
    %31 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %32 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator"* %31, i32 0, i32 0
    %33 = load %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"** %32
    %34 = getelementptr %"_Pshadow_Pstandard_CString_IStringIterator_Mclass"* %33, i32 0, i32 8
    %35 = load %boolean (%"_Pshadow_Pstandard_CString_IStringIterator"*)** %34
    %36 = load %"_Pshadow_Pstandard_CString_IStringIterator"** %iterator
    %37 = call %boolean %35(%"_Pshadow_Pstandard_CString_IStringIterator"* %36)
    br %boolean %37, label %_label5, label %_label7
_label7:
    %38 = load %"_Pshadow_Pstandard_CMutableString"** %string
    %39 = getelementptr %"_Pshadow_Pstandard_CMutableString"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CMutableString_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CMutableString_Mclass"* %40, i32 0, i32 7
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
    %11 = icmp ne %byte %6, %10
    br %boolean %11, label %_label11, label %_label12
_label11:
    %12 = load %"_Pshadow_Pstandard_CString"** %this
    %13 = load %"_Pshadow_Pstandard_CString"** %this
    %14 = load %int* %i
    %15 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %13, %int %14)
    %16 = load %"_Pshadow_Pstandard_CString"** %other
    %17 = load %"_Pshadow_Pstandard_CString"** %other
    %18 = load %int* %i
    %19 = call %byte @"_Pshadow_Pstandard_CString_MgetChar_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"* %17, %int %18)
    %20 = sub %byte %15, %19
    %21 = sext %byte %20 to %int
    ret %int %21
    br label %_label13
_label12:
    br label %_label13
_label13:
    %23 = load %int* %i
    %24 = add %int %23, 1
    store %int %24, %int* %i
    br label %_label9
_label9:
    %25 = load %"_Pshadow_Pstandard_CString"** %this
    %26 = load %int* %i
    %27 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %25)
    %28 = icmp slt %int %26, %27
    store %boolean %28, %boolean* %_temp
    br %boolean %28, label %_label15, label %_label14
_label15:
    %29 = load %"_Pshadow_Pstandard_CString"** %other
    %30 = load %int* %i
    %31 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %29)
    %32 = icmp slt %int %30, %31
    store %boolean %32, %boolean* %_temp
    br label %_label14
_label14:
    %33 = load %boolean* %_temp
    br %boolean %33, label %_label8, label %_label10
_label10:
    %34 = load %"_Pshadow_Pstandard_CString"** %this
    %35 = load %"_Pshadow_Pstandard_CString"** %other
    %36 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %34)
    %37 = call %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"* %35)
    %38 = sub %int %36, %37
    ret %int %38
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
    %21 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %20, i32 0, i32 15
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
    %24 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %22 to %"_Pshadow_Pstandard_CException"*
    %25 = bitcast %"_Pshadow_Pstandard_CIllegalArgumentException"* %22 to %"_Pshadow_Pstandard_CException"*
    call void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"* %25)
    br label %_label24
_label23:
    %26 = load { %code*, [1 x %int] }* %data
    %27 = load %int* %i
    %28 = extractvalue { %code*, [1 x %int] } %26, 0
    %29 = getelementptr inbounds %code* %28, %int %27
    %30 = shl %int 1, 7
    %31 = load %code* %29
    %32 = bitcast %code %31 to %int
    %33 = icmp sge %int %32, %30
    br %boolean %33, label %_label25, label %_label26
_label25:
    %34 = load %"_Pshadow_Pstandard_CString"** %this
    %35 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %34, i32 0, i32 2
    store %boolean false, %boolean* %35
    %36 = load %int* %length
    %37 = add %int %36, 1
    store %int %37, %int* %length
    store %int 11, %int* %shift
    br label %_label29
_label28:
    %38 = load %int* %length
    %39 = add %int %38, 1
    store %int %39, %int* %length
    %40 = load %int* %shift
    %41 = add %int %40, 5
    store %int %41, %int* %shift
    br label %_label29
_label29:
    %42 = load { %code*, [1 x %int] }* %data
    %43 = load %int* %i
    %44 = extractvalue { %code*, [1 x %int] } %42, 0
    %45 = getelementptr inbounds %code* %44, %int %43
    %46 = load %int* %shift
    %47 = shl %int 1, %46
    %48 = load %code* %45
    %49 = bitcast %code %48 to %int
    %50 = icmp sge %int %49, %47
    br %boolean %50, label %_label28, label %_label30
_label30:
    br label %_label27
_label26:
    br label %_label27
_label27:
    br label %_label24
_label24:
    %51 = load %int* %i
    %52 = add %int %51, 1
    store %int %52, %int* %i
    br label %_label20
_label20:
    %53 = load { %code*, [1 x %int] }* %data
    %54 = extractvalue { %code*, [1 x %int] } %53, 1, 0
    %55 = load %int* %i
    %56 = icmp slt %int %55, %54
    br %boolean %56, label %_label19, label %_label21
_label21:
    %57 = load %int* %length
    %58 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cbyte_Mclass"* @"_Pshadow_Pstandard_Cbyte_Mclass", i32 0, i32 0), %int %57)
    %59 = bitcast %"_Pshadow_Pstandard_CObject"* %58 to %byte*
    %60 = insertvalue { %byte*, [1 x %int] } undef, %byte* %59, 0
    %61 = insertvalue { %byte*, [1 x %int] } %60, %int %57, 1, 0
    %62 = load %"_Pshadow_Pstandard_CString"** %this
    %63 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %62, i32 0, i32 1
    store { %byte*, [1 x %int] } %61, { %byte*, [1 x %int] }* %63
    store %int 0, %int* %i1
    store %int 0, %int* %j
    br label %_label32
_label31:
    %64 = load { %code*, [1 x %int] }* %data
    %65 = load %int* %i1
    %66 = extractvalue { %code*, [1 x %int] } %64, 0
    %67 = getelementptr inbounds %code* %66, %int %65
    %68 = shl %int 1, 7
    %69 = load %code* %67
    %70 = bitcast %code %69 to %int
    %71 = icmp slt %int %70, %68
    br %boolean %71, label %_label34, label %_label35
_label34:
    %72 = load { %code*, [1 x %int] }* %data
    %73 = load %int* %i1
    %74 = extractvalue { %code*, [1 x %int] } %72, 0
    %75 = getelementptr inbounds %code* %74, %int %73
    %76 = load %code* %75
    %77 = trunc %code %76 to %byte
    %78 = load %"_Pshadow_Pstandard_CString"** %this
    %79 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %78, i32 0, i32 1
    %80 = load %int* %j
    %81 = add %int %80, 0
    %82 = load { %byte*, [1 x %int] }* %79
    %83 = extractvalue { %byte*, [1 x %int] } %82, 0
    %84 = getelementptr inbounds %byte* %83, %int %81
    store %byte %77, %byte* %84
    %85 = load %int* %j
    %86 = add %int %85, 1
    store %int %86, %int* %j
    br label %_label36
_label35:
    %87 = load { %code*, [1 x %int] }* %data
    %88 = load %int* %i1
    %89 = extractvalue { %code*, [1 x %int] } %87, 0
    %90 = getelementptr inbounds %code* %89, %int %88
    %91 = shl %int 1, 11
    %92 = load %code* %90
    %93 = bitcast %code %92 to %int
    %94 = icmp slt %int %93, %91
    br %boolean %94, label %_label37, label %_label38
_label37:
    %95 = load { %code*, [1 x %int] }* %data
    %96 = load %int* %i1
    %97 = extractvalue { %code*, [1 x %int] } %95, 0
    %98 = getelementptr inbounds %code* %97, %int %96
    %99 = load %code* %98
    %100 = bitcast %code %99 to %int
    %101 = ashr %int %100, 6
    %102 = and %int %101, 31
    %103 = or %int %102, 192
    %104 = trunc %int %103 to %byte
    %105 = load %"_Pshadow_Pstandard_CString"** %this
    %106 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %105, i32 0, i32 1
    %107 = load %int* %j
    %108 = add %int %107, 0
    %109 = load { %byte*, [1 x %int] }* %106
    %110 = extractvalue { %byte*, [1 x %int] } %109, 0
    %111 = getelementptr inbounds %byte* %110, %int %108
    store %byte %104, %byte* %111
    %112 = load { %code*, [1 x %int] }* %data
    %113 = load %int* %i1
    %114 = extractvalue { %code*, [1 x %int] } %112, 0
    %115 = getelementptr inbounds %code* %114, %int %113
    %116 = load %code* %115
    %117 = bitcast %code %116 to %int
    %118 = ashr %int %117, 0
    %119 = and %int %118, 63
    %120 = or %int %119, 128
    %121 = trunc %int %120 to %byte
    %122 = load %"_Pshadow_Pstandard_CString"** %this
    %123 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %122, i32 0, i32 1
    %124 = load %int* %j
    %125 = add %int %124, 1
    %126 = load { %byte*, [1 x %int] }* %123
    %127 = extractvalue { %byte*, [1 x %int] } %126, 0
    %128 = getelementptr inbounds %byte* %127, %int %125
    store %byte %121, %byte* %128
    %129 = load %int* %j
    %130 = add %int %129, 2
    store %int %130, %int* %j
    br label %_label39
_label38:
    %131 = load { %code*, [1 x %int] }* %data
    %132 = load %int* %i1
    %133 = extractvalue { %code*, [1 x %int] } %131, 0
    %134 = getelementptr inbounds %code* %133, %int %132
    %135 = shl %int 1, 16
    %136 = load %code* %134
    %137 = bitcast %code %136 to %int
    %138 = icmp slt %int %137, %135
    br %boolean %138, label %_label40, label %_label41
_label40:
    %139 = load { %code*, [1 x %int] }* %data
    %140 = load %int* %i1
    %141 = extractvalue { %code*, [1 x %int] } %139, 0
    %142 = getelementptr inbounds %code* %141, %int %140
    %143 = load %code* %142
    %144 = bitcast %code %143 to %int
    %145 = ashr %int %144, 12
    %146 = and %int %145, 15
    %147 = or %int %146, 224
    %148 = trunc %int %147 to %byte
    %149 = load %"_Pshadow_Pstandard_CString"** %this
    %150 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %149, i32 0, i32 1
    %151 = load %int* %j
    %152 = add %int %151, 0
    %153 = load { %byte*, [1 x %int] }* %150
    %154 = extractvalue { %byte*, [1 x %int] } %153, 0
    %155 = getelementptr inbounds %byte* %154, %int %152
    store %byte %148, %byte* %155
    %156 = load { %code*, [1 x %int] }* %data
    %157 = load %int* %i1
    %158 = extractvalue { %code*, [1 x %int] } %156, 0
    %159 = getelementptr inbounds %code* %158, %int %157
    %160 = load %code* %159
    %161 = bitcast %code %160 to %int
    %162 = ashr %int %161, 6
    %163 = and %int %162, 63
    %164 = or %int %163, 128
    %165 = trunc %int %164 to %byte
    %166 = load %"_Pshadow_Pstandard_CString"** %this
    %167 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %166, i32 0, i32 1
    %168 = load %int* %j
    %169 = add %int %168, 1
    %170 = load { %byte*, [1 x %int] }* %167
    %171 = extractvalue { %byte*, [1 x %int] } %170, 0
    %172 = getelementptr inbounds %byte* %171, %int %169
    store %byte %165, %byte* %172
    %173 = load { %code*, [1 x %int] }* %data
    %174 = load %int* %i1
    %175 = extractvalue { %code*, [1 x %int] } %173, 0
    %176 = getelementptr inbounds %code* %175, %int %174
    %177 = load %code* %176
    %178 = bitcast %code %177 to %int
    %179 = ashr %int %178, 0
    %180 = and %int %179, 63
    %181 = or %int %180, 128
    %182 = trunc %int %181 to %byte
    %183 = load %"_Pshadow_Pstandard_CString"** %this
    %184 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %183, i32 0, i32 1
    %185 = load %int* %j
    %186 = add %int %185, 2
    %187 = load { %byte*, [1 x %int] }* %184
    %188 = extractvalue { %byte*, [1 x %int] } %187, 0
    %189 = getelementptr inbounds %byte* %188, %int %186
    store %byte %182, %byte* %189
    %190 = load %int* %j
    %191 = add %int %190, 3
    store %int %191, %int* %j
    br label %_label42
_label41:
    %192 = load { %code*, [1 x %int] }* %data
    %193 = load %int* %i1
    %194 = extractvalue { %code*, [1 x %int] } %192, 0
    %195 = getelementptr inbounds %code* %194, %int %193
    %196 = shl %int 1, 21
    %197 = load %code* %195
    %198 = bitcast %code %197 to %int
    %199 = icmp slt %int %198, %196
    br %boolean %199, label %_label43, label %_label44
_label43:
    %200 = load { %code*, [1 x %int] }* %data
    %201 = load %int* %i1
    %202 = extractvalue { %code*, [1 x %int] } %200, 0
    %203 = getelementptr inbounds %code* %202, %int %201
    %204 = load %code* %203
    %205 = bitcast %code %204 to %int
    %206 = ashr %int %205, 18
    %207 = and %int %206, 7
    %208 = or %int %207, 240
    %209 = trunc %int %208 to %byte
    %210 = load %"_Pshadow_Pstandard_CString"** %this
    %211 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %210, i32 0, i32 1
    %212 = load %int* %j
    %213 = add %int %212, 0
    %214 = load { %byte*, [1 x %int] }* %211
    %215 = extractvalue { %byte*, [1 x %int] } %214, 0
    %216 = getelementptr inbounds %byte* %215, %int %213
    store %byte %209, %byte* %216
    %217 = load { %code*, [1 x %int] }* %data
    %218 = load %int* %i1
    %219 = extractvalue { %code*, [1 x %int] } %217, 0
    %220 = getelementptr inbounds %code* %219, %int %218
    %221 = load %code* %220
    %222 = bitcast %code %221 to %int
    %223 = ashr %int %222, 12
    %224 = and %int %223, 63
    %225 = or %int %224, 128
    %226 = trunc %int %225 to %byte
    %227 = load %"_Pshadow_Pstandard_CString"** %this
    %228 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %227, i32 0, i32 1
    %229 = load %int* %j
    %230 = add %int %229, 1
    %231 = load { %byte*, [1 x %int] }* %228
    %232 = extractvalue { %byte*, [1 x %int] } %231, 0
    %233 = getelementptr inbounds %byte* %232, %int %230
    store %byte %226, %byte* %233
    %234 = load { %code*, [1 x %int] }* %data
    %235 = load %int* %i1
    %236 = extractvalue { %code*, [1 x %int] } %234, 0
    %237 = getelementptr inbounds %code* %236, %int %235
    %238 = load %code* %237
    %239 = bitcast %code %238 to %int
    %240 = ashr %int %239, 6
    %241 = and %int %240, 63
    %242 = or %int %241, 128
    %243 = trunc %int %242 to %byte
    %244 = load %"_Pshadow_Pstandard_CString"** %this
    %245 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %244, i32 0, i32 1
    %246 = load %int* %j
    %247 = add %int %246, 2
    %248 = load { %byte*, [1 x %int] }* %245
    %249 = extractvalue { %byte*, [1 x %int] } %248, 0
    %250 = getelementptr inbounds %byte* %249, %int %247
    store %byte %243, %byte* %250
    %251 = load { %code*, [1 x %int] }* %data
    %252 = load %int* %i1
    %253 = extractvalue { %code*, [1 x %int] } %251, 0
    %254 = getelementptr inbounds %code* %253, %int %252
    %255 = load %code* %254
    %256 = bitcast %code %255 to %int
    %257 = ashr %int %256, 0
    %258 = and %int %257, 63
    %259 = or %int %258, 128
    %260 = trunc %int %259 to %byte
    %261 = load %"_Pshadow_Pstandard_CString"** %this
    %262 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %261, i32 0, i32 1
    %263 = load %int* %j
    %264 = add %int %263, 3
    %265 = load { %byte*, [1 x %int] }* %262
    %266 = extractvalue { %byte*, [1 x %int] } %265, 0
    %267 = getelementptr inbounds %byte* %266, %int %264
    store %byte %260, %byte* %267
    %268 = load %int* %j
    %269 = add %int %268, 4
    store %int %269, %int* %j
    br label %_label45
_label44:
    %270 = load { %code*, [1 x %int] }* %data
    %271 = load %int* %i1
    %272 = extractvalue { %code*, [1 x %int] } %270, 0
    %273 = getelementptr inbounds %code* %272, %int %271
    %274 = shl %int 1, 26
    %275 = load %code* %273
    %276 = bitcast %code %275 to %int
    %277 = icmp slt %int %276, %274
    br %boolean %277, label %_label46, label %_label47
_label46:
    %278 = load { %code*, [1 x %int] }* %data
    %279 = load %int* %i1
    %280 = extractvalue { %code*, [1 x %int] } %278, 0
    %281 = getelementptr inbounds %code* %280, %int %279
    %282 = load %code* %281
    %283 = bitcast %code %282 to %int
    %284 = ashr %int %283, 24
    %285 = and %int %284, 3
    %286 = or %int %285, 248
    %287 = trunc %int %286 to %byte
    %288 = load %"_Pshadow_Pstandard_CString"** %this
    %289 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %288, i32 0, i32 1
    %290 = load %int* %j
    %291 = add %int %290, 0
    %292 = load { %byte*, [1 x %int] }* %289
    %293 = extractvalue { %byte*, [1 x %int] } %292, 0
    %294 = getelementptr inbounds %byte* %293, %int %291
    store %byte %287, %byte* %294
    %295 = load { %code*, [1 x %int] }* %data
    %296 = load %int* %i1
    %297 = extractvalue { %code*, [1 x %int] } %295, 0
    %298 = getelementptr inbounds %code* %297, %int %296
    %299 = load %code* %298
    %300 = bitcast %code %299 to %int
    %301 = ashr %int %300, 18
    %302 = and %int %301, 63
    %303 = or %int %302, 128
    %304 = trunc %int %303 to %byte
    %305 = load %"_Pshadow_Pstandard_CString"** %this
    %306 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %305, i32 0, i32 1
    %307 = load %int* %j
    %308 = add %int %307, 1
    %309 = load { %byte*, [1 x %int] }* %306
    %310 = extractvalue { %byte*, [1 x %int] } %309, 0
    %311 = getelementptr inbounds %byte* %310, %int %308
    store %byte %304, %byte* %311
    %312 = load { %code*, [1 x %int] }* %data
    %313 = load %int* %i1
    %314 = extractvalue { %code*, [1 x %int] } %312, 0
    %315 = getelementptr inbounds %code* %314, %int %313
    %316 = load %code* %315
    %317 = bitcast %code %316 to %int
    %318 = ashr %int %317, 12
    %319 = and %int %318, 63
    %320 = or %int %319, 128
    %321 = trunc %int %320 to %byte
    %322 = load %"_Pshadow_Pstandard_CString"** %this
    %323 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %322, i32 0, i32 1
    %324 = load %int* %j
    %325 = add %int %324, 2
    %326 = load { %byte*, [1 x %int] }* %323
    %327 = extractvalue { %byte*, [1 x %int] } %326, 0
    %328 = getelementptr inbounds %byte* %327, %int %325
    store %byte %321, %byte* %328
    %329 = load { %code*, [1 x %int] }* %data
    %330 = load %int* %i1
    %331 = extractvalue { %code*, [1 x %int] } %329, 0
    %332 = getelementptr inbounds %code* %331, %int %330
    %333 = load %code* %332
    %334 = bitcast %code %333 to %int
    %335 = ashr %int %334, 6
    %336 = and %int %335, 63
    %337 = or %int %336, 128
    %338 = trunc %int %337 to %byte
    %339 = load %"_Pshadow_Pstandard_CString"** %this
    %340 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %339, i32 0, i32 1
    %341 = load %int* %j
    %342 = add %int %341, 3
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
    %352 = ashr %int %351, 0
    %353 = and %int %352, 63
    %354 = or %int %353, 128
    %355 = trunc %int %354 to %byte
    %356 = load %"_Pshadow_Pstandard_CString"** %this
    %357 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %356, i32 0, i32 1
    %358 = load %int* %j
    %359 = add %int %358, 4
    %360 = load { %byte*, [1 x %int] }* %357
    %361 = extractvalue { %byte*, [1 x %int] } %360, 0
    %362 = getelementptr inbounds %byte* %361, %int %359
    store %byte %355, %byte* %362
    %363 = load %int* %j
    %364 = add %int %363, 5
    store %int %364, %int* %j
    br label %_label48
_label47:
    %365 = load { %code*, [1 x %int] }* %data
    %366 = load %int* %i1
    %367 = extractvalue { %code*, [1 x %int] } %365, 0
    %368 = getelementptr inbounds %code* %367, %int %366
    %369 = shl %int 1, 31
    %370 = load %code* %368
    %371 = bitcast %code %370 to %int
    %372 = icmp slt %int %371, %369
    br %boolean %372, label %_label49, label %_label50
_label49:
    %373 = load { %code*, [1 x %int] }* %data
    %374 = load %int* %i1
    %375 = extractvalue { %code*, [1 x %int] } %373, 0
    %376 = getelementptr inbounds %code* %375, %int %374
    %377 = load %code* %376
    %378 = bitcast %code %377 to %int
    %379 = ashr %int %378, 30
    %380 = and %int %379, 1
    %381 = or %int %380, 252
    %382 = trunc %int %381 to %byte
    %383 = load %"_Pshadow_Pstandard_CString"** %this
    %384 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %383, i32 0, i32 1
    %385 = load %int* %j
    %386 = add %int %385, 0
    %387 = load { %byte*, [1 x %int] }* %384
    %388 = extractvalue { %byte*, [1 x %int] } %387, 0
    %389 = getelementptr inbounds %byte* %388, %int %386
    store %byte %382, %byte* %389
    %390 = load { %code*, [1 x %int] }* %data
    %391 = load %int* %i1
    %392 = extractvalue { %code*, [1 x %int] } %390, 0
    %393 = getelementptr inbounds %code* %392, %int %391
    %394 = load %code* %393
    %395 = bitcast %code %394 to %int
    %396 = ashr %int %395, 24
    %397 = and %int %396, 63
    %398 = or %int %397, 128
    %399 = trunc %int %398 to %byte
    %400 = load %"_Pshadow_Pstandard_CString"** %this
    %401 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %400, i32 0, i32 1
    %402 = load %int* %j
    %403 = add %int %402, 1
    %404 = load { %byte*, [1 x %int] }* %401
    %405 = extractvalue { %byte*, [1 x %int] } %404, 0
    %406 = getelementptr inbounds %byte* %405, %int %403
    store %byte %399, %byte* %406
    %407 = load { %code*, [1 x %int] }* %data
    %408 = load %int* %i1
    %409 = extractvalue { %code*, [1 x %int] } %407, 0
    %410 = getelementptr inbounds %code* %409, %int %408
    %411 = load %code* %410
    %412 = bitcast %code %411 to %int
    %413 = ashr %int %412, 18
    %414 = and %int %413, 63
    %415 = or %int %414, 128
    %416 = trunc %int %415 to %byte
    %417 = load %"_Pshadow_Pstandard_CString"** %this
    %418 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %417, i32 0, i32 1
    %419 = load %int* %j
    %420 = add %int %419, 2
    %421 = load { %byte*, [1 x %int] }* %418
    %422 = extractvalue { %byte*, [1 x %int] } %421, 0
    %423 = getelementptr inbounds %byte* %422, %int %420
    store %byte %416, %byte* %423
    %424 = load { %code*, [1 x %int] }* %data
    %425 = load %int* %i1
    %426 = extractvalue { %code*, [1 x %int] } %424, 0
    %427 = getelementptr inbounds %code* %426, %int %425
    %428 = load %code* %427
    %429 = bitcast %code %428 to %int
    %430 = ashr %int %429, 12
    %431 = and %int %430, 63
    %432 = or %int %431, 128
    %433 = trunc %int %432 to %byte
    %434 = load %"_Pshadow_Pstandard_CString"** %this
    %435 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %434, i32 0, i32 1
    %436 = load %int* %j
    %437 = add %int %436, 3
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
    %447 = ashr %int %446, 6
    %448 = and %int %447, 63
    %449 = or %int %448, 128
    %450 = trunc %int %449 to %byte
    %451 = load %"_Pshadow_Pstandard_CString"** %this
    %452 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %451, i32 0, i32 1
    %453 = load %int* %j
    %454 = add %int %453, 4
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
    %464 = ashr %int %463, 0
    %465 = and %int %464, 63
    %466 = or %int %465, 128
    %467 = trunc %int %466 to %byte
    %468 = load %"_Pshadow_Pstandard_CString"** %this
    %469 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %468, i32 0, i32 1
    %470 = load %int* %j
    %471 = add %int %470, 5
    %472 = load { %byte*, [1 x %int] }* %469
    %473 = extractvalue { %byte*, [1 x %int] } %472, 0
    %474 = getelementptr inbounds %byte* %473, %int %471
    store %byte %467, %byte* %474
    %475 = load %int* %j
    %476 = add %int %475, 6
    store %int %476, %int* %j
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
    %477 = load %int* %i1
    %478 = add %int %477, 1
    store %int %478, %int* %i1
    br label %_label32
_label32:
    %479 = load { %code*, [1 x %int] }* %data
    %480 = extractvalue { %code*, [1 x %int] } %479, 1, 0
    %481 = load %int* %i1
    %482 = icmp slt %int %481, %480
    br %boolean %482, label %_label31, label %_label33
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
