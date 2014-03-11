; shadow.standard@ushort native methods

i1 = type i1
i8 = type i8
i8 = type i8
i16 = type i16
i16 = type i16
i32 = type i32
i32 = type i32
i32 = type i32
i64 = type i64
i64 = type i64
float = type float
double = type double

define i8 @_Pshadow_Pstandard_Cushort_MtoByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Cushort_MtoUByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Cushort_MtoShort(i16) #0 {	
	ret i16 %0
}

define i16 @_Pshadow_Pstandard_Cushort_MtoUShort(i16) #0 {
	ret i16 %0
}

define i32 @_Pshadow_Pstandard_Cushort_MtoInt(i16) #0 {	
	%2 = zext i16 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cushort_MtoUInt(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cushort_MtoCode(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Cushort_MtoLong(i16) #0 {
	%2 = zext i16 %0 to i64
	ret i64 %2
}

define i64 @_Pshadow_Pstandard_Cushort_MtoULong(i16) #0 {
	%2 = zext i16 %0 to i64
	ret i64 %2
}

define float @_Pshadow_Pstandard_Cushort_MtoFloat(i16) #0 {
	%2 = uitofp i16 %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Cushort_MtoDouble(i16) #0 {
	%2 = uitofp i16 %0 to double
	ret double %2
}