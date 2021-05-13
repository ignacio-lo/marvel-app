package com.example.marvelapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.data.model.comic.Comic
import com.example.marvelapp.ui.adapter.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.comic_item.view.*
import java.util.*

class ComicAdapter(private val context: Context, private val list: ArrayList<Comic>) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ComicViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is ComicViewHolder -> holder.bind(list[position], position)
        }
    }

    inner class ComicViewHolder(itemView: View) : BaseViewHolder<Comic>(itemView) {
        override fun bind(item: Comic, position: Int) {
            itemView.tv_comic_name.text = item.name
        }
    }
}