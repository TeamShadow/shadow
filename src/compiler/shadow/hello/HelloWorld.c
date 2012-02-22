/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/HelloWorld.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.hello@HelloWorld"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CHelloWorld_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

uint_t _Pshadow_Phello_CHelloWorld_MgetBoth(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString** _Ireturn1) {
     (*_Ireturn1) = this->message;                                               /* (39:9) */
     return this->times;                                                         /* (39:9) */
}

struct _Pshadow_Pstandard_CString* _Pshadow_Phello_CHelloWorld_MgetMessage(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->message;                                                       /* (35:9) */
}

void _Pshadow_Phello_CHelloWorld_Mdoit(struct _Pshadow_Phello_CHelloWorld* this) {
     boolean_t _Itemp0;                                                          /* (45:34) */
     uint_t i;                                                                   /* (45:21) */
     i = (uint_t)0;                                                              /* (45:21) */
     
     if ( i < this->times ) {                                                    /* (45:34) */
          _Itemp0 = 1;                                                           /* (45:34) */
     }                                                                           /* (45:34) */
     else {
          _Itemp0 = 0;                                                           /* (45:34) */
     }                                                                           /* (45:34) */
     while ( _Itemp0 == 1 ) {                                                    /* (44:9) */
          uint_t _Itemp1;                                                        /* (47:38) */
          _Itemp1 = i + (uint_t)1;                                               /* (47:38) */
          _Pshadow_Pio_CConsole_Mprint_Ruint(_Itemp1);                           /* (47:32) */
          static struct _Pshadow_Pstandard_CString _Istring1 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)": "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);  /* (48:32) */
          _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(this->message); /* (49:32) */
          i = i + (uint_t)1;                                                     /* (45:45) */
          
          if ( i < this->times ) {                                               /* (45:34) */
               _Itemp0 = 1;                                                      /* (45:34) */
          }                                                                      /* (45:34) */
          else {
               _Itemp0 = 0;                                                      /* (45:34) */
          }                                                                      /* (45:34) */
     } 
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (51:24) */
}

uint_t _Pshadow_Phello_CHelloWorld_MgetTimes(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->times;                                                         /* (31:9) */
}

uint_t _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(uint_t first, uint_t second, uint_t* _Ireturn1, uint_t* _Ireturn2, uint_t* _Ireturn3, uint_t* _Ireturn4, uint_t* _Ireturn5, uint_t* _Ireturn6, uint_t* _Ireturn7, uint_t* _Ireturn8, struct _Pshadow_Pstandard_CString** _Ireturn9, uint_t* _Ireturn10, struct _Pshadow_Pstandard_CObject** _Ireturn11) {
     uint_t _Itemp2;                                                             /* (56:28) */
     uint_t _Itemp3;                                                             /* (57:29) */
     uint_t _Itemp4;                                                             /* (58:28) */
     uint_t _Itemp5;                                                             /* (59:28) */
     uint_t _Itemp6;                                                             /* (60:30) */
     uint_t _Itemp7;                                                             /* (61:29) */
     uint_t _Itemp8;                                                             /* (62:28) */
     uint_t _Itemp9;                                                             /* (63:24) */
     uint_t _Itemp10;                                                            /* (63:48) */
     uint_t _Itemp11;                                                            /* (63:125) */
     _Itemp2 = first + second;                                                   /* (56:28) */
     uint_t third;                                                               /* (55:9) */
     third = _Itemp2;                                                            /* (55:9) */
     _Itemp3 = second + third;                                                   /* (57:29) */
     uint_t fourth;                                                              /* (56:44) */
     fourth = _Itemp3;                                                           /* (56:44) */
     _Itemp4 = third + fourth;                                                   /* (58:28) */
     uint_t fifth;                                                               /* (57:45) */
     fifth = _Itemp4;                                                            /* (57:45) */
     _Itemp5 = fourth + fifth;                                                   /* (59:28) */
     uint_t sixth;                                                               /* (58:44) */
     sixth = _Itemp5;                                                            /* (58:44) */
     _Itemp6 = fifth + sixth;                                                    /* (60:30) */
     uint_t seventh;                                                             /* (59:44) */
     seventh = _Itemp6;                                                          /* (59:44) */
     _Itemp7 = sixth + seventh;                                                  /* (61:29) */
     uint_t eighth;                                                              /* (60:45) */
     eighth = _Itemp7;                                                           /* (60:45) */
     _Itemp8 = seventh + eighth;                                                 /* (62:28) */
     uint_t ninth;                                                               /* (61:46) */
     ninth = _Itemp8;                                                            /* (61:46) */
     _Itemp9 = third - second;                                                   /* (63:24) */
     _Itemp10 = first + second;                                                  /* (63:48) */
     _Itemp11 = eighth + ninth;                                                  /* (63:125) */
     struct _Pshadow_Pstandard_CObject* _Itemp12;                                /* (63:141) */
     _Itemp12 = calloc(1, sizeof(struct _Pshadow_Pstandard_CObject));            /* (63:141) */
     _Pshadow_Pstandard_CObject_Mconstructor(_Itemp12);                          /* (63:141) */
     (*_Ireturn1) = second;                                                      /* (62:46) */
     (*_Ireturn2) = _Itemp10;                                                    /* (62:46) */
     (*_Ireturn3) = fourth;                                                      /* (62:46) */
     (*_Ireturn4) = fifth;                                                       /* (62:46) */
     (*_Ireturn5) = sixth;                                                       /* (62:46) */
     (*_Ireturn6) = seventh;                                                     /* (62:46) */
     (*_Ireturn7) = eighth;                                                      /* (62:46) */
     (*_Ireturn8) = ninth;                                                       /* (62:46) */
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"String Test"
     };
     (*_Ireturn9) = &_Istring2;                                                  /* (62:46) */
     (*_Ireturn10) = _Itemp11;                                                   /* (62:46) */
     (*_Ireturn11) = _Itemp12;                                                   /* (62:46) */
     return _Itemp9;                                                             /* (62:46) */
}

