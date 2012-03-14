/* AUTO-GENERATED FILE, DO NOT EDIT! */
#include "shadow/hello/Arrays.meta"

static struct _Pshadow_Pstandard_CString _Istring0 = {
     &_Pshadow_Pstandard_CString_Imethods,
     (boolean_t)1, (ubyte_t *)"shadow.hello@Arrays"
};
struct _Pshadow_Pstandard_CClass _Pshadow_Phello_CArrays_Iclass = {
     &_Pshadow_Pstandard_CClass_Imethods, &_Istring0
};

void _Pshadow_Phello_CArrays_Mconstructor(struct _Pshadow_Phello_CArrays* this) {
     this->_Imethods = &_Pshadow_Phello_CArrays_Imethods;
}

void _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1(struct _Pshadow_Pstandard_CString** args) {
     boolean_t _Itemp0;
     boolean_t bool;
     _Itemp0 = 1 < 2;
     bool = _Itemp0;
     return;
}

struct _Pshadow_Pstandard_CClass* _Pshadow_Phello_CArrays_MgetClass(struct _Pshadow_Phello_CArrays* this) {
     return &_Pshadow_Phello_CArrays_Iclass;
}

struct _Pshadow_Phello_CArrays_Itable _Pshadow_Phello_CArrays_Imethods = {
     _Pshadow_Phello_CArrays_MgetClass,
     _Pshadow_Pstandard_CObject_MtoString,
     _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1,
};

int main(int argc, char **argv) {
     _Pshadow_Phello_CArrays_Mmain_R_Pshadow_Pstandard_CString_A1((struct _Pshadow_Pstandard_CString **)0);
     return 0;
}

