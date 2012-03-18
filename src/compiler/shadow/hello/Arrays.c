/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Arrays.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.hello@Arrays", (int_shadow_t)1, {(int_shadow_t)19}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrays_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

struct _IArray * _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(struct _IArray * array) {
	int_shadow_t _Itemp0;
	int_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	int_shadow_t _Itemp3;
	boolean_shadow_t _Itemp4;
	int_shadow_t _Itemp5;
	boolean_shadow_t _Itemp6;
	int_shadow_t _Itemp7;
	boolean_shadow_t _Itemp8;
	int_shadow_t i;
	_Itemp0 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(array);
	_Itemp1 = _Itemp0 - 1;
	i = _Itemp1;
	goto label7;
label0: (void)0;
	int_shadow_t maxIndex;
	maxIndex = i;
	struct _Pshadow_Pstandard_CString* max;
	max = ((struct _Pshadow_Pstandard_CString**)array->_Iarray)[i];
	int_shadow_t j;
	_Itemp2 = i - 1;
	j = _Itemp2;
	goto label5;
label1: (void)0;
	_Itemp3 = max->_Imethods->_McompareTo_R_Pshadow_Pstandard_CString(max, ((struct _Pshadow_Pstandard_CString**)array->_Iarray)[j]);
	_Itemp4 = _Itemp3 < 0;
	if ( _Itemp4 )
		goto label2;
	else
		goto label3;
label2: (void)0;
	maxIndex = j;
	max = ((struct _Pshadow_Pstandard_CString**)array->_Iarray)[j];
	goto label4;
label3: (void)0;
	goto label4;
label4: (void)0;
	_Itemp5 = j - 1;
	j = _Itemp5;
	goto label5;
label5: (void)0;
	_Itemp6 = j >= 0;
	if ( _Itemp6 )
		goto label1;
	else
		goto label6;
label6: (void)0;
	((struct _Pshadow_Pstandard_CString**)array->_Iarray)[maxIndex] = ((struct _Pshadow_Pstandard_CString**)array->_Iarray)[i];
	((struct _Pshadow_Pstandard_CString**)array->_Iarray)[i] = max;
	_Itemp7 = i - 1;
	i = _Itemp7;
	goto label7;
label7: (void)0;
	_Itemp8 = i > 0;
	if ( _Itemp8 )
		goto label0;
	else
		goto label8;
label8: (void)0;
	return array;
}

void _Pshadow_Phello_CArrays_Mconstructor(struct _Pshadow_Phello_CArrays* this) {
	this->_Imethods = &_Pshadow_Phello_CArrays_Imethods;
	return;
}

void _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	int_shadow_t _Itemp0;
	int_shadow_t _Itemp1;
	boolean_shadow_t _Itemp2;
	int_shadow_t _Itemp3;
	int_shadow_t _Itemp4;
	boolean_shadow_t _Itemp5;
	int_shadow_t _Itemp6;
	int_shadow_t _Itemp7;
	int_shadow_t _Itemp8;
	boolean_shadow_t _Itemp9;
	struct _IArray * _Itemp10;
	int_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	boolean_shadow_t _Itemp13;
	static struct _IArray _Iarray1 = {
		(void *)"Arrays", (int_shadow_t)1, {(int_shadow_t)6}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray1
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
	int_shadow_t i;
	i = 0;
	goto label1;
label0: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode(' ');
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)args->_Iarray)[i]);
	_Itemp0 = i + 1;
	i = _Itemp0;
	goto label1;
label1: (void)0;
	_Itemp1 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(args);
	_Itemp2 = i < _Itemp1;
	if ( _Itemp2 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	static struct _IArray _Iarray2 = {
		(void *)"Arguments:", (int_shadow_t)1, {(int_shadow_t)10}
	};
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray2
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring2);
	int_shadow_t i_;
	i_ = 0;
	goto label4;
label3: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('\t');
	_Pshadow_Pio_CConsole_Mprint_Rint(i_);
	static struct _IArray _Iarray3 = {
		(void *)":\t", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring3 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray3
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring3);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)args->_Iarray)[i_]);
	_Itemp3 = i_ + 1;
	i_ = _Itemp3;
	goto label4;
label4: (void)0;
	_Itemp4 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(args);
	_Itemp5 = i_ < _Itemp4;
	if ( _Itemp5 )
		goto label3;
	else
		goto label5;
label5: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	static struct _IArray _Iarray4 = {
		(void *)"Arguments (backwards):", (int_shadow_t)1, {(int_shadow_t)22}
	};
	static struct _Pshadow_Pstandard_CString _Istring4 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray4
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring4);
	int_shadow_t i__;
	_Itemp6 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(args);
	_Itemp7 = _Itemp6 - 1;
	i__ = _Itemp7;
	goto label7;
label6: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('\t');
	_Pshadow_Pio_CConsole_Mprint_Rint(i__);
	static struct _IArray _Iarray5 = {
		(void *)":\t", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring5 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray5
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)args->_Iarray)[i__]);
	_Itemp8 = i__ - 1;
	i__ = _Itemp8;
	goto label7;
label7: (void)0;
	_Itemp9 = i__ >= 0;
	if ( _Itemp9 )
		goto label6;
	else
		goto label8;
label8: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	_Itemp10 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(args);
	args = _Itemp10;
	static struct _IArray _Iarray6 = {
		(void *)"Arguments (sorted):", (int_shadow_t)1, {(int_shadow_t)19}
	};
	static struct _Pshadow_Pstandard_CString _Istring6 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray6
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring6);
	int_shadow_t i___;
	i___ = 0;
	goto label10;
label9: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('\t');
	_Pshadow_Pio_CConsole_Mprint_Rint(i___);
	static struct _IArray _Iarray7 = {
		(void *)":\t", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring7 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray7
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)args->_Iarray)[i___]);
	_Itemp11 = i___ + 1;
	i___ = _Itemp11;
	goto label10;
label10: (void)0;
	_Itemp12 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(args);
	_Itemp13 = i___ < _Itemp12;
	if ( _Itemp13 )
		goto label9;
	else
		goto label11;
label11: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CArrays_MgetClass(struct _Pshadow_Phello_CArrays* this) {
	return &_Pshadow_Phello_CArrays_Iclass;
}

struct _Pshadow_Phello_CArrays_Itable _Pshadow_Phello_CArrays_Imethods = {
	_Pshadow_Phello_CArrays_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1,
	_Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
	int i, argsLength = argc - 1; ++argv;
	struct _Pshadow_Pstandard_CString **args = (struct _Pshadow_Pstandard_CString **)calloc(argsLength, sizeof(struct _Pshadow_Pstandard_CString *));
	for (i = 0; i < argsLength; ++i) {
		struct _Pshadow_Pstandard_CString *arg = (struct _Pshadow_Pstandard_CString *)malloc(sizeof(struct _Pshadow_Pstandard_CString));
		arg->_Imethods = &_Pshadow_Pstandard_CString_Imethods;
		arg->ascii = ((boolean_shadow_t)1);
		arg->data = (struct _IArray *)malloc(sizeof(struct _IArray));
		arg->data->_Iarray = (void *)argv[i];
		arg->data->_Idims = (int_shadow_t)1;
		arg->data->_Ilengths[0] = strlen(argv[i]);
		args[i] = arg;
	}
	struct _IArray *argsArray = (struct _IArray *)malloc(sizeof(struct _IArray));
	argsArray->_Iarray = (void *)args;
	argsArray->_Idims = 1;
	argsArray->_Ilengths[0] = argsLength;
	_Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

