; shadow.standard@Class

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

%"shadow.standard!Class!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Class"*)*, %"shadow.standard!String"* (%"shadow.standard!Class"*)*, %"shadow.standard!String"* (%"shadow.standard!Class"*)*, %"shadow.standard!Class"* (%"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)*, %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)* }
@"shadow.standard!Class!methods" = constant %"shadow.standard!Class!methods" { %"shadow.standard!Class"* (%"shadow.standard!Class"*)* @"shadow.standard!Class!!getClass", %"shadow.standard!String"* (%"shadow.standard!Class"*)* @"shadow.standard!Class!!toString", %"shadow.standard!String"* (%"shadow.standard!Class"*)* @"shadow.standard!Class!!getClassName", %"shadow.standard!Class"* (%"shadow.standard!Class"*)* @"shadow.standard!Class!!getSuperClass", %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)* @"shadow.standard!Class!!isClassSubtype!shadow.standard!Class", %boolean (%"shadow.standard!Class"*)* @"shadow.standard!Class!!isInterface", %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)* @"shadow.standard!Class!!isInterfaceSubtype!shadow.standard!Class", %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)* @"shadow.standard!Class!!isSubtype!shadow.standard!Class" }
%"shadow.standard!Class" = type { %"shadow.standard!Class!methods"*, %"shadow.standard!String"*, { %"shadow.standard!Class"**, [1 x %long] }, %"shadow.standard!Class"* }

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

%"shadow.standard!Array!methods" = type { %"shadow.standard!Class"* (%"shadow.standard!Array"*)*, %"shadow.standard!String"* (%"shadow.standard!Object"*)*, %long (%"shadow.standard!Array"*)*, %long (%"shadow.standard!Array"*)*, %long (%"shadow.standard!Array"*, %long)*, { %long*, [1 x %long] } (%"shadow.standard!Array"*)* }
@"shadow.standard!Array!methods" = external constant %"shadow.standard!Array!methods"
%"shadow.standard!Array" = type { %"shadow.standard!Array!methods"*, %long, { %long*, [1 x %long] } }
declare { %long*, [1 x %long] } @"shadow.standard!Array!!getLengths"(%"shadow.standard!Array"*)
declare %long @"shadow.standard!Array!!getDimensions"(%"shadow.standard!Array"*)
declare %long @"shadow.standard!Array!!getLength"(%"shadow.standard!Array"*)
declare %long @"shadow.standard!Array!!getLength!long"(%"shadow.standard!Array"*, %long)
declare void @"shadow.standard!Array!!constructor!long!long[]"(%"shadow.standard!Array"*, %long, { %long*, [1 x %long] })

@"shadow.standard!Class!class" = constant %"shadow.standard!Class" { %"shadow.standard!Class!methods"* @"shadow.standard!Class!methods", %"shadow.standard!String"* @.str0, { %"shadow.standard!Class"**, [1 x %long] } { %"shadow.standard!Class"** null, [1 x %long] [%long 0] }, %"shadow.standard!Class"* @"shadow.standard!Object!class" }
@"shadow.standard!Object!class" = external constant %"shadow.standard!Class"
@"shadow.standard!String!class" = external constant %"shadow.standard!Class"
@"shadow.standard!Array!class" = external constant %"shadow.standard!Class"

define %"shadow.standard!Class"* @"shadow.standard!Class!!getSuperClass"(%"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    %2 = load %"shadow.standard!Class"** %this
    %3 = getelementptr inbounds %"shadow.standard!Class"* %2, i32 0, i32 3
    %4 = load %"shadow.standard!Class"** %3
    ret %"shadow.standard!Class"* %4
.label0:
}
define %boolean @"shadow.standard!Class!!isClassSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    %other = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    store %"shadow.standard!Class"* %1, %"shadow.standard!Class"** %other
    %3 = load %"shadow.standard!Class"** %this
    %4 = load %"shadow.standard!Class"** %other
    %5 = icmp eq %"shadow.standard!Class"* %3, %4
    br %boolean %5, label %.label2, label %.label3
.label2:
    ret %boolean true
    br label %.label3
.label3:
    %7 = load %"shadow.standard!Class"** %this
    %8 = getelementptr inbounds %"shadow.standard!Class"* %7, i32 0, i32 3
    %9 = load %"shadow.standard!Class"** %8
    %10 = icmp eq %"shadow.standard!Class"* %9, null
    br %boolean %10, label %.label4, label %.label6
