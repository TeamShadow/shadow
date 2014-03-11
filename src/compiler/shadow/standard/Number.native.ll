attributes #0 = { alwaysinline nounwind readnone }

; shadow.standard@Boolean native methods


; shadow.standard@Byte native methods

define i8 @_Pshadow_Pstandard_Cbyte_MbitComplement(i8) #0 {
	%2 = xor i8 %0, -1
	ret i8 %2
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitOr_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = or i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitXor_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = xor i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitAnd_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = and i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitShiftLeft_Pshadow_Pstandard_Cuint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = shl i8 %0, %5
	ret i8 %6
	ret i8 0
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitShiftRight_Pshadow_Pstandard_Cuint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = ashr i8 %0, %5
	ret i8 %6
	%8 = ashr i8 %0, 7
	ret i8 %8
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitRotateLeft_Pshadow_Pstandard_Cuint(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = shl i8 %0, %4
	%7 = lshr i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}
define i8 @_Pshadow_Pstandard_Cbyte_MbitRotateRight_Pshadow_Pstandard_Cuint(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = lshr i8 %0, %4
	%7 = shl i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}

define i8 @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = add i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = sub i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = mul i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mdivide_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = sdiv i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cbyte_Mmodulus_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = srem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cbyte_Mcompare_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cbyte_Mequal_Pshadow_Pstandard_Cbyte(i8, i8) #0 {
	%3 = icmp eq i8 %0, %1
	ret i1 %3
}

define i8 @_Pshadow_Pstandard_Cbyte_Mnegate(i8) #0 {	
	%2 = sub i8 0, %0
	ret i8 %2
}

define i8 @_Pshadow_Pstandard_Cbyte_MtoByte(i8) #0 {	
	ret i8 %0
}
@_Pshadow_Pstandard_Cbyte_MtoUByte = alias i8 (i8)* @_Pshadow_Pstandard_Cbyte_MtoByte
define i16 @_Pshadow_Pstandard_Cbyte_MtoShort(i8) #0 {	
	%2 = sext i8 %0 to i16
	ret i16 %2
}
define i16 @_Pshadow_Pstandard_Cbyte_MtoUShort(i8) #0 {
	%2 = zext i8 %0 to i16
	ret i16 %2
}
define i32 @_Pshadow_Pstandard_Cbyte_MtoInt(i8) #0 {	
	%2 = sext i8 %0 to i32
	ret i32 %2
}
define i32 @_Pshadow_Pstandard_Cbyte_MtoUInt(i8) #0 {
	%2 = zext i8 %0 to i32
	ret i32 %2
}
@_Pshadow_Pstandard_Cbyte_MtoCode = alias i32 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUInt
define i64 @_Pshadow_Pstandard_Cbyte_MtoLong(i8) #0 {
	%2 = sext i8 %0 to i64
	ret i64 %2
}
define i64 @_Pshadow_Pstandard_Cbyte_MtoULong(i8) #0 {
	%2 = zext i8 %0 to i64
	ret i64 %2
}
define float @_Pshadow_Pstandard_Cbyte_MtoFloat(i8) #0 {
	%2 = sitofp i8 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Cbyte_MtoDouble(i8) #0 {
	%2 = sitofp i8 %0 to double
	ret double %2
}

; shadow.standard@UByte native methods

@_Pshadow_Pstandard_Cubyte_MbitComplement = alias i8 (i8)* @_Pshadow_Pstandard_Cbyte_MbitComplement
@_Pshadow_Pstandard_Cubyte_MbitOr_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitOr_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_MbitXor_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitXor_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_MbitAnd_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_MbitAnd_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_MbitShiftLeft_Pshadow_Pstandard_Cuint = alias i8 (i8, i32)* @_Pshadow_Pstandard_Cbyte_MbitShiftLeft_Pshadow_Pstandard_Cuint
define i8 @_Pshadow_Pstandard_Cubyte_MbitShiftRight_Pshadow_Pstandard_Cuint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = lshr i8 %0, %5
	ret i8 %6
	ret i8 0
}
@_Pshadow_Pstandard_Cubyte_MbitRotateLeft_Pshadow_Pstandard_Cuint = alias i8 (i8, i32)* @_Pshadow_Pstandard_Cbyte_MbitRotateLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Cubyte_MbitRotateRight_Pshadow_Pstandard_Cuint = alias i8 (i8, i32)* @_Pshadow_Pstandard_Cbyte_MbitRotateRight_Pshadow_Pstandard_Cuint

