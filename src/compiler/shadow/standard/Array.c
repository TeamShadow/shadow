/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Array.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.standard@Array", (int_shadow_t)1, {(int_shadow_t)21}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CArray_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

int_shadow_t  _Pshadow_Pstandard_CArray_MgetDimensions(struct _Pshadow_Pstandard_CArray * this) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	struct _Pshadow_Pstandard_CArray *_Itemp1;
	struct _Pshadow_Pstandard_CArray *_Itemp2;
	int_shadow_t _Itemp3;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this;
	_Itemp1 = _Itemp0->_Imethods->_MgetLengths(_Itemp0);
	_Itemp2 = (struct _Pshadow_Pstandard_CArray *)_Itemp1;
	_Itemp3 = _Itemp2->_Imethods->_MgetLength(_Itemp2);
	return _Itemp3;
}

int_shadow_t  _Pshadow_Pstandard_CArray_MgetLength(struct _Pshadow_Pstandard_CArray * this) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this;
	_Itemp1 = _Itemp0->_Imethods->_MgetLength_Rint(_Itemp0, 0);
	return _Itemp1;
}

int_shadow_t  _Pshadow_Pstandard_CArray_MgetLength_Rint(struct _Pshadow_Pstandard_CArray * this, int_shadow_t  dimension) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	struct _Pshadow_Pstandard_CArray *_Itemp1;
	struct _Pshadow_Pstandard_CArray *_Itemp2;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)this;
	_Itemp1 = _Itemp0->_Imethods->_MgetLengths(_Itemp0);
	_Itemp2 = (struct _Pshadow_Pstandard_CArray *)_Itemp1;
	return ((int_shadow_t *)_Itemp2->_Iarray)[dimension];
}

void _Pshadow_Pstandard_CArray_Mconstructor(struct _Pshadow_Pstandard_CArray * this) {
	this->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CArray_MgetClass(struct _Pshadow_Pstandard_CArray * this) {
	return &_Pshadow_Pstandard_CArray_Iclass;
}

#include "shadow/standard/Array.h"

struct _Pshadow_Pstandard_CArray_Itable _Pshadow_Pstandard_CArray_Imethods = {
	_Pshadow_Pstandard_CArray_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Pstandard_CArray_MgetBaseClass,
	_Pshadow_Pstandard_CArray_MgetLengths,
	_Pshadow_Pstandard_CArray_MgetDimensions,
	_Pshadow_Pstandard_CArray_MgetLength,
	_Pshadow_Pstandard_CArray_MgetLength_Rint,
	_Pshadow_Pstandard_CArray_Msubarray_Rint_Rint_Rint,
};

