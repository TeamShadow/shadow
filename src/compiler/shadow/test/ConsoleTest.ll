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

declare noalias i8* @malloc(i32)
declare noalias i8* @calloc(i32, i32)

%"_Pshadow_Ptest_CConsoleTest_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Ptest_CConsoleTest"*)* }
%"_Pshadow_Ptest_CConsoleTest" = type { %"_Pshadow_Ptest_CConsoleTest_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %uint (%int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"* }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%ushort, %ushort)*, %int (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"* }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"* }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %double (%double, %double)*, %int (%double, %double)*, %double (%double, %double)*, %double (%double, %double)*, %double (%double, %double)*, %double (%double, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"* }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %long (%long, %long)*, %int (%long, %long)*, %long (%long, %long)*, %long (%long, %long)*, %long (%long, %long)*, %long (%long, %long)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"* }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%float, %float)*, %int (%float, %float)*, %float (%float, %float)*, %float (%float, %float)*, %float (%float, %float)*, %float (%float, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"* }
%"_Pshadow_Pio_CConsole_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %boolean)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, void (%"_Pshadow_Pio_CConsole"*, %code)*, void (%"_Pshadow_Pio_CConsole"*, %long)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, void (%"_Pshadow_Pio_CConsole"*, %boolean)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, void (%"_Pshadow_Pio_CConsole"*, %code)*, void (%"_Pshadow_Pio_CConsole"*, %long)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pio_CConsole_Mclass"* }
%"_Pshadow_Pstandard_CInterface_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CInterface"*)*, %boolean (%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)* }
%"_Pshadow_Pstandard_CInterface" = type { %"_Pshadow_Pstandard_CInterface_Mclass"* }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%short, %short)*, %int (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"* }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %byte (%byte, %byte)*, %int (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"* }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*, %int)*, %int (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] } }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CInterface"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int }
%"_Pshadow_Pstandard_Cboolean_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_Cboolean" = type { %"_Pshadow_Pstandard_Cboolean_Mclass"* }
%"_Pshadow_Pstandard_Culong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ulong (%ulong, %ulong)*, %int (%ulong, %ulong)*, %ulong (%ulong, %ulong)*, %ulong (%ulong, %ulong)*, %ulong (%ulong, %ulong)*, %ulong (%ulong, %ulong)* }
%"_Pshadow_Pstandard_Culong" = type { %"_Pshadow_Pstandard_Culong_Mclass"* }
%"_Pshadow_Pstandard_Cubyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ubyte (%ubyte, %ubyte)*, %int (%ubyte, %ubyte)*, %ubyte (%ubyte, %ubyte)*, %ubyte (%ubyte, %ubyte)*, %ubyte (%ubyte, %ubyte)*, %ubyte (%ubyte, %ubyte)* }
%"_Pshadow_Pstandard_Cubyte" = type { %"_Pshadow_Pstandard_Cubyte_Mclass"* }
%"_Pshadow_Pstandard_Cuint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %uint (%uint, %uint)*, %int (%uint, %uint)*, %uint (%uint, %uint)*, %uint (%uint, %uint)*, %uint (%uint, %uint)*, %uint (%uint, %uint)*, %uint (%uint, %uint)*, %uint (%uint, %uint)* }
%"_Pshadow_Pstandard_Cuint" = type { %"_Pshadow_Pstandard_Cuint_Mclass"* }
%"_Pshadow_Pstandard_CObject_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CObject_Mclass"* }

@"_Pshadow_Ptest_CConsoleTest_Mclass" = global %"_Pshadow_Ptest_CConsoleTest_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int ptrtoint (%"_Pshadow_Ptest_CConsoleTest"* getelementptr (%"_Pshadow_Ptest_CConsoleTest"* null, i32 1) to i32), %int 8 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", void (%"_Pshadow_Ptest_CConsoleTest"*)* @"_Pshadow_Ptest_CConsoleTest_Mmain" }
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pio_CConsole_Mclass" = external global %"_Pshadow_Pio_CConsole_Mclass"
@"_Pshadow_Pio_CConsole_Minstance" = external global %"_Pshadow_Pio_CConsole"*
@"_Pshadow_Pstandard_CInterface_Mclass" = external global %"_Pshadow_Pstandard_CInterface_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external global %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external global %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external global %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external global %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external global %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external global %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external global %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external global %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external global %"_Pshadow_Pstandard_CObject_Mclass"

