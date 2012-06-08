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

@"shadow.standard@Object!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@Object!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Object"*)* }
@"shadow.standard@Object!!methods" = external constant %"shadow.standard@Object!!methods"
%"shadow.standard@Object" = type { %"shadow.standard@Object!!methods"* }
declare %"shadow.standard@Class"* @"shadow.standard@Object!getClass!shadow.standard@Object"(%"shadow.standard@Object"*)
declare %"shadow.standard@String"* @"shadow.standard@Object!toString!shadow.standard@Object"(%"shadow.standard@Object"*)
declare void @"shadow.standard@Object!constructor!shadow.standard@Object"(%"shadow.standard@Object"*)

@"ubyte[]!!class" = external constant %"shadow.standard@Class"
%"ubyte[]!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Array"*)*, %"shadow.standard@Class"* (%"shadow.standard@Array"*)*, { %int*, [1 x %int] } (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*)*, %int (%"shadow.standard@Array"*, %int)*, %"shadow.standard@Object"* (%"shadow.standard@Array"*, %int, %int, %int)* }
@"ubyte[]!!methods" = external constant %"ubyte[]!!methods"
%"ubyte[]" = type { %"ubyte[]!!methods"*, %"shadow.standard@Class"*, %long, { %int*, [1 x %int] } }

@"shadow.standard@Math!!class" = external constant %"shadow.standard@Class"
%"shadow.standard@Math!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@Object"*)*, void (%"shadow.standard@Math"*)* }
@"shadow.standard@Math!!methods" = external constant %"shadow.standard@Math!!methods"
%"shadow.standard@Math" = type { %"shadow.standard@Math!!methods"*, %double, %double }
declare %int @"shadow.standard@Math!min!int!int"(%int, %int)
declare %double @"shadow.standard@Math!ln!double"(%double)
declare %double @"shadow.standard@Math!cosh!double"(%double)
declare %double @"shadow.standard@Math!atan!double"(%double)
declare %double @"shadow.standard@Math!atan!double!double"(%double, %double)
declare %int @"shadow.standard@Math!pow!int!uint"(%int, %uint)
declare %long @"shadow.standard@Math!pow!long!uint"(%long, %uint)
declare %double @"shadow.standard@Math!pow!double!uint"(%double, %uint)
declare %double @"shadow.standard@Math!pow!double!double"(%double, %double)
declare %int @"shadow.standard@Math!max!int!int"(%int, %int)
declare %double @"shadow.standard@Math!asin!double"(%double)
declare %double @"shadow.standard@Math!cos!double"(%double)
declare %double @"shadow.standard@Math!sqrt!double"(%double)
declare %double @"shadow.standard@Math!mod!double!double"(%double, %double)
declare { %int, %int } @"shadow.standard@Math!div!int!int"(%int, %int)
declare { %long, %long } @"shadow.standard@Math!div!long!long"(%long, %long)
declare %double @"shadow.standard@Math!sinh!double"(%double)
declare { %double, %double } @"shadow.standard@Math!parts!double"(%double)
declare %double @"shadow.standard@Math!log!double"(%double)
declare %double @"shadow.standard@Math!log!double!double"(%double, %double)
declare %double @"shadow.standard@Math!tanh!double"(%double)
declare %double @"shadow.standard@Math!exp!double"(%double)
declare %uint @"shadow.standard@Math!abs!int"(%int)
declare %ulong @"shadow.standard@Math!abs!long"(%long)
declare %double @"shadow.standard@Math!abs!double"(%double)
declare %double @"shadow.standard@Math!floor!double"(%double)
declare %double @"shadow.standard@Math!ceiling!double"(%double)
declare %double @"shadow.standard@Math!sin!double"(%double)
declare %double @"shadow.standard@Math!acos!double"(%double)
declare %double @"shadow.standard@Math!tan!double"(%double)
declare void @"shadow.standard@Math!constructor!shadow.standard@Math"(%"shadow.standard@Math"*)

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

