#ifndef SHADOW_POINTER_H
#define SHADOW_POINTER_H

/**
 * The managed pointer which is the main way to pass handles/pointers between C, LLVM and Shadow code.
 * This is the C pointer representation for shadow:natives@Pointer.
 */
typedef void* shadow_Pointer_t;

// 
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
shadow_Pointer_t* _shadow_natives__Pointer_create(shadow_Pointer_t* instance, void* ptr, free_type_t type);
void* _shadow_natives__Pointer_extract(shadow_Pointer_t*);

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
 *		shadow_Pointer_t* __shadow_Test__Test_createSomethingUseful() {
 *			return _shadow_natives__Pointer_create(NULL, malloc(sizeof(UsefulStruct)), SHADOW_CAN_FREE);
 *		}
 *
 * 	Shadow:
 *		private extern __ShadowTest_CreateSomethingUseful() => (Pointer);
 */
//#define _shadow_natives__Pointer_create(ptr, type) _shadow_natives__Pointer_create(0, ptr, type)

/**
 * Extracts the pointer of type T* from the shadow:natives@Pointer object.
 * 
 * params:
 *   T: the type of the original pointer which was stored earlier in the Pointer object.
 * 	 shadow_reference: the shadow_Pointer_t* reference.
 *
 * usage:
 *  C:
 *		void __ShadowTest_UseSomethingUseful(shadow_pointer_t* shadow_reference) {
 *			UsefulStruct* extracted = _shadow_natives__Pointer_extract(UsefulStruct, shadow_reference);
 *		}
 *
 *  Shadow:
 *		private extern __ShadowTest_UseSomethingUseful(Pointer) => ();
 */
#define _shadow_natives__Pointer_extract(T, shadow_reference) ((T*)_shadow_natives__Pointer_extract(shadow_reference))

#endif