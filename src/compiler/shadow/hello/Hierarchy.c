/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Hierarchy.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.hello@Hierarchy", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CHierarchy_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Pshadow_Pstandard_CObject_Iclass, &_Istring0
};

void _Pshadow_Phello_CHierarchy_Mconstructor(struct _Pshadow_Phello_CHierarchy * this) {
	this->_Imethods = &_Pshadow_Phello_CHierarchy_Imethods;
	return;
}

void _Pshadow_Phello_CHierarchy_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * args) {
	struct _Pshadow_Phello_CHierarchy *_Itemp0;
	struct _Pshadow_Pstandard_CObject *_Itemp1;
	struct _Pshadow_Pstandard_CObject *_Itemp2;
	struct _Pshadow_Pstandard_CClass *_Itemp3;
	struct _Pshadow_Pstandard_CObject *_Itemp4;
	struct _Pshadow_Pstandard_CObject *_Itemp5;
	struct _Pshadow_Pstandard_CClass *_Itemp6;
	struct _Pshadow_Pstandard_CClass *_Itemp7;
	struct _Pshadow_Pstandard_CObject *_Itemp8;
	struct _Pshadow_Pstandard_CObject *_Itemp9;
	struct _Pshadow_Pstandard_CClass *_Itemp10;
	struct _Pshadow_Pstandard_CClass *_Itemp11;
	boolean_shadow_t _Itemp12;
	struct _Pshadow_Pstandard_CClass *_Itemp13;
	struct _Pshadow_Pstandard_CObject *_Itemp14;
	struct _Pshadow_Phello_CHierarchy *hierarchy;
	_Itemp0 = malloc(sizeof(struct _Pshadow_Phello_CHierarchy));
	_Pshadow_Phello_CHierarchy_Mconstructor(_Itemp0);
	hierarchy = _Itemp0;
	_Itemp1 = (struct _Pshadow_Pstandard_CObject *)hierarchy;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(_Itemp1);
	_Itemp2 = (struct _Pshadow_Pstandard_CObject *)hierarchy;
	_Itemp3 = _Itemp2->_Imethods->_MgetClass(_Itemp2);
	_Itemp4 = (struct _Pshadow_Pstandard_CObject *)_Itemp3;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(_Itemp4);
	_Itemp5 = (struct _Pshadow_Pstandard_CObject *)hierarchy;
	_Itemp6 = _Itemp5->_Imethods->_MgetClass(_Itemp5);
	_Itemp7 = _Itemp6->_Imethods->_MgetSuper(_Itemp6);
	_Itemp8 = (struct _Pshadow_Pstandard_CObject *)_Itemp7;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(_Itemp8);
	_Itemp9 = (struct _Pshadow_Pstandard_CObject *)hierarchy;
	_Itemp10 = _Itemp9->_Imethods->_MgetClass(_Itemp9);
	_Itemp11 = _Itemp10->_Imethods->_MgetSuper(_Itemp10);
	_Itemp12 = _Itemp11 != ((void *)0);
	if ( _Itemp12 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Itemp13 = _Itemp11->_Imethods->_MgetSuper(_Itemp11);
	_Itemp14 = (struct _Pshadow_Pstandard_CObject *)_Itemp13;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(_Itemp14);
	goto label2;
label1: (void)0;
	static struct _Pshadow_Pstandard_CArray _Iarray1 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"Null Pointer Exception!", (int_shadow_t)1, {(int_shadow_t)23}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray1
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring1);
	goto label2;
label2: (void)0;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Phello_CHierarchy_MgetClass(struct _Pshadow_Phello_CHierarchy * this) {
	return &_Pshadow_Phello_CHierarchy_Iclass;
}

struct _Pshadow_Phello_CHierarchy_Itable _Pshadow_Phello_CHierarchy_Imethods = {
	_Pshadow_Phello_CHierarchy_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_CHierarchy_Mmain_R_Pshadow_Pstandard_CString_A1,
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
	_Pshadow_Phello_CHierarchy_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

