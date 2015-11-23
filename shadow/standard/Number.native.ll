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

define i8 @"shadow:standard@byte.bitComplement()"(i8) #0 {
	%2 = xor i8 %0, -1
	ret i8 %2
}
define i8 @"shadow:standard@byte.bitOr(shadow:standard@byte)"(i8, i8) #0 {
	%3 = or i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.bitXor(shadow:standard@byte)"(i8, i8) #0 {
	%3 = xor i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.bitAnd(shadow:standard@byte)"(i8, i8) #0 {
	%3 = and i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.bitShiftLeft(shadow:standard@uint)"(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = shl i8 %0, %5
	ret i8 %6
	ret i8 0
}
define i8 @"shadow:standard@byte.bitShiftRight(shadow:standard@uint)"(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = ashr i8 %0, %5
	ret i8 %6
	%8 = ashr i8 %0, 7
	ret i8 %8
}
define i8 @"shadow:standard@byte.bitRotateLeft(shadow:standard@uint)"(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = shl i8 %0, %4
	%7 = lshr i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}
define i8 @"shadow:standard@byte.bitRotateRight(shadow:standard@uint)"(i8, i32) #0 {
	%3 = and i32 %1, 7
	%4 = trunc i32 %3 to i8
	%5 = sub i8 8, %4
	%6 = lshr i8 %0, %4
	%7 = shl i8 %0, %5
	%8 = or i8 %6, %7
	ret i8 %8
}

define i8 @"shadow:standard@byte.add(shadow:standard@byte)"(i8, i8) #0 {
	%3 = add i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.subtract(shadow:standard@byte)"(i8, i8) #0 {
	%3 = sub i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.multiply(shadow:standard@byte)"(i8, i8) #0 {
	%3 = mul i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.divide(shadow:standard@byte)"(i8, i8) #0 {
	%3 = sdiv i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@byte.modulus(shadow:standard@byte)"(i8, i8) #0 {
	%3 = srem i8 %0, %1
	ret i8 %3
}

define i32 @"shadow:standard@byte.compare(shadow:standard@byte)"(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @"shadow:standard@byte.equal(shadow:standard@byte)"(i8, i8) #0 {
	%3 = icmp eq i8 %0, %1
	ret i1 %3
}

define i8 @"shadow:standard@byte.negate()"(i8) #0 {	
	%2 = sub i8 0, %0
	ret i8 %2
}

define i8 @"shadow:standard@byte.toByte()"(i8) #0 {	
	ret i8 %0
}
@"shadow:standard@byte.toUByte()" = alias i8 (i8)* @"shadow:standard@byte.toByte()"
define i16 @"shadow:standard@byte.toShort()"(i8) #0 {	
	%2 = sext i8 %0 to i16
	ret i16 %2
}
define i16 @"shadow:standard@byte.toUShort()"(i8) #0 {
	%2 = zext i8 %0 to i16
	ret i16 %2
}
define i32 @"shadow:standard@byte.toInt()"(i8) #0 {	
	%2 = sext i8 %0 to i32
	ret i32 %2
}
define i32 @"shadow:standard@byte.toUInt()"(i8) #0 {
	%2 = zext i8 %0 to i32
	ret i32 %2
}
@"shadow:standard@byte.toCode()" = alias i32 (i8)* @"shadow:standard@byte.toUInt()"
define i64 @"shadow:standard@byte.toLong()"(i8) #0 {
	%2 = sext i8 %0 to i64
	ret i64 %2
}
define i64 @"shadow:standard@byte.toULong()"(i8) #0 {
	%2 = zext i8 %0 to i64
	ret i64 %2
}
define float @"shadow:standard@byte.toFloat()"(i8) #0 {
	%2 = sitofp i8 %0 to float
	ret float %2
}
define double @"shadow:standard@byte.toDouble()"(i8) #0 {
	%2 = sitofp i8 %0 to double
	ret double %2
}

define i8 @"shadow:standard@byte.ones()"(i8) #0 {
	%2 = call i8 @llvm.ctpop.i8(i8 %0)
	ret i8 %2
}

define i8 @"shadow:standard@byte.leadingZeroes()"(i8) #0 {
	%2 = call i8  @llvm.ctlz.i8(i8 %0, i1 false)
	ret i8 %2
}
define i8 @"shadow:standard@byte.trailingZeroes()"(i8) #0 {
	%2 = call i8  @llvm.cttz.i8(i8 %0, i1 false)
	ret i8 %2
}

; shadow.standard@UByte native methods

@"shadow:standard@ubyte.bitComplement = alias i8 (i8)* @"shadow:standard@byte.bitComplement()"
@"shadow:standard@ubyte.bitOrshadow:standard@ubyte = alias i8 (i8, i8)* @"shadow:standard@byte.bitOr(shadow:standard@byte)"
@"shadow:standard@ubyte.bitXorshadow:standard@ubyte = alias i8 (i8, i8)* @"shadow:standard@byte.bitXor(shadow:standard@byte)"
@"shadow:standard@ubyte.bitAndshadow:standard@ubyte = alias i8 (i8, i8)* @"shadow:standard@byte.bitAnd(shadow:standard@byte)"
@"shadow:standard@ubyte.bitShiftLeftshadow:standard@uint = alias i8 (i8, i32)* @"shadow:standard@byte.bitShiftLeft(shadow:standard@uint)"
define i8 @"shadow:standard@ubyte.bitShiftRight(shadow:standard@uint)"(i8, i32) #0 {
	%3 = icmp ult i32 %1, 8
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i8
	%6 = lshr i8 %0, %5
	ret i8 %6
	ret i8 0
}
@"shadow:standard@ubyte.bitRotateLeft(shadow:standard@uint)" = alias i8 (i8, i32)* @"shadow:standard@byte.bitRotateLeft(shadow:standard@uint)"
@"shadow:standard@ubyte.bitRotateRight(shadow:standard@uint)" = alias i8 (i8, i32)* @"shadow:standard@byte.bitRotateRight(shadow:standard@uint)"

