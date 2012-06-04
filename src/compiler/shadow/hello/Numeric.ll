%boolean = type i1
%code = type i32
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

@string0 = constant [3 x i8] c"Yes"

define void @"hello@Numeric%constructor"() {
	ret void
}

define void @"hello@Numeric%main"({ %"shadow.standard@String"*, %int }) {
	%z = alloca %long
	%1 = sext %byte 1 to %short
	%2 = add %short %1, 2
	%3 = sext %short %2 to %int
	%4 = add %int %3, 3
	%5 = sext %int %4 to %long
	store %long %5, %long* %z
	%6 = mul %int 4, 5
	%7 = sext %int %6 to %long
	%8 = sdiv %long %7, 2
	%z.0 = load %long* %z
	%9 = icmp eq %long %z.0, %8
	br %boolean %9, label %10, label %11
; %10:
	call void @"shadow.io@Console%printLine"(getelementptr inbounds ([3 x i8]* @string0, i32 0, i32 0))
	br label %12
; %11:
	br label %12
; %12:
	%a = alloca %int
	%13 = add %int 1, 2
	%14 = sub %int %13, 3
	%15 = add %int %14, 4
	store %int %15, %int* %a
	%b = alloca %int
	%16 = add %int 1, 2
	store %int %16, %int* %b
	%c = alloca %int
	%17 = add %int 2, 3
	%18 = add %int 4, 5
	%19 = mul %int %17, %18
	store %int %19, %int* %c
	%a.0 = load %int* %a
	%b.0 = load %int* %b
	%20 = add %int %a.0, %b.0
	store %int %20, %int* %a
	%21 = add %int 3, 4
	%b.1 = load %int* %b
	%22 = add %int %b.1, %21
	store %int %22, %int* %b
	%a.1 = load %int* %a
	%23 = shl %int %a.1, 1
	%b.2 = load %int* %b
	%24 = shl %int %23, %b.2
	store %int %24, %int* %c
	%a.2 = load %int* %a
	%b.3 = load %int* %b
	%25 = mul %int %a.2, %b.3
	%c.0 = load %int* %c
	%26 = shl %int %c.0, %25
	store %int %26, %int* %c
	%a.3 = load %int* %a
	%b.4 = load %int* %b
	%27 = or %int %a.3, %b.4
	store %int %27, %int* %a
	%a.4 = load %int* %a
	%b.5 = load %int* %b
	%28 = and %int %a.4, %b.5
	store %int %28, %int* %a
	%a.5 = load %int* %a
	%b.6 = load %int* %b
	%29 = xor %int %a.5, %b.6
	store %int %29, %int* %a
	%b.7 = load %int* %b
	%a.6 = load %int* %a
	%30 = or %int %b.7, %a.6
	store %int %30, %int* %b
	%b.8 = load %int* %b
	%a.7 = load %int* %a
	%31 = and %int %b.8, %a.7
	store %int %31, %int* %b
	%b.9 = load %int* %b
	%a.8 = load %int* %a
	%32 = xor %int %b.9, %a.8
	store %int %32, %int* %b
	ret void
}

