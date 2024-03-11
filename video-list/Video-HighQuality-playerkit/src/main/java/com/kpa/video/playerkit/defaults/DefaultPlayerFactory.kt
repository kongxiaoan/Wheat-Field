package com.kpa.video.playerkit.defaults

import android.content.Context
import android.os.Looper
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.source.MediaSource


/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
class DefaultPlayerFactory constructor(var context: Context) : Player.Factory {

    private var mContext: Context = context.applicationContext


    override fun create(source: MediaSource): Player {
        return ExoPlayer(mContext, DefaultAdapter.Factory(), Looper.getMainLooper())
    }
}