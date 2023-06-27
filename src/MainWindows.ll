target triple = "x86_64-pc-windows-msvc"

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
%size_t = type i8*

; standard definitions
%shadow.standard..Object._methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object._methods*  }
%shadow.standard..Class._methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass._methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator._methods = type opaque
%shadow.standard..String._methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap._methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable._methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array._methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array._methods* , %long }
%shadow.standard..ArrayNullable._methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable._methods* , %long }

%shadow.standard..Exception._methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception._methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException._methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException._methods* , %shadow.standard..String* }

@shadow.standard..Class._methods = external constant %shadow.standard..Class._methods
@shadow.standard..Class.class = external constant %shadow.standard..Class
@shadow.standard..String._methods = external constant %shadow.standard..String._methods
@shadow.standard..String.class = external constant %shadow.standard..Class
@shadow.standard..Exception._methods = external constant %shadow.standard..Exception._methods
@shadow.standard..Exception.class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException.class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException._methods = external constant %shadow.standard..OutOfMemoryException._methods
@ubyte._A.class = external constant %shadow.standard..GenericClass
@shadow.standard..String._A.class = external constant %shadow.standard..GenericClass

%shadow.io..Console._methods = type opaque
@shadow.io..Console._methods = external constant %shadow.io..Console._methods
@shadow.io..Console.class = external constant %shadow.standard..Class
%shadow.io..Console = type opaque
@shadow.io..Console.singleton = external thread_local global %shadow.io..Console*

declare %shadow.io..Console* @shadow.io..Console..create(%shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console..printError_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console..printError_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console..printErrorLine(%shadow.io..Console*)
declare %shadow.io..Console* @shadow.io..Console..printErrorLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object*, %shadow.standard..Array*)

;declare %shadow.io..Console* @shadow.io..Console..print_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console..printLine(%shadow.io..Console*) 

declare %size_t @strlen(i8* nocapture)
declare i8* @strncpy(i8*, i8* nocapture, %size_t)

%shadow.test..Test = type opaque
%shadow.test..Test._methods = type opaque
@shadow.test..Test._methods = external constant %shadow.test..Test._methods
@shadow.test..Test.class = external constant %shadow.standard..Class
declare %shadow.test..Test* @shadow.test..Test..create(%shadow.standard..Object*)
declare void @shadow.test..Test..main_shadow.standard..String._A(%shadow.test..Test*, %shadow.standard..Array*)

declare i32 @__C_specific_handler(...)
@__exceptionStorage = external thread_local global %shadow.standard..Exception*

;declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__printRef(%shadow.standard..Object* %object) nounwind
declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object._methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* %class, %ulong %longElements, %boolean %nullable)

define i32 @main(i32 %argc, i8** %argv) personality i32 (...)* @__C_specific_handler  {
_start:
    %ex = alloca %shadow.standard..Exception*
	%uninitializedConsole = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..Console.class, %shadow.standard..Object._methods* bitcast(%shadow.io..Console._methods* @shadow.io..Console._methods to %shadow.standard..Object._methods*) )
	%console = call %shadow.io..Console* @shadow.io..Console..create(%shadow.standard..Object* %uninitializedConsole)
    store %shadow.io..Console* %console, %shadow.io..Console** @shadow.io..Console.singleton
	%countInt = sub i32 %argc, 1	
	%count = zext %int %countInt to %long
	%array = call %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* @shadow.standard..String._A.class, %long %count, %boolean false)
	%stringRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%stringArray = bitcast %shadow.standard..Array* %stringRef to %shadow.standard..String**
	br label %_loopTest
