package com.kpa.video.common.utils

import android.os.Parcelable
import java.io.Serializable
import java.util.Collections

/**
 *
 * @author: kpa
 * @date: 2024/3/4
 * @description:
 */
open class ExtraObject {
    protected val mExtras: MutableMap<String, Any> =
        Collections.synchronizedMap(LinkedHashMap<String, Any>())

    public fun <T> getExtra(key: String, clazz: Class<T>): T? {
        var extra = mExtras.get(key)
        extra?.let {
            if (clazz.isInstance(it)) {
                return@let extra as T
            }
            throw ClassCastException("${extra.javaClass} can't be cast to $clazz")
        }
        return null
    }

    fun putExtra(key: String, extra: Any?) {
        if (extra == null) {
            mExtras.remove(key)
        } else {
            if (extra is Serializable || extra is Parcelable) {
                mExtras[key] = extra
            } else {
                throw IllegalArgumentException("Unsupported type " + extra.javaClass)
            }
        }
    }

    fun clearExtras() {
        mExtras.clear()
    }

}