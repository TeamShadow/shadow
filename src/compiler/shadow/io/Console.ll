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

%"Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"Interface$methods" = external constant %"Interface$methods"
%"Interface" = type { %"Interface$methods"* }
declare %boolean @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@String"* @"Interface$$toString"(%"shadow.standard@Interface"*)
declare %"shadow.standard@Interface"* @"Interface$$create"(%"shadow.standard@Interface"*)

%"String$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@String"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, %int (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %boolean (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %ubyte (%"shadow.standard@String"*, %int)*, %int (%"shadow.standard@String"*)* }
@"String$methods" = external constant %"String$methods"
%"String" = type { %"String$methods"* }
declare %boolean @"String$$equals$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %ubyte @"String$$getByte$int"(%"shadow.standard@String"*, %int)
declare %int @"String$$compare$_Pshadow_Pstandard_CString"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %int @"String$$length"(%"shadow.standard@String"*)
declare %"shadow.standard@String"* @"String$$toString"(%"shadow.standard@String"*)
declare %"shadow.standard@String"* @"String$$create"(%"shadow.standard@String"*)

%"Class$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Class"*)*, %boolean (%"shadow.standard@Class"*, %"shadow.standard@Interface"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"Class$methods" = external constant %"Class$methods"
%"Class" = type { %"Class$methods"* }
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CClass"(%"shadow.standard@Class"*, %"shadow.standard@Class"*)
declare %boolean @"Class$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Class"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@Class"* @"Class$$parent"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"Class$$toString"(%"shadow.standard@Class"*)
declare %"shadow.standard@Class"* @"Class$$create"(%"shadow.standard@Class"*)

@"Console$instance" = global %"shadow.io@Console"* null
%"Console$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.io@Console"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.io@Console"*, %boolean)*, void (%"shadow.io@Console"*, %"shadow.standard@Object"*)*, void (%"shadow.io@Console"*, %int)*, void (%"shadow.io@Console"*, %code)*, void (%"shadow.io@Console"*, %long)*, void (%"shadow.io@Console"*, %"shadow.standard@String"*)*, void (%"shadow.io@Console"*, %boolean)*, void (%"shadow.io@Console"*, %int)*, void (%"shadow.io@Console"*, %code)*, void (%"shadow.io@Console"*, %long)*, void (%"shadow.io@Console"*, %"shadow.standard@String"*)*, void (%"shadow.io@Console"*, %"shadow.standard@Object"*)*, void (%"shadow.io@Console"*)* }
@"Console$methods" = constant %"Console$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.io@Console"*)* @"Console$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"Object$$toString", void (%"shadow.io@Console"*, %boolean)* @"Console$$print$boolean", void (%"shadow.io@Console"*, %"shadow.standard@Object"*)* @"Console$$print$_Pshadow_Pstandard_CObject", void (%"shadow.io@Console"*, %int)* @"Console$$print$int", void (%"shadow.io@Console"*, %code)* @"Console$$print$code", void (%"shadow.io@Console"*, %long)* @"Console$$print$long", void (%"shadow.io@Console"*, %"shadow.standard@String"*)* @"Console$$print$_Pshadow_Pstandard_CString", void (%"shadow.io@Console"*, %boolean)* @"Console$$printLine$boolean", void (%"shadow.io@Console"*, %int)* @"Console$$printLine$int", void (%"shadow.io@Console"*, %code)* @"Console$$printLine$code", void (%"shadow.io@Console"*, %long)* @"Console$$printLine$long", void (%"shadow.io@Console"*, %"shadow.standard@String"*)* @"Console$$printLine$_Pshadow_Pstandard_CString", void (%"shadow.io@Console"*, %"shadow.standard@Object"*)* @"Console$$printLine$_Pshadow_Pstandard_CObject", void (%"shadow.io@Console"*)* @"Console$$printLine" }
%"Console" = type { %"Console$methods"* }

%"Object$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"Object$methods" = external constant %"Object$methods"
%"Object" = type { %"Object$methods"* }
declare %"shadow.standard@Class"* @"Object$$getClass"(%"shadow.standard@Object"*)
declare %boolean @"Object$$equals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$freeze"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"Object$$toString"(%"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$create"(%"shadow.standard@Object"*)
declare %boolean @"Object$$referenceEquals$_Pshadow_Pstandard_CObject"(%"shadow.standard@Object"*, %"shadow.standard@Object"*)
declare %"shadow.standard@Object"* @"Object$$copy"(%"shadow.standard@Object"*)

@"Interface$class" = external constant %"shadow.standard@Class"
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"Console$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"Console$$getClass"(%"shadow.io@Console"*) {
    ret %"shadow.standard@Class"* @"Console$class"
}

