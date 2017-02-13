/**
 * Author: Claude Abounegm
 */
#include "ShadowConsole.h"
#include <Windows.h>

static char newLine[2] = { '\r', '\n' };

void __ShadowConsole_Initialize(void)
{
	SetConsoleCP(65001);
	SetConsoleOutputCP(65001);
}

void __ShadowConsole_ReadByte(ShadowByte* value, ShadowBoolean* eof)
{
	DWORD bytesRead = 0;
	ReadFile(GetStdHandle(STD_INPUT_HANDLE), value, 1, &bytesRead, NULL);
	
	if(bytesRead == 0) {
		*value = 0;
		*eof = 1;
	} else {
		*eof = 0;	
	}
}

static void printString(int handleId, ShadowString stringRef)
{
	ShadowStringData str; // { size, chars, ascii }
	UnpackShadowString(stringRef, &str);
	
	DWORD bytesWritten = 0;
	WriteFile(GetStdHandle(handleId), str.chars, str.size, &bytesWritten, NULL);
}

void __ShadowConsole_Print(ShadowString stringRef)
{
	printString(STD_OUTPUT_HANDLE, stringRef);
}

void __ShadowConsole_PrintError(ShadowString stringRef)
{
	printString(STD_ERROR_HANDLE, stringRef);
}

static void printLine(int handleId)
{
	DWORD bytesWritten = 0;
	WriteFile(GetStdHandle(handleId), newLine, sizeof(newLine), &bytesWritten, NULL);
}

void __ShadowConsole_PrintLine(void)
{
	printLine(STD_OUTPUT_HANDLE);
}

void __ShadowConsole_PrintErrorLine(void)
{
	printLine(STD_ERROR_HANDLE);
}