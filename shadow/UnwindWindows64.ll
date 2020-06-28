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


;typedef struct _EXCEPTION_RECORD {
;  DWORD                    ExceptionCode;
;  DWORD                    ExceptionFlags;
;  struct _EXCEPTION_RECORD *ExceptionRecord;
;  PVOID                    ExceptionAddress;
;  DWORD                    NumberParameters;
;  ULONG_PTR                ExceptionInformation[EXCEPTION_MAXIMUM_PARAMETERS];
;} EXCEPTION_RECORD;

%struct._EXCEPTION_RECORD = type {i32, i32, %struct._EXCEPTION_RECORD*, i8*, i32, i32, [15 x i64]}

;RaiseException(
;  DWORD           dwExceptionCode,
;  DWORD           dwExceptionFlags,
;  DWORD           nNumberOfArguments,
;  const ULONG_PTR *lpArguments

; Shadow exception code: 0x0SHD -> 0x00534844 -> 5457988 (since some upper bits are used by the system)
declare void @RaiseException(i32, i32, i32, i8*) noreturn
declare i32 @__C_specific_handler(%struct._EXCEPTION_RECORD*, i8*, i8*, i8*) nounwind

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


define void @__shadow_throw(%shadow.standard..Object* %obj) cold noreturn {
entry:
	%arg = bitcast %shadow.standard..Object* %obj to i8*
	; Shadow code, 0 for flags (means continuable exception), 1 argument, exception itself
	tail call void @RaiseException(i32 5457988, i32 0, i32 1, i8* %arg) noreturn
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

define i32 @__shadow_personality_v0(%struct._EXCEPTION_RECORD* %record, i8* %frame, i8* %contextRecord, i8* %dispatcherContext) {
_entry:
	%codeRef = getelementptr inbounds %struct._EXCEPTION_RECORD, %struct._EXCEPTION_RECORD* %record, i32 0, i32 0
	%code = load i32, i32* %codeRef	
	%isShadowCode = icmp eq i32 %code, 5457988
	br i1 %isShadowCode, label %_isShadow, label %_notShadow
_isShadow:
	%value = call i32 @__C_specific_handler(%struct._EXCEPTION_RECORD* %record, i8* %frame, i8* %contextRecord, i8* %dispatcherContext)
	ret i32 %value
_notShadow:
	ret i32 0 ; EXCEPTION_CONTINUE_SEARCH 
}
