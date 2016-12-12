/**
 * Author: Claude Abounegm
 */
#include "ShadowMutex.h"

#include <Windows.h>

typedef struct {
	BOOL allowRecursive;
	ShadowThread owner;
	
	HANDLE lockEvent;
	LONG lockCount;
	
	volatile LONG currentTurn;
	volatile LONG totalTurns;
} ShadowMutexData;

ShadowPointer __ShadowMutex_Initialize(ShadowBoolean allowRecursive)
{
	ShadowMutexData* data = calloc(1, sizeof(ShadowMutexData));
	data->lockEvent = CreateEvent(NULL, TRUE, FALSE, NULL);
	data->allowRecursive = allowRecursive;
	return CreateShadowPointer(data, true);
}

ShadowBoolean __ShadowMutex_Destroy(ShadowPointer pointer)
{
	return CloseHandle(((ShadowMutexData*)ExtractRawPointer(pointer))->lockEvent);
}

ShadowBoolean __ShadowMutex_Lock(ShadowPointer pointer, ShadowThread currentThread)
{
	ShadowMutexData* data = ExtractRawPointer(pointer);
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

ShadowBoolean __ShadowMutex_Unlock(ShadowPointer pointer)
{
	ShadowMutexData* data = ExtractRawPointer(pointer);
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

ShadowThread __ShadowMutex_GetOwner(ShadowPointer pointer)
{
	return ((ShadowMutexData*)ExtractRawPointer(pointer))->owner;
}