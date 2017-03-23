/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

#include <ShadowCore.h>
#include <standard/Thread.h>

typedef void* shadow_Mutex_t;

shadow_Pointer_t* __ShadowMutex_Initialize(shadow_boolean_t);
shadow_boolean_t __ShadowMutex_Destroy(shadow_Pointer_t*);
shadow_boolean_t __ShadowMutex_Lock(shadow_Pointer_t*, shadow_Thread_t*);
shadow_boolean_t __ShadowMutex_Unlock(shadow_Pointer_t*);
shadow_Thread_t* __ShadowMutex_GetOwner(shadow_Pointer_t*);

#endif