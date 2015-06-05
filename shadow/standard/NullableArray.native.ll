; shadow.standard@NullableArray native methods
; Note: These are placeholders copied from Array and probably do not all work!

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
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CGenericClass"*)*, %"_Pshadow_Pstandard_CGenericClass"* (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CArrayClass_methods" = type { %"_Pshadow_Pstandard_CArrayClass"* (%"_Pshadow_Pstandard_CArrayClass"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } (%"_Pshadow_Pstandard_CGenericClass"*)* }
%"_Pshadow_Pstandard_CArrayClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArrayClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, %"_Pshadow_Pstandard_CClass"* }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %byte (%"_Pshadow_Pstandard_CString"*)*, %double (%"_Pshadow_Pstandard_CString"*)*, %float (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %long (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %short (%"_Pshadow_Pstandard_CString"*)*, %ubyte (%"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %ulong (%"_Pshadow_Pstandard_CString"*)*, %ushort (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }
%"_Pshadow_Pstandard_CAddressMap_methods" = type opaque
%"_Pshadow_Pstandard_CAddressMap" = type opaque


@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"

%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CException"* (%"_Pshadow_Pstandard_CException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = type { %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*)*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* (%"_Pshadow_Pstandard_CIndexOutOfBoundsException"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* , %"_Pshadow_Pstandard_CString"* }

@"_Pshadow_Pstandard_CIndexOutOfBoundsException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = external constant %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"

%"_Pshadow_Pstandard_CNullableArray_methods" = type { %"_Pshadow_Pstandard_CNullableArray"* (%"_Pshadow_Pstandard_CNullableArray"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CNullableArray"*)*, %int (%"_Pshadow_Pstandard_CNullableArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CNullableArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CNullableArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CNullableArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CNullableArray"*)*, %int (%"_Pshadow_Pstandard_CNullableArray"*)* }
%"_Pshadow_Pstandard_CNullableArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CNullableArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
@"_Pshadow_Pstandard_Cint_class" = external constant %"_Pshadow_Pstandard_CClass"

@_generics_Pshadow_Pstandard_CNullableArray = external constant { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }
@"_Pshadow_Pstandard_CNullableArray_methods" = external constant %"_Pshadow_Pstandard_CNullableArray_methods"


declare %"_Pshadow_Pstandard_CNullableArray"* @"_Pshadow_Pstandard_CNullableArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*, %"_Pshadow_Pstandard_CObject_methods"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_MallocateNullable_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)		

declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"*)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CGenericClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass_A1"(%"_Pshadow_Pstandard_CGenericClass"*, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] })
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArrayClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CArrayClass"*, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"*)
declare %int @"_Pshadow_Pstandard_CNullableArray_Msize"(%"_Pshadow_Pstandard_CNullableArray"*)

; debug
%"_Pshadow_Pio_CConsole_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, { %byte, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CConsole_methods"* , %boolean }
@"_Pshadow_Pio_CConsole_instance" = external global %"_Pshadow_Pio_CConsole"*
declare void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)

define private void @debugPrint(%int) {
	%2 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"
	call void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"* %2, %int %0)
	ret void
}

declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn

define i32 @_Pshadow_Pstandard_CNullableArray_Mdims(%_Pshadow_Pstandard_CNullableArray*) {
	%2 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %0, i32 0, i32 3
	%3 = load { i32*, [1 x i32] }* %2
	%4 = extractvalue { i32*, [1 x i32] } %3, 1, 0
	ret i32 %4
}

declare void @abort() noreturn

