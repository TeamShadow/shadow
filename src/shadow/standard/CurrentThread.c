/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/CurrentThread.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	shadow_boolean_t __shadow_standard__CurrentThread_yield(shadow_CurrentThread_t* _this)
	{
		Sleep(0);
		return TRUE;
	}
#else
	#include <sched.h>

	shadow_boolean_t __shadow_standard__CurrentThread_yield(shadow_CurrentThread_t* _this)
	{
		return sched_yield() == 0;
	}
#endif