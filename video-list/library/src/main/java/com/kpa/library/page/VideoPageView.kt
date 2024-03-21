package com.kpa.library.page

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.kpa.library.core.VideoView
import com.kpa.library.page.model.VideoPageItem
import com.kpa.library.player.controller.PlayerController
import com.kpa.library.widget.OnPageChangeCallbackCompat

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class VideoPageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes), LifecycleEventObserver {

    private val mAdapter: VideoPageAdapter by lazy {
        VideoPageAdapter()
    }
    private val mViewPager: ViewPager2 by lazy {
        ViewPager2(context).apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = mAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallbackCompat(this@apply) {
                override fun onPageSelected(position: Int) {
                    togglePlayback(position)
                }
            })
        }
    }

    private fun togglePlayback(currentPosition: Int) {
        if (!mLifeCycle!!.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            return
        }
        val videoItem: VideoPageItem = mAdapter.getItem(currentPosition)
        val videoView: VideoView =
            findItemViewByPosition(
                mViewPager,
                currentPosition
            ) as VideoView
        Log.d(PlayerController.TAG, "videoView = $videoView ${mController.videoView()}")
        if (mController.videoView() == null) {
            mController.bind(videoView)
            mController.startPlayback()
        } else {
            if (videoView !== mController.videoView()) {
                mController.stopPlayback()
                mController.bind(videoView)
            }
            mController.startPlayback()
        }
    }

    private fun findItemViewByPosition(pager: ViewPager2, position: Int): View? {
        val recyclerView = pager.getChildAt(0) as RecyclerView
        if (recyclerView != null) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (layoutManager != null) {
                return layoutManager.findViewByPosition(position)
            }
        }
        return null
    }
    private val mController: PlayerController by lazy {
        PlayerController()
    }
    private var mLifeCycle: Lifecycle? = null

    init {
        addView(
            mViewPager, LayoutParams(
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    fun setLifeCycle(lifeCycle: Lifecycle) {
        if (mLifeCycle !== lifeCycle) {
            if (mLifeCycle != null) {
                mLifeCycle!!.removeObserver(this)
            }
            mLifeCycle = lifeCycle
        }
        if (mLifeCycle != null) {
            mLifeCycle!!.addObserver(this)
        }
    }

    fun setItems(videoItems: List<VideoPageItem>) {
        mAdapter.setItems(videoItems)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                mLifeCycle?.removeObserver(this)
                mLifeCycle = null
            }

            else -> {

            }
        }
    }
}