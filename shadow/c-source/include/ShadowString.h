/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_STRING_H
#define SHADOW_STRING_H

typedef struct {
	ShadowInt size;
	ShadowByte* chars;
} String;

// String.native.ll
void __getShadowArrayFromString(ShadowString, ShadowArray*);

// ShadowString.c
char* __unpackShadowString(ShadowString str);
void __getDataFromShadowString(ShadowString stringRef, String* str);

#endif