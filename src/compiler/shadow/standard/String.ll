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

declare noalias i8* @malloc(i64)
declare noalias i8* @calloc(i64, i64)

%"Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"Interface$methods" = external constant %"Interface$methods"
%"Interface" = type { %"Interface$methods"* }
declare %boolean @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@String"* @"Interface$$toString"(%"shadow.standard@Interface"*)
declare %"shadow.standard@Interface"* @"Interface$$create"(%"shadow.standard@Interface"*)

%"String$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %int (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %int)*, %int (%"shadow.standard@String"*)* }
@"String$methods" = constant %"String$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@String"*)* @"String$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@String"*)* @"String$$toString", %int (%"shadow.standard@String"*, %"shadow.standard@String"*)* @"String$$compare$_Pshadow_Pstandard_CString", %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)* @"String$$equals$_Pshadow_Pstandard_CString", %ubyte (%"shadow.standard@String"*, %int)* @"String$$getByte$int", %int (%"shadow.standard@String"*)* @"String$$length" }
%"String" = type { %"String$methods"*, { %ubyte*, [1 x %long] } }

%"Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"Class$methods" = external constant %"Class$methods"
%"Class" = type { %"Class$methods"* }
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CClass"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@Class"* @"Class$$parent"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"Class$$toString"(%"shadow.standard@Class"*)
declare %"shadow.standard@Class"* @"Class$$create"(%"shadow.standard@Class"*)

%"Array<T>$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array<T>"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %int (%"shadow.standard@Array<T>"*)*, %long (%"shadow.standard@Array<T>"*, %int)*, void (%"shadow.standard@Array<T>"*, %int, %long)*, %int (%"shadow.standard@Array<T>"*)*, { %int*, [1 x %long] } (%"shadow.standard@Array<T>"*)* }
@"Array<T>$methods" = external constant %"Array<T>$methods"
%"Array<T>" = type { %"Array<T>$methods"* }
declare %long @"Array<T>$$index$int"(%"shadow.standard@Array<T>"*, %int)
declare void @"Array<T>$$index$int$T"(%"shadow.standard@Array<T>"*, %int, %long)
declare %int @"Array<T>$$dims"(%"shadow.standard@Array<T>"*)
declare %int @"Array<T>$$length"(%"shadow.standard@Array<T>"*)
declare { %int*, [1 x %long] } @"Array<T>$$lengths"(%"shadow.standard@Array<T>"*)
declare %"shadow.standard@Array<T>"* @"Array<T>$$create"(%"shadow.standard@Array<T>"*)

%"Object$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"Object$methods" = external constant %"Object$methods"
%"Object" = type { %"Object$methods"* }
declare %"shadow.standard@Class"* @"Object$$getClass"(%"shadow.standard@Object"*)
declare %boolean @"Object$$equals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$freeze"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"Object$$toString"(%"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$create"(%"shadow.standard@Object"*)
declare %boolean @"Object$$referenceEquals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$copy"(%"shadow.standard@Object"*)

@"Interface$class" = external constant %"shadow.standard@Class"
@"String$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"Class$class" = external constant %"shadow.standard@Class"
@"Array<T>$class" = external constant %"shadow.standard@Class"
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"String$$getClass"(%"shadow.standard@String"*) {
    ret %"shadow.standard@Class"* @"String$class"
}

define %boolean @"String$$equals$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    %other = alloca %"shadow.standard@String"*
    %i = alloca %int
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %other
    %3 = load %"shadow.standard@String"** %this
    %4 = load %"shadow.standard@String"** %other
    %5 = getelementptr %"shadow.standard@String"* %3, i32 0, i32 0
    %6 = load %"String$methods"** %5
    %7 = getelementptr %"String$methods"* %6, i32 0, i32 9
    %8 = load %int (%"shadow.standard@String"*)** %7
    %9 = call %int %)
    %10 = getelementptr %"shadow.standard@String"* %4, i32 0, i32 0
    %11 = load %"String$methods"** %10
    %12 = getelementptr %"String$methods"* %11, i32 0, i32 9
    %13 = load %int (%"shadow.standard@String"*)** %12
    %14 = call %int %1)
    %15 = icmp ne %int %9, %14
    br %boolean %15, label %.label0, label %.label1
.label0:
    ret %boolean false
    br label %.label2
.label1:
    br label %.label2
.label2:
    store %int 0, %int* %i
    br label %.label4
.label3:
    %17 = load %"shadow.standard@String"** %this
    %18 = load %int* %i
    %19 = getelementptr %"shadow.standard@String"* %17, i32 0, i32 0
    %20 = load %"String$methods"** %19
    %21 = getelementptr %"String$methods"* %20, i32 0, i32 8
    %22 = load %ubyte (%"shadow.standard@String"*, %int)** %21
    %23 = call %ubyte %22(%"shadow.standard@String"* %17, %int %18)
    %24 = load %"shadow.standard@String"** %other
    %25 = load %int* %i
    %26 = getelementptr %"shadow.standard@String"* %24, i32 0, i32 0
    %27 = load %"String$methods"** %26
    %28 = getelementptr %"String$methods"* %27, i32 0, i32 8
    %29 = load %ubyte (%"shadow.standard@String"*, %int)** %28
    %30 = call %ubyte %29(%"shadow.standard@String"* %24, %int %25)
    %31 = icmp ne %ubyte %23, %30
    br %boolean %31, label %.label6, label %.label7
