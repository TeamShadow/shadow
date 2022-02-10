/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <Shadow.h>

typedef void* shadow_io_Console_t;

shadow_io_Console_t* _shadowIoConsole_printLine(shadow_io_Console_t*, shadow_Object_t*);
shadow_io_Console_t* _shadowIoConsole_printErrorLine(shadow_io_Console_t*, shadow_Object_t*);
shadow_io_Console_t* _shadowIoConsole_getInstance(shadow_io_Console_t*);

#endif