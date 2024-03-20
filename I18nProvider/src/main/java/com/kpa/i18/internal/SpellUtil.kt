package com.kpa.i18.internal

import android.icu.text.Transliterator
import android.os.Build
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import java.util.Locale

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
object SpellUtil {
    var sb = StringBuilder()

    /**
     * 获取汉字字符串的各个字首字母
     * 中国：zg
     *
     * @param chines
     * @return
     */
    fun getSpellHeadChar(chines: String): String {

        sb.setLength(0)
        val chars = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)
        for (i in chars.indices) {
            if (chars[i].code > 128) {
                try {
                    sb.append(
                        PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[0][0]
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                sb.append(chars[i])
            }
        }
        return sb.toString()
    }

    /**
     * 获取汉字字符串的首字母
     * 中国：Z
     *
     * @param string
     * @return
     */
    fun getSpellFirstLetter(string: String): String {
        sb.setLength(0)
        val c = string[0]
        val pinyinArray: Array<String> = PinyinHelper.toHanyuPinyinStringArray(c)
        sb.append(pinyinArray[0][0])
        return sb.toString().uppercase(Locale.getDefault())
    }

    /**
     * 获取汉字字符串的汉语拼音
     * 中国：zhongguo
     *
     * @param chines
     * @return
     */
    fun getAllSpell(chines: String): String {
        sb.setLength(0)
        val nameChar = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)
        for (i in nameChar.indices) {
            if (nameChar[i].code > 128) {
                try {
                    sb.append(
                        PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat).get(0)
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                sb.append(nameChar[i])
            }
        }
        return sb.toString()
    }
}