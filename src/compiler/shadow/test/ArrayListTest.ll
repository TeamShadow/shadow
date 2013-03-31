; shadow.test@ArrayListTest

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

%"_Pshadow_Ptest_CArrayListTest_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Ptest_CArrayListTest"* (%"_Pshadow_Ptest_CArrayListTest"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Ptest_CArrayListTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] })* }
%"_Pshadow_Ptest_CArrayListTest" = type { %"_Pshadow_Ptest_CArrayListTest_Mclass"* }
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
%"_Pshadow_Pio_CConsole_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, { %byte, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pio_CConsole_Mclass"* }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cshort"* (%"_Pshadow_Pstandard_Cshort"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %int (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)*, %short (%"_Pshadow_Pstandard_Cshort"*, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"*, %short }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_Cbyte"* (%"_Pshadow_Pstandard_Cbyte"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*)*, %ubyte (%"_Pshadow_Pstandard_Cbyte"*)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %int (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %byte (%"_Pshadow_Pstandard_Cbyte"*, %byte)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_Cbyte"*, %ubyte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"*, %byte }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %byte*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, { %code*, [1 x %int] })*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int, %int }
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
%"_Pshadow_Putility_CArrayList_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Putility_CArrayList"* (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Putility_CArrayList"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)*, void (%"_Pshadow_Putility_CArrayList"*, %int, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*)*, %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Putility_CArrayList"*)* }
%"_Pshadow_Putility_CArrayList" = type { %"_Pshadow_Putility_CArrayList_Mclass"*, %"_Pshadow_Pstandard_CClass"* }

