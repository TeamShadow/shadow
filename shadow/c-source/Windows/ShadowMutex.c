/**
 * Author: Claude Abounegm
 */
#include "ShadowMutex.h"

#include <stddef.h>
#include <stdlib.h>
#include <pthread.h>

ShadowPointer __ShadowMutex_Initialize(ShadowMutex this)
{
	pthread_mutex_t* mutex = malloc(sizeof(pthread_mutex_t));
	if(pthread_mutex_init(mutex, NULL) != 0) {
		free(mutex);
		mutex = NULL;
	}

	return CreateShadowPointer(mutex);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowMutex this, ShadowPointer pointer)
{
	return (pthread_mutex_destroy(ExtractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Lock(ShadowMutex this, ShadowPointer pointer)
{
	return (pthread_mutex_lock(ExtractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Unlock(ShadowMutex this, ShadowPointer pointer)
{
	return (pthread_mutex_unlock(ExtractRawPointer(pointer)) == 0);
}