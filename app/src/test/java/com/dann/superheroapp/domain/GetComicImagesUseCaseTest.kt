package com.dann.superheroapp.domain

import com.dann.superheroapp.data.Repository
import com.dann.superheroapp.data.model.Hero
import com.dann.superheroapp.data.model.Images
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetComicImagesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: Repository

    lateinit var getComicImagesUseCase: GetComicImagesUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getComicImagesUseCase = GetComicImagesUseCase(repository)
    }

    @Test
    fun `when the path is equals to _ return Image()`() = runBlocking {
        //Given
        val images = Images()
        coEvery { repository.getImage(any()) } returns images

        //When
        val response = getComicImagesUseCase("")

        //Then
        coVerify(exactly = 1) { repository.getImage(any()) }
        assert(images == response)
    }

    @Test
    fun `when the path is not equals to _ return the image from api`() = runBlocking {
        //Given
        val images = Images("web.com", "jpg")
        coEvery { repository.getImage(any()) } returns images

        //When
        val response = getComicImagesUseCase("")

        //Then
        coVerify(exactly = 1) { repository.getImage(any()) }
        assert(images == response)
    }
}