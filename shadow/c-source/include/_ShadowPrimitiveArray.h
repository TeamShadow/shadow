#ifndef SHADOW_PRIMITIVE_ARRAY_H
#define SHADOW_PRIMITIVE_ARRAY_H

// This should be changed when the structure of the arrays change.
// YOU SHOULD NOT ATTEMPT TO USE THIS STRUCT UNLESS YOU REALLY KNOW
// KNOW WHAT YOU'RE DOING. USE ArrayData INSTEAD.
typedef struct {
	shadow_ulong_t* data;
	shadow_int_t size;
} shadow_PrimitiveArray_t;

/**
 * This is the C representation of the Shadow array when unpacked.
 */
typedef struct {
	shadow_int_t size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					// to the desired data structure.
} ArrayData;

/**
 * Takes a shadow_PrimitiveArray_t* supplied from Shadow code, and stores the data
 * in the C usable format, in ArrayData* array. If data is modified here,
 * the data is also modified in Shadow.
 */
ArrayData* shadowArray_GetData(const shadow_PrimitiveArray_t* shadowArray, ArrayData* array);

/**
 * Creates a Shadow Array which can be passed to Shadow methods.
 *
 * param num - the number of elements in the array.
 * param size - the size of each element in the array.
 * param data - this is a block of memory initialized using calloc(num, size)
 *				and is the actual data that Shadow will use. So index through that.
 */
shadow_PrimitiveArray_t* shadowArray_Create(size_t num, size_t size, void** data);

/**
 * Frees the array that was created using shadowArray_Create();
 */
void shadowArray_Free(shadow_PrimitiveArray_t* array);

#endif