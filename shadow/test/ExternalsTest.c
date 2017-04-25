/**
 * Author: Claude Abounegm
 */
#include <ShadowCore.h>
#include <io/Console.h>
#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>

typedef void* shadow_ExternalsTest_t;

typedef struct {
	int number;
} TestData;

void _shadowExternalsTest_PrintNumberWithOffset(shadow_ExternalsTest_t* instance, int number);

void __ShadowExternalTest_PrintfToString(shadow_ExternalsTest_t* ref)
{
	// equivalent to ref.toString()
	shadow_String_t* str = shadow_ToString(ref);
	
	// get the C string from the Shadow String object
	char* cStr = shadow_GetStringDataAsCStr(str);
	
	// print the C null terminated string
	printf("%s\n", cStr);
	
	// since the Shadow Console and printf do not use the 
	// same system calls for the console, we flush the output
	// for consistency.
	fflush(stdout);
	
	// the shadow_UnpackStringToCStr allocates memory,
	// so we free it.
	free(cStr);
}

void __ShadowExternalTest_CreateArray()
{
	// Create a Shadow string from a C string
	shadow_String_t* string = shadow_CreateString("Hello World from C!");

	shadow_PrintLine(string);
	
	// free the String we created
	shadow_FreeString(string);
}

shadow_Pointer_t* __ShadowExternalTest_InitPointer(int number)
{
	TestData* data = malloc(sizeof(TestData));
	data->number = number;
	
	return shadow_CreatePointer(data, SHADOW_CAN_FREE);
}

void __ShadowExternalTest_PrintPointerData(shadow_ExternalsTest_t* instance, shadow_Pointer_t* ptr)
{
	TestData* data = shadow_ExtractPointer(TestData, ptr);
	
	printf("%d\n", data->number);
	fflush(stdout);
	
	_shadowExternalsTest_PrintNumberWithOffset(instance, data->number);
}