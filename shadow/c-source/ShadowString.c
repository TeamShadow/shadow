/**
 * Author: Claude Abounegm
 */
#include "ShadowString.h"
#include "ShadowArray.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void ExtractDataFromShadowString(ShadowString, ShadowArray*, ShadowBoolean*);

char* UnpackShadowStringToCStr(ShadowString stringRef)
{
	ShadowStringData str;
	UnpackShadowString(stringRef, &str);
	
	char* dest = malloc(str.size + 1);
	int i;
	for(i = 0; i < str.size; ++i) {
		dest[i] = str.chars[i];
	}
	dest[i] = '\0';
	
	return dest;
}

void UnpackShadowString(ShadowString stringRef, ShadowStringData* str)
{
	ShadowArray chars;	
	ExtractDataFromShadowString(stringRef, &chars, &str->ascii);
	
	UnpackShadowArray(chars, (VoidArray*)str);
}

void __ShadowString_TestPrintf(ShadowString stringRef)
{
	char* cStr = UnpackShadowStringToCStr(stringRef);
	printf("%s", cStr);
	free(cStr);
}