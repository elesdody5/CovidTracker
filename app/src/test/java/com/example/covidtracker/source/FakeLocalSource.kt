package com.example.covidtracker.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.database.CountyEntity
import com.example.covidtracker.database.CovidDao
import com.example.covidtracker.database.LocalCountryHistory

class FakeLocalSource(
    var countries: MutableList<CountyEntity>?,
    var history: MutableList<LocalCountryHistory>?
) : CovidDao {
    override fun insertCountry(vararg country: CountyEntity) {
        countries?.addAll(country)
    }

    override fun insertCountryHistory(countryHistory: LocalCountryHistory) {
        history?.add(countryHistory)
    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    override fun getAllCoutrySubscribed(): List<CountryEntitySubscribed> {
        TODO("Not yet implemented")
    }

    override fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>> {
        TODO("Not yet implemented")
    }

    override fun getAllCountry(): LiveData<List<CountyEntity>> {
        return MutableLiveData(countries)

    }

    override fun getTotalWorld(): LiveData<CountyEntity> {
        return MutableLiveData(countries?.firstOrNull { it.country == "total_world" })
    }

    override fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        TODO("Not yet implemented")
    }

    override fun geCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        TODO("Not yet implemented")
    }

    override fun getCountryByCases(): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")
    }

    override fun getCountryByDeath(): List<CountyEntity> {
        TODO("Not yet implemented")
    }

    override fun getCountryByRecovered(): List<CountyEntity> {
        TODO("Not yet implemented")
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    fun getOrderedCountry(filter: String): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")

    }

    fun getAllSubscribedCountries(isSubscribed: Boolean): LiveData<List<CountyEntity>> {
        TODO("Not yet implemented")
    }

    fun updateSubscribedCountry(countryName: String, isSubscribed: Boolean) {
        TODO("Not yet implemented")
    }


    override fun getCountryByName(countryName: String): LiveData<CountyEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllHistory(): List<LocalCountryHistory> {
        history?.also { return it }
        return emptyList()
    }
}