package com.android.popup.interfaces

import android.app.Activity
import com.android.popup.DialogParams

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:
 */
interface IWindow {
    fun show(activity: Activity, dialogParams: DialogParams? = null)
    fun dismiss()
    fun isShowing(): Boolean
    fun setOnWindowDismissListener(listener: OnWindowDismissListener)
    fun setOnWindowShowListener(listener: OnWindowShowListener)
}