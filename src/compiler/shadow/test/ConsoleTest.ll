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
    %4 = icmp eq %"_Pshadow_Pio_CConsole"* %3, null
    br %boolean %4, label %_label0, label %_label1
_label0:
    %5 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %6 = bitcast %"_Pshadow_Pstandard_CObject"* %5 to %"_Pshadow_Pio_CConsole"*
    %7 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %6)
    store %"_Pshadow_Pio_CConsole"* %7, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label1
_label1:
    %8 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    store %"_Pshadow_Pio_CConsole"* %8, %"_Pshadow_Pio_CConsole"** %console
    %9 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %10 = icmp eq %"_Pshadow_Pio_CConsole"* %9, null
    br %boolean %10, label %_label2, label %_label3
_label2:
    %11 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %12 = bitcast %"_Pshadow_Pstandard_CObject"* %11 to %"_Pshadow_Pio_CConsole"*
    %13 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %12)
    store %"_Pshadow_Pio_CConsole"* %13, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label3
_label3:
    %14 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %15 = getelementptr %"_Pshadow_Pio_CConsole"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pio_CConsole_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %16, i32 0, i32 13
    %18 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %17
    %19 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0))
    %20 = bitcast %"_Pshadow_Pstandard_CObject"* %19 to %"_Pshadow_Pstandard_Cint"*
    %21 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %20, i32 0, i32 0
    store %"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", %"_Pshadow_Pstandard_Cint_Mclass"** %21
    %22 = getelementptr inbounds %"_Pshadow_Pstandard_Cint"* %20, i32 0, i32 1
    store %int 12345, %int* %22
    %23 = call %"_Pshadow_Pio_CConsole"* %18(%"_Pshadow_Pio_CConsole"* %14, %"_Pshadow_Pstandard_CObject"* %19)
    %24 = load %"_Pshadow_Pio_CConsole"** %console
    %25 = getelementptr %"_Pshadow_Pio_CConsole"* %24, i32 0, i32 0
    %26 = load %"_Pshadow_Pio_CConsole_Mclass"** %25
    %27 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %26, i32 0, i32 7
    %28 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %27
    %29 = load %"_Pshadow_Pio_CConsole"** %console
    %30 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %31 = bitcast %"_Pshadow_Pstandard_CObject"* %30 to %"_Pshadow_Pstandard_Ccode"*
    %32 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %31, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %32
    %33 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %31, i32 0, i32 1
    store %code 72, %code* %33
    %34 = call %"_Pshadow_Pio_CConsole"* %28(%"_Pshadow_Pio_CConsole"* %29, %"_Pshadow_Pstandard_CObject"* %30)
    %35 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %36 = icmp eq %"_Pshadow_Pio_CConsole"* %35, null
    br %boolean %36, label %_label4, label %_label5
_label4:
    %37 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %38 = bitcast %"_Pshadow_Pstandard_CObject"* %37 to %"_Pshadow_Pio_CConsole"*
    %39 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %38)
    store %"_Pshadow_Pio_CConsole"* %39, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label5
_label5:
    %40 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %41 = getelementptr %"_Pshadow_Pio_CConsole"* %40, i32 0, i32 0
    %42 = load %"_Pshadow_Pio_CConsole_Mclass"** %41
    %43 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %42, i32 0, i32 7
    %44 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %43
    %45 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %46 = bitcast %"_Pshadow_Pstandard_CObject"* %45 to %"_Pshadow_Pstandard_Ccode"*
    %47 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %46, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %47
    %48 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %46, i32 0, i32 1
    store %code 101, %code* %48
    %49 = call %"_Pshadow_Pio_CConsole"* %44(%"_Pshadow_Pio_CConsole"* %40, %"_Pshadow_Pstandard_CObject"* %45)
    %50 = load %"_Pshadow_Pio_CConsole"** %console
    %51 = getelementptr %"_Pshadow_Pio_CConsole"* %50, i32 0, i32 0
    %52 = load %"_Pshadow_Pio_CConsole_Mclass"** %51
    %53 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %52, i32 0, i32 7
    %54 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %53
    %55 = load %"_Pshadow_Pio_CConsole"** %console
    %56 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %57 = bitcast %"_Pshadow_Pstandard_CObject"* %56 to %"_Pshadow_Pstandard_Ccode"*
    %58 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %57, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %58
    %59 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %57, i32 0, i32 1
    store %code 108, %code* %59
    %60 = call %"_Pshadow_Pio_CConsole"* %54(%"_Pshadow_Pio_CConsole"* %55, %"_Pshadow_Pstandard_CObject"* %56)
    %61 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %62 = icmp eq %"_Pshadow_Pio_CConsole"* %61, null
    br %boolean %62, label %_label6, label %_label7
