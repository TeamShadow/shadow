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
%shadow.standard..Object_methods = type { %shadow.standard..Object* (%shadow.standard..Object*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Object*)* }
%shadow.standard..Object = type { %shadow.standard..Class*, %shadow.standard..Object_methods*  }
%shadow.standard..Class_methods = type { %shadow.standard..Class* (%shadow.standard..Class*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..Class*)*, { %shadow.standard..Object**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %uint (%shadow.standard..Class*)*, %shadow.standard..Object* (%shadow.standard..Class*, %shadow.standard..Class*)*, { %shadow.standard..Class**, [1 x %int] } (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*)*, %boolean (%shadow.standard..Class*, %shadow.standard..Class*)*, %shadow.standard..String* (%shadow.standard..Class*, %shadow.standard..String*, { %shadow.standard..Object**, [1 x %int] }, %int, %int)*, %shadow.standard..String* (%shadow.standard..Class*)*, %shadow.standard..Class* (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)*, %int (%shadow.standard..Class*)* }
%shadow.standard..Class = type { %shadow.standard..Class*, %shadow.standard..Class_methods* , %shadow.standard..String*, %shadow.standard..Class*, { %shadow.standard..Object**, [1 x %int] }, { %shadow.standard..Class**, [1 x %int] }, %int, %int }
%shadow.standard..Iterator_methods = type { %boolean (%shadow.standard..Object*)*, %shadow.standard..Object* (%shadow.standard..Object*)* }
%shadow.standard..String_methods = type { %shadow.standard..String* (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..AddressMap*)*, %shadow.standard..Class* (%shadow.standard..Object*)*, %shadow.standard..String* (%shadow.standard..String*)*, { %byte*, [1 x %int] } (%shadow.standard..String*)*, %int (%shadow.standard..String*, %shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %shadow.standard..String*)*, %boolean (%shadow.standard..String*, %shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %byte (%shadow.standard..String*, %int)*, %boolean (%shadow.standard..String*)*, { %shadow.standard..Iterator_methods*, %shadow.standard..Object* } (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*, %int)*, %shadow.standard..String* (%shadow.standard..String*, %int, %int)*, %byte (%shadow.standard..String*)*, %double (%shadow.standard..String*)*, %float (%shadow.standard..String*)*, %int (%shadow.standard..String*)*, %long (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)*, %short (%shadow.standard..String*)*, %ubyte (%shadow.standard..String*)*, %uint (%shadow.standard..String*)*, %ulong (%shadow.standard..String*)*, %ushort (%shadow.standard..String*)*, %shadow.standard..String* (%shadow.standard..String*)* }
%shadow.standard..String = type { %shadow.standard..Class*, %shadow.standard..String_methods* , { %byte*, [1 x %int] }, %boolean }
%shadow.standard..AddressMap_methods = type opaque
%shadow.standard..AddressMap = type opaque
%shadow.standard..Thread = type opaque

@shadow.standard..Class_methods = external constant %shadow.standard..Class_methods
@shadow.standard..Class_class = external constant %shadow.standard..Class
@shadow.standard..String_methods = external constant %shadow.standard..String_methods
@shadow.standard..String_class = external constant %shadow.standard..Class

define %shadow.standard..Class* @shadow.standard..Object_MgetClass(%shadow.standard..Object*) {
	%2 = getelementptr %shadow.standard..Object, %shadow.standard..Object* %0, i32 0, i32 0
	%3 = load %shadow.standard..Class*, %shadow.standard..Class** %2	
	ret %shadow.standard..Class* %3
}

; temp
; curthread() => (Thread);
@shadow.standard..Thread_TLS_currentThread = external global %shadow.standard..Thread*
define %shadow.standard..Thread* @shadow.standard..Object_Mcurthread(%shadow.standard..Object*) {
entry:
	%currentThread = load %shadow.standard..Thread*, %shadow.standard..Thread** @shadow.standard..Thread_TLS_currentThread
	ret %shadow.standard..Thread* %currentThread
}

declare { %shadow.standard..Object*, %shadow.standard..Thread* } @shadow.standard..Thread_MreceiveNative_shadow.standard..Class(%shadow.standard..Thread*, %shadow.standard..Class*)
define { %shadow.standard..Object*, %shadow.standard..Thread* } @shadow.standard..Object_Mreceive__1_shadow.standard..Thread_shadow.standard..Class(%shadow.standard..Object*, %shadow.standard..Thread*, %shadow.standard..Class*) {
entry:
	%call = call { %shadow.standard..Object*, %shadow.standard..Thread* } @shadow.standard..Thread_MreceiveNative_shadow.standard..Class(%shadow.standard..Thread* %1, %shadow.standard..Class* %2)
	
	ret { %shadow.standard..Object*, %shadow.standard..Thread* } %call
}

declare %shadow.standard..Object* @shadow.standard..Thread_MreceiveNative_shadow.standard..Class_shadow.standard..Thread(%shadow.standard..Thread*, %shadow.standard..Class*, %shadow.standard..Thread*)
define %shadow.standard..Object* @shadow.standard..Object_Mreceive__2_shadow.standard..Thread_shadow.standard..Class_shadow.standard..Thread(%shadow.standard..Object*, %shadow.standard..Thread*, %shadow.standard..Class*, %shadow.standard..Thread*) {
entry:
	%call = call %shadow.standard..Object* @shadow.standard..Thread_MreceiveNative_shadow.standard..Class_shadow.standard..Thread(%shadow.standard..Thread* %1, %shadow.standard..Class* %2, %shadow.standard..Thread* %3)
	ret %shadow.standard..Object* %call
}

declare void @shadow.standard..Thread_MsendNative_shadow.standard..Object(%shadow.standard..Thread*, %shadow.standard..Object*)
define void @shadow.standard..Object_Msend_shadow.standard..Object_shadow.standard..Thread(%shadow.standard..Object*, %shadow.standard..Object*, %shadow.standard..Thread*) {
entry:
	call void @shadow.standard..Thread_MsendNative_shadow.standard..Object(%shadow.standard..Thread* %2, %shadow.standard..Object* %1)
	
	ret void
}