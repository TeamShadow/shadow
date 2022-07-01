/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/CurrentThread.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	shadow_boolean_t __shadow_standard__CurrentThread_yield(void)
	{
		Sleep(0);
		return true;
	}
#else
	#include <sched.h>

	shadow_boolean_t __shadow_standard__CurrentThread_yield(void)
	{
		return sched_yield() == 0;
	}
#endif