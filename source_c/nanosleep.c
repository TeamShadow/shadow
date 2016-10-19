/*
* Shadow implementation of System.sleep().
* 
* Claude Abounegm
*/

#include <time.h>

int nsleep(int ms)
{
	struct timespec t;
	
	if(ms < 1000)
	{
		t.tv_sec = 0;
		t.tv_nsec = ms * 1000000;
	}
	else
	{
		t.tv_sec = (ms / 1000);
		t.tv_nsec = (ms - t.tv_sec * 1000) * 1000000;
	}

	return nanosleep(&t, NULL);
}

int main()
{
	return nsleep(1000);
}