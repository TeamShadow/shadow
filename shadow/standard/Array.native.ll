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
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
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
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods*, %long }

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..Array_class = external constant %shadow.standard..Class
@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods
@shadow.standard..ArrayNullable_class = external constant %shadow.standard..Class
@shadow.standard..ArrayNullable_methods = external constant %shadow.standard..ArrayNullable_methods
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class

@shadow.standard..byte_methods = external constant %shadow.standard..Object_methods
@shadow.standard..int_methods = external constant %shadow.standard..Object_methods
@shadow.standard..double_methods = external constant %shadow.standard..Object_methods
@shadow.standard..long_methods = external constant %shadow.standard..Object_methods
@shadow.standard..boolean_methods = external constant %shadow.standard..Object_methods
@shadow.standard..float_methods = external constant %shadow.standard..Object_methods
@shadow.standard..code_methods = external constant %shadow.standard..Object_methods
@shadow.standard..short_methods = external constant %shadow.standard..Object_methods
@shadow.standard..ubyte_methods = external constant %shadow.standard..Object_methods
@shadow.standard..uint_methods = external constant %shadow.standard..Object_methods
@shadow.standard..ulong_methods = external constant %shadow.standard..Object_methods
@shadow.standard..ushort_methods = external constant %shadow.standard..Object_methods

@shadow.standard..byte_class = external constant %shadow.standard..Class
@shadow.standard..int_class = external constant %shadow.standard..Class
@shadow.standard..double_class = external constant %shadow.standard..Class
@shadow.standard..long_class = external constant %shadow.standard..Class
@shadow.standard..boolean_class = external constant %shadow.standard..Class
@shadow.standard..float_class = external constant %shadow.standard..Class
@shadow.standard..code_class = external constant %shadow.standard..Class
@shadow.standard..short_class = external constant %shadow.standard..Class
@shadow.standard..ubyte_class = external constant %shadow.standard..Class
@shadow.standard..uint_class = external constant %shadow.standard..Class
@shadow.standard..ulong_class = external constant %shadow.standard..Class
@shadow.standard..ushort_class = external constant %shadow.standard..Class

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %longElements, %boolean %nullable)
declare %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object*)
declare void @shadow.standard..Object_Mdestroy(%shadow.standard..Object*)
declare %shadow.standard..MethodTable* @shadow.standard..Class_MinterfaceData_shadow.standard..Class(%shadow.standard..Class* %class, %shadow.standard..Class* %interface)

declare %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Class_A_shadow.standard..MethodTable_boolean(%shadow.standard..ClassSet*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong }, %shadow.standard..MethodTable*, %boolean)
declare %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object*)
declare %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate_long(%shadow.standard..Object*, %long)

declare void @llvm.memcpy.p0i8.p0i8.i64(i8*, i8*, i64, i32, i1)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)
declare %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Class_A(%shadow.standard..Class*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong })
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind

; it's not really a long there at the end, but we need something big enough for all the possibilities
%__primitive = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*, %long  }

@_genericSet = external global %shadow.standard..ClassSet*

; aliases used by ArrayNullable
@shadow.standard..ArrayNullable_Mcreate = alias %shadow.standard..Array* (%shadow.standard..Object*), %shadow.standard..Array* (%shadow.standard..Object*)* @shadow.standard..Array_Mcreate
@shadow.standard..ArrayNullable_Mdestroy = alias void (%shadow.standard..Array*), void (%shadow.standard..Array*)* @shadow.standard..Array_Mdestroy
@shadow.standard..ArrayNullable_MisNullable = alias %boolean(%shadow.standard..Array*), %boolean(%shadow.standard..Array*)* @shadow.standard..Array_MisNullable
@shadow.standard..ArrayNullable_MsizeLong = alias %long(%shadow.standard..Array*), %long(%shadow.standard..Array*)* @shadow.standard..Array_MsizeLong
@shadow.standard..ArrayNullable_Msubarray_long_long = alias %shadow.standard..Array* (%shadow.standard..Array*, %long, %long), %shadow.standard..Array* (%shadow.standard..Array*, %long, %long)* @shadow.standard..Array_Msubarray_long_long
@shadow.standard..ArrayNullable_Mindex_long_TT = alias void (%shadow.standard..Array*, %long, %shadow.standard..Object*), void (%shadow.standard..Array*, %long, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_long_TT
@shadow.standard..ArrayNullable_Mindex_long = alias %shadow.standard..Object* (%shadow.standard..Array*, %long), %shadow.standard..Object* (%shadow.standard..Array*, %long)* @shadow.standard..Array_Mindex_long

declare void @__shadow_throw(%shadow.standard..Object*) noreturn

;%shadow.io..Console = type opaque
;declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
;declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*)
;declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)

