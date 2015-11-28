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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type { %shadow.standard..GenericClass* (%shadow.standard..GenericClass*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..GenericClass*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..GenericClass*)* }
%shadow.standard..GenericClass = type { %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int, { %shadow.standard..Object**, [1 x %int] } }
%shadow.standard..ArrayClass_methods = type { %shadow.standard..ArrayClass* (%shadow.standard..ArrayClass*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..GenericClass*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..GenericClass*)* }
%shadow.standard..ArrayClass = type { %shadow.standard..Class*, %shadow.standard..ArrayClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int, { %shadow.standard..Object**, [1 x %int] }, %shadow.standard..Class* }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque

%shadow.standard..ClassSet_methods = type { %shadow.standard..ClassSet* (%shadow.standard..ClassSet*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..ClassSet*, %shadow.standard..Class*, %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Class**, [1 x %int] }, { %shadow.standard..Object**, [1 x %int] })*, void (%shadow.standard..ClassSet*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..ClassSet*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] })*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, void (%shadow.standard..ClassSet*, %shadow.standard..Class*, %boolean)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..ClassSet*)*, %boolean (%shadow.standard..ClassSet*, %shadow.standard..Class*)*, void (%shadow.standard..ClassSet*, %int)*, %int (%shadow.standard..ClassSet*)* }
%shadow.standard..ClassSet = type { %shadow.standard..Class*, %shadow.standard..ClassSet_methods* , { %shadow.standard..ClassSet.Node**, [1 x %int] }, %float, %int, %int, %int }
%shadow.standard..ClassSet.Node_methods = type { %shadow.standard..ClassSet.Node* (%shadow.standard..ClassSet.Node*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)*, %int (%shadow.standard..ClassSet.Node*)*, %shadow.standard..ClassSet.Node* (%shadow.standard..ClassSet.Node*)*, void (%shadow.standard..ClassSet.Node*, %shadow.standard..ClassSet.Node*)*, %shadow.standard..Class* (%shadow.standard..ClassSet.Node*)* }
%shadow.standard..ClassSet.Node = type { %shadow.standard..Class*, %shadow.standard..ClassSet.Node_methods* , %shadow.standard..ClassSet*, %shadow.standard..ClassSet.Node*, %shadow.standard..Class*, %int }
@shadow.standard..ClassSet_methods = external constant %shadow.standard..ClassSet_methods
@shadow.standard..ClassSet_class = external constant %shadow.standard..Class
declare %boolean @shadow.standard..ClassSet_Madd_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*)
declare %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* returned, %int)

%shadow.standard..Exception_methods = type { %shadow.standard..Exception* (%shadow.standard..Exception*)*, %shadow.standard..Exception* (%shadow.standard..Exception*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..Exception = type { %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type { %shadow.standard..OutOfMemoryException* (%shadow.standard..OutOfMemoryException*)*, %shadow.standard..OutOfMemoryException* (%shadow.standard..OutOfMemoryException*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Exception*)*, %shadow.standard..String* (%shadow.standard..Exception*)* }
%shadow.standard..OutOfMemoryException = type { %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

%shadow.io..Console_methods = type opaque
@shadow.io..Console_methods = external constant %shadow.io..Console_methods
@shadow.io..Console_class = external constant %shadow.standard..Class
%shadow.io..Console = type { %shadow.standard..Class*, %shadow.io..Console_methods* , %boolean }
@shadow.io..Console_instance = external global %shadow.io..Console*

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class

declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class*, %shadow.standard..Object_methods*)
declare noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class*, i32)
declare %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine(%shadow.io..Console*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A1(%shadow.standard..Object*, { %byte*, [1 x %int] })

declare i32 @strlen(i8* nocapture)

%shadow.test..Test = type opaque
%shadow.test..Test_methods = type opaque
@shadow.test..Test_methods = external constant %shadow.test..Test_methods
@shadow.test..Test_class = external constant %shadow.standard..Class
declare %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object*)
declare void @shadow.test..Test_Mmain_shadow.standard..String_A1(%shadow.test..Test*, { %shadow.standard..String**, [1 x i32] })

declare i32 @__shadow_personality_v0(...)
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind

@_genericSet = global %shadow.standard..ClassSet* null
@_arraySet = global %shadow.standard..ClassSet* null

define i32 @main(i32 %argc, i8** %argv) personality i32 (...)* @__shadow_personality_v0 {
_start:	
	%uninitializedConsole = call noalias %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.io..Console_class, %shadow.standard..Object_methods* bitcast(%shadow.io..Console_methods* @shadow.io..Console_methods to %shadow.standard..Object_methods*) )
    %console = call %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object* %uninitializedConsole)
    store %shadow.io..Console* %console, %shadow.io..Console** @shadow.io..Console_instance	
	%count = sub i32 %argc, 1	
	%array = call %shadow.standard..Object* @shadow.standard..Class_Mallocate_int(%shadow.standard..Class* @shadow.standard..String_class, i32 %count)
	%stringArray = bitcast %shadow.standard..Object* %array to %shadow.standard..String**
	br label %_loopTest
_loopStart:
	%allocatedString = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..String_methods* @shadow.standard..String_methods to %shadow.standard..Object_methods*))	
	%uninitializedArgAsArray = insertvalue { i8*, [1 x i32] } undef, i8* %nextArg, 0
	%length = call i32 @strlen(i8* nocapture %nextArg)	
	%argAsArray = insertvalue { i8*, [1 x i32] } %uninitializedArgAsArray, i32 %length, 1, 0	
	%string = call %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A1(%shadow.standard..Object* %allocatedString, { %byte*, [1 x %int] } %argAsArray)
	store %shadow.standard..String* %string, %shadow.standard..String** %stringPhi
	%nextString = getelementptr %shadow.standard..String*, %shadow.standard..String** %stringPhi, i32 1
	br label %_loopTest
