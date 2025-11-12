package com.freshdesk.southwest.utils

import android.util.Log

const val LOG_TAG = "Southwest"

inline fun loge(message: () -> String) = Log.e(LOG_TAG, message())
inline fun logd(message: () -> String) = Log.d(LOG_TAG, message())
inline fun logi(message: () -> String) = Log.i(LOG_TAG, message())