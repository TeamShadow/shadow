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
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %int, %int, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] } }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%ulong, %byte}*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

%shadow.standard..ClassSet_methods = type opaque
%shadow.standard..ClassSet = type { %ulong, %shadow.standard..Class*, %shadow.standard..ClassSet_methods* , {{%ulong, %shadow.standard..ClassSet.Node*}*, [1 x %int] }, %float, %int, %int, %int }
%shadow.standard..ClassSet.Node_methods = type opaque
%shadow.standard..ClassSet.Node = type { %ulong, %shadow.standard..Class*, %shadow.standard..ClassSet.Node_methods* , %shadow.standard..ClassSet*, %shadow.standard..ClassSet.Node*, %shadow.standard..Class*, %int }

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

%shadow.standard..Exception_methods = type opaque
%shadow.standard..Exception = type { %ulong, %shadow.standard..Class*, %shadow.standard..Exception_methods* , %shadow.standard..String* }
%shadow.standard..IndexOutOfBoundsException_methods = type opaque
%shadow.standard..IndexOutOfBoundsException = type { %ulong, %shadow.standard..Class*, %shadow.standard..IndexOutOfBoundsException_methods* , %shadow.standard..String* }

@shadow.standard..IndexOutOfBoundsException_class = external constant %shadow.standard..Class
@shadow.standard..IndexOutOfBoundsException_methods = external constant %shadow.standard..IndexOutOfBoundsException_methods

%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %shadow.standard..Object*, {{%ulong, %int}*, [1 x %int] } }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %shadow.standard..Object*, {{%ulong, %int}*, [1 x %int] } }
%shadow.standard..IteratorNullable_methods = type opaque

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..int_class = external constant %shadow.standard..Class
@shadow.standard..Array_class = external constant %shadow.standard..Class
@shadow.standard..Array_methods = external constant %shadow.standard..Array_methods
@shadow.standard..ArrayNullable_class = external constant %shadow.standard..Class
@shadow.standard..ArrayNullable_methods = external constant %shadow.standard..ArrayNullable_methods
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class

%shadow.io..Console = type opaque

declare %int @shadow.standard..Array_Msize(%shadow.standard..Array*)

declare noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methods)
declare noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %class, %uint %elements)
declare %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object*)
declare void @shadow.standard..Object_Mdestroy(%shadow.standard..Object*)

declare %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Class_A1_shadow.standard..MethodTable_boolean(%shadow.standard..ClassSet*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %shadow.standard..MethodTable*, %boolean)
declare %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @shadow.standard..Class_Mwidth(%shadow.standard..Class*)
declare %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Class_A1_int_int(%shadow.standard..Class*, %shadow.standard..String*, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, %int, %int)
declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__incrementRefArray({%ulong, %shadow.standard..Object*}*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, i32}* %arrayPtr, i32 %dims, %shadow.standard..Class* %base) nounwind

@_genericSet = external global %shadow.standard..ClassSet*

; aliases used by ArrayNullable
@shadow.standard..ArrayNullable_Mcreate_int_A1 = alias %shadow.standard..Array* (%shadow.standard..Object*, { {%ulong, %int}*, [1 x %int] }), %shadow.standard..Array* (%shadow.standard..Object*, { {%ulong, %int}*, [1 x %int] })* @shadow.standard..Array_Mcreate_int_A1
@shadow.standard..ArrayNullable_Mdimensions = alias %int (%shadow.standard..Array*), %int (%shadow.standard..Array*)* @shadow.standard..Array_Mdimensions
@shadow.standard..ArrayNullable_Msubarray_int_int = alias %shadow.standard..Array* (%shadow.standard..Array*, %int, %int), %shadow.standard..Array* (%shadow.standard..Array*, %int, %int)* @shadow.standard..Array_Msubarray_int_int
@shadow.standard..ArrayNullable_Mindex_int_A1 = alias %shadow.standard..Object* (%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] }), %shadow.standard..Object* (%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] })* @shadow.standard..Array_Mindex_int_A1
@shadow.standard..ArrayNullable_Mindex_int_A1_T = alias void (%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] }, %shadow.standard..Object*), void (%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] }, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_int_A1_T
@shadow.standard..ArrayNullable_Mindex_int_T = alias void (%shadow.standard..Array*, %int, %shadow.standard..Object*), void (%shadow.standard..Array*, %int, %shadow.standard..Object*)* @shadow.standard..Array_Mindex_int_T
@shadow.standard..ArrayNullable_MgetBaseClass = alias %shadow.standard..Class* (%shadow.standard..Array*), %shadow.standard..Class* (%shadow.standard..Array*)* @shadow.standard..Array_MgetBaseClass
@shadow.standard..ArrayNullable_Mdestroy = alias void (%shadow.standard..Array*), void (%shadow.standard..Array*)* @shadow.standard..Array_Mdestroy

declare void @__shadow_throw(%shadow.standard..Object*) noreturn

declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..Object(%shadow.io..Console*, %shadow.standard..Object*)
declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 
declare %shadow.io..Console* @shadow.io..Console_MdebugPrint_int(%shadow.io..Console*, %int)


