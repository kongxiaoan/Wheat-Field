package com.kpa.player.event

/**
 *
 * @author: kpa
 * @date: 2023/12/22
 * @description:用于配置事件池的相关参数。
 */
object Config {
    /**
     * 是否启用事件对象池，默认为 true。
     */
    var EVENT_POOL_ENABLE = true

    /**
     * 事件对象池的大小，默认为 5。
     */
    var EVENT_POOL_SIZE = 5
}