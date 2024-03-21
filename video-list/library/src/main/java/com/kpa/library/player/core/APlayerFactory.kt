package com.kpa.library.player.core

import android.content.Context
import com.kpa.library.player.exo.ExoPlayerFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description: 播放器抽象工厂 完成具体播放器的创建
 */
abstract class APlayerFactory<out T : IPlayer> {

    companion object {
        val PLAYER = ExoPlayerFactory()
    }

    abstract fun create(context: Context): List<T>
}