package com.nginapps.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nginapps.core.R
import com.nginapps.core.model.Destination
import kotlinx.android.synthetic.main.item_destination.view.*
import java.util.*

class DestinationAdapter : RecyclerView.Adapter<DestinationAdapter.ListViewHolder>() {

    private var listData = ArrayList<Destination>()
    var onItemClick: ((Destination) -> Unit)? = null

    fun setData(newListData: List<Destination>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_destination, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Destination) {
            with(itemView) {
                imageView.load(data.photo)
                titleTv.text = data.title
                descTv.text = data.desc
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}