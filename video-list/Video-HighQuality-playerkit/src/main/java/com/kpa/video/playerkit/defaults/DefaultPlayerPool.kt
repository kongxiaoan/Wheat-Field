package com.kpa.video.playerkit.defaults

import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.playback.PlayerPool
import com.kpa.video.playerkit.source.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class DefaultPlayerPool : PlayerPool {
    override fun acquire(source: MediaSource, factory: Player.Factory): Player {
        TODO("Not yet implemented")
    }

    override fun get(source: MediaSource): Player {
        TODO("Not yet implemented")
    }

    override fun recycle(player: Player) {
        TODO("Not yet implemented")
    }

}