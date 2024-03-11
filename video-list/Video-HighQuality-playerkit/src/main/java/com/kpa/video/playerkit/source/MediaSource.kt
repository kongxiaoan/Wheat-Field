package com.kpa.video.playerkit.source

import com.kpa.video.common.utils.ExtraObject
import java.io.Serializable

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class MediaSource : ExtraObject, Serializable {
    // 1. 资源类型（url）
    // 2. 媒体协议
    // 3. 媒体类型
    var sourceUrl: String? = null
    var mediaId: String? = null

    var coverUrl: String? = null

    var duration: Int = 0

    var title: String? = null

    constructor(sourceUrl: String?) : super() {
        this.sourceUrl = sourceUrl
    }

    companion object {
        fun createUrlSource(mediaId: String, url: String, title: String): MediaSource {
            var mediaSource = MediaSource(url)
            mediaSource.title = title
            mediaSource.mediaId = mediaId
            mediaSource.coverUrl = url
            return mediaSource
        }
    }


}