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

%"shadow.standard@Array<T>$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*)*, %long (%"shadow.standard@Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard@Array"*)* }
@"shadow.standard@Array<T>$methods" = external constant %"shadow.standard@Array<T>$methods"
%"shadow.standard@Array<T>" = type { %"shadow.standard@Array<T>$methods"*, %long, { %long*, [1 x %long] } }
declare { %long*, [1 x %long] } @"shadow.standard@Array<T>$$getLengths"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getDimensions"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getLength"(%"shadow.standard@Array"*)
declare %long @"shadow.standard@Array<T>$$getLength$long"(%"shadow.standard@Array"*, %long)
declare void @"shadow.standard@Array<T>$$constructor$long$long[]"(%"shadow.standard@Array"*, %long, { %long*, [1 x %long] })

%"shadow.standard@Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)* }
@"shadow.standard@Class$methods" = constant %"shadow.standard@Class$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"shadow.standard@Object$$equals$shadow.standard@Object", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"shadow.standard@Object$$referenceEquals$shadow.standard@Object", %"shadow.standard@String"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$toString", %"shadow.standard@Class"* (%"shadow.standard@Class"*)* @"shadow.standard@Class$$getParent", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* @"shadow.standard@Class$$isSubtype$shadow.standard@Class", %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)* @"shadow.standard@Class$$isSubtype$shadow.standard@Interface" }
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"*, %"shadow.standard@Class"* }

%"shadow.standard@Object$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.standard@Object$methods" = external constant %"shadow.standard@Object$methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object$methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object$$getClass"(%"shadow.standard@Object"*)
declare %boolean @"shadow.standard@Object$$equals$shadow.standard@Object"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"shadow.standard@Object$$freeze"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object$$toString"(%"shadow.standard@Object"*)
declare %boolean @"shadow.standard@Object$$referenceEquals$shadow.standard@Object"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare void @"shadow.standard@Object$$constructor"(%"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"shadow.standard@Object$$copy"(%"shadow.standard@Object"*)

%"shadow.standard@String$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %long)*, %long (%"shadow.standard@String"*)* }
@"shadow.standard@String$methods" = external constant %"shadow.standard@String$methods"
%"shadow.standard@String" = type { %"shadow.standard@String$methods"*, { %ubyte*, [1 x %long] } }
declare %boolean @"shadow.standard@String$$equals$shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %"shadow.standard@String"* @"shadow.standard@String$$toString"(%"shadow.standard@String"*)
declare %ubyte @"shadow.standard@String$$getCode$long"(%"shadow.standard@String"*, %long)
declare %long @"shadow.standard@String$$getLength"(%"shadow.standard@String"*)
declare void @"shadow.standard@String$$constructor"(%"shadow.standard@String"*)

%"shadow.standard@Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"shadow.standard@Interface$methods" = external constant %"shadow.standard@Interface$methods"
%"shadow.standard@Interface" = type { %"shadow.standard@Interface$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"* }
declare %boolean @"shadow.standard@Interface$$isSubtype$shadow.standard@Interface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@String"* @"shadow.standard@Interface$$toString"(%"shadow.standard@Interface"*)
declare void @"shadow.standard@Interface$$constructor"(%"shadow.standard@Interface"*)

@"shadow.standard@Array<T>$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Class$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Interface$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.standard@Class$$getClass"(%"shadow.standard@Class"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Class$class"
}

define %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*) {
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
    %10 = load %"shadow.standard@Class"** %other
    %11 = getelementptr %"shadow.standard@Class"* %9, i32 0, i32 0
    %12 = load %"shadow.standard@Class$methods"** %11
    %13 = getelementptr %"shadow.standard@Class$methods"* %12, i32 0, i32 7
    %14 = load %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)** %13
    %15 = call %boolean %14(%"shadow.standard@Class"* %9, %"shadow.standard@Class"* %10)
    ret %boolean %15
    br label %.label4
.label3:
    br label %.label4
.label4:
    ret %boolean false
}
define %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Interface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Class"*
    %other = alloca %"shadow.standard@Interface"*
    %i = alloca %long
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    store %"shadow.standard@Interface"* %1, %"shadow.standard@Interface"** %other
    %3 = sext %int 0 to %long
    store %long %3, %long* %i
    br label %.label6