@"shadow:standard@ubyte.add(shadow:standard@ubyte)" = alias i8 (i8, i8)* @"shadow:standard@byte.add(shadow:standard@byte)"
@"shadow:standard@ubyte.subtract(shadow:standard@ubyte)" = alias i8 (i8, i8)* @"shadow:standard@byte.subtract(shadow:standard@byte)"
@"shadow:standard@ubyte.multiply(shadow:standard@ubyte)" = alias i8 (i8, i8)* @"shadow:standard@byte.multiply(shadow:standard@byte)"
define i8 @"shadow:standard@ubyte.divide(shadow:standard@ubyte)"(i8, i8) #0 {
	%3 = udiv i8 %0, %1
	ret i8 %3
}
define i8 @"shadow:standard@ubyte.modulus(shadow:standard@ubyte)"(i8, i8) #0 {
	%3 = urem i8 %0, %1
	ret i8 %3
}

define i32 @"shadow:standard@ubyte.compare(shadow:standard@ubyte)"(i8, i8) #0 {
	%3 = icmp ne i8 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i8 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@"shadow:standard@ubyte.equal(shadow:standard@ubyte)" = alias i1 (i8, i8)* @"shadow:standard@byte.equal(shadow:standard@byte)"

@"shadow:standard@ubyte.toByte()" = alias i8 (i8)* @"shadow:standard@byte.toByte()" 
@"shadow:standard@ubyte.toUByte()" = alias i8 (i8)* @"shadow:standard@byte.toByte()"
@"shadow:standard@ubyte.toShort()" = alias i16 (i8)* @"shadow:standard@byte.toUShort()" 
@"shadow:standard@ubyte.toUShort()" = alias i16 (i8)* @"shadow:standard@byte.toUShort()"
@"shadow:standard@ubyte.toInt()" = alias i32 (i8)* @"shadow:standard@byte.toUInt()"
@"shadow:standard@ubyte.toUInt()" = alias i32 (i8)* @"shadow:standard@byte.toUInt()"
@"shadow:standard@ubyte.toCode()" = alias i32 (i8)* @"shadow:standard@byte.toUInt()"
@"shadow:standard@ubyte.toLong()" = alias i64 (i8)* @"shadow:standard@byte.toULong()"
@"shadow:standard@ubyte.toULong()" = alias i64 (i8)* @"shadow:standard@byte.toULong()"

define float @"shadow:standard@ubyte.toFloat()"(i8) #0 {
	%2 = uitofp i8 %0 to float
	ret float %2
}

define double @"shadow:standard@ubyte.toDouble()"(i8) #0 {
	%2 = uitofp i8 %0 to double
	ret double %2
}

@"shadow:standard@ubyte.ones()" = alias i8 (i8)* @"shadow:standard@byte.ones()"
@"shadow:standard@ubyte.leadingZeroes()" = alias i8 (i8)* @"shadow:standard@byte.leadingZeroes()"
@"shadow:standard@ubyte.trailingZeroes()" = alias i8 (i8)* @"shadow:standard@byte.trailingZeroes()"	

; shadow.standard@Short native methods

define i16 @"shadow:standard@short.bitComplement()"(i16) #0 {
	%2 = xor i16 %0, -1
	ret i16 %2
}
define i16 @"shadow:standard@short.bitOr(shadow:standard@short)"(i16, i16) #0 {
	%3 = or i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.bitXor(shadow:standard@short)"(i16, i16) #0 {
	%3 = xor i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.bitAnd(shadow:standard@short)"(i16, i16) #0 {
	%3 = and i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.bitShiftLeft(shadow:standard@uint)"(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = shl i16 %0, %5
	ret i16 %6
	ret i16 0
}
define i16 @"shadow:standard@short.bitShiftRight(shadow:standard@uint)"(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = ashr i16 %0, %5
	ret i16 %6
	%8 = ashr i16 %0, 15
	ret i16 %8
}
define i16 @"shadow:standard@short.bitRotateLeft(shadow:standard@uint)"(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = shl i16 %0, %4
	%7 = lshr i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}
define i16 @"shadow:standard@short.bitRotateRight(shadow:standard@uint)"(i16, i32) #0 {
	%3 = and i32 %1, 15
	%4 = trunc i32 %3 to i16
	%5 = sub i16 16, %4
	%6 = lshr i16 %0, %4
	%7 = shl i16 %0, %5
	%8 = or i16 %6, %7
	ret i16 %8
}

define i16 @"shadow:standard@short.add(shadow:standard@short)"(i16, i16) #0 {
	%3 = add i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.subtract(shadow:standard@short)"(i16, i16) #0 {
	%3 = sub i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.multiply(shadow:standard@short)"(i16, i16) #0 {
	%3 = mul i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.divide(shadow:standard@short)"(i16, i16) #0 {
	%3 = sdiv i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@short.modulus(shadow:standard@short)"(i16, i16) #0 {
	%3 = srem i16 %0, %1
	ret i16 %3
}

define i32 @"shadow:standard@short.compare(shadow:standard@short)"(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @"shadow:standard@short.equal(shadow:standard@short)"(i16, i16) #0 {
	%3 = icmp eq i16 %0, %1
	ret i1 %3
}

define i16 @"shadow:standard@short.negate()"(i16) #0 {	
	%2 = sub i16 0, %0
	ret i16 %2
}

