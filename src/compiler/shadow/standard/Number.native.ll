; shadow.standard@Boolean native methods

; shadow.standard@Code native methods

; shadow.standard@Byte native methods

define i8 @_Pshadow_Pstandard_Cbyte_Madd_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = add i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Msubtract_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = sub i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Mmultiply_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = mul i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Mdivide_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = sdiv i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Mmodulus_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = srem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cbyte_Mcompare_R_Pshadow_Pstandard_Cbyte(i8, i8) {
	%3 = icmp eq i8 %0, %1
	%4 = icmp slt i8 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@UByte native methods

define i8 @_Pshadow_Pstandard_Cubyte_Madd_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = add i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cubyte_Msubtract_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = sub i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cubyte_Mmultiply_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = mul i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cubyte_Mdivide_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = udiv i8 %0, %1
	ret i8 %3
}

define i8 @_Pshadow_Pstandard_Cubyte_Mmodulus_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = urem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cubyte_Mcompare_R_Pshadow_Pstandard_Cubyte(i8, i8) {
	%3 = icmp eq i8 %0, %1
	%4 = icmp ult i8 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@Short native methods

define i16 @_Pshadow_Pstandard_Cshort_Madd_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = add i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Msubtract_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = sub i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Mmultiply_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = mul i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Mdivide_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = sdiv i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Mmodulus_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = srem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cshort_Mcompare_R_Pshadow_Pstandard_Cshort(i16, i16) {
	%3 = icmp eq i16 %0, %1
	%4 = icmp slt i16 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@UShort native methods

define i16 @_Pshadow_Pstandard_Cushort_Madd_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = add i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cushort_Msubtract_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = sub i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cushort_Mmultiply_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = mul i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cushort_Mdivide_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = udiv i16 %0, %1
	ret i16 %3
}

define i16 @_Pshadow_Pstandard_Cushort_Mmodulus_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = urem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cushort_Mcompare_R_Pshadow_Pstandard_Cushort(i16, i16) {
	%3 = icmp eq i16 %0, %1
	%4 = icmp ult i16 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@Int native methods

define i32 @_Pshadow_Pstandard_Cint_Madd_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = add i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Msubtract_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = sub i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mmultiply_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = mul i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mdivide_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = sdiv i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mmodulus_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = srem i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mcompare_R_Pshadow_Pstandard_Cint(i32, i32) {
	%3 = icmp eq i32 %0, %1
	%4 = icmp slt i32 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@UInt native methods

define i32 @_Pshadow_Pstandard_Cuint_Madd_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = add i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Msubtract_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = sub i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mmultiply_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = mul i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mdivide_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = udiv i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mmodulus_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = urem i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mcompare_R_Pshadow_Pstandard_Cuint(i32, i32) {
	%3 = icmp eq i32 %0, %1
	%4 = icmp ult i32 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@Long native methods

define i64 @_Pshadow_Pstandard_Clong_Madd_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = add i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Clong_Msubtract_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = sub i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Clong_Mmultiply_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = mul i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Clong_Mdivide_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = sdiv i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Clong_Mmodulus_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = srem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Clong_Mcompare_R_Pshadow_Pstandard_Clong(i64, i64) {
	%3 = icmp eq i64 %0, %1
	%4 = icmp slt i64 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@ULong native methods

define i64 @_Pshadow_Pstandard_Culong_Madd_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = add i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Culong_Msubtract_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = sub i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Culong_Mmultiply_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = mul i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Culong_Mdivide_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = udiv i64 %0, %1
	ret i64 %3
}

define i64 @_Pshadow_Pstandard_Culong_Mmodulus_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = urem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Culong_Mcompare_R_Pshadow_Pstandard_Culong(i64, i64) {
	%3 = icmp eq i64 %0, %1
	%4 = icmp ult i64 %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@Float native methods

define float @_Pshadow_Pstandard_Cfloat_Madd_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = fadd float %0, %1
	ret float %3
}

define float @_Pshadow_Pstandard_Cfloat_Msubtract_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = fsub float %0, %1
	ret float %3
}

define float @_Pshadow_Pstandard_Cfloat_Mmultiply_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = fmul float %0, %1
	ret float %3
}

define float @_Pshadow_Pstandard_Cfloat_Mdivide_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = fdiv float %0, %1
	ret float %3
}

define float @_Pshadow_Pstandard_Cfloat_Mmodulus_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = frem float %0, %1
	ret float %3
}

define i32 @_Pshadow_Pstandard_Cfloat_Mcompare_R_Pshadow_Pstandard_Cfloat(float, float) {
	%3 = fcmp oeq float %0, %1
	%4 = fcmp olt float %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

; shadow.standard@Double native methods

define double @_Pshadow_Pstandard_Cdouble_Madd_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = fadd double %0, %1
	ret double %3
}

define double @_Pshadow_Pstandard_Cdouble_Msubtract_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = fsub double %0, %1
	ret double %3
}

define double @_Pshadow_Pstandard_Cdouble_Mmultiply_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = fmul double %0, %1
	ret double %3
}

define double @_Pshadow_Pstandard_Cdouble_Mdivide_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = fdiv double %0, %1
	ret double %3
}

define double @_Pshadow_Pstandard_Cdouble_Mmodulus_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = frem double %0, %1
	ret double %3
}

define i32 @_Pshadow_Pstandard_Cdouble_Mcompare_R_Pshadow_Pstandard_Cdouble(double, double) {
	%3 = fcmp oeq double %0, %1
	%4 = fcmp olt double %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}
