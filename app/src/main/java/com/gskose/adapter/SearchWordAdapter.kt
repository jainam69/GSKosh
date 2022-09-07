package com.gskose.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.databinding.ItemSearchWordBinding
import com.gskose.model.SearchWordModel

class SearchWordAdapter(
    var context: BaseActivity,
    var searchWordModel: List<SearchWordModel>?
) :
    RecyclerView.Adapter<SearchWordAdapter.MainSearchWordViewHolder>() {

    class MainSearchWordViewHolder(var binding: ItemSearchWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MainSearchWordViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_search_word,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MainSearchWordViewHolder, position: Int) {
        if (searchWordModel != null) {
            if (searchWordModel!!.isNotEmpty()) {
                holder.binding.searchWord = searchWordModel?.get(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return searchWordModel?.size ?: 0
    }
}