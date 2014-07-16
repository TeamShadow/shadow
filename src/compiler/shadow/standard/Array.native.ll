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
%"_Pshadow_Pstandard_CObject_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CObject" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CObject_methods"*  }
%"_Pshadow_Pstandard_CClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int }
%"_Pshadow_Pstandard_CGenericClass_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CClass"*)*, %uint (%"_Pshadow_Pstandard_CClass"*)*, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] } (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CClass"*)*, %boolean (%"_Pshadow_Pstandard_CGenericClass"*, %"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CClass"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)*, %int (%"_Pshadow_Pstandard_CClass"*)* }
%"_Pshadow_Pstandard_CGenericClass" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CGenericClass_methods"* , %"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CClass"*, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }, { %"_Pshadow_Pstandard_CClass"**, [1 x %int] }, %int, %int, { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } }
%"_Pshadow_Pstandard_CIterator_methods" = type { %boolean (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)* }
%"_Pshadow_Pstandard_CString_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, { %byte*, [1 x %int] } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %boolean (%"_Pshadow_Pstandard_CString"*, %"_Pshadow_Pstandard_CString"*)*, %uint (%"_Pshadow_Pstandard_CString"*)*, %byte (%"_Pshadow_Pstandard_CString"*, %int)*, %boolean (%"_Pshadow_Pstandard_CString"*)*, { %"_Pshadow_Pstandard_CIterator_methods"*, %"_Pshadow_Pstandard_CObject"* } (%"_Pshadow_Pstandard_CString"*)*, %int (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*, %int, %int)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CString"*)* }
%"_Pshadow_Pstandard_CString" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CString_methods"* , { %byte*, [1 x %int] }, %boolean }

@"_Pshadow_Pstandard_CClass_methods" = external constant %"_Pshadow_Pstandard_CClass_methods"
@"_Pshadow_Pstandard_CClass_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CString_methods" = external constant %"_Pshadow_Pstandard_CString_methods"
@"_Pshadow_Pstandard_CString_class" = external constant %"_Pshadow_Pstandard_CClass"

%"_Pshadow_Pstandard_CException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CException_methods"* , %"_Pshadow_Pstandard_CString"* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CException"*)* }
%"_Pshadow_Pstandard_CIndexOutOfBoundsException" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* , %"_Pshadow_Pstandard_CString"* }

@"_Pshadow_Pstandard_CIndexOutOfBoundsException_class" = external constant %"_Pshadow_Pstandard_CClass"
@"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" = external constant %"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"

%"_Pshadow_Pstandard_CArray_methods" = type { %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] })*, void (%"_Pshadow_Pstandard_CArray"*, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)*, { %int*, [1 x %int] } (%"_Pshadow_Pstandard_CArray"*)*, %int (%"_Pshadow_Pstandard_CArray"*)*, %"_Pshadow_Pstandard_CArray"* (%"_Pshadow_Pstandard_CArray"*, %int, %int)* }
%"_Pshadow_Pstandard_CArray" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pstandard_CArray_methods"* , %"_Pshadow_Pstandard_CObject"*, { %int*, [1 x %int] } }
@"_Pshadow_Pstandard_Cint_class" = external constant %"_Pshadow_Pstandard_CClass"


declare %"_Pshadow_Pstandard_CArray"* @"_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject"(%"_Pshadow_Pstandard_CObject"* returned, { %int*, [1 x %int] }, %"_Pshadow_Pstandard_CObject"*)
;declare %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare %_Pshadow_Pstandard_CIndexOutOfBoundsException* @_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate(%_Pshadow_Pstandard_CIndexOutOfBoundsException*)
; declare void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"* nocapture) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)
declare %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"*)

; debug
%"_Pshadow_Pio_CConsole_methods" = type { %"_Pshadow_Pstandard_CObject"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CClass"* (%"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pstandard_CString"* (%"_Pshadow_Pstandard_CObject"*)*, void (%"_Pshadow_Pio_CConsole"*, %int)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CString"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*, %"_Pshadow_Pstandard_CObject"*)*, %"_Pshadow_Pio_CConsole"* (%"_Pshadow_Pio_CConsole"*)*, { %byte, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %code, %boolean } (%"_Pshadow_Pio_CConsole"*)*, { %"_Pshadow_Pstandard_CString"*, %boolean } (%"_Pshadow_Pio_CConsole"*)* }
%"_Pshadow_Pio_CConsole" = type { %"_Pshadow_Pstandard_CClass"*, %"_Pshadow_Pio_CConsole_methods"* , %boolean }
@"_Pshadow_Pio_CConsole_instance" = external global %"_Pshadow_Pio_CConsole"*
declare void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"*, %int)

