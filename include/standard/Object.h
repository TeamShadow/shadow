/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_OBJECT_H
#define SHADOW_OBJECT_H

#include <standard/String.h>
#include <standard/Class.h>
#include <standard/ULong.h>
#include <standard/MethodTable.h>

typedef struct 
{
	shadow_ulong_t ref_count;
	shadow_Class_t* class_ref;
	shadow_MethodTable_t* methods;
}
shadow_Object_t;

shadow_String_t* _shadowObject_toString(shadow_Object_t* ref);
shadow_Class_t* _shadowObject_getClass(shadow_Object_t* ref);

void __decrementRef(shadow_Object_t* object);
void __incrementRef(shadow_Object_t* object);

#endif