_label6:
    %63 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %64 = bitcast %"_Pshadow_Pstandard_CObject"* %63 to %"_Pshadow_Pio_CConsole"*
    %65 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %64)
    store %"_Pshadow_Pio_CConsole"* %65, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label7
_label7:
    %66 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %67 = getelementptr %"_Pshadow_Pio_CConsole"* %66, i32 0, i32 0
    %68 = load %"_Pshadow_Pio_CConsole_Mclass"** %67
    %69 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %68, i32 0, i32 7
    %70 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %69
    %71 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %72 = bitcast %"_Pshadow_Pstandard_CObject"* %71 to %"_Pshadow_Pstandard_Ccode"*
    %73 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %72, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %73
    %74 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %72, i32 0, i32 1
    store %code 108, %code* %74
    %75 = call %"_Pshadow_Pio_CConsole"* %70(%"_Pshadow_Pio_CConsole"* %66, %"_Pshadow_Pstandard_CObject"* %71)
    %76 = load %"_Pshadow_Pio_CConsole"** %console
    %77 = getelementptr %"_Pshadow_Pio_CConsole"* %76, i32 0, i32 0
    %78 = load %"_Pshadow_Pio_CConsole_Mclass"** %77
    %79 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %78, i32 0, i32 7
    %80 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %79
    %81 = load %"_Pshadow_Pio_CConsole"** %console
    %82 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %83 = bitcast %"_Pshadow_Pstandard_CObject"* %82 to %"_Pshadow_Pstandard_Ccode"*
    %84 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %83, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %84
    %85 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %83, i32 0, i32 1
    store %code 111, %code* %85
    %86 = call %"_Pshadow_Pio_CConsole"* %80(%"_Pshadow_Pio_CConsole"* %81, %"_Pshadow_Pstandard_CObject"* %82)
    %87 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %88 = icmp eq %"_Pshadow_Pio_CConsole"* %87, null
    br %boolean %88, label %_label8, label %_label9
_label8:
    %89 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %90 = bitcast %"_Pshadow_Pstandard_CObject"* %89 to %"_Pshadow_Pio_CConsole"*
    %91 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %90)
    store %"_Pshadow_Pio_CConsole"* %91, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label9
_label9:
    %92 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %93 = getelementptr %"_Pshadow_Pio_CConsole"* %92, i32 0, i32 0
    %94 = load %"_Pshadow_Pio_CConsole_Mclass"** %93
    %95 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %94, i32 0, i32 7
    %96 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %95
    %97 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %98 = bitcast %"_Pshadow_Pstandard_CObject"* %97 to %"_Pshadow_Pstandard_Ccode"*
    %99 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %98, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %99
    %100 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %98, i32 0, i32 1
    store %code 32, %code* %100
    %101 = call %"_Pshadow_Pio_CConsole"* %96(%"_Pshadow_Pio_CConsole"* %92, %"_Pshadow_Pstandard_CObject"* %97)
    %102 = load %"_Pshadow_Pio_CConsole"** %console
    %103 = getelementptr %"_Pshadow_Pio_CConsole"* %102, i32 0, i32 0
    %104 = load %"_Pshadow_Pio_CConsole_Mclass"** %103
    %105 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %104, i32 0, i32 7
    %106 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %105
    %107 = load %"_Pshadow_Pio_CConsole"** %console
    %108 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %109 = bitcast %"_Pshadow_Pstandard_CObject"* %108 to %"_Pshadow_Pstandard_Ccode"*
    %110 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %109, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %110
    %111 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %109, i32 0, i32 1
    store %code 87, %code* %111
    %112 = call %"_Pshadow_Pio_CConsole"* %106(%"_Pshadow_Pio_CConsole"* %107, %"_Pshadow_Pstandard_CObject"* %108)
    %113 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %114 = icmp eq %"_Pshadow_Pio_CConsole"* %113, null
    br %boolean %114, label %_label10, label %_label11