;define private void @_debugPrint(%int) alwaysinline {
	;%2 = load %"_Pshadow_Pio_CConsole"** @"_Pshadow_Pio_CConsole_instance"
	;call void @"_Pshadow_Pio_CConsole_MdebugPrint_Pshadow_Pstandard_Cint"(%"_Pshadow_Pio_CConsole"* %2, %int %0)
	;ret void
;}


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
	%ex.obj = call %"_Pshadow_Pstandard_CObject"* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* @_Pshadow_Pstandard_CIndexOutOfBoundsException_class)
	%ex.1 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %ex.obj, i32 0, i32 0
	store %"_Pshadow_Pstandard_CClass"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_class", %"_Pshadow_Pstandard_CClass"** %ex.1
	%ex.2 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %ex.obj, i32 0, i32 1	
	store %"_Pshadow_Pstandard_CObject_methods"* bitcast(%"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_methods" to %"_Pshadow_Pstandard_CObject_methods"*), %"_Pshadow_Pstandard_CObject_methods"** %ex.2
	%ex.3 = bitcast %_Pshadow_Pstandard_CObject* %ex.obj to %_Pshadow_Pstandard_CIndexOutOfBoundsException*
	%ex.4 = call %"_Pshadow_Pstandard_CIndexOutOfBoundsException"* @"_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate"(%"_Pshadow_Pstandard_CIndexOutOfBoundsException"* %ex.3)
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %ex.obj) noreturn	
	unreachable
}


define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) {
	%3 = call i32 @computeIndex(%"_Pshadow_Pstandard_CArray"* %0, { i32*, [1 x i32] } %1)	
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
	
	; if flag does not contain 2, proceed with Object type (perhaps array or method should be dealt with some day)
	br i1 %14, label %17, label %21
	%18 = bitcast %_Pshadow_Pstandard_CObject* %16 to %_Pshadow_Pstandard_CObject**
	%19 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %18, i32 %3
	%20 = load %_Pshadow_Pstandard_CObject** %19
	ret %_Pshadow_Pstandard_CObject* %20	
	
	; deal with primitive type
	; create new wrapper object
	%22 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %10)
	
	; store type into new object
	%23 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %22, i32 0, i32 0
	store %"_Pshadow_Pstandard_CClass"* %10, %"_Pshadow_Pstandard_CClass"** %23
	
	; store method table into new object
	%24 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %22, i32 0, i32 1		
	%25 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"** %8, i32 1
	%26 = load %"_Pshadow_Pstandard_CObject"** %25
	%27 = bitcast %"_Pshadow_Pstandard_CObject"* %26 to %"_Pshadow_Pstandard_CObject_methods"*	
	store %"_Pshadow_Pstandard_CObject_methods"* %27, %"_Pshadow_Pstandard_CObject_methods"** %24	
	
	; copy primitive value into new object
	%28 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %22, i32 1
	%29 = bitcast %_Pshadow_Pstandard_CObject* %28 to i8*
	
	%30 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %10)		
	%31 = mul i32 %3, %30
	%32 = bitcast %_Pshadow_Pstandard_CObject* %16 to i8*
	%33 = getelementptr inbounds i8* %32, i32 %31
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %29, i8* %33, i32 %30, i32 1, i1 0)
	ret %_Pshadow_Pstandard_CObject* %22
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*) {
	%4 = call i32 @computeIndex(%_Pshadow_Pstandard_CArray* %0, { i32*, [1 x i32] } %1)
	
	%5 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	
	; get array class
	%6 = load %"_Pshadow_Pstandard_CClass"** %5
	%7 = bitcast %"_Pshadow_Pstandard_CClass"* %6 to %"_Pshadow_Pstandard_CGenericClass"*	
	%8 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass"* %7, i32 0, i32 8, i32 0
	
	; index into first generic class
	%9 = load %"_Pshadow_Pstandard_CObject"*** %8
	%10 = load %"_Pshadow_Pstandard_CObject"** %9	
	%11 = bitcast %"_Pshadow_Pstandard_CObject"* %10 to %"_Pshadow_Pstandard_CClass"*
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %11, i32 0, i32 6
	
	; get generic class flag
	%13 = load i32* %12
	%14 = and i32 %13, 2	
	%15 = icmp eq i32 %14, 0	
	%16 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%17 = load %_Pshadow_Pstandard_CObject** %16
	br i1 %15, label %18, label %21
	%19 = bitcast %_Pshadow_Pstandard_CObject* %17 to %_Pshadow_Pstandard_CObject**
	%20 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %19, i32 %4
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %20
	ret void
	%22 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %11)	
	%23 = mul i32 %4, %22
	%24 = bitcast %_Pshadow_Pstandard_CObject* %17 to i8*
	%25 = getelementptr inbounds i8* %24, i32 %23
	%26 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 1
	%27 = bitcast %_Pshadow_Pstandard_CObject* %26 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %25, i8* %27, i32 %22, i32 1, i1 0)
	ret void
}

