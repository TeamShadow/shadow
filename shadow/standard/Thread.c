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
void* _shadowThread_ThreadStart(shadow_Thread_t*);

// *to be implemented*
/// The method called from Shadow to spawn a new thread
shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t*);
// METHOD SIGNATURES //

shadow_Pointer_t* __ShadowThread_Spawn(shadow_Thread_t* _this)
{
	return shadowPointer_Create(malloc(1), SHADOW_CAN_FREE);
}