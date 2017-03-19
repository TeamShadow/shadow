attributes #0 = { alwaysinline nounwind readnone }

declare float     @llvm.sqrt.f32(float %Val)
declare double    @llvm.sqrt.f64(double %Val)

declare float     @llvm.powi.f32(float  %Val, i32 %power)
declare double    @llvm.powi.f64(double %Val, i32 %power)

declare float     @llvm.sin.f32(float  %Val)
declare double    @llvm.sin.f64(double %Val)

declare float     @llvm.cos.f32(float  %Val)
declare double    @llvm.cos.f64(double %Val)

declare float     @llvm.pow.f32(float  %Val, float %Power)
declare double    @llvm.pow.f64(double %Val, double %Power)

declare float     @llvm.log.f32(float  %Val)
declare double    @llvm.log.f64(double %Val)

declare float     @llvm.log10.f32(float  %Val)
declare double    @llvm.log10.f64(double %Val)

declare float     @llvm.log2.f32(float  %Val)
declare double    @llvm.log2.f64(double %Val)

declare float     @llvm.fma.f32(float  %a, float  %b, float  %c)
declare double    @llvm.fma.f64(double %a, double %b, double %c)

declare float     @llvm.floor.f32(float  %Val)
declare double    @llvm.floor.f64(double %Val)

declare float     @llvm.ceil.f32(float  %Val)
declare double    @llvm.ceil.f64(double %Val)

declare i16 @llvm.bswap.i16(i16 %Val)
declare i32 @llvm.bswap.i32(i32 %Val)
declare i64 @llvm.bswap.i64(i64 %Val)

declare i8 @llvm.ctpop.i8(i8  %Val)
declare i16 @llvm.ctpop.i16(i16 %Val)
declare i32 @llvm.ctpop.i32(i32 %Val)
declare i64 @llvm.ctpop.i64(i64 %Val)

declare i8   @llvm.ctlz.i8  (i8   %Val, i1 %Undef)
declare i16  @llvm.ctlz.i16 (i16  %Val, i1 %Undef)
declare i32  @llvm.ctlz.i32 (i32  %Val, i1 %Undef)
declare i64  @llvm.ctlz.i64 (i64  %Val, i1 %Undef)

declare i8   @llvm.cttz.i8  (i8   %Val, i1 %Undef)
declare i16  @llvm.cttz.i16 (i16  %Val, i1 %Undef)
declare i32  @llvm.cttz.i32 (i32  %Val, i1 %Undef)
declare i64  @llvm.cttz.i64 (i64  %Val, i1 %Undef)

declare {i16, i1} @llvm.sadd.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.sadd.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.sadd.with.overflow.i64(i64 %a, i64 %b)

declare {i16, i1} @llvm.uadd.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.uadd.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.uadd.with.overflow.i64(i64 %a, i64 %b)

declare {i16, i1} @llvm.ssub.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.ssub.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.ssub.with.overflow.i64(i64 %a, i64 %b)

declare {i16, i1} @llvm.usub.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.usub.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.usub.with.overflow.i64(i64 %a, i64 %b)

declare {i16, i1} @llvm.smul.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.smul.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.smul.with.overflow.i64(i64 %a, i64 %b)

declare {i16, i1} @llvm.umul.with.overflow.i16(i16 %a, i16 %b)
declare {i32, i1} @llvm.umul.with.overflow.i32(i32 %a, i32 %b)
declare {i64, i1} @llvm.umul.with.overflow.i64(i64 %a, i64 %b)

; shadow.standard@Boolean native methods


; shadow.standard@Byte native methods

define i8 @shadow.standard..byte_MbitComplement(i8) #0 {
	%2 = xor i8 %0, -1
	ret i8 %2
}
define i8 @shadow.standard..byte_MbitOr_byte(i8, i8) #0 {
	%3 = or i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_MbitXor_byte(i8, i8) #0 {
	%3 = xor i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_MbitAnd_byte(i8, i8) #0 {
	%3 = and i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_MbitShiftLeft_uint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = shl i8 %0, %5
	ret i8 %6
	ret i8 0
}
define i8 @shadow.standard..byte_MbitShiftRight_uint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = ashr i8 %0, %5
	ret i8 %6
	%8 = ashr i8 %0, 7
	ret i8 %8
}
define i8 @shadow.standard..byte_MbitRotateLeft_uint(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = shl i8 %0, %4
	%7 = lshr i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}
define i8 @shadow.standard..byte_MbitRotateRight_uint(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = lshr i8 %0, %4
	%7 = shl i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}

define i8 @shadow.standard..byte_Madd_byte(i8, i8) #0 {
	%3 = add i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_Msubtract_byte(i8, i8) #0 {
	%3 = sub i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_Mmultiply_byte(i8, i8) #0 {
	%3 = mul i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_Mdivide_byte(i8, i8) #0 {
	%3 = sdiv i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..byte_Mmodulus_byte(i8, i8) #0 {
	%3 = srem i8 %0, %1
	ret i8 %3
}

define i32 @shadow.standard..byte_Mcompare_byte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @shadow.standard..byte_Mequal_byte(i8, i8) #0 {
	%3 = icmp eq i8 %0, %1
	ret i1 %3
}

define i8 @shadow.standard..byte_Mnegate(i8) #0 {	
	%2 = sub i8 0, %0
	ret i8 %2
}

