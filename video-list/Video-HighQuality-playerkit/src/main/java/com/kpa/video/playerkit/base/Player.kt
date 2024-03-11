package com.kpa.video.playerkit.base

import android.view.Surface
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import com.kpa.player.event.Dispatcher
import com.kpa.video.playerkit.source.MediaSource


/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
interface Player {
    companion object {
        @Retention(AnnotationRetention.SOURCE)
        @IntDef(FRAME_TYPE_UNKNOWN, FRAME_TYPE_VIDEO, FRAME_TYPE_AUDIO)
        annotation class FrameType


        const val FRAME_TYPE_UNKNOWN = 0
        const val FRAME_TYPE_VIDEO = 1
        const val FRAME_TYPE_AUDIO = 2
        /**
         * 表示空闲状态。
         *
         * - 新创建的播放器实例处于空闲状态。
         * - 从任何状态（不包括已释放的状态 [.STATE_RELEASED]）调用 [.reset] 将
         *   把播放器状态转换为空闲状态。
         */
        const val STATE_IDLE = 0

        /**
         * 表示准备中状态。
         *
         * - 从 [.STATE_IDLE] 或 [.STATE_STOPPED] 调用 [.prepare] 将
         *   把播放器状态转换为准备中。
         */
        const val STATE_PREPARING = 1

        /**
         * 表示已准备就绪状态。
         *
         * - 在发出 [StatePrepared] 事件后，播放器状态将为已准备就绪。
         */
        const val STATE_PREPARED = 2

        /**
         * 表示已启动状态。
         *
         * - 从 [.STATE_PREPARED]、[.STATE_PAUSED] 或 [.STATE_COMPLETED]
         *   状态调用 [.start] 将把播放器状态转换为已启动。
         */
        const val STATE_STARTED = 3

        /**
         * 表示已暂停状态。
         *
         * - 从 [.STATE_PREPARED]、[.STATE_STARTED] 或 [.STATE_COMPLETED]
         *   状态调用 [.pause] 将把播放器状态转换为已暂停。
         */
        const val STATE_PAUSED = 4

        /**
         * 表示已完成状态。
         *
         * - 在发出 [StateCompleted] 事件后，播放器状态将为已完成。
         */
        const val STATE_COMPLETED = 5

        /**
         * 表示错误状态。
         *
         * - 在发出 [StateError] 事件后，播放器状态将为错误状态。
         */
        const val STATE_ERROR = 6

        /**
         * 表示已停止状态。
         *
         * - 在调用 [.stop] 后，播放器将处于已停止状态。
         */
        const val STATE_STOPPED = 7

        /**
         * 表示已释放状态。
         *
         * - 在调用 [.release] 后，播放器将处于已释放状态。
         */
        const val STATE_RELEASED = 8

        /**
         * 播放器状态。可能的取值包括：
         *
         * - [STATE_IDLE]
         * - [STATE_PREPARING]
         * - [STATE_PREPARED]
         * - [STATE_STARTED]
         * - [STATE_PAUSED]
         * - [STATE_COMPLETED]
         * - [STATE_ERROR]
         * - [STATE_STOPPED]
         * - [STATE_RELEASED]
         *
         * <p>状态变更事件将通过
         * {@link com.bytedance.playerkit.utils.event.Dispatcher.EventListener}
         * 发送。播放器状态事件可能是以下之一：
         *
         * - {@link com.bytedance.playerkit.player.event.StateIDLE}
         * - [StatePreparing]
         * - [StatePrepared]
         * - [StateStarted]
         * - [StatePaused]
         * - [StateCompleted]
         * - [StateError]
         * - [StateStopped]
         * - [StateReleased]
         */
        @IntDef(
            STATE_IDLE,
            STATE_PREPARING,
            STATE_PREPARED,
            STATE_STARTED,
            STATE_PAUSED,
            STATE_COMPLETED,
            STATE_ERROR,
            STATE_STOPPED,
            STATE_RELEASED
        )
        annotation class PlayerState


        /**
         * 视频缩放模式。可选取值包括
         * [.SCALING_MODE_DEFAULT]，
         * [.SCALING_MODE_ASPECT_FIT]，
         * [.SCALING_MODE_ASPECT_FILL]。
         */
        @Retention(AnnotationRetention.SOURCE)
        @IntDef(SCALING_MODE_DEFAULT, SCALING_MODE_ASPECT_FIT, SCALING_MODE_ASPECT_FILL)
        annotation class ScalingMode

        /**
         * 默认缩放模式。
         */
        const val SCALING_MODE_DEFAULT = 0

        /**
         * 等比例适应缩放模式。
         */
        const val SCALING_MODE_ASPECT_FIT = 1

        /**
         * 等比例填充缩放模式。
         */
        const val SCALING_MODE_ASPECT_FILL = 2


        /**
         * 解码器类型。可选取值包括
         * [.DECODER_TYPE_UNKNOWN]，
         * [.DECODER_TYPE_SOFTWARE]，
         * [.DECODER_TYPE_HARDWARE]。
         */
        @Retention(AnnotationRetention.SOURCE)
        @IntDef(DECODER_TYPE_UNKNOWN, DECODER_TYPE_SOFTWARE, DECODER_TYPE_HARDWARE)
        annotation class DecoderType

        /**
         * 未知解码器类型。
         */
        const val DECODER_TYPE_UNKNOWN = 0

        /**
         * 软件解码器类型。
         */
        const val DECODER_TYPE_SOFTWARE = 1

        /**
         * 硬件解码器类型。
         */
        const val DECODER_TYPE_HARDWARE = 2

        /**
         * 编解码器 ID。可选取值包括
         * [.CODEC_ID_UNKNOWN]，
         * [.CODEC_ID_H264]，
         * [.CODEC_ID_H265]，
         * [.CODEC_ID_H266]。
         */
        @Retention(AnnotationRetention.SOURCE)
        @IntDef(CODEC_ID_UNKNOWN, CODEC_ID_H264, CODEC_ID_H265, CODEC_ID_H266)
        annotation class CodecId

        /**
         * 未知编解码器 ID。
         */
        const val CODEC_ID_UNKNOWN = 0

        /**
         * H.264 编解码器 ID。
         */
        const val CODEC_ID_H264 = 1

        /**
         * H.265 编解码器 ID。
         */
        const val CODEC_ID_H265 = 2

        /**
         * H.266 编解码器 ID。
         */
        const val CODEC_ID_H266 = 3

        /**
         * 轨道变更原因。可选取值包括
         * [.TRACK_CHANGE_REASON_UNKNOWN]，
         * [.TRACK_CHANGE_REASON_USER_SELECT]，
         * [.TRACK_CHANGE_REASON_AUTO_SELECT]，
         * [.TRACK_CHANGE_REASON_DEFAULT_SELECT]。
         */
        @IntDef(
            TRACK_CHANGE_REASON_UNKNOWN,
            TRACK_CHANGE_REASON_USER_SELECT,
            TRACK_CHANGE_REASON_AUTO_SELECT,
            TRACK_CHANGE_REASON_DEFAULT_SELECT
        )
        internal annotation class TrackChangeReason


        /**
         * 未知轨道变更原因。
         */
        const val TRACK_CHANGE_REASON_UNKNOWN = 0

        /**
         * 用户选择导致的轨道变更原因。
         */
        const val TRACK_CHANGE_REASON_USER_SELECT = 1

        /**
         * 自动选择导致的轨道变更原因。
         */
        const val TRACK_CHANGE_REASON_AUTO_SELECT = 2

        /**
         * 默认选择导致的轨道变更原因。
         */
        const val TRACK_CHANGE_REASON_DEFAULT_SELECT = 3


    }