.label6:
    %11 = load %"shadow.standard!Class"** %other
    %12 = load %"shadow.standard!Class"** %8
    %13 = getelementptr %"shadow.standard!Class"* %12, i32 0, i32 0
    %14 = load %"shadow.standard!Class!methods"** %13
    %15 = getelementptr %"shadow.standard!Class!methods"* %14, i32 0, i32 4
    %16 = load %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)** %15
    %17 = call %boolean %16(%"shadow.standard!Class"* %12, %"shadow.standard!Class"* %11)
    ret %boolean %17
.label4:
    br label %.label5
.label5:
    ret %boolean false
.label1:
}
define %boolean @"shadow.standard!Class!!isSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    %other = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    store %"shadow.standard!Class"* %1, %"shadow.standard!Class"** %other
    %3 = load %"shadow.standard!Class"** %other
    %4 = getelementptr %"shadow.standard!Class"* %3, i32 0, i32 0
    %5 = load %"shadow.standard!Class!methods"** %4
    %6 = getelementptr %"shadow.standard!Class!methods"* %5, i32 0, i32 5
    %7 = load %boolean (%"shadow.standard!Class"*)** %6
    %8 = call %boolean %7(%"shadow.standard!Class"* %3)
    br %boolean %8, label %.label8, label %.label9
.label8:
    %9 = load %"shadow.standard!Class"** %other
    %10 = load %"shadow.standard!Class"** %this
    %11 = getelementptr %"shadow.standard!Class"* %10, i32 0, i32 0
    %12 = load %"shadow.standard!Class!methods"** %11
    %13 = getelementptr %"shadow.standard!Class!methods"* %12, i32 0, i32 6
    %14 = load %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)** %13
    %15 = call %boolean %14(%"shadow.standard!Class"* %10, %"shadow.standard!Class"* %9)
    ret %boolean %15
    br label %.label9
.label9:
    %17 = load %"shadow.standard!Class"** %other
    %18 = load %"shadow.standard!Class"** %this
    %19 = getelementptr %"shadow.standard!Class"* %18, i32 0, i32 0
    %20 = load %"shadow.standard!Class!methods"** %19
    %21 = getelementptr %"shadow.standard!Class!methods"* %20, i32 0, i32 4
    %22 = load %boolean (%"shadow.standard!Class"*, %"shadow.standard!Class"*)** %21
    %23 = call %boolean %22(%"shadow.standard!Class"* %18, %"shadow.standard!Class"* %17)
    ret %boolean %23
