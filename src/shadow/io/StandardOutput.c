/**
 * Author: Barry Wittman
 */
#include <Shadow.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
#else
	#include <unistd.h>
	#include <fcntl.h>
#endif

shadow_long_t __shadow_io__StandardOutput_setUp(shadow_boolean_t error)
{
 #ifdef SHADOW_WINDOWS
    if (error)
 	    return (shadow_long_t)GetStdHandle(STD_ERROR_HANDLE);
 	else
 	    return (shadow_long_t)GetStdHandle(STD_OUTPUT_HANDLE);
 #else
    if (error)
        return (shadow_long_t)STDERR_FILENO;
    else
        return (shadow_long_t)STDOUT_FILENO;
 #endif
}