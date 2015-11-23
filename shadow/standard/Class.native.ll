; shadow.standard@Class native methods

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

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%"shadow:standard@Object"*) noreturn
declare %"shadow:standard@Exception"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

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

%"shadow:standard@Exception:_methods" = type { %"shadow:standard@Exception"* (%"shadow:standard@Exception"*)*, %"shadow:standard@Exception"* (%"shadow:standard@Exception"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)* }
%"shadow:standard@Exception" = type { %"shadow:standard@Class"*, %"shadow:standard@Exception:_methods"* , %"shadow:standard@String"* }
%"shadow:standard@OutOfMemoryException:_methods" = type { %"shadow:standard@OutOfMemoryException"* (%"shadow:standard@OutOfMemoryException"*)*, %"shadow:standard@OutOfMemoryException"* (%"shadow:standard@OutOfMemoryException"*, %"shadow:standard@AddressMap"*)*, %"shadow:standard@Class"* (%"shadow:standard@Object"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)*, %"shadow:standard@String"* (%"shadow:standard@Exception"*)* }
%"shadow:standard@OutOfMemoryException" = type { %"shadow:standard@Class"*, %"shadow:standard@OutOfMemoryException:_methods"* , %"shadow:standard@String"* }

@"shadow:standard@Class:_methods" = external constant %"shadow:standard@Class:_methods"
@"shadow:standard@Class:class" = external constant %"shadow:standard@Class"
@"shadow:standard@String:_methods" = external constant %"shadow:standard@String:_methods"
@"shadow:standard@String:class" = external constant %"shadow:standard@Class"
@"shadow:standard@Exception:_methods" = external constant %"shadow:standard@Exception:_methods"
@"shadow:standard@Exception:class" = external constant %"shadow:standard@Class"
@"shadow:standard@OutOfMemoryException:class" = external constant %"shadow:standard@Class"
@"shadow:standard@OutOfMemoryException:_methods" = external constant %"shadow:standard@OutOfMemoryException:_methods"

declare noalias i8* @malloc(i32) nounwind
declare noalias i8* @calloc(i32, i32) nounwind
declare %"shadow:standard@OutOfMemoryException"* @"shadow:standard@OutOfMemoryException.create()"(%"shadow:standard@Object"*)
declare %int @"shadow:standard@Class.width()"(%"shadow:standard@Class"*)
declare %boolean @"shadow:standard@Class.isArray()"(%"shadow:standard@Class"*)

define noalias %"shadow:standard@Object"* @"shadow:standard@Class.allocate()"(%"shadow:standard@Class"*, %"shadow:standard@Object:_methods"*) {
	%3 = getelementptr inbounds %"shadow:standard@Class", %"shadow:standard@Class"* %0, i32 0, i32 7
	%4 = load i32, i32* %3
	%5 = call noalias i8* @malloc(i32 %4) nounwind
	%6 = bitcast i8* %5 to %"shadow:standard@Object"*
	%7 = icmp eq %"shadow:standard@Object"* %6, null
	br i1 %7, label %_label0, label %_label1
_label0: 
	%8 = bitcast %"shadow:standard@OutOfMemoryException"* @_OutOfMemoryException to %"shadow:standard@Object"*
	call void @__shadow_throw(%"shadow:standard@Object"* %8) noreturn
    unreachable	
_label1:	
	%9 = getelementptr inbounds %"shadow:standard@Object", %"shadow:standard@Object"* %6, i32 0, i32 0
    store %"shadow:standard@Class"* %0, %"shadow:standard@Class"** %9
    %10 = getelementptr inbounds %"shadow:standard@Object", %"shadow:standard@Object"* %6, i32 0, i32 1
    store %"shadow:standard@Object:_methods"* %1, %"shadow:standard@Object:_methods"** %10	
	ret %"shadow:standard@Object"* %6
}

define noalias %"shadow:standard@Object"* @"shadow:standard@Class.allocate(int)"(%"shadow:standard@Class"*, i32) {
	%3 = call %int @"shadow:standard@Class.width()"(%"shadow:standard@Class"* %0)		
	%4 = call noalias i8* @calloc(i32 %1, i32 %3)	
	%5 = bitcast i8* %4 to %"shadow:standard@Object"*
	%6 = icmp eq %"shadow:standard@Object"* %5, null
	br i1 %6, label %_label2, label %_label3
_label2: 
	%7 = bitcast %"shadow:standard@OutOfMemoryException"* @_OutOfMemoryException to %"shadow:standard@Object"*
	call void @__shadow_throw(%"shadow:standard@Object"* %7) noreturn
   unreachable	
_label3:
	ret %"shadow:standard@Object"* %5
}

define %int @"shadow:standard@Class.pointerSize"(%"shadow:standard@Class"*) {
		%2 = ptrtoint %"shadow:standard@Object"** getelementptr (%"shadow:standard@Object"*, %"shadow:standard@Object"** null, i32 1) to i32
		ret %int %2
}

@_array0 = private unnamed_addr constant [20 x %byte] c"Heap space exhausted"
@_string0 = private unnamed_addr constant %"shadow:standard@String" { %"shadow:standard@Class"* @"shadow:standard@String:class", %"shadow:standard@String:_methods"* @"shadow:standard@String:_methods", { %byte*, [1 x %int] } { %byte* getelementptr inbounds ([20 x %byte], [20 x %byte]* @_array0, i32 0, i32 0), [1 x %int] [%int 20] }, %boolean true }
@_OutOfMemoryException = private constant %"shadow:standard@OutOfMemoryException" { %"shadow:standard@Class"* @"shadow:standard@OutOfMemoryException:class", %"shadow:standard@OutOfMemoryException:_methods"* @"shadow:standard@OutOfMemoryException:_methods", %"shadow:standard@String"* @_string0 }