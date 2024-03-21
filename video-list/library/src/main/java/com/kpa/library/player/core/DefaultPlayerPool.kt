package com.kpa.library.player.core

import android.content.Context
import com.kpa.library.player.core.pool.PlayerPool

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */
class DefaultPlayerPool : PlayerPool {
    private var mAcquiredPlayers: MutableList<IPlayer> = mutableListOf()
    private var currentPlayerIndex = 0
    private var lastPageIndex: Int = 0

    override fun <T : IPlayer> create(context: Context, factory: APlayerFactory<T>): IPlayer? {
        val playerList = factory.create(context)
        mAcquiredPlayers.clear()
        mAcquiredPlayers.addAll(playerList)
        return get(0)
    }


    override fun get(pageIndex: Int): IPlayer? {
        if (mAcquiredPlayers.isEmpty()) {
            return null
        }
        if (pageIndex == 0) {
            currentPlayerIndex = 0
        } else {
            currentPlayerIndex =
                (currentPlayerIndex + (if (pageIndex > lastPageIndex) 1 else -1) + 3) % 3
        }
        lastPageIndex = pageIndex
        return mAcquiredPlayers[currentPlayerIndex]
    }

    override fun recycle(player: IPlayer) {

    }
}