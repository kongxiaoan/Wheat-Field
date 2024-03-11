package com.kpa.i18.base

import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: 国家化APi抽象文件
 */
interface I18nBaseApi {
    fun updateLocal(locale: Locale)
    fun getAlphabeticIndexByLocale(): Array<String>
}