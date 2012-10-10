; shadow.standard@Error

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

%"shadow.standard!Error!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Error"*)*, %"shadow.standard!String"* (%"shadow.standard!Error"*)*, %"shadow.standard!String"* (%"shadow.standard!Error"*)* }
@"shadow.standard!Error!methods" = constant %"shadow.standard!Error!methods" { %"shadow.standard!Class"* (%"shadow.standard!Error"*)* @"shadow.standard!Error!!getClass", %"shadow.standard!String"* (%"shadow.standard!Error"*)* @"shadow.standard!Error!!toString", %"shadow.standard!String"* (%"shadow.standard!Error"*)* @"shadow.standard!Error!!getMessage" }
%"shadow.standard!Error" = type { %"shadow.standard!Error!methods"*, %"shadow.standard!String"* }

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

%"shadow.standard!String!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!String"*)*, %"shadow.standard!String"* (%"shadow.standard!String"*)*, %boolean (%"shadow.standard!String"*, %"shadow.standard!String"*)*, %ubyte (%"shadow.standard!String"*, %long)*, %long (%"shadow.standard!String"*)* }
@"shadow.standard!String!methods" = external constant %"shadow.standard!String!methods"
%"shadow.standard!String" = type { %"shadow.standard!String!methods"*, { %ubyte*, [1 x %long] } }
declare %boolean @"shadow.standard!String!!equals!shadow.standard!String"(%"shadow.standard!String"*, %"shadow.standard!String"*)
declare %"shadow.standard!String"* @"shadow.standard!String!!toString"(%"shadow.standard!String"*)
declare %ubyte @"shadow.standard!String!!getCode!long"(%"shadow.standard!String"*, %long)
declare %long @"shadow.standard!String!!getLength"(%"shadow.standard!String"*)
declare void @"shadow.standard!String!!constructor"(%"shadow.standard!String"*)

@"shadow.standard!Error!class" = constant %"shadow.standard!Class" { %"shadow.standard!Class!methods"* @"shadow.standard!Class!methods", %"shadow.standard!String"* @.str0, { %"shadow.standard!Class"**, [1 x %long] } { %"shadow.standard!Class"** null, [1 x %long] [%long 0] }, %"shadow.standard!Class"* @"shadow.standard!Object!class" }
@"shadow.standard!Class!class" = external constant %"shadow.standard!Class"
@"shadow.standard!Object!class" = external constant %"shadow.standard!Class"
@"shadow.standard!String!class" = external constant %"shadow.standard!Class"

define %"shadow.standard!Class"* @"shadow.standard!Error!!getClass"(%"shadow.standard!Error"*) {
    ret %"shadow.standard!Class"* @"shadow.standard!Error!class"
}

define %"shadow.standard!String"* @"shadow.standard!Error!!getMessage"(%"shadow.standard!Error"*) {
    %this = alloca %"shadow.standard!Error"*
    store %"shadow.standard!Error"* %0, %"shadow.standard!Error"** %this
    %2 = load %"shadow.standard!Error"** %this
    %3 = getelementptr inbounds %"shadow.standard!Error"* %2, i32 0, i32 1
    %4 = load %"shadow.standard!String"** %3
    ret %"shadow.standard!String"* %4
}
define %"shadow.standard!String"* @"shadow.standard!Error!!toString"(%"shadow.standard!Error"*) {
    %this = alloca %"shadow.standard!Error"*
    store %"shadow.standard!Error"* %0, %"shadow.standard!Error"** %this
    %2 = load %"shadow.standard!Error"** %this
    %3 = getelementptr %"shadow.standard!Error"* %2, i32 0, i32 0
    %4 = load %"shadow.standard!Error!methods"** %3
    %5 = getelementptr %"shadow.standard!Error!methods"* %4, i32 0, i32 2
    %6 = load %"shadow.standard!String"* (%"shadow.standard!Error"*)** %5
    %7 = call %"shadow.standard!String"* %6(%"shadow.standard!Error"* %2)
    ret %"shadow.standard!String"* %7
}
define void @"shadow.standard!Error!!constructor"(%"shadow.standard!Error"*) {
    %this = alloca %"shadow.standard!Error"*
    store %"shadow.standard!Error"* %0, %"shadow.standard!Error"** %this
    %2 = load %"shadow.standard!Error"** %this
    %3 = getelementptr %"shadow.standard!Error"* %2, i32 0, i32 0
    store %"shadow.standard!Error!methods"* @"shadow.standard!Error!methods", %"shadow.standard!Error!methods"** %3
    %4 = load %"shadow.standard!Error"** %this
    %5 = getelementptr inbounds %"shadow.standard!Error"* %4, i32 0, i32 1
    store %"shadow.standard!String"* @.str1, %"shadow.standard!String"** %5
    %6 = load %"shadow.standard!Error"** %this
    %7 = getelementptr inbounds %"shadow.standard!Error"* %6, i32 0, i32 1
    store %"shadow.standard!String"* @.str2, %"shadow.standard!String"** %7
    ret void
}
define void @"shadow.standard!Error!!constructor!shadow.standard!String"(%"shadow.standard!Error"*, %"shadow.standard!String"*) {
    %this = alloca %"shadow.standard!Error"*
    %message = alloca %"shadow.standard!String"*
    store %"shadow.standard!Error"* %0, %"shadow.standard!Error"** %this
    store %"shadow.standard!String"* %1, %"shadow.standard!String"** %message
    %3 = load %"shadow.standard!Error"** %this
    %4 = getelementptr %"shadow.standard!Error"* %3, i32 0, i32 0
    store %"shadow.standard!Error!methods"* @"shadow.standard!Error!methods", %"shadow.standard!Error!methods"** %4
    %5 = load %"shadow.standard!Error"** %this
    %6 = getelementptr inbounds %"shadow.standard!Error"* %5, i32 0, i32 1
    store %"shadow.standard!String"* @.str3, %"shadow.standard!String"** %6
    %7 = load %"shadow.standard!Error"** %this
    %8 = getelementptr inbounds %"shadow.standard!Error"* %7, i32 0, i32 1
    %9 = load %"shadow.standard!String"** %message
    store %"shadow.standard!String"* %9, %"shadow.standard!String"** %8
    ret void
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard!Error"
@.str0 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
@.array2 = private unnamed_addr constant [13 x %ubyte] c"Unknown Error"
@.str2 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([13 x %ubyte]* @.array2, i32 0, i32 0), [1 x %long] [%long 13] } }
@.array3 = private unnamed_addr constant [0 x %ubyte] c""
@.str3 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array3, i32 0, i32 0), [1 x %long] [%long 0] } }
