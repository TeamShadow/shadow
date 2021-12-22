/**
 * Author: Claude Abounegm
 */
#include <natives/Pointer.h>
#include <stdlib.h>

// METHOD SIGNATURES //
void __shadowPointer_free(void* ptr);
// METHOD SIGNATURES //

void __shadowPointer_free(void* ptr)
{
	free(ptr);
}