LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := JniBitmapOperationsLibrary
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \
	-ljnigraphics \

LOCAL_SRC_FILES := \
	D:\Projects\trunk\app\src\main\jni\Android.mk \
	D:\Projects\trunk\app\src\main\jni\Application.mk \
	D:\Projects\trunk\app\src\main\jni\bitmap\Android.mk \
	D:\Projects\trunk\app\src\main\jni\bitmap\JniBitmapOperationsLibrary.cpp \
	D:\Projects\trunk\app\src\main\jni\native\Android.mk \
	D:\Projects\trunk\app\src\main\jni\native\native.c \
	D:\Projects\trunk\app\src\main\jni\native\observer.c \

LOCAL_C_INCLUDES += D:\Projects\trunk\app\src\main\jni
LOCAL_C_INCLUDES += D:\Projects\trunk\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
