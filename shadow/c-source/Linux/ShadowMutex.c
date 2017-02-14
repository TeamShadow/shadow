/**
 * Author: Claude Abounegm
 */
#include "ShadowMutex.h"

ShadowPointer __ShadowMutex_Initialize(ShadowBoolean allowRecursive)
{
	return CreateShadowPointer(malloc(1), SHADOW_CAN_FREE);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowPointer pointer)
{
	return false;
}

ShadowBoolean __ShadowMutex_Lock(ShadowPointer pointer, ShadowThread currentThread)
{
	return false;
}

ShadowBoolean __ShadowMutex_Unlock(ShadowPointer pointer)
{
	return false;
}

ShadowThread __ShadowMutex_GetOwner(ShadowPointer pointer)
{
	return NULL;
}