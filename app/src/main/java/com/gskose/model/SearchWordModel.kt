package com.gskose.model

import com.google.gson.annotations.SerializedName

open class SearchWordModel {
    @SerializedName("SearchWord")
    var searchWord: String = ""
    @SerializedName("SearchWordResults")
    var searchWordResults: List<String> = arrayListOf()
}