; doesn't really work for multidimensional arrays
define noalias %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32, i32) {
	; get class from original array
	%4 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 0
	%5 = load %"_Pshadow_Pstandard_CClass"** %4
	
	; allocate new array 
	%6 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%"_Pshadow_Pstandard_CClass"* %5)
		
	; store class into new object
	%7 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %6, i32 0, i32 0
	store %"_Pshadow_Pstandard_CClass"* %5, %"_Pshadow_Pstandard_CClass"** %7
	
	; store method table into new object
	%8 = getelementptr inbounds %"_Pshadow_Pstandard_CObject"* %6, i32 0, i32 1		
	%9 = getelementptr inbounds %"_Pshadow_Pstandard_CArray"* %0, i32 0, i32 1		
	%10 = load %"_Pshadow_Pstandard_CArray_methods"** %9
	%11 = bitcast %"_Pshadow_Pstandard_CArray_methods"* %10 to %"_Pshadow_Pstandard_CObject_methods"*
	store %"_Pshadow_Pstandard_CObject_methods"* %11, %"_Pshadow_Pstandard_CObject_methods"** %8	

	; get first generic class
	%12 = bitcast %"_Pshadow_Pstandard_CClass"* %5 to %"_Pshadow_Pstandard_CGenericClass"*	
	%13 = getelementptr inbounds %"_Pshadow_Pstandard_CGenericClass"* %12, i32 0, i32 8	
	%14 = load { %"_Pshadow_Pstandard_CObject"**, [1 x %int] }* %13
	%15 = extractvalue { %"_Pshadow_Pstandard_CObject"**, [1 x %int] } %14, 0
	
	; index into first generic class	
	%16 = load %"_Pshadow_Pstandard_CObject"** %15	
	%17 = bitcast %"_Pshadow_Pstandard_CObject"* %16 to %"_Pshadow_Pstandard_CClass"*
	
	; get number of dimensions
	%18 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 1, i32 0
	%19 = load i32* %18	
	
	; create array for dimensions
	%20 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_Cint_class, i32 %19)
	%21 = bitcast %_Pshadow_Pstandard_CObject* %20 to i32*
	
	; get difference between indexes
	%22 = sub i32 %2, %1
	
	; store difference (size) in the first position in the new array
	store i32 %22, i32* %21
		
	; get next position in the new array
	%23 = getelementptr i32* %21, i32 1
	
	; get pointer to the array of integers storing the size of the original array, skipping the first one
	%24 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3, i32 0
	%25 = load i32** %24
	%26 = getelementptr i32* %25, i32 1
	
	; subtract 1 from the number of dimensions
	%27 = sub i32 %19, 1
	
	; copy the data from the second dimension size onward
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %23, i32* %26, i32 %27, i32 0, i1 0)
	
	; put new dimension data into array
	%28 = insertvalue { i32*, [1 x i32] } undef, i32* %21, 0
	
	; put number of dimensions into array
	%29 = insertvalue { i32*, [1 x i32] } %28, i32 %19, 1, 0
	
	; get array data
	%30 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%31 = load %_Pshadow_Pstandard_CObject** %30
	
	; cast to char*
	%32 = bitcast %_Pshadow_Pstandard_CObject* %31 to i8*
	
	; get type parameter width
	%33 = call %int @"_Pshadow_Pstandard_CClass_Mwidth"(%"_Pshadow_Pstandard_CClass"* %17)		
	
	; multiply starting point by width
	%34 = mul i32 %1, %33
	
	; index into starting location
	%35 = getelementptr i8* %32, i32 %34
	%36 = bitcast i8* %35 to %_Pshadow_Pstandard_CObject*
	%37 = call %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CObject* %6, { i32*, [1 x i32] } %29, %_Pshadow_Pstandard_CObject* %36)
	ret %_Pshadow_Pstandard_CArray* %37
}
