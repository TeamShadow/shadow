#include <stdio.h>

#ifndef SHADOW_H
#define SHADOW_H

void print_Pint(int i) {
	printf("%d", i);
}
void print_Puint(unsigned int i) {
	printf("%u", i);
}
void print_PString(char *str) {
	printf("%s", str);
}
void printLine(void) {
	printf("\n");
}
void printLine_Pint(int i) {
	print_Pint(i);
	printLine();
}
void printLine_Puint(unsigned int i) {
	print_Puint(i);
	printLine();
}
void printLine_PString(char *str) {
	print_PString(str);
	printLine();
}

#endif
