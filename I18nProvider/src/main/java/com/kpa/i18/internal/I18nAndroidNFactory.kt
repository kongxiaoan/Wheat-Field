package com.kpa.i18.internal

import com.kpa.i18.base.I18nBaseFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description:
 */
object I18nAndroidNFactory : I18nBaseFactory<I8nAndroidNApiImpl>() {
    override fun create(): I8nAndroidNApiImpl {
        return I8nAndroidNApiImpl()
    }
}