#include <jni.h>
#include <stdlib.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "music_effector.h"

JNIEXPORT jstring JNICALL Java_org_ls_musiceffector_MainActivity_haha(JNIEnv *env, jobject instance)
{
    SLObjectItf pEngine;
    slCreateEngine(&pEngine, 0, NULL, 0, NULL, NULL);
    return (*env)->NewStringUTF(env, "");
}
