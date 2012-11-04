; shadow.io@Console

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
@"shadow.io@Console$methods" = constant %"shadow.io@Console$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.io@Console"*)* @"shadow.io@Console$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString", void (%"shadow.io@Console"*, %boolean)* @"shadow.io@Console$$print$boolean", void (%"shadow.io@Console"*, %"shadow.standard@Object"*)* @"shadow.io@Console$$print$shadow.standard@Object", void (%"shadow.io@Console"*, %int)* @"shadow.io@Console$$print$int", void (%"shadow.io@Console"*, %code)* @"shadow.io@Console$$print$code", void (%"shadow.io@Console"*, %long)* @"shadow.io@Console$$print$long", void (%"shadow.io@Console"*, %"shadow.standard@String"*)* @"shadow.io@Console$$print$shadow.standard@String", void (%"shadow.io@Console"*, %boolean)* @"shadow.io@Console$$printLine$boolean", void (%"shadow.io@Console"*, %int)* @"shadow.io@Console$$printLine$int", void (%"shadow.io@Console"*, %code)* @"shadow.io@Console$$printLine$code", void (%"shadow.io@Console"*, %long)* @"shadow.io@Console$$printLine$long", void (%"shadow.io@Console"*, %"shadow.standard@String"*)* @"shadow.io@Console$$printLine$shadow.standard@String", void (%"shadow.io@Console"*, %"shadow.standard@Object"*)* @"shadow.io@Console$$printLine$shadow.standard@Object", void (%"shadow.io@Console"*)* @"shadow.io@Console$$printLine" }
%"shadow.io@Console" = type { %"shadow.io@Console$methods"* }

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

@"shadow.io@Console$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, { %"shadow.standard@Class"**, [1 x %long] } { %"shadow.standard@Class"** null, [1 x %long] [%long 0] }, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.io@Console$$getClass"(%"shadow.io@Console"*) {
    ret %"shadow.standard@Class"* @"shadow.io@Console$class"
}

