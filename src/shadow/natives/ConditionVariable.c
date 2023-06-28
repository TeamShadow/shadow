/**
 * Author: Barry Wittman
 */
#include <natives/ConditionVariable.h>
#include <natives/Pointer.h>
#include <stdio.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	typedef struct {
	    shadow_Thread_t* owner;
        LONG counter;
        CONDITION_VARIABLE variable;
        CRITICAL_SECTION   criticalSection;
    } ShadowConditionVariableData;
#else

#endif

enum {NOT_OWNER, TIMEOUT, WAKEUP};

shadow_Pointer_t __shadow_natives__ConditionVariable_initialize(shadow_ConditionVariable_t* _this)
{
	ShadowConditionVariableData* data = calloc(1, sizeof(ShadowConditionVariableData));
    InitializeConditionVariable(&data->variable);
    InitializeCriticalSection (&data->criticalSection);
    return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__ConditionVariable_destroy(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner)
        return FALSE;
    DeleteCriticalSection(&data->criticalSection);
    return TRUE;
}

void __shadow_natives__ConditionVariable_lock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    EnterCriticalSection(&data->criticalSection);
    data->counter++;
    data->owner = currentThread;
}

shadow_boolean_t __shadow_natives__ConditionVariable_unlock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
        return FALSE;

    data->counter--;

    if (data->counter < 0)
        return FALSE;
    if (data->counter == 0)
        data->owner = NULL;

    LeaveCriticalSection (&data->criticalSection);
    return TRUE;
}

shadow_boolean_t __shadow_natives__ConditionVariable_wait(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
            return FALSE;
    SleepConditionVariableCS(&data->variable, &data->criticalSection, INFINITE);
    return TRUE;
}

shadow_int_t __shadow_natives__ConditionVariable_waitTimeout(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread, shadow_long_t timeEpochNow, shadow_long_t timeout)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
            return NOT_OWNER;
    shadow_long_t nanoseconds = timeout - timeEpochNow;
    DWORD milliseconds = nanoseconds / 1000000;
    BOOL result = SleepConditionVariableCS(&data->variable, &data->criticalSection, milliseconds);
    if (!result) {
        if (GetLastError() == ERROR_TIMEOUT)
            return TIMEOUT;
        else
            return NOT_OWNER; // assume ownership problem?
    }
    else
        return WAKEUP;
}

void __shadow_natives__ConditionVariable_notifyAll(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    WakeAllConditionVariable (&data->variable);
}

shadow_Thread_t __shadow_natives__ConditionVariable_getOwner(shadow_ConditionVariable_t* _this, shadow_Pointer_t* pointer)
{
	return _shadow_natives__Pointer_extract(ShadowConditionVariableData, pointer)->owner;
}