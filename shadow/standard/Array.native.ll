; shadow.standard@Array native methods

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
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong} }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..ClassSet_methods = type opaque
%shadow.standard..ClassSet = type opaque

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class

%shadow.standard..Exception = type opaque
%shadow.standard..IndexOutOfBoundsException = type opaque
%shadow.standard..IndexOutOfBoundsException_methods = type opaque

@shadow.standard..IndexOutOfBoundsException_class = external constant %shadow.standard..Class
@shadow.standard..IndexOutOfBoundsException_methods = external constant %shadow.standard..IndexOutOfBoundsException_methods

%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} }

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..int_class = external constant %shadow.standard..Class
@shadow.standard..Array_class = external constant %shadow.standard..Class
@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods
@shadow.standard..ArrayNullable_class = external constant %shadow.standard..Class
@shadow.standard..ArrayNullable_methods = external constant %shadow.standard..ArrayNullable_methods
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class



declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %ulong %elements)
declare %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object*)
declare void @shadow.standard..Object_Mdestroy(%shadow.standard..Object*)
declare %shadow.standard..MethodTable* @shadow.standard..Class_MinterfaceData_shadow.standard..Class(%shadow.standard..Class* %class, %shadow.standard..Class* %interface)

declare %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Class_A_shadow.standard..MethodTable_boolean(%shadow.standard..ClassSet*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong }, %shadow.standard..MethodTable*, %boolean)
declare %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object*)

declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i32, i1)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)
declare %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Class_A(%shadow.standard..Class*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong })
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %arrayData) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array) nounwind


; it's not really a long there at the end, but we need something big enough for all the possibilities
%__primitive = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*, %long  }

@_genericSet = external global %shadow.standard..ClassSet*

; aliases used by ArrayNullable
@shadow.standard..ArrayNullable_Mcreate_shadow.standard..Object_A = alias %shadow.standard..Array* (%shadow.standard..Object*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}), %shadow.standard..Array* (%shadow.standard..Object*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong})* @shadow.standard..Array_Mcreate_shadow.standard..Object_A
@shadow.standard..ArrayNullable_Mcreate_long = alias %shadow.standard..Array* (%shadow.standard..Object*, %long), %shadow.standard..Array* (%shadow.standard..Object*, %long)* @shadow.standard..Array_Mcreate_long
@shadow.standard..ArrayNullable_Mdestroy = alias void (%shadow.standard..Array*), void (%shadow.standard..Array*)* @shadow.standard..Array_Mdestroy
@shadow.standard..ArrayNullable_MsizeLong = alias %long(%shadow.standard..Array*), %long(%shadow.standard..Array*)* @shadow.standard..Array_MsizeLong
@shadow.standard..ArrayNullable_Msubarray_long_long = alias %shadow.standard..Array* (%shadow.standard..Array*, %long, %long), %shadow.standard..Array* (%shadow.standard..Array*, %long, %long)* @shadow.standard..Array_Msubarray_long_long
@shadow.standard..ArrayNullable_Mindex_long_T = alias void (%shadow.standard..Array*, %long, %shadow.standard..Object*), void (%shadow.standard..Array*, %long, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_long_T

declare void @__shadow_throw(%shadow.standard..Object*) noreturn

;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*)
;declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)

define %shadow.standard..Array* @shadow.standard..Array_Mcreate_shadow.standard..Object_A(%shadow.standard..Object* %object, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data) {
	call %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object* %object)

	%array = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*
	%dataPointer = extractvalue  {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 0
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %dataPointer) nounwind

    %dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
    store {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef

	ret %shadow.standard..Array* %array
}

define %shadow.standard..Array* @shadow.standard..Array_Mcreate_long(%shadow.standard..Object* %object, %ulong %size) {
	call %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object* %object)

	%array = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*

	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*

	; get base class
	%classArrayRef = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 9
	%classArray = load {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong}* %classArrayRef
	%classArrayPointer = extractvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong} %classArray, 0
	%baseClassRef = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %classArrayPointer, i32 0, i32 1
	; since there's only one, we can simply load the pointer without offset
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef

	%arrayPointer = call {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %baseClass, %ulong %size)

	%newArray1 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } zeroinitializer, {%ulong, %shadow.standard..Object*}* %arrayPointer, 0
	%newArray2 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } %newArray1, %shadow.standard..Class* %baseClass, 1
	%newArray3 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } %newArray2, %ulong %size, 2

	; no need to increment ref on the array since it is "born" with a ref count of 1 that won't be decremented

	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
    store {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %newArray3, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef

	ret %shadow.standard..Array* %array
}

