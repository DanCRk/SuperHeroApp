package com.dann.superheroapp.data.adapters

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.databinding.ItemHeroBinding

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHeroBinding.bind(view)

    fun bind(hero: Hero, context: Context,onClickListener: (Hero) -> Unit) {
        val url = "${hero.thumbnail.path}/standard_xlarge.${hero.thumbnail.extension}"
        binding.nameHero.text = hero.name
        if (hero.thumbnail.path.split("/").last() != "image_not_available") {
            Glide
                .with(context)
                .load(url.replace("http","https"))
                .centerCrop()
                .into(binding.imageHero)
        }

        binding.card.setOnClickListener {
            onClickListener(hero)
        }

    }
}