define i8 @"shadow:standard@short.toByte()"(i16) #0 {	
	%2 = trunc i16 %0 to i8
	ret i8 %2
}
@"shadow:standard@short.toUByte = alias i8 (i16)* @"shadow:standard@short.toByte	
define i16 @"shadow:standard@short.toShort()"(i16) #0 {	
	ret i16 %0
}
@"shadow:standard@short.toUShort = alias i16 (i16)* @"shadow:standard@short.toShort 
define i32 @"shadow:standard@short.toInt()"(i16) #0 {	
	%2 = sext i16 %0 to i32
	ret i32 %2
}
define i32 @"shadow:standard@short.toUInt()"(i16) #0 {
	%2 = zext i16 %0 to i32
	ret i32 %2
}
@"shadow:standard@short.toCode = alias i32 (i16)* @"shadow:standard@short.toUInt 
define i64 @"shadow:standard@short.toLong()"(i16) #0 {
	%2 = sext i16 %0 to i64
	ret i64 %2
}
define i64 @"shadow:standard@short.toULong()"(i16) #0 {
	%2 = zext i16 %0 to i64
	ret i64 %2
}
define float @"shadow:standard@short.toFloat()"(i16) #0 {
	%2 = sitofp i16 %0 to float
	ret float %2
}
define double @"shadow:standard@short.toDouble()"(i16) #0 {
	%2 = sitofp i16 %0 to double
	ret double %2
}

define i16 @"shadow:standard@short.flipEndian()"(i16) #0 {
	%2 = call i16 @llvm.bswap.i16(i16 %0)
	ret i16 %2
}

define i16 @"shadow:standard@short.ones()"(i16) #0 {
	%2 = call i16 @llvm.ctpop.i16(i16 %0)
	ret i16 %2
}

define i16 @"shadow:standard@short.leadingZeroes()"(i16) #0 {
	%2 = call i16  @llvm.ctlz.i16(i16 %0, i1 false)
	ret i16 %2
}
define i16 @"shadow:standard@short.trailingZeroes()"(i16) #0 {
	%2 = call i16  @llvm.cttz.i16(i16 %0, i1 false)
	ret i16 %2
}
	
define {i16, i1} @"shadow:standard@short.addWithOverflow(shadow:standard@short)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.sadd.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @"shadow:standard@short.subtractWithOverflow(shadow:standard@short)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.ssub.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @"shadow:standard@short.multiplyWithOverflow(shadow:standard@short)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.smul.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

; shadow.standard@UShort native methods

@"shadow:standard@ushort.bitComplement()" = alias i16 (i16)* @"shadow:standard@short.bitComplement()"
@"shadow:standard@ushort.bitOr(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.bitOr(shadow:standard@short)"
@"shadow:standard@ushort.bitXor(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.bitXor(shadow:standard@short)"
@"shadow:standard@ushort.bitAnd(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.bitAnd(shadow:standard@short)"
@"shadow:standard@ushort.bitShiftLeft(shadow:standard@uint)" = alias i16 (i16, i32)* @"shadow:standard@short.bitShiftLeft(shadow:standard@uint)"
define i16 @"shadow:standard@ushort.bitShiftRight(shadow:standard@uint)"(i16, i32) #0 {
	%3 = icmp ult i32 %1, 16
	br i1 %3, label %4, label %7
	%5 = trunc i32 %1 to i16
	%6 = lshr i16 %0, %5
	ret i16 %6
	ret i16 0
}
@"shadow:standard@ushort.bitRotateLeft(shadow:standard@uint)" = alias i16 (i16, i32)* @"shadow:standard@short.bitRotateLeft(shadow:standard@uint)"
@"shadow:standard@ushort.bitRotateRight(shadow:standard@uint)" = alias i16 (i16, i32)* @"shadow:standard@short.bitRotateRight(shadow:standard@uint)"

@"shadow:standard@ushort.add(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.add(shadow:standard@short)"
@"shadow:standard@ushort.subtract(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.subtract(shadow:standard@short)"
@"shadow:standard@ushort.multiply(shadow:standard@ushort)" = alias i16 (i16, i16)* @"shadow:standard@short.multiply(shadow:standard@short)"
define i16 @"shadow:standard@ushort.divide(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = udiv i16 %0, %1
	ret i16 %3
}
define i16 @"shadow:standard@ushort.modulus(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = urem i16 %0, %1
	ret i16 %3
}

define i32 @"shadow:standard@ushort.compare(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = icmp ne i16 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i16 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@"shadow:standard@ushort.equal(shadow:standard@ushort)" = alias i1 (i16, i16)* @"shadow:standard@short.equal(shadow:standard@short)"

@"shadow:standard@ushort.toByte()" = alias i8 (i16)* @"shadow:standard@short.toByte()"
@"shadow:standard@ushort.toUByte()" = alias i8 (i16)* @"shadow:standard@short.toByte()"
@"shadow:standard@ushort.toShort()" = alias i16 (i16)* @"shadow:standard@short.toShort()" 
@"shadow:standard@ushort.toUShort()" = alias i16 (i16)* @"shadow:standard@short.toShort()"
@"shadow:standard@ushort.toInt()" = alias i32 (i16)* @"shadow:standard@short.toUInt()" 	
@"shadow:standard@ushort.toUInt()" = alias i32 (i16)* @"shadow:standard@short.toUInt()"
@"shadow:standard@ushort.toCode()" = alias i32 (i16)* @"shadow:standard@short.toUInt()"
@"shadow:standard@ushort.toLong()" = alias i64 (i16)* @"shadow:standard@short.toULong()" 
@"shadow:standard@ushort.toULong()" = alias i64 (i16)* @"shadow:standard@short.toULong()"
define float @"shadow:standard@ushort.toFloat()"(i16) #0 {
	%2 = uitofp i16 %0 to float
	ret float %2
}
define double @"shadow:standard@ushort.toDouble()"(i16) #0 {
	%2 = uitofp i16 %0 to double
	ret double %2
}

@"shadow:standard@ushort.flipEndian()" = alias i16 (i16)* @"shadow:standard@short.flipEndian()"
@"shadow:standard@ushort.ones()" = alias i16 (i16)* @"shadow:standard@short.ones()"
@"shadow:standard@ushort.leadingZeroes()" = alias i16 (i16)* @"shadow:standard@short.leadingZeroes()"
@"shadow:standard@ushort.trailingZeroes()" = alias i16 (i16)* @"shadow:standard@short.trailingZeroes()"	
	
define {i16, i1} @"shadow:standard@ushort.addWithOverflow(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.uadd.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @"shadow:standard@ushort.subtractWithOverflow(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.usub.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

define {i16, i1} @"shadow:standard@ushort.multiplyWithOverflow(shadow:standard@ushort)"(i16, i16) #0 {
	%3 = call {i16, i1} @llvm.umul.with.overflow.i16(i16 %0, i16 %1)
	ret {i16, i1} %3
}

