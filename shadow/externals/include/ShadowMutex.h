#ifndef SHADOW_MUTEX_H
#define SHADOW_MUTEX_H

ShadowPointer __ShadowMutex_Initialize();
ShadowBoolean __ShadowMutex_Destroy(ShadowPointer);
ShadowBoolean __ShadowMutex_Lock(ShadowPointer);
ShadowBoolean __ShadowMutex_Unlock(ShadowPointer);

#endif