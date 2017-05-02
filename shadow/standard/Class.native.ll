; shadow.standard@Class native methods

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

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, void (%shadow.standard..Object*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong} }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods*, {{%ulong, %byte}*, %shadow.standard..Class*,%ulong}, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..OutOfMemoryException_methods = type opaque
%shadow.standard..OutOfMemoryException = type { %ulong, %shadow.standard..Class*, %shadow.standard..OutOfMemoryException_methods* , %shadow.standard..String* }

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..byte_class = external constant %shadow.standard..Class
@shadow.standard..Exception_methods = external constant %shadow.standard..Exception_methods
@shadow.standard..Exception_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_class = external constant %shadow.standard..Class
@shadow.standard..OutOfMemoryException_methods = external constant %shadow.standard..OutOfMemoryException_methods
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class

declare void @free(i8*) nounwind
declare %shadow.standard..OutOfMemoryException* @shadow.standard..OutOfMemoryException_Mcreate(%shadow.standard..Object*)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)
declare void @shadow.standard..Object_Mdestroy(%shadow.standard..Object*)


;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 
;declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)


define %int @shadow.standard..Class_MarraySize(%shadow.standard..Class*) alwaysinline nounwind readnone {
		%2 = ptrtoint {%shadow.standard..Class*, %shadow.standard..Object*, %ulong}* getelementptr ({%shadow.standard..Class*, %shadow.standard..Object*, %ulong}, {%shadow.standard..Class*, %shadow.standard..Object*, %ulong}* null, i32 1) to i32
		ret %int %2
}

define %int @shadow.standard..Class_MpointerSize(%shadow.standard..Class*) alwaysinline nounwind readnone {
		%2 = ptrtoint %shadow.standard..Object** getelementptr (%shadow.standard..Object*, %shadow.standard..Object** null, i32 1) to i32
		ret %int %2
}


define void @__decrementRef(%shadow.standard..Object* %object) nounwind {	
	%isNull = icmp eq %shadow.standard..Object* %object, null
	br i1 %isNull, label %_exit, label %_check
_check:
	; get reference count
	%countRef = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 0
	%count = load %ulong, %ulong* %countRef
	; check if reference count is not int max (marks non-gc objects)  (unsigned -1 is int max)
	%isGC = icmp ne %ulong %count, -1
	br i1 %isGC, label %_checkPassed, label %_exit
_checkPassed:
	; atomically decrease reference count by one and get old value
	%oldCount = atomicrmw sub %ulong* %countRef, %ulong 1 acquire
	; if old count was 1, call destroy and deallocate (prevents double free in multithreaded situations)
	%free = icmp eq %ulong %oldCount, 1
	br i1 %free, label %_freeLabel, label %_exit
_freeLabel:	
	; call destroy before free	
	%methodsRef = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 2
    %methods = load %shadow.standard..Object_methods*, %shadow.standard..Object_methods** %methodsRef
    %destroyRef = getelementptr inbounds %shadow.standard..Object_methods, %shadow.standard..Object_methods* %methods, i32 0, i32 1
    %destroy = load void (%shadow.standard..Object*)*, void (%shadow.standard..Object*)** %destroyRef

    call void %destroy(%shadow.standard..Object* %object)
	
	; free	
	%address = bitcast %shadow.standard..Object* %object to i8*
	tail call void @free(i8* %address) nounwind
	ret void
_exit:	
	ret void
}

define void @__incrementRef(%shadow.standard..Object* %object) nounwind {
	
	%isNull = icmp eq %shadow.standard..Object* %object, null
	br i1 %isNull, label %_exit, label %_check
_check:
	; get reference count
	%countRef = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 0
	%count = load %ulong, %ulong* %countRef
	; check if reference count is not ulong max (marks non-gc objects)  (unsigned -1 is ulong max)
	%isGC = icmp ne %ulong %count, -1
	br i1 %isGC, label %_checkPassed, label %_exit
_checkPassed:
	; atomically increase reference count by one
	atomicrmw add %ulong* %countRef, %ulong 1 acquire
	ret void
_exit:		
	ret void
}

define void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %arrayData) nounwind {	
	%isNull = icmp eq {%ulong, %shadow.standard..Object*}* %arrayData, null
	br i1 %isNull, label %_exit, label %_check
_check:
	%countRef = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %arrayData, i32 0, i32 0
	%count = load %ulong, %ulong* %countRef
	; check if reference count is not ulong max (marks non-gc objects)  (unsigned -1 is ulong max)
	%isGC = icmp ne %ulong %count, -1
	br i1 %isGC, label %_checkPassed, label %_exit
_checkPassed:
	; atomically increase reference count by one
	atomicrmw add %ulong* %countRef, %ulong 1 acquire
	ret void
_exit:		
	ret void
}