_loopBody:
	%allocatedString = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..String.class, %shadow.standard..Object._methods* bitcast(%shadow.standard..String._methods* @shadow.standard..String._methods to %shadow.standard..Object._methods*))	
	%length = call %size_t @strlen(i8* nocapture %nextArg)	
	%longLength = ptrtoint %size_t %length to %ulong
	%allocatedArray = call %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* @ubyte._A.class, %long %longLength, %boolean false)	
	%byteArray = getelementptr %shadow.standard..Array, %shadow.standard..Array* %allocatedArray, i32 1
	%bytes = bitcast %shadow.standard..Array* %byteArray to i8*
	call i8* @strncpy(i8* %bytes, i8* nocapture %nextArg, %size_t %length)	
	%string = call %shadow.standard..String* @shadow.standard..String..create.ubyte._A(%shadow.standard..Object* %allocatedString, %shadow.standard..Array* %allocatedArray)
	%byteArrayAsObj = bitcast %shadow.standard..Array* %byteArray to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %byteArrayAsObj) nounwind
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
	%object = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.test..Test.class, %shadow.standard..Object._methods* bitcast(%shadow.test..Test._methods* @shadow.test..Test._methods to %shadow.standard..Object._methods*))		
	%initialized = call %shadow.test..Test* @shadow.test..Test..create(%shadow.standard..Object* %object)
	invoke void @callMain(%shadow.test..Test* %initialized, %shadow.standard..Array* %array)
			to label %_success unwind label %_exception
_success:		
	call void @__decrementRef(%shadow.standard..Object* %object) nounwind
	%arrayAsObj = bitcast %shadow.standard..Array* %array to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %arrayAsObj) nounwind	
	store %shadow.io..Console* null, %shadow.io..Console** @shadow.io..Console.singleton		
	%consoleAsObj = bitcast %shadow.io..Console* %console to %shadow.standard..Object*
    call void @__decrementRef(%shadow.standard..Object* %consoleAsObj) nounwind
	ret i32 0
_exception:
	%switchToken = catchswitch within none [label %_catch] unwind to caller
_catch:
    %catchToken = catchpad within %switchToken [i8* bitcast (i32 (i8*, i8*)* @_exceptionMethodshadow.standard..Exception to i8*)]
	catchret from %catchToken to label %_catchBody
_catchBody:
	; Console already initialized	
	%exception = load %shadow.standard..Exception*, %shadow.standard..Exception**  @__exceptionStorage
	%exceptionAsObject = bitcast %shadow.standard..Exception* %exception to %shadow.standard..Object*	
	call %shadow.io..Console* @shadow.io..Console..printErrorLine_shadow.standard..Object(%shadow.io..Console* %console, %shadow.standard..Object* %exceptionAsObject)
	call void @__decrementRef(%shadow.standard..Object* %exceptionAsObject) nounwind
	ret i32 1
}

%shadow.standard..Thread = type opaque
declare %shadow.standard..Thread* @shadow.standard..Thread..initMainThread()
declare void @shadow.standard..Thread..waitForThreads(%shadow.standard..Thread*)

define void @callMain(%shadow.test..Test* %initialized, %shadow.standard..Array* %args) {
entry:
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread..initMainThread()
	call void @shadow.test..Test..main_shadow.standard..String._A(%shadow.test..Test* %initialized, %shadow.standard..Array* %args)
	call void @shadow.standard..Thread..waitForThreads(%shadow.standard..Thread* %mainThread)

	%threadAsObj = bitcast %shadow.standard..Thread* %mainThread to %shadow.standard..Object*
	; call void @__printRef(%shadow.standard..Object* %threadAsObj) nounwind
	call void @__decrementRef(%shadow.standard..Object* %threadAsObj) nounwind
	
	ret void
}

declare i32 @__exceptionFilter(i8*, i8*, %shadow.standard..Class*)
$_exceptionMethodshadow.standard..Exception = comdat any
define linkonce_odr i32 @_exceptionMethodshadow.standard..Exception(i8* %0, i8* %1) comdat {
    %3 = call i32 @__exceptionFilter(i8* %0, i8* %1, %shadow.standard..Class* @shadow.standard..Exception.class)
    ret i32 %3
}