@"shadow.standard@String!!class" = constant %"shadow.standard@Class" { %"shadow.standard@Class!!methods"* @"shadow.standard@Class!!methods", %"shadow.standard@Class"* @"shadow.standard@Object!!class", %"shadow.standard@String"* @.str }
%"shadow.standard@String!!methods" = type { %"shadow.standard@Class"* (%"shadow.standard@Object"*)*, %"shadow.standard@String"* (%"shadow.standard@String"*)*, void (%"shadow.standard@Object"*)*, %ubyte (%"shadow.standard@String"*, %int)*, %"shadow.standard@String"* (%"shadow.standard@String"*, %int, %int)*, %int (%"shadow.standard@String"*, %"shadow.standard@String"*)*, %code (%"shadow.standard@String"*, %int)*, %int (%"shadow.standard@String"*)*, void (%"shadow.standard@String"*, %boolean, { %ubyte*, [1 x %int] })*, void (%"shadow.standard@String"*, %"shadow.standard@String"*)* }
@"shadow.standard@String!!methods" = constant %"shadow.standard@String!!methods" { %"shadow.standard@Class"* (%"shadow.standard@Object"*)* @"shadow.standard@Object!getClass!shadow.standard@Object", %"shadow.standard@String"* (%"shadow.standard@String"*)* @"shadow.standard@String!toString!shadow.standard@String", void (%"shadow.standard@Object"*)* @"shadow.standard@Object!constructor!shadow.standard@Object", %ubyte (%"shadow.standard@String"*, %int)* @"shadow.standard@String!getByte!shadow.standard@String!int", %"shadow.standard@String"* (%"shadow.standard@String"*, %int, %int)* @"shadow.standard@String!substring!shadow.standard@String!int!int", %int (%"shadow.standard@String"*, %"shadow.standard@String"*)* @"shadow.standard@String!compareTo!shadow.standard@String!shadow.standard@String", %code (%"shadow.standard@String"*, %int)* @"shadow.standard@String!getCode!shadow.standard@String!int", %int (%"shadow.standard@String"*)* @"shadow.standard@String!getLength!shadow.standard@String", void (%"shadow.standard@String"*, %boolean, { %ubyte*, [1 x %int] })* @"shadow.standard@String!constructor!shadow.standard@String!boolean!ubyte[]", void (%"shadow.standard@String"*, %"shadow.standard@String"*)* @"shadow.standard@String!constructor!shadow.standard@String!shadow.standard@String" }
%"shadow.standard@String" = type { %"shadow.standard@String!!methods"*, %boolean, { %ubyte*, [1 x %int] } }

@.data = private unnamed_addr constant [22 x i8] c"shadow.standard@String"
@.str = private unnamed_addr constant %"shadow.standard@String" { %"shadow.standard@String!!methods"* @"shadow.standard@String!!methods", %boolean true, { %ubyte*, [1 x %int] } { %ubyte* getelementptr inbounds ([22 x i8]* @.data, i32 0, i32 0), [1 x %int] [%int 22] } }

define %"shadow.standard@Class"* @"shadow.standard@String!getClass!shadow.standard@String"(%"shadow.standard@String"*) {
	ret %"shadow.standard@Class"* @"shadow.standard@String!!class"
}

define %ubyte @"shadow.standard@String!getByte!shadow.standard@String!int"(%"shadow.standard@String"*, %int) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%index = alloca %int
	store %int %1, %int* %index
	%3 = load { %ubyte*, [1 x %int] }* %data
	%4 = load %int* %index
	%.ptr.3 = extractvalue { %ubyte*, [1 x %int] } %3, 0
	%.3 = getelementptr inbounds %ubyte* %.ptr.3, %int %4
	ret %ubyte .3
}