define void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array) nounwind {	
	%countAndArray = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array, 0
	%arrayNull = icmp eq {%ulong, %shadow.standard..Object*}* %countAndArray, null
	br i1 %arrayNull, label %_exit, label %_check	
_check:		
	%countRef = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %countAndArray, i32 0, i32 0
	%count = load %ulong, %ulong* %countRef
	; check if reference count is not ulong max (marks non-gc objects)  (unsigned -1 is ulong max)
	%isGC = icmp ne %ulong %count, -1
	br i1 %isGC, label %_checkPassed, label %_exit
_checkPassed:	
	; get reference count (stored before array)
	; decrease by one and get old value
	%oldCount = atomicrmw sub %ulong* %countRef, %ulong 1 acquire
	; if old value was 1, call destroy and deallocate (prevents double free in multithreaded situations)
	%free = icmp eq %ulong %oldCount, 1
	br i1 %free, label %_checkLength, label %_exit
_checkLength:	
	%size = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array, 2
	%check.length = icmp ne i64 %size, 0
	br i1 %check.length, label %_getClass, label %_freeArray
_getClass:	
	; here's where things change from objects
	%base = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array, 1	
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %base, i32 0, i32 7	
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	%notMethodTable = icmp ne %shadow.standard..Class* %base, @shadow.standard..MethodTable_class
	%notPrimitiveOrMethodTable = and i1 %notPrimitive, %notMethodTable	
	; if primitive or method table elements, no elements to decrement
	br i1 %notPrimitiveOrMethodTable, label %_checkFlag, label %_freeArray	

	; see if it's an array of arrays
_checkFlag:
	%arrayData = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %countAndArray, i32 0, i32 1
	%arrayFlag = and i32 %flag, 8	
	%notArray = icmp eq i32 %arrayFlag, 0
	br i1 %notArray, label %_checkInterface, label %_array
	
_checkInterface:
	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface	
	
	; array of objects to free
	; loop through and decrement
_object:
	%i.3 = phi i64 [0, %_checkInterface], [%i.4, %_object]
	%element.1.ref = getelementptr inbounds %shadow.standard..Object*, %shadow.standard..Object** %arrayData, i64 %i.3	
	%element.1 = load %shadow.standard..Object*, %shadow.standard..Object** %element.1.ref		
	call void @__decrementRef(%shadow.standard..Object* %element.1)
	%i.4 = add i64 %i.3, 1
	%check.i4 = icmp ult i64 %i.4, %size
	br i1 %check.i4, label %_object, label %_freeArray

	; array of arrays to free
_array:
	; width of each element in bytes	
	%arrayElements = bitcast %shadow.standard..Object** %arrayData to {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}*
	br label %_arrayLoop
	
	; loop through and decrement array elements (which are also arrays)
_arrayLoop:
	%i.5 = phi i64 [0, %_array], [%i.6, %_arrayLoop]	
	%element.2.ref = getelementptr inbounds {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %arrayElements, i64 %i.5
	%element.2 = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %element.2.ref
	call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %element.2)
	%i.6 = add i64 %i.5, 1
	%check.i6 = icmp ult i64 %i.6, %size
	br i1 %check.i6, label %_arrayLoop, label %_freeArray
		
	; array of interfaces to free
_interface:
	%elements = bitcast %shadow.standard..Object** %arrayData to {%shadow.standard..MethodTable*, %shadow.standard..Object*}*
	br label %_interfaceLoop
_interfaceLoop:
	%i.7 = phi i64 [0, %_interface], [%i.8, %_interfaceLoop]
	%element.3.ref = getelementptr inbounds {%shadow.standard..MethodTable*, %shadow.standard..Object*}, {%shadow.standard..MethodTable*, %shadow.standard..Object*}* %elements, i64 %i.7	
	%element.3 = load {%shadow.standard..MethodTable*, %shadow.standard..Object*}, {%shadow.standard..MethodTable*, %shadow.standard..Object*}* %element.3.ref		
	%elementObject = extractvalue {%shadow.standard..MethodTable*, %shadow.standard..Object*} %element.3, 1
	call void @__decrementRef(%shadow.standard..Object* %elementObject)
	%i.8 = add i64 %i.7, 1
	%check.i8 = icmp ult i64 %i.8, %size
	br i1 %check.i8, label %_interfaceLoop, label %_freeArray

_freeArray:
	%address = bitcast %ulong* %countRef to i8*
	tail call void @free(i8* %address) nounwind	
	ret void
_exit:	
	ret void
}