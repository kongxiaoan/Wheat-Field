package com.kpa.library.defaults

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kpa.library.R
import com.kpa.library.core.VideoLayer
import com.kpa.library.core.VideoView

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class VideoInfoLayer : VideoLayer() {
    override fun createView(parent: ViewGroup): View {
        return TextView(parent.context).apply {
            textSize = 30F
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(parent.context, R.color.default_bg))
        }
    }

    override fun onBindVideoView(videoView: VideoView) {
        super.onBindVideoView(videoView)
        show()
    }

    override fun show() {
        super.show()
        render()
    }

    private fun render() {
        val videoView = videoView() ?: return

    }
}