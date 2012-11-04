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

@"shadow.io@Console$instance" = global %"shadow.io@Console"* null
%"shadow.io@Console$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.io@Console"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.io@Console"*, %boolean)*, void (%"shadow.io@Console"*, %"shadow.standard@Object"*)*, void (%"shadow.io@Console"*, %int)*, void (%"shadow.io@Console"*, %code)*, void (%"shadow.io@Console"*, %long)*, void (%"shadow.io@Console"*, %"shadow.standard@String"*)*, void (%"shadow.io@Console"*, %boolean)*, void (%"shadow.io@Console"*, %int)*, void (%"shadow.io@Console"*, %code)*, void (%"shadow.io@Console"*, %long)*, void (%"shadow.io@Console"*, %"shadow.standard@String"*)*, void (%"shadow.io@Console"*, %"shadow.standard@Object"*)*, void (%"shadow.io@Console"*)* }
@"shadow.io@Console$methods" = external constant %"shadow.io@Console$methods"
%"shadow.io@Console" = type { %"shadow.io@Console$methods"* }
declare void @"shadow.io@Console$$printLine$boolean"(%"shadow.io@Console"*, %boolean)
declare void @"shadow.io@Console$$printLine$int"(%"shadow.io@Console"*, %int)
declare void @"shadow.io@Console$$printLine$code"(%"shadow.io@Console"*, %code)
declare void @"shadow.io@Console$$printLine$long"(%"shadow.io@Console"*, %long)
declare void @"shadow.io@Console$$printLine$shadow.standard@String"(%"shadow.io@Console"*, %"shadow.standard@String"*)
declare void @"shadow.io@Console$$printLine$shadow.standard@Object"(%"shadow.io@Console"*, %"shadow.standard@Object"*)
declare void @"shadow.io@Console$$printLine"(%"shadow.io@Console"*)
declare void @"shadow.io@Console$$print$boolean"(%"shadow.io@Console"*, %boolean)
declare void @"shadow.io@Console$$print$shadow.standard@Object"(%"shadow.io@Console"*, %"shadow.standard@Object"*)
declare void @"shadow.io@Console$$print$int"(%"shadow.io@Console"*, %int)
declare void @"shadow.io@Console$$print$code"(%"shadow.io@Console"*, %code)
declare void @"shadow.io@Console$$print$long"(%"shadow.io@Console"*, %long)
declare void @"shadow.io@Console$$print$shadow.standard@String"(%"shadow.io@Console"*, %"shadow.standard@String"*)
declare void @"shadow.io@Console$$constructor"(%"shadow.io@Console"*)

%"shadow.test@Test$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.test@Test"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.test@Test"*)* }
@"shadow.test@Test$methods" = constant %"shadow.test@Test$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.test@Test"*)* @"shadow.test@Test$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString", void (%"shadow.test@Test"*)* @"shadow.test@Test$$main" }
%"shadow.test@Test" = type { %"shadow.test@Test$methods"*, %"shadow.test@Test"* }

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

@"shadow.io@Console$class" = external constant %"shadow.standard@Class"
@"shadow.test@Test$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, { %"shadow.standard@Class"**, [1 x %long] } { %"shadow.standard@Class"** null, [1 x %long] [%long 0] }, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.test@Test$$getClass"(%"shadow.test@Test"*) {
    ret %"shadow.standard@Class"* @"shadow.test@Test$class"
}

define void @"shadow.test@Test$$main"(%"shadow.test@Test"*) {
    %this = alloca %"shadow.test@Test"*
    %temp = alloca %"shadow.io@Console"*
    %console = alloca %"shadow.io@Console"*
    %test = alloca %"shadow.test@Test"*
    store %"shadow.test@Test"* %0, %"shadow.test@Test"** %this
    %2 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %3 = icmp ne %"shadow.io@Console"* %2, null
    br %boolean %3, label %.label0, label %.label1
.label0:
    %4 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %4, %"shadow.io@Console"** %temp
    br label %.label2
.label1:
    %5 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %6 = bitcast i8* %5 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %6)
    store %"shadow.io@Console"* %6, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %6, %"shadow.io@Console"** %temp
    br label %.label2
