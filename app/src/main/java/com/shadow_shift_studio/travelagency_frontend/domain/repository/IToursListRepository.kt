package com.shadow_shift_studio.travelagency_frontend.domain.repository

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview

interface IToursListRepository {
    suspend fun getCatalog(context: Context): List<TourPreview>
    suspend fun getCountries(context: Context): List<Country>
}
