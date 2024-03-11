package com.kpa.video.uikit.video

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *
 * @author: kpa
 * @date: 2024/3/1
 * @description:
 */
class ShortVideoSceneView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val mRefreshLayout: SwipeRefreshLayout by lazy {
        SwipeRefreshLayout(context).apply {
            setOnRefreshListener {

            }
        }
    }

    private val mPageView: ShortVideoPageView by lazy {
        ShortVideoPageView(context)
    }


    fun pageView(): ShortVideoPageView = mPageView


    init {
        mRefreshLayout.addView(
            mPageView, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        addView(
            mRefreshLayout,
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
    }


}