define i32 @__computeIndex(%shadow.standard..Array* %array, { {%ulong,i32}*, [1 x i32] } %indexes) alwaysinline {	
	%lengthsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 4	
	%lengths = load { {%ulong,i32}*, [1 x i32] }, { {%ulong,i32}*, [1 x i32] }* %lengthsRef
	%indexPointer = extractvalue { {%ulong,i32}*, [1 x i32] } %indexes, 0
	%indexDims = extractvalue { {%ulong,i32}*, [1 x i32] } %indexes, 1, 0
	%lengthsPointer = extractvalue { {%ulong,i32}*, [1 x i32] } %lengths, 0
	%lengthsDims = extractvalue { {%ulong,i32}*, [1 x i32] } %lengths, 1, 0
	%dimsEqual = icmp eq i32 %indexDims, %lengthsDims
	br i1 %dimsEqual, label %_equalDims, label %throw
_equalDims:
	%firstLengthRef = getelementptr {%ulong,i32}, {%ulong,i32}* %lengthsPointer, i32 0, i32 1
	%firstIndexRef = getelementptr {%ulong,i32}, {%ulong,i32}* %indexPointer, i32 0, i32 1
	br label %_loopTest
_loopTest:
	%currentLengthRef = phi i32* [ %firstLengthRef,  %_equalDims], [ %nextLengthRef, %_loopUpdate ]
	%currentIndexRef = phi i32* [ %firstIndexRef,  %_equalDims], [ %nextIndexRef, %_loopUpdate ]
	%count = phi i32 [ 0, %_equalDims ], [ %next, %_loopUpdate ]
	%total = phi i32 [ 0, %_equalDims ], [ %nextTotal, %_loopUpdate ]
	%lastDimension = phi i32 [ 1, %_equalDims ], [ %currentLength, %_loopUpdate ]
	%test = icmp ult i32 %count, %indexDims
	br i1 %test, label %_loopBody, label %_done
_loopBody:
	%currentLength = load i32, i32* %currentLengthRef
	%currentIndex = load i32, i32* %currentIndexRef
	%less = icmp ult i32 %currentIndex, %currentLength
	br i1 %less, label %_loopUpdate, label %throw	
_loopUpdate:
	%nextLengthRef = getelementptr i32, i32* %currentLengthRef, i32 1
	%nextIndexRef = getelementptr i32, i32* %currentIndexRef, i32 1
	%next = add i32 %count, 1
	%totalScaled = mul i32 %total, %lastDimension	
	%nextTotal = add i32 %totalScaled, %currentIndex
	br label %_loopTest
_done:
	ret i32 %total
throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn	
	unreachable
}


define %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* %object, {{%ulong, %int}*, [1 x %int] } %lengths, %shadow.standard..Object* %data) {
	call %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object* %object)   
	
	%array = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*       
    %lengthsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 4
	
	; no need to increment ref on lengths, since they were allocated specifically for this create (with a reference count of 1)
    store {{%ulong, %int}*, [1 x %int] } %lengths, {{%ulong, %int}*, [1 x %int] }* %lengthsRef
    %dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	%dataAsArray = bitcast %shadow.standard..Object* %data to {%ulong, %shadow.standard..Object*}*
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %dataAsArray) nounwind
	store %shadow.standard..Object* %data, %shadow.standard..Object** %dataRef
	ret %shadow.standard..Array* %array
}

define %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1(%shadow.standard..Object* %object, { {%ulong, %int}*, [1 x %int] }  %lengths) {
    ; first call parent Object create
	call %shadow.standard..Object* @shadow.standard..Object_Mcreate(%shadow.standard..Object* %object)
	
	; cast to array
	%array = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*
	
	; next increment reference count on lengths	
	%lengthsPointer = extractvalue { {%ulong, %int}*, [1 x %int] } %lengths, 0	
	%lengthsAsObjArray = bitcast {%ulong, %int}* %lengthsPointer to {%ulong, %shadow.standard..Object*}*
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %lengthsAsObjArray) nounwind
	
	; store lengths
	%lengthsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 4
	store { {%ulong, %int}*, [1 x %int] } %lengths, { {%ulong, %int}*, [1 x %int] }* %lengthsRef
	
	; get base class	
	%baseClass = call %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array* %array)
	
	; get total size of array (only works because lengths is already stored)
	%size = call %int @shadow.standard..Array_Msize(%shadow.standard..Array* %array)

	; allocate space
	%data = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %baseClass, %uint %size)
	%dataAsObj = bitcast {%ulong, %shadow.standard..Object*}* %data to %shadow.standard..Object*
	
	;store data
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	store %shadow.standard..Object* %dataAsObj, %shadow.standard..Object** %dataRef

    ret %shadow.standard..Array* %array
}

