/**
 * Author: Claude Abounegm
 */
#include <natives/Pointer.h>
#include <stdlib.h>

// METHOD SIGNATURES //
void __ShadowPointer_Free(void* ptr);
// METHOD SIGNATURES //

void __ShadowPointer_Free(void* ptr)
{
	free(ptr);
}