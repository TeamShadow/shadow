/**
 * Author: Claude Abounegm
 */
#include <natives/Pointer.h>
#include <stdlib.h>

void __shadow_natives__Pointer_free(void* ptr)
{
	free(ptr);
}