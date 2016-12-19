; shadow.standard@Object native methods

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
%shadow.standard..Object = type { %uint, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %uint, %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %uint, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , %shadow.standard..String*, %shadow.standard..Class*, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] }, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, %int, %int, {{%uint, %shadow.standard..Class*}*, [1 x %int] }, {{%uint, %shadow.standard..MethodTable*}*, [1 x %int] } }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type { %uint, %shadow.standard..Class*, %shadow.standard..String_methods* , {{%uint, %byte}*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

declare void @__incrementRef(%shadow.standard..Object* %object) nounwind
declare void @__decrementRef(%shadow.standard..Object* %object) nounwind

@shadow.standard..Object_class = external constant %shadow.standard..Class
@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

%shadow.io..Console_methods = type opaque
%shadow.io..Console = type { %shadow.standard..Class*, %shadow.io..Console_methods* , %boolean }

declare %shadow.io..Console* @shadow.io..Console_Mprint_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console_MprintLine(%shadow.io..Console*) 


define %shadow.standard..Class* @shadow.standard..Object_MgetClass(%shadow.standard..Object*) {
	%2 = getelementptr %shadow.standard..Object, %shadow.standard..Object* %0, i32 0, i32 1
	%3 = load %shadow.standard..Class*, %shadow.standard..Class** %2	
	%4 = bitcast %shadow.standard..Class* %3 to %shadow.standard..Object*
	call void @__incrementRef(%shadow.standard..Object* %4) nounwind
	ret %shadow.standard..Class* %3
}

define void @shadow.standard..Object_Mdestroy(%shadow.standard..Object*) {
	%2 = getelementptr %shadow.standard..Object, %shadow.standard..Object* %0, i32 0, i32 1
	%3 = load %shadow.standard..Class*, %shadow.standard..Class** %2	
	%4 = bitcast %shadow.standard..Class* %3 to %shadow.standard..Object*
	call void @__decrementRef(%shadow.standard..Object* %4) nounwind
    ret void
}