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

declare noalias i8* @malloc(i32)
declare noalias i8* @calloc(i32, i32)

%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*, %int)*, %int (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] } }
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
%"_Pshadow_Pstandard_CInterface_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CInterface"*)*, %boolean (%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)* }
%"_Pshadow_Pstandard_CInterface" = type { %"_Pshadow_Pstandard_CInterface_Mclass"* }
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%short, %short)*, %int (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"* }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %byte (%byte, %byte)*, %int (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"* }
%"_Pshadow_Pstandard_CClass_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CInterface"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass_Mclass"*, { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, %int, %int }
%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*, %int)*, void (%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"* }
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

@"_Pshadow_Pstandard_CString_Mclass" = global %"_Pshadow_Pstandard_CString_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int ptrtoint (%"_Pshadow_Pstandard_CString"* getelementptr (%"_Pshadow_Pstandard_CString"* null, i32 1) to i32), %int 8 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_MtoString", %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString", %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString", %ubyte (%"_Pshadow_Pstandard_CString"*, %int)* @"_Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint", %int (%"_Pshadow_Pstandard_CString"*)* @"_Pshadow_Pstandard_CString_Mlength" }
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_CInterface_Mclass" = external global %"_Pshadow_Pstandard_CInterface_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external global %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external global %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external global %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external global %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external global %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external global %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external global %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external global %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external global %"_Pshadow_Pstandard_CObject_Mclass"

define %boolean @"_Pshadow_Pstandard_CString_Mequals_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %i = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = load %"_Pshadow_Pstandard_CString"** %other
    %5 = getelementptr %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pstandard_CString_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %6, i32 0, i32 10
    %8 = load %int (%"_Pshadow_Pstandard_CString"*)** %7
    %9 = call %int %8(%"_Pshadow_Pstandard_CString"* %3)
    %10 = getelementptr %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 0
    %11 = load %"_Pshadow_Pstandard_CString_Mclass"** %10
    %12 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %11, i32 0, i32 10
    %13 = load %int (%"_Pshadow_Pstandard_CString"*)** %12
    %14 = call %int %13(%"_Pshadow_Pstandard_CString"* %4)
    %15 = icmp ne %int %9, %14
    br %boolean %15, label %_label0, label %_label1
_label0:
    ret %boolean false
    br label %_label2
_label1:
    br label %_label2
_label2:
    store %int 0, %int* %i
    br label %_label4
_label3:
    %17 = load %"_Pshadow_Pstandard_CString"** %this
    %18 = load %int* %i
    %19 = getelementptr %"_Pshadow_Pstandard_CString"* %17, i32 0, i32 0
    %20 = load %"_Pshadow_Pstandard_CString_Mclass"** %19
    %21 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %20, i32 0, i32 9
    %22 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %21
    %23 = call %ubyte %22(%"_Pshadow_Pstandard_CString"* %17, %int %18)
    %24 = load %"_Pshadow_Pstandard_CString"** %other
    %25 = load %int* %i
    %26 = getelementptr %"_Pshadow_Pstandard_CString"* %24, i32 0, i32 0
    %27 = load %"_Pshadow_Pstandard_CString_Mclass"** %26
    %28 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %27, i32 0, i32 9
    %29 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %28
    %30 = call %ubyte %29(%"_Pshadow_Pstandard_CString"* %24, %int %25)
    %31 = icmp ne %ubyte %23, %30
    br %boolean %31, label %_label6, label %_label7
_label6:
    ret %boolean false
    br label %_label8
_label7:
    br label %_label8
_label8:
    %33 = load %int* %i
    %34 = add %int %33, 1
    store %int %34, %int* %i
    br label %_label4
_label4:
    %35 = load %"_Pshadow_Pstandard_CString"** %this
    %36 = load %int* %i
    %37 = getelementptr %"_Pshadow_Pstandard_CString"* %35, i32 0, i32 0
    %38 = load %"_Pshadow_Pstandard_CString_Mclass"** %37
    %39 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %38, i32 0, i32 10
    %40 = load %int (%"_Pshadow_Pstandard_CString"*)** %39
    %41 = call %int %40(%"_Pshadow_Pstandard_CString"* %35)
    %42 = icmp slt %int %36, %41
    br %boolean %42, label %_label3, label %_label5
_label5:
    ret %boolean true
}

