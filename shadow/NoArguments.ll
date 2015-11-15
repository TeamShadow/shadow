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
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CGenericClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CArrayClass_methods" = type { %"_Pshadow_Pstandard_CArrayClass"* (%"_Pshadow_Pstandard_CArrayClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CGenericClass"*)* }
%"_Pshadow_Pstandard_CArrayClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArrayClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %"_Pshadow_Pstandard_CClass"* }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %byte (%"_Pshadow_Pstandard_CString"*)*, %double (%"_Pshadow_Pstandard_CString"*)*, %float (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %long (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %short (%"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %ulong (%"_Pshadow_Pstandard_CString"*)*, %ushort (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CAddressMap_methods" = type opaque
%"_Pshadow_Pstandard_CAddressMap" = type opaque

%"_Pshadow_Pstandard_CClassSet_methods" = type { %"_Pshadow_Pstandard_CClassSet"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] })*, void (%"_Pshadow_Pstandard_CClassSet"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CString"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] })*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, void (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*, %boolean)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CClassSet"*)*, %boolean (%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)*, void (%"_Pshadow_Pstandard_CClassSet"*, %int)*, %int (%"_Pshadow_Pstandard_CClassSet"*)* }
%"_Pshadow_Pstandard_CClassSet" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClassSet_methods"* , { %"_Pshadow_Pstandard_CClassSet_INode"**, [1 x %int] }, %float, %int, %int, %int }
%"_Pshadow_Pstandard_CClassSet_INode_methods" = type { %"_Pshadow_Pstandard_CClassSet_INode"* (%"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, %int (%"_Pshadow_Pstandard_CClassSet_INode"*)*, %"_Pshadow_Pstandard_CClassSet_INode"* (%"_Pshadow_Pstandard_CClassSet_INode"*)*, void (%"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CClassSet_INode"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClassSet_INode"*)* }
%"_Pshadow_Pstandard_CClassSet_INode" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClassSet_INode_methods"* , %"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClassSet_INode"*, %"_Pshadow_Pstandard_CClass"*, %int }
@"_Pshadow_Pstandard_CClassSet_methods" = external constant %"_Pshadow_Pstandard_CClassSet_methods"
@"_Pshadow_Pstandard_CClassSet_class" = external constant %"_Pshadow_Pstandard_CClass"
declare %boolean @"_Pshadow_Pstandard_CClassSet_Madd_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CClassSet"*, %"_Pshadow_Pstandard_CClass"*)
declare %"_Pshadow_Pstandard_CClassSet"* @"_Pshadow_Pstandard_CClassSet_Mcreate_Pshadow_Pstandard_Cint"(%"_Pshadow_Pstandard_CObject"* returned, %int)

%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_COutOfMemoryException_methods" = type { %"_Pshadow_Pstandard_COutOfMemoryException"* (%"_Pshadow_Pstandard_COutOfMemoryException"*)*, %"_Pshadow_Pstandard_COutOfMemoryException"* (%"_Pshadow_Pstandard_COutOfMemoryException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_COutOfMemoryException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_COutOfMemoryException_methods"* , %"_Pshadow_Pstandard_CString"* }

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

declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*, %"_Pshadow_Pstandard_CObject_methods"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
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
declare void @_Pshadow_Ptest_CTest_Mmain(%_Pshadow_Ptest_CTest*)

declare i32 @__shadow_personality_v0(...)
declare %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture) nounwind

@_genericSet = global %"_Pshadow_Pstandard_CClassSet"* null;
@_arraySet = global %"_Pshadow_Pstandard_CClassSet"* null;

define i32 @main(i32, i8**) personality i32 (...)* @__shadow_personality_v0 {	
	%uninitializedConsole = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pio_CConsole_class", %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pio_CConsole_methods"* @"_Pshadow_Pio_CConsole_methods" to %"_Pshadow_Pstandard_CObject_methods"*) )
    %console = call %"_Pshadow_Pio_CConsole"* @"_Pshadow_Pio_CConsole_Mcreate"(%"_Pshadow_Pstandard_CObject"* %uninitializedConsole)
    store %"_Pshadow_Pio_CConsole"* %console, %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"	
	%object = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Ptest_CTest_class, %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Ptest_CTest_methods* @_Pshadow_Ptest_CTest_methods to %_Pshadow_Pstandard_CObject_methods*))		
	%initialized = call %_Pshadow_Ptest_CTest* @_Pshadow_Ptest_CTest_Mcreate(%_Pshadow_Pstandard_CObject* %object)	
	%uninitializedGenericSet = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CClassSet_class, %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pstandard_CClassSet_methods* @_Pshadow_Pstandard_CClassSet_methods to %_Pshadow_Pstandard_CObject_methods*))		
	%genericSet = call %_Pshadow_Pstandard_CClassSet* @_Pshadow_Pstandard_CClassSet_Mcreate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CObject* %uninitializedGenericSet, %int %genericSize)
	store %_Pshadow_Pstandard_CClassSet* %genericSet, %_Pshadow_Pstandard_CClassSet** @_genericSet	
	%uninitializedArraySet = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CClassSet_class, %_Pshadow_Pstandard_CObject_methods* bitcast(%_Pshadow_Pstandard_CClassSet_methods* @_Pshadow_Pstandard_CClassSet_methods to %_Pshadow_Pstandard_CObject_methods*))		
	%arraySet = call %_Pshadow_Pstandard_CClassSet* @_Pshadow_Pstandard_CClassSet_Mcreate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CObject* %uninitializedArraySet, %int %arraySize)	
	store %_Pshadow_Pstandard_CClassSet* %arraySet, %_Pshadow_Pstandard_CClassSet** @_arraySet	
	invoke void @_Pshadow_Ptest_CTest_Mmain(%_Pshadow_Ptest_CTest* %initialized)
			to label %_success unwind label %_exception
_success:
	ret i32 0
_exception:
	%caught = landingpad { i8*, i32 }
            catch %"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CException_class
	%data = extractvalue { i8*, i32 } %caught, 0
	%exception = call %_Pshadow_Pstandard_CException* @__shadow_catch(i8* nocapture %data) nounwind
	; Console already initialized		
	%exceptionAsObject = bitcast %_Pshadow_Pstandard_CException* %exception to %_Pshadow_Pstandard_CObject*	
	call %_Pshadow_Pio_CConsole* @_Pshadow_Pio_CConsole_MprintErrorLine_Pshadow_Pstandard_CObject(%_Pshadow_Pio_CConsole* %console, %_Pshadow_Pstandard_CObject* %exceptionAsObject )
	ret i32 1
}