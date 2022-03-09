package com.dann.superheroapp.data

import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.data.network.MarvelService
import javax.inject.Inject

class Repository @Inject constructor(private val apiClient: MarvelService) {

    suspend fun getHeroes(url:String):List<Hero>{
        val response = apiClient.getHeroes(url)
        return response.data.results
    }

}