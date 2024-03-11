package com.kpa.video.uikit.video.layer

import android.R
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kpa.video.playerkit.base.VideoLayer
import com.kpa.video.playerkit.base.VideoView
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
class ConvertLayer : VideoLayer() {
    override fun createView(parent: ViewGroup): View {
        val imageView = ImageView(parent.context)
        imageView.setScaleType(ImageView.ScaleType.FIT_XY)
        imageView.setBackgroundColor(parent.resources.getColor(R.color.black))
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        lp.gravity = Gravity.CENTER
        imageView.setLayoutParams(lp)
        return imageView
    }

    override fun onVideoViewBindDataSource(dataSource: MediaSource?) {
        show()
    }

    override fun show() {
        super.show()
        load()
    }

    private fun load() {
        val imageView: ImageView = getView()
        val coverUrl: String? = resolveCoverUrl()
        val activity: Activity? = activity()
        if (activity != null && !activity.isDestroyed) {
            Glide.with(imageView).load(coverUrl).into(imageView)
        }
    }

    fun resolveCoverUrl(): String? {
        val videoView: VideoView = videoView() ?: return null
        val mediaSource = videoView.getDataSource() ?: return null
        return mediaSource.coverUrl
    }

    override fun tag(): String {
        return ConvertLayer::javaClass.name
    }

    override fun onBindVideoView(videoView: VideoView) {
        super.onBindVideoView(videoView)
        show()
    }

}