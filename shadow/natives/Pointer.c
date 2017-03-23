/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <stdlib.h>

void __ShadowPointer_Free(void* ptr)
{
	free(ptr);
}