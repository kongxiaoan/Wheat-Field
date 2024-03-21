package com.kpa.library.core

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:管理层视图的添加/分离状态
 */
class VideoLayerHost(context: Context) {
    private val mLayers: CopyOnWriteArrayList<VideoLayer> = CopyOnWriteArrayList()
    private val mListeners: CopyOnWriteArrayList<VideoLayerHostListener> = CopyOnWriteArrayList()
    private var mHostView: FrameLayout = FrameLayout(context)
    private var mVideoView: VideoView? = null

    fun addLayer(layer: VideoLayer) {
        if (!mLayers.contains(layer)) {
            mLayers.add(layer)
            layer.bindLayerHost(this)
        }
    }

    fun hostView() = mHostView

    fun attachToVideoView(videoView: VideoView) {
        if (mVideoView == null) {
            mVideoView = videoView
            mVideoView?.bindLayerHost(this)
            var lp = mHostView.layoutParams as? FrameLayout.LayoutParams
            if (lp == null) {
                lp = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                lp.gravity = Gravity.CENTER
            }
            videoView.addView(mHostView, lp)
            mListeners.forEach {
                it.onLayerHostAttachedToVideoView(videoView)
            }
        }
    }

    fun addVideoLayerHostListener(listener: VideoLayerHostListener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener)
        }
    }

    fun removeVideoLayerHostListener(listener: VideoLayerHostListener) {
        mListeners.remove(listener)
    }

    fun addLayerView(videoLayer: VideoLayer) {
        val hostView: ViewGroup = mHostView
        val layerView: View = videoLayer.getView() ?: return
        if (layerView.parent == null) {
            val layerIndex = indexOfLayer(videoLayer)
            val layerViewIndex = calViewIndex(hostView, layerIndex)
            hostView.addView(layerView, layerViewIndex)
            videoLayer.onViewAddedToHostView(hostView)
        }
    }

    private fun indexOfLayer(videoLayer: VideoLayer): Int {
        return mLayers.indexOf(videoLayer)
    }

    /**
     * 计算将新的视频图层插入到宿主视图中的合适位置的索引。
     *
     * @param hostView 宿主视图，即要将新的视频图层插入到其中的视图组。
     * @param layerIndex 要插入的视频图层的索引位置。
     * @return 返回新的视频图层应该插入的位置索引。
     */
    private fun calViewIndex(hostView: ViewGroup, layerIndex: Int): Int {
        // 计算前一个图层的索引
        val preLayerIndex = layerIndex - 1
        // 初始化前一个图层在宿主视图中的位置索引
        var preLayerViewIndex = -1
        // 从前一个图层的索引开始向前遍历
        for (i in preLayerIndex downTo 0) {
            // 获取对应索引位置的视频图层实例
            val layer: VideoLayer = findLayer(i)
            // 获取视频图层的视图
            val layerView: View? = layer.getView()
            // 如果视频图层的视图不为空
            if (layerView != null) {
                // 获取视频图层在宿主视图中的位置索引
                val viewIndex = hostView.indexOfChild(layerView)
                // 如果视频图层在宿主视图中存在
                if (viewIndex >= 0) {
                    // 将视频图层在宿主视图中的位置索引赋值给前一个图层在宿主视图中的位置索引
                    preLayerViewIndex = viewIndex
                    // 终止循环
                    break
                }
            }
        }
        // 返回新的视频图层应该插入的位置索引
        return if (preLayerViewIndex < 0) 0 else preLayerViewIndex + 1
    }


    fun findLayer(index: Int): VideoLayer {
        return mLayers[index]
    }

    fun videoView(): VideoView? {
        return mVideoView
    }

    interface VideoLayerHostListener {
        /**
         * Callbacks when [VideoLayerHost] instance is attached to VideoView.
         *
         * @param videoView [VideoView] attached into
         * @see VideoLayerHost.attachToVideoView
         */
        fun onLayerHostAttachedToVideoView(videoView: VideoView)

        /**
         * Callbacks when [VideoLayerHost] instance is detached from a VideoView.
         *
         * @param videoView [VideoView] detached from
         * @see VideoLayerHost.detachFromVideoView
         */
        fun onLayerHostDetachedFromVideoView(videoView: VideoView)
    }

}