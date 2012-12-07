; shadow.test@ConsoleTest

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

%"ConsoleTest$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)*, %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.test@ConsoleTest"*)* }
@"ConsoleTest$methods" = constant %"ConsoleTest$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$copy", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$equals$_Pshadow_Pstandard_CObject", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"Object$$freeze", %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)* @"ConsoleTest$$getClass", %boolean (%"shadow.standard@Object"*, %"shadow.standard@Object"*)* @"Object$$referenceEquals$_Pshadow_Pstandard_CObject", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"Object$$toString", void (%"shadow.test@ConsoleTest"*)* @"ConsoleTest$$main" }
%"ConsoleTest" = type { %"ConsoleTest$methods"* }

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
@"String$class" = external constant %"shadow.standard@Class"
@"Class$class" = external constant %"shadow.standard@Class"
@"ConsoleTest$class" = constant %"Class" { %"Class$methods"* @"Class$methods", { %"shadow.standard@Interface"**, [1 x %long] } { %"shadow.standard@Interface"** null, [1 x %long] [%long 0] }, %"String"* @.str0, %"shadow.standard@Class"* @"Object$class" }
@"Console$class" = external constant %"shadow.standard@Class"
@"Object$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"ConsoleTest$$getClass"(%"shadow.test@ConsoleTest"*) {
    ret %"shadow.standard@Class"* @"ConsoleTest$class"
}

define %"shadow.test@ConsoleTest"* @"ConsoleTest$$create"(%"shadow.test@ConsoleTest"*) {
    %this = alloca %"shadow.test@ConsoleTest"*
    store %"shadow.test@ConsoleTest"* %0, %"shadow.test@ConsoleTest"** %this
    %2 = load %"shadow.test@ConsoleTest"** %this
    %3 = getelementptr %"shadow.test@ConsoleTest"* %2, i32 0, i32 0
    store %"ConsoleTest$methods"* @"ConsoleTest$methods", %"ConsoleTest$methods"** %3
    %4 = load %"shadow.test@ConsoleTest"** %this
    ret %"shadow.test@ConsoleTest"* %4
}
define void @"ConsoleTest$$main"(%"shadow.test@ConsoleTest"*) {
    %this = alloca %"shadow.test@ConsoleTest"*
    %console = alloca %"shadow.io@Console"*
    store %"shadow.test@ConsoleTest"* %0, %"shadow.test@ConsoleTest"** %this
    %2 = load %"shadow.io@Console"** @"Console$instance"
    %3 = icmp eq %"shadow.io@Console"* %2, null
    br %boolean %3, label %.label0, label %.label1
.label0:
    %4 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %5 = bitcast i8* %4 to %"shadow.io@Console"*
    %6 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %5)
    store %"shadow.io@Console"* %6, %"shadow.io@Console"** @"Console$instance"
    br label %.label1
.label1:
    %7 = load %"shadow.io@Console"** @"Console$instance"
    store %"shadow.io@Console"* %7, %"shadow.io@Console"** %console
    %8 = load %"shadow.io@Console"** @"Console$instance"
    %9 = icmp eq %"shadow.io@Console"* %8, null
    br %boolean %9, label %.label2, label %.label3
.label2:
    %10 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %11 = bitcast i8* %10 to %"shadow.io@Console"*
    %12 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %11)
    store %"shadow.io@Console"* %12, %"shadow.io@Console"** @"Console$instance"
    br label %.label3
.label3:
    %13 = load %"shadow.io@Console"** @"Console$instance"
    %14 = getelementptr %"shadow.io@Console"* %13, i32 0, i32 0
    %15 = load %"Console$methods"** %14
    %16 = getelementptr %"Console$methods"* %15, i32 0, i32 13
    %17 = load void (%"shadow.io@Console"*, %int)** %16
    call void %17(%"shadow.io@Console"* %13, %int 12345)
    %18 = load %"shadow.io@Console"** %console
    %19 = getelementptr %"shadow.io@Console"* %18, i32 0, i32 0
    %20 = load %"Console$methods"** %19
    %21 = getelementptr %"Console$methods"* %20, i32 0, i32 9
    %22 = load void (%"shadow.io@Console"*, %code)** %21
    call void %22(%"shadow.io@Console"* %18, %code 72)
    %23 = load %"shadow.io@Console"** @"Console$instance"
    %24 = icmp eq %"shadow.io@Console"* %23, null
    br %boolean %24, label %.label4, label %.label5
