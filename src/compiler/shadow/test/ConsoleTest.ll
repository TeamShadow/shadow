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

%"shadow.io@Console$methods" = type { %"shadow.standard@Class"* (%"shadow.io@Console"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.io@Console$methods" = external constant %"shadow.io@Console$methods"
%"shadow.io@Console" = type { %"shadow.io@Console$methods"* }
declare void @"shadow.io@Console$static$printLine$boolean"(%boolean)
declare void @"shadow.io@Console$static$printLine$int"(%int)
declare void @"shadow.io@Console$static$printLine$code"(%code)
declare void @"shadow.io@Console$static$printLine$long"(%long)
declare void @"shadow.io@Console$static$printLine$shadow.standard@String"(%"shadow.standard@String"*)
declare void @"shadow.io@Console$static$printLine$shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.io@Console$static$printLine"()
declare void @"shadow.io@Console$static$print$boolean"(%boolean)
declare void @"shadow.io@Console$static$print$shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.io@Console$static$print$int"(%int)
declare void @"shadow.io@Console$static$print$code"(%code)
declare void @"shadow.io@Console$static$print$long"(%long)
declare void @"shadow.io@Console$static$print$shadow.standard@String"(%"shadow.standard@String"*)
declare void @"shadow.io@Console$$constructor"(%"shadow.io@Console"*)

%"shadow.standard@Class$methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"shadow.standard@Class$methods" = external constant %"shadow.standard@Class$methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class$methods"*, %"shadow.standard@String"*, %"shadow.standard@Class"* }
declare %"shadow.standard@Class"* @"shadow.standard@Class$$getSuperClass"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$getClassName"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class$$toString"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class$$constructor"(%"shadow.standard@Class"*)

%"shadow.test@ConsoleTest$methods" = type { %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)* }
@"shadow.test@ConsoleTest$methods" = constant %"shadow.test@ConsoleTest$methods" { %"shadow.standard@Class"* (%"shadow.test@ConsoleTest"*)* @"shadow.test@ConsoleTest$$getClass", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object$$toString" }
%"shadow.test@ConsoleTest" = type { %"shadow.test@ConsoleTest$methods"* }

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

@"shadow.io@Console$class" = external constant %"shadow.standard@Class"
@"shadow.standard@Class$class" = external constant %"shadow.standard@Class"
@"shadow.test@ConsoleTest$class" = constant %"shadow.standard@Class" { %"shadow.standard@Class$methods"* @"shadow.standard@Class$methods", %"shadow.standard@String"* @.str0, %"shadow.standard@Class"* @"shadow.standard@Object$class" }
@"shadow.standard@Object$class" = external constant %"shadow.standard@Class"
@"shadow.standard@String$class" = external constant %"shadow.standard@Class"

define %"shadow.standard@Class"* @"shadow.test@ConsoleTest$$getClass"(%"shadow.test@ConsoleTest"*) {
    ret %"shadow.standard@Class"* @"shadow.test@ConsoleTest$class"
}

define void @"shadow.test@ConsoleTest$static$main"() {
    call void @"shadow.io@Console$static$printLine$int"(%int 12345)
    call void @"shadow.io@Console$static$print$code"(%code 72)
    call void @"shadow.io@Console$static$print$code"(%code 101)
    call void @"shadow.io@Console$static$print$code"(%code 108)
    call void @"shadow.io@Console$static$print$code"(%code 108)
    call void @"shadow.io@Console$static$print$code"(%code 111)
    call void @"shadow.io@Console$static$print$code"(%code 32)
    call void @"shadow.io@Console$static$print$code"(%code 87)
    call void @"shadow.io@Console$static$print$code"(%code 111)
    call void @"shadow.io@Console$static$print$code"(%code 114)
    call void @"shadow.io@Console$static$print$code"(%code 108)
    call void @"shadow.io@Console$static$print$code"(%code 100)
    call void @"shadow.io@Console$static$print$code"(%code 33)
    call void @"shadow.io@Console$static$print$code"(%code 10)
    call void @"shadow.io@Console$static$printLine"()
    ret void
}
define void @"shadow.test@ConsoleTest$$constructor"(%"shadow.test@ConsoleTest"*) {
    %this = alloca %"shadow.test@ConsoleTest"*
    store %"shadow.test@ConsoleTest"* %0, %"shadow.test@ConsoleTest"** %this
    %2 = load %"shadow.test@ConsoleTest"** %this
    %3 = getelementptr %"shadow.test@ConsoleTest"* %2, i32 0, i32 0
    store %"shadow.test@ConsoleTest$methods"* @"shadow.test@ConsoleTest$methods", %"shadow.test@ConsoleTest$methods"** %3
    ret void
}

@.array0 = private unnamed_addr constant [23 x %ubyte] c"shadow.test@ConsoleTest"
@.str0 = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String$methods"* @"shadow.standard@String$methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([23 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 23] } }
