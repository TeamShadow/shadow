/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SYSTEM_H
#define SHADOW_SYSTEM_H

#include <ShadowCore.h>

typedef void* shadow_System_t;

shadow_ulong_t __ShadowSystem_GetNanoTime(void);
shadow_ulong_t __ShadowSystem_GetEpochNanoTime(void);

#endif