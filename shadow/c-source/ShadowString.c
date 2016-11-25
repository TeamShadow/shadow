/**
 * Author: Claude Abounegm
 */
#include "ShadowString.h"
#include "ShadowArray.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

ShadowArray GetShadowArrayFromShadowString(ShadowString);

char* UnpackShadowStringToCStr(ShadowString stringRef)
{
	ByteArray* str = UnpackShadowString(stringRef);
	
	char* dest = malloc(str->size + 1);
	strncpy(dest, str->chars, str->size);
	dest[str->size] = '\0';
	
	return dest;
}

ByteArray* UnpackShadowString(ShadowString stringRef)
{
	return (ByteArray*)UnpackShadowArray(GetShadowArrayFromShadowString(stringRef));
}