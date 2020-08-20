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

declare void @__shadow_throw(%shadow.standard..Object*) noreturn
declare %shadow.standard..Exception* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

; standard definitions
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, void (%shadow.standard..Object*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
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

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
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
;declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 
;declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)
;declare void @shadow.io..Console_MdebugPrint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;@byte_A_class = external constant %shadow.standard..GenericClass
;@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods

define %int @shadow.standard..Class_MpointerSize(%shadow.standard..Class*) alwaysinline nounwind readnone {
		%2 = ptrtoint %shadow.standard..Object** getelementptr (%shadow.standard..Object*, %shadow.standard..Object** null, i32 1) to i32
		ret %int %2
}

define void @__decrementRef(%shadow.standard..Object* %object) nounwind {
	%isNull = icmp eq %shadow.standard..Object* %object, null
	br i1 %isNull, label %_exit, label %_check
_check:
	;call void @shadow.io..Console_MdebugPrint_shadow.standard..String(%shadow.io..Console* null, %shadow.standard..String* @_string0)
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
	;call void @shadow.io..Console_MdebugPrint_shadow.standard..String(%shadow.io..Console* null, %shadow.standard..String* @_string1)
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