define i8 @shadow.standard..byte_MtoByte(i8) #0 {	
	ret i8 %0
}
@shadow.standard..byte_MtoUByte = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MtoByte
define i16 @shadow.standard..byte_MtoShort(i8) #0 {	
	%2 = sext i8 %0 to i16
	ret i16 %2
}
define i16 @shadow.standard..byte_MtoUShort(i8) #0 {
	%2 = zext i8 %0 to i16
	ret i16 %2
}
define i32 @shadow.standard..byte_MtoInt(i8) #0 {	
	%2 = sext i8 %0 to i32
	ret i32 %2
}
define i32 @shadow.standard..byte_MtoUInt(i8) #0 {
	%2 = zext i8 %0 to i32
	ret i32 %2
}
@shadow.standard..byte_MtoCode = alias i32 (i8), i32 (i8)* @shadow.standard..byte_MtoUInt
define i64 @shadow.standard..byte_MtoLong(i8) #0 {
	%2 = sext i8 %0 to i64
	ret i64 %2
}
define i64 @shadow.standard..byte_MtoULong(i8) #0 {
	%2 = zext i8 %0 to i64
	ret i64 %2
}
define float @shadow.standard..byte_MtoFloat(i8) #0 {
	%2 = sitofp i8 %0 to float
	ret float %2
}
define double @shadow.standard..byte_MtoDouble(i8) #0 {
	%2 = sitofp i8 %0 to double
	ret double %2
}

define i8 @shadow.standard..byte_Mones(i8) #0 {
	%2 = call i8 @llvm.ctpop.i8(i8 %0)
	ret i8 %2
}

define i8 @shadow.standard..byte_MleadingZeroes(i8) #0 {
	%2 = call i8  @llvm.ctlz.i8(i8 %0, i1 false)
	ret i8 %2
}
define i8 @shadow.standard..byte_MtrailingZeroes(i8) #0 {
	%2 = call i8  @llvm.cttz.i8(i8 %0, i1 false)
	ret i8 %2
}

; shadow.standard@UByte native methods

@shadow.standard..ubyte_MbitComplement = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MbitComplement
@shadow.standard..ubyte_MbitOr_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_MbitOr_byte
@shadow.standard..ubyte_MbitXor_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_MbitXor_byte
@shadow.standard..ubyte_MbitAnd_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_MbitAnd_byte
@shadow.standard..ubyte_MbitShiftLeft_uint = alias i8 (i8, i32), i8 (i8, i32)* @shadow.standard..byte_MbitShiftLeft_uint
define i8 @shadow.standard..ubyte_MbitShiftRight_uint(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = lshr i8 %0, %5
	ret i8 %6
	ret i8 0
}
@shadow.standard..ubyte_MbitRotateLeft_uint = alias i8 (i8, i32), i8 (i8, i32)* @shadow.standard..byte_MbitRotateLeft_uint
@shadow.standard..ubyte_MbitRotateRight_uint = alias i8 (i8, i32), i8 (i8, i32)* @shadow.standard..byte_MbitRotateRight_uint

@shadow.standard..ubyte_Madd_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_Madd_byte
@shadow.standard..ubyte_Msubtract_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_Msubtract_byte
@shadow.standard..ubyte_Mmultiply_ubyte = alias i8 (i8, i8), i8 (i8, i8)* @shadow.standard..byte_Mmultiply_byte
define i8 @shadow.standard..ubyte_Mdivide_ubyte(i8, i8) #0 {
	%3 = udiv i8 %0, %1
	ret i8 %3
}
define i8 @shadow.standard..ubyte_Mmodulus_ubyte(i8, i8) #0 {
	%3 = urem i8 %0, %1
	ret i8 %3
}

define i32 @shadow.standard..ubyte_Mcompare_ubyte(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@shadow.standard..ubyte_Mequal_ubyte = alias i1 (i8, i8), i1 (i8, i8)* @shadow.standard..byte_Mequal_byte

@shadow.standard..ubyte_MtoByte = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MtoByte 
@shadow.standard..ubyte_MtoUByte = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MtoByte
@shadow.standard..ubyte_MtoShort = alias i16 (i8), i16 (i8)* @shadow.standard..byte_MtoUShort 
@shadow.standard..ubyte_MtoUShort = alias i16 (i8), i16 (i8)* @shadow.standard..byte_MtoUShort
@shadow.standard..ubyte_MtoInt = alias i32 (i8), i32 (i8)* @shadow.standard..byte_MtoUInt
@shadow.standard..ubyte_MtoUInt = alias i32 (i8), i32 (i8)* @shadow.standard..byte_MtoUInt
@shadow.standard..ubyte_MtoCode = alias i32 (i8), i32 (i8)* @shadow.standard..byte_MtoUInt
@shadow.standard..ubyte_MtoLong = alias i64 (i8), i64 (i8)* @shadow.standard..byte_MtoULong
@shadow.standard..ubyte_MtoULong = alias i64 (i8), i64 (i8)* @shadow.standard..byte_MtoULong

define float @shadow.standard..ubyte_MtoFloat(i8) #0 {
	%2 = uitofp i8 %0 to float
	ret float %2
}

define double @shadow.standard..ubyte_MtoDouble(i8) #0 {
	%2 = uitofp i8 %0 to double
	ret double %2
}

@shadow.standard..ubyte_Mones = alias i8 (i8), i8 (i8)* @shadow.standard..byte_Mones
@shadow.standard..ubyte_MleadingZeroes = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MleadingZeroes
@shadow.standard..ubyte_MtrailingZeroes = alias i8 (i8), i8 (i8)* @shadow.standard..byte_MtrailingZeroes	

; shadow.standard@Short native methods