define %"_Pshadow_Ptest_CConsoleTest"* @"_Pshadow_Ptest_CConsoleTest_Mcreate"(%"_Pshadow_Ptest_CConsoleTest"*) {
    %this = alloca %"_Pshadow_Ptest_CConsoleTest"*
    store %"_Pshadow_Ptest_CConsoleTest"* %0, %"_Pshadow_Ptest_CConsoleTest"** %this
    %2 = load %"_Pshadow_Ptest_CConsoleTest"** %this
    %3 = getelementptr inbounds %"_Pshadow_Ptest_CConsoleTest"* %2, i32 0, i32 0
    store %"_Pshadow_Ptest_CConsoleTest_Mclass"* @"_Pshadow_Ptest_CConsoleTest_Mclass", %"_Pshadow_Ptest_CConsoleTest_Mclass"** %3
    %4 = load %"_Pshadow_Ptest_CConsoleTest"** %this
    ret %"_Pshadow_Ptest_CConsoleTest"* %4
}

define void @"_Pshadow_Ptest_CConsoleTest_Mmain"(%"_Pshadow_Ptest_CConsoleTest"*) {
    %this = alloca %"_Pshadow_Ptest_CConsoleTest"*
    %console = alloca %"_Pshadow_Pio_CConsole"*
    store %"_Pshadow_Ptest_CConsoleTest"* %0, %"_Pshadow_Ptest_CConsoleTest"** %this
    %2 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %3 = icmp eq %"_Pshadow_Pio_CConsole"* %2, null
    br %boolean %3, label %_label0, label %_label1
_label0:
    %4 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %5 = bitcast %"_Pshadow_Pstandard_CObject"* %4 to %"_Pshadow_Pio_CConsole"*
    %6 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %5)
    store %"_Pshadow_Pio_CConsole"* %6, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label1
_label1:
    %7 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    store %"_Pshadow_Pio_CConsole"* %7, %"_Pshadow_Pio_CConsole"** %console
    %8 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %9 = icmp eq %"_Pshadow_Pio_CConsole"* %8, null
    br %boolean %9, label %_label2, label %_label3
_label2:
    %10 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %"_Pshadow_Pio_CConsole"*
    %12 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %11)
    store %"_Pshadow_Pio_CConsole"* %12, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label3
_label3:
    %13 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %14 = getelementptr %"_Pshadow_Pio_CConsole"* %13, i32 0, i32 0
    %15 = load %"_Pshadow_Pio_CConsole_Mclass"** %14
    %16 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %15, i32 0, i32 14
    %17 = load void (%"_Pshadow_Pio_CConsole"*, %int)** %16
    call void %17(%"_Pshadow_Pio_CConsole"* %13, %int 12345)
    %18 = load %"_Pshadow_Pio_CConsole"** %console
    %19 = getelementptr %"_Pshadow_Pio_CConsole"* %18, i32 0, i32 0
    %20 = load %"_Pshadow_Pio_CConsole_Mclass"** %19
    %21 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %20, i32 0, i32 10
    %22 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %21
    call void %22(%"_Pshadow_Pio_CConsole"* %18, %code 72)
    %23 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %24 = icmp eq %"_Pshadow_Pio_CConsole"* %23, null
    br %boolean %24, label %_label4, label %_label5
_label4:
    %25 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %26 = bitcast %"_Pshadow_Pstandard_CObject"* %25 to %"_Pshadow_Pio_CConsole"*
    %27 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %26)
    store %"_Pshadow_Pio_CConsole"* %27, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label5
_label5:
    %28 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %29 = getelementptr %"_Pshadow_Pio_CConsole"* %28, i32 0, i32 0
    %30 = load %"_Pshadow_Pio_CConsole_Mclass"** %29
    %31 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %30, i32 0, i32 10
    %32 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %31
    call void %32(%"_Pshadow_Pio_CConsole"* %28, %code 101)
    %33 = load %"_Pshadow_Pio_CConsole"** %console
    %34 = getelementptr %"_Pshadow_Pio_CConsole"* %33, i32 0, i32 0
    %35 = load %"_Pshadow_Pio_CConsole_Mclass"** %34
    %36 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %35, i32 0, i32 10
    %37 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %36
    call void %37(%"_Pshadow_Pio_CConsole"* %33, %code 108)
    %38 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %39 = icmp eq %"_Pshadow_Pio_CConsole"* %38, null
    br %boolean %39, label %_label6, label %_label7
