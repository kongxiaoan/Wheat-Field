package com.kpa.i18

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import com.kpa.i18.base.I18nBaseApi
import com.kpa.i18.internal.I18nAndroidMFactory
import com.kpa.i18.internal.I18nAndroidNFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.Executors

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: i18n 使用句柄
 */
object I18nProvider {
    private var oldLocale: Locale = Locale.getDefault()

    val i18nApi: I18nBaseApi by lazy {
        I18nAndroidNFactory.create()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            I18nAndroidNFactory.create()
//        } else {
//            I18nAndroidMFactory.create()
//        }
    }

    fun init(locale: Locale) {
        if (this.oldLocale == null || this.oldLocale == locale) {
            return
        }
        this.oldLocale = locale
    }

}