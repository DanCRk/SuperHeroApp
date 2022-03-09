package com.dann.superheroapp.data.network

import com.dann.superheroapp.data.model.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MarvelAPIClient {
    @GET
    suspend fun getHeroes(@Url url:String):Response<MarvelResponse>
}