define %"shadow.standard@String"* @"shadow.standard@String!substring!shadow.standard@String!int!int"(%"shadow.standard@String"*, %int, %int) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%start = alloca %int
	store %int %1, %int* %start
	%end = alloca %int
	store %int %2, %int* %end
	%newAscii = alloca %boolean
	%4 = load %boolean* %newAscii
	%5 = load %boolean* %ascii
	store %boolean %5, %boolean* %%4
	%newData = alloca { %ubyte*, [1 x %int] }
	%6 = load { %ubyte*, [1 x %int] }* %newData
	%7 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..4.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..4.1 = bitcast i8* %..4.0 to %"shadow.standard@Array"*
	%..4.2 = extractvalue { %ubyte*, [1 x %int] } %7, 0
	%..4.3 = ptrtoint %ubyte* %..4.2 to %long
	%..4.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..4.3, 2
	%..4.5 = extractvalue { %ubyte*, [1 x %int] } %7, 1
	%..4.6 = getelementptr inbounds %"shadow.standard@Array"* %..4.1, i32 1
	%..4.7 = bitcast %"shadow.standard@Array"* %..4.6 to [1 x %int]*
	store [1 x %int] %..4.5, [1 x %int]* %..4.7
	%..4.8 = getelementptr inbounds [1 x %int]* %..4.7, i32 0, i32 0
	%..4.9 = insertvalue %"shadow.standard@Array" %..4.4, %int* %..4.8, 3, 0
	store %"shadow.standard@Array" %..4.9, %"shadow.standard@Array"* %..4.1
	%.4 = bitcast %"shadow.standard@Array"* %..4.1 to %"shadow.standard@Array"*

	%8 = load %int* %start
	%9 = load %int* %end
	%10 = load %int* %start
	%.5 = sub %int %9, %10
	%.6 = call %"shadow.standard@Object"* @"shadow.standard@Array!subarray!shadow.standard@Array!int!int!int"(%"shadow.standard@Array"* .4, %int %8, %int .5, %int 1)

	; cast<ubyte[]>subarray( int start, (int end - int start), 1 )
	%..7.0 = bitcast %"shadow.standard@Object"* .6 to %"shadow.standard@Array"*
	%..7.1 = getelementptr inbounds %"shadow.standard@Array"* %..7.0, i32 0, i32 2
	%..7.2 = load %long* %..7.1
	%..7.3 = inttoptr %long %..7.2 to %ubyte*
	%..7.4 = getelementptr inbounds %"shadow.standard@Array"* %..7.0, i32 0, i32 3, i32 0
	%..7.5 = load %int** %..7.4
	%..7.6 = bitcast %int* %..7.5 to [1 x %int]*
	%..7.7 = load [1 x %int]* %..7.6
	%..7.8 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %..7.3, 0
	%.7 = insertvalue { %ubyte*, [1 x %int] } %..7.8, [1 x %int] %..7.7, 1

	store { %ubyte*, [1 x %int] } .7, { %ubyte*, [1 x %int] }* %%6
	%11 = load %boolean* %ascii
	%.8 = xor %boolean -1, %11
	br %boolean .8, label label, label label1
label:
	br label label2
label1:
	br label label2
label2:
	%12 = load %boolean* %newAscii
	%13 = load { %ubyte*, [1 x %int] }* %newData
	%14 = call i8* @malloc(%int ptrtoint (%"shadow.standard@String"* getelementptr inbounds (%"shadow.standard@String"* null, i32 1) to %int))
	%15 = bitcast i8* %14 to %"shadow.standard@String"*
	call void @"shadow.standard@String!constructor!shadow.standard@String!boolean!ubyte[]"(%"shadow.standard@String"* %15, %boolean %12, { %ubyte*, [1 x %int] } %13)
	%17 = call i8* @malloc(%int ptrtoint (%"shadow.standard@String"* getelementptr inbounds (%"shadow.standard@String"* null, i32 1) to %int))
	%18 = bitcast i8* %17 to %"shadow.standard@String"*
	ret %"shadow.standard@String"* %16
}

define %"shadow.standard@String"* @"shadow.standard@String!toString!shadow.standard@String"(%"shadow.standard@String"*) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%2 = load %"shadow.standard@String"** %this
	ret %"shadow.standard@String"* %2
}

