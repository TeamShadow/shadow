/**
 * Author: Claude Abounegm
 */
#include <io/File.h>
#include <stdlib.h>

// METHOD SIGNATURES //
shadow_Pointer_t* __ShadowFile_Initialize(shadow_String_t* str);
void __ShadowFile_Close(shadow_Pointer_t* ptr);
// METHOD SIGNATURES //

#ifdef SHADOW_WINDOWS
	#include <Windows.h>
#else
	#include <unistd.h>
#endif

typedef struct {
#ifdef SHADOW_WINDOWS
	HANDLE fd;
#else
	int fd;
#endif
} FileData;

shadow_Pointer_t* __ShadowFile_Initialize(shadow_String_t* str)
{
	FileData* data = calloc(1, sizeof(FileData));
	char* path = shadowString_GetCString(str);
	
#ifdef SHADOW_WINDOWS
	//data->fd = CreateFileA(path, ...);
#else
	//data->fd = open(path, FLAGS);
#endif

	free(path);
	
	// return a pointer that can be freed in Shadow
	return shadowPointer_Create(data, SHADOW_CAN_FREE);
}

void __ShadowFile_Close(shadow_Pointer_t* ptr)
{
	FileData* data = shadowPointer_Extract(FileData, ptr);
	
#ifdef SHADOW_WINDOWS
	CloseHandle(data->fd);
#else
	close(data->fd);
#endif
}