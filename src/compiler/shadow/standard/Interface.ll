; shadow.standard@Interface

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
@"shadow.standard@Class$methods" = external constant %"shadow.standard@Class$methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"*, %"shadow.standard@Class"* }
declare %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Class"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"shadow.standard@Class$$isSubtype$shadow.standard@Interface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@Class"* @"shadow.standard@Class$$getParent"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*)

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
@"shadow.standard@Interface$methods" = constant %"shadow.standard@Interface$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"shadow.standard@Object$$equals$shadow.standard@Object", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$freeze", %"shadow.standard@Class"* (%"shadow.standard@Interface"*)* @"shadow.standard@Interface$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"shadow.standard@Object$$referenceEquals$shadow.standard@Object", %"shadow.standard@String"* (%"shadow.standard@Interface"*)* @"shadow.standard@Interface$$toString", %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* @"shadow.standard@Interface$$isSubtype$shadow.standard@Interface" }
%"shadow.standard@Interface" = type { %"shadow.standard@Interface$methods"*, { %"shadow.standard@Interface"**, [1 x %long] }, %"shadow.standard@String"* }

@"shadow.standard@Array<T>$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Interface$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }

define %"shadow.standard@Class"* @"shadow.standard@Interface$$getClass"(%"shadow.standard@Interface"*) {
    ret %"shadow.standard@Class"* @"shadow.standard@Interface$class"
}

define %boolean @"shadow.standard@Interface$$isSubtype$shadow.standard@Interface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    %other = alloca %"shadow.standard@Interface"*
    %i = alloca %long
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    store %"shadow.standard@Interface"* %1, %"shadow.standard@Interface"** %other
    %3 = load %"shadow.standard@Interface"** %this
    %4 = load %"shadow.standard@Interface"** %other
    %5 = icmp eq %"shadow.standard@Interface"* %3, %4
    br %boolean %5, label %.label0, label %.label1
.label0:
    ret %boolean true
    br label %.label2
.label1:
    br label %.label2
.label2:
    %7 = sext %int 0 to %long
    store %long %7, %long* %i
    br label %.label4
.label3:
    %8 = load %"shadow.standard@Interface"** %this
    %9 = getelementptr inbounds %"shadow.standard@Interface"* %8, i32 0, i32 1
    %10 = load { %"shadow.standard@Interface"**, [1 x %long] }* %9
    %11 = load %long* %i
    %12 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %10, 0
    %13 = getelementptr %"shadow.standard@Interface"** %12, %long %11
    %14 = load %"shadow.standard@Interface"** %13
    %15 = load %"shadow.standard@Interface"** %other
    %16 = getelementptr %"shadow.standard@Interface"* %14, i32 0, i32 0
    %17 = load %"shadow.standard@Interface$methods"** %16
    %18 = getelementptr %"shadow.standard@Interface$methods"* %17, i32 0, i32 6
    %19 = load %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)** %18
    %20 = call %boolean %19(%"shadow.standard@Interface"* %14, %"shadow.standard@Interface"* %15)
    br %boolean %20, label %.label8, label %.label9
.label8:
    ret %boolean true
    br label %.label10
.label9:
    br label %.label10
.label10:
    br label %.label7
.label6:
    br label %.label7
.label7:
    br label %.label4
.label4:
    %22 = load %"shadow.standard@Interface"** %this
    %23 = getelementptr inbounds %"shadow.standard@Interface"* %22, i32 0, i32 1
    %24 = load { %"shadow.standard@Interface"**, [1 x %long] }* %23
    %25 = call i8* @malloc(i64 ptrtoint (%"shadow.standard@Array"* getelementptr (%"shadow.standard@Array"* null, i32 1) to i64))
    %26 = bitcast i8* %25 to %"shadow.standard@Array"*
    %27 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %24, 0
    %28 = ptrtoint %"shadow.standard@Interface"** %27 to %long
    %29 = extractvalue { %"shadow.standard@Interface"**, [1 x %long] } %24, 1
    %30 = call i8* @malloc(i64 ptrtoint ([1 x %long]* getelementptr ([1 x %long]* null, i32 1) to i64))
    %31 = bitcast i8* %30 to [1 x %long]*
    store [1 x %long] %29, [1 x %long]* %31
    %32 = getelementptr [1 x %long]* %31, i32 0, i32 0
    %33 = insertvalue { %long*, [1 x %long] } { %long* null, [1 x %long] [%long 1] }, %long* %32, 0
    call void @"shadow.standard@Array<T>$$constructor$long$long[]"(%"shadow.standard@Array"* %26, %long %28, { %long*, [1 x %long] } %33)
    %34 = getelementptr %"shadow.standard@Array"* %26, i32 0, i32 0
    %35 = load %"shadow.standard@Array<T>$methods"** %34
    %36 = getelementptr %"shadow.standard@Array<T>$methods"* %35, i32 0, i32 7
    %37 = load %long (%"shadow.standard@Array"*)** %36
    %38 = call %long %37(%"shadow.standard@Array"* %26)
    %39 = load %long* %i
    %40 = icmp slt %long %39, %38
    br %boolean %40, label %.label3, label %.label5
.label5:
    ret %boolean false
}
define %"shadow.standard@String"* @"shadow.standard@Interface$$toString"(%"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    %2 = load %"shadow.standard@Interface"** %this
    %3 = getelementptr inbounds %"shadow.standard@Interface"* %2, i32 0, i32 2
    %4 = load %"shadow.standard@String"** %3
    ret %"shadow.standard@String"* %4
}
define void @"shadow.standard@Interface$$constructor"(%"shadow.standard@Interface"*) {
    %this = alloca %"shadow.standard@Interface"*
    store %"shadow.standard@Interface"* %0, %"shadow.standard@Interface"** %this
    %2 = load %"shadow.standard@Interface"** %this
    %3 = getelementptr %"shadow.standard@Interface"* %2, i32 0, i32 0
    store %"shadow.standard@Interface$methods"* @"shadow.standard@Interface$methods", %"shadow.standard@Interface$methods"** %3
    %4 = load %"shadow.standard@Interface"** %this
    %5 = getelementptr inbounds %"shadow.standard@Interface"* %4, i32 0, i32 2
    store %"shadow.standard@String"* @.str1, %"shadow.standard@String"** %5
    %6 = load %"shadow.standard@Interface"** %this
    %7 = getelementptr inbounds %"shadow.standard@Interface"* %6, i32 0, i32 1
    %8 = sext %int 0 to %long
    %9 = call noalias i8* @calloc(i64 %8, i64 ptrtoint (%"shadow.standard@Interface"** getelementptr (%"shadow.standard@Interface"** null, i32 1) to i64))
    %10 = bitcast i8* %9 to %"shadow.standard@Interface"**
    %11 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } undef, %"shadow.standard@Interface"** %10, 0
    %12 = insertvalue { %"shadow.standard@Interface"**, [1 x %long] } %11, %long %8, 1, 0
    store { %"shadow.standard@Interface"**, [1 x %long] } %12, { %"shadow.standard@Interface"**, [1 x %long] }* %7
    ret void
}

@.array0 = private unnamed_addr constant [25 x %ubyte] c"shadow.standard@Interface"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([25 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 25] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
