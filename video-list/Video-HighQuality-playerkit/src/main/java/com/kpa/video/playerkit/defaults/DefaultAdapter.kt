package com.kpa.video.playerkit.defaults

import android.os.Looper
import android.view.Surface
import android.view.SurfaceHolder
import com.kpa.video.playerkit.base.adapter.PlayerAdapter
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class DefaultAdapter : PlayerAdapter {
    override fun setListener(listener: PlayerAdapter.Listener?) {
        TODO("Not yet implemented")
    }

    override fun setSurface(surface: Surface?) {
        TODO("Not yet implemented")
    }

    override fun setDisplay(display: SurfaceHolder?) {
        TODO("Not yet implemented")
    }

    override fun setVideoScalingMode(mode: Int) {
        TODO("Not yet implemented")
    }

    override fun setDataSource(source: MediaSource) {
        TODO("Not yet implemented")
    }

    override fun getDataSource(): MediaSource? {
        TODO("Not yet implemented")
    }

    override fun setStartTime(startTime: Long) {
        TODO("Not yet implemented")
    }

    override fun setStartWhenPrepared(startWhenPrepared: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isStartWhenPrepared(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getStartTime(): Long {
        TODO("Not yet implemented")
    }

    override fun prepareAsync() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    override fun seekTo(seekTo: Long) {
        TODO("Not yet implemented")
    }

    override fun getDuration(): Long {
        TODO("Not yet implemented")
    }

    override fun getCurrentPosition(): Long {
        TODO("Not yet implemented")
    }

    override fun getBufferedPercentage(): Int {
        TODO("Not yet implemented")
    }

    override fun getBufferedDuration(): Long {
        TODO("Not yet implemented")
    }

    override fun getVideoWidth(): Int {
        TODO("Not yet implemented")
    }

    override fun getVideoHeight(): Int {
        TODO("Not yet implemented")
    }

    override fun setLooping(looping: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isLooping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        TODO("Not yet implemented")
    }

    override fun getVolume(): FloatArray? {
        TODO("Not yet implemented")
    }

    override fun setMuted(muted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isMuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSpeed(speed: Float) {
        TODO("Not yet implemented")
    }

    override fun getSpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun setAudioPitch(audioPitch: Float) {
        TODO("Not yet implemented")
    }

    override fun getAudioPitch(): Float {
        TODO("Not yet implemented")
    }

    override fun setAudioSessionId(audioSessionId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAudioSessionId(): Int {
        TODO("Not yet implemented")
    }

    override fun setSuperResolutionEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSuperResolutionEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSubtitleEnabled(enabled: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSubtitleEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dump(): String? {
        TODO("Not yet implemented")
    }

    class Factory : PlayerAdapter.Factory {
        override fun create(eventLooper: Looper?): PlayerAdapter? {
            TODO("Not yet implemented")
        }

    }
}