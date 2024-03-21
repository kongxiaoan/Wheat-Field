package com.kpa.library.base

import android.content.Context
import com.kpa.library.core.VideoView
import com.kpa.library.defaults.DefaultVideoViewFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
interface VideoViewFactory {
    companion object {
        internal var DEFAULT: VideoViewFactory = DefaultVideoViewFactory()
        fun setVideoViewFactory(factory: VideoViewFactory) {
            DEFAULT = factory
        }
    }

    abstract fun createVideoView(context: Context): VideoView
}