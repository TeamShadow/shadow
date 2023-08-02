The stuff below is included with:
  #include <Shadow.h>

NOTE: Conventions used are:
		-   [ExportAssembly]
		    Method implemented in Shadow but visible to C: _package__Class_method(...)
		    (No mangling, can be called from C [or Shadow?] but needs the first parameter to be a pointer to this in C)
		-   [ImportAssembly]
		    Method implemented in C: __package__Class_method(...)
		    (No mangling, can be called from Shadow or C)
		-   [ImportNative]
		    Method implemented in raw LLVM: method(...)
		    (Automatically mangled to correct naming like other methods, can't be called from C)
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
  shadow_Class_t* _shadow_standard__Boolean_getClass();
  
  shadow_byte_t
  shadow_Class_t* _shadow_standard__Byte_getClass();
  
  shadow_ubyte_t
  shadow_Class_t* _shadow_standard__UByte_getClass();
  
  shadow_short_t
  shadow_Class_t* _shadow_standard__Short_getClass();
  
  shadow_ushort_t
  shadow_Class_t* _shadow_standard__UShort_getClass();

  shadow_int_t
  shadow_Class_t* _shadow_standard__Int_getClass();

  shadow_uint_t
  shadow_Class_t* _shadow_standard__UInt_getClass();
  
  shadow_code_t
  shadow_Class_t* _shadow_standard__Code_getClass();

  shadow_long_t
  shadow_Class_t* _shadow_standard__Long_getClass();
  
  shadow_ulong_t
  shadow_Class_t* _shadow_standard__ULong_getClass();

  shadow_float_t
  shadow_Class_t* _shadow_standard__Float_getClass();
  
  shadow_double_t
  shadow_Class_t* _shadow_standard__Double_getClass();
-----------------------------------------------------------------------------------
====================
=  Array =
====================
  type: shadow_Array_t
  ---

  ArrayData* __shadow_standard__Array_getData(const shadow_PrimitiveArray_t* shadowArray, ArrayData* array);

  shadow_Array_t* __shadow_standard__Array_create(size_t num, size_t size, void** data);
  void __shadow_standard__Array_free(shadow_Array_t* array);
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

  StringData* __shadow_standard__String_getData(const shadow_String_t* instance, StringData* str);
  char* __shadow_standard__String_getCString(const shadow_String_t* instance);
  shadow_String_t* __shadow_standard__String_create(const char*);
  void __shadow_standard__String_free(shadow_String_t*);
-----------------------------------------------------------------------------------
===========
= Pointer =
===========
  #include <natives/Pointer.h>
  type: shadow_Pointer_t*
  ---

  shadow_Pointer_t* __shadow_standard__Pointer_create(void* ptr, free_type_t type);

  // T: Type of the extracted pointer
  T* __shadow_standard__Pointer_extract(T, shadow_Pointer_t*);
-----------------------------------------------------------------------------------
===========
= Console =
===========
  #include <io/Console.h> // needed to be included
  type: shadow_Console_t*
  ---

  void _shadowConsole_printLine(shadow_Object_t*);
  void _shadowConsole_printErrorLine(shadow_Object_t*);
-----------------------------------------------------------------------------------
-----------------------------------------------------------------------------------
-----------------------------------------------------------------------------------

======================
= On the Shadow side =
======================

  // no name mangling
  // "this" argument is passed as any normal function
  // function body generated like any normal function
  [ExportAssembly]
  private extern _test(int x) => (boolean) {
	...
  }
    -- generates --
  define %boolean @_test(%shadow.test..Test*, %int) {
    ...
  }

  // name mangling
  // acts a normal function
  // Allows only Class1 and Class2 to import the function
  [ExportMethod(exportedTo = Class1:class)]
  private test() => () {
	...
  }
  * in Class1: *
  [ImportMethod]
  private test(OtherClass instance) => ();