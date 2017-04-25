/**
 * Author: Claude Abounegm
 */
#include <standard/Thread.h>
#include <stddef.h>
#include <stdlib.h>

// METHOD SIGNATURES //
shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t*, void* (*thread_start)(void*));
// METHOD SIGNATURES //


shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t* this, void* (*thread_start)(void*))
{
	return shadowPointer_Create(malloc(1), SHADOW_CAN_FREE);
}