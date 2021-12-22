/**
 * Authors: Claude Abounegm and Barry Wittman
 */
#ifndef SHADOW_ARRAY_H
#define SHADOW_ARRAY_H

#include <standard/ULong.h>
#include <standard/Boolean.h>
#include <standard/Long.h>
#include <standard/Class.h>
#include <standard/MethodTable.h>
#include <stddef.h>

// This should be changed when the structure of the arrays change.
// YOU SHOULD NOT ATTEMPT TO USE THIS STRUCT UNLESS YOU REALLY KNOW
// KNOW WHAT YOU'RE DOING. USE ArrayData INSTEAD.
typedef struct {
	shadow_ulong_t ref_count;
	shadow_Class_t* class_ref;
	shadow_MethodTable_t* methods;
	shadow_long_t size;
} shadow_Array_t;

/**
 * This is the C representation of the Shadow array when unpacked.
 */
typedef struct {
	shadow_long_t size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be cast
					// to the desired data structure.
} ArrayData;

/**
 * Takes a shadow_Array_t* supplied from Shadow code, and stores the data
 * in the C usable format, in ArrayData* array. If data is modified here,
 * the data is also modified in Shadow.
 */
ArrayData* shadowArray_getData(shadow_Array_t* shadowArray, ArrayData* array);

/**
 * Creates a Shadow Array which can be passed to Shadow methods.
 *
 * param num - the number of elements in the array.
 * param class - the Shadow Class of the array (NOT the class of the elements, the class of the whole array)
 * param nullable - true if the array can contain null elements, false otherwise
 * param data - this is a block of memory initialized using calloc(num, size)
 *				and is the actual data that Shadow will use. So index through that.
 */
shadow_Array_t* shadowArray_create(size_t num, shadow_Class_t* class, bool nullable, void** data);

/**
 * Frees the array that was created using shadowArray_Create();
 */
void shadowArray_free(shadow_Array_t* array);


#endif