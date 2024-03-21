package com.kpa.library.player.exo

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.util.Log
import android.view.Surface
import androidx.annotation.OptIn
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import com.kpa.library.player.controller.PlayerController
import com.kpa.library.player.core.IPlayer

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */
@OptIn(UnstableApi::class)
class ExoPlayer(val context: Context) : IPlayer(), Player.Listener {
    private val mInternalPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }
    private var mSpeedPlaybackParameters: PlaybackParameters? = null
    private var mIsPreparing = false
    private var mMediaSource: MediaSource? = null


    init {
        initPlayer()
    }


    override fun initPlayer() {
        setOptions()
        mInternalPlayer.addListener(this)
        super.initPlayer()
    }

    override fun setDataSource(path: String, headers: Map<String, String>) {
        mMediaSource = ExoMediaSourceHelper.getMediaSource(path)
    }

    override fun setDataSource(fd: AssetFileDescriptor) {
        TODO("Not yet implemented")
    }

    override fun start() {
        mInternalPlayer.playWhenReady = true
        setState(STATE_STARTED)
    }

    override fun pause() {
        mInternalPlayer.playWhenReady = false
        setState(STATE_PAUSED)
    }

    override fun stop() {
        mInternalPlayer.stop()
        setState(STATE_STOPPED)
    }

    override fun prepareAsync() {
        if (mMediaSource == null) return
        if (mSpeedPlaybackParameters != null) {
            mInternalPlayer.playbackParameters = mSpeedPlaybackParameters!!
        }
        mIsPreparing = true
        mInternalPlayer.setMediaSource(mMediaSource!!, true)
        mInternalPlayer.prepare()
    }

    override fun reset() {
        mInternalPlayer.stop()
        mInternalPlayer.clearMediaItems()
        mInternalPlayer.setVideoSurface(null)
        mIsPreparing = false
        setState(STATE_IDLE)
    }

    override fun isPlaying(): Boolean {
        val state = mInternalPlayer.playbackState
        return when (state) {
            Player.STATE_BUFFERING, Player.STATE_READY -> mInternalPlayer.playWhenReady
            Player.STATE_IDLE, Player.STATE_ENDED -> false
            else -> false
        }
    }

    override fun seekTo(time: Long) {
        mInternalPlayer.seekTo(time)
    }

    override fun release() {
        mInternalPlayer.removeListener(this)
        mInternalPlayer.release()
        mIsPreparing = false
        mSpeedPlaybackParameters = null
        setState(STATE_RELEASED)
    }

    override fun getCurrentPosition(): Long {
        return mInternalPlayer.currentPosition
    }

    override fun getDuration(): Long {
        return mInternalPlayer.duration
    }

    override fun getBufferedPercentage(): Int {
        return mInternalPlayer.bufferedPercentage
    }

    override fun setSurface(surface: Surface?) {
        mInternalPlayer.setVideoSurface(surface)
    }

    override fun setVolume(v1: Float, v2: Float) {
        mInternalPlayer.volume = (v1 + v2) / 2
    }

    override fun setLooping(isLooping: Boolean) {
        mInternalPlayer.repeatMode =
            if (isLooping) Player.REPEAT_MODE_ALL else Player.REPEAT_MODE_OFF
    }

    override fun setOptions() {
        mInternalPlayer.playWhenReady = true
    }

    override fun setSpeed(speed: Float) {
        val playbackParameters = PlaybackParameters(speed)
        mSpeedPlaybackParameters = playbackParameters
        mInternalPlayer.playbackParameters = playbackParameters
    }

    override fun getSpeed(): Float {
        return mSpeedPlaybackParameters?.speed ?: 1.0f
    }

    override fun getTcpSpeed(): Long {
        return 0
    }

    override fun setStartWhenPrepared(startWhenPrepared: Boolean) {
        mInternalPlayer.playWhenReady = startWhenPrepared
    }

    /**
     * 播放器状态更改
     * Player.STATE_IDLE：这是初始状态、播放器停止时以及播放失败时的状态。在此状态下，播放器将仅保留有限的资源。
     * Player.STATE_BUFFERING：播放器无法立即从当前位置开始播放。这主要是因为需要加载更多数据。
     * Player.STATE_READY：播放器能够从当前位置立即播放。
     * Player.STATE_ENDED：播放器完整播放了所有媒体。
     */
    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        Log.d(PlayerController.TAG, "onPlaybackStateChanged playbackState = $playbackState ")
        if (mIsPreparing) {
            if (playbackState == Player.STATE_READY) {
                setState(STATE_PREPARED)
                mPlayerEventListener?.onPrepared()
                mPlayerEventListener?.onInfo(MEDIA_INFO_RENDERING_START, 0)
                mIsPreparing = false
            }
            return
        }
        when (playbackState) {
            Player.STATE_ENDED -> {
                setState(STATE_COMPLETED)
                mPlayerEventListener?.onCompletion()
            }

            Player.STATE_READY -> {
                setState(STATE_PREPARED)
                mPlayerEventListener?.onInfo(MEDIA_INFO_BUFFERING_END, getBufferedPercentage())
            }

            Player.STATE_BUFFERING -> {
                setState(STATE_PREPARING)
                mPlayerEventListener?.onInfo(MEDIA_INFO_BUFFERING_START, getBufferedPercentage())
            }

            Player.STATE_IDLE -> {

            }
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Log.d(PlayerController.TAG, "onPlayerError error = ${error.message} ")
        setState(STATE_ERROR)
        mPlayerEventListener?.onError(error)
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        super.onVideoSizeChanged(videoSize)
        Log.d(PlayerController.TAG, "onVideoSizeChanged ------ ${videoSize.width}")
        mPlayerEventListener?.onVideoSizeChanged(videoSize.width, videoSize.height)
    }
}