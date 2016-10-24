/*
* Shadow implementation of Thread.sleep().
* 
* Claude Abounegm
*/

#include <time.h>

int sleep(int sec, int nsec)
{
	struct timespec t = { sec, nsec };
	return nanosleep(&t, NULL);
}

int main()
{
	return nsleep(1000);
}