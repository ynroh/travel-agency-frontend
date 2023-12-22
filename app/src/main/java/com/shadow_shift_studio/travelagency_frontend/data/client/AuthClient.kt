package com.shadow_shift_studio.travelagency_frontend.data.client

import com.shadow_shift_studio.travelagency_frontend.data.service.IAuthorizationService
import com.shadow_shift_studio.travelagency_frontend.data.service.IRegistrationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.13:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val loginService: IAuthorizationService = retrofit.create(IAuthorizationService::class.java)
    val registrationService: IRegistrationService = retrofit.create(IRegistrationService::class.java)
}