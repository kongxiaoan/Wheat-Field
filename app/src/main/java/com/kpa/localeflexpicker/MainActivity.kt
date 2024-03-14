package com.kpa.localeflexpicker

import android.icu.text.Transliterator
import android.icu.util.ULocale
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val isoCountries = ULocale.getISOCountries()
        isoCountries.forEach {
            Log.d(
                "MainActivity",
                "--- ${ULocale("", it).getDisplayCountry(ULocale.ENGLISH)}    code  = $it"
            )
        }

        Log.d("MainActivity", getPinyin("你好中国"))
        Log.d("MainActivity", getFirstLetters(getPinyin("你好中国")))

    }

    private fun getFirstLetters(pinyin: String): String {
        // 使用正则表达式提取首字母
        val pinyinArray = pinyin.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val result = StringBuilder()
        for (s in pinyinArray) {
            if (!s.isEmpty()) {
                result.append(s[0])
            }
        }
        return result.toString()
    }

    fun getPinyin(chineseString: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Transliterator.getInstance("Han-Latin;Latin-ASCII").transliterate(chineseString)
        } else {
            chineseString
        }
    }
}