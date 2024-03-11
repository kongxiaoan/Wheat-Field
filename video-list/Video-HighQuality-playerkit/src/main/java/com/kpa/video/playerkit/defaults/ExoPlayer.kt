package com.kpa.video.playerkit.defaults

import android.content.Context
import android.os.Looper
import android.view.Surface
import com.kpa.player.event.Dispatcher
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.base.PlayerException
import com.kpa.video.playerkit.base.adapter.PlayerAdapter
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class ExoPlayer : Player {

    constructor(context: Context, playerFactory: PlayerAdapter.Factory, eventLooper: Looper)

    override fun addPlayerListener(listener: Dispatcher.EventListener) {
        TODO("Not yet implemented")
    }

    override fun removePlayerListener(listener: Dispatcher.EventListener) {
        TODO("Not yet implemented")
    }

    override fun removeAllPlayerListener() {
        TODO("Not yet implemented")
    }

    override fun setSurface(surface: Surface) {
        TODO("Not yet implemented")
    }

    override fun getSurface(): Surface {
        TODO("Not yet implemented")
    }

    override fun setVideoScalingMode(scalingMode: Int) {
        TODO("Not yet implemented")
    }

    override fun getVideoScalingMode(): Int {
        TODO("Not yet implemented")
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        TODO("Not yet implemented")
    }

    override fun getVolume(): FloatArray {
        TODO("Not yet implemented")
    }

    override fun setMuted(muted: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isMuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun prepare(source: MediaSource) {
        TODO("Not yet implemented")
    }

    override fun getDataSource(): MediaSource? {
        TODO("Not yet implemented")
    }

    override fun setStartWhenPrepared(startWhenPrepared: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isStartWhenPrepared(): Boolean {
        TODO("Not yet implemented")
    }

    override fun seekTo(seekTo: Long) {
        TODO("Not yet implemented")
    }

    override fun start() {
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

    override fun getVideoSampleAspectRatio(): Float {
        TODO("Not yet implemented")
    }

    override fun setLooping(looping: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isLooping(): Boolean {
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

    override fun getVideoDecoderType(): Int {
        TODO("Not yet implemented")
    }

    override fun getVideoCodecId(): Int {
        TODO("Not yet implemented")
    }

    override fun isBuffering(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getState(): Int {
        TODO("Not yet implemented")
    }

    override fun isIDLE(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPreparing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPrepared(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPaused(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCompleted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isStopped(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isReleased(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isError(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInPlaybackState(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlayerException(): PlayerException {
        TODO("Not yet implemented")
    }

    override fun dump(): String? {
        TODO("Not yet implemented")
    }
}