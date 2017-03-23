/**
 * Author: Claude Abounegm
 */
#include <natives/Mutex.h>
#include <stdlib.h>

shadow_Pointer_t* __ShadowMutex_Initialize(shadow_boolean_t allowRecursive)
{
	return shadow_CreatePointer(malloc(1), SHADOW_CAN_FREE);
}

shadow_boolean_t __ShadowMutex_Destroy(shadow_Pointer_t* pointer)
{
	return false;
}

shadow_boolean_t __ShadowMutex_Lock(shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
	return false;
}

shadow_boolean_t __ShadowMutex_Unlock(shadow_Pointer_t* pointer)
{
	return false;
}

shadow_Thread_t* __ShadowMutex_GetOwner(shadow_Pointer_t* pointer)
{
	return false;
}