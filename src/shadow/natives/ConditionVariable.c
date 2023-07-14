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
        CRITICAL_SECTION criticalSection;
    } ShadowConditionVariableData;
#else
    #include <pthread.h>
    #include <stdlib.h>
    #include <errno.h>

	typedef struct {
	    shadow_Thread_t* owner;
        pthread_cond_t variable;
        pthread_mutex_t mutex;
    } ShadowConditionVariableData;
#endif

enum {NOT_OWNER, TIMEOUT, WAKEUP};

shadow_Pointer_t __shadow_natives__ConditionVariable_initialize(shadow_ConditionVariable_t* _this)
{
	ShadowConditionVariableData* data = calloc(1, sizeof(ShadowConditionVariableData));
#ifdef SHADOW_WINDOWS
    InitializeConditionVariable(&data->variable);
    InitializeCriticalSection (&data->criticalSection);
#else
    pthread_cond_init(&data->variable, NULL);
    pthread_mutex_init(&data->mutex, NULL);
#endif
    return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__ConditionVariable_destroy(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner)
    {
        fprintf(stderr, "Trying to destroy condition variable while it's locked\n");
        return 0;
    }
#ifdef SHADOW_WINDOWS
    DeleteCriticalSection(&data->criticalSection);
    return 1;
#else
    int destroyCondition = pthread_cond_destroy(&data->variable);
    int destroyMutex = pthread_mutex_destroy(&data->mutex);
    return destroyCondition == 0 && destroyMutex == 0;
#endif
}

shadow_boolean_t __shadow_natives__ConditionVariable_lock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
#ifdef SHADOW_WINDOWS
    EnterCriticalSection(&data->criticalSection);
#else
    pthread_mutex_lock(&data->mutex);
#endif
    if (data->owner != NULL)
    {
        fprintf(stderr, "Condition variable locked repeatedly by the same thread\n");
#ifdef SHADOW_WINDOWS
        LeaveCriticalSection (&data->criticalSection); // Non-NULL owner would mean entering twice
#else
        pthread_mutex_unlock(&data->mutex); // shouldn't be possible with pthreads
#endif
        return 0;
    }
    data->owner = currentThread;
    return 1;
}

shadow_boolean_t __shadow_natives__ConditionVariable_unlock(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
    {
        fprintf(stderr, "Condition variable unlocked by a thread that isn't its owner\n");
        return 0;
    }

    data->owner = NULL;
#ifdef SHADOW_WINDOWS
    LeaveCriticalSection (&data->criticalSection);
#else
    pthread_mutex_unlock(&data->mutex);
#endif
    return 1;
}

shadow_boolean_t __shadow_natives__ConditionVariable_wait(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
    {
        fprintf(stderr, "Condition variable waited on by a thread that isn't its owner\n");
        return 0;
    }
    data->owner = NULL;


#ifdef SHADOW_WINDOWS
    if (SleepConditionVariableCS(&data->variable, &data->criticalSection, INFINITE))
#else
    if (pthread_cond_wait(&data->variable, &data->mutex) == 0)
#endif

    {
        data->owner = currentThread;
        return 1;
    }
    else
    {
        fprintf(stderr, "Error while thread was waiting on a condition variable\n");
        return 0;
    }
}

shadow_int_t __shadow_natives__ConditionVariable_waitTimeout(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle, shadow_Thread_t* currentThread, shadow_long_t timeEpochNow, shadow_long_t timeout)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
    if (data->owner != currentThread)
        return NOT_OWNER;

    data->owner = NULL;
    shadow_long_t nanoseconds = timeout - timeEpochNow;
#ifdef SHADOW_WINDOWS
    DWORD milliseconds = nanoseconds / 1000000;
    if (SleepConditionVariableCS(&data->variable, &data->criticalSection, milliseconds))
    {
        data->owner = currentThread;
        return WAKEUP;
    }
    else if (GetLastError() == ERROR_TIMEOUT)
    {
        data->owner = currentThread;
        return TIMEOUT;
    }
    else
    {
        fprintf(stderr, "Error while thread was waiting on a condition variable\n");
        return NOT_OWNER; // assume ownership problem?
    }
#else
    struct timespec ts;
    ts.tv_sec = nanoseconds / 1000000000;
    ts.tv_nsec = nanoseconds % 1000000000;
    data->owner = NULL;
    int result = pthread_cond_timedwait(&data->variable, &data->mutex, &ts);
    if (result == 0)
    {
        data->owner = currentThread;
        return WAKEUP;
    }
    else if (result == ETIMEDOUT)
    {
        data->owner = currentThread;
        return TIMEOUT;
    }
    else
    {
        fprintf(stderr, "Error while thread was waiting on a condition variable\n");
        return NOT_OWNER; // assume ownership problem?
    }
#endif
}

void __shadow_natives__ConditionVariable_notifyAll(shadow_ConditionVariable_t* _this, shadow_Pointer_t* handle)
{
    ShadowConditionVariableData* data = _shadow_natives__Pointer_extract(ShadowConditionVariableData, handle);
#ifdef SHADOW_WINDOWS
    WakeAllConditionVariable (&data->variable);
#else
    pthread_cond_broadcast (&data->variable);
#endif
}