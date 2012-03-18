
struct _IArray *_Pshadow_Pstandard_CArray_MgetLengths_R_Pshadow_Pstandard_CObject(struct _Pshadow_Pstandard_CObject *arrayObject) {
	struct _IArray *array = (struct _IArray *)arrayObject;
	struct _IArray *lengths = (struct _IArray *)malloc(sizeof(struct _IArray) + sizeof(int_shadow_t));
	lengths->_Iarray = (void *)array->_Ilengths;
	lengths->_Idims = 1;
	lengths->_Ilengths[0] = array->_Idims;
	return lengths;
}
