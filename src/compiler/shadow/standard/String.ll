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

%"shadow.standard@Array$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard@Array"*)* }
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

define %boolean @"shadow.standard@String$$equals$shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.standard@String"*
    %other = alloca %"shadow.standard@String"*
    %i = alloca %long
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %other
    %3 = load %"shadow.standard@String"** %this
    %4 = getelementptr %"shadow.standard@String"* %3, i32 0, i32 0
    %5 = load %"() => (long)$methods"** %4
    %6 = getelementptr %"() => (long)$methods"* %5, i32 0, i32 6
    %7 = load %long (%"shadow.standard@String"*)** %6
    %8 = call %long %7(%"shadow.standard@String"* %3)
    %9 = load %"shadow.standard@String"** %other
    %10 = getelementptr %"shadow.standard@String"* %9, i32 0, i32 0
    %11 = load %"() => (long)$methods"** %10
    %12 = getelementptr %"() => (long)$methods"* %11, i32 0, i32 6
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
    %16 = sext %int 0 to %long
    store %long %16, %long* %i
    br label %.label4
.label3:
    %17 = load %"shadow.standard@String"** %this
    %18 = load %long* %i
    %19 = getelementptr %"shadow.standard@String"* %17, i32 0, i32 0
    %20 = load %"(long) => (ubyte)$methods"** %19
    %21 = getelementptr %"(long) => (ubyte)$methods"* %20, i32 0, i32 5
    %22 = load %ubyte (%"shadow.standard@String"*, %long)** %21
    %23 = call %ubyte %22(%"shadow.standard@String"* %17, %long %18)
    %24 = load %"shadow.standard@String"** %other
    %25 = load %long* %i
    %26 = getelementptr %"shadow.standard@String"* %24, i32 0, i32 0
    %27 = load %"(long) => (ubyte)$methods"** %26
    %28 = getelementptr %"(long) => (ubyte)$methods"* %27, i32 0, i32 5
    %29 = load %ubyte (%"shadow.standard@String"*, %long)** %28
    %30 = call %ubyte %29(%"shadow.standard@String"* %24, %long %25)
    %31 = icmp ne %ubyte %23, %30
    br %boolean %31, label %.label6, label %.label7
.label6:
    ret %boolean false
    br label %.label8
.label7:
    br label %.label8
.label8:
    br label %.label4
.label4:
    %32 = load %"shadow.standard@String"** %this
    %33 = getelementptr %"shadow.standard@String"* %32, i32 0, i32 0
    %34 = load %"() => (long)$methods"** %33
    %35 = getelementptr %"() => (long)$methods"* %34, i32 0, i32 6
    %36 = load %long (%"shadow.standard@String"*)** %35
    %37 = call %long %36(%"shadow.standard@String"* %32)
    %38 = load %long* %i
    %39 = icmp slt %long %38, %37
    br %boolean %39, label %.label3, label %.label5
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
    %5 = getelementptr { %ubyte*, [1 x %long] } %4, i32 0, i32 0
    %6 = load %"(final int) => (long)$methods"** %5
    %7 = getelementptr %"(final int) => (long)$methods"* %6, i32 0, i32 -1
    %8 = load %long ({ %ubyte*, [1 x %long] }, %int)** %7
    %9 = call %long %8({ %ubyte*, [1 x %long] } %4, %int 0)
    ret %long %9
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
