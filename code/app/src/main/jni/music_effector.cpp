#include <jni.h>
#include <cstdlib>
#include <android/log.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "music_effector.h"

JNIEXPORT void JNICALL Java_org_ls_musiceffector_MainActivity_playUriTest(JNIEnv *env, jobject instance, jstring jstring_uri)
{
	const char TAG[] = "playUriTest";
	__android_log_print(ANDROID_LOG_DEBUG, TAG, "slCreateEngine");

	jboolean is_copy;
	//const char *uri = env->GetStringUTFChars(jstring_uri, &is_copy);

	SLresult re;
	SLObjectItf sl;
	SLEngineOption options[] = {
		(SLuint32) SL_ENGINEOPTION_THREADSAFE,
		(SLuint32) SL_BOOLEAN_TRUE };
	re = slCreateEngine(&sl, 1, options, 0, NULL, NULL);
	re = (*sl)->Realize(sl, SL_BOOLEAN_FALSE);

	SLEngineItf engine;
	re = (*sl)->GetInterface(sl, SL_IID_ENGINE, &engine);
	
	SLObjectItf player;

	SLDataLocator_URI locator_uri;
	locator_uri.locatorType = SL_DATALOCATOR_URI;
	//locator_uri.URI = (SLchar*) uri;

	SLDataSource data_source;
	data_source.pFormat = NULL;
	data_source.pLocator = &locator_uri;
	//(*engine)->CreateAudioPlayer(engine, &player, &data_source, );


	SLObjectItf output_mix;
	SLInterfaceID itf_iids[] = { SL_IID_VOLUME };
	SLboolean itf_requireds[] = { SL_BOOLEAN_TRUE };
	re = (*engine)->CreateOutputMix(engine, &output_mix, 1, itf_iids, itf_requireds);
	
	(*sl)->Destroy(sl);

	//env->ReleaseStringUTFChars(jstring_uri, uri);
}