.label7:
}
define %boolean @"shadow.standard!Class!!isInterface"(%"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    ret %boolean false
.label10:
}
define %boolean @"shadow.standard!Class!!isInterfaceSubtype!shadow.standard!Class"(%"shadow.standard!Class"*, %"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    %other = alloca %"shadow.standard!Class"*
    %i = alloca %long
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    store %"shadow.standard!Class"* %1, %"shadow.standard!Class"** %other
    %3 = sext %int 0 to %long
    store %long %3, %long* %i
    br label %.label13
.label12:
    %4 = load %"shadow.standard!Class"** %this
    %5 = getelementptr inbounds %"shadow.standard!Class"* %4, i32 0, i32 2
    %6 = load { %"shadow.standard!Class"**, [1 x %long] }* %5
    %7 = load %long* %i
    %8 = extractvalue { %"shadow.standard!Class"**, [1 x %long] } %6, 0
    %9 = getelementptr %"shadow.standard!Class"** %8, %long %7
    %10 = load %"shadow.standard!Class"** %9
    %11 = load %"shadow.standard!Class"** %other
    %12 = icmp eq %"shadow.standard!Class"* %10, %11
    br %boolean %12, label %.label16, label %.label17
.label16:
    ret %boolean true
    br label %.label17
.label17:
    br label %.label15
.label15:
    %14 = load %long* %i
    %15 = sext %int 1 to %long
    %16 = add %long %14, %15
    store %long %16, %long* %i
    br label %.label13
.label13:
    %17 = load %"shadow.standard!Class"** %this
    %18 = getelementptr inbounds %"shadow.standard!Class"* %17, i32 0, i32 2
    %19 = sext %int 0 to %long
    %20 = load { %"shadow.standard!Class"**, [1 x %long] }* %18
    %21 = call i8* @malloc(i64 ptrtoint (%"shadow.standard!Array"* getelementptr(%"shadow.standard!Array"* null, i32 1) to i64))
    %22 = bitcast i8* %21 to %"shadow.standard!Array"*
    %23 = extractvalue { %"shadow.standard!Class"**, [1 x %long] } %20, 0
    %24 = ptrtoint %"shadow.standard!Class"** %23 to %long
    %25 = extractvalue { %"shadow.standard!Class"**, [1 x %long] } %20, 1
    %26 = call i8* @malloc(i64 ptrtoint ([1 x %long]* getelementptr([1 x %long]* null, i32 1) to i64))
    %27 = bitcast i8* %26 to [1 x %long]*
    store [1 x %long] %25, [1 x %long]* %27
    %28 = getelementptr [1 x %long]* %27, i32 0, i32 0
    %29 = insertvalue { %long*, [1 x %long] } { %long* null, [1 x %long] [%long 1] }, %long* %28, 0
    call void @"shadow.standard!Array!!constructor!long!long[]"(%"shadow.standard!Array"* %22, %long %24, { %long*, [1 x %long] } %29)
    %30 = getelementptr %"shadow.standard!Array"* %22, i32 0, i32 0
    %31 = load %"shadow.standard!Array!methods"** %30
    %32 = getelementptr %"shadow.standard!Array!methods"* %31, i32 0, i32 4
    %33 = load %long (%"shadow.standard!Array"*, %long)** %32
    %34 = call %long %33(%"shadow.standard!Array"* %22, %long %19)
    %35 = load %long* %i
    %36 = icmp slt %long %35, %34
    br %boolean %36, label %.label12, label %.label14
.label14:
    ret %boolean false
.label11:
}
define %"shadow.standard!String"* @"shadow.standard!Class!!getClassName"(%"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    %2 = load %"shadow.standard!Class"** %this
    %3 = getelementptr inbounds %"shadow.standard!Class"* %2, i32 0, i32 1
    %4 = load %"shadow.standard!String"** %3
    ret %"shadow.standard!String"* %4
.label18:
}
define %"shadow.standard!String"* @"shadow.standard!Class!!toString"(%"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    %2 = load %"shadow.standard!Class"** %this
    %3 = getelementptr %"shadow.standard!Class"* %2, i32 0, i32 0
    %4 = load %"shadow.standard!Class!methods"** %3
    %5 = getelementptr %"shadow.standard!Class!methods"* %4, i32 0, i32 2
    %6 = load %"shadow.standard!String"* (%"shadow.standard!Class"*)** %5
    %7 = call %"shadow.standard!String"* %6(%"shadow.standard!Class"* %2)
    ret %"shadow.standard!String"* %7
.label19:
}
define void @"shadow.standard!Class!!constructor"(%"shadow.standard!Class"*) {
    %this = alloca %"shadow.standard!Class"*
    store %"shadow.standard!Class"* %0, %"shadow.standard!Class"** %this
    %2 = load %"shadow.standard!Class"** %this
    %3 = getelementptr %"shadow.standard!Class"* %2, i32 0, i32 0
    store %"shadow.standard!Class!methods"* @"shadow.standard!Class!methods", %"shadow.standard!Class!methods"** %3
    %4 = load %"shadow.standard!Class"** %this
    %5 = getelementptr inbounds %"shadow.standard!Class"* %4, i32 0, i32 1
    store %"shadow.standard!String"* @.str1, %"shadow.standard!String"** %5
    %6 = load %"shadow.standard!Class"** %this
    %7 = getelementptr inbounds %"shadow.standard!Class"* %6, i32 0, i32 3
    store %"shadow.standard!Class"* null, %"shadow.standard!Class"** %7
    %8 = load %"shadow.standard!Class"** %this
    %9 = getelementptr inbounds %"shadow.standard!Class"* %8, i32 0, i32 2
    %10 = sext %int 0 to %long
    %11 = call noalias i8* @calloc(i64 %10, i64 ptrtoint (%"shadow.standard!Class"** getelementptr(%"shadow.standard!Class"** null, i32 1) to i64))
    %12 = bitcast i8* %11 to %"shadow.standard!Class"**
    %13 = insertvalue { %"shadow.standard!Class"**, [1 x %long] } undef, %"shadow.standard!Class"** %12, 0
    %14 = insertvalue { %"shadow.standard!Class"**, [1 x %long] } %13, %long %10, 1, 0
    store { %"shadow.standard!Class"**, [1 x %long] } %14, { %"shadow.standard!Class"**, [1 x %long] }* %9
.label20:
}

@.array0 = private unnamed_addr constant [21 x %ubyte] c"shadow.standard!Class"
@.str0 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([21 x %ubyte]* @.array0, i32 0, i32 0), [1 x %long] [%long 21] } }
@.array1 = private unnamed_addr constant [0 x %ubyte] c""
@.str1 = private unnamed_addr constant %"shadow.standard!String" { %"shadow.standard!String!methods"* @"shadow.standard!String!methods", { %ubyte*, [1 x %long] } { %ubyte* getelementptr inbounds ([0 x %ubyte]* @.array1, i32 0, i32 0), [1 x %long] [%long 0] } }
