package com.kpa.i18.internal

import android.icu.text.AlphabeticIndex
import android.icu.text.DateFormat
import android.icu.util.ULocale
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.kpa.i18.base.I18nBaseApi
import com.kpa.i18.entities.ISOCountriesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/6
 * @description: Android N API 实现
 */
class I8nAndroidNApiImpl : I18nBaseApi {

    private val index: AlphabeticIndex<Int> by lazy {
        AlphabeticIndex<Int>(getLocale())
    }


    override fun updateLocal(locale: Locale) {
        TODO("Not yet implemented")
    }


    override fun getAlphabeticIndexByLocale(): MutableList<String> {
        return try {
            val bucketLabels = index.bucketLabels
            bucketLabels
        } finally {
            indexArray()
        }
    }

    override fun getISOCountries(): Array<String> {
        return ULocale.getISOCountries()
    }

    override suspend fun getISOCountriesByLocale(locale: ULocale): MutableList<ISOCountriesEntity> =
        coroutineScope {
            val countriesList = mutableListOf<ISOCountriesEntity>()
            val isoCountries = getISOCountries()
            isoCountries.map { countryCode ->
                async(Dispatchers.Default) {
                    val countryName = ULocale("", countryCode).getDisplayCountry(locale)
                    val countriesRegion =
                        PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode)
                    val countriesHead = SpellUtil.getSpellHeadChar(countryName)
                    val countriesAllSpell = SpellUtil.getAllSpell(countryName)
                    val countriesFirstSpell = SpellUtil.getSpellFirstLetter(countryName)
                    ISOCountriesEntity(
                        countriesCode = countryCode,
                        countriesName = countryName,
                        countriesRegion = countriesRegion,
                        countriesHead = countriesHead,
                        countriesAllSpell = countriesAllSpell,
                        countriesFirstSpell = countriesFirstSpell
                    )
                }
            }.forEach {
                countriesList.add(it.await())
            }
            println("countriesList = $countriesList")
            countriesList
        }
}