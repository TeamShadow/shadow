/**
 * Author: Claude Abounegm
 */
#include "ShadowCurrentThread.h"

#include <stddef.h>
#include <pthread.h>
#include <stdlib.h>

ShadowBoolean __ShadowCurrentThread_Yield(void)
{
	return pthread_yield() == 0;
}