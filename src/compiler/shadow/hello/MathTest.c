/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/MathTest.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.hello@MathTest"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CMathTest_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CMathTest_Mconstructor(struct _Pshadow_Phello_CMathTest* this) {
     this->_Imethods = &_Pshadow_Phello_CMathTest_Imethods;
     return;
}

void _Pshadow_Phello_CMathTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     int_shadow_t _Itemp0;
     int_shadow_t _Itemp1;
     boolean_shadow_t _Itemp2;
     int_shadow_t _Itemp3;
     int_shadow_t _Itemp4;
     int_shadow_t _Itemp5;
     int_shadow_t _Itemp6;
     int_shadow_t _Itemp7;
     int_shadow_t _Itemp8;
     int_shadow_t _Itemp9;
     int_shadow_t _Itemp10;
     int_shadow_t _Itemp11;
     boolean_shadow_t _Itemp12;
     int_shadow_t _Itemp13;
     boolean_shadow_t _Itemp14;
     uint_shadow_t _Itemp15;
     int_shadow_t _Itemp16;
     uint_shadow_t _Itemp17;
     long_shadow_t _Itemp18;
     ulong_shadow_t _Itemp19;
     long_shadow_t _Itemp20;
     ulong_shadow_t _Itemp21;
     int_shadow_t _Itemp22;
     int_shadow_t _Itemp23;
     long_shadow_t _Itemp24;
     long_shadow_t _Itemp25;
     double_shadow_t _Itemp26;
     double_shadow_t _Itemp27;
     double_shadow_t _Itemp28;
     double_shadow_t _Itemp29;
     int_shadow_t _Itemp30;
     double_shadow_t _Itemp31;
     double_shadow_t _Itemp32;
     double_shadow_t _Itemp33;
     double_shadow_t _Itemp34;
     double_shadow_t _Itemp35;
     int_shadow_t _Itemp36;
     double_shadow_t _Itemp37;
     double_shadow_t _Itemp38;
     double_shadow_t _Itemp39;
     double_shadow_t _Itemp40;
     double_shadow_t _Itemp41;
     double_shadow_t _Itemp42;
     uint_shadow_t _Itemp43;
     double_shadow_t _Itemp44;
     double_shadow_t _Itemp45;
     double_shadow_t _Itemp46;
     double_shadow_t _Itemp47;
     double_shadow_t _Itemp48;
     double_shadow_t _Itemp49;
     double_shadow_t _Itemp50;
     double_shadow_t _Itemp51;
     double_shadow_t _Itemp52;
     int_shadow_t _Itemp53;
     int_shadow_t _Itemp54;
     int_shadow_t _Itemp55;
     int_shadow_t _Itemp56;
     long_shadow_t _Itemp57;
     double_shadow_t _Itemp58;
     double_shadow_t _Itemp59;
     double_shadow_t _Itemp60;
     double_shadow_t _Itemp61;
     int_shadow_t a;
     int_shadow_t quot;
     int_shadow_t rem;
     long_shadow_t longQuot;
     long_shadow_t longRem;
     _Itemp0 = -5;
     _Itemp1 = 33 * 2;
     _Itemp2 = ((boolean_shadow_t)1) || ((boolean_shadow_t)0);
     if ( _Itemp2 )
          goto label0;
     else
          goto label1;
label0: (void)0;
     _Itemp3 = 1;
     goto label2;
label1: (void)0;
     _Itemp3 = 2;
     goto label2;
label2: (void)0;
     _Itemp4 = _Itemp1 % _Itemp3;
     _Itemp5 = _Itemp0 + _Itemp4;
     a = _Itemp5;
     _Itemp6 = a + 1;
     a = _Itemp6;
     _Itemp7 = a / 15;
     a = _Itemp7;
     _Itemp8 = 1 - 2;
     _Itemp9 = a - _Itemp8;
     a = _Itemp9;
     _Itemp10 = a % 5;
     a = _Itemp10;
     goto label3;
label4: (void)0;
     _Itemp11 = a / 2;
     a = _Itemp11;
     goto label3;
label3: (void)0;
     _Itemp12 = a != 0;
     if ( _Itemp12 )
          goto label4;
     else
          goto label5;
label5: (void)0;
     goto label6;
label6: (void)0;
     _Itemp13 = a + 10;
     a = _Itemp13;
     goto label7;
label7: (void)0;
     _Itemp14 = a < 10;
     if ( _Itemp14 )
          goto label6;
     else
          goto label8;