; Never expected to be called
define %shadow.standard..Array* @shadow.standard..Array_Mcreate(%shadow.standard..Object* %object) {
	%array = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*
	ret %shadow.standard..Array* %array
}

define void @shadow.standard..Array_Mdestroy(%shadow.standard..Array* %array) {
	%size = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array)
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	%baseClassRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 6
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %classRef	

	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7	
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	%notMethodTable = icmp ne %shadow.standard..Class* %baseClass, @shadow.standard..MethodTable_class
	%notPrimitiveOrMethodTable = and i1 %notPrimitive, %notMethodTable	
	; if primitive or method table elements, no elements to decrement
	br i1 %notPrimitiveOrMethodTable, label %_checkInterface, label %_exit	

_checkInterface:
	; skip past array meta data
	%arrayOffset = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%arrayData = bitcast %shadow.standard..Array* %arrayOffset to %shadow.standard..Object**	
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
	br i1 %check.i4, label %_object, label %_exit

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
	br i1 %check.i8, label %_interfaceLoop, label %_exit
_exit:	
	ret void
}

define %shadow.standard..Object* @shadow.standard..Array_Mindex_long(%shadow.standard..Array* %array, %ulong %index ) {
	%size = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array)
	%inRange = icmp ult %long %index, %size
	br i1 %inRange, label %_inRange, label %_throw
_inRange:		
	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef

	; get base class
	%baseClassRef =  getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 6
	%baseClass =  load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef	
	
	; get array data	
	%arrayData = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1

	; get base class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2
	%notPrimitive = icmp eq i32 %primitiveFlag, 0

	; if flag does not contain 2 (primitive), proceed with interface check
	br i1 %notPrimitive, label %_checkInterface, label %_checkByte

_checkInterface:
	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface

