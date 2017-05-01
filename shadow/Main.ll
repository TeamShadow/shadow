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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, void (%shadow.standard..Object*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong} }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..byte_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods

%shadow.standard..ClassSet = type opaque
%shadow.standard..ClassSet_methods = type opaque

@shadow.standard..ClassSet_methods = external constant %shadow.standard..ClassSet_methods
@shadow.standard..ClassSet_class = external constant %shadow.standard..Class
declare %boolean @shadow.standard..ClassSet_Madd_shadow.standard..Class(%shadow.standard..ClassSet*, %shadow.standard..Class*)
declare %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* returned, %int)

%shadow.io..Console_methods = type opaque
@shadow.io..Console_methods = external constant %shadow.io..Console_methods
@shadow.io..Console_class = external constant %shadow.standard..Class
%shadow.io..Console = type opaque
@shadow.io..Console_instance = external global %shadow.io..Console*

declare %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine(%shadow.io..Console*)
declare %shadow.io..Console* @shadow.io..Console_MprintErrorLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A(%shadow.standard..Object*, { {%ulong, i8}*, %shadow.standard..Class*, %ulong })

declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 

declare i32 @strlen(i8* nocapture)
declare i8* @strncpy(i8*, i8* nocapture, i32)

%shadow.test..Test = type opaque
%shadow.test..Test_methods = type opaque
@shadow.test..Test_methods = external constant %shadow.test..Test_methods
@shadow.test..Test_class = external constant %shadow.standard..Class
declare %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object*)
declare void @shadow.test..Test_Mmain_shadow.standard..String_A(%shadow.test..Test*, { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong })

declare i32 @__shadow_personality_v0(...)
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array) nounwind 
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %ulong %elements)

@_genericSet = global %shadow.standard..ClassSet* null
@_arraySet = global %shadow.standard..ClassSet* null

define i32 @main(i32 %argc, i8** %argv) personality i32 (...)* @__shadow_personality_v0 {
_start:
	%uninitializedGenericSet = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..ClassSet_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..ClassSet_methods* @shadow.standard..ClassSet_methods to %shadow.standard..Object_methods*))		
	%genericSet = call %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* %uninitializedGenericSet, %int %genericSize) ; %genericSize is replaced by compiler
	store %shadow.standard..ClassSet* %genericSet, %shadow.standard..ClassSet** @_genericSet	
	%uninitializedArraySet = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..ClassSet_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..ClassSet_methods* @shadow.standard..ClassSet_methods to %shadow.standard..Object_methods*))		
	%arraySet = call %shadow.standard..ClassSet* @shadow.standard..ClassSet_Mcreate_int(%shadow.standard..Object* %uninitializedArraySet, %int %arraySize) ; %arraySize is replaced by compiler
	store %shadow.standard..ClassSet* %arraySet, %shadow.standard..ClassSet** @_arraySet		
	;_INITIALIZE_CLASS_SETS_	
	%uninitializedConsole = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..Console_class, %shadow.standard..Object_methods* bitcast(%shadow.io..Console_methods* @shadow.io..Console_methods to %shadow.standard..Object_methods*) )
	%console = call %shadow.io..Console* @shadow.io..Console_Mcreate(%shadow.standard..Object* %uninitializedConsole)
    store %shadow.io..Console* %console, %shadow.io..Console** @shadow.io..Console_instance	
	%countInt = sub i32 %argc, 1	
	%count = zext %int %countInt to %long
	%array = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..String_class, %long %count)
	%stringRef = bitcast {%ulong, %shadow.standard..Object*}* %array to {%ulong, %shadow.standard..String*}*	
	%stringArray = getelementptr {%ulong, %shadow.standard..String*}, {%ulong, %shadow.standard..String*}* %stringRef, i32 0, i32 1
	br label %_loopTest
_loopBody:
	%allocatedString = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..String_methods* @shadow.standard..String_methods to %shadow.standard..Object_methods*))	
	%length = call i32 @strlen(i8* nocapture %nextArg)	
	%longLength = zext %int %length to %ulong
	%allocatedArray = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..byte_class, i64 %longLength)	
	%byteArray = bitcast {%ulong, %shadow.standard..Object*}* %allocatedArray to {%ulong, %byte}*
	%bytes = getelementptr {%ulong, %byte}, {%ulong, %byte}* %byteArray, i32 0, i32 1
	call i8* @strncpy(i8* %bytes, i8* nocapture %nextArg, i32 %length)	
	%argAsArray1 = insertvalue { {%ulong, %byte}*, %shadow.standard..Class*, %ulong } undef, {%ulong, %byte}* %byteArray, 0	
	%argAsArray2 = insertvalue { {%ulong, %byte}*, %shadow.standard..Class*, %ulong } %argAsArray1, %shadow.standard..Class* @shadow.standard..byte_class, 1		
	%argAsArray3 = insertvalue { {%ulong, %byte}*, %shadow.standard..Class*, %ulong } %argAsArray2, %ulong %longLength, 2	
	%string = call %shadow.standard..String* @shadow.standard..String_Mcreate_byte_A(%shadow.standard..Object* %allocatedString, { {%ulong, %byte}*, %shadow.standard..Class*, %ulong } %argAsArray3)
	store %shadow.standard..String* %string, %shadow.standard..String** %stringPhi
	%nextString = getelementptr %shadow.standard..String*, %shadow.standard..String** %stringPhi, i32 1
	br label %_loopTest
_loopTest:
	%argPhi = phi i8** [ %argv, %_start ], [ %nextArgPointer, %_loopBody ]
	%stringPhi = phi %shadow.standard..String** [ %stringArray, %_start ], [ %nextString, %_loopBody ]
	%nextArgPointer = getelementptr i8*, i8** %argPhi, i32 1
	%nextArg = load i8*, i8** %nextArgPointer
	%done = icmp eq i8* %nextArg, null
	br i1 %done, label %_loopEnd, label %_loopBody	
_loopEnd:	
	%object = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.test..Test_class, %shadow.standard..Object_methods* bitcast(%shadow.test..Test_methods* @shadow.test..Test_methods to %shadow.standard..Object_methods*))		
	%initialized = call %shadow.test..Test* @shadow.test..Test_Mcreate(%shadow.standard..Object* %object)
	%args1 = insertvalue { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } undef, {%ulong, %shadow.standard..String*}* %stringRef, 0
	%args2 = insertvalue { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } %args1, %shadow.standard..Class* @shadow.standard..String_class, 1
	%args3 = insertvalue { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } %args2, %ulong %count, 2
	invoke void @callMain(%shadow.test..Test* %initialized, { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } %args3)
			to label %_success unwind label %_exception
_success:		
	call void @__decrementRef(%shadow.standard..Object* %object) nounwind
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

%shadow.standard..Thread = type opaque
declare %shadow.standard..Thread* @shadow.standard..Thread_MinitMainThread()
declare void @shadow.standard..Thread_MwaitForThreadsNative(%shadow.standard..Thread*)
define void @callMain(%shadow.test..Test* %initialized, { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } %args) {
entry:
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread_MinitMainThread()
	call void @shadow.test..Test_Mmain_shadow.standard..String_A(%shadow.test..Test* %initialized, { {%ulong, %shadow.standard..String*}*, %shadow.standard..Class*, %ulong } %args)
	call void @shadow.standard..Thread_MwaitForThreadsNative(%shadow.standard..Thread* %mainThread)
	
	ret void
}