/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdio.h>
#include <stdlib.h>

void __ShadowExternalTest_TestPrintf(shadow_String_t* stringRef)
{
	char* cStr = shadow_UnpackStringToCStr(stringRef);
	printf("%s", cStr);
	free(cStr);
}