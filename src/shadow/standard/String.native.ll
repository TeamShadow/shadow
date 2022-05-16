; shadow.standard@String native methods
; 
; Author:
; Barry Wittman

;-------------
; Definitions
;-------------
; Primitives
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
%shadow.standard..String._methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap._methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable._methods = type opaque
%shadow.standard..MethodTable = type opaque
%shadow.standard..Array._methods = type opaque
%shadow.standard..Array = type opaque
%shadow.standard..ArrayNullable._methods = type opaque
%shadow.standard..ArrayNullable = type opaque

declare void @__incrementRef(%shadow.standard..Object*) nounwind

;---------------------------
; Shadow Method Definitions
;---------------------------
; Used to pretend that an array that will never be used elsewhere and cannot leak is actually immutable.
; private native readonly virtualFreeze( ubyte[] data ) => ( immutable byte[] )
define %shadow.standard..Array* @shadow.standard..String..virtualFreeze.ubyte._A(%shadow.standard..String* %string, %shadow.standard..Array* %data) alwaysinline nounwind readnone  {
_entry:	
	%asObject = bitcast %shadow.standard..Array* %data to %shadow.standard..Object*
	call void @__incrementRef(%shadow.standard..Object* %asObject) nounwind	
	ret %shadow.standard..Array* %data
}