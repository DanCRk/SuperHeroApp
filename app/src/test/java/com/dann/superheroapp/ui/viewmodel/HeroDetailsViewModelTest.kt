package com.dann.superheroapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dann.superheroapp.data.model.Images
import com.dann.superheroapp.domain.GetComicImagesUseCase
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
class HeroDetailsViewModelTes{

    @RelaxedMockK
    private lateinit var getComicImagesUseCase: GetComicImagesUseCase

    private lateinit var heroesDetailsViewModel: HeroDetailsViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        heroesDetailsViewModel = HeroDetailsViewModel(getComicImagesUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the viewmodel is created at the firts time, get an Image and set the value`() = runBlockingTest {
        //Given
        val myImage = Images("web.com","jpg")
        coEvery { getComicImagesUseCase(any()) } returns myImage

        //When
        heroesDetailsViewModel.onCreate("any")

        //Then
        coVerify(exactly = 1) { getComicImagesUseCase(any()) }
        assert(myImage == heroesDetailsViewModel.comicsViewModel.value)
    }

    @Test
    fun `when the viewmodel is created at the firts time, if the url is empty do not post the value`() = runBlockingTest {
        //Given
        val myImage = Images("web.com","jpg")
        coEvery { getComicImagesUseCase(any()) } returns myImage

        //When
        heroesDetailsViewModel.onCreate("")

        //Then
        coVerify(exactly = 0) { getComicImagesUseCase(any()) }
        assert(null == heroesDetailsViewModel.comicsViewModel.value)
    }

    @Test
    fun `when the viewmodel is created at the firts time, if the response is an empty Image do not post the value`() = runBlockingTest {
        //Given
        val myImage = Images()
        coEvery { getComicImagesUseCase(any()) } returns myImage

        //When
        heroesDetailsViewModel.onCreate("any")

        //Then
        coVerify(exactly = 1) { getComicImagesUseCase(any()) }
        assert(null == heroesDetailsViewModel.comicsViewModel.value)
    }
}