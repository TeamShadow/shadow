; shadow.standard@Array<T>

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

%"_Pshadow_Pstandard_CArray_Mclass" = type { %"_Pshadow_Pstandard_CClass", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %long (%"_Pshadow_Pstandard_CArray"*, %int)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*, %int)*, void (%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CArray_Mclass"*, %"_Pshadow_Pstandard_CClass"*, %long, { %int*, [1 x %int] } }
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

@"_Pshadow_Pstandard_CArray_Mclass" = global %"_Pshadow_Pstandard_CArray_Mclass" { %"_Pshadow_Pstandard_CClass" { %"_Pshadow_Pstandard_CClass_Mclass"* @"_Pshadow_Pstandard_CClass_Mclass", { %"_Pshadow_Pstandard_CInterface"**, [1 x %int] } zeroinitializer, %"_Pshadow_Pstandard_CString"* @_string0, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_CObject_Mclass"* @"_Pshadow_Pstandard_CObject_Mclass", i32 0, i32 0), %int ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32), %int 8 }, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcopy", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mequals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mfreeze", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MgetClass", %boolean (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MreferenceEquals_R_Pshadow_Pstandard_CObject", %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_MtoString", %long (%"_Pshadow_Pstandard_CArray"*, %int)* @"_Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mdims", %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*, %int)* @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint", void (%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CClass"*)* @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT", %int (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mlength", { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)* @"_Pshadow_Pstandard_CArray_Mlengths" }
@"_Pshadow_Pstandard_Cint_Mclass" = external global %"_Pshadow_Pstandard_Cint_Mclass"
@"_Pshadow_Pstandard_Cushort_Mclass" = external global %"_Pshadow_Pstandard_Cushort_Mclass"
@"_Pshadow_Pstandard_Ccode_Mclass" = external global %"_Pshadow_Pstandard_Ccode_Mclass"
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

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int)

declare void @"_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT"(%"_Pshadow_Pstandard_CArray"*, %int, %"_Pshadow_Pstandard_CClass"*)

define %int @"_Pshadow_Pstandard_CArray_Mdims"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 3
    %4 = load { %int*, [1 x %int] }* %3
    %5 = call i8* @malloc(i32 ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32))
    %6 = bitcast i8* %5 to %"_Pshadow_Pstandard_CArray"*
    %7 = extractvalue { %int*, [1 x %int] } %4, 0
    %8 = ptrtoint %int* %7 to %long
    %9 = extractvalue { %int*, [1 x %int] } %4, 1
    %10 = call i8* @malloc(i32 ptrtoint ([1 x %int]* getelementptr ([1 x %int]* null, i32 1) to i32))
    %11 = bitcast i8* %10 to [1 x %int]*
    store [1 x %int] %9, [1 x %int]* %11
    %12 = getelementptr inbounds [1 x %int]* %11, i32 0, i32 0
    %13 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %12, 0
    %14 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"* %6, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %long %8, { %int*, [1 x %int] } %13)
    %15 = getelementptr %"_Pshadow_Pstandard_CArray"* %14, i32 0, i32 0
    %16 = load %"_Pshadow_Pstandard_CArray_Mclass"** %15
    %17 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %16, i32 0, i32 12
    %18 = load { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)** %17
    %19 = call { %int*, [1 x %int] } %18(%"_Pshadow_Pstandard_CArray"* %14)
    %20 = extractvalue { %int*, [1 x %int] } %19, 0
    %21 = getelementptr inbounds %int* %20, %int 0
    %22 = load %int* %21
    ret %int %22
}

define %int @"_Pshadow_Pstandard_CArray_Mlength"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %length = alloca %int
    %i = alloca %int
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 3
    %4 = load { %int*, [1 x %int] }* %3
    %5 = extractvalue { %int*, [1 x %int] } %4, 0
    %6 = getelementptr inbounds %int* %5, %int 0
    %7 = load %int* %6
    store %int %7, %int* %length
    store %int 1, %int* %i
    br label %_label1
_label0:
    %8 = load %"_Pshadow_Pstandard_CArray"** %this
    %9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %8, i32 0, i32 3
    %10 = load { %int*, [1 x %int] }* %9
    %11 = load %int* %i
    %12 = extractvalue { %int*, [1 x %int] } %10, 0
    %13 = getelementptr inbounds %int* %12, %int %11
    %14 = load %int* %length
    %15 = load %int* %13
    %16 = mul %int %14, %15
    store %int %16, %int* %length
    %17 = load %int* %i
    %18 = add %int %17, 1
    store %int %18, %int* %i
    br label %_label1
_label1:
    %19 = load %"_Pshadow_Pstandard_CArray"** %this
    %20 = load %int* %i
    %21 = getelementptr %"_Pshadow_Pstandard_CArray"* %19, i32 0, i32 0
    %22 = load %"_Pshadow_Pstandard_CArray_Mclass"** %21
    %23 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %22, i32 0, i32 8
    %24 = load %int (%"_Pshadow_Pstandard_CArray"*)** %23
    %25 = call %int %24(%"_Pshadow_Pstandard_CArray"* %19)
    %26 = icmp slt %int %20, %25
    br %boolean %26, label %_label0, label %_label2
