package com.kpa.localeflexpicker.base

import android.graphics.Color
import android.icu.util.ULocale
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kpa.i18.I18nProvider
import com.kpa.localeflexpicker.utils.UIUtils
import com.kpa.localeflexpicker.widget.CountryOrRegionSideBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
abstract class BaseCountryOrRegionActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    protected abstract fun buildRecyclerView(): RecyclerView

    protected abstract fun buildSuspendView(): TextView

    protected abstract fun buildSearchView(): EditText

    protected abstract fun buildAdapter(): BaseCountryOrRegionSpellAdapter<*>

    protected abstract fun buildSideBar(): CountryOrRegionSideBar

    protected var selectedCountries = ArrayList<CountryOrRegionEntity>()
    protected var allCountries: MutableList<CountryOrRegionEntity> =
        arrayListOf<CountryOrRegionEntity>()
    private var suspendBarHeight = 0
    private var adapter: BaseCountryOrRegionSpellAdapter<*>? = null
    private var textView: TextView? = null
    private var manager: LinearLayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UIUtils.setSystemBarTheme(
            this,
            Color.TRANSPARENT,
            lightStatusBar = false,
            immersiveStatusBar = true,
            navigationBarColor = Color.BLACK,
            lightNavigationBar = false,
            immersiveNavigationBar = false
        )
        launch(Dispatchers.IO) {
            val isoCountriesEntities =
                I18nProvider.i18nApi.getISOCountriesByLocale(ULocale.getDefault())
            val countryOrRegionEntities = isoCountriesEntities.map { isoCountry ->
                CountryOrRegionEntity(
                    InternalState().apply {
                        code = isoCountry.countriesRegion
                        name = isoCountry.countriesName
                        spell = isoCountry.countriesAllSpell
                        indexName = isoCountry.countriesFirstSpell
                        headerSpell = isoCountry.countriesHead
                    },
                    ExternalState(false)
                )
            }
            withContext(Dispatchers.Main) {
                allCountries.clear()
                allCountries.addAll(countryOrRegionEntities)
            }
        }
        selectedCountries.clear()
        selectedCountries.addAll(allCountries)
        initRv()
    }

    private fun initRv() {
        val recyclerView = buildRecyclerView()
        textView = buildSuspendView()
        manager = LinearLayoutManager(this)
        adapter = buildAdapter()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                suspendBarHeight = textView!!.height
                if (!recyclerView.canScrollVertically(1)) {
                    val position = adapter!!.getLetterPosition("Z")
                    if (position != -1) {
                        manager!!.scrollToPositionWithOffset(position, 0)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (selectedCountries.size <= 1) {
                    return
                }
                val firstItemPosition = manager?.findFirstVisibleItemPosition() ?: 0
                //获取最顶部的item
                val firstView = manager?.findViewByPosition(firstItemPosition)
                val secondItemPosition = manager?.findFirstVisibleItemPosition() ?: (0 + 1)
                // 获取第二个显示的item
                val secondView = manager?.findViewByPosition(secondItemPosition)
                val firstBean = adapter?.getBaseSell(firstItemPosition)
                val secondBean = adapter?.getBaseSell(secondItemPosition)
                if (dy > 0) { // 上滑
                    if (dy < 200) {
                        if (secondBean is BaseCountryOrRegionSpellAdapter.LetterEntity) {
                            val secondViewY = secondView!!.y
                            if (secondViewY < suspendBarHeight) {
                                val setY = secondViewY - suspendBarHeight
                                textView!!.y = setY
                            }
                        }
                    } else {
                        textView!!.y = 0f
                    }
                    if (firstBean is BaseCountryOrRegionSpellAdapter.LetterEntity) {
                        if (null != firstView && firstView.y <= 0) {
                            textView!!.y = 0f
                        }
                    }
                } else { // 下拉
                    if (secondBean is BaseCountryOrRegionSpellAdapter.LetterEntity) {
                        val secondViewY = secondView!!.y
                        if (secondViewY < suspendBarHeight) {
                            textView!!.y = secondViewY - suspendBarHeight
                        } else {
                            textView!!.y = 0f
                        }
                    } else {
                        textView!!.y = 0f
                    }
                }
                var slideText = ""
                if (firstBean is BaseCountryOrRegionSpellAdapter.LetterEntity) {
                    slideText = firstBean.getSpell().toUpperCase()
                } else if (firstBean?.getPriority() == 0) {
                    slideText = firstBean.getIndexName().toUpperCase()
                }
                textView?.text = slideText
            }
        })
        recyclerView.setLayoutManager(manager)
        recyclerView.setAdapter(adapter)
        val side = buildSideBar()
        side.setOnLetterChangeListener(object : CountryOrRegionSideBar.OnLetterChangeListener {
            override fun onLetterChange(letter: String) {
                var position = adapter?.getLetterPosition(letter) ?: 0
                if (letter == "#") {
                    position = 0
                }
                if (position != -1) {
                    manager?.scrollToPositionWithOffset(position, 0)
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}