define void @shadow.standard..Array_Mdestroy(%shadow.standard..Array* %array) {
_start:
	; really awkward!  we have to reassemble a proper array in order to decrement it!
	%fakeArray = alloca {{%ulong, %shadow.standard..Object*}*, %uint}
		
	; retrieve total dimensions before cleaning up lengths
	%dims = call %int @shadow.standard..Array_Mdimensions(%shadow.standard..Array* %array)
	
	%lengths = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 4

	%lengthsArray = getelementptr inbounds {{%ulong, %int}*, [1 x %int] }, {{%ulong, %int}*, [1 x %int] }* %lengths, i32 0, i32 0
	%sizesRef = load {%ulong, %int}*, {%ulong, %int}** %lengthsArray
	%sizes = getelementptr inbounds {%ulong, %int}, {%ulong, %int}* %sizesRef, i32 0, i32 1
	br label %_lengthLoop
	; loop multiplies sizes to get full length
_lengthLoop:	
	%i.1 = phi i32 [0, %_start], [%i.2, %_lengthLoop]
	%length.1 = phi i32 [1, %_start], [%length.2, %_lengthLoop]
	%nextSizeRef = getelementptr inbounds i32, i32* %sizes, i32 %i.1
	%nextSize = load i32, i32* %nextSizeRef
	%length.2 = mul i32 %length.1, %nextSize
	%i.2 = add i32 %i.1, 1
	%check.i2 = icmp ult i32 %i.2, %dims
	br i1 %check.i2, label %_lengthLoop, label %_done	
	; shouldn't get this far if the array has zero length...but just to be safe
_done:
	; put single length into fake array
	
	%fakeArrayLength = getelementptr {{%ulong, %shadow.standard..Object*}*, %uint}, {{%ulong, %shadow.standard..Object*}*, %uint}* %fakeArray, i32 0, i32 1
	store i32 %length.2, i32* %fakeArrayLength
		
	; put data into fake array
	%dataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
    %data = load %shadow.standard..Object*, %shadow.standard..Object** %dataRef
    %dataAsArray = bitcast %shadow.standard..Object* %data to {%ulong, %shadow.standard..Object*}*
	
	%fakeArrayData = getelementptr {{%ulong, %shadow.standard..Object*}*, %uint}, {{%ulong, %shadow.standard..Object*}*, %uint}* %fakeArray, i32 0, i32 0
	store {%ulong, %shadow.standard..Object*}* %dataAsArray, {%ulong, %shadow.standard..Object*}** %fakeArrayData
		
	%baseClass = call %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array* %array)
		
	; because we already loaded up the lengths, we act like it has a dimension of 1
    call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %uint}* %fakeArray, i32 1, %shadow.standard..Class* %baseClass) nounwind
    
    
	; clean up lengths	
	%lengthsForDecrement = bitcast {{%ulong, %int}*, [1 x %int] }* %lengths to {{%ulong, %shadow.standard..Object*}*, %uint}*
    call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %uint}* %lengthsForDecrement, i32 1, %shadow.standard..Class* @shadow.standard..int_class) nounwind
    	
	;parent destroy
	%asObject = bitcast %shadow.standard..Array* %array to %shadow.standard..Object*
    call void @shadow.standard..Object_Mdestroy(%shadow.standard..Object* %asObject)
    ret void
}


define %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array* %array) {    
    ; get base class	
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
    %genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*
	%typeParameters = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 9	
	%baseClassArray = load {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }* %typeParameters
	%baseClassArrayPointer = extractvalue {{%ulong, %shadow.standard..Class*}*, [1 x %int] } %baseClassArray, 0
	%baseClassRef = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %baseClassArrayPointer, i32 0, i32 1
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef
    ret %shadow.standard..Class* %baseClass
}

define %shadow.standard..Object* @shadow.standard..Array_Mindex_int(%shadow.standard..Array* %object, i32 %index ) {
	; get array data
	%arrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %object, i32 0, i32 3
	%arrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %arrayRef	
	%array = bitcast %shadow.standard..Object* %arrayAsObj to {%ulong, %shadow.standard..Object*}*

	; get array class
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %object, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef
    
	; get base class
	%genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*
	%typeParameters = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 9	
	%baseClassArray = load {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }* %typeParameters
	%baseClassArrayPointer = extractvalue {{%ulong, %shadow.standard..Class*}*, [1 x %int] } %baseClassArray, 0
	%baseClassRef = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %baseClassArrayPointer, i32 0, i32 1
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef	
	
	; get method table
	%methodTables = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 10	
	%methodTablesArray = load {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] }* %methodTables
	%methodsTablesArrayPointer = extractvalue {{%ulong, %shadow.standard..MethodTable*}*, [1 x %int] } %methodTablesArray, 0
	%methodTableRef = getelementptr {%ulong, %shadow.standard..MethodTable*}, {%ulong, %shadow.standard..MethodTable*}* %methodsTablesArrayPointer, i32 0, i32 1
	%methodTable = load %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %methodTableRef
	
	%result = call %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, i32 %index, %shadow.standard..Class* %baseClass, %shadow.standard..MethodTable* %methodTable, %boolean false)
	ret %shadow.standard..Object* %result
}

