/**
 * Author: Claude Abounegm
 */
#include "ShadowThread.h"

#include <stddef.h>
#include <pthread.h>

#include <stdlib.h>
#include <Windows.h>

ShadowPointer __ShadowThread_Spawn(ShadowThread this, void* (*thread_start)(ShadowThread))
{
	pthread_t* ptr = malloc(sizeof(pthread_t));
	if(pthread_create(ptr, NULL, thread_start, this) != 0) {
		free(ptr);
		ptr = NULL;
	}
	
	return CreateShadowPointer(ptr, SHADOW_CAN_FREE);
}