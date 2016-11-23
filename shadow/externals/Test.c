#include <stddef.h>
#include <stdlib.h>
#include <pthread.h>
#include "Shadow.h"

ShadowPointer __Test_Allocate()
{	
	return __createShadowPointer(malloc(sizeof(int)));
}