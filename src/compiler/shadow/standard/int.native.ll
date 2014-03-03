; shadow.standard@int native methods

define i32 @_Pshadow_Pstandard_Cint_Madd(i32 %this, i32 %other) alwaysinline {
	%temp = add i32 %this, %other
	ret i32 %temp
}