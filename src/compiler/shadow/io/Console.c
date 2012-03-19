/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/io/Console.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.io@Console", (int_shadow_t)1, {(int_shadow_t)17}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pio_CConsole_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Pio_CConsole_Mconstructor(struct _Pshadow_Pio_CConsole * this) {
	this->_Imethods = &_Pshadow_Pio_CConsole_Imethods;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pio_CConsole_MgetClass(struct _Pshadow_Pio_CConsole * this) {
	return &_Pshadow_Pio_CConsole_Iclass;
}

#include "shadow/io/Console.h"

struct _Pshadow_Pio_CConsole_Itable _Pshadow_Pio_CConsole_Imethods = {
	_Pshadow_Pio_CConsole_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Pio_CConsole_MreadInt,
	_Pshadow_Pio_CConsole_MreadULong,
	_Pshadow_Pio_CConsole_MreadLong,
	_Pshadow_Pio_CConsole_MprintLine_Rboolean,
	_Pshadow_Pio_CConsole_MprintLine_Rubyte,
	_Pshadow_Pio_CConsole_MprintLine_Rbyte,
	_Pshadow_Pio_CConsole_MprintLine_Rushort,
	_Pshadow_Pio_CConsole_MprintLine_Rshort,
	_Pshadow_Pio_CConsole_MprintLine_Ruint,
	_Pshadow_Pio_CConsole_MprintLine_Rint,
	_Pshadow_Pio_CConsole_MprintLine_Rulong,
	_Pshadow_Pio_CConsole_MprintLine_Rlong,
	_Pshadow_Pio_CConsole_MprintLine_Rfloat,
	_Pshadow_Pio_CConsole_MprintLine_Rdouble,
	_Pshadow_Pio_CConsole_MprintLine_Rcode,
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString,
	_Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject,
	_Pshadow_Pio_CConsole_MprintLine,
	_Pshadow_Pio_CConsole_MreadShort,
	_Pshadow_Pio_CConsole_Mprint_Rboolean,
	_Pshadow_Pio_CConsole_Mprint_Rubyte,
	_Pshadow_Pio_CConsole_Mprint_Rbyte,
	_Pshadow_Pio_CConsole_Mprint_Rushort,
	_Pshadow_Pio_CConsole_Mprint_Rshort,
	_Pshadow_Pio_CConsole_Mprint_Ruint,
	_Pshadow_Pio_CConsole_Mprint_Rint,
	_Pshadow_Pio_CConsole_Mprint_Rulong,
	_Pshadow_Pio_CConsole_Mprint_Rlong,
	_Pshadow_Pio_CConsole_Mprint_Rfloat,
	_Pshadow_Pio_CConsole_Mprint_Rdouble,
	_Pshadow_Pio_CConsole_Mprint_Rcode,
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString,
	_Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CObject,
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

