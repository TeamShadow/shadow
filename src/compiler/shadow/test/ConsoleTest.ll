; shadow.test@ConsoleTest

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

%"_Pshadow_Ptest_CConsoleTest_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Ptest_CConsoleTest"* (%"_Pshadow_Ptest_CConsoleTest"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Ptest_CConsoleTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] })* }
%"_Pshadow_Ptest_CConsoleTest" = type { %"_Pshadow_Ptest_CConsoleTest_Mclass"* }
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

@"_Pshadow_Ptest_CConsoleTest_Mclass" = constant %"_Pshadow_Ptest_CConsoleTest_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 0, %int ptrtoint (%"_Pshadow_Ptest_CConsoleTest"* getelementptr (%"_Pshadow_Ptest_CConsoleTest"* null, i32 1) to i32), %int ptrtoint (%"_Pshadow_Ptest_CConsoleTest"** getelementptr (%"_Pshadow_Ptest_CConsoleTest"** null, i32 1) to i32) }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %"_Pshadow_Ptest_CConsoleTest"* (%"_Pshadow_Ptest_CConsoleTest"*)* @"_Pshadow_Ptest_CConsoleTest_Mcreate", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", void (%"_Pshadow_Ptest_CConsoleTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] })* @"_Pshadow_Ptest_CConsoleTest_Mmain_Pshadow_Pstandard_CString_A1" }
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

define %"_Pshadow_Ptest_CConsoleTest"* @"_Pshadow_Ptest_CConsoleTest_Mcreate"(%"_Pshadow_Ptest_CConsoleTest"*) {
    %this = alloca %"_Pshadow_Ptest_CConsoleTest"*
    store %"_Pshadow_Ptest_CConsoleTest"* %0, %"_Pshadow_Ptest_CConsoleTest"** %this
    %2 = load %"_Pshadow_Ptest_CConsoleTest"** %this
    %3 = bitcast %"_Pshadow_Ptest_CConsoleTest"* %2 to %"_Pshadow_Pstandard_CObject"*
    %4 = call %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %3)
    %5 = getelementptr inbounds %"_Pshadow_Ptest_CConsoleTest"* %0, i32 0, i32 0
    store %"_Pshadow_Ptest_CConsoleTest_Mclass"* @"_Pshadow_Ptest_CConsoleTest_Mclass", %"_Pshadow_Ptest_CConsoleTest_Mclass"** %5
    ret %"_Pshadow_Ptest_CConsoleTest"* %0
}

define void @"_Pshadow_Ptest_CConsoleTest_Mmain_Pshadow_Pstandard_CString_A1"(%"_Pshadow_Ptest_CConsoleTest"*, { %"_Pshadow_Pstandard_CString"**, [1 x %int] }) {
    %this = alloca %"_Pshadow_Ptest_CConsoleTest"*
    %args = alloca { %"_Pshadow_Pstandard_CString"**, [1 x %int] }
    %console = alloca %"_Pshadow_Pio_CConsole"*
    %_temp = alloca %"_Pshadow_Pstandard_CString"*
    %_temp1 = alloca %"_Pshadow_Pstandard_CString"*
    %eof = alloca %boolean
    %c = alloca %code
    store %"_Pshadow_Ptest_CConsoleTest"* %0, %"_Pshadow_Ptest_CConsoleTest"** %this
    store { %"_Pshadow_Pstandard_CString"**, [1 x %int] } %1, { %"_Pshadow_Pstandard_CString"**, [1 x %int] }* %args
    %3 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %4 = bitcast %"_Pshadow_Pio_CConsole"* %3 to %"_Pshadow_Pstandard_CObject"*
    %5 = icmp eq %"_Pshadow_Pstandard_CObject"* %4, null
    br %boolean %5, label %_label0, label %_label1
_label0:
    %6 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %7 = bitcast %"_Pshadow_Pstandard_CObject"* %6 to %"_Pshadow_Pio_CConsole"*
    %8 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %7)
    store %"_Pshadow_Pio_CConsole"* %8, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label1
