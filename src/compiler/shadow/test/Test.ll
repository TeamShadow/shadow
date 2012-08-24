; shadow.test@Test

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

%"shadow.io@Console$methods" = type { %"shadow.standard@Class"* (%"shadow.io@Console"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.io@Console$methods" = external constant %"shadow.io@Console$methods"
%"shadow.io@Console" = type { %"shadow.io@Console$methods"* }
declare void @"shadow.io@Console$static$printLine$boolean"(%boolean)
declare void @"shadow.io@Console$static$printLine$int"(%int)
declare void @"shadow.io@Console$static$printLine$code"(%code)
declare void @"shadow.io@Console$static$printLine$long"(%long)
declare void @"shadow.io@Console$static$printLine$shadow.standard@String"(%"shadow.standard@String"*)
declare void @"shadow.io@Console$static$printLine$shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.io@Console$static$printLine"()
declare void @"shadow.io@Console$static$print$boolean"(%boolean)
declare void @"shadow.io@Console$static$print$shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.io@Console$static$print$int"(%int)
declare void @"shadow.io@Console$static$print$code"(%code)
declare void @"shadow.io@Console$static$print$long"(%long)
declare void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"*)
declare void @"shadow.io@Console$$constructor"(%"shadow.io@Console"*)

%"shadow.test@Test$methods" = type { %"shadow.standard@Class"* (%"shadow.test@Test"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.test@Test$methods" = constant %"shadow.test@Test$methods" { %"shadow.standard@Class"* (%"shadow.test@Test"*)* @"shadow.test@Test$$getClass", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString" }
%"shadow.test@Test" = type { %"shadow.test@Test$methods"*, %"shadow.test@Test"* }

%"shadow.standard@Class$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = external constant %"shadow.standard@Class$methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, %"shadow.standard@Class"* }
declare %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$getClassName"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*)

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

@"shadow.io@Console$class" = external constant %"shadow.standard@Class"
@"shadow.test@Test$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.test@Test$$getClass"(%"shadow.test@Test"*) {
    ret %"shadow.standard@Class"* @"shadow.test@Test$class"
}

define void @"shadow.test@Test$static$main"() {
    %test = alloca %"shadow.test@Test"*
    %1 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.test@Test"* getelementptr(%"shadow.test@Test"* null, i32 1) to i64))
    %2 = bitcast i8* %1 to %"shadow.test@Test"*
    call void @"shadow.test@Test$$constructor"(%"shadow.test@Test"* %2)
    store %"shadow.test@Test"* %2, %"shadow.test@Test"** %test
    %3 = load %"shadow.test@Test"** %test
    %4 = getelementptr inbounds %"shadow.test@Test"* %3, i32 0, i32 1
    %5 = load %"shadow.test@Test"** %4
    %6 = bitcast %"shadow.test@Test"* %5 to %"shadow.standard@Object"*
    call void @"shadow.io@Console$static$printLine$shadow.standard@Object"(%"shadow.standard@Object"* %6)
    call void @"shadow.io@Console$static$printLine$int"(%int 1)
    %7 = load %"shadow.test@Test"** %test
    %8 = getelementptr inbounds %"shadow.test@Test"* %7, i32 0, i32 1
    store %"shadow.test@Test"* null, %"shadow.test@Test"** %8
    call void @"shadow.io@Console$static$printLine$int"(%int 2)
    %9 = load %"shadow.test@Test"** %test
    %10 = getelementptr inbounds %"shadow.test@Test"* %9, i32 0, i32 1
    %11 = load %"shadow.test@Test"** %10
    %12 = bitcast %"shadow.test@Test"* %11 to %"shadow.standard@Object"*
    call void @"shadow.io@Console$static$printLine$shadow.standard@Object"(%"shadow.standard@Object"* %12)
    call void @"shadow.io@Console$static$printLine$int"(%int 3)
    ret void
}
define void @"shadow.test@Test$$constructor"(%"shadow.test@Test"*) {
    %this = alloca %"shadow.test@Test"*
    store %"shadow.test@Test"* %0, %"shadow.test@Test"** %this
    %2 = load %"shadow.test@Test"** %this
    %3 = getelementptr %"shadow.test@Test"* %2, i32 0, i32 0
    store %"shadow.test@Test$methods"* @"shadow.test@Test$methods", %"shadow.test@Test$methods"** %3
    %4 = load %"shadow.test@Test"** %this
    %5 = getelementptr inbounds %"shadow.test@Test"* %4, i32 0, i32 1
    %6 = load %"shadow.test@Test"** %this
    store %"shadow.test@Test"* %6, %"shadow.test@Test"** %5
    ret void
}

@.array0 = private unnamed_addr constant [16 x %ubyte] c"shadow.test@Test"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([16 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 16] } }
