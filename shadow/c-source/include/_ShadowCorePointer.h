#ifndef SHADOW_CORE_POINTER_H
#define SHADOW_CORE_POINTER_H

#include <natives/Pointer.h>

typedef enum {
	// This means that the method ShadowPointer.free() will not call C's free() function
	// when called. This is used when the pointer was allocated using a function such as
	// CreateEvent() which requires a CloseHandle() instead of a free().
	SHADOW_CANNOT_FREE = 0,
	
	// This means that the method ShadowPointer.free() will call C's free() function,
	// this enum is usually used when the pointer was allocated using malloc or some equivalent
	// function which requires a subsequent free() call.
	SHADOW_CAN_FREE = 1
} free_type_t;

void* _shadow_ExtractPointer(shadow_Pointer_t*);
shadow_Pointer_t* _shadow_CreatePointer(void* ptr, free_type_t type);

/**
 * Creates a managed shadow:natives@ShadowPointer from the void pointer. A NULL pointer
 * initializes an invalid ShadowPointer, which tells Shadow code that something went wrong.
 *
 * usage:
 *	ShadowPointer __ShadowTest_DoSomething() {
 *		return shadow_CreatePointer(malloc(sizeof(UsefulStruct)), SHADOW_CAN_FREE);
 *	}
 */
#define shadow_CreatePointer _shadow_CreatePointer

/**
 * Works the same as ExtractRawPointer() but returns a Type* instead of a void*.
 * With the RawPointer format we got to do a cast as follows: (SomethingUseful*)ExtractRawPointer(ptr),
 * with this marco we can do ExtractPointer(SomethingUseful, ptr) and it returns SomethingUseful*.
 */
#define shadow_ExtractPointer(T, ShadowPointer) ((T*)_shadow_ExtractPointer(ShadowPointer))

#endif