; shadow.standard@Array native methods

%_Pshadow_Pstandard_CArray = type opaque

declare noalias i8* @calloc(i32, i32) nounwind

define i64 @_Pshadow_Pstandard_CArray_McreateArray_R_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CArray*, i32) {
	%3 = call noalias i8* @calloc(i32 %1, i32 8) nounwind
	%4 = ptrtoint i8* %3 to i64
	ret i64 %4
}

define i64 @_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint() {
	ret i64 0
}

define void @_Pshadow_Pstandard_CArray_Mindex_R_Pshadow_Pstandard_Cint_RT(i64) {
	ret void
}
