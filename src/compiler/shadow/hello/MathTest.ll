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

define void @_Mconstructor() {
entry:
	ret void
}

define void @_Mmain_R_Pshadow_Pstandard_CString_A1() {
entry:
	%a = alloca %int
	%0 = sub %int 0, 5
	%1 = mul %int 33, 2
	br label %2
; %2:
	br %boolean true, label %4, label %3
; %3:
	br label %4
; %4:
	%5 = phi %boolean [ true, %2 ], [ false, %3 ]
	br %boolean %5, label %6, label %7
; %6:
	br label %8
; %7:
	br label %8
; %8:
	%9 = phi %int [ 1, %6 ], [ 2, %7 ]
	%10 = srem %int %1, %9
	%11 = add %int %0, %10
	store %int %11, %int* %a
	%a.0 = load %int* %a
	%12 = add %int %a.0, 1
	store %int %12, %int* %a
	%a.1 = load %int* %a
	%13 = sdiv %int %a.1, 15
	store %int %13, %int* %a
	%14 = sub %int 1, 2
	%a.2 = load %int* %a
	%15 = sub %int %a.2, %14
	store %int %15, %int* %a
	%a.3 = load %int* %a
	%16 = srem %int %a.3, 5
	store %int %16, %int* %a
	br label %19
; %17:
	%a.4 = load %int* %a
	%18 = sdiv %int %a.4, 2
	store %int %18, %int* %a
	br label %19
; %19:
	%a.5 = load %int* %a
	%20 = icmp ne %int %a.5, 0
	br %boolean %20, label %17, label %21
; %21:
	br label %22
; %22:
	%a.6 = load %int* %a
	%23 = add %int %a.6, 10
	store %int %23, %int* %a
	br label %24
; %24:
	%a.7 = load %int* %a
	%25 = icmp slt %int %a.7, 10
	br %boolean %25, label %22, label %26
; %26:
	%28 = sub %int 0, 5
	%30 = add %long 0, 5
	%32 = sub %long 0, 5
	%quot = alloca %int
	store %int 0, %int* %quot
	%rem = alloca %int
	store %int 0, %int* %rem
	store struct null * %34, struct null ** %null
	%longQuot = alloca %long
	store %long 0, %long* %longQuot
	%longRem = alloca %long
	store %long 0, %long* %longRem
	store struct null * %36, struct null ** %null
	%39 = sub %double 0, 1.3
	%41 = zext %int 1 to %double
	%43 = sub %int 0, 1
	%44 = zext %int %43 to %double
	%47 = sub %double 0, 1.3
	%49 = zext %int 1 to %double
	%51 = sub %int 0, 1
	%52 = zext %int %51 to %double
	%55 = sub %double 0, 1.3
	%57 = zext %int 0 to %double
	%59 = zext %int 0 to %double
	%62 = zext %int 1 to %double
	%64 = zext %int 1 to %double
	%66 = zext %int 1 to %double
	%68 = zext %int 5 to %double
	%69 = zext %int 3 to %double
	%71 = zext %int 0 to %double
	%73 = zext %int 0 to %double
	%75 = zext %int 0 to %double
	%77 = zext %int 33 to %double
	%79 = zext %int 1 to %double
	%82 = sub %int 0, 5
	%88 = zext %int 101 to %double
	%90 = zext %int 2 to %double
	%91 = zext %int 9 to %double
	ret void
}

