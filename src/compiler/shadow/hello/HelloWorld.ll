%boolean = type i1
%ubyte = type i8
%byte = type i8
%ushort = type i16
%short = type i16
%uint = type i32
%int = type i32
%ulong = type i64
%long = type i64
%float = type float
%double = type double
%code = type i32

define {%uint, struct _Pshadow_Pstandard_CString *} @_MgetBoth {
entry:
	br %boolean %0, label %1, label %10
1:
	br %boolean %2, label %3, label %10
3:
	br %boolean %4, label %5, label %10
5:
	br %boolean %6, label %7, label %10
7:
	br %boolean %8, label %9, label %10
9:
	ret {%uint %times, struct _Pshadow_Pstandard_CString * %message}
	br label %11
10:
	br label %11
11:
	ret {%uint %times, struct _Pshadow_Pstandard_CString * %message}
}

define struct _Pshadow_Pstandard_CString * @_MgetMessage {
entry:
	br %boolean %0, label %1, label %2
1:
	ret struct _Pshadow_Pstandard_CString * %message
	br label %3
2:
	br label %3
3:
	ret struct _Pshadow_Pstandard_CString * %message
}

define void @_Mdoit {
entry:
	br label %3
0:
	%1 = add %uint %i, %uint %1ui
	%2 = add %uint %i, %uint %1ui
	br label %3
3:
	br %boolean %4, label %0, label %5
5:
	ret void
}

define %uint @_MgetTimes {
entry:
	ret %uint %times
}

define struct _Pshadow_Pstandard_CObject * @_MgetSelfObject {
entry:
	ret struct null * %0
}

define {%uint, %uint, %uint, %uint, %uint, %uint, %uint, %uint, %uint, struct _Pshadow_Pstandard_CString *, %uint, struct _Pshadow_Pstandard_CObject *} @_Mfibonacci_Ruint_Ruint {
entry:
	%0 = add %uint %first, %uint %second
	%1 = add %uint %second, %uint %third
	%2 = add %uint %third, %uint %fourth
	%3 = add %uint %fourth, %uint %fifth
	%4 = add %uint %fifth, %uint %sixth
	%5 = add %uint %sixth, %uint %seventh
	%6 = add %uint %seventh, %uint %eighth
	%7 = sub %uint %third, %uint %second
	%8 = add %uint %first, %uint %second
	%9 = add %uint %eighth, %uint %ninth
	ret {%uint %7, %uint %second, %uint %8, %uint %fourth, %uint %fifth, %uint %sixth, %uint %seventh, %uint %eighth, %uint %ninth, struct _Pshadow_Pstandard_CString * %"String Test", %uint %9, struct _Pshadow_Pstandard_CObject * %10}
}

define void @_Mmain_R_Pshadow_Pstandard_CString_A1 {
entry:
	br label %20
0:
	br %boolean %1, label %2, label %3
2:
	br label %4
3:
	br label %4
4:
	%17 = add %uint %fib8, %uint %fib9
	%18 = add %uint %start, %uint %1ui
	%19 = sub %uint %temp, %uint %10000ui
	br label %20
20:
	br %boolean %21, label %0, label %22
22:
	br %boolean %26, label %27, label %77
27:
	br %boolean %31, label %32, label %77
32:
	br %boolean %33, label %34, label %77
34:
	br %boolean %35, label %36, label %77
36:
	br %boolean %37, label %38, label %77
38:
	br %boolean %39, label %40, label %77
40:
	br %boolean %41, label %42, label %77
42:
	br %boolean %44, label %45, label %77
45:
	br %boolean %47, label %48, label %77
48:
	br %boolean %49, label %50, label %77
50:
	br %boolean %52, label %53, label %77
53:
	br %boolean %54, label %55, label %77
55:
	br %boolean %56, label %57, label %77
57:
	br %boolean %67, label %68, label %77
68:
	br %boolean %71, label %72, label %77
72:
	br %boolean %73, label %74, label %77
74:
	br label %78
77:
	br label %78
78:
	ret void
}

define void @_Mconstructor {
entry:
	ret void
}

define void @_Mconstructor_Ruint {
entry:
	ret void
}

define void @_Mconstructor_R_Pshadow_Pstandard_CString {
entry:
	ret void
}

define void @_Mconstructor_Ruint_R_Pshadow_Pstandard_CString {
entry:
	ret void
}