define private i32 @computeIndex(%"_Pshadow_Pstandard_CNullableArray"*, { i32*, [1 x i32] }) alwaysinline {	
	%3 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %0, i32 0, i32 3	
	%4 = load { i32*, [1 x i32] }* %3
	%5 = extractvalue { i32*, [1 x i32] } %1, 0
	%6 = extractvalue { i32*, [1 x i32] } %1, 1, 0
	%7 = extractvalue { i32*, [1 x i32] } %4, 0
	%8 = extractvalue { i32*, [1 x i32] } %4, 1, 0
	%9 = icmp eq i32 %6, %8
	br i1 %9, label %10, label %throw
	%11 = load i32* %5
	%12 = load i32* %7
	%13 = icmp ult i32 %11, %12
	br i1 %13, label %14, label %throw
	%15 = sub i32 %6, 1
	%16 = icmp ne i32 %15, 0
	br i1 %16, label %17, label %32
	%18 = phi i32* [ %5, %14 ], [ %22, %27 ]
	%19 = phi i32* [ %7, %14 ], [ %23, %27 ]
	%20 = phi i32 [ %11, %14 ], [ %29, %27 ]
	%21 = phi i32 [ %15, %14 ], [ %30, %27 ]
	%22 = getelementptr inbounds i32* %18, i32 1
	%23 = getelementptr inbounds i32* %19, i32 1
	%24 = load i32* %22
	%25 = load i32* %23
	%26 = icmp ult i32 %24, %25
	br i1 %26, label %27, label %throw
	%28 = mul i32 %20, %25
	%29 = add i32 %28, %24
	%30 = sub i32 %21, 1
	%31 = icmp ne i32 %30, 0
	br i1 %31, label %17, label %32
	%33 = phi i32 [ %11, %14 ], [ %29, %27 ]
	ret i32 %33
throw:
	%ex.obj = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CIndexOutOfBoundsException_class, %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" to %"_Pshadow_Pstandard_CObject_methods"*))
	%ex.ex = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"* %ex.obj)
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %ex.obj) noreturn	
	unreachable
}


; Maybe okay!
define %"_Pshadow_Pstandard_CNullableArray"* @"_Pshadow_Pstandard_CNullableArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CNullableArray"*
    %lengths = alloca { %int*, [1 x %int] }
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CNullableArray"*
    store %"_Pshadow_Pstandard_CNullableArray"* %3, %"_Pshadow_Pstandard_CNullableArray"** %this
    store { %int*, [1 x %int] } %1, { %int*, [1 x %int] }* %lengths
    %4 = load %"_Pshadow_Pstandard_CNullableArray"** %this
    %5 = bitcast %"_Pshadow_Pstandard_CNullableArray"* %4 to %"_Pshadow_Pstandard_CObject"*
    %6 = call %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %5)
    %7 = load %"_Pshadow_Pstandard_CNullableArray"** %this		
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %7, i32 0, i32 2
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %8
    %9 = load %"_Pshadow_Pstandard_CNullableArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %9, i32 0, i32 3
    %11 = insertvalue { %int*, [1 x %int] } zeroinitializer, %int* null, 0
    store { %int*, [1 x %int] } %11, { %int*, [1 x %int] }* %10
    %12 = load %"_Pshadow_Pstandard_CNullableArray"** %this
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %12, i32 0, i32 3
    %14 = load { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %14, { %int*, [1 x %int] }* %13
    %15 = load %"_Pshadow_Pstandard_CNullableArray"** %this
    %16 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %15, i32 0, i32 2
    %17 = load %"_Pshadow_Pstandard_CNullableArray"** %this    
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %17, i32 0, i32 0
    %19 = load %"_Pshadow_Pstandard_CClass"** %18
    %20 = bitcast %"_Pshadow_Pstandard_CClass"* %19 to %"_Pshadow_Pstandard_CArrayClass"*
	%21 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %20, i32 0, i32 9
    %22 = load %"_Pshadow_Pstandard_CClass"** %21
    %23 = call %int (%"_Pshadow_Pstandard_CNullableArray"*)* @"_Pshadow_Pstandard_CNullableArray_Msize"(%"_Pshadow_Pstandard_CNullableArray"* %17)
	%24 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_MallocateNullable_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %22, i32 %23)		
    store %"_Pshadow_Pstandard_CObject"* %24, %"_Pshadow_Pstandard_CObject"** %16
    %25 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CNullableArray"*
    ret %"_Pshadow_Pstandard_CNullableArray"* %25
}

define %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CNullableArray_MgetBaseClass"(%"_Pshadow_Pstandard_CNullableArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CNullableArray"*
    store %"_Pshadow_Pstandard_CNullableArray"* %0, %"_Pshadow_Pstandard_CNullableArray"** %this
    %2 = load %"_Pshadow_Pstandard_CNullableArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %2, i32 0, i32 0
    %4 = load %"_Pshadow_Pstandard_CClass"** %3
    %5 = bitcast %"_Pshadow_Pstandard_CClass"* %4 to %"_Pshadow_Pstandard_CArrayClass"*
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %5, i32 0, i32 9
    %7 = load %"_Pshadow_Pstandard_CClass"** %6    
    ret %"_Pshadow_Pstandard_CClass"* %7
}

