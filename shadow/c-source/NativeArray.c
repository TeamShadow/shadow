/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>

void ExtractDataFromShadowArray(shadow_NativeArray_t*, shadow_int_t*, void**);

void shadow_UnpackArray(shadow_NativeArray_t* arrayRef, VoidArray* array)
{
	ExtractDataFromShadowArray(arrayRef, &array->size, &array->data);
}