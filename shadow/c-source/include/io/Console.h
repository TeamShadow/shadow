/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <Shadow.h>

typedef void* shadow_io_Console_t;

void _shadowIoConsole_printLine(shadow_io_Console_t*, shadow_Object_t*);
void _shadowIoConsole_printErrorLine(shadow_io_Console_t*, shadow_Object_t*);

#define shadowIoConsole_printLine(obj) _shadowIoConsole_printLine(0, obj)
#define shadowIoConsole_printErrorLine(obj) _shadowIoConsole_printErrorLine(0, obj)

#endif