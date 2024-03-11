package com.kpa.video.playerkit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntDef
import com.kpa.video.playerkit.R

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
open class RatioFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        /**
         * 表示宽高比的固定宽度模式。
         * 在这种模式下，宽度是固定的，高度可以根据宽高比进行调整。
         */
        const val RATIO_MODE_FIXED_WIDTH = 0

        /**
         * 表示宽高比的固定高度模式。
         * 在这种模式下，高度是固定的，宽度可以根据宽高比进行调整。
         */
        const val RATIO_MODE_FIXED_HEIGHT = 1

    }

    private var mRatio = 0f

    /**
     * Ratio mode of [RatioFrameLayout]. One of [.RATIO_MODE_FIXED_WIDTH] or
     * [.RATIO_MODE_FIXED_HEIGHT]
     */
    @MustBeDocumented
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(RATIO_MODE_FIXED_WIDTH, RATIO_MODE_FIXED_HEIGHT)
    annotation class RatioMode


    @RatioMode
    private var mRatioMode = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout, defStyleAttr, 0)
        try {
            mRatioMode = a.getInt(R.styleable.RatioFrameLayout_ratioMode, RATIO_MODE_FIXED_WIDTH)
            mRatio = a.getFloat(R.styleable.RatioFrameLayout_ratio, 0f)
        } finally {
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mRatio <= 0 || mRatioMode < 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            if (mRatioMode == RATIO_MODE_FIXED_WIDTH) {
                var width = MeasureSpec.getSize(widthMeasureSpec)
                val height = (width / mRatio) as Int
                super.onMeasure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
                )
            } else if (mRatioMode == RATIO_MODE_FIXED_HEIGHT) {
                val height = MeasureSpec.getSize(heightMeasureSpec)
                val width = (height * mRatio).toInt()
                super.onMeasure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    heightMeasureSpec
                )
            } else {
                throw IllegalArgumentException("unsupported ratio mode! $mRatioMode")
            }
        }
    }

    /**
     * @param ratio width/height
     */
    fun setRatio(ratio: Float) {
        if (mRatio != ratio) {
            mRatio = ratio
            requestLayout()
        }
    }

    fun getRatio(): Float {
        return mRatio
    }

    fun getRatioMode(): Int {
        return mRatioMode
    }

    fun setRatioMode(@RatioMode ratioMode: Int) {
        if (mRatioMode != ratioMode) {
            mRatioMode = ratioMode
            requestLayout()
        }
    }
}