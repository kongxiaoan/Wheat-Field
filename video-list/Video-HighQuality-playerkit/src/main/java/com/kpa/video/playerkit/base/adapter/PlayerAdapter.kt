package com.kpa.video.playerkit.base.adapter

import android.media.MediaPlayer
import android.os.Looper
import android.view.Surface
import android.view.SurfaceHolder
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.base.PlayerException
import com.kpa.video.playerkit.source.MediaSource
import java.io.Closeable
import java.io.IOException

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
interface PlayerAdapter {

    object Info {
        /* Do not change these values without updating their counterparts
         * in include/media/mediaplayer.h!
         */
        /**
         * Unspecified media player info.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_UNKNOWN = MediaPlayer.MEDIA_INFO_UNKNOWN

        /**
         * The player just pushed the very first video frame for rendering.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_VIDEO_RENDERING_START = MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START

        /**
         * The player just pushed the very first audio frame for rendering.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_AUDIO_RENDERING_START = 4
        const val MEDIA_INFO_VIDEO_RENDERING_START_BEFORE_START = 5

        /**
         * MediaPlayer is temporarily pausing playback internally in order to
         * buffer more data.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_BUFFERING_START = MediaPlayer.MEDIA_INFO_BUFFERING_START

        /**
         * MediaPlayer is resuming playback after filling buffers.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_BUFFERING_END = MediaPlayer.MEDIA_INFO_BUFFERING_END

        /**
         * Estimated network bandwidth information (kbps) is available; currently this event fires
         * simultaneously as [.MEDIA_INFO_BUFFERING_START] and [.MEDIA_INFO_BUFFERING_END]
         * when playing network files.
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_NETWORK_BANDWIDTH = 703

        /**
         * The media cannot be seeked (e.g live stream)
         *
         * @see Listener.onInfo
         */
        const val MEDIA_INFO_NOT_SEEKABLE = MediaPlayer.MEDIA_INFO_NOT_SEEKABLE
    }


    object MediaSourceUpdateReason {
        const val MEDIA_SOURCE_UPDATE_REASON_PLAY_INFO_FETCHED = 1
        const val MEDIA_SOURCE_UPDATE_REASON_SUBTITLE_INFO_FETCHED = 2
        const val MEDIA_SOURCE_UPDATE_REASON_MASK_INFO_FETCHED = 3
    }


    interface Factory {
        fun create(eventLooper: Looper?): PlayerAdapter?
    }


    /**
     * Same with [android.media.MediaDataSource] with no api limit.
     */
    abstract class MediaDataSource : Closeable {
        @Throws(IOException::class)
        abstract fun readAt(position: Long, buffer: ByteArray?, offset: Int, size: Int): Int

        @get:Throws(IOException::class)
        abstract val size: Long
    }


    fun setListener(listener: Listener?)

    fun setSurface(surface: Surface?)

    fun setDisplay(display: SurfaceHolder?)

    fun setVideoScalingMode(@Player.Companion.ScalingMode mode: Int)

    @Throws(IOException::class)
    fun setDataSource(source: MediaSource)

    fun getDataSource(): MediaSource?

    fun setStartTime(startTime: Long)

    fun setStartWhenPrepared(startWhenPrepared: Boolean)

    fun isStartWhenPrepared(): Boolean

    fun getStartTime(): Long

    @Throws(IllegalStateException::class)
    fun prepareAsync()

    @Throws(IllegalStateException::class)
    fun start()

    fun isPlaying(): Boolean

    @Throws(IllegalStateException::class)
    fun pause()

    @Throws(IllegalStateException::class)
    fun stop()

    fun reset()

    fun release()

    fun seekTo(seekTo: Long)

    fun getDuration(): Long

    fun getCurrentPosition(): Long

    fun getBufferedPercentage(): Int

    fun getBufferedDuration(): Long

    fun getVideoWidth(): Int

    fun getVideoHeight(): Int

    fun setLooping(looping: Boolean)

    fun isLooping(): Boolean

    fun setVolume(leftVolume: Float, rightVolume: Float)

    fun getVolume(): FloatArray?

    fun setMuted(muted: Boolean)

    fun isMuted(): Boolean

    fun setSpeed(speed: Float)

    fun getSpeed(): Float

    fun setAudioPitch(audioPitch: Float)

    fun getAudioPitch(): Float

    fun setAudioSessionId(audioSessionId: Int)

    fun getAudioSessionId(): Int

    fun setSuperResolutionEnabled(enabled: Boolean)

    fun isSuperResolutionEnabled(): Boolean

    fun setSubtitleEnabled(enabled: Boolean)

    fun isSubtitleEnabled(): Boolean


    fun dump(): String?


    interface Listener : PlayerListener, MediaSourceListener, SubtitleListener,
        FrameInfoListener


    interface PlayerListener {
        fun onPrepared(mp: PlayerAdapter)
        fun onCompletion(mp: PlayerAdapter)
        fun onError(mp: PlayerAdapter, code: Int, msg: String)
        fun onSeekComplete(mp: PlayerAdapter)
        fun onVideoSizeChanged(mp: PlayerAdapter, width: Int, height: Int)
        fun onSARChanged(mp: PlayerAdapter, num: Int, den: Int)
        fun onBufferingUpdate(mp: PlayerAdapter, percent: Int)
        fun onProgressUpdate(mp: PlayerAdapter, position: Long)
        fun onInfo(mp: PlayerAdapter, what: Int, extra: Any?)
        fun onCacheHint(mp: PlayerAdapter?, cacheSize: Long)
    }


    interface FrameInfoListener {
        fun onFrameInfoUpdate(
            mp: PlayerAdapter?,
            @Player.Companion.FrameType frameType: Int,
            pts: Long,
            clockTime: Long
        )
    }


    interface MediaSourceListener {
        fun onMediaSourceUpdateStart(mp: PlayerAdapter?, type: Int, source: MediaSource?)
        fun onMediaSourceUpdated(mp: PlayerAdapter?, type: Int, source: MediaSource?)
        fun onMediaSourceUpdateError(mp: PlayerAdapter?, type: Int, e: PlayerException?)
    }



    interface SubtitleListener {
        fun onSubtitleStateChanged(mp: PlayerAdapter, enabled: Boolean)
        fun onSubtitleInfoReady(mp: PlayerAdapter, subtitles: List<String?>?)
        fun onSubtitleFileLoadFinish(mp: PlayerAdapter, success: Int, info: String?)
        fun onSubtitleWillChange(mp: PlayerAdapter, current: String?, target: String?)
        fun onSubtitleChanged(mp: PlayerAdapter, pre: String?, current: String?)
        fun onSubtitleTextUpdate(mp: PlayerAdapter, subtitleText: String)
    }

}