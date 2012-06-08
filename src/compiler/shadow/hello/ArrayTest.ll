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

@"shadow.standard@Array!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@Array!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Array"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, { %int*, [1 x %int] } (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*, %int)*, %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* }
@"shadow.standard@Array!!methods" = external constant %"shadow.standard@Array!!methods"
%"shadow.standard@Array" = type { %"shadow.standard@Array!!methods"*, %"shadow.standard@Class"*, %long, { %int*, [1 x %int] } }
declare %"shadow.standard@Class"* @"shadow.standard@Array!getBaseClass!shadow.standard@Array"(%"shadow.standard@Array"*)
declare { %int*, [1 x %int] } @"shadow.standard@Array!getLengths!shadow.standard@Array"(%"shadow.standard@Array"*)
declare %int @"shadow.standard@Array!getDimensions!shadow.standard@Array"(%"shadow.standard@Array"*)
declare %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"*)
declare %int @"shadow.standard@Array!getLength!shadow.standard@Array!int"(%"shadow.standard@Array"*, %int)
declare %"shadow.standard@Object"* @"shadow.standard@Array!subarray!shadow.standard@Array!int!int!int"(%"shadow.standard@Array"*, %int, %int, %int)
declare void @"shadow.standard@Array!constructor!shadow.standard@Array"(%"shadow.standard@Array"*)

@"shadow.hello@ArrayTest!!class" = constant %"shadow.standard@Class" { %"shadow.standard@Class!!methods"* @"shadow.standard@Class!!methods", %"shadow.standard@Class"* @"shadow.standard@Object!!class", %"shadow.standard@String"* @.str }
%"shadow.hello@ArrayTest!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.hello@ArrayTest"*)* }
@"shadow.hello@ArrayTest!!methods" = constant %"shadow.hello@ArrayTest!!methods" { %"shadow.standard@Class"* (%"shadow.standard@Object"*)* @"shadow.standard@Object!getClass!shadow.standard@Object", %"shadow.standard@String"* (%"shadow.standard@Object"*)* @"shadow.standard@Object!toString!shadow.standard@Object", void (%"shadow.hello@ArrayTest"*)* @"shadow.hello@ArrayTest!constructor!shadow.hello@ArrayTest" }
%"shadow.hello@ArrayTest" = type { %"shadow.hello@ArrayTest!!methods"* }

@.data = private unnamed_addr constant [22 x i8] c"shadow.hello@ArrayTest"
@.str = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String!!methods"* @"shadow.standard@String!!methods", %boolean true, { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([22 x i8]* @.data, i32 0, i32 0), [1 x %int] [%int 22] } }

define %"shadow.standard@Class"* @"shadow.hello@ArrayTest!getClass!shadow.hello@ArrayTest"(%"shadow.hello@ArrayTest"*) {
	ret %"shadow.standard@Class"* @"shadow.hello@ArrayTest!!class"
}

define void @"shadow.hello@ArrayTest!constructor!shadow.hello@ArrayTest"(%"shadow.hello@ArrayTest"*) {
	%this = alloca %"shadow.hello@ArrayTest"*
	store %"shadow.hello@ArrayTest"* %0, %"shadow.hello@ArrayTest"** %this
	ret void
}

define void @"shadow.hello@ArrayTest!main!int[]"({ %int*, [1 x %int] }) {
	%array = alloca { %int*, [1 x %int] }
	store { %int*, [1 x %int] } %0, { %int*, [1 x %int] }* %array
	%i = alloca %int
	%2 = load %int* %i
	%3 = load { %int*, [1 x %int] }* %array
	%4 = extractvalue { %int*, [1 x %int] } %3, 0
	%5 = getelementptr inbounds %int* %4, %int 5
	%6 = load %int %5
	store %int %6, %int* %2
	ret void
}
