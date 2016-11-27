/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_THREADS_H
#define SHADOW_THREADS_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowThread;
typedef void* ShadowCurrentThread;

ShadowPointer __ShadowThread_spawn(ShadowThread);
ShadowBoolean __ShadowCurrentThread_Yield(void);

#endif