; shadow.standard@Int native methods

define i32 @"shadow:standard@int.bitComplement()"(i32) #0 {
	%2 = xor i32 %0, -1
	ret i32 %2
}
define i32 @"shadow:standard@int.bitOr(shadow:standard@int)"(i32, i32) #0 {
	%3 = or i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.bitXor(shadow:standard@int)"(i32, i32) #0 {
	%3 = xor i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.bitAnd(shadow:standard@int)"(i32, i32) #0 {
	%3 = and i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.bitShiftLeft(shadow:standard@uint)"(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = shl i32 %0, %1
	ret i32 %5
	ret i32 0
}
define i32 @"shadow:standard@int.bitShiftRight(shadow:standard@uint)"(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = ashr i32 %0, %1
	ret i32 %5
	%7 = ashr i32 %0, 31
	ret i32 %7
}
define i32 @"shadow:standard@int.bitRotateLeft(shadow:standard@uint)"(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = shl i32 %0, %3
	%6 = lshr i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}
define i32 @"shadow:standard@int.bitRotateRight(shadow:standard@uint)"(i32, i32) #0 {
	%3 = and i32 %1, 31
	%4 = sub i32 32, %3
	%5 = lshr i32 %0, %3
	%6 = shl i32 %0, %4
	%7 = or i32 %5, %6
	ret i32 %7
}

define i32 @"shadow:standard@int.add(shadow:standard@int)"(i32, i32) #0 {
	%3 = add i32 %0, %1
	ret i32 %3
}

define i32 @"shadow:standard@int.subtract(shadow:standard@int)"(i32, i32) #0 {
	%3 = sub i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.multiply(shadow:standard@int)"(i32, i32) #0 {
	%3 = mul i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.divide(shadow:standard@int)"(i32, i32) #0 {
	%3 = sdiv i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.modulus(shadow:standard@int)"(i32, i32) #0 {
	%3 = srem i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@int.compare(shadow:standard@int)"(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}
define i1 @"shadow:standard@int.equal(shadow:standard@int)"(i32, i32) #0 {
	%3 = icmp eq i32 %0, %1
	ret i1 %3
}

define i32 @"shadow:standard@int.negate"(i32) #0 {	
	%2 = sub i32 0, %0
	ret i32 %2
}

define i8 @"shadow:standard@int.toByte"(i32) #0 {	
	%2 = trunc i32 %0 to i8
	ret i8 %2
}
@"shadow:standard@int.toUByte()" = alias i8 (i32)* @"shadow:standard@int.toByte()"  
define i16 @"shadow:standard@int.toShort()"(i32) #0 {
	%2 = trunc i32 %0 to i16
	ret i16 %2
}
@"shadow:standard@int.toUShort()" = alias i16 (i32)* @"shadow:standard@int.toShort()"
define i32 @"shadow:standard@int.toInt()"(i32) #0 {	
	ret i32 %0
}
@"shadow:standard@int.toUInt()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@int.toCode()" = alias i32 (i32)* @"shadow:standard@int.toInt()"

define i64 @"shadow:standard@int.toLong"(i32) #0 {
	%2 = sext i32 %0 to i64
	ret i64 %2
}
define i64 @"shadow:standard@int.toULong"(i32) #0 {
	%2 = zext i32 %0 to i64
	ret i64 %2
} 
define float @"shadow:standard@int.toFloat"(i32) #0 {
	%2 = sitofp i32 %0 to float
	ret float %2
}
define double @"shadow:standard@int.toDouble"(i32) #0 {
	%2 = sitofp i32 %0 to double
	ret double %2
}

define i32 @"shadow:standard@int.flipEndian"(i32) #0 {
	%2 = call i32 @llvm.bswap.i32(i32 %0)
	ret i32 %2
}

define i32 @"shadow:standard@int.ones"(i32) #0 {
	%2 = call i32 @llvm.ctpop.i32(i32 %0)
	ret i32 %2
}

define i32 @"shadow:standard@int.leadingZeroes"(i32) #0 {
	%2 = call i32  @llvm.ctlz.i32(i32 %0, i1 false)
	ret i32 %2
}
define i32 @"shadow:standard@int.trailingZeroes"(i32) #0 {
	%2 = call i32  @llvm.cttz.i32(i32 %0, i1 false)
	ret i32 %2
}
	
define {i32, i1} @"shadow:standard@int.addWithOverflow(shadow:standard@int)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.sadd.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @"shadow:standard@int.subtractWithOverflow(shadow:standard@int)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.ssub.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @"shadow:standard@int.multiplyWithOverflow(shadow:standard@int)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.smul.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

; shadow.standard@UInt native methods

@"shadow:standard@uint.bitComplement()" = alias i32 (i32)* @"shadow:standard@int.bitComplement()"
@"shadow:standard@uint.bitOr(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitOr(shadow:standard@int)"
@"shadow:standard@uint.bitXor(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitXor(shadow:standard@int)"
@"shadow:standard@uint.bitAnd(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitAnd(shadow:standard@int)"
@"shadow:standard@uint.bitShiftLeft(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitShiftLeft(shadow:standard@int)"
define i32 @"shadow:standard@uint.bitShiftRight(shadow:standard@uint)"(i32, i32) #0 {
	%3 = icmp ult i32 %1, 32
	br i1 %3, label %4, label %6
	%5 = lshr i32 %0, %1
	ret i32 %5
	ret i32 0
}
@"shadow:standard@uint.bitRotateLeft(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitRotateLeft(shadow:standard@uint)"
@"shadow:standard@uint.bitRotateRight(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitRotateRight(shadow:standard@uint)"

