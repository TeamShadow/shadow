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
    {
        fprintf(stderr, "Trying to destroy condition variable while it's locked\n");
        return FALSE;
    }

    DeleteCriticalSection(&data->criticalSection);
    return TRUE;
}

shadow_boolean_t __shadow_natives__ConditionVariable_lock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    EnterCriticalSection(&data->criticalSection);
    if (data->owner != NULL)
    {
        fprintf(stderr, "Condition variable locked repeatedly by the same thread\n");
        LeaveCriticalSection (&data->criticalSection); // Non-NULL owner would mean entering twice
        return FALSE;
    }
    data->owner = currentThread;
    return TRUE;
}

shadow_boolean_t __shadow_natives__ConditionVariable_unlock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
    {
        fprintf(stderr, "Condition variable unlocked by a thread that isn't its owner\n");
        return FALSE;
    }

    data->owner = NULL;
    LeaveCriticalSection (&data->criticalSection);
    return TRUE;
}

shadow_boolean_t __shadow_natives__ConditionVariable_wait(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
    {
        fprintf(stderr, "Condition variable waited on by a thread that isn't its owner\n");
        return FALSE;
    }
    data->owner = NULL;

    if (SleepConditionVariableCS(&data->variable, &data->criticalSection, INFINITE))
    {
        data->owner = currentThread;
        return TRUE;
    }
    else
    {
        fprintf(stderr, "Error while thread was waiting on a condition variable\n");
        return FALSE;
    }
}

shadow_int_t __shadow_natives__ConditionVariable_waitTimeout(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread, shadow_long_t timeEpochNow, shadow_long_t timeout)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
        return NOT_OWNER;

    shadow_long_t nanoseconds = timeout - timeEpochNow;
    DWORD milliseconds = nanoseconds / 1000000;
    data->owner = NULL;

    if (SleepConditionVariableCS(&data->variable, &data->criticalSection, milliseconds))
    {
        data->owner = currentThread;
        return WAKEUP;
    }
    else
    {
        if (GetLastError() == ERROR_TIMEOUT)
        {
            data->owner = currentThread;
            return TIMEOUT;
        }
        else
        {
            fprintf(stderr, "Error while thread was waiting on a condition variable\n");
            return NOT_OWNER; // assume ownership problem?
        }
    }
}

void __shadow_natives__ConditionVariable_notifyAll(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    WakeAllConditionVariable (&data->variable);
}