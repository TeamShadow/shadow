/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/System.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
	#include <winreg.h>
	#include <stdio.h>
    #include <string.h>
	#define OFFSET_FROM_1601_TO_1970 116444736000000000ULL
	#define VERSION_REGISTRY_KEY "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion"
	#pragma warning(disable : 4996)
#elif defined SHADOW_MAC
	#include <mach/clock.h>
	#include <mach/mach.h>
	#include <stdlib.h>
	#include <sys/utsname.h>

	static shadow_long_t get_nano_time(clock_id_t id)
	{
		clock_serv_t cclock;
		mach_timespec_t tp;

		host_get_clock_service(mach_host_self(), id, &cclock);
		clock_get_time(cclock, &tp);
		mach_port_deallocate(mach_task_self(), cclock);

		return (shadow_long_t)tp.tv_sec * 1000000000L + tp.tv_nsec;
	}
#else
	#include <time.h>
	#include <stdlib.h>
	#include <sys/utsname.h>

	static shadow_ulong_t get_nano_time(clockid_t id)
	{
		struct timespec tp;
		clock_gettime(id, &tp);
		return (shadow_ulong_t)tp.tv_sec * 1000000000 + tp.tv_nsec;
	}
#endif


shadow_long_t __shadow_standard__System_getEpochNanoTime(shadow_System_t* _this)
{
#ifdef SHADOW_WINDOWS
    FILETIME now;
    GetSystemTimeAsFileTime(&now);
    ULARGE_INTEGER time;
    time.LowPart = now.dwLowDateTime;
    time.HighPart = now.dwHighDateTime;
    return ((shadow_long_t)time.QuadPart - OFFSET_FROM_1601_TO_1970) * 100;
#elif defined SHADOW_MAC
	return get_nano_time(CALENDAR_CLOCK);
#else
	return get_nano_time(CLOCK_REALTIME);
#endif
}


shadow_long_t __shadow_standard__System_getNanoTime(shadow_System_t* _this)
{
#ifdef SHADOW_WINDOWS
		LARGE_INTEGER freq;
		LARGE_INTEGER counter;

		if(!QueryPerformanceFrequency(&freq)) {
			return 0;
		}
		if(!QueryPerformanceCounter(&counter)) {
			return 0;
		}
		return (shadow_long_t)(counter.QuadPart * 1000000000LL / freq.QuadPart);
#elif defined SHADOW_MAC
		return get_nano_time(SYSTEM_CLOCK);
#else
		return get_nano_time(CLOCK_MONOTONIC);
#endif
}

shadow_boolean_t __shadow_standard__System_isWindows(shadow_System_t* _this)
{
#ifdef SHADOW_WINDOWS
    return 1;
#else
    return 0;
#endif
}

shadow_String_t* __shadow_standard__System_getEnvironment(shadow_System_t* _this, shadow_String_t* input)
{
	char* variable = __shadow_standard__String_getCString(input);
	shadow_String_t* value = NULL;
#ifdef SHADOW_WINDOWS
    char buffer[32767];
    if (GetEnvironmentVariable(variable, buffer, sizeof(buffer)) != 0)
        value = __shadow_standard__String_create(buffer);
#else
    char* result = getenv(variable);
    if (result != NULL)
        value = __shadow_standard__String_create(result);
#endif
    free(variable);
    return value;
}

shadow_String_t* __shadow_standard__System_osName(shadow_System_t* _this)
{
    shadow_String_t* value = NULL;
#ifdef SHADOW_WINDOWS
    HKEY key;
    bool error = true;
    if (RegOpenKeyExA(HKEY_LOCAL_MACHINE, VERSION_REGISTRY_KEY, 0, KEY_QUERY_VALUE, &key) == ERROR_SUCCESS) {
        unsigned char buffer[1024];
        DWORD size = sizeof(buffer);
        if (RegQueryValueExA(key, "ProductName", NULL, NULL, buffer, &size) == ERROR_SUCCESS) {
            value = __shadow_standard__String_create((const char*)buffer);
            error = false;
        }
        RegCloseKey(key);
    }

    if (error)
        value = __shadow_standard__String_create("Windows");
#elif defined SHADOW_MAC
    struct utsname data;
    if (uname(&data) == 0)
        value = __shadow_standard__String_create(data.sysname);
    else
        value = __shadow_standard__String_create("Darwin");
#else
    struct utsname data;
    if (uname(&data) == 0)
        value = __shadow_standard__String_create(data.sysname);
    else
        value = __shadow_standard__String_create("Linux");
#endif
    return value;
}

shadow_String_t* __shadow_standard__System_osVersion(shadow_System_t* _this)
{
    shadow_String_t* value = NULL;
#ifdef SHADOW_WINDOWS
    HKEY key;
    DWORD major = 0;
    DWORD minor = 0;
    if (RegOpenKeyExA(HKEY_LOCAL_MACHINE, VERSION_REGISTRY_KEY, 0, KEY_QUERY_VALUE, &key) == ERROR_SUCCESS) {
        DWORD size = sizeof(DWORD);
        if (RegQueryValueExA(key, "CurrentMajorVersionNumber", NULL, NULL, (unsigned char*)&major, &size) == ERROR_SUCCESS) {
            size = sizeof(DWORD);
            RegQueryValueExA(key, "CurrentMinorVersionNumber", NULL, NULL, (unsigned char*)&minor, &size);
        }
        RegCloseKey(key);
    }
    char buffer[1024];
    sprintf_s(buffer, sizeof(buffer), "%d.%d", (int)major, (int)minor);
    value = __shadow_standard__String_create(buffer);
#else
    struct utsname data;
    if (uname(&data) == 0)
        value = __shadow_standard__String_create(data.release);
    else
        value = __shadow_standard__String_create("0.0");
#endif
    return value;
}
