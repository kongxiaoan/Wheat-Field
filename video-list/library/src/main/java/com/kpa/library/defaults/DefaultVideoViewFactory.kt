package com.kpa.library.defaults

import android.content.Context
import androidx.core.content.ContextCompat
import com.kpa.library.R
import com.kpa.library.base.VideoViewFactory
import com.kpa.library.core.VideoLayerHost
import com.kpa.library.core.VideoView

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class DefaultVideoViewFactory : VideoViewFactory {

    override fun createVideoView(context: Context): VideoView {
        val videoView = VideoView(context)
        val layerHost = VideoLayerHost(context)
        val videoInfoLayer = VideoInfoLayer()
        layerHost.addLayer(videoInfoLayer)
        layerHost.attachToVideoView(videoView)
        videoView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        videoView.setDisplayView()
        return videoView
    }
}