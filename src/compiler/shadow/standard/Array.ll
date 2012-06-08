; AUTO-GENERATED FILE, DO NOT EDIT!
%boolean = type i1
%code = type i32
%ubyte = type i8
%byte = type i8
%ushort = type i16
%short = type i16
%uint = type i32
%int = type i32
%ulong = type i64
%long = type i64
%float = type float
%double = type double

declare noalias i8* @malloc(%int) nounwind

@"shadow.standard@Class!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@Class!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Class"*)*, void (%"shadow.standard@Class"*)*, %"shadow.standard@Class"* (%"shadow.standard@Class"*)* }
@"shadow.standard@Class!!methods" = external constant %"shadow.standard@Class!!methods"
%"shadow.standard@Class" = type { %"shadow.standard@Class!!methods"*, %"shadow.standard@Class"*, %"shadow.standard@String"* }
declare %"shadow.standard@Class"* @"shadow.standard@Class!getSuper!shadow.standard@Class"(%"shadow.standard@Class"*)
declare %"shadow.standard@String"* @"shadow.standard@Class!toString!shadow.standard@Class"(%"shadow.standard@Class"*)
declare void @"shadow.standard@Class!constructor!shadow.standard@Class"(%"shadow.standard@Class"*)

@"int[]!!class" = external constant %"shadow.standard@Class"
%"int[]!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Array"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, { %int*, [1 x %int] } (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*, %int)*, %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* }
@"int[]!!methods" = external constant %"int[]!!methods"
%"int[]" = type { %"int[]!!methods"*, %"shadow.standard@Class"*, %long, { %int*, [1 x %int] } }

@"shadow.standard@Object!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@Object!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Object"*)* }
@"shadow.standard@Object!!methods" = external constant %"shadow.standard@Object!!methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object!!methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object!getClass!shadow.standard@Object"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object!toString!shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.standard@Object!constructor!shadow.standard@Object"(%"shadow.standard@Object"*)

@"int[][,]!!class" = external constant %"shadow.standard@Class"
%"int[][,]!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Array"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, { %int*, [1 x %int] } (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*, %int)*, %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* }
@"int[][,]!!methods" = external constant %"int[][,]!!methods"
%"int[][,]" = type { %"int[][,]!!methods"*, %"shadow.standard@Class"*, %long, { %int*, [1 x %int] } }

@"shadow.standard@String!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@String!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, void (%"shadow.standard@Object"*)*, %ubyte (%"shadow.standard@String"*, %int)*, %"shadow.standard@String"* (%"shadow.standard@String"*, %int, %int)*, %int (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %code (%"shadow.standard@String"*, %int)*, %int (%"shadow.standard@String"*)*, void (%"shadow.standard@String"*, %boolean, { %ubyte*, [1 x %int] })*, void (%"shadow.standard@String"*, %"shadow.standard@String"*)* }
@"shadow.standard@String!!methods" = external constant %"shadow.standard@String!!methods"
%"shadow.standard@String" = type { %"shadow.standard@String!!methods"*, %boolean, { %ubyte*, [1 x %int] } }
declare %ubyte @"shadow.standard@String!getByte!shadow.standard@String!int"(%"shadow.standard@String"*, %int)
declare %"shadow.standard@String"* @"shadow.standard@String!substring!shadow.standard@String!int!int"(%"shadow.standard@String"*, %int, %int)
declare %"shadow.standard@String"* @"shadow.standard@String!toString!shadow.standard@String"(%"shadow.standard@String"*)
declare %int @"shadow.standard@String!compareTo!shadow.standard@String!shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*)
declare %code @"shadow.standard@String!getCode!shadow.standard@String!int"(%"shadow.standard@String"*, %int)
declare %int @"shadow.standard@String!getLength!shadow.standard@String"(%"shadow.standard@String"*)
declare void @"shadow.standard@String!constructor!shadow.standard@String!boolean!ubyte[]"(%"shadow.standard@String"*, %boolean, { %ubyte*, [1 x %int] })
declare void @"shadow.standard@String!constructor!shadow.standard@String!shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*)

