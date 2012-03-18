/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/HelloWorld.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.hello@HelloWorld", (int_shadow_t)1, {(int_shadow_t)23}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CHelloWorld_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

uint_shadow_t _Pshadow_Phello_CHelloWorld_MgetBoth(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString** _Ireturn1) {
	*_Ireturn1 = this->self->self->self->message;
	return this->self->self->times;
}

struct _Pshadow_Pstandard_CString* _Pshadow_Phello_CHelloWorld_MgetMessage(struct _Pshadow_Phello_CHelloWorld* this) {
	return this->self->message;
}

void _Pshadow_Phello_CHelloWorld_Mdoit(struct _Pshadow_Phello_CHelloWorld* this) {
	uint_shadow_t _Itemp0;
	uint_shadow_t _Itemp1;
	boolean_shadow_t _Itemp2;
	uint_shadow_t i;
	i = ((uint_shadow_t)0);
	goto label1;
label0: (void)0;
	_Itemp0 = i + ((uint_shadow_t)1);
	_Pshadow_Pio_CConsole_Mprint_Ruint(_Itemp0);
	static struct _IArray _Iarray1 = {
		(void *)": ", (int_shadow_t)1, {(int_shadow_t)2}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray1
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(this->message);
	_Itemp1 = i + ((uint_shadow_t)1);
	i = _Itemp1;
	goto label1;
label1: (void)0;
	_Itemp2 = i < this->times;
	if ( _Itemp2 )
		goto label0;
	else
		goto label2;
label2: (void)0;
	_Pshadow_Pio_CConsole_MprintLine();
	return;
}

uint_shadow_t _Pshadow_Phello_CHelloWorld_MgetTimes(struct _Pshadow_Phello_CHelloWorld* this) {
	return this->times;
}

uint_shadow_t _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(uint_shadow_t first, uint_shadow_t second, uint_shadow_t* _Ireturn1, uint_shadow_t* _Ireturn2, uint_shadow_t* _Ireturn3, uint_shadow_t* _Ireturn4, uint_shadow_t* _Ireturn5, uint_shadow_t* _Ireturn6, uint_shadow_t* _Ireturn7, uint_shadow_t* _Ireturn8, struct _Pshadow_Pstandard_CString** _Ireturn9, uint_shadow_t* _Ireturn10, struct _Pshadow_Pstandard_CObject** _Ireturn11) {
	uint_shadow_t _Itemp0;
	uint_shadow_t _Itemp1;
	uint_shadow_t _Itemp2;
	uint_shadow_t _Itemp3;
	uint_shadow_t _Itemp4;
	uint_shadow_t _Itemp5;
	uint_shadow_t _Itemp6;
	uint_shadow_t _Itemp7;
	uint_shadow_t _Itemp8;
	uint_shadow_t _Itemp9;
	struct _Pshadow_Pstandard_CObject* _Itemp10;
	uint_shadow_t third;
	_Itemp0 = first + second;
	third = _Itemp0;
	uint_shadow_t fourth;
	_Itemp1 = second + third;
	fourth = _Itemp1;
	uint_shadow_t fifth;
	_Itemp2 = third + fourth;
	fifth = _Itemp2;
	uint_shadow_t sixth;
	_Itemp3 = fourth + fifth;
	sixth = _Itemp3;
	uint_shadow_t seventh;
	_Itemp4 = fifth + sixth;
	seventh = _Itemp4;
	uint_shadow_t eighth;
	_Itemp5 = sixth + seventh;
	eighth = _Itemp5;
	uint_shadow_t ninth;
	_Itemp6 = seventh + eighth;
	ninth = _Itemp6;
	_Itemp7 = third - second;
	_Itemp8 = first + second;
	_Itemp9 = eighth + ninth;
	_Itemp10 = calloc(1, sizeof(struct _Pshadow_Pstandard_CObject));
	_Pshadow_Pstandard_CObject_Mconstructor(_Itemp10);
	*_Ireturn1 = second;
	*_Ireturn2 = _Itemp8;
	*_Ireturn3 = fourth;
	*_Ireturn4 = fifth;
	*_Ireturn5 = sixth;
	*_Ireturn6 = seventh;
	*_Ireturn7 = eighth;
	*_Ireturn8 = ninth;
	static struct _IArray _Iarray2 = {
		(void *)"String Test", (int_shadow_t)1, {(int_shadow_t)11}
	};
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray2
	};
	*_Ireturn9 = &_Istring2;
	*_Ireturn10 = _Itemp9;
	*_Ireturn11 = _Itemp10;
	return _Itemp7;
}

