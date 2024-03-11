package com.kpa.player.event

/**
 *
 * @author: kpa
 * @date: 2023/12/22
 * @description:
 */
object Factory {
    /**
     * 创建指定类型的事件实例。
     *
     * @param clazz 要创建的事件类型
     * @param <T>   事件类型
     * @return 创建的事件实例
     * @throws NullPointerException 如果创建实例时发生空指针异常
    </T> */
    fun <T : Event?> create(clazz: Class<T>): T {
        try {
            return clazz.newInstance()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        throw NullPointerException()
    }
}