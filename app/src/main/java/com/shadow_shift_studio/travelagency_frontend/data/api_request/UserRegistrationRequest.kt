package com.shadow_shift_studio.travelagency_frontend.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.travelagency_frontend.data.client.RegistrationClient
import com.shadow_shift_studio.travelagency_frontend.data.credentail.CredentialsForRegistration
import com.shadow_shift_studio.travelagency_frontend.domain.repository.IRegistrationRepository
import com.shadow_shift_studio.travelagency_frontend.model.api_response.TokenResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class UserRegistrationRequest : IRegistrationRepository {
    override suspend fun registerUser(context: Context, login: String, email: String, password: String): Boolean {

        val backendService = RegistrationClient.registrationService

        val credentials = CredentialsForRegistration(login, email, password)

        return suspendCancellableCoroutine { continuation ->
            val call = backendService.register(credentials)
            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            continuation.resume(true)
                        } else {
                            continuation.resume(false)
                        }
                    } else {
                        Log.e("Registration error", response.errorBody().toString())
                        continuation.resume(false)
                    }
                }

                // Handling an error while making the request.
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false)
                }
            })
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}