define %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, i32 %index, %shadow.standard..Class* %class, %shadow.standard..MethodTable* %methods, %boolean %nullable) {

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

	%elementRefBase = getelementptr inbounds {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1

	%interfaceFlag = and i32 %flag, 1
	%notInterface = icmp eq i32 %interfaceFlag, 0
	br i1 %notInterface, label %_object, label %_interface
	
_interface:	
	%interfaceRefBase = bitcast %shadow.standard..Object** %elementRefBase to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	%interfaceRef = getelementptr { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRefBase, i32 %index
	%interface = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %interfaceRef
	%object = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %interface, 1
	ret %shadow.standard..Object* %object	

_object:		
	
	%elementRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %elementRefBase, i32 %index
	%element = load %shadow.standard..Object*, %shadow.standard..Object** %elementRef
	ret %shadow.standard..Object* %element	

_primitive:

	; deal with primitive type
	; create new wrapper object
	%methodTable.1 = bitcast %shadow.standard..MethodTable* %methods to %shadow.standard..Object_methods*
	%wrapper = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %methodTable.1)
	
	; copy primitive value into new object
	%data = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %wrapper, i32 1
	%dataAsBytes = bitcast %shadow.standard..Object* %data to i8*
	
		
	%width = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %class)	
	%offset = mul i32 %index, %width
	%arrayData = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1	
	%arrayAsBytes = bitcast %shadow.standard..Object** %arrayData to i8*
	%primitiveElement = getelementptr inbounds i8, i8* %arrayAsBytes, i32 %offset
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %dataAsBytes, i8* %primitiveElement, i32 %width, i32 1, i1 0)
	ret %shadow.standard..Object* %wrapper

_array:	
	; create new Array wrapper
	; ugh, have to create Array<T> class or ArrayNullable<T>
	; parent in array class is base type: i.e., String[] parent -> String
	%baseClassRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 4
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef
	
	br i1 %nullable, label %_null, label %_notNull
_null:
	%nullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..ArrayNullable_class, i32 0, i32 3
	%nullName = load %shadow.standard..String*, %shadow.standard..String** %nullNameRef
	%nullMethods = bitcast %shadow.standard..ArrayNullable_methods* @shadow.standard..ArrayNullable_methods to %shadow.standard..Object_methods*
	br label %_name	
_notNull:
	%notNullNameRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* @shadow.standard..Array_class , i32 0, i32 3
	%notNullName = load %shadow.standard..String*, %shadow.standard..String** %notNullNameRef
	%notNullMethods = bitcast %shadow.standard..Array_methods* @shadow.standard..Array_methods to %shadow.standard..Object_methods*
	br label %_name
_name:
	%arrayName = phi %shadow.standard..String* [ %nullName, %_null ], [ %notNullName, %_notNull ]
	%methodTable.2 = phi %shadow.standard..Object_methods* [%nullMethods, %_null ], [ %notNullMethods, %_notNull ]

	%parametersArray = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..Class_class, %uint 1)
	%parameters = bitcast {%ulong, %shadow.standard..Object*}* %parametersArray to {%ulong, %shadow.standard..Class*}*	
	%parametersRef = getelementptr inbounds {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %parameters, i32 0, i32 1
	store %shadow.standard..Class* %baseClass, %shadow.standard..Class** %parametersRef
		
	%uninitializedParameters = insertvalue { {%ulong, %shadow.standard..Class*}*, [1 x i32] } undef, {%ulong, %shadow.standard..Class*}* %parameters, 0
	%initializedParameters = insertvalue { {%ulong, %shadow.standard..Class*}*, [1 x i32] } %uninitializedParameters, i32 1, 1, 0
		
	%className = call %shadow.standard..String* @shadow.standard..Class_MmakeName_shadow.standard..String_shadow.standard..Class_A1_int_int(%shadow.standard..Class* %class, %shadow.standard..String* %arrayName, { {%ulong, %shadow.standard..Class*}*, [1 x i32] } %initializedParameters, %int 0, %int 1)
	%classSet = load %shadow.standard..ClassSet*, %shadow.standard..ClassSet** @_genericSet
	%arrayClass = call %shadow.standard..GenericClass* @shadow.standard..ClassSet_MgetGenericArray_shadow.standard..String_shadow.standard..Class_A1_shadow.standard..MethodTable_boolean(%shadow.standard..ClassSet* %classSet, %shadow.standard..String* %className, { {%ulong, %shadow.standard..Class*}*, [1 x i32] } %initializedParameters, %shadow.standard..MethodTable* %methods, %boolean %nullable)
	%arrayClassAsClass = bitcast %shadow.standard..GenericClass* %arrayClass to %shadow.standard..Class*
	%arrayWrapper = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %arrayClassAsClass, %shadow.standard..Object_methods* %methodTable.2)
	
	; get size member of array class, which is equal to dimensions
	%dimensionsRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %class, i32 0, i32 8
	%dimensions = load i32, i32* %dimensionsRef
	
	; allocate new array for dimensions
	%dimensionsArrayPointer = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..int_class, i32 %dimensions)
	%dimensionsArray = bitcast {%ulong, %shadow.standard..Object*}* %dimensionsArrayPointer to {%ulong, i32}*
	
	; get element width (which is an array)
	%arrayWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %class)	
	
	%arrayOffset = mul i32 %index, %arrayWidth	
	%arrayData2 = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1
	%arrayAsBytes2 = bitcast %shadow.standard..Object** %arrayData2 to i8*
	
	; get offset in bytes
	%arrayElement = getelementptr inbounds i8, i8* %arrayAsBytes2, i32 %arrayOffset
	
	; cast to {%ulong, %shadow.standard..Object*}**
	%arrayElementAsArray = bitcast i8* %arrayElement to {{%ulong, %shadow.standard..Object*}*, %uint}*
	%arrayDataRef = getelementptr {{%ulong, %shadow.standard..Object*}*, %uint}, {{%ulong, %shadow.standard..Object*}*, %uint}* %arrayElementAsArray, i32 0, i32 0
	
	%arrayAsPointer = load {%ulong, %shadow.standard..Object*}*, {%ulong, %shadow.standard..Object*}** %arrayDataRef
	%arrayDataAsObj = bitcast {%ulong, %shadow.standard..Object*}* %arrayAsPointer to %shadow.standard..Object*
		
	; skip past data pointer to dimensions
	%arraySizes = getelementptr {{%ulong, %shadow.standard..Object*}*, %uint}, {{%ulong, %shadow.standard..Object*}*, %uint}* %arrayElementAsArray, i32 0, i32 1	
	
	; copy dimensions into allocated space
	%dimensionsArrayData = getelementptr  {%ulong, i32}, {%ulong, i32}* %dimensionsArray, i32 0, i32 1	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %dimensionsArrayData, i32* %arraySizes, i32 %dimensions, i32 0, i1 0)
	
	; make lengths array
	%lengthsArray1 = insertvalue { {%ulong, i32}*, [1 x i32] } undef, {%ulong, i32}* %dimensionsArray, 0
	%lengthsArray2 = insertvalue { {%ulong, i32}*, [1 x i32] } %lengthsArray1, i32 %dimensions, 1, 0
	
	; initialize Array<T> object
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* %arrayWrapper, { {%ulong, i32}*, [1 x i32] } %lengthsArray2, %shadow.standard..Object* %arrayDataAsObj)
	
	; Object that just got initialized
	ret %shadow.standard..Object* %arrayWrapper
}

