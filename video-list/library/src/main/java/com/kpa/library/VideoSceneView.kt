package com.kpa.library

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.kpa.library.page.VideoPageView

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class VideoSceneView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    val videoPageView: VideoPageView by lazy {
        VideoPageView(context)
    }

    private val mLoadMoreProgressBar: ContentLoadingProgressBar by lazy {
        LayoutInflater.from(context)
            .inflate(
                R.layout.video_loading_more,
                this,
                false
            ) as ContentLoadingProgressBar
    }

    init {
        addView(
            videoPageView, LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        addView(
            mLoadMoreProgressBar, LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM
            )
        )
    }
}