/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/InputTest.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.hello@InputTest"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CInputTest_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CInputTest_Mconstructor(struct _Pshadow_Phello_CInputTest* this) {
     this->_Imethods = &_Pshadow_Phello_CInputTest_Imethods;
}

void _Pshadow_Phello_CInputTest_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     boolean_t _Itemp1;                                                          /* (14:20) */
     boolean_t _Itemp2;                                                          /* (19:34) */
     int_t _Itemp4;                                                              /* (25:37) */
     int_t _Itemp5;                                                              /* (25:29) */
     static struct _Pshadow_Pstandard_CString _Istring1 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Enter a number: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);       /* (9:24) */
     uint_t _Itemp0;                                                             /* (10:37) */
     _Itemp0 = _Pshadow_Pio_CConsole_MreadUInt();                                /* (10:37) */
     uint_t total;                                                               /* (9:50) */
     total = _Itemp0;                                                            /* (9:50) */
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Enter "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring2);       /* (11:24) */
     _Pshadow_Pio_CConsole_Mprint_Ruint(total);                                  /* (12:24) */
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)" number"
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring3);       /* (13:24) */
     
     if ( total != (uint_t)1 ) {                                                 /* (14:20) */
          _Itemp1 = 1;                                                           /* (14:20) */
     }                                                                           /* (14:20) */
     else {
          _Itemp1 = 0;                                                           /* (14:20) */
     }                                                                           /* (14:20) */
     
     if ( _Itemp1 == 1 ) {                                                       /* (13:41) */
          _Pshadow_Pio_CConsole_Mprint_Rcode('s');                               /* (15:32) */
     }                                                                           /* (13:41) */
     _Pshadow_Pio_CConsole_MprintLine_Rcode(':');                                /* (16:24) */
     int_t sum;                                                                  /* (16:39) */
     int_t product;                                                              /* (16:39) */
     sum = 0;                                                                    /* (16:39) */
     product = 1;                                                                /* (16:39) */
     uint_t i;                                                                   /* (19:21) */
     i = 0u;                                                                     /* (19:21) */
     
     if ( i < total ) {                                                          /* (19:34) */
          _Itemp2 = 1;                                                           /* (19:34) */
     }                                                                           /* (19:34) */
     else {
          _Itemp2 = 0;                                                           /* (19:34) */
     }                                                                           /* (19:34) */
     while ( _Itemp2 == 1 ) {                                                    /* (18:41) */
          int_t _Itemp3;                                                         /* (21:43) */
          _Itemp3 = _Pshadow_Pio_CConsole_MreadInt();                            /* (21:43) */
          int_t next;                                                            /* (20:17) */
          next = _Itemp3;                                                        /* (20:17) */
          sum = sum + next;                                                      /* (21:53) */
          product = product * next;                                              /* (22:36) */
          i = i + 1u;                                                            /* (19:45) */
          
          if ( i < total ) {                                                     /* (19:34) */
               _Itemp2 = 1;                                                      /* (19:34) */
          }                                                                      /* (19:34) */
          else {
               _Itemp2 = 0;                                                      /* (19:34) */
          }                                                                      /* (19:34) */
     } 
     _Itemp4 = 0 + total;                                                        /* (25:37) */
     _Itemp5 = sum / _Itemp4;                                                    /* (25:29) */
     int_t average;                                                              /* (24:17) */
     average = _Itemp5;                                                          /* (24:17) */
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"The sum is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);       /* (27:24) */
     _Pshadow_Pio_CConsole_MprintLine_Rint(sum);                                 /* (28:24) */
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"The average is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);       /* (29:24) */
     _Pshadow_Pio_CConsole_MprintLine_Rint(average);                             /* (30:24) */
     static struct _Pshadow_Pstandard_CString _Istring6 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"The product is: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);       /* (31:24) */
     _Pshadow_Pio_CConsole_MprintLine_Rint(product);                             /* (32:24) */
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

