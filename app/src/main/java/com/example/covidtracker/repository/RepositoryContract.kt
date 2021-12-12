package com.example.covidtracker.repository

import androidx.lifecycle.LiveData
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.database.LocalCountryHistory
import com.example.covidtracker.domain.CountryModel

interface RepositoryContract {
    val countryList: LiveData<List<CountryModel>>
    val totalWorld: LiveData<CountryModel>
    suspend fun refreshCountries()
    fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory>
    suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory>
    fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed)
    fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed)
    suspend fun getAllCoutrySubscribed(): List<CountryEntitySubscribed>
    fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>>
    fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed>
    suspend fun getCountryData(countryName: String): LiveData<CountryModel>?
    suspend fun notifyCountries(): List<String>
    fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed)
}