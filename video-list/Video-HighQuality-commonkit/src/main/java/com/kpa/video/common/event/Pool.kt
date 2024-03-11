package com.kpa.player.event

import android.util.ArrayMap
import androidx.core.util.Pools
import com.kpa.player.event.Factory.create


/**
 *
 * @author: kpa
 * @date: 2023/12/22
 * @description:
 */
object Pool {
    // 存储不同事件类型的对象池
    private val sPools: MutableMap<Class<out Event>, Pools.SimplePool<Event>> = ArrayMap()

    /**
     * 从对象池中获取指定类型的事件实例。
     *
     * @param clazz 事件类型
     * @param <T>   事件类型
     * @return 获取的事件实例
    </T> */
    @Synchronized
    fun <T : Event> acquire(clazz: Class<T>): T {
        // 根据事件类型获取对象池
        var pool = sPools[clazz]
        if (pool == null) {
            // 如果对象池不存在，则创建一个新的对象池，并添加到集合中
            pool = Pools.SimplePool<Event>(Config.EVENT_POOL_SIZE)
            sPools[clazz] = pool
        }
        // 从对象池中获取事件实例
        val event = pool.acquire()
        return if (event != null) {
            clazz.cast(event)
        } else create(clazz)
        // 如果对象池为空，调用工厂方法创建新的事件实例
    }

    /**
     * 将事件实例放回对象池。
     *
     * @param event 要放回的事件实例
     */
    @Synchronized
    fun release(event: Event) {
        // 回收事件实例
        event.recycle()
        // 获取事件类型对应的对象池
        val eventPool = sPools[event.javaClass]
        eventPool?.release(event)
    }
}