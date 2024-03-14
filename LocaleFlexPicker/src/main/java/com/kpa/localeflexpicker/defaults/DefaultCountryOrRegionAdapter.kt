package com.kpa.localeflexpicker.defaults

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kpa.localeflexpicker.R
import com.kpa.localeflexpicker.base.BaseCountryOrRegionSpellAdapter
import com.kpa.localeflexpicker.base.BaseSpell
import com.kpa.localeflexpicker.base.CountryOrRegionEntity

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
class DefaultCountryOrRegionAdapter(val activity: AppCompatActivity, entities: List<BaseSpell>) :
    BaseCountryOrRegionSpellAdapter<RecyclerView.ViewHolder>(entities, '#') {


    override fun onCreateLetterHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LetterHolder(
            activity.layoutInflater.inflate(R.layout.item_letter, parent, false)
        )
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return DefaultCountryRegionViewHolder(
            activity.layoutInflater
                .inflate(R.layout.item_country_region_large_padding, parent, false)
        )
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindHolder(holder: RecyclerView.ViewHolder, entity: BaseSpell, position: Int) {
        super.onBindHolder(holder, entity, position)
        val vh: DefaultCountryRegionViewHolder = holder as DefaultCountryRegionViewHolder
        val countryOrRegion: CountryOrRegionEntity = entity as CountryOrRegionEntity
        vh.tvName.setText(countryOrRegion.getName())
        vh.tvCode.setText("+" + countryOrRegion.getCode())
        holder.itemView.setOnClickListener { v: View? ->
            val data = Intent()
            data.putExtra("country", vh.tvCode.getText().toString())
            activity.setResult(Activity.RESULT_OK, data)
            activity.finish()
        }
    }

    override fun onBindLetterHolder(
        holder: RecyclerView.ViewHolder,
        entity: LetterEntity,
        position: Int
    ) {
        super.onBindLetterHolder(holder, entity, position)
        (holder as LetterHolder).textView?.text = entity.letter.toUpperCase()
    }
    class LetterHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        val textView: TextView?

        init {
            textView = itemView as TextView?
        }
    }

}