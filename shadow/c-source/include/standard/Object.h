/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_OBJECT_H
#define SHADOW_OBJECT_H

#include <ShadowCore.h>
#include <standard/String.h>

typedef void* shadow_Object_t;

shadow_String_t* shadow_ToString(shadow_Object_t* ref);

#endif