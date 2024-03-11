package com.kpa.video_simple

import android.app.Application
import com.kpa.video.playerkit.base.Player
import com.kpa.video.playerkit.defaults.DefaultPlayerFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Player.Factory.Default.set(DefaultPlayerFactory(this))
    }
}