void _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     boolean_t _Itemp13;                                                         /* (70:39) */
     struct _Pshadow_Pstandard_CString* name;                                    /* (67:9) */
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Fibonacci"
     };
     name = &_Istring3;                                                          /* (67:9) */
     uint_t start;                                                               /* (70:21) */
     start = (uint_t)0;                                                          /* (70:21) */
     
     if ( start <= (uint_t)2 ) {                                                 /* (70:39) */
          _Itemp13 = 1;                                                          /* (70:39) */
     }                                                                           /* (70:39) */
     else {
          _Itemp13 = 0;                                                          /* (70:39) */
     }                                                                           /* (70:39) */
     while ( _Itemp13 == 1 ) {                                                   /* (69:42) */
          boolean_t _Itemp14;                                                    /* (72:28) */
          uint_t _Itemp27;                                                       /* (110:42) */
          
          if ( start == (uint_t)2 ) {                                            /* (72:28) */
               _Itemp14 = 1;                                                     /* (72:28) */
          }                                                                      /* (72:28) */
          else {
               _Itemp14 = 0;                                                     /* (72:28) */
          }                                                                      /* (72:28) */
          
          if ( _Itemp14 == 1 ) {                                                 /* (71:17) */
               static struct _Pshadow_Pstandard_CString _Istring4 = {
                    &_Pshadow_Pstandard_CString_Imethods,
                    (boolean_t)1, (ubyte_t *)"Lucas"
               };
               name = &_Istring4;                                                /* (72:43) */
          }                                                                      /* (71:17) */
          struct _Pshadow_Pstandard_CObject* last;                               /* (73:47) */
          last = ((void *)0);                                                    /* (73:47) */
          struct _Pshadow_Pstandard_CString* test;                               /* (74:36) */
          test = ((void *)0);                                                    /* (74:36) */
          uint_t fib0;                                                           /* (75:36) */
          uint_t fib1;                                                           /* (75:36) */
          uint_t fib2;                                                           /* (75:36) */
          uint_t fib3;                                                           /* (75:36) */
          uint_t fib4;                                                           /* (75:36) */
          uint_t fib5;                                                           /* (75:36) */
          uint_t fib6;                                                           /* (75:36) */
          uint_t fib7;                                                           /* (75:36) */
          uint_t fib8;                                                           /* (75:36) */
          uint_t fib9;                                                           /* (75:36) */
          fib0 = 0;                                                              /* (75:36) */
          fib1 = 0;                                                              /* (75:36) */
          fib2 = 0;                                                              /* (75:36) */
          fib3 = 0;                                                              /* (75:36) */
          fib4 = 0;                                                              /* (75:36) */
          fib5 = 0;                                                              /* (75:36) */
          fib6 = 0;                                                              /* (75:36) */
          fib7 = 0;                                                              /* (75:36) */
          fib8 = 0;                                                              /* (75:36) */
          fib9 = 0;                                                              /* (75:36) */
          uint_t _Itemp15;                                                       /* (77:98) */
          uint_t _Itemp16;                                                       /* (77:98) */
          uint_t _Itemp17;                                                       /* (77:98) */
          uint_t _Itemp18;                                                       /* (77:98) */
          uint_t _Itemp19;                                                       /* (77:98) */
          uint_t _Itemp20;                                                       /* (77:98) */
          uint_t _Itemp21;                                                       /* (77:98) */
          uint_t _Itemp22;                                                       /* (77:98) */
          uint_t _Itemp23;                                                       /* (77:98) */
          struct _Pshadow_Pstandard_CString* _Itemp24;                           /* (77:98) */
          uint_t _Itemp25;                                                       /* (77:98) */
          struct _Pshadow_Pstandard_CObject* _Itemp26;                           /* (77:98) */
          _Itemp15 = _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(start, (uint_t)1, &_Itemp16, &_Itemp17, &_Itemp18, &_Itemp19, &_Itemp20, &_Itemp21, &_Itemp22, &_Itemp23, &_Itemp24, &_Itemp25, &_Itemp26); /* (77:98) */
          fib0 = _Itemp15;                                                       /* (76:88) */
          fib1 = _Itemp16;                                                       /* (76:88) */
          fib2 = _Itemp17;                                                       /* (76:88) */
          fib3 = _Itemp18;                                                       /* (76:88) */
          fib4 = _Itemp19;                                                       /* (76:88) */
          fib5 = _Itemp20;                                                       /* (76:88) */
          fib6 = _Itemp21;                                                       /* (76:88) */
          fib7 = _Itemp22;                                                       /* (76:88) */
          fib8 = _Itemp23;                                                       /* (76:88) */
          test = _Itemp24;                                                       /* (76:88) */
          fib9 = _Itemp25;                                                       /* (76:88) */
          last = _Itemp26;                                                       /* (76:88) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (78:32) */
          static struct _Pshadow_Pstandard_CString _Istring5 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 0: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);  /* (79:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib0);                          /* (80:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (81:32) */
          static struct _Pshadow_Pstandard_CString _Istring6 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 1: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);  /* (82:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib1);                          /* (83:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (84:32) */
          static struct _Pshadow_Pstandard_CString _Istring7 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 2: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);  /* (85:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib2);                          /* (86:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (87:32) */
          static struct _Pshadow_Pstandard_CString _Istring8 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 3: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring8);  /* (88:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib3);                          /* (89:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (90:32) */
          static struct _Pshadow_Pstandard_CString _Istring9 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 4: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring9);  /* (91:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib4);                          /* (92:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (93:32) */
          static struct _Pshadow_Pstandard_CString _Istring10 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 5: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring10); /* (94:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib5);                          /* (95:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (96:32) */
          static struct _Pshadow_Pstandard_CString _Istring11 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 6: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring11); /* (97:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib6);                          /* (98:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (99:32) */
          static struct _Pshadow_Pstandard_CString _Istring12 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 7: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring12); /* (100:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib7);                          /* (101:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (102:32) */
          static struct _Pshadow_Pstandard_CString _Istring13 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 8: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring13); /* (103:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib8);                          /* (104:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (105:32) */
          static struct _Pshadow_Pstandard_CString _Istring14 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 9: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring14); /* (106:32) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(fib9);                          /* (107:32) */
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);        /* (108:32) */
          static struct _Pshadow_Pstandard_CString _Istring15 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)" 10: "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring15); /* (109:32) */
          _Itemp27 = fib8 + fib9;                                                /* (110:42) */
          _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp27);                      /* (110:32) */
          _Pshadow_Pio_CConsole_MprintLine();                                    /* (111:32) */
          _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(test);    /* (112:32) */
          _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(last);    /* (113:32) */
          _Pshadow_Pio_CConsole_MprintLine();                                    /* (114:32) */
          start = start + (uint_t)1;                                             /* (70:53) */
          
          if ( start <= (uint_t)2 ) {                                            /* (70:39) */
               _Itemp13 = 1;                                                     /* (70:39) */
          }                                                                      /* (70:39) */
          else {
               _Itemp13 = 0;                                                     /* (70:39) */
          }                                                                      /* (70:39) */
     } 
     struct _Pshadow_Phello_CHelloWorld* _Itemp28;                               /* (117:35) */
     _Itemp28 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));           /* (117:35) */
     _Pshadow_Phello_CHelloWorld_Mconstructor(_Itemp28);                         /* (117:35) */
     struct _Pshadow_Phello_CHelloWorld* hello1;                                 /* (115:17) */
     hello1 = _Itemp28;                                                          /* (115:17) */
     uint_t defaultTimes;                                                        /* (117:53) */
     defaultTimes = 0;                                                           /* (117:53) */
     struct _Pshadow_Pstandard_CString* defaultMessage;                          /* (119:34) */
     defaultMessage = ((void *)0);                                               /* (119:34) */
     uint_t _Itemp29;                                                            /* (121:56) */
     struct _Pshadow_Pstandard_CString* _Itemp30;                                /* (121:56) */
     _Itemp29 = hello1->_Imethods->_MgetBoth(hello1, &_Itemp30);                 /* (121:56) */
     defaultTimes = _Itemp29;                                                    /* (120:38) */
     defaultMessage = _Itemp30;                                                  /* (120:38) */
     static struct _Pshadow_Pstandard_CString _Istring16 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Default times: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring16);      /* (122:24) */
     _Pshadow_Pio_CConsole_MprintLine_Ruint(defaultTimes);                       /* (123:24) */
     static struct _Pshadow_Pstandard_CString _Istring17 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Default message: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring17);      /* (124:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(defaultMessage); /* (125:24) */
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (126:24) */
     struct _Pshadow_Phello_CHelloWorld* _Itemp31;                               /* (128:35) */
     _Itemp31 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));           /* (128:35) */
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(_Itemp31, (uint_t)10);       /* (128:35) */
     struct _Pshadow_Phello_CHelloWorld* hello2;                                 /* (126:36) */
     hello2 = _Itemp31;                                                          /* (126:36) */
     struct _Pshadow_Phello_CHelloWorld* _Itemp32;                               /* (129:35) */
     _Itemp32 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));           /* (129:35) */
     static struct _Pshadow_Pstandard_CString _Istring18 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Five Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(_Itemp32, &_Istring18); /* (129:35) */
     struct _Pshadow_Phello_CHelloWorld* hello3;                                 /* (128:57) */
     hello3 = _Itemp32;                                                          /* (128:57) */
     struct _Pshadow_Phello_CHelloWorld* _Itemp33;                               /* (130:35) */
     _Itemp33 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));           /* (130:35) */
     static struct _Pshadow_Pstandard_CString _Istring19 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Fifteen Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(_Itemp33, (uint_t)15, &_Istring19); /* (130:35) */
     struct _Pshadow_Phello_CHelloWorld* hello4;                                 /* (129:66) */
     hello4 = _Itemp33;                                                          /* (129:66) */
     hello1->_Imethods->_Mdoit(hello1);                                          /* (132:23) */
     hello2->_Imethods->_Mdoit(hello2);                                          /* (133:23) */
     hello3->_Imethods->_Mdoit(hello3);                                          /* (134:23) */
     hello4->_Imethods->_Mdoit(hello4);                                          /* (135:23) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello1); /* (137:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello2); /* (138:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello3); /* (139:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello4); /* (140:24) */
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (141:24) */
     struct _Pshadow_Pstandard_CString* _Itemp34;                                /* (143:41) */
     _Itemp34 = hello1->_Imethods->_MgetMessage(hello1);                         /* (143:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp34);     /* (143:24) */
     struct _Pshadow_Pstandard_CString* _Itemp35;                                /* (144:41) */
     _Itemp35 = hello2->_Imethods->_MgetMessage(hello2);                         /* (144:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp35);     /* (144:24) */
     struct _Pshadow_Pstandard_CString* _Itemp36;                                /* (145:41) */
     _Itemp36 = hello3->_Imethods->_MgetMessage(hello3);                         /* (145:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp36);     /* (145:24) */
     struct _Pshadow_Pstandard_CString* _Itemp37;                                /* (146:41) */
     _Itemp37 = hello4->_Imethods->_MgetMessage(hello4);                         /* (146:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp37);     /* (146:24) */
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (147:24) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor(struct _Pshadow_Phello_CHelloWorld* this) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = (uint_t)5;                                                    /* (10:9) */
     static struct _Pshadow_Pstandard_CString _Istring20 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Hello World!"
     };
     this->message = &_Istring20;                                                /* (11:28) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(struct _Pshadow_Phello_CHelloWorld* this, uint_t times) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = times;                                                        /* (15:9) */
     static struct _Pshadow_Pstandard_CString _Istring21 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Hello World!"
     };
     this->message = &_Istring21;                                                /* (16:35) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString* message) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = (uint_t)5;                                                    /* (20:9) */
     this->message = message;                                                    /* (21:28) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, uint_t times, struct _Pshadow_Pstandard_CString* message) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = times;                                                        /* (25:9) */
     this->message = message;                                                    /* (26:35) */
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
     _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
     return 0;
}

