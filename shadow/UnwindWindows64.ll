; Exception and memory related native methods

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
%shadow.standard..Object_methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , %shadow.standard..Array*, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %long }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %long }

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@byte_A_class = external constant %shadow.standard..Class

@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods
@shadow.standard..ArrayNullable_methods = external constant %shadow.standard..Array_methods

@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods

declare %shadow.standard..OutOfMemoryException* @shadow.standard..OutOfMemoryException_Mcreate(%shadow.standard..Object*)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)

declare i1 @shadow.standard..Class_MisSubtype_shadow.standard..Class(%shadow.standard..Class*, %shadow.standard..Class*)

declare %shadow.standard..Class* @getBaseClass(%shadow.standard..Class* %class) nounwind alwaysinline

; _URC_NO_REASON = 0
; _URC_FOREIGN_EXCEPTION_CAUGHT = 1
; _URC_FATAL_PHASE2_ERROR = 2
; _URC_FATAL_PHASE1_ERROR = 3
; _URC_NORMAL_STOP = 4
; _URC_END_OF_STACK = 5
; _URC_HANDLER_FOUND = 6
; _URC_INSTALL_CONTEXT = 7
; _URC_CONTINUE_UNWIND = 8

; _UA_SEARCH_PHASE = 1
; _UA_CLEANUP_PHASE = 2
; _UA_HANDLER_FRAME = 3
; _UA_FORCE_UNWIND = 8
; _UA_END_OF_STACK = 16

%_Unwind_Ptr = type i8*
%_Unwind_Word = type i8*
%_Unwind_Sword = type i8*
%_Unwind_Action = type i32
%_Unwind_Reason_Code = type i32
%_Unwind_Exception_Class = type i64
%_Unwind_Stop_Fn = type %_Unwind_Reason_Code (i32, %_Unwind_Action, %_Unwind_Exception_Class, %struct._Unwind_Exception*, %struct._Unwind_Context*, i8*)*
%_Unwind_Trace_Fn = type %_Unwind_Reason_Code (%struct._Unwind_Context*, i8*)*
%_Unwind_Personality_Fn = type %_Unwind_Reason_Code (i32, %_Unwind_Action, %_Unwind_Exception_Class, %struct._Unwind_Exception*, %struct._Unwind_Context*)*
%_Unwind_Exception_Cleanup_Fn = type void (%_Unwind_Reason_Code, %struct._Unwind_Exception*)*
%struct._Unwind_Context = type opaque
%struct._Unwind_Exception = type { %_Unwind_Exception_Class, %_Unwind_Exception_Cleanup_Fn, i64, i64 }

declare %_Unwind_Reason_Code @_Unwind_RaiseException(%struct._Unwind_Exception*)
declare %_Unwind_Reason_Code @_Unwind_ForcedUnwind(%struct._Unwind_Exception*, %_Unwind_Stop_Fn, i8*)
declare void @_Unwind_DeleteException(%struct._Unwind_Exception*)
declare void @_Unwind_Resume(%struct._Unwind_Exception*)
declare %_Unwind_Reason_Code @_Unwind_Resume_or_Rethrow(%struct._Unwind_Exception)
declare %_Unwind_Reason_Code @_Unwind_Backtrace(%_Unwind_Trace_Fn, i8*)
declare %_Unwind_Word @_Unwind_GetGR(%struct._Unwind_Context*, i32) nounwind readonly
declare void @_Unwind_SetGR(%struct._Unwind_Context*, i32, %_Unwind_Word) nounwind
declare %_Unwind_Ptr @_Unwind_GetIP(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetIPInfo(%struct._Unwind_Context*, i32*) nounwind readonly
declare void @_Unwind_SetIP(%struct._Unwind_Context*, %_Unwind_Ptr) nounwind
declare %_Unwind_Word @_Unwind_GetCFA(%struct._Unwind_Context*) nounwind readonly
declare i8* @_Unwind_GetLanguageSpecificData(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetRegionStart(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetDataRelBase(%struct._Unwind_Context*) nounwind readonly
declare %_Unwind_Ptr @_Unwind_GetTextRelBase(%struct._Unwind_Context*) nounwind readonly
declare i8* @_Unwind_FindEnclosingFunction(i8*) nounwind readonly

declare noalias i8* @calloc(i64, i64) nounwind
declare noalias i8* @malloc(i64) nounwind
declare void @free(i8*) nounwind
declare void @abort() noreturn nounwind
declare void @exit(i32) noreturn nounwind

;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 
;declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)


define void @__shadow_throw(%shadow.standard..Object*) cold noreturn {
entry:
	%1 = tail call noalias i8* @malloc(i64 add (i64 ptrtoint (%struct._Unwind_Exception* getelementptr (%struct._Unwind_Exception, %struct._Unwind_Exception* null, i64 1) to i64), i64 ptrtoint (i1** getelementptr (i1*, i1** null, i64 1) to i64))) nounwind
	%2 = bitcast i8* %1 to %struct._Unwind_Exception*
	%3 = load %_Unwind_Exception_Class, %_Unwind_Exception_Class* bitcast ([8 x i8]* @shadow.exception.class to %_Unwind_Exception_Class*)
	%4 = getelementptr %struct._Unwind_Exception, %struct._Unwind_Exception* %2, i64 0, i32 0
	store %_Unwind_Exception_Class %3, %_Unwind_Exception_Class* %4
	%5 = getelementptr %struct._Unwind_Exception, %struct._Unwind_Exception* %2, i64 0, i32 1
	store %_Unwind_Exception_Cleanup_Fn @shadow.exception.cleanup, %_Unwind_Exception_Cleanup_Fn* %5
	%6 = getelementptr %struct._Unwind_Exception, %struct._Unwind_Exception* %2, i64 1
	%7 = bitcast %struct._Unwind_Exception* %6 to %shadow.standard..Object**
	store %shadow.standard..Object* %0, %shadow.standard..Object** %7
	%8 = tail call %_Unwind_Reason_Code @_Unwind_RaiseException(%struct._Unwind_Exception* %2)
	tail call void @abort() noreturn nounwind unreachable
}

define noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods) {	
	%sizeRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 8
	%size = load %uint, %uint* %sizeRef	

	%classAsObj = bitcast %shadow.standard..Class* %class to %shadow.standard..Object*
	%sizeLong = zext %uint %size to %ulong

	; allocate
	%memory = call noalias i8* @calloc(%ulong 1, %ulong %sizeLong) nounwind
	
	%isNull = icmp eq i8* %memory, null
	br i1 %isNull, label %_outOfMemory, label %_success
_outOfMemory: 
	%exception = bitcast %shadow.standard..OutOfMemoryException* @_OutOfMemoryException to %shadow.standard..Object*
	call void @__shadow_throw(%shadow.standard..Object* %exception) noreturn
    unreachable
_success:
	%object = bitcast i8* %memory to %shadow.standard..Object*
	; set reference count
	%countRef = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 0
	store %ulong 1, %ulong* %countRef		
	%object.class = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 1
    store %shadow.standard..Class* %class, %shadow.standard..Class** %object.class
    %object.methods = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 2
    store %shadow.standard..Object_methods* %methods, %shadow.standard..Object_methods** %object.methods
	
	ret %shadow.standard..Object* %object
}

; %shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long }

define noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..GenericClass* %class, %ulong %elements, %boolean %nullable) {	
	%classAsClass =  bitcast %shadow.standard..GenericClass* %class to %shadow.standard..Class*
	%baseClass =  call %shadow.standard..Class* @getBaseClass(%shadow.standard..Class* %classAsClass)
	%perObject = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)		
	%perObjectLong = zext %int %perObject to %long
	%size = mul %long %perObjectLong, %elements
	
	; Add size of Array object
	%arraySize = ptrtoint %shadow.standard..Array* getelementptr (%shadow.standard..Array, %shadow.standard..Array* null, i32 1) to %long
	%sizeAsObject = add %long %size, %arraySize	
	%arrayAsBytes = call noalias i8* @calloc(%ulong 1, %ulong %sizeAsObject)	
	%isNull = icmp eq i8* %arrayAsBytes, null
	br i1 %isNull, label %_outOfMemory, label %_success
_outOfMemory:	
	%exception = bitcast %shadow.standard..OutOfMemoryException* @_OutOfMemoryException to %shadow.standard..Object*
	call void @__shadow_throw(%shadow.standard..Object* %exception) noreturn
	unreachable
_success:
	%array = bitcast i8* %arrayAsBytes to  %shadow.standard..Array*	
		
	; store reference count of 1
	%countRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 0
	store %ulong 1, %ulong* %countRef

	; store class
	%classRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
	%regularClass = bitcast %shadow.standard..GenericClass* %class to %shadow.standard..Class*
	store %shadow.standard..Class* %regularClass, %shadow.standard..Class** %classRef
	
	; store length	
	%lengthRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	store %long %elements,  %long* %lengthRef		
	
	br i1 %nullable, label %_isNullable, label %_notNullable
	
_isNullable:
	; store methods
	%nullableMethodRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 2
	store %shadow.standard..Array_methods* @shadow.standard..ArrayNullable_methods,  %shadow.standard..Array_methods** %nullableMethodRef
	
	ret %shadow.standard..Array* %array
	
_notNullable:
	; store methods
	%methodRef = getelementptr %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 2
	store %shadow.standard..Array_methods* @shadow.standard..Array_methods,  %shadow.standard..Array_methods** %methodRef
		
	ret %shadow.standard..Array* %array
}


define %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind {
entry:
	%1 = bitcast i8* %0 to %struct._Unwind_Exception*
	%2 = getelementptr %struct._Unwind_Exception, %struct._Unwind_Exception* %1, i32 1
	%3 = bitcast %struct._Unwind_Exception* %2 to %shadow.standard..Exception**
	%4 = load %shadow.standard..Exception*, %shadow.standard..Exception** %3
	tail call void @free(i8* %0) nounwind
	ret %shadow.standard..Exception* %4
}

@_array0 = private unnamed_addr constant {%ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long, [20 x %byte]} {%ulong -1, %shadow.standard..Class* @byte_A_class, %shadow.standard..Array_methods* @shadow.standard..Array_methods, %long 20, [20 x %byte] c"Heap space exhausted"}
@_string0 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, %shadow.standard..Array* bitcast ( {%ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long, [20 x %byte]}* @_array0 to %shadow.standard..Array*), %boolean true }
@_OutOfMemoryException = private constant %shadow.standard..OutOfMemoryException { %ulong -1, %shadow.standard..Class* @shadow.standard..OutOfMemoryException_class, %shadow.standard..OutOfMemoryException_methods* @shadow.standard..OutOfMemoryException_methods, %shadow.standard..String* @_string0 }
