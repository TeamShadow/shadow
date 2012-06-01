/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Class.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.standard@Class", (int_shadow_t)1, {(int_shadow_t)21}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CClass_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Pshadow_Pstandard_CObject_Iclass, &_Istring0
};

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CClass_MgetSuper(struct _Pshadow_Pstandard_CClass * this) {
	return this->superClass;
}

struct _Pshadow_Pstandard_CString * _Pshadow_Pstandard_CClass_MtoString(struct _Pshadow_Pstandard_CClass * this) {
	return this->className;
}

void _Pshadow_Pstandard_CClass_Mconstructor(struct _Pshadow_Pstandard_CClass * this) {
	this->_Imethods = &_Pshadow_Pstandard_CClass_Imethods;
	this->superClass = ((void *)0);
	static struct _Pshadow_Pstandard_CArray _Iarray1 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"", (int_shadow_t)1, {(int_shadow_t)0}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray1
	};
	this->className = &_Istring1;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CClass_MgetClass(struct _Pshadow_Pstandard_CClass * this) {
	return &_Pshadow_Pstandard_CClass_Iclass;
}

struct _Pshadow_Pstandard_CClass_Itable _Pshadow_Pstandard_CClass_Imethods = {
	_Pshadow_Pstandard_CClass_MgetClass,
	_Pshadow_Pstandard_CClass_MtoString,
	_Pshadow_Pstandard_CClass_MgetSuper,
};

