package com.kpa.library.page.model

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
data class VideoPageItem(
    val title: String,
    val subtitle: String,
    val videoUrl: String,
    val coverImageUrl: String,
    val videoAspectRatio: String,
    val videoId: String
) {
    companion object {
        fun create(
            title: String,
            subtitle: String,
            videoUrl: String,
            coverImageUrl: String,
            videoAspectRatio: String,
            videoId: String
        ): VideoPageItem {
            return VideoPageItem(
                title = title,
                subtitle = subtitle,
                videoUrl = videoUrl,
                coverImageUrl = coverImageUrl,
                videoAspectRatio = videoAspectRatio,
                videoId = videoId
            )
        }
    }
}