define %shadow.standard..Object* @shadow.standard..Array_Mindex_int_A1(%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] }) alwaysinline {
	%3 = call i32 @__computeIndex(%shadow.standard..Array* %0, { {%ulong, i32}*, [1 x i32] } %1)	
	%4 = call %shadow.standard..Object* @shadow.standard..Array_Mindex_int(%shadow.standard..Array* %0, i32 %3)
	ret %shadow.standard..Object* %4
}

define void @shadow.standard..Array_Mindex_int_T(%shadow.standard..Array*, i32, %shadow.standard..Object*) {
	%baseClass = call %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array* %0)
		
	%arrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 3
	%arrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %arrayRef
	%array = bitcast %shadow.standard..Object* %arrayAsObj to {%ulong, %shadow.standard..Object*}*
	
	call void @__arrayStore({%ulong, %shadow.standard..Object*}* %array, i32 %1, %shadow.standard..Object* %2, %shadow.standard..Class* %baseClass)
	ret void
}

define void @__arrayStore({%ulong, %shadow.standard..Object*}* %array, i32 %index, %shadow.standard..Object* %object, %shadow.standard..Class* %baseClass) {
	
	; TODO: Make this work for storing interfaces as well!  (Will need to reconstruct the interface methods)
	
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
	br i1 %notArray, label %_object, label %_array

_object:
	call void @__incrementRef(%shadow.standard..Object* %object) nounwind 
	%elementRefBase = getelementptr inbounds {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32  0, i32 1
	%elementRef = getelementptr %shadow.standard..Object*, %shadow.standard..Object** %elementRefBase, i32 %index
	
	%old = load %shadow.standard..Object*, %shadow.standard..Object** %elementRef
	call void @__decrementRef(%shadow.standard..Object* %old) nounwind
	store %shadow.standard..Object* %object, %shadow.standard..Object** %elementRef
	ret void

_primitive:
	%primitiveWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)
	%offset = mul i32 %index, %primitiveWidth	
	%arrayPointer1 = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1
	%arrayAsBytes1 = bitcast %shadow.standard..Object** %arrayPointer1 to i8*
	%primitiveElement = getelementptr i8, i8* %arrayAsBytes1, i32 %offset
	%primitiveWrapper = getelementptr inbounds %shadow.standard..Object, %shadow.standard..Object* %object, i32 1
	%primitiveWrapperAsBytes = bitcast %shadow.standard..Object* %primitiveWrapper to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %primitiveElement, i8* %primitiveWrapperAsBytes, i32 %primitiveWidth, i32 1, i1 0)
	ret void

