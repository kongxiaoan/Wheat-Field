package com.kpa.i18.base

import android.icu.util.ULocale
import com.kpa.i18.entities.ISOCountriesEntity
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: 国家化APi抽象文件
 */
interface I18nBaseApi {
    fun updateLocal(locale: Locale)
    fun getAlphabeticIndexByLocale(): MutableList<String>

    fun getISOCountries(): Array<String>

    suspend fun getISOCountriesByLocale(locale:ULocale): MutableList<ISOCountriesEntity>
}