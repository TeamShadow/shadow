/**
 * A simple way to test if __createShadowPointer works correctly. The accompanying test
 * is in ExternalsTest.shadow.
 *
 * Author: Claude Abounegm
 */
#include <stddef.h>
#include <stdlib.h>
#include "Shadow.h"

ShadowPointer __Test_Allocate()
{	
	return __createShadowPointer(malloc(sizeof(int)));
}