_label1:
    %9 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    store %"_Pshadow_Pio_CConsole"* %9, %"_Pshadow_Pio_CConsole"** %console
    %10 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %11 = bitcast %"_Pshadow_Pio_CConsole"* %10 to %"_Pshadow_Pstandard_CObject"*
    %12 = icmp eq %"_Pshadow_Pstandard_CObject"* %11, null
    br %boolean %12, label %_label2, label %_label3
_label2:
    %13 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %14 = bitcast %"_Pshadow_Pstandard_CObject"* %13 to %"_Pshadow_Pio_CConsole"*
    %15 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %14)
    store %"_Pshadow_Pio_CConsole"* %15, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label3
_label3:
    %16 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %17 = getelementptr %"_Pshadow_Pio_CConsole"* %16, i32 0, i32 0
    %18 = load %"_Pshadow_Pio_CConsole_Mclass"** %17
    %19 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %18, i32 0, i32 13
    %20 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %19
    %21 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0))
    %22 = bitcast %"_Pshadow_Pstandard_CObject"* %21 to %"_Pshadow_Pstandard_Cint"*
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %22, i32 0, i32 0
    store %"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", %"_Pshadow_Pstandard_Cint_Mclass"** %23
    %24 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %22, i32 0, i32 1
    store %int 12345, %int* %24
    %25 = call %"_Pshadow_Pio_CConsole"* %20(%"_Pshadow_Pio_CConsole"* %16, %"_Pshadow_Pstandard_CObject"* %21)
    %26 = load %"_Pshadow_Pio_CConsole"** %console
    %27 = getelementptr %"_Pshadow_Pio_CConsole"* %26, i32 0, i32 0
    %28 = load %"_Pshadow_Pio_CConsole_Mclass"** %27
    %29 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %28, i32 0, i32 7
    %30 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %29
    %31 = load %"_Pshadow_Pio_CConsole"** %console
    %32 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %33 = bitcast %"_Pshadow_Pstandard_CObject"* %32 to %"_Pshadow_Pstandard_Ccode"*
    %34 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %33, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %34
    %35 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %33, i32 0, i32 1
    store %code 72, %code* %35
    %36 = call %"_Pshadow_Pio_CConsole"* %30(%"_Pshadow_Pio_CConsole"* %31, %"_Pshadow_Pstandard_CObject"* %32)
    %37 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %38 = bitcast %"_Pshadow_Pio_CConsole"* %37 to %"_Pshadow_Pstandard_CObject"*
    %39 = icmp eq %"_Pshadow_Pstandard_CObject"* %38, null
    br %boolean %39, label %_label4, label %_label5
_label4:
    %40 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %41 = bitcast %"_Pshadow_Pstandard_CObject"* %40 to %"_Pshadow_Pio_CConsole"*
    %42 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %41)
    store %"_Pshadow_Pio_CConsole"* %42, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label5
_label5:
    %43 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %44 = getelementptr %"_Pshadow_Pio_CConsole"* %43, i32 0, i32 0
    %45 = load %"_Pshadow_Pio_CConsole_Mclass"** %44
    %46 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %45, i32 0, i32 7
    %47 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %46
    %48 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %49 = bitcast %"_Pshadow_Pstandard_CObject"* %48 to %"_Pshadow_Pstandard_Ccode"*
    %50 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %49, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %50
    %51 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %49, i32 0, i32 1
    store %code 101, %code* %51
    %52 = call %"_Pshadow_Pio_CConsole"* %47(%"_Pshadow_Pio_CConsole"* %43, %"_Pshadow_Pstandard_CObject"* %48)
    %53 = load %"_Pshadow_Pio_CConsole"** %console
    %54 = getelementptr %"_Pshadow_Pio_CConsole"* %53, i32 0, i32 0
    %55 = load %"_Pshadow_Pio_CConsole_Mclass"** %54
    %56 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %55, i32 0, i32 7
    %57 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %56
    %58 = load %"_Pshadow_Pio_CConsole"** %console
    %59 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %60 = bitcast %"_Pshadow_Pstandard_CObject"* %59 to %"_Pshadow_Pstandard_Ccode"*
    %61 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %60, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %61
    %62 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %60, i32 0, i32 1
    store %code 108, %code* %62
    %63 = call %"_Pshadow_Pio_CConsole"* %57(%"_Pshadow_Pio_CConsole"* %58, %"_Pshadow_Pstandard_CObject"* %59)
    %64 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %65 = bitcast %"_Pshadow_Pio_CConsole"* %64 to %"_Pshadow_Pstandard_CObject"*
    %66 = icmp eq %"_Pshadow_Pstandard_CObject"* %65, null
    br %boolean %66, label %_label6, label %_label7
