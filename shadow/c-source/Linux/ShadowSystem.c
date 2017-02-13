/**
 * Author: Claude Abounegm
 */

#include "ShadowSystem.h"
#include <time.h>

static ShadowULong GetNanoTime(clockid_t id)
{
	struct timespec tp;
	clock_gettime(id, &tp);
	return (ShadowULong)tp.tv_sec * 1000000000 + tp.tv_nsec;
}

ShadowULong __ShadowSystem_GetNanoTime(void)
{
	return GetNanoTime(CLOCK_MONOTONIC);
}

ShadowULong __ShadowSystem_GetEpochNanoTime(void)
{
	return GetNanoTime(CLOCK_REALTIME);
}