/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Object.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.standard@Object"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CObject_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

struct _Pshadow_Pstandard_CString* _Pshadow_Pstandard_CObject_MtoString(struct _Pshadow_Pstandard_CObject* this) {
     struct _Pshadow_Pstandard_CClass* _Itemp0;                                  /* (10:17) */
     _Itemp0 = this->_Imethods->_MgetClass(this);                                /* (10:17) */
     struct _Pshadow_Pstandard_CString* _Itemp1;                                 /* (10:34) */
     _Itemp1 = _Itemp0->_Imethods->_MtoString(_Itemp0);                          /* (10:34) */
     return _Itemp1;                                                             /* (9:9) */
}

void _Pshadow_Pstandard_CObject_Mconstructor(struct _Pshadow_Pstandard_CObject* this) {
     this->_Imethods = &_Pshadow_Pstandard_CObject_Imethods;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pstandard_CObject_MgetClass(struct _Pshadow_Pstandard_CObject* this) {
     return &_Pshadow_Pstandard_CObject_Iclass;
}

struct _Pshadow_Pstandard_CObject_Itable _Pshadow_Pstandard_CObject_Imethods = {
     _Pshadow_Pstandard_CObject_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
};