define i16 @shadow.standard..short_MbitComplement(i16) #0 {
	%2 = xor i16 %0, -1
	ret i16 %2
}
define i16 @shadow.standard..short_MbitOr_short(i16, i16) #0 {
	%3 = or i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_MbitXor_short(i16, i16) #0 {
	%3 = xor i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_MbitAnd_short(i16, i16) #0 {
	%3 = and i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_MbitShiftLeft_uint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = shl i16 %0, %5
	ret i16 %6
	ret i16 0
}
define i16 @shadow.standard..short_MbitShiftRight_uint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = ashr i16 %0, %5
	ret i16 %6
	%8 = ashr i16 %0, 15
	ret i16 %8
}
define i16 @shadow.standard..short_MbitRotateLeft_uint(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = shl i16 %0, %4
	%7 = lshr i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}
define i16 @shadow.standard..short_MbitRotateRight_uint(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = lshr i16 %0, %4
	%7 = shl i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}

define i16 @shadow.standard..short_Madd_short(i16, i16) #0 {
	%3 = add i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_Msubtract_short(i16, i16) #0 {
	%3 = sub i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_Mmultiply_short(i16, i16) #0 {
	%3 = mul i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_Mdivide_short(i16, i16) #0 {
	%3 = sdiv i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..short_Mmodulus_short(i16, i16) #0 {
	%3 = srem i16 %0, %1
	ret i16 %3
}

define i32 @shadow.standard..short_Mcompare_short(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @shadow.standard..short_Mequal_short(i16, i16) #0 {
	%3 = icmp eq i16 %0, %1
	ret i1 %3
}

define i16 @shadow.standard..short_Mnegate(i16) #0 {	
	%2 = sub i16 0, %0
	ret i16 %2
}

define i8 @shadow.standard..short_MtoByte(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}
@shadow.standard..short_MtoUByte = alias i8 (i16), i8 (i16)* @shadow.standard..short_MtoByte	
define i16 @shadow.standard..short_MtoShort(i16) #0 {	
	ret i16 %0
}
@shadow.standard..short_MtoUShort = alias i16 (i16), i16 (i16)* @shadow.standard..short_MtoShort 
define i32 @shadow.standard..short_MtoInt(i16) #0 {	
	%2 = sext i16 %0 to i32
	ret i32 %2
}
define i32 @shadow.standard..short_MtoUInt(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}
@shadow.standard..short_MtoCode = alias i32 (i16), i32 (i16)* @shadow.standard..short_MtoUInt 
define i64 @shadow.standard..short_MtoLong(i16) #0 {
	%2 = sext i16 %0 to i64
	ret i64 %2
}
define i64 @shadow.standard..short_MtoULong(i16) #0 {
	%2 = zext i16 %0 to i64
	ret i64 %2
}
define float @shadow.standard..short_MtoFloat(i16) #0 {
	%2 = sitofp i16 %0 to float
	ret float %2
}
define double @shadow.standard..short_MtoDouble(i16) #0 {
	%2 = sitofp i16 %0 to double
	ret double %2
}

define i16 @shadow.standard..short_MflipEndian(i16) #0 {
	%2 = call i16 @llvm.bswap.i16(i16 %0)
	ret i16 %2
}

define i16 @shadow.standard..short_Mones(i16) #0 {
	%2 = call i16 @llvm.ctpop.i16(i16 %0)
	ret i16 %2
}

define i16 @shadow.standard..short_MleadingZeroes(i16) #0 {
	%2 = call i16  @llvm.ctlz.i16(i16 %0, i1 false)
	ret i16 %2
}
define i16 @shadow.standard..short_MtrailingZeroes(i16) #0 {
	%2 = call i16  @llvm.cttz.i16(i16 %0, i1 false)
	ret i16 %2
}
	
define {i16, i1} @shadow.standard..short_MaddWithOverflow_short(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.sadd.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @shadow.standard..short_MsubtractWithOverflow_short(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.ssub.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @shadow.standard..short_MmultiplyWithOverflow_short(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.smul.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

; shadow.standard@UShort native methods

@shadow.standard..ushort_MbitComplement = alias i16 (i16), i16 (i16)* @shadow.standard..short_MbitComplement
@shadow.standard..ushort_MbitOr_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_MbitOr_short
@shadow.standard..ushort_MbitXor_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_MbitXor_short
@shadow.standard..ushort_MbitAnd_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_MbitAnd_short
@shadow.standard..ushort_MbitShiftLeft_uint = alias i16 (i16, i32), i16 (i16, i32)* @shadow.standard..short_MbitShiftLeft_uint
define i16 @shadow.standard..ushort_MbitShiftRight_uint(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = lshr i16 %0, %5
	ret i16 %6
	ret i16 0
}
@shadow.standard..ushort_MbitRotateLeft_uint = alias i16 (i16, i32), i16 (i16, i32)* @shadow.standard..short_MbitRotateLeft_uint
@shadow.standard..ushort_MbitRotateRight_uint = alias i16 (i16, i32), i16 (i16, i32)* @shadow.standard..short_MbitRotateRight_uint

@shadow.standard..ushort_Madd_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_Madd_short
@shadow.standard..ushort_Msubtract_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_Msubtract_short
@shadow.standard..ushort_Mmultiply_ushort = alias i16 (i16, i16), i16 (i16, i16)* @shadow.standard..short_Mmultiply_short
define i16 @shadow.standard..ushort_Mdivide_ushort(i16, i16) #0 {
	%3 = udiv i16 %0, %1
	ret i16 %3
}
define i16 @shadow.standard..ushort_Mmodulus_ushort(i16, i16) #0 {
	%3 = urem i16 %0, %1
	ret i16 %3
}

define i32 @shadow.standard..ushort_Mcompare_ushort(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@shadow.standard..ushort_Mequal_ushort = alias  i1 (i16, i16), i1 (i16, i16)* @shadow.standard..short_Mequal_short

@shadow.standard..ushort_MtoByte = alias i8 (i16),  i8 (i16)* @shadow.standard..short_MtoByte
@shadow.standard..ushort_MtoUByte = alias i8 (i16),  i8 (i16)* @shadow.standard..short_MtoByte
@shadow.standard..ushort_MtoShort = alias i16 (i16), i16 (i16)* @shadow.standard..short_MtoShort 
@shadow.standard..ushort_MtoUShort = alias i16 (i16), i16 (i16)* @shadow.standard..short_MtoShort
@shadow.standard..ushort_MtoInt = alias  i32 (i16), i32 (i16)* @shadow.standard..short_MtoUInt 	
@shadow.standard..ushort_MtoUInt = alias  i32 (i16), i32 (i16)* @shadow.standard..short_MtoUInt
@shadow.standard..ushort_MtoCode = alias  i32 (i16), i32 (i16)* @shadow.standard..short_MtoUInt
@shadow.standard..ushort_MtoLong = alias i64 (i16), i64 (i16)* @shadow.standard..short_MtoULong 
@shadow.standard..ushort_MtoULong = alias i64 (i16), i64 (i16)* @shadow.standard..short_MtoULong
define float @shadow.standard..ushort_MtoFloat(i16) #0 {
	%2 = uitofp i16 %0 to float
	ret float %2
}
define double @shadow.standard..ushort_MtoDouble(i16) #0 {
	%2 = uitofp i16 %0 to double
	ret double %2
}

