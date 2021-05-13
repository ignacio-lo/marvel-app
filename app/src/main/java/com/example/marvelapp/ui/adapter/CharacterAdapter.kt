package com.example.marvelapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.R
import com.example.marvelapp.data.model.character.Character
import com.example.marvelapp.ui.adapter.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterAdapter(private val context: Context, private val list: ArrayList<Character>, private val listener: CharacterActions) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface CharacterActions{
        fun onCharacterClicked(id: Int)
    }

    fun addCharacters(data: ArrayList<Character>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is CharacterViewHolder -> holder.bind(list[position], position)
        }
    }

    inner class CharacterViewHolder(itemView: View) : BaseViewHolder<Character>(itemView) {
        override fun bind(item: Character, position: Int) {

            if (item.thumbnail.path == context.resources.getString(R.string.img_not_available)) {
                Glide.with(context)
                    .load(item.thumbnail.path + extensionJpg)
                    .fitCenter()
                    .error(R.drawable.img_not_available)
                    .into(itemView.cv_image_charac)
            } else {
                Glide.with(context)
                    .load(item.thumbnail.path + extensionImg)
                    .centerCrop()
                    .error(R.drawable.img_not_available)
                    .into(itemView.cv_image_charac)
            }

            itemView.tv_charac_name.text = item.name

            itemView.tv_charac_desc.text = if (item.description == "") context.resources.getString(R.string.no_description) else item.description

            itemView.setOnClickListener {
                listener.onCharacterClicked(item.id)
            }
        }
    }

    companion object {
        const val extensionImg = "/standard_fantastic.jpg"
        const val extensionJpg = ".jpg"
    }
}