package com.dann.superheroapp.domain

import com.dann.superheroapp.data.Repository
import com.dann.superheroapp.data.model.Hero
import javax.inject.Inject

class GetAllHeroesUseCase @Inject constructor(private val repository: Repository){

    suspend operator fun invoke(url:String):List<Hero>{
        return repository.getHeroes(url)
    }

}