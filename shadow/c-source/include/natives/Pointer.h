#ifndef SHADOW_POINTER_H
#define SHADOW_POINTER_H

#include <ShadowCore.h>

/**
 * The managed pointer which is the main way to pass handles/pointers between C, LLVM and Shadow code.
 * This is the C pointer representation for shadow:natives@Pointer.
 */
typedef void* shadow_Pointer_t;

void __ShadowPointer_Free(void* ptr);

#endif