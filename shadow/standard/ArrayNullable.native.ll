; shadow.standard@ArrayNullable native methods

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
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class

%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods* , %shadow.standard..Object*, {{%ulong, %int}*, [1 x %int] } }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..ArrayNullable_methods* , %shadow.standard..Object*, {{%ulong, %int}*, [1 x %int] } }
%shadow.standard..IteratorNullable_methods = type opaque

declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__incrementRefArray({%ulong, %shadow.standard..Object*}*) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, i32}* %arrayPtr, i32 %dims, %shadow.standard..Class* %base) nounwind


;aliases are in Array.native.ll

declare %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, i32 %index, %shadow.standard..Class* %class, %shadow.standard..MethodTable* %methods, %boolean %nullable)

define %shadow.standard..Object* @shadow.standard..ArrayNullable_Mindex_int(%shadow.standard..Array* %object, i32 %index) {
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
	
	%result = call %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, i32 %index, %shadow.standard..Class* %baseClass, %shadow.standard..MethodTable* %methodTable, %boolean true)
	ret %shadow.standard..Object* %result
}