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
	return ((ubyte_shadow_t*)this->data->_Iarray)[index];
}

struct _Pshadow_Pstandard_CString* _Pshadow_Pstandard_CString_MtoString(struct _Pshadow_Pstandard_CString* this) {
	return this;
}

int_shadow_t _Pshadow_Pstandard_CString_McompareTo_R_Pshadow_Pstandard_CString(struct _Pshadow_Pstandard_CString* this, struct _Pshadow_Pstandard_CString* other) {
	boolean_shadow_t _Itemp0;
	int_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	int_shadow_t _Itemp3;
	boolean_shadow_t _Itemp4;
	boolean_shadow_t _Itemp5;
	int_shadow_t _Itemp6;
	int_shadow_t _Itemp7;
	int_shadow_t _Itemp8;
	boolean_shadow_t _Itemp9;
	int_shadow_t _Itemp10;
	boolean_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	int_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	_Itemp0 = this->ascii && other->ascii;
	if ( _Itemp0 )
		goto label0;
	else
		goto label13;
label0: (void)0;
	int_shadow_t count;
	_Itemp1 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(this->data);
	_Itemp2 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(other->data);
	_Itemp3 = _Pshadow_Pstandard_CMath_Mmin_Rint_Rint(_Itemp1, _Itemp2);
	count = _Itemp3;
	int_shadow_t i;
	i = 0;
	goto label8;
label1: (void)0;
	_Itemp4 = ((ubyte_shadow_t*)this->data->_Iarray)[i] != ((ubyte_shadow_t*)other->data->_Iarray)[i];
	if ( _Itemp4 )
		goto label2;
	else
		goto label6;
label2: (void)0;
	_Itemp5 = ((ubyte_shadow_t*)this->data->_Iarray)[i] < ((ubyte_shadow_t*)other->data->_Iarray)[i];
	if ( _Itemp5 )
		goto label3;
	else
		goto label4;
label3: (void)0;
	_Itemp6 = -1;
	_Itemp7 = _Itemp6;
	goto label5;
label4: (void)0;
	_Itemp7 = 1;
	goto label5;
label5: (void)0;
	return _Itemp7;
	goto label7;
label6: (void)0;
	goto label7;
label7: (void)0;
	_Itemp8 = i + 1;
	i = _Itemp8;
	goto label8;
label8: (void)0;
	_Itemp9 = i < count;
	if ( _Itemp9 )
		goto label1;
	else
		goto label9;
label9: (void)0;
	_Itemp10 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(this->data);
	_Itemp11 = count == _Itemp10;
	if ( _Itemp11 )
		goto label10;
	else
		goto label11;
label10: (void)0;
	_Itemp12 = -1;
	_Itemp13 = _Itemp12;
	goto label12;
label11: (void)0;
	_Itemp13 = 1;
	goto label12;
label12: (void)0;
	return _Itemp13;
	goto label14;
label13: (void)0;
	goto label14;
label14: (void)0;
	_Itemp14 = -1;
	return _Itemp14;
}

code_shadow_t _Pshadow_Pstandard_CString_MgetCode_Rint(struct _Pshadow_Pstandard_CString* this, int_shadow_t index) {
	int_shadow_t _Itemp0;
	if ( this->ascii )
		goto label0;
	else
		goto label1;
label0: (void)0;
	return ((ubyte_shadow_t*)this->data->_Iarray)[index];
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Itemp0 = -1;
	return _Itemp0;
}

int_shadow_t _Pshadow_Pstandard_CString_MgetLength(struct _Pshadow_Pstandard_CString* this) {
	int_shadow_t _Itemp0;
	_Itemp0 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(this->data);
	return _Itemp0;
}

void _Pshadow_Pstandard_CString_Mconstructor(struct _Pshadow_Pstandard_CString* this) {
	this->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
	this->ascii = 0;
	this->data = ((void *)0);
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

