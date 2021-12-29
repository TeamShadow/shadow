/**
 * Author: Barry Wittman
 */
#include <Shadow.h>
#include <standard/String.h>

shadow_String_t* __shadowIoTextOutput_getSeparator()
{
#ifdef SHADOW_WINDOWS
    return shadowString_create("\r\n");
#else
    return shadowString_create("\n");
#endif
}