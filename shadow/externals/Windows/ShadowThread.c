#include <stddef.h>
#include <pthread.h>
#include <stdlib.h>
#include "../Shadow.h"

ShadowPointer __ShadowThread_Spawn(void* (*start_routine)(void*), void* arg)
{
	pthread_t* ptr = malloc(sizeof(pthread_t));
	if(pthread_create(ptr, NULL, start_routine, arg) != 0) {
		free(ptr);
		ptr = NULL;
	}
	
	return __createShadowPointer(ptr);
}