.label2:
    %7 = load %"shadow.io@Console"** %temp
    store %"shadow.io@Console"* %7, %"shadow.io@Console"** %console
    %8 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.test@Test"* getelementptr(%"shadow.test@Test"* null, i32 1) to i64))
    %9 = bitcast i8* %8 to %"shadow.test@Test"*
    call void @"shadow.test@Test$$constructor"(%"shadow.test@Test"* %9)
    store %"shadow.test@Test"* %9, %"shadow.test@Test"** %test
    %10 = load %"shadow.test@Test"** %test
    %11 = getelementptr inbounds %"shadow.test@Test"* %10, i32 0, i32 1
    %12 = load %"shadow.io@Console"** %console
    %13 = load %"shadow.test@Test"** %11
    %14 = bitcast %"shadow.test@Test"* %13 to %"shadow.standard@Object"*
    %15 = getelementptr %"shadow.io@Console"* %12, i32 0, i32 0
    %16 = load %"shadow.io@Console$methods"** %15
    %17 = getelementptr %"shadow.io@Console$methods"* %16, i32 0, i32 15
    %18 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %17
    call void %18(%"shadow.io@Console"* %12, %"shadow.standard@Object"* %14)
    br label %.label4
.label3:
    %19 = load %"shadow.io@Console"** %console
    %20 = getelementptr %"shadow.io@Console"* %19, i32 0, i32 0
    %21 = load %"shadow.io@Console$methods"** %20
    %22 = getelementptr %"shadow.io@Console$methods"* %21, i32 0, i32 11
    %23 = load void (%"shadow.io@Console"*, %int)** %22
    call void %23(%"shadow.io@Console"* %19, %int 1)
    br label %.label4
.label4:
    %24 = load %"shadow.test@Test"** %test
    %25 = getelementptr inbounds %"shadow.test@Test"* %24, i32 0, i32 1
    br label %.label6
.label5:
    %26 = load %"shadow.io@Console"** %console
    %27 = getelementptr %"shadow.io@Console"* %26, i32 0, i32 0
    %28 = load %"shadow.io@Console$methods"** %27
    %29 = getelementptr %"shadow.io@Console$methods"* %28, i32 0, i32 11
    %30 = load void (%"shadow.io@Console"*, %int)** %29
    call void %30(%"shadow.io@Console"* %26, %int 2)
    br label %.label6
.label6:
    %31 = load %"shadow.test@Test"** %test
    %32 = getelementptr inbounds %"shadow.test@Test"* %31, i32 0, i32 1
    %33 = load %"shadow.io@Console"** %console
    %34 = load %"shadow.test@Test"** %32
    %35 = bitcast %"shadow.test@Test"* %34 to %"shadow.standard@Object"*
    %36 = getelementptr %"shadow.io@Console"* %33, i32 0, i32 0
    %37 = load %"shadow.io@Console$methods"** %36
    %38 = getelementptr %"shadow.io@Console$methods"* %37, i32 0, i32 15
    %39 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %38
    call void %39(%"shadow.io@Console"* %33, %"shadow.standard@Object"* %35)
    br label %.label8
.label7:
    %40 = load %"shadow.io@Console"** %console
    %41 = getelementptr %"shadow.io@Console"* %40, i32 0, i32 0
    %42 = load %"shadow.io@Console$methods"** %41
    %43 = getelementptr %"shadow.io@Console$methods"* %42, i32 0, i32 11
    %44 = load void (%"shadow.io@Console"*, %int)** %43
    call void %44(%"shadow.io@Console"* %40, %int 3)
    br label %.label8
.label8:
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
