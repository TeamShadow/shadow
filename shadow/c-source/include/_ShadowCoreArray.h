#ifndef SHADOW_CORE_ARRAY_H
#define SHADOW_CORE_ARRAY_H

typedef void* shadow_NativeArray_t;

/**
 * This is the C representation of the Shadow array when unpacked.
 */
typedef struct {
	shadow_int_t size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					// to the desired data structure.
} VoidArray;

/**
 * Takes a shadow_NativeArray_t* supplied from Shadow code, and stores the data
 * in the C usable format, in VoidArray* array. If data is modified here,
 * the data is also modified in Shadow.
 */
void shadow_UnpackArray(shadow_NativeArray_t*, VoidArray*);

#endif