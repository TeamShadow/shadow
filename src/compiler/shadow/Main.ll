; Shadow Library

%_Pshadow_Pstandard_CObject = type opaque
%_Pshadow_Pstandard_CClass = type {}
%_Pshadow_Pstandard_CArray_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CArray_Mclass = external constant %_Pshadow_Pstandard_CArray_Mclass
%_Pshadow_Pstandard_CString = type { %_Pshadow_Pstandard_CString_Mclass*, { i8*, [1 x i32] }, i1 }
%_Pshadow_Pstandard_CString_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CString_Mclass = external constant %_Pshadow_Pstandard_CString_Mclass
%_Pshadow_Pstandard_CException_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CException_Mclass = external constant %_Pshadow_Pstandard_CException_Mclass 
%_Pshadow_Pstandard_CException = type { %_Pshadow_Pstandard_CException_Mclass*, %_Pshadow_Pstandard_CString* }
%_Pshadow_Pio_CConsole_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pio_CConsole_Mclass = external constant %_Pshadow_Pio_CConsole_Mclass
%_Pshadow_Pio_CConsole = type opaque
@_Pshadow_Pio_CConsole_Minstance = external global %_Pshadow_Pio_CConsole*

declare %_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CObject_MgetClass(%_Pshadow_Pstandard_CObject*)
declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mcreate(%_Pshadow_Pio_CConsole*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CObject*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine(%_Pshadow_Pio_CConsole*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CObject*)
declare i32 @strlen(i8* nocapture)

%_Pshadow_Ptest_CTest = type opaque
%_Pshadow_Ptest_CTest_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Ptest_CTest_Mclass = external constant %_Pshadow_Ptest_CTest_Mclass
declare %_Pshadow_Ptest_CTest* @_Pshadow_Ptest_CTest_Mcreate(%_Pshadow_Ptest_CTest*)
declare i32 @_Pshadow_Ptest_CTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CTest*, { %_Pshadow_Pstandard_CString**, [1 x i32] })

declare i32 @__shadow_personality_v0(...)
declare %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture) nounwind

define i32 @main(i32, i8**) {
	%3 = sub i32 %0, 1
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, i32 0, i32 0), i32 %3)
	%5 = bitcast %_Pshadow_Pstandard_CObject* %4 to %_Pshadow_Pstandard_CString**
	br label %14
	%7 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, i32 0, i32 0))
	%8 = bitcast %_Pshadow_Pstandard_CObject* %7 to %_Pshadow_Pstandard_CString*
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 1, i32 0
	store i8* %18, i8** %9
	%10 = call i32 @strlen(i8* nocapture %18)
	%11 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 0
	store %_Pshadow_Pstandard_CString_Mclass* @_Pshadow_Pstandard_CString_Mclass, %_Pshadow_Pstandard_CString_Mclass** %11
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CString* %8, i32 0, i32 1, i32 1, i32 0
	store i32 %10, i32* %12
	store %_Pshadow_Pstandard_CString* %8, %_Pshadow_Pstandard_CString** %16
	%13 = getelementptr inbounds %_Pshadow_Pstandard_CString** %16, i32 1
	br label %14
	%15 = phi i8** [ %1, %2 ], [ %17, %6 ]
	%16 = phi %_Pshadow_Pstandard_CString** [ %5, %2 ], [ %13, %6 ]
	%17 = getelementptr i8** %15, i32 1
	%18 = load i8** %17
	%19 = icmp eq i8* %18, null
	br i1 %19, label %20, label %6
	%21 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Ptest_CTest_Mclass* @_Pshadow_Ptest_CTest_Mclass, i32 0, i32 0))
	%22 = bitcast %_Pshadow_Pstandard_CObject* %21 to %_Pshadow_Ptest_CTest*
	%23 = call %_Pshadow_Ptest_CTest* @_Pshadow_Ptest_CTest_Mcreate(%_Pshadow_Ptest_CTest* %22)
	%24 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } undef, %_Pshadow_Pstandard_CString** %5, 0
	%25 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } %24, i32 %3, 1, 0
	%26 = invoke i32 @_Pshadow_Ptest_CTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CTest* %23, { %_Pshadow_Pstandard_CString**, [1 x i32] } %25)
			to label %27 unwind label %28
	ret i32 %26
	%29 = landingpad { i8*, i32 } personality i32 (...)* @__shadow_personality_v0
            catch %"_Pshadow_Pstandard_CClass"* getelementptr inbounds (%_Pshadow_Pstandard_CException_Mclass* @_Pshadow_Pstandard_CException_Mclass, i32 0, i32 0)
	%30 = extractvalue { i8*, i32 } %29, 0
	%31 = call %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture %30) nounwind
	%32 = load %_Pshadow_Pio_CConsole** @_Pshadow_Pio_CConsole_Minstance
	%33 = icmp eq %_Pshadow_Pio_CConsole* %32, null
	br i1 %33, label %34, label %38
	%35 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr (%_Pshadow_Pio_CConsole_Mclass* @_Pshadow_Pio_CConsole_Mclass, i32 0, i32 0))
	%36 = bitcast %_Pshadow_Pstandard_CObject* %35 to %_Pshadow_Pio_CConsole*
	%37 = call %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mcreate(%_Pshadow_Pio_CConsole* %36)
	store %_Pshadow_Pio_CConsole* %37, %_Pshadow_Pio_CConsole** @_Pshadow_Pio_CConsole_Minstance
	br label %38
	%39 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_Minstance"
	%40 = bitcast %_Pshadow_Pstandard_CException* %31 to %_Pshadow_Pstandard_CObject*	
	%41 = call %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole* %39, %_Pshadow_Pstandard_CObject* %40 )
	ret i32 1
}