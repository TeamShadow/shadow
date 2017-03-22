/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <standard/CurrentThread.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	shadow_boolean_t __ShadowCurrentThread_Yield(void)
	{
		Sleep(0);
		return true;
	}
#else
	#include <stddef.h>
	#include <pthread.h>
	#include <stdlib.h>

	shadow_boolean_t __ShadowCurrentThread_Yield(void)
	{
		return pthread_yield() == 0;
	}
#endif