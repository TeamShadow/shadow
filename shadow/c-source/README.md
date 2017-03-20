# C micro-framework Documentation

This documentation will explain a few features available in the Shadow-C micro-framework.

## Folder Structure
There are four folders in which `.c` files could be placed. Three platform specific folders and one general folder.
At the time of this writing, the general folder (the root folder of the `c-source`) contains `ShadowArray.c`, `ShadowPointer.c` and `ShadowString.c`; this files implement general functionalities that communicate with native Shadow code. The specifics of each, are explained later on.
The other three folders are `Linux`, `Mac` and `Windows`. Those are chosen by the Shadow compiler, according to the operating system on which the compiler is ran. This is an advantage as we could write a unified Shadow code, and write C specific code for low level features which require system calls and platform specific operations that would otherwise be difficult to write in Shadow or LLVM. A good example for this is `ShadowConsole.c` which is available on the three platforms.

## C Includes
The compiler automatically references all `.h` files in the `include` folder. This means that whether you're in the root of the `c-source` folder, or in a platform specific one, any `.c` file could have a direct `#include` to the header files. For example, if we need to use `ShadowArray` features, we would simply `#include "ShadowArray.h"` and it will be included as expected at compile time.

## Shadow Classes/Objects/Singletons
Most of the `.c` files in the `c-source` folder, have a one-to-one representation with Shadow. For example, we have a class called `ShadowPointer` and we also have a `ShadowPointer.c`. Since C does not know anything about Shadow objects, we represent the classes simply as a `void*`. This allows us to still be able to pass the Shadow object back to Shadow from C code even if we cannot perform operations on it. An example of this could be seen in `ShadowThread.c` where the reference of the `Thread` object is passed to the newly spawned thread. The definition of the `ShadowThread` in C is simply `typedef void* ShadowThread;`.

## ShadowTypes.h
Shadow promises a specific length for each data structure type. A `byte` is guaranteed to be 8 bits, a `short` is guaranteed to be 16 bits, and so on... C is a little sloppy with those sizes, so predefined types which match the Shadow promises are supplied. Whenever data is exchanged between Shadow and C, we use the predefined types defined in `ShadowTypes.h`. 

## ShadowArray
Shadow uses a specific structure for arrays which are complicated and incompatible with C code. When we would like to perform operations on Shadow arrays, we first need to get a pointer to the array from Shadow code, some way or another (this can be seen in action in `ShadowString.c`), and then we could convert that pointer to a usable C struct using `UnpackShadowArray(ShadowArray, VoidArray*)` method. The first argument, `ShadowArray` is a pointer to the Shadow array, supplied from Shadow code. The `VoidArray` is a struct defined as:
```C

typedef struct {
	ShadowInt size; // how many elements are in the array
	void* data;     // the pointer to the data, which needs to be casted
					 // to the desired data structure.
} VoidArray;
```
###### Example
```C
#include "ShadowArray.h"
typedef struct {
	ShadowInt size;
	int* data;
} IntArray;

// let's assume the array in shadow has two elements [50, 50]
ShadowArray shadow_array_ref = GetIntArrayFromSomeShadowCode();

IntArray C_array;
UnpackShadowArray(C_array, (VoidArray*)shadow_array_ref);

int i, total = 0;
for(i = 0; i < C_array.size; ++i) {
	total += C_array.data[i];
}
printf("%d\n", total); // prints 100
```

## ShadowString
A Shadow String can be directly passed from Shadow to C code. The framework supplies two different methods to manipulate strings in C. `char* UnpackShadowStringToCStr(ShadowString)` and `void UnpackShadowString(ShadowString, ShadowStringData*)`.
The `UnpackShadowStringToCStr` method clones the string data and returns a null-terminated C string. The `UnpackShadowString` method on the other hand, stores the data in the pointer which is passed as the second argument as it is; thus, if the string is modified in C, it will also be modified in Shadow.
The structure of the struct is as follows:
```C
typedef struct {
	ShadowInt size;
	ShadowByte* chars;
	ShadowBoolean ascii;
} ShadowStringData;
```
###### Example
```C
// Shadow side
private extern __PrintFromC(String str) => ();

// in some method:
String str = "Hello from Shadow!";
__PrintFromC(str);
Console.printLine(str); // prints Hello from Shadow.

// C side
#include "ShadowString.h"
void __PrintFromC(ShadowString strRef) {
	ShadowStringData data;
	UnpackShadowString(strRef, &data);
	
	data.chars[17] = '.'; // changes the string in Shadow to "Hello from Shadow."
	int i;
	for(i = 0; i < data.size; ++i) {
		printf("%c", data.chars[i]);
	}
	printf("\n");
	
	// prints "Hello from Shadow."
}
```

## ShadowPointer
Shadow does not support direct pointer manipulation. For cross-platform compatibility, we sometimes need to allocate memory in C code, and store that pointer in Shadow. `ShadowPointer` is a managed wrapper, which stores the address of the pointer and can be passed around in Shadow. Shadow can perform several operations on it such as freeing this block of memory, and checking whether the pointer is valid.
When a block of memory is allocated in C, for example using `malloc/calloc`, we can store that pointer in Shadow to use it later. The managed `ShadowPointer` could be allocated using `ShadowPointer CreateShadowPointer(void* ptr, ShadowPointerType type);`

1. The first argument is the pointer allocated in C.
2. The second argument specifies whether Shadow code can free the pointer using `free()` or not. `SHADOW_CANNOT_FREE` specifies that calling `ShadowPointer.free()` would only invalidate the `ShadowPointer`. `SHADOW_CAN_FREE` speficies that calling `ShadowPointer.free()` would call the C `free()` and then would invalidate the `ShadowPointer`. Invalidating the `ShadowPointer` means that `ShadowPointer->isValid` would return `false` after calling `ShadowPointer.free()`. Calling `ShadowPointer.free()` more than once has no effect.

###### Example
```C
// Shadow side
private extern __allocateAnInteger(int i) => (ShadowPointer);
private extern __printInteger(ShadowPointer ptr) => ();
public main() => ()
{
	ShadowPointer ptr = __allocateAnInteger(55);
	__printInteger(ptr); // prints 55
}

// C side
#include "ShadowPointer.h"
ShadowPointer __allocateAnInteger(ShadowInteger i) {
	ShadowInteger* intPtr = malloc(sizeof(ShadowInteger));
	*intPtr = i;
	return CreateShadowPointer(intPtr, SHADOW_CAN_FREE);
}

void __printInteger(ShadowPointer ptr) {
	ShadowInteger* intPtr = ExtractPointer(ShadowInteger, ptr);
	printf("%d\n", *intPtr);
}
```
