/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Arrays.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.hello@Arrays", (int_shadow_t)1, {(int_shadow_t)19}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrays_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

struct _Pshadow_Pstandard_CArray * _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * array) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	struct _Pshadow_Pstandard_CArray *_Itemp2;
	struct _Pshadow_Pstandard_CArray *_Itemp3;
	struct _Pshadow_Pstandard_CArray *_Itemp4;
	int_shadow_t _Itemp5;
	struct _Pshadow_Pstandard_CArray *_Itemp6;
	int_shadow_t _Itemp7;
	boolean_shadow_t _Itemp8;
	struct _Pshadow_Pstandard_CArray *_Itemp9;
	int_shadow_t _Itemp10;
	int_shadow_t _Itemp11;
	struct _Pshadow_Pstandard_CArray *_Itemp12;
	int_shadow_t _Itemp13;
	struct _Pshadow_Pstandard_CArray *_Itemp14;
	boolean_shadow_t _Itemp15;
	struct _Pshadow_Pstandard_CString *_Itemp16;
	int_shadow_t _Itemp17;
	boolean_shadow_t _Itemp18;
	int_shadow_t _Itemp19;
	boolean_shadow_t _Itemp20;
	struct _Pshadow_Pstandard_CArray *_Itemp21;
	struct _Pshadow_Pstandard_CArray *_Itemp22;
	struct _Pshadow_Pstandard_CArray *_Itemp23;
	int_shadow_t _Itemp24;
	boolean_shadow_t _Itemp25;
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
	_Itemp3 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp4 = (struct _Pshadow_Pstandard_CArray *)array;
	((struct _Pshadow_Pstandard_CString **)_Itemp3->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)_Itemp4->_Iarray)[i];
	_Itemp5 = i + 1;
	i = _Itemp5;
	goto label1;
