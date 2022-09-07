package com.gskose.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gskose.R
import kotlinx.android.synthetic.main.item_auto_complete.view.*

class SearchAdapter(
    var context: Context,
    private var response: MutableList<String>,
    private var searchInterface: SearchInterface?
) : RecyclerView.Adapter<SearchAdapter.ViewSearch>() {
    class ViewSearch(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSearch {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_auto_complete, parent, false)
        return ViewSearch(view)
    }

    override fun onBindViewHolder(holder: ViewSearch, position: Int) {
        holder.itemView.txtKeyword.text = response[position]
        holder.itemView.setOnClickListener {
            searchInterface!!.selectKayWord(
                response[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return response.size
    }

    interface SearchInterface {
        fun selectKayWord(value: String?)
    }
}