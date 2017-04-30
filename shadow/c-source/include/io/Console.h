/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <Shadow.h>

typedef void* shadow_Console_t;

void _shadowConsole_PrintLine(shadow_Console_t*, shadow_Object_t*);
void _shadowConsole_PrintErrorLine(shadow_Console_t*, shadow_Object_t*);

#define shadowConsole_PrintLine(obj) _shadowConsole_PrintLine(0, obj)
#define shadowConsole_PrintErrorLine(obj) _shadowConsole_PrintErrorLine(0, obj)

#endif