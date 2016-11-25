/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowMutex;

ShadowPointer __ShadowMutex_Initialize(void);
ShadowBoolean __ShadowMutex_Destroy(ShadowPointer);
ShadowBoolean __ShadowMutex_Lock(ShadowPointer);
ShadowBoolean __ShadowMutex_Unlock(ShadowPointer);

#endif