package com.kpa.video.uikit.video.layer

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kpa.video.playerkit.PlaybackController
import com.kpa.video.playerkit.base.VideoLayer
import com.kpa.video.playerkit.base.VideoView
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
class SimpleTitleLayer : VideoLayer() {

    private var mSubText: TextView? = null


    override fun createView(parent: ViewGroup): View {
        mSubText = TextView(parent.context)
        mSubText!!.setTextColor(Color.WHITE)
        mSubText?.textSize = 30F
        return mSubText!!
    }

    override fun tag(): String {
        return SimpleTitleLayer::javaClass.name
    }

    override fun onBindVideoView(videoView: VideoView) {
        super.onBindVideoView(videoView)
        show()
    }

    override fun onVideoViewBindDataSource(dataSource: MediaSource?) {
        super.onVideoViewBindDataSource(dataSource)
        mSubText?.text = dataSource?.title
    }
}