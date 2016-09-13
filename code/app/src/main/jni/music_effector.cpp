#include <jni.h>
#include <cstdlib>
#include <android/log.h>
#include <SLES/OpenSLES.h>
#include <SLES/OpenSLES_Android.h>
#include "music_effector.h"

SLObjectItf sl;
SLObjectItf output_mix;
SLBassBoostItf bass_boost;

JNIEXPORT void JNICALL Java_org_ls_musiceffector_MusicEffector_init(JNIEnv *env, jobject instance)
{
	const char TAG[] = "playUriTest";
	__android_log_print(ANDROID_LOG_DEBUG, TAG, "slCreateEngine");

	SLresult re;
	SLEngineOption options[] = {
		(SLuint32) SL_ENGINEOPTION_THREADSAFE,
		(SLuint32) SL_BOOLEAN_TRUE };
	re = slCreateEngine(&sl, 1, options, 0, NULL, NULL);
	re = (*sl)->Realize(sl, SL_BOOLEAN_FALSE);

	SLEngineItf engine;
	re = (*sl)->GetInterface(sl, SL_IID_ENGINE, &engine);

	const SLInterfaceID ids[5] = { SL_IID_BASSBOOST, SL_IID_ENVIRONMENTALREVERB, SL_IID_EQUALIZER, SL_IID_VIRTUALIZER, SL_IID_PRESETREVERB };
	const SLboolean req[5] = { SL_BOOLEAN_FALSE, SL_BOOLEAN_FALSE, SL_BOOLEAN_FALSE, SL_BOOLEAN_FALSE, SL_BOOLEAN_FALSE };
	re = (*engine)->CreateOutputMix(engine, &output_mix, 5, ids, req);
	(*output_mix)->Realize(output_mix, SL_BOOLEAN_FALSE);

	(*output_mix)->GetInterface(output_mix, SL_IID_BASSBOOST, &bass_boost);
	//SLVolumeItf volume;
	//(*output_mix)->GetInterface(output_mix, SL_IID_VOLUME, &volume);
	//(*volume)->SetMute(volume, SL_BOOLEAN_TRUE);
	(*bass_boost)->SetEnabled(bass_boost, SL_BOOLEAN_TRUE);

	SLDataLocator_URI locator_uri;
	locator_uri.locatorType = SL_DATALOCATOR_URI;
	locator_uri.URI = (SLchar*) "/sdcard/1.mp3";

	SLDataFormat_MIME format_mime;
	format_mime.containerType = SL_CONTAINERTYPE_UNSPECIFIED;
	format_mime.formatType = SL_DATAFORMAT_MIME;
	format_mime.mimeType = NULL;

	SLDataSource data_source;
	data_source.pLocator = &locator_uri;
	data_source.pFormat = &format_mime;

	SLDataLocator_OutputMix locator_outputmix;
	locator_outputmix.locatorType = SL_DATALOCATOR_OUTPUTMIX;
	locator_outputmix.outputMix = output_mix;

	SLDataSink sink;
	sink.pLocator = &locator_outputmix;
	sink.pFormat = NULL;

	SLObjectItf player;
	(*engine)->CreateAudioPlayer(engine, &player, &data_source, &sink, 0, NULL, NULL);
	(*player)->Realize(player, SL_BOOLEAN_FALSE);

	SLPlayItf play;
	(*player)->GetInterface(player, SL_IID_PLAY, &play);

	(*play)->SetPlayState(play, SL_PLAYSTATE_PLAYING);
/*
	(*output_mix)->Destroy(output_mix);
	(*player)->Destroy(player);
	(*sl)->Destroy(sl);
*/
}

JNIEXPORT void JNICALL Java_org_ls_musiceffector_MusicEffector_destory(JNIEnv *env, jobject instance)
{
	(*output_mix)->Destroy(output_mix);
	(*sl)->Destroy(sl);
}

JNIEXPORT void JNICALL Java_org_ls_musiceffector_MusicEffector_setBassboostStrength(JNIEnv *env, jobject instance, jint strength)
{
	(*bass_boost)->SetStrength(bass_boost, strength);
}
