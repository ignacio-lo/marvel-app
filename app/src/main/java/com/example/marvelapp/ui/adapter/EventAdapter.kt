package com.example.marvelapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.data.model.comic.Comic
import com.example.marvelapp.data.model.event.Event
import com.example.marvelapp.ui.adapter.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.event_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(private val context: Context, private val list: ArrayList<Event>) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is EventViewHolder -> holder.bind(list[position], position)
        }
    }

    inner class EventViewHolder(itemView: View) : BaseViewHolder<Event>(itemView) {
        @SuppressLint("DefaultLocale")
        override fun bind(item: Event, position: Int) {
            Glide.with(context)
                .load( item.thumbnail.path + extensionImg)
                .centerCrop()
                .error(R.drawable.img_not_available)
                .into(itemView.image_event)

            itemView.tv_event_name.text = item.title

            val myDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(item.start)

            val cal = Calendar.getInstance()
            cal.time = myDate!!

            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())?.capitalize()
            val year = cal.get(Calendar.YEAR)

            var dateToShow = "$month $day $year"

            if (Locale.getDefault().displayLanguage == context.resources.getString(R.string.español_language)) {
                dateToShow = "$day de $month $year"
            }

            itemView.tv_event_desc.text = dateToShow

            itemView.img_expand.setOnClickListener{
                if (itemView.tv_comic_discuss.visibility == View.GONE) {
                    itemView.tv_comic_discuss.visibility = View.VISIBLE
                    itemView.rv_comics.visibility = View.VISIBLE
                    itemView.img_expand.setImageResource(R.drawable.ic_baseline_expand_less_48)
                } else {
                    itemView.tv_comic_discuss.visibility = View.GONE
                    itemView.rv_comics.visibility = View.GONE
                    itemView.img_expand.setImageResource(R.drawable.ic_baseline_expand_more_48)
                }
            }

            setComicAdapter(itemView, item.comics.comicList)
        }
    }

    private fun setComicAdapter(itemView: View, comics: ArrayList<Comic>) {
        itemView.rv_comics.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        itemView.rv_comics.layoutManager = LinearLayoutManager(context)
        itemView.rv_comics.setHasFixedSize(true)
        itemView.rv_comics.adapter = ComicAdapter(context, comics)
    }

    companion object {
        const val extensionImg = "/standard_large.jpg"
    }
}