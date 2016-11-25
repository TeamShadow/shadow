/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SIGNALER_H
#define SHADOW_SIGNALER_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowSignaler;

ShadowPointer __ShadowSignaler_Initialize(ShadowSignaler);
ShadowBoolean __ShadowSignaler_Destroy(ShadowSignaler, ShadowPointer);
ShadowBoolean __ShadowSignaler_Wait(ShadowSignaler, ShadowPointer);
ShadowBoolean __ShadowSignaler_WaitTimeout(ShadowSignaler, ShadowPointer, ShadowLong);
ShadowBoolean __ShadowSignaler_Broadcast(ShadowSignaler, ShadowPointer);

#endif