@"shadow:standard@uint.add(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.add(shadow:standard@int)"
@"shadow:standard@uint.subtract(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.subtract(shadow:standard@int)"
@"shadow:standard@uint.multiply(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.multiply(shadow:standard@int)"
define i32 @"shadow:standard@uint.divide(shadow:standard@uint)"(i32, i32) #0 {
	%3 = udiv i32 %0, %1
	ret i32 %3
}
define i32 @"shadow:standard@uint.modulus(shadow:standard@uint)"(i32, i32) #0 {
	%3 = urem i32 %0, %1
	ret i32 %3
}

define i32 @"shadow:standard@uint.compare(shadow:standard@uint)"(i32, i32) #0 {
	%3 = icmp ne i32 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i32 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@"shadow:standard@uint.equal(shadow:standard@uint)" = alias i1 (i32, i32)* @"shadow:standard@int.equal(shadow:standard@int)"

@"shadow:standard@uint.toByte()" = alias i8 (i32)* @"shadow:standard@int.toByte()" 	
@"shadow:standard@uint.toUByte()" = alias i8 (i32)* @"shadow:standard@int.toByte()"
@"shadow:standard@uint.toShort()" = alias i16 (i32)* @"shadow:standard@int.toShort()" 
@"shadow:standard@uint.toUShort()" = alias i16 (i32)* @"shadow:standard@int.toShort()"
@"shadow:standard@uint.toInt()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@uint.toUInt()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@uint.toCode()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@uint.toLong()" = alias i64 (i32)* @"shadow:standard@int.toULong()" 
@"shadow:standard@uint.toULong()" = alias i64 (i32)* @"shadow:standard@int.toULong()"
define float @"shadow:standard@uint.toFloat()"(i32) #0 {
	%2 = uitofp i32 %0 to float
	ret float %2
}
define double @"shadow:standard@uint.toDouble()"(i32) #0 {
	%2 = uitofp i32 %0 to double
	ret double %2
}

@"shadow:standard@uint.flipEndian()" = alias i32 (i32)* @"shadow:standard@int.flipEndian()"
@"shadow:standard@uint.ones()" = alias i32 (i32)* @"shadow:standard@int.ones()"
@"shadow:standard@uint.leadingZeroes()" = alias i32 (i32)* @"shadow:standard@int.leadingZeroes()"
@"shadow:standard@uint.trailingZeroes()" = alias i32 (i32)* @"shadow:standard@int.trailingZeroes()"	
	
define {i32, i1} @"shadow:standard@uint.addWithOverflow(shadow:standard@uint)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.uadd.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @"shadow:standard@uint.subtractWithOverflow(shadow:standard@uint)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.usub.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

define {i32, i1} @"shadow:standard@uint.multiplyWithOverflow(shadow:standard@uint)"(i32, i32) #0 {
	%3 = call {i32, i1} @llvm.umul.with.overflow.i32(i32 %0, i32 %1)
	ret {i32, i1} %3
}

; shadow.standard@Code native methods

@"shadow:standard@code.toByte()" = alias i8 (i32)* @"shadow:standard@int.toByte()"
@"shadow:standard@code.toUByte()" = alias i8 (i32)* @"shadow:standard@int.toByte()"
@"shadow:standard@code.toShort()" = alias i16 (i32)* @"shadow:standard@int.toShort()"
@"shadow:standard@code.toUShort()" = alias i16 (i32)* @"shadow:standard@int.toShort()"
@"shadow:standard@code.toInt()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@code.toUInt()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@code.toCode()" = alias i32 (i32)* @"shadow:standard@int.toInt()"
@"shadow:standard@code.toLong()" = alias i64 (i32)* @"shadow:standard@int.toULong()"
@"shadow:standard@code.toULong()" = alias i64 (i32)* @"shadow:standard@int.toULong()"
@"shadow:standard@code.toFloat()" = alias float (i32)* @"shadow:standard@uint.toFloat()"
@"shadow:standard@code.toDouble()" = alias double (i32)* @"shadow:standard@uint.toDouble()"

@"shadow:standard@code.bitComplement()" = alias i32 (i32)* @"shadow:standard@int.bitComplement()"
@"shadow:standard@code.bitOr(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.bitOr(shadow:standard@int)"
@"shadow:standard@code.bitXor(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.bitXor(shadow:standard@int)"
@"shadow:standard@code.bitAnd(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.bitAnd(shadow:standard@int)"
@"shadow:standard@code.bitShiftLeft(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitShiftLeft(shadow:standard@uint)"
@"shadow:standard@code.bitShiftRight(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@uint.bitShiftRight(shadow:standard@uint)"
@"shadow:standard@code.bitRotateLeft(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitRotateLeft(shadow:standard@uint)"
@"shadow:standard@code.bitRotateRight(shadow:standard@uint)" = alias i32 (i32, i32)* @"shadow:standard@int.bitRotateRight(shadow:standard@uint)"

@"shadow:standard@code.add(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.add(shadow:standard@int)"
@"shadow:standard@code.subtract(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.subtract(shadow:standard@int)"
@"shadow:standard@code.multiply(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@int.multiply(shadow:standard@int)"
@"shadow:standard@code.divide(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@uint.divide(shadow:standard@int)"
@"shadow:standard@code.modulus(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@uint.modulus(shadow:standard@uint)"
@"shadow:standard@code.compare(shadow:standard@code)" = alias i32 (i32, i32)* @"shadow:standard@uint.compare(shadow:standard@uint)"
@"shadow:standard@code.equal(shadow:standard@code)" = alias i1 (i32, i32)* @"shadow:standard@int.equal(shadow:standard@int)"

; shadow.standard@Long native methods

define i64 @"shadow:standard@long.bitComplement()"(i64) #0 {
	%2 = xor i64 %0, -1
	ret i64 %2
}
define i64 @"shadow:standard@long.bitOr(shadow:standard@long)"(i64, i64) #0 {
	%3 = or i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.bitXor(shadow:standard@long)"(i64, i64) #0 {
	%3 = xor i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.bitAnd(shadow:standard@long)"(i64, i64) #0 {
	%3 = and i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.bitShiftLeft(shadow:standard@uint)"(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = shl i64 %0, %5
	ret i64 %6
	ret i64 0
}
define i64 @"shadow:standard@long.bitShiftRight(shadow:standard@uint)"(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = ashr i64 %0, %5
	ret i64 %6
	%8 = ashr i64 %0, 63
	ret i64 %8
}
define i64 @"shadow:standard@long.bitRotateLeft(shadow:standard@uint)"(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = shl i64 %0, %4
	%7 = lshr i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}
