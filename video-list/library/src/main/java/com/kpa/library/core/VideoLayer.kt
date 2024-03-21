package com.kpa.library.core

import android.view.View
import android.view.ViewGroup

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:实现单一职责、模块化和可重用的层视图
 */
abstract class VideoLayer : VideoLayerHost.VideoLayerHostListener {
    private var mLayerHost: VideoLayerHost? = null
    private var mLayerView: View? = null

    fun bindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost == null) {
            mLayerHost = layerHost
            layerHost.addVideoLayerHostListener(this)
            onBindLayerHost(layerHost)
            // 如果已经添绑定到有效VideoView 那直接进行处理，因为VideoView 中会有播放器和播放器的数据
            val videoView: VideoView? = layerHost.videoView()
            if (videoView != null) {
                bindVideoView(videoView)
            }
        }
    }

    fun unbindLayerHost(layerHost: VideoLayerHost) {
        if (mLayerHost != null && mLayerHost == layerHost) {
            val videoView = layerHost.videoView()
            unbindVideoView(videoView)
            layerHost.removeVideoLayerHostListener(this)
            mLayerHost = null
            onUnbindLayerHost(layerHost)
        }
    }

    fun videoView(): VideoView? {
        return mLayerHost?.videoView()
    }

    open fun onUnbindLayerHost(layerHost: VideoLayerHost) {
    }

    open fun unbindVideoView(videoView: VideoView?) {

    }

    /**
     * 部分场景是添加到VideoLayerHost 集合后就自身需要添加到VideoLayerHost中的
     * 比如： 1. 手势检测
     *       2. 全屏 等不设计显示隐藏的Layer 可以直接理解为看不见UI的Layer或者必须要显示的Layer
     *
     */
    protected open fun onBindLayerHost(layerHost: VideoLayerHost) {}


    fun <V : View> getView(): V? {
        return mLayerView as V
    }

    open fun show() {
        val layoutHost = mLayerHost ?: return
        val layerView: View? = createView(layoutHost)
        layoutHost.addLayerView(this)
        if (layerView != null && layerView.visibility != View.VISIBLE) {
            layerView.visibility = View.VISIBLE
        }
    }

    private fun createView(layoutHost: VideoLayerHost): View? {
        if (mLayerView == null) {
            val hostView = layoutHost.hostView()
            mLayerView = createView(hostView)
        }
        return mLayerView
    }

    protected abstract fun createView(parent: ViewGroup): View

    override fun onLayerHostAttachedToVideoView(videoView: VideoView) {
        // 管理视图层已经添加到VideoView
        bindVideoView(videoView)
    }

    fun bindVideoView(videoView: VideoView) {
        onBindVideoView(videoView)
    }

    open fun onBindVideoView(videoView: VideoView) {}
    open fun onViewAddedToHostView(hostView: ViewGroup?) {}

    override fun onLayerHostDetachedFromVideoView(videoView: VideoView) {
        TODO("Not yet implemented")
    }
}