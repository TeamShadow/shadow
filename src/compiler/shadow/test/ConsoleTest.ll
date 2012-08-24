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

%"shadow.io@Console$$methods" = type {}
%"shadow.io@Console" = type { %"shadow.io@Console$$methods"* }
declare void @"shadow.io@Console$printLine"()
declare void @"shadow.io@Console$printLine$int"(%int)
declare void @"shadow.io@Console$printLine$code"(%code)
declare void @"shadow.io@Console$print$int"(%int)
declare void @"shadow.io@Console$print$code"(%code)
declare void @"shadow.io@Console$constructor%this"(%"shadow.io@Console"*)

%"shadow.test@ConsoleTest$$methods" = type {}
%"shadow.test@ConsoleTest" = type { %"shadow.test@ConsoleTest$$methods"* }

define void @"shadow.test@ConsoleTest$main"() {
    call void @"shadow.io@Console$printLine$int"(%int 12345)
    call void @"shadow.io@Console$print$code"(%code 72)
    call void @"shadow.io@Console$print$code"(%code 101)
    call void @"shadow.io@Console$print$code"(%code 108)
    call void @"shadow.io@Console$print$code"(%code 108)
    call void @"shadow.io@Console$print$code"(%code 111)
    call void @"shadow.io@Console$print$code"(%code 32)
    call void @"shadow.io@Console$print$code"(%code 87)
    call void @"shadow.io@Console$print$code"(%code 111)
    call void @"shadow.io@Console$print$code"(%code 114)
    call void @"shadow.io@Console$print$code"(%code 108)
    call void @"shadow.io@Console$print$code"(%code 100)
    call void @"shadow.io@Console$print$code"(%code 33)
    call void @"shadow.io@Console$print$code"(%code 10)
    call void @"shadow.io@Console$printLine"()
    ret void
}

