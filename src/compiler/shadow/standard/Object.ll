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

%"shadow.standard@Class$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = external constant %"shadow.standard@Class$methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, %"shadow.standard@Class"* }
declare %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$getClassName"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*)

%"shadow.standard@Object$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.standard@Object$methods" = constant %"shadow.standard@Object$methods" { %"shadow.standard@Class"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$getClass", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString" }
%"shadow.standard@Object" = type { %"shadow.standard@Object$methods"* }

%"shadow.standard@String$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)* }
@"shadow.standard@String$methods" = external constant %"shadow.standard@String$methods"
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }
declare %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*)
declare void @"shadow.standard@String$$constructor"(%"shadow.standard@String"*)

@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* null }
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.standard@Object$$getClass"(%"shadow.standard@Object"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Object$class"
}

define %"shadow.standard@String"* @"shadow.standard@Object$$toString"(%"shadow.standard@Object"*) {
    %this = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %this
    %2 = load %"shadow.standard@Object"** %this
    %3 = getelementptr %"shadow.standard@Object"* %2, i32 0, i32 0
    %4 = load %"shadow.standard@Object$methods"** %3
    %5 = getelementptr %"shadow.standard@Object$methods"* %4, i32 0, i32 0
    %6 = load %"shadow.standard@Class"* (%"shadow.standard@Object"*)** %5
    %7 = call %"shadow.standard@Class"* %6(%"shadow.standard@Object"* %2)
    %8 = getelementptr %"shadow.standard@Class"* %7, i32 0, i32 0
    %9 = load %"shadow.standard@Class$methods"** %8
    %10 = getelementptr %"shadow.standard@Class$methods"* %9, i32 0, i32 2
    %11 = load %"shadow.standard@String"* (%"shadow.standard@Class"*)** %10
    %12 = call %"shadow.standard@String"* %11(%"shadow.standard@Class"* %7)
    ret %"shadow.standard@String"* %12
}
define void @"shadow.standard@Object$$constructor"(%"shadow.standard@Object"*) {
    %this = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %this
    %2 = load %"shadow.standard@Object"** %this
    %3 = getelementptr %"shadow.standard@Object"* %2, i32 0, i32 0
    store %"shadow.standard@Object$methods"* @"shadow.standard@Object$methods", %"shadow.standard@Object$methods"** %3
    ret void
}

@.array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@Object"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([22 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 22] } }
