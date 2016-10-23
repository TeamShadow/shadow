/*
* Shadow implementation of threads
* 
* Claude Abounegm
*/

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

int x = 0;

void* thread_func(void* arg) {
	int i;
	for (i = 0; i < 10000000; ++i)
		x += 1;
	return NULL;
}

int main()
{
	pthread_t* threads = calloc(5, sizeof(pthread_t));
	int i;
	for(i = 0; i < 5; ++i) {
		pthread_create(&threads[i], NULL, thread_func, NULL);
	}
	
	for(i = 0; i < 5; ++i) {
		pthread_join(threads[i], NULL);
	}
	
	printf("%d\n", x);
	
	return 0;
}