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

declare noalias i8* @malloc(i64)
declare noalias i8* @calloc(i64, i64)

%"Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"Interface$methods" = constant %"Interface$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Interface"*)* @"Interface$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Interface"*)* @"Interface$$toString", %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface" }
%"Interface" = type { %"Interface$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"* }

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

@"Interface$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"Array<T>$class" = external constant %"shadow.standard@Class"
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"Interface$$getClass"(%"shadow.standard@Interface"*) {
    ret %"shadow.standard@Class"* @"Interface$class"
}

define %boolean @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    %other = alloca %"shadow.standard@Interface"*
    %i = alloca %long
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    store %"shadow.standard@Interface"* %1, %"shadow.standard@Interface"** %other
    %3 = load %"shadow.standard@Interface"** %this
    %4 = load %"shadow.standard@Interface"** %other
    %5 = icmp eq %"shadow.standard@Interface"* %3, %4
    br %boolean %5, label %.label0, label %.label1
.label0:
    ret %boolean true
    br label %.label2
.label1:
    br label %.label2
.label2:
    %7 = sext %int 0 to %long
    store %long %7, %long* %i
    br label %.label4
.label3:
    %8 = load %"shadow.standard@Interface"** %this
    %9 = getelementptr inbounds %"shadow.standard@Interface"* %8, i32 0, i32 1
    %10 = load { %"shadow.standard@Interface"**, [1 x %long] }* %9
    %11 = load %long* %i
    %12 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %10, 0
    %13 = getelementptr %"shadow.standard@Interface"** %12, %long %11
    %14 = load %"shadow.standard@Interface"** %13
    %15 = icmp ne %"shadow.standard@Interface"* %14, null
    br %boolean %15, label %.label11, label %.label6
.label11:
    %16 = load %"shadow.standard@Interface"** %13
    %17 = load %"shadow.standard@Interface"** %other
    %18 = getelementptr %"shadow.standard@Interface"* %16, i32 0, i32 0
    %19 = load %"Interface$methods"** %18
    %20 = getelementptr %"Interface$methods"* %19, i32 0, i32 6
    %21 = load %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)** %20
    %22 = call %boolean %21(%"shadow.standard@Interface"* %16, %"shadow.standard@Interface"* %17)
    br %boolean %22, label %.label8, label %.label9
.label8:
    ret %boolean true
    br label %.label10
.label9:
    br label %.label10
.label10:
    br label %.label7
.label6:
    br label %.label7
.label7:
    %24 = load %long* %i
    %25 = sext %int 1 to %long
    %26 = add %long %24, %25
    store %long %26, %long* %i
    br label %.label4
.label4:
    %27 = load %long* %i
    %28 = sext %int 0 to %long
    %29 = icmp slt %long %27, %28
    br %boolean %29, label %.label3, label %.label5
.label5:
    ret %boolean false
}
define %"shadow.standard@String"* @"Interface$$toString"(%"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    %2 = load %"shadow.standard@Interface"** %this
    %3 = getelementptr inbounds %"shadow.standard@Interface"* %2, i32 0, i32 2
    %4 = load %"shadow.standard@String"** %3
    ret %"shadow.standard@String"* %4
}
define %"shadow.standard@Interface"* @"Interface$$create"(%"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    %2 = load %"shadow.standard@Interface"** %this
    %3 = getelementptr %"shadow.standard@Interface"* %2, i32 0, i32 0
    store %"Interface$methods"* @"Interface$methods", %"Interface$methods"** %3
    %4 = load %"shadow.standard@Interface"** %this
    %5 = getelementptr inbounds %"shadow.standard@Interface"* %4, i32 0, i32 2
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %5
    %6 = load %"shadow.standard@Interface"** %this
    %7 = getelementptr inbounds %"shadow.standard@Interface"* %6, i32 0, i32 1
    %8 = call noalias i8* @calloc(i64 0, i64 ptrtoint (%"shadow.standard@Interface"** getelementptr (%"shadow.standard@Interface"** null, i32 1) to i64))
    %9 = bitcast i8* %8 to %"shadow.standard@Interface"**
    %10 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } undef, %"shadow.standard@Interface"** %9, 0
    %11 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } %10, %int 0, 1, 0
    store { %"shadow.standard@Interface"**, [1 x %long] } %11, { %"shadow.standard@Interface"**, [1 x %long] }* %7
    %12 = load %"shadow.standard@Interface"** %this
    ret %"shadow.standard@Interface"* %12
}

@.array0 = private unnamed_addr constant [9 x %ubyte] c"Interface"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([9 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 9] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