_label6:
    %67 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %68 = bitcast %"_Pshadow_Pstandard_CObject"* %67 to %"_Pshadow_Pio_CConsole"*
    %69 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %68)
    store %"_Pshadow_Pio_CConsole"* %69, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label7
_label7:
    %70 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %71 = getelementptr %"_Pshadow_Pio_CConsole"* %70, i32 0, i32 0
    %72 = load %"_Pshadow_Pio_CConsole_Mclass"** %71
    %73 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %72, i32 0, i32 7
    %74 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %73
    %75 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %76 = bitcast %"_Pshadow_Pstandard_CObject"* %75 to %"_Pshadow_Pstandard_Ccode"*
    %77 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %76, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %77
    %78 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %76, i32 0, i32 1
    store %code 108, %code* %78
    %79 = call %"_Pshadow_Pio_CConsole"* %74(%"_Pshadow_Pio_CConsole"* %70, %"_Pshadow_Pstandard_CObject"* %75)
    %80 = load %"_Pshadow_Pio_CConsole"** %console
    %81 = getelementptr %"_Pshadow_Pio_CConsole"* %80, i32 0, i32 0
    %82 = load %"_Pshadow_Pio_CConsole_Mclass"** %81
    %83 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %82, i32 0, i32 7
    %84 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %83
    %85 = load %"_Pshadow_Pio_CConsole"** %console
    %86 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %87 = bitcast %"_Pshadow_Pstandard_CObject"* %86 to %"_Pshadow_Pstandard_Ccode"*
    %88 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %87, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %88
    %89 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %87, i32 0, i32 1
    store %code 111, %code* %89
    %90 = call %"_Pshadow_Pio_CConsole"* %84(%"_Pshadow_Pio_CConsole"* %85, %"_Pshadow_Pstandard_CObject"* %86)
    %91 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %92 = bitcast %"_Pshadow_Pio_CConsole"* %91 to %"_Pshadow_Pstandard_CObject"*
    %93 = icmp eq %"_Pshadow_Pstandard_CObject"* %92, null
    br %boolean %93, label %_label8, label %_label9
_label8:
    %94 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %95 = bitcast %"_Pshadow_Pstandard_CObject"* %94 to %"_Pshadow_Pio_CConsole"*
    %96 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %95)
    store %"_Pshadow_Pio_CConsole"* %96, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label9
_label9:
    %97 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %98 = getelementptr %"_Pshadow_Pio_CConsole"* %97, i32 0, i32 0
    %99 = load %"_Pshadow_Pio_CConsole_Mclass"** %98
    %100 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %99, i32 0, i32 7
    %101 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %100
    %102 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %103 = bitcast %"_Pshadow_Pstandard_CObject"* %102 to %"_Pshadow_Pstandard_Ccode"*
    %104 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %103, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %104
    %105 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %103, i32 0, i32 1
    store %code 32, %code* %105
    %106 = call %"_Pshadow_Pio_CConsole"* %101(%"_Pshadow_Pio_CConsole"* %97, %"_Pshadow_Pstandard_CObject"* %102)
    %107 = load %"_Pshadow_Pio_CConsole"** %console
    %108 = getelementptr %"_Pshadow_Pio_CConsole"* %107, i32 0, i32 0
    %109 = load %"_Pshadow_Pio_CConsole_Mclass"** %108
    %110 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %109, i32 0, i32 7
    %111 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %110
    %112 = load %"_Pshadow_Pio_CConsole"** %console
    %113 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %114 = bitcast %"_Pshadow_Pstandard_CObject"* %113 to %"_Pshadow_Pstandard_Ccode"*
    %115 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %114, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %115
    %116 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %114, i32 0, i32 1
    store %code 87, %code* %116
    %117 = call %"_Pshadow_Pio_CConsole"* %111(%"_Pshadow_Pio_CConsole"* %112, %"_Pshadow_Pstandard_CObject"* %113)
    %118 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %119 = bitcast %"_Pshadow_Pio_CConsole"* %118 to %"_Pshadow_Pstandard_CObject"*
    %120 = icmp eq %"_Pshadow_Pstandard_CObject"* %119, null
    br %boolean %120, label %_label10, label %_label11