@shadow.standard..ushort_MflipEndian = alias i16 (i16), i16 (i16)* @shadow.standard..short_MflipEndian
@shadow.standard..ushort_Mones = alias i16 (i16), i16 (i16)* @shadow.standard..short_Mones
@shadow.standard..ushort_MleadingZeroes = alias i16 (i16), i16 (i16)* @shadow.standard..short_MleadingZeroes
@shadow.standard..ushort_MtrailingZeroes = alias i16 (i16), i16 (i16)* @shadow.standard..short_MtrailingZeroes	
	
define {i16, i1} @shadow.standard..ushort_MaddWithOverflow_ushort(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.uadd.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @shadow.standard..ushort_MsubtractWithOverflow_ushort(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.usub.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @shadow.standard..ushort_MmultiplyWithOverflow_ushort(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.umul.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

; shadow.standard@Int native methods

define i32 @shadow.standard..int_MbitComplement(i32) #0 {
	%2 = xor i32 %0, -1
	ret i32 %2
}
define i32 @shadow.standard..int_MbitOr_int(i32, i32) #0 {
	%3 = or i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_MbitXor_int(i32, i32) #0 {
	%3 = xor i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_MbitAnd_int(i32, i32) #0 {
	%3 = and i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_MbitShiftLeft_uint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = shl i32 %0, %1
	ret i32 %5
	ret i32 0
}
define i32 @shadow.standard..int_MbitShiftRight_uint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = ashr i32 %0, %1
	ret i32 %5
	%7 = ashr i32 %0, 31
	ret i32 %7
}
define i32 @shadow.standard..int_MbitRotateLeft_uint(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = shl i32 %0, %3
	%6 = lshr i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}
define i32 @shadow.standard..int_MbitRotateRight_uint(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = lshr i32 %0, %3
	%6 = shl i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}

define i32 @shadow.standard..int_Madd_int(i32, i32) #0 {
	%3 = add i32 %0, %1
	ret i32 %3
}

define i32 @shadow.standard..int_Msubtract_int(i32, i32) #0 {
	%3 = sub i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_Mmultiply_int(i32, i32) #0 {
	%3 = mul i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_Mdivide_int(i32, i32) #0 {
	%3 = sdiv i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_Mmodulus_int(i32, i32) #0 {
	%3 = srem i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..int_Mcompare_int(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}
define i1 @shadow.standard..int_Mequal_int(i32, i32) #0 {
	%3 = icmp eq i32 %0, %1
	ret i1 %3
}

define i32 @shadow.standard..int_Mnegate(i32) #0 {	
	%2 = sub i32 0, %0
	ret i32 %2
}

define i8 @shadow.standard..int_MtoByte(i32) #0 {	
	%2 = trunc i32 %0 to i8
	ret i8 %2
}
@shadow.standard..int_MtoUByte = alias i8 (i32), i8 (i32)* @shadow.standard..int_MtoByte  
define i16 @shadow.standard..int_MtoShort(i32) #0 {
	%2 = trunc i32 %0 to i16
	ret i16 %2
}
@shadow.standard..int_MtoUShort = alias i16 (i32), i16 (i32)* @shadow.standard..int_MtoShort
define i32 @shadow.standard..int_MtoInt(i32) #0 {	
	ret i32 %0
}
@shadow.standard..int_MtoUInt = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..int_MtoCode = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt

define i64 @shadow.standard..int_MtoLong(i32) #0 {
	%2 = sext i32 %0 to i64
	ret i64 %2
}
define i64 @shadow.standard..int_MtoULong(i32) #0 {
	%2 = zext i32 %0 to i64
	ret i64 %2
} 
define float @shadow.standard..int_MtoFloat(i32) #0 {
	%2 = sitofp i32 %0 to float
	ret float %2
}
define double @shadow.standard..int_MtoDouble(i32) #0 {
	%2 = sitofp i32 %0 to double
	ret double %2
}

define i32 @shadow.standard..int_MflipEndian(i32) #0 {
	%2 = call i32 @llvm.bswap.i32(i32 %0)
	ret i32 %2
}

define i32 @shadow.standard..int_Mones(i32) #0 {
	%2 = call i32 @llvm.ctpop.i32(i32 %0)
	ret i32 %2
}

