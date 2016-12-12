/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_STRING_H
#define SHADOW_STRING_H

#include "ShadowTypes.h"

typedef void* ShadowString;

typedef struct {
	ShadowInt size;
	ShadowByte* chars;
	ShadowBoolean ascii;
} ShadowStringData;

char* UnpackShadowStringToCStr(ShadowString stringRef);
void UnpackShadowString(ShadowString stringRef, ShadowStringData* str);

#endif