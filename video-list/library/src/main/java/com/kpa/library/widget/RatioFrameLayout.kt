package com.kpa.library.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntDef
import com.kpa.library.R

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:布局中灵活控制视图的宽高比，适用于需要定制宽高比的场景
 */
open class RatioFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val RATIO_MODE_FIXED_WIDTH = 0
        const val RATIO_MODE_FIXED_HEIGHT = 1
    }

    var mRatio = 0f

    @RatioMode
    var mRatioMode = 0

    @IntDef(RATIO_MODE_FIXED_WIDTH, RATIO_MODE_FIXED_HEIGHT)
    annotation class RatioMode


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
                val width = MeasureSpec.getSize(widthMeasureSpec)
                val height = (width / mRatio).toInt()
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
            this.mRatio = ratio
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
            this.mRatioMode = ratioMode
            requestLayout()
        }
    }
}