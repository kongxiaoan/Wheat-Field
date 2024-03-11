package com.kpa.video.playerkit

import android.os.Looper
import androidx.annotation.MainThread
import com.kpa.player.event.Dispatcher
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.playback.PlayerPool

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class PlaybackController {
    private var mPlayerPool: PlayerPool? = null
    private var mPlayerFactory: Player.Factory? = null
    private val mDispatcher: Dispatcher by lazy {
        Dispatcher(Looper.getMainLooper())
    }

    @MainThread
    constructor() : this(PlayerPool.DEFAULT, Player.Factory.Default.get()) {
    }

    @MainThread
    constructor(playerPool: PlayerPool, factory: Player.Factory) {
        this.mPlayerPool = playerPool
        this.mPlayerFactory = factory
    }

    @MainThread
    fun addPlaybackListener(listener: Dispatcher.EventListener) {
        mDispatcher.addEventListener(listener)
    }

}