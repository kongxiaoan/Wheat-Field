package com.kpa.library.player.controller

import android.content.res.Configuration
import android.util.Log
import android.view.Surface
import androidx.annotation.MainThread
import androidx.annotation.OptIn
import androidx.media3.common.util.Assertions.checkMainThread
import androidx.media3.common.util.UnstableApi
import com.kpa.library.core.VideoView
import com.kpa.library.page.model.VideoPageItem
import com.kpa.library.player.core.APlayerFactory
import com.kpa.library.player.core.IPlayer
import com.kpa.library.player.core.pool.PlayerPool
import com.kpa.library.player.exo.ExoPlayerFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description: 播放器控制器
 */
@OptIn(UnstableApi::class)
class PlayerController {
    private var mPlayerPool: PlayerPool? = null
    private var mPlayerFactory: APlayerFactory<*>? = null
    private var mVideoView: VideoView? = null
    private var mPlayer: IPlayer? = null
    private var mStartOnReadyCommand: Runnable? = null
    private var mSurfaceListener: SurfaceListener? =
        null

    companion object {
        const val TAG = "PlayerController"
    }

    constructor() : this(PlayerPool.DEFAULT, APlayerFactory.PLAYER)

    constructor(mPlayerPool: PlayerPool, mPlayerFactory: APlayerFactory<*>?) {
        this.mPlayerPool = mPlayerPool
        this.mPlayerFactory = mPlayerFactory
        mSurfaceListener =
            SurfaceListener(this)
    }

    private fun unbindPlayer(recycle: Boolean) {
        if (mPlayer != null) {
            if (recycle) {
                mPlayer!!.setSurface(null)
                mPlayerPool!!.recycle(mPlayer!!)
            }
            mStartOnReadyCommand = null;
//            mPlayer.removePlayerListener(mPlayerListener)
            val toUnbind: IPlayer? = mPlayer
            mPlayer = null
        }
    }


    private fun bindVideoView(newVideoView: VideoView?) {
        if (mVideoView == null && newVideoView != null) {
            mVideoView = newVideoView
            mVideoView?.addVideoViewListener(mSurfaceListener)
            mVideoView!!.bindController(this)
        }
    }

    fun videoView(): VideoView? {
        return mVideoView
    }

    @MainThread
    fun startPlayback() {
        startPlay(true)
    }

    @MainThread
    fun stopPlayback() {
        val attachedView = mVideoView
        val attachedPlayer: IPlayer? = mPlayer
        attachedView?.setReuseSurface(false)
        if (mStartOnReadyCommand != null // startPlayback but surface not ready
            || attachedPlayer != null
        ) {
            mStartOnReadyCommand = null
            unbindPlayer(true)
        }
    }

    @MainThread
    fun startPlay(startWhenPrepared: Boolean) {
        checkMainThread()
        val attachedView: VideoView =
            mVideoView ?: throw IllegalStateException("videoview 没有绑定")
        val attachedPlayer: IPlayer?
        attachedView.setReuseSurface(true)
        if (mPlayer == null) {
            attachedPlayer =
                mPlayerPool?.create(attachedView.context, mPlayerFactory ?: ExoPlayerFactory())
            if (mPlayer == null) {
                mPlayer = attachedPlayer
            }
        } else {
            attachedPlayer = mPlayer
        }
        Log.d(TAG, "mPlayer ${isReady(attachedPlayer, attachedView)}")
        if (isReady(attachedPlayer, attachedView)) {
            start(attachedView, attachedPlayer, startWhenPrepared)
        } else {
            Log.d(TAG, "isReady false")
            mStartOnReadyCommand = Runnable {
                if (isReady(attachedPlayer, attachedView)) {
                    start(attachedView, attachedPlayer, startWhenPrepared)
                }
            }
        }
    }

    private fun start(
        attachedView: VideoView,
        attachedPlayer: IPlayer?,
        startWhenPrepared: Boolean
    ) {
        mStartOnReadyCommand = null
        val surface: Surface = attachedView.getSurface() ?: return
        Log.d(TAG, "isReady true state = $surface")
        attachedPlayer?.setSurface(surface)
        @IPlayer.Companion.PlayerState
        val state = attachedPlayer?.getState()
        Log.d(TAG, "isReady true state = $state")
        when (state) {
            IPlayer.STATE_IDLE -> {
                attachedPlayer.setStartWhenPrepared(startWhenPrepared)
                attachedPlayer.setDataSource("http://vjs.zencdn.net/v/oceans.mp4")
                attachedPlayer.prepareAsync()
            }

            IPlayer.STATE_COMPLETED -> {
                if (startWhenPrepared) {
                    attachedPlayer.start()
                }
            }
        }
    }

    private fun isReady(player: IPlayer?, videoView: VideoView?): Boolean {
        return player != null && videoView != null && videoView.getSurface() != null && videoView.getSurface()?.isValid == true && videoView.getSource() != null
    }

    fun bind(videoView: VideoView) {
        if (mVideoView != null && mVideoView !== videoView) {
            unbindVideoView()
        }
        bindVideoView(videoView)
    }

    private fun unbindVideoView() {
        if (mVideoView != null) {
            val toUnbind: VideoView? = mVideoView
            mVideoView = null
            toUnbind!!.unbindController(this)
        }
    }

    @MainThread
    fun player(): IPlayer? {
        return mPlayer
    }


    internal class SurfaceListener(val controller: PlayerController) :
        VideoView.VideoViewListener {
        override fun onVideoViewBindDataSource(dataSource: VideoPageItem) {

        }

        override fun onVideoViewClick(videoView: VideoView) {

        }

        override fun onConfigurationChanged(newConfig: Configuration?) {

        }

        override fun onWindowFocusChanged(hasWindowFocus: Boolean) {

        }

        override fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int) {
            if (controller.mStartOnReadyCommand != null) {
                controller.mStartOnReadyCommand!!.run()
            } else {
                controller.player()?.setSurface(surface)
            }
        }

        override fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int) {

        }

        override fun onSurfaceUpdated(surface: Surface?) {
        }

        override fun onSurfaceDestroy(surface: Surface?) {
        }

    }

}