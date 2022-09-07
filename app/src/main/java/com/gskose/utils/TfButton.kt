package com.gskose.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * Created by Bhargavi Panchal on 11/01/2021
 */

class TfButton : AppCompatButton {
    private var _ctx: Context? = null

    constructor(context: Context) : super(context) {
        if (!isInEditMode) {
            this._ctx = context
            init()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode) {
            this._ctx = context
            init()
        }
    }

    private fun init() {
        try {
            typeface = CommonUtils.getBoldFont(_ctx!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
