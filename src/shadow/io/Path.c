/**
 * Author: Barry Wittman
 */
#include <Shadow.h>

shadow_code_t __shadowIoPath_fileSeparator()
{
#ifdef SHADOW_WINDOWS
    return (shadow_code_t)'\\';
#else
    return (shadow_code_t)'/';
#endif
}

shadow_code_t __shadowIoPath_pathSeparator()
{
#ifdef SHADOW_WINDOWS
    return (shadow_code_t)';';
#else
    return (shadow_code_t)':';
#endif
}