void _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	boolean_shadow_t _Itemp0;
	uint_shadow_t _Itemp1;
	uint_shadow_t _Itemp2;
	uint_shadow_t _Itemp3;
	uint_shadow_t _Itemp4;
	uint_shadow_t _Itemp5;
	uint_shadow_t _Itemp6;
	uint_shadow_t _Itemp7;
	uint_shadow_t _Itemp8;
	uint_shadow_t _Itemp9;
	struct _Pshadow_Pstandard_CString* _Itemp10;
	uint_shadow_t _Itemp11;
	struct _Pshadow_Pstandard_CObject* _Itemp12;
	uint_shadow_t _Itemp13;
	uint_shadow_t _Itemp14;
	uint_shadow_t _Itemp15;
	boolean_shadow_t _Itemp16;
	struct _Pshadow_Phello_CHelloWorld* _Itemp17;
	uint_shadow_t _Itemp18;
	struct _Pshadow_Pstandard_CString* _Itemp19;
	struct _Pshadow_Phello_CHelloWorld* _Itemp20;
	struct _Pshadow_Phello_CHelloWorld* _Itemp21;
	struct _Pshadow_Phello_CHelloWorld* _Itemp22;
	struct _Pshadow_Pstandard_CString* _Itemp23;
	struct _Pshadow_Pstandard_CString* _Itemp24;
	struct _Pshadow_Pstandard_CString* _Itemp25;
	struct _Pshadow_Pstandard_CString* _Itemp26;
	struct _Pshadow_Pstandard_CString* name;
	static struct _IArray _Iarray3 = {
		(void *)"Fibonacci", (int_shadow_t)1, {(int_shadow_t)9}
	};
	static struct _Pshadow_Pstandard_CString _Istring3 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray3
	};
	name = &_Istring3;
	uint_shadow_t start;
	start = ((uint_shadow_t)0);
	uint_shadow_t temp;
	temp = ((uint_shadow_t)10000000);
	goto label4;
label0: (void)0;
	_Itemp0 = start == ((uint_shadow_t)2);
	if ( _Itemp0 )
		goto label1;
	else
		goto label2;
label1: (void)0;
	static struct _IArray _Iarray4 = {
		(void *)"Lucas", (int_shadow_t)1, {(int_shadow_t)5}
	};
	static struct _Pshadow_Pstandard_CString _Istring4 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray4
	};
	name = &_Istring4;
	goto label3;
label2: (void)0;
	goto label3;
