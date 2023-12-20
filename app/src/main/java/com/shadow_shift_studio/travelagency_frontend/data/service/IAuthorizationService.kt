package com.shadow_shift_studio.travelagency_frontend.data.service

import com.shadow_shift_studio.travelagency_frontend.data.credentail.CredentialsForAuthorization
import com.shadow_shift_studio.travelagency_frontend.model.api_response.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthorizationService {
    @POST("/auth/login")
    fun login(@Body credentials: CredentialsForAuthorization): Call<TokenResponse>
}