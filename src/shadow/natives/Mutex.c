/**
 * Author: Claude Abounegm
 * Author: Barry Wittman
 */
#include <natives/Mutex.h>
#include <natives/Pointer.h>

#ifdef SHADOW_WINDOWS
	#include <Windows.h>

	typedef struct {
        shadow_Thread_t* owner;
        LONG counter;
        CRITICAL_SECTION   criticalSection;

    } ShadowMutexData;
#else
    #include <pthread.h>

	typedef struct {
        shadow_Thread_t* owner;
        long counter;
        pthread_mutex_t  mutex;
    } ShadowMutexData;
#endif

shadow_Pointer_t __shadow_natives__Mutex_initialize(shadow_Mutex_t* _this)
{
	ShadowMutexData* data = calloc(1, sizeof(ShadowMutexData));
#ifdef SHADOW_WINDOWS
	InitializeCriticalSection (&data->criticalSection);
#else
    pthread_mutexattr_t attributes;
    pthread_mutexattr_init(&attributes);
    pthread_mutexattr_settype(&attributes, PTHREAD_MUTEX_RECURSIVE);
    pthread_mutex_init(&data->mutex, &attributes);
#endif
	return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

shadow_boolean_t __shadow_natives__Mutex_destroy(shadow_Mutex_t* _this, shadow_Pointer_t* pointer)
{
    ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
    if (data->owner)
        return 0; // false
#ifdef SHADOW_WINDOWS
	DeleteCriticalSection(&data->criticalSection);
	return 1; // true
#else
    return pthread_mutex_destroy(&data->mutex) == 0;
#endif
}

void __shadow_natives__Mutex_lock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
	ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
#ifdef SHADOW_WINDOWS
	EnterCriticalSection(&data->criticalSection);
#else
    pthread_mutex_lock(&data->mutex);
#endif
	data->counter++;
    data->owner = currentThread;
}

shadow_boolean_t __shadow_natives__Mutex_unlock(shadow_Mutex_t* _this, shadow_Pointer_t* pointer, shadow_Thread_t* currentThread)
{
    ShadowMutexData* data = _shadow_natives__Pointer_extract(ShadowMutexData, pointer);
    if (data->owner != currentThread)
        return 0; // false

    data->counter--;

    if (data->counter < 0)
        return 0; // false
    if (data->counter == 0)
        data->owner = NULL;
#ifdef SHADOW_WINDOWS
	LeaveCriticalSection (&data->criticalSection);
    return TRUE;
#else
    return pthread_mutex_unlock(&data->mutex) == 0;
#endif
}

shadow_Thread_t __shadow_natives__Mutex_getOwner(shadow_Mutex_t* _this, shadow_Pointer_t* pointer)
{
	return _shadow_natives__Pointer_extract(ShadowMutexData, pointer)->owner;
}