label8: (void)0;
     static struct _Pshadow_Pstandard_CString _Istring1 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(5) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
     _Itemp15 = _Pshadow_Pstandard_CMath_Mabs_Rint(5);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp15);
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(-5) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring2);
     _Itemp16 = -5;
     _Itemp17 = _Pshadow_Pstandard_CMath_Mabs_Rint(_Itemp16);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp17);
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(5l) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring3);
     _Itemp18 = +((long_shadow_t)5ll);
     _Itemp19 = _Pshadow_Pstandard_CMath_Mabs_Rlong(_Itemp18);
     _Pshadow_Pio_CConsole_MprintLine_Rulong(_Itemp19);
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(-5l) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);
     _Itemp20 = -((long_shadow_t)5ll);
     _Itemp21 = _Pshadow_Pstandard_CMath_Mabs_Rlong(_Itemp20);
     _Pshadow_Pio_CConsole_MprintLine_Rulong(_Itemp21);
     quot = 0;
     rem = 0;
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.div(10, 3) = ("
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
     _Itemp22 = _Pshadow_Pstandard_CMath_Mdiv_Rint_Rint(5, 3, &_Itemp23);
     quot = _Itemp22;
     rem = _Itemp23;
     _Pshadow_Pio_CConsole_Mprint_Rint(quot);
     static struct _Pshadow_Pstandard_CString _Istring6 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)", "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
     _Pshadow_Pio_CConsole_Mprint_Rint(rem);
     _Pshadow_Pio_CConsole_MprintLine_Rcode(')');
     longQuot = 0;
     longRem = 0;
     static struct _Pshadow_Pstandard_CString _Istring7 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.div(10000000000l, 123456l) = ("
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
     _Itemp24 = _Pshadow_Pstandard_CMath_Mdiv_Rlong_Rlong(((long_shadow_t)10000000000ll), ((long_shadow_t)123456ll), &_Itemp25);
     longQuot = _Itemp24;
     longRem = _Itemp25;
     _Pshadow_Pio_CConsole_Mprint_Rlong(longQuot);
     static struct _Pshadow_Pstandard_CString _Istring8 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)", "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring8);
     _Pshadow_Pio_CConsole_Mprint_Rlong(longRem);
     _Pshadow_Pio_CConsole_MprintLine_Rcode(')');
     static struct _Pshadow_Pstandard_CString _Istring9 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring9);
     _Itemp26 = _Pshadow_Pstandard_CMath_Mabs_Rdouble(1.3);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp26);
     static struct _Pshadow_Pstandard_CString _Istring10 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.abs(-1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring10);
     _Itemp27 = -1.3;
     _Itemp28 = _Pshadow_Pstandard_CMath_Mabs_Rdouble(_Itemp27);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp28);
     static struct _Pshadow_Pstandard_CString _Istring11 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.floor(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring11);
     _Itemp29 = _Pshadow_Pstandard_CMath_Mfloor_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp29);
     static struct _Pshadow_Pstandard_CString _Istring12 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.floor(-1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring12);
     _Itemp30 = -1;
     _Itemp31 = _Pshadow_Pstandard_CMath_Mfloor_Rdouble(_Itemp30);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp31);
     static struct _Pshadow_Pstandard_CString _Istring13 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.floor(1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring13);
     _Itemp32 = _Pshadow_Pstandard_CMath_Mfloor_Rdouble(1.3);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp32);
     static struct _Pshadow_Pstandard_CString _Istring14 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.floor(-1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring14);
     _Itemp33 = -1.3;
     _Itemp34 = _Pshadow_Pstandard_CMath_Mfloor_Rdouble(_Itemp33);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp34);
     static struct _Pshadow_Pstandard_CString _Istring15 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.ceiling(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring15);
     _Itemp35 = _Pshadow_Pstandard_CMath_Mceiling_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp35);
     static struct _Pshadow_Pstandard_CString _Istring16 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.ceiling(-1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring16);
     _Itemp36 = -1;
     _Itemp37 = _Pshadow_Pstandard_CMath_Mceiling_Rdouble(_Itemp36);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp37);
     static struct _Pshadow_Pstandard_CString _Istring17 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.ceiling(1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring17);
     _Itemp38 = _Pshadow_Pstandard_CMath_Mceiling_Rdouble(1.3);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp38);
     static struct _Pshadow_Pstandard_CString _Istring18 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.ceiling(-1.3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring18);
     _Itemp39 = -1.3;
     _Itemp40 = _Pshadow_Pstandard_CMath_Mceiling_Rdouble(_Itemp39);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp40);
     static struct _Pshadow_Pstandard_CString _Istring19 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.sin(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring19);
     _Itemp41 = _Pshadow_Pstandard_CMath_Msin_Rdouble(0);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp41);
     static struct _Pshadow_Pstandard_CString _Istring20 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.cos(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring20);
     _Itemp42 = _Pshadow_Pstandard_CMath_Mcos_Rdouble(0);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp42);
     static struct _Pshadow_Pstandard_CString _Istring21 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.tan(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring21);
     _Itemp43 = _Pshadow_Pstandard_CMath_Mabs_Rint(0);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp43);
     static struct _Pshadow_Pstandard_CString _Istring22 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.asin(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring22);
     _Itemp44 = _Pshadow_Pstandard_CMath_Masin_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp44);
     static struct _Pshadow_Pstandard_CString _Istring23 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.acos(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring23);
     _Itemp45 = _Pshadow_Pstandard_CMath_Macos_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp45);
     static struct _Pshadow_Pstandard_CString _Istring24 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.atan(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring24);
     _Itemp46 = _Pshadow_Pstandard_CMath_Matan_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp46);
     static struct _Pshadow_Pstandard_CString _Istring25 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.atan(5, 3) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring25);
     _Itemp47 = _Pshadow_Pstandard_CMath_Matan_Rdouble_Rdouble(5, 3);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp47);
     static struct _Pshadow_Pstandard_CString _Istring26 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.sinh(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring26);
     _Itemp48 = _Pshadow_Pstandard_CMath_Msinh_Rdouble(0);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp48);
     static struct _Pshadow_Pstandard_CString _Istring27 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.cosh(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring27);
     _Itemp49 = _Pshadow_Pstandard_CMath_Mcosh_Rdouble(0);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp49);
     static struct _Pshadow_Pstandard_CString _Istring28 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.tanh(0) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring28);
     _Itemp50 = _Pshadow_Pstandard_CMath_Mtanh_Rdouble(0);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp50);
     static struct _Pshadow_Pstandard_CString _Istring29 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.sqrt(33) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring29);
     _Itemp51 = _Pshadow_Pstandard_CMath_Msqrt_Rdouble(33);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp51);
     static struct _Pshadow_Pstandard_CString _Istring30 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.exp(1) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring30);
     _Itemp52 = _Pshadow_Pstandard_CMath_Mexp_Rdouble(1);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp52);
     static struct _Pshadow_Pstandard_CString _Istring31 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.pow(5, 9u) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring31);
     _Itemp53 = _Pshadow_Pstandard_CMath_Mpow_Rint_Ruint(5, 9u);
     _Pshadow_Pio_CConsole_MprintLine_Rint(_Itemp53);
     static struct _Pshadow_Pstandard_CString _Istring32 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.pow(-5, 9u) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring32);
     _Itemp54 = -5;
     _Itemp55 = _Pshadow_Pstandard_CMath_Mpow_Rint_Ruint(_Itemp54, 9u);
     _Pshadow_Pio_CConsole_MprintLine_Rint(_Itemp55);
     static struct _Pshadow_Pstandard_CString _Istring33 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.pow(50, 10u) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring33);
     _Itemp56 = _Pshadow_Pstandard_CMath_Mpow_Rint_Ruint(50, 10u);
     _Pshadow_Pio_CConsole_MprintLine_Rint(_Itemp56);
     static struct _Pshadow_Pstandard_CString _Istring34 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.pow(50l, 10u) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring34);
     _Itemp57 = _Pshadow_Pstandard_CMath_Mpow_Rlong_Ruint(((long_shadow_t)50ll), 10u);
     _Pshadow_Pio_CConsole_MprintLine_Rlong(_Itemp57);
     static struct _Pshadow_Pstandard_CString _Istring35 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.pow(1.23, 4.56) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring35);
     _Itemp58 = _Pshadow_Pstandard_CMath_Mpow_Rdouble_Rdouble(1.23, 4.56);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp58);
     static struct _Pshadow_Pstandard_CString _Istring36 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.ln(2.718281828459045) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring36);
     _Itemp59 = _Pshadow_Pstandard_CMath_Mln_Rdouble(2.718281828459045);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp59);
     static struct _Pshadow_Pstandard_CString _Istring37 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.log(101) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring37);
     _Itemp60 = _Pshadow_Pstandard_CMath_Mlog_Rdouble(101);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp60);
     static struct _Pshadow_Pstandard_CString _Istring38 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Math.log(2, 9) = "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring38);
     _Itemp61 = _Pshadow_Pstandard_CMath_Mlog_Rdouble_Rdouble(2, 9);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(_Itemp61);
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CMathTest_MgetClass(struct _Pshadow_Phello_CMathTest* this) {
     return &_Pshadow_Phello_CMathTest_Iclass;
}

struct _Pshadow_Phello_CMathTest_Itable _Pshadow_Phello_CMathTest_Imethods = {
     _Pshadow_Phello_CMathTest_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
     _Pshadow_Phello_CMathTest_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
     _Pshadow_Phello_CMathTest_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
     return 0;
}

