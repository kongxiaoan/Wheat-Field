package com.kpa.library.player.exo

import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.rtmp.RtmpDataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.rtsp.RtspMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.kpa.library.player.controller.PlayerController
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */
@OptIn(UnstableApi::class)
object ExoMediaSourceHelper {
    fun getMediaSource(
        uri: String
    ): MediaSource {
        val contentUri = Uri.parse(uri)
        if ("rtmp" == contentUri.scheme) {
            return ProgressiveMediaSource.Factory(RtmpDataSource.Factory())
                .createMediaSource(MediaItem.fromUri(contentUri))
        } else if ("rtsp" == contentUri.scheme) {
            return RtspMediaSource.Factory().createMediaSource(MediaItem.fromUri(contentUri))
        }
        val contentType: Int = inferContentType(uri)
        val factory: DataSource.Factory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
        Log.d(PlayerController.TAG, "contentType = $contentType")
        return when (contentType) {
            C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(factory)
                .createMediaSource(MediaItem.fromUri(contentUri))

            C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(factory)
                .createMediaSource(MediaItem.fromUri(contentUri))

            C.CONTENT_TYPE_OTHER -> ProgressiveMediaSource.Factory(factory)
                .createMediaSource(MediaItem.fromUri(contentUri))

            else -> ProgressiveMediaSource.Factory(factory)
                .createMediaSource(MediaItem.fromUri(contentUri))
        }
    }

    private fun inferContentType(fileName: String): Int {
        var fileName = fileName
        fileName = fileName.lowercase(Locale.getDefault())
        return if (fileName.contains(".mpd")) {
            C.CONTENT_TYPE_DASH
        } else if (fileName.contains(".m3u8")) {
            C.CONTENT_TYPE_HLS
        } else {
            C.CONTENT_TYPE_OTHER
        }
    }
}