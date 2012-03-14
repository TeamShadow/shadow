/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/standard/Class.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.standard@Class"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Pstandard_CClass_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

struct _Pshadow_Pstandard_CString* _Pshadow_Pstandard_CClass_MtoString(struct _Pshadow_Pstandard_CClass* this) {
     return this->className;
}

void _Pshadow_Pstandard_CClass_Mconstructor(struct _Pshadow_Pstandard_CClass* this) {
     this->_Imethods = &_Pshadow_Pstandard_CClass_Imethods;
     this->className = ((void *)0);
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Pstandard_CClass_MgetClass(struct _Pshadow_Pstandard_CClass* this) {
     return &_Pshadow_Pstandard_CClass_Iclass;
}

struct _Pshadow_Pstandard_CClass_Itable _Pshadow_Pstandard_CClass_Imethods = {
     _Pshadow_Pstandard_CClass_MgetClass,
     _Pshadow_Pstandard_CClass_MtoString,
};

