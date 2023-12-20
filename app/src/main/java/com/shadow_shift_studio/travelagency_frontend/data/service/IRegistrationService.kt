package com.shadow_shift_studio.travelagency_frontend.data.service

import com.shadow_shift_studio.travelagency_frontend.data.credentail.CredentialsForRegistration
import com.shadow_shift_studio.travelagency_frontend.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IRegistrationService {
    @POST("/auth/register")
    fun register(@Body credentials: CredentialsForRegistration): Call<TokenResponse>
}