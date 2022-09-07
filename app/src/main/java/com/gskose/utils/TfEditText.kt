package com.gskose.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.gskose.R

/**
 * Created by Bhargavi Panchal on 11/01/2021
 */

class TfEditText : AppCompatEditText {
    private var _ctx: Context? = null
    private var isBold: Boolean = false

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            this._ctx = context
            init()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TfTextView, 0, 0)
            try {
                isBold = a.getBoolean(R.styleable.TfTextView_isBold, false)
            } finally {
                a.recycle()
            }
            this._ctx = context
            init()
        }
    }

    fun setBold(isBold: Boolean) {
        this.isBold = isBold
        if (isBold) {
            typeface = _ctx?.let { CommonUtils.getBoldFont(it) }
        } else {
            typeface = _ctx?.let { CommonUtils.getRegularFont(it) }
        }
    }

    private fun init() {
        try {
            if (isBold) {
                typeface = _ctx?.let { CommonUtils.getBoldFont(it) }
            } else {
                typeface = _ctx?.let { CommonUtils.getRegularFont(it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