define void @shadow.standard..Array_Mdestroy(%shadow.standard..Array* %array) {
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	%null = icmp eq {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef, null
	br i1 %null, label %_destroyObject, label %_notNull
_notNull:
	%data = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef
	call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data) nounwind
	br label %_destroyObject
_destroyObject:
	;parent destroy
	%asObject = bitcast %shadow.standard..Array* %array to %shadow.standard..Object*
    call void @shadow.standard..Object_Mdestroy(%shadow.standard..Object* %asObject)
    ret void
}

define %shadow.standard..Object* @shadow.standard..Array_Mindex_long(%shadow.standard..Array* %object, %ulong %index ) {
	; get array data
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %object, i32 0, i32 3
	%data = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef
	%array = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 0

	; get base class
	%baseClass = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 1

	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %object, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*

	; get method table
	%methodTables = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 10
	%methodTablesArray = load {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong}* %methodTables
	%methodsTablesArrayPointer = extractvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %methodTablesArray, 0
	%methodTableRef = getelementptr {%ulong, %shadow.standard..MethodTable*}, {%ulong, %shadow.standard..MethodTable*}* %methodsTablesArrayPointer, i32 0, i32 1
	; since there's only one, we can simply load the pointer without offset
	%methodTable = load %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %methodTableRef

	%result = call %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, %ulong %index, %shadow.standard..Class* %baseClass, %shadow.standard..MethodTable* %methodTable, %boolean false)

	; increment ref by one (since all methods up their return value by 1)
	call void @__incrementRef(%shadow.standard..Object* %result) nounwind

	ret %shadow.standard..Object* %result
}

define %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, %ulong %index, %shadow.standard..Class* %class, %shadow.standard..MethodTable* %methods, %boolean %nullable) {
	%arrayData = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1

	; get generic class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2
	%notPrimitive = icmp eq i32 %primitiveFlag, 0

	; if flag does not contain 2 (primitive), proceed with array check
	br i1 %notPrimitive, label %_checkArray, label %_primitive
_checkArray:
	; get array class flag
	%arrayFlag = and i32 %flag, 8
	%notArray = icmp eq i32 %arrayFlag, 0

	; if flag does not contain 8, proceed with regular object
	br i1 %notArray, label %_checkInterface, label %_array

_checkInterface:
	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface

_interface:
	%interfaceRefBase = bitcast %shadow.standard..Object** %arrayData to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	%interfaceRef = getelementptr { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRefBase, %ulong %index
	%interface = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	%object = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %interface, 1
	ret %shadow.standard..Object* %object

_object:
	%elementRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %arrayData, %ulong %index
	%element = load %shadow.standard..Object*, %shadow.standard..Object** %elementRef
	ret %shadow.standard..Object* %element

_primitive:

	; deal with primitive type
	; create new wrapper object
	%methodTable.1 = bitcast %shadow.standard..MethodTable* %methods to %shadow.standard..Object_methods*
	%wrapper = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methodTable.1)

	; copy primitive value into new object
	%wrapperAsPrimitive = bitcast %shadow.standard..Object* %wrapper to %__primitive*
	%data = getelementptr inbounds %__primitive, %__primitive* %wrapperAsPrimitive, i32 0, i32 3
	%dataAsBytes = bitcast %long* %data to i8*

	%widthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %class)
	%width = zext %uint %widthInt to %ulong
	%offset = mul %ulong %index, %width

	%arrayAsBytes = bitcast %shadow.standard..Object** %arrayData to i8*
	%primitiveElement = getelementptr inbounds i8, i8* %arrayAsBytes, %ulong %offset
	; dangerous if we didn't get the offset into the primitive wrapper exactly right
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %dataAsBytes, i8* %primitiveElement, %ulong %width, i32 1, i1 0)
	ret %shadow.standard..Object* %wrapper

_array:
	; create new Array wrapper
	; ugh, have to create Array<T> class or ArrayNullable<T>
	; parent in array class is base type: i.e., String[] parent -> String
	%baseClassRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 6
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef

	br i1 %nullable, label %_null, label %_notNull
