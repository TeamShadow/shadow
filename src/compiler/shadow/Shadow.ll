; Shadow Library

%_Pshadow_Pstandard_CObject = type opaque
%_Pshadow_Pstandard_CClass = type {}
%_Pshadow_Pstandard_CArray_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CArray_Mclass = external constant %_Pshadow_Pstandard_CArray_Mclass
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] } }
%_Pshadow_Pstandard_CString_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CString_Mclass = external constant %_Pshadow_Pstandard_CString_Mclass

declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare i32 @strlen(i8* nocapture)

%_Pshadow_Ptest_CStringTest = type opaque
%_Pshadow_Ptest_CStringTest_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Ptest_CStringTest_Mclass = external constant %_Pshadow_Ptest_CStringTest_Mclass
declare %_Pshadow_Ptest_CStringTest* @_Pshadow_Ptest_CStringTest_Mcreate(%_Pshadow_Ptest_CStringTest*)
declare i32 @_Pshadow_Ptest_CStringTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CStringTest*, { %_Pshadow_Pstandard_CString**, [1 x i32] })

define i32 @main(i32, i8**) {
	%3 = sub i32 %0, 1
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, i32 0, i32 0), i32 %3)
	%5 = bitcast %_Pshadow_Pstandard_CObject* %4 to %_Pshadow_Pstandard_CString**
	br label %13
	%7 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, i32 0, i32 0))
	%8 = bitcast %_Pshadow_Pstandard_CObject* %7 to %_Pshadow_Pstandard_CString*
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 1, i32 0
	store i8* %17, i8** %9
	%10 = call i32 @strlen(i8* nocapture %17)
	%11 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 1, i32 1, i32 0
	store i32 %10, i32* %11
	store %_Pshadow_Pstandard_CString* %8, %_Pshadow_Pstandard_CString** %15
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CString** %15, i32 1
	br label %13
	%14 = phi i8** [ %1, %2 ], [ %16, %6 ]
	%15 = phi %_Pshadow_Pstandard_CString** [ %5, %2 ], [ %12, %6 ]
	%16 = getelementptr i8** %14, i32 1
	%17 = load i8** %16
	%18 = icmp eq i8* %17, null
	br i1 %18, label %19, label %6
	%20 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Ptest_CStringTest_Mclass* @_Pshadow_Ptest_CStringTest_Mclass, i32 0, i32 0))
	%21 = bitcast %_Pshadow_Pstandard_CObject* %20 to %_Pshadow_Ptest_CStringTest*
	%22 = call %_Pshadow_Ptest_CStringTest* @_Pshadow_Ptest_CStringTest_Mcreate(%_Pshadow_Ptest_CStringTest* %21)
	%23 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } undef, %_Pshadow_Pstandard_CString** %5, 0
	%24 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } %23, i32 %3, 1, 0
	%25 = tail call i32 @_Pshadow_Ptest_CStringTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CStringTest* %22, { %_Pshadow_Pstandard_CString**, [1 x i32] } %24)
	ret i32 %25
}
