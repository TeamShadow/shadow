/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include "ShadowTypes.h"
#include "ShadowString.h"

typedef void* ShadowConsole;

void __ShadowConsole_Initialize(void);
void __ShadowConsole_ReadByte(ShadowByte* value, ShadowBoolean* eof);
void __ShadowConsole_Print(ShadowString stringRef);
void __ShadowConsole_PrintError(ShadowString stringRef);

void __ShadowConsole_PrintLine(void);
void __ShadowConsole_PrintErrorLine(void);

#endif