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
%"shadow:standard@Object:_methods" = type { %"shadow:standard@Object"* (%"shadow:standard@Object"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Object"*)* }
%"shadow:standard@Object" = type { %"shadow:standard@Class"*, %"shadow:standard@Object:_methods"*  }
%"shadow:standard@Class:_methods" = type { %"shadow:standard@Class"* (%"shadow:standard@Class"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, { %"shadow:standard@Object"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %uint (%"shadow:standard@Class"*)*, %"shadow:standard@Object"* (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, { %"shadow:standard@Class"**, [1 x %int] } (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*)*, %boolean (%"shadow:standard@Class"*, %"shadow:standard@Class"*)*, %"shadow:standard@String"* (%"shadow:standard@Class"*, %"shadow:standard@String"*, { %"shadow:standard@Object"**, [1 x %int] }, %int, %int)*, %"shadow:standard@String"* (%"shadow:standard@Class"*)*, %"shadow:standard@Class"* (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)*, %int (%"shadow:standard@Class"*)* }
%"shadow:standard@Class" = type { %"shadow:standard@Class"*, %"shadow:standard@Class:_methods"* , %"shadow:standard@String"*, %"shadow:standard@Class"*, { %"shadow:standard@Object"**, [1 x %int] }, { %"shadow:standard@Class"**, [1 x %int] }, %int, %int }
%"shadow:standard@Iterator:_methods" = type { %boolean (%"shadow:standard@Object"*)*, %"shadow:standard@Object"* (%"shadow:standard@Object"*)* }
%"shadow:standard@String:_methods" = type { %"shadow:standard@String"* (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)*, { %byte*, [1 x %int] } (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %boolean (%"shadow:standard@String"*, %"shadow:standard@String"*)*, %uint (%"shadow:standard@String"*)*, %byte (%"shadow:standard@String"*, %int)*, %boolean (%"shadow:standard@String"*)*, { %"shadow:standard@Iterator:_methods"*, %"shadow:standard@Object"* } (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %int)*, %"shadow:standard@String"* (%"shadow:standard@String"*, %int, %int)*, %byte (%"shadow:standard@String"*)*, %double (%"shadow:standard@String"*)*, %float (%"shadow:standard@String"*)*, %int (%"shadow:standard@String"*)*, %long (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)*, %short (%"shadow:standard@String"*)*, %ubyte (%"shadow:standard@String"*)*, %uint (%"shadow:standard@String"*)*, %ulong (%"shadow:standard@String"*)*, %ushort (%"shadow:standard@String"*)*, %"shadow:standard@String"* (%"shadow:standard@String"*)* }
%"shadow:standard@String" = type { %"shadow:standard@Class"*, %"shadow:standard@String:_methods"* , { %byte*, [1 x %int] }, %boolean }
%"shadow:standard@AddressMap:_methods" = type opaque
%"shadow:standard@AddressMap" = type opaque

@"shadow:standard@Class:_methods" = external constant %"shadow:standard@Class:_methods"
@"shadow:standard@Class:class" = external constant %"shadow:standard@Class"
@"shadow:standard@String:_methods" = external constant %"shadow:standard@String:_methods"
@"shadow:standard@String:class" = external constant %"shadow:standard@Class"

define %"shadow:standard@Class"* @"shadow:standard@Object.getClass()"(%"shadow:standard@Object"*) {
	%2 = getelementptr %"shadow:standard@Object", %"shadow:standard@Object"* %0, i32 0, i32 0
	%3 = load %"shadow:standard@Class"*, %"shadow:standard@Class"** %2	
	ret %"shadow:standard@Class"* %3
}