package com.android.popup

import androidx.lifecycle.MutableLiveData
import com.android.popup.interfaces.OnWindowDismissListener

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:
 */
object WindowHelper {
    var isActivityShow = false
    var activityDismissListener: OnWindowDismissListener? = null
    fun onDestroy() {
        activityDismissListener = null
        isActivityShow = false
    }
}