    /**
     * Registers a listener to receive all events from the player.
     *
     * @param listener The listener to add
     * @see .removePlayerListener
     */
    fun addPlayerListener(listener: Dispatcher.EventListener)

    /**
     * Unregister a listener registered through [.addPlayerListener].
     * The listener will no longer receive events.
     *
     * @param listener The listener to unregister
     * @see .addPlayerListener
     */
    fun removePlayerListener(listener: Dispatcher.EventListener)

    /**
     * Unregister all listeners registered through [.addPlayerListener]
     * All the listeners will no longer receive events.
     */
    fun removeAllPlayerListener()

    /**
     * Set the [Surface] to player. Setting a new surface will un-set the surface that was
     * previously set. Clear the surface by calling `setSurface(null)` if the surface is
     * destroyed.
     *
     *
     * If surface changed [ActionSetSurface] will be emitted immediately after this method
     * is invoked.
     *
     * @param surface the [Surface]
     * @see .getSurface
     */
    fun setSurface(surface: Surface)

    /**
     * Get the [Surface] that was previously set.
     *
     * @return the [Surface] hold by player.
     * @see .setSurface
     */
    fun getSurface(): Surface

    /**
     * Sets video scaling mode. To make the target video scaling mode effective during playback. The
     * default video scaling mode is [.SCALING_MODE_DEFAULT].
     *
     *
     * The supported scaling mode are:
     *
     *  * [.SCALING_MODE_DEFAULT]
     *  * [.SCALING_MODE_ASPECT_FIT]
     *  * [.SCALING_MODE_ASPECT_FILL]
     *
     *
     * @param scalingMode target video scaling mode.
     * @throws IllegalArgumentException scalingMode must be one of the supported video scaling modes;
     * otherwise, IllegalArgumentException will be thrown.
     */
    @Throws(IllegalArgumentException::class)
    fun setVideoScalingMode(@ScalingMode scalingMode: Int)

