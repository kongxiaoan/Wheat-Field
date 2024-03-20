package com.kpa.i18.entities

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
data class ISOCountriesEntity(
    var countriesName: String = "",
    var countriesCode: String = "",
    var countriesRegion: Int = 0,
    var countriesAllSpell: String = "",
    var countriesFirstSpell: String = "",
    var countriesHead: String = ""
)
