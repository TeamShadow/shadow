/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/ArrayTest.meta"

static const struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.hello@ArrayTest", (int_shadow_t)1, {(int_shadow_t)22}
};
static const struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrayTest_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CArrayTest_Mconstructor(struct _Pshadow_Phello_CArrayTest * this) {
	this->_Imethods = &_Pshadow_Phello_CArrayTest_Imethods;
	return;
}

void _Pshadow_Phello_CArrayTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * args) {
	struct _Pshadow_Pstandard_CArray * _Itemp0;
	int_shadow_t  _Itemp1;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp1 = _Itemp0->_Imethods->_MgetLength(_Itemp0);
	_Pshadow_Pio_CConsole_MprintLine_Rint(_Itemp1);
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Phello_CArrayTest_MgetClass(struct _Pshadow_Phello_CArrayTest * this) {
	return &_Pshadow_Phello_CArrayTest_Iclass;
}

struct _Pshadow_Phello_CArrayTest_Itable _Pshadow_Phello_CArrayTest_Imethods = {
	_Pshadow_Phello_CArrayTest_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_CArrayTest_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
	int i, argsLength = argc - 1; ++argv;
	struct _Pshadow_Pstandard_CString **args = calloc(argsLength, sizeof(struct _Pshadow_Pstandard_CString *));
	for (i = 0; i < argsLength; ++i) {
		struct _Pshadow_Pstandard_CString *arg = malloc(sizeof(struct _Pshadow_Pstandard_CString));
		arg->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
		arg->ascii = ((boolean_shadow_t)1);
		arg->data = malloc(sizeof(struct _IArray));
		arg->data->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
		arg->data->_Iarray = (void *)argv[i];
		arg->data->_Idims = (int_shadow_t)1;
		*arg->data->_Ilengths = strlen(argv[i]);
		args[i] = arg;
	}
	struct _IArray *argsArray = malloc(sizeof(struct _IArray));
	argsArray->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	argsArray->_Iarray = (void *)args;
	argsArray->_Idims = 1;
	*argsArray->_Ilengths = argsLength;
	_Pshadow_Phello_CArrayTest_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

