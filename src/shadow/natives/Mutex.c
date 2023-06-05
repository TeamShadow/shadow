/**
 * Author: Claude Abounegm
 */
#include <natives/Mutex.h>
#include <natives/Pointer.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	typedef struct {
    	BOOL allowRecursive;
    	shadow_Thread_t owner;

    	HANDLE lockEvent;
    	LONG lockCount;

    	volatile LONG currentTurn;
    	volatile LONG totalTurns;
    } ShadowMutexData;
#else

#endif



shadow_Pointer_t __shadow_natives__Mutex_initialize(shadow_boolean_t allowRecursive)
{
	ShadowMutexData* data = calloc(1, sizeof(ShadowMutexData));
	data->lockEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
	data->allowRecursive = allowRecursive;
	return _shadow_natives__Pointer_create(data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__Mutex_destroy(shadow_Pointer_t* pointer)
{
	return CloseHandle(_shadow_natives__Pointer_extract(ShadowMutexData, pointer)->lockEvent);
}

shadow_boolean_t __shadow_natives__Mutex_lock(shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
	ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
	if(data->owner != currentThread) {
		LONG turn = InterlockedIncrement(&data->totalTurns) - 1;

		while(turn != data->currentTurn) {
			WaitForSingleObject(data->lockEvent, INFINITE);

			// my reasoning here is that if each thread yields to another one,
			// that would allow to get the thread which has its turn to actually run
			// and in turn reset event.
			if(turn != data->currentTurn) {
				Sleep(0); // yield
			}
		}
		ResetEvent(data->lockEvent);

		data->owner = currentThread;
	} else if(!data->allowRecursive) {
		return false;
	}

	++data->lockCount;
	return true;
}

shadow_boolean_t __shadow_natives__Mutex_unlock(shadow_Pointer_t* pointer)
{
	ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
	DWORD retValue = 0;
	if(data->lockCount == 1) {
		data->lockCount = 0;
		data->owner = NULL;
		InterlockedIncrement(&data->currentTurn);
		retValue = SetEvent(data->lockEvent);
	} else {
		--data->lockCount;
	}

	return (retValue != 0);
}

shadow_Thread_t __shadow_natives__Mutex_getOwner(shadow_Pointer_t* pointer)
{
	return _shadow_natives__Pointer_extract(ShadowMutexData, pointer)->owner;
}