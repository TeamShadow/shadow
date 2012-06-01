/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Arrays.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.hello@Arrays", (int_shadow_t)1, {(int_shadow_t)19}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrays_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Pshadow_Pstandard_CObject_Iclass, &_Istring0
};

struct _Pshadow_Pstandard_CArray *_Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray *array) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CArray *_Itemp2;
	int_shadow_t _Itemp3;
	struct _Pshadow_Pstandard_CArray *_Itemp4;
	int_shadow_t _Itemp5;
	boolean_shadow_t _Itemp6;
	struct _Pshadow_Pstandard_CArray *_Itemp7;
	int_shadow_t _Itemp8;
	int_shadow_t _Itemp9;
	int_shadow_t _Itemp10;
	boolean_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	boolean_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	boolean_shadow_t _Itemp15;
	int_shadow_t _Itemp16;
	boolean_shadow_t _Itemp17;
	struct _Pshadow_Pstandard_CArray *newArray;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp1 = _Itemp0->_Imethods->_MgetLength(_Itemp0);
	_Itemp2 = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	_Itemp2->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	_Itemp2->_Iarray = calloc(_Itemp1, sizeof(struct _Pshadow_Pstandard_CString *));
	_Itemp2->_Idims = 1;
	_Itemp2->_Ilengths[0] = _Itemp1;
	newArray = _Itemp2;
	int_shadow_t i;
	i = 0;
	goto label1;
label0: (void)0;
	((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)array->_Iarray)[i];
	_Itemp3 = i + 1;
	i = _Itemp3;
	goto label1;
