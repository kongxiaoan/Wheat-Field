package com.android.popup

import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.LayoutRes
import java.io.Serializable

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:
 */
class DialogParams(builder: Builder) : Serializable {
    val title: String? = builder.title
    val content: String? = builder.content
    val isRoundedCorners: Boolean = builder.isRoundedCorners
    val width: Int = builder.width
    val height: Int = builder.height
    val gravity: Int = builder.gravity
    val layoutId = builder.layoutId

    class Builder() {
        var title: String? = null
        var content: String? = null
        var isRoundedCorners: Boolean = false
        var layoutId: Int = 0
        var width: Int = WindowManager.LayoutParams.WRAP_CONTENT
        var height: Int = WindowManager.LayoutParams.WRAP_CONTENT
        var gravity: Int = Gravity.CENTER

        fun setLayoutId(@LayoutRes layoutId: Int) = apply {
            this.layoutId = layoutId
        }

        fun setTitle(title: String?) = apply { this.title = title }
        fun setContent(content: String?) = apply { this.content = content }
        fun setRoundedCorners(isRoundedCorners: Boolean) =
            apply { this.isRoundedCorners = isRoundedCorners }

        fun setWidth(width: Int) = apply { this.width = width }
        fun setHeight(height: Int) = apply { this.height = height }
        fun setGravity(gravity: Int) = apply { this.gravity = gravity }

        fun build(): DialogParams {
            return DialogParams(this)
        }
    }

    override fun toString(): String {
        return "DialogParams(title=$title, content=$content, isRoundedCorners=$isRoundedCorners, width=$width, height=$height, gravity=$gravity)"
    }
}