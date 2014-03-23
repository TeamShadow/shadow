; shadow.standard@Object native methods

%_Pshadow_Pstandard_CClass = type { }
%_Pshadow_Pstandard_CObject_Mclass = type { %_Pshadow_Pstandard_CClass }
%_Pshadow_Pstandard_CObject = type { %_Pshadow_Pstandard_CObject_Mclass* }

define i1 @_Pshadow_Pstandard_CObject_MreferenceEquals_Pshadow_Pstandard_CObject(%_Pshadow_Pstandard_CObject*, %_Pshadow_Pstandard_CObject*) {
	%3 = icmp eq %_Pshadow_Pstandard_CObject* %0, %1
	ret i1 %3
}

define %_Pshadow_Pstandard_CClass* @_Pshadow_Pstandard_CObject_MgetClass(%_Pshadow_Pstandard_CObject*) {
	%2 = getelementptr %_Pshadow_Pstandard_CObject* %0, i32 0, i32 0
	%3 = load %_Pshadow_Pstandard_CObject_Mclass** %2
	%4 = getelementptr %_Pshadow_Pstandard_CObject_Mclass* %3, i32 0, i32 0
	ret %_Pshadow_Pstandard_CClass* %4
}

define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CObject_Mcopy(%_Pshadow_Pstandard_CObject*) {
	ret %_Pshadow_Pstandard_CObject* %0
}

;define %_Pshadow_Pstandard_CObject* @_Pshadow_Pstandard_CObject_Mfreeze(%_Pshadow_Pstandard_CObject*) {
;	ret %_Pshadow_Pstandard_CObject* %0
;}
