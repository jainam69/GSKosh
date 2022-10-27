package com.gskose.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.gskose.R
import com.gskose.activity.base.BaseActivity
import com.gskose.databinding.ItemSearchWordBinding
import com.gskose.model.SearchWordModel

class SearchWordAdapter(
    var context: BaseActivity,
    var searchWordModel: List<SearchWordModel>?,
    var selectLanguage: String = ""
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
        if (position % 2 != 0) {
            holder.binding.txtSearchWord.setBackgroundColor(context.resources.getColor(R.color.search_back_1))
            holder.binding.viewLine.setBackgroundColor(context.resources.getColor(R.color.search_line_1))
            holder.binding.txtSearchWordResults.setBackgroundColor(context.resources.getColor(R.color.search_back_2))
        } else {
            holder.binding.txtSearchWord.setBackgroundColor(context.resources.getColor(R.color.search_back_1))
            holder.binding.viewLine.setBackgroundColor(context.resources.getColor(R.color.search_line_2))
            holder.binding.txtSearchWordResults.setBackgroundColor(context.resources.getColor(R.color.search_back_2))
        }
        if (selectLanguage.toInt() == 1) {
            val typeface = ResourcesCompat.getFont(context, R.font.hindvadodara_bold)
            holder.binding.txtSearchWord.typeface = typeface
            val typeface1 = ResourcesCompat.getFont(context, R.font.adobedevanagari_bold)
            holder.binding.txtSearchWordResults.typeface = typeface1
        } else {
            val typeface = ResourcesCompat.getFont(context, R.font.adobedevanagari_bold)
            holder.binding.txtSearchWord.typeface = typeface
            val typeface1 = ResourcesCompat.getFont(context, R.font.hindvadodara_bold)
            holder.binding.txtSearchWordResults.typeface = typeface1
        }
    }

    override fun getItemCount(): Int {
        return searchWordModel?.size ?: 0
    }
}