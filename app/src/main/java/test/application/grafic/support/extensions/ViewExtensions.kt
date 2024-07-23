package test.application.grafic.support.extensions

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import test.application.grafic.R

fun Context.columnX(x: String? = null): TextView {
    val textView: TextView?
    textView = TextView(this, null, 0, R.style.tableColumnsStyle)
    textView.apply {
        setTypeface(typeface, Typeface.BOLD)
        setText(x)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    }
    val greyColor = ContextCompat.getColor(
        this,
        R.color.grey
    )
    textView.setTextColor(greyColor)
    return textView
}

fun Context.columnY(y: String? = null): TextView {
    val textView: TextView?
    textView = TextView(this, null, 0, R.style.tableColumnsStyle)
    textView.apply {
        setTypeface(typeface, Typeface.BOLD)
        setText(y)
        textAlignment = View.TEXT_ALIGNMENT_TEXT_START
    }
    val greyColor = ContextCompat.getColor(
        this,
        R.color.grey
    )
    textView.setTextColor(greyColor)
    return textView
}