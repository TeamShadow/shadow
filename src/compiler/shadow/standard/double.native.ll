; shadow.standard@double native methods

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

define double @_Pshadow_Pstandard_Cdouble_Mnegate(double) #0 {	
	%2 = fsub double -0.0, %0
	ret double %2
}

define i8 @_Pshadow_Pstandard_Cdouble_MtoByte(double) #0 {	
	%2 = fptosi double %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Cdouble_MtoUByte(double) #0 {	
	%2 = fptoui double %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Cdouble_MtoShort(double) #0 {
	%2 = fptosi double %0 to i16
	ret i16 %2
}

define i16 @_Pshadow_Pstandard_Cdouble_MtoUShort(double) #0 {	
	%2 = fptoui double %0 to i16
	ret i16 %2
}

define i32 @_Pshadow_Pstandard_Cdouble_MtoInt(double) #0 {	
	%2 = fptosi double %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cdouble_MtoUInt(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cdouble_MtoCode(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Cdouble_MtoLong(double) #0 {
	%2 = fptosi double %0 to i64
	ret i64 %2
}

define i64 @_Pshadow_Pstandard_Cdouble_MtoULong(double) #0 {
	%2 = fptoui double %0 to i64
	ret i64 %2
}

define float @_Pshadow_Pstandard_Cdouble_MtoFloat(double) #0 {
	%2 = fptrunc double %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Cdouble_MtoDouble(double) #0 {	
	ret double %0
}






