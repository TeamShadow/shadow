; shadow.standard@float native methods

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

define float @_Pshadow_Pstandard_Cfloat_Mnegate(float) #0 {	
	%2 = fsub float -0.0, %0
	ret float %2
}

define i8 @_Pshadow_Pstandard_Cfloat_MtoByte(float) #0 {	
	%2 = fptosi float %0 to i8
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Cfloat_MtoUByte(float) #0 {	
	%2 = fptoui float %0 to i8
	ret i8 %2
}

define i16 @_Pshadow_Pstandard_Cfloat_MtoShort(float) #0 {
	%2 = fptosi float %0 to i16
	ret i16 %2
}

define i16 @_Pshadow_Pstandard_Cfloat_MtoUShort(float) #0 {	
	%2 = fptoui float %0 to i16
	ret i16 %2
}

define i32 @_Pshadow_Pstandard_Cfloat_MtoInt(float) #0 {	
	%2 = fptosi float %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cfloat_MtoUInt(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i32 @_Pshadow_Pstandard_Cfloat_MtoCode(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i64 @_Pshadow_Pstandard_Cfloat_MtoLong(float) #0 {
	%2 = fptosi float %0 to i64
	ret i64 %2
}

define i64 @_Pshadow_Pstandard_Cfloat_MtoULong(float) #0 {
	%2 = fptoui float %0 to i64
	ret i64 %2
}

define float @_Pshadow_Pstandard_Cfloat_MtoFloat(float) #0 {
	ret float %0
}

define double @_Pshadow_Pstandard_Cfloat_MtoDouble(float) #0 {	
	%2 = fpext float %0 to double
	ret double %2
}






