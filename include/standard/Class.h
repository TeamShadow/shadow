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
shadow_Class_t* _shadow_standard__ULong_getClass();
shadow_Class_t* _shadow_standard__Long_getClass();
shadow_Class_t* _shadow_standard__Byte_getClass();
shadow_Class_t* _shadow_standard__UByteArray_getClass();
shadow_Class_t* _shadow_standard__Int_getClass();
shadow_Class_t* _shadow_standard__UInt_getClass();
shadow_Class_t* _shadow_standard__Boolean_getClass();
shadow_Class_t* _shadow_standard__Code_getClass();
shadow_Class_t* _shadow_standard__Float_getClass();
shadow_Class_t* _shadow_standard__Short_getClass();
shadow_Class_t* _shadow_standard__UByte_getClass();
shadow_Class_t* _shadow_standard__UShort_getClass();
shadow_Class_t* _shadow_standard__Double_getClass();

#endif
