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

%"_Pshadow_Pstandard_CArray_methods" = type { %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %"_Pshadow_Pstandard_CAddressMap"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CArray"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
@"_Pshadow_Pstandard_Cint_class" = external constant %"_Pshadow_Pstandard_CClass"


declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*, %"_Pshadow_Pstandard_CObject_methods"*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CObject"*)

declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"*)

; debug
%"_Pshadow_Pio_CConsole_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, { %byte, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CConsole_methods"* , %boolean }
@"_Pshadow_Pio_CConsole_instance" = external global %"_Pshadow_Pio_CConsole"*
;declare void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)

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


define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32 ) {
	%3 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	
	; get array class
	%4 = load %"_Pshadow_Pstandard_CClass"** %3
	%5 = bitcast %"_Pshadow_Pstandard_CClass"* %4 to %"_Pshadow_Pstandard_CGenericClass"*	
	%6 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass"* %5, i32 0, i32 8, i32 0
	
	; index into first generic class
	%7 = load %"_Pshadow_Pstandard_CObject"*** %6
	%8 = load %"_Pshadow_Pstandard_CObject"** %7	
	%9 = bitcast %"_Pshadow_Pstandard_CObject"* %8 to %"_Pshadow_Pstandard_CClass"*
	%10 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %9, i32 0, i32 6
	
	; get generic class flag
	%11 = load i32* %10
	%12 = and i32 %11, 2	
	%13 = icmp eq i32 %12, 0	
	%14 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%15 = load %_Pshadow_Pstandard_CObject** %14
	
	; if flag does not contain 2, proceed with Object type (perhaps array or method should be dealt with some day)
	br i1 %13, label %16, label %20
	%17 = bitcast %_Pshadow_Pstandard_CObject* %15 to %_Pshadow_Pstandard_CObject**
	%18 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %17, i32 %1
	%19 = load %_Pshadow_Pstandard_CObject** %18
	ret %_Pshadow_Pstandard_CObject* %19	
	
	; deal with primitive type
	; first get correct method table	
	%21 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %7, i32 1
	%22 = load %"_Pshadow_Pstandard_CObject"** %21
	%23 = bitcast %"_Pshadow_Pstandard_CObject"* %22 to %"_Pshadow_Pstandard_CObject_methods"*	
	
	; create new wrapper object
	%24 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %10, %"_Pshadow_Pstandard_CObject_methods"* %23)
	
	; copy primitive value into new object
	%25 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %24, i32 1
	%26 = bitcast %_Pshadow_Pstandard_CObject* %25 to i8*
	
	%27 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %10)		
	%28 = mul i32 %1, %27
	%29 = bitcast %_Pshadow_Pstandard_CObject* %16 to i8*
	%30 = getelementptr inbounds i8* %29, i32 %28
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %26, i8* %30, i32 %27, i32 1, i1 0)
	ret %_Pshadow_Pstandard_CObject* %24
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
	%6 = bitcast %"_Pshadow_Pstandard_CClass"* %5 to %"_Pshadow_Pstandard_CGenericClass"*	
	%7 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass"* %6, i32 0, i32 8, i32 0
	
	; index into first generic class
	%8 = load %"_Pshadow_Pstandard_CObject"*** %7
	%9 = load %"_Pshadow_Pstandard_CObject"** %8	
	%10 = bitcast %"_Pshadow_Pstandard_CObject"* %9 to %"_Pshadow_Pstandard_CClass"*
	%11 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %10, i32 0, i32 6
	
	; get generic class flag
	%12 = load i32* %11
	%13 = and i32 %12, 2	
	%14 = icmp eq i32 %13, 0	
	%15 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%16 = load %_Pshadow_Pstandard_CObject** %15
	br i1 %14, label %17, label %20
	%18 = bitcast %_Pshadow_Pstandard_CObject* %16 to %_Pshadow_Pstandard_CObject**
	%19 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %18, i32 %1
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %19
	ret void
	%21 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %11)	
	%22 = mul i32 %1, %21
	%23 = bitcast %_Pshadow_Pstandard_CObject* %16 to i8*
	%24 = getelementptr inbounds i8* %23, i32 %22
	%25 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 1
	%26 = bitcast %_Pshadow_Pstandard_CObject* %25 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %24, i8* %26, i32 %21, i32 1, i1 0)
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

	; get first generic class
	%10 = bitcast %"_Pshadow_Pstandard_CClass"* %5 to %"_Pshadow_Pstandard_CGenericClass"*	
	%11 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass"* %10, i32 0, i32 8	
	%12 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %11
	%13 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %12, 0
	
	; index into first generic class	
	%14 = load %"_Pshadow_Pstandard_CObject"** %13	
	%15 = bitcast %"_Pshadow_Pstandard_CObject"* %14 to %"_Pshadow_Pstandard_CClass"*
	
	; get number of dimensions
	%16 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 1, i32 0
	%17 = load i32* %16	
	
	; create array for dimensions
	%18 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %17)
	%19 = bitcast %_Pshadow_Pstandard_CObject* %18 to i32*
	
	; get difference between indexes
	%20 = sub i32 %2, %1
	
	; store difference (size) in the first position in the new array
	store i32 %20, i32* %19
		
	; get next position in the new array
	%21 = getelementptr i32* %19, i32 1
	
	; get pointer to the array of integers storing the size of the original array, skipping the first one
	%22 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 0
	%23 = load i32** %22
	%24 = getelementptr i32* %23, i32 1
	
	; subtract 1 from the number of dimensions
	%25 = sub i32 %17, 1	
	
	; copy the data from the second dimension size onward
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %21, i32* %24, i32 %25, i32 0, i1 0)

	; put new dimension data into array
	%26 = insertvalue { i32*, [1 x i32] } undef, i32* %19, 0
	
	; put number of dimensions into array
	%27 = insertvalue { i32*, [1 x i32] } %26, i32 %17, 1, 0

	; get array data
	%28 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%29 = load %_Pshadow_Pstandard_CObject** %28
	
	; cast to char*
	%30 = bitcast %_Pshadow_Pstandard_CObject* %29 to i8*
	
	; get type parameter width
	%31 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %15)		
	
	; multiply starting point by width
	%32 = mul i32 %1, %31
	
	; index into starting location
	%33 = getelementptr i8* %30, i32 %32
	%34 = bitcast i8* %33 to %_Pshadow_Pstandard_CObject*
	%35 = call %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CObject* %9, { i32*, [1 x i32] } %27, %_Pshadow_Pstandard_CObject* %34)
		
	ret %_Pshadow_Pstandard_CArray* %35
}
