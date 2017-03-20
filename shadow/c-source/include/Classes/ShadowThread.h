/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_THREAD_H
#define SHADOW_THREAD_H

#include "ShadowTypes.h"
#include "ShadowPointer.h"

typedef void* ShadowThread;

ShadowPointer __ShadowThread_Spawn(ShadowThread, void* (*thread_start)(ShadowThread));

#endif