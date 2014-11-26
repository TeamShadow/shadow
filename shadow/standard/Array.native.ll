; shadow.standard@Array native methods

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

%"_Pshadow_Pstandard_CArray_methods" = type { %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
@"_Pshadow_Pstandard_Cint_class" = external constant %"_Pshadow_Pstandard_CClass"

@_generics_Pshadow_Pstandard_CArray = external constant { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }
@"_Pshadow_Pstandard_CArray_methods" = external constant %"_Pshadow_Pstandard_CArray_methods"

declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*, %"_Pshadow_Pstandard_CObject_methods"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"*)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"*)

declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CGenericClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass_A1"(%"_Pshadow_Pstandard_CGenericClass"*, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] })
declare %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArrayClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CArrayClass"*, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }, %"_Pshadow_Pstandard_CClass"*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"*)

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

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, %_Pshadow_Pstandard_CClass*, i32) {
	%4 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %1, i32 %2)
	ret %_Pshadow_Pstandard_CObject* %4
}

define i32 @_Pshadow_Pstandard_CArray_Mdims(%_Pshadow_Pstandard_CArray*) {
	%2 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 3
	%3 = load { i32*, [1 x i32] }* %2
	%4 = extractvalue { i32*, [1 x i32] } %3, 1, 0
	ret i32 %4
}

declare void @abort() noreturn

define private i32 @computeIndex(%"_Pshadow_Pstandard_CArray"*, { i32*, [1 x i32] }) alwaysinline {	
	%3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 3	
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

define %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    %lengths = alloca { %int*, [1 x %int] }
    %3 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %3, %"_Pshadow_Pstandard_CArray"** %this
    store { %int*, [1 x %int] } %1, { %int*, [1 x %int] }* %lengths
    %4 = load %"_Pshadow_Pstandard_CArray"** %this
    %5 = bitcast %"_Pshadow_Pstandard_CArray"* %4 to %"_Pshadow_Pstandard_CObject"*
    %6 = call %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_CObject_Mcreate"(%"_Pshadow_Pstandard_CObject"* %5)
    %7 = load %"_Pshadow_Pstandard_CArray"** %this		
    %8 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %7, i32 0, i32 2
    store %"_Pshadow_Pstandard_CObject"* null, %"_Pshadow_Pstandard_CObject"** %8
    %9 = load %"_Pshadow_Pstandard_CArray"** %this
    %10 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %9, i32 0, i32 3
    %11 = insertvalue { %int*, [1 x %int] } zeroinitializer, %int* null, 0
    store { %int*, [1 x %int] } %11, { %int*, [1 x %int] }* %10
    %12 = load %"_Pshadow_Pstandard_CArray"** %this
    %13 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %12, i32 0, i32 3
    %14 = load { %int*, [1 x %int] }* %lengths
    store { %int*, [1 x %int] } %14, { %int*, [1 x %int] }* %13
    %15 = load %"_Pshadow_Pstandard_CArray"** %this
    %16 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %15, i32 0, i32 2
    %17 = load %"_Pshadow_Pstandard_CArray"** %this    
    %18 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 0
    %19 = load %"_Pshadow_Pstandard_CClass"** %18
    %20 = bitcast %"_Pshadow_Pstandard_CClass"* %19 to %"_Pshadow_Pstandard_CArrayClass"*
	%21 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %20, i32 0, i32 9
    %22 = load %"_Pshadow_Pstandard_CClass"** %21
    %23 = getelementptr %"_Pshadow_Pstandard_CArray"* %17, i32 0, i32 1
    %24 = load %"_Pshadow_Pstandard_CArray_methods"** %23
    %25 = getelementptr %"_Pshadow_Pstandard_CArray_methods"* %24, i32 0, i32 9
    %26 = load %int (%"_Pshadow_Pstandard_CArray"*)** %25
    %27 = call %int (%"_Pshadow_Pstandard_CArray"*)* %26(%"_Pshadow_Pstandard_CArray"* %17)
	%28 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %22, i32 %27)		
    store %"_Pshadow_Pstandard_CObject"* %28, %"_Pshadow_Pstandard_CObject"** %16
    %29 = bitcast %"_Pshadow_Pstandard_CObject"* %0 to %"_Pshadow_Pstandard_CArray"*
    ret %"_Pshadow_Pstandard_CArray"* %29
}

