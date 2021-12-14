package com.example.covidtracker.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type

interface DiseaseAPI {
    @GET("/v3/covid-19/all")
    suspend fun getGeneralInfo(): GeneralInfo

    @GET("/v3/covid-19/countries")
    suspend fun getCountriesData(): List<CountryData>

    @GET("/v3/covid-19/countries/{country}")
    suspend fun getCountryData(@Path("country") countryName: String): CountryData

    @GET("/v3/covid-19/historical/{country}")
    suspend fun getCountryHistory(@Path("country") countryName: String): CountryHistory
}


val moshi = Moshi.Builder()
    .add(MoshiJsonListAdapersFactory())
    .add(KotlinJsonAdapterFactory())
    .build()


class MoshiJsonListAdapersFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        return when (type) {
            Types.newParameterizedType(
                ArrayList::class.java,
                CountryData::class.java
            ) -> moshi.adapter<List<CountryData>>(type)
            Types.newParameterizedType(
                ArrayList::class.java,
                String::class.java
            ) -> moshi.adapter<List<String>>(type)
            else -> null
        }
    }
}

object NetworkService {
    private val retrofitInstance = Retrofit.Builder().baseUrl(Constants.BASE_URL.value)
        .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    val INSTANCE = retrofitInstance.create(DiseaseAPI::class.java)
}