define %int @"shadow.standard@String!compareTo!shadow.standard@String!shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%otherString = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %1, %"shadow.standard@String"** %otherString
	%other = alloca %"shadow.standard@String"*
	%3 = load %"shadow.standard@String"** %other
	%4 = load %"shadow.standard@String"** %otherString
	%.3 = icmp ne %"shadow.standard@String"* %4, null
	br %boolean .3, label label, label label22
label:
	%6 = load %"shadow.standard@String"** %otherString
	store %"shadow.standard@String"* %5, %"shadow.standard@String"** %%3
	br label label1
label1:
	%7 = load %boolean* %ascii
	br %boolean %7, label label2, label label3
label2:
	%8 = load %"shadow.standard@String"** %other
	%9 = load %boolean* %ascii
	br label label3
label3:
	%.4 = phi %boolean [ %7, %label1 ], [ %9, %label2 ]
	br %boolean .4, label label4, label label20
label4:
	%count = alloca %int
	%10 = load %int* %count
	%11 = load %"shadow.standard@Math"** %Math
	%12 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..5.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..5.1 = bitcast i8* %..5.0 to %"shadow.standard@Array"*
	%..5.2 = extractvalue { %ubyte*, [1 x %int] } %12, 0
	%..5.3 = ptrtoint %ubyte* %..5.2 to %long
	%..5.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..5.3, 2
	%..5.5 = extractvalue { %ubyte*, [1 x %int] } %12, 1
	%..5.6 = getelementptr inbounds %"shadow.standard@Array"* %..5.1, i32 1
	%..5.7 = bitcast %"shadow.standard@Array"* %..5.6 to [1 x %int]*
	store [1 x %int] %..5.5, [1 x %int]* %..5.7
	%..5.8 = getelementptr inbounds [1 x %int]* %..5.7, i32 0, i32 0
	%..5.9 = insertvalue %"shadow.standard@Array" %..5.4, %int* %..5.8, 3, 0
	store %"shadow.standard@Array" %..5.9, %"shadow.standard@Array"* %..5.1
	%.5 = bitcast %"shadow.standard@Array"* %..5.1 to %"shadow.standard@Array"*

	%.6 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"* .5)
	%13 = load %"shadow.standard@String"** %other
	%14 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..7.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..7.1 = bitcast i8* %..7.0 to %"shadow.standard@Array"*
	%..7.2 = extractvalue { %ubyte*, [1 x %int] } %14, 0
	%..7.3 = ptrtoint %ubyte* %..7.2 to %long
	%..7.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..7.3, 2
	%..7.5 = extractvalue { %ubyte*, [1 x %int] } %14, 1
	%..7.6 = getelementptr inbounds %"shadow.standard@Array"* %..7.1, i32 1
	%..7.7 = bitcast %"shadow.standard@Array"* %..7.6 to [1 x %int]*
	store [1 x %int] %..7.5, [1 x %int]* %..7.7
	%..7.8 = getelementptr inbounds [1 x %int]* %..7.7, i32 0, i32 0
	%..7.9 = insertvalue %"shadow.standard@Array" %..7.4, %int* %..7.8, 3, 0
	store %"shadow.standard@Array" %..7.9, %"shadow.standard@Array"* %..7.1
	%.7 = bitcast %"shadow.standard@Array"* %..7.1 to %"shadow.standard@Array"*

	%.8 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"* .7)
	%.9 = call %int @"shadow.standard@Math!min!int!int"(%int .6, %int .8)
	store %int .9, %int* %%10
	%i = alloca %int
	%15 = load %int* %i
	store %int 0, %int* %%15
	br label label12
