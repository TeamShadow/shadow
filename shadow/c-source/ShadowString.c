#include "Shadow.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* __unpackShadowString(ShadowString stringRef)
{
	String str;
	__getDataFromShadowString(stringRef, &str);
	
	char* dest = malloc(str.size + 1);
	strncpy(dest, str.chars, str.size);
	dest[str.size] = '\0';
	
	return dest;
}

void __getDataFromShadowString(ShadowString stringRef, String* str)
{
	ShadowArray arr;
	__getShadowArrayFromString(stringRef, &arr);
	__getDataFromShadowArray(arr, &str->size, &str->chars);
}