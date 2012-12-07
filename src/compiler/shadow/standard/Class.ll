; shadow.standard@Class

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
@"Class$methods" = constant %"Class$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"Class$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"Class$$toString", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* @"Class$$isSubtype$_Pshadow_Pstandard_CClass", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)* @"Class$$isSubtype$_Pshadow_Pstandard_CInterface", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"Class$$parent" }
%"Class" = type { %"Class$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"*, %"shadow.standard@Class"* }

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
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"Array<T>$class" = external constant %"shadow.standard@Class"
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"Class$$getClass"(%"shadow.standard@Class"*) {
    ret %"shadow.standard@Class"* @"Class$class"
}

define %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CClass"(%"shadow.standard@Class"*, %"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    %other = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    store %"shadow.standard@Class"* %1, %"shadow.standard@Class"** %other
    %3 = load %"shadow.standard@Class"** %this
    %4 = load %"shadow.standard@Class"** %other
    %5 = icmp eq %"shadow.standard@Class"* %3, %4
    br %boolean %5, label %.label0, label %.label1
.label0:
    ret %boolean true
    br label %.label2
.label1:
    br label %.label2
.label2:
    %7 = load %"shadow.standard@Class"** %this
    %8 = getelementptr inbounds %"shadow.standard@Class"* %7, i32 0, i32 3
    %9 = load %"shadow.standard@Class"** %8
    %10 = icmp ne %"shadow.standard@Class"* %9, null
    br %boolean %10, label %.label5, label %.label3
.label5:
    %11 = load %"shadow.standard@Class"** %8
    %12 = load %"shadow.standard@Class"** %other
    %13 = getelementptr %"shadow.standard@Class"* %11, i32 0, i32 0
    %14 = load %"Class$methods"** %13
    %15 = getelementptr %"Class$methods"* %14, i32 0, i32 6
    %16 = load %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)** %15
    %17 = call %boolean %16(%"shadow.standard@Class"* %11, %"shadow.standard@Class"* %12)
    ret %boolean %17
    br label %.label4
.label3:
    br label %.label4
.label4:
    ret %boolean false
}
define %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Class"*
    %other = alloca %"shadow.standard@Interface"*
    %i = alloca %long
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    store %"shadow.standard@Interface"* %1, %"shadow.standard@Interface"** %other
    %3 = sext %int 0 to %long
    store %long %3, %long* %i
    br label %.label7
.label6:
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 1
    %6 = load { %"shadow.standard@Interface"**, [1 x %long] }* %5
    %7 = load %long* %i
    %8 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %6, 0
    %9 = getelementptr %"shadow.standard@Interface"** %8, %long %7
    %10 = load %"shadow.standard@Interface"** %9
    %11 = icmp ne %"shadow.standard@Interface"* %10, null
    br %boolean %11, label %.label14, label %.label9
.label14:
    %12 = load %"shadow.standard@Interface"** %9
    %13 = load %"shadow.standard@Interface"** %other
    %14 = getelementptr %"shadow.standard@Interface"* %12, i32 0, i32 0
    %15 = load %"Interface$methods"** %14
    %16 = getelementptr %"Interface$methods"* %15, i32 0, i32 6
    %17 = load %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)** %16
    %18 = call %boolean %17(%"shadow.standard@Interface"* %12, %"shadow.standard@Interface"* %13)
    br %boolean %18, label %.label11, label %.label12
.label11:
    ret %boolean true
    br label %.label13
.label12:
    br label %.label13
.label13:
    br label %.label10
.label9:
    br label %.label10
.label10:
    %20 = load %long* %i
    %21 = sext %int 1 to %long
    %22 = add %long %20, %21
    store %long %22, %long* %i
    br label %.label7
.label7:
    %23 = load %long* %i
    %24 = sext %int 0 to %long
    %25 = icmp slt %long %23, %24
    br %boolean %25, label %.label6, label %.label8
.label8:
    ret %boolean false
}
define %"shadow.standard@Class"* @"Class$$parent"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    ret void
}
define %"shadow.standard@String"* @"Class$$toString"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 2
    %4 = load %"shadow.standard@String"** %3
    ret %"shadow.standard@String"* %4
}
define %"shadow.standard@Class"* @"Class$$create"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr %"shadow.standard@Class"* %2, i32 0, i32 0
    store %"Class$methods"* @"Class$methods", %"Class$methods"** %3
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 2
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %5
    %6 = load %"shadow.standard@Class"** %this
    %7 = getelementptr inbounds %"shadow.standard@Class"* %6, i32 0, i32 3
    store %"shadow.standard@Class"* null, %"shadow.standard@Class"** %7
    %8 = load %"shadow.standard@Class"** %this
    %9 = getelementptr inbounds %"shadow.standard@Class"* %8, i32 0, i32 1
    %10 = call noalias i8* @calloc(i64 0, i64 ptrtoint (%"shadow.standard@Interface"** getelementptr (%"shadow.standard@Interface"** null, i32 1) to i64))
    %11 = bitcast i8* %10 to %"shadow.standard@Interface"**
    %12 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } undef, %"shadow.standard@Interface"** %11, 0
    %13 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } %12, %int 0, 1, 0
    store { %"shadow.standard@Interface"**, [1 x %long] } %13, { %"shadow.standard@Interface"**, [1 x %long] }* %9
    %14 = load %"shadow.standard@Class"** %this
    ret %"shadow.standard@Class"* %14
}

@.array0 = private unnamed_addr constant [5 x %ubyte] c"Class"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([5 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 5] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