@_Pshadow_Pstandard_Cubyte_Madd_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Madd_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_Msubtract_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Msubtract_Pshadow_Pstandard_Cbyte
@_Pshadow_Pstandard_Cubyte_Mmultiply_Pshadow_Pstandard_Cubyte = alias i8 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Mmultiply_Pshadow_Pstandard_Cbyte
define i8 @_Pshadow_Pstandard_Cubyte_Mdivide_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = udiv i8 %0, %1
	ret i8 %3
}
define i8 @_Pshadow_Pstandard_Cubyte_Mmodulus_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = urem i8 %0, %1
	ret i8 %3
}

define i32 @_Pshadow_Pstandard_Cubyte_Mcompare_Pshadow_Pstandard_Cubyte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cubyte_Mequal_Pshadow_Pstandard_Cubyte = alias i1 (i8, i8)* @_Pshadow_Pstandard_Cbyte_Mequal_Pshadow_Pstandard_Cbyte

@_Pshadow_Pstandard_Cubyte_MtoByte = alias i8 (i8)* @_Pshadow_Pstandard_Cbyte_MtoByte 
@_Pshadow_Pstandard_Cubyte_MtoUByte = alias i8 (i8)* @_Pshadow_Pstandard_Cbyte_MtoByte
@_Pshadow_Pstandard_Cubyte_MtoShort = alias i16 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUShort 
@_Pshadow_Pstandard_Cubyte_MtoUShort = alias i16 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUShort
@_Pshadow_Pstandard_Cubyte_MtoInt = alias i32 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUInt
@_Pshadow_Pstandard_Cubyte_MtoUInt = alias i32 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUInt
@_Pshadow_Pstandard_Cubyte_MtoCode = alias i32 (i8)* @_Pshadow_Pstandard_Cbyte_MtoUInt
@_Pshadow_Pstandard_Cubyte_MtoLong = alias i64 (i8)* @_Pshadow_Pstandard_Cbyte_MtoULong
@_Pshadow_Pstandard_Cubyte_MtoULong = alias i64 (i8)* @_Pshadow_Pstandard_Cbyte_MtoULong

define float @_Pshadow_Pstandard_Cubyte_MtoFloat(i8) #0 {
	%2 = uitofp i8 %0 to float
	ret float %2
}

define double @_Pshadow_Pstandard_Cubyte_MtoDouble(i8) #0 {
	%2 = uitofp i8 %0 to double
	ret double %2
}

; shadow.standard@Short native methods

define i16 @_Pshadow_Pstandard_Cshort_MbitComplement(i16) #0 {
	%2 = xor i16 %0, -1
	ret i16 %2
}
define i16 @_Pshadow_Pstandard_Cshort_MbitOr_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = or i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_MbitXor_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = xor i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_MbitAnd_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = and i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_MbitShiftLeft_Pshadow_Pstandard_Cuint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = shl i16 %0, %5
	ret i16 %6
	ret i16 0
}
define i16 @_Pshadow_Pstandard_Cshort_MbitShiftRight_Pshadow_Pstandard_Cuint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = ashr i16 %0, %5
	ret i16 %6
	%8 = ashr i16 %0, 15
	ret i16 %8
}
define i16 @_Pshadow_Pstandard_Cshort_MbitRotateLeft_Pshadow_Pstandard_Cuint(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = shl i16 %0, %4
	%7 = lshr i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}
define i16 @_Pshadow_Pstandard_Cshort_MbitRotateRight_Pshadow_Pstandard_Cuint(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = lshr i16 %0, %4
	%7 = shl i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}

define i16 @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = add i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = sub i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = mul i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mdivide_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = sdiv i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cshort_Mmodulus_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = srem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cshort_Mcompare_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cshort_Mequal_Pshadow_Pstandard_Cshort(i16, i16) #0 {
	%3 = icmp eq i16 %0, %1
	ret i1 %3
}

define i16 @_Pshadow_Pstandard_Cshort_Mnegate(i16) #0 {	
	%2 = sub i16 0, %0
	ret i16 %2
}