@"_Pshadow_Ptest_CArrayListTest_Mclass" = constant %"_Pshadow_Ptest_CArrayListTest_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Ptest_CArrayListTest"* getelementptr (%"_Pshadow_Ptest_CArrayListTest"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Ptest_CArrayListTest"** getelementptr (%"_Pshadow_Ptest_CArrayListTest"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Ptest_CArrayListTest"* (%"_Pshadow_Ptest_CArrayListTest"*)* @"_Pshadow_Ptest_CArrayListTest_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", %int (%"_Pshadow_Ptest_CArrayListTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] })* @"_Pshadow_Ptest_CArrayListTest_Mmain_Pshadow_Pstandard_CString_A1" }
@"_Pshadow_Pstandard_CException_Mclass" = external constant %"_Pshadow_Pstandard_CException_Mclass"
@"_Pshadow_Pstandard_Cint_Mclass" = external constant %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external constant %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external constant %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external constant %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external constant %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external constant %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pio_CConsole_Mclass" = external constant %"_Pshadow_Pio_CConsole_Mclass"
@"_Pshadow_Pio_CConsole_Minstance" = external global %"_Pshadow_Pio_CConsole"*
@"_Pshadow_Pstandard_Cshort_Mclass" = external constant %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external constant %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external constant %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external constant %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external constant %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external constant %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external constant %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external constant %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external constant %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external constant %"_Pshadow_Pstandard_CObject_Mclass"
@"_Pshadow_Putility_CArrayList_Mclass" = external constant %"_Pshadow_Putility_CArrayList_Mclass"

define %"_Pshadow_Ptest_CArrayListTest"* @"_Pshadow_Ptest_CArrayListTest_Mcreate"(%"_Pshadow_Ptest_CArrayListTest"*) {
    %this = alloca %"_Pshadow_Ptest_CArrayListTest"*
    store %"_Pshadow_Ptest_CArrayListTest"* %0, %"_Pshadow_Ptest_CArrayListTest"** %this
    %2 = load %"_Pshadow_Ptest_CArrayListTest"** %this
    %3 = bitcast %"_Pshadow_Ptest_CArrayListTest"* %2 to %"_Pshadow_Pstandard_CObject"*
    %4 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %3)
    %5 = getelementptr inbounds %"_Pshadow_Ptest_CArrayListTest"* %0, i32 0, i32 0
    store %"_Pshadow_Ptest_CArrayListTest_Mclass"* @"_Pshadow_Ptest_CArrayListTest_Mclass", %"_Pshadow_Ptest_CArrayListTest_Mclass"** %5
    ret %"_Pshadow_Ptest_CArrayListTest"* %0
}

define %int @"_Pshadow_Ptest_CArrayListTest_Mmain_Pshadow_Pstandard_CString_A1"(%"_Pshadow_Ptest_CArrayListTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] }) {
    %this = alloca %"_Pshadow_Ptest_CArrayListTest"*
    %args = alloca { %"_Pshadow_Pstandard_CString"**, [1 x %int] }
    %list = alloca %"_Pshadow_Putility_CArrayList"*
    %i = alloca %int
    %i1 = alloca %int
    %i2 = alloca %int
    store %"_Pshadow_Ptest_CArrayListTest"* %0, %"_Pshadow_Ptest_CArrayListTest"** %this
    store { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %1, { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %3 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Putility_CArrayList_Mclass"* @"_Pshadow_Putility_CArrayList_Mclass", i32 0, i32 0))
    %4 = bitcast %"_Pshadow_Pstandard_CObject"* %3 to %"_Pshadow_Putility_CArrayList"*
    %5 = call %"_Pshadow_Putility_CArrayList"* @"_Pshadow_Putility_CArrayList_Mcreate"(%"_Pshadow_Putility_CArrayList"* %4, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0))
    store %"_Pshadow_Putility_CArrayList"* %4, %"_Pshadow_Putility_CArrayList"** %list
    store %int 0, %int* %i
    br label %_label1
_label0:
    %6 = load %"_Pshadow_Putility_CArrayList"** %list
    %7 = getelementptr %"_Pshadow_Putility_CArrayList"* %6, i32 0, i32 0
    %8 = load %"_Pshadow_Putility_CArrayList_Mclass"** %7
    %9 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %8, i32 0, i32 7
    %10 = load %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)** %9
    %11 = load { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %12 = load %int* %i
    %13 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %11, 0
    %14 = getelementptr inbounds %"_Pshadow_Pstandard_CString"** %13, %int %12
    %15 = load %"_Pshadow_Putility_CArrayList"** %list
    %16 = load %"_Pshadow_Pstandard_CString"** %14
    %17 = bitcast %"_Pshadow_Pstandard_CString"* %16 to %"_Pshadow_Pstandard_CObject"*
    %18 = call %boolean %10(%"_Pshadow_Putility_CArrayList"* %15, %"_Pshadow_Pstandard_CObject"* %17)
    %19 = load %int* %i
    %20 = add %int %19, 1
    store %int %20, %int* %i
    br label %_label1
_label1:
    %21 = load { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %22 = load %int* %i
    %23 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %21, 1
    %24 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %25 = bitcast %"_Pshadow_Pstandard_CObject"* %24 to [1 x %int]*
    store [1 x %int] %23, [1 x %int]* %25
    %26 = getelementptr inbounds [1 x %int]* %25, i32 0, i32 0
    %27 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %26, 0
    %28 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %21, 0
    %29 = bitcast %"_Pshadow_Pstandard_CString"** %28 to %"_Pshadow_Pstandard_CObject"*
    %30 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %31 = bitcast %"_Pshadow_Pstandard_CObject"* %30 to %"_Pshadow_Pstandard_CArray"*
    %32 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %31, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %27, %"_Pshadow_Pstandard_CObject"* %29)
    %33 = getelementptr %"_Pshadow_Pstandard_CArray"* %32, i32 0, i32 0
    %34 = load %"_Pshadow_Pstandard_CArray_Mclass"** %33
    %35 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %34, i32 0, i32 13
    %36 = load %int (%"_Pshadow_Pstandard_CArray"*)** %35
    %37 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %21, 1
    %38 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %39 = bitcast %"_Pshadow_Pstandard_CObject"* %38 to [1 x %int]*
    store [1 x %int] %37, [1 x %int]* %39
    %40 = getelementptr inbounds [1 x %int]* %39, i32 0, i32 0
    %41 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %40, 0
    %42 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %21, 0
    %43 = bitcast %"_Pshadow_Pstandard_CString"** %42 to %"_Pshadow_Pstandard_CObject"*
    %44 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %45 = bitcast %"_Pshadow_Pstandard_CObject"* %44 to %"_Pshadow_Pstandard_CArray"*
    %46 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %45, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %41, %"_Pshadow_Pstandard_CObject"* %43)
    %47 = call %int %36(%"_Pshadow_Pstandard_CArray"* %46)
    %48 = icmp slt %int %22, %47
    br %boolean %48, label %_label0, label %_label2
_label2:
    store %int 1, %int* %i1
    br label %_label4
_label3:
    %49 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %50 = icmp eq %"_Pshadow_Pio_CConsole"* %49, null
    br %boolean %50, label %_label6, label %_label7
_label6:
    %51 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %52 = bitcast %"_Pshadow_Pstandard_CObject"* %51 to %"_Pshadow_Pio_CConsole"*
    %53 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %52)
    store %"_Pshadow_Pio_CConsole"* %53, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label7
_label7:
    %54 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %55 = getelementptr %"_Pshadow_Pio_CConsole"* %54, i32 0, i32 0
    %56 = load %"_Pshadow_Pio_CConsole_Mclass"** %55
    %57 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %56, i32 0, i32 13
    %58 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %57
    %59 = load %"_Pshadow_Putility_CArrayList"** %list
    %60 = getelementptr %"_Pshadow_Putility_CArrayList"* %59, i32 0, i32 0
    %61 = load %"_Pshadow_Putility_CArrayList_Mclass"** %60
    %62 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %61, i32 0, i32 15
    %63 = load %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)** %62
    %64 = load { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %65 = load %int* %i1
    %66 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %64, 0
    %67 = getelementptr inbounds %"_Pshadow_Pstandard_CString"** %66, %int %65
    %68 = load %"_Pshadow_Putility_CArrayList"** %list
    %69 = load %"_Pshadow_Pstandard_CString"** %67
    %70 = bitcast %"_Pshadow_Pstandard_CString"* %69 to %"_Pshadow_Pstandard_CObject"*
    %71 = call %boolean %63(%"_Pshadow_Putility_CArrayList"* %68, %"_Pshadow_Pstandard_CObject"* %70)
    %72 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cboolean_Mclass"* @"_Pshadow_Pstandard_Cboolean_Mclass", i32 0, i32 0))
    %73 = bitcast %"_Pshadow_Pstandard_CObject"* %72 to %"_Pshadow_Pstandard_Cboolean"*
    %74 = getelementptr inbounds %"_Pshadow_Pstandard_Cboolean"* %73, i32 0, i32 0
    store %"_Pshadow_Pstandard_Cboolean_Mclass"* @"_Pshadow_Pstandard_Cboolean_Mclass", %"_Pshadow_Pstandard_Cboolean_Mclass"** %74
    %75 = getelementptr inbounds %"_Pshadow_Pstandard_Cboolean"* %73, i32 0, i32 1
    store %boolean %71, %boolean* %75
    %76 = call %"_Pshadow_Pio_CConsole"* %58(%"_Pshadow_Pio_CConsole"* %54, %"_Pshadow_Pstandard_CObject"* %72)
    %77 = load %int* %i1
    %78 = add %int %77, 3
    store %int %78, %int* %i1
    br label %_label4
_label4:
    %79 = load { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %80 = load %int* %i1
    %81 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %79, 1
    %82 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %83 = bitcast %"_Pshadow_Pstandard_CObject"* %82 to [1 x %int]*
    store [1 x %int] %81, [1 x %int]* %83
    %84 = getelementptr inbounds [1 x %int]* %83, i32 0, i32 0
    %85 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %84, 0
    %86 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %79, 0
    %87 = bitcast %"_Pshadow_Pstandard_CString"** %86 to %"_Pshadow_Pstandard_CObject"*
    %88 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %89 = bitcast %"_Pshadow_Pstandard_CObject"* %88 to %"_Pshadow_Pstandard_CArray"*
    %90 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %89, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %85, %"_Pshadow_Pstandard_CObject"* %87)
    %91 = getelementptr %"_Pshadow_Pstandard_CArray"* %90, i32 0, i32 0
    %92 = load %"_Pshadow_Pstandard_CArray_Mclass"** %91
    %93 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %92, i32 0, i32 13
    %94 = load %int (%"_Pshadow_Pstandard_CArray"*)** %93
    %95 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %79, 1
    %96 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %int 1)
    %97 = bitcast %"_Pshadow_Pstandard_CObject"* %96 to [1 x %int]*
    store [1 x %int] %95, [1 x %int]* %97
    %98 = getelementptr inbounds [1 x %int]* %97, i32 0, i32 0
    %99 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %98, 0
    %100 = extractvalue { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %79, 0
    %101 = bitcast %"_Pshadow_Pstandard_CString"** %100 to %"_Pshadow_Pstandard_CObject"*
    %102 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", i32 0, i32 0))
    %103 = bitcast %"_Pshadow_Pstandard_CObject"* %102 to %"_Pshadow_Pstandard_CArray"*
    %104 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CArray"* %103, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", i32 0, i32 0), { %int*, [1 x %int] } %99, %"_Pshadow_Pstandard_CObject"* %101)
    %105 = call %int %94(%"_Pshadow_Pstandard_CArray"* %104)
    %106 = icmp slt %int %80, %105
    br %boolean %106, label %_label3, label %_label5
