#include "../Shadow.h"
#include <stddef.h>
#include <stdlib.h>
#include <pthread.h>

ShadowPointer __ShadowMutex_Initialize()
{
	pthread_mutex_t* mutex = malloc(sizeof(pthread_mutex_t));
	if(pthread_mutex_init(mutex, NULL) != 0) {
		free(mutex);
		mutex = NULL;
	}
	
	return __createShadowPointer(mutex);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowPointer pointer)
{
	return (pthread_mutex_destroy(__extractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Lock(ShadowPointer pointer)
{
	return (pthread_mutex_lock(__extractRawPointer(pointer)) == 0);
}

ShadowBoolean __ShadowMutex_Unlock(ShadowPointer pointer)
{
	return (pthread_mutex_unlock(__extractRawPointer(pointer)) == 0);
}