define i8 @_Pshadow_Pstandard_Cshort_MtoByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}
@_Pshadow_Pstandard_Cshort_MtoUByte = alias i8 (i16)* @_Pshadow_Pstandard_Cshort_MtoByte	
define i16 @_Pshadow_Pstandard_Cshort_MtoShort(i16) #0 {	
	ret i16 %0
}
@_Pshadow_Pstandard_Cshort_MtoUShort = alias i16 (i16)* @_Pshadow_Pstandard_Cshort_MtoShort 
define i32 @_Pshadow_Pstandard_Cshort_MtoInt(i16) #0 {	
	%2 = sext i16 %0 to i32
	ret i32 %2
}
define i32 @_Pshadow_Pstandard_Cshort_MtoUInt(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}
@_Pshadow_Pstandard_Cshort_MtoCode = alias i32 (i16)* @_Pshadow_Pstandard_Cshort_MtoUInt 
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

; shadow.standard@UShort native methods

@_Pshadow_Pstandard_Cushort_MbitComplement = alias i16 (i16)* @_Pshadow_Pstandard_Cshort_MbitComplement
@_Pshadow_Pstandard_Cushort_MbitOr_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitOr_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_MbitXor_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitXor_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_MbitAnd_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_MbitAnd_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_MbitShiftLeft_Pshadow_Pstandard_Cuint = alias i16 (i16, i32)* @_Pshadow_Pstandard_Cshort_MbitShiftLeft_Pshadow_Pstandard_Cuint
define i16 @_Pshadow_Pstandard_Cushort_MbitShiftRight_Pshadow_Pstandard_Cuint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = lshr i16 %0, %5
	ret i16 %6
	ret i16 0
}
@_Pshadow_Pstandard_Cushort_MbitRotateLeft_Pshadow_Pstandard_Cuint = alias i16 (i16, i32)* @_Pshadow_Pstandard_Cshort_MbitRotateLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Cushort_MbitRotateRight_Pshadow_Pstandard_Cuint = alias i16 (i16, i32)* @_Pshadow_Pstandard_Cshort_MbitRotateRight_Pshadow_Pstandard_Cuint

@_Pshadow_Pstandard_Cushort_Madd_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Madd_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_Msubtract_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Msubtract_Pshadow_Pstandard_Cshort
@_Pshadow_Pstandard_Cushort_Mmultiply_Pshadow_Pstandard_Cushort = alias i16 (i16, i16)* @_Pshadow_Pstandard_Cshort_Mmultiply_Pshadow_Pstandard_Cshort
define i16 @_Pshadow_Pstandard_Cushort_Mdivide_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = udiv i16 %0, %1
	ret i16 %3
}
define i16 @_Pshadow_Pstandard_Cushort_Mmodulus_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = urem i16 %0, %1
	ret i16 %3
}

define i32 @_Pshadow_Pstandard_Cushort_Mcompare_Pshadow_Pstandard_Cushort(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cushort_Mequal_Pshadow_Pstandard_Cushort = alias i1 (i16, i16)* @_Pshadow_Pstandard_Cshort_Mequal_Pshadow_Pstandard_Cshort

@_Pshadow_Pstandard_Cushort_MtoByte = alias i8 (i16)* @_Pshadow_Pstandard_Cshort_MtoByte 
@_Pshadow_Pstandard_Cushort_MtoShort = alias i16 (i16)* @_Pshadow_Pstandard_Cshort_MtoShort 
@_Pshadow_Pstandard_Cushort_MtoUShort = alias i16 (i16)* @_Pshadow_Pstandard_Cshort_MtoShort
@_Pshadow_Pstandard_Cushort_MtoInt = alias i32 (i16)* @_Pshadow_Pstandard_Cshort_MtoUInt 	
@_Pshadow_Pstandard_Cushort_MtoUInt = alias i32 (i16)* @_Pshadow_Pstandard_Cshort_MtoUInt
@_Pshadow_Pstandard_Cushort_MtoCode = alias i32 (i16)* @_Pshadow_Pstandard_Cshort_MtoUInt
@_Pshadow_Pstandard_Cushort_MtoLong = alias i64 (i16)* @_Pshadow_Pstandard_Cshort_MtoULong 
@_Pshadow_Pstandard_Cushort_MtoULong = alias i64 (i16)* @_Pshadow_Pstandard_Cshort_MtoULong
define float @_Pshadow_Pstandard_Cushort_MtoFloat(i16) #0 {
	%2 = uitofp i16 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Cushort_MtoDouble(i16) #0 {
	%2 = uitofp i16 %0 to double
	ret double %2
}