_label2:
    %27 = load %int* %length
    ret %int %27
}

declare %long @"_Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CArray"*, %int)

define { %int*, [1 x %int] } @"_Pshadow_Pstandard_CArray_Mlengths"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 3
    %4 = load { %int*, [1 x %int] }* %3
    ret { %int*, [1 x %int] } %4
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, %long, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %data = alloca %long
    %lengths = alloca { %int*, [1 x %int] }
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store %long %2, %long* %data
    store { %int*, [1 x %int] } %3, { %int*, [1 x %int] }* %lengths
    %5 = load %"_Pshadow_Pstandard_CArray"** %this
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %5, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %6
    %7 = load %"_Pshadow_Pstandard_CArray"** %this
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %7, i32 0, i32 1
    %9 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %9, %"_Pshadow_Pstandard_CClass"** %8
    %10 = load %"_Pshadow_Pstandard_CArray"** %this
    %11 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %10, i32 0, i32 2
    %12 = sext %int 0 to %long
    store %long %12, %long* %11
    %13 = load %"_Pshadow_Pstandard_CArray"** %this
    %14 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %13, i32 0, i32 3
    %15 = call noalias i8* @calloc(i32 0, i32 ptrtoint (%int* getelementptr (%int* null, i32 1) to i32))
    %16 = bitcast i8* %15 to %int*
    %17 = insertvalue { %int*, [1 x %int] } undef, %int* %16, 0
    %18 = insertvalue { %int*, [1 x %int] } %17, %int 0, 1, 0
    store { %int*, [1 x %int] } %18, { %int*, [1 x %int] }* %14
    %19 = load %"_Pshadow_Pstandard_CArray"** %this
    %20 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %19, i32 0, i32 2
    %21 = load %long* %data
    store %long %21, %long* %20
    %22 = load %"_Pshadow_Pstandard_CArray"** %this
    %23 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %22, i32 0, i32 3
    %24 = load { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %24, { %int*, [1 x %int] }* %23
    %25 = load %"_Pshadow_Pstandard_CArray"** %this
    ret %"_Pshadow_Pstandard_CArray"* %25
}

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CClass"*, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %T = alloca %"_Pshadow_Pstandard_CClass"*
    %lengths = alloca { %int*, [1 x %int] }
    %i = alloca %int
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    store %"_Pshadow_Pstandard_CClass"* %1, %"_Pshadow_Pstandard_CClass"** %T
    store { %int*, [1 x %int] } %2, { %int*, [1 x %int] }* %lengths
    %4 = load %"_Pshadow_Pstandard_CArray"** %this
    %5 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %4, i32 0, i32 0
    store %"_Pshadow_Pstandard_CArray_Mclass"* @"_Pshadow_Pstandard_CArray_Mclass", %"_Pshadow_Pstandard_CArray_Mclass"** %5
    %6 = load %"_Pshadow_Pstandard_CArray"** %this
    %7 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %6, i32 0, i32 1
    %8 = load %"_Pshadow_Pstandard_CClass"** %T
    store %"_Pshadow_Pstandard_CClass"* %8, %"_Pshadow_Pstandard_CClass"** %7
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 2
    %11 = sext %int 0 to %long
    store %long %11, %long* %10
    %12 = load %"_Pshadow_Pstandard_CArray"** %this
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %12, i32 0, i32 3
    %14 = call noalias i8* @calloc(i32 0, i32 ptrtoint (%int* getelementptr (%int* null, i32 1) to i32))
    %15 = bitcast i8* %14 to %int*
    %16 = insertvalue { %int*, [1 x %int] } undef, %int* %15, 0
    %17 = insertvalue { %int*, [1 x %int] } %16, %int 0, 1, 0
    store { %int*, [1 x %int] } %17, { %int*, [1 x %int] }* %13
    %18 = load { %int*, [1 x %int] }* %lengths
    %19 = call i8* @malloc(i32 ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32))
    %20 = bitcast i8* %19 to %"_Pshadow_Pstandard_CArray"*
    %21 = extractvalue { %int*, [1 x %int] } %18, 0
    %22 = ptrtoint %int* %21 to %long
    %23 = extractvalue { %int*, [1 x %int] } %18, 1
    %24 = call i8* @malloc(i32 ptrtoint ([1 x %int]* getelementptr ([1 x %int]* null, i32 1) to i32))
    %25 = bitcast i8* %24 to [1 x %int]*
    store [1 x %int] %23, [1 x %int]* %25
    %26 = getelementptr inbounds [1 x %int]* %25, i32 0, i32 0
    %27 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %26, 0
    %28 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"* %20, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %long %22, { %int*, [1 x %int] } %27)
    %29 = getelementptr %"_Pshadow_Pstandard_CArray"* %28, i32 0, i32 0
    %30 = load %"_Pshadow_Pstandard_CArray_Mclass"** %29
    %31 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %30, i32 0, i32 11
    %32 = load %int (%"_Pshadow_Pstandard_CArray"*)** %31
    %33 = call %int %32(%"_Pshadow_Pstandard_CArray"* %28)
    %34 = call noalias i8* @calloc(i32 %33, i32 ptrtoint (%int* getelementptr (%int* null, i32 1) to i32))
    %35 = bitcast i8* %34 to %int*
    %36 = insertvalue { %int*, [1 x %int] } undef, %int* %35, 0
    %37 = insertvalue { %int*, [1 x %int] } %36, %int %33, 1, 0
    %38 = load %"_Pshadow_Pstandard_CArray"** %this
    %39 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %38, i32 0, i32 3
    store { %int*, [1 x %int] } %37, { %int*, [1 x %int] }* %39
    store %int 0, %int* %i
    br label %_label4
