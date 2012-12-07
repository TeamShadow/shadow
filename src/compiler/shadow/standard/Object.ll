; shadow.standard@Object

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

%"Object$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"Object$methods" = constant %"Object$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Object"*)* @"Object$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"Object$$toString" }
%"Object" = type { %"Object$methods"* }

@"Interface$class" = external constant %"shadow.standard@Class"
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"Object$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* null }

define %"shadow.standard@Class"* @"Object$$getClass"(%"shadow.standard@Object"*) {
    ret %"shadow.standard@Class"* @"Object$class"
}

define %boolean @"Object$$equals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*) {
    %this = alloca %"shadow.standard@Object"*
    %other = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %this
    store %"shadow.standard@Object"* %1, %"shadow.standard@Object"** %other
    %3 = load %"shadow.standard@Object"** %this
    %4 = load %"shadow.standard@Object"** %other
    %5 = getelementptr %"shadow.standard@Object"* %3, i32 0, i32 0
    %6 = load %"Object$methods"** %5
    %7 = getelementptr %"Object$methods"* %6, i32 0, i32 4
    %8 = load %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)** %7
    %9 = call %boolean %8(%"shadow.standard@Object"* %3, %"shadow.standard@Object"* %4)
    ret %boolean %9
}
declare %"shadow.standard@Object"* @"Object$$freeze"(%"shadow.standard@Object"*)
define %"shadow.standard@String"* @"Object$$toString"(%"shadow.standard@Object"*) {
    %this = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %this
    %2 = load %"shadow.standard@Object"** %this
    %3 = getelementptr %"shadow.standard@Object"* %2, i32 0, i32 0
    %4 = load %"Object$methods"** %3
    %5 = getelementptr %"Object$methods"* %4, i32 0, i32 3
    %6 = load %"shadow.standard@Class"* (%"shadow.standard@Object"*)** %5
    %7 = call %"shadow.standard@Class"* %6(%"shadow.standard@Object"* %2)
    %8 = getelementptr %"shadow.standard@Class"* %7, i32 0, i32 0
    %9 = load %"Class$methods"** %8
    %10 = getelementptr %"Class$methods"* %9, i32 0, i32 5
    %11 = load %"shadow.standard@String"* (%"shadow.standard@Class"*)** %10
    %12 = call %"shadow.standard@String"* %11(%"shadow.standard@Class"* %7)
    ret %"shadow.standard@String"* %12
}
define %"shadow.standard@Object"* @"Object$$create"(%"shadow.standard@Object"*) {
    %this = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %this
    %2 = load %"shadow.standard@Object"** %this
    %3 = getelementptr %"shadow.standard@Object"* %2, i32 0, i32 0
    store %"Object$methods"* @"Object$methods", %"Object$methods"** %3
    %4 = load %"shadow.standard@Object"** %this
    ret %"shadow.standard@Object"* %4
}
declare %boolean @"Object$$referenceEquals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$copy"(%"shadow.standard@Object"*)

@.array0 = private unnamed_addr constant [6 x %ubyte] c"Object"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([6 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 6] } }
