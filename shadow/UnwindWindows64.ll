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


@_array0 = private unnamed_addr constant {%ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long, [20 x %byte]} {%ulong -1, %shadow.standard..Class* @byte_A_class, %shadow.standard..Array_methods* @shadow.standard..Array_methods, %long 20, [20 x %byte] c"Heap space exhausted"}
@_string0 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, %shadow.standard..Array* bitcast ( {%ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long, [20 x %byte]}* @_array0 to %shadow.standard..Array*), %boolean true }
@_OutOfMemoryException = private unnamed_addr constant %shadow.standard..OutOfMemoryException { %ulong -1, %shadow.standard..Class* @shadow.standard..OutOfMemoryException_class, %shadow.standard..OutOfMemoryException_methods* @shadow.standard..OutOfMemoryException_methods, %shadow.standard..String* @_string0 }


declare %shadow.standard..OutOfMemoryException* @shadow.standard..OutOfMemoryException_Mcreate(%shadow.standard..Object*)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)

declare i1 @shadow.standard..Class_MisSubtype_shadow.standard..Class(%shadow.standard..Class*, %shadow.standard..Class*)

declare %shadow.standard..Class* @getBaseClass(%shadow.standard..Class* %class) nounwind alwaysinline

;typedef struct _EXCEPTION_RECORD64 {
;  DWORD   ExceptionCode;
;  DWORD   ExceptionFlags;
;  DWORD64 ExceptionRecord;
;  DWORD64 ExceptionAddress;
;  DWORD   NumberParameters;
;  DWORD   __unusedAlignment;
;  DWORD64 ExceptionInformation[EXCEPTION_MAXIMUM_PARAMETERS]; //15
;} EXCEPTION_RECORD64;




@__exceptionStorage = thread_local global %shadow.standard..Exception* null



declare noalias i8* @calloc(i64, i64) nounwind
declare noalias i8* @malloc(i64) nounwind
declare void @free(i8*) nounwind
declare void @abort() noreturn nounwind
declare void @exit(i32) noreturn nounwind

%shadow.io..Console = type opaque
declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 
declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)
declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)

%eh.ThrowInfo = type { i32, i32, i32, i32 }
%eh.CatchableTypeArray = type { i32, [1 x i32] }
;declare dso_local void @_CxxThrowException(i8*, %eh.ThrowInfo*)

;typedef struct _EXCEPTION_RECORD {
;  DWORD                    ExceptionCode;
;  DWORD                    ExceptionFlags;
;  struct _EXCEPTION_RECORD *ExceptionRecord;
;  PVOID                    ExceptionAddress;
;  DWORD                    NumberParameters;
;  ULONG_PTR                ExceptionInformation[EXCEPTION_MAXIMUM_PARAMETERS];
;} EXCEPTION_RECORD;

%struct._EXCEPTION_RECORD = type {i32, i32, %struct._EXCEPTION_RECORD*, i8*, i32, [15 x i64]}

