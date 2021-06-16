package com.samplecode.giphyapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samplecode.giphyapplication.databinding.GifItemBinding
import com.samplecode.giphyapplication.models.Gif

class GifAdapter(private val listener: OnItemClickListener) : PagingDataAdapter<Gif, GifAdapter.GifViewHolder>(PHOTO_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = GifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val currentItem = getItem(position)

        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    inner class GifViewHolder(private val binding: GifItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if(item != null)
                    {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(gif: Gif){
            binding.apply {
                gifImage.minimumHeight = gif.images.downsized.height.toInt()
                Glide.with(itemView)
                    .load(gif.images.downsized.url)
                    .into(gifImage)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(gif: Gif)
    }

    companion object{
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Gif>(){
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem == newItem
            }
        }
    }
}