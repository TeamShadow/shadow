; shadow.test@ExceptionTest

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

%"Exception!methods" = type { }
@"Exception!methods" = external constant %"Exception!methods"
%"Exception" = type { %"Exception!methods"* }

%"shadow.standard!Error!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Error"*)*, %"shadow.standard!String"* (%"shadow.standard!Error"*)*, %"shadow.standard!String"* (%"shadow.standard!Error"*)* }
@"shadow.standard!Error!methods" = external constant %"shadow.standard!Error!methods"
%"shadow.standard!Error" = type { %"shadow.standard!Error!methods"*, %"shadow.standard!String"* }
declare %"shadow.standard!String"* @"shadow.standard!Error!!getMessage"(%"shadow.standard!Error"*)
declare %"shadow.standard!String"* @"shadow.standard!Error!!toString"(%"shadow.standard!Error"*)
declare void @"shadow.standard!Error!!constructor"(%"shadow.standard!Error"*)
declare void @"shadow.standard!Error!!constructor!shadow.standard!String"(%"shadow.standard!Error"*, %"shadow.standard!String"*)

%"shadow.io!Console!methods" = type { %"shadow.standard!Class"* (%"shadow.io!Console"*)*, %"shadow.standard!String"* (%"shadow.standard!Object"*)* }
@"shadow.io!Console!methods" = external constant %"shadow.io!Console!methods"
%"shadow.io!Console" = type { %"shadow.io!Console!methods"* }
declare void @"shadow.io!Console!static!printLine!boolean"(%boolean)
declare void @"shadow.io!Console!static!printLine!int"(%int)
declare void @"shadow.io!Console!static!printLine!code"(%code)
declare void @"shadow.io!Console!static!printLine!long"(%long)
declare void @"shadow.io!Console!static!printLine!shadow.standard!String"(%"shadow.standard!String"*)
declare void @"shadow.io!Console!static!printLine!shadow.standard!Object"(%"shadow.standard!Object"*)
declare void @"shadow.io!Console!static!printLine"()
declare void @"shadow.io!Console!static!print!boolean"(%boolean)
declare void @"shadow.io!Console!static!print!shadow.standard!Object"(%"shadow.standard!Object"*)
declare void @"shadow.io!Console!static!print!int"(%int)
declare void @"shadow.io!Console!static!print!code"(%code)
declare void @"shadow.io!Console!static!print!long"(%long)
declare void @"shadow.io!Console!static!print!shadow.standard!String"(%"shadow.standard!String"*)
declare void @"shadow.io!Console!!constructor"(%"shadow.io!Console"*)

%"shadow.standard!Class!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Class"*)*, %"shadow.standard!String"* (%"shadow.standard!Class"*)*, %"shadow.standard!String"* (%"shadow.standard!Class"*)*, %"shadow.standard!Class"* (%"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)* }
@"shadow.standard!Class!methods" = external constant %"shadow.standard!Class!methods"
%"shadow.standard!Class" = type { %"shadow.standard!Class!methods"*, %"shadow.standard!String"*, { %"shadow.standard!Class"**, [1 x %long] }, %"shadow.standard!Class"* }
declare %"shadow.standard!Class"* @"shadow.standard!Class!!getSuperClass"(%"shadow.standard!Class"*)
declare %boolean @"shadow.standard!Class!!isClassSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*)
declare %boolean @"shadow.standard!Class!!isSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*)
declare %boolean @"shadow.standard!Class!!isInterface"(%"shadow.standard!Class"*)
declare %boolean @"shadow.standard!Class!!isInterfaceSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*)
declare %"shadow.standard!String"* @"shadow.standard!Class!!getClassName"(%"shadow.standard!Class"*)
declare %"shadow.standard!String"* @"shadow.standard!Class!!toString"(%"shadow.standard!Class"*)
declare void @"shadow.standard!Class!!constructor"(%"shadow.standard!Class"*)

%"shadow.standard!Object!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Object"*)*, %"shadow.standard!String"* (%"shadow.standard!Object"*)* }
@"shadow.standard!Object!methods" = external constant %"shadow.standard!Object!methods"
%"shadow.standard!Object" = type { %"shadow.standard!Object!methods"* }
declare %"shadow.standard!Class"* @"shadow.standard!Object!!getClass"(%"shadow.standard!Object"*)
declare %"shadow.standard!String"* @"shadow.standard!Object!!toString"(%"shadow.standard!Object"*)
declare void @"shadow.standard!Object!!constructor"(%"shadow.standard!Object"*)

