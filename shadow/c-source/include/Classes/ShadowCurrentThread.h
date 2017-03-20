/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CURRENT_THREAD_H
#define SHADOW_CURRENT_THREAD_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowCurrentThread;

ShadowBoolean __ShadowCurrentThread_Yield(void);

#endif