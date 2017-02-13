/**
 * Author: Claude Abounegm
 */
#include "ShadowConsole.h"
#include <unistd.h>

static char newLine[1] = { '\n' };

void __ShadowConsole_Initialize(void) { }

void __ShadowConsole_ReadByte(ShadowByte* value, ShadowBoolean* eof)
{
	ssize_t bytesRead = read(0, value, 1);
	
	if(bytesRead == 0) {
		*value = 0;
		*eof = 1;
	} else {
		*eof = 0;
	}
}

static void printString(int fd, ShadowString stringRef)
{
	ShadowStringData str; // { size, chars, ascii }
	UnpackShadowString(stringRef, &str);

	write(fd, str.chars, str.size);
}

void __ShadowConsole_Print(ShadowString stringRef)
{
	printString(STDOUT_FILENO, stringRef);
}

void __ShadowConsole_PrintError(ShadowString stringRef)
{
	printString(STDERR_FILENO, stringRef);
}

static void printLine(int fd)
{
	write(fd, newLine, sizeof(newLine));
}

void __ShadowConsole_PrintLine(void)
{
	printLine(STDOUT_FILENO);
}

void __ShadowConsole_PrintErrorLine(void)
{
	printLine(STDERR_FILENO);
}