package com.shadow_shift_studio.travelagency_frontend.data.client

import com.shadow_shift_studio.travelagency_frontend.data.service.IAuthorizationService
import com.shadow_shift_studio.travelagency_frontend.data.service.IRegistrationService
import com.shadow_shift_studio.travelagency_frontend.data.service.IToursListService
import com.shadow_shift_studio.travelagency_frontend.domain.repository.IToursListRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.13:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${KeyStoreManager.getDecryptAccessKey("1")}")
                .build()
            chain.proceed(request)
        }.build())
        .build()

    val CatalogService: IToursListService = retrofit.create(IToursListService::class.java)
}