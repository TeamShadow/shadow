/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_ARRAY_H
#define SHADOW_ARRAY_H

#include "ShadowTypes.h"

typedef void* ShadowArray;

/**
 * This is the C representation of the Shadow array when unpacked.
 */
typedef struct {
	ShadowInt size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					// to the desired data structure.
} VoidArray;

/**
 * Takes a ShadowArray supplied from Shadow code, and stores the data
 * in the C usable format, in VoidArray* array. If data is modified here,
 * the data is also modified in Shadow.
 */
void UnpackShadowArray(ShadowArray arrayRef, VoidArray* array);

#endif