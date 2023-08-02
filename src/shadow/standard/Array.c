/**
 * Authors: Claude Abounegm and Barry Wittman
 */
#include <standard/Array.h>
#include <stdlib.h>

shadow_Array_t* __allocateArray(shadow_Class_t* class, shadow_ulong_t elements, shadow_boolean_t nullable);
void __destroyArray(shadow_Array_t* array);

ArrayData* __shadow_standard__Array_getData(shadow_Array_t* shadowArray, ArrayData* array)
{
	if(!array) {
		array = malloc(sizeof(ArrayData));
	}
	
	array->size = shadowArray->size;
	array->data = &shadowArray[1];
	return array;
}



shadow_Array_t* __shadow_standard__Array_create(size_t num, shadow_Class_t* class, bool nullable, void** data)
{
	// allocate the array
	shadow_Array_t* array = __allocateArray(class, num, nullable ? 1 : 0);	
	
	// give the user access to the first element of the array
	// the real data is stored after the header information
	*data = &array[1];
	
	return array;
}

void __shadow_standard__Array_free(shadow_Array_t* array)
{
	__destroyArray(array);
	free(array);
}
