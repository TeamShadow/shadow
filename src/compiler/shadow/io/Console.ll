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

%"shadow.io@Console$methods" = type { %"shadow.standard@Class"* (%"shadow.io@Console"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.io@Console$methods" = constant %"shadow.io@Console$methods" { %"shadow.standard@Class"* (%"shadow.io@Console"*)* @"shadow.io@Console$$getClass", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString" }
%"shadow.io@Console" = type { %"shadow.io@Console$methods"* }

%"shadow.standard@Class$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)* }
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

%"shadow.standard@Object$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.standard@Object$methods" = external constant %"shadow.standard@Object$methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object$methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object$$getClass"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object$$toString"(%"shadow.standard@Object"*)
declare void @"shadow.standard@Object$$constructor"(%"shadow.standard@Object"*)

%"shadow.standard@String$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %long)*, %long (%"shadow.standard@String"*)* }
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

define void @"shadow.io@Console$static$printLine$boolean"(%boolean) {
    %b = alloca %boolean
    store %boolean %0, %boolean* %b
    %2 = load %boolean* %b
    call void @"shadow.io@Console$static$print$boolean"(%boolean %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine$int"(%int) {
    %i = alloca %int
    store %int %0, %int* %i
    %2 = load %int* %i
    call void @"shadow.io@Console$static$print$int"(%int %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine$code"(%code) {
    %c = alloca %code
    store %code %0, %code* %c
    %2 = load %code* %c
    call void @"shadow.io@Console$static$print$code"(%code %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine$long"(%long) {
    %l = alloca %long
    store %long %0, %long* %l
    %2 = load %long* %l
    call void @"shadow.io@Console$static$print$long"(%long %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine$shadow.standard@String"(%"shadow.standard@String"*) {
    %s = alloca %"shadow.standard@String"*
    store %"shadow.standard@String"* %0, %"shadow.standard@String"** %s
    %2 = load %"shadow.standard@String"** %s
    call void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"* %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine$shadow.standard@Object"(%"shadow.standard@Object"*) {
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %o
    %2 = load %"shadow.standard@Object"** %o
    call void @"shadow.io@Console$static$print$shadow.standard@Object"(%"shadow.standard@Object"* %2)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.io@Console$static$printLine"() {
    call void @"shadow.io@Console$static$print$code"(%code 10)
    ret void
}
define void @"shadow.io@Console$static$print$boolean"(%boolean) {
    %b = alloca %boolean
    store %boolean %0, %boolean* %b
    %2 = load %boolean* %b
    br %boolean %2, label %.label0, label %.label1
.label0:
    call void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"* @.str1)
    br label %.label2
.label1:
    call void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"* @.str2)
    br label %.label2
.label2:
    ret void
}
define void @"shadow.io@Console$static$print$shadow.standard@Object"(%"shadow.standard@Object"*) {
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.standard@Object"* %0, %"shadow.standard@Object"** %o
    %2 = load %"shadow.standard@Object"** %o
    %3 = getelementptr %"shadow.standard@Object"* %2, i32 0, i32 0
    %4 = load %"shadow.standard@Object$methods"** %3
    %5 = getelementptr %"shadow.standard@Object$methods"* %4, i32 0, i32 1
    %6 = load %"shadow.standard@String"* (%"shadow.standard@Object"*)** %5
    %7 = call %"shadow.standard@String"* %6(%"shadow.standard@Object"* %2)
    call void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"* %7)
    ret void
}
declare void @"shadow.io@Console$static$print$int"(%int)
declare void @"shadow.io@Console$static$print$code"(%code)
declare void @"shadow.io@Console$static$print$long"(%long)
declare void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"*)
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
