/**
 * Author: Claude Abounegm
 */
#include "ShadowMutex.h"

#include <stddef.h>
#include <stdlib.h>
#include <Windows.h>

ShadowPointer __ShadowMutex_Initialize(void)
{
	return CreateShadowPointer(CreateMutex(NULL, FALSE, NULL), 0);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowPointer pointer)
{
	return CloseHandle(ExtractRawPointer(pointer));
}

ShadowBoolean __ShadowMutex_Lock(ShadowPointer pointer)
{
	return (WaitForSingleObject(ExtractRawPointer(pointer), INFINITE) == WAIT_OBJECT_0);
}

ShadowBoolean __ShadowMutex_Unlock(ShadowPointer pointer)
{
	return ReleaseMutex(ExtractRawPointer(pointer));
}