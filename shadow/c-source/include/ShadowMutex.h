/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowMutex;

ShadowPointer __ShadowMutex_Initialize(ShadowMutex);
ShadowBoolean __ShadowMutex_Destroy(ShadowMutex, ShadowPointer);
ShadowBoolean __ShadowMutex_Lock(ShadowMutex, ShadowPointer);
ShadowBoolean __ShadowMutex_Unlock(ShadowMutex, ShadowPointer);

#endif