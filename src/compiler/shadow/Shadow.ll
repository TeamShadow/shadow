%_Pshadow_Ptest_CArrayTest = type opaque
declare void @_Pshadow_Ptest_CArrayTest_Mmain(%_Pshadow_Ptest_CArrayTest*)

define i32 @main(i32, i8*) {
	call void @_Pshadow_Ptest_CArrayTest_Mmain(%_Pshadow_Ptest_CArrayTest* null)
	ret i32 0
}

;define i64 @shadow.generic.load(i8* %class, i8* %ptr, i8* %index) {
;	ret i64 0
;}

;define void @shadow.generic.store(i8* %class, i8* %ptr, i8* %index, i64 %value) {
;	ret void
;}
