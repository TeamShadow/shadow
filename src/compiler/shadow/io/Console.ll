; shadow.io@Console

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

%"_Pshadow_Pio_CConsole_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %boolean)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, void (%"_Pshadow_Pio_CConsole"*, %code)*, void (%"_Pshadow_Pio_CConsole"*, %long)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, void (%"_Pshadow_Pio_CConsole"*, %boolean)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, void (%"_Pshadow_Pio_CConsole"*, %code)*, void (%"_Pshadow_Pio_CConsole"*, %long)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pio_CConsole_Mclass"* }
%"_Pshadow_Pstandard_Cint_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %uint (%int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)*, %int (%int, %int)* }
%"_Pshadow_Pstandard_Cint" = type { %"_Pshadow_Pstandard_Cint_Mclass"* }
%"_Pshadow_Pstandard_Ccode_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_Ccode" = type { %"_Pshadow_Pstandard_Ccode_Mclass"* }
%"_Pshadow_Pstandard_Cushort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %ushort (%ushort, %ushort)*, %int (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)*, %ushort (%ushort, %ushort)* }
%"_Pshadow_Pstandard_Cushort" = type { %"_Pshadow_Pstandard_Cushort_Mclass"* }
%"_Pshadow_Pstandard_Cdouble_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %double (%double, %double)*, %int (%double, %double)*, %double (%double, %double)*, %double (%double, %double)*, %double (%double, %double)*, %double (%double, %double)* }
%"_Pshadow_Pstandard_Cdouble" = type { %"_Pshadow_Pstandard_Cdouble_Mclass"* }
%"_Pshadow_Pstandard_Clong_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %long (%long, %long)*, %int (%long, %long)*, %long (%long, %long)*, %long (%long, %long)*, %long (%long, %long)*, %long (%long, %long)* }
%"_Pshadow_Pstandard_Clong" = type { %"_Pshadow_Pstandard_Clong_Mclass"* }
%"_Pshadow_Pstandard_Cfloat_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %float (%float, %float)*, %int (%float, %float)*, %float (%float, %float)*, %float (%float, %float)*, %float (%float, %float)*, %float (%float, %float)* }
%"_Pshadow_Pstandard_Cfloat" = type { %"_Pshadow_Pstandard_Cfloat_Mclass"* }
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

@"_Pshadow_Pio_CConsole_Mclass" = global %"_Pshadow_Pio_CConsole_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int ptrtoint (%"_Pshadow_Pio_CConsole"* getelementptr (%"_Pshadow_Pio_CConsole"* null, i32 1) to i32), %int 8 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", void (%"_Pshadow_Pio_CConsole"*, %boolean)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean", void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject", void (%"_Pshadow_Pio_CConsole"*, %int)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cint", void (%"_Pshadow_Pio_CConsole"*, %code)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Ccode", void (%"_Pshadow_Pio_CConsole"*, %long)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Clong", void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString", void (%"_Pshadow_Pio_CConsole"*, %boolean)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean", void (%"_Pshadow_Pio_CConsole"*, %int)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint", void (%"_Pshadow_Pio_CConsole"*, %code)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode", void (%"_Pshadow_Pio_CConsole"*, %long)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong", void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString", void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject", void (%"_Pshadow_Pio_CConsole"*)* @"_Pshadow_Pio_CConsole_MprintLine" }
@"_Pshadow_Pio_CConsole_Minstance" = global %"_Pshadow_Pio_CConsole"* null
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
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

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pio_CConsole"*, %boolean) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %b = alloca %boolean
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %boolean %1, %boolean* %b
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %boolean* %b
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 7
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %boolean)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %boolean %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %i = alloca %int
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %int %1, %int* %i
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %int* %i
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 9
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %int)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %int %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Ccode"(%"_Pshadow_Pio_CConsole"*, %code) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %c = alloca %code
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %code %1, %code* %c
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %code* %c
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 10
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %code %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_Clong"(%"_Pshadow_Pio_CConsole"*, %long) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %l = alloca %long
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %long %1, %long* %l
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %long* %l
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 11
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %long)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %long %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %s = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %s
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %"_Pshadow_Pstandard_CString"** %s
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 12
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %"_Pshadow_Pstandard_CString"* %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %o = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %o
    %3 = load %"_Pshadow_Pio_CConsole"** %this
    %4 = load %"_Pshadow_Pstandard_CObject"** %o
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 8
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %3, %"_Pshadow_Pstandard_CObject"* %4)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 19
    %13 = load void (%"_Pshadow_Pio_CConsole"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9)
    ret void
}

