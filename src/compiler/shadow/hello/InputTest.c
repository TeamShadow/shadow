/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/InputTest.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.hello@InputTest"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CInputTest_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CInputTest_Mconstructor(struct _Pshadow_Phello_CInputTest* this) {
     this->_Imethods = &_Pshadow_Phello_CInputTest_Imethods;
     return;
}

void _Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     uint_shadow_t _Itemp0;
     boolean_shadow_t _Itemp1;
     int_shadow_t _Itemp2;
     int_shadow_t _Itemp3;
     int_shadow_t _Itemp4;
     uint_shadow_t _Itemp5;
     boolean_shadow_t _Itemp6;
     double_shadow_t _Itemp7;
     int_shadow_t _Itemp8;
     double_shadow_t _Itemp9;
     uint_shadow_t total;
     int_shadow_t sum;
     int_shadow_t product;
     uint_shadow_t i;
     double_shadow_t average;
     static struct _Pshadow_Pstandard_CString _Istring1 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Enter a number: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
     _Itemp0 = _Pshadow_Pio_CConsole_MreadUInt();
     total = _Itemp0;
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Enter "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring2);
     _Pshadow_Pio_CConsole_Mprint_Ruint(total);
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" number"
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring3);
     _Itemp1 = total != ((uint_shadow_t)1);
     if ( _Itemp1 )
          goto label0;
     else
          goto label1;
label0: (void)0;
     _Pshadow_Pio_CConsole_Mprint_Rcode('s');
     goto label1;
label1: (void)0;
     _Pshadow_Pio_CConsole_MprintLine_Rcode(':');
     sum = 0;
     product = 1;
     i = 0u;
     goto label2;
label3: (void)0;
     int_shadow_t next;
     _Itemp2 = _Pshadow_Pio_CConsole_MreadInt();
     next = _Itemp2;
     _Itemp3 = sum + next;
     sum = _Itemp3;
     _Itemp4 = product * next;
     product = _Itemp4;
     _Itemp5 = i + 1u;
     i = _Itemp5;
     goto label2;
label2: (void)0;
     _Itemp6 = i < total;
     if ( _Itemp6 )
          goto label3;
     else
          goto label4;
label4: (void)0;
     _Itemp7 = (double_shadow_t)sum;
     _Itemp8 = (int_shadow_t)total;
     _Itemp9 = _Itemp7 / _Itemp8;
     average = _Itemp9;
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"The sum is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);
     _Pshadow_Pio_CConsole_MprintLine_Rint(sum);
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"The average is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
     _Pshadow_Pio_CConsole_MprintLine_Rdouble(average);
     static struct _Pshadow_Pstandard_CString _Istring6 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"The product is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
     _Pshadow_Pio_CConsole_MprintLine_Rint(product);
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CInputTest_MgetClass(struct _Pshadow_Phello_CInputTest* this) {
     return &_Pshadow_Phello_CInputTest_Iclass;
}

struct _Pshadow_Phello_CInputTest_Itable _Pshadow_Phello_CInputTest_Imethods = {
     _Pshadow_Phello_CInputTest_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
     _Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
     _Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
     return 0;
}

