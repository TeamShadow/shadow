/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdlib.h>
#include <__PrimitiveArrayType.h>

void _shadow_GetArrayData(const shadow_PrimitiveArray_t*, shadow_int_t*, void**);

void shadow_GetArrayData(const shadow_PrimitiveArray_t* arrayRef, VoidArray* array)
{
	_shadow_GetArrayData(arrayRef, &array->size, &array->data);
}

shadow_PrimitiveArray_t* shadow_CreateArray(size_t num, size_t size, void** data)
{
	__primitive_array* ret = malloc(sizeof(__primitive_array));
	
	ret->data = calloc(1, sizeof(shadow_ulong_t) + num*size);
	ret->size = num;
	
	*data = &ret->data[1];
	
	return (shadow_PrimitiveArray_t*)ret;
}

void shadow_FreeArray(shadow_PrimitiveArray_t* ref)
{
	__primitive_array* array = (__primitive_array*)ref;
	free(array->data);
	free(array);
}