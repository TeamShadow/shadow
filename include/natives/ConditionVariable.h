/**
 * Author: Barry Wittman
 */
#ifndef SHADOW_CONDITION_VARIABLE_H
#define SHADOW_CONDITION_VARIABLE_H

#include <natives/Pointer.h>
#include <standard/Thread.h>
#include <Shadow.h>

typedef void* shadow_ConditionVariable_t;

shadow_Pointer_t __shadow_natives__ConditionVariable_initialize(shadow_ConditionVariable_t* _this);
shadow_boolean_t __shadow_natives__ConditionVariable_destroy(shadow_ConditionVariable_t* _this, shadow_Pointer_t*);
shadow_boolean_t __shadow_natives__ConditionVariable_lock(shadow_ConditionVariable_t* _this, shadow_Pointer_t*, shadow_Thread_t*);
shadow_boolean_t __shadow_natives__ConditionVariable_unlock(shadow_ConditionVariable_t* _this, shadow_Pointer_t*, shadow_Thread_t*);
shadow_boolean_t __shadow_natives__ConditionVariable_wait(shadow_ConditionVariable_t* _this, shadow_Pointer_t*, shadow_Thread_t*);
shadow_int_t __shadow_natives__ConditionVariable_waitTimeout(shadow_ConditionVariable_t* _this, shadow_Pointer_t*, shadow_Thread_t*, shadow_long_t timeEpochNow, shadow_long_t timeout);
void __shadow_natives__ConditionVariable_notifyAll(shadow_ConditionVariable_t* _this, shadow_Pointer_t*);


#endif