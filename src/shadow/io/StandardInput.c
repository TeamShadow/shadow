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

shadow_long_t __shadow_io__StandardInput_setUp()
{
 #ifdef SHADOW_WINDOWS
 	return (shadow_long_t)GetStdHandle(STD_INPUT_HANDLE);
 #else
    return (shadow_long_t)STDIN_FILENO;
 #endif
}