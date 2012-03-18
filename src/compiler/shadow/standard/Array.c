/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Array.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.standard@Array", (int_shadow_t)1, {(int_shadow_t)21}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CArray_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

int_shadow_t _Pshadow_Pstandard_CArray_MgetDimensions_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject* array) {
	struct _IArray * _Itemp0;
	struct _Pshadow_Pstandard_CObject* _Itemp1;
	int_shadow_t _Itemp2;
	_Itemp0 = _Pshadow_Pstandard_CArray_MgetLengths_R_Pshadow_Pstandard_CObject(array);
	_Itemp1 = (struct _Pshadow_Pstandard_CObject*)_Itemp0;
	_Itemp2 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp1);
	return _Itemp2;
}

int_shadow_t _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject* array) {
	int_shadow_t _Itemp0;
	_Itemp0 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject_Rint(array, 0);
	return _Itemp0;
}

int_shadow_t _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject_Rint(struct _Pshadow_Pstandard_CObject* array, int_shadow_t dimension) {
	struct _IArray * _Itemp0;
	_Itemp0 = _Pshadow_Pstandard_CArray_MgetLengths_R_Pshadow_Pstandard_CObject(array);
	return ((int_shadow_t*)_Itemp0->_Iarray)[dimension];
}

void _Pshadow_Pstandard_CArray_Mconstructor(struct _Pshadow_Pstandard_CArray* this) {
	this->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pstandard_CArray_MgetClass(struct _Pshadow_Pstandard_CArray* this) {
	return &_Pshadow_Pstandard_CArray_Iclass;
}

#include "shadow/standard/Array.h"

struct _Pshadow_Pstandard_CArray_Itable _Pshadow_Pstandard_CArray_Imethods = {
	_Pshadow_Pstandard_CArray_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Pstandard_CArray_MgetLengths_R_Pshadow_Pstandard_CObject,
	_Pshadow_Pstandard_CArray_MgetDimensions_R_Pshadow_Pstandard_CObject,
	_Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject,
	_Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject_Rint,
};

