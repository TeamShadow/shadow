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

declare noalias i8* @malloc(i64)
declare noalias i8* @calloc(i64, i64)

%"Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"Interface$methods" = external constant %"Interface$methods"
%"Interface" = type { %"Interface$methods"* }
declare %boolean @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@String"* @"Interface$$toString"(%"shadow.standard@Interface"*)
declare %"shadow.standard@Interface"* @"Interface$$create"(%"shadow.standard@Interface"*)

%"String$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %int (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %int)*, %int (%"shadow.standard@String"*)* }
@"String$methods" = external constant %"String$methods"
%"String" = type { %"String$methods"* }
declare %boolean @"String$$equals$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %ubyte @"String$$getByte$int"(%"shadow.standard@String"*, %int)
declare %int @"String$$compare$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %int @"String$$length"(%"shadow.standard@String"*)
declare %"shadow.standard@String"* @"String$$toString"(%"shadow.standard@String"*)
declare %"shadow.standard@String"* @"String$$create"(%"shadow.standard@String"*)

%"Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"Class$methods" = external constant %"Class$methods"
%"Class" = type { %"Class$methods"* }
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CClass"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@Class"* @"Class$$parent"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"Class$$toString"(%"shadow.standard@Class"*)
declare %"shadow.standard@Class"* @"Class$$create"(%"shadow.standard@Class"*)

%"Array<T>$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array<T>"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %int (%"shadow.standard@Array<T>"*)*, %long (%"shadow.standard@Array<T>"*, %int)*, void (%"shadow.standard@Array<T>"*, %int, %long)*, %int (%"shadow.standard@Array<T>"*)*, { %int*, [1 x %long] } (%"shadow.standard@Array<T>"*)* }
@"Array<T>$methods" = constant %"Array<T>$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Array<T>"*)* @"Array<T>$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"Object$$toString", %int (%"shadow.standard@Array<T>"*)* @"Array<T>$$dims", %long (%"shadow.standard@Array<T>"*, %int)* @"Array<T>$$index$int", void (%"shadow.standard@Array<T>"*, %int, %long)* @"Array<T>$$index$int$T", %int (%"shadow.standard@Array<T>"*)* @"Array<T>$$length", { %int*, [1 x %long] } (%"shadow.standard@Array<T>"*)* @"Array<T>$$lengths" }
%"Array<T>" = type { %"Array<T>$methods"*, %long, { %int*, [1 x %long] } }

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
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"Array<T>$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"Array<T>$$getClass"(%"shadow.standard@Array<T>"*) {
    ret %"shadow.standard@Class"* @"Array<T>$class"
}

declare %long @"Array<T>$$index$int"(%"shadow.standard@Array<T>"*, %int)
declare void @"Array<T>$$index$int$T"(%"shadow.standard@Array<T>"*, %int, %long)
define %int @"Array<T>$$dims"(%"shadow.standard@Array<T>"*) {
    %this = alloca %"shadow.standard@Array<T>"*
    store %"shadow.standard@Array<T>"* %0, %"shadow.standard@Array<T>"** %this
    ret %int 0
}
define %int @"Array<T>$$length"(%"shadow.standard@Array<T>"*) {
    %this = alloca %"shadow.standard@Array<T>"*
    %length = alloca %int
    %i = alloca %int
    store %"shadow.standard@Array<T>"* %0, %"shadow.standard@Array<T>"** %this
    %2 = load %"shadow.standard@Array<T>"** %this
    %3 = getelementptr inbounds %"shadow.standard@Array<T>"* %2, i32 0, i32 2
    %4 = load { %int*, [1 x %long] }* %3
    %5 = sext %int 0 to %long
    %6 = extractvalue { %int*, [1 x %long] } %4, 0
    %7 = getelementptr %int* %6, %long %5
    %8 = load %int* %7
    store %int %8, %int* %length
    store %int 1, %int* %i
    br label %.label1
.label0:
    %9 = load %"shadow.standard@Array<T>"** %this
    %10 = getelementptr inbounds %"shadow.standard@Array<T>"* %9, i32 0, i32 2
    %11 = load { %int*, [1 x %long] }* %10
    %12 = load %int* %i
    %13 = sext %int %12 to %long
    %14 = extractvalue { %int*, [1 x %long] } %11, 0
    %15 = getelementptr %int* %14, %long %13
    %16 = load %int* %length
    %17 = load %int* %15
    %18 = mul %int %16, %17
    store %int %18, %int* %length
    %19 = load %int* %i
    %20 = add %int %19, 1
    store %int %20, %int* %i
    br label %.label1
.label1:
    %21 = load %int* %i
    %22 = icmp slt %int %21, 0
    br %boolean %22, label %.label0, label %.label2
.label2:
    %23 = load %int* %length
    ret %int %23
}
define { %int*, [1 x %long] } @"Array<T>$$lengths"(%"shadow.standard@Array<T>"*) {
    %this = alloca %"shadow.standard@Array<T>"*
    store %"shadow.standard@Array<T>"* %0, %"shadow.standard@Array<T>"** %this
    ret void
}
define %"shadow.standard@Array<T>"* @"Array<T>$$create$long$int_A1"(%"shadow.standard@Array<T>"*, %long, { %int*, [1 x %long] }) {
    %this = alloca %"shadow.standard@Array<T>"*
    %data = alloca %long
    %lengths = alloca { %int*, [1 x %long] }
    store %"shadow.standard@Array<T>"* %0, %"shadow.standard@Array<T>"** %this
    store %long %1, %long* %data
    store { %int*, [1 x %long] } %2, { %int*, [1 x %long] }* %lengths
    %4 = load %"shadow.standard@Array<T>"** %this
    %5 = getelementptr %"shadow.standard@Array<T>"* %4, i32 0, i32 0
    store %"Array<T>$methods"* @"Array<T>$methods", %"Array<T>$methods"** %5
    %6 = load %"shadow.standard@Array<T>"** %this
    %7 = getelementptr inbounds %"shadow.standard@Array<T>"* %6, i32 0, i32 2
    %8 = call noalias i8* @calloc(i64 0, i64 ptrtoint (%int* getelementptr (%int* null, i32 1) to i64))
    %9 = bitcast i8* %8 to %int*
    %10 = insertvalue { %int*, [1 x %long] } undef, %int* %9, 0
    %11 = insertvalue { %int*, [1 x %long] } %10, %int 0, 1, 0
    store { %int*, [1 x %long] } %11, { %int*, [1 x %long] }* %7
    %12 = load %"shadow.standard@Array<T>"** %this
    %13 = getelementptr inbounds %"shadow.standard@Array<T>"* %12, i32 0, i32 1
    %14 = sext %int 0 to %long
    store %long %14, %long* %13
    %15 = load %"shadow.standard@Array<T>"** %this
    %16 = getelementptr inbounds %"shadow.standard@Array<T>"* %15, i32 0, i32 1
    %17 = load %long* %data
    store %long %17, %long* %16
    %18 = load %"shadow.standard@Array<T>"** %this
    %19 = getelementptr inbounds %"shadow.standard@Array<T>"* %18, i32 0, i32 2
    %20 = load { %int*, [1 x %long] }* %lengths
    store { %int*, [1 x %long] } %20, { %int*, [1 x %long] }* %19
    %21 = load %"shadow.standard@Array<T>"** %this
    ret %"shadow.standard@Array<T>"* %21
}

@.array0 = private unnamed_addr constant [8 x %ubyte] c"Array<T>"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([8 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 8] } }