define i32 @shadow.standard..int_MleadingZeroes(i32) #0 {
	%2 = call i32  @llvm.ctlz.i32(i32 %0, i1 false)
	ret i32 %2
}
define i32 @shadow.standard..int_MtrailingZeroes(i32) #0 {
	%2 = call i32  @llvm.cttz.i32(i32 %0, i1 false)
	ret i32 %2
}
	
define {i32, i1} @shadow.standard..int_MaddWithOverflow_int(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.sadd.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @shadow.standard..int_MsubtractWithOverflow_int(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.ssub.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @shadow.standard..int_MmultiplyWithOverflow_int(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.smul.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

; shadow.standard@UInt native methods

@shadow.standard..uint_MbitComplement = alias i32 (i32), i32 (i32)* @shadow.standard..int_MbitComplement
@shadow.standard..uint_MbitOr_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitOr_int
@shadow.standard..uint_MbitXor_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitXor_int
@shadow.standard..uint_MbitAnd_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitAnd_int
@shadow.standard..uint_MbitShiftLeft_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitShiftLeft_uint
define i32 @shadow.standard..uint_MbitShiftRight_uint(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = lshr i32 %0, %1
	ret i32 %5
	ret i32 0
}
@shadow.standard..uint_MbitRotateLeft_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitRotateLeft_uint
@shadow.standard..uint_MbitRotateRight_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitRotateRight_uint

@shadow.standard..uint_Madd_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Madd_int
@shadow.standard..uint_Msubtract_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Msubtract_int
@shadow.standard..uint_Mmultiply_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Mmultiply_int
define i32 @shadow.standard..uint_Mdivide_uint(i32, i32) #0 {
	%3 = udiv i32 %0, %1
	ret i32 %3
}
define i32 @shadow.standard..uint_Mmodulus_uint(i32, i32) #0 {
	%3 = urem i32 %0, %1
	ret i32 %3
}

define i32 @shadow.standard..uint_Mcompare_uint(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@shadow.standard..uint_Mequal_uint = alias i1 (i32, i32), i1 (i32, i32)* @shadow.standard..int_Mequal_int

@shadow.standard..uint_MtoByte = alias i8 (i32), i8 (i32)* @shadow.standard..int_MtoByte 	
@shadow.standard..uint_MtoUByte = alias i8 (i32), i8 (i32)* @shadow.standard..int_MtoByte
@shadow.standard..uint_MtoShort = alias i16 (i32), i16 (i32)* @shadow.standard..int_MtoShort 
@shadow.standard..uint_MtoUShort = alias i16 (i32), i16 (i32)* @shadow.standard..int_MtoShort
@shadow.standard..uint_MtoInt = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..uint_MtoUInt = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..uint_MtoCode = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..uint_MtoLong = alias i64 (i32), i64 (i32)* @shadow.standard..int_MtoULong 
@shadow.standard..uint_MtoULong = alias i64 (i32), i64 (i32)* @shadow.standard..int_MtoULong
define float @shadow.standard..uint_MtoFloat(i32) #0 {
	%2 = uitofp i32 %0 to float
	ret float %2
}
define double @shadow.standard..uint_MtoDouble(i32) #0 {
	%2 = uitofp i32 %0 to double
	ret double %2
}

@shadow.standard..uint_MflipEndian = alias i32 (i32), i32 (i32)* @shadow.standard..int_MflipEndian
@shadow.standard..uint_Mones = alias i32 (i32), i32 (i32)* @shadow.standard..int_Mones
@shadow.standard..uint_MleadingZeroes = alias i32 (i32), i32 (i32)* @shadow.standard..int_MleadingZeroes
@shadow.standard..uint_MtrailingZeroes = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtrailingZeroes	
	
define {i32, i1} @shadow.standard..uint_MaddWithOverflow_uint(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.uadd.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @shadow.standard..uint_MsubtractWithOverflow_uint(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.usub.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @shadow.standard..uint_MmultiplyWithOverflow_uint(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.umul.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

; shadow.standard@Code native methods

@shadow.standard..code_MtoByte = alias i8 (i32), i8 (i32)* @shadow.standard..int_MtoByte
@shadow.standard..code_MtoUByte = alias i8 (i32), i8 (i32)* @shadow.standard..int_MtoByte
@shadow.standard..code_MtoShort = alias i16 (i32), i16 (i32)* @shadow.standard..int_MtoShort
@shadow.standard..code_MtoUShort = alias i16 (i32), i16 (i32)* @shadow.standard..int_MtoShort
@shadow.standard..code_MtoInt = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..code_MtoUInt = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..code_MtoCode = alias i32 (i32), i32 (i32)* @shadow.standard..int_MtoInt
@shadow.standard..code_MtoLong = alias i64 (i32), i64 (i32)* @shadow.standard..int_MtoULong
@shadow.standard..code_MtoULong = alias i64 (i32), i64 (i32)* @shadow.standard..int_MtoULong
@shadow.standard..code_MtoFloat = alias float (i32), float (i32)* @shadow.standard..uint_MtoFloat
@shadow.standard..code_MtoDouble = alias double (i32), double (i32)* @shadow.standard..uint_MtoDouble

@shadow.standard..code_MbitComplement = alias i32 (i32), i32 (i32)* @shadow.standard..int_MbitComplement
@shadow.standard..code_MbitOr_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitOr_int
@shadow.standard..code_MbitXor_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitXor_int
@shadow.standard..code_MbitAnd_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitAnd_int
@shadow.standard..code_MbitShiftLeft_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitShiftLeft_uint
@shadow.standard..code_MbitShiftRight_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..uint_MbitShiftRight_uint
@shadow.standard..code_MbitRotateLeft_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitRotateLeft_uint
@shadow.standard..code_MbitRotateRight_uint = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_MbitRotateRight_uint

@shadow.standard..code_Madd_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Madd_int
@shadow.standard..code_Msubtract_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Msubtract_int
@shadow.standard..code_Mmultiply_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..int_Mmultiply_int
@shadow.standard..code_Mdivide_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..uint_Mdivide_uint
@shadow.standard..code_Mmodulus_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..uint_Mmodulus_uint
@shadow.standard..code_Mcompare_code = alias i32 (i32, i32), i32 (i32, i32)* @shadow.standard..uint_Mcompare_uint
@shadow.standard..code_Mequal_code = alias i1 (i32, i32), i1 (i32, i32)* @shadow.standard..int_Mequal_int

; shadow.standard@Long native methods

define i64 @shadow.standard..long_MbitComplement(i64) #0 {
	%2 = xor i64 %0, -1
	ret i64 %2
}
define i64 @shadow.standard..long_MbitOr_long(i64, i64) #0 {
	%3 = or i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_MbitXor_long(i64, i64) #0 {
	%3 = xor i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_MbitAnd_long(i64, i64) #0 {
	%3 = and i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_MbitShiftLeft_uint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = shl i64 %0, %5
	ret i64 %6
	ret i64 0
}
define i64 @shadow.standard..long_MbitShiftRight_uint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = ashr i64 %0, %5
	ret i64 %6
	%8 = ashr i64 %0, 63
	ret i64 %8
}
define i64 @shadow.standard..long_MbitRotateLeft_uint(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = shl i64 %0, %4
	%7 = lshr i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}
