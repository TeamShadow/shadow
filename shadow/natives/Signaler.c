/**
 * Author: Claude Abounegm
 */
#include <natives/Signaler.h>
#include <natives/Pointer.h>
#include <stdlib.h>

// METHOD SIGNATURES //
shadow_Pointer_t* __ShadowSignaler_Initialize(void);
shadow_boolean_t __ShadowSignaler_Destroy(shadow_Pointer_t*);

shadow_boolean_t __ShadowSignaler_Wait(shadow_Pointer_t*);
shadow_boolean_t __ShadowSignaler_WaitTimeout(shadow_Pointer_t*, shadow_ulong_t, shadow_ulong_t);
shadow_boolean_t __ShadowSignaler_Broadcast(shadow_Pointer_t*);
// METHOD SIGNATURES //


shadow_Pointer_t* __ShadowSignaler_Initialize(void)
{
	return CreateShadowPointer(malloc(1), SHADOW_CAN_FREE);
}

shadow_boolean_t __ShadowSignaler_Destroy(shadow_Pointer_t* shadowdata)
{
	return false;
}

shadow_boolean_t __ShadowSignaler_Wait(shadow_Pointer_t* shadowdata)
{
	return false;
}

shadow_boolean_t __ShadowSignaler_WaitTimeout(shadow_Pointer_t* shadowdata, shadow_ulong_t currentEpochTime, shadow_ulong_t timeout)
{
	return false;
}

shadow_boolean_t __ShadowSignaler_Broadcast(shadow_Pointer_t* shadowdata)
{
	return false;
}