_label10:
    %115 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %116 = bitcast %"_Pshadow_Pstandard_CObject"* %115 to %"_Pshadow_Pio_CConsole"*
    %117 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %116)
    store %"_Pshadow_Pio_CConsole"* %117, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label11
_label11:
    %118 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %119 = getelementptr %"_Pshadow_Pio_CConsole"* %118, i32 0, i32 0
    %120 = load %"_Pshadow_Pio_CConsole_Mclass"** %119
    %121 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %120, i32 0, i32 7
    %122 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %121
    %123 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %124 = bitcast %"_Pshadow_Pstandard_CObject"* %123 to %"_Pshadow_Pstandard_Ccode"*
    %125 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %124, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %125
    %126 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %124, i32 0, i32 1
    store %code 111, %code* %126
    %127 = call %"_Pshadow_Pio_CConsole"* %122(%"_Pshadow_Pio_CConsole"* %118, %"_Pshadow_Pstandard_CObject"* %123)
    %128 = load %"_Pshadow_Pio_CConsole"** %console
    %129 = getelementptr %"_Pshadow_Pio_CConsole"* %128, i32 0, i32 0
    %130 = load %"_Pshadow_Pio_CConsole_Mclass"** %129
    %131 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %130, i32 0, i32 7
    %132 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %131
    %133 = load %"_Pshadow_Pio_CConsole"** %console
    %134 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %135 = bitcast %"_Pshadow_Pstandard_CObject"* %134 to %"_Pshadow_Pstandard_Ccode"*
    %136 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %135, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %136
    %137 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %135, i32 0, i32 1
    store %code 114, %code* %137
    %138 = call %"_Pshadow_Pio_CConsole"* %132(%"_Pshadow_Pio_CConsole"* %133, %"_Pshadow_Pstandard_CObject"* %134)
    %139 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %140 = icmp eq %"_Pshadow_Pio_CConsole"* %139, null
    br %boolean %140, label %_label12, label %_label13
_label12:
    %141 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %142 = bitcast %"_Pshadow_Pstandard_CObject"* %141 to %"_Pshadow_Pio_CConsole"*
    %143 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %142)
    store %"_Pshadow_Pio_CConsole"* %143, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label13
_label13:
    %144 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %145 = getelementptr %"_Pshadow_Pio_CConsole"* %144, i32 0, i32 0
    %146 = load %"_Pshadow_Pio_CConsole_Mclass"** %145
    %147 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %146, i32 0, i32 7
    %148 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %147
    %149 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %150 = bitcast %"_Pshadow_Pstandard_CObject"* %149 to %"_Pshadow_Pstandard_Ccode"*
    %151 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %150, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %151
    %152 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %150, i32 0, i32 1
    store %code 108, %code* %152
    %153 = call %"_Pshadow_Pio_CConsole"* %148(%"_Pshadow_Pio_CConsole"* %144, %"_Pshadow_Pstandard_CObject"* %149)
    %154 = load %"_Pshadow_Pio_CConsole"** %console
    %155 = getelementptr %"_Pshadow_Pio_CConsole"* %154, i32 0, i32 0
    %156 = load %"_Pshadow_Pio_CConsole_Mclass"** %155
    %157 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %156, i32 0, i32 7
    %158 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %157
    %159 = load %"_Pshadow_Pio_CConsole"** %console
    %160 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %161 = bitcast %"_Pshadow_Pstandard_CObject"* %160 to %"_Pshadow_Pstandard_Ccode"*
    %162 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %161, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %162
    %163 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %161, i32 0, i32 1
    store %code 100, %code* %163
    %164 = call %"_Pshadow_Pio_CConsole"* %158(%"_Pshadow_Pio_CConsole"* %159, %"_Pshadow_Pstandard_CObject"* %160)
    %165 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %166 = icmp eq %"_Pshadow_Pio_CConsole"* %165, null
    br %boolean %166, label %_label14, label %_label15
