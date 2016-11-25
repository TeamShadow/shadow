/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_ARRAY_H
#define SHADOW_ARRAY_H

#include "ShadowTypes.h"

typedef struct {
	ShadowInt size;
	void* data;
} VoidArray;

typedef void* ShadowArray;

VoidArray* UnpackShadowArray(ShadowArray);

#endif