; shadow.standard@Class native methods

declare i32 @__shadow_personality_v0(...)
declare void @__shadow_throw(%"_Pshadow_Pstandard_CObject"*) noreturn
declare %"_Pshadow_Pstandard_CException"* @__shadow_catch(i8* nocapture) nounwind
declare i32 @llvm.eh.typeid.for(i8*) nounwind readnone

%_Pshadow_Pstandard_CObject_Mclass = type opaque
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }
%_Pshadow_Pstandard_CClass_Mclass = type opaque%_Pshadow_Pstandard_CClass = type { %_Pshadow_Pstandard_CClass_Mclass*, { %_Pshadow_Pstandard_CObject**, [1 x i32] }, { %_Pshadow_Pstandard_CClass**, [1 x i32] }, %_Pshadow_Pstandard_CString*, %_Pshadow_Pstandard_CClass*, i32, i32, i32 }
%_Pshadow_Pstandard_COutOfMemoryException_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)* }
%_Pshadow_Pstandard_COutOfMemoryException = type { %_Pshadow_Pstandard_COutOfMemoryException_Mclass* }
%_Pshadow_Pstandard_CException_Mclass = type { %_Pshadow_Pstandard_CClass, %_Pshadow_Pstandard_CObject* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CClass* (%_Pshadow_Pstandard_CObject*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)*, %_Pshadow_Pstandard_CString* (%_Pshadow_Pstandard_CException*)* }
%_Pshadow_Pstandard_CException = type { %_Pshadow_Pstandard_CException_Mclass* }

@_Pshadow_Pstandard_COutOfMemoryException_Mclass = external constant %_Pshadow_Pstandard_COutOfMemoryException_Mclass

%_Pshadow_Pstandard_CInterface = type opaque
%_Pshadow_Pstandard_CString = type opaque

declare noalias i8* @malloc(i32) nounwind
declare noalias i8* @calloc(i32, i32) nounwind
declare %_Pshadow_Pstandard_COutOfMemoryException* @_Pshadow_Pstandard_COutOfMemoryException_Mcreate(%_Pshadow_Pstandard_CObject*)

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate(%_Pshadow_Pstandard_CClass*) {
	%2 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %0, i32 0, i32 6
	%3 = load i32* %2
	%4 = call noalias i8* @malloc(i32 %3) nounwind
	%5 = bitcast i8* %4 to %_Pshadow_Pstandard_CObject*
	%6 = icmp eq %_Pshadow_Pstandard_CObject* %5, null
	br i1 %6, label %_label0, label %_label1
_label0:
	%7 = call noalias %"_Pshadow_Pstandard_CObject"* @"_Pshadow_Pstandard_CClass_Mallocate"(%"_Pshadow_Pstandard_CClass"* getelementptr (%"_Pshadow_Pstandard_COutOfMemoryException_Mclass"* @"_Pshadow_Pstandard_COutOfMemoryException_Mclass", i32 0, i32 0))
    %8 = call %"_Pshadow_Pstandard_COutOfMemoryException"* (%"_Pshadow_Pstandard_CObject"*)* @"_Pshadow_Pstandard_COutOfMemoryException_Mcreate"(%"_Pshadow_Pstandard_CObject"* %7)
    %9 = bitcast %"_Pshadow_Pstandard_COutOfMemoryException"* %8 to %"_Pshadow_Pstandard_CObject"*
	call void @__shadow_throw(%"_Pshadow_Pstandard_CObject"* %9) noreturn
    unreachable	
_label1:
;	%6 = getelementptr %_Pshadow_Pstandard_CObject* %5, i32 0, i32 0
;	%7 = bitcast %_Pshadow_Pstandard_CClass* %0 to %_Pshadow_Pstandard_CObject_Mclass*
;	store %_Pshadow_Pstandard_CObject_Mclass* %7, %_Pshadow_Pstandard_CObject_Mclass** %6
	ret %_Pshadow_Pstandard_CObject* %5
}

define noalias %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CClass_Mallocate_Pshadow_Pstandard_Cint(%_Pshadow_Pstandard_CClass*, i32) {
	%3 = getelementptr inbounds %_Pshadow_Pstandard_CClass* %0, i32 0, i32 7
	%4 = load i32* %3
	%5 = call noalias i8* @calloc(i32 %1, i32 %4)
	%6 = bitcast i8* %5 to %_Pshadow_Pstandard_CObject*
	ret %_Pshadow_Pstandard_CObject* %6
}