@"shadow.standard@Array!!class" = constant %"shadow.standard@Class" { %"shadow.standard@Class!!methods"* @"shadow.standard@Class!!methods", %"shadow.standard@Class"* @"shadow.standard@Object!!class", %"shadow.standard@String"* @.str }
%"shadow.standard@Array!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Array"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, { %int*, [1 x %int] } (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*, %int)*, %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* }
@"shadow.standard@Array!!methods" = constant %"shadow.standard@Array!!methods" { %"shadow.standard@Class"* (%"shadow.standard@Object"*)* @"shadow.standard@Object!getClass!shadow.standard@Object", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object!toString!shadow.standard@Object", void (%"shadow.standard@Array"*)* @"shadow.standard@Array!constructor!shadow.standard@Array", %"shadow.standard@Class"* (%"shadow.standard@Array"*)* @"shadow.standard@Array!getBaseClass!shadow.standard@Array", { %int*, [1 x %int] } (%"shadow.standard@Array"*)* @"shadow.standard@Array!getLengths!shadow.standard@Array", %int (%"shadow.standard@Array"*)* @"shadow.standard@Array!getDimensions!shadow.standard@Array", %int (%"shadow.standard@Array"*)* @"shadow.standard@Array!getLength!shadow.standard@Array", %int (%"shadow.standard@Array"*, %int)* @"shadow.standard@Array!getLength!shadow.standard@Array!int", %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* @"shadow.standard@Array!subarray!shadow.standard@Array!int!int!int" }
%"shadow.standard@Array" = type { %"shadow.standard@Array!!methods"*, %"shadow.standard@Class"*, %long, { %int*, [1 x %int] } }

@.data = private unnamed_addr constant [21 x i8] c"shadow.standard@Array"
@.str = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String!!methods"* @"shadow.standard@String!!methods", %boolean true, { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([21 x i8]* @.data, i32 0, i32 0), [1 x %int] [%int 21] } }

define %"shadow.standard@Class"* @"shadow.standard@Array!getClass!shadow.standard@Array"(%"shadow.standard@Array"*) {
	ret %"shadow.standard@Class"* @"shadow.standard@Array!!class"
}

define %"shadow.standard@Class"* @"shadow.standard@Array!getBaseClass!shadow.standard@Array"(%"shadow.standard@Array"*) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%...2 = load %"shadow.standard@Class"** %baseClass
	%.load0.baseClass = load %"shadow.standard@Class"** %baseClass
	ret %"shadow.standard@Class"* %.load0.baseClass
}

define { %int*, [1 x %int] } @"shadow.standard@Array!getLengths!shadow.standard@Array"(%"shadow.standard@Array"*) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%...2 = load { %int*, [1 x %int] }* %lengths
	%.load0.lengths = load { %int*, [1 x %int] }* %lengths
	ret { %int*, [1 x %int] } %.load0.lengths
}

define %int @"shadow.standard@Array!getDimensions!shadow.standard@Array"(%"shadow.standard@Array"*) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%test = alloca { { %int*, [2 x %int] }*, [1 x %int] }
	%...2 = load { { %int*, [2 x %int] }*, [1 x %int] }* %test
	store { { %int*, [2 x %int] }*, [1 x %int] } null, { { %int*, [2 x %int] }*, [1 x %int] }* %...2
	%...3 = load { { %int*, [2 x %int] }*, [1 x %int] }* %test
	%.load0.test = load { { %int*, [2 x %int] }*, [1 x %int] }* %test
	%.ptr.2 = extractvalue { { %int*, [2 x %int] }*, [1 x %int] } %.load0.test, 0
	%.2 = icmp ne { %int*, [2 x %int] }* %.ptr.2, null
	br %boolean %.2, label %label, label %label1
