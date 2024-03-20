package com.android.popup

import android.app.Activity
import android.util.Log
import java.util.PriorityQueue

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:
 */
object PopupManager {
    private val popupQueue =
        PriorityQueue<KeyValuePair<String, WindowWrapper>>(compareByDescending {
            it.value.priority
        })
    const val TAG = "PopupManager"
    private var autoShowNext: Boolean = false
    fun autoShowNext(autoShowNext: Boolean) = apply {
        this.autoShowNext = autoShowNext
    }

    /**
     * 添加弹窗到队列
     */
    @JvmStatic
    fun addWindow(windowWrapper: WindowWrapper) {
        popupQueue.add(KeyValuePair(windowWrapper.windowName, windowWrapper))
        Log.d(TAG, "add window size = ${popupQueue.size} windowWrapper = ${windowWrapper.dialogParams.toString()}")
    }

    @JvmStatic
    fun showTarget(activity: Activity, windowWrapper: WindowWrapper) {
        if (windowWrapper.isCanShow && isActivityAlive(activity)) {
            val mWindow = windowWrapper.mWindow
            windowWrapper.isWindowShow = true
            mWindow.setOnWindowDismissListener {
                windowWrapper.isWindowShow = false
                if (windowWrapper.isShowOnce) {
                    popupQueue.removeIf { it.value == windowWrapper }
                }
                if (autoShowNext) {
                    showNext(activity)
                }
            }
        }
    }

    fun continueShow(activity: Activity) {
        var keyValuePair = popupQueue.poll()
        Log.d(TAG, "keyValuePair = ${popupQueue.size}")
        val windowWrapper = keyValuePair?.value
        windowWrapper?.run {
            Log.d(TAG, "!isWindowShow && isCanShow = ${!isWindowShow && isCanShow}")
            if (!isWindowShow && isCanShow) {
                isWindowShow = true
                mWindow.setOnWindowDismissListener {
                    isWindowShow = false
                    Log.d(TAG, "${popupQueue.size}")
                    if (isShowOnce) {
                        popupQueue.remove(keyValuePair)
                    }
                    if (autoShowNext) {
                        showNext(activity)
                    }
                }
                mWindow.show(activity, dialogParams)
            }
        }
    }

    private fun showNext(activity: Activity) {
        val nextPopup = popupQueue.poll()
        nextPopup?.run {
            this.value.mWindow.show(activity, this.value.dialogParams)
        }
    }

    @JvmStatic
    fun clear() {
        popupQueue.forEach {
            var windowWrapper = it.value
            windowWrapper?.run {
                var window = mWindow
                window?.run {
                    if (isShowing()) {
                        dismiss()
                    }
                }
            }
        }
        popupQueue.clear()
    }

    private fun isActivityAlive(activity: Activity): Boolean {
        return (!activity.isDestroyed && !activity.isFinishing)
    }

    private fun canShowPopup(windowWrapper: WindowWrapper): Boolean {
        if (!windowWrapper.isCanShow) {
            return false
        }
        for (wrapper in popupQueue) {
            if (wrapper.value.priority > windowWrapper.priority) {
                return false
            }
        }
        return true
    }
}