label5:
	%16 = load { %ubyte*, [1 x %int] }* %data
	%17 = load %int* %i
	%.ptr.10 = extractvalue { %ubyte*, [1 x %int] } %16, 0
	%.10 = getelementptr inbounds %ubyte* %.ptr.10, %int %17
	%18 = load %"shadow.standard@String"** %other
	%19 = load { %ubyte*, [1 x %int] }* %data
	%20 = load %int* %i
	%.ptr.11 = extractvalue { %ubyte*, [1 x %int] } %19, 0
	%.11 = getelementptr inbounds %ubyte* %.ptr.11, %int %20
	%.12 = icmp ne %ubyte .10, .11
	br %boolean .12, label label6, label label10
label6:
	%21 = load { %ubyte*, [1 x %int] }* %data
	%22 = load %int* %i
	%.ptr.13 = extractvalue { %ubyte*, [1 x %int] } %21, 0
	%.13 = getelementptr inbounds %ubyte* %.ptr.13, %int %22
	%23 = load %"shadow.standard@String"** %other
	%24 = load { %ubyte*, [1 x %int] }* %data
	%25 = load %int* %i
	%.ptr.14 = extractvalue { %ubyte*, [1 x %int] } %24, 0
	%.14 = getelementptr inbounds %ubyte* %.ptr.14, %int %25
	%.15 = icmp slt %ubyte .13, .14
	br %boolean .15, label label7, label label8
label7:
	%.16 = sub %int 0, 1
	br label label9
label8:
	br label label9
label9:
	%.17 = phi %int [ .16, %label7 ], [ 1, %label8 ]
	ret %int .17
	br label label11
label10:
	br label label11
label11:
	%26 = load %int* %i
	%.19 = add %int %26, 1
	store %int .19, %int* %%26
	br label label12
label12:
	%27 = load %int* %i
	%28 = load %int* %count
	%.20 = icmp slt %int %27, %28
	br %boolean .20, label label5, label label13
label13:
	%29 = load %int* %count
	%30 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..21.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..21.1 = bitcast i8* %..21.0 to %"shadow.standard@Array"*
	%..21.2 = extractvalue { %ubyte*, [1 x %int] } %30, 0
	%..21.3 = ptrtoint %ubyte* %..21.2 to %long
	%..21.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..21.3, 2
	%..21.5 = extractvalue { %ubyte*, [1 x %int] } %30, 1
	%..21.6 = getelementptr inbounds %"shadow.standard@Array"* %..21.1, i32 1
	%..21.7 = bitcast %"shadow.standard@Array"* %..21.6 to [1 x %int]*
	store [1 x %int] %..21.5, [1 x %int]* %..21.7
	%..21.8 = getelementptr inbounds [1 x %int]* %..21.7, i32 0, i32 0
	%..21.9 = insertvalue %"shadow.standard@Array" %..21.4, %int* %..21.8, 3, 0
	store %"shadow.standard@Array" %..21.9, %"shadow.standard@Array"* %..21.1
	%.21 = bitcast %"shadow.standard@Array"* %..21.1 to %"shadow.standard@Array"*

	%.22 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"* .21)
	%.23 = icmp eq %int %29, .22
	br %boolean .23, label label14, label label18
label14:
	%31 = load %int* %count
	%32 = load %"shadow.standard@String"** %other
	%33 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..24.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..24.1 = bitcast i8* %..24.0 to %"shadow.standard@Array"*
	%..24.2 = extractvalue { %ubyte*, [1 x %int] } %33, 0
	%..24.3 = ptrtoint %ubyte* %..24.2 to %long
	%..24.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..24.3, 2
	%..24.5 = extractvalue { %ubyte*, [1 x %int] } %33, 1
	%..24.6 = getelementptr inbounds %"shadow.standard@Array"* %..24.1, i32 1
	%..24.7 = bitcast %"shadow.standard@Array"* %..24.6 to [1 x %int]*
	store [1 x %int] %..24.5, [1 x %int]* %..24.7
	%..24.8 = getelementptr inbounds [1 x %int]* %..24.7, i32 0, i32 0
	%..24.9 = insertvalue %"shadow.standard@Array" %..24.4, %int* %..24.8, 3, 0
	store %"shadow.standard@Array" %..24.9, %"shadow.standard@Array"* %..24.1
	%.24 = bitcast %"shadow.standard@Array"* %..24.1 to %"shadow.standard@Array"*

	%.25 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"* .24)
	%.26 = icmp eq %int %31, .25
	br %boolean .26, label label15, label label16
