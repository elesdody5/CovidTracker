package com.example.covidtracker.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covidtracker.CovidApplication
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.database.LocalCountryHistory
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.repository.RepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val covidRepo: RepositoryContract
    private var countryHistoryLiveData: LiveData<LocalCountryHistory>
    private var countryEntity: CountryModel? = null

    init {
        covidRepo = (application.applicationContext as CovidApplication).repository
        countryHistoryLiveData = MutableLiveData()
    }

    fun setCountryEntity(country: CountryModel) {
        country.let {
            this.countryEntity = it
            viewModelScope.launch {
                countryHistoryLiveData = covidRepo.getCountryHistory(country.country)
            }
        }

    }

    fun getCountryHistory(): LiveData<LocalCountryHistory> {
        return countryHistoryLiveData
    }

    fun addCountrySubscribed(countyEntity: CountryModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                covidRepo.insertContrySubscribed(
                    CountryEntitySubscribed(
                        countyEntity.country,
                        countyEntity.cases, countyEntity.countryInfo?.flag ?: ""
                    )
                )
            }
        }
    }

    fun isCountrySubscribed(countryName: String): LiveData<CountryEntitySubscribed> {
        var countrySubscribed: LiveData<CountryEntitySubscribed> =
            MutableLiveData<CountryEntitySubscribed>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                countrySubscribed = covidRepo.getCountrySubscribed(countryName)
            }
        }
        return countrySubscribed
    }

    fun deleteSubscribedCountry(countyEntity: CountryModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                covidRepo.deleteCountrySubscribed(
                    CountryEntitySubscribed(
                        countyEntity.country,
                        countyEntity.cases,
                        countyEntity.countryInfo?.flag ?: ""
                    )
                )
            }
        }
    }

}
