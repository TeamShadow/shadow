/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_CONSOLE_H
#define SHADOW_CONSOLE_H

void __ShadowConsole_Initialize(void);
void __ShadowConsole_ReadByte(ShadowByte* value, ShadowBoolean* eof);
void __ShadowConsole_Print(ShadowString string);
void __ShadowConsole_PrintError(ShadowString string);
void __ShadowConsole_PrintLine(ShadowString string);
void __ShadowConsole_PrintErrorLine(ShadowString string);

#endif