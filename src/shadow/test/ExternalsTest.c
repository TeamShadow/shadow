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

void __shadowTestExternalTest_printfToString(shadow_ExternalsTest_t* ref)
{
	// equivalent to ref.toString()
	shadow_String_t* str = _shadowObject_toString(ref);
	
	// get the C string from the Shadow String object
	char* cStr = shadowString_getCString(str);
	
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

shadow_String_t* __shadowTestExternalTest_createString()
{
	// Create a Shadow string from a C string
	shadow_String_t* string = shadowString_create("This is a string created in C and printed using Shadow's Console.printLine()");

	// If we passed the String to somewhere else instead of returning it, we could decrement the reference count
	// once we were done with it
	//__decrementRef((shadow_Object_t*) string);

    return string;
}


typedef struct {
	int number;
} TestData;

shadow_Pointer_t* __shadowTestExternalTest_initPointer(int number)
{
	TestData* data = malloc(sizeof(TestData));
	data->number = number;
	
	return _shadowPointer_create(NULL, data, SHADOW_CAN_FREE);
}

void _shadowTestExternalsTest_printNumberWithOffset(shadow_ExternalsTest_t* instance, int number);
void __shadowTestExternalTest_printPointerData(shadow_ExternalsTest_t* instance, shadow_Pointer_t* ptr)
{
	// retrieve the original pointer we allocated earlier
	TestData* data = _shadowPointer_extract(ptr);
	
	// print the number that we set earlier
	printf("%d\n", data->number);
	fflush(stdout);
	
	// pass the number to Shadow to print it with an offset
	_shadowTestExternalsTest_printNumberWithOffset(instance, data->number);
}

void __shadowTestExternalTest_printClasses(shadow_ExternalsTest_t* instance)
{

    shadow_io_Console_t* console = _shadowIoConsole_getInstance(NULL);

	// object class
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowObject_getClass(instance));
	
	// primitives
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowBoolean_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowByte_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowUByte_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowShort_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowUShort_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowInt_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowUInt_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowCode_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowLong_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowULong_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowFloat_getClass());
	_shadowIoConsole_printLine(console, (shadow_Object_t*)_shadowDouble_getClass());
}
