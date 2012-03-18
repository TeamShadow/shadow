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
	struct _Pshadow_Pstandard_CObject* _Itemp0;
	int_shadow_t _Itemp1;
	struct _IArray * _Itemp2;
	struct _IArray * _Itemp3;
	struct _IArray * _Itemp4;
	int_shadow_t _Itemp5;
	struct _Pshadow_Pstandard_CObject* _Itemp6;
	int_shadow_t _Itemp7;
	boolean_shadow_t _Itemp8;
	struct _Pshadow_Pstandard_CObject* _Itemp9;
	int_shadow_t _Itemp10;
	int_shadow_t _Itemp11;
	struct _IArray * _Itemp12;
	int_shadow_t _Itemp13;
	struct _IArray * _Itemp14;
	boolean_shadow_t _Itemp15;
	struct _Pshadow_Pstandard_CString* _Itemp16;
	int_shadow_t _Itemp17;
	boolean_shadow_t _Itemp18;
	int_shadow_t _Itemp19;
	boolean_shadow_t _Itemp20;
	struct _IArray * _Itemp21;
	struct _IArray * _Itemp22;
	struct _IArray * _Itemp23;
	int_shadow_t _Itemp24;
	boolean_shadow_t _Itemp25;
	struct _IArray * newArray;
	_Itemp0 = (struct _Pshadow_Pstandard_CObject*)array;
	_Itemp1 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp0);
	_Itemp2 = malloc(sizeof(struct _IArray));
	_Itemp2->_Iarray = calloc(_Itemp1, sizeof(struct _Pshadow_Pstandard_CString*));
	_Itemp2->_Idims = 1;
	_Itemp2->_Ilengths[0] = _Itemp1;
	newArray = _Itemp2;
	int_shadow_t i;
	i = 0;
	goto label1;
label0: (void)0;
	_Itemp3 = (struct _IArray *)newArray;
	_Itemp4 = (struct _IArray *)array;
	((struct _Pshadow_Pstandard_CString**)_Itemp3->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString**)_Itemp4->_Iarray)[i];
	_Itemp5 = i + 1;
	i = _Itemp5;
	goto label1;
label1: (void)0;
	_Itemp6 = (struct _Pshadow_Pstandard_CObject*)newArray;
	_Itemp7 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp6);
	_Itemp8 = i < _Itemp7;
	if ( _Itemp8 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	int_shadow_t i_;
	_Itemp9 = (struct _Pshadow_Pstandard_CObject*)newArray;
	_Itemp10 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp9);
	_Itemp11 = _Itemp10 - 1;
	i_ = _Itemp11;
	goto label13;
label3: (void)0;
	int_shadow_t maxIndex;
	maxIndex = i_;
	struct _Pshadow_Pstandard_CString* max;
	_Itemp12 = (struct _IArray *)newArray;
	max = ((struct _Pshadow_Pstandard_CString**)_Itemp12->_Iarray)[i_];
	int_shadow_t j;
	_Itemp13 = i_ - 1;
	j = _Itemp13;
	goto label11;
label4: (void)0;
	struct _Pshadow_Pstandard_CString* current;
	_Itemp14 = (struct _IArray *)newArray;
	_Itemp15 = ((struct _Pshadow_Pstandard_CString**)_Itemp14->_Iarray)[j] != ((void *)0);
	if ( _Itemp15 )
		goto label5;
	else
		goto label9;
label5: (void)0;
	current = ((struct _Pshadow_Pstandard_CString**)_Itemp14->_Iarray)[j];
	_Itemp16 = (struct _Pshadow_Pstandard_CString*)current;
	_Itemp17 = _Itemp16->_Imethods->_McompareTo_R_Pshadow_Pstandard_CString(_Itemp16, max);
	_Itemp18 = _Itemp17 > 0;
	if ( _Itemp18 )
		goto label6;
	else
		goto label7;
label6: (void)0;
	maxIndex = j;
	max = current;
	goto label8;
label7: (void)0;
	goto label8;
label8: (void)0;
	goto label10;
label9: (void)0;
	goto label10;
label10: (void)0;
	_Itemp19 = j - 1;
	j = _Itemp19;
	goto label11;
label11: (void)0;
	_Itemp20 = j >= 0;
	if ( _Itemp20 )
		goto label4;
	else
		goto label12;
label12: (void)0;
	_Itemp21 = (struct _IArray *)newArray;
	_Itemp22 = (struct _IArray *)newArray;
	((struct _Pshadow_Pstandard_CString**)_Itemp21->_Iarray)[maxIndex] = ((struct _Pshadow_Pstandard_CString**)_Itemp22->_Iarray)[i_];
	_Itemp23 = (struct _IArray *)newArray;
	((struct _Pshadow_Pstandard_CString**)_Itemp23->_Iarray)[i_] = max;
	_Itemp24 = i_ - 1;
	i_ = _Itemp24;
	goto label13;
