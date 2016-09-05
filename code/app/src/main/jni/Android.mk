LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := music_effector
LOCAL_SRC_FILES := music_effector.c
LOCAL_LDLIBS += -lOpenSLES

include $(BUILD_SHARED_LIBRARY)