label15:
	br label label17
label16:
	%.27 = sub %int 0, 1
	br label label17
label17:
	%.28 = phi %int [ 0, %label15 ], [ .27, %label16 ]
	br label label19
label18:
	br label label19
label19:
	%.29 = phi %int [ .28, %label14 ], [ 1, %label18 ]
	ret %int .29
	br label label21
label20:
	br label label21
label21:
	br label label23
label22:
	ret %int 1
	br label label23
label23:
	%.32 = sub %int 0, 1
	ret %int .32
}

define %code @"shadow.standard@String!getCode!shadow.standard@String!int"(%"shadow.standard@String"*, %int) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%index = alloca %int
	store %int %1, %int* %index
	%3 = load %boolean* %ascii
	br %boolean %3, label label, label label1
label:
	%4 = load { %ubyte*, [1 x %int] }* %data
	%5 = load %int* %index
	%.ptr.3 = extractvalue { %ubyte*, [1 x %int] } %4, 0
	%.3 = getelementptr inbounds %ubyte* %.ptr.3, %int %5
	%.4 = sext %ubyte .3 to %code
	ret %code .4
	br label label2
label1:
	br label label2
label2:
	ret %code 0
}

define %int @"shadow.standard@String!getLength!shadow.standard@String"(%"shadow.standard@String"*) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%2 = load { %ubyte*, [1 x %int] }* %data

	; cast<shadow.standard@Array>ubyte[] data
	%..2.0 = call i8* @malloc(%int add (%int ptrtoint (%"shadow.standard@Array"** getelementptr inbounds (%"shadow.standard@Array"** null, i32 1) to %int), %int 4))
	%..2.1 = bitcast i8* %..2.0 to %"shadow.standard@Array"*
	%..2.2 = extractvalue { %ubyte*, [1 x %int] } %2, 0
	%..2.3 = ptrtoint %ubyte* %..2.2 to %long
	%..2.4 = insertvalue %"shadow.standard@Array" { %"shadow.standard@Array!!methods"* @"shadow.standard@Array!!methods", %"shadow.standard@Class"* null, %long undef, { %int*, [1 x %int] } { %int* undef, [1 x %int] [%int 1] } }, %long %..2.3, 2
	%..2.5 = extractvalue { %ubyte*, [1 x %int] } %2, 1
	%..2.6 = getelementptr inbounds %"shadow.standard@Array"* %..2.1, i32 1
	%..2.7 = bitcast %"shadow.standard@Array"* %..2.6 to [1 x %int]*
	store [1 x %int] %..2.5, [1 x %int]* %..2.7
	%..2.8 = getelementptr inbounds [1 x %int]* %..2.7, i32 0, i32 0
	%..2.9 = insertvalue %"shadow.standard@Array" %..2.4, %int* %..2.8, 3, 0
	store %"shadow.standard@Array" %..2.9, %"shadow.standard@Array"* %..2.1
	%.2 = bitcast %"shadow.standard@Array"* %..2.1 to %"shadow.standard@Array"*

	%.3 = call %int @"shadow.standard@Array!getLength!shadow.standard@Array"(%"shadow.standard@Array"* .2)
	ret %int .3
}

