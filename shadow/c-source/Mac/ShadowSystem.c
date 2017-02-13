/**
 * Author: Claude Abounegm
 */

#include "ShadowSystem.h"

#include <mach/clock.h>
#include <mach/mach.h>

static ShadowULong GetNanoTime(clock_id_t id)
{
	clock_serv_t cclock;
	mach_timespec_t tp;

	host_get_clock_service(mach_host_self(), id, &cclock);
	clock_get_time(cclock, &tp);
	mach_port_deallocate(mach_task_self(), cclock);

	return (ShadowULong)tp.tv_sec * 1000000000 + tp.tv_nsec;
}

ShadowULong __ShadowSystem_GetNanoTime(void)
{
	return GetNanoTime(SYSTEM_CLOCK);
}

ShadowULong __ShadowSystem_GetEpochNanoTime(void)
{
	return GetNanoTime(CALENDAR_CLOCK);
}