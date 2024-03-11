package com.kpa.video.uikit.model

import com.kpa.video.playerkit.source.MediaSource
import java.io.Serializable

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
class VideoItem : Serializable {
    var videoUrl: String = ""
    var convertUrl: String = ""
    var title: String = ""
    var mediaSource: MediaSource? = null

    companion object {
        fun createVideoModelItem(
            videoUrl: String = "https://media.w3.org/2010/05/sintel/trailer.mp4",
            title: String = "测试标题  ${(0..100).random()}",
            convertUrl: String = "https://img.iplaysoft.com/wp-content/uploads/2019/free-images/free_stock_photo.jpg"
        ): VideoItem {
            val videoItem = VideoItem()
            videoItem.videoUrl = videoUrl
            videoItem.convertUrl = convertUrl
            videoItem.title = title
            return videoItem
        }

        fun toMediaSource(
            videoItem: VideoItem
        ): MediaSource? {
            if (videoItem.mediaSource == null) {
                videoItem.mediaSource = createMediaSource(videoItem)
            }
            val mediaSource = videoItem.mediaSource
            return mediaSource
        }

        private fun createMediaSource(videoItem: VideoItem): MediaSource {
            return MediaSource.createUrlSource("111", videoItem.videoUrl, videoItem.title)
        }
    }
}