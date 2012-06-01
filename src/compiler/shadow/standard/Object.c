/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Object.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.standard@Object", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CObject_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, ((void *)0), &_Istring0
};

struct _Pshadow_Pstandard_CString * _Pshadow_Pstandard_CObject_MtoString(struct _Pshadow_Pstandard_CObject * this) {
	struct _Pshadow_Pstandard_CObject *_Itemp0;
	struct _Pshadow_Pstandard_CClass *_Itemp1;
	struct _Pshadow_Pstandard_CClass *_Itemp2;
	struct _Pshadow_Pstandard_CString *_Itemp3;
	_Itemp0 = (struct _Pshadow_Pstandard_CObject *)this;
	_Itemp1 = _Itemp0->_Imethods->_MgetClass(_Itemp0);
	_Itemp2 = (struct _Pshadow_Pstandard_CClass *)_Itemp1;
	_Itemp3 = _Itemp2->_Imethods->_MtoString(_Itemp2);
	return _Itemp3;
}

void _Pshadow_Pstandard_CObject_Mconstructor(struct _Pshadow_Pstandard_CObject * this) {
	this->_Imethods = &_Pshadow_Pstandard_CObject_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CObject_MgetClass(struct _Pshadow_Pstandard_CObject * this) {
	return &_Pshadow_Pstandard_CObject_Iclass;
}

struct _Pshadow_Pstandard_CObject_Itable _Pshadow_Pstandard_CObject_Imethods = {
	_Pshadow_Pstandard_CObject_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
};

