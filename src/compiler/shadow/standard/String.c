/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/String.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.standard@String", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CString_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

ubyte_shadow_t  _Pshadow_Pstandard_CString_MgetByte_Rint(struct _Pshadow_Pstandard_CString * this, int_shadow_t  index) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this->data;
	return ((ubyte_shadow_t *)_Itemp0->_Iarray)[index];
}

struct _Pshadow_Pstandard_CString * _Pshadow_Pstandard_CString_Msubstring_Rint_Rint(struct _Pshadow_Pstandard_CString * this, int_shadow_t  start, int_shadow_t  end) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CObject *_Itemp2;
	struct _Pshadow_Pstandard_CArray *_Itemp3;
	boolean_shadow_t _Itemp4;
	struct _Pshadow_Pstandard_CString *_Itemp5;
	boolean_shadow_t newAscii;
	newAscii = this->ascii;
	struct _Pshadow_Pstandard_CArray *newData;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp1 = end - start;
	_Itemp2 = _Itemp0->_Imethods->_Msubarray_Rint_Rint_Rint(_Itemp0, start, _Itemp1, 1);
	_Itemp3 = (struct _Pshadow_Pstandard_CArray *)_Itemp2;
	newData = _Itemp3;
	_Itemp4 = !this->ascii;
	if ( _Itemp4 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Itemp5 = malloc(sizeof(struct _Pshadow_Pstandard_CString));
	_Pshadow_Pstandard_CString_Mconstructor_Rboolean_Rubyte_A1(_Itemp5, newAscii, newData);
	return _Itemp5;
}

struct _Pshadow_Pstandard_CString * _Pshadow_Pstandard_CString_MtoString(struct _Pshadow_Pstandard_CString * this) {
	return this;
}

int_shadow_t  _Pshadow_Pstandard_CString_McompareTo_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString * this, struct _Pshadow_Pstandard_CString * otherString) {
	boolean_shadow_t _Itemp0;
	boolean_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CArray *_Itemp2;
	int_shadow_t _Itemp3;
	struct _Pshadow_Pstandard_CArray *_Itemp4;
	int_shadow_t _Itemp5;
	int_shadow_t _Itemp6;
	struct _Pshadow_Pstandard_CArray *_Itemp7;
	struct _Pshadow_Pstandard_CArray *_Itemp8;
	boolean_shadow_t _Itemp9;
	struct _Pshadow_Pstandard_CArray *_Itemp10;
	struct _Pshadow_Pstandard_CArray *_Itemp11;
	boolean_shadow_t _Itemp12;
	int_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	int_shadow_t _Itemp15;
	boolean_shadow_t _Itemp16;
	struct _Pshadow_Pstandard_CArray *_Itemp17;
	int_shadow_t _Itemp18;
	boolean_shadow_t _Itemp19;
	struct _Pshadow_Pstandard_CArray *_Itemp20;
	int_shadow_t _Itemp21;
	boolean_shadow_t _Itemp22;
	int_shadow_t _Itemp23;
	int_shadow_t _Itemp24;
	int_shadow_t _Itemp25;
	int_shadow_t _Itemp26;
	struct _Pshadow_Pstandard_CString *other;
	_Itemp0 = otherString != ((void *)0);
	if ( _Itemp0 )
		goto label0;
	else
		goto label22;
label0: (void)0;
	other = otherString;
	goto label1;
label1: (void)0;
	_Itemp1 = this->ascii;
	if ( this->ascii )
		goto label2;
	else
		goto label3;
label2: (void)0;
	_Itemp1 = other->ascii;
	goto label3;
label3: (void)0;
	if ( _Itemp1 )
		goto label4;
	else
		goto label20;
label4: (void)0;
	int_shadow_t count;
	_Itemp2 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp3 = _Itemp2->_Imethods->_MgetLength(_Itemp2);
	_Itemp4 = (struct _Pshadow_Pstandard_CArray *)other->data;
	_Itemp5 = _Itemp4->_Imethods->_MgetLength(_Itemp4);
	_Itemp6 = _Pshadow_Pstandard_CMath_Mmin_Rint_Rint(_Itemp3, _Itemp5);
	count = _Itemp6;
	int_shadow_t i;
	i = 0;
	goto label12;
label5: (void)0;
	_Itemp7 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp8 = (struct _Pshadow_Pstandard_CArray *)other->data;
	_Itemp9 = ((ubyte_shadow_t *)_Itemp7->_Iarray)[i] != ((ubyte_shadow_t *)_Itemp8->_Iarray)[i];
	if ( _Itemp9 )
		goto label6;
	else
		goto label10;
label6: (void)0;
	_Itemp10 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp11 = (struct _Pshadow_Pstandard_CArray *)other->data;
	_Itemp12 = ((ubyte_shadow_t *)_Itemp10->_Iarray)[i] < ((ubyte_shadow_t *)_Itemp11->_Iarray)[i];
	if ( _Itemp12 )
		goto label7;
	else
		goto label8;
label7: (void)0;
	_Itemp13 = -1;
	_Itemp14 = _Itemp13;
	goto label9;
label8: (void)0;
	_Itemp14 = 1;
	goto label9;
label9: (void)0;
	return _Itemp14;
	goto label11;
label10: (void)0;
	goto label11;
label11: (void)0;
	_Itemp15 = i + 1;
	i = _Itemp15;
	goto label12;
label12: (void)0;
	_Itemp16 = i < count;
	if ( _Itemp16 )
		goto label5;
	else
		goto label13;
label13: (void)0;
	_Itemp17 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp18 = _Itemp17->_Imethods->_MgetLength(_Itemp17);
	_Itemp19 = count == _Itemp18;
	if ( _Itemp19 )
		goto label14;
	else
		goto label18;
label14: (void)0;
	_Itemp20 = (struct _Pshadow_Pstandard_CArray *)other->data;
	_Itemp21 = _Itemp20->_Imethods->_MgetLength(_Itemp20);
	_Itemp22 = count == _Itemp21;
	if ( _Itemp22 )
		goto label15;
	else
		goto label16;
label15: (void)0;
	_Itemp24 = 0;
	goto label17;
label16: (void)0;
	_Itemp23 = -1;
	_Itemp24 = _Itemp23;
	goto label17;
label17: (void)0;
	_Itemp25 = _Itemp24;
	goto label19;
label18: (void)0;
	_Itemp25 = 1;
	goto label19;
label19: (void)0;
	return _Itemp25;
	goto label21;
label20: (void)0;
	goto label21;
label21: (void)0;
	goto label23;
label22: (void)0;
	return 1;
	goto label23;
label23: (void)0;
	_Itemp26 = -1;
	return _Itemp26;
}

