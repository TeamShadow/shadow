declare void @"shadow.test@Test$static$main"()

define i32 @main() {
	call void @"shadow.test@Test$static$main"()
	ret i32 0
}

define i64 @shadow.generic.load(i8* %class, i8* %ptr, i8* %index) {
	ret i64 0
}

define void @shadow.generic.store(i8* %class, i8* %ptr, i8* %index, i64 %value) {
	ret void
}
