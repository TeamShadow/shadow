/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_SIGNALER_H
#define SHADOW_SIGNALER_H

#include <Shadow.h>
#include <natives/Pointer.h>

typedef void* shadow_Signaler_t;

shadow_Pointer_t __shadow_natives__Signaler_initialize(void);
shadow_boolean_t __shadow_natives__Signaler_destroy(shadow_Pointer_t);

shadow_boolean_t __shadow_natives__Signaler_wait(shadow_Pointer_t);
shadow_boolean_t __shadow_natives__Signaler_waitTimeout(shadow_Pointer_t shadowPtr, shadow_long_t currentEpochTime, shadow_long_t timeout);
shadow_boolean_t __shadow_natives__Signaler_broadcast(shadow_Pointer_t);

#endif