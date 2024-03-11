package com.kpa.video.uikit.widgets.viewpager2

import android.util.SparseIntArray
import androidx.viewpager2.widget.ViewPager2
import java.lang.ref.WeakReference

/**
 *
 * @author: kpa
 * @date: 2024/3/1
 * @description:
 */
abstract class OnPageChangeCallbackCompat(viewPager: ViewPager2) :
    ViewPager2.OnPageChangeCallback() {
    val RETRY_COUNT = 10
    var mViewPagerRef: WeakReference<ViewPager2>? = null
    private val mPageSelectedTryInvokeCounts = SparseIntArray()

    init {
        this.mViewPagerRef = WeakReference(viewPager)
    }

    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
    }

    open fun onPageSelected(position: Int, pager: ViewPager2?) {}
}