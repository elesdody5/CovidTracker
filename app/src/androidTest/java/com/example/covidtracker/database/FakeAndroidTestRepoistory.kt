package com.example.covidtracker.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.domain.asCountryModel
import com.example.covidtracker.domain.asCountryModelList
import com.example.covidtracker.network.CountryData
import com.example.covidtracker.network.CountryInfo
import com.example.covidtracker.repository.RepositoryContract
import kotlinx.coroutines.runBlocking
import java.util.*

class FakeAndroidTestRepoistory : RepositoryContract {
    override val countryList: LiveData<List<CountryModel>>
        get() = _countryList

    private var _countryList = MutableLiveData<List<CountryModel>>()
    var countryServiceData: LinkedHashMap<String, CountyEntity> = LinkedHashMap()
    var historyServiceData: LinkedHashMap<String, LocalCountryHistory> = LinkedHashMap()
    override val totalWorld: LiveData<CountryModel> =
        MutableLiveData(_countryList.value?.first { it.country == "total_world" })


    override suspend fun refreshCountries() {
        _countryList.postValue(countryServiceData.values.toList().asCountryModelList())
    }

    override fun getCountryHistory(countryName: String): LiveData<LocalCountryHistory> {
        return MutableLiveData(historyServiceData[countryName])
    }

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

    override suspend fun getCountryData(countryName: String): LiveData<CountryModel> {
        return MutableLiveData(countryServiceData[countryName]?.asCountryModel())
    }

    override suspend fun notifyCountries(): List<String> {
        TODO("Not yet implemented")
    }

    override fun updateCountrySubscriped(countryEntitySubscribed: CountryEntitySubscribed) {
        TODO("Not yet implemented")
    }

    fun orderList() {
        TODO("Not yet implemented")
    }

    fun orderList(order: String) {
        _countryList.value?.sortedBy { countryModel -> countryModel.cases }
    }

    fun addTasks(vararg countryList: CountyEntity) {
        for (country in countryList) {
            countryServiceData[country.country] = country
        }
        runBlocking { refreshCountries() }
    }
}

fun createMockCountry(): List<CountryData> {
    return listOf(
        CountryData(
            country = "USA",
            countryInfo = CountryInfo(
                _id = 4,
                iso2 = "AF",
                iso3 = "AFG",
                lat = 33.0,
                long = 65.0,
                flag = "https://corona.lmao.ninja/assets/img/flags/af.png"
            ), cases = 2171,
            todayCases = 232,
            deaths = 64,
            todayDeaths = 4,
            recovered = 260,
            active = 1847,
            critical = 7,
            casesPerOneMillion = 56,
            deathsPerOneMillion = 2.0,
            updated = 1588287677285

        ), CountryData(
            country = "Afghanistan",
            countryInfo = CountryInfo(
                _id = 5,
                iso2 = "AF",
                iso3 = "AFG",
                lat = 33.0,
                long = 65.0,
                flag = "https://corona.lmao.ninja/assets/img/flags/af.png"
            ), cases = 8000,
            todayCases = 232,
            deaths = 64,
            todayDeaths = 4,
            recovered = 260,
            active = 1847,
            critical = 7,
            casesPerOneMillion = 56,
            deathsPerOneMillion = 2.0,
            updated = 1588287677285

        )
    )

}
