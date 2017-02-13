/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_ARRAY_H
#define SHADOW_ARRAY_H

#include "ShadowTypes.h"

typedef void* ShadowArray;

typedef struct {
	ShadowInt size;
	void* data;
} VoidArray;

void UnpackShadowArray(ShadowArray arrayRef, VoidArray* array);

#endif