label13: (void)0;
	_Itemp25 = i_ > 0;
	if ( _Itemp25 )
		goto label3;
	else
		goto label14;
label14: (void)0;
	return newArray;
}

void _Pshadow_Phello_CArrays_Mconstructor(struct _Pshadow_Phello_CArrays* this) {
	this->_Imethods = &_Pshadow_Phello_CArrays_Imethods;
	return;
}

void _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	struct _IArray * _Itemp0;
	int_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CObject* _Itemp2;
	int_shadow_t _Itemp3;
	boolean_shadow_t _Itemp4;
	struct _IArray * _Itemp5;
	int_shadow_t _Itemp6;
	struct _Pshadow_Pstandard_CObject* _Itemp7;
	int_shadow_t _Itemp8;
	boolean_shadow_t _Itemp9;
	struct _Pshadow_Pstandard_CObject* _Itemp10;
	int_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	struct _IArray * _Itemp13;
	int_shadow_t _Itemp14;
	boolean_shadow_t _Itemp15;
	struct _IArray * _Itemp16;
	struct _IArray * _Itemp17;
	struct _IArray * _Itemp18;
	int_shadow_t _Itemp19;
	struct _Pshadow_Pstandard_CObject* _Itemp20;
	int_shadow_t _Itemp21;
	boolean_shadow_t _Itemp22;
	struct _IArray * _Itemp23;
	int_shadow_t _Itemp24;
	struct _Pshadow_Pstandard_CObject* _Itemp25;
	int_shadow_t _Itemp26;
	boolean_shadow_t _Itemp27;
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
	_Itemp0 = (struct _IArray *)args;
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)_Itemp0->_Iarray)[i]);
	_Itemp1 = i + 1;
	i = _Itemp1;
	goto label1;
label1: (void)0;
	_Itemp2 = (struct _Pshadow_Pstandard_CObject*)args;
	_Itemp3 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp2);
	_Itemp4 = i < _Itemp3;
	if ( _Itemp4 )
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
	_Itemp5 = (struct _IArray *)args;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)_Itemp5->_Iarray)[i_]);
	_Itemp6 = i_ + 1;
	i_ = _Itemp6;
	goto label4;
label4: (void)0;
	_Itemp7 = (struct _Pshadow_Pstandard_CObject*)args;
	_Itemp8 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp7);
	_Itemp9 = i_ < _Itemp8;
	if ( _Itemp9 )
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
	_Itemp10 = (struct _Pshadow_Pstandard_CObject*)args;
	_Itemp11 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp10);
	_Itemp12 = _Itemp11 - 1;
	i__ = _Itemp12;
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
	_Itemp13 = (struct _IArray *)args;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)_Itemp13->_Iarray)[i__]);
	_Itemp14 = i__ - 1;
	i__ = _Itemp14;
	goto label7;
label7: (void)0;
	_Itemp15 = i__ >= 0;
	if ( _Itemp15 )
		goto label6;
	else
		goto label8;
label8: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	struct _IArray * sorted;
	_Itemp16 = (struct _IArray *)args;
	_Itemp17 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(_Itemp16);
	sorted = _Itemp17;
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
	_Itemp18 = (struct _IArray *)sorted;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)_Itemp18->_Iarray)[i___]);
	_Itemp19 = i___ + 1;
	i___ = _Itemp19;
	goto label10;
label10: (void)0;
	_Itemp20 = (struct _Pshadow_Pstandard_CObject*)sorted;
	_Itemp21 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp20);
	_Itemp22 = i___ < _Itemp21;
	if ( _Itemp22 )
		goto label9;
	else
		goto label11;
label11: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	static struct _IArray _Iarray8 = {
		(void *)"Arguments (original):", (int_shadow_t)1, {(int_shadow_t)21}
	};
	static struct _Pshadow_Pstandard_CString _Istring8 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray8
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring8);
	int_shadow_t i____;
	i____ = 0;
	goto label13;
label12: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('\t');
	_Pshadow_Pio_CConsole_Mprint_Rint(i____);
	static struct _IArray _Iarray9 = {
		(void *)":\t", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring9 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray9
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring9);
	_Itemp23 = (struct _IArray *)args;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString**)_Itemp23->_Iarray)[i____]);
	_Itemp24 = i____ + 1;
	i____ = _Itemp24;
	goto label13;
label13: (void)0;
	_Itemp25 = (struct _Pshadow_Pstandard_CObject*)args;
	_Itemp26 = _Pshadow_Pstandard_CArray_MgetLength_R_Pshadow_Pstandard_CObject(_Itemp25);
	_Itemp27 = i____ < _Itemp26;
	if ( _Itemp27 )
		goto label12;
	else
		goto label14;
label14: (void)0;
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
	_Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

