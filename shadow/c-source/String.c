/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <__PrimitiveArrayType.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>

void _shadow_GetStringData(const shadow_String_t*, shadow_PrimitiveArray_t**, shadow_boolean_t*);
shadow_String_t* _shadow_CreateString(const shadow_String_t*, __primitive_array);

void shadow_GetStringData(const shadow_String_t* instance, ShadowStringData* str)
{
	shadow_PrimitiveArray_t* array;
	_shadow_GetStringData(instance, &array, &str->ascii);
	
	shadow_GetArrayData(array, (VoidArray*)str);
}

char* shadow_GetStringDataAsCStr(const shadow_String_t* instance)
{
	ShadowStringData str;
	shadow_GetStringData(instance, &str);
	
	char* dest = malloc(str.size + 1);
	memcpy(dest, str.chars, str.size);
	dest[str.size] = '\0';
	
	return dest;
}

shadow_String_t* shadow_CreateString(const char* string)
{
	int length = strlen(string);
	
	char* chars;
	__primitive_array* array = (__primitive_array*)shadow_CreateArray(length, sizeof(char), (void**)&chars);
	memcpy(chars, string, length);
	
	return _shadow_CreateString(NULL, *array);
}

void shadow_FreeString(shadow_String_t* instance)
{
	shadow_PrimitiveArray_t* array;
	shadow_boolean_t ascii;
	
	_shadow_GetStringData(instance, &array, &ascii);
	shadow_FreeArray(array);
	free(instance);
}