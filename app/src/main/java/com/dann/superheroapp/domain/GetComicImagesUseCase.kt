package com.dann.superheroapp.domain

import com.dann.superheroapp.data.Repository
import com.dann.superheroapp.data.model.Images
import javax.inject.Inject

class GetComicImagesUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(url:String): Images {
        return repository.getImage(url)
    }

}