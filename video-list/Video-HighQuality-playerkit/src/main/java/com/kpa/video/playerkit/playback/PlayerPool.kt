package com.kpa.video.playerkit.playback

import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.defaults.DefaultPlayerPool
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
interface PlayerPool {
    companion object {
        @JvmStatic
        public val DEFAULT: PlayerPool = DefaultPlayerPool()
    }

    fun acquire(source: MediaSource, factory: Player.Factory): Player

    fun get(source: MediaSource): Player

    fun recycle(player: Player)

}