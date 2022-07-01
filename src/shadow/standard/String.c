/**
 * Authors: Claude Abounegm and Barry Wittman
 */
#include <standard/String.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>

// METHOD SIGNATURES //
shadow_String_t* _shadow_standard__String_createBytes(const shadow_String_t*, shadow_Array_t*);
// METHOD SIGNATURES //


StringData* __shadow_standard__String_getData(const shadow_String_t* instance, StringData* str)
{
	if(!str) {
		str = malloc(sizeof(StringData));
	}
	
	__shadow_standard__Array_getData(instance->data, (ArrayData*)str);
	str->ascii = instance->ascii;
	
	return str;
}

char* __shadow_standard__String_getCString(const shadow_String_t* instance)
{
	StringData str;
	__shadow_standard__String_getData(instance, &str);
	
	// allocate a string which is big enough for the chars + null character
	char* c_str = malloc(str.size + 1);
	memcpy(c_str, str.chars, str.size);
	c_str[str.size] = '\0';
	
	return c_str;
}

shadow_String_t* __shadow_standard__String_create(const char* c_str)
{
	// get the length of the NULL terminated string
	int length = strlen(c_str);
	
	// this is an empty array with size length
	shadow_ubyte_t* chars;

	// create the array then copy the c_str to chars without the null terminator
	shadow_Array_t* array = __shadow_standard__Array_create(length, _shadow_standard__UByteArray_getClass(), false, (void**)&chars);
	memcpy(chars, c_str, length);
	
	// we then need to create the actual Shadow String
	return _shadow_standard__String_createBytes(NULL, array);
}

void __shadow_standard__String_free(shadow_String_t* instance)
{
	// free the data array
	__shadow_standard__Array_free(instance->data);
	
	// free the string reference
	free(instance);
}
