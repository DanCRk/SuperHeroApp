package com.dann.superheroapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.domain.GetAllHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val getAllHeroes: GetAllHeroesUseCase
) : ViewModel() {
    val heroesViewModel = MutableLiveData<List<Hero>>()
    val progressBar = MutableLiveData<Boolean>()
    val loadingMoreHeroes = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            progressBar.postValue(true)
            val result =
                getAllHeroes("v1/public/characters?ts=1&limit=20&offset=0&apikey=6297766f747707d8733f11ec846ba65c&hash=9cae473e2dbf1e5c71aed13ab674a620")
            if (!result.isNullOrEmpty()) {
                heroesViewModel.postValue(result)
            }
            progressBar.postValue(false)
        }
    }

    fun loadMore(offset: Int) {
        viewModelScope.launch {
            loadingMoreHeroes.postValue(true)
            val result =
                getAllHeroes("v1/public/characters?ts=1&limit=20&offset=${offset}&apikey=6297766f747707d8733f11ec846ba65c&hash=9cae473e2dbf1e5c71aed13ab674a620")
            if (!result.isNullOrEmpty()) {
                heroesViewModel.postValue(result)
            }
            loadingMoreHeroes.postValue(false)
        }
    }

}