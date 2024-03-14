package com.kpa.localeflexpicker.defaults

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.kpa.localeflexpicker.R
import com.kpa.localeflexpicker.base.BaseCountryOrRegionActivity
import com.kpa.localeflexpicker.base.BaseCountryOrRegionSpellAdapter
import com.kpa.localeflexpicker.widget.CountryOrRegionSideBar

class DefaultCountryOrReginActivity : BaseCountryOrRegionActivity() {
    override fun buildRecyclerView(): RecyclerView {
        return findViewById(R.id.rv_pick)
    }

    override fun buildSuspendView(): TextView {
        return findViewById(R.id.suspendTv)
    }

    override fun buildSearchView(): EditText {
        return findViewById(R.id.searchET)
    }

    override fun buildAdapter(): BaseCountryOrRegionSpellAdapter<*> {
        return DefaultCountryOrRegionAdapter(this, allCountries)
    }

    override fun buildSideBar(): CountryOrRegionSideBar {
        return findViewById(R.id.side)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_country_or_regin)

    }
}