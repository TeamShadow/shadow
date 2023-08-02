/**
 * Author: Barry Wittman
 */
#include <Shadow.h>
#include <standard/String.h>
#include <io/TextOutput.h>

shadow_String_t* __shadow_io__TextOutput_getSeparator(shadow_io_TextOutput_t* _this)
{
#ifdef SHADOW_WINDOWS
    return __shadow_standard__String_create("\r\n");
#else
    return __shadow_standard__String_create("\n");
#endif
}