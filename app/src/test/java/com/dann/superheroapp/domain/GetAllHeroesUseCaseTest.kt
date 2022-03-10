package com.dann.superheroapp.domain

import com.dann.superheroapp.data.Repository
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.data.model.Thumbnail
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test


class GetAllHeroesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    lateinit var getHeroesUseCase: GetAllHeroesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getHeroesUseCase = GetAllHeroesUseCase(repository)
    }

    @Test
    fun `when the api return the heroes list empty`() = runBlocking {
        //Given
        coEvery { repository.getHeroes("") } returns emptyList()

        //When
        val response = getHeroesUseCase("")

        //Then
        coVerify(exactly = 1) { repository.getHeroes(any()) }
        assert(response == emptyList<Hero>())
    }

    @Test
    fun `when the api return a list of heroes`() = runBlocking {
        //Given
        val myList = listOf(Hero())
        coEvery { repository.getHeroes(any()) } returns myList

        //When
        val response = getHeroesUseCase("")

        //Then
        coVerify(exactly = 1) { repository.getHeroes(any()) }
        assert(myList== response)
    }

}