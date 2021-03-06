package com.example.covidtracker.database

import android.content.Context
import androidx.lifecycle.LiveData

class LocalDataSource(context: Context) : CovidDao {
    private val dao: CovidDao

    init {
        val dataBase = CovidDataBase.getInstance(context.applicationContext)
        dao = dataBase.covidDao
    }

    override fun insertCountry(vararg country: CountyEntity) {
        dao.insertCountry(* country)
    }

    override fun insertCountryHistory(countryHistory: LocalCountryHistory) {
        dao.insertCountryHistory(countryHistory)
    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        dao.insertContrySubscribed(countryEntitySubscribed)
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        dao.deleteCountrySubscribed(countryEntitySubscribed)
    }

    override fun getAllCoutrySubscribed(): List<CountryEntitySubscribed> {
        return dao.getAllCoutrySubscribed()
    }

    override fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>> {
        return dao.getAllCoutrySubscribedLiveData()
    }

    override fun getAllCountry(): LiveData<List<CountyEntity>> {
        return dao.getAllCountry()
    }

    override fun getTotalWorld(): LiveData<CountyEntity> {
        return dao.getTotalWorld()
    }

    override fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        return dao.getCountrySubscribed(countryName)
    }

    override fun geCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        return dao.geCountryHistory(countryName)
    }

    override fun getCountryByCases(): LiveData<List<CountyEntity>> {
        return dao.getCountryByCases()
    }

    override fun getCountryByDeath(): List<CountyEntity> {
        TODO()
    }

    override fun getCountryByRecovered(): List<CountyEntity> {
        TODO("not implemented")
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        return dao.updateCountrySubscriped(countryEntitySubscribed)
    }


    override fun getCountryByName(countryName: String): LiveData<CountyEntity> {
        return dao.getCountryByName(countryName)
    }

    override fun getAllHistory(): List<LocalCountryHistory> {
        return dao.getAllHistory()
    }


}