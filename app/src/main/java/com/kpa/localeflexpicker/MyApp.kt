package com.kpa.localeflexpicker

import android.app.Application
import com.kpa.i18.I18nProvider
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/7
 * @description:
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        I18nProvider.init(Locale.CHINA)
    }
}