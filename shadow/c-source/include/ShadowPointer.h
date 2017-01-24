#ifndef SHADOW_POINTER_H
#define SHADOW_POINTER_H

/**
 * The managed pointer which is the main way to pass handles/pointers between C, LLVM and Shadow code.
 * This is the C pointer representation for shadow:natives@ShadowPointer.
 */
typedef void* ShadowPointer;

typedef enum {
	// This means that the method ShadowPointer.free() will not call C's free() function
	// when called. This is used when the pointer was allocated using a function such as
	// CreateEvent() which requires a CloseHandle() instead of a free().
	SHADOW_CANNOT_FREE = 0,
	
	// This means that the method ShadowPointer.free() will call C's free() function,
	// this enum is usually used when the pointer was allocated using malloc or some equivalent
	// function which requires a subsequent free() call.
	SHADOW_CAN_FREE = 1
} ShadowPointerType;

/**
 * Creates a managed shadow:natives@ShadowPointer from the void pointer. A NULL pointer
 * initializes an invalid ShadowPointer, which tells Shadow code that something went wrong.
 *
 * usage:
 *	ShadowPointer __ShadowTest_DoSomething() {
 *		return CreateShadowPointer(malloc(sizeof(UsefulStruct)), SHADOW_CAN_FREE);
 *	}
 */
ShadowPointer CreateShadowPointer(void* ptr, ShadowPointerType type);

/**
 * Gets the actual pointer from the managed ShadowPointer. This is the raw pointer which was
 * allocated using malloc or calloc.
 */
void* ExtractRawPointer(ShadowPointer);

#define ExtractPointer(Type, ShadowPointer) ((Type*)ExtractRawPointer(ShadowPointer))

#endif