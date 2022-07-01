/**
 * Author: Barry Wittman
 */
#include <Shadow.h>
#include <standard/String.h>

shadow_String_t* __shadow_io__TextOutput_getSeparator()
{
#ifdef SHADOW_WINDOWS
    return __shadow_standard__String_create("\r\n");
#else
    return __shadow_standard__String_create("\n");
#endif
}