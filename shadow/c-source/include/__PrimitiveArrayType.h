#ifndef PRIMITIVE_ARRAY_TYPE_H
#define PRIMITIVE_ARRAY_TYPE_H

typedef struct {
	shadow_ulong_t* data;
	shadow_int_t size;
} __primitive_array;

#endif