_label5:
    %107 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %108 = icmp eq %"_Pshadow_Pio_CConsole"* %107, null
    br %boolean %108, label %_label8, label %_label9
_label8:
    %109 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %110 = bitcast %"_Pshadow_Pstandard_CObject"* %109 to %"_Pshadow_Pio_CConsole"*
    %111 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %110)
    store %"_Pshadow_Pio_CConsole"* %111, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label9
_label9:
    %112 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %113 = getelementptr %"_Pshadow_Pio_CConsole"* %112, i32 0, i32 0
    %114 = load %"_Pshadow_Pio_CConsole_Mclass"** %113
    %115 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %114, i32 0, i32 13
    %116 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %115
    %117 = load %"_Pshadow_Putility_CArrayList"** %list
    %118 = getelementptr %"_Pshadow_Putility_CArrayList"* %117, i32 0, i32 0
    %119 = load %"_Pshadow_Putility_CArrayList_Mclass"** %118
    %120 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %119, i32 0, i32 15
    %121 = load %boolean (%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)** %120
    %122 = load %"_Pshadow_Putility_CArrayList"** %list
    %123 = bitcast %"_Pshadow_Pstandard_CString"* @_string1 to %"_Pshadow_Pstandard_CObject"*
    %124 = call %boolean %121(%"_Pshadow_Putility_CArrayList"* %122, %"_Pshadow_Pstandard_CObject"* %123)
    %125 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cboolean_Mclass"* @"_Pshadow_Pstandard_Cboolean_Mclass", i32 0, i32 0))
    %126 = bitcast %"_Pshadow_Pstandard_CObject"* %125 to %"_Pshadow_Pstandard_Cboolean"*
    %127 = getelementptr inbounds %"_Pshadow_Pstandard_Cboolean"* %126, i32 0, i32 0
    store %"_Pshadow_Pstandard_Cboolean_Mclass"* @"_Pshadow_Pstandard_Cboolean_Mclass", %"_Pshadow_Pstandard_Cboolean_Mclass"** %127
    %128 = getelementptr inbounds %"_Pshadow_Pstandard_Cboolean"* %126, i32 0, i32 1
    store %boolean %124, %boolean* %128
    %129 = call %"_Pshadow_Pio_CConsole"* %116(%"_Pshadow_Pio_CConsole"* %112, %"_Pshadow_Pstandard_CObject"* %125)
    store %int 0, %int* %i2
    br label %_label11
