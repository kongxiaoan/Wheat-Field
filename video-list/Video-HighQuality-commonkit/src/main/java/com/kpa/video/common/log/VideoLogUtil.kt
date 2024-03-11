package com.kpa.video.common.log

import android.util.Log

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
object VideoLogUtil {
    var TAG: String = "VideoTag"
    fun init(tag: String) {
        TAG = tag
    }

    fun d(message: String) {
        Log.d(TAG, message)
    }
}