define void @"Console$$printLine$boolean"(%"shadow.io@Console"*, %boolean) {
    %this = alloca %"shadow.io@Console"*
    %b = alloca %boolean
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %boolean %1, %boolean* %b
    %3 = load %"shadow.io@Console"** %this
    %4 = load %boolean* %b
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 6
    %8 = load void (%"shadow.io@Console"*, %boolean)** %7
    call void %8(%"shadow.io@Console"* %3, %boolean %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine$int"(%"shadow.io@Console"*, %int) {
    %this = alloca %"shadow.io@Console"*
    %i = alloca %int
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %int %1, %int* %i
    %3 = load %"shadow.io@Console"** %this
    %4 = load %int* %i
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 8
    %8 = load void (%"shadow.io@Console"*, %int)** %7
    call void %8(%"shadow.io@Console"* %3, %int %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine$code"(%"shadow.io@Console"*, %code) {
    %this = alloca %"shadow.io@Console"*
    %c = alloca %code
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %code %1, %code* %c
    %3 = load %"shadow.io@Console"** %this
    %4 = load %code* %c
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 9
    %8 = load void (%"shadow.io@Console"*, %code)** %7
    call void %8(%"shadow.io@Console"* %3, %code %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine$long"(%"shadow.io@Console"*, %long) {
    %this = alloca %"shadow.io@Console"*
    %l = alloca %long
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %long %1, %long* %l
    %3 = load %"shadow.io@Console"** %this
    %4 = load %long* %l
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 10
    %8 = load void (%"shadow.io@Console"*, %long)** %7
    call void %8(%"shadow.io@Console"* %3, %long %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine$_Pshadow_Pstandard_CString"(%"shadow.io@Console"*, %"shadow.standard@String"*) {
    %this = alloca %"shadow.io@Console"*
    %s = alloca %"shadow.standard@String"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@String"* %1, %"shadow.standard@String"** %s
    %3 = load %"shadow.io@Console"** %this
    %4 = load %"shadow.standard@String"** %s
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 11
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %7
    call void %8(%"shadow.io@Console"* %3, %"shadow.standard@String"* %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine$_Pshadow_Pstandard_CObject"(%"shadow.io@Console"*, %"shadow.standard@Object"*) {
    %this = alloca %"shadow.io@Console"*
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@Object"* %1, %"shadow.standard@Object"** %o
    %3 = load %"shadow.io@Console"** %this
    %4 = load %"shadow.standard@Object"** %o
    %5 = getelementptr %"shadow.io@Console"* %3, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 7
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %7
    call void %8(%"shadow.io@Console"* %3, %"shadow.standard@Object"* %4)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 18
    %13 = load void (%"shadow.io@Console"*)** %12
    call void %13(%"shadow.io@Console"* %9)
    ret void
}
define void @"Console$$printLine"(%"shadow.io@Console"*) {
    %this = alloca %"shadow.io@Console"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    %2 = load %"shadow.io@Console"** %this
    %3 = getelementptr %"shadow.io@Console"* %2, i32 0, i32 0
    %4 = load %"Console$methods"** %3
    %5 = getelementptr %"Console$methods"* %4, i32 0, i32 9
    %6 = load void (%"shadow.io@Console"*, %code)** %5
    call void %6(%"shadow.io@Console"* %2, %code 10)
    ret void
}
define %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"*) {
    %this = alloca %"shadow.io@Console"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    %2 = load %"shadow.io@Console"** %this
    %3 = getelementptr %"shadow.io@Console"* %2, i32 0, i32 0
    store %"Console$methods"* @"Console$methods", %"Console$methods"** %3
    %4 = load %"shadow.io@Console"** %this
    ret %"shadow.io@Console"* %4
}
define void @"Console$$print$boolean"(%"shadow.io@Console"*, %boolean) {
    %this = alloca %"shadow.io@Console"*
    %b = alloca %boolean
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %boolean %1, %boolean* %b
    %3 = load %boolean* %b
    br %boolean %3, label %.label0, label %.label1
.label0:
    %4 = load %"shadow.io@Console"** %this
    %5 = getelementptr %"shadow.io@Console"* %4, i32 0, i32 0
    %6 = load %"Console$methods"** %5
    %7 = getelementptr %"Console$methods"* %6, i32 0, i32 11
    %8 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %7
    call void %8(%"shadow.io@Console"* %4, %"shadow.standard@String"* @.str1)
    br label %.label2
.label1:
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 11
    %13 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %12
    call void %13(%"shadow.io@Console"* %9, %"shadow.standard@String"* @.str2)
    br label %.label2
.label2:
    ret void
}
define void @"Console$$print$_Pshadow_Pstandard_CObject"(%"shadow.io@Console"*, %"shadow.standard@Object"*) {
    %this = alloca %"shadow.io@Console"*
    %o = alloca %"shadow.standard@Object"*
    store %"shadow.io@Console"* %0, %"shadow.io@Console"** %this
    store %"shadow.standard@Object"* %1, %"shadow.standard@Object"** %o
    %3 = load %"shadow.standard@Object"** %o
    %4 = getelementptr %"shadow.standard@Object"* %3, i32 0, i32 0
    %5 = load %"Object$methods"** %4
    %6 = getelementptr %"Object$methods"* %5, i32 0, i32 5
    %7 = load %"shadow.standard@String"* (%"shadow.standard@Object"*)** %6
    %8 = call %"shadow.standard@String"* %7(%"shadow.standard@Object"* %3)
    %9 = load %"shadow.io@Console"** %this
    %10 = getelementptr %"shadow.io@Console"* %9, i32 0, i32 0
    %11 = load %"Console$methods"** %10
    %12 = getelementptr %"Console$methods"* %11, i32 0, i32 11
    %13 = load void (%"shadow.io@Console"*, %"shadow.standard@String"*)** %12
    call void %13(%"shadow.io@Console"* %9, %"shadow.standard@String"* %8)
    ret void
}
declare void @"Console$$print$int"(%"shadow.io@Console"*, %int)
declare void @"Console$$print$code"(%"shadow.io@Console"*, %code)
declare void @"Console$$print$long"(%"shadow.io@Console"*, %long)
declare void @"Console$$print$_Pshadow_Pstandard_CString"(%"shadow.io@Console"*, %"shadow.standard@String"*)

@.array0 = private unnamed_addr constant [7 x %ubyte] c"Console"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([7 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 7] } }
@.array1 = private unnamed_addr constant [4 x %ubyte] c"true"
@.str1 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([4 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 4] } }
@.array2 = private unnamed_addr constant [5 x %ubyte] c"false"
@.str2 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([5 x %ubyte]* @.array2, i32 0, i32 0), [1 x %long] [%long 5] } }
