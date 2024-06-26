package com.shadow_shift_studio.travelagency_frontend.data.api_request

import android.content.Context
import android.util.Log
import com.shadow_shift_studio.travelagency_frontend.data.client.AuthClient
import com.shadow_shift_studio.travelagency_frontend.domain.repository.IToursListRepository
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

class ToursListRequest: IToursListRepository {
    override suspend fun getCatalog(context: Context): List<TourPreview> {

        val backendService = AuthClient.CatalogService

        val tourForErrorResponse = listOf<TourPreview>()

        try {
            return suspendCancellableCoroutine { continuation ->
                val call = backendService.getCatalog()

                call.enqueue(object : Callback<List<TourPreview>> {
                    override fun onResponse(call: Call<List<TourPreview>>, response: Response<List<TourPreview>>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                continuation.resume(responseBody)
                            } else {
                                continuation.resume(tourForErrorResponse)
                            }
                        } else {
                            Log.e("Tours get error", response.errorBody().toString())
                            continuation.resume(tourForErrorResponse)
                        }
                    }
                    override fun onFailure(call: Call<List<TourPreview>>, t: Throwable) {
                        Log.e("Network client error", t.message ?: "HTTP client failed to connect")
                        continuation.resume(tourForErrorResponse)
                    }
                })

                continuation.invokeOnCancellation {
                    call.cancel()
                }
            }
        } catch (e: Exception) {
            Log.e("Unknown Error", e.toString())
        }

        return tourForErrorResponse
    }


}