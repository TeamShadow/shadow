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

%"shadow.test@ConsoleTest$methods" = type { %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)*, %"shadow.standard@Object"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.test@ConsoleTest"*)* }
@"shadow.test@ConsoleTest$methods" = constant %"shadow.test@ConsoleTest$methods" { %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$clone", %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)* @"shadow.test@ConsoleTest$$getClass", %"shadow.standard@Object"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$immutableClone", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString", void (%"shadow.test@ConsoleTest"*)* @"shadow.test@ConsoleTest$$main" }
%"shadow.test@ConsoleTest" = type { %"shadow.test@ConsoleTest$methods"* }

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
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.test@ConsoleTest$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, { %"shadow.standard@Class"**, [1 x %long] } { %"shadow.standard@Class"** null, [1 x %long] [%long 0] }, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.test@ConsoleTest$$getClass"(%"shadow.test@ConsoleTest"*) {
    ret %"shadow.standard@Class"* @"shadow.test@ConsoleTest$class"
}

define void @"shadow.test@ConsoleTest$$constructor"(%"shadow.test@ConsoleTest"*) {
    %this = alloca %"shadow.test@ConsoleTest"*
    store %"shadow.test@ConsoleTest"* %0, %"shadow.test@ConsoleTest"** %this
    %2 = load %"shadow.test@ConsoleTest"** %this
    %3 = getelementptr %"shadow.test@ConsoleTest"* %2, i32 0, i32 0
    store %"shadow.test@ConsoleTest$methods"* @"shadow.test@ConsoleTest$methods", %"shadow.test@ConsoleTest$methods"** %3
    ret void
}
define void @"shadow.test@ConsoleTest$$main"(%"shadow.test@ConsoleTest"*) {
    %this = alloca %"shadow.test@ConsoleTest"*
    %temp = alloca %"shadow.io@Console"*
    %console = alloca %"shadow.io@Console"*
    %temp_ = alloca %"shadow.io@Console"*
    %temp__ = alloca %"shadow.io@Console"*
    %temp___ = alloca %"shadow.io@Console"*
    %temp____ = alloca %"shadow.io@Console"*
    %temp_____ = alloca %"shadow.io@Console"*
    %temp______ = alloca %"shadow.io@Console"*
    %temp_______ = alloca %"shadow.io@Console"*
    %temp________ = alloca %"shadow.io@Console"*
    store %"shadow.test@ConsoleTest"* %0, %"shadow.test@ConsoleTest"** %this
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
    %8 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %9 = icmp ne %"shadow.io@Console"* %8, null
    br %boolean %9, label %.label3, label %.label4
.label3:
    %10 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %10, %"shadow.io@Console"** %temp_
    br label %.label5
.label4:
    %11 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %12 = bitcast i8* %11 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %12)
    store %"shadow.io@Console"* %12, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %12, %"shadow.io@Console"** %temp_
    br label %.label5
.label5:
    %13 = load %"shadow.io@Console"** %temp_
    %14 = getelementptr %"shadow.io@Console"* %13, i32 0, i32 0
    %15 = load %"shadow.io@Console$methods"** %14
    %16 = getelementptr %"shadow.io@Console$methods"* %15, i32 0, i32 11
    %17 = load void (%"shadow.io@Console"*, %int)** %16
    call void %17(%"shadow.io@Console"* %13, %int 12345)
    %18 = load %"shadow.io@Console"** %console
    %19 = getelementptr %"shadow.io@Console"* %18, i32 0, i32 0
    %20 = load %"shadow.io@Console$methods"** %19
    %21 = getelementptr %"shadow.io@Console$methods"* %20, i32 0, i32 7
    %22 = load void (%"shadow.io@Console"*, %code)** %21
    call void %22(%"shadow.io@Console"* %18, %code 72)
    %23 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %24 = icmp ne %"shadow.io@Console"* %23, null
    br %boolean %24, label %.label6, label %.label7
.label6:
    %25 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %25, %"shadow.io@Console"** %temp__
    br label %.label8
.label7:
    %26 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %27 = bitcast i8* %26 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %27)
    store %"shadow.io@Console"* %27, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %27, %"shadow.io@Console"** %temp__
    br label %.label8
