package com.kpa.localeflexpicker.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.TypedValue
import android.view.DisplayCutout
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager

/**
 *
 * @author: kpa
 * @date: 2024/3/11
 * @description:
 */
object UIUtils {
    /**
     * 设置系统状态栏和导航栏的主题样式。
     *
     * @param activity               Activity 对象
     * @param statusBarColor         状态栏颜色
     * @param lightStatusBar         是否启用状态栏浅色主题
     * @param immersiveStatusBar     是否启用沉浸式状态栏
     * @param navigationBarColor     导航栏颜色
     * @param lightNavigationBar     是否启用导航栏浅色主题
     * @param immersiveNavigationBar 是否启用沉浸式导航栏
     */
    fun setSystemBarTheme(
        activity: Activity,
        statusBarColor: Int,
        lightStatusBar: Boolean,
        immersiveStatusBar: Boolean,
        navigationBarColor: Int,
        lightNavigationBar: Boolean,
        immersiveNavigationBar: Boolean
    ) {
        val window = activity.window
        var flags = window.decorView.systemUiVisibility
        if (immersiveStatusBar) {
            flags =
                flags or (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
        if (immersiveNavigationBar) {
            flags = flags or (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
        if (lightNavigationBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags =
                    flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv() or (0 and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
            }
        }
        if (lightStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags =
                    flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() or (0 and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
        }
        window.decorView.systemUiVisibility = flags
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = statusBarColor
        window.navigationBarColor = navigationBarColor
    }

    fun setWindowFlags(attrs: WindowManager.LayoutParams?, mask: Int) {
        setWindowFlags(attrs, mask, mask)
    }

    fun clearWindowFlags(attrs: WindowManager.LayoutParams?, mask: Int) {
        setWindowFlags(attrs, 0, mask)
    }

    private fun setWindowFlags(attrs: WindowManager.LayoutParams?, flags: Int, mask: Int) {
        if (attrs == null) return
        attrs.flags = attrs.flags and mask.inv() or (flags and mask)
    }

    fun getWindowLayoutParams(view: View): WindowManager.LayoutParams? {
        if (view.context is Activity) {
            val activity = view.context as Activity
            if (activity != null) {
                val window = activity.window
                if (window != null) {
                    return window.attributes
                }
            }
        }
        return null
    }

    fun hasDisplayCutout(window: Window): Boolean {
        var displayCutout: DisplayCutout? = null
        val rootView = window.decorView
        var insets: WindowInsets? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            insets = rootView.getRootWindowInsets()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && insets != null) {
            displayCutout = insets.displayCutout
            if (displayCutout != null) {
                if (displayCutout.getBoundingRects() != null && displayCutout.getBoundingRects().size > 0 && displayCutout.safeInsetTop > 0) {
                    return true
                }
            }
        }
        return true
    }

    fun getSystemBrightness(context: Context): Float {
        var systemBrightness = 0
        try {
            systemBrightness =
                Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return systemBrightness / UIUtils.getSystemSettingBrightnessMax()
    }

    fun getSystemSettingBrightnessMax(): Float {
        try {
            val res = Resources.getSystem()
            val resId =
                res.getIdentifier("config_screenBrightnessSettingMaximum", "integer", "android")
            if (resId != 0) {
                return res.getInteger(resId).toFloat()
            }
        } catch (e: Exception) { /* ignore */
        }
        return 255f
    }

    fun getScreenWidth(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val dm = context.resources.displayMetrics
        return dm?.widthPixels ?: 0
    }

    fun getScreenHeight(context: Context?): Int {
        if (context == null) {
            return 0
        }
        val dm = context.resources.displayMetrics
        return dm?.heightPixels ?: 0
    }

    fun sp2px(context: Context?, sp: Float): Float {
        return if (context != null) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                context.resources.displayMetrics
            )
        } else 0F
    }

    fun dip2Px(context: Context?, dipValue: Float): Float {
        if (context != null) {
            val scale = context.resources.displayMetrics.density
            return dipValue * scale + 0.5f
        }
        return 0F
    }

    fun px2dip(context: Context?, pxValue: Float): Int {
        if (context != null) {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
        return 0
    }

    fun getStatusBarHeight(context: Context?): Int {
        if (context == null) {
            return 0
        }
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun isNavigationBarShow(activity: Activity): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            val realSize = Point()
            display.getSize(size)
            display.getRealSize(realSize)
            realSize.y != size.y
        } else {
            val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
            val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
            !menu && !back
        }
    }

    fun getNavigationBarHeight(context: Context): Int {
        return if (isNavigationBarShow(context as Activity)) {
            getSizeByReflection(context, "navigation_bar_height")
        } else 0
    }

    fun getSizeByReflection(context: Context, field: String?): Int {
        var size = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = clazz.getField(field)[`object`].toString().toInt()
            size = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }


    private val sLocation = IntArray(2)

    fun getLocationInWindow(view: View): IntArray {
        view.getLocationInWindow(sLocation)
        return sLocation
    }


    fun isInViewArea(e: MotionEvent, view: View?): Boolean {
        if (view == null) return false
        val x = e.rawX
        val y = e.rawY
        val location = getLocationInWindow(view)
        val width = view.width
        val height = view.height
        val left = location[0]
        val top = location[1]
        val right = left + width
        val bottom = top + height
        return left < x && x < right && top < y && y < bottom
    }
}