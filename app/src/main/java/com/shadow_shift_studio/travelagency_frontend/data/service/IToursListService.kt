package com.shadow_shift_studio.travelagency_frontend.data.service

import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IToursListService {
    @JvmSuppressWildcards
    @GET("/tour/catalog")
    fun getCatalog(): Call<List<TourPreview>>

    @GET("/country/all")
    fun getCountry(): Call<List<Country>>
}