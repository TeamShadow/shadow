; shadow.standard@Class native methods

%_Pshadow_Pstandard_CObject_Mclass = type opaque
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CClass_Mclass = type opaque
%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass*, { %_Pshadow_Pstandard_CClass**, [1 x i32] }, %_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CString*, %_Pshadow_Pstandard_CClass*, i32, i32, i32 }
%_Pshadow_Pstandard_CInterface = type opaque
%_Pshadow_Pstandard_CString = type opaque

declare noalias i8* @malloc(i32) nounwind
declare noalias i8* @calloc(i32, i32) nounwind

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*) {
	%2 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %0, i32 0, i32 6
	%3 = load i32* %2
	%4 = call noalias i8* @malloc(i32 %3) nounwind
	%5 = bitcast i8* %4 to %_Pshadow_Pstandard_CObject*
	%6 = getelementptr %_Pshadow_Pstandard_CObject* %5, i32 0, i32 0
	%7 = bitcast %_Pshadow_Pstandard_CClass* %0 to %_Pshadow_Pstandard_CObject_Mclass*
	store %_Pshadow_Pstandard_CObject_Mclass* %7, %_Pshadow_Pstandard_CObject_Mclass** %6
	ret %_Pshadow_Pstandard_CObject* %5
}

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32) {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %0, i32 0, i32 7
	%4 = load i32* %3
	%5 = call noalias i8* @calloc(i32 %1, i32 %4)
	%6 = bitcast i8* %5 to %_Pshadow_Pstandard_CObject*
	ret %_Pshadow_Pstandard_CObject* %6
}
