package com.example.covidtracker.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.covid_19tracker.getOrAwaitValue
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.domain.asCountryModelList
import com.example.covidtracker.network.asLocalCountryList
import com.example.covidtracker.source.FakeTestRepository
import com.example.covidtracker.source.createMockCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    // Subject under test
    private lateinit var homeViewModel: HomeViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repository: FakeTestRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        repository = FakeTestRepository()
        repository.addTasks(
            *createMockCountry().asLocalCountryList().asCountryModelList().toTypedArray()
        )
        Dispatchers.setMain(mainThreadSurrogate)
        runBlockingTest {
            homeViewModel = HomeViewModel(repository)
        }
    }

    @Test
    fun getCountryList() {
        // When add New Country
        repository.addTasks(
            *createMockCountry().asLocalCountryList().asCountryModelList().toTypedArray()
        )
        // Then the new task event is triggered
        val value = homeViewModel.countryList.getOrAwaitValue()

        assertThat(value, `is`(repository.countryServiceData.values.toList()))
    }

    @Test
    fun getNavigateToCountryDetails() {
        // When adding a new Country
        homeViewModel.onCountryClicked(CountryModel("USA"))

        // Then the new task event is triggered
        val value = homeViewModel.navigateToCountryDetails.getOrAwaitValue()

        assertThat(value, not(nullValue()))

    }


    @Test
    fun onFilterChanged() {
        // when filter Data
        homeViewModel.onFilterChanged(1, true)
        val orderdList = homeViewModel.countryList.getOrAwaitValue()
        //That countryList is ordered by cases
        assertArrayEquals(orderdList.toTypedArray(), createOrderCountry().toTypedArray())


    }

    private fun createOrderCountry(): List<CountryModel> {
        return createMockCountry().asLocalCountryList().asCountryModelList()
            .sortedBy { countyEntity -> countyEntity.cases }
    }
}