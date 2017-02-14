/**
 * Author: Claude Abounegm
 */
#include "ShadowTypes.h"
#include "ShadowPointer.h"
#include <stdlib.h>

void __ShadowPointer_Free(void* ptr)
{
	free(ptr);
}