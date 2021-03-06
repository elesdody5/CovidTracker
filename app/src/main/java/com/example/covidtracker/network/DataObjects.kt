package com.example.covidtracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryData(
    val country: String,
    val countryInfo: CountryInfo,
    val cases: Long,
    val todayCases: Long,
    val deaths: Long,
    val todayDeaths: Long,
    val recovered: Long,
    val active: Long,
    val critical: Long,
    val casesPerOneMillion: Long,
    val deathsPerOneMillion: Double,
    val updated: Long
)

@JsonClass(generateAdapter = true)
data class CountryInfo(
    val _id: Int?,
    val iso2: String?,
    val iso3: String?,
    val flag: String?,
    val lat: Double,
    val long: Double
)

@JsonClass(generateAdapter = true)
data class GeneralInfo(
    val updated: Long,
    val cases: Long,
    val todayCases: Long,
    val deaths: Long,
    val todayDeaths: Long,
    val recovered: Long,
    val active: Long,
    val critical: Long,
    val casesPerOneMillion: Long,
    val deathsPerOneMillion: Double,
    val affectedCountries: Long
)

@JsonClass(generateAdapter = true)
data class CountryHistory(
    val country: String, val province: List<String>, val timeline: History
)

@JsonClass(generateAdapter = true)
data class History(
    val cases: Map<String, Long>, val deaths: Map<String, Long>, val recovered: Map<String, Long>
)