.label5:
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 1
    %6 = load { %"shadow.standard@Interface"**, [1 x %long] }* %5
    %7 = load %long* %i
    %8 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %6, 0
    %9 = getelementptr %"shadow.standard@Interface"** %8, %long %7
    %10 = load %"shadow.standard@Interface"** %9
    %11 = load %"shadow.standard@Interface"** %other
    %12 = getelementptr %"shadow.standard@Interface"* %10, i32 0, i32 0
    %13 = load %"shadow.standard@Interface$methods"** %12
    %14 = getelementptr %"shadow.standard@Interface$methods"* %13, i32 0, i32 6
    %15 = load %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)** %14
    %16 = call %boolean %15(%"shadow.standard@Interface"* %10, %"shadow.standard@Interface"* %11)
    br %boolean %16, label %.label10, label %.label11
.label10:
    ret %boolean true
    br label %.label12
.label11:
    br label %.label12
.label12:
    br label %.label9
.label8:
    br label %.label9
.label9:
    br label %.label6
.label6:
    %18 = load %"shadow.standard@Class"** %this
    %19 = getelementptr inbounds %"shadow.standard@Class"* %18, i32 0, i32 1
    %20 = load { %"shadow.standard@Interface"**, [1 x %long] }* %19
    %21 = call i8* @malloc(i64 ptrtoint (%"shadow.standard@Array"* getelementptr (%"shadow.standard@Array"* null, i32 1) to i64))
    %22 = bitcast i8* %21 to %"shadow.standard@Array"*
    %23 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %20, 0
    %24 = ptrtoint %"shadow.standard@Interface"** %23 to %long
    %25 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %20, 1
    %26 = call i8* @malloc(i64 ptrtoint ([1 x %long]* getelementptr ([1 x %long]* null, i32 1) to i64))
    %27 = bitcast i8* %26 to [1 x %long]*
    store [1 x %long] %25, [1 x %long]* %27
    %28 = getelementptr [1 x %long]* %27, i32 0, i32 0
    %29 = insertvalue { %long*, [1 x %long] } { %long* null, [1 x %long] [%long 1] }, %long* %28, 0
    call void @"shadow.standard@Array<T>$$constructor$long$long[]"(%"shadow.standard@Array"* %22, %long %24, { %long*, [1 x %long] } %29)
    %30 = getelementptr %"shadow.standard@Array"* %22, i32 0, i32 0
    %31 = load %"shadow.standard@Array<T>$methods"** %30
    %32 = getelementptr %"shadow.standard@Array<T>$methods"* %31, i32 0, i32 7
    %33 = load %long (%"shadow.standard@Array"*)** %32
    %34 = call %long %33(%"shadow.standard@Array"* %22)
    %35 = load %long* %i
    %36 = icmp slt %long %35, %34
    br %boolean %36, label %.label5, label %.label7
.label7:
    ret %boolean false
}
define %"shadow.standard@Class"* @"shadow.standard@Class$$getParent"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 3
    %4 = load %"shadow.standard@Class"** %3
    ret %"shadow.standard@Class"* %4
}
define %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr inbounds %"shadow.standard@Class"* %2, i32 0, i32 2
    %4 = load %"shadow.standard@String"** %3
    ret %"shadow.standard@String"* %4
}
define void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*) {
    %this = alloca %"shadow.standard@Class"*
    store %"shadow.standard@Class"* %0, %"shadow.standard@Class"** %this
    %2 = load %"shadow.standard@Class"** %this
    %3 = getelementptr %"shadow.standard@Class"* %2, i32 0, i32 0
    store %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@Class$methods"** %3
    %4 = load %"shadow.standard@Class"** %this
    %5 = getelementptr inbounds %"shadow.standard@Class"* %4, i32 0, i32 2
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %5
    %6 = load %"shadow.standard@Class"** %this
    %7 = getelementptr inbounds %"shadow.standard@Class"* %6, i32 0, i32 3
    store %"shadow.standard@Class"* null, %"shadow.standard@Class"** %7
    %8 = load %"shadow.standard@Class"** %this
    %9 = getelementptr inbounds %"shadow.standard@Class"* %8, i32 0, i32 1
    %10 = sext %int 0 to %long
    %11 = call noalias i8* @calloc(i64 %10, i64 ptrtoint (%"shadow.standard@Interface"** getelementptr (%"shadow.standard@Interface"** null, i32 1) to i64))
    %12 = bitcast i8* %11 to %"shadow.standard@Interface"**
    %13 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } undef, %"shadow.standard@Interface"** %12, 0
    %14 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } %13, %long %10, 1, 0
    store { %"shadow.standard@Interface"**, [1 x %long] } %14, { %"shadow.standard@Interface"**, [1 x %long] }* %9
    ret void
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard@Class"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