define void @"shadow.io@Console$$printLine$boolean"(%"shadow.io@Console"*, %boolean) {
    %this = alloca %"shadow.io@Console"*
    %b = alloca %boolean
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %boolean %1, %boolean* %b
    %3 = load %"shadow.io@Console"** %this
    %4 = load %boolean* %b
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 4
    %8 = load void (%"shadow.io@Console"*, %boolean)** %7
    call void %8(%"shadow.io@Console"* %3, %boolean %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine$int"(%"shadow.io@Console"*, %int) {
    %this = alloca %"shadow.io@Console"*
    %i = alloca %int
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %int %1, %int* %i
    %3 = load %"shadow.io@Console"** %this
    %4 = load %int* %i
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 6
    %8 = load void (%"shadow.io@Console"*, %int)** %7
    call void %8(%"shadow.io@Console"* %3, %int %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine$code"(%"shadow.io@Console"*, %code) {
    %this = alloca %"shadow.io@Console"*
    %c = alloca %code
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %code %1, %code* %c
    %3 = load %"shadow.io@Console"** %this
    %4 = load %code* %c
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 7
    %8 = load void (%"shadow.io@Console"*, %code)** %7
    call void %8(%"shadow.io@Console"* %3, %code %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine$long"(%"shadow.io@Console"*, %long) {
    %this = alloca %"shadow.io@Console"*
    %l = alloca %long
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %long %1, %long* %l
    %3 = load %"shadow.io@Console"** %this
    %4 = load %long* %l
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 8
    %8 = load void (%"shadow.io@Console"*, %long)** %7
    call void %8(%"shadow.io@Console"* %3, %long %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine$shadow.standard@String"(%"shadow.io@Console"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.io@Console"*
    %s = alloca %"shadow.standard@String"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %s
    %3 = load %"shadow.io@Console"** %this
    %4 = load %"shadow.standard@String"** %s
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 9
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %7
    call void %8(%"shadow.io@Console"* %3, %"shadow.standard@String"* %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine$shadow.standard@Object"(%"shadow.io@Console"*, %"shadow.standard@Object"*) {
    %this = alloca %"shadow.io@Console"*
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@Object"* %1, %"shadow.standard@Object"** %o
    %3 = load %"shadow.io@Console"** %this
    %4 = load %"shadow.standard@Object"** %o
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 5
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %7
    call void %8(%"shadow.io@Console"* %3, %"shadow.standard@Object"* %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 16
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"shadow.io@Console$$printLine"(%"shadow.io@Console"*) {
    %this = alloca %"shadow.io@Console"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    %2 = load %"shadow.io@Console"** %this
    %3 = getelementptr %"shadow.io@Console"* %2, i32 0, i32 0
    %4 = load %"shadow.io@Console$methods"** %3
    %5 = getelementptr %"shadow.io@Console$methods"* %4, i32 0, i32 7
    %6 = load void (%"shadow.io@Console"*, %code)** %5
    call void %6(%"shadow.io@Console"* %2, %code 10)
    ret void
}
define void @"shadow.io@Console$$print$boolean"(%"shadow.io@Console"*, %boolean) {
    %this = alloca %"shadow.io@Console"*
    %b = alloca %boolean
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %boolean %1, %boolean* %b
    %3 = load %boolean* %b
    br %boolean %3, label %.label0, label %.label1
.label0:
    %4 = load %"shadow.io@Console"** %this
    %5 = getelementptr %"shadow.io@Console"* %4, i32 0, i32 0
    %6 = load %"shadow.io@Console$methods"** %5
    %7 = getelementptr %"shadow.io@Console$methods"* %6, i32 0, i32 9
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %7
    call void %8(%"shadow.io@Console"* %4, %"shadow.standard@String"* @.str1)
    br label %.label2
.label1:
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 9
    %13 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %12
    call void %13(%"shadow.io@Console"* %9, %"shadow.standard@String"* @.str2)
    br label %.label2
.label2:
    ret void
}
define void @"shadow.io@Console$$print$shadow.standard@Object"(%"shadow.io@Console"*, %"shadow.standard@Object"*) {
    %this = alloca %"shadow.io@Console"*
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@Object"* %1, %"shadow.standard@Object"** %o
    %3 = load %"shadow.standard@Object"** %o
    %4 = getelementptr %"shadow.standard@Object"* %3, i32 0, i32 0
    %5 = load %"shadow.standard@Object$methods"** %4
    %6 = getelementptr %"shadow.standard@Object$methods"* %5, i32 0, i32 3
    %7 = load %"shadow.standard@String"* (%"shadow.standard@Object"*)** %6
    %8 = call %"shadow.standard@String"* %7(%"shadow.standard@Object"* %3)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"shadow.io@Console$methods"** %10
    %12 = getelementptr %"shadow.io@Console$methods"* %11, i32 0, i32 9
    %13 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %12
    call void %13(%"shadow.io@Console"* %9, %"shadow.standard@String"* %8)
    ret void
}
declare void @"shadow.io@Console$$print$int"(%"shadow.io@Console"*, %int)
declare void @"shadow.io@Console$$print$code"(%"shadow.io@Console"*, %code)
declare void @"shadow.io@Console$$print$long"(%"shadow.io@Console"*, %long)
declare void @"shadow.io@Console$$print$shadow.standard@String"(%"shadow.io@Console"*, %"shadow.standard@String"*)
define void @"shadow.io@Console$$constructor"(%"shadow.io@Console"*) {
    %this = alloca %"shadow.io@Console"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    %2 = load %"shadow.io@Console"** %this
    %3 = getelementptr %"shadow.io@Console"* %2, i32 0, i32 0
    store %"shadow.io@Console$methods"* @"shadow.io@Console$methods", %"shadow.io@Console$methods"** %3
    ret void
}

@.array0 = private unnamed_addr constant [17 x %ubyte] c"shadow.io@Console"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([17 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 17] } }
@.array1 = private unnamed_addr constant [4 x %ubyte] c"true"
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([4 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 4] } }
@.array2 = private unnamed_addr constant [5 x %ubyte] c"false"
@.str2 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([5 x %ubyte]* @.array2, i32 0, i32 0), [1 x %long] [%long 5] } }