.label4:
    %25 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %26 = bitcast i8* %25 to %"shadow.io@Console"*
    %27 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %26)
    store %"shadow.io@Console"* %27, %"shadow.io@Console"** @"Console$instance"
    br label %.label5
.label5:
    %28 = load %"shadow.io@Console"** @"Console$instance"
    %29 = getelementptr %"shadow.io@Console"* %28, i32 0, i32 0
    %30 = load %"Console$methods"** %29
    %31 = getelementptr %"Console$methods"* %30, i32 0, i32 9
    %32 = load void (%"shadow.io@Console"*, %code)** %31
    call void %32(%"shadow.io@Console"* %28, %code 101)
    %33 = load %"shadow.io@Console"** %console
    %34 = getelementptr %"shadow.io@Console"* %33, i32 0, i32 0
    %35 = load %"Console$methods"** %34
    %36 = getelementptr %"Console$methods"* %35, i32 0, i32 9
    %37 = load void (%"shadow.io@Console"*, %code)** %36
    call void %37(%"shadow.io@Console"* %33, %code 108)
    %38 = load %"shadow.io@Console"** @"Console$instance"
    %39 = icmp eq %"shadow.io@Console"* %38, null
    br %boolean %39, label %.label6, label %.label7
.label6:
    %40 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %41 = bitcast i8* %40 to %"shadow.io@Console"*
    %42 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %41)
    store %"shadow.io@Console"* %42, %"shadow.io@Console"** @"Console$instance"
    br label %.label7
.label7:
    %43 = load %"shadow.io@Console"** @"Console$instance"
    %44 = getelementptr %"shadow.io@Console"* %43, i32 0, i32 0
    %45 = load %"Console$methods"** %44
    %46 = getelementptr %"Console$methods"* %45, i32 0, i32 9
    %47 = load void (%"shadow.io@Console"*, %code)** %46
    call void %47(%"shadow.io@Console"* %43, %code 108)
    %48 = load %"shadow.io@Console"** %console
    %49 = getelementptr %"shadow.io@Console"* %48, i32 0, i32 0
    %50 = load %"Console$methods"** %49
    %51 = getelementptr %"Console$methods"* %50, i32 0, i32 9
    %52 = load void (%"shadow.io@Console"*, %code)** %51
    call void %52(%"shadow.io@Console"* %48, %code 111)
    %53 = load %"shadow.io@Console"** @"Console$instance"
    %54 = icmp eq %"shadow.io@Console"* %53, null
    br %boolean %54, label %.label8, label %.label9
.label8:
    %55 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %56 = bitcast i8* %55 to %"shadow.io@Console"*
    %57 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %56)
    store %"shadow.io@Console"* %57, %"shadow.io@Console"** @"Console$instance"
    br label %.label9
.label9:
    %58 = load %"shadow.io@Console"** @"Console$instance"
    %59 = getelementptr %"shadow.io@Console"* %58, i32 0, i32 0
    %60 = load %"Console$methods"** %59
    %61 = getelementptr %"Console$methods"* %60, i32 0, i32 9
    %62 = load void (%"shadow.io@Console"*, %code)** %61
    call void %62(%"shadow.io@Console"* %58, %code 32)
    %63 = load %"shadow.io@Console"** %console
    %64 = getelementptr %"shadow.io@Console"* %63, i32 0, i32 0
    %65 = load %"Console$methods"** %64
    %66 = getelementptr %"Console$methods"* %65, i32 0, i32 9
    %67 = load void (%"shadow.io@Console"*, %code)** %66
    call void %67(%"shadow.io@Console"* %63, %code 87)
    %68 = load %"shadow.io@Console"** @"Console$instance"
    %69 = icmp eq %"shadow.io@Console"* %68, null
    br %boolean %69, label %.label10, label %.label11
.label10:
    %70 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %71 = bitcast i8* %70 to %"shadow.io@Console"*
    %72 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %71)
    store %"shadow.io@Console"* %72, %"shadow.io@Console"** @"Console$instance"
    br label %.label11
