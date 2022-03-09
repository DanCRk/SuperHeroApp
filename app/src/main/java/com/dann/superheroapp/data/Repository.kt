package com.dann.superheroapp.data

import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.data.model.Images
import com.dann.superheroapp.data.network.MarvelService
import javax.inject.Inject

class Repository @Inject constructor(private val apiClient: MarvelService) {

    suspend fun getHeroes(url:String):List<Hero>{
        val response = apiClient.getHeroes(url)
        return response.data.results
    }

    suspend fun getImage(url:String):Images{
        val response = apiClient.getHeroes(url)
        return if (!response.data.results.first().images.isNullOrEmpty()){
            response.data.results.first().images.first().name = response.data.results.first().title
            response.data.results.first().images.first()
        }else{
            Images()
        }
    }

}