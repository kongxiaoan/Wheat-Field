package com.kpa.video.playerkit.base

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.kpa.video.common.log.VideoLogUtil
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
class VideoLayerHost(context: Context) {
    private var mVideoView: VideoView? = null
    private var mHostView: FrameLayout? = null
    private var mListeners: CopyOnWriteArrayList<VideoLayerHostListener> = CopyOnWriteArrayList()
    private val mLayers: CopyOnWriteArrayList<VideoLayer> = CopyOnWriteArrayList()

    interface VideoLayerHostListener {
        /**
         * Callbacks when [VideoLayerHost] instance is attached to VideoView.
         *
         */
        fun onLayerHostAttachedToVideoView(videoView: VideoView)

        /**
         * Callbacks when [VideoLayerHost] instance is detached from a VideoView.
         *
         */
        fun onLayerHostDetachedFromVideoView(videoView: VideoView)
    }

    init {
        mHostView = FrameLayout(context)
    }

    fun hostView(): FrameLayout? {
        return mHostView
    }

    fun videoView(): VideoView? {
        return mVideoView
    }

    fun addLayer(layer: VideoLayer) {
        if (!mLayers.contains(layer)) {
            mLayers.add(layer)
            layer.bindLayerHost(this)
        }
    }

    fun addLayerView(layer: VideoLayer) {
        val hostView: ViewGroup = mHostView!!
        val layerView: View = layer.getView()
        if (layerView.parent == null) {
            val layerIndex: Int = indexOfLayer(layer)
            val layerViewIndex: Int = calViewIndex(hostView, layerIndex)
            VideoLogUtil.d(
                "addLayerView layer = $layer hostView = $hostView layoutIndex = $layerIndex viewIndex = $layerViewIndex"
            )
            hostView.addView(layerView, layerViewIndex)
            layer.onViewAddedToHostView(hostView)
        }
    }

    fun indexOfLayer(layer: VideoLayer?): Int {
        return if (layer != null) {
            mLayers.indexOf(layer)
        } else -1
    }

    private fun calViewIndex(hostView: ViewGroup, layerIndex: Int): Int {
        val preLayerIndex = layerIndex - 1
        var preLayerViewIndex = -1
        for (i in preLayerIndex downTo 0) {
            val layer: VideoLayer = findLayer(i)
            val layerView: View = layer.getView()
            if (layerView != null) {
                val viewIndex = hostView.indexOfChild(layerView)
                if (viewIndex >= 0) {
                    preLayerViewIndex = viewIndex
                    break
                }
            }
        }
        return if (preLayerViewIndex < 0) 0 else preLayerViewIndex + 1
    }


    fun findLayer(index: Int): VideoLayer {
        return mLayers[index]
    }


    fun indexOfLayerView(layer: VideoLayer): Int {
        val hostView: ViewGroup? = mHostView
        val layerView: View = layer.getView()
        return hostView?.indexOfChild(layerView) ?: -1
    }


    fun addVideoLayerHostListener(listener: VideoLayerHostListener) {
        if (!mListeners.contains(listener)) {
            mListeners.add(listener)
        }
    }

    fun removeVideoLayerHostListener(listener: VideoLayerHostListener) {
        mListeners.remove(listener)
    }

    fun attachToVideoView(videoView: VideoView) {
        if (mVideoView == null) {
            mVideoView = videoView
            mVideoView?.run {
                bindLayerHost(this@VideoLayerHost)
                var lp: FrameLayout.LayoutParams? =
                    if (mHostView?.layoutParams == null) null else mHostView?.layoutParams as FrameLayout.LayoutParams
                if (lp == null) {
                    lp = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    lp.gravity = Gravity.CENTER
                }
                videoView.addView(mHostView, lp)
                VideoLogUtil.d("onLayerHostAttachedToVideoView = $videoView")
                for (listener in mListeners) {
                    listener.onLayerHostAttachedToVideoView(videoView)
                }
            }

        }
    }

    fun detachFromVideoView() {
        if (mVideoView != null) {
            mVideoView?.run {
                unbindLayerHost(this@VideoLayerHost)
                removeView(mHostView)
                val toDetach: VideoView = this
                mVideoView = null
                VideoLogUtil.d("onLayerHostDetachedFromVideoView = $toDetach")
                for (listener in mListeners) {
                    listener.onLayerHostDetachedFromVideoView(toDetach)
                }
            }
        }
    }

    fun removeLayerView(layer: VideoLayer) {
        val hostView: ViewGroup = mHostView!!
        val layerView: View = layer.getView() ?: return
        val layerIndex = mLayers.indexOf(layer)
        val layerViewIndex = hostView.indexOfChild(layerView)
        if (layerViewIndex >= 0) {
            hostView.removeView(layerView)
            layer.onViewRemovedFromHostView(hostView)
        }
    }

}