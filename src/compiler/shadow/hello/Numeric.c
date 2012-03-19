/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "Numeric.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"Numeric", (int_shadow_t)1, {(int_shadow_t)7}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _P_CNumeric_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _P_CNumeric_Mconstructor(struct _P_CNumeric * this) {
	this->_Imethods = &_P_CNumeric_Imethods;
	return;
}

void _P_CNumeric_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * args) {
	byte_shadow_t  _Itemp0;
	byte_shadow_t  _Itemp1;
	byte_shadow_t  _Itemp2;
	int_shadow_t  _Itemp3;
	int_shadow_t  _Itemp4;
	boolean_shadow_t  _Itemp5;
	int_shadow_t  _Itemp6;
	int_shadow_t  _Itemp7;
	int_shadow_t  _Itemp8;
	int_shadow_t  _Itemp9;
	int_shadow_t  _Itemp10;
	int_shadow_t  _Itemp11;
	int_shadow_t  _Itemp12;
	int_shadow_t  _Itemp13;
	int_shadow_t  _Itemp14;
	int_shadow_t  _Itemp15;
	int_shadow_t  _Itemp16;
	int_shadow_t  _Itemp17;
	int_shadow_t  _Itemp18;
	int_shadow_t  _Itemp19;
	int_shadow_t  _Itemp20;
	int_shadow_t  _Itemp21;
	int_shadow_t  _Itemp22;
	int_shadow_t  _Itemp23;
	int_shadow_t  _Itemp24;
	int_shadow_t  _Itemp25;
	long_shadow_t z;
	_Itemp0 = ((byte_shadow_t)1) + ((short_shadow_t)2);
	_Itemp1 = _Itemp0 + ((int_shadow_t)3);
	_Itemp2 = _Itemp1 + ((long_shadow_t)4ll);
	z = _Itemp2;
	_Itemp3 = 4 * 5;
	_Itemp4 = _Itemp3 / ((long_shadow_t)2ll);
	_Itemp5 = z == _Itemp4;
	if ( _Itemp5 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	static struct _Pshadow_Pstandard_CArray _Iarray1 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"Yes", (int_shadow_t)1, {(int_shadow_t)3}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray1
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring1);
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	int_shadow_t a;
	_Itemp6 = 1 + 2;
	_Itemp7 = _Itemp6 - 3;
	_Itemp8 = _Itemp7 + 4;
	a = _Itemp8;
	int_shadow_t b;
	_Itemp9 = 1 + 2;
	b = _Itemp9;
	int_shadow_t c;
	_Itemp10 = 2 + 3;
	_Itemp11 = 4 + 5;
	_Itemp12 = _Itemp10 * _Itemp11;
	c = _Itemp12;
	_Itemp13 = a + b;
	a = _Itemp13;
	_Itemp14 = 3 + 4;
	_Itemp15 = b + _Itemp14;
	b = _Itemp15;
	_Itemp16 = a << 1;
	_Itemp17 = _Itemp16 << b;
	c = _Itemp17;
	_Itemp18 = a * b;
	_Itemp19 = c << _Itemp18;
	c = _Itemp19;
	_Itemp20 = a | b;
	a = _Itemp20;
	_Itemp21 = a & b;
	a = _Itemp21;
	_Itemp22 = a ^ b;
	a = _Itemp22;
	_Itemp23 = b | a;
	b = _Itemp23;
	_Itemp24 = b & a;
	b = _Itemp24;
	_Itemp25 = b ^ a;
	b = _Itemp25;
	return;
}

struct _Pshadow_Pstandard_CClass * _P_CNumeric_MgetClass(struct _P_CNumeric * this) {
	return &_P_CNumeric_Iclass;
}

struct _P_CNumeric_Itable _P_CNumeric_Imethods = {
	_P_CNumeric_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_P_CNumeric_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
	int i, argsLength = argc - 1; ++argv;
	struct _Pshadow_Pstandard_CString **args = calloc(argsLength, sizeof(struct _Pshadow_Pstandard_CString *));
	for (i = 0; i < argsLength; ++i) {
		struct _Pshadow_Pstandard_CString *arg = malloc(sizeof(struct _Pshadow_Pstandard_CString));
		arg->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
		arg->ascii = ((boolean_shadow_t)1);
		arg->data = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
		arg->data->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
		arg->data->_Iarray = (void *)argv[i];
		arg->data->_Idims = (int_shadow_t)1;
		*arg->data->_Ilengths = strlen(argv[i]);
		args[i] = arg;
	}
	struct _Pshadow_Pstandard_CArray *argsArray = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	argsArray->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	argsArray->_Iarray = (void *)args;
	argsArray->_Idims = 1;
	*argsArray->_Ilengths = argsLength;
	_P_CNumeric_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

