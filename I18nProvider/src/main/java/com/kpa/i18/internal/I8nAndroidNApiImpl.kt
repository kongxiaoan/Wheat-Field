package com.kpa.i18.internal

import android.icu.text.AlphabeticIndex
import android.icu.text.DateFormat
import android.icu.util.ULocale
import com.kpa.i18.base.I18nBaseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: Android N API 实现
 */
class I8nAndroidNApiImpl : I18nBaseApi {

    private val index: AlphabeticIndex<Int> by lazy {
        AlphabeticIndex<Int>(getLocale())
    }


    override fun updateLocal(locale: Locale) {
        TODO("Not yet implemented")
    }


    override fun getAlphabeticIndexByLocale(): MutableList<String> {
        return try {
            val bucketLabels = index.bucketLabels
            bucketLabels
        } finally {
            indexArray()
        }
    }

    override fun getISOCountries(): Array<String> {
        return ULocale.getISOCountries()
    }
}