; shadow.standard@Array

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

%"shadow.standard@Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = external constant %"shadow.standard@Class$methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, { %"shadow.standard@Class"**, [1 x %long] }, %"shadow.standard@Class"* }
declare %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*)
declare %boolean @"shadow.standard@Class$$isClassSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"shadow.standard@Class$$isInterface"(%"shadow.standard@Class"*)
declare %boolean @"shadow.standard@Class$$isInterfaceSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$getClassName"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*)

%"shadow.standard@Object$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.standard@Object$methods" = external constant %"shadow.standard@Object$methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object$methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object$$getClass"(%"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"shadow.standard@Object$$clone"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object$$toString"(%"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"shadow.standard@Object$$immutableClone"(%"shadow.standard@Object"*)
declare void @"shadow.standard@Object$$constructor"(%"shadow.standard@Object"*)

%"shadow.standard@String$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %long)*, %long (%"shadow.standard@String"*)* }
@"shadow.standard@String$methods" = external constant %"shadow.standard@String$methods"
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }
declare %boolean @"shadow.standard@String$$equals$shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*)
declare %ubyte @"shadow.standard@String$$getCode$long"(%"shadow.standard@String"*, %long)
declare %long @"shadow.standard@String$$getLength"(%"shadow.standard@String"*)
declare void @"shadow.standard@String$$constructor"(%"shadow.standard@String"*)

%"shadow.standard@Array$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard@Array"*)* }
@"shadow.standard@Array$methods" = constant %"shadow.standard@Array$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.standard@Array"*)* @"shadow.standard@Array$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString", %long (%"shadow.standard@Array"*)* @"shadow.standard@Array$$getDimensions", %long (%"shadow.standard@Array"*)* @"shadow.standard@Array$$getLength", %long (%"shadow.standard@Array"*, %long)* @"shadow.standard@Array$$getLength$long", { %long*, [1 x %long] } (%"shadow.standard@Array"*)* @"shadow.standard@Array$$getLengths" }
%"shadow.standard@Array" = type { %"shadow.standard@Array$methods"*, %long, { %long*, [1 x %long] } }

@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Array$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, { %"shadow.standard@Class"**, [1 x %long] } { %"shadow.standard@Class"** null, [1 x %long] [%long 0] }, %"shadow.standard@Class"* @"shadow.standard@Object$class" }

define %"shadow.standard@Class"* @"shadow.standard@Array$$getClass"(%"shadow.standard@Array"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Array$class"
}

