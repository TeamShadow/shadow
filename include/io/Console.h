/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <Shadow.h>

typedef void* shadow_io_Console_t;

shadow_io_Console_t* _shadow_io__Console_printLine(shadow_io_Console_t*, shadow_Object_t*);
shadow_io_Console_t* _shadow_io__Console_printErrorLine(shadow_io_Console_t*, shadow_Object_t*);
shadow_io_Console_t* _shadow_io__Console_getInstance(shadow_io_Console_t*);

#endif