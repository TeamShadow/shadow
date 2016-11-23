#ifndef SHADOW_POINTER_H
#define SHADOW_POINTER_H

typedef void* ShadowPointer;

ShadowPointer __createShadowPointer(void*);
void* __extractRawPointer(ShadowPointer);

#endif