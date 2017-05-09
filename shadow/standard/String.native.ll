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
%void = type i8

; standard definitions
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, void (%shadow.standard..Object*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %ulong, %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type opaque
%shadow.standard..Class = type { %ulong, %shadow.standard..Class*, %shadow.standard..Class_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int }
%shadow.standard..GenericClass_methods = type opaque
%shadow.standard..GenericClass = type { %ulong, %shadow.standard..Class*, %shadow.standard..GenericClass_methods* , {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, %shadow.standard..String*, %shadow.standard..Class*, %int, %int, {{%ulong, %shadow.standard..Class*}*, %shadow.standard..Class*,%ulong}, {{%ulong, %shadow.standard..MethodTable*}*, %shadow.standard..Class*,%ulong} }
%shadow.standard..Iterator_methods = type opaque
%shadow.standard..String_methods = type opaque
%shadow.standard..String = type opaque
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..MethodTable_methods = type opaque
%shadow.standard..MethodTable = type opaque

declare void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %arrayData) nounwind

;---------------------------
; Shadow Method Definitions
;---------------------------
; Used to pretend that an array that will never be used elsewhere and cannot leak is actually immutable.
; private native readonly virtualFreeze( byte[] data ) => ( immutable byte[] )
define {{%ulong, %byte}*, %shadow.standard..Class*,%ulong} @shadow.standard..String_MvirtualFreeze_byte_A(%shadow.standard..String* %string, {{%ulong, %byte}*, %shadow.standard..Class*,%ulong} %data) alwaysinline nounwind readnone  {
_entry:
	%arrayData = extractvalue {{%ulong, %byte}*, %shadow.standard..Class*,%ulong} %data, 0
	; all return values are expected to have an "extra" reference count that will get decremented on the outside
	%arrayDataAsObj = bitcast {%ulong, %byte}* %arrayData to {%ulong, %shadow.standard..Object*}*
	call void @__incrementRefArray({%ulong, %shadow.standard..Object*}* %arrayDataAsObj) nounwind
	ret {{%ulong, %byte}*, %shadow.standard..Class*,%ulong} %data
}