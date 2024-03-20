package com.kpa.i18


import android.icu.util.ULocale
import android.telephony.PhoneNumberUtils
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.kpa.i18.internal.SpellUtil
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kpa.i18.test", appContext.packageName)
        println("countries =  start }")
        runBlocking {
            val isoCountriesEntities = I18nProvider.i18nApi.getISOCountriesByLocale(ULocale.CHINA)
            println("countries =  ${isoCountriesEntities} }")
            isoCountriesEntities.forEach {
                println("countries 11 =  $it}")
            }
        }
    }
}
