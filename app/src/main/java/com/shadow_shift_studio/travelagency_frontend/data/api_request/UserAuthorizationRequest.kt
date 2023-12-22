package com.shadow_shift_studio.travelagency_frontend.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.travelagency_frontend.data.client.AuthClient
import com.shadow_shift_studio.travelagency_frontend.data.client.KeyStoreManager
import com.shadow_shift_studio.travelagency_frontend.data.credentail.CredentialsForAuthorization
import com.shadow_shift_studio.travelagency_frontend.domain.repository.ILoginRepository
import com.shadow_shift_studio.travelagency_frontend.model.api_response.TokenResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class UserAuthorizationRequest : ILoginRepository {
    override suspend fun loginUser(context: Context, login: String, password: String): Boolean {
        // Create an instance of the remote service caller
        val backendService = AuthClient.loginService
        // Create an instance with user credentials
        val credentials = CredentialsForAuthorization(login, password)

        // Use suspendCancellableCoroutine for handling asynchronous code
        return suspendCancellableCoroutine { continuation ->
            // Create a call to the remote service
            val call = backendService.login(credentials)

            // Handling a successful response from the server
            call.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            // Create a KeyStoreManager object to manage the key store
                            val keyStore = KeyStoreManager.getKeyStore(context)
                            Log.e("123456", responseBody.accessToken)
                            // Create and encrypt access and refresh tokens
                            keyStore.createSecretKey("1")
                            keyStore.createSecretKey("2")
                            val encryptedAccessToken = keyStore.encryptData("1", responseBody.accessToken.toByteArray())
                            val encryptedRefreshToken = keyStore.encryptData("2", responseBody.updateToken.toByteArray())

                            // Save tokens in KeyStoreManager for future use
                            KeyStoreManager.accessToken = encryptedAccessToken
                            KeyStoreManager.refreshToken = encryptedRefreshToken

                            continuation.resume(true) // Resume the coroutine with a successful result
                        } else {
                            continuation.resume(false) // Return `false` in case of missing data in the response
                        }
                    } else {
                        Log.e("Authorization error", response.errorBody().toString())
                        continuation.resume(false) // Return `false` in case of an error response
                    }
                }

                // Handling an error while making the request
                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                    continuation.resume(false) // Return `false` in case of an error
                }
            })

            // Cancel the call when the coroutine is canceled
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }
    }
}