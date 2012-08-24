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

%"shadow.standard@Class$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = constant %"shadow.standard@Class$methods" { %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getClass", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$toString", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getClassName", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getSuperClass" }
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, %"shadow.standard@Class"* }

%"shadow.standard@Object$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.standard@Object$methods" = external constant %"shadow.standard@Object$methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object$methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object$$getClass"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object$$toString"(%"shadow.standard@Object"*)
declare void @"shadow.standard@Object$$constructor"(%"shadow.standard@Object"*)

%"shadow.standard@String$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)* }
@"shadow.standard@String$methods" = external constant %"shadow.standard@String$methods"
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }
declare %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*)
declare void @"shadow.standard@String$$constructor"(%"shadow.standard@String"*)

@"shadow.standard@Class$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.standard@Class$$getClass"(%"shadow.standard@Class"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Class$class"
}

define %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 2
    %4 = load %"shadow.standard@Class"** %3
    ret %"shadow.standard@Class"* %4
}
define %"shadow.standard@String"* @"shadow.standard@Class$$getClassName"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 1
    %4 = load %"shadow.standard@String"** %3
    ret %"shadow.standard@String"* %4
}
define %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr %"shadow.standard@Class"* %2, i32 0, i32 0
    %4 = load %"shadow.standard@Class$methods"** %3
    %5 = getelementptr %"shadow.standard@Class$methods"* %4, i32 0, i32 2
    %6 = load %"shadow.standard@String"* (%"shadow.standard@Class"*)** %5
    %7 = call %"shadow.standard@String"* %6(%"shadow.standard@Class"* %2)
    ret %"shadow.standard@String"* %7
}
define void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr %"shadow.standard@Class"* %2, i32 0, i32 0
    store %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@Class$methods"** %3
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 1
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %5
    %6 = load %"shadow.standard@Class"** %this
    %7 = getelementptr inbounds %"shadow.standard@Class"* %6, i32 0, i32 2
    store %"shadow.standard@Class"* null, %"shadow.standard@Class"** %7
    ret void
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard@Class"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