define %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArray_MgetBaseClass"(%"_Pshadow_Pstandard_CArray"*) {
    %this = alloca %"_Pshadow_Pstandard_CArray"*
    store %"_Pshadow_Pstandard_CArray"* %0, %"_Pshadow_Pstandard_CArray"** %this
    %2 = load %"_Pshadow_Pstandard_CArray"** %this
    %3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %2, i32 0, i32 0
    %4 = load %"_Pshadow_Pstandard_CClass"** %3
    %5 = bitcast %"_Pshadow_Pstandard_CClass"* %4 to %"_Pshadow_Pstandard_CArrayClass"*
    %6 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %5, i32 0, i32 9
    %7 = load %"_Pshadow_Pstandard_CClass"** %6    
    ret %"_Pshadow_Pstandard_CClass"* %7
}


define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32 ) {
	%3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	
	; get array class
	%4 = load %"_Pshadow_Pstandard_CClass"** %3
	%5 = bitcast %"_Pshadow_Pstandard_CClass"* %4 to %"_Pshadow_Pstandard_CArrayClass"*	
	
	; index into internal representation
	%6 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %5, i32 0, i32 9
	%7 = load %"_Pshadow_Pstandard_CClass"** %6
	%8 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %7, i32 0, i32 6
	
	; get generic class flag
	%9 = load i32* %8
	%10 = and i32 %9, 2	
	%11 = icmp eq i32 %10, 0	
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%13 = load %_Pshadow_Pstandard_CObject** %12	
	
	; if flag does not contain 2 (primitive), proceed with array check
	br i1 %11, label %14, label %21
	
	; get array class flag	
	%15 = and i32 %9, 8
	%16 = icmp eq i32 %15, 0
	
	; if flag does not contain 8, proceed with regular object
	br i1 %16, label %17, label %34
	
	%18 = bitcast %_Pshadow_Pstandard_CObject* %13 to %_Pshadow_Pstandard_CObject**
	%19 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %18, i32 %1
	%20 = load %_Pshadow_Pstandard_CObject** %19
	ret %_Pshadow_Pstandard_CObject* %20	

	; deal with primitive type
	; first get correct method table	
	%22 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %5, i32 0, i32 8, i32 0
	%23 = load %"_Pshadow_Pstandard_CObject"*** %22
	%24 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %23, i32 1
	%25 = load %"_Pshadow_Pstandard_CObject"** %24
	%26 = bitcast %"_Pshadow_Pstandard_CObject"* %25 to %"_Pshadow_Pstandard_CObject_methods"*	
	
	; create new wrapper object
	%27 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %7, %"_Pshadow_Pstandard_CObject_methods"* %26)
	
	; copy primitive value into new object
	%28 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %27, i32 1
	%29 = bitcast %_Pshadow_Pstandard_CObject* %28 to i8*
	
	%30 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %7)	
	%31 = mul i32 %1, %30		
	%32 = bitcast %_Pshadow_Pstandard_CObject* %13 to i8*
	%33 = getelementptr inbounds i8* %32, i32 %31
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %29, i8* %33, i32 %30, i32 1, i1 0)
	ret %_Pshadow_Pstandard_CObject* %27
	
	; get generic class parameter T out of generic T[] class	
	%35 = getelementptr inbounds %"_Pshadow_Pstandard_CClass"* %7, i32 0, i32 3
	%36 = load %"_Pshadow_Pstandard_CClass"** %35
	
	; now find class Array<T>
	%37 = load { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] }*  @_generics_Pshadow_Pstandard_CArray
	%38 = call %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CArrayClass_MfindClass_Pshadow_Pstandard_CGenericClass_A1_Pshadow_Pstandard_CClass"(%"_Pshadow_Pstandard_CArrayClass"* null, { %"_Pshadow_Pstandard_CGenericClass"**, [1 x %int] } %37, %"_Pshadow_Pstandard_CClass"* %36)
	
	; create new Array wrapper
	%39 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %38, %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CArray_methods"* @"_Pshadow_Pstandard_CArray_methods" to %"_Pshadow_Pstandard_CObject_methods"*))
	
	; get size member of array class, which is equal to dimensions
	%40 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %7, i32 0, i32 7
	%41 = load i32* %40
	
	; allocate new array for dimensions
	%42 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %41)
	
	; get element (which is an array)
	%43 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %7)		
	%44 = mul i32 %1, %43	
	%45 = bitcast %_Pshadow_Pstandard_CObject* %13 to i8*
	
	; get offset in bytes
	%46 = getelementptr inbounds i8* %45, i32 %44
	
	; cast to object*** because an array is { Object**, [dimensions x int] }
	%47 = bitcast i8* %46 to %_Pshadow_Pstandard_CObject***
	%48 = load %_Pshadow_Pstandard_CObject*** %47
	%49 = bitcast %_Pshadow_Pstandard_CObject** %48 to %_Pshadow_Pstandard_CObject*
	
	; skip past object pointer to dimensions
	%50 = getelementptr inbounds %_Pshadow_Pstandard_CObject*** %47, i32 1
	%51 = bitcast %_Pshadow_Pstandard_CObject*** %50 to i32*
	
	; copy dimensions into allocated space
	%52 = bitcast %_Pshadow_Pstandard_CObject* %42 to i32*
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %52, i32* %51, i32 %41, i32 0, i1 0)
	
	; make lengths array
	%53 = insertvalue { i32*, [1 x i32] } undef, i32* %51, 0
	%54 = insertvalue { i32*, [1 x i32] } %53, i32 %41, 1, 0
	
	; initialize Array<T> object
	%55 = call %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* %39, { %int*, [1 x %int] } %54, %"_Pshadow_Pstandard_CObject"* %49)
	
	; Object that just got initialized
	ret %_Pshadow_Pstandard_CObject* %39
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) alwaysinline {
	%3 = call i32 @computeIndex(%"_Pshadow_Pstandard_CArray"* %0, { i32*, [1 x i32] } %1)	
	%4 = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray* %0, i32 %3)
	ret %_Pshadow_Pstandard_CObject* %4
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, i32, %_Pshadow_Pstandard_CObject*) {
	%4 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	
	; get array class
	%5 = load %"_Pshadow_Pstandard_CClass"** %4
	%6 = bitcast %"_Pshadow_Pstandard_CClass"* %5 to %"_Pshadow_Pstandard_CArrayClass"*	
	%7 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %6, i32 0, i32 9
	
	%8 = load %"_Pshadow_Pstandard_CClass"** %7
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %8, i32 0, i32 6	
	
	; get generic class flag
	%10 = load i32* %9
	%11 = and i32 %10, 2	
	%12 = icmp eq i32 %11, 0	
	%13 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%14 = load %_Pshadow_Pstandard_CObject** %13
	br i1 %12, label %15, label %21
	
	; check for array class
	%16 = and i32 %10, 8	
	%17 = icmp eq i32 %16, 0
	br i1 %17, label %18, label %28
	
	%19 = bitcast %_Pshadow_Pstandard_CObject* %14 to %_Pshadow_Pstandard_CObject**
	%20 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %19, i32 %1
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %20
	ret void
	
	%22 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %8)
	%23 = mul i32 %1, %22	
	%24 = bitcast %_Pshadow_Pstandard_CObject* %14 to i8*
	%25 = getelementptr inbounds i8* %24, i32 %23
	%26 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 1
	%27 = bitcast %_Pshadow_Pstandard_CObject* %26 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %25, i8* %27, i32 %22, i32 1, i1 0)
	ret void
	
	; get the location inside the current Array<T>
	%29 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %8)	
	%30 = mul i32 %1, %29	
	%31 = bitcast %_Pshadow_Pstandard_CObject* %14 to i8*
	%32 = getelementptr inbounds i8* %31, i32 %30
		
	; get the array data from the input Array<T> (which it must be)
	%33 = bitcast %_Pshadow_Pstandard_CObject* %2 to %"_Pshadow_Pstandard_CArray"*
	%34 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %33, i32 0, i32 2
	%35 = load %"_Pshadow_Pstandard_CObject"** %34
				
	; store it into the array
	%36 = bitcast i8* %32 to %"_Pshadow_Pstandard_CObject"**
	store %"_Pshadow_Pstandard_CObject"* %35, %"_Pshadow_Pstandard_CObject"** %36	
	
	; store length data
	%37 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %33, i32 0, i32 3, i32 1, i32 0
	%38 = load i32* %37
	%39 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %33, i32 0, i32 3, i32 0
	%40 = load i32** %39
	
	; skip past the pointer to the length data
	%41 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %36, i32 1
	%42 = bitcast %"_Pshadow_Pstandard_CObject"** %41 to i32*
	
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %42, i32* %40, i32 %38, i32 0, i1 0)	
	ret void
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*) alwaysinline {
	%4 = call i32 @computeIndex(%_Pshadow_Pstandard_CArray* %0, { i32*, [1 x i32] } %1)	
	call void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray* %0, i32 %4, %_Pshadow_Pstandard_CObject* %2)
	ret void
}

