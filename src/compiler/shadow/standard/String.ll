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
@"shadow.standard@String$methods" = constant %"shadow.standard@String$methods" { %"shadow.standard@Class"* (%"shadow.standard@String"*)* @"shadow.standard@String$$getClass", %"shadow.standard@String"* (%"shadow.standard@String"*)* @"shadow.standard@String$$toString" }
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }

%"shadow.standard@Array$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard@Array"*)* }
@"shadow.standard@Array$methods" = external constant %"shadow.standard@Array$methods"
%"shadow.standard@Array" = type { %"shadow.standard@Array$methods"*, %long, { %long*, [1 x %long] } }
declare { %long*, [1 x %long] } @"shadow.standard@Array$$getLengths"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getDimensions"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getLength"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getLength$long"(%"shadow.standard@Array"*, %long)
declare void @"shadow.standard@Array$$constructor$long$long[]"(%"shadow.standard@Array"*, %long, { %long*, [1 x %long] })

@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Array$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.standard@String$$getClass"(%"shadow.standard@String"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@String$class"
}

define %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    ret %"shadow.standard@String"* %2
}
define void @"shadow.standard@String$$constructor"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    %3 = getelementptr %"shadow.standard@String"* %2, i32 0, i32 0
    store %"shadow.standard@String$methods"* @"shadow.standard@String$methods", %"shadow.standard@String$methods"** %3
    %4 = load %"shadow.standard@String"** %this
    %5 = getelementptr inbounds %"shadow.standard@String"* %4, i32 0, i32 1
    %6 = sext %int 0 to %long
    %7 = call noalias i8* @calloc(i64 %6, i64 ptrtoint (%ubyte* getelementptr(%ubyte* null, i32 1) to i64))
    %8 = bitcast i8* %7 to %ubyte*
    %9 = insertvalue { %ubyte*, [1 x %long] } undef, %ubyte* %8, 0
    %10 = insertvalue { %ubyte*, [1 x %long] } %9, %long %6, 1, 0
    store { %ubyte*, [1 x %long] } %10, { %ubyte*, [1 x %long] }* %5
    ret void
}

@.array0 = private unnamed_addr constant [22 x %ubyte] c"shadow.standard@String"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([22 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 22] } }
