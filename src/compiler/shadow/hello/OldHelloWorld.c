/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/OldHelloWorld.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods,
	(boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.hello@OldHelloWorld"
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
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)": "
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

void _Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
	struct _Pshadow_Phello_COldHelloWorld* _Itemp0;
	struct _Pshadow_Phello_COldHelloWorld* _Itemp1;
	struct _Pshadow_Phello_COldHelloWorld* _Itemp2;
	static struct _Pshadow_Pstandard_CString _Istring2 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"Hello World!"
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring2);
	_Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint(0u, 10u);
	_Itemp0 = calloc(1, sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(_Itemp0, 1, 2);
	_Itemp1 = calloc(1, sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint(_Itemp1, 0);
	_Itemp2 = calloc(1, sizeof(struct _Pshadow_Phello_COldHelloWorld));
	_Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(_Itemp2, 0, 1);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor(struct _Pshadow_Phello_COldHelloWorld* this) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _Pshadow_Pstandard_CString _Istring3 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"object Hello World initialized with no arguments."
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring3);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_shadow_t a) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _Pshadow_Pstandard_CString _Istring4 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"object Hello World initialized with one argument: "
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);
	_Pshadow_Pio_CConsole_Mprint_Rint(a);
	static struct _Pshadow_Pstandard_CString _Istring5 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"."
	};
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring5);
	return;
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_shadow_t a, int_shadow_t b) {
	this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
	static struct _Pshadow_Pstandard_CString _Istring6 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"object Hello World initialized with two arguments: "
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
	_Pshadow_Pio_CConsole_Mprint_Rint(a);
	static struct _Pshadow_Pstandard_CString _Istring7 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)" and "
	};
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
	_Pshadow_Pio_CConsole_Mprint_Rint(b);
	static struct _Pshadow_Pstandard_CString _Istring8 = {
		&_Pshadow_Pstandard_CString_Imethods,
		(boolean_shadow_t)1, (ubyte_shadow_t *)"."
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
	_Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
	return 0;
}

