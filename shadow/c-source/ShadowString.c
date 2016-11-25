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
	ByteArray str;
	UnpackShadowString(stringRef, &str);
	
	char* dest = malloc(str.size + 1);
	strncpy(dest, str.chars, str.size);
	dest[str.size] = '\0';
	
	return dest;
}

void UnpackShadowString(ShadowString stringRef, ByteArray* str)
{
	UnpackShadowArray(GetShadowArrayFromShadowString(stringRef), (VoidArray*)str);
}

void __ShadowString_TestPrintf(ShadowString stringRef)
{
	char* cStr = UnpackShadowStringToCStr(stringRef);
	printf("%s", cStr);
	free(cStr);
}