; shadow.utility@Random native methods

%_Pshadow_Putility_CRandom = type opaque

declare void @srand(i32) nounwind
declare i32 @rand() nounwind

define void @_Pshadow_Putility_CRandom_Mseed_Pshadow_Pstandard_Culong(%_Pshadow_Putility_CRandom*, i64) {
	%3 = trunc i64 %1 to i32
	%4 = lshr i64 %1, 32
	%5 = trunc i64 %1 to i32
	%6 = xor i32 %3, %5
	tail call void @srand(i32 %6) nounwind
	ret void
}

define i32 @_Pshadow_Putility_CRandom_MnextUInt(%_Pshadow_Putility_CRandom*) {
	%2 = tail call i32 @rand() nounwind
	ret i32 %2
}
