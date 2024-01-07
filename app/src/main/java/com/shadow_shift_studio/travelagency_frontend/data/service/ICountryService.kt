package com.shadow_shift_studio.travelagency_frontend.data.service

import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import retrofit2.Call
import retrofit2.http.GET

interface ICountryService {
    @JvmSuppressWildcards
    @GET("/country/all")
    fun getCountries(): Call<List<Country>>
}