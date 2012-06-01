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

define void @_Mconstructor {
entry:
	ret void
}

define void @_Mmain_R_Pshadow_Pstandard_CString_A1 {
entry:
	%z = alloca %long
	%0 = sext %byte 1 to %short
	%1 = add %short %0, 2
	%2 = sext %short %1 to %int
	%3 = add %int %2, 3
	%4 = sext %int %3 to %long
	%load.0 = load %long* %z
	store %long %4, %long* %load.0
	%5 = mul %int 4, 5
	%6 = sext %int %5 to %long
	%7 = sdiv %long %6, 2
	%load.1 = load %long* %z
	%8 = icmp eq %long %load.1, %7
	br %boolean %8, label %9, label %10
9:
	br label %11
10:
	br label %11
11:
	%a = alloca %int
	%12 = add %int 1, 2
	%13 = sub %int %12, 3
	%14 = add %int %13, 4
	%load.2 = load %int* %a
	store %int %14, %int* %load.2
	%b = alloca %int
	%15 = add %int 1, 2
	%load.3 = load %int* %b
	store %int %15, %int* %load.3
	%c = alloca %int
	%16 = add %int 2, 3
	%17 = add %int 4, 5
	%18 = mul %int %16, %17
	%load.4 = load %int* %c
	store %int %18, %int* %load.4
	%load.5 = load %int* %a
	%load.6 = load %int* %b
	%19 = add %int %load.5, %load.6
	%load.7 = load %int* %a
	store %int %19, %int* %load.7
	%20 = add %int 3, 4
	%load.8 = load %int* %b
	%21 = add %int %load.8, %20
	%load.9 = load %int* %b
	store %int %21, %int* %load.9
	%load.10 = load %int* %a
	%22 = shl %int %load.10, 1
	%load.11 = load %int* %b
	%23 = shl %int %22, %load.11
	%load.12 = load %int* %c
	store %int %23, %int* %load.12
	%load.13 = load %int* %a
	%load.14 = load %int* %b
	%24 = mul %int %load.13, %load.14
	%load.15 = load %int* %c
	%25 = shl %int %load.15, %24
	%load.16 = load %int* %c
	store %int %25, %int* %load.16
	%load.17 = load %int* %a
	%load.18 = load %int* %b
	%26 = or %int %load.17, %load.18
	%load.19 = load %int* %a
	store %int %26, %int* %load.19
	%load.20 = load %int* %a
	%load.21 = load %int* %b
	%27 = and %int %load.20, %load.21
	%load.22 = load %int* %a
	store %int %27, %int* %load.22
	%load.23 = load %int* %a
	%load.24 = load %int* %b
	%28 = xor %int %load.23, %load.24
	%load.25 = load %int* %a
	store %int %28, %int* %load.25
	%load.26 = load %int* %b
	%load.27 = load %int* %a
	%29 = or %int %load.26, %load.27
	%load.28 = load %int* %b
	store %int %29, %int* %load.28
	%load.29 = load %int* %b
	%load.30 = load %int* %a
	%30 = and %int %load.29, %load.30
	%load.31 = load %int* %b
	store %int %30, %int* %load.31
	%load.32 = load %int* %b
	%load.33 = load %int* %a
	%31 = xor %int %load.32, %load.33
	%load.34 = load %int* %b
	store %int %31, %int* %load.34
	ret void
}