_label3:
    %40 = load { %int*, [1 x %int] }* %lengths
    %41 = load %int* %i
    %42 = extractvalue { %int*, [1 x %int] } %40, 0
    %43 = getelementptr inbounds %int* %42, %int %41
    %44 = load %"_Pshadow_Pstandard_CArray"** %this
    %45 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %44, i32 0, i32 3
    %46 = load { %int*, [1 x %int] }* %45
    %47 = load %int* %i
    %48 = extractvalue { %int*, [1 x %int] } %46, 0
    %49 = getelementptr inbounds %int* %48, %int %47
    %50 = load %int* %43
    store %int %50, %int* %49
    %51 = load %int* %i
    %52 = add %int %51, 1
    store %int %52, %int* %i
    br label %_label4
_label4:
    %53 = load { %int*, [1 x %int] }* %lengths
    %54 = load %int* %i
    %55 = call i8* @malloc(i32 ptrtoint (%"_Pshadow_Pstandard_CArray"* getelementptr (%"_Pshadow_Pstandard_CArray"* null, i32 1) to i32))
    %56 = bitcast i8* %55 to %"_Pshadow_Pstandard_CArray"*
    %57 = extractvalue { %int*, [1 x %int] } %53, 0
    %58 = ptrtoint %int* %57 to %long
    %59 = extractvalue { %int*, [1 x %int] } %53, 1
    %60 = call i8* @malloc(i32 ptrtoint ([1 x %int]* getelementptr ([1 x %int]* null, i32 1) to i32))
    %61 = bitcast i8* %60 to [1 x %int]*
    store [1 x %int] %59, [1 x %int]* %61
    %62 = getelementptr inbounds [1 x %int]* %61, i32 0, i32 0
    %63 = insertvalue { %int*, [1 x %int] } { %int* null, [1 x %int] [%int 1] }, %int* %62, 0
    %64 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_R_Pshadow_Pstandard_Clong_R_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CArray"* %56, %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%"_Pshadow_Pstandard_Cint_Mclass"* @"_Pshadow_Pstandard_Cint_Mclass", i32 0, i32 0), %long %58, { %int*, [1 x %int] } %63)
    %65 = getelementptr %"_Pshadow_Pstandard_CArray"* %64, i32 0, i32 0
    %66 = load %"_Pshadow_Pstandard_CArray_Mclass"** %65
    %67 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %66, i32 0, i32 11
    %68 = load %int (%"_Pshadow_Pstandard_CArray"*)** %67
    %69 = call %int %68(%"_Pshadow_Pstandard_CArray"* %64)
    %70 = icmp slt %int %54, %69
    br %boolean %70, label %_label3, label %_label5
_label5:
    %71 = load %"_Pshadow_Pstandard_CArray"** %this
    %72 = load %"_Pshadow_Pstandard_CArray"** %this
    %73 = getelementptr %"_Pshadow_Pstandard_CArray"* %71, i32 0, i32 0
    %74 = load %"_Pshadow_Pstandard_CArray_Mclass"** %73
    %75 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %74, i32 0, i32 11
    %76 = load %int (%"_Pshadow_Pstandard_CArray"*)** %75
    %77 = call %int %76(%"_Pshadow_Pstandard_CArray"* %71)
    %78 = getelementptr %"_Pshadow_Pstandard_CArray"* %72, i32 0, i32 0
    %79 = load %"_Pshadow_Pstandard_CArray_Mclass"** %78
    %80 = getelementptr %"_Pshadow_Pstandard_CArray_Mclass"* %79, i32 0, i32 7
    %81 = load %long (%"_Pshadow_Pstandard_CArray"*, %int)** %80
    %82 = call %long %81(%"_Pshadow_Pstandard_CArray"* %72, %int %77)
    %83 = load %"_Pshadow_Pstandard_CArray"** %this
    %84 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %83, i32 0, i32 2
    store %long %82, %long* %84
    %85 = load %"_Pshadow_Pstandard_CArray"** %this
    ret %"_Pshadow_Pstandard_CArray"* %85
}

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

@_array0 = private unnamed_addr constant [24 x %ubyte] c"shadow.standard@Array<T>"
@_string0 = private unnamed_addr constant %"_Pshadow_Pstandard_CString" { %"_Pshadow_Pstandard_CString_Mclass"* @"_Pshadow_Pstandard_CString_Mclass", { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([24 x %ubyte]* @_array0, i32 0, i32 0), [1 x %int] [%int 24] } }