_label10:
    %130 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %131 = icmp eq %"_Pshadow_Pio_CConsole"* %130, null
    br %boolean %131, label %_label13, label %_label14
_label13:
    %132 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %133 = bitcast %"_Pshadow_Pstandard_CObject"* %132 to %"_Pshadow_Pio_CConsole"*
    %134 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %133)
    store %"_Pshadow_Pio_CConsole"* %134, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label14
_label14:
    %135 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %136 = getelementptr %"_Pshadow_Pio_CConsole"* %135, i32 0, i32 0
    %137 = load %"_Pshadow_Pio_CConsole_Mclass"** %136
    %138 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %137, i32 0, i32 13
    %139 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %138
    %140 = load %"_Pshadow_Putility_CArrayList"** %list
    %141 = getelementptr %"_Pshadow_Putility_CArrayList"* %140, i32 0, i32 0
    %142 = load %"_Pshadow_Putility_CArrayList_Mclass"** %141
    %143 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %142, i32 0, i32 11
    %144 = load %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Putility_CArrayList"*, %int)** %143
    %145 = load %"_Pshadow_Putility_CArrayList"** %list
    %146 = load %int* %i2
    %147 = call %"_Pshadow_Pstandard_CObject"* %144(%"_Pshadow_Putility_CArrayList"* %145, %int %146)
    %148 = bitcast %"_Pshadow_Pstandard_CObject"* %147 to %"_Pshadow_Pstandard_CObject"*
    %149 = call %"_Pshadow_Pio_CConsole"* %139(%"_Pshadow_Pio_CConsole"* %135, %"_Pshadow_Pstandard_CObject"* %148)
    %150 = load %int* %i2
    %151 = add %int %150, 1
    store %int %151, %int* %i2
    br label %_label11
