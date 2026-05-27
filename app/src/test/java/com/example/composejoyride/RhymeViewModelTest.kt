package com.example.composejoyride

import com.example.composejoyride.data.repositories.RhymeRepository
import com.example.composejoyride.ui.viewModels.RhymeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RhymeViewModelTest {

    private lateinit var repository: RhymeRepository
    private lateinit var viewModel: RhymeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = RhymeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setInput updates input value`() {
        viewModel.setInput("берёза")
        assertEquals("берёза", viewModel.input.value)
    }

    @Test
    fun `findRhymes updates result on success`() = runTest {
        viewModel.setInput("дорога")
        val rhymes = listOf("недорога", "строга")
        coEvery { repository.getRhymes("дорога", 0) } returns rhymes

        viewModel.findRhymes(0)
        advanceUntilIdle()

        assertEquals(rhymes, viewModel.result.value)
        assertFalse(viewModel.hasError.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `findRhymes sets error on failure`() = runTest {
        viewModel.setInput("море")
        coEvery { repository.getRhymes("море", 1) } throws Exception("fail")

        viewModel.findRhymes(1)
        advanceUntilIdle()

        assertTrue(viewModel.hasError.value)
        assertEquals(emptyList<String>(), viewModel.result.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `findRhymes ignores blank input`() = runTest {
        viewModel.setInput("   ")
        viewModel.findRhymes(0)
        advanceUntilIdle()

        assertEquals(emptyList<String>(), viewModel.result.value)
        assertFalse(viewModel.isLoading.value)
    }
}
