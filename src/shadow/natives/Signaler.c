/**
 * Author: Claude Abounegm
 */
#include <natives/Signaler.h>
#include <stddef.h>
#include <stdlib.h>

#define NANOS_IN_SECONDS 1000000000LL

#ifdef SHADOW_WINDOWS
    #include <Windows.h>

    typedef struct {
        CONDITION_VARIABLE cond;
        CRITICAL_SECTION mutex;
    } ShadowSignalerData;
#else
    #include <pthread.h>

    typedef struct {
        pthread_cond_t cond;
        pthread_mutex_t mutex;
    } ShadowSignalerData;
#endif

static void InvalidatePointer(ShadowSignalerData** data) 
{
	free(*data);
	*data = NULL;
}

/// valid pointer returned on success, otherwise, invalid.
shadow_Pointer_t __shadow_natives__Signaler_initialize(void)
{
	ShadowSignalerData* data = malloc(sizeof(ShadowSignalerData));

#ifdef SHADOW_WINDOWS
	InitializeConditionVariable (&data->cond);
	InitializeCriticalSection (&data->mutex);
#else
    if(pthread_cond_init(&data->cond, NULL) != 0) {
        InvalidatePointer(&data);
    } else if(pthread_mutex_init(&data->mutex, NULL) != 0) {
        pthread_cond_destroy(&data->mutex);
        InvalidatePointer(&data);
    }
#endif

	return _shadow_natives__Pointer_create(data, SHADOW_CAN_FREE);
}

/// true on success, otherwise, false.
shadow_boolean_t __shadow_natives__Signaler_destroy(shadow_Pointer_t shadowdata)
{
	ShadowSignalerData* data = _shadow_natives__Pointer_extract(ShadowSignalerData, shadowdata);

#ifdef SHADOW_WINDOWS
	int result = true;
	DeleteCriticalSection(&data->mutex);
#else
	int result = (pthread_cond_destroy(&data->cond) == 0);
	result &= (pthread_mutex_destroy(&data->mutex) == 0);
#endif

	return result;
}

/// true on success, otherwise, false.
shadow_boolean_t __shadow_natives__Signaler_wait(shadow_Pointer_t shadowdata)
{
	ShadowSignalerData* data = _shadow_natives__Pointer_extract(ShadowSignalerData, shadowdata);

#ifdef SHADOW_WINDOWS
    EnterCriticalSection (&data->mutex);
    int result = SleepConditionVariableCS(
      &data->cond,
      &data->mutex,
      INFINITE
    ) != 0;
    LeaveCriticalSection (&data->mutex);
#else
	pthread_mutex_lock(&data->mutex);
	int result = pthread_cond_wait(&data->cond, &data->mutex);
	pthread_mutex_unlock(&data->mutex);
#endif
	
	return (result == 0);
}

/// returns true if it timedout, otherwise, false.
// Should this take in only the timeout and work out the absolute time for Linux?
shadow_boolean_t __shadow_natives__Signaler_waitTimeout(shadow_Pointer_t shadowPtr, shadow_long_t currentEpochTime, shadow_long_t timeout)
{
	ShadowSignalerData* data = _shadow_natives__Pointer_extract(ShadowSignalerData, shadowPtr);
#ifdef SHADOW_WINDOWS
    EnterCriticalSection (&data->mutex);
    int result = SleepConditionVariableCS(
      &data->cond,
      &data->mutex,
      timeout / 1000000 // milliseconds
    ) != ERROR_TIMEOUT;
    LeaveCriticalSection (&data->mutex);
#else
	shadow_long_t absoluteNanos = currentEpochTime + timeout;
	struct timespec time = { absoluteNanos / NANOS_IN_SECONDS, absoluteNanos % NANOS_IN_SECONDS };
	
	pthread_mutex_lock(&data->mutex);
	int result = pthread_cond_timedwait(&data->cond, &data->mutex, &time) != ETIMEDOUT;
	pthread_mutex_unlock(&data->mutex);
#endif

	return (result == 0);
}

/// true on success, otherwise, false.
shadow_boolean_t __shadow_natives__Signaler_broadcast(shadow_Pointer_t shadowdata)
{
	ShadowSignalerData* data = _shadow_natives__Pointer_extract(ShadowSignalerData, shadowdata);

#ifdef SHADOW_WINDOWS
    WakeAllConditionVariable (&data->cond);
    return true;
#else
	return (pthread_cond_broadcast(&data->cond) == 0);
#endif
}