_null:
	%nullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..ArrayNullable_class, i32 0, i32 5
	%nullName = load %shadow.standard..String*, %shadow.standard..String** %nullNameRef
	%nullMethods = bitcast %shadow.standard..ArrayNullable_methods* @shadow.standard..ArrayNullable_methods to %shadow.standard..Object_methods*
	br label %_name
_notNull:
	%notNullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..Array_class , i32 0, i32 5
	%notNullName = load %shadow.standard..String*, %shadow.standard..String** %notNullNameRef
	%notNullMethods = bitcast %shadow.standard..Array_methods* @shadow.standard..Array_methods to %shadow.standard..Object_methods*
	br label %_name
_name:
	%arrayName = phi %shadow.standard..String* [ %nullName, %_null ], [ %notNullName, %_notNull ]
	%methodTable.2 = phi %shadow.standard..Object_methods* [%nullMethods, %_null ], [ %notNullMethods, %_notNull ]

	%parametersArray = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %ulong 1)
	%parameters = bitcast {%ulong, %shadow.standard..Object*}* %parametersArray to {%ulong, %shadow.standard..Class*}*
	%parametersRef = getelementptr inbounds {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %parameters, i32 0, i32 1
	store %shadow.standard..Class* %baseClass, %shadow.standard..Class** %parametersRef

	%uninitializedParameters1 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong } undef, {%ulong, %shadow.standard..Class*}* %parameters, 0
	%uninitializedParameters2 = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong } %uninitializedParameters1, %shadow.standard..Class* @shadow.standard..Class_class, 1
	%initializedParameters = insertvalue {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong } %uninitializedParameters2, %ulong 1, 2

	%className = call %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Class_A(%shadow.standard..Class* %class, %shadow.standard..String* %arrayName, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong } %initializedParameters)
	%classSet = load %shadow.standard..ClassSet*, %shadow.standard..ClassSet** @_genericSet
	%arrayClass = call %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Class_A_shadow.standard..MethodTable_boolean(%shadow.standard..ClassSet* %classSet, %shadow.standard..String* %className, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong } %initializedParameters, %shadow.standard..MethodTable* %methods, %boolean %nullable)
	%arrayClassAsClass = bitcast %shadow.standard..GenericClass* %arrayClass to %shadow.standard..Class*
	%arrayWrapper = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %arrayClassAsClass, %shadow.standard..Object_methods* %methodTable.2)

	%arrayAsArrays = bitcast %shadow.standard..Object** %arrayData to {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong}*
	%arrayElementRef = getelementptr inbounds {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong}* %arrayAsArrays, %ulong %index

	%arrayElement = load {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong}* %arrayElementRef

	; initialize Array<T> object
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_shadow.standard..Object_A(%shadow.standard..Object* %arrayWrapper, {{%ulong, %shadow.standard..Object*}*,%shadow.standard..Class*, %ulong} %arrayElement)

	; Object that just got initialized
	ret %shadow.standard..Object* %arrayWrapper
}

define void @shadow.standard..Array_Mindex_long_T(%shadow.standard..Array*, %ulong, %shadow.standard..Object*) {
	; get array data
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 3
	%data = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef
	%array = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 0
	%baseClass = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 1
	call void @__arrayStore({%ulong, %shadow.standard..Object*}* %array, %ulong %1, %shadow.standard..Object* %2, %shadow.standard..Class* %baseClass)
	ret void
}

