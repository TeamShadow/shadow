/**
 * Author: Claude Abounegm
 */
#include <standard/Thread.h>

#include <stddef.h>
#include <stdlib.h>
#include <pthread.h>

shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t* this, void* (*thread_start)(void*))
{
	/*
	pthread_t* ptr = malloc(sizeof(pthread_t));
	if(pthread_create(ptr, NULL, thread_start, this) != 0) {
		free(ptr);
		ptr = NULL;
	}
	*/
	
	//return shadow_CreatePointer(ptr, SHADOW_CAN_FREE);
	return shadow_CreatePointer(malloc(1), SHADOW_CAN_FREE);
}