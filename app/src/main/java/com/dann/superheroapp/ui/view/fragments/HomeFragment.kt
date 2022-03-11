package com.dann.superheroapp.ui.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dann.superheroapp.data.adapters.reciclerview.RecyclerAdapter
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.databinding.FragmentHomeBinding
import com.dann.superheroapp.ui.view.HeroActivity
import com.dann.superheroapp.ui.viewmodel.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeroesViewModel by viewModels()

    private lateinit var adapter: RecyclerAdapter
    private var offset = 0
    private val heroList = mutableListOf<Hero>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setReciclerView()

        if (heroList.isNullOrEmpty()){
            viewModel.onCreate()
        }

        viewModel.heroesViewModel.observe(viewLifecycleOwner) { heroesList ->
            for (hero in heroesList) {
                if (!heroList.contains(hero)) {
                    heroList.add(hero)
                }
            }
            refrescarRecyclerView()
        }

        viewModel.progressBar.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.loadingMoreHeroes.observe(viewLifecycleOwner) {
            binding.progressBarMore.isVisible = it
        }

        viewModel.noMoreConsults.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        cargarMas()
    }

    private fun cargarMas() {
        binding.rvHeroes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    offset += 20
                    viewModel.loadMore(offset)
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setReciclerView() {
        adapter = RecyclerAdapter(heroList, requireActivity().applicationContext) { onItemSelectedListener(it) }
        binding.rvHeroes.layoutManager = GridLayoutManager(context, 3)
        binding.rvHeroes.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun onItemSelectedListener(hero: Hero) {
        val intent = Intent(activity, HeroActivity::class.java)
        intent.putExtra("HERO", hero)
        activity?.startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refrescarRecyclerView() {
        if (!heroList.isNullOrEmpty()) {
            adapter.notifyDataSetChanged()
        } else {
            showError()
        }
    }

    private fun showError() {
        Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

}