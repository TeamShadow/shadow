/**
 * Author: Claude Abounegm
 */
#include "ShadowThread.h"

#include <stddef.h>
#include <pthread.h>
#include <stdlib.h>

ShadowPointer __ShadowThread_Spawn(ShadowThread this, void* (*thread_start)(ShadowThread))
{
	pthread_t* ptr = malloc(sizeof(pthread_t));
	if(pthread_create(ptr, NULL, thread_start, this) != 0) {
		free(ptr);
		ptr = NULL;
	}
	
	return CreateShadowPointer(ptr);
}

ShadowBoolean __ShadowCurrentThread_Yield(void)
{
	return (sched_yield() == 0);
}