label:

	; cast<shadow.standard@Array>int[][,] test
	%.load1.test = load { { %int*, [2 x %int] }*, [1 x %int] }* %test
	%..3.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..3.1 = bitcast i8* %..3.0 to %"shadow.standard@Array"*
	%..3.2 = extractvalue { { %int*, [2 x %int] }*, [1 x %int] } %.load1.test, 0
	%..3.3 = ptrtoint { %int*, [2 x %int] }* %..3.2 to %long
	%..3.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..3.3, 2
	%..3.5 = extractvalue { { %int*, [2 x %int] }*, [1 x %int] } %.load1.test, 1
	%..3.6 = getelementptr inbounds %"shadow.standard@Array"* %..3.1, i32 1
	%..3.7 = bitcast %"shadow.standard@Array"* %..3.6 to [1 x %int]*
	store [1 x %int] %..3.5, [1 x %int]* %..3.7
	%..3.8 = getelementptr inbounds [1 x %int]* %..3.7, i32 0, i32 0
	%..3.9 = insertvalue %"shadow.standard@Array" %..3.4, %int* %..3.8, 3, 0
	store %"shadow.standard@Array" %..3.9, %"shadow.standard@Array"* %..3.1
	%.3 = bitcast %"shadow.standard@Array"* %..3.1 to %"shadow.standard@Array"*

	%.4 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array!int"(%"shadow.standard@Array"* %.3, %int 1)
	ret %int %.4
	br label %label2
label1:
	ret %int 0
	br label %label2
label2:
	ret void
}

define %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"*) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%...2 = load %"shadow.standard@Array"** %this
	%.load0.this = load %"shadow.standard@Array"** %this
	%.2 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array!int"(%"shadow.standard@Array"* %.load0.this, %int 0)
	ret %int %.2
}

define %int @"shadow.standard@Array!getLength!shadow.standard@Array!int"(%"shadow.standard@Array"*, %int) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%dimension = alloca %int
	store %int %1, %int* %dimension
	%...3 = load %"shadow.standard@Array"** %this
	%.load0.this = load %"shadow.standard@Array"** %this
	%.3 = call { %int*, [1 x %int] } @"shadow.standard@Array!getLengths!shadow.standard@Array"(%"shadow.standard@Array"* %.load0.this)
	%...4 = load %int* %dimension
	%.ptr.4 = extractvalue { %int*, [1 x %int] } %.3, 0
	%.load0.dimension = load %int* %dimension
	%.4 = getelementptr inbounds %int* %.ptr.4, %int %.load0.dimension
	%.load0..4 = load %int* %.4
	ret %int %.load0..4
}

define void @"shadow.standard@Array!constructor!shadow.standard@Array"(%"shadow.standard@Array"*) {
	%this = alloca %"shadow.standard@Array"*
	store %"shadow.standard@Array"* %0, %"shadow.standard@Array"** %this
	%...2 = load %"shadow.standard@Array"** %this
	%...3 = load %"shadow.standard@Class"** %baseClass
	%...4 = load %"shadow.standard@Object"** %shadow.standard@Object
	%...5 = load %"shadow.standard@Class"** %class
	store %"shadow.standard@Class"* @"shadow.standard@Object!!class", %"shadow.standard@Class"** %...3
	%...6 = load %"shadow.standard@Array"** %this
	%...7 = load %long* %data
	%.2 = sext %int 0 to %long
	store %long %.2, %long* %...7
	%...8 = load %"shadow.standard@Array"** %this
	%...9 = load { %int*, [1 x %int] }* %lengths
	%.size.3 = mul %int 0, ptrtoint (%int* getelementptr inbounds (%int* null, i32 1) to %int)
	%.alloc.3 = call i8* @malloc(%int %.size.3)
	%.cast.3 = bitcast i8* %.alloc.3 to %int*
	%.val.3.0 = insertvalue { %int*, [1 x %int] } undef, %int* %.cast.3, 0
	%.val.3.1 = insertvalue [1 x %int] undef, %int 0, 0
	%.3 = insertvalue { %int*, [1 x %int] } %.val.3.0, [1 x %int] %.val.3.1, 1
	store { %int*, [1 x %int] } %.3, { %int*, [1 x %int] }* %...9
	ret void
}

define %"shadow.standard@Object"* @"shadow.standard@Array!subarray!shadow.standard@Array!int!int!int"(%"shadow.standard@Array"*, %int, %int, %int) {
	ret %"shadow.standard@Object"* null
}