define { %long*, [1 x %long] } @"shadow.standard@Array$$getLengths"(%"shadow.standard@Array"*) {
    %this = alloca %"shadow.standard@Array"*
    store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
    %2 = load %"shadow.standard@Array"** %this
    %3 = getelementptr inbounds %"shadow.standard@Array"* %2, i32 0, i32 2
    %4 = load { %long*, [1 x %long] }* %3
    ret { %long*, [1 x %long] } %4
}
define %long @"shadow.standard@Array$$getDimensions"(%"shadow.standard@Array"*) {
    %this = alloca %"shadow.standard@Array"*
    store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
    %2 = load %"shadow.standard@Array"** %this
    %3 = getelementptr inbounds %"shadow.standard@Array"* %2, i32 0, i32 2
    %4 = load { %long*, [1 x %long] }* %3
    %5 = call i8* @malloc(i64 ptrtoint (%"shadow.standard@Array"* getelementptr(%"shadow.standard@Array"* null, i32 1) to i64))
    %6 = bitcast i8* %5 to %"shadow.standard@Array"*
    %7 = extractvalue { %long*, [1 x %long] } %4, 0
    %8 = ptrtoint %long* %7 to %long
    %9 = extractvalue { %long*, [1 x %long] } %4, 1
    %10 = call i8* @malloc(i64 ptrtoint ([1 x %long]* getelementptr([1 x %long]* null, i32 1) to i64))
    %11 = bitcast i8* %10 to [1 x %long]*
    store [1 x %long] %9, [1 x %long]* %11
    %12 = getelementptr [1 x %long]* %11, i32 0, i32 0
    %13 = insertvalue { %long*, [1 x %long] } { %long* null, [1 x %long] [%long 1] }, %long* %12, 0
    call void @"shadow.standard@Array$$constructor$long$long[]"(%"shadow.standard@Array"* %6, %long %8, { %long*, [1 x %long] } %13)
    %14 = sext %int 0 to %long
    %15 = getelementptr %"shadow.standard@Array"* %6, i32 0, i32 0
    %16 = load %"shadow.standard@Array$methods"** %15
    %17 = getelementptr %"shadow.standard@Array$methods"* %16, i32 0, i32 6
    %18 = load %long (%"shadow.standard@Array"*, %long)** %17
    %19 = call %long %18(%"shadow.standard@Array"* %6, %long %14)
    ret %long %19
}
define %long @"shadow.standard@Array$$getLength"(%"shadow.standard@Array"*) {
    %this = alloca %"shadow.standard@Array"*
    %length = alloca %long
    %i = alloca %long
    store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
    %2 = sext %int 1 to %long
    store %long %2, %long* %length
    %3 = sext %int 0 to %long
    store %long %3, %long* %i
    br label %.label1
.label0:
    %4 = load %"shadow.standard@Array"** %this
    %5 = load %long* %i
    %6 = getelementptr %"shadow.standard@Array"* %4, i32 0, i32 0
    %7 = load %"shadow.standard@Array$methods"** %6
    %8 = getelementptr %"shadow.standard@Array$methods"* %7, i32 0, i32 6
    %9 = load %long (%"shadow.standard@Array"*, %long)** %8
    %10 = call %long %9(%"shadow.standard@Array"* %4, %long %5)
    br label %.label1
.label1:
    %11 = load %"shadow.standard@Array"** %this
    %12 = getelementptr %"shadow.standard@Array"* %11, i32 0, i32 0
    %13 = load %"shadow.standard@Array$methods"** %12
    %14 = getelementptr %"shadow.standard@Array$methods"* %13, i32 0, i32 4
    %15 = load %long (%"shadow.standard@Array"*)** %14
    %16 = call %long %15(%"shadow.standard@Array"* %11)
    %17 = load %long* %i
    %18 = icmp slt %long %17, %16
    br %boolean %18, label %.label0, label %.label2
.label2:
    %19 = load %long* %length
    ret %long %19
}
define %long @"shadow.standard@Array$$getLength$long"(%"shadow.standard@Array"*, %long) {
    %this = alloca %"shadow.standard@Array"*
    %index = alloca %long
    store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
    store %long %1, %long* %index
    %3 = load %"shadow.standard@Array"** %this
    %4 = getelementptr inbounds %"shadow.standard@Array"* %3, i32 0, i32 2
    %5 = load { %long*, [1 x %long] }* %4
    %6 = load %long* %index
    %7 = extractvalue { %long*, [1 x %long] } %5, 0
    %8 = getelementptr %long* %7, %long %6
    %9 = load %long* %8
    ret %long %9
}
define void @"shadow.standard@Array$$constructor$long$long[]"(%"shadow.standard@Array"*, %long, { %long*, [1 x %long] }) {
    %this = alloca %"shadow.standard@Array"*
    %data = alloca %long
    %lengths = alloca { %long*, [1 x %long] }
    store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
    store %long %1, %long* %data
    store { %long*, [1 x %long] } %2, { %long*, [1 x %long] }* %lengths
    %4 = load %"shadow.standard@Array"** %this
    %5 = getelementptr %"shadow.standard@Array"* %4, i32 0, i32 0
    store %"shadow.standard@Array$methods"* @"shadow.standard@Array$methods", %"shadow.standard@Array$methods"** %5
    %6 = load %"shadow.standard@Array"** %this
    %7 = getelementptr inbounds %"shadow.standard@Array"* %6, i32 0, i32 2
    %8 = sext %int 0 to %long
    %9 = call noalias i8* @calloc(i64 %8, i64 ptrtoint (%long* getelementptr(%long* null, i32 1) to i64))
    %10 = bitcast i8* %9 to %long*
    %11 = insertvalue { %long*, [1 x %long] } undef, %long* %10, 0
    %12 = insertvalue { %long*, [1 x %long] } %11, %long %8, 1, 0
    store { %long*, [1 x %long] } %12, { %long*, [1 x %long] }* %7
    %13 = load %"shadow.standard@Array"** %this
    %14 = getelementptr inbounds %"shadow.standard@Array"* %13, i32 0, i32 1
    %15 = sext %int 0 to %long
    store %long %15, %long* %14
    %16 = load %"shadow.standard@Array"** %this
    %17 = getelementptr inbounds %"shadow.standard@Array"* %16, i32 0, i32 1
    %18 = load %"shadow.standard@Array"** %this
    %19 = getelementptr inbounds %"shadow.standard@Array"* %18, i32 0, i32 2
    ret void
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard@Array"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
