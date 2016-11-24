#include <stddef.h>
#include <pthread.h>
#include <stdlib.h>
#include "../Shadow.h"

ShadowPointer __ShadowThread_Spawn(void* (*thread_start)(ShadowThread), ShadowThread currentThread)
{
	pthread_t* ptr = malloc(sizeof(pthread_t));
	if(pthread_create(ptr, NULL, thread_start, currentThread) != 0) {
		free(ptr);
		ptr = NULL;
	}
	
	return __createShadowPointer(ptr);
}

ShadowBoolean __ShadowThread_Yield()
{
	return (sched_yield() == 0);
}