_label6:
    %40 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %41 = bitcast %"_Pshadow_Pstandard_CObject"* %40 to %"_Pshadow_Pio_CConsole"*
    %42 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %41)
    store %"_Pshadow_Pio_CConsole"* %42, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label7
_label7:
    %43 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %44 = getelementptr %"_Pshadow_Pio_CConsole"* %43, i32 0, i32 0
    %45 = load %"_Pshadow_Pio_CConsole_Mclass"** %44
    %46 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %45, i32 0, i32 10
    %47 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %46
    call void %47(%"_Pshadow_Pio_CConsole"* %43, %code 108)
    %48 = load %"_Pshadow_Pio_CConsole"** %console
    %49 = getelementptr %"_Pshadow_Pio_CConsole"* %48, i32 0, i32 0
    %50 = load %"_Pshadow_Pio_CConsole_Mclass"** %49
    %51 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %50, i32 0, i32 10
    %52 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %51
    call void %52(%"_Pshadow_Pio_CConsole"* %48, %code 111)
    %53 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %54 = icmp eq %"_Pshadow_Pio_CConsole"* %53, null
    br %boolean %54, label %_label8, label %_label9
_label8:
    %55 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %56 = bitcast %"_Pshadow_Pstandard_CObject"* %55 to %"_Pshadow_Pio_CConsole"*
    %57 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %56)
    store %"_Pshadow_Pio_CConsole"* %57, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label9
_label9:
    %58 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %59 = getelementptr %"_Pshadow_Pio_CConsole"* %58, i32 0, i32 0
    %60 = load %"_Pshadow_Pio_CConsole_Mclass"** %59
    %61 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %60, i32 0, i32 10
    %62 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %61
    call void %62(%"_Pshadow_Pio_CConsole"* %58, %code 32)
    %63 = load %"_Pshadow_Pio_CConsole"** %console
    %64 = getelementptr %"_Pshadow_Pio_CConsole"* %63, i32 0, i32 0
    %65 = load %"_Pshadow_Pio_CConsole_Mclass"** %64
    %66 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %65, i32 0, i32 10
    %67 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %66
    call void %67(%"_Pshadow_Pio_CConsole"* %63, %code 87)
    %68 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %69 = icmp eq %"_Pshadow_Pio_CConsole"* %68, null
    br %boolean %69, label %_label10, label %_label11
_label10:
    %70 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %71 = bitcast %"_Pshadow_Pstandard_CObject"* %70 to %"_Pshadow_Pio_CConsole"*
    %72 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %71)
    store %"_Pshadow_Pio_CConsole"* %72, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label11
_label11:
    %73 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %74 = getelementptr %"_Pshadow_Pio_CConsole"* %73, i32 0, i32 0
    %75 = load %"_Pshadow_Pio_CConsole_Mclass"** %74
    %76 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %75, i32 0, i32 10
    %77 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %76
    call void %77(%"_Pshadow_Pio_CConsole"* %73, %code 111)
    %78 = load %"_Pshadow_Pio_CConsole"** %console
    %79 = getelementptr %"_Pshadow_Pio_CConsole"* %78, i32 0, i32 0
    %80 = load %"_Pshadow_Pio_CConsole_Mclass"** %79
    %81 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %80, i32 0, i32 10
    %82 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %81
    call void %82(%"_Pshadow_Pio_CConsole"* %78, %code 114)
    %83 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %84 = icmp eq %"_Pshadow_Pio_CConsole"* %83, null
    br %boolean %84, label %_label12, label %_label13
_label12:
    %85 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %86 = bitcast %"_Pshadow_Pstandard_CObject"* %85 to %"_Pshadow_Pio_CConsole"*
    %87 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %86)
    store %"_Pshadow_Pio_CConsole"* %87, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label13
_label13:
    %88 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %89 = getelementptr %"_Pshadow_Pio_CConsole"* %88, i32 0, i32 0
    %90 = load %"_Pshadow_Pio_CConsole_Mclass"** %89
    %91 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %90, i32 0, i32 10
    %92 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %91
    call void %92(%"_Pshadow_Pio_CConsole"* %88, %code 108)
    %93 = load %"_Pshadow_Pio_CConsole"** %console
    %94 = getelementptr %"_Pshadow_Pio_CConsole"* %93, i32 0, i32 0
    %95 = load %"_Pshadow_Pio_CConsole_Mclass"** %94
    %96 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %95, i32 0, i32 10
    %97 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %96
    call void %97(%"_Pshadow_Pio_CConsole"* %93, %code 100)
    %98 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %99 = icmp eq %"_Pshadow_Pio_CConsole"* %98, null
    br %boolean %99, label %_label14, label %_label15