label1: (void)0;
	_Itemp6 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp7 = _Itemp6->_Imethods->_MgetLength(_Itemp6);
	_Itemp8 = i < _Itemp7;
	if ( _Itemp8 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	int_shadow_t i_;
	_Itemp9 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp10 = _Itemp9->_Imethods->_MgetLength(_Itemp9);
	_Itemp11 = _Itemp10 - 1;
	i_ = _Itemp11;
	goto label13;
label3: (void)0;
	int_shadow_t maxIndex;
	maxIndex = i_;
	struct _Pshadow_Pstandard_CString *max;
	_Itemp12 = (struct _Pshadow_Pstandard_CArray *)newArray;
	max = ((struct _Pshadow_Pstandard_CString **)_Itemp12->_Iarray)[i_];
	int_shadow_t j;
	_Itemp13 = i_ - 1;
	j = _Itemp13;
	goto label11;
label4: (void)0;
	struct _Pshadow_Pstandard_CString *current;
	_Itemp14 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp15 = ((struct _Pshadow_Pstandard_CString **)_Itemp14->_Iarray)[j] != ((void *)0);
	if ( _Itemp15 )
		goto label5;
	else
		goto label9;
label5: (void)0;
	current = ((struct _Pshadow_Pstandard_CString **)_Itemp14->_Iarray)[j];
	_Itemp16 = (struct _Pshadow_Pstandard_CString *)current;
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
	_Itemp21 = (struct _Pshadow_Pstandard_CArray *)newArray;
	_Itemp22 = (struct _Pshadow_Pstandard_CArray *)newArray;
	((struct _Pshadow_Pstandard_CString **)_Itemp21->_Iarray)[maxIndex] = ((struct _Pshadow_Pstandard_CString **)_Itemp22->_Iarray)[i_];
	_Itemp23 = (struct _Pshadow_Pstandard_CArray *)newArray;
	((struct _Pshadow_Pstandard_CString **)_Itemp23->_Iarray)[i_] = max;
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

void _Pshadow_Phello_CArrays_Mconstructor(struct _Pshadow_Phello_CArrays * this) {
	this->_Imethods = &_Pshadow_Phello_CArrays_Imethods;
	return;
}

void _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CArray * args) {
	struct _Pshadow_Pstandard_CArray *_Itemp0;
	int_shadow_t _Itemp1;
	int_shadow_t _Itemp2;
	struct _Pshadow_Pstandard_CArray *_Itemp3;
	int_shadow_t _Itemp4;
	struct _Pshadow_Pstandard_CArray *_Itemp5;
	int_shadow_t _Itemp6;
	boolean_shadow_t _Itemp7;
	struct _Pshadow_Pstandard_CArray *_Itemp8;
	boolean_shadow_t _Itemp9;
	struct _Pshadow_Pstandard_CArray *_Itemp10;
	struct _Pshadow_Pstandard_CArray *_Itemp11;
	int_shadow_t _Itemp12;
	int_shadow_t _Itemp13;
	int_shadow_t _Itemp14;
	struct _Pshadow_Pstandard_CArray *_Itemp15;
	boolean_shadow_t _Itemp16;
	struct _Pshadow_Pstandard_CArray *_Itemp17;
	struct _Pshadow_Pstandard_CArray *_Itemp18;
	boolean_shadow_t _Itemp19;
	struct _Pshadow_Pstandard_CArray *_Itemp20;
	struct _Pshadow_Pstandard_CArray *_Itemp21;
	boolean_shadow_t _Itemp22;
	struct _Pshadow_Pstandard_CArray *_Itemp23;
	boolean_shadow_t _Itemp24;
	struct _Pshadow_Pstandard_CArray *_Itemp25;
	struct _Pshadow_Pstandard_CArray *_Itemp26;
	boolean_shadow_t _Itemp27;
	struct _Pshadow_Pstandard_CArray *_Itemp28;
	boolean_shadow_t _Itemp29;
	struct _Pshadow_Pstandard_CString *_Itemp30;
	int_shadow_t _Itemp31;
	int_shadow_t _Itemp32;
	struct _Pshadow_Pstandard_CString *_Itemp33;
	int_shadow_t _Itemp34;
	struct _Pshadow_Pstandard_CArray *_Itemp35;
	int_shadow_t _Itemp36;
	boolean_shadow_t _Itemp37;
	struct _Pshadow_Pstandard_CArray *_Itemp38;
	struct _Pshadow_Pstandard_CArray *_Itemp39;
	struct _Pshadow_Pstandard_CArray *_Itemp40;
	struct _Pshadow_Pstandard_CArray *_Itemp41;
	struct _Pshadow_Pstandard_CArray *_Itemp42;
	struct _Pshadow_Pstandard_CArray *_Itemp43;
	boolean_shadow_t _Itemp44;
	struct _Pshadow_Pstandard_CArray *_Itemp45;
	struct _Pshadow_Pstandard_CArray *_Itemp46;
	struct _Pshadow_Pstandard_CArray *_Itemp47;
	struct _Pshadow_Pstandard_CArray *_Itemp48;
	struct _Pshadow_Pstandard_CArray *_Itemp49;
	boolean_shadow_t _Itemp50;
	struct _Pshadow_Pstandard_CArray *_Itemp51;
	int_shadow_t _Itemp52;
	struct _Pshadow_Pstandard_CArray *_Itemp53;
	boolean_shadow_t _Itemp54;
	struct _Pshadow_Pstandard_CArray *_Itemp55;
	int_shadow_t _Itemp56;
	boolean_shadow_t _Itemp57;
	int_shadow_t _Itemp58;
	struct _Pshadow_Pstandard_CArray *_Itemp59;
	int_shadow_t _Itemp60;
	boolean_shadow_t _Itemp61;
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
	_Itemp8 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp9 = ((struct _Pshadow_Pstandard_CArray **)_Itemp8->_Iarray)[1] != ((void *)0);
	if ( _Itemp9 )
		goto label4;
	else
		goto label22;
label4: (void)0;
	_Itemp10 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp8->_Iarray)[1];
	_Itemp11 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp12 = _Itemp11->_Imethods->_MgetLength(_Itemp11);
	_Itemp13 = i + 1;
	_Itemp14 = _Itemp12 - _Itemp13;
	_Itemp15 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp16 = ((struct _Pshadow_Pstandard_CArray **)_Itemp15->_Iarray)[2] != ((void *)0);
	if ( _Itemp16 )
		goto label5;
	else
		goto label22;
label5: (void)0;
	_Itemp17 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp15->_Iarray)[2];
	_Itemp18 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp19 = ((struct _Pshadow_Pstandard_CArray **)_Itemp18->_Iarray)[3] != ((void *)0);
	if ( _Itemp19 )
		goto label6;
	else
		goto label22;
label6: (void)0;
	_Itemp20 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp18->_Iarray)[3];
	_Itemp21 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp22 = ((struct _Pshadow_Pstandard_CString **)_Itemp21->_Iarray)[i] != ((void *)0);
	if ( _Itemp22 )
		goto label7;
	else
		goto label22;
label7: (void)0;
	((struct _Pshadow_Pstandard_CString **)_Itemp20->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)_Itemp21->_Iarray)[i];
	((struct _Pshadow_Pstandard_CString **)_Itemp17->_Iarray)[i] = ((struct _Pshadow_Pstandard_CString **)_Itemp20->_Iarray)[i];
	((struct _Pshadow_Pstandard_CString **)_Itemp10->_Iarray)[_Itemp14] = ((struct _Pshadow_Pstandard_CString **)_Itemp17->_Iarray)[i];
	_Itemp23 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp24 = ((struct _Pshadow_Pstandard_CArray **)_Itemp23->_Iarray)[0] != ((void *)0);
	if ( _Itemp24 )
		goto label8;
	else
		goto label22;
