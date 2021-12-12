package com.example.covidtracker.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.covidtracker.CovidApplication
import com.example.covidtracker.database.CountryEntitySubscribed
import com.example.covidtracker.domain.CountryModel
import com.example.covidtracker.repository.RepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribedViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val covidRepo :RepositoryContract
    private val countryList : LiveData<List<CountryModel>>
    private var countriesLiveData : LiveData<List<CountryEntitySubscribed>>
    init {
        covidRepo = (application.applicationContext as CovidApplication).repository
        countryList = covidRepo.countryList
        countriesLiveData = MutableLiveData()
        viewModelScope.launch {
            countriesLiveData = covidRepo.getAllCoutrySubscribedLiveData()
        }
    }

    fun getFavouriteCountryList():LiveData<List<CountryEntitySubscribed>>{
        return countriesLiveData
    }
    fun deleteSubscribedCountry(countryEntitySubscribed: CountryEntitySubscribed){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                covidRepo.deleteCountrySubscribed(countryEntitySubscribed)
            }
        }
    }

    fun getEquivalentCountryModel(countryEntitySubscribed: CountryEntitySubscribed): CountryModel{
        return countryList.value?.filter {
            it.country.equals(countryEntitySubscribed.country)
        }!!.first()
    }
}