_label14:
    %100 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %101 = bitcast %"_Pshadow_Pstandard_CObject"* %100 to %"_Pshadow_Pio_CConsole"*
    %102 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %101)
    store %"_Pshadow_Pio_CConsole"* %102, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label15
_label15:
    %103 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %104 = getelementptr %"_Pshadow_Pio_CConsole"* %103, i32 0, i32 0
    %105 = load %"_Pshadow_Pio_CConsole_Mclass"** %104
    %106 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %105, i32 0, i32 10
    %107 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %106
    call void %107(%"_Pshadow_Pio_CConsole"* %103, %code 33)
    %108 = load %"_Pshadow_Pio_CConsole"** %console
    %109 = getelementptr %"_Pshadow_Pio_CConsole"* %108, i32 0, i32 0
    %110 = load %"_Pshadow_Pio_CConsole_Mclass"** %109
    %111 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %110, i32 0, i32 10
    %112 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %111
    call void %112(%"_Pshadow_Pio_CConsole"* %108, %code 10)
    %113 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %114 = icmp eq %"_Pshadow_Pio_CConsole"* %113, null
    br %boolean %114, label %_label16, label %_label17
_label16:
    %115 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %116 = bitcast %"_Pshadow_Pstandard_CObject"* %115 to %"_Pshadow_Pio_CConsole"*
    %117 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %116)
    store %"_Pshadow_Pio_CConsole"* %117, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label17
_label17:
    %118 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %119 = getelementptr %"_Pshadow_Pio_CConsole"* %118, i32 0, i32 0
    %120 = load %"_Pshadow_Pio_CConsole_Mclass"** %119
    %121 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %120, i32 0, i32 19
    %122 = load void (%"_Pshadow_Pio_CConsole"*)** %121
    call void %122(%"_Pshadow_Pio_CConsole"* %118)
    %123 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %124 = icmp eq %"_Pshadow_Pio_CConsole"* %123, null
    br %boolean %124, label %_label18, label %_label19
_label18:
    %125 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", i32 0, i32 0))
    %126 = bitcast %"_Pshadow_Pstandard_CObject"* %125 to %"_Pshadow_Pio_CConsole"*
    %127 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"* %126)
    store %"_Pshadow_Pio_CConsole"* %127, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    br label %_label19
_label19:
    %128 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
    %129 = getelementptr %"_Pshadow_Pio_CConsole"* %128, i32 0, i32 0
    %130 = load %"_Pshadow_Pio_CConsole_Mclass"** %129
    %131 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %130, i32 0, i32 12
    %132 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %131
    call void %132(%"_Pshadow_Pio_CConsole"* %128, %"_Pshadow_Pstandard_CString"* @_string1)
    ret void
}

declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pio_CConsole"*, %boolean)
declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)
declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode"(%"_Pshadow_Pio_CConsole"*, %code)
declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong"(%"_Pshadow_Pio_CConsole"*, %long)
declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)
declare void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare void @"_Pshadow_Pio_CConsole_MprintLine"(%"_Pshadow_Pio_CConsole"*)
declare %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"*)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pio_CConsole"*, %boolean)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode"(%"_Pshadow_Pio_CConsole"*, %code)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong"(%"_Pshadow_Pio_CConsole"*, %long)
declare void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)

declare %boolean @"_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface"(%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CInterface_MtoString"(%"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CInterface"* @"_Pshadow_Pstandard_CInterface_Mcreate"(%"_Pshadow_Pstandard_CInterface"*)

declare %boolean @"_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %ubyte @"_Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int)
declare %int @"_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)
declare %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*)

declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mcreate"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CObject_MgetClass"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mfreeze"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CObject_MtoString"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcopy"(%"_Pshadow_Pstandard_CObject"*)

@_array0 = private unnamed_addr constant [23 x %ubyte] c"shadow.test@ConsoleTest"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([23 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 23] } }
@_array1 = private unnamed_addr constant [13 x %ubyte] c"Test String\0d\0a"
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([13 x %ubyte]* @_array1, i32 0, i32 0), [1 x %int] [%int 13] } }
