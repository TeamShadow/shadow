
struct _Pshadow_Pstandard_CArray *_Pshadow_Pstandard_CArray_MgetLengths(struct _Pshadow_Pstandard_CArray *this) {
	struct _Pshadow_Pstandard_CArray *lengths = malloc(sizeof(struct _Pshadow_Pstandard_CArray));
	lengths->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	lengths->_Iarray = this->_Ilengths;
	lengths->_Idims = 1;
	*lengths->_Ilengths = this->_Idims;
	return lengths;
}

struct _Pshadow_Pstandard_CObject *_Pshadow_Pstandard_CArray_Msubarray_Rint_Rint_Rint(struct _Pshadow_Pstandard_CArray *this, int_shadow_t offset, int_shadow_t length, int_shadow_t elementSize) {
	struct _Pshadow_Pstandard_CArray *newArray = malloc(sizeof(struct _Pshadow_Pstandard_CArray) + (this->_Idims - 1) * sizeof(int_shadow_t));
	newArray->_Imethods = &_Pshadow_Pstandard_CArray_Imethods;
	newArray->_Iarray = ((char *)this->_Iarray) + offset * elementSize;
	newArray->_Idims = this->_Idims;
	newArray->_Ilengths[0] = length * elementSize;
	int i; for (i = 1; i < this->_Idims; i++)
		newArray->_Ilengths[i] = this->_Ilengths[i];
	return (struct _Pshadow_Pstandard_CObject *)newArray;
}

//struct _Pshadow_Pstandard_CClass *_Pshadow_Pstandard_CArray_MgetBaseClass(struct _Pshadow_Pstandard_CArray *this) {
//	return ((void *)0);
//}