; shadow.standard@Int native methods

define i32 @_Pshadow_Pstandard_Cint_MbitComplement(i32) #0 {
	%2 = xor i32 %0, -1
	ret i32 %2
}
define i32 @_Pshadow_Pstandard_Cint_MbitOr_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = or i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_MbitXor_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = xor i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_MbitAnd_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = and i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_MbitShiftLeft_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = shl i32 %0, %1
	ret i32 %5
	ret i32 0
}
define i32 @_Pshadow_Pstandard_Cint_MbitShiftRight_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = ashr i32 %0, %1
	ret i32 %5
	%7 = ashr i32 %0, 31
	ret i32 %7
}
define i32 @_Pshadow_Pstandard_Cint_MbitRotateLeft_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = shl i32 %0, %3
	%6 = lshr i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}
define i32 @_Pshadow_Pstandard_Cint_MbitRotateRight_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = lshr i32 %0, %3
	%6 = shl i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}

define i32 @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = add i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = sub i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = mul i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mdivide_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = sdiv i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mmodulus_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = srem i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cint_Mcompare_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}
define i1 @_Pshadow_Pstandard_Cint_Mequal_Pshadow_Pstandard_Cint(i32, i32) #0 {
	%3 = icmp eq i32 %0, %1
	ret i1 %3
}

define i32 @_Pshadow_Pstandard_Cint_Mnegate(i32) #0 {	
	%2 = sub i32 0, %0
	ret i32 %2
}

define i8 @_Pshadow_Pstandard_Cint_MtoByte(i32) #0 {	
	%2 = trunc i32 %0 to i8
	ret i8 %2
}
@_Pshadow_Pstandard_Cint_MtoUByte = alias i8 (i32)* @_Pshadow_Pstandard_Cint_MtoByte  
define i16 @_Pshadow_Pstandard_Cint_MtoShort(i32) #0 {
	%2 = trunc i32 %0 to i16
	ret i16 %2
}
@_Pshadow_Pstandard_Cint_MtoUShort = alias i16 (i32)* @_Pshadow_Pstandard_Cint_MtoShort
define i32 @_Pshadow_Pstandard_Cint_MtoInt(i32) #0 {	
	ret i32 %0
}
@_Pshadow_Pstandard_Cint_MtoUInt = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Cint_MtoCode = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt

define i64 @_Pshadow_Pstandard_Cint_MtoLong(i32) #0 {
	%2 = sext i32 %0 to i64
	ret i64 %2
}
define i64 @_Pshadow_Pstandard_Cint_MtoULong(i32) #0 {
	%2 = zext i32 %0 to i64
	ret i64 %2
} 
define float @_Pshadow_Pstandard_Cint_MtoFloat(i32) #0 {
	%2 = sitofp i32 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Cint_MtoDouble(i32) #0 {
	%2 = sitofp i32 %0 to double
	ret double %2
}

; shadow.standard@UInt native methods

@_Pshadow_Pstandard_Cuint_MbitComplement = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MbitComplement
@_Pshadow_Pstandard_Cuint_MbitOr_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitOr_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_MbitXor_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitXor_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_MbitAnd_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitAnd_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_MbitShiftLeft_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitShiftLeft_Pshadow_Pstandard_Cuint
define i32 @_Pshadow_Pstandard_Cuint_MbitShiftRight_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = lshr i32 %0, %1
	ret i32 %5
	ret i32 0
}
@_Pshadow_Pstandard_Cuint_MbitRotateLeft_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitRotateLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Cuint_MbitRotateRight_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitRotateRight_Pshadow_Pstandard_Cuint

@_Pshadow_Pstandard_Cuint_Madd_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_Msubtract_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Cuint_Mmultiply_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
define i32 @_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = udiv i32 %0, %1
	ret i32 %3
}
define i32 @_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = urem i32 %0, %1
	ret i32 %3
}

define i32 @_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Cuint_Mequal_Pshadow_Pstandard_Cuint = alias i1 (i32, i32)* @_Pshadow_Pstandard_Cint_Mequal_Pshadow_Pstandard_Cint

