; int

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

@"_Pshadow_Pstandard_Cint_Mclass" = global %"_Pshadow_Pstandard_Cint_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int 4, %int 4 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", %uint (%int)* @"_Pshadow_Pstandard_Cint_Mabs", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint", %int (%int, %int)* @"_Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint" }
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

define %int @"_Pshadow_Pstandard_Cint_Mmin_R_Pshadow_Pstandard_Cint"(%int, %int) {
    %this = alloca %int
    %other = alloca %int
    %_temp = alloca %int
    store %int %0, %int* %this
    store %int %1, %int* %other
    %3 = load %int* %this
    %4 = load %int* %other
    %5 = icmp slt %int %3, %4
    br %boolean %5, label %_label0, label %_label1
_label0:
    %6 = load %int* %this
    store %int %6, %int* %_temp
    br label %_label2
_label1:
    %7 = load %int* %other
    store %int %7, %int* %_temp
    br label %_label2
_label2:
    %8 = load %int* %_temp
    ret %int %8
}

declare %int @"_Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint"(%int, %int)

define %uint @"_Pshadow_Pstandard_Cint_Mabs"(%int) {
    %this = alloca %int
    %_temp = alloca %int
    store %int %0, %int* %this
    %2 = load %int* %this
    %3 = icmp slt %int %2, 0
    br %boolean %3, label %_label3, label %_label4
_label3:
    %4 = load %int* %this
    %5 = sub %int 0, %4
    store %int %5, %int* %_temp
    br label %_label5
_label4:
    %6 = load %int* %this
    store %int %6, %int* %_temp
    br label %_label5
_label5:
    %7 = load %int* %_temp
    %8 = bitcast %int %7 to %uint
    ret %uint %8
}

define %int @"_Pshadow_Pstandard_Cint_Mmax_R_Pshadow_Pstandard_Cint"(%int, %int) {
    %this = alloca %int
    %other = alloca %int
    %_temp = alloca %int
    store %int %0, %int* %this
    store %int %1, %int* %other
    %3 = load %int* %this
    %4 = load %int* %other
    %5 = icmp sgt %int %3, %4
    br %boolean %5, label %_label6, label %_label7
_label6:
    %6 = load %int* %this
    store %int %6, %int* %_temp
    br label %_label8
_label7:
    %7 = load %int* %other
    store %int %7, %int* %_temp
    br label %_label8
_label8:
    %8 = load %int* %_temp
    ret %int %8
}

declare %int @"_Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint"(%int, %int)

declare %int @"_Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint"(%int, %int)

declare %int @"_Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint"(%int, %int)

declare %int @"_Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint"(%int, %int)

define %int @"_Pshadow_Pstandard_Cint_Mcreate"(%int) {
    %this = alloca %int
    store %int %0, %int* %this
    %2 = load %int* %this
    ret %int %2
}

declare %int @"_Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint"(%int, %int)

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

@_array0 = private unnamed_addr constant [3 x %ubyte] c"int"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([3 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 3] } }
