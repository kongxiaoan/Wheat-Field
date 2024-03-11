package com.kpa.video.playerkit.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import com.kpa.video.common.log.VideoLogUtil
import com.kpa.video.playerkit.PlaybackController

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
abstract class VideoLayer : VideoView.VideoViewListener.Adapter(),
    VideoLayerHost.VideoLayerHostListener {

    private var mLayerView: View? = null
    private var mLayerHost: VideoLayerHost? = null
    private var mIgnoreLock = false

    protected abstract fun createView(parent: ViewGroup): View
    fun videoView(): VideoView? {
        return if (mLayerHost == null) null else mLayerHost!!.videoView()
    }

    fun bindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost == null) {
            mLayerHost = layerHost
            layerHost.addVideoLayerHostListener(this)
            VideoLogUtil.d("onBindLayerHost $layerHost")
            onBindLayerHost(layerHost)
            val videoView = layerHost.videoView()
            videoView?.let { bindVideoView(it) }
        }
    }

    fun unbindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost != null && mLayerHost === layerHost) {
            val videoView = layerHost.videoView()
            unbindVideoView(videoView)
            layerHost.removeVideoLayerHostListener(this)
            VideoLogUtil.d("onUnbindLayerHost $layerHost")
            mLayerHost = null
            onUnbindLayerHost(layerHost)
        }
    }

    fun bindVideoView(videoView: VideoView?) {
        if (videoView != null) {
            videoView.addVideoViewListener(this)
            onBindVideoView(videoView)
            val controller: PlaybackController? = videoView.controller()
            if (controller != null) {
                bindController(controller)
            }
        }
    }

    fun unbindVideoView(videoView: VideoView?) {
        if (videoView != null) {
            val controller: PlaybackController? = videoView.controller()
            if (controller != null) {
                unbindController(videoView.controller())
            }
            videoView.removeVideoViewListener(this)
            onUnBindVideoView(videoView)
        }
    }

    fun bindController(controller: PlaybackController?) {
        if (controller != null) {
            onBindPlaybackController(controller)
        }
    }

    fun unbindController(controller: PlaybackController?) {
        if (controller != null) {
            onUnbindPlaybackController(controller)
        }
    }

    override fun onLayerHostAttachedToVideoView(videoView: VideoView) {
        bindVideoView(videoView)
    }

    override fun onLayerHostDetachedFromVideoView(videoView: VideoView) {
        unbindVideoView(videoView)
    }

    override fun onVideoViewBindController(controller: PlaybackController?) {
        bindController(controller)
    }

    override fun onVideoViewUnbindController(controller: PlaybackController?) {
        unbindController(controller)
    }

    abstract fun tag(): String
    fun <V : View> getView(): V {
        return mLayerView as V
    }

    fun context(): Context? {
        if (mLayerView != null) return mLayerView!!.context
        return if (mLayerHost != null) mLayerHost?.hostView()?.context else null
    }

    fun activity(): FragmentActivity? {
        val context = context()
        return if (context is FragmentActivity) {
            context
        } else null
    }

    @CallSuper
    open fun show() {
        if (isShowing()) return
        val layerHost: VideoLayerHost = mLayerHost ?: return
        val layerView = createView(layerHost)
        layerHost.addLayerView(this)
        if (layerView != null && layerView.visibility != View.VISIBLE) {
            layerView.visibility = View.VISIBLE
        }
    }

    private fun createView(layerHost: VideoLayerHost): View? {
        if (mLayerView == null) {
            val hostView: ViewGroup? = layerHost.hostView()
            val start = System.currentTimeMillis()
            mLayerView = createView(hostView!!)
            VideoLogUtil.d("createView $mLayerView time = ${System.currentTimeMillis() - start}")
        }
        return mLayerView
    }

    fun isShowing(): Boolean {
        return mLayerView != null && mLayerView!!.visibility == View.VISIBLE && mLayerHost != null && (mLayerHost?.indexOfLayerView(
            this
        ) ?: 0) >= 0
    }


    open fun onViewAddedToHostView(hostView: ViewGroup?) {}

    open fun onViewRemovedFromHostView(hostView: ViewGroup?) {}

    open fun onBindLayerHost(layerHost: VideoLayerHost) {}

    open fun onUnbindLayerHost(layerHost: VideoLayerHost) {}

    open fun onBindVideoView(videoView: VideoView) {}

    open fun onUnBindVideoView(videoView: VideoView) {}

    open fun onBindPlaybackController(controller: PlaybackController) {}

    open fun onUnbindPlaybackController(controller: PlaybackController) {}

    fun requestShow(reason: String) {
        show()
    }

    fun requestDismiss(reason: String) {
        dismiss()
    }

    fun requestHide(reason: String) {
        hide()
    }

    @CallSuper
    fun dismiss() {
        if (!isShowing()) return
        val layerHost = mLayerHost ?: return
        layerHost.removeLayerView(this)
    }

    @CallSuper
    fun hide() {
        if (!isShowing()) return
        if (mLayerView != null && mLayerView!!.visibility != View.GONE) {
            mLayerView!!.visibility = View.GONE
        }
    }
}