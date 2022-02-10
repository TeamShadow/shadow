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

/* In theory, all of these should have a parameter,
 * but the functions don't need a parameter to get
 * the appropriate class.
 */
shadow_Class_t* _shadowULong_getClass();
shadow_Class_t* _shadowLong_getClass();
shadow_Class_t* _shadowByte_getClass();
shadow_Class_t* _shadowUByteArray_getClass();
shadow_Class_t* _shadowInt_getClass();
shadow_Class_t* _shadowUInt_getClass();
shadow_Class_t* _shadowBoolean_getClass();
shadow_Class_t* _shadowCode_getClass();
shadow_Class_t* _shadowFloat_getClass();
shadow_Class_t* _shadowShort_getClass();
shadow_Class_t* _shadowUByte_getClass();
shadow_Class_t* _shadowUShort_getClass();
shadow_Class_t* _shadowDouble_getClass();

#endif
