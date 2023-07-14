/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <io/Console.h>
#include <standard/Object.h>
#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

typedef shadow_Object_t shadow_ExternalsTest_t;

void __shadow_test__ExternalsTest_printfToString(shadow_ExternalsTest_t* _this)
{
	// equivalent to ref.toString()
	shadow_String_t* str = _shadow_standard__Object_toString(_this);
	
	// get the C string from the Shadow String object
	char* cStr = __shadow_standard__String_getCString(str);
	
	// print the C null terminated string
	printf("%s\n", cStr);
	
	// since the Shadow Console and printf do not use the 
	// same system calls for the console, we flush the output
	// for consistency.
	fflush(stdout);
	
	// the shadowString_GetCString() allocates memory,
	// so we free it.
	free(cStr);
}

shadow_String_t* __shadow_test__ExternalsTest_createString(shadow_ExternalsTest_t* _this)
{
	// Create a Shadow string from a C string
	shadow_String_t* string = __shadow_standard__String_create("This is a string created in C and printed using Shadow's Console.printLine()");

	// If we passed the String to somewhere else instead of returning it, we could decrement the reference count
	// once we were done with it
	//__decrementRef((shadow_Object_t*) string);

    return string;
}


typedef struct {
	int number;
} TestData;

shadow_Pointer_t* __shadow_test__ExternalsTest_initPointer(shadow_ExternalsTest_t* _this, int number)
{
	TestData* data = malloc(sizeof(TestData));
	data->number = number;
	
	return _shadow_natives__Pointer_create(NULL, data, SHADOW_CAN_FREE);
}

void _shadow_test__ExternalsTest_printNumberWithOffset(shadow_ExternalsTest_t* _this, int number);
void __shadow_test__ExternalsTest_printPointerData(shadow_ExternalsTest_t* _this, shadow_Pointer_t* ptr)
{
	// retrieve the original pointer we allocated earlier
	TestData* data = _shadow_natives__Pointer_extract(TestData, ptr);
	
	// print the number that we set earlier
	printf("%d\n", data->number);
	fflush(stdout);
	
	// pass the number to Shadow to print it with an offset
	_shadow_test__ExternalsTest_printNumberWithOffset(_this, data->number);
}

void __shadow_test__ExternalsTest_printClasses(shadow_ExternalsTest_t* _this)
{
    shadow_io_Console_t* console = _shadow_io__Console_getInstance(NULL);

	// object class
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Object_getClass(_this));
	
	// primitives
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Boolean_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Byte_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__UByte_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Short_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__UShort_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Int_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__UInt_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Code_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Long_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__ULong_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Float_getClass());
	_shadow_io__Console_printLine(console, (shadow_Object_t*)_shadow_standard__Double_getClass());
}
