/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdlib.h>

void _shadow_GetArrayData(const shadow_PrimitiveArray_t*, shadow_int_t*, void**);

void shadow_GetArrayData(const shadow_PrimitiveArray_t* arrayRef, VoidArray* array)
{
	_shadow_GetArrayData(arrayRef, &array->size, &array->data);
}

shadow_PrimitiveArray_t* shadow_CreateArray(size_t num, size_t size, void** data)
{
	__array* ret = malloc(sizeof(__array));
	
	ret->data = calloc(1, sizeof(shadow_ulong_t) + num*size);
	ret->size = num;
	
	*data = &ret->data[1];
	
	return (shadow_PrimitiveArray_t*)ret;
}

void shadow_FreeArray(shadow_PrimitiveArray_t* ref)
{
	__array* array = (__array*)ref;
	free(array->data);
	free(array);
}