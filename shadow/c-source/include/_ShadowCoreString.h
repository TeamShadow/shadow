#ifndef SHADOW_CORE_STRING_H
#define SHADOW_CORE_STRING_H

#include <standard/String.h>

typedef struct {
	// The size of the chars array.
	shadow_int_t size;
	
	// The array which contains size elements.
	// Note: this array is not null terminated.
	shadow_byte_t* chars;
	
	// A boolean to whether the chars array is in ascii or not.
	shadow_boolean_t ascii;
} ShadowStringData;

/**
 * Returns a null-terminated clone of the supplied ShadowString. Since this is a copy,
 * the result needs to be freed after use.
 */
char* shadow_UnpackStringToCStr(shadow_String_t*);

/**
 * Takes a ShadowString and stores the data in the ShadowStringData struct supplied
 * as the second argument.
 */
void shadow_UnpackString(shadow_String_t*, ShadowStringData* str);

#endif