code_shadow_t  _Pshadow_Pstandard_CString_MgetCode_Rint(struct _Pshadow_Pstandard_CString * this, int_shadow_t  index) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	code_shadow_t _Itemp1;
	if ( this->ascii )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp1 = (code_shadow_t )((ubyte_shadow_t *)_Itemp0->_Iarray)[index];
	return _Itemp1;
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	return '\0';
}

int_shadow_t  _Pshadow_Pstandard_CString_MgetLength(struct _Pshadow_Pstandard_CString * this) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this->data;
	_Itemp1 = _Itemp0->_Imethods->_MgetLength(_Itemp0);
	return _Itemp1;
}

void _Pshadow_Pstandard_CString_Mconstructor_Rboolean_Rubyte_A1(struct _Pshadow_Pstandard_CString * this, boolean_shadow_t  isAscii, struct _Pshadow_Pstandard_CArray * dataArray) {
	this->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	this->ascii = ((boolean_shadow_t)1);
	_Itemp0 = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	_Itemp0->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	_Itemp0->_Iarray = calloc(0, sizeof(ubyte_shadow_t));
	_Itemp0->_Idims = 1;
	_Itemp0->_Ilengths[0] = 0;
	this->data = _Itemp0;
	this->data = dataArray;
	this->ascii = isAscii;
	return;
}

void _Pshadow_Pstandard_CString_Mconstructor_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString * this, struct _Pshadow_Pstandard_CString * other) {
	this->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	this->ascii = ((boolean_shadow_t)1);
	_Itemp0 = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	_Itemp0->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	_Itemp0->_Iarray = calloc(0, sizeof(ubyte_shadow_t));
	_Itemp0->_Idims = 1;
	_Itemp0->_Ilengths[0] = 0;
	this->data = _Itemp0;
	this->data = other->data;
	this->ascii = other->ascii;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CString_MgetClass(struct _Pshadow_Pstandard_CString * this) {
	return &_Pshadow_Pstandard_CString_Iclass;
}

struct _Pshadow_Pstandard_CString_Itable _Pshadow_Pstandard_CString_Imethods = {
	_Pshadow_Pstandard_CString_MgetClass,
	_Pshadow_Pstandard_CString_MtoString,
	_Pshadow_Pstandard_CString_MgetByte_Rint,
	_Pshadow_Pstandard_CString_Msubstring_Rint_Rint,
	_Pshadow_Pstandard_CString_McompareTo_R_Pshadow_Pstandard_CString,
	_Pshadow_Pstandard_CString_MgetCode_Rint,
	_Pshadow_Pstandard_CString_MgetLength,
};

