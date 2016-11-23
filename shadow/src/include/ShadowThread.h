/**
 * Author: Claude Abounegm
 */
#ifndef SHADOW_THREADS_H
#define SHADOW_THREADS_H

ShadowPointer __ShadowThread_Spawn(void* (*thread_start)(ShadowThread), ShadowThread currentThread);

#endif