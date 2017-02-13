/**
 * Author: Claude Abounegm
 */
#include "ShadowSignaler.h"

#include <stddef.h>
#include <pthread.h>
#include <stdlib.h>

#define NANOS_IN_SECONDS 1000000000LL

typedef struct {
	pthread_cond_t cond;
	pthread_mutex_t mutex;
} ShadowSignalerData;

static void InvalidatePointer(ShadowSignalerData** data) 
{
	free(*data);
	*data = NULL;
}

/// valid pointer returned on success, otherwise, invalid.
ShadowPointer __ShadowSignaler_Initialize(void)
{
	ShadowSignalerData* data = malloc(sizeof(ShadowSignalerData));
	
	if(pthread_cond_init(&data->cond, NULL) != 0) {
		InvalidatePointer(&data);
	} else if(pthread_mutex_init(&data->mutex, NULL) != 0) {
		pthread_cond_destroy(&data->mutex);
		InvalidatePointer(&data);
	}
	
	return CreateShadowPointer(data, SHADOW_CAN_FREE);
}

/// true on success, otherwise, false.
ShadowBoolean __ShadowSignaler_Destroy(ShadowPointer shadowdata)
{
	ShadowSignalerData* data = ExtractRawPointer(shadowdata);
	
	int result = (pthread_cond_destroy(&data->cond) == 0);
	result &= (pthread_mutex_destroy(&data->mutex) == 0);

	return result;
}

/// true on success, otherwise, false.
ShadowBoolean __ShadowSignaler_Wait(ShadowPointer shadowdata)
{
	ShadowSignalerData* data = ExtractRawPointer(shadowdata);

	pthread_mutex_lock(&data->mutex);
	int result = pthread_cond_wait(&data->cond, &data->mutex);
	pthread_mutex_unlock(&data->mutex);
	
	return (result == 0);
}

/// returns true if it timedout, otherwise, false.
ShadowBoolean __ShadowSignaler_WaitTimeout(ShadowPointer shadowdata, ShadowULong currentEpochTime, ShadowULong timeout)
{
	ShadowSignalerData* data = ExtractRawPointer(shadowdata);

	ShadowULong absoluteNanos = currentEpochTime + timeout;
	struct timespec time = { absoluteNanos / NANOS_IN_SECONDS, absoluteNanos % NANOS_IN_SECONDS };
	
	pthread_mutex_lock(&data->mutex);
	int result = pthread_cond_timedwait(&data->cond, &data->mutex, &time);
	pthread_mutex_unlock(&data->mutex);
	
	return (result != 0);
}

/// true on success, otherwise, false.
ShadowBoolean __ShadowSignaler_Broadcast(ShadowPointer shadowdata)
{
	ShadowSignalerData* data = ExtractRawPointer(shadowdata);
	
	return (pthread_cond_broadcast(&data->cond) == 0);
}