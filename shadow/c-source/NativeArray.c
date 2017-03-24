/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>

void _shadow_UnpackArray(shadow_NativeArray_t*, shadow_int_t*, void**);

void shadow_UnpackArray(shadow_NativeArray_t* arrayRef, VoidArray* array)
{
	_shadow_UnpackArray(arrayRef, &array->size, &array->data);
}