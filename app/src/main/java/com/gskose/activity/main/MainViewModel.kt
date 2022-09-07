package com.gskose.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gailgas.sahajdra.repository.RetrofitService
import com.gskose.activity.base.BaseActivity
import com.gskose.model.SearchWordModel
import com.gskose.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    fun getSearchSuggestions(
        baseActivity: BaseActivity,
        searchCharacter: String,
        lang: String,
    ) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success(
                        getSearchSuggestionsData(
                            baseActivity,
                            searchCharacter,
                            lang
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        msg = e.message ?: "Error Occurred! in GetSelfMeterReadingDetails"
                    )
                )
            }
        }

    private suspend fun getSearchSuggestionsData(
        baseActivity: BaseActivity,
        searchCharacter: String,
        lang: String
    ): ArrayList<String> {
        return withContext(Dispatchers.IO) {
            RetrofitService.getServiceD()
                .searchSuggestions(searchCharacter, lang)
        }
    }

    fun getSearchResultNew(
        baseActivity: BaseActivity,
        searchCharacter: String,
        lang: String,
        st: String
    ) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(
                    Resource.success(
                        getSearchResultNewData(
                            baseActivity,
                            searchCharacter,
                            lang,
                            st
                        )
                    )
                )
            } catch (e: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        msg = e.message ?: "Error Occurred! in GetSelfMeterReadingDetails"
                    )
                )
            }
        }

    private suspend fun getSearchResultNewData(
        baseActivity: BaseActivity,
        searchCharacter: String,
        lang: String,
        st: String
    ): ArrayList<SearchWordModel> {
        return withContext(Dispatchers.IO) {
            RetrofitService.getServiceD()
                .searchResultNew(searchCharacter, lang, st)
        }
    }
}