package com.dann.superheroapp.data.adapters.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dann.superheroapp.R
import com.dann.superheroapp.data.model.Images

class SliderAdapter(
    private val sliderItem: List<Images>,
    private val context: Context,
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<SliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder =
        SliderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.slider_item_container, parent, false)
        )


    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(sliderItem[position],context,onClickListener)
    }

    override fun getItemCount(): Int = sliderItem.size

}