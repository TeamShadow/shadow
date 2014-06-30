; Shadow Library

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

; standard definitions
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }


%"_Pshadow_Pstandard_COutOfMemoryException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_COutOfMemoryException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_COutOfMemoryException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }

%"_Pshadow_Pio_CConsole_methods" = type opaque
@"_Pshadow_Pio_CConsole_methods" = external constant %"_Pshadow_Pio_CConsole_methods"
@"_Pshadow_Pio_CConsole_class" = external constant %"_Pshadow_Pstandard_CClass"
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CConsole_methods"* , %boolean }
@"_Pshadow_Pio_CConsole_instance" = external global %"_Pshadow_Pio_CConsole"*

@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CException_methods" = external constant %"_Pshadow_Pstandard_CException_methods"
@"_Pshadow_Pstandard_CException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_COutOfMemoryException_methods" = external constant %"_Pshadow_Pstandard_COutOfMemoryException_methods"
@"_Pshadow_Pstandard_COutOfMemoryException_class" = external constant %"_Pshadow_Pstandard_CClass"

; declare %_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CObject_MgetClass(%_Pshadow_Pstandard_CObject*)
declare %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CClass"*, %int)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mcreate(%_Pshadow_Pstandard_CObject*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CObject*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintError_Pshadow_Pstandard_CString(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CString*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine(%_Pshadow_Pio_CConsole*)
declare %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole*, %_Pshadow_Pstandard_CObject*)
declare %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CObject"*, { %byte*, [1 x %int] })

declare i32 @strlen(i8* nocapture)

%_Pshadow_Ptest_CTest = type opaque
%_Pshadow_Ptest_CTest_methods = type opaque
@_Pshadow_Ptest_CTest_methods = external constant %_Pshadow_Ptest_CTest_methods
@_Pshadow_Ptest_CTest_class = external constant %_Pshadow_Pstandard_CClass
declare %_Pshadow_Ptest_CTest* @_Pshadow_Ptest_CTest_Mcreate(%_Pshadow_Pstandard_CObject*)
declare void @_Pshadow_Ptest_CTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CTest*, { %_Pshadow_Pstandard_CString**, [1 x i32] })

declare i32 @__shadow_personality_v0(...)
declare %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture) nounwind

define void @consoleInitialize() {
	%1 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pio_CConsole_class" )
    %2 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %1, i32 0, i32 0
    store %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pio_CConsole_class", %"_Pshadow_Pstandard_CClass"** %2
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %1, i32 0, i32 1
    store %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pio_CConsole_methods"* @"_Pshadow_Pio_CConsole_methods" to %"_Pshadow_Pstandard_CObject_methods"*), %"_Pshadow_Pstandard_CObject_methods"** %3
    %4 = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pstandard_CObject"* %1)
    store %"_Pshadow_Pio_CConsole"* %4, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"
	ret void
}

define i32 @main(i32, i8**) {
	%3 = sub i32 %0, 1
	;call void @consoleInitialize()
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CString_class, i32 %3)
	%5 = bitcast %_Pshadow_Pstandard_CObject* %4 to %_Pshadow_Pstandard_CString**
	br label %15
	%7 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CString_class)
	%8 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %7, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CString_class, %_Pshadow_Pstandard_CClass** %8
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %7, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pstandard_CString_methods* @_Pshadow_Pstandard_CString_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %9
	%10 = insertvalue { i8*, [1 x i32] } undef, i8* %19, 0	
		
	%11 = call i32 @strlen(i8* nocapture %19)	
	%12 = insertvalue { i8*, [1 x i32] } %10, i32 %11, 1, 0	
	%13 = call %"_Pshadow_Pstandard_CString"* @"_Pshadow_Pstandard_CString_Mcreate_Pshadow_Pstandard_Cbyte_A1"(%"_Pshadow_Pstandard_CObject"* %7, { %byte*, [1 x %int] } %12)
	store %_Pshadow_Pstandard_CString* %13, %_Pshadow_Pstandard_CString** %17
	%14 = getelementptr inbounds %_Pshadow_Pstandard_CString** %17, i32 1
	br label %15
	%16 = phi i8** [ %1, %2 ], [ %18, %6 ]
	%17 = phi %_Pshadow_Pstandard_CString** [ %5, %2 ], [ %14, %6 ]
	%18 = getelementptr i8** %16, i32 1
	%19 = load i8** %18
	%20 = icmp eq i8* %19, null
	br i1 %20, label %21, label %6
	
	%22 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Ptest_CTest_class)		
	%23 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %22, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Ptest_CTest_class, %_Pshadow_Pstandard_CClass** %23
	%24 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %22, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Ptest_CTest_methods* @_Pshadow_Ptest_CTest_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %24
	%25 = call %_Pshadow_Ptest_CTest* @_Pshadow_Ptest_CTest_Mcreate(%_Pshadow_Pstandard_CObject* %22)
	%26 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } undef, %_Pshadow_Pstandard_CString** %5, 0
	%27 = insertvalue { %_Pshadow_Pstandard_CString**, [1 x i32] } %26, i32 %3, 1, 0
	invoke void @_Pshadow_Ptest_CTest_Mmain_Pshadow_Pstandard_CString_A1(%_Pshadow_Ptest_CTest* %25, { %_Pshadow_Pstandard_CString**, [1 x i32] } %27)
			to label %28 unwind label %29
	ret i32 0
	%30 = landingpad { i8*, i32 } personality i32 (...)* @__shadow_personality_v0
            catch %"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CException_class
	%31 = extractvalue { i8*, i32 } %30, 0
	%32 = call %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture %31) nounwind
	%33 = load %_Pshadow_Pio_CConsole** @_Pshadow_Pio_CConsole_instance
	%34 = icmp eq %_Pshadow_Pio_CConsole* %33, null
	br i1 %34, label %35, label %40
	%36 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CConsole_class)
	%37 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %36, i32 0, i32 0
	store %_Pshadow_Pstandard_CClass* @_Pshadow_Pio_CConsole_class, %_Pshadow_Pstandard_CClass** %37
	%38 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %36, i32 0, i32 1
	store %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pio_CConsole_methods* @_Pshadow_Pio_CConsole_methods to %_Pshadow_Pstandard_CObject_methods*), %_Pshadow_Pstandard_CObject_methods** %38
	%39 = call %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_Mcreate(%_Pshadow_Pstandard_CObject* %36)
	store %_Pshadow_Pio_CConsole* %39, %_Pshadow_Pio_CConsole** @_Pshadow_Pio_CConsole_instance
	br label %40
	%41 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"
	%42 = bitcast %_Pshadow_Pstandard_CException* %32 to %_Pshadow_Pstandard_CObject*	
	%43 = call %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole* %41, %_Pshadow_Pstandard_CObject* %42 )
	ret i32 1
}