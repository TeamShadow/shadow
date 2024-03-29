/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

#include <natives/Pointer.h>
#include <standard/Thread.h>
#include <Shadow.h>

typedef void* shadow_Mutex_t;

shadow_Pointer_t __shadow_natives__Mutex_initialize(shadow_Mutex_t* _this);
shadow_boolean_t __shadow_natives__Mutex_destroy(shadow_Mutex_t* _this, shadow_Pointer_t*);
void __shadow_natives__Mutex_lock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread);
shadow_boolean_t __shadow_natives__Mutex_unlock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread);
shadow_Thread_t __shadow_natives__Mutex_getOwner(shadow_Mutex_t* _this, shadow_Pointer_t* pointer);

#endif