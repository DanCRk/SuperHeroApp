package com.dann.superheroapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dann.superheroapp.data.model.Images
import com.dann.superheroapp.domain.GetComicImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailsViewModel @Inject constructor(
    private val getComicImagesUseCase: GetComicImagesUseCase
) : ViewModel() {
    val comicsViewModel = MutableLiveData<Images>()

    fun onCreate(url: String) {
        viewModelScope.launch {
            if (url.isNotEmpty()) {
                val result = getComicImagesUseCase(url)
                if (result != Images()) {
                    comicsViewModel.postValue(result)
                }
            }
        }
    }
}