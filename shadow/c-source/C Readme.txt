All the stuff below are included with this include:
  #include <Shadow.h>

NOTE1: If C calls a Shadow method that throws an exception, the program will immediately crash.
NOTE2: Standards used are:
		- C Core Method: shadowClassName_FunctionName(...)
		- C Helper Implemented in Shadow: _shadowClassName_FunctionName(...)
		- Shadow Method needed to be implemented in C: __shadowClassName_FunctionName(...)
-----------------------------------------------------------------------------------
Usage:
  #ifdef SHADOW_*

=================
=   Platforms   =
=================
  SHADOW_WINDOWS
  SHADOW_MAC
  SHADOW_LINUX

=================
= Architectures =
=================
  SHADOW_ARCH32
  SHADOW_ARCH64
-----------------------------------------------------------------------------------
===================
= Primitive types =
===================
  shadow_boolean_t
  shadow_Class_t* shadowBoolean_GetClass();
  
  shadow_byte_t
  shadow_Class_t* shadowByte_GetClass();
  
  shadow_ubyte_t
  shadow_Class_t* shadowUByte_GetClass();
  
  shadow_short_t
  shadow_Class_t* shadowShort_GetClass();
  
  shadow_ushort_t
  shadow_Class_t* shadowUShort_GetClass();

  shadow_int_t
  shadow_Class_t* shadowInt_GetClass();

  shadow_uint_t
  shadow_Class_t* shadowUInt_GetClass();
  
  shadow_code_t
  shadow_Class_t* shadowCode_GetClass();

  shadow_long_t
  shadow_Class_t* shadowLong_GetClass();
  
  shadow_ulong_t
  shadow_Class_t* shadowULong_GetClass();

  shadow_float_t
  shadow_Class_t* shadowFloat_GetClass();
  
  shadow_double_t
  shadow_Class_t* shadowDouble_GetClass();
-----------------------------------------------------------------------------------
====================
=  Primitive Array =
====================
  // NOTE: This is a pointer to a Shadow Primitive Array T[] and IS NOT an Array<T> object.
  type: shadow_PrimitiveArray_t
  ---

  ArrayData* shadowArray_GetData(const shadow_PrimitiveArray_t* shadowArray, ArrayData* array);

  shadow_PrimitiveArray_t* shadowArray_Create(size_t num, size_t size, void** data);
  void shadowArray_Free(shadow_PrimitiveArray_t* array);
-----------------------------------------------------------------------------------
==========
= Object =
==========
  #include <standard/Object.h>
  type: shadow_Object_t*
  ---

  // Note: any reference type can be passed to this method. 
  //       for example, passing a reference of type `shadow_File_t*` will return 
  //       a Shadow String containing the path of the file. This is 
  //       equivalent to ref.toString() but in C.
  shadow_String_t* shadowObject_ToString(shadow_Object_t* ref);
  
  shadow_Class_t* shadowObject_GetClass(shadow_Object_t* ref);
-----------------------------------------------------------------------------------
==========
= String =
==========
  #include <standard/String.h>
  type: shadow_String_t*
  ---
  
  typedef struct { 
    shadow_int_t size;
    shadow_byte_t* chars;
    shadow_boolean_t ascii;
  } StringData;

  StringData* shadowString_GetData(const shadow_String_t* instance, StringData* str);
  char* shadowString_GetCString(const shadow_String_t* instance);

  // Create and Free are still experimental.
  shadow_String_t* shadowString_Create(const char*);
  void shadowString_Free(shadow_String_t*);
-----------------------------------------------------------------------------------
===========
= Pointer =
===========
  #include <natives/Pointer.h>
  type: shadow_Pointer_t*
  ---

  shadow_Pointer_t* shadowPointer_Create(void* ptr, free_type_t type);

  // T: Type of the extracted pointer
  T* shadowPointer_Extract(T, shadow_Pointer_t*);
-----------------------------------------------------------------------------------
===========
= Console =
===========
  #include <io/Console.h> // needed to be included
  type: shadow_Console_t*
  ---

  void shadowConsole_PrintLine(shadow_Object_t*);
  void shadowConsole_PrintErrorLine(shadow_Object_t*);
-----------------------------------------------------------------------------------
-----------------------------------------------------------------------------------
-----------------------------------------------------------------------------------

======================
= On the Shadow side =
======================

  // no name mangling
  // no "this" as first argument
  private extern _Test(int x) => (boolean);
    -- generates --
  declare %boolean @_Test(%int)


  // no name mangling
  // "this" argument is passed as any normal function
  // function body generated like any normal function
  private extern _Test(int x) => (boolean) {
	...
  }
    -- generates --
  define %boolean @_Test(%shadow.test..Test*, %int) {
    ...
  }

  // name mangling
  // acts a normal function
  // Allows only Class1 and Class2 to import the function
  private [Class1, Class2] $Test() => () {
	...
  }
  * in Class1 and Class2: *
  private extern $Test(OtherClass instance) => ();