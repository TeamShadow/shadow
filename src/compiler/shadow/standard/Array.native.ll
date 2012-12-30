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

declare %_Pshadow_Pstandard_CArray* @_Pshadow_Pstandard_CArray_Mcreate_Pshadow_Pstandard_Cint_A1_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CArray*, %_Pshadow_Pstandard_CClass*, { i32*, [1 x i32] }, %_Pshadow_Pstandard_CObject*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*)
declare noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32)
declare void @llvm.memcpy.p0i8.p0i8.i32(i8*, i8*, i32, i32, i1)
declare void @llvm.memcpy.p0i32.p0i32.i32(i32*, i32*, i32, i32, i1)

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mallocate_Pshadow_Pstandard_CClass_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, %_Pshadow_Pstandard_CClass*, i32) {
	%4 = tail call noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass* %1, i32 %2)
	ret %_Pshadow_Pstandard_CObject* %4
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32) {
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

define void @_Pshadow_Pstandard_CArray_Mindex_Pshadow_Pstandard_Cint_CT(%_Pshadow_Pstandard_CArray*, i32, %_Pshadow_Pstandard_CObject*) {
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
