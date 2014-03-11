; shadow.standard@long native methods

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

define i64 @_Pshadow_Pstandard_Clong_Mnegate(i64) #0 {	
	%2 = sub i64 0, %0
	ret i64 %2
}

define i8 @_Pshadow_Pstandard_Clong_MtoByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Clong_MtoUByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Clong_MtoShort(i64) #0 {
	%2 = trunc i64 %0 to i16
	ret i16 %2
}

define i16 @_Pshadow_Pstandard_Clong_MtoUShort(i64) #0 {	
	%2 = trunc i64 %0 to i16
	ret i16 %2
}

define i32 @_Pshadow_Pstandard_Clong_MtoInt(i64) #0 {	
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Clong_MtoUInt(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Clong_MtoCode(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Clong_MtoLong(i64) #0 {
	ret i64 %0
}

define i64 @_Pshadow_Pstandard_Clong_MtoULong(i64) #0 {
	ret i64 %0
}

define float @_Pshadow_Pstandard_Clong_MtoFloat(i64) #0 {
	%2 = sitofp i64 %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Clong_MtoDouble(i64) #0 {
	%2 = sitofp i64 %0 to double
	ret double %2
}