_interface:
	%interfaceRefBase = bitcast %shadow.standard..Array* %arrayData to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	%interfaceRef = getelementptr { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRefBase, %ulong %index
	%interface = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	%interfaceObject = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %interface, 1
	
	; increment ref by one (since all methods up their return value by 1)
	call void @__incrementRef(%shadow.standard..Object* %interfaceObject) nounwind		
	ret %shadow.standard..Object* %interfaceObject
	
_object:
	%objectRefBase = bitcast %shadow.standard..Array* %arrayData to %shadow.standard..Object**
	%objectRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %objectRefBase, %ulong %index
	%object = load %shadow.standard..Object*, %shadow.standard..Object** %objectRef
	%notMethodTable = icmp ne %shadow.standard..Class* %baseClass, @shadow.standard..MethodTable_class	
	; if primitive or method table elements, no elements to decrement
	br i1 %notMethodTable, label %_increment, label %_returnObject		
_increment:
	; increment ref by one (since all methods up their return value by 1)
	call void @__incrementRef(%shadow.standard..Object* %object) nounwind	
	br label %_returnObject
_returnObject:	
	ret %shadow.standard..Object* %object

	; deal with primitive type
	; create new wrapper object
	; check byte first for method table
	; this order is based on assumed likelihood of type (byte first because of Strings)
_checkByte:	
	%isByte = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..byte_class
	br i1 %isByte, label %_foundPrimitive, label %_checkInt
_checkInt:
	%isInt = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..int_class
	br i1 %isInt, label %_foundPrimitive, label %_checkDouble
_checkDouble:
	%isDouble = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..double_class
	br i1 %isDouble, label %_foundPrimitive, label %_checkLong
_checkLong:
	%isLong = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..long_class
	br i1 %isLong, label %_foundPrimitive, label %_checkBoolean
_checkBoolean:
	%isBoolean = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..boolean_class
	br i1 %isBoolean, label %_foundPrimitive, label %_checkFloat
_checkFloat:
	%isFloat = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..float_class
	br i1 %isFloat, label %_foundPrimitive, label %_checkCode
_checkCode:
	%isCode = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..code_class
	br i1 %isCode, label %_foundPrimitive, label %_checkShort
_checkShort:	
	%isShort = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..short_class	
	br i1 %isShort, label %_foundPrimitive, label %_checkUByte
_checkUByte:	
	%isUByte = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..ubyte_class
	br i1 %isUByte, label %_foundPrimitive, label %_checkUInt
_checkUInt:
	%isUInt = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..uint_class
	br i1 %isUInt, label %_foundPrimitive, label %_checkULong
_checkULong:
	%isULong = icmp eq %shadow.standard..Class* %baseClass, @shadow.standard..ulong_class
	br i1 %isULong, label %_foundPrimitive, label %_checkUShort
_checkUShort:
	br label %_foundPrimitive
_foundPrimitive:
	%methodTable = phi %shadow.standard..Object_methods* [@shadow.standard..byte_methods, %_checkByte], [@shadow.standard..int_methods, %_checkInt], [@shadow.standard..double_methods, %_checkDouble], [@shadow.standard..long_methods, %_checkLong], [@shadow.standard..boolean_methods, %_checkBoolean], [@shadow.standard..float_methods, %_checkFloat], [@shadow.standard..code_methods, %_checkCode], [@shadow.standard..short_methods, %_checkShort], [@shadow.standard..ubyte_methods, %_checkUByte], [@shadow.standard..uint_methods, %_checkUInt], [@shadow.standard..ulong_methods, %_checkULong], [@shadow.standard..ushort_methods, %_checkUShort]
	%wrapper = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %baseClass, %shadow.standard..Object_methods* %methodTable)
	
	; copy primitive value into new object
	%wrapperAsPrimitive = bitcast %shadow.standard..Object* %wrapper to %__primitive*
	%data = getelementptr inbounds %__primitive, %__primitive* %wrapperAsPrimitive, i32 0, i32 3
	%dataAsBytes = bitcast %long* %data to i8*

	%widthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%width = zext %uint %widthInt to %ulong
	%offset = mul %ulong %index, %width
		
	%arrayAsBytes = bitcast %shadow.standard..Array* %arrayData to i8*
	%primitiveElement = getelementptr inbounds i8, i8* %arrayAsBytes, %ulong %offset
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %dataAsBytes, i8* %primitiveElement, %ulong %width, i32 1, i1 0)
	; "born" with a reference count of 1, no increment needed
	ret %shadow.standard..Object* %wrapper
	
_throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate_long(%shadow.standard..Object* %ex.obj, %long %index)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn
	unreachable	
}

; %shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long }

define void @shadow.standard..Array_Mindex_long_TT(%shadow.standard..Array* %array, %ulong %index, %shadow.standard..Object* %object) {
	%size = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array)
	%inRange = icmp ult %long %index, %size
	br i1 %inRange, label %_inRange, label %_throw
_inRange:		
	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef

	; get base class
	%baseClassRef =  getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 6
	%baseClass =  load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef	
	
	; get array data	
	%arrayData = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	
	; get class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	br i1 %notPrimitive, label %_checkInterface, label %_primitive

_checkInterface:
	; if interface or object, increment reference
	call void @__incrementRef(%shadow.standard..Object* %object) nounwind

	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface

_interface:
	%interfaceRefBase = bitcast %shadow.standard..Array* %arrayData to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
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
	%objectRefBase = bitcast %shadow.standard..Array* %arrayData to %shadow.standard..Object**
	%objectRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %objectRefBase, %ulong %index
	%old = load %shadow.standard..Object*, %shadow.standard..Object** %objectRef
	call void @__decrementRef(%shadow.standard..Object* %old) nounwind
	store %shadow.standard..Object* %object, %shadow.standard..Object** %objectRef
	ret void

_primitive:
	%widthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%width = zext %int %widthInt to %ulong
	%offset = mul %ulong %index, %width
	%arrayAsBytes = bitcast %shadow.standard..Array* %arrayData to i8*
	%primitiveElement = getelementptr i8, i8* %arrayAsBytes, %ulong %offset
	%primitiveWrapper = bitcast %shadow.standard..Object* %object to %__primitive*
	%primitiveData = getelementptr inbounds %__primitive, %__primitive* %primitiveWrapper, i32 0, i32 3
	%primitiveDataAsBytes = bitcast %long* %primitiveData to i8*
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %primitiveElement, i8* %primitiveDataAsBytes, i64 %width, i32 1, i1 0)
	ret void

_throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate_long(%shadow.standard..Object* %ex.obj, %long %index)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn
	unreachable	
}

define noalias %shadow.standard..Array* @shadow.standard..Array_Msubarray_long_long(%shadow.standard..Array* %array, %long %first, %long %second) {
	; check sizes first
	%size = call %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array)
	%test1 = icmp ule %ulong %second, %size
	br i1 %test1, label %_secondLessThanSize, label %_throw
_secondLessThanSize:
	%test2 = icmp ule %ulong %first, %second
	br i1 %test2, label %_firstLessThanSecond, label %_throw
_firstLessThanSecond:
	; get class from original Array<T>
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
	; get difference between indexes
	%difference = sub %long %second, %first
	%isNull = call %boolean @shadow.standard..Array_MisNullable(%shadow.standard..Array* %array)
	
	; allocate new array
	%newArray = call noalias %shadow.standard..Array* @__allocateArray(%shadow.standard..Class* %class, %ulong %difference, %boolean %isNull )

	; pointer to old data
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 1
	%dataRefAsChar = bitcast %shadow.standard..Array* %dataRef to i8*

	; pointer to new data
	%newArrayDataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %newArray, i32 1
	%newArrayAsChar = bitcast %shadow.standard..Array* %newArrayDataRef to i8*
	
	; get element width
	%baseClassRef =  getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 6
	%baseClass =  load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef	
	%widthInt = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%width = zext %int %widthInt to %ulong

	; multiply starting point by width
	%offset = mul %ulong %first, %width
	%arrayDataAtOffset = getelementptr i8, i8* %dataRefAsChar, %ulong %offset
	%total = mul %ulong %difference, %width
	
	;copy data
	call void @llvm.memcpy.p0i8.p0i8.i64(i8* %newArrayAsChar, i8* %arrayDataAtOffset, %ulong %total, i32 1, i1 0)

	; get base class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	
	; if not primitive or method table, increment array elements
	%notPrimitive = icmp eq i32 %primitiveFlag, 0
	%notMethodTable = icmp ne %shadow.standard..Class* %baseClass, @shadow.standard..MethodTable_class
	%notPrimitiveOrMethodTable = and i1 %notPrimitive, %notMethodTable	
	br i1 %notPrimitiveOrMethodTable, label %_incrementArray, label %_done
	
_incrementArray:
	%interfaceFlag = and i32 %flag, 1
	%isInterface = icmp ne i32 %interfaceFlag, 0
	br i1 %isInterface, label %_loopStartInterface, label %_loopStartObj

	; array of interfaces
_loopStartInterface:
	%newArrayAsInterface = bitcast i8* %newArrayAsChar to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
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

	; array of objects (includes arrays)
_loopStartObj:
	%newArrayAsObj = bitcast i8* %newArrayAsChar to %shadow.standard..Object**
	br label %_loopTestObj
_loopTestObj:
	%i2 = phi %ulong [ 0, %_loopStartObj ], [%incremented2, %_loopBodyObj]
	%less3 = icmp ult %ulong %i2, %difference
	br i1 %less3, label %_loopBodyObj, label %_done
_loopBodyObj:
	%elementAsObjRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %newArrayAsObj, %ulong %i2
	%elementAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %elementAsObjRef
	call void @__incrementRef(%shadow.standard..Object* %elementAsObj) nounwind
	%incremented2 = add %ulong %i2, 1
	br label %_loopTestObj
	
_done:
	ret %shadow.standard..Array* %newArray
_throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn
	unreachable
}

; %shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, %long }
define %long @shadow.standard..Array_MsizeLong(%shadow.standard..Array* %array) alwaysinline nounwind {
    %sizeRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
    %size = load %long, %long* %sizeRef
	; 2^63 - 1, used to remove MSB (which marks null or not)
    %trueSize = and %long %size, 9223372036854775807
    ret %long %trueSize
}

define %boolean @shadow.standard..Array_MisNullable(%shadow.standard..Array* %array) alwaysinline nounwind {
    %sizeRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
    %size = load %long, %long* %sizeRef
	; 2^63, used to test if MSB is 1
    %msb = and %long %size, 9223372036854775808
	%isNull = icmp ne %long %msb, 0
    ret %boolean %isNull
}