;declare i32 @_exceptionMethodshadow.test..ExceptionA(i8*, i8*)
;define i64 @__exceptionHashCode(i8* %object) {
;	call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int 128)
;	ret i64 3763554372 ; Shadow exception code
;}
define i32 @__exceptionFilter(i8* %records, i8* %payload, %shadow.standard..Class* %handlerClass ) {
	%asPointer = bitcast i8* %records to { %struct._EXCEPTION_RECORD*, i8* }*
	%recordRefRef = getelementptr inbounds { %struct._EXCEPTION_RECORD*, i8* }, { %struct._EXCEPTION_RECORD*, i8* }* %asPointer, i32 0, i32 0
	%recordRef = load %struct._EXCEPTION_RECORD*, %struct._EXCEPTION_RECORD** %recordRefRef
	
	%exCodeRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordRef, i32 0, i32 0
	%exCode = load i32, i32* %exCodeRef
	%codeMatches = icmp eq i32 %exCode, 3763554372 ; Shadow exception code
	br i1 %codeMatches, label %_match, label %_nomatch
_match:
	%exInfoRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordRef, i32 0, i32 5, i32 0
	%exInfo = load i64, i64* %exInfoRef
	%exInfoAsObj = inttoptr i64 %exInfo to %shadow.standard..Object*
	%classRef = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %exInfoAsObj, i32 0, i32 1
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%exceptionMatches = call i1 @shadow.standard..Class_MisSubtype_shadow.standard..Class(%shadow.standard..Class* %class, %shadow.standard..Class* %handlerClass)
	br i1 %exceptionMatches, label %_handle, label %_nomatch
_handle:
	;%payloadRefRef = load i64*, i64** %payload
	;%payloadOffset = getelementptr i64, i64* %payloadRefRef, i32 2
	;%payloadValue = load i64, i64* %payloadOffset
	;%payloadAsObj = inttoptr i64 %payloadValue to %shadow.standard..Object*
	;call void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %payloadAsObj)
	
	;%payloadValueRef = inttoptr i64 %payloadValueValue to i64*
	;%payloadValueValueValue = load i64, i64* %payloadValueRef
	;%payloadAsObj3 = inttoptr i64 %payloadValueValueValue to %shadow.standard..Object*
	;call void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %payloadAsObj3)
	%exInfoAsException = bitcast %shadow.standard..Object* %exInfoAsObj to %shadow.standard..Exception*
	store %shadow.standard..Exception* %exInfoAsException, %shadow.standard..Exception** @__exceptionStorage
	
	ret i32 1 ; EXCEPTION_EXECUTE_HANDLER
_nomatch:	
	ret i32 0 ; EXCEPTION_CONTINUE_SEARCH
	


	;%address = bitcast i8* %payload to %shadow.standard..Object*
	;call void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %address)
	

	;%recordPointerFirst = bitcast i8* %records to %struct._EXCEPTION_RECORD*
	;%exceptionRecordRef0 = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointerFirst, i32 0, i32 2
	;%recordPointer = load %struct._EXCEPTION_RECORD*, %struct._EXCEPTION_RECORD** %exceptionRecordRef0
	
	
	;%numberRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 0
	;%number = load i32, i32* %numberRef
	;call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int %number)
	
	;%numberRef2 = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 1
	;%number2 = load i32, i32* %numberRef2
	;call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int %number2)
	
	;%exceptionObjRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 3
	;%exceptionRef = bitcast i8** %exceptionObjRef to %shadow.standard..Object**
	;%exception = load %shadow.standard..Object*, %shadow.standard..Object** %exceptionRef
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %exception)
	
	;%numberRef3 = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 4
	;%number3 = load i32, i32* %numberRef3
	;call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int %number3)
	
	;%pointerRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 5, i32 0
	;%pointer = load i64, i64* %pointerRef
	;%pointerAsObj = inttoptr i64 %pointer to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %pointerAsObj)

	;call %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console* null) 
	;call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int 101)

	;%recordsAsObj = bitcast i8* %records to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %recordsAsObj)
	
	;%payloadAsObj = bitcast i8* %payload to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %payloadAsObj)
	
	;%payloadRef = bitcast i8* %payload to i8**
	;%payloadValue = load i8*, i8** %payloadRef
	;%payloadValueAsObj = bitcast i8* %payloadValue to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %payloadValueAsObj)
	
	;%payloadRef2 = getelementptr i8*, i8** %payloadRef, i32 1
	;%payloadValue2 = load i8*, i8** %payloadRef2
	;%payloadValueAsObj2 = bitcast i8* %payloadValue2 to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %payloadValueAsObj2)
	
	;%imageAsObj = bitcast i8* @__ImageBase to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %imageAsObj)
	;%exception = inttoptr i64 %number to %shadow.standard..Object*
		
	;%valueRef = getelementptr %eh.ThrowInfo, %eh.ThrowInfo* @__exceptionInfo, i32 0, i32 3
	;%value = load i32, i32* %valueRef
	;call %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console* null, %int %value)
	
	;%exceptionRecordRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %recordPointer, i32 0, i32 2
	;%exceptionRecord = load %struct._EXCEPTION_RECORD*, %struct._EXCEPTION_RECORD** %exceptionRecordRef
	;%exceptionRecordAsObj = bitcast %struct._EXCEPTION_RECORD* %exceptionRecord to %shadow.standard..Object*
	;call  void @shadow.io..Console_MprintAddress_shadow.standard..Object(%shadow.io..Console* null, %shadow.standard..Object* %exceptionRecordAsObj)

	;"Shadow\00\00"
	;0x836861646F770000

}

