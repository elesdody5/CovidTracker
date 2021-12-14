package com.example.covidtracker.domain

import android.os.Parcelable
import com.example.covidtracker.database.CountyEntity
import com.example.covidtracker.database.LocalCountryInfo
import kotlinx.android.parcel.Parcelize

/*
*   Model class that represents in UI to user
*
* */
@Parcelize
data class CountryModel(
    val country: String = "",
    val countryInfo: LocalCountryInfo? = null,
    val cases: Long = 0,
    val deaths: Long = 0,
    val recovered: Long = 0,
    val todayDeaths: Long = 0,
    val active: Long = 0,
    val critical: Long = 0,
    val casesPerOneMillion: Long = 0,
    val deathsPerOneMillion: Double = 0.0,
    val updated: Long = 0,
    val todayCases: Long = 0

) : Parcelable

fun List<CountyEntity>.asCountryModelList(): List<CountryModel> {
    return map {
        CountryModel(
            country = it.country,
            countryInfo = it.countryInfo,
            cases = it.cases,
            deaths = it.deaths,
            recovered = it.recovered,
            todayDeaths = it.todayDeaths,
            active = it.active,
            critical = it.critical,
            casesPerOneMillion = it.casesPerOneMillion,
            deathsPerOneMillion = it.deathsPerOneMillion,
            updated = it.updated,
            todayCases = it.todayCases
        )
    }
}

fun CountyEntity.asCountryModel(): CountryModel {
    return CountryModel(
        country = country,
        countryInfo = countryInfo,
        cases = cases,
        deaths = deaths,
        recovered = recovered,
        todayDeaths = todayDeaths,
        active = active,
        critical = critical,
        casesPerOneMillion = casesPerOneMillion,
        deathsPerOneMillion = deathsPerOneMillion,
        updated = updated,
        todayCases = todayCases
    )
}
