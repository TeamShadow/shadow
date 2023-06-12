/**
 * Author: Claude Abounegm
 */
#include <natives/Pointer.h>
#include <stdlib.h>

void __shadow_natives__Pointer_free(shadow_Pointer_t* _this, void* ptr)
{
	free(ptr);
}