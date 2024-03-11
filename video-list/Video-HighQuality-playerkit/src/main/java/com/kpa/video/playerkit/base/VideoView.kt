package com.kpa.video.playerkit.base

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import com.kpa.player.event.Dispatcher
import com.kpa.player.event.Event
import com.kpa.video.playerkit.PlaybackController
import com.kpa.video.playerkit.source.MediaSource
import com.kpa.video.playerkit.widgets.RatioFrameLayout
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class VideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RatioFrameLayout(context, attrs, defStyleAttr), Dispatcher.EventListener,
    DisplayView.SurfaceListener {

    private val mDisplayModeHelper: DisplayModeHelper by lazy {
        DisplayModeHelper()
    }
    private var mOnClickListener: OnClickListener? = null
    private var mInterceptDispatchClick = false
    private val mListeners = CopyOnWriteArrayList<VideoViewListener>()
    private var mHasWindowFocus: Boolean = false
    private var mLayerHost: VideoLayerHost? = null
    private var mController: PlaybackController? = null
    private var mSource: MediaSource? = null

    init {
        super.setOnClickListener { v ->
            mOnClickListener?.run { onClick(v) }
            if (!mInterceptDispatchClick) {
                mListeners.forEach {
                    it.onVideoViewClick(this)
                }
            }
        }
    }

    fun bindDataSource(source: MediaSource) {
        mSource = source
        mListeners.forEach {
            it.onVideoViewBindDataSource(source)
        }
    }

    fun getDataSource(): MediaSource? {
        return mSource
    }

    fun bindController(controller: PlaybackController) {
        if (mController == null) {
            mController = controller
            mController?.addPlaybackListener(this)
            for (listener in mListeners) {
                listener.onVideoViewBindController(controller)
            }
        }
    }

    fun bindLayerHost(layerHost: VideoLayerHost) {
        if (this.mLayerHost == null) {
            mLayerHost = layerHost
        }
    }

    fun unbindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost != null && mLayerHost === layerHost) {
            mLayerHost = null
        }
    }

    fun addVideoViewListener(listener: VideoViewListener?) {
        if (listener != null) {
            mListeners.addIfAbsent(listener)
        }
    }

    fun removeVideoViewListener(listener: VideoViewListener?) {
        if (listener != null) {
            mListeners.remove(listener)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mDisplayModeHelper.apply()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mListeners.forEach {
            it.onConfigurationChanged(newConfig)
        }
    }

    fun controller(): PlaybackController? {
        return mController
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        // Using mHasWindowFocus to avoid onWindowFocusChanged callback multi times with same
        // hasWindowFocus value on some devices.
        if (mHasWindowFocus == null || mHasWindowFocus != hasWindowFocus) {
            mHasWindowFocus = hasWindowFocus
            for (listener in mListeners) {
                listener.onWindowFocusChanged(hasWindowFocus)
            }
        }
    }

    interface ViewEventListener {
        fun onConfigurationChanged(newConfig: Configuration?)
        fun onWindowFocusChanged(hasWindowFocus: Boolean)
    }


    interface VideoViewListener : DisplayView.SurfaceListener, ViewEventListener {
        fun onVideoViewBindController(controller: PlaybackController?)
        fun onVideoViewUnbindController(controller: PlaybackController?)
        fun onVideoViewBindDataSource(dataSource: MediaSource?)
        fun onVideoViewClick(videoView: VideoView)
        fun onVideoViewDisplayModeChanged(
            @DisplayModeHelper.Companion.DisplayMode oldMode: Int,
            @DisplayModeHelper.Companion.DisplayMode newMode: Int
        )

        fun onVideoViewDisplayViewCreated(displayView: View?)
        fun onVideoViewDisplayViewChanged(oldView: View?, newView: View?)
        fun onVideoViewPlaySceneChanged(fromScene: Int, toScene: Int)
        open class Adapter : VideoViewListener {
            override fun onVideoViewBindController(controller: PlaybackController?) {}
            override fun onVideoViewUnbindController(controller: PlaybackController?) {}
            override fun onVideoViewBindDataSource(dataSource: MediaSource?) {}
            override fun onVideoViewClick(videoView: VideoView) {}
            override fun onVideoViewDisplayViewChanged(oldView: View?, newView: View?) {}
            override fun onVideoViewDisplayModeChanged(
                @DisplayModeHelper.Companion.DisplayMode fromMode: Int,
                @DisplayModeHelper.Companion.DisplayMode toMode: Int
            ) {
            }

            override fun onVideoViewDisplayViewCreated(displayView: View?) {}
            override fun onVideoViewPlaySceneChanged(fromScene: Int, toScene: Int) {}
            override fun onConfigurationChanged(newConfig: Configuration?) {}
            override fun onWindowFocusChanged(hasWindowFocus: Boolean) {}
            override fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int) {}
            override fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int) {}
            override fun onSurfaceUpdated(surface: Surface?) {}
            override fun onSurfaceDestroy(surface: Surface?) {}
        }
    }


    override fun setOnClickListener(l: OnClickListener?) {
        this.mOnClickListener = l
    }

    fun setInterceptDispatchClick(interceptClick: Boolean) {
        mInterceptDispatchClick = interceptClick
    }

    fun isInterceptDispatchClick(): Boolean {
        return mInterceptDispatchClick
    }


    override fun onEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int) {
        mListeners.forEach {
            it.onSurfaceAvailable(surface, width, height)
        }
    }

    override fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int) {
        mListeners.forEach {
            it.onSurfaceSizeChanged(surface, width, height)
        }
    }

    override fun onSurfaceUpdated(surface: Surface?) {
        mListeners.forEach {
            it.onSurfaceUpdated(surface)
        }
    }

    override fun onSurfaceDestroy(surface: Surface?) {
        mListeners.forEach {
            it.onSurfaceDestroy(surface)
        }
    }
}