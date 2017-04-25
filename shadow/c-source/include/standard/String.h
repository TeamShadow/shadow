/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_STRING_H
#define SHADOW_STRING_H

#include <ShadowCore.h>

typedef void* shadow_String_t;

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
 * Takes a ShadowString and stores the data in the ShadowStringData struct supplied
 * as the second argument.
 */
void shadow_GetStringData(const shadow_String_t*, ShadowStringData*);

/**
 * Returns a null-terminated clone of the supplied ShadowString. Since this is a copy,
 * the result needs to be freed after use.
 */
char* shadow_GetStringDataAsCStr(const shadow_String_t*);

/**
 * Creates a Shadow String from the NULL-terminated C String.
 */
shadow_String_t* shadow_CreateString(const char*);

/**
 * Frees a Shadow String that was created using shadow_CreateString()
 */
void shadow_FreeString(shadow_String_t*);

#endif