; Maybe okay!
define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CNullableArray*, i32 ) {
	%classRef = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %0, i32 0, i32 0
	
	; get array class
	%class = load %"_Pshadow_Pstandard_CClass"** %classRef
	%arrayClass = bitcast %"_Pshadow_Pstandard_CClass"* %class to %"_Pshadow_Pstandard_CArrayClass"*	
	
	; index into internal representation
	%baseClassRef = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %arrayClass, i32 0, i32 9
	%baseClass = load %"_Pshadow_Pstandard_CClass"** %baseClassRef
	%flagRef = getelementptr inbounds %_Pshadow_Pstandard_CClass* %baseClass, i32 0, i32 6
	
	; get generic class flag
	%flag = load i32* %flagRef
	%arrayRef = getelementptr inbounds %_Pshadow_Pstandard_CNullableArray* %0, i32 0, i32 2
	%arrayAsObj = load %_Pshadow_Pstandard_CObject** %arrayRef	
 	
	; test array class flag	
	%arrayFlag = and i32 %flag, 8
	%notArray = icmp eq i32 %arrayFlag, 0
	
	; if flag does not contain 8, proceed with regular object
	br i1 %notArray, label %_object, label %_array

_object:	
	%array = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to %_Pshadow_Pstandard_CObject**
	%elementRef = getelementptr inbounds %_Pshadow_Pstandard_CObject** %array, i32 %1
	%element = load %_Pshadow_Pstandard_CObject** %elementRef
	ret %_Pshadow_Pstandard_CObject* %element	
	
_array:	
	; get generic class parameter T out of generic T[] class	
	%arrayBaseRef = getelementptr inbounds %"_Pshadow_Pstandard_CClass"* %baseClass, i32 0, i32 3
	%arrayBase = load %"_Pshadow_Pstandard_CClass"** %arrayBaseRef
	
	; now find class Array<T>
	%arrayOfGenerics = load { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }*  @_generics_Pshadow_Pstandard_CNullableArray
	%genericArray = call %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArrayClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CArrayClass"* null, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] } %arrayOfGenerics, %"_Pshadow_Pstandard_CClass"* %arrayBase)
	
	; create new Array wrapper
	%arrayWrapper = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %genericArray, %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CNullableArray_methods"* @"_Pshadow_Pstandard_CNullableArray_methods" to %"_Pshadow_Pstandard_CObject_methods"*))
	
	; get size member of array class, which is equal to dimensions
	%dimensionsRef = getelementptr inbounds %_Pshadow_Pstandard_CClass* %baseClass, i32 0, i32 7
	%dimensions = load i32* %dimensionsRef
	
	; allocate new array for dimensions
	%dimensionsArrayAsObj = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %dimensions)
	
	; get element (which is an array)
	%arrayWidth = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %baseClass)		
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to i8*
	
	; get offset in bytes
	%arrayElement = getelementptr inbounds i8* %arrayAsBytes2, i32 %arrayOffset
	
	; cast to object*** because an array is { Object**, [dimensions x int] }
	%arrayElementAsBytes = bitcast i8* %arrayElement to %_Pshadow_Pstandard_CObject***
	%arrayDataRef = load %_Pshadow_Pstandard_CObject*** %arrayElementAsBytes
	%arrayDataAsObj = bitcast %_Pshadow_Pstandard_CObject** %arrayDataRef to %_Pshadow_Pstandard_CObject*
	
	; skip past object pointer to dimensions
	%arraySizesAsObj = getelementptr inbounds %_Pshadow_Pstandard_CObject*** %arrayElementAsBytes, i32 1
	%arraySizes = bitcast %_Pshadow_Pstandard_CObject*** %arraySizesAsObj to i32*
	
	; copy dimensions into allocated space
	%dimensionsArray = bitcast %_Pshadow_Pstandard_CObject* %dimensionsArrayAsObj to i32*
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %dimensionsArray, i32* %arraySizes, i32 %dimensions, i32 0, i1 0)
	
	; make lengths array
	%lengthsArray1 = insertvalue { i32*, [1 x i32] } undef, i32* %arraySizes, 0
	%lengthsArray2 = insertvalue { i32*, [1 x i32] } %lengthsArray1, i32 %dimensions, 1, 0
	
	; initialize Array<T> object
	%initializedArray = call %"_Pshadow_Pstandard_CNullableArray"* @"_Pshadow_Pstandard_CNullableArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* %arrayWrapper, { %int*, [1 x %int] } %lengthsArray2, %"_Pshadow_Pstandard_CObject"* %arrayDataAsObj)
	
	; Object that just got initialized
	ret %_Pshadow_Pstandard_CObject* %arrayWrapper
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CNullableArray*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @computeIndex(%"_Pshadow_Pstandard_CNullableArray"* %0, { i32*, [1 x i32] } %1)	
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CNullableArray* %0, i32 %3)
	ret %_Pshadow_Pstandard_CObject* %4
}

