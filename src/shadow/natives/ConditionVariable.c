/**
 * Author: Barry Wittman
 */
#include <natives/ConditionVariable.h>
#include <natives/Pointer.h>
#include <stdio.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	typedef struct {
        CONDITION_VARIABLE variable;
        CRITICAL_SECTION   criticalSection;
    } ShadowConditionVariableData;
#else

#endif


shadow_Pointer_t __shadow_natives__ConditionVariable_initialize(shadow_ConditionVariable_t* _this)
{
	ShadowConditionVariableData* data = calloc(1, sizeof(ShadowConditionVariableData));
    InitializeConditionVariable(&data->variable);
    InitializeCriticalSection (&data->criticalSection);
    return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__ConditionVariable_destroy(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    DeleteCriticalSection(&_shadow_natives__Pointer_extract(ShadowConditionVariableData, handle)->criticalSection);
    return TRUE;
}

shadow_boolean_t __shadow_natives__ConditionVariable_lock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data == NULL)
        printf("Bad condition variable data!\n");
    else
        printf("Entering critical section!\n");
    EnterCriticalSection(&data->criticalSection);
    return TRUE;
}

void __shadow_natives__ConditionVariable_unlock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    LeaveCriticalSection(&data->criticalSection);
}

void __shadow_natives__ConditionVariable_wait(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    SleepConditionVariableCS(&data->variable, &data->criticalSection, INFINITE);
}

shadow_boolean_t __shadow_natives__ConditionVariable_waitTimeout(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle,  shadow_long_t timeEpochNow, shadow_long_t timeout)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    shadow_long_t nanoseconds = timeout - timeEpochNow;
    DWORD milliseconds = nanoseconds / 1000000;
    BOOL result = SleepConditionVariableCS(&data->variable, &data->criticalSection, milliseconds);
    if (!result)
        return GetLastError() == ERROR_TIMEOUT;
    else
        return FALSE;
}

void __shadow_natives__ConditionVariable_notifyAll(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    WakeAllConditionVariable (&data->variable);
}