%"shadow.test!ExceptionTest!methods" = type { %"shadow.standard!Class"* (%"shadow.test!ExceptionTest"*)*, %"shadow.standard!String"* (%"shadow.standard!Object"*)* }
@"shadow.test!ExceptionTest!methods" = constant %"shadow.test!ExceptionTest!methods" { %"shadow.standard!Class"* (%"shadow.test!ExceptionTest"*)* @"shadow.test!ExceptionTest!!getClass", %"shadow.standard!String"* (%"shadow.standard!Object"*)* @"shadow.standard!Object!!toString" }
%"shadow.test!ExceptionTest" = type { %"shadow.test!ExceptionTest!methods"*, %"shadow.standard!Error"* }

%"shadow.test!ExceptionA!methods" = type { }
@"shadow.test!ExceptionA!methods" = external constant %"shadow.test!ExceptionA!methods"
%"shadow.test!ExceptionA" = type { %"shadow.test!ExceptionA!methods"* }
declare void @"shadow.test!ExceptionA!!constructor"(%"shadow.test!ExceptionA"*)

%"shadow.standard!String!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!String"*)*, %"shadow.standard!String"* (%"shadow.standard!String"*)*, %boolean (%"shadow.standard!String"*, %"shadow.standard!String"*)*, %ubyte (%"shadow.standard!String"*, %long)*, %long (%"shadow.standard!String"*)* }
@"shadow.standard!String!methods" = external constant %"shadow.standard!String!methods"
%"shadow.standard!String" = type { %"shadow.standard!String!methods"*, { %ubyte*, [1 x %long] } }
declare %boolean @"shadow.standard!String!!equals!shadow.standard!String"(%"shadow.standard!String"*, %"shadow.standard!String"*)
declare %"shadow.standard!String"* @"shadow.standard!String!!toString"(%"shadow.standard!String"*)
declare %ubyte @"shadow.standard!String!!getCode!long"(%"shadow.standard!String"*, %long)
declare %long @"shadow.standard!String!!getLength"(%"shadow.standard!String"*)
declare void @"shadow.standard!String!!constructor"(%"shadow.standard!String"*)

@"Exception!class" = external constant %"shadow.standard!Class"
@"shadow.standard!Error!class" = external constant %"shadow.standard!Class"
@"shadow.io!Console!class" = external constant %"shadow.standard!Class"
@"shadow.standard!Class!class" = external constant %"shadow.standard!Class"
@"shadow.standard!Object!class" = external constant %"shadow.standard!Class"
@"shadow.test!ExceptionTest!class" = constant %"shadow.standard!Class" { %"shadow.standard!Class!methods"* @"shadow.standard!Class!methods", %"shadow.standard!String"* @.str0, { %"shadow.standard!Class"**, [1 x %long] } { %"shadow.standard!Class"** null, [1 x %long] [%long 0] }, %"shadow.standard!Class"* @"shadow.standard!Object!class" }
@"shadow.test!ExceptionA!class" = external constant %"shadow.standard!Class"
@"shadow.standard!String!class" = external constant %"shadow.standard!Class"

define void @"shadow.test!ExceptionTest!static!throwException"() {
    %1 = call noalias i8* @malloc(i64 ptrtoint (%"shadow.test!ExceptionA"* getelementptr(%"shadow.test!ExceptionA"* null, i32 1) to i64))
    %2 = bitcast i8* %1 to %"shadow.test!ExceptionA"*
    call void @"shadow.test!ExceptionA!!constructor"(%"shadow.test!ExceptionA"* %2)
    %3 = bitcast %"shadow.test!ExceptionA"* %2 to %"Exception"*
    ret void
.label0:
}
define void @"shadow.test!ExceptionTest!static!main"() {
    call void @"shadow.test!ExceptionTest!static!throwException"()
    call void @"shadow.io!Console!static!printLine!shadow.standard!String"(%"shadow.standard!String"* @.str1)
    br label %.label2
.label2:
    call void @"shadow.io!Console!static!printLine!shadow.standard!String"(%"shadow.standard!String"* @.str2)
    ret void
.label1:
}
define void @"shadow.test!ExceptionTest!!constructor"(%"shadow.test!ExceptionTest"*) {
    %this = alloca %"shadow.test!ExceptionTest"*
    store %"shadow.test!ExceptionTest"* %0, %"shadow.test!ExceptionTest"** %this
    %2 = load %"shadow.test!ExceptionTest"** %this
    %3 = getelementptr %"shadow.test!ExceptionTest"* %2, i32 0, i32 0
    store %"shadow.test!ExceptionTest!methods"* @"shadow.test!ExceptionTest!methods", %"shadow.test!ExceptionTest!methods"** %3
    ret void
}

@.array0 = private unnamed_addr constant [25 x %ubyte] c"shadow.test!ExceptionTest"
@.str0 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([25 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 25] } }
@.array1 = private unnamed_addr constant [10 x %ubyte] c"ExceptionA"
@.str1 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([10 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 10] } }
@.array2 = private unnamed_addr constant [7 x %ubyte] c"finally"
@.str2 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([7 x %ubyte]* @.array2, i32 0, i32 0), [1 x %long] [%long 7] } }
