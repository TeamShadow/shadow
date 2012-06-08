; AUTO-GENERATED FILE, DO NOT EDIT!
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

%"hello@Numeric!!methods" = type { %"shadow.standard@Class"()*, %"shadow.standard@String"()*, void()* }
@"hello@Numeric!!methods" = constant %"hello@Numeric!!methods" { %"shadow.standard@Class"()* @"shadow.standard@Object!getClass", %"shadow.standard@String"()* @"shadow.standard@Object!toString", void()* @"hello@Numeric!constructor" }
%"hello@Numeric" = type {}

define void @"hello@Numeric!constructor"() {
	ret void
}

define void @"hello@Numeric!main"({ %"shadow.standard@String"*, %int }) {
	%z = alloca %long
	%2 = sext %byte 1 to %short
	%3 = add %short %2, 2
	%4 = sext %short %3 to %int
	%5 = add %int %4, 3
	%6 = sext %int %5 to %long
	store %long %6, %long* %z
	%7 = mul %int 4, 5
	%8 = sext %int %7 to %long
	%9 = sdiv %long %8, 2
	%z.0 = load %long* %z
	%10 = icmp eq %long %z.0, %9
	br %boolean %10, label %11, label %12
; %11:
	call void @"shadow.io@Console!printLine"(%"shadow.standard@String" getelementptr inbounds ([3 x i8]* @string0, i32 0, i32 0))
	br label %13
; %12:
	br label %13
; %13:
	%a = alloca %int
	%14 = add %int 1, 2
	%15 = sub %int %14, 3
	%16 = add %int %15, 4
	store %int %16, %int* %a
	%b = alloca %int
	%17 = add %int 1, 2
	store %int %17, %int* %b
	%c = alloca %int
	%18 = add %int 2, 3
	%19 = add %int 4, 5
	%20 = mul %int %18, %19
	store %int %20, %int* %c
	%a.0 = load %int* %a
	%b.0 = load %int* %b
	%21 = add %int %a.0, %b.0
	store %int %21, %int* %a
	%22 = add %int 3, 4
	%b.1 = load %int* %b
	%23 = add %int %b.1, %22
	store %int %23, %int* %b
	%a.1 = load %int* %a
	%24 = shl %int %a.1, 1
	%b.2 = load %int* %b
	%25 = shl %int %24, %b.2
	store %int %25, %int* %c
	%a.2 = load %int* %a
	%b.3 = load %int* %b
	%26 = mul %int %a.2, %b.3
	%c.0 = load %int* %c
	%27 = shl %int %c.0, %26
	store %int %27, %int* %c
	%a.3 = load %int* %a
	%b.4 = load %int* %b
	%28 = or %int %a.3, %b.4
	store %int %28, %int* %a
	%a.4 = load %int* %a
	%b.5 = load %int* %b
	%29 = and %int %a.4, %b.5
	store %int %29, %int* %a
	%a.5 = load %int* %a
	%b.6 = load %int* %b
	%30 = xor %int %a.5, %b.6
	store %int %30, %int* %a
	%b.7 = load %int* %b
	%a.6 = load %int* %a
	%31 = or %int %b.7, %a.6
	store %int %31, %int* %b
	%b.8 = load %int* %b
	%a.7 = load %int* %a
	%32 = and %int %b.8, %a.7
	store %int %32, %int* %b
	%b.9 = load %int* %b
	%a.8 = load %int* %a
	%33 = xor %int %b.9, %a.8
	store %int %33, %int* %b
	ret void
}