define i64 @shadow.standard..long_MbitRotateRight_uint(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = lshr i64 %0, %4
	%7 = shl i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}

define i64 @shadow.standard..long_Madd_long(i64, i64) #0 {
	%3 = add i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_Msubtract_long(i64, i64) #0 {
	%3 = sub i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_Mmultiply_long(i64, i64) #0 {
	%3 = mul i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_Mdivide_long(i64, i64) #0 {
	%3 = sdiv i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..long_Mmodulus_long(i64, i64) #0 {
	%3 = srem i64 %0, %1
	ret i64 %3
}

define i32 @shadow.standard..long_Mcompare_long(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @shadow.standard..long_Mequal_long(i64, i64) #0 {
	%3 = icmp eq i64 %0, %1
	ret i1 %3
}

define i64 @shadow.standard..long_Mnegate(i64) #0 {	
	%2 = sub i64 0, %0
	ret i64 %2
}

define i8 @shadow.standard..long_MtoByte(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}
@shadow.standard..long_MtoUByte = alias i8 (i64), i8 (i64)* @shadow.standard..long_MtoByte
define i16 @shadow.standard..long_MtoShort(i64) #0 {
	%2 = trunc i64 %0 to i16
	ret i16 %2
}
@shadow.standard..long_MtoUShort = alias i16 (i64), i16 (i64)* @shadow.standard..long_MtoShort 
define i32 @shadow.standard..long_MtoInt(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}
@shadow.standard..long_MtoUInt = alias i32 (i64), i32 (i64)* @shadow.standard..long_MtoInt 
@shadow.standard..long_MtoCode = alias i32 (i64), i32 (i64)* @shadow.standard..long_MtoInt
define i64 @shadow.standard..long_MtoLong(i64) #0 {
	ret i64 %0
}
@shadow.standard..long_MtoULong = alias i64 (i64), i64 (i64)* @shadow.standard..long_MtoLong
define float @shadow.standard..long_MtoFloat(i64) #0 {
	%2 = sitofp i64 %0 to float
	ret float %2
}
define double @shadow.standard..long_MtoDouble(i64) #0 {
	%2 = sitofp i64 %0 to double
	ret double %2
}

define i64 @shadow.standard..long_MflipEndian(i64) #0 {
	%2 = call i64 @llvm.bswap.i64(i64 %0)
	ret i64 %2
}

define i64 @shadow.standard..long_Mones(i64) #0 {
	%2 = call i64 @llvm.ctpop.i64(i64 %0)
	ret i64 %2
}

define i64 @shadow.standard..long_MleadingZeroes(i64) #0 {
	%2 = call i64  @llvm.ctlz.i64(i64 %0, i1 false)
	ret i64 %2
}
define i64 @shadow.standard..long_MtrailingZeroes(i64) #0 {
	%2 = call i64  @llvm.cttz.i64(i64 %0, i1 false)
	ret i64 %2
}
	
;define {i64, i1} @shadow.standard..long_MaddWithOverflow_long(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.sadd.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @shadow.standard..long_MsubtractWithOverflow_long(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.ssub.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @shadow.standard..long_MmultiplyWithOverflow_long(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.smul.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}


; shadow.standard@ULong native methods

@shadow.standard..ulong_MbitComplement = alias i64 (i64), i64 (i64)* @shadow.standard..long_MbitComplement
@shadow.standard..ulong_MbitOr_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_MbitOr_long
@shadow.standard..ulong_MbitXor_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_MbitXor_long
@shadow.standard..ulong_MbitAnd_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_MbitAnd_long
@shadow.standard..ulong_MbitShiftLeft_uint = alias i64 (i64, i32), i64 (i64, i32)* @shadow.standard..long_MbitShiftLeft_uint
define i64 @shadow.standard..ulong_MbitShiftRight_uint(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = lshr i64 %0, %5
	ret i64 %6
	ret i64 0
}
@shadow.standard..ulong_MbitRotateLeft_uint = alias i64 (i64, i32), i64 (i64, i32)* @shadow.standard..long_MbitRotateLeft_uint
@shadow.standard..ulong_MbitRotateRight_uint = alias i64 (i64, i32), i64 (i64, i32)* @shadow.standard..long_MbitRotateRight_uint

@shadow.standard..ulong_Madd_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_Madd_long
@shadow.standard..ulong_Msubtract_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_Msubtract_long
@shadow.standard..ulong_Mmultiply_ulong = alias i64 (i64, i64), i64 (i64, i64)* @shadow.standard..long_Mmultiply_long
define i64 @shadow.standard..ulong_Mdivide_ulong(i64, i64) #0 {
	%3 = udiv i64 %0, %1
	ret i64 %3
}
define i64 @shadow.standard..ulong_Mmodulus_ulong(i64, i64) #0 {
	%3 = urem i64 %0, %1
	ret i64 %3
}