.label6:
    ret %boolean false
    br label %.label8
.label7:
    br label %.label8
.label8:
    %33 = load %int* %i
    %34 = add %int %33, 1
    store %int %34, %int* %i
    br label %.label4
.label4:
    %35 = load %int* %i
    %36 = icmp slt %int %35, 0
    br %boolean %36, label %.label3, label %.label5
.label5:
    ret %boolean true
}
define %ubyte @"String$$getByte$int"(%"shadow.standard@String"*, %int) {
    %this = alloca %"shadow.standard@String"*
    %index = alloca %int
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %int %1, %int* %index
    %3 = load %"shadow.standard@String"** %this
    %4 = getelementptr inbounds %"shadow.standard@String"* %3, i32 0, i32 1
    %5 = load { %ubyte*, [1 x %long] }* %4
    %6 = load %int* %index
    %7 = sext %int %6 to %long
    %8 = extractvalue { %ubyte*, [1 x %long] } %5, 0
    %9 = getelementptr %ubyte* %8, %long %7
    %10 = load %ubyte* %9
    ret %ubyte %10
}
define %int @"String$$compare$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    %other = alloca %"shadow.standard@String"*
    %i = alloca %int
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %other
    store %int 0, %int* %i
    br label %.label10
.label9:
    %3 = load %"shadow.standard@String"** %this
    %4 = load %int* %i
    %5 = getelementptr %"shadow.standard@String"* %3, i32 0, i32 0
    %6 = load %"String$methods"** %5
    %7 = getelementptr %"String$methods"* %6, i32 0, i32 8
    %8 = load %ubyte (%"shadow.standard@String"*, %int)** %7
    %9 = call %ubyte %8(%"shadow.standard@String"* %3, %int %4)
    %10 = load %"shadow.standard@String"** %other
    %11 = load %int* %i
    %12 = getelementptr %"shadow.standard@String"* %10, i32 0, i32 0
    %13 = load %"String$methods"** %12
    %14 = getelementptr %"String$methods"* %13, i32 0, i32 8
    %15 = load %ubyte (%"shadow.standard@String"*, %int)** %14
    %16 = call %ubyte %15(%"shadow.standard@String"* %10, %int %11)
    %17 = icmp ne %ubyte %9, %16
    br %boolean %17, label %.label12, label %.label13
.label12:
    %18 = load %"shadow.standard@String"** %this
    %19 = load %int* %i
    %20 = getelementptr %"shadow.standard@String"* %18, i32 0, i32 0
    %21 = load %"String$methods"** %20
    %22 = getelementptr %"String$methods"* %21, i32 0, i32 8
    %23 = load %ubyte (%"shadow.standard@String"*, %int)** %22
    %24 = call %ubyte %23(%"shadow.standard@String"* %18, %int %19)
    %25 = load %"shadow.standard@String"** %other
    %26 = load %int* %i
    %27 = getelementptr %"shadow.standard@String"* %25, i32 0, i32 0
    %28 = load %"String$methods"** %27
    %29 = getelementptr %"String$methods"* %28, i32 0, i32 8
    %30 = load %ubyte (%"shadow.standard@String"*, %int)** %29
    %31 = call %ubyte %30(%"shadow.standard@String"* %25, %int %26)
    %32 = sub %ubyte %24, %31
    %33 = sext %ubyte %32 to %int
    ret %int %33
    br label %.label14
.label13:
    br label %.label14
.label14:
    %35 = load %int* %i
    %36 = add %int %35, 1
    store %int %36, %int* %i
    br label %.label10
.label10:
    %37 = load %int* %i
    %38 = icmp slt %int %37, 0
    %39 = load %int* %i
    %40 = icmp slt %int %39, 0
    %41 = and %boolean %38, %40
    br %boolean %41, label %.label9, label %.label11
.label11:
    %42 = sub %int 0, 0
    ret %int %42
}
define %int @"String$$length"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    ret %int 0
}
define %"shadow.standard@String"* @"String$$toString"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    ret %"shadow.standard@String"* %2
}
define %"shadow.standard@String"* @"String$$create"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    %3 = getelementptr %"shadow.standard@String"* %2, i32 0, i32 0
    store %"String$methods"* @"String$methods", %"String$methods"** %3
    %4 = load %"shadow.standard@String"** %this
    %5 = getelementptr inbounds %"shadow.standard@String"* %4, i32 0, i32 1
    %6 = call noalias i8* @calloc(i64 0, i64 ptrtoint (%ubyte* getelementptr (%ubyte* null, i32 1) to i64))
    %7 = bitcast i8* %6 to %ubyte*
    %8 = insertvalue { %ubyte*, [1 x %long] } undef, %ubyte* %7, 0
    %9 = insertvalue { %ubyte*, [1 x %long] } %8, %int 0, 1, 0
    store { %ubyte*, [1 x %long] } %9, { %ubyte*, [1 x %long] }* %5
    %10 = load %"shadow.standard@String"** %this
    ret %"shadow.standard@String"* %10
}

@.array0 = private unnamed_addr constant [6 x %ubyte] c"String"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([6 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 6] } }
