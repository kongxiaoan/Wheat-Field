package com.kpa.player.event

import android.os.SystemClock
import androidx.annotation.CallSuper
import androidx.annotation.Keep


/**
 *
 * @author: kpa
 * @date: 2023/12/22
 * @description: 事件， 表示何种操作事件
 */
@Keep
open class Event {
    private var code = 0
    private var owner: Any? = null
    private var dispatcher: Dispatcher? = null

    // 事件分发时间
    private var dispatchTime: Long = 0

    /**
     * 构造方法，初始化事件实例。
     *
     * @param code 事件的唯一标识码
     */
    constructor(code: Int) {
        this.code = code
    }

    /**
     * 获取事件的唯一标识码。
     *
     * @return 事件的唯一标识码
     */
    fun code(): Int {
        return code
    }

    /**
     * 获取事件拥有者的实例，转换为指定类型。
     *
     * @param clazz 拥有者的类型
     * @param <T>   拥有者的类型
     * @return 拥有者的实例
    </T> */
    fun <T> owner(clazz: Class<T>): T {
        return clazz.cast(owner)
    }

    /**
     * 设置事件的拥有者。
     *
     * @param owner 事件的拥有者
     * @return 当前事件实例
     */
    fun owner(owner: Any?): Event {
        this.owner = owner
        return this
    }

    /**
     * 设置事件的分发器。
     *
     * @param dispatcher 事件的分发器
     * @return 当前事件实例
     */
    fun dispatcher(dispatcher: Dispatcher): Event {
        this.dispatcher = dispatcher
        return this
    }

    /**
     * 获取事件的分发器。
     *
     * @return 事件的分发器
     */
    fun dispatcher(): Dispatcher? {
        return dispatcher
    }

    /**
     * 执行事件分发，将事件发送到注册的监听器。
     */
    fun dispatch() {
        dispatchTime = SystemClock.uptimeMillis()
        dispatcher?.dispatchEvent(this)
    }

    /**
     * 获取事件的分发时间。
     *
     * @return 事件的分发时间
     */
    fun dispatchTime(): Long {
        return dispatchTime
    }

    /**
     * 回收事件，清空拥有者、分发器和分发时间。
     * 子类可重写此方法以释放额外资源。
     */
    @CallSuper
    fun recycle() {
        owner = null
        dispatcher = null
        dispatchTime = 0
    }

    /**
     * 判断事件是否已被回收。
     *
     * @return 如果事件已被回收，则返回 true；否则返回 false
     */
    fun isRecycled(): Boolean {
        return dispatcher == null
    }

    /**
     * 将事件强制转换为指定类型。
     *
     * @param clazz 目标类型
     * @param <E>   目标类型
     * @return 转换后的实例
    </E> */
    fun <E> cast(clazz: Class<E>): E {
        return clazz.cast(this)
    }

    override fun toString(): String {
        return "Event(code=$code, owner=$owner, dispatcher=$dispatcher, dispatchTime=$dispatchTime)"
    }


}