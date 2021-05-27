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
;declare %shadow.io..Console* @shadow.io..Console..debugPrint.int(%shadow.io..Console*, %int)

declare i32 @strlen(i8* nocapture)

%shadow.test..Test = type opaque
%shadow.test..Test._methods = type opaque
@shadow.test..Test._methods = external constant %shadow.test..Test._methods
@shadow.test..Test.class = external constant %shadow.standard..Class
declare %shadow.test..Test* @shadow.test..Test..create(%shadow.standard..Object*)
declare void @shadow.test..Test..main(%shadow.test..Test*)

declare i32 @__shadow_personality_v0(...)
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object._methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)

define i32 @main(i32, i8**) personality i32 (...)* @__shadow_personality_v0 {				
	%uninitializedConsole = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.io..Console.class, %shadow.standard..Object._methods* bitcast(%shadow.io..Console._methods* @shadow.io..Console._methods to %shadow.standard..Object._methods*) )
	%console = call %shadow.io..Console* @shadow.io..Console..create(%shadow.standard..Object* %uninitializedConsole)
    store %shadow.io..Console* %console, %shadow.io..Console** @shadow.io..Console.singleton		
	%object = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.test..Test.class, %shadow.standard..Object._methods* bitcast(%shadow.test..Test._methods* @shadow.test..Test._methods to %shadow.standard..Object._methods*))		
	%initialized = call %shadow.test..Test* @shadow.test..Test..create(%shadow.standard..Object* %object)	
	invoke void @callMain(%shadow.test..Test* %initialized)
			to label %_success unwind label %_exception
_success:	
	call void @__decrementRef(%shadow.standard..Object* %object) nounwind
	store %shadow.io..Console* null, %shadow.io..Console** @shadow.io..Console.singleton		
	%consoleAsObj = bitcast %shadow.io..Console* %console to %shadow.standard..Object*
    call void @__decrementRef(%shadow.standard..Object* %consoleAsObj) nounwind
	ret i32 0
_exception:
	%caught = landingpad { i8*, i32 }
            catch %shadow.standard..Class* @shadow.standard..Exception.class
	%data = extractvalue { i8*, i32 } %caught, 0
	%exception = call %shadow.standard..Exception* @__shadow_catch(i8* nocapture %data) nounwind
	; Console already initialized		
	%exceptionAsObject = bitcast %shadow.standard..Exception* %exception to %shadow.standard..Object*	
	call %shadow.io..Console* @shadow.io..Console..printErrorLine_shadow.standard..Object(%shadow.io..Console* %console, %shadow.standard..Object* %exceptionAsObject )
	ret i32 1
}

%shadow.standard..Thread = type opaque
declare %shadow.standard..Thread* @shadow.standard..Thread..initMainThread()
declare void @shadow.standard..Thread..waitForThreads(%shadow.standard..Thread*)
define void @callMain(%shadow.test..Test* %initialized) {
entry:
	%mainThread = call %shadow.standard..Thread* @shadow.standard..Thread..initMainThread()
	call void @shadow.test..Test..main(%shadow.test..Test* %initialized)
	call void @shadow.standard..Thread..waitForThreads(%shadow.standard..Thread* %mainThread)
	
	%threadAsObj = bitcast %shadow.standard..Thread* %mainThread to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %threadAsObj) nounwind	
	
	ret void
}
