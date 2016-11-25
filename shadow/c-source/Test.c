/**
 * A simple way to test if __createShadowPointer works correctly. The accompanying test
 * is in ExternalsTest.shadow.
 *
 * Author: Claude Abounegm
 */
#include <stddef.h>
#include <stdlib.h>
#include "ShadowPointer.h"

ShadowPointer __Test_Allocate()
{	
	return CreateShadowPointer(malloc(sizeof(int)));
}