_label10:
    %121 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %122 = bitcast %"_Pshadow_Pstandard_CObject"* %121 to %"_Pshadow_Pio_CConsole"*
    %123 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %122)
    store %"_Pshadow_Pio_CConsole"* %123, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label11
_label11:
    %124 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %125 = getelementptr %"_Pshadow_Pio_CConsole"* %124, i32 0, i32 0
    %126 = load %"_Pshadow_Pio_CConsole_Mclass"** %125
    %127 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %126, i32 0, i32 7
    %128 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %127
    %129 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %130 = bitcast %"_Pshadow_Pstandard_CObject"* %129 to %"_Pshadow_Pstandard_Ccode"*
    %131 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %130, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %131
    %132 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %130, i32 0, i32 1
    store %code 111, %code* %132
    %133 = call %"_Pshadow_Pio_CConsole"* %128(%"_Pshadow_Pio_CConsole"* %124, %"_Pshadow_Pstandard_CObject"* %129)
    %134 = load %"_Pshadow_Pio_CConsole"** %console
    %135 = getelementptr %"_Pshadow_Pio_CConsole"* %134, i32 0, i32 0
    %136 = load %"_Pshadow_Pio_CConsole_Mclass"** %135
    %137 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %136, i32 0, i32 7
    %138 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %137
    %139 = load %"_Pshadow_Pio_CConsole"** %console
    %140 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %141 = bitcast %"_Pshadow_Pstandard_CObject"* %140 to %"_Pshadow_Pstandard_Ccode"*
    %142 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %141, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %142
    %143 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %141, i32 0, i32 1
    store %code 114, %code* %143
    %144 = call %"_Pshadow_Pio_CConsole"* %138(%"_Pshadow_Pio_CConsole"* %139, %"_Pshadow_Pstandard_CObject"* %140)
    %145 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %146 = bitcast %"_Pshadow_Pio_CConsole"* %145 to %"_Pshadow_Pstandard_CObject"*
    %147 = icmp eq %"_Pshadow_Pstandard_CObject"* %146, null
    br %boolean %147, label %_label12, label %_label13
_label12:
    %148 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %149 = bitcast %"_Pshadow_Pstandard_CObject"* %148 to %"_Pshadow_Pio_CConsole"*
    %150 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %149)
    store %"_Pshadow_Pio_CConsole"* %150, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label13
_label13:
    %151 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %152 = getelementptr %"_Pshadow_Pio_CConsole"* %151, i32 0, i32 0
    %153 = load %"_Pshadow_Pio_CConsole_Mclass"** %152
    %154 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %153, i32 0, i32 7
    %155 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %154
    %156 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %157 = bitcast %"_Pshadow_Pstandard_CObject"* %156 to %"_Pshadow_Pstandard_Ccode"*
    %158 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %157, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %158
    %159 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %157, i32 0, i32 1
    store %code 108, %code* %159
    %160 = call %"_Pshadow_Pio_CConsole"* %155(%"_Pshadow_Pio_CConsole"* %151, %"_Pshadow_Pstandard_CObject"* %156)
    %161 = load %"_Pshadow_Pio_CConsole"** %console
    %162 = getelementptr %"_Pshadow_Pio_CConsole"* %161, i32 0, i32 0
    %163 = load %"_Pshadow_Pio_CConsole_Mclass"** %162
    %164 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %163, i32 0, i32 7
    %165 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %164
    %166 = load %"_Pshadow_Pio_CConsole"** %console
    %167 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %168 = bitcast %"_Pshadow_Pstandard_CObject"* %167 to %"_Pshadow_Pstandard_Ccode"*
    %169 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %168, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %169
    %170 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %168, i32 0, i32 1
    store %code 100, %code* %170
    %171 = call %"_Pshadow_Pio_CConsole"* %165(%"_Pshadow_Pio_CConsole"* %166, %"_Pshadow_Pstandard_CObject"* %167)
    %172 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %173 = bitcast %"_Pshadow_Pio_CConsole"* %172 to %"_Pshadow_Pstandard_CObject"*
    %174 = icmp eq %"_Pshadow_Pstandard_CObject"* %173, null
    br %boolean %174, label %_label14, label %_label15
