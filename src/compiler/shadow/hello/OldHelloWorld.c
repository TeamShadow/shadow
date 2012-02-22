/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/OldHelloWorld.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.hello@OldHelloWorld"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_COldHelloWorld_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint(uint_t start, uint_t end) {
     boolean_t _Itemp0;                                                          /* (47:36) */
     uint_t i;                                                                   /* (47:21) */
     i = start;                                                                  /* (47:21) */
     
     if ( i <= end ) {                                                           /* (47:36) */
          _Itemp0 = 1;                                                           /* (47:36) */
     }                                                                           /* (47:36) */
     else {
          _Itemp0 = 0;                                                           /* (47:36) */
     }                                                                           /* (47:36) */
     while ( _Itemp0 == 1 ) {                                                    /* (46:9) */
          _Pshadow_Pio_CConsole_Mprint_Ruint(i);                                 /* (48:32) */
          static struct _Pshadow_Pstandard_CString _Istring1 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)": "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);  /* (49:32) */
          uint_t _Itemp1;                                                        /* (50:42) */
          _Itemp1 = _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(i);          /* (50:42) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp1);                       /* (50:32) */
          i = i + 1u;                                                            /* (47:46) */
          
          if ( i <= end ) {                                                      /* (47:36) */
               _Itemp0 = 1;                                                      /* (47:36) */
          }                                                                      /* (47:36) */
          else {
               _Itemp0 = 0;                                                      /* (47:36) */
          }                                                                      /* (47:36) */
     } 
}

uint_t _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(uint_t a) {
     boolean_t _Itemp2;                                                          /* (55:20) */
     boolean_t _Itemp3;                                                          /* (55:29) */
     boolean_t _Itemp4;                                                          /* (55:20) */
     uint_t _Itemp5;                                                             /* (56:33) */
     uint_t _Itemp7;                                                             /* (56:17) */
     _Itemp4 = 1;                                                                /* (55:20) */
     
     if ( a == 1u ) {                                                            /* (55:20) */
          _Itemp2 = 1;                                                           /* (55:20) */
     }                                                                           /* (55:20) */
     else {
          _Itemp2 = 0;                                                           /* (55:20) */
     }                                                                           /* (55:20) */
     
     if ( _Itemp2 == 1 ) {                                                       /* (55:20) */
     }                                                                           /* (55:20) */
     else {
          
          if ( a == 0u ) {                                                       /* (55:29) */
               _Itemp3 = 1;                                                      /* (55:29) */
          }                                                                      /* (55:29) */
          else {
               _Itemp3 = 0;                                                      /* (55:29) */
          }                                                                      /* (55:29) */
          
          if ( _Itemp3 == 1 ) {                                                  /* (55:20) */
          }                                                                      /* (55:20) */
          else {
               _Itemp4 = 0;                                                      /* (55:20) */
          }                                                                      /* (55:20) */
     }                                                                           /* (55:20) */
     
     if ( _Itemp4 == 1 ) {                                                       /* (54:51) */
          return 1u;                                                             /* (55:39) */
     }                                                                           /* (54:51) */
     uint_t _Itemp6;                                                             /* (56:17) */
     _Itemp5 = a - 1u;                                                           /* (56:33) */
     _Itemp6 = _Pshadow_Phello_COldHelloWorld_Mfactorial_Ruint(_Itemp5);         /* (56:17) */
     _Itemp7 = _Itemp6 * a;                                                      /* (56:17) */
     return _Itemp7;                                                             /* (55:50) */
}

void _Pshadow_Phello_COldHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Hello World!"
     };
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring2);   /* (31:24) */
     _Pshadow_Phello_COldHelloWorld_Mtest_Ruint_Ruint(0u, 10u);                  /* (31:50) */
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor(struct _Pshadow_Phello_COldHelloWorld* this) {
     this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"object Hello World initialized with no arguments."
     };
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring3);   /* (10:24) */
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_t a) {
     this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"object Hello World initialized with one argument: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring4);       /* (15:24) */
     _Pshadow_Pio_CConsole_Mprint_Rint(a);                                       /* (16:24) */
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"."
     };
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring5);   /* (17:24) */
}

void _Pshadow_Phello_COldHelloWorld_Mconstructor_Rint_Rint(struct _Pshadow_Phello_COldHelloWorld* this, int_t a, int_t b) {
     this->_Imethods = &_Pshadow_Phello_COldHelloWorld_Imethods;
     static struct _Pshadow_Pstandard_CString _Istring6 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"object Hello World initialized with two arguments: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);       /* (22:24) */
     _Pshadow_Pio_CConsole_Mprint_Rint(a);                                       /* (23:24) */
     static struct _Pshadow_Pstandard_CString _Istring7 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)" and "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);       /* (24:24) */
     _Pshadow_Pio_CConsole_Mprint_Rint(b);                                       /* (25:24) */
     static struct _Pshadow_Pstandard_CString _Istring8 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"."
     };
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(&_Istring8);   /* (26:24) */
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

