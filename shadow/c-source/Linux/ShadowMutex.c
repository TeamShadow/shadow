/**
 * Author: Claude Abounegm
 */
#include "ShadowMutex.h"

#include <stddef.h>
#include <stdlib.h>
#include <pthread.h>

ShadowPointer __ShadowMutex_Initialize(void)
{
	pthread_mutex_t* handle = malloc(sizeof(pthread_mutex_t));
	if(pthread_mutex_init(handle, NULL) != 0) {
		free(handle);
		handle = NULL;
	}
	
	return CreateShadowPointer(handle, 1);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowPointer pointer)
{
	return (pthread_mutex_destroy(ExtractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Lock(ShadowPointer pointer)
{
	return (pthread_mutex_lock(ExtractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Unlock(ShadowPointer pointer)
{
	return (pthread_mutex_unlock(ExtractRawPointer(pointer)) == 0);
}