define void @"_Pshadow_Pio_CConsole_MprintLine"(%"_Pshadow_Pio_CConsole"*) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    %2 = load %"_Pshadow_Pio_CConsole"** %this
    %3 = getelementptr %"_Pshadow_Pio_CConsole"* %2, i32 0, i32 0
    %4 = load %"_Pshadow_Pio_CConsole_Mclass"** %3
    %5 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %4, i32 0, i32 10
    %6 = load void (%"_Pshadow_Pio_CConsole"*, %code)** %5
    call void %6(%"_Pshadow_Pio_CConsole"* %2, %code 10)
    ret void
}

define %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pio_CConsole"*) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    %2 = load %"_Pshadow_Pio_CConsole"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pio_CConsole"* %2, i32 0, i32 0
    store %"_Pshadow_Pio_CConsole_Mclass"* @"_Pshadow_Pio_CConsole_Mclass", %"_Pshadow_Pio_CConsole_Mclass"** %3
    %4 = load %"_Pshadow_Pio_CConsole"** %this
    ret %"_Pshadow_Pio_CConsole"* %4
}

define void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_Cboolean"(%"_Pshadow_Pio_CConsole"*, %boolean) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %b = alloca %boolean
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %boolean %1, %boolean* %b
    %3 = load %boolean* %b
    br %boolean %3, label %_label0, label %_label1
_label0:
    %4 = load %"_Pshadow_Pio_CConsole"** %this
    %5 = getelementptr %"_Pshadow_Pio_CConsole"* %4, i32 0, i32 0
    %6 = load %"_Pshadow_Pio_CConsole_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %6, i32 0, i32 12
    %8 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %7
    call void %8(%"_Pshadow_Pio_CConsole"* %4, %"_Pshadow_Pstandard_CString"* @_string1)
    br label %_label2
_label1:
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 12
    %13 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9, %"_Pshadow_Pstandard_CString"* @_string2)
    br label %_label2
_label2:
    ret void
}

define void @"_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*) {
    %this = alloca %"_Pshadow_Pio_CConsole"*
    %o = alloca %"_Pshadow_Pstandard_CObject"*
    store %"_Pshadow_Pio_CConsole"* %0, %"_Pshadow_Pio_CConsole"** %this
    store %"_Pshadow_Pstandard_CObject"* %1, %"_Pshadow_Pstandard_CObject"** %o
    %3 = load %"_Pshadow_Pstandard_CObject"** %o
    %4 = getelementptr %"_Pshadow_Pstandard_CObject"* %3, i32 0, i32 0
    %5 = load %"_Pshadow_Pstandard_CObject_Mclass"** %4
    %6 = getelementptr %"_Pshadow_Pstandard_CObject_Mclass"* %5, i32 0, i32 6
    %7 = load %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)** %6
    %8 = call %"_Pshadow_Pstandard_CString"* %7(%"_Pshadow_Pstandard_CObject"* %3)
    %9 = load %"_Pshadow_Pio_CConsole"** %this
    %10 = getelementptr %"_Pshadow_Pio_CConsole"* %9, i32 0, i32 0
    %11 = load %"_Pshadow_Pio_CConsole_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pio_CConsole_Mclass"* %11, i32 0, i32 12
    %13 = load void (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)** %12
    call void %13(%"_Pshadow_Pio_CConsole"* %9, %"_Pshadow_Pstandard_CString"* %8)
    ret void
}

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

@_array0 = private unnamed_addr constant [17 x %ubyte] c"shadow.io@Console"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([17 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 17] } }
@_array1 = private unnamed_addr constant [4 x %ubyte] c"true"
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([4 x %ubyte]* @_array1, i32 0, i32 0), [1 x %int] [%int 4] } }
@_array2 = private unnamed_addr constant [5 x %ubyte] c"false"
@_string2 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([5 x %ubyte]* @_array2, i32 0, i32 0), [1 x %int] [%int 5] } }
