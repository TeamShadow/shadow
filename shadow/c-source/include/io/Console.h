/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include <ShadowCore.h>

typedef void* shadow_Console_t;

void __ShadowConsole_Initialize(void);
void __ShadowConsole_ReadByte(shadow_byte_t*, shadow_boolean_t*);

void __ShadowConsole_Print(shadow_String_t*);
void __ShadowConsole_PrintError(shadow_String_t*);

void __ShadowConsole_PrintLine(void);
void __ShadowConsole_PrintErrorLine(void);

#endif