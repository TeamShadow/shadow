/**
 * Authors: Claude Abounegm and Barry Wittman
 */
#include <standard/String.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>

// METHOD SIGNATURES //
shadow_String_t* _shadowString_CreateBytes(const shadow_String_t*, shadow_Array_t*);
// METHOD SIGNATURES //


StringData* shadowString_GetData(const shadow_String_t* instance, StringData* str)
{
	if(!str) {
		str = malloc(sizeof(StringData));
	}
	
	shadowArray_GetData(instance->data, (ArrayData*)str);
	str->ascii = instance->ascii;
	
	return str;
}

char* shadowString_GetCString(const shadow_String_t* instance)
{
	StringData str;
	shadowString_GetData(instance, &str);
	
	// allocate a string which is big enough for the chars + null character
	char* c_str = malloc(str.size + 1);
	memcpy(c_str, str.chars, str.size);
	c_str[str.size] = '\0';
	
	return c_str;
}

shadow_String_t* shadowString_Create(const char* c_str)
{
	// get the length of the NULL terminated string
	int length = strlen(c_str);
	
	// this is an empty array with size length
	shadow_byte_t* chars;

	// create the array then copy the c_str to chars without the null terminator
	shadow_Array_t* array = shadowArray_Create(length, shadowByteArray_GetClass(), false, (void**)&chars);
	memcpy(chars, c_str, length);
	
	// we then need to create the actual Shadow String, but Shadow deals with arrays
	// as structs and not as pointers, so we need to dereference it first.
	return _shadowString_CreateBytes(NULL, array);
}

void shadowString_Free(shadow_String_t* instance)
{
	// free the data array
	shadowArray_Free(instance->data);
	
	// free the string reference
	free(instance);
}
