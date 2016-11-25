/**
 * Author: Claude Abounegm
 */

#include "ShadowSystem.h"

#include <Windows.h>

#define OFFSET_FROM_1601_TO_1970 116444736000000000ULL

ShadowULong __ShadowSystem_GetNanoTime(ShadowSystem this)
{
	union {
		ShadowULong	actual;
		LARGE_INTEGER li;
	} freq, counter;
	
	if(!QueryPerformanceFrequency(&freq.li)) {
		return 0;
	}
	
	if(!QueryPerformanceCounter(&counter.li)) {
		return 0;
	}
	
	return (counter.actual * 1000000000ULL / freq.actual);
}

ShadowULong __ShadowSystem_GetEpochNanoTime(ShadowSystem this)
{
	union {
		ShadowULong actual;
		FILETIME ft;
	} now;
	
    GetSystemTimeAsFileTime(&now.ft);

    return (now.actual - OFFSET_FROM_1601_TO_1970) * 100;
}