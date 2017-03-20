/**
 * Author: Claude Abounegm
 */
#include "ShadowCurrentThread.h"

#include <Windows.h>

ShadowBoolean __ShadowCurrentThread_Yield(void)
{
	Sleep(0);
	return true;
}