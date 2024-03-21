package com.kpa.library.player.core

import android.content.res.AssetFileDescriptor
import android.view.Surface
import androidx.annotation.IntDef

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description: 播放器抽象层，分离具体播放业务
 */
abstract class IPlayer {

    companion object {
        /**
         * 视频/音频开始渲染
         */
        const val MEDIA_INFO_RENDERING_START = 3

        /**
         * 缓冲开始
         */
        const val MEDIA_INFO_BUFFERING_START = 701

        /**
         * 缓冲结束
         */
        const val MEDIA_INFO_BUFFERING_END = 702

        /**
         * 视频旋转信息
         */
        const val MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001

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
    }

    protected var mPlayerEventListener: PlayerEventListener? = null

    @Companion.PlayerState
    private var mState = 0

    fun setState(@PlayerState newState: Int) {
        var state = 0
        synchronized(this) {
            state = this.mState
            this.mState = newState
        }
    }

    @PlayerState
    fun getState(): Int = mState

    /**
     * 初始化播放器实例
     */
    open fun initPlayer() {
        setState(STATE_IDLE)
    }

    /**
     * 设置播放地址
     *
     * @param path    播放地址
     * @param headers 播放地址请求头
     */
    abstract fun setDataSource(path: String, headers: Map<String, String> = hashMapOf())

    /**
     * 用于播放raw和asset里面的视频文件
     */
    abstract fun setDataSource(fd: AssetFileDescriptor)

    /**
     * 播放
     */
    abstract fun start()

    /**
     * 暂停
     */
    abstract fun pause()

    /**
     * 停止
     */
    abstract fun stop()

    /**
     * 准备开始播放（异步）
     */
    abstract fun prepareAsync()

    /**
     * 重置播放器
     */
    abstract fun reset()

    /**
     * 是否正在播放
     */
    abstract fun isPlaying(): Boolean

    /**
     * 调整进度
     */
    abstract fun seekTo(time: Long)

    /**
     * 释放播放器
     */
    abstract fun release()

    /**
     * 获取当前播放的位置
     */
    abstract fun getCurrentPosition(): Long

    /**
     * 获取视频总时长
     */
    abstract fun getDuration(): Long

    /**
     * 获取缓冲百分比
     */
    abstract fun getBufferedPercentage(): Int

    /**
     * 设置渲染视频的View,主要用于TextureView
     */
    abstract fun setSurface(surface: Surface?)

    /**
     * 设置音量
     */
    abstract fun setVolume(v1: Float, v2: Float)

    /**
     * 设置是否循环播放
     */
    abstract fun setLooping(isLooping: Boolean)

    /**
     * 设置其他播放配置
     */
    abstract fun setOptions()

    /**
     * 设置播放速度
     */
    abstract fun setSpeed(speed: Float)

    /**
     * 获取播放速度
     */
    abstract fun getSpeed(): Float

    /**
     * 获取当前缓冲的网速
     */
    abstract fun getTcpSpeed(): Long

    abstract fun setStartWhenPrepared(startWhenPrepared: Boolean)
    /**
     * 绑定VideoView
     */
    fun setPlayerEventListener(playerEventListener: PlayerEventListener) {
        mPlayerEventListener = playerEventListener
    }


    interface PlayerEventListener {
        fun onError(error: Exception)

        fun onCompletion()

        fun onInfo(what: Int, extra: Int)

        fun onPrepared()

        fun onVideoSizeChanged(width: Int, height: Int)
    }

}