package com.dann.superheroapp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.dann.superheroapp.R
import com.dann.superheroapp.data.adapters.viewpager.SliderAdapter
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.data.model.Images
import com.dann.superheroapp.databinding.ActivityHeroBinding
import com.dann.superheroapp.ui.viewmodel.HeroDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HeroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeroBinding
    private var heroId: Int = 0
    private lateinit var heroName: String
    private lateinit var heroDescription: String
    private lateinit var heroUrl: String
    private lateinit var hero: Hero
    private lateinit var adapter: SliderAdapter

    private val comicImageList = mutableListOf<Images>()

    private val heroViewModel: HeroDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getHerolInformation()

        setUpViewPager()

        setInformationInViews()

        goToDetails()

        heroViewModel.comicsViewModel.observe(this) {
            val url =
                "${it.path}/standard_fantastic.${it.extension}".replace(
                    "http",
                    "https"
                )
            val comicImage = Images(url, "", it.name)
            comicImageList.add(comicImage)
            binding.noComics.isVisible = false
            refresh()
        }
    }

    private fun goToDetails() {
        binding.fav.setOnClickListener {
            val uri = hero.urls.first().url
            if (!uri.equals(null)) {
                val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay pagina web en este momento!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setUpViewPager() {
        adapter = SliderAdapter(comicImageList, this@HeroActivity) { onItemListener(it) }
        binding.sliderComics.adapter = adapter
        binding.sliderComics.clipToPadding = false
        binding.sliderComics.clipChildren = false
        binding.sliderComics.offscreenPageLimit = 3
        binding.sliderComics.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePagerTransformer = CompositePageTransformer()
        compositePagerTransformer.addTransformer(MarginPageTransformer(30))
        compositePagerTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.25f
        }
        binding.sliderComics.setPageTransformer(compositePagerTransformer)
    }

    private fun onItemListener(name: String) {
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
    }

    private fun getHerolInformation() {
        hero = intent.getSerializableExtra("HERO") as Hero
        heroId = hero.id
        heroName = hero.name
        heroDescription = hero.description
        heroUrl = hero.thumbnail.path
        for (comic in hero.comics.items) {
            val uri =
                "${comic.resourceURI}?ts=1&apikey=6297766f747707d8733f11ec846ba65c&hash=9cae473e2dbf1e5c71aed13ab674a620".replace(
                    "http",
                    "https"
                )
            heroViewModel.onCreate(uri)
        }
    }

    private fun setInformationInViews() {
        if (heroUrl.split("/").last() != "image_not_available") {
            val uri = "$heroUrl/standard_fantastic.${hero.thumbnail.extension}".replace(
                "http",
                "https"
            )
            Glide.with(this).load(uri).centerCrop().into(binding.imgHeroDetail)
        } else {
            binding.imgHeroDetail.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_person
                )
            )
        }
        binding.collpTb.title = heroName
        if (heroDescription.isNotEmpty()) {
            binding.description.text = heroDescription
        }
        binding.progressBar.isVisible = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        if (!comicImageList.isNullOrEmpty()) {
            adapter.notifyDataSetChanged()
        }
    }
}