/**
 * Author: Claude Abounegm
 */
#include <io/Console.h>

// METHOD SIGNATURES //
void __ShadowConsole_Initialize(void);
void __ShadowConsole_ReadByte(shadow_byte_t*, shadow_boolean_t*);

void __ShadowConsole_Print(shadow_String_t*);
void __ShadowConsole_PrintError(shadow_String_t*);

void __ShadowConsole_PrintLine(void);
void __ShadowConsole_PrintErrorLine(void);
// METHOD SIGNATURES //


#ifdef SHADOW_WINDOWS
	#include <Windows.h>
	
	static char newLine[2] = { '\r', '\n' };

	void __ShadowConsole_Initialize(void)
	{
		SetConsoleCP(65001);
		SetConsoleOutputCP(65001);
	}

	void __ShadowConsole_ReadByte(shadow_byte_t* value, shadow_boolean_t* eof)
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

	static void printString(int handleId, shadow_String_t* stringRef)
	{
		ShadowStringData str; // { size, chars, ascii }
		shadow_GetStringData(stringRef, &str);
		
		DWORD bytesWritten = 0;
		WriteFile(GetStdHandle(handleId), str.chars, str.size, &bytesWritten, NULL);
	}

	void __ShadowConsole_Print(shadow_String_t* stringRef)
	{
		printString(STD_OUTPUT_HANDLE, stringRef);
	}

	void __ShadowConsole_PrintError(shadow_String_t* stringRef)
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
#else
	#include <unistd.h>

	static char newLine[1] = { '\n' };

	void __ShadowConsole_Initialize(void) { }

	void __ShadowConsole_ReadByte(shadow_byte_t* value, shadow_boolean_t* eof)
	{
		ssize_t bytesRead = read(0, value, 1);
		
		if(bytesRead == 0) {
			*value = 0;
			*eof = 1;
		} else {
			*eof = 0;
		}
	}

	static void printString(int fd, shadow_String_t* stringRef)
	{
		ShadowStringData str; // { size, chars, ascii }
		shadow_GetStringData(stringRef, &str);

		write(fd, str.chars, str.size);
	}

	void __ShadowConsole_Print(shadow_String_t* stringRef)
	{
		printString(STDOUT_FILENO, stringRef);
	}

	void __ShadowConsole_PrintError(shadow_String_t* stringRef)
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
#endif