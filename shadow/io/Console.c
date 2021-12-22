/**
 * Author: Claude Abounegm
 */
#include <Shadow.h>
#include <io/Console.h>
#include <standard/String.h>

// METHOD SIGNATURES //
void __shadowIoConsole_initialize(void);
void __shadowIoConsole_readByte(shadow_byte_t*, shadow_boolean_t*);

void __shadowIoConsole_print(shadow_String_t*);
void __shadowIoConsole_printError(shadow_String_t*);

void __shadowIoConsole_printLine(void);
void __shadowIoConsole_printErrorLine(void);
// METHOD SIGNATURES //


#ifdef SHADOW_WINDOWS
	#include <Windows.h>
	
	static char newLine[2] = { '\r', '\n' };

	void __shadowIoConsole_initialize(void)
	{
		SetConsoleCP(65001);
		SetConsoleOutputCP(65001);
	}
	
	int __shadowIoConsole_readBuffer(shadow_Array_t* array)
	{
		ArrayData buffer;
		shadowArray_getData(array, &buffer);
		DWORD bytesRead = 0;
		ReadFile(GetStdHandle(STD_INPUT_HANDLE), buffer.data, buffer.size, &bytesRead, NULL);
		
		return bytesRead;		
	}

	/*
	void __shadowIoConsole_ReadByte(shadow_byte_t* value, shadow_boolean_t* eof)
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
	*/

	static void printString(int handleId, shadow_String_t* stringRef)
	{
		StringData str; // { size, chars, ascii }
		shadowString_getData(stringRef, &str);
		
		DWORD bytesWritten = 0;
		WriteFile(GetStdHandle(handleId), str.chars, str.size, &bytesWritten, NULL);
	}

	void __shadowIoConsole_print(shadow_String_t* stringRef)
	{
		printString(STD_OUTPUT_HANDLE, stringRef);
	}

	void __shadowIoConsole_printError(shadow_String_t* stringRef)
	{
		printString(STD_ERROR_HANDLE, stringRef);
	}

	static void printLine(int handleId)
	{
		DWORD bytesWritten = 0;
		WriteFile(GetStdHandle(handleId), newLine, sizeof(newLine), &bytesWritten, NULL);
	}

	void __shadowIoConsole_printLine(void)
	{
		printLine(STD_OUTPUT_HANDLE);
	}

	void __shadowIoConsole_printErrorLine(void)
	{
		printLine(STD_ERROR_HANDLE);
	}
#else
	#include <unistd.h>

	static char newLine[1] = { '\n' };

	void __shadowIoConsole_initialize(void) { }
	
	int __shadowIoConsole_readBuffer(shadow_Array_t* array)
	{
		ArrayData buffer;
		shadowArray_GetData(array, &buffer);
		ssize_t bytesRead = read(0, buffer.data, buffer.size);
		
		return bytesRead;		
	}

	/*
	void __shadowIoConsole_ReadByte(shadow_byte_t* value, shadow_boolean_t* eof)
	{
		ssize_t bytesRead = read(0, value, 1);
		
		if(bytesRead == 0) {
			*value = 0;
			*eof = 1;
		} else {
			*eof = 0;
		}
	}
	*/

	static void printString(int fd, shadow_String_t* stringRef)
	{
		StringData str; // { size, chars, ascii }
		shadowString_GetData(stringRef, &str);

		write(fd, str.chars, str.size);
	}

	void __shadowIoConsole_print(shadow_String_t* stringRef)
	{
		printString(STDOUT_FILENO, stringRef);
	}

	void __shadowIoConsole_printError(shadow_String_t* stringRef)
	{
		printString(STDERR_FILENO, stringRef);
	}

	static void printLine(int fd)
	{
		write(fd, newLine, sizeof(newLine));
	}

	void __shadowIoConsole_printLine(void)
	{
		printLine(STDOUT_FILENO);
	}

	void __shadowIoConsole_printErrorLine(void)
	{
		printLine(STDERR_FILENO);
	}
#endif