define i32 @shadow.standard..ulong_Mcompare_ulong(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@shadow.standard..ulong_Mequal_ulong = alias i1 (i64, i64), i1 (i64, i64)* @shadow.standard..long_Mequal_long

@shadow.standard..ulong_MtoByte = alias i8 (i64), i8 (i64)* @shadow.standard..long_MtoByte 
@shadow.standard..ulong_MtoUByte = alias i8 (i64), i8 (i64)* @shadow.standard..long_MtoByte
@shadow.standard..ulong_MtoShort = alias i16 (i64), i16 (i64)* @shadow.standard..long_MtoShort 
@shadow.standard..ulong_MtoUShort = alias i16 (i64), i16 (i64)* @shadow.standard..long_MtoShort
@shadow.standard..ulong_MtoInt = alias i32 (i64), i32 (i64)* @shadow.standard..long_MtoInt 
@shadow.standard..ulong_MtoUInt = alias i32 (i64), i32 (i64)* @shadow.standard..long_MtoInt
@shadow.standard..ulong_MtoCode = alias i32 (i64), i32 (i64)* @shadow.standard..long_MtoInt
@shadow.standard..ulong_MtoLong = alias i64 (i64), i64 (i64)* @shadow.standard..long_MtoLong 
@shadow.standard..ulong_MtoULong = alias i64 (i64), i64 (i64)* @shadow.standard..long_MtoLong
define float @shadow.standard..ulong_MtoFloat(i64) #0 {
	%2 = uitofp i64 %0 to float
	ret float %2
}
define double @shadow.standard..ulong_MtoDouble(i64) #0 {
	%2 = uitofp i64 %0 to double
	ret double %2
}

@shadow.standard..ulong_MflipEndian = alias i64 (i64), i64 (i64)* @shadow.standard..long_MflipEndian
@shadow.standard..ulong_Mones = alias i64 (i64), i64 (i64)* @shadow.standard..long_Mones
@shadow.standard..ulong_MleadingZeroes = alias i64 (i64), i64 (i64)* @shadow.standard..long_MleadingZeroes
@shadow.standard..ulong_MtrailingZeroes = alias i64 (i64), i64 (i64)* @shadow.standard..long_MtrailingZeroes	
	
;define {i64, i1} @shadow.standard..ulong_MaddWithOverflow_ulong(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.uadd.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @shadow.standard..ulong_MsubtractWithOverflow_ulong(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.usub.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @shadow.standard..ulong_MmultiplyWithOverflow_ulong(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.umul.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

; shadow.standard@Float native methods

define float @shadow.standard..float_Madd_float(float, float) #0 {
	%3 = fadd float %0, %1
	ret float %3
}
define float @shadow.standard..float_Msubtract_float(float, float) #0 {
	%3 = fsub float %0, %1
	ret float %3
}
define float @shadow.standard..float_Mmultiply_float(float, float) #0 {
	%3 = fmul float %0, %1
	ret float %3
}
define float @shadow.standard..float_Mdivide_float(float, float) #0 {
	%3 = fdiv float %0, %1
	ret float %3
}
define float @shadow.standard..float_Mmodulus_float(float, float) #0 {
	%3 = frem float %0, %1
	ret float %3
}

define i32 @shadow.standard..float_Mcompare_float(float, float) #0 {
	%3 = fcmp ueq float %0, %1
	%4 = fcmp olt float %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @shadow.standard..float_Mequal_float(float, float) #0 {
	%3 = fcmp ord float %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq float %0, %1
	ret i1 %5
	%7 = fcmp uno float %0, 0.
	%8 = fcmp uno float %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i32 @shadow.standard..float_Mraw(float) #0 {
	%2 = bitcast float %0 to i32
	ret i32 %2
}

define float @shadow.standard..float_Mnegate(float) #0 {	
	%2 = fsub float -0.0, %0
	ret float %2
}

define i8 @shadow.standard..float_MtoByte(float) #0 {	
	%2 = fptosi float %0 to i8
	ret i8 %2
}

define i8 @shadow.standard..float_MtoUByte(float) #0 {	
	%2 = fptoui float %0 to i8
	ret i8 %2
}

define i16 @shadow.standard..float_MtoShort(float) #0 {
	%2 = fptosi float %0 to i16
	ret i16 %2
}

define i16 @shadow.standard..float_MtoUShort(float) #0 {	
	%2 = fptoui float %0 to i16
	ret i16 %2
}

define i32 @shadow.standard..float_MtoInt(float) #0 {	
	%2 = fptosi float %0 to i32
	ret i32 %2
}

define i32 @shadow.standard..float_MtoUInt(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i32 @shadow.standard..float_MtoCode(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i64 @shadow.standard..float_MtoLong(float) #0 {
	%2 = fptosi float %0 to i64
	ret i64 %2
}

define i64 @shadow.standard..float_MtoULong(float) #0 {
	%2 = fptoui float %0 to i64
	ret i64 %2
}

