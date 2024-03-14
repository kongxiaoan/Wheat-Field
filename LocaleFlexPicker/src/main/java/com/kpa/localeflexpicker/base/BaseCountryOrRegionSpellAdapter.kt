package com.kpa.localeflexpicker.base

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kpa.localeflexpicker.base.BaseCountryOrRegionSpellAdapter.OnItemClickListener
import java.util.Collections
import java.util.Locale
import java.util.WeakHashMap

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
abstract class BaseCountryOrRegionSpellAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>,
    View.OnClickListener {
    companion object {
        const val TYPE_LETTER = 0
        const val TYPE_OTHER = 1
    }
    private val holders = WeakHashMap<View, VH>()
    val entityList: ArrayList<BaseSpell> = ArrayList<BaseSpell>()
    val letterList = HashSet<String>()
    val letterSet: HashSet<LetterEntity> =
        HashSet()
    private var listener: OnItemClickListener =
        OnItemClickListener { entity: BaseSpell?, position: Int -> }
    private var specialLetter = 0.toChar()

    constructor(entities: List<BaseSpell>, specialLetter: Char) {
        this.specialLetter = specialLetter
        update(false, entities)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(isSearMode: Boolean, entities: List<BaseSpell>) {
        entityList.clear()
        letterList.clear()
        entityList.addAll(entities)
        letterSet.clear()
        for (entity in entities) {
            val pinyin = entity.getSpell()
            if (!TextUtils.isEmpty(pinyin)) {
                var letter = pinyin!![0]
                if (!isLetter(letter)) {
                    letter = specialLetter
                }
                letterList.add((letter.toString() + "").uppercase(Locale.getDefault()))
                letterSet.add(LetterEntity(letter.toString() + ""))
            }
        }
        entityList.addAll(letterSet)
        Collections.sort(entityList, Comparator<BaseSpell> { o1: BaseSpell, o2: BaseSpell ->
            val pinyin: String =
                if (TextUtils.isEmpty(o1.getHeaderSpell())) o1.getSpell()
                    .toLowerCase() else o1.getHeaderSpell().toLowerCase()
            val anotherPinyin: String =
                if (TextUtils.isEmpty(o2.getHeaderSpell())) o2.getSpell()
                    .toLowerCase() else o2.getHeaderSpell().toLowerCase()
            val letter = pinyin[0]
            val otherLetter = anotherPinyin[0]
            if (isLetter(letter) && isLetter(otherLetter)) {
                return@Comparator pinyin.compareTo(anotherPinyin)
            } else if (isLetter(letter) && !isLetter(otherLetter)) {
                return@Comparator -1
            } else if (!isLetter(letter) && isLetter(otherLetter)) {
                return@Comparator 1
            } else {
                if (letter == specialLetter && o1 is LetterEntity) {
                    return@Comparator -1
                } else if (otherLetter == specialLetter && o2 is LetterEntity) {
                    return@Comparator 1
                } else {
                    return@Comparator pinyin.compareTo(anotherPinyin)
                }
            }
        })
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val entity = entityList[position]
        holders[holder.itemView] = holder
        holder.itemView.setOnClickListener(this)
        if (entity is LetterEntity) {
            onBindLetterHolder(holder, entity, position)
        } else {
            onBindHolder(holder, entity, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return if (viewType == TYPE_LETTER) onCreateLetterHolder(
            parent,
            viewType
        ) else onCreateHolder(parent, viewType)
    }

    abstract fun onCreateLetterHolder(parent: ViewGroup?, viewType: Int): VH

    abstract fun onCreateHolder(parent: ViewGroup?, viewType: Int): VH

    fun getLetterPosition(letter: String?): Int {
        val entity = LetterEntity(letter!!)
        return entityList.indexOf(entity)
    }

    fun getBaseSell(position: Int): BaseSpell {
        return entityList[position]
    }

    override fun getItemViewType(position: Int): Int {
        val entity = entityList[position]
        return if (entity is LetterEntity) TYPE_LETTER else getViewType(
            entity,
            position
        )
    }

    fun getViewType(entity: BaseSpell?, position: Int): Int {
        return TYPE_OTHER
    }

    override fun getItemCount(): Int {
        return entityList.size
    }

    open fun onBindLetterHolder(holder: VH, entity: LetterEntity, position: Int) {}

    open fun onBindHolder(holder: VH, entity: BaseSpell, position: Int) {}
    fun isLetter(letter: Char): Boolean {
        return 'a' <= letter && 'z' >= letter || 'A' <= letter && 'Z' >= letter
    }

    override fun onClick(v: View?) {
        val holder = holders[v] ?: return
        val position = holder.getAdapterPosition()
        val pyEntity = entityList[position]
        listener.onItemClick(pyEntity, position)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun interface OnItemClickListener {
        fun onItemClick(entity: BaseSpell?, position: Int)
    }


    class LetterEntity(val letter: String) : BaseSpell {
        override fun getSpell(): String {
            return letter.lowercase(Locale.getDefault())
        }

        override fun getIndexName(): String {
            return letter.uppercase(Locale.getDefault())
        }

        override fun getPriority(): Int {
            return 0
        }

        override fun isCommon(): Boolean {
            return false
        }

        override fun getHeaderSpell(): String {
            return letter.uppercase(Locale.getDefault())
        }

        override fun toString(): String {
            return "LetterEntity{" +
                    "letter='" + letter + '\'' +
                    '}'
        }

        override fun equals(o: Any?): Boolean {
            if (this === o) {
                return true
            }
            if (o == null || javaClass != o.javaClass) {
                return false
            }
            val that = o as LetterEntity
            return letter.lowercase(Locale.getDefault()) == that.letter.lowercase(Locale.getDefault())
        }

        override fun hashCode(): Int {
            return letter.lowercase(Locale.getDefault()).hashCode()
        }
    }

}