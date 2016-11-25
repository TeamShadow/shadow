/**
 * Author: Claude Abounegm
 */
#include "ShadowArray.h"
#include <stdlib.h>

void ExtractDataFromShadowArray(ShadowArray, ShadowInt*, void**);

VoidArray* UnpackShadowArray(ShadowArray arrayRef)
{
	VoidArray* array = malloc(sizeof(VoidArray));
	ExtractDataFromShadowArray(arrayRef, &array->size, &array->data);
	return array;
}