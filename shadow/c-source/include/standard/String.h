/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_STRING_H
#define SHADOW_STRING_H

#include <standard/ULong.h>
#include <standard/Long.h>
#include <standard/Byte.h>
#include <standard/Boolean.h>
#include <standard/MethodTable.h>
#include <standard/Array.h>

typedef struct shadow_Class_t shadow_Class_t;

typedef struct
{
	shadow_ulong_t ref_count;
	shadow_Class_t* class_ref;
	shadow_MethodTable_t* methods;
	shadow_Array_t* data;
	shadow_boolean_t ascii;
} shadow_String_t;

typedef struct {
	// The size of the chars array.
	shadow_long_t size;
	
	// The array which contains size elements.
	// Note: this array is not null terminated.
	shadow_byte_t* chars;
	
	// A boolean to whether the chars array is in ascii or not.
	shadow_boolean_t ascii;
} StringData;

/**
 * Takes a Shadow String and stores the data in the StringData struct supplied
 * as the second argument.
 */
StringData* shadowString_GetData(const shadow_String_t* instance, StringData* str);

/**
 * Returns a null-terminated clone of the supplied Shadow String. Since this is a copy,
 * the result needs to be freed after use.
 */
char* shadowString_GetCString(const shadow_String_t* instance);

/**
 * Creates a Shadow String from a NULL-terminated C String.
 */
shadow_String_t* shadowString_Create(const char*);

/**
 * Frees a Shadow String that was created using shadowString_Create()
 */
void shadowString_Free(shadow_String_t* instance);

#endif