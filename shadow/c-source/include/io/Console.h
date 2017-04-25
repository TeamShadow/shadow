/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <ShadowCore.h>

typedef void* shadow_Console_t;

void _shadowConsole_PrintLine(shadow_Console_t*, shadow_String_t*);
void _shadowConsole_PrintErrorLine(shadow_Console_t*, shadow_String_t*);

#define shadowConsole_PrintLine(str) _shadowConsole_PrintLine(0, str)
#define shadowConsole_PrintErrorLine(str) _shadowConsole_PrintErrorLine(0, str)

#endif