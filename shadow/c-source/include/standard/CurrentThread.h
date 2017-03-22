/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CURRENT_THREAD_H
#define SHADOW_CURRENT_THREAD_H

#include <ShadowCore.h>

typedef void* shadow_CurrentThread_t;

shadow_boolean_t __ShadowCurrentThread_Yield(void);

#endif