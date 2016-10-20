/*
* Shadow implementation of System.sleep().
* 
* Claude Abounegm
*/

#include <time.h>

#define S_TO_MS 1000
#define MS_TO_NS 1000000

int nsleep(int ms)
{
	struct timespec t;
	
	if(ms < 1000)
	{
		t.tv_sec = 0;
		t.tv_nsec = ms * MS_TO_NS;
	}
	else
	{
		t.tv_sec = ms / S_TO_MS;
		t.tv_nsec = (ms - t.tv_sec * S_TO_MS) * MS_TO_NS;
	}

	return nanosleep(&t, NULL);
}

int main()
{
	return nsleep(1000);
}