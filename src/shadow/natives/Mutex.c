/**
 * Author: Claude Abounegm
 * Author: Barry Wittman
 */
#include <natives/Mutex.h>
#include <natives/Pointer.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	typedef struct {
	/*
    	BOOL allowRecursive;
    	shadow_Thread_t* owner;

    	HANDLE lockEvent;
    	LONG lockCount;

    	volatile LONG currentTurn;
    	volatile LONG totalTurns;
    	*/

        shadow_Thread_t* owner;
        LONG counter;
        CRITICAL_SECTION   criticalSection;

    } ShadowMutexData;
#else

#endif



shadow_Pointer_t __shadow_natives__Mutex_initialize(shadow_Mutex_t* _this)
{
	ShadowMutexData* data = calloc(1, sizeof(ShadowMutexData));
	InitializeCriticalSection (&data->criticalSection);
	return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__Mutex_destroy(shadow_Mutex_t* _this, shadow_Pointer_t* pointer)
{
    ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
    if (data->owner)
        return FALSE;
	DeleteCriticalSection(&data->criticalSection);
	return TRUE;
}

void __shadow_natives__Mutex_lock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
	ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
	EnterCriticalSection(&data->criticalSection);
	data->counter++;
    data->owner = currentThread;
}

shadow_boolean_t __shadow_natives__Mutex_unlock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
    ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
    if (data->owner != currentThread)
        return FALSE;

    data->counter--;

    if (data->counter < 0)
        return FALSE;
    if (data->counter == 0)
        data->owner = NULL;

	LeaveCriticalSection (&data->criticalSection);
    return TRUE;
}

shadow_Thread_t __shadow_natives__Mutex_getOwner(shadow_Mutex_t* _this, shadow_Pointer_t* pointer)
{
	return _shadow_natives__Pointer_extract(ShadowMutexData, pointer)->owner;
}