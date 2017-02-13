/**
 * Author: Claude Abounegm
 */
#include "ShadowSignaler.h"
#include <stdlib.h>

ShadowPointer __ShadowSignaler_Initialize(void)
{
	return CreateShadowPointer(malloc(1), SHADOW_CAN_FREE);
}

ShadowBoolean __ShadowSignaler_Destroy(ShadowPointer shadowdata)
{
	return false;
}

ShadowBoolean __ShadowSignaler_Wait(ShadowPointer shadowdata)
{
	return false;
}

ShadowBoolean __ShadowSignaler_WaitTimeout(ShadowPointer shadowdata, ShadowULong currentEpochTime, ShadowULong timeout)
{
	return false;
}

ShadowBoolean __ShadowSignaler_Broadcast(ShadowPointer shadowdata)
{
	return false;
}
