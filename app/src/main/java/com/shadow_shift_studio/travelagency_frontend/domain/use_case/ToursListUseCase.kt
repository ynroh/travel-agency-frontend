package com.shadow_shift_studio.travelagency_frontend.domain.use_case

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.domain.repository.IToursListRepository
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview

class ToursListUseCase(private val catalog: IToursListRepository) {
    suspend fun getCatalog(context: Context): List<TourPreview> {
        return catalog.getCatalog(context)
    }

    suspend fun getCountries(context: Context): List<Country> {
        return catalog.getCountries(context)
    }
}