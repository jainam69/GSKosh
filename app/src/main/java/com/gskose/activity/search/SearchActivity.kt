package com.gskose.activity.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.activity.main.MainActivity
import com.gskose.activity.main.MainViewModel
import com.gskose.adapter.SearchWordAdapter
import com.gskose.custom.SearchDialog
import com.gskose.model.SearchWordModel
import com.gskose.repository.AppConstants
import com.gskose.utils.CommonUtils
import com.gskose.utils.MDToast
import com.gskose.utils.Status
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_activity.*

@SuppressLint("SetTextI18n")
class SearchActivity : BaseActivity(), SearchDialog.SearchInterface {

    private var selectLanguage: String = ""
    private var selectCriteria: String = ""
    private var getSearchSuggestionsViewModel: MainViewModel? = null
    var title: String = "Search"

    companion object {
        fun launchActivity(
            activity: BaseActivity?,
            selectLanguage: String,
            selectCriteria: String
        ) {
            if (activity != null) {
                val intent = Intent(activity, SearchActivity::class.java)
                intent.putExtra("selectLanguage", selectLanguage)
                intent.putExtra("selectCriteria", selectCriteria)
                CommonUtils.fireIntent(activity, intent, true, isFinished = false)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        actionListener()
    }

    private fun initView() {
        getSearchSuggestionsViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]
        imgBack.visibility = View.VISIBLE
        toolbar_title.text = "Search"
        selectLanguage = intent.getStringExtra("selectLanguage")!!
        selectCriteria = intent.getStringExtra("selectCriteria")!!
        SearchDialog(this, selectLanguage, selectCriteria, this)
        title = if (selectLanguage.toInt() == 1) {
            resources.getString(R.string.guj_sans)
        } else {
            resources.getString(R.string.sans_guj)
        }
        title += if (selectCriteria.toInt() == 1) {
            " ( " + resources.getString(R.string.exact) + " ) "
        } else if (selectCriteria.toInt() == 2) {
            " ( " + resources.getString(R.string.anywhere) + " ) "
        } else {
            " ( " + resources.getString(R.string.starting_from) + " ) "
        }
        toolbar_title.text = title
    }

    private fun actionListener() {
        imgBack.setOnClickListener {
            MainActivity.launchActivity(this)
        }
        editTextTextPersonName.setOnClickListener {
            SearchDialog(
                this,
                selectLanguage,
                selectCriteria,
                this,
                editTextTextPersonName.text.toString()
            )
        }
        btnSearch.setOnClickListener {
            getSearchResultNew(editTextTextPersonName.text.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun select(keyword: String) {
        editTextTextPersonName.text = keyword
        if (keyword != "") {
            getSearchResultNew(keyword)
        }
    }

    private fun getSearchResultNew(editTextTextPersonName: String) {
        CommonUtils.hideKeyPad(this, btnSearch)
        showProgress(this)
        if (CommonUtils.getConnectivityStatus(this) == 0 && CommonUtils.getConnectivityStatusString(
                this
            ) == AppConstants.NOT_CONNECT
        ) {
            closeProgress()
            CommonUtils.showToast(
                this,
                getString(R.string.check_your_internet), MDToast.TYPE_INFO
            )
        } else if (editTextTextPersonName == "") {
            closeProgress()
            CommonUtils.showToast(
                this,
                getString(R.string.please_enter_word), MDToast.TYPE_INFO
            )
        } else {
            getSearchSuggestionsViewModel!!.getSearchResultNew(
                this,
                editTextTextPersonName,
                selectLanguage,
                selectCriteria
            ).observe(
                this
            ) {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { Response ->
                                searchResultNewResponse(Response)
                            }
                        }
                        Status.ERROR -> {
                            CommonUtils.showToast(this, resource.message!!, MDToast.TYPE_ERROR)
                            closeProgress()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            }
        }
    }

    private fun searchResultNewResponse(response: ArrayList<SearchWordModel>) {
        /*var gson = Gson()
        var type: Type = object : TypeToken<List<String?>?>() {}.type
        var contactList: List<String> = gson.fromJson(response.toString(), type)
        var a = response*/
        if (response.size > 0) {
            cardView.visibility = View.VISIBLE
            txtSearchResult.visibility = View.VISIBLE
            rvSearchWord.layoutManager = LinearLayoutManager(this)
            rvSearchWord.adapter = SearchWordAdapter(this, response,selectLanguage)
        } else {
            cardView.visibility = View.GONE
            txtSearchResult.visibility = View.GONE
            CommonUtils.showToast(
                this,
                resources.getString(R.string.search_result_not_found),
                MDToast.TYPE_ERROR
            )
        }
        closeProgress()
    }

}