; maybe okay!
define void @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CNullableArray*, i32, %_Pshadow_Pstandard_CObject*) {
	%classRef = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %0, i32 0, i32 0
	
	; get array class
	%class = load %"_Pshadow_Pstandard_CClass"** %classRef
	%arrayClass = bitcast %"_Pshadow_Pstandard_CClass"* %class to %"_Pshadow_Pstandard_CArrayClass"*	
	%baseClassRef = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %arrayClass, i32 0, i32 9
	
	%baseClass = load %"_Pshadow_Pstandard_CClass"** %baseClassRef
	%flagRef = getelementptr inbounds %_Pshadow_Pstandard_CClass* %baseClass, i32 0, i32 6	
	
	; get generic class flag
	%flag = load i32* %flagRef	
	%arrayRef = getelementptr inbounds %_Pshadow_Pstandard_CNullableArray* %0, i32 0, i32 2
	%arrayAsObj = load %_Pshadow_Pstandard_CObject** %arrayRef
	
	; check for array class
	%arrayFlag = and i32 %flag, 8	
	%notArray = icmp eq i32 %arrayFlag, 0
	br i1 %notArray, label %_object, label %_array

_object:	
	%array = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to %_Pshadow_Pstandard_CObject**
	%elementRef = getelementptr inbounds %_Pshadow_Pstandard_CObject** %array, i32 %1
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %elementRef
	ret void
	
_array:	
	; get the location inside the current Array<T>
	%arrayWidth = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %baseClass)	
	%arrayOffset = mul i32 %1, %arrayWidth	
	%arrayAsBytes2 = bitcast %_Pshadow_Pstandard_CObject* %arrayAsObj to i8*
	%arrayElement = getelementptr inbounds i8* %arrayAsBytes2, i32 %arrayOffset
		
	; get the array data from the input Array<T> (which it must be)
	%input = bitcast %_Pshadow_Pstandard_CObject* %2 to %"_Pshadow_Pstandard_CNullableArray"*
	%inputArray = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %input, i32 0, i32 2
	%inputArrayAsObj = load %"_Pshadow_Pstandard_CObject"** %inputArray
				
	; store it into the array
	%arrayElementAsObj = bitcast i8* %arrayElement to %"_Pshadow_Pstandard_CObject"**
	store %"_Pshadow_Pstandard_CObject"* %inputArrayAsObj, %"_Pshadow_Pstandard_CObject"** %arrayElementAsObj	
	
	; store length data
	%dimensionsRef = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %input, i32 0, i32 3, i32 1, i32 0
	%dimensions = load i32* %dimensionsRef
	%lengthsRef = getelementptr inbounds %"_Pshadow_Pstandard_CNullableArray"* %input, i32 0, i32 3, i32 0
	%lengths = load i32** %lengthsRef
	
	; skip past the pointer to the length data
	%lengthDataAsObj = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %arrayElementAsObj, i32 1
	%lengthData = bitcast %"_Pshadow_Pstandard_CObject"** %lengthDataAsObj to i32*
	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %lengthData, i32* %lengths, i32 %dimensions, i32 0, i1 0)	
	ret void
}

define void @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CNullableArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*) alwaysinline {
	%4 = call i32 @computeIndex(%_Pshadow_Pstandard_CNullableArray* %0, { i32*, [1 x i32] } %1)	
	call void @_Pshadow_Pstandard_CNullableArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CNullableArray* %0, i32 %4, %_Pshadow_Pstandard_CObject* %2)
	ret void
}