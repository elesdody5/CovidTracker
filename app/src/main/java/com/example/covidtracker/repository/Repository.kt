package com.example.covidtracker.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.database.CovidDao
import com.example.covidtracker.database.LocalCountryHistory
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.domain.asCountryModel
import com.example.covidtracker.domain.asCountryModelList
import com.example.covidtracker.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber

class Repository(
    private val remoteDataSource: DiseaseAPI,
    private val localDataSource: CovidDao
) : RepositoryContract {
    override var countryList: LiveData<List<CountryModel>> =
        Transformations.map(localDataSource.getAllCountry()) {
            it.asCountryModelList()
        }
    override val totalWorld: LiveData<CountryModel> =
        Transformations.map(localDataSource.getTotalWorld()) {
            it?.asCountryModel()
        }

    override suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            try {
                val countries = remoteDataSource.getCountriesData()
                val totalWorld = remoteDataSource.getGeneralInfo()
                localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
                localDataSource.insertCountry(totalWorld.asCountryEntity())
            } catch (e: Exception) {
                Timber.w(e)
            }

        }
    }

    private fun shouldNotify(
        countries: List<CountryData>,
        subscribed: CountryEntitySubscribed
    ): Boolean {
        val newCases = countries.filter {
            it.country.equals(subscribed.country)
        }.first().cases
        val notify = subscribed.totalCases != newCases
        localDataSource.updateCountrySubscriped(
            CountryEntitySubscribed(
                subscribed.country,
                newCases,
                subscribed.countryThumb
            )
        )
        return notify
    }

    override suspend fun getCountryData(countryName: String): LiveData<CountryModel> {
        return Transformations.map(localDataSource.getCountryByName(countryName)) {
            it.asCountryModel()
        }
    }

    override suspend fun notifyCountries(): List<String> {
        val listOfCoutriesToNotify = arrayListOf<String>()
        withContext(Dispatchers.IO) {
            val countries = remoteDataSource.getCountriesData()
            val subscribedList = getAllCoutrySubscribed()
            subscribedList.forEach {
                if (shouldNotify(countries, it)) {
                    listOfCoutriesToNotify.add(it.country)
                }
            }
            localDataSource.insertCountry(* countries.asLocalCountryList().toTypedArray())
        }
        return listOfCoutriesToNotify
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        return localDataSource.updateCountrySubscriped(countryEntitySubscribed)
    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.insertContrySubscribed(countryEntitySubscribed)
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        localDataSource.deleteCountrySubscribed(countryEntitySubscribed)
    }

    override suspend fun getAllCoutrySubscribed(): List<CountryEntitySubscribed> {
        var listOfCountry: List<CountryEntitySubscribed> = listOf()
        runBlocking {
            withContext(Dispatchers.IO) {
                listOfCountry = localDataSource.getAllCoutrySubscribed()
            }
        }
        return listOfCountry
    }

    override fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>> {
        return localDataSource.getAllCoutrySubscribedLiveData()
    }

    override fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        return localDataSource.getCountrySubscribed(countryName)
    }


    override fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        runBlocking {
            withContext(Dispatchers.IO) {
                try {

                    val countryHistory = remoteDataSource.getCountryHistory(countryName)
                    localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
                } catch (e: Exception) {
                    Timber.w(e)
                }
            }
        }

        return localDataSource.geCountryHistory(countryName)
    }

    //For test Repository not form production
    @VisibleForTesting
    override suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory> {
        val countryHistory = remoteDataSource.getCountryHistory(name)
        localDataSource.insertCountryHistory(countryHistory.asLocalCountryHistory())
        return localDataSource.getAllHistory()
    }

}