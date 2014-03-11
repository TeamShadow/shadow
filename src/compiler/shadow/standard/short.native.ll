; shadow.standard@short native methods

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

define i16 @_Pshadow_Pstandard_Cshort_Mnegate(i16) #0 {	
	%2 = sub i16 0, %0
	ret i16 %2
}

define i8 @_Pshadow_Pstandard_Cshort_MtoByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Cshort_MtoUByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Cshort_MtoShort(i16) #0 {	
	ret i16 %0
}

define i16 @_Pshadow_Pstandard_Cshort_MtoUShort(i16) #0 {
	ret i16 %0
}

define i32 @_Pshadow_Pstandard_Cshort_MtoInt(i16) #0 {	
	%2 = sext i16 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cshort_MtoUInt(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cshort_MtoCode(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Cshort_MtoLong(i16) #0 {
	%2 = sext i16 %0 to i64
	ret i64 %2
}

define i64 @_Pshadow_Pstandard_Cshort_MtoULong(i16) #0 {
	%2 = zext i16 %0 to i64
	ret i64 %2
}

define float @_Pshadow_Pstandard_Cshort_MtoFloat(i16) #0 {
	%2 = sitofp i16 %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Cshort_MtoDouble(i16) #0 {
	%2 = sitofp i16 %0 to double
	ret double %2
}