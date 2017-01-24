/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SIGNALER_H
#define SHADOW_SIGNALER_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowSignaler;

ShadowPointer __ShadowSignaler_Initialize(void);
ShadowBoolean __ShadowSignaler_Destroy(ShadowPointer);

ShadowBoolean __ShadowSignaler_Wait(ShadowPointer);
ShadowBoolean __ShadowSignaler_WaitTimeout(ShadowPointer shadowPtr, ShadowULong currentEpochTime, ShadowULong timeout);
ShadowBoolean __ShadowSignaler_Broadcast(ShadowPointer);

#endif