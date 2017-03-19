/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_ARRAY_H
#define SHADOW_ARRAY_H

#include "ShadowTypes.h"

typedef void* ShadowArray;

typedef struct {
	ShadowInt size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					// to the desired data structure.
} VoidArray;

void UnpackShadowArray(ShadowArray arrayRef, VoidArray* array);

#endif