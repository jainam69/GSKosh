package com.gskose.activity.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.activity.search.SearchActivity
import com.gskose.adapter.SearchWordAdapter
import com.gskose.model.SearchWordModel
import com.gskose.repository.AppConstants
import com.gskose.utils.CommonUtils
import com.gskose.utils.MDToast
import com.gskose.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class MainActivity : BaseActivity(), AdapterView.OnItemClickListener {

    private var getSearchSuggestionsViewModel: MainViewModel? = null
    private var selectLanguage: Int = 1
    private var selectCriteria: Int = 1
    private var autoAdapter: ArrayAdapter<String>? = null

    companion object {
        fun launchActivity(activity: BaseActivity?) {
            if (activity != null) {
                val intent = Intent(activity, MainActivity::class.java)
                CommonUtils.fireIntentWithClearFlag(activity, intent, true)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*CommonUtils.hideStatusBar(this)*/
        setContentView(R.layout.activity_main)
        initView()
        actionListener()
    }

    private fun initView() {
        imgBack.visibility = View.GONE
        getSearchSuggestionsViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]
        rdbGujSan.setTextColor(resources.getColor(R.color.black_29))
        rdbSanGuj.setTextColor(resources.getColor(R.color.black_29))
        rdbExact.setTextColor(resources.getColor(R.color.black_29))
        rdbAnywhere.setTextColor(resources.getColor(R.color.black_29))
        rdbStartFrom.setTextColor(resources.getColor(R.color.black_29))
    }

    private fun actionListener() {
        rdbGujSan.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectLanguage = 1
            }
        }
        rdbSanGuj.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectLanguage = 2
            }
        }
        rdbExact.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectCriteria = 1
            }
        }
        rdbAnywhere.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectCriteria = 2
            }
        }
        rdbStartFrom.setOnCheckedChangeListener { _, b ->
            if (b) {
                selectCriteria = 3
            }
        }
        btnSearch.setOnClickListener {
            SearchActivity.launchActivity(
                this,
                selectLanguage.toString(),
                selectCriteria.toString()
            )
            //getSearchResultNew(editTextTextPersonName.text.toString())
        }
        editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchSuggestionsAPI(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
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
                selectLanguage.toString(),
                selectCriteria.toString()
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
            rvSearchWord.adapter = SearchWordAdapter(this, response)
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

    private fun searchSuggestionsAPI(str: String) {
        if (CommonUtils.getConnectivityStatus(this) == 0 && CommonUtils.getConnectivityStatusString(
                this
            ) == AppConstants.NOT_CONNECT
        ) {
            CommonUtils.showToast(
                this,
                getString(R.string.check_your_internet), MDToast.TYPE_INFO
            )
        } else {
            getSearchSuggestionsViewModel!!.getSearchSuggestions(
                this,
                str,
                selectLanguage.toString()
            ).observe(
                this
            ) {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { Response ->
                                searchSuggestionsResponse(Response)
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

    private fun searchSuggestionsResponse(strList: ArrayList<String>) {
        autoAdapter =
            ArrayAdapter<String>(this, R.layout.item_auto_complete, R.id.txtKeyword, strList)
        editTextTextPersonName.setAdapter(autoAdapter)
        editTextTextPersonName.onItemClickListener = this
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: String =
            p0!!.getItemAtPosition(p2)
                .toString()
        getSearchResultNew(item)
    }
}
