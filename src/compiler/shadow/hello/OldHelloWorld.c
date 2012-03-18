/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/OldHelloWorld.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.hello@OldHelloWorld", (int_shadow_t)1, {(int_shadow_t)26}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_COldHelloWorld_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint(uint_shadow_t start, uint_shadow_t end) {
	uint_shadow_t _Itemp0;
	uint_shadow_t _Itemp1;
	boolean_shadow_t _Itemp2;
	uint_shadow_t i;
	i = start;
	goto label1;
label0: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Ruint(i);
	static struct _IArray _Iarray1 = {
		(void *)": ", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray1
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
	_Itemp0 = _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(i);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp0);
	_Itemp1 = i + 1u;
	i = _Itemp1;
	goto label1;
label1: (void)0;
	_Itemp2 = i <= end;
	if ( _Itemp2 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	return;
}

uint_shadow_t _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(uint_shadow_t a) {
	boolean_shadow_t _Itemp0;
	boolean_shadow_t _Itemp1;
	boolean_shadow_t _Itemp2;
	uint_shadow_t _Itemp3;
	uint_shadow_t _Itemp4;
	uint_shadow_t _Itemp5;
	_Itemp0 = a == 1u;
	_Itemp1 = a == 0u;
	_Itemp2 = _Itemp0 || _Itemp1;
	if ( _Itemp2 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	return 1u;
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Itemp3 = a - 1u;
	_Itemp4 = _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(_Itemp3);
	_Itemp5 = _Itemp4 * a;
	return _Itemp5;
}

void _Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	struct _Pshadow_Phello_COldHelloWorld* _Itemp0;
	struct _Pshadow_Phello_COldHelloWorld* _Itemp1;
	struct _Pshadow_Phello_COldHelloWorld* _Itemp2;
	static struct _IArray _Iarray2 = {
		(void *)"Hello World!", (int_shadow_t)1, {(int_shadow_t)12}
	};
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray2
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring2);
	_Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint(0u, 10u);
	_Itemp0 = malloc(sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(_Itemp0, 1, 2);
	_Itemp1 = malloc(sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint(_Itemp1, 0);
	_Itemp2 = malloc(sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(_Itemp2, 0, 1);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor(struct _Pshadow_Phello_COldHelloWorld* this) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _IArray _Iarray3 = {
		(void *)"object Hello World initialized with no arguments.", (int_shadow_t)1, {(int_shadow_t)49}
	};
	static struct _Pshadow_Pstandard_CString _Istring3 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray3
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring3);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_shadow_t a) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _IArray _Iarray4 = {
		(void *)"object Hello World initialized with one argument: ", (int_shadow_t)1, {(int_shadow_t)50}
	};
	static struct _Pshadow_Pstandard_CString _Istring4 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray4
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);
	_Pshadow_Pio_CConsole_Mprint_Rint(a);
	static struct _IArray _Iarray5 = {
		(void *)".", (int_shadow_t)1, {(int_shadow_t)1}
	};
	static struct _Pshadow_Pstandard_CString _Istring5 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray5
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring5);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_shadow_t a, int_shadow_t b) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _IArray _Iarray6 = {
		(void *)"object Hello World initialized with two arguments: ", (int_shadow_t)1, {(int_shadow_t)51}
	};
	static struct _Pshadow_Pstandard_CString _Istring6 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray6
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
	_Pshadow_Pio_CConsole_Mprint_Rint(a);
	static struct _IArray _Iarray7 = {
		(void *)" and ", (int_shadow_t)1, {(int_shadow_t)5}
	};
	static struct _Pshadow_Pstandard_CString _Istring7 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray7
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
	_Pshadow_Pio_CConsole_Mprint_Rint(b);
	static struct _IArray _Iarray8 = {
		(void *)".", (int_shadow_t)1, {(int_shadow_t)1}
	};
	static struct _Pshadow_Pstandard_CString _Istring8 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray8
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring8);
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_COldHelloWorld_MgetClass(struct _Pshadow_Phello_COldHelloWorld* this) {
	return &_Pshadow_Phello_COldHelloWorld_Iclass;
}

struct _Pshadow_Phello_COldHelloWorld_Itable _Pshadow_Phello_COldHelloWorld_Imethods = {
	_Pshadow_Phello_COldHelloWorld_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint,
	_Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint,
	_Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1,
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
	_Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

