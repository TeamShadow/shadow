/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/String.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.standard@String", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CString_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

ubyte_shadow_t _Pshadow_Pstandard_CString_MgetByte_Rint(struct _Pshadow_Pstandard_CString* this, int_shadow_t index) {
	struct _IArray * _Itemp0;
	_Itemp0 = (struct _IArray *)this->data;
	return ((ubyte_shadow_t*)_Itemp0->_Iarray)[index];
}

struct _Pshadow_Pstandard_CString* _Pshadow_Pstandard_CString_MtoString(struct _Pshadow_Pstandard_CString* this) {
	return this;
}

int_shadow_t _Pshadow_Pstandard_CString_McompareTo_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString* this, struct _Pshadow_Pstandard_CString* other) {
	boolean_shadow_t _Itemp0;
	boolean_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CObject* _Itemp2;
	int_shadow_t _Itemp3;
	struct _Pshadow_Pstandard_CObject* _Itemp4;
	int_shadow_t _Itemp5;
	int_shadow_t _Itemp6;
	struct _IArray * _Itemp7;
	struct _IArray * _Itemp8;
	boolean_shadow_t _Itemp9;
	struct _IArray * _Itemp10;
	struct _IArray * _Itemp11;
	boolean_shadow_t _Itemp12;
	int_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	int_shadow_t _Itemp15;
	boolean_shadow_t _Itemp16;
	struct _Pshadow_Pstandard_CObject* _Itemp17;
	int_shadow_t _Itemp18;
	boolean_shadow_t _Itemp19;
	int_shadow_t _Itemp20;
	int_shadow_t _Itemp21;
	int_shadow_t _Itemp22;
	_Itemp0 = other == ((void *)0);
	if ( _Itemp0 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	return 1;
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Itemp1 = this->ascii && other->ascii;
	if ( _Itemp1 )
		goto label3;
	else
		goto label16;
label3: (void)0;
	int_shadow_t count;
	_Itemp2 = (struct _Pshadow_Pstandard_CObject*)this->data;
	_Itemp3 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp2);
	_Itemp4 = (struct _Pshadow_Pstandard_CObject*)other->data;
	_Itemp5 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp4);
	_Itemp6 = _Pshadow_Pstandard_CMath_Mmin_Rint_Rint(_Itemp3, _Itemp5);
	count = _Itemp6;
	int_shadow_t i;
	i = 0;
	goto label11;
label4: (void)0;
	_Itemp7 = (struct _IArray *)this->data;
	_Itemp8 = (struct _IArray *)other->data;
	_Itemp9 = ((ubyte_shadow_t*)_Itemp7->_Iarray)[i] != ((ubyte_shadow_t*)_Itemp8->_Iarray)[i];
	if ( _Itemp9 )
		goto label5;
	else
		goto label9;
label5: (void)0;
	_Itemp10 = (struct _IArray *)this->data;
	_Itemp11 = (struct _IArray *)other->data;
	_Itemp12 = ((ubyte_shadow_t*)_Itemp10->_Iarray)[i] < ((ubyte_shadow_t*)_Itemp11->_Iarray)[i];
	if ( _Itemp12 )
		goto label6;
	else
		goto label7;
label6: (void)0;
	_Itemp13 = -1;
	_Itemp14 = _Itemp13;
	goto label8;
label7: (void)0;
	_Itemp14 = 1;
	goto label8;
label8: (void)0;
	return _Itemp14;
	goto label10;
label9: (void)0;
	goto label10;
label10: (void)0;
	_Itemp15 = i + 1;
	i = _Itemp15;
	goto label11;
label11: (void)0;
	_Itemp16 = i < count;
	if ( _Itemp16 )
		goto label4;
	else
		goto label12;
label12: (void)0;
	_Itemp17 = (struct _Pshadow_Pstandard_CObject*)this->data;
	_Itemp18 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp17);
	_Itemp19 = count == _Itemp18;
	if ( _Itemp19 )
		goto label13;
	else
		goto label14;
label13: (void)0;
	_Itemp20 = -1;
	_Itemp21 = _Itemp20;
	goto label15;
label14: (void)0;
	_Itemp21 = 1;
	goto label15;
label15: (void)0;
	return _Itemp21;
	goto label17;
label16: (void)0;
	goto label17;
label17: (void)0;
	_Itemp22 = -1;
	return _Itemp22;
}

code_shadow_t _Pshadow_Pstandard_CString_MgetCode_Rint(struct _Pshadow_Pstandard_CString* this, int_shadow_t index) {
	struct _IArray * _Itemp0;
	int_shadow_t _Itemp1;
	if ( this->ascii )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Itemp0 = (struct _IArray *)this->data;
	return ((ubyte_shadow_t*)_Itemp0->_Iarray)[index];
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Itemp1 = -1;
	return _Itemp1;
}

int_shadow_t _Pshadow_Pstandard_CString_MgetLength(struct _Pshadow_Pstandard_CString* this) {
	struct _Pshadow_Pstandard_CObject* _Itemp0;
	int_shadow_t _Itemp1;
	_Itemp0 = (struct _Pshadow_Pstandard_CObject*)this->data;
	_Itemp1 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp0);
	return _Itemp1;
}

void _Pshadow_Pstandard_CString_Mconstructor(struct _Pshadow_Pstandard_CString* this) {
	this->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
	struct _IArray * _Itemp0;
	this->ascii = ((boolean_shadow_t)1);
	_Itemp0 = malloc(sizeof(struct _IArray));
	_Itemp0->_Iarray = calloc(0, sizeof(ubyte_shadow_t));
	_Itemp0->_Idims = 1;
	_Itemp0->_Ilengths[0] = 0;
	this->data = _Itemp0;
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pstandard_CString_MgetClass(struct _Pshadow_Pstandard_CString* this) {
	return &_Pshadow_Pstandard_CString_Iclass;
}

struct _Pshadow_Pstandard_CString_Itable _Pshadow_Pstandard_CString_Imethods = {
	_Pshadow_Pstandard_CString_MgetClass,
	_Pshadow_Pstandard_CString_MtoString,
	_Pshadow_Pstandard_CString_MgetByte_Rint,
	_Pshadow_Pstandard_CString_McompareTo_R_Pshadow_Pstandard_CString,
	_Pshadow_Pstandard_CString_MgetCode_Rint,
	_Pshadow_Pstandard_CString_MgetLength,
};

