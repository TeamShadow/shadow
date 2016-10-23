/*
* Shadow implementation of threads
* 
* Claude Abounegm
*/

#include <pthread.h>

pthread_mutex_t counterLock;
int nextThreadId = 0;

void synchronizedEnter(pthread_mutex_t* lock) {
	pthread_mutex_lock(lock);
}

void synchronizedExit(pthread_mutex_t* lock) {
	pthread_mutex_unlock(lock);
}

int getNextId() {
	synchronizedEnter(&counterLock);
	int currentId = nextThreadId;
	nextThreadId += 1;
	synchronizedExit(&counterLock);
	
	return currentId;
}

int main()
{
	pthread_mutex_init(&counterLock, NULL);
	
	getNextId();
	
	pthread_mutex_destroy(&counterLock);
	return 0;
}