define i64 @"shadow:standard@long.bitRotateRight(shadow:standard@uint)"(i64, i32) #0 {
	%3 = and i32 %1, 63
	%4 = zext i32 %3 to i64
	%5 = sub i64 64, %4
	%6 = lshr i64 %0, %4
	%7 = shl i64 %0, %5
	%8 = or i64 %6, %7
	ret i64 %8
}

define i64 @"shadow:standard@long.add(shadow:standard@long)"(i64, i64) #0 {
	%3 = add i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.subtract(shadow:standard@long)"(i64, i64) #0 {
	%3 = sub i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.multiply(shadow:standard@long)"(i64, i64) #0 {
	%3 = mul i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.divide(shadow:standard@long)"(i64, i64) #0 {
	%3 = sdiv i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@long.modulus(shadow:standard@long)"(i64, i64) #0 {
	%3 = srem i64 %0, %1
	ret i64 %3
}

define i32 @"shadow:standard@long.compare(shadow:standard@long)"(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp slt i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

define i1 @"shadow:standard@long.equal(shadow:standard@long)"(i64, i64) #0 {
	%3 = icmp eq i64 %0, %1
	ret i1 %3
}

define i64 @"shadow:standard@long.negate()"(i64) #0 {	
	%2 = sub i64 0, %0
	ret i64 %2
}

define i8 @"shadow:standard@long.toByte()"(i64) #0 {	
	%2 = trunc i64 %0 to i8
	ret i8 %2
}
@"shadow:standard@long.toUByte()" = alias i8 (i64)* @"shadow:standard@long.toByte()"
define i16 @"shadow:standard@long.toShort()"(i64) #0 {
	%2 = trunc i64 %0 to i16
	ret i16 %2
}
@"shadow:standard@long.toUShort()" = alias i16 (i64)* @"shadow:standard@long.toShort()" 
define i32 @"shadow:standard@long.toInt()"(i64) #0 {
	%2 = trunc i64 %0 to i32
	ret i32 %2
}
@"shadow:standard@long.toUInt()" = alias i32 (i64)* @"shadow:standard@long.toInt()" 
@"shadow:standard@long.toCode()" = alias i32 (i64)* @"shadow:standard@long.toInt()"
define i64 @"shadow:standard@long.toLong()"(i64) #0 {
	ret i64 %0
}
@"shadow:standard@long.toULong()" = alias i64 (i64)* @"shadow:standard@long.toLong()"
define float @"shadow:standard@long.toFloat()"(i64) #0 {
	%2 = sitofp i64 %0 to float
	ret float %2
}
define double @"shadow:standard@long.toDouble()"(i64) #0 {
	%2 = sitofp i64 %0 to double
	ret double %2
}

define i64 @"shadow:standard@long.flipEndian()"(i64) #0 {
	%2 = call i64 @llvm.bswap.i64(i64 %0)
	ret i64 %2
}

define i64 @"shadow:standard@long.ones()"(i64) #0 {
	%2 = call i64 @llvm.ctpop.i64(i64 %0)
	ret i64 %2
}

define i64 @"shadow:standard@long.leadingZeroes()"(i64) #0 {
	%2 = call i64  @llvm.ctlz.i64(i64 %0, i1 false)
	ret i64 %2
}
define i64 @"shadow:standard@long.trailingZeroes()"(i64) #0 {
	%2 = call i64  @llvm.cttz.i64(i64 %0, i1 false)
	ret i64 %2
}
	
;define {i64, i1} @"shadow:standard@long.addWithOverflow(shadow:standard@long)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.sadd.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @"shadow:standard@long.subtractWithOverflow(shadow:standard@long)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.ssub.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @"shadow:standard@long.multiplyWithOverflow(shadow:standard@long)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.smul.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}


; shadow.standard@ULong native methods

@"shadow:standard@ulong.bitComplement()" = alias i64 (i64)* @"shadow:standard@long.bitComplement()"
@"shadow:standard@ulong.bitOr(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.bitOr(shadow:standard@long)"
@"shadow:standard@ulong.bitXor(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.bitXor(shadow:standard@long)"
@"shadow:standard@ulong.bitAnd(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.bitAnd(shadow:standard@long)"
@"shadow:standard@ulong.bitShiftLeft(shadow:standard@uint)" = alias i64 (i64, i32)* @"shadow:standard@long.bitShiftLeft(shadow:standard@uint)"
define i64 @"shadow:standard@ulong.bitShiftRight(shadow:standard@uint)"(i64, i32) #0 {
	%3 = icmp ult i32 %1, 64
	br i1 %3, label %4, label %7
	%5 = zext i32 %1 to i64
	%6 = lshr i64 %0, %5
	ret i64 %6
	ret i64 0
}
@"shadow:standard@ulong.bitRotateLeft(shadow:standard@uint)" = alias i64 (i64, i32)* @"shadow:standard@long.bitRotateLeft(shadow:standard@uint)"
@"shadow:standard@ulong.bitRotateRight(shadow:standard@uint)" = alias i64 (i64, i32)* @"shadow:standard@long.bitRotateRight(shadow:standard@uint)"

@"shadow:standard@ulong.add(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.add(shadow:standard@long)"
@"shadow:standard@ulong.subtract(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.subtract(shadow:standard@long)"
@"shadow:standard@ulong.multiply(shadow:standard@ulong)" = alias i64 (i64, i64)* @"shadow:standard@long.multiply(shadow:standard@long)"
define i64 @"shadow:standard@ulong.divide(shadow:standard@ulong)"(i64, i64) #0 {
	%3 = udiv i64 %0, %1
	ret i64 %3
}
define i64 @"shadow:standard@ulong.modulus(shadow:standard@ulong)"(i64, i64) #0 {
	%3 = urem i64 %0, %1
	ret i64 %3
}

