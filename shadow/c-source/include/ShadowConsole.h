/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

#include "ShadowTypes.h"
#include "ShadowString.h"

typedef void* ShadowConsole;

void __ShadowConsole_Initialize(ShadowConsole);
void __ShadowConsole_ReadByte(ShadowConsole, ShadowByte* value, ShadowBoolean* eof);
void __ShadowConsole_Print(ShadowConsole, ShadowString stringRef);
void __ShadowConsole_PrintError(ShadowConsole, ShadowString stringRef);

void __ShadowConsole_PrintLine(ShadowConsole);
void __ShadowConsole_PrintErrorLine(ShadowConsole);

#endif