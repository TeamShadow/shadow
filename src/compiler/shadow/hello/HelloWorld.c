/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/HelloWorld.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.hello@HelloWorld"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CHelloWorld_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

uint_shadow_t _Pshadow_Phello_CHelloWorld_MgetBoth(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString** _Ireturn1) {
     *_Ireturn1 = this->message;
     return this->times;
}

struct _Pshadow_Pstandard_CString* _Pshadow_Phello_CHelloWorld_MgetMessage(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->message;
}

void _Pshadow_Phello_CHelloWorld_Mdoit(struct _Pshadow_Phello_CHelloWorld* this) {
     uint_shadow_t _Itemp0;
     uint_shadow_t _Itemp1;
     boolean_shadow_t _Itemp2;
     uint_shadow_t i;
     i = ((uint_shadow_t)0);
     goto label0;
label1: (void)0;
     _Itemp0 = i + ((uint_shadow_t)1);
     _Pshadow_Pio_CConsole_Mprint_Ruint(_Itemp0);
     static struct _Pshadow_Pstandard_CString _Istring1 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)": "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring1);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(this->message);
     _Itemp1 = i + ((uint_shadow_t)1);
     i = _Itemp1;
     goto label0;
label0: (void)0;
     _Itemp2 = i < this->times;
     if ( _Itemp2 )
          goto label1;
     else
          goto label2;
label2: (void)0;
     _Pshadow_Pio_CConsole_MprintLine();
     return;
}

uint_shadow_t _Pshadow_Phello_CHelloWorld_MgetTimes(struct _Pshadow_Phello_CHelloWorld* this) {
     return this->times;
}

uint_shadow_t _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(uint_shadow_t first, uint_shadow_t second, uint_shadow_t* _Ireturn1, uint_shadow_t* _Ireturn2, uint_shadow_t* _Ireturn3, uint_shadow_t* _Ireturn4, uint_shadow_t* _Ireturn5, uint_shadow_t* _Ireturn6, uint_shadow_t* _Ireturn7, uint_shadow_t* _Ireturn8, struct _Pshadow_Pstandard_CString** _Ireturn9, uint_shadow_t* _Ireturn10, struct _Pshadow_Pstandard_CObject** _Ireturn11) {
     uint_shadow_t _Itemp0;
     uint_shadow_t _Itemp1;
     uint_shadow_t _Itemp2;
     uint_shadow_t _Itemp3;
     uint_shadow_t _Itemp4;
     uint_shadow_t _Itemp5;
     uint_shadow_t _Itemp6;
     uint_shadow_t _Itemp7;
     uint_shadow_t _Itemp8;
     uint_shadow_t _Itemp9;
     struct _Pshadow_Pstandard_CObject* _Itemp10;
     uint_shadow_t third;
     uint_shadow_t fourth;
     uint_shadow_t fifth;
     uint_shadow_t sixth;
     uint_shadow_t seventh;
     uint_shadow_t eighth;
     uint_shadow_t ninth;
     _Itemp0 = first + second;
     third = _Itemp0;
     _Itemp1 = second + third;
     fourth = _Itemp1;
     _Itemp2 = third + fourth;
     fifth = _Itemp2;
     _Itemp3 = fourth + fifth;
     sixth = _Itemp3;
     _Itemp4 = fifth + sixth;
     seventh = _Itemp4;
     _Itemp5 = sixth + seventh;
     eighth = _Itemp5;
     _Itemp6 = seventh + eighth;
     ninth = _Itemp6;
     _Itemp7 = third - second;
     _Itemp8 = first + second;
     _Itemp9 = eighth + ninth;
     _Itemp10 = calloc(1, sizeof(struct _Pshadow_Pstandard_CObject));
     _Pshadow_Pstandard_CObject_Mconstructor(_Itemp10);
     *_Ireturn1 = second;
     *_Ireturn2 = _Itemp8;
     *_Ireturn3 = fourth;
     *_Ireturn4 = fifth;
     *_Ireturn5 = sixth;
     *_Ireturn6 = seventh;
     *_Ireturn7 = eighth;
     *_Ireturn8 = ninth;
     static struct _Pshadow_Pstandard_CString _Istring2 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"String Test"
     };
     *_Ireturn9 = &_Istring2;
     *_Ireturn10 = _Itemp9;
     *_Ireturn11 = _Itemp10;
     return _Itemp7;
}

