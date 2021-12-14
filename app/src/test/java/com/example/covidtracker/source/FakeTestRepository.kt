package com.example.covidtracker.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.database.LocalCountryHistory
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.repository.RepositoryContract
import kotlinx.coroutines.runBlocking
import java.util.*

class FakeTestRepository : RepositoryContract {

    private var _countryList = MutableLiveData<List<CountryModel>>()
    var countryServiceData: LinkedHashMap<String, CountryModel> = LinkedHashMap()
    var historyServiceData: LinkedHashMap<String, LocalCountryHistory> = LinkedHashMap()

    override val countryList: LiveData<List<CountryModel>>
        get() = _countryList

    override suspend fun refreshCountries() {
        _countryList.value = countryServiceData.values.toList()
    }

    override fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        return MutableLiveData(historyServiceData[countryName])
    }

    override val totalWorld: LiveData<CountryModel> =
        MutableLiveData(_countryList.value?.first { it.country == "total_world" })


    override suspend fun getCountryHistroyList(name: String): List<LocalCountryHistory> {
        return historyServiceData.values.toList()
    }

    override fun insertContrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    override fun deleteCountrySubscribed(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCoutrySubscribed(): List<CountryEntitySubscribed> {
        TODO("Not yet implemented")
    }

    override fun getAllCoutrySubscribedLiveData(): LiveData<List<CountryEntitySubscribed>> {
        TODO("Not yet implemented")
    }

    override fun getCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        TODO("Not yet implemented")
    }

    override suspend fun getCountryData(countryName: String): LiveData<CountryModel>? {
        return MutableLiveData(countryServiceData[countryName])
    }

    override suspend fun notifyCountries(): List<String> {
        TODO("Not yet implemented")
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    fun orderList(order: String) {
        _countryList.value?.sortedBy { countryModel -> countryModel.cases }
    }

    fun addTasks(vararg countryList: CountryModel) {
        for (country in countryList) {
            countryServiceData[country.country] = country
        }
        runBlocking { refreshCountries() }
    }
}