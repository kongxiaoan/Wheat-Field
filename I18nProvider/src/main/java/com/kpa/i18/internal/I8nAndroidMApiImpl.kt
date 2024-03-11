package com.kpa.i18.internal

import com.kpa.i18.base.I18nBaseApi
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

    override fun getAlphabeticIndexByLocale(): Array<String> {
        return indexArray()
    }

}