; doesn't really work for multidimensional arrays
define noalias %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32, i32) {
	; get class from original array
	%4 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	%5 = load %"_Pshadow_Pstandard_CClass"** %4		
	; get method table	from original array
	%6 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 1		
	%7 = load %"_Pshadow_Pstandard_CArray_methods"** %6
	%8 = bitcast %"_Pshadow_Pstandard_CArray_methods"* %7 to %"_Pshadow_Pstandard_CObject_methods"*
	
	; allocate new array object
	%9 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* %5, %"_Pshadow_Pstandard_CObject_methods"* %8)

	; get internal generic class
	%10 = bitcast %"_Pshadow_Pstandard_CClass"* %5 to %"_Pshadow_Pstandard_CArrayClass"*	
	%11 = getelementptr inbounds %"_Pshadow_Pstandard_CArrayClass"* %10, i32 0, i32 9	
	%12 = load %"_Pshadow_Pstandard_CClass"** %11	
	
	; get number of dimensions
	%13 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 1, i32 0
	%14 = load i32* %13	
	
	; create array for dimensions
	%15 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %14)
	%16 = bitcast %_Pshadow_Pstandard_CObject* %15 to i32*
	
	; get difference between indexes
	%17 = sub i32 %2, %1
	
	; store difference (size) in the first position in the new array
	store i32 %17, i32* %16
		
	; get next position in the new array
	%18 = getelementptr i32* %16, i32 1
	
	; get pointer to the array of integers storing the size of the original array, skipping the first one
	%19 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 0
	%20 = load i32** %19
	%21 = getelementptr i32* %20, i32 1
	
	; subtract 1 from the number of dimensions
	%22 = sub i32 %14, 1	
	
	; copy the data from the second dimension size onward
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %18, i32* %21, i32 %22, i32 0, i1 0)

	; put new dimension data into array
	%23 = insertvalue { i32*, [1 x i32] } undef, i32* %16, 0
	
	; put number of dimensions into array
	%24 = insertvalue { i32*, [1 x i32] } %23, i32 %14, 1, 0

	; get array data
	%25 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%26 = load %_Pshadow_Pstandard_CObject** %25
	
	; cast to char*
	%27 = bitcast %_Pshadow_Pstandard_CObject* %26 to i8*
	
	; get type parameter width
	%28 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %12)		
	
	; multiply starting point by width
	%29 = mul i32 %1, %28
	
	; index into starting location
	%30 = getelementptr i8* %27, i32 %29
	%31 = bitcast i8* %30 to %_Pshadow_Pstandard_CObject*
	%32 = call %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CObject* %9, { i32*, [1 x i32] } %24, %_Pshadow_Pstandard_CObject* %31)
		
	ret %_Pshadow_Pstandard_CArray* %32
}