_loopTest:
	%argPhi = phi i8** [ %argv, %_start ], [ %nextArgPointer, %_loopStart ]
	%stringPhi = phi %shadow.standard..String** [ %stringArray, %_start ], [ %nextString, %_loopStart ]
	%nextArgPointer = getelementptr i8*, i8** %argPhi, i32 1
	%nextArg = load i8*, i8** %nextArgPointer
	%done = icmp eq i8* %nextArg, null
	br i1 %done, label %_loopEnd, label %_loopStart	
_loopEnd:
	%object = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.test..Test_class, %shadow.standard..Object_methods* bitcast(%shadow.test..Test_methods* @shadow.test..Test_methods to %shadow.standard..Object_methods*))		
	%initialized = call %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object* %object)
	%undefinedArgs = insertvalue { %shadow.standard..String**, [1 x i32] } undef, %shadow.standard..String** %stringArray, 0
	%args = insertvalue { %shadow.standard..String**, [1 x i32] } %undefinedArgs, i32 %count, 1, 0	
	%uninitializedGenericSet = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.standard..ClassSet_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..ClassSet_methods* @shadow.standard..ClassSet_methods to %shadow.standard..Object_methods*))		
	%genericSet = call %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* %uninitializedGenericSet, %int %genericSize) ; %genericSize is replaced by compiler
	store %shadow.standard..ClassSet* %genericSet, %shadow.standard..ClassSet** @_genericSet	
	%uninitializedArraySet = call %shadow.standard..Object* @shadow.standard..Class_Mallocate(%shadow.standard..Class* @shadow.standard..ClassSet_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..ClassSet_methods* @shadow.standard..ClassSet_methods to %shadow.standard..Object_methods*))		
	%arraySet = call %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* %uninitializedArraySet, %int %arraySize) ; %arraySize is replaced by compiler
	store %shadow.standard..ClassSet* %arraySet, %shadow.standard..ClassSet** @_arraySet	
	invoke void @shadow.test..Test_Mmain_shadow.standard..String_A1(%shadow.test..Test* %initialized, { %shadow.standard..String**, [1 x i32] } %args)
			to label %_success unwind label %_exception
_success:
	ret i32 0
_exception:
	%caught = landingpad { i8*, i32 }
            catch %shadow.standard..Class* @shadow.standard..Exception_class
	%data = extractvalue { i8*, i32 } %caught, 0
	%exception = call %shadow.standard..Exception* @__shadow_catch(i8* nocapture %data) nounwind
	; Console already initialized		
	%exceptionAsObject = bitcast %shadow.standard..Exception* %exception to %shadow.standard..Object*	
	call %shadow.io..Console* @shadow.io..Console_MprintErrorLine_shadow.standard..Object(%shadow.io..Console* %console, %shadow.standard..Object* %exceptionAsObject )
	ret i32 1
}