void _Pshadow_Phello_CHelloWorld_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     boolean_shadow_t _Itemp0;
     uint_shadow_t _Itemp1;
     uint_shadow_t _Itemp2;
     uint_shadow_t _Itemp3;
     uint_shadow_t _Itemp4;
     uint_shadow_t _Itemp5;
     uint_shadow_t _Itemp6;
     uint_shadow_t _Itemp7;
     uint_shadow_t _Itemp8;
     uint_shadow_t _Itemp9;
     struct _Pshadow_Pstandard_CString* _Itemp10;
     uint_shadow_t _Itemp11;
     struct _Pshadow_Pstandard_CObject* _Itemp12;
     uint_shadow_t _Itemp13;
     uint_shadow_t _Itemp14;
     uint_shadow_t _Itemp15;
     boolean_shadow_t _Itemp16;
     struct _Pshadow_Phello_CHelloWorld* _Itemp17;
     uint_shadow_t _Itemp18;
     struct _Pshadow_Pstandard_CString* _Itemp19;
     struct _Pshadow_Phello_CHelloWorld* _Itemp20;
     struct _Pshadow_Phello_CHelloWorld* _Itemp21;
     struct _Pshadow_Phello_CHelloWorld* _Itemp22;
     struct _Pshadow_Pstandard_CString* _Itemp23;
     struct _Pshadow_Pstandard_CString* _Itemp24;
     struct _Pshadow_Pstandard_CString* _Itemp25;
     struct _Pshadow_Pstandard_CString* _Itemp26;
     struct _Pshadow_Pstandard_CString* name;
     uint_shadow_t start;
     uint_shadow_t temp;
     struct _Pshadow_Phello_CHelloWorld* hello1;
     uint_shadow_t defaultTimes;
     struct _Pshadow_Pstandard_CString* defaultMessage;
     struct _Pshadow_Phello_CHelloWorld* hello2;
     struct _Pshadow_Phello_CHelloWorld* hello3;
     struct _Pshadow_Phello_CHelloWorld* hello4;
     static struct _Pshadow_Pstandard_CString _Istring3 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Fibonacci"
     };
     name = &_Istring3;
     start = ((uint_shadow_t)0);
     temp = ((uint_shadow_t)10000000);
     goto label0;
label3: (void)0;
     struct _Pshadow_Pstandard_CObject* last;
     struct _Pshadow_Pstandard_CString* test;
     uint_shadow_t fib0;
     uint_shadow_t fib1;
     uint_shadow_t fib2;
     uint_shadow_t fib3;
     uint_shadow_t fib4;
     uint_shadow_t fib5;
     uint_shadow_t fib6;
     uint_shadow_t fib7;
     uint_shadow_t fib8;
     uint_shadow_t fib9;
     _Itemp0 = start == ((uint_shadow_t)2);
     if ( _Itemp0 )
          goto label1;
     else
          goto label2;
label1: (void)0;
     static struct _Pshadow_Pstandard_CString _Istring4 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Lucas"
     };
     name = &_Istring4;
     goto label2;
label2: (void)0;
     last = ((void *)0);
     test = ((void *)0);
     fib0 = 0;
     fib1 = 0;
     fib2 = 0;
     fib3 = 0;
     fib4 = 0;
     fib5 = 0;
     fib6 = 0;
     fib7 = 0;
     fib8 = 0;
     fib9 = 0;
     _Itemp1 = _Pshadow_Phello_CHelloWorld_Mfibonacci_Ruint_Ruint(start, ((uint_shadow_t)1), &_Itemp2, &_Itemp3, &_Itemp4, &_Itemp5, &_Itemp6, &_Itemp7, &_Itemp8, &_Itemp9, &_Itemp10, &_Itemp11, &_Itemp12);
     fib0 = _Itemp1;
     fib1 = _Itemp2;
     fib2 = _Itemp3;
     fib3 = _Itemp4;
     fib4 = _Itemp5;
     fib5 = _Itemp6;
     fib6 = _Itemp7;
     fib7 = _Itemp8;
     fib8 = _Itemp9;
     test = _Itemp10;
     fib9 = _Itemp11;
     last = _Itemp12;
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring5 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 0: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring5);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib0);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring6 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 1: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring6);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib1);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring7 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 2: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring7);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib2);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring8 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 3: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring8);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib3);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring9 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 4: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring9);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib4);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring10 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 5: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring10);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib5);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring11 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 6: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring11);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib6);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring12 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 7: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring12);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib7);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring13 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 8: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring13);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib8);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring14 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 9: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring14);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(fib9);
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(name);
     static struct _Pshadow_Pstandard_CString _Istring15 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)" 10: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring15);
     _Itemp13 = fib8 + fib9;
     _Pshadow_Pio_CConsole_MprintLine_Ruint(_Itemp13);
     _Pshadow_Pio_CConsole_MprintLine();
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(test);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(last);
     _Pshadow_Pio_CConsole_MprintLine();
     _Itemp14 = start + ((uint_shadow_t)1);
     start = _Itemp14;
     _Itemp15 = temp - ((uint_shadow_t)100);
     temp = _Itemp15;
     goto label0;
label0: (void)0;
     _Itemp16 = start <= ((uint_shadow_t)2);
     if ( _Itemp16 )
          goto label3;
     else
          goto label4;
