#include "stdio.h"
#include "string.h"

void _Pshadow_Pio_CConsole_Mprint_Rboolean(boolean_shadow_t z) {
	printf(z ? "true" : "false");
}

void _Pshadow_Pio_CConsole_Mprint_Rubyte(ubyte_shadow_t ub) {
	printf("%hhu", ub);
}

void _Pshadow_Pio_CConsole_Mprint_Rbyte(byte_shadow_t b) {
	printf("%hhi", b);
}

void _Pshadow_Pio_CConsole_Mprint_Rushort(ushort_shadow_t us) {
	printf("%hu", us);
}

void _Pshadow_Pio_CConsole_Mprint_Rshort(short_shadow_t s) {
	printf("%hi", s);
}

void _Pshadow_Pio_CConsole_Mprint_Ruint(uint_shadow_t ui) {
	printf("%u", ui);
}

void _Pshadow_Pio_CConsole_Mprint_Rint(int_shadow_t i) {
	printf("%i", i);
}

void _Pshadow_Pio_CConsole_Mprint_Rulong(ulong_shadow_t ul) {
	printf("%llu", ul);
}

void _Pshadow_Pio_CConsole_Mprint_Rlong(long_shadow_t l) {
	printf("%lli", l);
}

void _Pshadow_Pio_CConsole_Mprint_Rfloat(float_shadow_t f) {
	printf("%f", f);
}

void _Pshadow_Pio_CConsole_Mprint_Rdouble(double_shadow_t d) {
	printf("%g", d);
}

void _Pshadow_Pio_CConsole_Mprint_Rcode(code_shadow_t c) {
	printf("%lc", c);
}

void _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString *str) {
	printf("%s", str->data);
}

void _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject *obj) {
	printf("%s", obj->_Imethods->_MtoString(obj)->data);
}

void _Pshadow_Pio_CConsole_MprintLine_Rboolean(boolean_shadow_t z) {
	printf(z ? "true\n" : "false\n");
}

void _Pshadow_Pio_CConsole_MprintLine_Rubyte(ubyte_shadow_t ub) {
	printf("%hhu\n", ub);
}

void _Pshadow_Pio_CConsole_MprintLine_Rbyte(byte_shadow_t b) {
	printf("%hhi\n", b);
}

void _Pshadow_Pio_CConsole_MprintLine_Rushort(ushort_shadow_t us) {
	printf("%hu\n", us);
}

void _Pshadow_Pio_CConsole_MprintLine_Rshort(short_shadow_t s) {
	printf("%hi\n", s);
}

void _Pshadow_Pio_CConsole_MprintLine_Ruint(uint_shadow_t ui) {
	printf("%u\n", ui);
}

void _Pshadow_Pio_CConsole_MprintLine_Rint(int_shadow_t i) {
	printf("%i\n", i);
}

void _Pshadow_Pio_CConsole_MprintLine_Rulong(ulong_shadow_t ul) {
	printf("%llu\n", ul);
}

void _Pshadow_Pio_CConsole_MprintLine_Rlong(long_shadow_t l) {
	printf("%lli\n", l);
}

void _Pshadow_Pio_CConsole_MprintLine_Rfloat(float_shadow_t f) {
	printf("%f\n", f);
}

void _Pshadow_Pio_CConsole_MprintLine_Rdouble(double_shadow_t d) {
	printf("%g\n", d);
}

void _Pshadow_Pio_CConsole_MprintLine_Rcode(code_shadow_t c) {
	printf("%lc\n", c);
}

void _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString *str) {
	printf("%s\n", str->data);
}

void _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject *obj) {
	printf("%s\n", obj->_Imethods->_MtoString(obj)->data);
}

void _Pshadow_Pio_CConsole_MprintLine(void) {
	printf("\n");
}

boolean_shadow_t _Pshadow_Pio_CConsole_MreadBoolean(void) {
	boolean_shadow_t z;
	scanf("%hhi", &z);
	return z;
}

ubyte_shadow_t _Pshadow_Pio_CConsole_MreadUByte(void) {
	ubyte_shadow_t ub;
	scanf("%hhi", &ub);
	return ub;
}

byte_shadow_t _Pshadow_Pio_CConsole_MreadByte(void) {
	byte_shadow_t b;
	scanf("%hhi", &b);
	return b;
}

ushort_shadow_t _Pshadow_Pio_CConsole_MreadUShort(void) {
	ushort_shadow_t us;
	scanf("%hi", &us);
	return us;
}

short_shadow_t _Pshadow_Pio_CConsole_MreadShort(void) {
	short_shadow_t s;
	scanf("%hi", &s);
	return s;
}

uint_shadow_t _Pshadow_Pio_CConsole_MreadUInt(void) {
	uint_shadow_t ui;
	scanf("%i", &ui);
	return ui;
}

int_shadow_t _Pshadow_Pio_CConsole_MreadInt(void) {
	int_shadow_t i;
	scanf("%i", &i);
	return i;
}

ulong_shadow_t _Pshadow_Pio_CConsole_MreadULong(void) {
	ulong_shadow_t ul;
	scanf("%lli", &ul);
	return ul;
}

long_shadow_t _Pshadow_Pio_CConsole_MreadLong(void) {
	long_shadow_t l;
	scanf("%lli", &l);
	return l;
}

float_shadow_t _Pshadow_Pio_CConsole_MreadFloat(void) {
	float_shadow_t f;
	scanf("%f", &f);
	return f;
}

double_shadow_t _Pshadow_Pio_CConsole_MreadDouble(void) {
	double_shadow_t d;
	scanf("%lg", &d);
	return d;
}

code_shadow_t _Pshadow_Pio_CConsole_MreadCode(void) {
	code_shadow_t c;
	scanf("%lc", (wchar_t *)&c);
	return c;
}

struct _Pshadow_Pstandard_CString* _Pshadow_Pio_CConsole_Mread(void) {
	static char buffer[101];
	static int length;
	scanf("%100s%n", buffer, &length);
	struct _Pshadow_Pstandard_CString *str = malloc(sizeof(struct _Pshadow_Pstandard_CString));
	str->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
	str->ascii = (boolean_shadow_t)0;
	str->data = malloc(length + 1);
	strcpy((char *)str->data, buffer);
	return str;
}
