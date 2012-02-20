/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/HelloWorld.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.hello@HelloWorld"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CHelloWorld_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

struct _Pshadow_Pstandard_CString* _Pshadow_Phello_CHelloWorld_MgetMessage(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->message;                                                       /* (35:9) */
}

void _Pshadow_Phello_CHelloWorld_Mdoit(struct _Pshadow_Phello_CHelloWorld* this) {
     boolean_t temp_0;                                                           /* (41:34) */
     uint_t i;                                                                   /* (41:21) */
     i = (uint_t)0;                                                              /* (41:21) */
     
     if ( i < this->times ) {                                                    /* (41:34) */
          temp_0 = 1;                                                            /* (41:34) */
     }                                                                           /* (41:34) */
     else {
          temp_0 = 0;                                                            /* (41:34) */
     }                                                                           /* (41:34) */
     while ( temp_0 == 1 ) {                                                     /* (40:9) */
          uint_t temp_1;                                                         /* (43:38) */
          temp_1 = i + (uint_t)1;                                                /* (43:38) */
          _Pshadow_Pio_CConsole_Mprint_Ruint(temp_1);                            /* (43:32) */
          static struct _Pshadow_Pstandard_CString _Istring1 = {
               &_Pshadow_Pstandard_CString_Imethods,
               (boolean_t)1, (ubyte_t *)": "
          };
          _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);  /* (44:32) */
          _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(this->message); /* (45:32) */
          i = i + (uint_t)1;                                                     /* (41:45) */
          
          if ( i < this->times ) {                                               /* (41:34) */
               temp_0 = 1;                                                       /* (41:34) */
          }                                                                      /* (41:34) */
          else {
               temp_0 = 0;                                                       /* (41:34) */
          }                                                                      /* (41:34) */
     } 
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (47:24) */
}

uint_t _Pshadow_Phello_CHelloWorld_MgetTimes(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->times;                                                         /* (31:9) */
}

void _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     struct _Pshadow_Phello_CHelloWorld* temp_2;                                 /* (52:35) */
     temp_2 = (struct _Pshadow_Phello_CHelloWorld*)calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld)); /* (52:35) */
     _Pshadow_Phello_CHelloWorld_Mconstructor(temp_2);                           /* (52:35) */
     struct _Pshadow_Phello_CHelloWorld* hello1;                                 /* (51:9) */
     hello1 = temp_2;                                                            /* (51:9) */
     struct _Pshadow_Phello_CHelloWorld* temp_3;                                 /* (53:35) */
     temp_3 = (struct _Pshadow_Phello_CHelloWorld*)calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld)); /* (53:35) */
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(temp_3, (uint_t)10);         /* (53:35) */
     struct _Pshadow_Phello_CHelloWorld* hello2;                                 /* (52:53) */
     hello2 = temp_3;                                                            /* (52:53) */
     struct _Pshadow_Phello_CHelloWorld* temp_4;                                 /* (54:35) */
     temp_4 = (struct _Pshadow_Phello_CHelloWorld*)calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld)); /* (54:35) */
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Five Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(temp_4, &_Istring2); /* (54:35) */
     struct _Pshadow_Phello_CHelloWorld* hello3;                                 /* (53:57) */
     hello3 = temp_4;                                                            /* (53:57) */
     struct _Pshadow_Phello_CHelloWorld* temp_5;                                 /* (55:35) */
     temp_5 = (struct _Pshadow_Phello_CHelloWorld*)calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld)); /* (55:35) */
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Fifteen Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(temp_5, (uint_t)15, &_Istring3); /* (55:35) */
     struct _Pshadow_Phello_CHelloWorld* hello4;                                 /* (54:66) */
     hello4 = temp_5;                                                            /* (54:66) */
     hello1->_Imethods->_Mdoit(hello1);                                          /* (56:23) */
     hello2->_Imethods->_Mdoit(hello2);                                          /* (57:23) */
     hello3->_Imethods->_Mdoit(hello3);                                          /* (58:23) */
     hello4->_Imethods->_Mdoit(hello4);                                          /* (59:23) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello1); /* (60:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello2); /* (61:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello3); /* (62:24) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject((struct _Pshadow_Pstandard_CObject*)hello4); /* (63:24) */
     _Pshadow_Pio_CConsole_MprintLine();                                         /* (64:24) */
     struct _Pshadow_Pstandard_CString* temp_6;                                  /* (65:41) */
     temp_6 = hello1->_Imethods->_MgetMessage(hello1);                           /* (65:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(temp_6);       /* (65:24) */
     struct _Pshadow_Pstandard_CString* temp_7;                                  /* (66:41) */
     temp_7 = hello2->_Imethods->_MgetMessage(hello2);                           /* (66:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(temp_7);       /* (66:24) */
     struct _Pshadow_Pstandard_CString* temp_8;                                  /* (67:41) */
     temp_8 = hello3->_Imethods->_MgetMessage(hello3);                           /* (67:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(temp_8);       /* (67:24) */
     struct _Pshadow_Pstandard_CString* temp_9;                                  /* (68:41) */
     temp_9 = hello4->_Imethods->_MgetMessage(hello4);                           /* (68:41) */
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(temp_9);       /* (68:24) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor(struct _Pshadow_Phello_CHelloWorld* this) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = (uint_t)5;                                                    /* (10:9) */
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Hello World!"
     };
     this->message = &_Istring4;                                                 /* (11:28) */
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(struct _Pshadow_Phello_CHelloWorld* this, uint_t times) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);                                                /* (8:9) */
     this->times = 0;                                                            /* (7:9) */
     this->times = times;                                                        /* (15:9) */
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_t)1, (ubyte_t *)"Hello World!"
     };
     this->message = &_Istring5;                                                 /* (16:35) */
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
     _Pshadow_Phello_CHelloWorld_MgetMessage,
     _Pshadow_Phello_CHelloWorld_Mdoit,
     _Pshadow_Phello_CHelloWorld_MgetTimes,
     _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
     _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
     return 0;
}

