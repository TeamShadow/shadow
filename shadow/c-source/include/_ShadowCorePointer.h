#ifndef SHADOW_CORE_POINTER_H
#define SHADOW_CORE_POINTER_H

#include <natives/Pointer.h>

typedef enum {
	// This means that the method Pointer.free() will not call C's free() function
	// when called. This is used when the pointer was allocated using a function such as
	// CreateEvent() which requires a CloseHandle() instead of a free().
	SHADOW_CANNOT_FREE = 0,
	
	// This means that the method Pointer.free() will call C's free() function,
	// this enum is usually used when the pointer was allocated using malloc or some equivalent
	// function which requires a subsequent free() call.
	SHADOW_CAN_FREE = 1
} free_type_t;

// "private" methods
void* _shadow_ExtractPointer(shadow_Pointer_t*);
shadow_Pointer_t* _shadow_CreatePointer(void* ptr, free_type_t type);

/**
 * Creates a shadow:natives@Pointer object, which holds the address of the passed pointer. This
 * is necessary to be able to store the reference in Shadow code, as Objects in Shadow have a
 * specific structure. The pointer can then be retrieved again using shadow_CreatePointer() method.
 * 
 * params:
 *   ptr: a void* pointer to the desired reference
 *   type: free_type_t, which can be either SHADOW_CANNOT_FREE, or SHADOW_CAN_FREE.
 *
 * usage:
 *	C:
 *		shadow_Pointer_t* __ShadowTest_CreateSomethingUseful() {
 *			return shadow_CreatePointer(malloc(sizeof(UsefulStruct)), SHADOW_CAN_FREE);
 *		}
 *
 * 	Shadow:
 *		private extern __ShadowTest_CreateSomethingUseful() => (Pointer);
 */
#define shadow_CreatePointer(ptr, type) _shadow_CreatePointer((ptr), (type))

/**
 * Extracts the pointer of type T* from the shadow:natives@Pointer object.
 * 
 * params:
 *   T: the type of the original pointer which was stored earlier in the Pointer object.
 * 	 shadow_reference: the shadow_pointer_t* reference.
 *
 * usage:
 *  C:
 *		void __ShadowTest_UseSomethingUseful(shadow_pointer_t* shadow_reference) {
 *			UsefulStruct* extracted = shadow_ExtractPointer(UsefulStruct, shadow_reference);
 *		}
 *
 *  Shadow:
 *		private extern __ShadowTest_UseSomethingUseful(Pointer) => ();
 */
#define shadow_ExtractPointer(T, shadow_reference) ((T*)_shadow_ExtractPointer(shadow_reference))

#endif