; shadow.standard@int native methods

define i32 @_Pshadow_Pstandard_Cint_Madd(i32 %this, i32 %other) alwaysinline {
	%temp = add i32 %this, %other
	ret i32 %temp
}

define i64 @_Pshadow_Pstandard_Cint_MtoLong(i32 %this) alwaysinline {
	%temp = sext i32 %this to i64
	ret i64 %temp
}

define i16 @_Pshadow_Pstandard_Cint_MtoShort(i32 %this) alwaysinline {
	%temp = trunc i32 %this to i16
	ret i16 %temp
}

define i8 @_Pshadow_Pstandard_Cint_MtoUbyte(i32 %this) alwaysinline {
	%temp = trunc i32 %this to i8
	ret i8 %temp
}

define i32 @_Pshadow_Pstandard_Cint_MtoUInt(i32 %this) alwaysinline {
	ret i32 %this
}

define i64 @_Pshadow_Pstandard_Cint_MtoULong(i32 %this) alwaysinline {
	%temp = zext i32 %this to i64
	ret i64 %temp
}
