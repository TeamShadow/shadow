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

%"shadow.standard@Array<T>$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard@Array"*)* }
@"shadow.standard@Array<T>$methods" = external constant %"shadow.standard@Array<T>$methods"
%"shadow.standard@Array<T>" = type { %"shadow.standard@Array<T>$methods"*, %long, { %long*, [1 x %long] } }
declare { %long*, [1 x %long] } @"shadow.standard@Array<T>$$getLengths"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getDimensions"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getLength"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getLength$long"(%"shadow.standard@Array"*, %long)
declare void @"shadow.standard@Array<T>$$constructor$long$long[]"(%"shadow.standard@Array"*, %long, { %long*, [1 x %long] })

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
@"shadow.standard@String$methods" = constant %"shadow.standard@String$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.standard@String"*)* @"shadow.standard@String$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@String"*)* @"shadow.standard@String$$toString", %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)* @"shadow.standard@String$$equals$shadow.standard@String", %ubyte (%"shadow.standard@String"*, %long)* @"shadow.standard@String$$getCode$long", %long (%"shadow.standard@String"*)* @"shadow.standard@String$$getLength" }
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }

@"shadow.standard@Array<T>$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, { %"shadow.standard@Class"**, [1 x %long] } { %"shadow.standard@Class"** null, [1 x %long] [%long 0] }, %"shadow.standard@Class"* @"shadow.standard@Object$class" }

define %"shadow.standard@Class"* @"shadow.standard@String$$getClass"(%"shadow.standard@String"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@String$class"
}

define %boolean @"shadow.standard@String$$equals$shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    %other = alloca %"shadow.standard@String"*
    %i = alloca %long
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %other
    %3 = load %"shadow.standard@String"** %this
    %4 = getelementptr %"shadow.standard@String"* %3, i32 0, i32 0
    %5 = load %"shadow.standard@String$methods"** %4
    %6 = getelementptr %"shadow.standard@String$methods"* %5, i32 0, i32 6
    %7 = load %long (%"shadow.standard@String"*)** %6
    %8 = call %long %7(%"shadow.standard@String"* %3)
    %9 = load %"shadow.standard@String"** %other
    %10 = getelementptr %"shadow.standard@String"* %9, i32 0, i32 0
    %11 = load %"shadow.standard@String$methods"** %10
    %12 = getelementptr %"shadow.standard@String$methods"* %11, i32 0, i32 6
    %13 = load %long (%"shadow.standard@String"*)** %12
    %14 = call %long %13(%"shadow.standard@String"* %9)
    %15 = icmp ne %long %8, %14
    br %boolean %15, label %.label0, label %.label1
.label0:
    ret %boolean false
    br label %.label2
.label1:
    br label %.label2
.label2:
    %17 = sext %int 0 to %long
    store %long %17, %long* %i
    br label %.label4
.label3:
    %18 = load %"shadow.standard@String"** %this
    %19 = load %long* %i
    %20 = getelementptr %"shadow.standard@String"* %18, i32 0, i32 0
    %21 = load %"shadow.standard@String$methods"** %20
    %22 = getelementptr %"shadow.standard@String$methods"* %21, i32 0, i32 5
    %23 = load %ubyte (%"shadow.standard@String"*, %long)** %22
    %24 = call %ubyte %23(%"shadow.standard@String"* %18, %long %19)
    %25 = load %"shadow.standard@String"** %other
    %26 = load %long* %i
    %27 = getelementptr %"shadow.standard@String"* %25, i32 0, i32 0
    %28 = load %"shadow.standard@String$methods"** %27
    %29 = getelementptr %"shadow.standard@String$methods"* %28, i32 0, i32 5
    %30 = load %ubyte (%"shadow.standard@String"*, %long)** %29
    %31 = call %ubyte %30(%"shadow.standard@String"* %25, %long %26)
    %32 = icmp ne %ubyte %24, %31
    br %boolean %32, label %.label6, label %.label7
.label6:
    ret %boolean false
    br label %.label8
.label7:
    br label %.label8
.label8:
    br label %.label4
.label4:
    %34 = load %"shadow.standard@String"** %this
    %35 = getelementptr %"shadow.standard@String"* %34, i32 0, i32 0
    %36 = load %"shadow.standard@String$methods"** %35
    %37 = getelementptr %"shadow.standard@String$methods"* %36, i32 0, i32 6
    %38 = load %long (%"shadow.standard@String"*)** %37
    %39 = call %long %38(%"shadow.standard@String"* %34)
    %40 = load %long* %i
    %41 = icmp slt %long %40, %39
    br %boolean %41, label %.label3, label %.label5
.label5:
    ret %boolean true
}
define %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    ret %"shadow.standard@String"* %2
}
define %ubyte @"shadow.standard@String$$getCode$long"(%"shadow.standard@String"*, %long) {
    %this = alloca %"shadow.standard@String"*
    %index = alloca %long
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %long %1, %long* %index
    %3 = load %"shadow.standard@String"** %this
    %4 = getelementptr inbounds %"shadow.standard@String"* %3, i32 0, i32 1
    %5 = load { %ubyte*, [1 x %long] }* %4
    %6 = load %long* %index
    %7 = extractvalue { %ubyte*, [1 x %long] } %5, 0
    %8 = getelementptr %ubyte* %7, %long %6
    %9 = load %ubyte* %8
    ret %ubyte %9
}
define %long @"shadow.standard@String$$getLength"(%"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    %2 = load %"shadow.standard@String"** %this
    %3 = getelementptr inbounds %"shadow.standard@String"* %2, i32 0, i32 1
    %4 = load { %ubyte*, [1 x %long] }* %3
    %5 = call i8* @malloc(i64 ptrtoint (%"shadow.standard@Array"* getelementptr(%"shadow.standard@Array"* null, i32 1) to i64))
    %6 = bitcast i8* %5 to %"shadow.standard@Array"*
    %7 = extractvalue { %ubyte*, [1 x %long] } %4, 0
    %8 = ptrtoint %ubyte* %7 to %long
    %9 = extractvalue { %ubyte*, [1 x %long] } %4, 1
    %10 = call i8* @malloc(i64 ptrtoint ([1 x %long]* getelementptr([1 x %long]* null, i32 1) to i64))
    %11 = bitcast i8* %10 to [1 x %long]*
    store [1 x %long] %9, [1 x %long]* %11
    %12 = getelementptr [1 x %long]* %11, i32 0, i32 0
    %13 = insertvalue { %long*, [1 x %long] } { %long* null, [1 x %long] [%long 1] }, %long* %12, 0
    call void @"shadow.standard@Array<T>$$constructor$long$long[]"(%"shadow.standard@Array"* %6, %long %8, { %long*, [1 x %long] } %13)
    %14 = sext %int 0 to %long
    %15 = getelementptr %"shadow.standard@Array"* %6, i32 0, i32 0
    %16 = load %"shadow.standard@Array<T>$methods"** %15
    %17 = getelementptr %"shadow.standard@Array<T>$methods"* %16, i32 0, i32 6
    %18 = load %long (%"shadow.standard@Array"*, %long)** %17
    %19 = call %long %18(%"shadow.standard@Array"* %6, %long %14)
    ret %long %19
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
