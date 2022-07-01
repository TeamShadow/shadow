/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>


#ifdef SHADOW_WINDOWS
	#include <Windows.h>
#endif

void __shadow_io__Console_initialize(void)
{
#ifdef SHADOW_WINDOWS
    SetConsoleCP(65001);
    SetConsoleOutputCP(65001);
#endif
}