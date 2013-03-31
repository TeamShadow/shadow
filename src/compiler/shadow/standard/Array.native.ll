; shadow.standard@Array native methods

%_Pshadow_Pstandard_CObject_Mclass = type { %_Pshadow_Pstandard_CClass }
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CString = type opaque
%_Pshadow_Pstandard_CInterface = type opaque
%_Pshadow_Pstandard_CClass_Mclass = type opaque
%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass*, { %_Pshadow_Pstandard_CClass**, [1 x i32] }, %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CString*, %_Pshadow_Pstandard_CClass*, i32, i32, i32 }
%_Pshadow_Pstandard_CArray_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CArray_Mclass = external constant %_Pshadow_Pstandard_CArray_Mclass
%_Pshadow_Pstandard_CArray = type { %_Pshadow_Pstandard_CArray_Mclass*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CClass*, %_Pshadow_Pstandard_CObject* }
%_Pshadow_Pstandard_Cint_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_Cint_Mclass = external constant %_Pshadow_Pstandard_Cint_Mclass
%_Pshadow_Pstandard_CException_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CException* (%_Pshadow_Pstandard_CException*)*, i1 (%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CException* (%_Pshadow_Pstandard_CException*, %_Pshadow_Pstandard_CString*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)*, void (%_Pshadow_Pstandard_CException*)* }
@_Pshadow_Pstandard_CException_Mclass = external constant %_Pshadow_Pstandard_CException_Mclass
%_Pshadow_Pstandard_CException = type { %_Pshadow_Pstandard_CException_Mclass* }
%_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass = type { %_Pshadow_Pstandard_CClass }
@_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass = external constant %_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass
%_Pshadow_Pstandard_CIndexOutOfBoundsException = type opaque

declare %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, %_Pshadow_Pstandard_CClass*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare %_Pshadow_Pstandard_CIndexOutOfBoundsException* @_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate(%_Pshadow_Pstandard_CIndexOutOfBoundsException*)
declare void @"_Pshadow_Pstandard_CException_Mthrow__"(%"_Pshadow_Pstandard_CException"* nocapture) noreturn
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, %_Pshadow_Pstandard_CClass*, i32) {
	%4 = tail call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %1, i32 %2)
	ret %_Pshadow_Pstandard_CObject* %4
}

declare void @abort() noreturn

