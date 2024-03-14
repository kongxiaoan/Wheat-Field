package com.kpa.video.cache

import android.app.Application
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
object VideoPreloadingManager {
    lateinit var simpleCache: SimpleCache
    const val exoPlayerCacheSize: Long = 90 * 1024 * 1024
    lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
    lateinit var exoDatabaseProvider: ExoDatabaseProvider

    fun init(application: Application) {
        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = ExoDatabaseProvider(application)
        simpleCache =
            SimpleCache(application.cacheDir, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)
    }
}