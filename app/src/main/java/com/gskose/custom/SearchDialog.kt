package com.gskose.custom

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.activity.main.MainViewModel
import com.gskose.adapter.SearchAdapter
import com.gskose.repository.AppConstants
import com.gskose.utils.CommonUtils
import com.gskose.utils.MDToast
import com.gskose.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.dialog_search.editTextTextPersonName

@SuppressLint("InflateParams")
class SearchDialog(
    var context: BaseActivity,
    var selectLanguage: String,
    var onSelected: SearchInterface,
    var text: String = ""
) : Dialog(context), SearchAdapter.SearchInterface {
    private val scaleIn: Animation?
    private val scaleOut: Animation
    private val view: View?
    private var mAdapter: SearchAdapter? = null
    private var getSearchSuggestionsViewModel: MainViewModel? = null

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        view = LayoutInflater.from(context).inflate(R.layout.dialog_search, null)
        setContentView(view!!)
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        //lp.gravity = Gravity.BOTTOM
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.attributes = lp
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        )
        window!!.setFlags(
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        )

        this.setCanceledOnTouchOutside(false)
        this.setCancelable(true)

        scaleIn = AnimationUtils.loadAnimation(context, R.anim.scale_in_dialog)
        scaleOut = AnimationUtils.loadAnimation(context, R.anim.scale_out_dialog)
        show()

        setOnCancelListener { view.startAnimation(scaleOut) }
        scaleOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                onSelected.select("")
                dismiss()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        imgExit.setOnClickListener {
            dismiss()
        }
        initView()
        actionListner()
        if (text != "") {
            editTextTextPersonName.setText(text)
        }
    }

    private fun initView() {
        getSearchSuggestionsViewModel =
            ViewModelProvider(context)[MainViewModel::class.java]
    }

    private fun actionListner() {
        editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchSuggestionsAPI(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                searchSuggestionsAPI(p0.toString())
            }
        })
    }

    override fun onBackPressed() {
        view!!.startAnimation(scaleOut)
    }

    interface SearchInterface {
        fun select(keyword: String)
    }

    private fun searchSuggestionsAPI(str: String) {
        if (CommonUtils.getConnectivityStatus(context) == 0 && CommonUtils.getConnectivityStatusString(
                context
            ) == AppConstants.NOT_CONNECT
        ) {
            CommonUtils.showToast(
                context,
                context.getString(R.string.check_your_internet), MDToast.TYPE_INFO
            )
        } else {
            getSearchSuggestionsViewModel!!.getSearchSuggestions(
                context,
                str,
                selectLanguage
            ).observe(
                context
            ) {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { Response ->
                                searchSuggestionsResponse(Response)
                            }
                        }
                        Status.ERROR -> {
                            CommonUtils.showToast(context, resource.message!!, MDToast.TYPE_ERROR)
                            context.closeProgress()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            }
        }
    }

    private fun searchSuggestionsResponse(strList: ArrayList<String>) {
        mAdapter =
            SearchAdapter(context, strList, this)
        rvSearchKeyword.layoutManager = LinearLayoutManager(context)
        rvSearchKeyword.adapter = mAdapter
    }

    override fun selectKayWord(value: String?) {
        onSelected.select(value!!)
        dismiss()
    }

}
