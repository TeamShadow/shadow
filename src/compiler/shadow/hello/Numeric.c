/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "Numeric.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods,
	(boolean_shadow_t)1, (ubyte_shadow_t *)"Numeric"
};
struct _Pshadow_Pstandard_CClass _P_CNumeric_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _P_CNumeric_Mtest(struct _P_CNumeric* this) {
	int_shadow_t _Itemp0;
	int_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	int_shadow_t _Itemp3;
	int_shadow_t _Itemp4;
	int_shadow_t _Itemp5;
	int_shadow_t _Itemp6;
	int_shadow_t _Itemp7;
	int_shadow_t _Itemp8;
	int_shadow_t _Itemp9;
	int_shadow_t _Itemp10;
	int_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	int_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	int_shadow_t _Itemp15;
	int_shadow_t _Itemp16;
	int_shadow_t _Itemp17;
	int_shadow_t _Itemp18;
	int_shadow_t _Itemp19;
	int_shadow_t a;
	_Itemp0 = 1 + 2;
	_Itemp1 = _Itemp0 - 3;
	_Itemp2 = _Itemp1 + 4;
	a = _Itemp2;
	int_shadow_t b;
	_Itemp3 = 1 + 2;
	b = _Itemp3;
	int_shadow_t c;
	_Itemp4 = 2 + 3;
	_Itemp5 = 4 + 5;
	_Itemp6 = _Itemp4 * _Itemp5;
	c = _Itemp6;
	_Itemp7 = a + b;
	a = _Itemp7;
	_Itemp8 = 3 + 4;
	_Itemp9 = b + _Itemp8;
	b = _Itemp9;
	_Itemp10 = a << 1;
	_Itemp11 = _Itemp10 << b;
	c = _Itemp11;
	_Itemp12 = a * b;
	_Itemp13 = c << _Itemp12;
	c = _Itemp13;
	_Itemp14 = a | b;
	a = _Itemp14;
	_Itemp15 = a & b;
	a = _Itemp15;
	_Itemp16 = a ^ b;
	a = _Itemp16;
	_Itemp17 = b | a;
	b = _Itemp17;
	_Itemp18 = b & a;
	b = _Itemp18;
	_Itemp19 = b ^ a;
	b = _Itemp19;
	return;
}

void _P_CNumeric_Mconstructor(struct _P_CNumeric* this) {
	this->_Imethods = &_P_CNumeric_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass* _P_CNumeric_MgetClass(struct _P_CNumeric* this) {
	return &_P_CNumeric_Iclass;
}

struct _P_CNumeric_Itable _P_CNumeric_Imethods = {
	_P_CNumeric_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_P_CNumeric_Mtest,
};