_label14:
    %167 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %168 = bitcast %"_Pshadow_Pstandard_CObject"* %167 to %"_Pshadow_Pio_CConsole"*
    %169 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %168)
    store %"_Pshadow_Pio_CConsole"* %169, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label15
_label15:
    %170 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %171 = getelementptr %"_Pshadow_Pio_CConsole"* %170, i32 0, i32 0
    %172 = load %"_Pshadow_Pio_CConsole_Mclass"** %171
    %173 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %172, i32 0, i32 7
    %174 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %173
    %175 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %176 = bitcast %"_Pshadow_Pstandard_CObject"* %175 to %"_Pshadow_Pstandard_Ccode"*
    %177 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %176, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %177
    %178 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %176, i32 0, i32 1
    store %code 33, %code* %178
    %179 = call %"_Pshadow_Pio_CConsole"* %174(%"_Pshadow_Pio_CConsole"* %170, %"_Pshadow_Pstandard_CObject"* %175)
    %180 = load %"_Pshadow_Pio_CConsole"** %console
    %181 = getelementptr %"_Pshadow_Pio_CConsole"* %180, i32 0, i32 0
    %182 = load %"_Pshadow_Pio_CConsole_Mclass"** %181
    %183 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %182, i32 0, i32 7
    %184 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %183
    %185 = load %"_Pshadow_Pio_CConsole"** %console
    %186 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %187 = bitcast %"_Pshadow_Pstandard_CObject"* %186 to %"_Pshadow_Pstandard_Ccode"*
    %188 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %187, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %188
    %189 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %187, i32 0, i32 1
    store %code 10, %code* %189
    %190 = call %"_Pshadow_Pio_CConsole"* %184(%"_Pshadow_Pio_CConsole"* %185, %"_Pshadow_Pstandard_CObject"* %186)
    %191 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %192 = icmp eq %"_Pshadow_Pio_CConsole"* %191, null
    br %boolean %192, label %_label16, label %_label17
_label16:
    %193 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %194 = bitcast %"_Pshadow_Pstandard_CObject"* %193 to %"_Pshadow_Pio_CConsole"*
    %195 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %194)
    store %"_Pshadow_Pio_CConsole"* %195, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label17
_label17:
    %196 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %197 = getelementptr %"_Pshadow_Pio_CConsole"* %196, i32 0, i32 0
    %198 = load %"_Pshadow_Pio_CConsole_Mclass"** %197
    %199 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %198, i32 0, i32 14
    %200 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)** %199
    %201 = call %"_Pshadow_Pio_CConsole"* %200(%"_Pshadow_Pio_CConsole"* %196)
    %202 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %203 = icmp eq %"_Pshadow_Pio_CConsole"* %202, null
    br %boolean %203, label %_label18, label %_label19
_label18:
    %204 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %205 = bitcast %"_Pshadow_Pstandard_CObject"* %204 to %"_Pshadow_Pio_CConsole"*
    %206 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %205)
    store %"_Pshadow_Pio_CConsole"* %206, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label19
