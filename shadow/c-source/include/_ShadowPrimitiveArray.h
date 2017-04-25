#ifndef SHADOW_PRIMITIVE_ARRAY_H
#define SHADOW_PRIMITIVE_ARRAY_H

typedef void* shadow_PrimitiveArray_t;

/**
 * This is the C representation of the Shadow array when unpacked.
 */
typedef struct {
	shadow_int_t size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					// to the desired data structure.
} VoidArray;

/**
 * Takes a shadow_PrimitiveArray_t* supplied from Shadow code, and stores the data
 * in the C usable format, in VoidArray* array. If data is modified here,
 * the data is also modified in Shadow.
 */
void shadow_GetArrayData(const shadow_PrimitiveArray_t*, VoidArray*);

/**
 * Creates a Shadow Array which can be passed to Shadow methods.
 *
 * param num - the number of elements in the array.
 * param size - the size of each element in the array.
 * param data - this is a block of memory initialized using calloc(num, size)
 *				and is the actual data that Shadow will use. So index through that.
 */
shadow_PrimitiveArray_t* shadow_CreateArray(size_t num, size_t size, void** data);

/**
 * Frees the array that was created using shadow_CreateArray();
 */
void shadow_FreeArray(shadow_PrimitiveArray_t*);

#endif