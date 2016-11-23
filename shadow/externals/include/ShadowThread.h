#ifndef SHADOW_THREADS_H
#define SHADOW_THREADS_H

ShadowPointer __ShadowThread_Spawn(void* (*start_routine)(void*), void* arg);

#endif