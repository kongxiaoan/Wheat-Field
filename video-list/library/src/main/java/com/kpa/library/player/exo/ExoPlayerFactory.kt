package com.kpa.library.player.exo

import android.content.Context
import com.kpa.library.player.core.APlayerFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */
class ExoPlayerFactory : APlayerFactory<ExoPlayer>() {
    override fun create(context: Context): List<ExoPlayer> {
        val list = arrayListOf<ExoPlayer>()
        for (i in 0..3) {
            val exoPlayer = ExoPlayer(context)
            list.add(exoPlayer)
        }
        return list
    }

}