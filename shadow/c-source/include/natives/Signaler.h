/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SIGNALER_H
#define SHADOW_SIGNALER_H

#include <ShadowCore.h>
#include <natives/Pointer.h>

typedef void* shadow_Signaler_t;

shadow_Pointer_t* __ShadowSignaler_Initialize(void);
shadow_boolean_t __ShadowSignaler_Destroy(shadow_Pointer_t*);

shadow_boolean_t __ShadowSignaler_Wait(shadow_Pointer_t*);
shadow_boolean_t __ShadowSignaler_WaitTimeout(shadow_Pointer_t*, shadow_ulong_t, shadow_ulong_t);
shadow_boolean_t __ShadowSignaler_Broadcast(shadow_Pointer_t*);

#endif