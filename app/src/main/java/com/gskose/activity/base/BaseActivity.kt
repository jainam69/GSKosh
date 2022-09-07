package com.gskose.activity.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gskose.R
import java.util.*

open class BaseActivity : AppCompatActivity() {
    var selectedScreen = 0
    var fragmentBackStack: Stack<Fragment>? = null
    var showBackMessage: Boolean? = true
    var isGoBack: Boolean? = true
    var dialog: ProgressDialog? = null
    var customdialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        fragmentBackStack = Stack()
    }

    private fun doubleTapOnBackPress() {
        finish()
    }

    override fun onBackPressed() {
        doubleTapOnBackPress()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun showProgressDialog(isCancelable: Boolean) {
        try {
            if (dialog != null && dialog?.isShowing!!) {
                dialog!!.dismiss()
            }
            dialog = ProgressDialog(this)
            //dialog!!.setMessage(getString(R.string.msg_please_wait))
            dialog!!.setCancelable(isCancelable)
            dialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    fun showProgress(ctx: Context) {
        if (customdialog != null) {
            try {
                if (customdialog!!.isShowing) {
                    customdialog!!.dismiss()
                }
            } catch (e: Exception) {

            }
        }
        customdialog = Dialog(ctx, R.style.ActivityDialog)
        customdialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        customdialog!!.window?.setGravity(Gravity.CENTER)
        customdialog!!.setCancelable(false)
        customdialog!!.setContentView(R.layout.custom_progressbar)
        customdialog!!.show()
    }

    fun closeProgress() {
        try {
            if (customdialog != null && customdialog!!.isShowing) {
                customdialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }

}