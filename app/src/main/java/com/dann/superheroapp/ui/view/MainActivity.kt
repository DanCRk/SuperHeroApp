package com.dann.superheroapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dann.superheroapp.data.adapters.RecyclerAdapter
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.databinding.ActivityMainBinding
import com.dann.superheroapp.ui.viewmodel.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: HeroesViewModel by viewModels()

    private lateinit var adapter: RecyclerAdapter
    private var offset = 0
    private val heroList = mutableListOf<Hero>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReciclerView()

        viewModel.onCreate()

        viewModel.heroesViewModel.observe(this) { heroesList ->
            if (!heroesList.isNullOrEmpty()) {
                for (hero in heroesList) {
                    if (!heroList.contains(hero)) {
                        heroList.add(hero)
                        refrescarRecyclerView(heroList.size)
                    }
                }
            }
        }

        viewModel.progressBar.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.loadingMoreHeroes.observe(this){
            binding.progressBarMore.isVisible = it
        }

        cargarMas()
    }

    private fun cargarMas(){
        binding.rvHeroes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    offset +=20
                    viewModel.loadMore(offset)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setReciclerView() {
        adapter = RecyclerAdapter(heroList, this) { onItemSelectedListener(it) }
        binding.rvHeroes.layoutManager = GridLayoutManager(this,3)
        binding.rvHeroes.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun onItemSelectedListener(hero: Hero) {
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refrescarRecyclerView(last:Int){ if(!heroList.isNullOrEmpty()){ adapter.notifyItemInserted(last) }else{ showError() } }

    private fun showError(){ Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show() }

}