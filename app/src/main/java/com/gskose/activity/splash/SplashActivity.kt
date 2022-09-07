package com.gskose.activity.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.activity.main.MainActivity
import com.gskose.utils.CommonUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    private var mDelayHandler: Handler? = null
    private val Splash_Delay: Long = 2000 //5 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonUtils.hideStatusBar(this)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        initView()
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            MainActivity.launchActivity(this@SplashActivity)
            finishAffinity()
        }
    }

    private fun initView() {
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, Splash_Delay)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}