define void @"shadow.standard@String!constructor!shadow.standard@String!boolean!ubyte[]"(%"shadow.standard@String"*, %boolean, { %ubyte*, [1 x %int] }) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%isAscii = alloca %boolean
	store %boolean %1, %boolean* %isAscii
	%dataArray = alloca { %ubyte*, [1 x %int] }
	store { %ubyte*, [1 x %int] } %2, { %ubyte*, [1 x %int] }* %dataArray
	%4 = load %"shadow.standard@String"** %this
	%5 = load %boolean* %ascii
	store %boolean true, %boolean* %%5
	%6 = load %"shadow.standard@String"** %this
	%7 = load { %ubyte*, [1 x %int] }* %data
	%.size.4 = mul %int 0, ptrtoint (%ubyte* getelementptr inbounds (%ubyte* null, i32 1) to %int)
	%.alloc.4 = call i8* @malloc(%int %.size.4)
	%.cast.4 = bitcast i8* %.alloc.4 to %ubyte*
	%.val.4.0 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %.cast.4, 0
	%.val.4.1 = insertvalue [1 x %int] undef, %int 0, 0
	%.4 = insertvalue { %ubyte*, [1 x %int] } %.val.4.0, [1 x %int] %.val.4.1, 1
	%.size.4 = mul %int 0, ptrtoint (%ubyte* getelementptr inbounds (%ubyte* null, i32 1) to %int)
	%.alloc.4 = call i8* @malloc(%int %.size.4)
	%.cast.4 = bitcast i8* %.alloc.4 to %ubyte*
	%.val.4.0 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %.cast.4, 0
	%.val.4.1 = insertvalue [1 x %int] undef, %int 0, 0
	%.4 = insertvalue { %ubyte*, [1 x %int] } %.val.4.0, [1 x %int] %.val.4.1, 1
	store { %ubyte*, [1 x %int] } %8, { %ubyte*, [1 x %int] }* %%7
	%9 = load { %ubyte*, [1 x %int] }* %data
	%10 = load { %ubyte*, [1 x %int] }* %dataArray
	store { %ubyte*, [1 x %int] } %10, { %ubyte*, [1 x %int] }* %%9
	ret void
}

define void @"shadow.standard@String!constructor!shadow.standard@String!shadow.standard@String"(%"shadow.standard@String"*, %"shadow.standard@String"*) {
	%this = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %0, %"shadow.standard@String"** %this
	%other = alloca %"shadow.standard@String"*
	store %"shadow.standard@String"* %1, %"shadow.standard@String"** %other
	%3 = load %"shadow.standard@String"** %this
	%4 = load %boolean* %ascii
	store %boolean true, %boolean* %%4
	%5 = load %"shadow.standard@String"** %this
	%6 = load { %ubyte*, [1 x %int] }* %data
	%.size.4 = mul %int 0, ptrtoint (%ubyte* getelementptr inbounds (%ubyte* null, i32 1) to %int)
	%.alloc.4 = call i8* @malloc(%int %.size.4)
	%.cast.4 = bitcast i8* %.alloc.4 to %ubyte*
	%.val.4.0 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %.cast.4, 0
	%.val.4.1 = insertvalue [1 x %int] undef, %int 0, 0
	%.4 = insertvalue { %ubyte*, [1 x %int] } %.val.4.0, [1 x %int] %.val.4.1, 1
	%.size.4 = mul %int 0, ptrtoint (%ubyte* getelementptr inbounds (%ubyte* null, i32 1) to %int)
	%.alloc.4 = call i8* @malloc(%int %.size.4)
	%.cast.4 = bitcast i8* %.alloc.4 to %ubyte*
	%.val.4.0 = insertvalue { %ubyte*, [1 x %int] } undef, %ubyte* %.cast.4, 0
	%.val.4.1 = insertvalue [1 x %int] undef, %int 0, 0
	%.4 = insertvalue { %ubyte*, [1 x %int] } %.val.4.0, [1 x %int] %.val.4.1, 1
	store { %ubyte*, [1 x %int] } %7, { %ubyte*, [1 x %int] }* %%6
	%8 = load { %ubyte*, [1 x %int] }* %data
	%9 = load %"shadow.standard@String"** %other
	%10 = load { %ubyte*, [1 x %int] }* %data
	store { %ubyte*, [1 x %int] } %10, { %ubyte*, [1 x %int] }* %%8
	ret void
}
