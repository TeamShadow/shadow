/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Math.meta"

static struct _Pshadow_Pstandard_CArray _Iarray0 = {
	&_Pshadow_Pstandard_CArray_Imethods, (void *)"shadow.standard@Math", (int_shadow_t)1, {(int_shadow_t)20}
};
static struct _Pshadow_Pstandard_CString _Istring0 = {
	&_Pshadow_Pstandard_CString_Imethods, ((boolean_shadow_t)1), &_Iarray0
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CMath_Iclass = {
	&_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

int_shadow_t  _Pshadow_Pstandard_CMath_Mmin_Rint_Rint(int_shadow_t  x, int_shadow_t  y) {
	boolean_shadow_t  _Itemp0;
	int_shadow_t  _Itemp1;
	_Itemp0 = x < y;
	if ( _Itemp0 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Itemp1 = x;
	goto label2;
label1: (void)0;
	_Itemp1 = y;
	goto label2;
label2: (void)0;
	return _Itemp1;
}

int_shadow_t  _Pshadow_Pstandard_CMath_Mmax_Rint_Rint(int_shadow_t  x, int_shadow_t  y) {
	boolean_shadow_t  _Itemp0;
	int_shadow_t  _Itemp1;
	_Itemp0 = x > y;
	if ( _Itemp0 )
		goto label0;
	else
		goto label1;
label0: (void)0;
	_Itemp1 = x;
	goto label2;
label1: (void)0;
	_Itemp1 = y;
	goto label2;
label2: (void)0;
	return _Itemp1;
}

void _Pshadow_Pstandard_CMath_Mconstructor(struct _Pshadow_Pstandard_CMath * this) {
	this->_Imethods = &_Pshadow_Pstandard_CMath_Imethods;
	this->E = 2.718281828459045;
	this->PI = 3.141592653589793;
	return;
}

struct _Pshadow_Pstandard_CClass * _Pshadow_Pstandard_CMath_MgetClass(struct _Pshadow_Pstandard_CMath * this) {
	return &_Pshadow_Pstandard_CMath_Iclass;
}

#include "shadow/standard/Math.h"

struct _Pshadow_Pstandard_CMath_Itable _Pshadow_Pstandard_CMath_Imethods = {
	_Pshadow_Pstandard_CMath_MgetClass,
	_Pshadow_Pstandard_CObject_MtoString,
	_Pshadow_Pstandard_CMath_Mmin_Rint_Rint,
	_Pshadow_Pstandard_CMath_Mln_Rdouble,
	_Pshadow_Pstandard_CMath_Mcosh_Rdouble,
	_Pshadow_Pstandard_CMath_Matan_Rdouble,
	_Pshadow_Pstandard_CMath_Matan_Rdouble_Rdouble,
	_Pshadow_Pstandard_CMath_Mpow_Rint_Ruint,
	_Pshadow_Pstandard_CMath_Mpow_Rlong_Ruint,
	_Pshadow_Pstandard_CMath_Mpow_Rdouble_Ruint,
	_Pshadow_Pstandard_CMath_Mpow_Rdouble_Rdouble,
	_Pshadow_Pstandard_CMath_Mmax_Rint_Rint,
	_Pshadow_Pstandard_CMath_Masin_Rdouble,
	_Pshadow_Pstandard_CMath_Mcos_Rdouble,
	_Pshadow_Pstandard_CMath_Msqrt_Rdouble,
	_Pshadow_Pstandard_CMath_Mmod_Rdouble_Rdouble,
	_Pshadow_Pstandard_CMath_Mdiv_Rint_Rint,
	_Pshadow_Pstandard_CMath_Mdiv_Rlong_Rlong,
	_Pshadow_Pstandard_CMath_Msinh_Rdouble,
	_Pshadow_Pstandard_CMath_Mparts_Rdouble,
	_Pshadow_Pstandard_CMath_Mlog_Rdouble,
	_Pshadow_Pstandard_CMath_Mlog_Rdouble_Rdouble,
	_Pshadow_Pstandard_CMath_Mtanh_Rdouble,
	_Pshadow_Pstandard_CMath_Mexp_Rdouble,
	_Pshadow_Pstandard_CMath_Mabs_Rint,
	_Pshadow_Pstandard_CMath_Mabs_Rlong,
	_Pshadow_Pstandard_CMath_Mabs_Rdouble,
	_Pshadow_Pstandard_CMath_Mfloor_Rdouble,
	_Pshadow_Pstandard_CMath_Mceiling_Rdouble,
	_Pshadow_Pstandard_CMath_Msin_Rdouble,
	_Pshadow_Pstandard_CMath_Macos_Rdouble,
	_Pshadow_Pstandard_CMath_Mtan_Rdouble,
};