label3: (void)0;
	struct _Pshadow_Pstandard_CObject* last;
	last = ((void *)0);
	struct _Pshadow_Pstandard_CString* test;
	test = ((void *)0);
	uint_shadow_t fib0;
	fib0 = 0;
	uint_shadow_t fib1;
	fib1 = 0;
	uint_shadow_t fib2;
	fib2 = 0;
	uint_shadow_t fib3;
	fib3 = 0;
	uint_shadow_t fib4;
	fib4 = 0;
	uint_shadow_t fib5;
	fib5 = 0;
	uint_shadow_t fib6;
	fib6 = 0;
	uint_shadow_t fib7;
	fib7 = 0;
	uint_shadow_t fib8;
	fib8 = 0;
	uint_shadow_t fib9;
	fib9 = 0;
	_Itemp1 = _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(start, ((uint_shadow_t)1), &_Itemp2, &_Itemp3, &_Itemp4, &_Itemp5, &_Itemp6, &_Itemp7, &_Itemp8, &_Itemp9, &_Itemp10, &_Itemp11, &_Itemp12);
	fib0 = _Itemp1;
	fib1 = _Itemp2;
	fib2 = _Itemp3;
	fib3 = _Itemp4;
	fib4 = _Itemp5;
	fib5 = _Itemp6;
	fib6 = _Itemp7;
	fib7 = _Itemp8;
	fib8 = _Itemp9;
	test = _Itemp10;
	fib9 = _Itemp11;
	last = _Itemp12;
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray5 = {
		(void *)" 0: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring5 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray5
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib0);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray6 = {
		(void *)" 1: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring6 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray6
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib1);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray7 = {
		(void *)" 2: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring7 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray7
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib2);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray8 = {
		(void *)" 3: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring8 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray8
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring8);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib3);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray9 = {
		(void *)" 4: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring9 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray9
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring9);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib4);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray10 = {
		(void *)" 5: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring10 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray10
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring10);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib5);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray11 = {
		(void *)" 6: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring11 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray11
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring11);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib6);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray12 = {
		(void *)" 7: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring12 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray12
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring12);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib7);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray13 = {
		(void *)" 8: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring13 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray13
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring13);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib8);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray14 = {
		(void *)" 9: ", (int_shadow_t)1, {(int_shadow_t)4}
	};
	static struct _Pshadow_Pstandard_CString _Istring14 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray14
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring14);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(fib9);
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
	static struct _IArray _Iarray15 = {
		(void *)" 10: ", (int_shadow_t)1, {(int_shadow_t)5}
	};
	static struct _Pshadow_Pstandard_CString _Istring15 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray15
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring15);
	_Itemp13 = fib8 + fib9;
	_Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp13);
	_Pshadow_Pio_CConsole_MprintLine();
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(test);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(last);
	_Pshadow_Pio_CConsole_MprintLine();
	_Itemp14 = start + ((uint_shadow_t)1);
	start = _Itemp14;
	_Itemp15 = temp - ((uint_shadow_t)10000);
	temp = _Itemp15;
	_Pshadow_Pio_CConsole_MprintLine_Ruint(temp);
	goto label4;
label4: (void)0;
	_Itemp16 = start <= ((uint_shadow_t)2);
	if ( _Itemp16 )
		goto label0;
	else
		goto label5;