define %ubyte @"_Pshadow_Pstandard_CString_MgetByte_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CString"*, %int) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %index = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %int %1, %int* %index
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 1
    %5 = load { %ubyte*, [1 x %int] }* %4
    %6 = load %int* %index
    %7 = extractvalue { %ubyte*, [1 x %int] } %5, 0
    %8 = getelementptr inbounds %ubyte* %7, %int %6
    %9 = load %ubyte* %8
    ret %ubyte %9
}

define %int @"_Pshadow_Pstandard_CString_Mcompare_R_Pshadow_Pstandard_CString"(%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    %other = alloca %"_Pshadow_Pstandard_CString"*
    %i = alloca %int
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    store %"_Pshadow_Pstandard_CString"* %1, %"_Pshadow_Pstandard_CString"** %other
    store %int 0, %int* %i
    br label %_label10
_label9:
    %3 = load %"_Pshadow_Pstandard_CString"** %this
    %4 = load %int* %i
    %5 = getelementptr %"_Pshadow_Pstandard_CString"* %3, i32 0, i32 0
    %6 = load %"_Pshadow_Pstandard_CString_Mclass"** %5
    %7 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %6, i32 0, i32 9
    %8 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %7
    %9 = call %ubyte %8(%"_Pshadow_Pstandard_CString"* %3, %int %4)
    %10 = load %"_Pshadow_Pstandard_CString"** %other
    %11 = load %int* %i
    %12 = getelementptr %"_Pshadow_Pstandard_CString"* %10, i32 0, i32 0
    %13 = load %"_Pshadow_Pstandard_CString_Mclass"** %12
    %14 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %13, i32 0, i32 9
    %15 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %14
    %16 = call %ubyte %15(%"_Pshadow_Pstandard_CString"* %10, %int %11)
    %17 = icmp ne %ubyte %9, %16
    br %boolean %17, label %_label12, label %_label13
_label12:
    %18 = load %"_Pshadow_Pstandard_CString"** %this
    %19 = load %int* %i
    %20 = getelementptr %"_Pshadow_Pstandard_CString"* %18, i32 0, i32 0
    %21 = load %"_Pshadow_Pstandard_CString_Mclass"** %20
    %22 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %21, i32 0, i32 9
    %23 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %22
    %24 = call %ubyte %23(%"_Pshadow_Pstandard_CString"* %18, %int %19)
    %25 = load %"_Pshadow_Pstandard_CString"** %other
    %26 = load %int* %i
    %27 = getelementptr %"_Pshadow_Pstandard_CString"* %25, i32 0, i32 0
    %28 = load %"_Pshadow_Pstandard_CString_Mclass"** %27
    %29 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %28, i32 0, i32 9
    %30 = load %ubyte (%"_Pshadow_Pstandard_CString"*, %int)** %29
    %31 = call %ubyte %30(%"_Pshadow_Pstandard_CString"* %25, %int %26)
    %32 = sub %ubyte %24, %31
    %33 = sext %ubyte %32 to %int
    ret %int %33
    br label %_label14
_label13:
    br label %_label14
_label14:
    %35 = load %int* %i
    %36 = add %int %35, 1
    store %int %36, %int* %i
    br label %_label10
_label10:
    %37 = load %"_Pshadow_Pstandard_CString"** %this
    %38 = load %int* %i
    %39 = getelementptr %"_Pshadow_Pstandard_CString"* %37, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CString_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %40, i32 0, i32 10
    %42 = load %int (%"_Pshadow_Pstandard_CString"*)** %41
    %43 = call %int %42(%"_Pshadow_Pstandard_CString"* %37)
    %44 = icmp slt %int %38, %43
    %45 = load %"_Pshadow_Pstandard_CString"** %other
    %46 = load %int* %i
    %47 = getelementptr %"_Pshadow_Pstandard_CString"* %45, i32 0, i32 0
    %48 = load %"_Pshadow_Pstandard_CString_Mclass"** %47
    %49 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %48, i32 0, i32 10
    %50 = load %int (%"_Pshadow_Pstandard_CString"*)** %49
    %51 = call %int %50(%"_Pshadow_Pstandard_CString"* %45)
    %52 = icmp slt %int %46, %51
    %53 = and %boolean %44, %52
    br %boolean %53, label %_label9, label %_label11
_label11:
    %54 = load %"_Pshadow_Pstandard_CString"** %this
    %55 = load %"_Pshadow_Pstandard_CString"** %other
    %56 = getelementptr %"_Pshadow_Pstandard_CString"* %54, i32 0, i32 0
    %57 = load %"_Pshadow_Pstandard_CString_Mclass"** %56
    %58 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %57, i32 0, i32 10
    %59 = load %int (%"_Pshadow_Pstandard_CString"*)** %58
    %60 = call %int %59(%"_Pshadow_Pstandard_CString"* %54)
    %61 = getelementptr %"_Pshadow_Pstandard_CString"* %55, i32 0, i32 0
    %62 = load %"_Pshadow_Pstandard_CString_Mclass"** %61
    %63 = getelementptr %"_Pshadow_Pstandard_CString_Mclass"* %62, i32 0, i32 10
    %64 = load %int (%"_Pshadow_Pstandard_CString"*)** %63
    %65 = call %int %64(%"_Pshadow_Pstandard_CString"* %55)
    %66 = sub %int %60, %65
    ret %int %66
}

