; shadow.standard@ulong native methods

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

define i8 @_Pshadow_Pstandard_Culong_MtoByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Culong_MtoUByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Culong_MtoShort(i64) #0 {
	%2 = trunc i64 %0 to i16
	ret i16 %2
}

define i16 @_Pshadow_Pstandard_Culong_MtoUShort(i64) #0 {	
	%2 = trunc i64 %0 to i16
	ret i16 %2
}

define i32 @_Pshadow_Pstandard_Culong_MtoInt(i64) #0 {	
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Culong_MtoUInt(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Culong_MtoCode(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Culong_MtoLong(i64) #0 {
	ret i64 %0
}

define i64 @_Pshadow_Pstandard_Culong_MtoULong(i64) #0 {
	ret i64 %0
}

define float @_Pshadow_Pstandard_Culong_MtoFloat(i64) #0 {
	%2 = uitofp i64 %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Culong_MtoDouble(i64) #0 {
	%2 = uitofp i64 %0 to double
	ret double %2
}






