package com.kpa.i18.internal

import com.kpa.i18.base.I18nBaseApi
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/7
 * @description:
 */


/**
 * 阿语文字适配
 * 阿拉伯语中的阿拉伯数字不是需要的，之前是使用TimeUtils.arabicToDecimal 利用unicode值进行转换的
 * 在国际语言中，阿拉拍文字用ar-u-nu-latn保持一致
 */
inline fun I18nBaseApi.getLocale(local: Locale? = null): Locale {
    val tempLocale = local ?: Locale.getDefault()
    return if (tempLocale.language.startsWith("ar")) {
        Locale("ar-u-nu-latn")
    } else {
        tempLocale
    }
}

inline fun Long.timeFormat(): Long {
    return if (this.toString().length == 13) {
        this
    } else {
        this * 1000L
    }
}

inline fun I18nBaseApi.indexArray(): Array<String> {
    return arrayOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    )
}