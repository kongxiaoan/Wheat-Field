package com.kpa.player.event

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.kpa.player.event.Pool.release
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList


/**
 *
 * @author: kpa
 * @date: 2023/12/22
 * @description: 事件分发器类，用于管理和分发事件回调。
 */

class Dispatcher {
    interface EventListener {
        fun onEvent(event: Event)
    }

    //处理事件分发的Handler
    private var mHandler: H

    //存储事件监听器的线程安全列表
    private val mListeners = CopyOnWriteArrayList<EventListener>()

    /**
     * 初始化Dispatcher实例。
     *
     * @param looper 用于处理事件分发的Looper
     */
    constructor(looper: Looper) {
        this.mHandler = H(looper, this)
    }

    /**
     * 获取指定类型的事件实例，可选择从对象池中获取或直接创建。
     *
     * @param clazz 事件类的Class对象
     * @param owner 事件的拥有者
     * @param <T>   事件类型
     * @return 指定类型的事件实例
    </T> */
    fun <T : Event> obtain(clazz: Class<T>, owner: Any): T? {
        val event: T = if (Config.EVENT_POOL_ENABLE) Pool.acquire(clazz) else Factory.create(clazz)
        return clazz.cast(event.owner(owner).dispatcher(this))
    }

    /**
     * 添加事件监听器。
     *
     * @param listener 要添加的事件监听器
     */
    fun addEventListener(listener: EventListener) {
        mListeners.addIfAbsent(listener)
    }

    /**
     * 移除指定的事件监听器。
     *
     * @param listener 要移除的事件监听器
     */
    fun removeEventListener(listener: EventListener) {
        if (listener != null) {
            mListeners.remove(listener)
        }
    }

    /**
     * 移除所有事件监听器。
     */
    fun removeAllEventListener() {
        mListeners.clear()
    }

    /**
     * 分发事件，如果不在主线程，则通过Handler在主线程分发。
     *
     * @param event 要分发的事件
     */
    fun dispatchEvent(event: Event) {
        if (Thread.currentThread() != mHandler.looper.thread) {
            mHandler.obtainMessage(0, event).sendToTarget()
        } else {
            dispatch(event)
        }
    }

    /**
     * 释放Dispatcher资源，清空所有事件监听器和Handler消息。
     */
    fun release() {
        mHandler.post {
            mHandler.removeCallbacksAndMessages(null)
            mListeners.clear()
        }
    }

    /**
     * 实际的事件分发逻辑，遍历所有监听器并调用其onEvent方法。
     *
     * @param event 要分发的事件
     */
    private fun dispatch(event: Event) {
        for (listener in mListeners) {
            listener.onEvent(event)
        }
        // 如果事件的dispatcher是当前Dispatcher，并且启用了对象池，则释放事件到对象池
        if (event.dispatcher() == this && Config.EVENT_POOL_ENABLE) {
            release(event)
        }
    }

    private inner class H : Handler {
        private var mRef: WeakReference<Dispatcher>? = null

        constructor(looper: Looper, dispatcher: Dispatcher) : super(looper) {
            this.mRef = WeakReference(dispatcher)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var dispatcher = this.mRef?.get() ?: return
            if (msg.what == 0) {
                dispatcher.dispatch(msg.obj as Event)
                return
            }
            throw IllegalArgumentException()
        }
    }
}