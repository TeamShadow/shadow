/**
 * Author: Barry Wittman
 */
#include <Shadow.h>
#include <io/Path.h>

shadow_code_t __shadow_io__Path_fileSeparator(shadow_io_Path_t* _this)
{
#ifdef SHADOW_WINDOWS
    return (shadow_code_t)'\\';
#else
    return (shadow_code_t)'/';
#endif
}

shadow_code_t __shadow_io__Path_pathSeparator(shadow_io_Path_t* _this)
{
#ifdef SHADOW_WINDOWS
    return (shadow_code_t)';';
#else
    return (shadow_code_t)':';
#endif
}