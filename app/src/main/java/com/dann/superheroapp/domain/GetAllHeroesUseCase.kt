package com.dann.superheroapp.domain

import com.dann.superheroapp.data.Repository
import com.dann.superheroapp.data.model.Hero
import javax.inject.Inject

class GetAllHeroesUseCase @Inject constructor(private val repository: Repository){

    suspend operator fun invoke(url:String):List<Hero>{
        val heroes = repository.getHeroes(url)
        return if (!heroes.isNullOrEmpty()){
            heroes
        }else{
            emptyList()
        }
    }

}