_label19:
    %207 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %208 = getelementptr %"_Pshadow_Pio_CConsole"* %207, i32 0, i32 0
    %209 = load %"_Pshadow_Pio_CConsole_Mclass"** %208
    %210 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %209, i32 0, i32 8
    %211 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %210
    %212 = call %"_Pshadow_Pio_CConsole"* %211(%"_Pshadow_Pio_CConsole"* %207, %"_Pshadow_Pstandard_CString"* @_string1)
    %213 = load %"_Pshadow_Pio_CConsole"** %console
    %214 = getelementptr %"_Pshadow_Pio_CConsole"* %213, i32 0, i32 0
    %215 = load %"_Pshadow_Pio_CConsole_Mclass"** %214
    %216 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %215, i32 0, i32 14
    %217 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)** %216
    %218 = load %"_Pshadow_Pio_CConsole"** %console
    %219 = call %"_Pshadow_Pio_CConsole"* %217(%"_Pshadow_Pio_CConsole"* %218)
    %220 = load %"_Pshadow_Pio_CConsole"** %console
    %221 = getelementptr %"_Pshadow_Pio_CConsole"* %220, i32 0, i32 0
    %222 = load %"_Pshadow_Pio_CConsole_Mclass"** %221
    %223 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %222, i32 0, i32 8
    %224 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %223
    %225 = load %"_Pshadow_Pio_CConsole"** %console
    %226 = call %"_Pshadow_Pio_CConsole"* %224(%"_Pshadow_Pio_CConsole"* %225, %"_Pshadow_Pstandard_CString"* @_string2)
    %227 = load %"_Pshadow_Pio_CConsole"** %console
    %228 = getelementptr %"_Pshadow_Pio_CConsole"* %227, i32 0, i32 0
    %229 = load %"_Pshadow_Pio_CConsole_Mclass"** %228
    %230 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %229, i32 0, i32 13
    %231 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %230
    %232 = icmp eq %"_Pshadow_Pstandard_CString"* @_string3, null
    br %boolean %232, label %_label20, label %_label21
_label20:
    store %"_Pshadow_Pstandard_CString"* @_string4, %"_Pshadow_Pstandard_CString"** %_temp
    br label %_label22
_label21:
    %233 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %234 = getelementptr %"_Pshadow_Pstandard_CObject"* %233, i32 0, i32 0
    %235 = load %"_Pshadow_Pstandard_CObject_Mclass"** %234
    %236 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %235, i32 0, i32 6
    %237 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %236
    %238 = bitcast %"_Pshadow_Pstandard_CString"* @_string3 to %"_Pshadow_Pstandard_CObject"*
    %239 = call %"_Pshadow_Pstandard_CString"* %237(%"_Pshadow_Pstandard_CObject"* %238)
    store %"_Pshadow_Pstandard_CString"* %239, %"_Pshadow_Pstandard_CString"** %_temp
    br label %_label22
_label22:
    %240 = load %"_Pshadow_Pstandard_CString"** %_temp
    %241 = load %"_Pshadow_Pio_CConsole"** %console
    %242 = getelementptr %"_Pshadow_Pio_CConsole"* %241, i32 0, i32 0
    %243 = load %"_Pshadow_Pio_CConsole_Mclass"** %242
    %244 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %243, i32 0, i32 17
    %245 = load { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)** %244
    %246 = load %"_Pshadow_Pio_CConsole"** %console
    %247 = call { %"_Pshadow_Pstandard_CString"*, %boolean } %245(%"_Pshadow_Pio_CConsole"* %246)
    %248 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %247, 0
    %249 = bitcast %"_Pshadow_Pstandard_CString"* %248 to %"_Pshadow_Pstandard_CObject"*
    %250 = icmp eq %"_Pshadow_Pstandard_CObject"* %249, null
    br %boolean %250, label %_label23, label %_label24
_label23:
    store %"_Pshadow_Pstandard_CString"* @_string4, %"_Pshadow_Pstandard_CString"** %_temp1
    br label %_label25
_label24:
    %251 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %247, 0
    %252 = bitcast %"_Pshadow_Pstandard_CString"* %251 to %"_Pshadow_Pstandard_CObject"*
    %253 = getelementptr %"_Pshadow_Pstandard_CObject"* %252, i32 0, i32 0
    %254 = load %"_Pshadow_Pstandard_CObject_Mclass"** %253
    %255 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %254, i32 0, i32 6
    %256 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %255
    %257 = extractvalue { %"_Pshadow_Pstandard_CString"*, %boolean } %247, 0
    %258 = bitcast %"_Pshadow_Pstandard_CString"* %257 to %"_Pshadow_Pstandard_CObject"*
    %259 = call %"_Pshadow_Pstandard_CString"* %256(%"_Pshadow_Pstandard_CObject"* %258)
    store %"_Pshadow_Pstandard_CString"* %259, %"_Pshadow_Pstandard_CString"** %_temp1
    br label %_label25
