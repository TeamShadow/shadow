/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/System.h>

// METHOD SIGNATURES //
shadow_ulong_t __ShadowSystem_GetNanoTime(void);
shadow_ulong_t __ShadowSystem_GetEpochNanoTime(void);
// METHOD SIGNATURES //

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
	#define OFFSET_FROM_1601_TO_1970 116444736000000000ULL
#elif SHADOW_MAC
	#include <mach/clock.h>
	#include <mach/mach.h>

	static shadow_ulong_t get_nano_time(clock_id_t id)
	{
		clock_serv_t cclock;
		mach_timespec_t tp;

		host_get_clock_service(mach_host_self(), id, &cclock);
		clock_get_time(cclock, &tp);
		mach_port_deallocate(mach_task_self(), cclock);

		return (shadow_ulong_t)tp.tv_sec * 1000000000 + tp.tv_nsec;
	}
#else
	#include <time.h>

	static shadow_ulong_t get_nano_time(clockid_t id)
	{
		struct timespec tp;
		clock_gettime(id, &tp);
		return (shadow_ulong_t)tp.tv_sec * 1000000000 + tp.tv_nsec;
	}
#endif

shadow_ulong_t __ShadowSystem_GetNanoTime(void)
{
	#ifdef SHADOW_WINDOWS
		union {
			shadow_ulong_t	actual;
			LARGE_INTEGER li;
		} freq, counter;
		
		if(!QueryPerformanceFrequency(&freq.li)) {
			return 0;
		}
		
		if(!QueryPerformanceCounter(&counter.li)) {
			return 0;
		}
		
		return (counter.actual * 1000000000ULL / freq.actual);

	#elif SHADOW_MAC
		return get_nano_time(SYSTEM_CLOCK);

	#else
		return get_nano_time(CLOCK_MONOTONIC);
	#endif
}

shadow_ulong_t __ShadowSystem_GetEpochNanoTime(void)
{
	#ifdef SHADOW_WINDOWS
		union {
			shadow_ulong_t actual;
			FILETIME ft;
		} now;
		
		GetSystemTimeAsFileTime(&now.ft);

		return (now.actual - OFFSET_FROM_1601_TO_1970) * 100;

	#elif SHADOW_MAC
		return get_nano_time(CALENDAR_CLOCK);

	#else
		return get_nano_time(CLOCK_REALTIME);
	#endif
}