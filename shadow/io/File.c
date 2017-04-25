/**
 * Author: Claude Abounegm
 */
#include <io/File.h>
#include <stdlib.h>

// METHOD SIGNATURES //
shadow_Pointer_t* __ShadowFile_Initialize(shadow_String_t* pathRef);
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

shadow_Pointer_t* __ShadowFile_Initialize(shadow_String_t* pathRef)
{
	FileData* data = calloc(1, sizeof(FileData));
	char* path = shadow_GetStringDataAsCStr(pathRef);
	
#ifdef SHADOW_WINDOWS
	//data->fd = CreateFileA(path, ...);
#else
	//data->fd = open(path, FLAGS);
#endif

	free(path);
	
	// return a pointer that can be freed in Shadow
	return shadow_CreatePointer(data, SHADOW_CAN_FREE);
}

void __ShadowFile_Close(shadow_Pointer_t* ptr)
{
	FileData* data = shadow_ExtractPointer(FileData, ptr);
	
#ifdef SHADOW_WINDOWS
	CloseHandle(data->fd);
#else
	close(data->fd);
#endif
}