label5: (void)0;
	struct _Pshadow_Phello_CHelloWorld* hello1;
	_Itemp17 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
	_Pshadow_Phello_CHelloWorld_Mconstructor(_Itemp17);
	hello1 = _Itemp17;
	uint_shadow_t defaultTimes;
	defaultTimes = 0;
	struct _Pshadow_Pstandard_CString* defaultMessage;
	defaultMessage = ((void *)0);
	_Itemp18 = hello1->_Imethods->_MgetBoth(hello1, &_Itemp19);
	defaultTimes = _Itemp18;
	defaultMessage = _Itemp19;
	static struct _IArray _Iarray16 = {
		(void *)"Default times: ", (int_shadow_t)1, {(int_shadow_t)15}
	};
	static struct _Pshadow_Pstandard_CString _Istring16 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray16
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring16);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(defaultTimes);
	_Pshadow_Pio_CConsole_MprintLine_Ruint(hello1->times);
	static struct _IArray _Iarray17 = {
		(void *)"Default message: ", (int_shadow_t)1, {(int_shadow_t)17}
	};
	static struct _Pshadow_Pstandard_CString _Istring17 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray17
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring17);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(defaultMessage);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(hello1->self->message);
	_Pshadow_Pio_CConsole_MprintLine();
	struct _Pshadow_Phello_CHelloWorld* hello2;
	_Itemp20 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
	_Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(_Itemp20, ((uint_shadow_t)10));
	hello2 = _Itemp20;
	struct _Pshadow_Phello_CHelloWorld* hello3;
	_Itemp21 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
	static struct _IArray _Iarray18 = {
		(void *)"Five Times!", (int_shadow_t)1, {(int_shadow_t)11}
	};
	static struct _Pshadow_Pstandard_CString _Istring18 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray18
	};
	_Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(_Itemp21, &_Istring18);
	hello3 = _Itemp21;
	struct _Pshadow_Phello_CHelloWorld* hello4;
	_Itemp22 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
	static struct _IArray _Iarray19 = {
		(void *)"Fifteen Times!", (int_shadow_t)1, {(int_shadow_t)14}
	};
	static struct _Pshadow_Pstandard_CString _Istring19 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray19
	};
	_Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(_Itemp22, ((uint_shadow_t)15), &_Istring19);
	hello4 = _Itemp22;
	hello1->_Imethods->_Mdoit(hello1);
	hello2->self->_Imethods->_Mdoit(hello2->self);
	hello3->self->self->_Imethods->_Mdoit(hello3->self->self);
	hello4->self->self->self->_Imethods->_Mdoit(hello4->self->self->self);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello1);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello2->self);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello3->self->self);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello4->self->self->self);
	_Pshadow_Pio_CConsole_MprintLine();
	_Itemp23 = hello1->_Imethods->_MgetMessage(hello1);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp23);
	_Itemp24 = hello2->_Imethods->_MgetMessage(hello2);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp24);
	_Itemp25 = hello3->_Imethods->_MgetMessage(hello3);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp25);
	_Itemp26 = hello4->_Imethods->_MgetMessage(hello4);
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp26);
	_Pshadow_Pio_CConsole_MprintLine();
	return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor(struct _Pshadow_Phello_CHelloWorld* this) {
	this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
	this->message = ((void *)0);
	this->times = 0;
	this->self = ((void *)0);
	this->self = this;
	this->times = ((uint_shadow_t)5);
	static struct _IArray _Iarray20 = {
		(void *)"Hello World!", (int_shadow_t)1, {(int_shadow_t)12}
	};
	static struct _Pshadow_Pstandard_CString _Istring20 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray20
	};
	this->message = &_Istring20;
	return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(struct _Pshadow_Phello_CHelloWorld* this, uint_shadow_t times) {
	this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
	this->message = ((void *)0);
	this->times = 0;
	this->self = ((void *)0);
	this->self = this;
	this->times = times;
	static struct _IArray _Iarray21 = {
		(void *)"Hello World!", (int_shadow_t)1, {(int_shadow_t)12}
	};
	static struct _Pshadow_Pstandard_CString _Istring21 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray21
	};
	this->message = &_Istring21;
	return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString* message) {
	this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
	this->message = ((void *)0);
	this->times = 0;
	this->self = ((void *)0);
	this->self = this;
	this->times = ((uint_shadow_t)5);
	this->message = message;
	return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, uint_shadow_t times, struct _Pshadow_Pstandard_CString* message) {
	this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
	this->message = ((void *)0);
	this->times = 0;
	this->self = ((void *)0);
	this->self = this;
	this->times = times;
	this->message = message;
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CHelloWorld_MgetClass(struct _Pshadow_Phello_CHelloWorld* this) {
	return &_Pshadow_Phello_CHelloWorld_Iclass;
}

struct _Pshadow_Phello_CHelloWorld_Itable _Pshadow_Phello_CHelloWorld_Imethods = {
	_Pshadow_Phello_CHelloWorld_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Phello_CHelloWorld_MgetBoth,
	_Pshadow_Phello_CHelloWorld_MgetMessage,
	_Pshadow_Phello_CHelloWorld_Mdoit,
	_Pshadow_Phello_CHelloWorld_MgetTimes,
	_Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint,
	_Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1,
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
	_Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

