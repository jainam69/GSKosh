package com.gskose.repository

import com.gskose.model.SearchWordModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.*

interface ApiInterface {
    @Headers(
        "Content-Type:application/json",
        "Authorization: Bearer "
    )
    @GET(AppConstants.SearchSuggestions)
    suspend fun searchSuggestions(
        @Query("Searchcharacter") SearchCharacter: String,
        @Query("Lang") Lang: String
    ): ArrayList<String>

    @Headers(
        "Content-Type:application/json",
        "Authorization: Bearer "
    )
    @GET(AppConstants.SearchResultNew)
    suspend fun searchResultNew(
        @Query("Searchword") SearchWord: String,
        @Query("Lang") Lang: String,
        @Query("st") st: String
    ): ArrayList<SearchWordModel>
}