;@__exceptionArray = linkonce_odr unnamed_addr constant %eh.CatchableTypeArray { i32 1, [1 x i32] [i32 trunc (i64 sub nuw nsw (i64 ptrtoint (%shadow.standard..Class* @shadow.standard..Exception_class to i64), i64 ptrtoint (i8* @__ImageBase to i64)) to i32)] }, section ".xdata"
;@__exceptionInfo = linkonce_odr unnamed_addr constant %eh.ThrowInfo { i32 0,
;;i32 trunc (i64 sub nuw nsw (i64 ptrtoint (void (%"class.std::bad_cast"*)* @"??1bad_cast@std@@UEAA@XZ" to i64), i64 ptrtoint (i8* @__ImageBase to i64)) to i32),
;i32 0, ; seems to be 0 on some calls?
;i32 0,
;i32 trunc (i64 sub nuw nsw (i64 ptrtoint (%eh.CatchableTypeArray* @__exceptionArray to i64), i64 ptrtoint (i8* @__ImageBase to i64)) to i32)
;}, section ".xdata"

;@__ImageBase = external dso_local constant i8

;RaiseException(
;  DWORD           dwExceptionCode,
;  DWORD           dwExceptionFlags,
;  DWORD           nNumberOfArguments,
;  const ULONG_PTR *lpArguments

; Shadow exception code: 0x?SHD -> 0xE0534844 -> 3763554372
;(? refers to bits that should be set: 1110 for bits 31 30 29 28, 0000 for the next 4)
; 31 and 30 as 11 means error
; 29 as 1 means user defined
; 28 is reserved to be 0
declare void @RaiseException(i32, i32, i32, i64*) noreturn

define void @__shadow_throw(%shadow.standard..Object* %obj) cold noreturn {
entry:
	%variable = alloca i64
	%value = ptrtoint %shadow.standard..Object* %obj to i64
	store i64 %value, i64* %variable
	;%arg = bitcast i64* %variable to i8*
	; Shadow code, 0 for flags (means continuable exception), 1 argument, exception itself
	call void @RaiseException(i32 3763554372, i32 0, i32 1, i64* %variable) noreturn
	;call void @_CxxThrowException(i8* %arg, %eh.ThrowInfo* @__exceptionInfo) noreturn
	unreachable
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
    ;call void @abort() noreturn nounwind ; fix this eventually
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
	;call void @abort() noreturn nounwind ; fix this eventually
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

;define i32 @__shadow_personality_v0(%struct._EXCEPTION_RECORD* %record, i8* %frame, i8* %contextRecord, i8* %dispatcherContext) {
;_entry:
;	%codeRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %record, i32 0, i32 0
;	%code = load i32, i32* %codeRef	
;	%isShadowCode = icmp eq i32 %code, 5457988
;	br i1 %isShadowCode, label %_isShadow, label %_notShadow
;_isShadow:
;	%value = call i32 @__C_specific_handler(%struct._EXCEPTION_RECORD* %record, i8* %frame, i8* %contextRecord, i8* %dispatcherContext)
;	ret i32 %value
;_notShadow:
;	ret i32 0 ; EXCEPTION_CONTINUE_SEARCH 
;}