@_Pshadow_Pstandard_Cuint_MtoByte = alias i8 (i32)* @_Pshadow_Pstandard_Cint_MtoByte 	
@_Pshadow_Pstandard_Cuint_MtoUByte = alias i8 (i32)* @_Pshadow_Pstandard_Cint_MtoByte
@_Pshadow_Pstandard_Cuint_MtoShort = alias i16 (i32)* @_Pshadow_Pstandard_Cint_MtoShort 
@_Pshadow_Pstandard_Cuint_MtoUShort = alias i16 (i32)* @_Pshadow_Pstandard_Cint_MtoShort
@_Pshadow_Pstandard_Cuint_MtoInt = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Cuint_MtoUInt = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Cuint_MtoCode = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Cuint_MtoLong = alias i64 (i32)* @_Pshadow_Pstandard_Cint_MtoULong 
@_Pshadow_Pstandard_Cuint_MtoULong = alias i64 (i32)* @_Pshadow_Pstandard_Cint_MtoULong
define float @_Pshadow_Pstandard_Cuint_MtoFloat(i32) #0 {
	%2 = uitofp i32 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Cuint_MtoDouble(i32) #0 {
	%2 = uitofp i32 %0 to double
	ret double %2
}

; shadow.standard@Code native methods

@_Pshadow_Pstandard_Ccode_MtoByte = alias i8 (i32)* @_Pshadow_Pstandard_Cint_MtoByte
@_Pshadow_Pstandard_Ccode_MtoUByte = alias i8 (i32)* @_Pshadow_Pstandard_Cint_MtoByte
@_Pshadow_Pstandard_Ccode_MtoShort = alias i16 (i32)* @_Pshadow_Pstandard_Cint_MtoShort
@_Pshadow_Pstandard_Ccode_MtoUShort = alias i16 (i32)* @_Pshadow_Pstandard_Cint_MtoShort
@_Pshadow_Pstandard_Ccode_MtoInt = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Ccode_MtoUInt = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Ccode_MtoCode = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MtoInt
@_Pshadow_Pstandard_Ccode_MtoLong = alias i64 (i32)* @_Pshadow_Pstandard_Cint_MtoULong
@_Pshadow_Pstandard_Ccode_MtoULong = alias i64 (i32)* @_Pshadow_Pstandard_Cint_MtoULong
@_Pshadow_Pstandard_Ccode_MtoFloat = alias float (i32)* @_Pshadow_Pstandard_Cuint_MtoFloat
@_Pshadow_Pstandard_Ccode_MtoDouble = alias double (i32)* @_Pshadow_Pstandard_Cuint_MtoDouble

@_Pshadow_Pstandard_Ccode_MbitComplement = alias i32 (i32)* @_Pshadow_Pstandard_Cint_MbitComplement
@_Pshadow_Pstandard_Ccode_MbitOr_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitOr_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_MbitXor_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitXor_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_MbitAnd_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitAnd_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_MbitShiftLeft_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitShiftLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_MbitShiftRight_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cuint_MbitShiftRight_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_MbitRotateLeft_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitRotateLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_MbitRotateRight_Pshadow_Pstandard_Cuint = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_MbitRotateRight_Pshadow_Pstandard_Cuint

@_Pshadow_Pstandard_Ccode_Madd_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Madd_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_Msubtract_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Msubtract_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_Mmultiply_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cint_Mmultiply_Pshadow_Pstandard_Cint
@_Pshadow_Pstandard_Ccode_Mdivide_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cuint_Mdivide_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_Mmodulus_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cuint_Mmodulus_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_Mcompare_Pshadow_Pstandard_Ccode = alias i32 (i32, i32)* @_Pshadow_Pstandard_Cuint_Mcompare_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Ccode_Mequal_Pshadow_Pstandard_Ccode = alias i1 (i32, i32)* @_Pshadow_Pstandard_Cint_Mequal_Pshadow_Pstandard_Cint


; shadow.standard@Long native methods