define private i32 @computeIndex(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) alwaysinline {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 1
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
	%ex.obj = call %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass* @_Pshadow_Pstandard_CIndexOutOfBoundsException_Mclass, i32 0, i32 0))
	%ex.1 = bitcast %_Pshadow_Pstandard_CObject* %ex.obj to %_Pshadow_Pstandard_CIndexOutOfBoundsException*
	%ex.2 = call %_Pshadow_Pstandard_CIndexOutOfBoundsException* @_Pshadow_Pstandard_CIndexOutOfBoundsException_Mcreate(%_Pshadow_Pstandard_CIndexOutOfBoundsException* %ex.1)
	%ex.3 = bitcast %_Pshadow_Pstandard_CIndexOutOfBoundsException* %ex.2 to %_Pshadow_Pstandard_CException*
	%ex.class.ptr = getelementptr inbounds %_Pshadow_Pstandard_CException* %ex.3, i32 0, i32 0
	%ex.class = load %_Pshadow_Pstandard_CException_Mclass** %ex.class.ptr
	%ex.throw.ptr = getelementptr inbounds %_Pshadow_Pstandard_CException_Mclass* %ex.class, i32 0, i32 9
	%ex.throw = load void (%_Pshadow_Pstandard_CException*)** %ex.throw.ptr
	call void %ex.throw(%_Pshadow_Pstandard_CException* %ex.3)
	unreachable
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }) {
	%3 = call i32 @computeIndex(%_Pshadow_Pstandard_CArray* %0, { i32*, [1 x i32] } %1)
	%4 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%5 = load %_Pshadow_Pstandard_CClass** %4
	%6 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %5, i32 0, i32 5
	%7 = load i32* %6
	%8 = and i32 %7, 2
	%9 = icmp eq i32 %8, 0
	%10 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3
	%11 = load %_Pshadow_Pstandard_CObject** %10
	br i1 %9, label %12, label %16
	%13 = bitcast %_Pshadow_Pstandard_CObject* %11 to %_Pshadow_Pstandard_CObject**
	%14 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %13, i32 %3
	%15 = load %_Pshadow_Pstandard_CObject** %14
	ret %_Pshadow_Pstandard_CObject* %15
	%17 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %5)
	%18 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %17, i32 1
	%19 = bitcast %_Pshadow_Pstandard_CObject* %18 to i8*
	%20 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %5, i32 0, i32 7
	%21 = load i32* %20
	%22 = mul i32 %3, %21
	%23 = bitcast %_Pshadow_Pstandard_CObject* %11 to i8*
	%24 = getelementptr inbounds i8* %23, i32 %22
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %19, i8* %24, i32 %21, i32 1, i1 0)
	ret %_Pshadow_Pstandard_CObject* %17
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT(%_Pshadow_Pstandard_CArray*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*) {
	%4 = call i32 @computeIndex(%_Pshadow_Pstandard_CArray* %0, { i32*, [1 x i32] } %1)
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%6 = load %_Pshadow_Pstandard_CClass** %5
	%7 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %6, i32 0, i32 5
	%8 = load i32* %7
	%9 = and i32 %8, 2
	%10 = icmp eq i32 %9, 0
	%11 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3
	%12 = load %_Pshadow_Pstandard_CObject** %11
	br i1 %10, label %13, label %16
	%14 = bitcast %_Pshadow_Pstandard_CObject* %12 to %_Pshadow_Pstandard_CObject**
	%15 = getelementptr inbounds %_Pshadow_Pstandard_CObject** %14, i32 %4
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %15
	ret void
	%17 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %6, i32 0, i32 7
	%18 = load i32* %17
	%19 = mul i32 %4, %18
	%20 = bitcast %_Pshadow_Pstandard_CObject* %12 to i8*
	%21 = getelementptr inbounds i8* %20, i32 %19
	%22 = getelementptr inbounds %_Pshadow_Pstandard_CObject* %2, i32 1
	%23 = bitcast %_Pshadow_Pstandard_CObject* %22 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %21, i8* %23, i32 %18, i32 1, i1 0)
	ret void
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_old(%_Pshadow_Pstandard_CArray*, i32) {
	call void @abort() noreturn
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%4 = load %_Pshadow_Pstandard_CClass** %3
	%5 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %4, i32 0, i32 7
	%6 = load i32* %5
	%7 = mul i32 %1, %6
	%8 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3
	%9 = load %_Pshadow_Pstandard_CObject** %8
	%10 = bitcast %_Pshadow_Pstandard_CObject* %9 to i8*
	%11 = getelementptr i8* %10, i32 %7
	%12 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %4, i32 0, i32 5
	%13 = load i32* %12
	%14 = lshr i32 %13, 1
	%15 = trunc i32 %14 to i1
	br i1 %15, label %19, label %16
	%17 = bitcast i8* %11 to %_Pshadow_Pstandard_CObject**
	%18 = load %_Pshadow_Pstandard_CObject** %17
	ret %_Pshadow_Pstandard_CObject* %18
	%20 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* %4)
	%21 = getelementptr %_Pshadow_Pstandard_CObject* %20, i32 1
	%22 = bitcast %_Pshadow_Pstandard_CObject* %21 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %22, i8* %11, i32 %6, i32 0, i1 0)
	ret %_Pshadow_Pstandard_CObject* %20
}

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_A1_CT_old(%_Pshadow_Pstandard_CArray*, i32, %_Pshadow_Pstandard_CObject*) {
	call void @abort() noreturn
	%4 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%5 = load %_Pshadow_Pstandard_CClass** %4
	%6 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %5, i32 0, i32 7
	%7 = load i32* %6
	%8 = mul i32 %1, %7
	%9 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3
	%10 = load %_Pshadow_Pstandard_CObject** %9
	%11 = bitcast %_Pshadow_Pstandard_CObject* %10 to i8*
	%12 = getelementptr i8* %11, i32 %8
	%13 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %5, i32 0, i32 5
	%14 = load i32* %13
	%15 = lshr i32 %14, 1
	%16 = trunc i32 %15 to i1
	br i1 %16, label %19, label %17
	%18 = bitcast i8* %12 to %_Pshadow_Pstandard_CObject**
	store %_Pshadow_Pstandard_CObject* %2, %_Pshadow_Pstandard_CObject** %18
	ret void
	%20 = getelementptr %_Pshadow_Pstandard_CObject* %2, i32 1
	%21 = bitcast %_Pshadow_Pstandard_CObject* %20 to i8*
	call void @llvm.memcpy.p0i8.p0i8.i32(i8* %12, i8* %21, i32 %7, i32 0, i1 0)
	ret void
}

define noalias %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Msubarray_Pshadow_Pstandard_Cint_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32, i32) {
	%4 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_CArray_Mclass* @_Pshadow_Pstandard_CArray_Mclass, i32 0, i32 0))
	%5 = bitcast %_Pshadow_Pstandard_CObject* %4 to %_Pshadow_Pstandard_CArray*
	%6 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 2
	%7 = load %_Pshadow_Pstandard_CClass** %6
	%8 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 1, i32 1, i32 0
	%9 = load i32* %8
	%10 = call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* getelementptr inbounds (%_Pshadow_Pstandard_Cint_Mclass* @_Pshadow_Pstandard_Cint_Mclass, i32 0, i32 0), i32 %9)
	%11 = bitcast %_Pshadow_Pstandard_CObject* %10 to i32*
	%12 = sub i32 %2, %1
	store i32 %12, i32* %11
	%13 = getelementptr i32* %11, i32 1
	%14 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 1, i32 0
	%15 = load i32** %14
	%16 = getelementptr i32* %15, i32 1
	%17 = sub i32 %9, 1
	call void @llvm.memcpy.p0i32.p0i32.i32(i32* %13, i32* %16, i32 %17, i32 0, i1 0)
	%18 = insertvalue { i32*, [1 x i32] } undef, i32* %11, 0
	%19 = insertvalue { i32*, [1 x i32] } %18, i32 %9, 1, 0
	%20 = getelementptr inbounds %_Pshadow_Pstandard_CArray* %0, i32 0, i32 3
	%21 = load %_Pshadow_Pstandard_CObject** %20
	%22 = bitcast %_Pshadow_Pstandard_CObject* %21 to i8*
	%23 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %7, i32 0, i32 7
	%24 = load i32* %23
	%25 = mul i32 %1, %24
	%26 = getelementptr i8* %22, i32 %25
	%27 = bitcast i8* %26 to %_Pshadow_Pstandard_CObject*
	%28 = tail call %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray* %5, %_Pshadow_Pstandard_CClass* %7, { i32*, [1 x i32] } %19, %_Pshadow_Pstandard_CObject* %27)
	ret %_Pshadow_Pstandard_CArray* %28
}
