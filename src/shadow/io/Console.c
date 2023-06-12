/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <io/Console.h>
#include <stdio.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
#endif

void __shadow_io__Console_initialize(shadow_io_Console_t* _this)
{
#ifdef SHADOW_WINDOWS
    SetConsoleCP(65001);
    SetConsoleOutputCP(65001);
#endif
}