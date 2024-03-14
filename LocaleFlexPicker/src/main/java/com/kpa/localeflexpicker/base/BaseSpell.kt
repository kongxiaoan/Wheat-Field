package com.kpa.localeflexpicker.base

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
interface BaseSpell {
    fun getSpell(): String
    fun getHeaderSpell(): String
    fun getIndexName(): String
    fun getPriority(): Int
    fun isCommon(): Boolean
}