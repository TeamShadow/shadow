/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Math.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.standard@Math"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CMath_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Pstandard_CMath_Mconstructor(struct _Pshadow_Pstandard_CMath* this) {
     this->_Imethods = &_Pshadow_Pstandard_CMath_Imethods;
     this->E = 2.718281828459045;
     this->PI = 3.141592653589793;
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pstandard_CMath_MgetClass(struct _Pshadow_Pstandard_CMath* this) {
     return &_Pshadow_Pstandard_CMath_Iclass;
}

#include "shadow/standard/Math.h"

struct _Pshadow_Pstandard_CMath_Itable _Pshadow_Pstandard_CMath_Imethods = {
     _Pshadow_Pstandard_CMath_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
     _Pshadow_Pstandard_CMath_Mln_Rdouble,
     _Pshadow_Pstandard_CMath_Mcosh_Rdouble,
     _Pshadow_Pstandard_CMath_Matan_Rdouble,
     _Pshadow_Pstandard_CMath_Matan_Rdouble_Rdouble,
     _Pshadow_Pstandard_CMath_Mpow_Rint_Ruint,
     _Pshadow_Pstandard_CMath_Mpow_Rlong_Ruint,
     _Pshadow_Pstandard_CMath_Mpow_Rdouble_Ruint,
     _Pshadow_Pstandard_CMath_Mpow_Rdouble_Rdouble,
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
     _Pshadow_Pstandard_CMath_Mtan_Rdouble,
     _Pshadow_Pstandard_CMath_Macos_Rdouble,
};

