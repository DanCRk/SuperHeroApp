package com.dann.superheroapp.data.network

import com.dann.superheroapp.data.model.MarvelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarvelService @Inject constructor(private val apiClient: MarvelAPIClient) {

    suspend fun getHeroes(url:String):MarvelResponse{
        return withContext(Dispatchers.IO){
            val response = apiClient.getHeroes(url)
            response.body()!!
        }
    }

}