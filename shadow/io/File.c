/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <io/File.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

// METHOD SIGNATURES //

// HELPERS
void _shadowFile_SetHandle(shadow_File_t* instance, shadow_long_t handle);

// to be implemented:
shadow_boolean_t __shadowFile_Exists(shadow_String_t* str);
void __shadowFile_Close(shadow_long_t handle);

// METHOD SIGNATURES //

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
#else
	#include <unistd.h>
#endif

shadow_boolean_t __shadowFile_Exists(shadow_String_t* str)
{
	char* path = shadowString_GetCString(str);

	shadow_boolean_t ret;
#ifdef SHADOW_WINDOWS
	ret = (GetFileAttributesA(path) != INVALID_FILE_ATTRIBUTES);
#else
	ret = (access(path, F_OK) == 0);
#endif
	
	// free the allocated string
	free(path);
	
	return ret;
}

void __shadowFile_Close(shadow_long_t handle)
{
#ifdef SHADOW_WINDOWS
	CloseHandle((HANDLE)(intptr_t)handle);
#else
	close((int)handle);
#endif
}