_label25:
    %260 = load %"_Pshadow_Pstandard_CString"** %_temp1
    %261 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %240, %"_Pshadow_Pstandard_CString"* %260)
    %262 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %263 = bitcast %"_Pshadow_Pstandard_CObject"* %262 to %"_Pshadow_Pstandard_Ccode"*
    %264 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %263, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %264
    %265 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %263, i32 0, i32 1
    store %code 33, %code* %265
    %266 = getelementptr %"_Pshadow_Pstandard_CObject"* %262, i32 0, i32 0
    %267 = load %"_Pshadow_Pstandard_CObject_Mclass"** %266
    %268 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %267, i32 0, i32 6
    %269 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %268
    %270 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %271 = bitcast %"_Pshadow_Pstandard_CObject"* %270 to %"_Pshadow_Pstandard_Ccode"*
    %272 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %271, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %272
    %273 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %271, i32 0, i32 1
    store %code 33, %code* %273
    %274 = call %"_Pshadow_Pstandard_CString"* %269(%"_Pshadow_Pstandard_CObject"* %270)
    %275 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mconcatenate_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"* %261, %"_Pshadow_Pstandard_CString"* %274)
    %276 = load %"_Pshadow_Pio_CConsole"** %console
    %277 = bitcast %"_Pshadow_Pstandard_CString"* %275 to %"_Pshadow_Pstandard_CObject"*
    %278 = call %"_Pshadow_Pio_CConsole"* %231(%"_Pshadow_Pio_CConsole"* %276, %"_Pshadow_Pstandard_CObject"* %277)
    store %boolean false, %boolean* %eof
    br label %_label26
_label26:
    store %code 0, %code* %c
    %279 = load %"_Pshadow_Pio_CConsole"** %console
    %280 = getelementptr %"_Pshadow_Pio_CConsole"* %279, i32 0, i32 0
    %281 = load %"_Pshadow_Pio_CConsole_Mclass"** %280
    %282 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %281, i32 0, i32 16
    %283 = load { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)** %282
    %284 = load %"_Pshadow_Pio_CConsole"** %console
    %285 = call { %code, %boolean } %283(%"_Pshadow_Pio_CConsole"* %284)
    %286 = extractvalue { %code, %boolean } %285, 0
    store %code %286, %code* %c
    %287 = extractvalue { %code, %boolean } %285, 1
    store %boolean %287, %boolean* %eof
    %288 = load %"_Pshadow_Pio_CConsole"** %console
    %289 = getelementptr %"_Pshadow_Pio_CConsole"* %288, i32 0, i32 0
    %290 = load %"_Pshadow_Pio_CConsole_Mclass"** %289
    %291 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %290, i32 0, i32 13
    %292 = load %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %291
    %293 = load %"_Pshadow_Pio_CConsole"** %console
    %294 = load %code* %c
    %295 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", i32 0, i32 0))
    %296 = bitcast %"_Pshadow_Pstandard_CObject"* %295 to %"_Pshadow_Pstandard_Ccode"*
    %297 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %296, i32 0, i32 0
    store %"_Pshadow_Pstandard_Ccode_Mclass"* @"_Pshadow_Pstandard_Ccode_Mclass", %"_Pshadow_Pstandard_Ccode_Mclass"** %297
    %298 = getelementptr inbounds %"_Pshadow_Pstandard_Ccode"* %296, i32 0, i32 1
    store %code %294, %code* %298
    %299 = call %"_Pshadow_Pio_CConsole"* %292(%"_Pshadow_Pio_CConsole"* %293, %"_Pshadow_Pstandard_CObject"* %295)
    br label %_label27
_label27:
    %300 = load %boolean* %eof
    %301 = xor %boolean -1, %300
    br %boolean %301, label %_label26, label %_label28
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
