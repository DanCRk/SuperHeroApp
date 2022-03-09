package com.dann.superheroapp.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dann.superheroapp.R
import com.dann.superheroapp.data.model.Hero

class RecyclerAdapter(
    private val heroesList: List<Hero>,
    private val context: Context,
    private val onClickListener: (Hero) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_hero, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(heroesList[position], context, onClickListener)
    }

    override fun getItemCount(): Int = heroesList.size

}