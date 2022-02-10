/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_FILE_H
#define SHADOW_FILE_H

#include <Shadow.h>

typedef void* shadow_io_File_t;
shadow_long_t __shadowIoFile_read(shadow_long_t handle, shadow_Array_t* array);

#endif