define void @__arrayStore({%ulong, %shadow.standard..Object*}* %array, %ulong %index, %shadow.standard..Object* %object, %shadow.standard..Class* %baseClass) {
	%arrayData = getelementptr inbounds {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32  0, i32 1

	; get generic class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	br i1 %notPrimitive, label %_checkArray, label %_primitive

_checkArray:
	; check for array class
	%arrayFlag = and i32 %flag, 8
	%notArray = icmp eq i32 %arrayFlag, 0
	br i1 %notArray, label %_checkInterface, label %_array

_checkInterface:
	; if interface or object, increment reference
	call void @__incrementRef(%shadow.standard..Object* %object) nounwind

	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface

_interface:
	%interfaceRefBase = bitcast %shadow.standard..Object** %arrayData to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	%interfaceRef = getelementptr { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRefBase, %ulong %index
	%oldInterace = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	%oldInterfaceObject = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %oldInterace, 1
	call void @__decrementRef(%shadow.standard..Object* %oldInterfaceObject) nounwind

	%null = icmp eq %shadow.standard..Object* %object, null
	br i1 %null, label %_null, label %_notNull
_null:
	store { %shadow.standard..Object_methods*, %shadow.standard..Object* } zeroinitializer, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	ret void
_notNull:
	%objectClassRef = getelementptr %shadow.standard..Object, %shadow.standard..Object* %object, i32 0, i32 1
	%objectClass = load %shadow.standard..Class*, %shadow.standard..Class** %objectClassRef
	%methodTable = call %shadow.standard..MethodTable* @shadow.standard..Class_MinterfaceData_shadow.standard..Class(%shadow.standard..Class* %objectClass, %shadow.standard..Class* %baseClass)
	%methods = bitcast %shadow.standard..MethodTable* %methodTable to %shadow.standard..Object_methods*
	%interface1 = insertvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } zeroinitializer, %shadow.standard..Object_methods* %methods, 0
	%interface2 = insertvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %interface1, %shadow.standard..Object* %object, 1
	store { %shadow.standard..Object_methods*, %shadow.standard..Object* } %interface2, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	ret void

_object:
	%objectRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %arrayData, %ulong %index
	%old = load %shadow.standard..Object*, %shadow.standard..Object** %objectRef
	call void @__decrementRef(%shadow.standard..Object* %old) nounwind
	store %shadow.standard..Object* %object, %shadow.standard..Object** %objectRef
	ret void

_primitive:
	%primitiveWidthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%primitiveWidth = zext %int %primitiveWidthInt to %ulong
	%offset = mul %ulong %index, %primitiveWidth
	%arrayAsBytes1 = bitcast %shadow.standard..Object** %arrayData to i8*
	%primitiveElement = getelementptr i8, i8* %arrayAsBytes1, %ulong %offset
	%primitiveWrapper = bitcast %shadow.standard..Object* %object to %__primitive*
	%primitiveData = getelementptr inbounds %__primitive, %__primitive* %primitiveWrapper, i32 0, i32 3
	%primitiveDataAsBytes = bitcast %long* %primitiveData to i8*
	; dangerous if we didn't get the offset into the primitive wrapper exactly right
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %primitiveElement, i8* %primitiveDataAsBytes, i64 %primitiveWidth, i32 1, i1 0)
	ret void

_array:
	; get the location inside the current Array<T>
	%arrayRefBase = bitcast %shadow.standard..Object** %arrayData to {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }*
	%arrayRef = getelementptr {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %arrayRefBase, %ulong %index
	%oldArray = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %arrayRef

	; get the array data from the input Array<T> (which it must be, since anything else isn't going to have an ArrayType as a parameter)
	%input = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*
	%inputArrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 3
	%inputArray = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %inputArrayRef
	%inputData = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } %inputArray, 0

	; increment reference count on new array
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %inputData) nounwind
	; decrement reference count on old array
	call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %oldArray) nounwind

	; store new array into the old array location
	store {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } %inputArray, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %arrayRef
	ret void
}