_label14:
    %175 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %176 = bitcast %"_Pshadow_Pstandard_CObject"* %175 to %"_Pshadow_Pio_CConsole"*
    %177 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %176)
    store %"_Pshadow_Pio_CConsole"* %177, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label15
_label15:
    %178 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %179 = getelementptr %"_Pshadow_Pio_CConsole"* %178, i32 0, i32 0
    %180 = load %"_Pshadow_Pio_CConsole_Mclass"** %179
    %181 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %180, i32 0, i32 7
    %182 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %181
    %183 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %184 = bitcast %"_Pshadow_Pstandard_CObject"* %183 to %"_Pshadow_Pstandard_Ccode"*
    %185 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %184, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %185
    %186 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %184, i32 0, i32 1
    store %code 33, %code* %186
    %187 = call %"_Pshadow_Pio_CConsole"* %182(%"_Pshadow_Pio_CConsole"* %178, %"_Pshadow_Pstandard_CObject"* %183)
    %188 = load %"_Pshadow_Pio_CConsole"** %console
    %189 = getelementptr %"_Pshadow_Pio_CConsole"* %188, i32 0, i32 0
    %190 = load %"_Pshadow_Pio_CConsole_Mclass"** %189
    %191 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %190, i32 0, i32 7
    %192 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %191
    %193 = load %"_Pshadow_Pio_CConsole"** %console
    %194 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %195 = bitcast %"_Pshadow_Pstandard_CObject"* %194 to %"_Pshadow_Pstandard_Ccode"*
    %196 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %195, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %196
    %197 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %195, i32 0, i32 1
    store %code 10, %code* %197
    %198 = call %"_Pshadow_Pio_CConsole"* %192(%"_Pshadow_Pio_CConsole"* %193, %"_Pshadow_Pstandard_CObject"* %194)
    %199 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %200 = bitcast %"_Pshadow_Pio_CConsole"* %199 to %"_Pshadow_Pstandard_CObject"*
    %201 = icmp eq %"_Pshadow_Pstandard_CObject"* %200, null
    br %boolean %201, label %_label16, label %_label17
_label16:
    %202 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %203 = bitcast %"_Pshadow_Pstandard_CObject"* %202 to %"_Pshadow_Pio_CConsole"*
    %204 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %203)
    store %"_Pshadow_Pio_CConsole"* %204, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label17
_label17:
    %205 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %206 = getelementptr %"_Pshadow_Pio_CConsole"* %205, i32 0, i32 0
    %207 = load %"_Pshadow_Pio_CConsole_Mclass"** %206
    %208 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %207, i32 0, i32 14
    %209 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)** %208
    %210 = call %"_Pshadow_Pio_CConsole"* %209(%"_Pshadow_Pio_CConsole"* %205)
    %211 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %212 = bitcast %"_Pshadow_Pio_CConsole"* %211 to %"_Pshadow_Pstandard_CObject"*
    %213 = icmp eq %"_Pshadow_Pstandard_CObject"* %212, null
    br %boolean %213, label %_label18, label %_label19
_label18:
    %214 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %215 = bitcast %"_Pshadow_Pstandard_CObject"* %214 to %"_Pshadow_Pio_CConsole"*
    %216 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %215)
    store %"_Pshadow_Pio_CConsole"* %216, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label19