define i64 @_Pshadow_Pstandard_Clong_MbitComplement(i64) #0 {
	%2 = xor i64 %0, -1
	ret i64 %2
}
define i64 @_Pshadow_Pstandard_Clong_MbitOr_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = or i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_MbitXor_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = xor i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_MbitAnd_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = and i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_MbitShiftLeft_Pshadow_Pstandard_Cuint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = shl i64 %0, %5
	ret i64 %6
	ret i64 0
}
define i64 @_Pshadow_Pstandard_Clong_MbitShiftRight_Pshadow_Pstandard_Cuint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = ashr i64 %0, %5
	ret i64 %6
	%8 = ashr i64 %0, 63
	ret i64 %8
}
define i64 @_Pshadow_Pstandard_Clong_MbitRotateLeft_Pshadow_Pstandard_Cuint(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = shl i64 %0, %4
	%7 = lshr i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}
define i64 @_Pshadow_Pstandard_Clong_MbitRotateRight_Pshadow_Pstandard_Cuint(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = lshr i64 %0, %4
	%7 = shl i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}

define i64 @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = add i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = sub i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = mul i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mdivide_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = sdiv i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Clong_Mmodulus_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = srem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Clong_Mcompare_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Clong_Mequal_Pshadow_Pstandard_Clong(i64, i64) #0 {
	%3 = icmp eq i64 %0, %1
	ret i1 %3
}

define i64 @_Pshadow_Pstandard_Clong_Mnegate(i64) #0 {	
	%2 = sub i64 0, %0
	ret i64 %2
}

define i8 @_Pshadow_Pstandard_Clong_MtoByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}
@_Pshadow_Pstandard_Clong_MtoUByte = alias i8 (i64)* @_Pshadow_Pstandard_Clong_MtoByte 
define i16 @_Pshadow_Pstandard_Clong_MtoShort(i64) #0 {
	%2 = trunc i64 %0 to i16
	ret i16 %2
}
@_Pshadow_Pstandard_Clong_MtoUShort = alias i16 (i64)* @_Pshadow_Pstandard_Clong_MtoShort 
define i32 @_Pshadow_Pstandard_Clong_MtoInt(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}
@_Pshadow_Pstandard_Clong_MtoUInt = alias i32 (i64)* @_Pshadow_Pstandard_Clong_MtoInt 
@_Pshadow_Pstandard_Clong_MtoCode = alias i32 (i64)* @_Pshadow_Pstandard_Clong_MtoInt
define i64 @_Pshadow_Pstandard_Clong_MtoLong(i64) #0 {
	ret i64 %0
}
@_Pshadow_Pstandard_Clong_MtoULong = alias i64 (i64)* @_Pshadow_Pstandard_Clong_MtoLong 
define float @_Pshadow_Pstandard_Clong_MtoFloat(i64) #0 {
	%2 = sitofp i64 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Clong_MtoDouble(i64) #0 {
	%2 = sitofp i64 %0 to double
	ret double %2
}

; shadow.standard@ULong native methods

@_Pshadow_Pstandard_Culong_MbitComplement = alias i64 (i64)* @_Pshadow_Pstandard_Clong_MbitComplement
@_Pshadow_Pstandard_Culong_MbitOr_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitOr_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_MbitXor_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitXor_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_MbitAnd_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_MbitAnd_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_MbitShiftLeft_Pshadow_Pstandard_Cuint = alias i64 (i64, i32)* @_Pshadow_Pstandard_Clong_MbitShiftLeft_Pshadow_Pstandard_Cuint
define i64 @_Pshadow_Pstandard_Culong_MbitShiftRight_Pshadow_Pstandard_Cuint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = lshr i64 %0, %5
	ret i64 %6
	ret i64 0
}
@_Pshadow_Pstandard_Culong_MbitRotateLeft_Pshadow_Pstandard_Cuint = alias i64 (i64, i32)* @_Pshadow_Pstandard_Clong_MbitRotateLeft_Pshadow_Pstandard_Cuint
@_Pshadow_Pstandard_Culong_MbitRotateRight_Pshadow_Pstandard_Cuint = alias i64 (i64, i32)* @_Pshadow_Pstandard_Clong_MbitRotateRight_Pshadow_Pstandard_Cuint

