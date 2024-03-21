package com.kpa.library.utils

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */

import android.os.Looper

inline fun checkMainThread() {
    if (Looper.myLooper() != Looper.getMainLooper()) {
        throw IllegalStateException("This method must be called from the main thread")
    }
}
