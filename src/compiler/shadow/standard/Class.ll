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

%"shadow.standard@Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = constant %"shadow.standard@Class$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$toString", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getClassName", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getSuperClass", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* @"shadow.standard@Class$$isClassSubtype$shadow.standard@Class", %boolean (%"shadow.standard@Class"*)* @"shadow.standard@Class$$isInterface", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* @"shadow.standard@Class$$isInterfaceSubtype$shadow.standard@Class", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* @"shadow.standard@Class$$isSubtype$shadow.standard@Class" }
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, { %"shadow.standard@Class"**, [1 x %long] }, %"shadow.standard@Class"* }

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
@"shadow.standard@Array$methods" = external constant %"shadow.standard@Array$methods"
%"shadow.standard@Array" = type { %"shadow.standard@Array$methods"*, %long, { %long*, [1 x %long] } }
declare { %long*, [1 x %long] } @"shadow.standard@Array$$getLengths"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getDimensions"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getLength"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array$$getLength$long"(%"shadow.standard@Array"*, %long)
declare void @"shadow.standard@Array$$constructor$long$long[]"(%"shadow.standard@Array"*, %long, { %long*, [1 x %long] })

@"shadow.standard@Class$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Array$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.standard@Class$$getClass"(%"shadow.standard@Class"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Class$class"
}

define %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 3
    %4 = load %"shadow.standard@Class"** %3
    ret %"shadow.standard@Class"* %4
}
define %boolean @"shadow.standard@Class$$isClassSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*) {
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
    %6 = load %"shadow.standard@Class"** %this
    %7 = getelementptr inbounds %"shadow.standard@Class"* %6, i32 0, i32 3
    %8 = load %"shadow.standard@Class"** %7
    %9 = load %"shadow.standard@Class"** %other
    %10 = getelementptr %"shadow.standard@Class"* %8, i32 0, i32 0
    %11 = load %"(shadow.standard@Class) => (boolean)$methods"** %10
    %12 = getelementptr %"(shadow.standard@Class) => (boolean)$methods"* %11, i32 0, i32 6
    %13 = load %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)** %12
    %14 = call %boolean %13(%"shadow.standard@Class"* %8, %"shadow.standard@Class"* %9)
    ret %boolean %14
    br label %.label3
.label4:
    br label %.label3
.label3:
    ret %boolean false
}
define %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    %other = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    store %"shadow.standard@Class"* %1, %"shadow.standard@Class"** %other
    %3 = load %"shadow.standard@Class"** %other
    %4 = getelementptr %"shadow.standard@Class"* %3, i32 0, i32 0
    %5 = load %"() => (boolean)$methods"** %4
    %6 = getelementptr %"() => (boolean)$methods"* %5, i32 0, i32 7
    %7 = load %boolean (%"shadow.standard@Class"*)** %6
    %8 = call %boolean %7(%"shadow.standard@Class"* %3)
    br %boolean %8, label %.label5, label %.label6
.label5:
    %9 = load %"shadow.standard@Class"** %this
    %10 = load %"shadow.standard@Class"** %other
    %11 = getelementptr %"shadow.standard@Class"* %9, i32 0, i32 0
    %12 = load %"(shadow.standard@Class) => (boolean)$methods"** %11
    %13 = getelementptr %"(shadow.standard@Class) => (boolean)$methods"* %12, i32 0, i32 8
    %14 = load %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)** %13
    %15 = call %boolean %14(%"shadow.standard@Class"* %9, %"shadow.standard@Class"* %10)
    ret %boolean %15
    br label %.label7
.label6:
    br label %.label7
.label7:
    %16 = load %"shadow.standard@Class"** %this
    %17 = load %"shadow.standard@Class"** %other
    %18 = getelementptr %"shadow.standard@Class"* %16, i32 0, i32 0
    %19 = load %"(shadow.standard@Class) => (boolean)$methods"** %18
    %20 = getelementptr %"(shadow.standard@Class) => (boolean)$methods"* %19, i32 0, i32 6
    %21 = load %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)** %20
    %22 = call %boolean %21(%"shadow.standard@Class"* %16, %"shadow.standard@Class"* %17)
    ret %boolean %22
}
define %boolean @"shadow.standard@Class$$isInterface"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    ret %boolean false
}
define %boolean @"shadow.standard@Class$$isInterfaceSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    %other = alloca %"shadow.standard@Class"*
    %i = alloca %long
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    store %"shadow.standard@Class"* %1, %"shadow.standard@Class"** %other
    %3 = sext %int 0 to %long
    store %long %3, %long* %i
    br label %.label9
.label8:
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 2
    %6 = load { %"shadow.standard@Class"**, [1 x %long] }* %5
    %7 = load %long* %i
    %8 = extractvalue { %"shadow.standard@Class"**, [1 x %long] } %6, 0
    %9 = getelementptr %"shadow.standard@Class"** %8, %long %7
    %10 = load %"shadow.standard@Class"** %9
    %11 = load %"shadow.standard@Class"** %other
    %12 = icmp eq %"shadow.standard@Class"* %10, %11
    br %boolean %12, label %.label11, label %.label12
.label11:
    ret %boolean true
    br label %.label13
.label12:
    br label %.label13
.label13:
    br label %.label9
.label9:
    %13 = load %"shadow.standard@Class"** %this
    %14 = getelementptr inbounds %"shadow.standard@Class"* %13, i32 0, i32 2
    %15 = load { %"shadow.standard@Class"**, [1 x %long] }* %14
    %16 = getelementptr { %"shadow.standard@Class"**, [1 x %long] } %15, i32 0, i32 0
    %17 = load %"(final int) => (long)$methods"** %16
    %18 = getelementptr %"(final int) => (long)$methods"* %17, i32 0, i32 -1
    %19 = load %long ({ %"shadow.standard@Class"**, [1 x %long] }, %int)** %18
    %20 = call %long %19({ %"shadow.standard@Class"**, [1 x %long] } %15, %int 0)
    %21 = load %long* %i
    %22 = icmp slt %long %21, %20
    br %boolean %22, label %.label8, label %.label10
.label10:
    ret %boolean false
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
    %4 = load %"() => (shadow.standard@String)$methods"** %3
    %5 = getelementptr %"() => (shadow.standard@String)$methods"* %4, i32 0, i32 4
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
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 2
    %6 = sext %int 0 to %long
    %7 = call noalias i8* @calloc(i64 %6, i64 ptrtoint (%"shadow.standard@Class"** getelementptr(%"shadow.standard@Class"** null, i32 1) to i64))
    %8 = bitcast i8* %7 to %"shadow.standard@Class"**
    %9 = insertvalue { %"shadow.standard@Class"**, [1 x %long] } undef, %"shadow.standard@Class"** %8, 0
    %10 = insertvalue { %"shadow.standard@Class"**, [1 x %long] } %9, %long %6, 1, 0
    store { %"shadow.standard@Class"**, [1 x %long] } %10, { %"shadow.standard@Class"**, [1 x %long] }* %5
    %11 = load %"shadow.standard@Class"** %this
    %12 = getelementptr inbounds %"shadow.standard@Class"* %11, i32 0, i32 3
    store %"shadow.standard@Class"* null, %"shadow.standard@Class"** %12
    %13 = load %"shadow.standard@Class"** %this
    %14 = getelementptr inbounds %"shadow.standard@Class"* %13, i32 0, i32 1
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %14
    ret void
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard@Class"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
