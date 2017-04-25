/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <ShadowCore.h>

typedef void* shadow_Console_t;

void _shadow_PrintLine(shadow_Console_t*, shadow_String_t*);
void _shadow_PrintErrorLine(shadow_Console_t*, shadow_String_t*);

#define shadow_PrintLine(str) _shadow_PrintLine(0, str)
#define shadow_PrintErrorLine(str) _shadow_PrintErrorLine(0, str)

#endif