_label19:
    %217 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %218 = getelementptr %"_Pshadow_Pio_CConsole"* %217, i32 0, i32 0
    %219 = load %"_Pshadow_Pio_CConsole_Mclass"** %218
    %220 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %219, i32 0, i32 8
    %221 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %220
    %222 = call %"_Pshadow_Pio_CConsole"* %221(%"_Pshadow_Pio_CConsole"* %217, %"_Pshadow_Pstandard_CString"* @_string1)
    %223 = load %"_Pshadow_Pio_CConsole"** %console
    %224 = getelementptr %"_Pshadow_Pio_CConsole"* %223, i32 0, i32 0
    %225 = load %"_Pshadow_Pio_CConsole_Mclass"** %224
    %226 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %225, i32 0, i32 14
    %227 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)** %226
    %228 = load %"_Pshadow_Pio_CConsole"** %console
    %229 = call %"_Pshadow_Pio_CConsole"* %227(%"_Pshadow_Pio_CConsole"* %228)
    %230 = load %"_Pshadow_Pio_CConsole"** %console
    %231 = getelementptr %"_Pshadow_Pio_CConsole"* %230, i32 0, i32 0
    %232 = load %"_Pshadow_Pio_CConsole_Mclass"** %231
    %233 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %232, i32 0, i32 8
    %234 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %233
    %235 = load %"_Pshadow_Pio_CConsole"** %console
    %236 = call %"_Pshadow_Pio_CConsole"* %234(%"_Pshadow_Pio_CConsole"* %235, %"_Pshadow_Pstandard_CString"* @_string2)
    %237 = load %"_Pshadow_Pio_CConsole"** %console
    %238 = getelementptr %"_Pshadow_Pio_CConsole"* %237, i32 0, i32 0
    %239 = load %"_Pshadow_Pio_CConsole_Mclass"** %238
    %240 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %239, i32 0, i32 13
    %241 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %240
    %242 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %243 = icmp eq %"_Pshadow_Pstandard_CObject"* %242, null
    br %boolean %243, label %_label20, label %_label21
_label20:
    store %"_Pshadow_Pstandard_CString"* @_string4, %"_Pshadow_Pstandard_CString"** %_temp
    br label %_label22
_label21:
    %244 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %245 = getelementptr %"_Pshadow_Pstandard_CObject"* %244, i32 0, i32 0
    %246 = load %"_Pshadow_Pstandard_CObject_Mclass"** %245
    %247 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %246, i32 0, i32 6
    %248 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %247
    %249 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %250 = call %"_Pshadow_Pstandard_CString"* %248(%"_Pshadow_Pstandard_CObject"* %249)
    store %"_Pshadow_Pstandard_CString"* %250, %"_Pshadow_Pstandard_CString"** %_temp
    br label %_label22
_label22:
    %251 = load %"_Pshadow_Pstandard_CString"** %_temp
    %252 = load %"_Pshadow_Pio_CConsole"** %console
    %253 = getelementptr %"_Pshadow_Pio_CConsole"* %252, i32 0, i32 0
    %254 = load %"_Pshadow_Pio_CConsole_Mclass"** %253
    %255 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %254, i32 0, i32 17
    %256 = load { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)** %255
    %257 = load %"_Pshadow_Pio_CConsole"** %console
    %258 = call { %"_Pshadow_Pstandard_CString"*, %boolean } %256(%"_Pshadow_Pio_CConsole"* %257)
    %259 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %258, 0
    %260 = bitcast %"_Pshadow_Pstandard_CString"* %259 to %"_Pshadow_Pstandard_CObject"*
    %261 = icmp eq %"_Pshadow_Pstandard_CObject"* %260, null
    br %boolean %261, label %_label23, label %_label24
_label23:
    store %"_Pshadow_Pstandard_CString"* @_string4, %"_Pshadow_Pstandard_CString"** %_temp1
    br label %_label25
_label24:
    %262 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %258, 0
    %263 = bitcast %"_Pshadow_Pstandard_CString"* %262 to %"_Pshadow_Pstandard_CObject"*
    %264 = getelementptr %"_Pshadow_Pstandard_CObject"* %263, i32 0, i32 0
    %265 = load %"_Pshadow_Pstandard_CObject_Mclass"** %264
    %266 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %265, i32 0, i32 6
    %267 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %266
    %268 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %258, 0
    %269 = bitcast %"_Pshadow_Pstandard_CString"* %268 to %"_Pshadow_Pstandard_CObject"*
    %270 = call %"_Pshadow_Pstandard_CString"* %267(%"_Pshadow_Pstandard_CObject"* %269)
    store %"_Pshadow_Pstandard_CString"* %270, %"_Pshadow_Pstandard_CString"** %_temp1
    br label %_label25
