package com.kpa.i18.internal

import com.kpa.i18.base.I18nBaseFactory

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description:
 */
object I18nAndroidMFactory : I18nBaseFactory<I8nAndroidMApiImpl>() {
    override fun create(): I8nAndroidMApiImpl {
        return I8nAndroidMApiImpl()
    }
}