_label11:
    %152 = load %"_Pshadow_Putility_CArrayList"** %list
    %153 = load %int* %i2
    %154 = getelementptr %"_Pshadow_Putility_CArrayList"* %152, i32 0, i32 0
    %155 = load %"_Pshadow_Putility_CArrayList_Mclass"** %154
    %156 = getelementptr %"_Pshadow_Putility_CArrayList_Mclass"* %155, i32 0, i32 16
    %157 = load %int (%"_Pshadow_Putility_CArrayList"*)** %156
    %158 = call %int %157(%"_Pshadow_Putility_CArrayList"* %152)
    %159 = icmp slt %int %153, %158
    br %boolean %159, label %_label10, label %_label12
_label12:
    %160 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %161 = icmp eq %"_Pshadow_Pio_CConsole"* %160, null
    br %boolean %161, label %_label15, label %_label16
_label15:
    %162 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %163 = bitcast %"_Pshadow_Pstandard_CObject"* %162 to %"_Pshadow_Pio_CConsole"*
    %164 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %163)
    store %"_Pshadow_Pio_CConsole"* %164, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label16
_label16:
    %165 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %166 = getelementptr %"_Pshadow_Pio_CConsole"* %165, i32 0, i32 0
    %167 = load %"_Pshadow_Pio_CConsole_Mclass"** %166
    %168 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %167, i32 0, i32 13
    %169 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %168
    %170 = load %"_Pshadow_Putility_CArrayList"** %list
    %171 = bitcast %"_Pshadow_Putility_CArrayList"* %170 to %"_Pshadow_Pstandard_CObject"*
    %172 = call %"_Pshadow_Pio_CConsole"* %169(%"_Pshadow_Pio_CConsole"* %165, %"_Pshadow_Pstandard_CObject"* %171)
    ret %int 0
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

declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)
declare { %"_Pshadow_Pstandard_CString"*, %boolean } @"_Pshadow_Pio_CConsole_MreadLine"(%"_Pshadow_Pio_CConsole"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintLine_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintLine"(%"_Pshadow_Pio_CConsole"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_MprintErrorLine"(%"_Pshadow_Pio_CConsole"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"*)
declare { %byte, %boolean } @"_Pshadow_Pio_CConsole_MreadByte"(%"_Pshadow_Pio_CConsole"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mprint_Pshadow_Pstandard_CString"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)
declare { %code, %boolean } @"_Pshadow_Pio_CConsole_MreadCode"(%"_Pshadow_Pio_CConsole"*)

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

declare void @"_Pshadow_Putility_CArrayList_Mclear"(%"_Pshadow_Putility_CArrayList"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CArrayList"*, %int)
declare void @"_Pshadow_Putility_CArrayList_Mindex_Pshadow_Pstandard_Cint_CV"(%"_Pshadow_Putility_CArrayList"*, %int, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Miterator"(%"_Pshadow_Putility_CArrayList"*)
declare %boolean @"_Pshadow_Putility_CArrayList_Mremove_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Putility_CArrayList_Mdelete_Pshadow_Pstandard_Cint"(%"_Pshadow_Putility_CArrayList"*, %int)
declare %boolean @"_Pshadow_Putility_CArrayList_Mcontains_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)
declare %int @"_Pshadow_Putility_CArrayList_MindexOf_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Putility_CArrayList_Madd_CV"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Putility_CArrayList"* @"_Pshadow_Putility_CArrayList_Mcreate"(%"_Pshadow_Putility_CArrayList"*, %"_Pshadow_Pstandard_CClass"*)
declare %int @"_Pshadow_Putility_CArrayList_Msize"(%"_Pshadow_Putility_CArrayList"*)

@_array0 = private unnamed_addr constant [25 x %ubyte] c"shadow.test@ArrayListTest"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([25 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 25] }, %boolean true }
@_array1 = private unnamed_addr constant [0 x %ubyte] c""
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([0 x %byte]* @_array1, i32 0, i32 0), [1 x %int] [%int 0] }, %boolean true }