label1: (void)0;
	_Itemp4 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp5 = _Itemp4->_Imethods->_MgetLength(_Itemp4);
	_Itemp6 = i < _Itemp5;
	if ( _Itemp6 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	int_shadow_t i_;
	_Itemp7 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp8 = _Itemp7->_Imethods->_MgetLength(_Itemp7);
	_Itemp9 = _Itemp8 - 1;
	i_ = _Itemp9;
	goto label13;
label3: (void)0;
	int_shadow_t maxIndex;
	maxIndex = i_;
	struct _Pshadow_Pstandard_CString *max;
	max = ((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[i_];
	int_shadow_t j;
	_Itemp10 = i_ - 1;
	j = _Itemp10;
	goto label11;
label4: (void)0;
	struct _Pshadow_Pstandard_CString *current;
	_Itemp11 = ((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[j] != ((void *)0);
	if ( _Itemp11 )
		goto label5;
	else
		goto label9;
label5: (void)0;
	current = ((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[j];
	_Itemp12 = current->_Imethods->_McompareTo_R_Pshadow_Pstandard_CString(current, max);
	_Itemp13 = _Itemp12 > 0;
	if ( _Itemp13 )
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
	_Itemp14 = j - 1;
	j = _Itemp14;
	goto label11;
label11: (void)0;
	_Itemp15 = j >= 0;
	if ( _Itemp15 )
		goto label4;
	else
		goto label12;
label12: (void)0;
	((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[maxIndex] = ((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[i_];
	((struct _Pshadow_Pstandard_CString **)newArray->_Iarray)[i_] = max;
	_Itemp16 = i_ - 1;
	i_ = _Itemp16;
	goto label13;
label13: (void)0;
	_Itemp17 = i_ > 0;
	if ( _Itemp17 )
		goto label3;
	else
		goto label14;
label14: (void)0;
	return newArray;
}

void_Pshadow_Phello_CArrays_Mconstructor(struct _Pshadow_Phello_CArrays *this) {
	this->_Imethods = &_Pshadow_Phello_CArrays_Imethods;
	return;
}

void_Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray *args) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	struct _Pshadow_Pstandard_CArray *_Itemp3;
	int_shadow_t _Itemp4;
	struct _Pshadow_Pstandard_CArray *_Itemp5;
	int_shadow_t _Itemp6;
	boolean_shadow_t _Itemp7;
	boolean_shadow_t _Itemp8;
	struct _Pshadow_Pstandard_CArray *_Itemp9;
	int_shadow_t _Itemp10;
	int_shadow_t _Itemp11;
	int_shadow_t _Itemp12;
	boolean_shadow_t _Itemp13;
	boolean_shadow_t _Itemp14;
	boolean_shadow_t _Itemp15;
	boolean_shadow_t _Itemp16;
	boolean_shadow_t _Itemp17;
	boolean_shadow_t _Itemp18;
	int_shadow_t _Itemp19;
	int_shadow_t _Itemp20;
	struct _Pshadow_Pstandard_CString *_Itemp21;
	int_shadow_t _Itemp22;
	struct _Pshadow_Pstandard_CArray *_Itemp23;
	int_shadow_t _Itemp24;
	boolean_shadow_t _Itemp25;
	struct _Pshadow_Pstandard_CArray *_Itemp26;
	boolean_shadow_t _Itemp27;
	struct _Pshadow_Pstandard_CArray *_Itemp28;
	boolean_shadow_t _Itemp29;
	int_shadow_t _Itemp30;
	boolean_shadow_t _Itemp31;
	struct _Pshadow_Pstandard_CArray *_Itemp32;
	int_shadow_t _Itemp33;
	boolean_shadow_t _Itemp34;
	int_shadow_t _Itemp35;
	struct _Pshadow_Pstandard_CArray *_Itemp36;
	int_shadow_t _Itemp37;
	boolean_shadow_t _Itemp38;
	struct _Pshadow_Pstandard_CArray *array;
	_Itemp0 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp1 = _Itemp0->_Imethods->_MgetLength(_Itemp0);
	_Itemp2 = _Itemp1 + 10;
	_Itemp3 = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	_Itemp3->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	_Itemp3->_Iarray = calloc(4, sizeof(struct _Pshadow_Pstandard_CArray *));
	_Itemp3->_Idims = 1;
	_Itemp3->_Ilengths[0] = 4;
	_Itemp4 = 0;
	goto label1;
label0: (void)0;
	_Itemp5 = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	_Itemp5->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	_Itemp5->_Iarray = calloc(_Itemp2, sizeof(struct _Pshadow_Pstandard_CString *));
	_Itemp5->_Idims = 1;
	_Itemp5->_Ilengths[0] = _Itemp2;
	((struct _Pshadow_Pstandard_CArray **)_Itemp3->_Iarray)[_Itemp4] = _Itemp5;
	_Itemp6 = _Itemp4 + 1;
	_Itemp4 = _Itemp6;
	goto label1;
label1: (void)0;
	_Itemp7 = _Itemp4 != 4;
	if ( _Itemp7 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	array = _Itemp3;
	int_shadow_t i;
	i = 0;
	goto label11;
label3: (void)0;
	_Itemp8 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[1] != ((void *)0);
	if ( _Itemp8 )
		goto label4;
	else
		goto label22;
label4: (void)0;
	_Itemp9 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp10 = _Itemp9->_Imethods->_MgetLength(_Itemp9);
	_Itemp11 = i + 1;
	_Itemp12 = _Itemp10 - _Itemp11;
	_Itemp13 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[2] != ((void *)0);
	if ( _Itemp13 )
		goto label5;
	else
		goto label22;
label5: (void)0;
	_Itemp14 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3] != ((void *)0);
	if ( _Itemp14 )
		goto label6;
	else
		goto label22;
label6: (void)0;
	_Itemp15 = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i] != ((void *)0);
	if ( _Itemp15 )
		goto label7;
	else
		goto label22;
label7: (void)0;
	((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3]->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i];
	((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[2]->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3]->_Iarray)[i];
	((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[1]->_Iarray)[_Itemp12] = ((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[2]->_Iarray)[i];
	_Itemp16 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[0] != ((void *)0);
	if ( _Itemp16 )
		goto label8;
	else
		goto label22;
label8: (void)0;
	_Itemp17 = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i] != ((void *)0);
	if ( _Itemp17 )
		goto label9;
	else
		goto label22;
label9: (void)0;
	_Itemp18 = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i] != ((void *)0);
	if ( _Itemp18 )
		goto label10;
	else
		goto label22;
label10: (void)0;
	_Itemp19 = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i]->_Imethods->_MgetLength(((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i]);
	_Itemp20 = _Itemp19 - 1;
	_Itemp21 = ((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i]->_Imethods->_Msubstring_Rint_Rint(((struct _Pshadow_Pstandard_CString **)args->_Iarray)[i], 1, _Itemp20);
	((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[0]->_Iarray)[i] = _Itemp21;
	_Itemp22 = i + 1;
	i = _Itemp22;
	goto label11;
label11: (void)0;
	_Itemp23 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp24 = _Itemp23->_Imethods->_MgetLength(_Itemp23);
	_Itemp25 = i < _Itemp24;
	if ( _Itemp25 )
		goto label3;
	else
		goto label12;
label12: (void)0;
	_Itemp26 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[2]);
	((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[2] = _Itemp26;
	_Itemp27 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[1] != ((void *)0);
	if ( _Itemp27 )
		goto label13;
	else
		goto label22;
label13: (void)0;
	((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3] = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[1];
	_Itemp28 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3]);
	((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[3] = _Itemp28;
	int_shadow_t i_;
	i_ = 0;
	goto label20;
label14: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	int_shadow_t j;
	j = 0;
	goto label17;
label15: (void)0;
	_Pshadow_Pio_CConsole_Mprint_Rcode('\t');
	_Pshadow_Pio_CConsole_Mprint_Rint(i_);
	_Pshadow_Pio_CConsole_Mprint_Rcode(',');
	_Pshadow_Pio_CConsole_Mprint_Rint(j);
	static struct _Pshadow_Pstandard_CArray _Iarray1 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)":\t", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray1
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
	_Itemp29 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[i_] != ((void *)0);
	if ( _Itemp29 )
		goto label16;
	else
		goto label22;
label16: (void)0;
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString **)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[i_]->_Iarray)[j]);
	_Itemp30 = j + 1;
	j = _Itemp30;
	goto label17;
label17: (void)0;
	_Itemp31 = ((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[i_] != ((void *)0);
	if ( _Itemp31 )
		goto label18;
	else
		goto label22;
label18: (void)0;
	_Itemp32 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)array->_Iarray)[i_];
	_Itemp33 = _Itemp32->_Imethods->_MgetLength(_Itemp32);
	_Itemp34 = j < _Itemp33;
	if ( _Itemp34 )
		goto label15;
	else
		goto label19;
label19: (void)0;
	_Itemp35 = i_ + 1;
	i_ = _Itemp35;
	goto label20;
label20: (void)0;
	_Itemp36 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp37 = _Itemp36->_Imethods->_MgetLength(_Itemp36);
	_Itemp38 = i_ < _Itemp37;
	if ( _Itemp38 )
		goto label14;
	else
		goto label21;
label21: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	goto label23;
label22: (void)0;
	static struct _Pshadow_Pstandard_CArray _Iarray2 = {
		&_Pshadow_Pstandard_CArray_Imethods, (void *)"NullPointerException!", (int_shadow_t)1, {(int_shadow_t)21}
	};
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray2
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring2);
	goto label23;
label23: (void)0;
	return;
}

struct _Pshadow_Pstandard_CClass *_Pshadow_Phello_CArrays_MgetClass(struct _Pshadow_Phello_CArrays *this) {
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
	_Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