.label8:
    %28 = load %"shadow.io@Console"** %temp__
    %29 = getelementptr %"shadow.io@Console"* %28, i32 0, i32 0
    %30 = load %"shadow.io@Console$methods"** %29
    %31 = getelementptr %"shadow.io@Console$methods"* %30, i32 0, i32 7
    %32 = load void (%"shadow.io@Console"*, %code)** %31
    call void %32(%"shadow.io@Console"* %28, %code 101)
    %33 = load %"shadow.io@Console"** %console
    %34 = getelementptr %"shadow.io@Console"* %33, i32 0, i32 0
    %35 = load %"shadow.io@Console$methods"** %34
    %36 = getelementptr %"shadow.io@Console$methods"* %35, i32 0, i32 7
    %37 = load void (%"shadow.io@Console"*, %code)** %36
    call void %37(%"shadow.io@Console"* %33, %code 108)
    %38 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %39 = icmp ne %"shadow.io@Console"* %38, null
    br %boolean %39, label %.label9, label %.label10
.label9:
    %40 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %40, %"shadow.io@Console"** %temp___
    br label %.label11
.label10:
    %41 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %42 = bitcast i8* %41 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %42)
    store %"shadow.io@Console"* %42, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %42, %"shadow.io@Console"** %temp___
    br label %.label11
.label11:
    %43 = load %"shadow.io@Console"** %temp___
    %44 = getelementptr %"shadow.io@Console"* %43, i32 0, i32 0
    %45 = load %"shadow.io@Console$methods"** %44
    %46 = getelementptr %"shadow.io@Console$methods"* %45, i32 0, i32 7
    %47 = load void (%"shadow.io@Console"*, %code)** %46
    call void %47(%"shadow.io@Console"* %43, %code 108)
    %48 = load %"shadow.io@Console"** %console
    %49 = getelementptr %"shadow.io@Console"* %48, i32 0, i32 0
    %50 = load %"shadow.io@Console$methods"** %49
    %51 = getelementptr %"shadow.io@Console$methods"* %50, i32 0, i32 7
    %52 = load void (%"shadow.io@Console"*, %code)** %51
    call void %52(%"shadow.io@Console"* %48, %code 111)
    %53 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %54 = icmp ne %"shadow.io@Console"* %53, null
    br %boolean %54, label %.label12, label %.label13
.label12:
    %55 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %55, %"shadow.io@Console"** %temp____
    br label %.label14
.label13:
    %56 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %57 = bitcast i8* %56 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %57)
    store %"shadow.io@Console"* %57, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %57, %"shadow.io@Console"** %temp____
    br label %.label14
.label14:
    %58 = load %"shadow.io@Console"** %temp____
    %59 = getelementptr %"shadow.io@Console"* %58, i32 0, i32 0
    %60 = load %"shadow.io@Console$methods"** %59
    %61 = getelementptr %"shadow.io@Console$methods"* %60, i32 0, i32 7
    %62 = load void (%"shadow.io@Console"*, %code)** %61
    call void %62(%"shadow.io@Console"* %58, %code 32)
    %63 = load %"shadow.io@Console"** %console
    %64 = getelementptr %"shadow.io@Console"* %63, i32 0, i32 0
    %65 = load %"shadow.io@Console$methods"** %64
    %66 = getelementptr %"shadow.io@Console$methods"* %65, i32 0, i32 7
    %67 = load void (%"shadow.io@Console"*, %code)** %66
    call void %67(%"shadow.io@Console"* %63, %code 87)
    %68 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %69 = icmp ne %"shadow.io@Console"* %68, null
    br %boolean %69, label %.label15, label %.label16
.label15:
    %70 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %70, %"shadow.io@Console"** %temp_____
    br label %.label17
.label16:
    %71 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %72 = bitcast i8* %71 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %72)
    store %"shadow.io@Console"* %72, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %72, %"shadow.io@Console"** %temp_____
    br label %.label17
.label17:
    %73 = load %"shadow.io@Console"** %temp_____
    %74 = getelementptr %"shadow.io@Console"* %73, i32 0, i32 0
    %75 = load %"shadow.io@Console$methods"** %74
    %76 = getelementptr %"shadow.io@Console$methods"* %75, i32 0, i32 7
    %77 = load void (%"shadow.io@Console"*, %code)** %76
    call void %77(%"shadow.io@Console"* %73, %code 111)
    %78 = load %"shadow.io@Console"** %console
    %79 = getelementptr %"shadow.io@Console"* %78, i32 0, i32 0
    %80 = load %"shadow.io@Console$methods"** %79
    %81 = getelementptr %"shadow.io@Console$methods"* %80, i32 0, i32 7
    %82 = load void (%"shadow.io@Console"*, %code)** %81
    call void %82(%"shadow.io@Console"* %78, %code 114)
    %83 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %84 = icmp ne %"shadow.io@Console"* %83, null
    br %boolean %84, label %.label18, label %.label19
