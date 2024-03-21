package com.kpa.library.widget

import android.util.SparseIntArray
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.lang.ref.WeakReference

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
open class OnPageChangeCallbackCompat(viewPager: ViewPager2) : ViewPager2.OnPageChangeCallback() {
    companion object {
        const val RETRY_COUNT = 10
    }

    var mViewPagerRef: WeakReference<ViewPager2>? = null
    private val mPageSelectedTryInvokeCounts = SparseIntArray()

    init {
        mViewPagerRef = WeakReference(viewPager)
    }

    override fun onPageSelected(position: Int) {
        val viewPager = mViewPagerRef!!.get() ?: return

        var view: View? = null
        val recyclerView = viewPager.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        if (layoutManager != null) {
            view = layoutManager.findViewByPosition(position)
        }
        var retryCountAtPos = mPageSelectedTryInvokeCounts[position]
        if (view == null && retryCountAtPos < OnPageChangeCallbackCompat.RETRY_COUNT) {
            mPageSelectedTryInvokeCounts.put(position, ++retryCountAtPos)
            viewPager.postDelayed({ onPageSelected(position) }, 10)
            return
        }
        mPageSelectedTryInvokeCounts.put(position, 0)
        onPageSelected(position, viewPager)
    }

    fun onPageSelected(position: Int, pager: ViewPager2) {}
}