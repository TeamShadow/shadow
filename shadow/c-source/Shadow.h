/**
 * This is the main header file which should be used 
 *
 * Author: Claude Abounegm
 */
#ifndef SHADOW_H
#define SHADOW_H

/**
 * The managed pointer which is the main way to pass handles/pointers between C, LLVM and Shadow code.
 * This is the C pointer representation for shadow:natives@ShadowPointer.
 */
typedef void* ShadowPointer;

/// This is the C pointer representation for shadow:standard@Thread
typedef void* ShadowThread;

typedef void* ShadowString;

typedef void* ShadowArray;

/**
 * Creates a managed shadow:natives@ShadowPointer from the void pointer. A NULL pointer
 * passed as the void pointer simply means that the ShadowPointer initialized is invalid,
 * and this signifies that something went wrong.
 *
 * usage:
 *	ShadowPointer __ShadowTest_DoSomething() {
 *		return __createShadowPointer(malloc(sizeof(somethingUseful)));
 *	}
 */
ShadowPointer __createShadowPointer(void*);

/**
 * Gets the actual pointer from the managed ShadowPointer. This is the raw pointer which was
 * allocated using malloc or calloc.
 */
void* __extractRawPointer(ShadowPointer);

// boolean
typedef char ShadowBoolean;

// byte
typedef signed char ShadowByte;

// ubyte
typedef unsigned char ShadowUByte;

// short
typedef signed short ShadowShort;

// ushort
typedef unsigned short ShadowUShort;

// int
typedef signed long ShadowInt;

// uint
typedef unsigned long ShadowUInt;

// code
typedef signed long ShadowCode;

// long
typedef signed long long ShadowLong;

// ulong
typedef unsigned long long ShadowULong;

// float
typedef float ShadowFloat;

// double
typedef double ShadowDouble;

#include "include/ShadowConsole.h"
#include "include/ShadowMutex.h"
#include "include/ShadowSignaler.h"
#include "include/ShadowSystem.h"
#include "include/ShadowThread.h"
#include "include/ShadowString.h"
#include "include/ShadowArray.h"

#endif