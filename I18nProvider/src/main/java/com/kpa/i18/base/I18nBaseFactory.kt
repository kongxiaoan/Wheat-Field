package com.kpa.i18.base

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description:
 */
abstract class I18nBaseFactory<out T : I18nBaseApi> {
    abstract fun create(): T
}