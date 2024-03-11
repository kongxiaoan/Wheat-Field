package com.kpa.video.uikit.video

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.kpa.player.event.Dispatcher
import com.kpa.player.event.Event
import com.kpa.video.playerkit.PlaybackController
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.base.PlayerEvent
import com.kpa.video.uikit.model.VideoItem
import com.kpa.video.uikit.widgets.viewpager2.OnPageChangeCallbackCompat

/**
 *
 * @author: kpa
 * @date: 2024/3/1
 * @description:
 */
class ShortVideoPageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), LifecycleEventObserver {

    private val mViewPager: ViewPager2 by lazy {
        ViewPager2(context).apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = mShortVideoAdapter
            registerOnPageChangeCallback(object : OnPageChangeCallbackCompat(this) {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
    }

    private val mController: PlaybackController by lazy {
        PlaybackController()
    }

    private val mShortVideoAdapter: ShortVideoAdapter by lazy {
        ShortVideoAdapter()
    }

    var mLifecycle: Lifecycle? = null
        set(value) {
            if (field != value) {
                field?.removeObserver(this)
                field = value
            }
            field?.addObserver(this)
        }

    init {
        addView(
            mViewPager,
            LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        mController.addPlaybackListener(object : Dispatcher.EventListener {
            override fun onEvent(event: Event) {
                when (event.code()) {
                    PlayerEvent.State.COMPLETED -> {
                        val player = event.owner(Player::class.java)
                        if (player != null && !player.isLooping()  /* 1 播放下一个 */) {
                            val currentPosition: Int = getCurrentItem()
                            val nextPosition = currentPosition + 1
                            if (nextPosition < mShortVideoAdapter.getItemCount()) {
                                setCurrentItem(nextPosition, true)
                            }
                        }
                    }
                }
            }

        })
    }

    fun getCurrentItem(): Int {
        return mViewPager.currentItem
    }

    fun setCurrentItem(position: Int, smoothScroll: Boolean) {
        mViewPager.setCurrentItem(position, smoothScroll)
    }

    public fun setItems(videoItems: MutableList<VideoItem>) {
        mShortVideoAdapter.setItems(videoItems)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

    }
}