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

%"Interface$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Interface"*)*, %boolean (%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)* }
@"Interface$methods" = external constant %"Interface$methods"
%"Interface" = type { %"Interface$methods"* }
declare %boolean @"Interface$$isSubtype$_Pshadow_Pstandard_CInterface"(%"shadow.standard@Interface"*, %"shadow.standard@Interface"*)
declare %"shadow.standard@String"* @"Interface$$toString"(%"shadow.standard@Interface"*)
declare %"shadow.standard@Interface"* @"Interface$$create"(%"shadow.standard@Interface"*)

%"Test$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.test@Test"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.test@Test"*)* }
@"Test$methods" = constant %"Test$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.test@Test"*)* @"Test$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"Object$$toString", void (%"shadow.test@Test"*)* @"Test$$main" }
%"Test" = type { %"Test$methods"*, %"shadow.test@Test"* }

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
@"Console$methods" = external constant %"Console$methods"
%"Console" = type { %"Console$methods"* }
declare void @"Console$$printLine$boolean"(%"shadow.io@Console"*, %boolean)
declare void @"Console$$printLine$int"(%"shadow.io@Console"*, %int)
declare void @"Console$$printLine$code"(%"shadow.io@Console"*, %code)
declare void @"Console$$printLine$long"(%"shadow.io@Console"*, %long)
declare void @"Console$$printLine$_Pshadow_Pstandard_CString"(%"shadow.io@Console"*, %"shadow.standard@String"*)
declare void @"Console$$printLine$_Pshadow_Pstandard_CObject"(%"shadow.io@Console"*, %"shadow.standard@Object"*)
declare void @"Console$$printLine"(%"shadow.io@Console"*)
declare %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"*)
declare void @"Console$$print$boolean"(%"shadow.io@Console"*, %boolean)
declare void @"Console$$print$_Pshadow_Pstandard_CObject"(%"shadow.io@Console"*, %"shadow.standard@Object"*)
declare void @"Console$$print$int"(%"shadow.io@Console"*, %int)
declare void @"Console$$print$code"(%"shadow.io@Console"*, %code)
declare void @"Console$$print$long"(%"shadow.io@Console"*, %long)
declare void @"Console$$print$_Pshadow_Pstandard_CString"(%"shadow.io@Console"*, %"shadow.standard@String"*)

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
@"Test$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"Console$class" = external constant %"shadow.standard@Class"
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"Test$$getClass"(%"shadow.test@Test"*) {
    ret %"shadow.standard@Class"* @"Test$class"
}

define %"shadow.test@Test"* @"Test$$create"(%"shadow.test@Test"*) {
    %this = alloca %"shadow.test@Test"*
    store %"shadow.test@Test"* %0, %"shadow.test@Test"** %this
    %2 = load %"shadow.test@Test"** %this
    %3 = getelementptr %"shadow.test@Test"* %2, i32 0, i32 0
    store %"Test$methods"* @"Test$methods", %"Test$methods"** %3
    %4 = load %"shadow.test@Test"** %this
    %5 = getelementptr inbounds %"shadow.test@Test"* %4, i32 0, i32 1
    %6 = load %"shadow.test@Test"** %this
    store %"shadow.test@Test"* %6, %"shadow.test@Test"** %5
    %7 = load %"shadow.test@Test"** %this
    ret %"shadow.test@Test"* %7
}
define void @"Test$$main"(%"shadow.test@Test"*) {
    %this = alloca %"shadow.test@Test"*
    %test = alloca %"shadow.test@Test"*
    %console = alloca %"shadow.io@Console"*
    store %"shadow.test@Test"* %0, %"shadow.test@Test"** %this
    %2 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.test@Test"* getelementptr (%"shadow.test@Test"* null, i32 1) to i64))
    %3 = bitcast i8* %2 to %"shadow.test@Test"*
    %4 = call %"shadow.test@Test"* @"Test$$create"(%"shadow.test@Test"* %3)
    store %"shadow.test@Test"* %3, %"shadow.test@Test"** %test
    %5 = load %"shadow.io@Console"** @"Console$instance"
    %6 = icmp eq %"shadow.io@Console"* %5, null
    br %boolean %6, label %.label2, label %.label3
.label2:
    %7 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %8 = bitcast i8* %7 to %"shadow.io@Console"*
    %9 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %8)
    store %"shadow.io@Console"* %9, %"shadow.io@Console"** @"Console$instance"
    br label %.label3
.label3:
    %10 = load %"shadow.io@Console"** @"Console$instance"
    %11 = load %"shadow.test@Test"** %test
    %12 = icmp ne %"shadow.test@Test"* %11, null
    br %boolean %12, label %.label5, label %.label0
