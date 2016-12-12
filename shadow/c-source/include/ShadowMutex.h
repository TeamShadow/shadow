/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"
#include "ShadowThread.h"

typedef void* ShadowMutex;

ShadowPointer __ShadowMutex_Initialize(ShadowBoolean allowRecursive);
ShadowBoolean __ShadowMutex_Destroy(ShadowPointer);
ShadowBoolean __ShadowMutex_Lock(ShadowPointer, ShadowThread);
ShadowBoolean __ShadowMutex_Unlock(ShadowPointer);
ShadowThread __ShadowMutex_GetOwner(ShadowPointer pointer);

#endif