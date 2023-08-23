package com.laragh.autorepair.photo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laragh.autorepair.databinding.ItemPhotoBinding

class PhotoAdapter(
    dataSet: MutableList<CardViewItem>,
    private val onClickDelete: (Int) -> Unit,
    private val onEmptyImageViewClick: (Context) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = dataSet

    inner class PhotoViewHolder(
        private var binding: ItemPhotoBinding,
        private var context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardViewItem: CardViewItem, position: Int) {
            with(binding) {
                photo.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                photo.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

                Glide.with(context)
                    .load(cardViewItem.uri)
                    .into(photo)

                deleteButton.setOnClickListener {
                    onClickDelete(position)
                }
            }
        }
    }

    inner class EmptyViewHolder(
        private var binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.deleteButton.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == CardViewItem.EMPTY_TYPE) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPhotoBinding.inflate(inflater, parent, false)
            return EmptyViewHolder(binding)
        }

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        val context: Context = parent.context
        return PhotoViewHolder(binding, context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        when (item.type) {
            CardViewItem.EMPTY_TYPE -> {
                (holder as EmptyViewHolder).bind()
                holder.itemView.setOnClickListener {
                    onEmptyImageViewClick(it.context)
                }
            }
            CardViewItem.IMAGE_TYPE -> {
                (holder as PhotoViewHolder).bind(item, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].type == 0) return CardViewItem.EMPTY_TYPE
        return CardViewItem.IMAGE_TYPE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: MutableList<CardViewItem>) {
        list = items
        notifyDataSetChanged()
    }
}