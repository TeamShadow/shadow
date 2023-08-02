#include <Shadow.h>

// Imported from ImportExportAssemblyTest.shadow
// ("this" pointer is required when importing methods)
shadow_int_t _shadow_test__ImportExportAssemblyTest_add(void* thisClass, shadow_int_t a, shadow_int_t b);

// Exported to ImportExportAssemblyTest.shadow
// ("this" pointer isn't required, but makes calling add easier)
shadow_int_t __shadow_test__ImportExportAssemblyTest_multiply(void* thisClass, shadow_int_t a, shadow_int_t b) {
    shadow_int_t result = 0;
    shadow_int_t i;
    for (i = 0; i < b; i++) {
        result = _shadow_test__ImportExportAssemblyTest_add(thisClass, result, a);
    }

    return result;
}

