package com.android.popup

import com.android.popup.interfaces.IWindow

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:相关的属性和状态
 */
class WindowWrapper constructor(builder: Builder) {

    val mWindow: IWindow = builder.getIWindow()
    val priority = builder.getPriority()
    val isCanShow: Boolean = builder.isCanShow()
    val windowName: String = builder.getWindowName()
    val forceShow = builder.isForceShow()
    var isWindowShow = false
    var isShowOnce = builder.isShowOnce()
    var dialogParams: DialogParams? = builder.getDialogParams()

    public class Builder() {
        private var window: IWindow? = null
        private var priority: Int = 0
        private var showOnce = true
        private var isCanShow: Boolean = true
        private var windowName: String = ""
        private var forceShow = false
        private var dialogParams: DialogParams? = null

        fun setDialogParams(dialogParams: DialogParams) = apply {
            this.dialogParams = dialogParams
        }

        fun setShowOnce(showOnce: Boolean) = apply {
            this.showOnce = showOnce
        }

        fun setForceShow(forceShow: Boolean) = apply {
            this.forceShow = forceShow
        }


        fun setWindowName(windowName: String) = apply {
            this.windowName = windowName
        }

        fun setCanShow(canShow: Boolean) = apply {
            this.isCanShow = canShow
        }

        fun setPriority(priority: Int) = apply {
            this.priority = priority
        }

        fun setWindow(window: IWindow) = apply {
            this.window = window
        }

        fun isForceShow() = forceShow
        fun isCanShow() = isCanShow
        fun getWindowName() = windowName
        fun getPriority() = priority
        fun isShowOnce() = showOnce
        fun getDialogParams() = dialogParams
        fun getIWindow() = window ?: throw NullPointerException("window is null")
        fun build(): WindowWrapper {
            return WindowWrapper(this)
        }
    }
}