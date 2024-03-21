package com.kpa.library.core

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.Surface
import com.kpa.library.page.model.VideoPageItem
import com.kpa.library.player.controller.PlayerController
import com.kpa.library.player.core.IPlayer
import com.kpa.library.player.core.MediaSource
import com.kpa.library.player.core.TextureDisplayView
import com.kpa.library.widget.RatioFrameLayout
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description: 渲染视频内容
 */
class VideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RatioFrameLayout(context, attrs, defStyleAttr, defStyleRes), TextureDisplayView.SurfaceListener, IPlayer.PlayerEventListener {

    private var mLayerHost: VideoLayerHost? = null
    private var mDisplayView: TextureDisplayView? = null
    private var mController: PlayerController? = null

    private val mListeners: CopyOnWriteArrayList<VideoViewListener> =
        CopyOnWriteArrayList<VideoViewListener>()

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

    fun setDisplayView() {
        if(mDisplayView == null) {
            mDisplayView = TextureDisplayView(context)
            mDisplayView?.setSurfaceListener(this)
            addView(
                mDisplayView?.getDisplayView(), 0,
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER)
            )
        }
    }

    fun setReuseSurface(reuseSurface: Boolean) {
        mDisplayView?.setReuseSurface(reuseSurface)

    }

    fun getSurface(): Surface? {
        Log.d(PlayerController.TAG, "mDisplayView = ${mDisplayView?.getSurface()}")
        return mDisplayView?.getSurface()
    }

    fun bindController(controller: PlayerController) {
        if (mController == null) {
            mController = controller
            for (listener in mListeners) {
//                listener.onVideoViewBindController(controller)
            }
        }
    }

    fun unbindController(controller: PlayerController) {
        if (mController != null && mController === controller) {
            mController = null
            for (listener in mListeners) {
//                listener.onVideoViewUnbindController(controller)
            }
//            controller.removePlaybackListener(this)
        }
    }

    fun stopPlayback() {
        if (mController == null) return
        mController!!.stopPlayback()
    }

    /**
     * 原则上来讲列表数据和播放器数据要进行区分
     * 列表数据更着重的是列表数据的展示
     * 播放器数据可能完全包含列表数据 但是还会有播放器相关的数据
     */
    private var source: MediaSource? = null

    fun bindSource(source: MediaSource) {
        this.source = source
    }

    fun getSource(): MediaSource? {
        return source
    }

    fun bindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost == null) {
            mLayerHost = layerHost
        }
    }

    interface VideoViewListener : VideoEventListener, TextureDisplayView.SurfaceListener {
        /**
         * 播放器绑定回调
         * 绑定、解绑
         */
        /**
         * 绑定数据
         */
        fun onVideoViewBindDataSource(dataSource: VideoPageItem)
        fun onVideoViewClick(videoView: VideoView)
    }

    interface VideoEventListener {
        fun onConfigurationChanged(newConfig: Configuration?)

        fun onWindowFocusChanged(hasWindowFocus: Boolean)
    }

    override fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int) {
        for (listener in mListeners) {
            listener.onSurfaceAvailable(surface, width, height)
        }
    }

    override fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int) {

    }

    override fun onSurfaceUpdated(surface: Surface?) {

    }

    override fun onSurfaceDestroy(surface: Surface?) {

    }

    override fun onError(error: Exception) {
        TODO("Not yet implemented")
    }

    override fun onCompletion() {
        TODO("Not yet implemented")
    }

    override fun onInfo(what: Int, extra: Int) {
        TODO("Not yet implemented")
    }

    override fun onPrepared() {
        TODO("Not yet implemented")
    }

    override fun onVideoSizeChanged(width: Int, height: Int) {
        TODO("Not yet implemented")
    }

}