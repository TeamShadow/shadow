/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <standard/Thread.h>
#include <stddef.h>
#include <stdlib.h>

// METHOD SIGNATURES //
//*HELPERS*
/**
 * The method that will be execute in the new spawned thread.
 * This is a wrapper which calls the actual thread start on the
 * Shadow side. It is here to allow the Thread.native.ll to set
 * the current Thread-Local-Storage thread to the newly spawned
 * thread.
**/ 
void* _shadow_standard__Thread_threadStart(shadow_Thread_t*);
// METHOD SIGNATURES //

shadow_Pointer_t* __shadow_standard__Thread_spawn(shadow_Thread_t* _this)
{
	return _shadow_natives__Pointer_create(malloc(1), SHADOW_CAN_FREE);
}