_array:	
	; get the location inside the current Array<T>
	%arrayWidth = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)	
	%arrayOffset = mul i32 %index, %arrayWidth	
	%arrayPointer2 = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %array, i32 0, i32 1
	%arrayAsBytes2 = bitcast %shadow.standard..Object** %arrayPointer2 to i8*
	%arrayElement = getelementptr i8, i8* %arrayAsBytes2, i32 %arrayOffset
		
	; get the array data from the input Array<T> (which it must be, since anything else isnt' going to have an ArrayType as a parameter)
	%input = bitcast %shadow.standard..Object* %object to %shadow.standard..Array*
	%inputArray = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 3
	%inputArrayAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %inputArray
	%inputArrayPointer = bitcast %shadow.standard..Object* %inputArrayAsObj to {%ulong, %shadow.standard..Object*}*
				
	; increment reference count on new array	
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %inputArrayPointer) nounwind
	
	; get dimensions from new array (which must match the old one)
	%dimensionsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 4, i32 1, i32 0
	%dimensions = load i32, i32* %dimensionsRef

	; decrement reference count on old array
	%oldArrayElement = bitcast i8* %arrayElement to {{%ulong, %shadow.standard..Object*}*, i32}*
	; needs to know base class of the array, which we get from the new array
	%base = call %shadow.standard..Class* @shadow.standard..Array_MgetBaseClass(%shadow.standard..Array* %input)
	call void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, i32}* %oldArrayElement, i32 %dimensions, %shadow.standard..Class* %base) nounwind
		
	; store it into the array
	%arrayPointer = getelementptr {{%ulong, %shadow.standard..Object*}*, i32}, {{%ulong, %shadow.standard..Object*}*, i32}* %oldArrayElement, i32 0, i32 0
	store {%ulong, %shadow.standard..Object*}* %inputArrayPointer, {%ulong, %shadow.standard..Object*}** %arrayPointer	
	
	; get pointer to input lengths (from Array<T>)
	%inputLengthsArrayRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %input, i32 0, i32 4
	%inputLengthsArray = load {{%ulong, i32}*, [1 x i32]}, {{%ulong, i32}*, [1 x i32]}* %inputLengthsArrayRef
	%inputLengthsPointer = extractvalue {{%ulong, i32}*, [1 x i32]} %inputLengthsArray, 0
	%inputLengthsRef = getelementptr {%ulong, i32}, {%ulong, i32}* %inputLengthsPointer, i32 0, i32 1	
	
	
	; get pointer to length storage location
	%oldLengths = getelementptr inbounds {{%ulong, %shadow.standard..Object*}*, i32}, {{%ulong, %shadow.standard..Object*}*, i32}* %oldArrayElement, i32 0, i32 1
	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %oldLengths, i32* %inputLengthsRef, i32 %dimensions, i32 0, i1 0)	
	ret void
}

define void @shadow.standard..Array_Mindex_int_A1_T(%shadow.standard..Array*, { {%ulong, i32}*, [1 x i32] }, %shadow.standard..Object*) alwaysinline {
	%4 = call i32 @__computeIndex(%shadow.standard..Array* %0, { {%ulong, i32}*, [1 x i32] } %1)	
	call void @shadow.standard..Array_Mindex_int_T(%shadow.standard..Array* %0, i32 %4, %shadow.standard..Object* %2)
	ret void
}

