; shadow.standard@Interface

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

%"_Pshadow_Pstandard_CInterface_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CInterface"*)*, %boolean (%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)* }
%"_Pshadow_Pstandard_CInterface" = type { %"_Pshadow_Pstandard_CInterface_Mclass"*, { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }, %"_Pshadow_Pstandard_CString"* }
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
%"_Pshadow_Pstandard_Cshort_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %short (%short, %short)*, %int (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)*, %short (%short, %short)* }
%"_Pshadow_Pstandard_Cshort" = type { %"_Pshadow_Pstandard_Cshort_Mclass"* }
%"_Pshadow_Pstandard_Cbyte_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %byte (%byte, %byte)*, %int (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)*, %byte (%byte, %byte)* }
%"_Pshadow_Pstandard_Cbyte" = type { %"_Pshadow_Pstandard_Cbyte_Mclass"* }
%"_Pshadow_Pstandard_CString_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*, %int)*, %int (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CString_Mclass"*, { %ubyte*, [1 x %int] } }
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

@"_Pshadow_Pstandard_CInterface_Mclass" = global %"_Pshadow_Pstandard_CInterface_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int ptrtoint (%"_Pshadow_Pstandard_CInterface"* getelementptr (%"_Pshadow_Pstandard_CInterface"* null, i32 1) to i32), %int 8 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CInterface"*)* @"_Pshadow_Pstandard_CInterface_MtoString", %boolean (%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)* @"_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface" }
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
@"_Pshadow_Pstandard_Cdouble_Mclass" = external global %"_Pshadow_Pstandard_Cdouble_Mclass"
@"_Pshadow_Pstandard_Clong_Mclass" = external global %"_Pshadow_Pstandard_Clong_Mclass"
@"_Pshadow_Pstandard_Cfloat_Mclass" = external global %"_Pshadow_Pstandard_Cfloat_Mclass"
@"_Pshadow_Pstandard_Cshort_Mclass" = external global %"_Pshadow_Pstandard_Cshort_Mclass"
@"_Pshadow_Pstandard_Cbyte_Mclass" = external global %"_Pshadow_Pstandard_Cbyte_Mclass"
@"_Pshadow_Pstandard_CString_Mclass" = external global %"_Pshadow_Pstandard_CString_Mclass"
@"_Pshadow_Pstandard_CClass_Mclass" = external global %"_Pshadow_Pstandard_CClass_Mclass"
@"_Pshadow_Pstandard_CArray_Mclass" = external global %"_Pshadow_Pstandard_CArray_Mclass"
@"_Pshadow_Pstandard_Cboolean_Mclass" = external global %"_Pshadow_Pstandard_Cboolean_Mclass"
@"_Pshadow_Pstandard_Culong_Mclass" = external global %"_Pshadow_Pstandard_Culong_Mclass"
@"_Pshadow_Pstandard_Cubyte_Mclass" = external global %"_Pshadow_Pstandard_Cubyte_Mclass"
@"_Pshadow_Pstandard_Cuint_Mclass" = external global %"_Pshadow_Pstandard_Cuint_Mclass"
@"_Pshadow_Pstandard_CObject_Mclass" = external global %"_Pshadow_Pstandard_CObject_Mclass"