_label25:
    %271 = load %"_Pshadow_Pstandard_CString"** %_temp1
    %272 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %251, %"_Pshadow_Pstandard_CString"* %271)
    %273 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %274 = bitcast %"_Pshadow_Pstandard_CObject"* %273 to %"_Pshadow_Pstandard_Ccode"*
    %275 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %274, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %275
    %276 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %274, i32 0, i32 1
    store %code 33, %code* %276
    %277 = getelementptr %"_Pshadow_Pstandard_CObject"* %273, i32 0, i32 0
    %278 = load %"_Pshadow_Pstandard_CObject_Mclass"** %277
    %279 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %278, i32 0, i32 6
    %280 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %279
    %281 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %282 = bitcast %"_Pshadow_Pstandard_CObject"* %281 to %"_Pshadow_Pstandard_Ccode"*
    %283 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %282, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %283
    %284 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %282, i32 0, i32 1
    store %code 33, %code* %284
    %285 = call %"_Pshadow_Pstandard_CString"* %280(%"_Pshadow_Pstandard_CObject"* %281)
    %286 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %272, %"_Pshadow_Pstandard_CString"* %285)
    %287 = load %"_Pshadow_Pio_CConsole"** %console
    %288 = bitcast %"_Pshadow_Pstandard_CString"* %286 to %"_Pshadow_Pstandard_CObject"*
    %289 = call %"_Pshadow_Pio_CConsole"* %241(%"_Pshadow_Pio_CConsole"* %287, %"_Pshadow_Pstandard_CObject"* %288)
    store %boolean false, %boolean* %eof
    br label %_label26
_label26:
    store %code 0, %code* %c
    %290 = load %"_Pshadow_Pio_CConsole"** %console
    %291 = getelementptr %"_Pshadow_Pio_CConsole"* %290, i32 0, i32 0
    %292 = load %"_Pshadow_Pio_CConsole_Mclass"** %291
    %293 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %292, i32 0, i32 16
    %294 = load { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)** %293
    %295 = load %"_Pshadow_Pio_CConsole"** %console
    %296 = call { %code, %boolean } %294(%"_Pshadow_Pio_CConsole"* %295)
    %297 = extractvalue { %code, %boolean } %296, 0
    store %code %297, %code* %c
    %298 = extractvalue { %code, %boolean } %296, 1
    store %boolean %298, %boolean* %eof
    %299 = load %"_Pshadow_Pio_CConsole"** %console
    %300 = getelementptr %"_Pshadow_Pio_CConsole"* %299, i32 0, i32 0
    %301 = load %"_Pshadow_Pio_CConsole_Mclass"** %300
    %302 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %301, i32 0, i32 13
    %303 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %302
    %304 = load %"_Pshadow_Pio_CConsole"** %console
    %305 = load %code* %c
    %306 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %307 = bitcast %"_Pshadow_Pstandard_CObject"* %306 to %"_Pshadow_Pstandard_Ccode"*
    %308 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %307, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %308
    %309 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %307, i32 0, i32 1
    store %code %305, %code* %309
    %310 = call %"_Pshadow_Pio_CConsole"* %303(%"_Pshadow_Pio_CConsole"* %304, %"_Pshadow_Pstandard_CObject"* %306)
    br label %_label27
_label27:
    %311 = load %boolean* %eof
    %312 = xor %boolean -1, %311
    br %boolean %312, label %_label26, label %_label28
_label28:
    ret void
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

@_array0 = private unnamed_addr constant [23 x %ubyte] c"shadow.test@ConsoleTest"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([23 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 23] }, %boolean true }
@_array1 = private unnamed_addr constant [13 x %ubyte] c"Test String\0d\0a"
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([13 x %byte]* @_array1, i32 0, i32 0), [1 x %int] [%int 13] }, %boolean true }
@_array2 = private unnamed_addr constant [17 x %ubyte] c"Enter your name: "
@_string2 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([17 x %byte]* @_array2, i32 0, i32 0), [1 x %int] [%int 17] }, %boolean true }
@_array3 = private unnamed_addr constant [13 x %ubyte] c"Your name is "
@_string3 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([13 x %byte]* @_array3, i32 0, i32 0), [1 x %int] [%int 13] }, %boolean true }
@_array4 = private unnamed_addr constant [4 x %ubyte] c"null"
@_string4 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([4 x %byte]* @_array4, i32 0, i32 0), [1 x %int] [%int 4] }, %boolean true }
