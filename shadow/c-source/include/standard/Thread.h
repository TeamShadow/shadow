/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_THREAD_H
#define SHADOW_THREAD_H

#include <ShadowCore.h>

typedef void* shadow_Thread_t;

shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t*, void* (*thread_start)(void*));

#endif