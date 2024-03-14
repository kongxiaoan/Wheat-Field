package com.kpa.localeflexpicker.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.kpa.i18.I18nProvider
import com.kpa.localeflexpicker.R
import kotlin.math.ceil

/**
 *
 * @author: kpa
 * @date: 2024/3/14
 * @description:
 */
class CountryOrRegionSideBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var letterColor = 0
    private var selectColor = 0
    private var selectBgColor = 0
    private var letterSize = 0
    private var paint: Paint? = null
    private var bgPaint: Paint? = null
    private var textHeight = 0f
    private val indexes = mutableListOf<String>()
    private var cellWidth = 0
    private var cellHeight = 0
    private var currentIndex = -1
    private var onLetterChangeListener: OnLetterChangeListener? = null
    private var selectIcon: Drawable? = null
    private var unselectIcon: Drawable? = null
    private var isFirstShowIcon: Boolean = false

    init {
        val typedArray = context?.obtainStyledAttributes(
            attrs,
            R.styleable.CountryOrRegionSideBar,
            defStyleAttr,
            0
        )
        if (typedArray != null) {
            letterColor =
                typedArray.getColor(R.styleable.CountryOrRegionSideBar_letterColor, Color.BLACK)
            selectColor =
                typedArray.getColor(R.styleable.CountryOrRegionSideBar_selectColor, Color.CYAN)
            selectBgColor =
                typedArray.getColor(R.styleable.CountryOrRegionSideBar_selectBgColor, Color.CYAN)
            letterSize =
                typedArray.getDimensionPixelSize(R.styleable.CountryOrRegionSideBar_letterSize, 24)
            isFirstShowIcon =
                typedArray.getBoolean(R.styleable.CountryOrRegionSideBar_isFirstShowIcon, false)
            selectIcon = typedArray.getDrawable(R.styleable.CountryOrRegionSideBar_selectedIcon)
            unselectIcon = typedArray.getDrawable(R.styleable.CountryOrRegionSideBar_unselectedIcon)
            typedArray.recycle()
        }
        paint = Paint()
        bgPaint = Paint()
        paint?.setAntiAlias(true)
        bgPaint?.isAntiAlias = true
        val fontMetrics = paint?.getFontMetrics()
        if (fontMetrics != null) {
            textHeight =
                ceil((fontMetrics.descent - fontMetrics.ascent).toDouble()).toFloat()
        }
        indexes.addAll(I18nProvider.i18nApi.getAlphabeticIndexByLocale())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cellWidth = measuredWidth
        cellHeight = measuredHeight / indexes.size
    }

    fun setOnLetterChangeListener(onLetterChangeListener: OnLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener
    }


    interface OnLetterChangeListener {
        fun onLetterChange(letter: String)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint?.textSize = letterSize.toFloat()
        for (i in indexes.indices) {
            val letter = indexes[i]
            val textWidth = paint!!.measureText(letter)
            val x = (cellWidth - textWidth) * 0.5f
            val y = (cellHeight + textHeight) * 0.5f + cellHeight * i
            if (i == currentIndex) {
                bgPaint?.setColor(selectBgColor)
                val radius = cellHeight * 0.5f
                var cy = y - textHeight * 0.6f
                if (i == 0) {
                    cy = y - textHeight * 0.5f
                }
                canvas.drawCircle(x + textWidth * 0.5f, cy, radius, bgPaint!!)
            }
            if (i == 0 && letter == "#" && isFirstShowIcon) {
                var icon: Drawable? = null
                if (selectIcon == null || unselectIcon == null) {
                    throw NullPointerException("isFirstShowIcon 是true 是 selectIcon和unselectIcon 不能为空")
                }
                icon = if (i == currentIndex) {
                    selectIcon
                } else {
                    unselectIcon
                }
                val iconWidth = icon!!.intrinsicWidth
                val iconHeight = icon.intrinsicHeight
                val xIcon = (cellWidth - iconWidth) / 2
                val yIcon = (cellHeight - iconHeight) / 2
                icon.setBounds(xIcon, yIcon, xIcon + iconWidth, yIcon + iconHeight)
                icon.draw(canvas)
            } else {
                if (i == currentIndex) {
                    paint!!.setColor(selectColor)
                } else {
                    paint!!.setColor(letterColor)
                }
                canvas.drawText(letter, x, y, paint!!)
            }
        }
    }


    /**
     * 处理 按下 移动 手指抬起
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val downY = event.y.toInt()
                //获取当前索引
                currentIndex = downY / cellHeight
                if (currentIndex < 0 || currentIndex > indexes.size - 1) {
                } else {
                    onLetterChangeListener?.onLetterChange(indexes[currentIndex])
                }
                //重新绘制
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                //手动刷新
                invalidate()
            }
        }
        // 为了 能够接受  move+up事件
        return true
    }

    fun selectCommonRegionLetter(letter: String) {
        val index = indexes.indexOf(letter)
        currentIndex = index
        invalidate()
    }

    fun refreshSideBar(indexs: MutableList<String>) {
        indexes.clear()
        indexes.addAll(indexs)
        invalidate()
    }

}