define i32 @"shadow:standard@ulong.compare(shadow:standard@ulong)"(i64, i64) #0 {
	%3 = icmp ne i64 %0, %1
	%4 = zext i1 %3 to i32
	%5 = icmp ult i64 %0, %1
	%6 = select i1 %5, i32 -1, i32 %4
	ret i32 %6
}

@"shadow:standard@ulong.equal(shadow:standard@ulong)" = alias i1 (i64, i64)* @"shadow:standard@long.equal(shadow:standard@long)"

@"shadow:standard@ulong.toByte()" = alias i8 (i64)* @"shadow:standard@long.toByte()" 
@"shadow:standard@ulong.toUByte()" = alias i8 (i64)* @"shadow:standard@long.toByte()"
@"shadow:standard@ulong.toShort()" = alias i16 (i64)* @"shadow:standard@long.toShort()" 
@"shadow:standard@ulong.toUShort()" = alias i16 (i64)* @"shadow:standard@long.toShort()"
@"shadow:standard@ulong.toInt()" = alias i32 (i64)* @"shadow:standard@long.toInt()" 
@"shadow:standard@ulong.toUInt()" = alias i32 (i64)* @"shadow:standard@long.toInt()"
@"shadow:standard@ulong.toCode()" = alias i32 (i64)* @"shadow:standard@long.toInt()"
@"shadow:standard@ulong.toLong()" = alias i64 (i64)* @"shadow:standard@long.toLong()" 
@"shadow:standard@ulong.toULong()" = alias i64 (i64)* @"shadow:standard@long.toLong()"
define float @"shadow:standard@ulong.toFloat()"(i64) #0 {
	%2 = uitofp i64 %0 to float
	ret float %2
}
define double @"shadow:standard@ulong.toDouble()"(i64) #0 {
	%2 = uitofp i64 %0 to double
	ret double %2
}

@"shadow:standard@ulong.flipEndian()" = alias i64 (i64)* @"shadow:standard@long.flipEndian()"
@"shadow:standard@ulong.ones()" = alias i64 (i64)* @"shadow:standard@long.ones()"
@"shadow:standard@ulong.leadingZeroes()" = alias i64 (i64)* @"shadow:standard@long.leadingZeroes()"
@"shadow:standard@ulong.trailingZeroes()" = alias i64 (i64)* @"shadow:standard@long.trailingZeroes()"	
	
;define {i64, i1} @"shadow:standard@ulong.addWithOverflow(shadow:standard@ulong)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.uadd.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @"shadow:standard@ulong.subtractWithOverflow(shadow:standard@ulong)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.usub.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

;define {i64, i1} @"shadow:standard@ulong.multiplyWithOverflow(shadow:standard@ulong)"(i64, i64) #0 {
;	%3 = call {i64, i1} @llvm.umul.with.overflow.i64(i64 %0, i64 %1)
;	ret {i64, i1} %3
;}

; shadow.standard@Float native methods

define float @"shadow:standard@float.add(shadow:standard@float)"(float, float) #0 {
	%3 = fadd float %0, %1
	ret float %3
}
define float @"shadow:standard@float.subtract(shadow:standard@float)"(float, float) #0 {
	%3 = fsub float %0, %1
	ret float %3
}
define float @"shadow:standard@float.multiply(shadow:standard@float)"(float, float) #0 {
	%3 = fmul float %0, %1
	ret float %3
}
define float @"shadow:standard@float.divide(shadow:standard@float)"(float, float) #0 {
	%3 = fdiv float %0, %1
	ret float %3
}
define float @"shadow:standard@float.modulus(shadow:standard@float)"(float, float) #0 {
	%3 = frem float %0, %1
	ret float %3
}

define i32 @"shadow:standard@float.compare(shadow:standard@float)"(float, float) #0 {
	%3 = fcmp ueq float %0, %1
	%4 = fcmp olt float %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @"shadow:standard@float.equal(shadow:standard@float)"(float, float) #0 {
	%3 = fcmp ord float %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq float %0, %1
	ret i1 %5
	%7 = fcmp uno float %0, 0.
	%8 = fcmp uno float %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i32 @"shadow:standard@float.raw()"(float) #0 {
	%2 = bitcast float %0 to i32
	ret i32 %2
}

define float @"shadow:standard@float.negate()"(float) #0 {	
	%2 = fsub float -0.0, %0
	ret float %2
}

define i8 @"shadow:standard@float.toByte()"(float) #0 {	
	%2 = fptosi float %0 to i8
	ret i8 %2
}

define i8 @"shadow:standard@float.toUByte()"(float) #0 {	
	%2 = fptoui float %0 to i8
	ret i8 %2
}

define i16 @"shadow:standard@float.toShort()"(float) #0 {
	%2 = fptosi float %0 to i16
	ret i16 %2
}

define i16 @"shadow:standard@float.toUShort()"(float) #0 {	
	%2 = fptoui float %0 to i16
	ret i16 %2
}

define i32 @"shadow:standard@float.toInt()"(float) #0 {	
	%2 = fptosi float %0 to i32
	ret i32 %2
}

define i32 @"shadow:standard@float.toUInt()"(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i32 @"shadow:standard@float.toCode()"(float) #0 {
	%2 = fptoui float %0 to i32
	ret i32 %2
}

define i64 @"shadow:standard@float.toLong()"(float) #0 {
	%2 = fptosi float %0 to i64
	ret i64 %2
}

define i64 @"shadow:standard@float.toULong()"(float) #0 {
	%2 = fptoui float %0 to i64
	ret i64 %2
}

define float @"shadow:standard@float.toFloat()"(float) #0 {
	ret float %0
}

define double @"shadow:standard@float.toDouble()"(float) #0 {	
	%2 = fpext float %0 to double
	ret double %2
}

define float @"shadow:standard@float.squareRoot()"(float) #0 {	
	%2 = call float @llvm.sqrt.f32(float %0)
	ret float %2
}	