define noalias %shadow.standard..Array* @shadow.standard..Array_Msubarray_int_int(%shadow.standard..Array* %array, i32 %first, i32 %second) {
	
	; check sizes first
	%size = call %int @shadow.standard..Array_Msize(%shadow.standard..Array* %array)
	
	%test1 = icmp ule i32 %second, %size
	br i1 %test1, label %_secondLessThanSize, label %throw
_secondLessThanSize:
	%test2 = icmp ule i32 %first, %second
	br i1 %test2, label %_firstLessThanSecond, label %throw
_firstLessThanSecond:

	; get class from original array
	%classRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 1
	%class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef		
	
	; get method table from original array
	%methodsRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 2		
	%methods = load %shadow.standard..Array_methods*, %shadow.standard..Array_methods** %methodsRef
	%objMethods = bitcast %shadow.standard..Array_methods* %methods to %shadow.standard..Object_methods*
	
	; allocate new array object
	%arrayObj = call noalias %shadow.standard..Object* @__allocate(%shadow.standard..Class* %class, %shadow.standard..Object_methods* %objMethods)
	
	; get base class
	%genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*
	%typeParameters = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 9	
	%baseClassArray = load {{%ulong, %shadow.standard..Class*}*, [1 x %int] }, {{%ulong, %shadow.standard..Class*}*, [1 x %int] }* %typeParameters
	%baseClassArrayPointer = extractvalue {{%ulong, %shadow.standard..Class*}*, [1 x %int] } %baseClassArray, 0
	%baseClassRef = getelementptr {%ulong, %shadow.standard..Class*}, {%ulong, %shadow.standard..Class*}* %baseClassArrayPointer, i32 0, i32 1
	%baseClass = load %shadow.standard..Class*, %shadow.standard..Class** %baseClassRef	
	
	; create array for dimensions (only a single element, since the subarray will always be 1D)
	%dimensionsPointer = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* @shadow.standard..int_class, i32 1)
	%dimensions = bitcast {%ulong, %shadow.standard..Object*}* %dimensionsPointer to {%ulong, i32}*
	
	%dimensionRef = getelementptr {%ulong, i32}, {%ulong, i32}* %dimensions, i32 0, i32 1
	
	; get difference between indexes
	%difference = sub i32 %second, %first
	
	; store difference (size) in the only position in the new array
	store i32 %difference, i32* %dimensionRef
		
	; put new dimension data into array
	%dimensionsArray1 = insertvalue { {%ulong, i32}*, [1 x i32] } undef, {%ulong, i32}* %dimensions, 0
	
	; put number of dimensions (1) into array
	%dimensionsArray2 = insertvalue { {%ulong, i32}*, [1 x i32] } %dimensionsArray1, i32 1, 1, 0

	; get array data
	%arrayDataRef = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %array, i32 0, i32 3
	%arrayData = load %shadow.standard..Object*, %shadow.standard..Object** %arrayDataRef
	
	; cast to array pointer
	%arrayDataAsArrayPointer = bitcast %shadow.standard..Object* %arrayData to {%ulong, %shadow.standard..Object*}*	
	%arrayDataPointer = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %arrayDataAsArrayPointer, i32 0, i32 1
	
	%arrayDataAsChar = bitcast %shadow.standard..Object** %arrayDataPointer to i8*
	
	
	;allocate new real array
	%newArray = call noalias {%ulong, %shadow.standard..Object*}* @__allocateArray(%shadow.standard..Class* %baseClass, %uint %difference)
	%newArrayDataPointer = getelementptr {%ulong, %shadow.standard..Object*}, {%ulong, %shadow.standard..Object*}* %newArray, i32 0, i32 1
	
	%newArrayAsChar = bitcast %shadow.standard..Object** %newArrayDataPointer to i8*
	
	; get type parameter width
	%width = call %int @shadow.standard..Class_Mwidth(%shadow.standard..Class* %baseClass)			
	
	; multiply starting point by width
	%offset = mul i32 %first, %width
	%arrayDataAtOffset = getelementptr i8, i8* %arrayDataAsChar, i32 %offset
	%total = mul i32 %difference, %width
	
	;copy data
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %newArrayAsChar, i8* %arrayDataAtOffset, i32 %total, i32 1, i1 0)
	
	; get base class flag
	%flagRef = getelementptr inbounds %shadow.standard..Class, %shadow.standard..Class* %baseClass, i32 0, i32 7	
	%flag = load i32, i32* %flagRef
	%primitiveFlag = and i32 %flag, 2	
	
	; if not primitive, increment array elements
	%notPrimitive = icmp eq i32 %primitiveFlag, 0	
	br i1 %notPrimitive, label %_incrementArray, label %_done	
_incrementArray:	
	%end = add i32 %offset, %total
	%interfaceFlag = and i32 %flag, 1
	%isInterface = icmp ne i32 %interfaceFlag, 0
	br i1 %isInterface, label %_loopTestInterface, label %_checkArray	
_checkArray:
	%arrayFlag = and i32 %flag, 8
	%isArray = icmp ne i32 %interfaceFlag, 0
	br i1 %isArray, label %_loopTestArray, label %_loopTestObj	

	; array of interfaces
_loopTestInterface:
	%i1 = phi i32 [ %offset, %_incrementArray ], [%incremented1, %_loopBodyInterface]
	%less1 = icmp ult i32 %i1, %end
	br i1 %less1, label %_loopBodyInterface, label %_done
_loopBodyInterface:
	%elementRef1 = getelementptr i8, i8* %newArrayAsChar, i32 %i1
	%elementAsInterfaceRef = bitcast i8* %elementRef1 to { %shadow.standard..Object_methods*, %shadow.standard..Object* }*
	%elementAsInterface = load { %shadow.standard..Object_methods*, %shadow.standard..Object* }, { %shadow.standard..Object_methods*, %shadow.standard..Object* }* %elementAsInterfaceRef
	%elementAsInterfaceObj = extractvalue { %shadow.standard..Object_methods*, %shadow.standard..Object* } %elementAsInterface, 1
	call void @__incrementRef(%shadow.standard..Object* %elementAsInterfaceObj) nounwind
	%incremented1 = add i32 %i1, %width
	br label %_loopTestInterface
	
	; array of arrays
_loopTestArray:
	%i2 = phi i32 [ %offset, %_checkArray ], [%incremented2, %_loopBodyArray]
	%less2 = icmp ult i32 %i2, %end
	br i1 %less2, label %_loopBodyArray, label %_done
