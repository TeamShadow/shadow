/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_STRING_H
#define SHADOW_STRING_H

#include "ShadowTypes.h"

typedef void* ShadowString;

typedef struct {
	// The size of the chars array.
	ShadowInt size;
	
	// The array which contains size elements.
	// Note: this array is not null terminated.
	ShadowByte* chars;
	
	// A boolean to whether the chars array is in ascii or not.
	ShadowBoolean ascii;
} ShadowStringData;

/**
 * Returns a null-terminated clone of the supplied ShadowString. Since this is a copy,
 * the result needs to be freed after use.
 */
char* UnpackShadowStringToCStr(ShadowString stringRef);

/**
 * Takes a ShadowString and stores the data in the ShadowStringData struct supplied
 * as the second argument.
 */
void UnpackShadowString(ShadowString stringRef, ShadowStringData* str);

#endif