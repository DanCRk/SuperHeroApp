package com.dann.superheroapp.data.adapters.viewpager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dann.superheroapp.data.model.Images
import com.dann.superheroapp.databinding.SliderItemContainerBinding

class SliderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val binding = SliderItemContainerBinding.bind(itemView)
    fun bind(comicImage: Images,onClickListener: (String) -> Unit){
        Glide.with(itemView.context)
            .load(comicImage.path)
            .into(binding.imageSlide)

        itemView.setOnClickListener {
            onClickListener(comicImage.name)
        }
    }
}