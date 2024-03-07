package com.kpa.i18.internal

import android.icu.text.DateFormat
import com.kpa.i18.base.I18nBaseApi
import java.util.Date
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: Android N API 实现
 */
class I8nAndroidNApiImpl : I18nBaseApi {

    private val dataFormat by lazy {
        DateFormat.getDateTimeInstance()
    }

    override fun getFormatTime(time: Long): String? {
        if (time <= 0) return null
        return dataFormat.format(Date(timeFormat(time)))
    }
}