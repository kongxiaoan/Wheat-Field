package com.kpa.i18.internal

import android.icu.util.ULocale
import com.kpa.i18.base.I18nBaseApi
import com.kpa.i18.entities.ISOCountriesEntity
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: android M API 实现
 */
class I8nAndroidMApiImpl : I18nBaseApi {
    override fun updateLocal(locale: Locale) {
        TODO("Not yet implemented")
    }

    override fun getAlphabeticIndexByLocale(): MutableList<String> {
        return indexArray()
    }

    override fun getISOCountries(): Array<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getISOCountriesByLocale(locale: ULocale): MutableList<ISOCountriesEntity> {
        TODO("Not yet implemented")
    }

}