    /**
     * Get the video scaling mode was previously set.
     *
     * @return scalingMode hold by player
     */
    @ScalingMode
    fun getVideoScalingMode(): Int

    /**
     * Sets the volume on this player.
     *
     *
     * Note that the passed volume values are raw scalars in range 0.0 to 1.0. UI controls
     * should be scaled logarithmically.
     *
     * @param leftVolume  left volume scalar. Range [0.0, 1.0]
     * @param rightVolume right volume scalar. Range [0.0, 1.0]
     */
    fun setVolume(leftVolume: Float, rightVolume: Float)

    /**
     * Gets the volume on this player.
     *
     * @return Volumes array of player. float[0] for leftVolume; float[1] for rightVolume.
     */
    fun getVolume(): FloatArray

    /**
     * Sets the mute state on this player.
     *
     * @param muted muted state. true for muted. Default value is false.
     */
    fun setMuted(muted: Boolean)

    /**
     * Gets whither the player is muted or not.
     *
     * @return true muted, false not muted. Default value is false.
     */
    fun isMuted(): Boolean

    /**
     * Prepares the player with [MediaSource] for playback, asynchronously.
     *
     *
     * [ActionPrepare] will be emitted immediately after this method is invoked.
     *
     *
     * Then [StatePreparing] will be emitted indicate player state is changing to preparing.
     *
     *
     * [StatePrepared] will be emitted if prepare succeed.
     *
     *
     * [StateError] will be emitted if prepare error.
     *
     * @param source [MediaSource] instance
     * @throws IllegalStateException prepare method must be called in one of:
     *
     *  * [.STATE_IDLE]
     *  * [.STATE_STOPPED]
     *
     * Otherwise, IllegalStateException will be thrown.
     * @see .addPlayerListener
     * @see .getState
     * @see .isIDLE
     * @see .isStopped
     */
    @Throws(java.lang.IllegalStateException::class)
    fun prepare(source: MediaSource)


    /**
     * @return The [MediaSource] instance hold by player
     */
    fun getDataSource(): MediaSource?

    /**
     * Set whether [.start] should be invoked automatically when player is prepared.
     *
     * @param startWhenPrepared whether [.start] should be invoked automatically
     * @see .addPlayerListener
     * @see .prepare
     * @see StatePrepared
     *
     * @see .isStartWhenPrepared
     */
    fun setStartWhenPrepared(startWhenPrepared: Boolean)

    /**
     * Get whether [.start] should be invoked automatically when player is prepared.
     *
     * @return startWhenPrepared flag hold by player
     */
    fun isStartWhenPrepared(): Boolean

    /**
     * Seeks to specified time position.
     *
     * @param seekTo the offset in milliseconds from the start to seek to
     * @throws IllegalStateException when player current state is not in [.isInPlaybackState]
     * @see .isInPlaybackState
     */
    @Throws(IllegalStateException::class)
    fun seekTo(seekTo: Long)

    /**
     * Starts or resumes playback.
     * If playback had previously been paused, playback will continue from where it was paused.
     * If playback is prepared, playback will start at [.getStartTime]
     *
     * @throws IllegalStateException when player current state is not in [.isInPlaybackState]
     * @see .isInPlaybackState
     */
    @Throws(IllegalStateException::class)
    fun start()

    /**
     * Pauses playback. Call start() to resume playback.
     *
     * @throws IllegalStateException when player current state is not in [.isInPlaybackState]
     * @see .isInPlaybackState
     */
    @Throws(IllegalStateException::class)
    fun pause()

    /**
     * Stops playback. Call prepare() + start() to start playback again. Call release() to release
     * the player when this player instance is no longer required.
     *
     *
     *  `stop()` will not clear playback info such like:
     *
     *  * [MediaSource] instance can still be get from [.getDataSource]
     *  * [Surface] instance can still be get from [.getSurface]
     *
     *
     *
     *  Listeners added by [.addPlayerListener] will not be
     * removed either.
     *
     * @throws IllegalStateException when player current state is not in:
     *
     *  * [.STATE_PREPARING]
     *  * [.isInPlaybackState]
     *  * [.STATE_STOPPED]
     *
     * @see .reset
     * @see .release
     */
    @Throws(IllegalStateException::class)
    fun stop()

