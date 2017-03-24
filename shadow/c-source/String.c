/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdlib.h>

void _shadow_UnpackString(shadow_String_t*, shadow_NativeArray_t**, shadow_boolean_t*);

char* shadow_UnpackStringToCStr(shadow_String_t* stringRef)
{
	ShadowStringData str;
	shadow_UnpackString(stringRef, &str);
	
	char* dest = malloc(str.size + 1);
	int i;
	for(i = 0; i < str.size; ++i) {
		dest[i] = str.chars[i];
	}
	dest[i] = '\0';
	
	return dest;
}

void shadow_UnpackString(shadow_String_t* stringRef, ShadowStringData* str)
{
	shadow_NativeArray_t* array;
	_shadow_UnpackString(stringRef, &array, &str->ascii);
	
	shadow_UnpackArray(array, (VoidArray*)str);
}