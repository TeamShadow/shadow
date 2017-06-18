/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <io/Console.h>
#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

typedef shadow_Object_t shadow_ExternalsTest_t;

void __shadowExternalTest_PrintfToString(shadow_ExternalsTest_t* ref)
{
	// equivalent to ref.toString()
	shadow_String_t* str = shadowObject_ToString(ref);
	
	// get the C string from the Shadow String object
	char* cStr = shadowString_GetCString(str);
	
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

void __shadowExternalTest_CreateString()
{
	// Create a Shadow string from a C string
	shadow_String_t* string = shadowString_Create("This is a string created in C and printed using Shadow's Console.printLine()");

	// this method is equivalent to Console.printLine(Object)
	shadowConsole_PrintLine((shadow_Object_t*)string);
	
	// Decrement the reference count on the String we created (freeing it)
	__decrementRef((shadow_Object_t*) string);
	
	// Instead, we could have simply freed the string, but decrementing allows
	// the possibility that some other object kept a reference to the string
	//shadowString_Free(string);
}


typedef struct {
	int number;
} TestData;

shadow_Pointer_t* __shadowExternalTest_InitPointer(int number)
{
	TestData* data = malloc(sizeof(TestData));
	data->number = number;
	
	return shadowPointer_Create(data, SHADOW_CAN_FREE);
}

void _shadowExternalsTest_PrintNumberWithOffset(shadow_ExternalsTest_t* instance, int number);
void __shadowExternalTest_PrintPointerData(shadow_ExternalsTest_t* instance, shadow_Pointer_t* ptr)
{
	// retrieve the original pointer we allocated earlier
	TestData* data = shadowPointer_Extract(TestData, ptr);
	
	// print the number that we set earlier
	printf("%d\n", data->number);
	fflush(stdout);
	
	// pass the number to Shadow to print it with an offset
	_shadowExternalsTest_PrintNumberWithOffset(instance, data->number);
}

void __shadowExternalTest_PrintClasses(shadow_ExternalsTest_t* instance)
{
	// object class
	shadowConsole_PrintLine((shadow_Object_t*)shadowObject_GetClass(instance));
	
	// primitives
	shadowConsole_PrintLine((shadow_Object_t*)shadowBoolean_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowByte_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowUByte_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowShort_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowUShort_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowInt_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowUInt_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowCode_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowLong_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowULong_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowFloat_GetClass());
	shadowConsole_PrintLine((shadow_Object_t*)shadowDouble_GetClass());
}