define float @"shadow:standard@float.power(shadow:standard@int)"(float, i32) #0 {	
	%3 = call float @llvm.powi.f32(float %0, i32 %1)
	ret float %3
}	

define float @"shadow:standard@float.power(shadow:standard@float)"(float, float) #0 {	
	%3 = call float @llvm.pow.f32(float %0, float %1)
	ret float %3
}

define float @"shadow:standard@float.sin()"(float) #0 {	
	%2 = call float @llvm.sin.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.cos()"(float) #0 {	
	%2 = call float @llvm.cos.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.logBaseE()"(float) #0 {	
	%2 = call float @llvm.log.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.logBase2()"(float) #0 {	
	%2 = call float @llvm.log2.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.logBase10()"(float) #0 {	
	%2 = call float @llvm.log10.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.multiplyAddshadow:standard@float(shadow:standard@float)"(float, float, float) #0 {	
	%4 = call float @llvm.fma.f32(float %0, float %1, float %2)
	ret float %4
}	

define float @"shadow:standard@float.floor()"(float) #0 {
	%2 = call float @llvm.floor.f32(float %0)
	ret float %2
}

define float @"shadow:standard@float.ceiling()"(float) #0 {	
	%2 = call float @llvm.ceil.f32(float %0)
	ret float %2
}

; shadow.standard@Double native methods

define double @"shadow:standard@double.add(shadow:standard@double)"(double, double) #0 {
	%3 = fadd double %0, %1
	ret double %3
}
define double @"shadow:standard@double.subtract(shadow:standard@double)"(double, double) #0 {
	%3 = fsub double %0, %1
	ret double %3
}
define double @"shadow:standard@double.multiply(shadow:standard@double)"(double, double) #0 {
	%3 = fmul double %0, %1
	ret double %3
}
define double @"shadow:standard@double.divide(shadow:standard@double)"(double, double) #0 {
	%3 = fdiv double %0, %1
	ret double %3
}
define double @"shadow:standard@double.modulus(shadow:standard@double)"(double, double) #0 {
	%3 = frem double %0, %1
	ret double %3
}

define i32 @"shadow:standard@double.compare(shadow:standard@double)"(double, double) #0 {
	%3 = fcmp ueq double %0, %1
	%4 = fcmp olt double %0, %1
	%5 = select i1 %3, i32 0, i32 1
	%6 = select i1 %4, i32 -1, i32 %5
	ret i32 %6
}

define i1 @"shadow:standard@double.equal(shadow:standard@double)"(double, double) #0 {
	%3 = fcmp ord double %0, %1
	br i1 %3, label %4, label %6
	%5 = fcmp ueq double %0, %1
	ret i1 %5
	%7 = fcmp uno double %0, 0.
	%8 = fcmp uno double %1, 0.
	%9 = xor i1 %7, %8
	ret i1 %9
}

define i64 @"shadow:standard@double.raw()"(double) #0 {
	%2 = bitcast double %0 to i64
	ret i64 %2
}

define double @"shadow:standard@double.negate()"(double) #0 {	
	%2 = fsub double -0.0, %0
	ret double %2
}

define i8 @"shadow:standard@double.toByte()"(double) #0 {	
	%2 = fptosi double %0 to i8
	ret i8 %2
}

define i8 @"shadow:standard@double.toUByte()"(double) #0 {	
	%2 = fptoui double %0 to i8
	ret i8 %2
}

define i16 @"shadow:standard@double.toShort()"(double) #0 {
	%2 = fptosi double %0 to i16
	ret i16 %2
}

define i16 @"shadow:standard@double.toUShort()"(double) #0 {	
	%2 = fptoui double %0 to i16
	ret i16 %2
}

define i32 @"shadow:standard@double.toInt()"(double) #0 {	
	%2 = fptosi double %0 to i32
	ret i32 %2
}

define i32 @"shadow:standard@double.toUInt()"(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i32 @"shadow:standard@double.toCode()"(double) #0 {
	%2 = fptoui double %0 to i32
	ret i32 %2
}

define i64 @"shadow:standard@double.toLong()"(double) #0 {
	%2 = fptosi double %0 to i64
	ret i64 %2
}

define i64 @"shadow:standard@double.toULong()"(double) #0 {
	%2 = fptoui double %0 to i64
	ret i64 %2
}

define float @"shadow:standard@double.toFloat()"(double) #0 {
	%2 = fptrunc double %0 to float
	ret float %2
}

define double @"shadow:standard@double.toDouble()"(double) #0 {	
	ret double %0
}

define double @"shadow:standard@double.squareRoot()"(double) #0 {	
	%2 = call double @llvm.sqrt.f64(double %0)
	ret double %2
}	

define double @"shadow:standard@double.power(shadow:standard@int)"(double, i32) #0 {	
	%3 = call double @llvm.powi.f64(double %0, i32 %1)
	ret double %3
}	

define double @"shadow:standard@double.power(shadow:standard@double)"(double, double) #0 {	
	%3 = call double @llvm.pow.f64(double %0, double %1)
	ret double %3
}

define double @"shadow:standard@double.sin()"(double) #0 {	
	%2 = call double @llvm.sin.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.cos()"(double) #0 {	
	%2 = call double @llvm.cos.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.logBaseE()"(double) #0 {	
	%2 = call double @llvm.log.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.logBase2()"(double) #0 {	
	%2 = call double @llvm.log2.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.logBase10()"(double) #0 {	
	%2 = call double @llvm.log10.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.multiplyAddshadow:standard@double(shadow:standard@double)"(double, double, double) #0 {	
	%4 = call double @llvm.fma.f64(double %0, double %1, double %2)
	ret double %4
}	

define double @"shadow:standard@double.floor()"(double) #0 {
	%2 = call double @llvm.floor.f64(double %0)
	ret double %2
}

define double @"shadow:standard@double.ceiling()"(double) #0 {	
	%2 = call double @llvm.ceil.f64(double %0)
	ret double %2
}