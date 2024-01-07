package com.shadow_shift_studio.travelagency_frontend.domain.use_case

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.domain.repository.ICountyRepository
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country

class CountryUseCase(private val country: ICountyRepository) {
    suspend fun getCountries(context: Context): List<Country> {
        return country.getCountries(context)
    }
}