    /**
     * Resets the player to [.STATE_IDLE] state. Call prepare() + start()  to start playback
     * again. Call release() to release the player when this player instance is no longer required.
     *
     *
     *  Calling `reset()` will clear all playback info. But listeners added by
     * [.addPlayerListener] will not be removed.
     *
     * @see .stop
     * @see .release
     */
    fun reset()

    /**
     * Releases the player. This method must be called when the player is no longer required. The
     * player must not be used after calling this method.
     */
    fun release()

    /**
     * @return The duration of media content in milliseconds.
     *
     *  Returns 0 if current player state is not one of:
     *
     *  * [.isInPlaybackState]
     *  * [.STATE_STOPPED]
     *
     */
    fun getDuration(): Long

    /**
     * @return The playback position in the current media content in milliseconds.
     */
    fun getCurrentPosition(): Long

    /**
     * @return Buffered percent in player memory cache queue.
     */
    @IntRange(from = 0, to = 100)
    fun getBufferedPercentage(): Int

    /**
     * @return Buffered duration in memory cache queue since [.getCurrentPosition].
     */
    fun getBufferedDuration(): Long

    /**
     * @return Width of video frame in px
     */
    fun getVideoWidth(): Int

    /**
     * @return Height of video frame in px
     */
    fun getVideoHeight(): Int

    /**
     * @return Sample aspect ratio of video frame in float.
     */
    fun getVideoSampleAspectRatio(): Float

    /**
     * Set loop playback when complete.
     * [StateCompleted] will be emitted once video is complete. [.start] will be
     * called if looping is true.
     *
     * @param looping true looping playback. Otherwise false.
     */
    fun setLooping(looping: Boolean)

    /**
     * @return looping true looping playback. Otherwise false.
     */
    fun isLooping(): Boolean

    /**
     * Set playback speed factor
     *
     * @param speed (0, 2] in float. Recommend values [0.5, 1, 1.5, 2].
     */
    fun setSpeed(speed: Float)

    /**
     * @return playback speed factor
     */
    fun getSpeed(): Float

    /**
     * Set audio pitch factor
     *
     * @param audioPitch audio pitch factor
     */
    fun setAudioPitch(audioPitch: Float)

    /**
     * @return Audio pitch factor.
     */
    fun getAudioPitch(): Float

    /**
     * Set the audio session ID.
     *
     * @param audioSessionId the audio session ID.
     */
    fun setAudioSessionId(audioSessionId: Int)

    /**
     * @return the audio session ID.
     * @see .setAudioSessionId
     */
    fun getAudioSessionId(): Int

    fun setSuperResolutionEnabled(enabled: Boolean)

    fun isSuperResolutionEnabled(): Boolean

    fun setSubtitleEnabled(enabled: Boolean)

    fun isSubtitleEnabled(): Boolean

    @DecoderType
    fun getVideoDecoderType(): Int

    @CodecId
    fun getVideoCodecId(): Int

    /**
     * @return true: IO buffering. Otherwise, false.
     */
    fun isBuffering(): Boolean

    @PlayerState
    fun getState(): Int

    /**
     * @see .STATE_IDLE
     */
    fun isIDLE(): Boolean

    /**
     * @see .STATE_PREPARING
     */
    fun isPreparing(): Boolean

    /**
     * @see .STATE_PREPARED
     */
    fun isPrepared(): Boolean

    /**
     * @see .STATE_STARTED
     */
    fun isPlaying(): Boolean

    /**
     * @see .STATE_PAUSED
     */
    fun isPaused(): Boolean

    /**
     * @see .STATE_COMPLETED
     */
    fun isCompleted(): Boolean

    /**
     * @see .STATE_STOPPED
     */
    fun isStopped(): Boolean

    /**
     * @see .STATE_RELEASED
     */
    fun isReleased(): Boolean

    /**
     * @see .STATE_ERROR
     */
    fun isError(): Boolean

    /**
     * @return true if [.getState] is in playback state.
     *
     *  * [.STATE_PREPARED]
     *  * [.STATE_STARTED]
     *  * [.STATE_PAUSED]
     *  * [.STATE_COMPLETED]
     *
     */
    fun isInPlaybackState(): Boolean

    /**
     * @return [PlayerException] instance if the player is in [.STATE_ERROR] state after
     * [StateError] event is emitted. Otherwise, return null.
     */
    fun getPlayerException(): PlayerException

    fun dump(): String?

    public interface Factory {
        class Default {
            companion object {
                private var sInstance: Factory? = null

                @Synchronized
                fun set(factory: Factory) {
                    sInstance = factory
                }

                @Synchronized
                fun get(): Factory {
                    return sInstance!!
                }
            }

        }

        fun create(source: MediaSource): Player
    }
}