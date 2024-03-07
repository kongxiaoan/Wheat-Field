package com.kpa.i18.base

import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: 国家化APi抽象文件
 */
interface I18nBaseApi {
    /**
     * 格式化long时间
     *
     * @param time 时间戳(秒)
     * @return 格式化后的时间
     */
    fun getFormatTime(time: Long): String?

    fun timeFormat(time: Long): Long {
        return if (time.toString().length == 13) {
            time
        } else {
            time * 1000L
        }
    }
}