/**
 * Author: Claude Abounegm
 */
#include "ShadowArray.h"

void ExtractDataFromShadowArray(ShadowArray, ShadowInt*, void**);

void UnpackShadowArray(ShadowArray arrayRef, VoidArray* array)
{
	ExtractDataFromShadowArray(arrayRef, &array->size, &array->data);
}