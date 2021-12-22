/**
 * Authors: Claude Abounegm and Barry Wittman
 */
#ifndef SHADOW_CLASS_H
#define SHADOW_CLASS_H

#include <standard/ULong.h>
#include <standard/Int.h>
#include <standard/MethodTable.h>

struct shadow_Array_t;
struct shadow_String_t;

typedef struct shadow_Class_t
{
	shadow_ulong_t ref_count;
	struct shadow_Class_t* class_ref;
	shadow_MethodTable_t* methods;
	struct shadow_Array_t* interfaceTables;
	struct shadow_Array_t* interfaceClasses;
	struct shadow_String_t* name;
	struct shadow_Class_t* parent;
	shadow_int_t flags;
	shadow_int_t size;	
}
shadow_Class_t;

shadow_Class_t* shadowULong_getClass();
shadow_Class_t* shadowLong_getClass();
shadow_Class_t* shadowByte_getClass();
shadow_Class_t* shadowUByteArray_getClass();
shadow_Class_t* shadowInt_getClass();
shadow_Class_t* shadowUInt_getClass();
shadow_Class_t* shadowBoolean_getClass();
shadow_Class_t* shadowCode_getClass();
shadow_Class_t* shadowFloat_getClass();
shadow_Class_t* shadowShort_getClass();
shadow_Class_t* shadowUByte_getClass();
shadow_Class_t* shadowUShort_getClass();
shadow_Class_t* shadowDouble_getClass();

#endif
