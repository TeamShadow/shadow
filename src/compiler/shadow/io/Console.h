#include "stdio.h"

void _Pshadow_Pio_CConsole_Mprint_Rboolean(boolean_t z) {
	printf(z ? "true" : "false");
}

void _Pshadow_Pio_CConsole_Mprint_Rubyte(ubyte_t ub) {
	printf("%hhu", ub);
}

void _Pshadow_Pio_CConsole_Mprint_Rbyte(byte_t b) {
	printf("%hhi", b);
}

void _Pshadow_Pio_CConsole_Mprint_Rushort(ushort_t us) {
	printf("%hu", us);
}

void _Pshadow_Pio_CConsole_Mprint_Rshort(short_t s) {
	printf("%hi", s);
}

void _Pshadow_Pio_CConsole_Mprint_Ruint(uint_t ui) {
	printf("%u", ui);
}

void _Pshadow_Pio_CConsole_Mprint_Rint(int_t i) {
	printf("%i", i);
}

void _Pshadow_Pio_CConsole_Mprint_Rulong(ulong_t ul) {
	printf("%llu", ul);
}

void _Pshadow_Pio_CConsole_Mprint_Rlong(long_t l) {
	printf("%lli", l);
}

void _Pshadow_Pio_CConsole_Mprint_Rfloat(float_t f) {
	printf("%f", f);
}

void _Pshadow_Pio_CConsole_Mprint_Rdouble(double_t d) {
	printf("%g", d);
}

void _Pshadow_Pio_CConsole_Mprint_Rcode(code_t c) {
	printf("%lc", c);
}

void _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString *str) {
	printf("%s", str->data);
}

void _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject *obj) {
	printf("%s", obj->_Imethods->_MtoString(obj)->data);
}

void _Pshadow_Pio_CConsole_MprintLine_Rboolean(boolean_t z) {
	printf(z ? "true\n" : "false\n");
}

void _Pshadow_Pio_CConsole_MprintLine_Rubyte(ubyte_t ub) {
	printf("%hhu\n", ub);
}

void _Pshadow_Pio_CConsole_MprintLine_Rbyte(byte_t b) {
	printf("%hhi\n", b);
}

void _Pshadow_Pio_CConsole_MprintLine_Rushort(ushort_t us) {
	printf("%hu\n", us);
}

void _Pshadow_Pio_CConsole_MprintLine_Rshort(short_t s) {
	printf("%hi\n", s);
}

void _Pshadow_Pio_CConsole_MprintLine_Ruint(uint_t ui) {
	printf("%u\n", ui);
}

void _Pshadow_Pio_CConsole_MprintLine_Rint(int_t i) {
	printf("%i\n", i);
}

void _Pshadow_Pio_CConsole_MprintLine_Rulong(ulong_t ul) {
	printf("%llu\n", ul);
}

void _Pshadow_Pio_CConsole_MprintLine_Rlong(long_t l) {
	printf("%lli\n", l);
}

void _Pshadow_Pio_CConsole_MprintLine_Rfloat(float_t f) {
	printf("%f\n", f);
}

void _Pshadow_Pio_CConsole_MprintLine_Rdouble(double_t d) {
	printf("%g\n", d);
}

void _Pshadow_Pio_CConsole_MprintLine_Rcode(code_t c) {
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
