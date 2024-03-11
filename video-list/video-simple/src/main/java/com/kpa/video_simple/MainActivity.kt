package com.kpa.video_simple

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kpa.video.common.utils.UIUtils
import com.kpa.video.uikit.model.VideoItem
import com.kpa.video.uikit.video.ShortVideoSceneView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoItems = mutableListOf<VideoItem>()
        testData(videoItems)
        setContentView(ShortVideoSceneView(this).apply {
            pageView().apply {
                mLifecycle = lifecycle
                setItems(videoItems)
            }
        })
        UIUtils.setSystemBarTheme(
            this,
            Color.TRANSPARENT,
            lightStatusBar = false,
            immersiveStatusBar = true,
            navigationBarColor = Color.BLACK,
            lightNavigationBar = false,
            immersiveNavigationBar = false
        )
    }

    private fun testData(videoItems: MutableList<VideoItem>) {
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
        videoItems.add(VideoItem.createVideoModelItem())
    }
}