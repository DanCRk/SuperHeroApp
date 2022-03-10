package com.dann.superheroapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.domain.GetAllHeroesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesViewModelTest {

    @RelaxedMockK
    private lateinit var getHeroesUseCase: GetAllHeroesUseCase

    private lateinit var heroesViewModel: HeroesViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        heroesViewModel = HeroesViewModel(getHeroesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the viewmodel is created at the firts time, get a list with 20 heroes, then set the value`() =
        runBlockingTest {
            //Given
            val myHeroesList = listOf(Hero("Spiderman"), Hero("IronMan"))
            coEvery { getHeroesUseCase(any()) } returns myHeroesList

            //when
            heroesViewModel.onCreate()

            //then
            coVerify (exactly = 1){ getHeroesUseCase(any()) }
            assert(myHeroesList == heroesViewModel.heroesViewModel.value)
            assert(false==heroesViewModel.progressBar.value)
        }

    @Test
    fun `when the viewmodel is called, get a list with the next 20 heroes, then set the value`() =
        runBlockingTest {
            //Given
            val myHeroesList = listOf(Hero("Spiderman"), Hero("IronMan"))
            coEvery { getHeroesUseCase(any()) } returns myHeroesList

            //when
            heroesViewModel.loadMore(20)

            //then
            coVerify (exactly = 1){ getHeroesUseCase(any()) }
            assert(myHeroesList == heroesViewModel.heroesViewModel.value)
            assert(false == heroesViewModel.loadingMoreHeroes.value)
        }
}