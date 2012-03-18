/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/ArrayTest.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.hello@ArrayTest", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrayTest_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CArrayTest_Mconstructor(struct _Pshadow_Phello_CArrayTest* this) {
	this->_Imethods = &_Pshadow_Phello_CArrayTest_Imethods;
	return;
}

void _Pshadow_Phello_CArrayTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	struct _IArray * _Itemp0;
	struct _IArray * array;
	_Itemp0 = malloc(sizeof(struct _IArray));
	_Itemp0->_Iarray = calloc(1, sizeof(int_shadow_t));
	_Itemp0->_Idims = 1;
	_Itemp0->_Ilengths[0] = 1;
	array = _Itemp0;
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CArrayTest_MgetClass(struct _Pshadow_Phello_CArrayTest* this) {
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
		arg->data->_Iarray = (void *)argv[i];
		arg->data->_Idims = (int_shadow_t)1;
		arg->data->_Ilengths[0] = strlen(argv[i]);
		args[i] = arg;
	}
	struct _IArray *argsArray = malloc(sizeof(struct _IArray));
	argsArray->_Iarray = (void *)args;
	argsArray->_Idims = 1;
	argsArray->_Ilengths[0] = argsLength;
	_Pshadow_Phello_CArrayTest_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