.label5:
    %13 = load %"shadow.test@Test"** %test
    %14 = getelementptr inbounds %"shadow.test@Test"* %13, i32 0, i32 1
    %15 = load %"shadow.test@Test"** %14
    %16 = icmp ne %"shadow.test@Test"* %15, null
    br %boolean %16, label %.label4, label %.label0
.label4:
    %17 = load %"shadow.test@Test"** %14
    %18 = bitcast %"shadow.test@Test"* %17 to %"shadow.standard@Object"*
    %19 = getelementptr %"shadow.io@Console"* %10, i32 0, i32 0
    %20 = load %"Console$methods"** %19
    %21 = getelementptr %"Console$methods"* %20, i32 0, i32 17
    %22 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %21
    call void %22(%"shadow.io@Console"* %10, %"shadow.standard@Object"* %18)
    br label %.label1
.label0:
    %23 = load %"shadow.io@Console"** @"Console$instance"
    %24 = icmp eq %"shadow.io@Console"* %23, null
    br %boolean %24, label %.label6, label %.label7
.label6:
    %25 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %26 = bitcast i8* %25 to %"shadow.io@Console"*
    %27 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %26)
    store %"shadow.io@Console"* %27, %"shadow.io@Console"** @"Console$instance"
    br label %.label7
.label7:
    %28 = load %"shadow.io@Console"** @"Console$instance"
    %29 = getelementptr %"shadow.io@Console"* %28, i32 0, i32 0
    %30 = load %"Console$methods"** %29
    %31 = getelementptr %"Console$methods"* %30, i32 0, i32 13
    %32 = load void (%"shadow.io@Console"*, %int)** %31
    call void %32(%"shadow.io@Console"* %28, %int 1)
    br label %.label1
.label1:
    %33 = load %"shadow.io@Console"** @"Console$instance"
    %34 = icmp eq %"shadow.io@Console"* %33, null
    br %boolean %34, label %.label8, label %.label9
.label8:
    %35 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %36 = bitcast i8* %35 to %"shadow.io@Console"*
    %37 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %36)
    store %"shadow.io@Console"* %37, %"shadow.io@Console"** @"Console$instance"
    br label %.label9
.label9:
    %38 = load %"shadow.io@Console"** @"Console$instance"
    store %"shadow.io@Console"* %38, %"shadow.io@Console"** %console
    %39 = load %"shadow.test@Test"** %test
    %40 = icmp ne %"shadow.test@Test"* %39, null
    br %boolean %40, label %.label12, label %.label10
.label12:
    %41 = load %"shadow.test@Test"** %test
    %42 = getelementptr inbounds %"shadow.test@Test"* %41, i32 0, i32 1
    store %"shadow.test@Test"* null, %"shadow.test@Test"** %42
    br label %.label11
.label10:
    %43 = load %"shadow.io@Console"** %console
    %44 = getelementptr %"shadow.io@Console"* %43, i32 0, i32 0
    %45 = load %"Console$methods"** %44
    %46 = getelementptr %"Console$methods"* %45, i32 0, i32 13
    %47 = load void (%"shadow.io@Console"*, %int)** %46
    call void %47(%"shadow.io@Console"* %43, %int 2)
    br label %.label11
.label11:
    %48 = load %"shadow.test@Test"** %test
    %49 = icmp ne %"shadow.test@Test"* %48, null
    br %boolean %49, label %.label16, label %.label13
.label16:
    %50 = load %"shadow.test@Test"** %test
    %51 = getelementptr inbounds %"shadow.test@Test"* %50, i32 0, i32 1
    %52 = load %"shadow.test@Test"** %51
    %53 = icmp ne %"shadow.test@Test"* %52, null
    br %boolean %53, label %.label15, label %.label13
.label15:
    %54 = load %"shadow.io@Console"** %console
    %55 = load %"shadow.test@Test"** %51
    %56 = bitcast %"shadow.test@Test"* %55 to %"shadow.standard@Object"*
    %57 = getelementptr %"shadow.io@Console"* %54, i32 0, i32 0
    %58 = load %"Console$methods"** %57
    %59 = getelementptr %"Console$methods"* %58, i32 0, i32 17
    %60 = load void (%"shadow.io@Console"*, %"shadow.standard@Object"*)** %59
    call void %60(%"shadow.io@Console"* %54, %"shadow.standard@Object"* %56)
    br label %.label14
.label13:
    %61 = load %"shadow.io@Console"** %console
    %62 = getelementptr %"shadow.io@Console"* %61, i32 0, i32 0
    %63 = load %"Console$methods"** %62
    %64 = getelementptr %"Console$methods"* %63, i32 0, i32 13
    %65 = load void (%"shadow.io@Console"*, %int)** %64
    call void %65(%"shadow.io@Console"* %61, %int 3)
    br label %.label14
.label14:
    ret void
}

@.array0 = private unnamed_addr constant [4 x %ubyte] c"Test"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([4 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 4] } }
