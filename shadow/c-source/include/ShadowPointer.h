#ifndef SHADOW_POINTER_H
#define SHADOW_POINTER_H

/**
 * The managed pointer which is the main way to pass handles/pointers between C, LLVM and Shadow code.
 * This is the C pointer representation for shadow:natives@ShadowPointer.
 */
typedef void* ShadowPointer;

/**
 * Creates a managed shadow:natives@ShadowPointer from the void pointer. A NULL pointer
 * passed as the void pointer simply means that the ShadowPointer initialized is invalid,
 * and this signifies that something went wrong.
 *
 * usage:
 *	ShadowPointer __ShadowTest_DoSomething() {
 *		return CreateShadowPointer(malloc(sizeof(somethingUseful)), 1);
 *	}
 */
ShadowPointer CreateShadowPointer(void* ptr, ShadowBoolean shouldFree);

/**
 * Gets the actual pointer from the managed ShadowPointer. This is the raw pointer which was
 * allocated using malloc or calloc.
 */
void* ExtractRawPointer(ShadowPointer);

#endif