package com.kpa.library.defaults

import android.view.View
import android.view.ViewGroup
import com.kpa.library.core.VideoLayer

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class GestureLayer: VideoLayer() {
    override fun createView(parent: ViewGroup): View {
        val view = View(parent.context)

        return view
    }
}