define %boolean @"_Pshadow_Pstandard_CInterface_MisSubtype_R_Pshadow_Pstandard_CInterface"(%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*) {
    %this = alloca %"_Pshadow_Pstandard_CInterface"*
    %other = alloca %"_Pshadow_Pstandard_CInterface"*
    %i = alloca %int
    store %"_Pshadow_Pstandard_CInterface"* %0, %"_Pshadow_Pstandard_CInterface"** %this
    store %"_Pshadow_Pstandard_CInterface"* %1, %"_Pshadow_Pstandard_CInterface"** %other
    %3 = load %"_Pshadow_Pstandard_CInterface"** %this
    %4 = load %"_Pshadow_Pstandard_CInterface"** %other
    %5 = icmp eq %"_Pshadow_Pstandard_CInterface"* %3, %4
    br %boolean %5, label %_label0, label %_label1
_label0:
    ret %boolean true
    br label %_label2
_label1:
    br label %_label2
_label2:
    store %int 0, %int* %i
    br label %_label4
_label3:
    %7 = load %"_Pshadow_Pstandard_CInterface"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %7, i32 0, i32 1
    %9 = load { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }* %8
    %10 = load %int* %i
    %11 = extractvalue { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } %9, 0
    %12 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"** %11, %int %10
    %13 = load %"_Pshadow_Pstandard_CInterface"** %12
    %14 = icmp ne %"_Pshadow_Pstandard_CInterface"* %13, null
    br %boolean %14, label %_label11, label %_label6
_label11:
    %15 = load %"_Pshadow_Pstandard_CInterface"** %12
    %16 = load %"_Pshadow_Pstandard_CInterface"** %other
    %17 = getelementptr %"_Pshadow_Pstandard_CInterface"* %15, i32 0, i32 0
    %18 = load %"_Pshadow_Pstandard_CInterface_Mclass"** %17
    %19 = getelementptr %"_Pshadow_Pstandard_CInterface_Mclass"* %18, i32 0, i32 7
    %20 = load %boolean (%"_Pshadow_Pstandard_CInterface"*, %"_Pshadow_Pstandard_CInterface"*)** %19
    %21 = call %boolean %20(%"_Pshadow_Pstandard_CInterface"* %15, %"_Pshadow_Pstandard_CInterface"* %16)
    br %boolean %21, label %_label8, label %_label9
_label8:
    ret %boolean true
    br label %_label10
_label9:
    br label %_label10
_label10:
    br label %_label7
_label6:
    br label %_label7
_label7:
    %23 = load %int* %i
    %24 = add %int %23, 1
    store %int %24, %int* %i
    br label %_label4
_label4:
    %25 = load %"_Pshadow_Pstandard_CInterface"** %this
    %26 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %25, i32 0, i32 1
    %27 = load { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }* %26
    %28 = load %int* %i
    %29 = call i8* @malloc(i32 ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32))
    %30 = bitcast i8* %29 to %"_Pshadow_Pstandard_CArray"*
    %31 = extractvalue { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } %27, 0
    %32 = ptrtoint %"_Pshadow_Pstandard_CInterface"** %31 to %long
    %33 = extractvalue { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } %27, 1
    %34 = call i8* @malloc(i32 ptrtoint ([1 x %int]* getelementptr ([1 x %int]* null, i32 1) to i32))
    %35 = bitcast i8* %34 to [1 x %int]*
    store [1 x %int] %33, [1 x %int]* %35
    %36 = getelementptr inbounds [1 x %int]* %35, i32 0, i32 0
    %37 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %36, 0
    %38 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"* %30, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CInterface_Mclass"* @"_Pshadow_Pstandard_CInterface_Mclass", i32 0, i32 0), %long %32, { %int*, [1 x %int] } %37)
    %39 = getelementptr %"_Pshadow_Pstandard_CArray"* %38, i32 0, i32 0
    %40 = load %"_Pshadow_Pstandard_CArray_Mclass"** %39
    %41 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %40, i32 0, i32 10
    %42 = load %int (%"_Pshadow_Pstandard_CArray"*)** %41
    %43 = call %int %42(%"_Pshadow_Pstandard_CArray"* %38)
    %44 = icmp slt %int %28, %43
    br %boolean %44, label %_label3, label %_label5
_label5:
    ret %boolean false
}

define %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CInterface_MtoString"(%"_Pshadow_Pstandard_CInterface"*) {
    %this = alloca %"_Pshadow_Pstandard_CInterface"*
    store %"_Pshadow_Pstandard_CInterface"* %0, %"_Pshadow_Pstandard_CInterface"** %this
    %2 = load %"_Pshadow_Pstandard_CInterface"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %2, i32 0, i32 2
    %4 = load %"_Pshadow_Pstandard_CString"** %3
    ret %"_Pshadow_Pstandard_CString"* %4
}

define %"_Pshadow_Pstandard_CInterface"* @"_Pshadow_Pstandard_CInterface_Mcreate"(%"_Pshadow_Pstandard_CInterface"*) {
    %this = alloca %"_Pshadow_Pstandard_CInterface"*
    store %"_Pshadow_Pstandard_CInterface"* %0, %"_Pshadow_Pstandard_CInterface"** %this
    %2 = load %"_Pshadow_Pstandard_CInterface"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %2, i32 0, i32 0
    store %"_Pshadow_Pstandard_CInterface_Mclass"* @"_Pshadow_Pstandard_CInterface_Mclass", %"_Pshadow_Pstandard_CInterface_Mclass"** %3
    %4 = load %"_Pshadow_Pstandard_CInterface"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %4, i32 0, i32 2
    store %"_Pshadow_Pstandard_CString"* @_string1, %"_Pshadow_Pstandard_CString"** %5
    %6 = load %"_Pshadow_Pstandard_CInterface"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CInterface"* %6, i32 0, i32 1
    %8 = call noalias i8* @calloc(i32 0, i32 ptrtoint (%"_Pshadow_Pstandard_CInterface"** getelementptr (%"_Pshadow_Pstandard_CInterface"** null, i32 1) to i32))
    %9 = bitcast i8* %8 to %"_Pshadow_Pstandard_CInterface"**
    %10 = insertvalue { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } undef, %"_Pshadow_Pstandard_CInterface"** %9, 0
    %11 = insertvalue { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } %10, %int 0, 1, 0
    store { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } %11, { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] }* %7
    %12 = load %"_Pshadow_Pstandard_CInterface"** %this
    ret %"_Pshadow_Pstandard_CInterface"* %12
}

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

@_array0 = private unnamed_addr constant [25 x %ubyte] c"shadow.standard@Interface"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([25 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 25] } }
@_array1 = private unnamed_addr constant [0 x %ubyte] c""
@_string1 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @_array1, i32 0, i32 0), [1 x %int] [%int 0] } }
