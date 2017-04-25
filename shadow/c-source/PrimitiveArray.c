/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdlib.h>


ArrayData* shadowArray_GetData(const shadow_PrimitiveArray_t* shadowArray, ArrayData* array)
{
	if(!array) {
		array = malloc(sizeof(ArrayData));
	}
	
	array->size = shadowArray->size;
	array->data = &shadowArray->data[1];
	return array;
}

shadow_PrimitiveArray_t* shadowArray_Create(size_t num, size_t size, void** data)
{
	// we first allocate memory to hold the bare bones of the array
	shadow_PrimitiveArray_t* ret = malloc(sizeof(shadow_PrimitiveArray_t));
	
	// now we allocate the memory needed + 8 bytes for the reference count
	ret->data = calloc(1, sizeof(shadow_ulong_t) + num*size);
	// set the size of the array
	ret->size = num;
	
	// give the user access to the first element of the array, since the first
	// item is the ulong, we jump and skip that.
	*data = &ret->data[1];
	
	return ret;
}

void shadowArray_Free(shadow_PrimitiveArray_t* array)
{
	free(array->data);
	free(array);
}