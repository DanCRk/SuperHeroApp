package com.dann.superheroapp.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dann.superheroapp.R
import com.dann.superheroapp.data.adapters.reciclerview.RecyclerAdapter
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.databinding.ActivityMainBinding
import com.dann.superheroapp.ui.viewmodel.HeroesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavController()

    }

    private fun setNavController() {
        val btmNav = binding.bottomNav
        val navControler = Navigation.findNavController(this, R.id.frag_host)
        NavigationUI.setupWithNavController(btmNav, navControler)
    }



}