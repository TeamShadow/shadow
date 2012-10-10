/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/InputTest.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.hello@InputTest", (int_shadow_t)1, {(int_shadow_t)22}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CInputTest_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CInputTest_Mconstructor(struct _Pshadow_Phello_CInputTest * this) {
	this->_Imethods = &_Pshadow_Phello_CInputTest_Imethods;
	return;
}

void _Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * args) {
	uint_shadow_t _Itemp0;
	boolean_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	int_shadow_t _Itemp3;
	int_shadow_t _Itemp4;
	uint_shadow_t _Itemp5;
	boolean_shadow_t _Itemp6;
	double_shadow_t _Itemp7;
	int_shadow_t _Itemp8;
	double_shadow_t _Itemp9;
	static struct _Pshadow_Pstandard_CArray _Iarray1 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"Enter a number: ", (int_shadow_t)1, {(int_shadow_t)16}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray1
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
	uint_shadow_t total;
	_Itemp0 = _Pshadow_Pio_CConsole_MreadUInt();
	total = _Itemp0;
	static struct _Pshadow_Pstandard_CArray _Iarray2 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"Enter ", (int_shadow_t)1, {(int_shadow_t)6}
	};
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray2
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring2);
	_Pshadow_Pio_CConsole_Mprint_Ruint(total);
	static struct _Pshadow_Pstandard_CArray _Iarray3 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)" number", (int_shadow_t)1, {(int_shadow_t)7}
	};
	static struct _Pshadow_Pstandard_CString _Istring3 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray3
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring3);
	_Itemp1 = total != ((uint_shadow_t)1);
	if ( _Itemp1 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('s');
	goto label2;
label1: (void)0;
	goto label2;
label2: (void)0;
	_Pshadow_Pio_CConsole_MprintLine_Rcode(':');
	int_shadow_t sum;
	sum = 0;
	int_shadow_t product;
	product = 1;
	uint_shadow_t i;
	i = 0u;
	goto label4;
label3: (void)0;
	int_shadow_t next;
	_Itemp2 = _Pshadow_Pio_CConsole_MreadInt();
	next = _Itemp2;
	_Itemp3 = sum + next;
	sum = _Itemp3;
	_Itemp4 = product * next;
	product = _Itemp4;
	_Itemp5 = i + 1u;
	i = _Itemp5;
	goto label4;
label4: (void)0;
	_Itemp6 = i < total;
	if ( _Itemp6 )
		goto label3;
	else
		goto label5;
label5: (void)0;
	double_shadow_t average;
	_Itemp7 = (double_shadow_t )sum;
	_Itemp8 = (int_shadow_t )total;
	_Itemp9 = _Itemp7 / _Itemp8;
	average = _Itemp9;
	static struct _Pshadow_Pstandard_CArray _Iarray4 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"The sum is: ", (int_shadow_t)1, {(int_shadow_t)12}
	};
	static struct _Pshadow_Pstandard_CString _Istring4 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray4
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);
	_Pshadow_Pio_CConsole_MprintLine_Rint(sum);
	static struct _Pshadow_Pstandard_CArray _Iarray5 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"The average is: ", (int_shadow_t)1, {(int_shadow_t)16}
	};
	static struct _Pshadow_Pstandard_CString _Istring5 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray5
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
	_Pshadow_Pio_CConsole_MprintLine_Rdouble(average);
	static struct _Pshadow_Pstandard_CArray _Iarray6 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"The product is: ", (int_shadow_t)1, {(int_shadow_t)16}
	};
	static struct _Pshadow_Pstandard_CString _Istring6 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray6
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
	_Pshadow_Pio_CConsole_MprintLine_Rint(product);
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Phello_CInputTest_MgetClass(struct _Pshadow_Phello_CInputTest * this) {
	return &_Pshadow_Phello_CInputTest_Iclass;
}

struct _Pshadow_Phello_CInputTest_Itable _Pshadow_Phello_CInputTest_Imethods = {
	_Pshadow_Phello_CInputTest_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1,
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
	_Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

