package com.kpa.library.player.core.pool

import android.content.Context
import com.kpa.library.player.core.APlayerFactory
import com.kpa.library.player.core.IPlayer
import com.kpa.library.player.core.DefaultPlayerPool

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description: 播放器缓存池
 */
interface PlayerPool {

    companion object {
        val DEFAULT = DefaultPlayerPool()
    }

    fun <T : IPlayer> create(context: Context, factory: APlayerFactory<T>): IPlayer?

    fun get(pageIndex: Int): IPlayer?

    fun recycle(player: IPlayer)
}