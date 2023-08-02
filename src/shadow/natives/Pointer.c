/**
 * Author: Claude Abounegm
 */
#include <natives/Pointer.h>
#include <stdlib.h>
#include <standard/Object.h>
#include <stdio.h>

void __shadow_natives__Pointer_free(shadow_Pointer_t* _this, void* ptr)
{
	free(ptr);
}

void __printRef(shadow_Object_t* object)
{
    printf("Reference count: %llu\n", (unsigned long long)object->ref_count);
}