label4: (void)0;
     _Itemp17 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
     _Pshadow_Phello_CHelloWorld_Mconstructor(_Itemp17);
     hello1 = _Itemp17;
     this->times = ((uint_shadow_t)5);
     static struct _Pshadow_Pstandard_CString _Istring16 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Hello World!"
     };
     this->message = &_Istring16;
     defaultTimes = 0;
     defaultMessage = ((void *)0);
     _Itemp18 = this->_Imethods->_MgetBoth(this, &_Itemp19);
     defaultTimes = _Itemp18;
     defaultMessage = _Itemp19;
     static struct _Pshadow_Pstandard_CString _Istring17 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Default times: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring17);
     _Pshadow_Pio_CConsole_MprintLine_Ruint(defaultTimes);
     static struct _Pshadow_Pstandard_CString _Istring18 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Default message: "
     };
     _Pshadow_Pio_CConsole_Mprint_R_Pshadow_Pstandard_CString(&_Istring18);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(defaultMessage);
     _Pshadow_Pio_CConsole_MprintLine();
     _Itemp20 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(_Itemp20, ((uint_shadow_t)10));
     hello2 = _Itemp20;
     this->times = ((uint_shadow_t)10);
     static struct _Pshadow_Pstandard_CString _Istring19 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Hello World!"
     };
     this->message = &_Istring19;
     _Itemp21 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
     static struct _Pshadow_Pstandard_CString _Istring20 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Five Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(_Itemp21, &_Istring20);
     hello3 = _Itemp21;
     this->times = ((uint_shadow_t)5);
     static struct _Pshadow_Pstandard_CString _Istring21 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Five Times!"
     };
     this->message = &_Istring21;
     _Itemp22 = calloc(1, sizeof(struct _Pshadow_Phello_CHelloWorld));
     static struct _Pshadow_Pstandard_CString _Istring22 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Fifteen Times!"
     };
     _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(_Itemp22, ((uint_shadow_t)15), &_Istring22);
     hello4 = _Itemp22;
     this->times = ((uint_shadow_t)15);
     static struct _Pshadow_Pstandard_CString _Istring23 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Fifteen Times!"
     };
     this->message = &_Istring23;
     this->_Imethods->_Mdoit(this);
     this->_Imethods->_Mdoit(this);
     this->_Imethods->_Mdoit(this);
     this->_Imethods->_Mdoit(this);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello1);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello2);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello3);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CObject(hello4);
     _Pshadow_Pio_CConsole_MprintLine();
     _Itemp23 = this->_Imethods->_MgetMessage(this);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp23);
     _Itemp24 = this->_Imethods->_MgetMessage(this);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp24);
     _Itemp25 = this->_Imethods->_MgetMessage(this);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp25);
     _Itemp26 = this->_Imethods->_MgetMessage(this);
     _Pshadow_Pio_CConsole_MprintLine_R_Pshadow_Pstandard_CString(_Itemp26);
     _Pshadow_Pio_CConsole_MprintLine();
     return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor(struct _Pshadow_Phello_CHelloWorld* this) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);
     this->o1 = ((void *)0);
     this->o2 = ((void *)0);
     this->o3 = ((void *)0);
     this->o4 = ((void *)0);
     this->a = 0;
     this->b = 0;
     this->times = 0;
     this->times = ((uint_shadow_t)5);
     static struct _Pshadow_Pstandard_CString _Istring24 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Hello World!"
     };
     this->message = &_Istring24;
     return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint(struct _Pshadow_Phello_CHelloWorld* this, uint_shadow_t times) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);
     this->o1 = ((void *)0);
     this->o2 = ((void *)0);
     this->o3 = ((void *)0);
     this->o4 = ((void *)0);
     this->a = 0;
     this->b = 0;
     this->times = 0;
     this->times = times;
     static struct _Pshadow_Pstandard_CString _Istring25 = {
          &_Pshadow_Pstandard_CString_Imethods,
          (boolean_shadow_t)1, (ubyte_shadow_t *)"Hello World!"
     };
     this->message = &_Istring25;
     return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, struct _Pshadow_Pstandard_CString* message) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);
     this->o1 = ((void *)0);
     this->o2 = ((void *)0);
     this->o3 = ((void *)0);
     this->o4 = ((void *)0);
     this->a = 0;
     this->b = 0;
     this->times = 0;
     this->times = ((uint_shadow_t)5);
     this->message = message;
     return;
}

void _Pshadow_Phello_CHelloWorld_Mconstructor_Ruint_R_Pshadow_Pstandard_CString(struct _Pshadow_Phello_CHelloWorld* this, uint_shadow_t times, struct _Pshadow_Pstandard_CString* message) {
     this->_Imethods = &_Pshadow_Phello_CHelloWorld_Imethods;
     this->message = ((void *)0);
     this->o1 = ((void *)0);
     this->o2 = ((void *)0);
     this->o3 = ((void *)0);
     this->o4 = ((void *)0);
     this->a = 0;
     this->b = 0;
     this->times = 0;
     this->times = times;
     this->message = message;
     return;
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