define noalias %shadow.standard..Array* @shadow.standard..Array_Msubarray_long_long(%shadow.standard..Array* %array, %long %first, %long %second) {
	; check sizes first
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	%data = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef
  %size = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 2

	%test1 = icmp ule %ulong %second, %size
	br i1 %test1, label %_secondLessThanSize, label %throw
_secondLessThanSize:
	%test2 = icmp ule %ulong %first, %second
	br i1 %test2, label %_firstLessThanSecond, label %throw
_firstLessThanSecond:


	; get class from original Array<T>
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef

	; get method table from original Array<T>
	%methodsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 2
	%methods = load %shadow.standard..Array_methods*, %shadow.standard..Array_methods** %methodsRef
	%objMethods = bitcast %shadow.standard..Array_methods* %methods to %shadow.standard..Object_methods*

	%arrayData = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 0
	%baseClass = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 1

	; allocate new array object
	%arrayObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %objMethods)

	; get difference between indexes
	%difference = sub %long %second, %first

	; cast to array pointer
	%arrayDataPointer = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %arrayData, i32 0, i32 1
	%arrayDataAsChar = bitcast %shadow.standard..Object** %arrayDataPointer to i8*

	;allocate new real array
	%newArrayData = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %baseClass, %ulong %difference)
	%newArrayDataPointer = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %newArrayData, i32 0, i32 1

	%newArrayAsChar = bitcast %shadow.standard..Object** %newArrayDataPointer to i8*

	; get type parameter width
	%widthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%width = zext %int %widthInt to %ulong

	; multiply starting point by width
	%offset = mul %ulong %first, %width
	%arrayDataAtOffset = getelementptr i8, i8* %arrayDataAsChar, %ulong %offset
	%total = mul %ulong %difference, %width



	;copy data
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %newArrayAsChar, i8* %arrayDataAtOffset, %ulong %total, i32 1, i1 0)



	%newArray1 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} zeroinitializer, {%ulong, %shadow.standard..Object*}* %newArrayData, 0
	%newArray2 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %newArray1, %shadow.standard..Class* %baseClass, 1
	%newArray3 = insertvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %newArray2, %ulong %difference, 2

	; get base class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2

	; if not primitive, increment array elements
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	br i1 %notPrimitive, label %_incrementArray, label %_done
_incrementArray:
	%interfaceFlag = and i32 %flag, 1
	%isInterface = icmp ne i32 %interfaceFlag, 0
	br i1 %isInterface, label %_loopStartInterface, label %_checkArray
_checkArray:
	%arrayFlag = and i32 %flag, 8
	%isArray = icmp ne i32 %interfaceFlag, 0
	br i1 %isArray, label %_loopStartArray, label %_loopTestObj

	; array of interfaces
_loopStartInterface:
	%newArrayAsInterface = bitcast %shadow.standard..Object** %newArrayDataPointer to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	br label %_loopTestInterface
_loopTestInterface:
	%i1 = phi %ulong [ 0, %_loopStartInterface ], [%incremented1, %_loopBodyInterface]
	%less1 = icmp ult %ulong %i1, %difference
	br i1 %less1, label %_loopBodyInterface, label %_done
_loopBodyInterface:
	%elementAsInterfaceRef = getelementptr { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %newArrayAsInterface, %ulong %i1
	%elementAsInterface = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %elementAsInterfaceRef
	%elementAsInterfaceObj = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %elementAsInterface, 1
	call void @__incrementRef(%shadow.standard..Object* %elementAsInterfaceObj) nounwind
	%incremented1 = add %ulong %i1, 1
	br label %_loopTestInterface

	; array of arrays
_loopStartArray:
	%newArrayAsArray = bitcast %shadow.standard..Object** %newArrayDataPointer to {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }*
	br label %_loopTestArray
_loopTestArray:
	%i2 = phi %ulong [ 0, %_loopStartArray ], [%incremented2, %_loopBodyArray]
	%less2 = icmp ult %ulong %i2, %difference
	br i1 %less2, label %_loopBodyArray, label %_done
_loopBodyArray:
	%elementAsArrayRef = getelementptr {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %newArrayAsArray, %ulong %i2
	%elementAsArray = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong }* %elementAsArrayRef
	%elementAsArrayData = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong } %elementAsArray, 0
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %elementAsArrayData) nounwind
	%incremented2 = add %ulong %i2, 1
	br label %_loopTestArray

	; array of objects
_loopTestObj:
	%i3 = phi %ulong [ 0, %_checkArray ], [%incremented3, %_loopBodyObj]
	%less3 = icmp ult %ulong %i3, %difference
	br i1 %less3, label %_loopBodyObj, label %_done
_loopBodyObj:
	%elementAsObjRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %newArrayDataPointer, %ulong %i3
	%elementAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %elementAsObjRef
	call void @__incrementRef(%shadow.standard..Object* %elementAsObj) nounwind
	%incremented3 = add %ulong %i3, 1
	br label %_loopTestObj

_done:
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_shadow.standard..Object_A(%shadow.standard..Object* %arrayObj, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %newArray3)
	%newArrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %initializedArray, i32 0, i32 3
	%newArray = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %newArrayRef
	; decrement new array, since it just got incremented when used in Array create (and was already incremented when it was born)
	call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %newArray) nounwind
	ret %shadow.standard..Array* %initializedArray
throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn
	unreachable
}

define %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array*) alwaysinline nounwind {
    %2 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 3
    %3 = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %2
    %4 = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %3, 2
    ret %long %4
}