@_Pshadow_Pstandard_Culong_Madd_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Madd_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_Msubtract_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Msubtract_Pshadow_Pstandard_Clong
@_Pshadow_Pstandard_Culong_Mmultiply_Pshadow_Pstandard_Culong = alias i64 (i64, i64)* @_Pshadow_Pstandard_Clong_Mmultiply_Pshadow_Pstandard_Clong
define i64 @_Pshadow_Pstandard_Culong_Mdivide_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = udiv i64 %0, %1
	ret i64 %3
}
define i64 @_Pshadow_Pstandard_Culong_Mmodulus_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = urem i64 %0, %1
	ret i64 %3
}

define i32 @_Pshadow_Pstandard_Culong_Mcompare_Pshadow_Pstandard_Culong(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@_Pshadow_Pstandard_Culong_Mequal_Pshadow_Pstandard_Culong = alias i1 (i64, i64)* @_Pshadow_Pstandard_Clong_Mequal_Pshadow_Pstandard_Clong

@_Pshadow_Pstandard_Culong_MtoByte = alias i8 (i64)* @_Pshadow_Pstandard_Clong_MtoByte 
@_Pshadow_Pstandard_Culong_MtoUByte = alias i8 (i64)* @_Pshadow_Pstandard_Clong_MtoByte
@_Pshadow_Pstandard_Culong_MtoShort = alias i16 (i64)* @_Pshadow_Pstandard_Clong_MtoShort 
@_Pshadow_Pstandard_Culong_MtoUShort = alias i16 (i64)* @_Pshadow_Pstandard_Clong_MtoShort
@_Pshadow_Pstandard_Culong_MtoInt = alias i32 (i64)* @_Pshadow_Pstandard_Clong_MtoInt 
@_Pshadow_Pstandard_Culong_MtoUInt = alias i32 (i64)* @_Pshadow_Pstandard_Clong_MtoInt
@_Pshadow_Pstandard_Culong_MtoCode = alias i32 (i64)* @_Pshadow_Pstandard_Clong_MtoInt
@_Pshadow_Pstandard_Culong_MtoLong = alias i64 (i64)* @_Pshadow_Pstandard_Clong_MtoLong 
@_Pshadow_Pstandard_Culong_MtoULong = alias i64 (i64)* @_Pshadow_Pstandard_Clong_MtoLong
define float @_Pshadow_Pstandard_Culong_MtoFloat(i64) #0 {
	%2 = uitofp i64 %0 to float
	ret float %2
}
define double @_Pshadow_Pstandard_Culong_MtoDouble(i64) #0 {
	%2 = uitofp i64 %0 to double
	ret double %2
}

; shadow.standard@Float native methods

define float @_Pshadow_Pstandard_Cfloat_Madd_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fadd float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Msubtract_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fsub float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mmultiply_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fmul float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mdivide_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fdiv float %0, %1
	ret float %3
}
define float @_Pshadow_Pstandard_Cfloat_Mmodulus_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = frem float %0, %1
	ret float %3
}

define i32 @_Pshadow_Pstandard_Cfloat_Mcompare_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fcmp ueq float %0, %1
	%4 = fcmp olt float %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cfloat_Mequal_Pshadow_Pstandard_Cfloat(float, float) #0 {
	%3 = fcmp ord float %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq float %0, %1
	ret i1 %5
	%7 = fcmp uno float %0, 0.
	%8 = fcmp uno float %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i32 @_Pshadow_Pstandard_Cfloat_Mraw(float) #0 {
	%2 = bitcast float %0 to i32
	ret i32 %2
}

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

; shadow.standard@Double native methods

define double @_Pshadow_Pstandard_Cdouble_Madd_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fadd double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Msubtract_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fsub double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mmultiply_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fmul double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mdivide_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fdiv double %0, %1
	ret double %3
}
define double @_Pshadow_Pstandard_Cdouble_Mmodulus_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = frem double %0, %1
	ret double %3
}

define i32 @_Pshadow_Pstandard_Cdouble_Mcompare_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fcmp ueq double %0, %1
	%4 = fcmp olt double %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @_Pshadow_Pstandard_Cdouble_Mequal_Pshadow_Pstandard_Cdouble(double, double) #0 {
	%3 = fcmp ord double %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq double %0, %1
	ret i1 %5
	%7 = fcmp uno double %0, 0.
	%8 = fcmp uno double %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i64 @_Pshadow_Pstandard_Cdouble_Mraw(double) #0 {
	%2 = bitcast double %0 to i64
	ret i64 %2
}

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