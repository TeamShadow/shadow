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
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,  %ulong }, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong }, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,  %ulong }, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong }, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*, %ulong }, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,  %ulong } }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %ulong, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%ulong, %byte}*, %shadow.standard..Class*, %ulong }, %boolean }%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class
@shadow.standard..MethodTable_class = external constant %shadow.standard..Class

%shadow.standard..Array_methods = type opaque
%shadow.standard..Array = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} }
%shadow.standard..ArrayNullable_methods = type opaque
%shadow.standard..ArrayNullable = type { %ulong, %shadow.standard..Class*, %shadow.standard..Array_methods*, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} }

declare void @__incrementRef(%shadow.standard..Object*) nounwind
declare void @__incrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRefArray({{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %array) nounwind 

;aliases are in Array.native.ll

declare %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, %ulong %index, %shadow.standard..Class* %class, %shadow.standard..MethodTable* %methods, %boolean %nullable)

define %shadow.standard..Object* @shadow.standard..ArrayNullable_Mindex_long(%shadow.standard..ArrayNullable* %object, %ulong %index ) {
	; get array data
	%dataRef = getelementptr inbounds %shadow.standard..ArrayNullable, %shadow.standard..ArrayNullable* %object, i32 0, i32 3
	%data = load {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong}* %dataRef	
	%array = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 0
	
	; get base class
	%baseClass = extractvalue {{%ulong, %shadow.standard..Object*}*, %shadow.standard..Class*, %ulong} %data, 1
	
	; get array class
	%classRef = getelementptr inbounds %shadow.standard..ArrayNullable, %shadow.standard..ArrayNullable* %object, i32 0, i32 1
    %class = load %shadow.standard..Class*, %shadow.standard..Class** %classRef 
	%genericClass = bitcast %shadow.standard..Class* %class to %shadow.standard..GenericClass*
	
	; get method table
	%methodTables = getelementptr inbounds %shadow.standard..GenericClass, %shadow.standard..GenericClass* %genericClass, i32 0, i32 10	
	%methodTablesArray = load {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong}* %methodTables
	%methodsTablesArrayPointer = extractvalue {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*, %ulong} %methodTablesArray, 0
	%methodTableRef = getelementptr {%ulong, %shadow.standard..MethodTable*}, {%ulong, %shadow.standard..MethodTable*}* %methodsTablesArrayPointer, i32 0, i32 1
	; since there's only one, we can simply load the pointer without offset
	%methodTable = load %shadow.standard..MethodTable*, %shadow.standard..MethodTable** %methodTableRef
	
	%result = call %shadow.standard..Object* @__arrayLoad({%ulong, %shadow.standard..Object*}* %array, %ulong %index, %shadow.standard..Class* %baseClass, %shadow.standard..MethodTable* %methodTable, %boolean true)
	ret %shadow.standard..Object* %result
}

