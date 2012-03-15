/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Container.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_shadow_t)1, (ubyte_shadow_t *)"shadow.hello@Container"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CContainer_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CContainer_Mconstructor(struct _Pshadow_Phello_CContainer* this) {
     this->_Imethods = &_Pshadow_Phello_CContainer_Imethods;
     this->obj = ((void *)0);
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CContainer_MgetClass(struct _Pshadow_Phello_CContainer* this) {
     return &_Pshadow_Phello_CContainer_Iclass;
}

struct _Pshadow_Phello_CContainer_Itable _Pshadow_Phello_CContainer_Imethods = {
     _Pshadow_Phello_CContainer_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
};

