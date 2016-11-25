/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SYSTEM_H
#define SHADOW_SYSTEM_H

#include "ShadowTypes.h"

typedef void* ShadowSystem;

ShadowULong __ShadowSystem_GetNanoTime(void);
ShadowULong __ShadowSystem_GetEpochNanoTime(void);

#endif