_loopBodyArray:
	%elementRef2 = getelementptr i8, i8* %newArrayAsChar, i32 %i2
	%elementAsArrayRef = bitcast i8* %elementRef2 to {{%ulong, %shadow.standard..Object*}*, %uint }*
	%elementAsArray = load {{%ulong, %shadow.standard..Object*}*, %uint }, {{%ulong, %shadow.standard..Object*}*, %uint }* %elementAsArrayRef
	%elementAsArrayPointer = extractvalue {{%ulong, %shadow.standard..Object*}*, %uint } %elementAsArray, 0
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %elementAsArrayPointer) nounwind
	%incremented2 = add i32 %i2, %width
	br label %_loopTestArray
	
	; array of objects
_loopTestObj:
	%i3 = phi i32 [ %offset, %_checkArray ], [%incremented3, %_loopBodyObj]
	%less3 = icmp ult i32 %i3, %end
	br i1 %less3, label %_loopBodyObj, label %_done
_loopBodyObj:
	%elementRef3 = getelementptr i8, i8* %newArrayAsChar, i32 %i3
	%elementAsObjRef = bitcast i8* %elementRef3 to %shadow.standard..Object**
	%elementAsObj = load %shadow.standard..Object*, %shadow.standard..Object** %elementAsObjRef
	call void @__incrementRef(%shadow.standard..Object* %elementAsObj) nounwind
	%incremented3 = add i32 %i3, %width
	br label %_loopTestObj	
	
_done:	
	%newArrayAsObj = bitcast {%ulong, %shadow.standard..Object*}* %newArray to %shadow.standard..Object*	
	%initializedArray = call %shadow.standard..Array* @shadow.standard..Array_Mcreate_int_A1_shadow.standard..Object(%shadow.standard..Object* %arrayObj, { {%ulong, i32}*, [1 x i32] } %dimensionsArray2, %shadow.standard..Object* %newArrayAsObj)		
	ret %shadow.standard..Array* %initializedArray	
throw:
	%ex.obj = call %shadow.standard..Object* @__allocate(%shadow.standard..Class* @shadow.standard..IndexOutOfBoundsException_class, %shadow.standard..Object_methods* bitcast(%shadow.standard..IndexOutOfBoundsException_methods* @shadow.standard..IndexOutOfBoundsException_methods to %shadow.standard..Object_methods*))
	%ex.ex = call %shadow.standard..IndexOutOfBoundsException* @shadow.standard..IndexOutOfBoundsException_Mcreate(%shadow.standard..Object* %ex.obj)
	
	call void @__shadow_throw(%shadow.standard..Object* %ex.obj) noreturn	
	unreachable
}

define %int @shadow.standard..Array_Mdimensions(%shadow.standard..Array*) {
    %2 = getelementptr inbounds %shadow.standard..Array, %shadow.standard..Array* %0, i32 0, i32 4
    %3 = load {{%ulong, %int}*, [1 x %int] } , {{%ulong, %int}*, [1 x %int] }* %2
    %4 = extractvalue { {%ulong, %int}*, [1 x %int] } %3, 1, 0
    ret %int %4
}


@_array0 = private unnamed_addr constant {%ulong, [17 x %byte]} { %ulong -1, [17 x %byte] c"Subarray started!" }
@_string0 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, {{%ulong, %byte}*, [1 x %int] }{{%ulong, %byte}* bitcast ({%ulong, [17 x %byte]}* @_array0 to {%ulong, %byte}*),  [1 x %int] [%int 17] }, %boolean true }

@_array1 = private unnamed_addr constant {%ulong, [17 x %byte]} { %ulong -1, [17 x %byte] c"Subarray done!!!!" }
@_string1 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, {{%ulong, %byte}*, [1 x %int] }{{%ulong, %byte}* bitcast ({%ulong, [17 x %byte]}* @_array1 to {%ulong, %byte}*),  [1 x %int] [%int 17] }, %boolean true }

@_array2 = private unnamed_addr constant {%ulong, [17 x %byte]} { %ulong -1, [17 x %byte] c"Past checks!!!!!!" }
@_string2 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, {{%ulong, %byte}*, [1 x %int] }{{%ulong, %byte}* bitcast ({%ulong, [17 x %byte]}* @_array2 to {%ulong, %byte}*),  [1 x %int] [%int 17] }, %boolean true }

@_array3 = private unnamed_addr constant {%ulong, [17 x %byte]} { %ulong -1, [17 x %byte] c"Reaching throw!!!" }
@_string3 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, {{%ulong, %byte}*, [1 x %int] }{{%ulong, %byte}* bitcast ({%ulong, [17 x %byte]}* @_array3 to {%ulong, %byte}*),  [1 x %int] [%int 17] }, %boolean true }

@_array4 = private unnamed_addr constant {%ulong, [17 x %byte]} { %ulong -1, [17 x %byte] c"Got size!!!!!!!!!" }
@_string4 = private unnamed_addr constant %shadow.standard..String { %ulong -1, %shadow.standard..Class* @shadow.standard..String_class, %shadow.standard..String_methods* @shadow.standard..String_methods, {{%ulong, %byte}*, [1 x %int] }{{%ulong, %byte}* bitcast ({%ulong, [17 x %byte]}* @_array4 to {%ulong, %byte}*),  [1 x %int] [%int 17] }, %boolean true }