.label11:
    %73 = load %"shadow.io@Console"** @"Console$instance"
    %74 = getelementptr %"shadow.io@Console"* %73, i32 0, i32 0
    %75 = load %"Console$methods"** %74
    %76 = getelementptr %"Console$methods"* %75, i32 0, i32 9
    %77 = load void (%"shadow.io@Console"*, %code)** %76
    call void %77(%"shadow.io@Console"* %73, %code 111)
    %78 = load %"shadow.io@Console"** %console
    %79 = getelementptr %"shadow.io@Console"* %78, i32 0, i32 0
    %80 = load %"Console$methods"** %79
    %81 = getelementptr %"Console$methods"* %80, i32 0, i32 9
    %82 = load void (%"shadow.io@Console"*, %code)** %81
    call void %82(%"shadow.io@Console"* %78, %code 114)
    %83 = load %"shadow.io@Console"** @"Console$instance"
    %84 = icmp eq %"shadow.io@Console"* %83, null
    br %boolean %84, label %.label12, label %.label13
.label12:
    %85 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %86 = bitcast i8* %85 to %"shadow.io@Console"*
    %87 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %86)
    store %"shadow.io@Console"* %87, %"shadow.io@Console"** @"Console$instance"
    br label %.label13
.label13:
    %88 = load %"shadow.io@Console"** @"Console$instance"
    %89 = getelementptr %"shadow.io@Console"* %88, i32 0, i32 0
    %90 = load %"Console$methods"** %89
    %91 = getelementptr %"Console$methods"* %90, i32 0, i32 9
    %92 = load void (%"shadow.io@Console"*, %code)** %91
    call void %92(%"shadow.io@Console"* %88, %code 108)
    %93 = load %"shadow.io@Console"** %console
    %94 = getelementptr %"shadow.io@Console"* %93, i32 0, i32 0
    %95 = load %"Console$methods"** %94
    %96 = getelementptr %"Console$methods"* %95, i32 0, i32 9
    %97 = load void (%"shadow.io@Console"*, %code)** %96
    call void %97(%"shadow.io@Console"* %93, %code 100)
    %98 = load %"shadow.io@Console"** @"Console$instance"
    %99 = icmp eq %"shadow.io@Console"* %98, null
    br %boolean %99, label %.label14, label %.label15
.label14:
    %100 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %101 = bitcast i8* %100 to %"shadow.io@Console"*
    %102 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %101)
    store %"shadow.io@Console"* %102, %"shadow.io@Console"** @"Console$instance"
    br label %.label15
.label15:
    %103 = load %"shadow.io@Console"** @"Console$instance"
    %104 = getelementptr %"shadow.io@Console"* %103, i32 0, i32 0
    %105 = load %"Console$methods"** %104
    %106 = getelementptr %"Console$methods"* %105, i32 0, i32 9
    %107 = load void (%"shadow.io@Console"*, %code)** %106
    call void %107(%"shadow.io@Console"* %103, %code 33)
    %108 = load %"shadow.io@Console"** %console
    %109 = getelementptr %"shadow.io@Console"* %108, i32 0, i32 0
    %110 = load %"Console$methods"** %109
    %111 = getelementptr %"Console$methods"* %110, i32 0, i32 9
    %112 = load void (%"shadow.io@Console"*, %code)** %111
    call void %112(%"shadow.io@Console"* %108, %code 10)
    %113 = load %"shadow.io@Console"** @"Console$instance"
    %114 = icmp eq %"shadow.io@Console"* %113, null
    br %boolean %114, label %.label16, label %.label17
.label16:
    %115 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr (%"shadow.io@Console"* null, i32 1) to i64))
    %116 = bitcast i8* %115 to %"shadow.io@Console"*
    %117 = call %"shadow.io@Console"* @"Console$$create"(%"shadow.io@Console"* %116)
    store %"shadow.io@Console"* %117, %"shadow.io@Console"** @"Console$instance"
    br label %.label17
.label17:
    %118 = load %"shadow.io@Console"** @"Console$instance"
    %119 = getelementptr %"shadow.io@Console"* %118, i32 0, i32 0
    %120 = load %"Console$methods"** %119
    %121 = getelementptr %"Console$methods"* %120, i32 0, i32 18
    %122 = load void (%"shadow.io@Console"*)** %121
    call void %122(%"shadow.io@Console"* %118)
    ret void
}

@.array0 = private unnamed_addr constant [11 x %ubyte] c"ConsoleTest"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"String$methods"* @"String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([11 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 11] } }