define float @shadow.standard..float_MtoFloat(float) #0 {
	ret float %0
}

define double @shadow.standard..float_MtoDouble(float) #0 {	
	%2 = fpext float %0 to double
	ret double %2
}

define float @shadow.standard..float_MsquareRoot(float) #0 {	
	%2 = call float @llvm.sqrt.f32(float %0)
	ret float %2
}	

define float @shadow.standard..float_Mpower_int(float, i32) #0 {	
	%3 = call float @llvm.powi.f32(float %0, i32 %1)
	ret float %3
}	

define float @shadow.standard..float_Mpower_float(float, float) #0 {	
	%3 = call float @llvm.pow.f32(float %0, float %1)
	ret float %3
}

define float @shadow.standard..float_Msin(float) #0 {	
	%2 = call float @llvm.sin.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_Mcos(float) #0 {	
	%2 = call float @llvm.cos.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_MlogBaseE(float) #0 {	
	%2 = call float @llvm.log.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_MlogBase2(float) #0 {	
	%2 = call float @llvm.log2.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_MlogBase10(float) #0 {	
	%2 = call float @llvm.log10.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_MmultiplyAdd_float_float(float, float, float) #0 {	
	%4 = call float @llvm.fma.f32(float %0, float %1, float %2)
	ret float %4
}	

define float @shadow.standard..float_Mfloor(float) #0 {
	%2 = call float @llvm.floor.f32(float %0)
	ret float %2
}

define float @shadow.standard..float_Mceiling(float) #0 {	
	%2 = call float @llvm.ceil.f32(float %0)
	ret float %2
}

; shadow.standard@Double native methods

define double @shadow.standard..double_Madd_double(double, double) #0 {
	%3 = fadd double %0, %1
	ret double %3
}
define double @shadow.standard..double_Msubtract_double(double, double) #0 {
	%3 = fsub double %0, %1
	ret double %3
}
define double @shadow.standard..double_Mmultiply_double(double, double) #0 {
	%3 = fmul double %0, %1
	ret double %3
}
define double @shadow.standard..double_Mdivide_double(double, double) #0 {
	%3 = fdiv double %0, %1
	ret double %3
}
define double @shadow.standard..double_Mmodulus_double(double, double) #0 {
	%3 = frem double %0, %1
	ret double %3
}

define i32 @shadow.standard..double_Mcompare_double(double, double) #0 {
	%3 = fcmp ueq double %0, %1
	%4 = fcmp olt double %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @shadow.standard..double_Mequal_double(double, double) #0 {
	%3 = fcmp ord double %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq double %0, %1
	ret i1 %5
	%7 = fcmp uno double %0, 0.
	%8 = fcmp uno double %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i64 @shadow.standard..double_Mraw(double) #0 {
	%2 = bitcast double %0 to i64
	ret i64 %2
}

define double @shadow.standard..double_Mnegate(double) #0 {	
	%2 = fsub double -0.0, %0
	ret double %2
}

define i8 @shadow.standard..double_MtoByte(double) #0 {	
	%2 = fptosi double %0 to i8
	ret i8 %2
}

define i8 @shadow.standard..double_MtoUByte(double) #0 {	
	%2 = fptoui double %0 to i8
	ret i8 %2
}

define i16 @shadow.standard..double_MtoShort(double) #0 {
	%2 = fptosi double %0 to i16
	ret i16 %2
}

define i16 @shadow.standard..double_MtoUShort(double) #0 {	
	%2 = fptoui double %0 to i16
	ret i16 %2
}

define i32 @shadow.standard..double_MtoInt(double) #0 {	
	%2 = fptosi double %0 to i32
	ret i32 %2
}

define i32 @shadow.standard..double_MtoUInt(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i32 @shadow.standard..double_MtoCode(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i64 @shadow.standard..double_MtoLong(double) #0 {
	%2 = fptosi double %0 to i64
	ret i64 %2
}

define i64 @shadow.standard..double_MtoULong(double) #0 {
	%2 = fptoui double %0 to i64
	ret i64 %2
}

define float @shadow.standard..double_MtoFloat(double) #0 {
	%2 = fptrunc double %0 to float
	ret float %2
}

define double @shadow.standard..double_MtoDouble(double) #0 {	
	ret double %0
}

define double @shadow.standard..double_MsquareRoot(double) #0 {	
	%2 = call double @llvm.sqrt.f64(double %0)
	ret double %2
}	

define double @shadow.standard..double_Mpower_int(double, i32) #0 {	
	%3 = call double @llvm.powi.f64(double %0, i32 %1)
	ret double %3
}	

define double @shadow.standard..double_Mpower_double(double, double) #0 {	
	%3 = call double @llvm.pow.f64(double %0, double %1)
	ret double %3
}

define double @shadow.standard..double_Msin(double) #0 {	
	%2 = call double @llvm.sin.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_Mcos(double) #0 {	
	%2 = call double @llvm.cos.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_MlogBaseE(double) #0 {	
	%2 = call double @llvm.log.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_MlogBase2(double) #0 {	
	%2 = call double @llvm.log2.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_MlogBase10(double) #0 {	
	%2 = call double @llvm.log10.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_MmultiplyAdd_double_double(double, double, double) #0 {	
	%4 = call double @llvm.fma.f64(double %0, double %1, double %2)
	ret double %4
}	

define double @shadow.standard..double_Mfloor(double) #0 {
	%2 = call double @llvm.floor.f64(double %0)
	ret double %2
}

define double @shadow.standard..double_Mceiling(double) #0 {	
	%2 = call double @llvm.ceil.f64(double %0)
	ret double %2
}