.label18:
    %85 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %85, %"shadow.io@Console"** %temp______
    br label %.label20
.label19:
    %86 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %87 = bitcast i8* %86 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %87)
    store %"shadow.io@Console"* %87, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %87, %"shadow.io@Console"** %temp______
    br label %.label20
.label20:
    %88 = load %"shadow.io@Console"** %temp______
    %89 = getelementptr %"shadow.io@Console"* %88, i32 0, i32 0
    %90 = load %"shadow.io@Console$methods"** %89
    %91 = getelementptr %"shadow.io@Console$methods"* %90, i32 0, i32 7
    %92 = load void (%"shadow.io@Console"*, %code)** %91
    call void %92(%"shadow.io@Console"* %88, %code 108)
    %93 = load %"shadow.io@Console"** %console
    %94 = getelementptr %"shadow.io@Console"* %93, i32 0, i32 0
    %95 = load %"shadow.io@Console$methods"** %94
    %96 = getelementptr %"shadow.io@Console$methods"* %95, i32 0, i32 7
    %97 = load void (%"shadow.io@Console"*, %code)** %96
    call void %97(%"shadow.io@Console"* %93, %code 100)
    %98 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %99 = icmp ne %"shadow.io@Console"* %98, null
    br %boolean %99, label %.label21, label %.label22
.label21:
    %100 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %100, %"shadow.io@Console"** %temp_______
    br label %.label23
.label22:
    %101 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %102 = bitcast i8* %101 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %102)
    store %"shadow.io@Console"* %102, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %102, %"shadow.io@Console"** %temp_______
    br label %.label23
.label23:
    %103 = load %"shadow.io@Console"** %temp_______
    %104 = getelementptr %"shadow.io@Console"* %103, i32 0, i32 0
    %105 = load %"shadow.io@Console$methods"** %104
    %106 = getelementptr %"shadow.io@Console$methods"* %105, i32 0, i32 7
    %107 = load void (%"shadow.io@Console"*, %code)** %106
    call void %107(%"shadow.io@Console"* %103, %code 33)
    %108 = load %"shadow.io@Console"** %console
    %109 = getelementptr %"shadow.io@Console"* %108, i32 0, i32 0
    %110 = load %"shadow.io@Console$methods"** %109
    %111 = getelementptr %"shadow.io@Console$methods"* %110, i32 0, i32 7
    %112 = load void (%"shadow.io@Console"*, %code)** %111
    call void %112(%"shadow.io@Console"* %108, %code 10)
    %113 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    %114 = icmp ne %"shadow.io@Console"* %113, null
    br %boolean %114, label %.label24, label %.label25
.label24:
    %115 = load %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %115, %"shadow.io@Console"** %temp________
    br label %.label26
.label25:
    %116 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.io@Console"* getelementptr(%"shadow.io@Console"* null, i32 1) to i64))
    %117 = bitcast i8* %116 to %"shadow.io@Console"*
    call void @"shadow.io@Console$$constructor"(%"shadow.io@Console"* %117)
    store %"shadow.io@Console"* %117, %"shadow.io@Console"** @"shadow.io@Console$instance"
    store %"shadow.io@Console"* %117, %"shadow.io@Console"** %temp________
    br label %.label26
.label26:
    %118 = load %"shadow.io@Console"** %temp________
    %119 = getelementptr %"shadow.io@Console"* %118, i32 0, i32 0
    %120 = load %"shadow.io@Console$methods"** %119
    %121 = getelementptr %"shadow.io@Console$methods"* %120, i32 0, i32 16
    %122 = load void (%"shadow.io@Console"*)** %121
    call void %122(%"shadow.io@Console"* %118)
    ret void
}

@.array0 = private unnamed_addr constant [23 x %ubyte] c"shadow.test@ConsoleTest"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([23 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 23] } }
