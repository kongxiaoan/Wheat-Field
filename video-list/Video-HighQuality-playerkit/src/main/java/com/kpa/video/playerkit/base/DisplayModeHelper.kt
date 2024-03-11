package com.kpa.video.playerkit.base

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntDef

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class DisplayModeHelper {

    companion object {
        @IntDef(
            DISPLAY_MODE_DEFAULT,
            DISPLAY_MODE_ASPECT_FILL_X,
            DISPLAY_MODE_ASPECT_FILL_Y,
            DISPLAY_MODE_ASPECT_FIT,
            DISPLAY_MODE_ASPECT_FILL
        )
        @Retention(
            AnnotationRetention.SOURCE
        )
        annotation class DisplayMode


        /**
         * 画面宽高都充满控件，可能会变形
         */
        const val DISPLAY_MODE_DEFAULT = 0

        /**
         * 画面宽充满控件，高按视频比例适配
         */
        const val DISPLAY_MODE_ASPECT_FILL_X = 1

        /**
         * 画面宽充满控件，高按视频比例适配
         */
        const val DISPLAY_MODE_ASPECT_FILL_Y = 2

        /**
         * 画面长边充满控件，短边按比例适配。保证画面不被裁剪，可能有黑边
         */
        const val DISPLAY_MODE_ASPECT_FIT = 3

        /**
         * 画面短片充满控件，长边按比例适配。画面可能会被裁剪，没有黑边
         */
        const val DISPLAY_MODE_ASPECT_FILL = 4
    }


    private var mDisplayAspectRatio = 0f

    @DisplayMode
    private var mDisplayMode = DISPLAY_MODE_DEFAULT

    private var mContainerView: FrameLayout? = null
    private var mDisplayView: View? = null


    fun map(@DisplayMode displayMode: Int): String? {
        return when (displayMode) {
            DISPLAY_MODE_DEFAULT -> "default"
            DISPLAY_MODE_ASPECT_FILL_X -> "aspect_fill_x"
            DISPLAY_MODE_ASPECT_FILL_Y -> "aspect_fill_y"
            DISPLAY_MODE_ASPECT_FIT -> "aspect_fit"
            DISPLAY_MODE_ASPECT_FILL -> "aspect_fill"
            else -> throw IllegalArgumentException("unsupported displayMode! $displayMode")
        }
    }

    fun setDisplayAspectRatio(dar: Float) {
        mDisplayAspectRatio = dar
        apply()
    }

    fun setDisplayMode(@DisplayMode displayMode: Int) {
        mDisplayMode = displayMode
        apply()
    }

    @DisplayMode
    fun getDisplayMode(): Int {
        return mDisplayMode
    }

    fun setContainerView(containerView: FrameLayout?) {
        mContainerView = containerView
        apply()
    }

    fun setDisplayView(displayView: View?) {
        mDisplayView = displayView
        apply()
    }

    fun apply() {
        if (mDisplayView == null) return
        mDisplayView!!.removeCallbacks(applyDisplayMode)
        mDisplayView!!.postOnAnimation(applyDisplayMode)
    }

    private val applyDisplayMode = Runnable { applyDisplayMode() }

    private fun applyDisplayMode() {
        val containerView = mContainerView ?: return
        val containerWidth = containerView.width
        val containerHeight = containerView.height
        val displayView = mDisplayView ?: return
        val displayMode = mDisplayMode
        val displayAspectRatio = mDisplayAspectRatio
        if (displayAspectRatio <= 0) return
        val containerRatio = containerWidth / containerHeight.toFloat()
        val displayGravity = Gravity.CENTER
        val displayWidth: Int
        val displayHeight: Int
        when (displayMode) {
            DISPLAY_MODE_DEFAULT -> {
                displayWidth = containerWidth
                displayHeight = containerHeight
            }

            DISPLAY_MODE_ASPECT_FILL_X -> {
                displayWidth = containerWidth
                displayHeight = (containerWidth / displayAspectRatio).toInt()
            }

            DISPLAY_MODE_ASPECT_FILL_Y -> {
                displayWidth = (containerHeight * displayAspectRatio).toInt()
                displayHeight = containerHeight
            }

            DISPLAY_MODE_ASPECT_FIT -> if (displayAspectRatio >= containerRatio) {
                displayWidth = containerWidth
                displayHeight = (containerWidth / displayAspectRatio).toInt()
            } else {
                displayWidth = (containerHeight * displayAspectRatio).toInt()
                displayHeight = containerHeight
            }

            DISPLAY_MODE_ASPECT_FILL -> if (displayAspectRatio >= containerRatio) {
                displayWidth = (containerHeight * displayAspectRatio).toInt()
                displayHeight = containerHeight
            } else {
                displayWidth = containerWidth
                displayHeight = (containerWidth / displayAspectRatio).toInt()
            }

            else -> throw IllegalArgumentException("unknown displayMode = $displayMode")
        }
        val displayLP = displayView.layoutParams as FrameLayout.LayoutParams
        if (displayLP.height != displayHeight || displayLP.width != displayWidth || displayLP.gravity != displayGravity) {
            displayLP.gravity = displayGravity
            displayLP.width = displayWidth
            displayLP.height = displayHeight
            displayView.requestLayout()
        }
    }

    fun calDisplayAspectRatio(videoWidth: Int, videoHeight: Int, sampleAspectRatio: Float): Float {
        val ratio = calRatio(videoWidth, videoHeight)
        return if (sampleAspectRatio > 0) {
            ratio * sampleAspectRatio
        } else ratio
    }

    private fun calRatio(videoWidth: Int, videoHeight: Int): Float {
        return if (videoWidth > 0 && videoHeight > 0) {
            videoWidth / videoHeight.toFloat()
        } else 0F
    }

}