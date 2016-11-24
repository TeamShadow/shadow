/**
 * Author: Claude Abounegm
 */

#include "../Shadow.h"
#include <Windows.h>

void __ShadowConsole_Initialize(void)
{
	SetConsoleCP(65001);
	SetConsoleOutputCP(65001);
}