label8: (void)0;
	_Itemp25 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp23->_Iarray)[0];
	_Itemp26 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp27 = ((struct _Pshadow_Pstandard_CString **)_Itemp26->_Iarray)[i] != ((void *)0);
	if ( _Itemp27 )
		goto label9;
	else
		goto label22;
label9: (void)0;
	_Itemp28 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp29 = ((struct _Pshadow_Pstandard_CString **)_Itemp28->_Iarray)[i] != ((void *)0);
	if ( _Itemp29 )
		goto label10;
	else
		goto label22;
label10: (void)0;
	_Itemp30 = (struct _Pshadow_Pstandard_CString *)((struct _Pshadow_Pstandard_CString **)_Itemp28->_Iarray)[i];
	_Itemp31 = _Itemp30->_Imethods->_MgetLength(_Itemp30);
	_Itemp32 = _Itemp31 - 1;
	_Itemp33 = ((struct _Pshadow_Pstandard_CString **)_Itemp26->_Iarray)[i]->_Imethods->_Msubstring_Rint_Rint(((struct _Pshadow_Pstandard_CString **)_Itemp26->_Iarray)[i], 1, _Itemp32);
	((struct _Pshadow_Pstandard_CString **)_Itemp25->_Iarray)[i] = _Itemp33;
	_Itemp34 = i + 1;
	i = _Itemp34;
	goto label11;
label11: (void)0;
	_Itemp35 = (struct _Pshadow_Pstandard_CArray *)args;
	_Itemp36 = _Itemp35->_Imethods->_MgetLength(_Itemp35);
	_Itemp37 = i < _Itemp36;
	if ( _Itemp37 )
		goto label3;
	else
		goto label12;
label12: (void)0;
	_Itemp38 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp39 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp40 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp39->_Iarray)[2];
	_Itemp41 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(_Itemp40);
	((struct _Pshadow_Pstandard_CArray **)_Itemp38->_Iarray)[2] = _Itemp41;
	_Itemp42 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp43 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp44 = ((struct _Pshadow_Pstandard_CArray **)_Itemp43->_Iarray)[1] != ((void *)0);
	if ( _Itemp44 )
		goto label13;
	else
		goto label22;
label13: (void)0;
	((struct _Pshadow_Pstandard_CArray **)_Itemp42->_Iarray)[3] = ((struct _Pshadow_Pstandard_CArray **)_Itemp43->_Iarray)[1];
	_Itemp45 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp46 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp47 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp46->_Iarray)[3];
	_Itemp48 = _Pshadow_Phello_CArrays_Msort_R_Pshadow_Pstandard_CString_A1(_Itemp47);
	((struct _Pshadow_Pstandard_CArray **)_Itemp45->_Iarray)[3] = _Itemp48;
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
	_Itemp49 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp50 = ((struct _Pshadow_Pstandard_CArray **)_Itemp49->_Iarray)[i_] != ((void *)0);
	if ( _Itemp50 )
		goto label16;
	else
		goto label22;
label16: (void)0;
	_Itemp51 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp49->_Iarray)[i_];
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(((struct _Pshadow_Pstandard_CString **)_Itemp51->_Iarray)[j]);
	_Itemp52 = j + 1;
	j = _Itemp52;
	goto label17;
label17: (void)0;
	_Itemp53 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp54 = ((struct _Pshadow_Pstandard_CArray **)_Itemp53->_Iarray)[i_] != ((void *)0);
	if ( _Itemp54 )
		goto label18;
	else
		goto label22;
label18: (void)0;
	_Itemp55 = (struct _Pshadow_Pstandard_CArray *)((struct _Pshadow_Pstandard_CArray **)_Itemp53->_Iarray)[i_];
	_Itemp56 = _Itemp55->_Imethods->_MgetLength(_Itemp55);
	_Itemp57 = j < _Itemp56;
	if ( _Itemp57 )
		goto label15;
	else
		goto label19;
label19: (void)0;
	_Itemp58 = i_ + 1;
	i_ = _Itemp58;
	goto label20;
label20: (void)0;
	_Itemp59 = (struct _Pshadow_Pstandard_CArray *)array;
	_Itemp60 = _Itemp59->_Imethods->_MgetLength(_Itemp59);
	_Itemp61 = i_ < _Itemp60;
	if ( _Itemp61 )
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

struct _Pshadow_Pstandard_CClass * _Pshadow_Phello_CArrays_MgetClass(struct _Pshadow_Phello_CArrays * this) {
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

