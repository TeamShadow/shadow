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
%shadow.standard..Object._methods = type opaque
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object._methods*  }
%shadow.standard..Class._methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass._methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass._methods* , %shadow.standard..Array*, %shadow.standard..Array*, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, %shadow.standard..Array*, %shadow.standard..Array* }
%shadow.standard..Iterator._methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap._methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable._methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array._methods = type opaque
%shadow.standard..Array = type opaque
%shadow.standard..ArrayNullable._methods = type opaque
%shadow.standard..ArrayNullable = type opaque

%shadow.io..Console = type opaque

declare %shadow.io..Console* @shadow.io..Console..print_shadow.standard..String(%shadow.io..Console*, %shadow.standard..String*)
declare %shadow.io..Console* @shadow.io..Console..printLine(%shadow.io..Console*)

define %shadow.standard..Class* @shadow.standard..Object..getClass(%shadow.standard..Object*) {
	%2 = getelementptr %shadow.standard..Object, %shadow.standard..Object* %0, i32 0, i32 1
	%3 = load %shadow.standard..Class*, %shadow.standard..Class** %2	
	; No increment since classes are never GCed
	ret %shadow.standard..Class* %3
}

define void @shadow.standard..Object..destroy(%shadow.standard..Object*) {
	; Nothing to do since classes are never GCed, so no decrement of class
    ret void
}