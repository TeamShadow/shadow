/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/io/Console.meta"

struct _IArray {
	void *_Iarray;
	int_shadow_t _Idims;
	int_shadow_t _Ilengths[1];
};

static struct _IArray _Iarray0 = {
	(void *)"shadow.io@Console", (int_shadow_t)1, {(int_shadow_t)17}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pio_CConsole_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Pio_CConsole_Mmain_R_Pshadow_Pstandard_CString_A1(struct _IArray * args) {
	struct _Pshadow_Pstandard_CString* helloWorld;
	static struct _IArray _Iarray1 = {
		(void *)"Hello World!", (int_shadow_t)1, {(int_shadow_t)12}
	};
	static struct _Pshadow_Pstandard_CString _Istring1 = {
		&_Pshadow_Pstandard_CString_Imethods, (boolean_shadow_t)1, &_Iarray1
	};
	helloWorld = &_Istring1;
	_Pshadow_Pio_CConsole_MprintLine_R_F_Pshadow_Pstandard_CString(helloWorld);
	return;
}

void _Pshadow_Pio_CConsole_Mconstructor(struct _Pshadow_Pio_CConsole* this) {
	this->_Imethods = &_Pshadow_Pio_CConsole_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pio_CConsole_MgetClass(struct _Pshadow_Pio_CConsole* this) {
	return &_Pshadow_Pio_CConsole_Iclass;
}

#include "shadow/io/Console.h"

struct _Pshadow_Pio_CConsole_Itable _Pshadow_Pio_CConsole_Imethods = {
	_Pshadow_Pio_CConsole_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Pio_CConsole_MreadInt,
	_Pshadow_Pio_CConsole_MreadULong,
	_Pshadow_Pio_CConsole_MreadLong,
	_Pshadow_Pio_CConsole_MprintLine_R_Fboolean,
	_Pshadow_Pio_CConsole_MprintLine_R_Fubyte,
	_Pshadow_Pio_CConsole_MprintLine_R_Fbyte,
	_Pshadow_Pio_CConsole_MprintLine_R_Fushort,
	_Pshadow_Pio_CConsole_MprintLine_R_Fshort,
	_Pshadow_Pio_CConsole_MprintLine_R_Fuint,
	_Pshadow_Pio_CConsole_MprintLine_R_Fint,
	_Pshadow_Pio_CConsole_MprintLine_R_Fulong,
	_Pshadow_Pio_CConsole_MprintLine_R_Flong,
	_Pshadow_Pio_CConsole_MprintLine_R_Ffloat,
	_Pshadow_Pio_CConsole_MprintLine_R_Fdouble,
	_Pshadow_Pio_CConsole_MprintLine_R_Fcode,
	_Pshadow_Pio_CConsole_MprintLine_R_F_Pshadow_Pstandard_CString,
	_Pshadow_Pio_CConsole_MprintLine_R_F_Pshadow_Pstandard_CObject,
	_Pshadow_Pio_CConsole_MprintLine,
	_Pshadow_Pio_CConsole_MreadShort,
	_Pshadow_Pio_CConsole_Mprint_R_Fboolean,
	_Pshadow_Pio_CConsole_Mprint_R_Fubyte,
	_Pshadow_Pio_CConsole_Mprint_R_Fbyte,
	_Pshadow_Pio_CConsole_Mprint_R_Fushort,
	_Pshadow_Pio_CConsole_Mprint_R_Fshort,
	_Pshadow_Pio_CConsole_Mprint_R_Fuint,
	_Pshadow_Pio_CConsole_Mprint_R_Fint,
	_Pshadow_Pio_CConsole_Mprint_R_Fulong,
	_Pshadow_Pio_CConsole_Mprint_R_Flong,
	_Pshadow_Pio_CConsole_Mprint_R_Ffloat,
	_Pshadow_Pio_CConsole_Mprint_R_Fdouble,
	_Pshadow_Pio_CConsole_Mprint_R_Fcode,
	_Pshadow_Pio_CConsole_Mprint_R_F_Pshadow_Pstandard_CString,
	_Pshadow_Pio_CConsole_Mprint_R_F_Pshadow_Pstandard_CObject,
	_Pshadow_Pio_CConsole_Mmain_R_Pshadow_Pstandard_CString_A1,
	_Pshadow_Pio_CConsole_MreadBoolean,
	_Pshadow_Pio_CConsole_MreadDouble,
	_Pshadow_Pio_CConsole_MreadUByte,
	_Pshadow_Pio_CConsole_MreadUInt,
	_Pshadow_Pio_CConsole_Mread,
	_Pshadow_Pio_CConsole_MreadFloat,
	_Pshadow_Pio_CConsole_MreadByte,
	_Pshadow_Pio_CConsole_MreadCode,
	_Pshadow_Pio_CConsole_MreadUShort,
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
	_Pshadow_Pio_CConsole_Mmain_R_Pshadow_Pstandard_CString_A1(argsArray);
	return 0;
}