define %int @"_Pshadow_Pstandard_CString_Mlength"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %2, i32 0, i32 1
    %4 = load { %ubyte*, [1 x %int] }* %3
    %5 = call i8* @malloc(i32 ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32))
    %6 = bitcast i8* %5 to %"_Pshadow_Pstandard_CArray"*
    %7 = extractvalue { %ubyte*, [1 x %int] } %4, 0
    %8 = ptrtoint %ubyte* %7 to %long
    %9 = extractvalue { %ubyte*, [1 x %int] } %4, 1
    %10 = call i8* @malloc(i32 ptrtoint ([1 x %int]* getelementptr ([1 x %int]* null, i32 1) to i32))
    %11 = bitcast i8* %10 to [1 x %int]*
    store [1 x %int] %9, [1 x %int]* %11
    %12 = getelementptr inbounds [1 x %int]* %11, i32 0, i32 0
    %13 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %12, 0
    %14 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"* %6, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cubyte_Mclass"* @"_Pshadow_Pstandard_Cubyte_Mclass", i32 0, i32 0), %long %8, { %int*, [1 x %int] } %13)
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 10
    %18 = load %int (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = call %int %18(%"_Pshadow_Pstandard_CArray"* %14)
    ret %int %19
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_MtoString"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    ret %"_Pshadow_Pstandard_CString"* %2
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate"(%"_Pshadow_Pstandard_CString"*) {
    %this = alloca %"_Pshadow_Pstandard_CString"*
    store %"_Pshadow_Pstandard_CString"* %0, %"_Pshadow_Pstandard_CString"** %this
    %2 = load %"_Pshadow_Pstandard_CString"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %2, i32 0, i32 0
    store %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", %"_Pshadow_Pstandard_CString_Mclass"** %3
    %4 = load %"_Pshadow_Pstandard_CString"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CString"* %4, i32 0, i32 1
    %6 = call noalias i8* @calloc(i32 0, i32 ptrtoint (%ubyte* getelementptr (%ubyte* null, i32 1) to i32))
    %7 = bitcast i8* %6 to %ubyte*
    %8 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %7, 0
    %9 = insertvalue { %ubyte*, [1 x %int] } %8, %int 0, 1, 0
    store { %ubyte*, [1 x %int] } %9, { %ubyte*, [1 x %int] }* %5
    %10 = load %"_Pshadow_Pstandard_CString"** %this
    ret %"_Pshadow_Pstandard_CString"* %10
}

declare %boolean @"_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface"(%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CInterface_MtoString"(%"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CInterface"* @"_Pshadow_Pstandard_CInterface_Mcreate"(%"_Pshadow_Pstandard_CInterface"*)

declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_McreateObject"(%"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)
declare %boolean @"_Pshadow_Pstandard_CClass_MisSubtype_R_Pshadow_Pstandard_CInterface"(%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CInterface"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mparent"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CClass_MtoString"(%"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CClass_Mcreate"(%"_Pshadow_Pstandard_CClass"*)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int)
declare void @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT"(%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CClass"*)
declare %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*)
declare %int @"_Pshadow_Pstandard_CArray_Mlength"(%"_Pshadow_Pstandard_CArray"*)
declare { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*)
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, %long, { %int*, [1 x %int] })
declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] })

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CObject_MgetClass"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mfreeze"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CObject_MtoString"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %boolean @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcopy"(%"_Pshadow_Pstandard_CObject"*)

@_array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@String"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([22 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 22] } }
